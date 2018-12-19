package banger.job;

import banger.common.tools.ZSCalculateBeanUtils;
import banger.common.tools.ZSCalculatorBean;
import banger.domain.config.AutoBaseField;
import banger.domain.config.AutoBaseTemplate;
import banger.domain.config.AutoFieldWrapper;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanRepayPlanInfo;
import banger.domain.loan.tmp.TmpLoanAccount;
import banger.domain.loan.tmp.TmpLoanInfo;
import banger.domain.loan.tmp.TmpLoanRepaymentPlan;
import banger.framework.collection.DataTable;
import banger.framework.spring.SpringContext;
import banger.framework.util.DateUtil;
import banger.moduleIntf.IConfigService;
import banger.moduleIntf.ITmpLoanDataService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Administrator on 2017/10/13.
 */
public class TmpLoanDataJob implements Job {

    ITmpLoanDataService tmpLoanDataService = (ITmpLoanDataService) SpringContext.instance().get("tmpLoanDataService");
    IConfigService configService = (IConfigService) SpringContext.instance().get("configServiceImpl");

    private static final Logger log = LoggerFactory.getLogger(TmpLoanDataJob.class);


    @Override
    public void execute(JobExecutionContext jobExecutionContext)  {
        log.info("//==============TmpLoanDataJob begin... ============== ");

        //
        long beginTimes = System.currentTimeMillis();

        //贷款信息核实接口（全量）
        checkLoanAccount();

        //还款计划（增量|全量）
//        setLoanRepaymentPlan();

        //贷款账户信息接口（增量|全量）
        updateLoanAccount();

        //还款记录（增量同步数据，表中为全量）（充盈管理，为客户经理计算出所催收贷款   下次 应还日期、应还金额、还款账户余额）
        readRepaymentItem();

        log.info("//==============TmpLoanDataJob end, in "+(System.currentTimeMillis()-beginTimes)+"ms... ============== ");
    }


    private void checkLoanAccount() {
        log.info("  1贷款信息核实 开始...");
        //查询贷款账户==null 合同号!=null  同时进入到出账为确认状态的贷款
        Map<String,Object> condition = new HashMap<String, Object>();
        condition.put("UndisclosedORAfter", "true");
        condition.put("loanAccountNotExist", "true");
        List<LoanApplyInfo> loanList = tmpLoanDataService.queryApplyInfoList(condition);

        //begin...1根据合同号查询贷款记录 2同步贷款帐号 合同金额 3贷款进入贷后阶段
        if(CollectionUtils.isEmpty(loanList)){
            return;
        }

        String loanContractNumber ;
        TmpLoanInfo tmpLoanInfo ;
        for (LoanApplyInfo loan : loanList) {
            loanContractNumber = loan.getLoanContractNumber();
            if(StringUtils.isBlank(loanContractNumber)) {
                continue;
            }

            //1根据合同号查询贷款记录
            tmpLoanInfo = tmpLoanDataService.getTmpLoanInfoByContractNo(loanContractNumber);
            if(null==tmpLoanInfo||StringUtils.isBlank(tmpLoanInfo.getLoanAccount())){
                continue;
            }

            //2同步贷款帐号 合同金额 3贷款进入贷后阶段
            loan.setLoanAccountNo(tmpLoanInfo.getLoanAccount());
//            loan.setLoanCreditDate(new Date());
            loan.setLoanAccountAmount(null != tmpLoanInfo.getLoanMoney() ? tmpLoanInfo.getLoanMoney() : loan.getLoanResultAmount());
            loan.setLoanCreditAmount(null != tmpLoanInfo.getLoanMoney() ? tmpLoanInfo.getLoanMoney() : loan.getLoanResultAmount());
            loan.setLoanProcessType(LoanProcessTypeEnum.AFTER_LOAN.type);
            loan.setUpdateDate(new Date());
            loan.setUpdateUser(0);
            loan.setLoanCreditUserId(0);
            //合同开始时间 合同到期时间
            String contractBeginStr = tmpLoanInfo.getBeginDate();
            String contractEndStr = tmpLoanInfo.getEndDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            if(StringUtils.isNotBlank(contractBeginStr)){
                try {
                    Date date = sdf.parse(contractBeginStr);
                    loan.setLoanContractBegin(date);
                } catch (ParseException e) {
                    log.error(" 合同开始日期格式错误，但不影响数据的同步！");
                }
            }
            if(StringUtils.isNotBlank(contractEndStr)){
                try {
                    Date date = sdf.parse(contractEndStr);
                    loan.setLoanContractEnd(date);
                } catch (ParseException e) {
                    log.error(" 合同结束日期格式错误，但不影响数据的同步！");
                }
            }
            tmpLoanDataService.updateApplyInfo(loan);
        }
        //end...
        log.info("  1贷款信息核实 结束...");
    }



    private void setLoanRepaymentPlan() {
        log.info("  2还款计划 开始...");

        //查询处于贷款状态的贷款，覆盖更新还款计划
        Map<String,Object> condition = new HashMap<String, Object>();
        condition.put("loanProcessType", LoanProcessTypeEnum.AFTER_LOAN.type);
        List<LoanApplyInfo> loanList = tmpLoanDataService.queryApplyInfoList(condition);

        if(CollectionUtils.isEmpty(loanList)){
            return;
        }

        String loanAccount ;
        List<TmpLoanRepaymentPlan> tmpRepaymentPlan;
        List<LoanRepayPlanInfo> planList;
        LoanRepayPlanInfo plan;
        List<ZSCalculatorBean> beanList;
        Map<String, Object> map;
        for(LoanApplyInfo loan : loanList){
            loanAccount = loan.getLoanAccountNo();
            if(StringUtils.isBlank(loanAccount)){
                continue;
            }

            tmpRepaymentPlan = tmpLoanDataService.queryTmpRepaymentPlanByLoanAccount(loanAccount);
            planList = tmpLoanDataService.getLoanRepayPlanInfoListByLoanId(loan.getLoanId(), LoanProcessTypeEnum.LOAN.type);
            if(!CollectionUtils.isEmpty(tmpRepaymentPlan)){
                //对比更新还款计划  不写了  没有更新 TODO
            }else if(CollectionUtils.isEmpty(planList)){//没有还款计划 自己生成还款计划

                map = getLoanInfoMap(loan.getLoanId());
                //贷款金额
                BigDecimal resultMoney = new BigDecimal(MapUtils.getString(map, "LBIZ_APPROVAL_RESOLUTION.RESULT_AMOUNT", "0"));
                //贷款利率
                BigDecimal resultRatio = new BigDecimal(MapUtils.getString(map,"LBIZ_APPROVAL_RESOLUTION.RESULT_RATIO","0"));
                //贷款期限
                int resultLimit = MapUtils.getIntValue(map,"LBIZ_APPROVAL_RESOLUTION.RESULT_LIMIT",0);
                //还款日
                int resultDay = MapUtils.getIntValue(map,"LBIZ_APPROVAL_RESOLUTION.REPAYMENT_DATE",0);
                //还款方式 1等额本金    2等额本息    3按月还息到期还本    4一次性还本付息     5灵活还款
                int resultMode = MapUtils.getIntValue(map,"LBIZ_APPROVAL_RESOLUTION.REPAYMENT_MODE",0);

                //贷款金额
                BigDecimal grantMoney = new BigDecimal(MapUtils.getString(map, "LBIZ_LOAN_GRANT.LOAN_AMOUNT", resultMoney.toString()));
                //贷款利率
                BigDecimal grantRatio = new BigDecimal(MapUtils.getString(map,"LBIZ_LOAN_GRANT.LOAN_RATIO",resultRatio.toString()));
                //贷款期限
                int grantLimit = MapUtils.getIntValue(map,"LBIZ_LOAN_GRANT.LOAN_LIMIT",resultLimit);
                //还款日
                int grantDay = MapUtils.getIntValue(map,"LBIZ_LOAN_GRANT.LOAN_BACK_DATE",resultDay);
                //还款方式 1等额本金    2等额本息    3按月还息到期还本    4一次性还本付息     5灵活还款
                int grantMode = MapUtils.getIntValue(map,"LBIZ_LOAN_GRANT.LOAN_BACK_MODE",resultMode);

                //放款起始日期
                String loanRatioDate = MapUtils.getString(map, "LBIZ_LOAN_GRANT.LOAN_RATIO_DATE");
                //放款终止日期
                String loanEndDate = MapUtils.getString(map, "LBIZ_LOAN_GRANT.LOAN_END_DATE");

                //合同开始时间
//                Date beginDate = loan.getLoanContractBegin();

                beanList = new ArrayList<ZSCalculatorBean>();
                if(grantMode==1){
                    beanList = ZSCalculateBeanUtils.calculatorBeanList1(grantMoney, grantRatio, grantLimit, DateUtil.parser(loanRatioDate, DateUtil.DEFAULT_DATE_FORMAT), loanEndDate, grantDay);
                }else if(grantMode==2){
                    beanList = ZSCalculateBeanUtils.calculatorBeanList2(grantMoney, grantRatio, grantLimit, DateUtil.parser(loanRatioDate, DateUtil.DEFAULT_DATE_FORMAT), loanEndDate, grantDay);
                }else if(grantMode==3){
                    beanList = ZSCalculateBeanUtils.calculatorBeanList3(grantMoney,grantRatio,grantLimit,DateUtil.parser(loanRatioDate, DateUtil.DEFAULT_DATE_FORMAT), loanEndDate, grantDay);
                } if(grantMode==4){
                    beanList = ZSCalculateBeanUtils.calculatorBeanList4(grantMoney, grantRatio, grantLimit, DateUtil.parser(loanRatioDate, DateUtil.DEFAULT_DATE_FORMAT), loanEndDate, grantDay);
                }

                if(CollectionUtils.isNotEmpty(beanList)){
                    for (ZSCalculatorBean bean : beanList) {
                        plan = new LoanRepayPlanInfo();
                        plan.setLoanId(loan.getLoanId());
                        plan.setLoanRepayDate(bean.getRepayDate());
                        plan.setLoanPrincipalAmount(bean.getNumMonthCapital());
                        plan.setLoanAccrualAmount(bean.getNumMonthInterest());
                        plan.setLoanRepayState(0);
                        plan.setLoanIsOverdue(0);
                        plan.setCreateDate(new Date());
                        plan.setCreateUser(0);
                        plan.setRepaymentMode(String.valueOf(resultMode));
                        plan.setLoanAccrualDays(bean.getNumDays());
                        tmpLoanDataService.insertRepayPlanInfo(plan);
                    }
                }
            }

        }





        log.info("  2还款计划 结束...");
    }

    //当数据 量比较大时候 1可以用存储过程触发器来实现 2当然也可以要求行里增量同步 这样数据不会很多
    private void updateLoanAccount() {
        log.info("  3贷款账户信息 开始...");

        //查询 所有 同步的贷款账户
        Map<String,Object> condition = new HashMap<String, Object>();
        List<TmpLoanAccount> tmpLoanAccountList = tmpLoanDataService.queryTmpLoanAccountList(condition);
        if(CollectionUtils.isEmpty(tmpLoanAccountList)){
            return;
        }

        // begin.... 更新贷款余额 //还款账户余额
        // 贷款余额为0的 更新为结清
        // !!!但是随借随还的 只有合同时间到期才算结清
        // 随借随还 当有余额>0同时本月没有还款计划时候 新增一条还款计划//
        String loanAccount ;
        BigDecimal loanBalance ;//贷款余额
        BigDecimal nextRepaymentAmount ;//应还款金额
        BigDecimal repaymentBalance ;//还款账户余额
        LoanApplyInfo loan ;
        LoanRepayPlanInfo plan ;
        for(TmpLoanAccount tmpLoanAccount : tmpLoanAccountList){
            loanAccount = tmpLoanAccount.getLoanAccount();
            if(StringUtils.isBlank(loanAccount)){
                continue;
            }
            //根据贷款账户查询贷款记录
            loan = tmpLoanDataService.getApplyInfoByLoanAccount(loanAccount);
            if(null==loan){
                continue;
            }

            //update
            loanBalance = tmpLoanAccount.getLoanBalance();
            if(null==loanBalance){
                loanBalance = new BigDecimal(0);
            }
            nextRepaymentAmount = tmpLoanAccount.getNextRepaymentAmount();
            if(null==nextRepaymentAmount){
                nextRepaymentAmount = new BigDecimal(0);
            }
            repaymentBalance = tmpLoanAccount.getRepaymentBalance();
            if(null==repaymentBalance){
                repaymentBalance = new BigDecimal(0);
            }

            loan.setLoanBalanceAmount(loanBalance);
            loan.setLoanAccountAmount(repaymentBalance);
            loan.setUpdateDate(new Date());
            loan.setUpdateUser(0);
            if(loanBalance.compareTo(new BigDecimal(0))==0&&loan.getLoanContractEnd().before(new Date())){
                loan.setLoanProcessType(LoanProcessTypeEnum.CLEARANCE.type);
            }else if(loan.getLoanProcessType().equals(LoanProcessTypeEnum.CLEARANCE.type)){
                loan.setLoanProcessType(LoanProcessTypeEnum.AFTER_LOAN.type);
            }



            //查询还款计划 有更新(还款计划表设计的我不知道要更新什么？？？) 没有新增
            plan = tmpLoanDataService.getTopLoanRepayPlanInfo(loan.getLoanId());
            if(null==plan&&nextRepaymentAmount.compareTo(new BigDecimal(0))==1){
                plan = new LoanRepayPlanInfo();
                plan.setCreateDate(new Date());
                plan.setCreateUser(0);
                plan.setLoanIsOverdue(0);
                plan.setLoanRepayPlanDate(getNext21Day());
                plan.setLoanPrincipalAmount(nextRepaymentAmount);
                plan.setLoanRepayState(0);
                tmpLoanDataService.insertRepayPlanInfo(plan);
            }

            //更新贷款主表 还款日期 还款金额
//            LOAN_REPAY_AMOUNT	贷款应还款金额
//            LOAN_REPAY_DATE	贷款应还款日期
            loan.setLoanRepayAmount(nextRepaymentAmount);
            loan.setLoanRepayDate(plan.getLoanRepayDate());
            tmpLoanDataService.updateApplyInfo(loan);

        }
        //end....

        log.info("  3贷款账户信息 结束...");
    }




    private void readRepaymentItem() {
        log.info("  4还款记录 开始...");

        //查询贷后状态贷款，根据贷款帐号查询还款记录 计算还款计划是否有还款完成
        Map<String,Object> condition = new HashMap<String, Object>();
        condition.put("loanProcessType", LoanProcessTypeEnum.AFTER_LOAN.type);
        List<LoanApplyInfo> loanList = tmpLoanDataService.queryApplyInfoList(condition);

        BigDecimal sum;
        BigDecimal loanPrincipalAmount;//还款本金
        BigDecimal loanAccrualAmount;//还款利息
        for (LoanApplyInfo loan : loanList){
            //查询当前贷款的还款记录 总额
            sum = tmpLoanDataService.sumPaymentAmountByAccount(loan.getLoanAccountNo());
            if(null==sum){
                sum = new BigDecimal(0);
            }

            //查询当前贷款的还款计划 已还总额抵消还款计划
            List<LoanRepayPlanInfo> planList =  tmpLoanDataService.getLoanRepayPlanInfoListByLoanId(loan.getLoanId(),LoanProcessTypeEnum.LOAN.type);
            for (LoanRepayPlanInfo plan : planList) {
                loanPrincipalAmount = plan.getLoanPrincipalAmount();
                if(null==loanPrincipalAmount){
                    loanPrincipalAmount = new BigDecimal(0);
                }
                loanAccrualAmount = plan.getLoanAccrualAmount();
                if(null==loanAccrualAmount){
                    loanAccrualAmount = new BigDecimal(0);
                }
                sum = sum.subtract(loanPrincipalAmount.add(loanAccrualAmount));

                //当前计划结清  //更新结清状态 结清状态 结清金额
                if(sum.compareTo(new BigDecimal(0))>0&&(null==plan.getLoanRepayState()||plan.getLoanRepayState()!=1)){
                    plan.setLoanRepayAmount(loanPrincipalAmount);
                    plan.setLoanRepayAccrualAmount(loanAccrualAmount);
                    if(null==plan.getLoanRepayState()||1!=plan.getLoanRepayState()){
                        plan.setLoanRepayDate(new Date());
                    }
                    plan.setLoanRepayState(1);
                    tmpLoanDataService.updateRepayPlanInfo(plan);
                }
            }

        }

        log.info("  4还款记录 结束...");
    }


    private Date getNext21Day(){
        Calendar ca = Calendar.getInstance();
        int day = ca.get(Calendar.DAY_OF_MONTH);
        if(day>=21){
            int month = ca.get(Calendar.MONTH);
            ca.set(Calendar.MONTH,month+1);
        }
        ca.set(Calendar.DAY_OF_MONTH,21);
        return new Date(ca.getTimeInMillis());
    }


    //根据贷款id 查询贷款数据 form
    private Map<String, Object> getLoanInfoMap(Integer loanId) {

        Map<String, Object> map = new HashMap<String, Object>();

        //查询所有表单
        List<AutoBaseTemplate> templateList = configService.getAllTemplateList();
        if(org.springframework.util.CollectionUtils.isEmpty(templateList)){
            return map;
        }
        for(AutoBaseTemplate template : templateList){
            //1单数据表单form  2table
            if(template.getType()!="1"){
                continue;
            }
            DataTable datatable = tmpLoanDataService.getDataTableByLoanId(template.getTableName(), "", loanId);
            //
            if(datatable!=null && datatable.rowSize()>0){
                template.setData(datatable.getRow(0));
                List<AutoBaseField> fieldList = template.getFields();
                for (int i = 0; i < fieldList.size(); i++) {
                    AutoFieldWrapper field = (AutoFieldWrapper)fieldList.get(i);
                    Object value = "";
                    if(null!=template.getData()){
                        value = banger.framework.reader.Reader.objectReader().getValue(template.getData(), field.getColumn());
                    }
                    if(null!=value){
                        String key = template.getTableName() + "." + field.getField();
                        map.put(key, value);
                    }
                }
            }
        }
        return map;

    }
}
