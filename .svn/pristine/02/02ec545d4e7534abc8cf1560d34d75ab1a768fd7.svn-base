package banger.service.impl;

import banger.common.tools.CalculateUtils;
import banger.common.tools.LoanCalculatorBean;
import banger.common.tools.ZSCalculateBeanUtils;
import banger.common.tools.ZSCalculatorBean;
import banger.dao.intf.IApplyInfoDao;
import banger.dao.intf.IRepayPlanInfoDao;
import banger.domain.enumerate.InterestUnitEnum;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.enumerate.LoanRepayPlanEnum;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanRepayPlanInfo;
import banger.domain.loan.LoanRepayPlanInfoExtend;
import banger.domain.system.SysBasicConfig;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.framework.util.OperationUtil;
import banger.moduleIntf.IRepayPlanInfoProvider;
import banger.moduleIntf.ISystemModule;
import banger.service.intf.IApplyInfoService;
import banger.service.intf.IRepayPlanInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

/**
 * 贷款还信催款息表业务访问类
 */
@Repository
public class RepayPlanInfoService implements IRepayPlanInfoService, IRepayPlanInfoProvider {

	private static final Logger logger = LoggerFactory.getLogger(RepayPlanInfoService.class);
	@Autowired
	private IRepayPlanInfoDao repayPlanInfoDao;
	@Autowired
	private IApplyInfoService applyInfoService;

	@Autowired
	private IApplyInfoDao applyInfoDao;
	@Autowired
	private ISystemModule systemModule;

    @Override
    public void savePlanList(List<LoanRepayPlanInfoExtend> loanRepayPlanInfos) {
        for (LoanRepayPlanInfoExtend info : loanRepayPlanInfos) {
            LoanRepayPlanInfo info1 = info;
            if (info.getId() != null && info.getId() > 0) {
                repayPlanInfoDao.updateRepayPlanInfo(info1);
            } else {
                repayPlanInfoDao.insertRepayPlanInfo(info1);
            }
        }
    }

	/**
	 * 通过主键得到贷款申请表
	 * @param loanId 主键Id
	 */
	@Override
	public LoanApplyInfo getApplyInfoById(Integer loanId) {
		return applyInfoService.getApplyInfoById(loanId);
	}

	/**
	 * 新增贷款还信催款息表
	 * @param repayPlanInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertRepayPlanInfo(LoanRepayPlanInfo repayPlanInfo,Integer loginUserId){
		repayPlanInfo.setCreateUser(loginUserId);
		repayPlanInfo.setCreateDate(DateUtil.getCurrentDate());
		repayPlanInfo.setUpdateUser(loginUserId);
		repayPlanInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.repayPlanInfoDao.insertRepayPlanInfo(repayPlanInfo);
	}

	/**
	 *修改贷款还信催款息表
	 * @param repayPlanInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateRepayPlanInfo(LoanRepayPlanInfo repayPlanInfo,Integer loginUserId){
		repayPlanInfo.setUpdateUser(loginUserId);
		repayPlanInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.repayPlanInfoDao.updateRepayPlanInfo(repayPlanInfo);
	}

	/**
	 * 通过主键删除贷款还信催款息表
	 */
	public void deleteRepayPlanInfoById(Integer id){
		this.repayPlanInfoDao.deleteRepayPlanInfoById(id);
	}

	/**
	 * 通过主键得到贷款还信催款息表
	 */
	public LoanRepayPlanInfo getRepayPlanInfoById(Integer id){
		return this.repayPlanInfoDao.getRepayPlanInfoById(id);
	}

	/**
	 * 查询贷款还信催款息表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanRepayPlanInfo> queryRepayPlanInfoList(Map<String,Object> condition){
		return this.repayPlanInfoDao.queryRepayPlanInfoList(condition);
	}

	/**
	 * 分页查询贷款还信催款息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanRepayPlanInfo> queryRepayPlanInfoList(Map<String,Object> condition,IPageSize page){
		return this.repayPlanInfoDao.queryRepayPlanInfoList(condition,page);
	}
	/**
	 * 查询催款列表
	 * @param loanId
	 * @return
	 */
	public List<LoanRepayPlanInfoExtend> queryLoanRepayPlanInfoByLoanId(Integer loanId) {
		return queryLoanRepayPlanInfoByLoanId(loanId, null, LoanProcessTypeEnum.LOAN.type);
	}
    public List<LoanRepayPlanInfoExtend> queryLoanRepayPlanInfoByLoanId(Integer loanId, String repaymentMode, String processType) {
        return this.repayPlanInfoDao.queryLoanRepayPlanInfoByLoanId(loanId, repaymentMode, processType);
    }



	public List<LoanRepayPlanInfo> queryListByLoanRepayPlanDate(Map<String, Object> condition) {
    	return this.repayPlanInfoDao.queryListByLoanRepayPlanDate(condition);
    }

    @Override
    public List<LoanRepayPlanInfo> queryNextPlan(Map<String, Object> condition) {
    	return this.repayPlanInfoDao.queryNextPlan(condition);
	}

    //查询最早的未催收
    public List<LoanRepayPlanInfo> queryUnConllection(Map<String, Object> condition) {
    	return this.repayPlanInfoDao.queryUnConllection(condition);
    }
	//查询催收状态为完成的
	public List<LoanRepayPlanInfo> queryComplete(Map<String, Object> condition) {
    	return this.repayPlanInfoDao.queryComplete(condition);
	}

	@Override
	public List<LoanRepayPlanInfoExtend> getRepayPlanInfoListForI(String loanId, String repaymentMode, String proposalAmount, String proposalLimit, String proposalRatio,String processType, Integer loginUserId) {
		//TODO 版本定制
		if (!"1".equals(repaymentMode) && !"2".equals(repaymentMode)  && !"3".equals(repaymentMode))
			repaymentMode = LoanRepayPlanEnum.FLEXIBLE_REPAYMENT.value;
		List<LoanRepayPlanInfoExtend> loanRepayPlanInfos = new ArrayList<LoanRepayPlanInfoExtend>();
		try {
			if ("0".equals(proposalAmount)) return loanRepayPlanInfos;
			if (LoanRepayPlanEnum.EQUAL_PRINCIPAL_ACCRUAL.value.equals(repaymentMode) || LoanRepayPlanEnum.EQUAL_PRINCIPAL.value.equals(repaymentMode)) {
				setLoanRepayPlanInfos(proposalRatio, repaymentMode, proposalAmount, proposalLimit, loginUserId, loanId, loanRepayPlanInfos);
			} else {
				//调查阶段只有灵活还款能查得出数据
				loanRepayPlanInfos = queryLoanRepayPlanInfoByLoanId(Integer.parseInt(loanId), repaymentMode, processType);
				if (CollectionUtils.isEmpty(loanRepayPlanInfos)) {
					setNewPlanByLimit(proposalLimit, proposalAmount, null, loanRepayPlanInfos);
				} else {
					setFlexible(repaymentMode, loanRepayPlanInfos);
				}
			}
		} catch (Exception e) {
			System.out.println("参数错误，请检查参数");
			return loanRepayPlanInfos;
		}
		//用第一个计划金额临时保存总金额（灵活还款使用），此处不要使用getRepayment，因为get方法已经重写
		if (loanRepayPlanInfos.get(0).repayment == null)
			loanRepayPlanInfos.get(0).setRepayment(new BigDecimal(proposalAmount));
		return loanRepayPlanInfos;
	}

	/**
	 * 如果是灵活还款，设置灵活还款所有金额
	 * @param repaymentMode
	 * @param loanRepayPlanInfos
	 */
	private void setFlexible(String repaymentMode, List<LoanRepayPlanInfoExtend> loanRepayPlanInfos) {
		if (LoanRepayPlanEnum.FLEXIBLE_REPAYMENT.value.equals(repaymentMode)) {
			BigDecimal all = new BigDecimal("0");
			for (LoanRepayPlanInfo loanRepayPlanInfo : loanRepayPlanInfos){
				if (loanRepayPlanInfo.getLoanPrincipalAmount() != null)
					all = all.add(loanRepayPlanInfo.getLoanPrincipalAmount());
			}
			loanRepayPlanInfos.get(0).setRepayment(all);
		}
	}

	private void setLoanRepayPlanInfos(String proposalRatio, String repaymentMode, String proposalAmount, String proposalLimit, Integer loginUserId, String loanId,
									   List<LoanRepayPlanInfoExtend> loanRepayPlanInfos) {
    	setLoanRepayPlanInfos(proposalRatio,repaymentMode, proposalAmount, proposalLimit, loginUserId, loanId, loanRepayPlanInfos, false, null, null);
	}
	private void setLoanRepayPlanInfos(String proposalRatio, String repaymentMode, String proposalAmount, String proposalLimit, Integer loginUserId, String loanId,
									   List<LoanRepayPlanInfoExtend> loanRepayPlanInfos, boolean isLoan, String ratioDate, String loanBackDate) {
		//调查阶段的等额本息不需要利率
		if (StringUtils.isBlank(proposalRatio))
			proposalRatio = "0";
		double dayRatio = 0l;

		Calendar calendar = Calendar.getInstance();
		Calendar r = Calendar.getInstance();
		if (isLoan) {
			r.setTime(DateUtil.parser(ratioDate, DateUtil.DEFAULT_DATE_FORMAT));
			calendar.setTime(DateUtil.parser(loanBackDate, DateUtil.DEFAULT_DATE_FORMAT));
		}
		List<LoanCalculatorBean> loanCalculatorBeans = new ArrayList<LoanCalculatorBean>();
		if (LoanRepayPlanEnum.EQUAL_PRINCIPAL.value.equals(repaymentMode))
			loanCalculatorBeans = CalculateUtils.calculatorBeanList2(Double.parseDouble(proposalAmount), getMonthRatio(proposalRatio), Integer.parseInt(proposalLimit));
		else
			loanCalculatorBeans = CalculateUtils.calculatorBeanList(Double.parseDouble(proposalAmount), getMonthRatio(proposalRatio), Integer.parseInt(proposalLimit));
		for (LoanCalculatorBean bean : loanCalculatorBeans) {
			LoanRepayPlanInfoExtend loanRepayPlanInfo = new LoanRepayPlanInfoExtend();
			loanRepayPlanInfo.setDefaultValue(loginUserId, Integer.parseInt(loanId));
			loanRepayPlanInfo.setRepayment(new BigDecimal(bean.getNumMonthPay()).setScale(2,BigDecimal.ROUND_HALF_DOWN));
			loanRepayPlanInfo.setLoanRepayPlanDate(calendar.getTime());
			loanRepayPlanInfo.setRatioDate(r.getTime());
			loanRepayPlanInfo.setLoanPrincipalAmount(new BigDecimal(bean.getNumMonthCapital()).setScale(2,BigDecimal.ROUND_HALF_DOWN));
			loanRepayPlanInfo.setLoanAccrualAmount(new BigDecimal(bean.getNumMonthInterest()).setScale(2,BigDecimal.ROUND_HALF_DOWN));
			loanRepayPlanInfos.add(loanRepayPlanInfo);
			calendar.add(Calendar.MONTH, 1);
			r.add(Calendar.MONTH, 1);
		}
	}


	private Double getMonthRatio(String loanRatio){
		double dayRatio = 0l;
		SysBasicConfig sysConfig = systemModule.getSysBasicConfig("lxdw");
		if (sysConfig!=null && sysConfig.getConfigValue() != null) {
			Integer ratioType = sysConfig.getConfigValue();
			if (InterestUnitEnum.INTEREST_UNIT_YEAR.value == ratioType)
				dayRatio = Double.parseDouble(loanRatio)/12;
			if (InterestUnitEnum.INTEREST_UNIT_MONTH.value == ratioType)
				dayRatio = Double.parseDouble(loanRatio);
		}
		String unit = applyInfoDao.selectUnitByTableNameAndColumnName("LBIZ_LOAN_GRANT", "LOAN_RATIO");
		double div = 1l;
		if ("%".equals(unit)) {
			div = 100;
		} else if ("‰".equals(unit)) {
			div = 1000;
		}
		return dayRatio/div;
	}

	public List<LoanRepayPlanInfoExtend> getRepayPlanInfoListForLoan_ZS(String loanId, String repaymentMode, String proposalAmount, String proposalLimit, String proposalRatio, String ratioDate, String loanEndDate, Integer date, Integer loginUserId, String processType,boolean isReset) {
		List<LoanRepayPlanInfoExtend> loanRepayPlanInfos = new ArrayList<LoanRepayPlanInfoExtend>();
		List<ZSCalculatorBean> planBeans ;
		if ("0".equals(proposalAmount)) return loanRepayPlanInfos;

		if(StringUtils.isBlank(ratioDate)){
			ratioDate = DateUtil.format(new Date(), DateUtil.DEFAULT_DATE_FORMAT);
		}

		//TODO 版本定制
//		if (!"1".equals(repaymentMode) && !"2".equals(repaymentMode)  && !"3".equals(repaymentMode))
//			repaymentMode = LoanRepayPlanEnum.FLEXIBLE_REPAYMENT.value;
		try {


			if(processType.equals(LoanProcessTypeEnum.LOAN.type)){
				loanRepayPlanInfos = queryLoanRepayPlanInfoByLoanId(Integer.parseInt(loanId), repaymentMode, LoanProcessTypeEnum.LOAN.type);
				if(CollectionUtils.isEmpty(loanRepayPlanInfos)&&!isReset){
					loanRepayPlanInfos = queryLoanRepayPlanInfoByLoanId(Integer.parseInt(loanId), repaymentMode, LoanProcessTypeEnum.APPROVAL.type);
				}
			}else if(processType.equals(LoanProcessTypeEnum.INVESTIGATE.type)){
				loanRepayPlanInfos = queryLoanRepayPlanInfoByLoanId(Integer.parseInt(loanId), repaymentMode, LoanProcessTypeEnum.INVESTIGATE.type);
			}else{
				loanRepayPlanInfos = queryLoanRepayPlanInfoByLoanId(Integer.parseInt(loanId), repaymentMode, LoanProcessTypeEnum.APPROVAL.type);
			}


			if (CollectionUtils.isEmpty(loanRepayPlanInfos)) {
				if (LoanRepayPlanEnum.EQUAL_PRINCIPAL_ACCRUAL.value.equals(repaymentMode)) {
					//等额本息
					planBeans = ZSCalculateBeanUtils.calculatorBeanList2(new BigDecimal(proposalAmount), new BigDecimal(proposalRatio), Integer.parseInt(proposalLimit), DateUtil.parser(ratioDate, DateUtil.DEFAULT_DATE_FORMAT),loanEndDate, date);
				} else if (LoanRepayPlanEnum.EQUAL_PRINCIPAL.value.equals(repaymentMode)){
					//等额本金
					planBeans = ZSCalculateBeanUtils.calculatorBeanList1(new BigDecimal(proposalAmount), new BigDecimal(proposalRatio), Integer.parseInt(proposalLimit), DateUtil.parser(ratioDate, DateUtil.DEFAULT_DATE_FORMAT),loanEndDate, date);
				}else if (LoanRepayPlanEnum.MONTHLY_REPAYMENT.value.equals(repaymentMode)){
					//按月换息 到期还本
					planBeans = ZSCalculateBeanUtils.calculatorBeanList3(new BigDecimal(proposalAmount), new BigDecimal(proposalRatio), Integer.parseInt(proposalLimit), DateUtil.parser(ratioDate, DateUtil.DEFAULT_DATE_FORMAT),loanEndDate, date);
				} else if (LoanRepayPlanEnum.LUMP_SUM_REPAYMENT.value.equals(repaymentMode)){
					//一次性还本付息
					planBeans = ZSCalculateBeanUtils.calculatorBeanList4(new BigDecimal(proposalAmount), new BigDecimal(proposalRatio), Integer.parseInt(proposalLimit), DateUtil.parser(ratioDate, DateUtil.DEFAULT_DATE_FORMAT),loanEndDate, date);
				}else {
					//其他的也返回00000
					planBeans = ZSCalculateBeanUtils.calculatorBeanList0(new BigDecimal(proposalAmount), new BigDecimal(proposalRatio), Integer.parseInt(proposalLimit), DateUtil.parser(ratioDate, DateUtil.DEFAULT_DATE_FORMAT),loanEndDate, date);
				}

				//对象转换
				if(CollectionUtils.isNotEmpty(planBeans)){

					LoanRepayPlanInfoExtend planInfoExtend ;

					for (ZSCalculatorBean plan :planBeans ){
						planInfoExtend = new LoanRepayPlanInfoExtend();

						planInfoExtend.setDefaultValue(loginUserId, Integer.parseInt(loanId));
//					planInfoExtend.setRepayment(plan.getNumMonthPay());
						planInfoExtend.setLoanRepayPlanDate(plan.getRepayDate());
						planInfoExtend.setRatioDate(plan.getRepayDate());
						planInfoExtend.setLoanPrincipalAmount(plan.getNumMonthCapital());
						planInfoExtend.setLoanAccrualAmount(plan.getNumMonthInterest());
						planInfoExtend.setLoanAccrualDays(plan.getNumDays());
						loanRepayPlanInfos.add(planInfoExtend);

					}
				}
			}else{
				//时间时间 补充计划时间
				loanRepayPlanInfos = ZSCalculateBeanUtils.calculatorBeanListDate(loanRepayPlanInfos,new BigDecimal(proposalRatio), DateUtil.parser(ratioDate, DateUtil.DEFAULT_DATE_FORMAT),loanEndDate, date, repaymentMode,Integer.parseInt(proposalLimit));
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("参数错误，请检查参数");
			return loanRepayPlanInfos;
		}
//		用第一个计划金额临时保存总金额（灵活还款使用），此处不要使用getRepayment，因为get方法已经重写
		if (loanRepayPlanInfos.get(0).repayment == null)
			loanRepayPlanInfos.get(0).setRepayment(new BigDecimal(proposalAmount));
		return loanRepayPlanInfos;
	}

	@Override
	public List<LoanRepayPlanInfoExtend> getRepayPlanInfoListForL(String loanId, String repaymentMode, String proposalAmount, String proposalLimit, String proposalRatio, String ratioDate, Integer date, Integer loginUserId) {
		//TODO 版本定制
		if (!"1".equals(repaymentMode) && !"2".equals(repaymentMode)  && !"3".equals(repaymentMode))
			repaymentMode = LoanRepayPlanEnum.FLEXIBLE_REPAYMENT.value;
		String loanBackDate = changeDate(ratioDate, date);
		List<LoanRepayPlanInfoExtend> loanRepayPlanInfos = new ArrayList<LoanRepayPlanInfoExtend>();
		try {
			if ("0".equals(proposalAmount)) return loanRepayPlanInfos;

			if (LoanRepayPlanEnum.EQUAL_PRINCIPAL_ACCRUAL.value.equals(repaymentMode)) {
				setLoanRepayPlanInfos(proposalRatio,repaymentMode, proposalAmount, proposalLimit, loginUserId, loanId, loanRepayPlanInfos, true, ratioDate, loanBackDate);
			} else {
				loanRepayPlanInfos = queryLoanRepayPlanInfoByLoanId(Integer.parseInt(loanId), repaymentMode, LoanProcessTypeEnum.APPROVAL.type);
				if (CollectionUtils.isEmpty(loanRepayPlanInfos)) {
					if (LoanRepayPlanEnum.EQUAL_PRINCIPAL.value.equals(repaymentMode))
						setLoanRepayPlanInfos(proposalRatio,repaymentMode, proposalAmount, proposalLimit, loginUserId, loanId, loanRepayPlanInfos, true, ratioDate, loanBackDate);
					else
						setNewPlanByLimit(proposalLimit, proposalAmount,loanBackDate, loanRepayPlanInfos);
				} else {
					if (LoanRepayPlanEnum.EQUAL_PRINCIPAL.value.equals(repaymentMode)) {
						List<LoanCalculatorBean> loanCalculatorBeans = CalculateUtils.calculatorBeanList2(Double.parseDouble(proposalAmount), getMonthRatio(proposalRatio), Integer.parseInt(proposalLimit));
						List<LoanRepayPlanInfoExtend> repayPlanInfoExtends = new ArrayList<LoanRepayPlanInfoExtend>();
						for (int i = 0; i < loanRepayPlanInfos.size(); i++) {
							LoanRepayPlanInfoExtend loanRepayPlanInfo = loanRepayPlanInfos.get(i);
							LoanCalculatorBean bean = loanCalculatorBeans.get(i);
							loanRepayPlanInfo.setLoanAccrualAmount(new BigDecimal(bean.getNumMonthInterest()).setScale(2,BigDecimal.ROUND_HALF_DOWN));
							repayPlanInfoExtends.add(loanRepayPlanInfo);
						}
						loanRepayPlanInfos = repayPlanInfoExtends;
					}
					setFlexible(repaymentMode, loanRepayPlanInfos);
				}
				setLoanRepayPlanInfo(ratioDate, loanBackDate, loanRepayPlanInfos, date, true);
			}
		} catch (Exception e) {
			System.out.println("参数错误，请检查参数");
			return loanRepayPlanInfos;
		}
		//用第一个计划金额临时保存总金额（灵活还款使用），此处不要使用getRepayment，因为get方法已经重写
		if (loanRepayPlanInfos.get(0).repayment == null)
			loanRepayPlanInfos.get(0).setRepayment(new BigDecimal(proposalAmount));
		return loanRepayPlanInfos;
	}


	private String changeDate(String loanCreditDate, Integer date) {
		Date date1 = DateUtil.parser(loanCreditDate, DateUtil.DEFAULT_DATE_FORMAT);
		Calendar c = Calendar.getInstance();
		c.setTime(date1);
		int day = c.get(Calendar.DAY_OF_MONTH);
//				1、	还款日<=起息日：首个还款日为起息日下月；
//				列：还款日为10号，起息日为2017-10-15，则首个还款日为2017-11-10
		if (date <= day) {
			c.add(Calendar.MONTH, 1);
		} else {
//				2、	还款日>起息日
			SysBasicConfig sysConfig = systemModule.getSysBasicConfig("qxsz");
			if (sysConfig!=null && sysConfig.getConfigValue() != null) {
//				a、	还款日-起息日<起息间隔：首个还款日为起息日下月
//				列：还款日为15日，起息日为2017-10-10，起息间隔为15天，则首个还款日为2017-11-15
//				b、	还款日-起息日>起息间隔：首个还款日为起息日本月
//				列：还款日为25日，起息日为2017-10-5，起息间隔为15天，则首个还款日为2017-10-25
				if ((date - day)< sysConfig.getConfigValue().intValue()) {
					c.add(Calendar.MONTH, 1);
				}
			}
		}
		c.set(Calendar.DAY_OF_MONTH, date);
    	return DateUtil.format(c.getTime(), DateUtil.DEFAULT_DATE_FORMAT);
	}


	private void setLoanRepayPlanInfo(String ratioDate, String loanBackDate, List<LoanRepayPlanInfoExtend> loanRepayPlanInfos, Integer date, boolean isLoan){
		if (isLoan) {
			Calendar calendar = Calendar.getInstance();
			Calendar r = Calendar.getInstance();
			r.setTime(DateUtil.parser(ratioDate, DateUtil.DEFAULT_DATE_FORMAT));
			calendar.setTime(DateUtil.parser(loanBackDate, DateUtil.DEFAULT_DATE_FORMAT));
			calendar.set(Calendar.DAY_OF_MONTH, date);
			int i = 0;
			for (LoanRepayPlanInfoExtend loanRepayPlanInfo :	loanRepayPlanInfos ) {
				if (i == 0) {
					i++;
					loanRepayPlanInfo.setRatioDate(r.getTime());
				} else {
					loanRepayPlanInfo.setRatioDate(calendar.getTime());
					calendar.add(Calendar.MONTH, 1);
				}
				if (loanRepayPlanInfo.getLoanRepayPlanDate() == null) { //如果老数据有还款日期，不做修改
					loanRepayPlanInfo.setLoanRepayPlanDate(calendar.getTime());
				} else {
					calendar.setTime(loanRepayPlanInfo.getLoanRepayPlanDate());
				}
			}
		}
	}

	@Override
	public void deleteRepayPlanInfoByLoanId(Integer loanId, String processType) {
		repayPlanInfoDao.deleteRepayPlanInfoByLoanId(loanId, processType);
	}

	private void setNewPlanByLimit(String proposalLimit, String proposalAmount,String loanBackDate, List<LoanRepayPlanInfoExtend> loanRepayPlanInfos){
		int limit = Integer.parseInt(proposalLimit);
		//
		//调查阶段使用
//		Calendar calendar = Calendar.getInstance();
//		if (loanBackDate != null) {
//			Date date1 = DateUtil.parser(loanBackDate, DateUtil.DEFAULT_DATE_FORMAT);
//			calendar.setTime(date1);
//		}
		for (int i = 0; i < limit; i++) {
			LoanRepayPlanInfoExtend repayPlanInfo = new LoanRepayPlanInfoExtend();
//			repayPlanInfo.setLoanRepayPlanDate(calendar.getTime());
			if (i == limit - 1) {
				repayPlanInfo.setLoanPrincipalAmount(new BigDecimal(proposalAmount).setScale(2,BigDecimal.ROUND_HALF_DOWN));
			} else {
				repayPlanInfo.setLoanPrincipalAmount(new BigDecimal("0"));
			}
			loanRepayPlanInfos.add(repayPlanInfo);
//			calendar.add(Calendar.MONTH, 1);
		}
	}

	@Override
	public void updateRepayPlanInfoById(LoanRepayPlanInfo loanRepayPlanInfo, Integer loginUserId) {
//		loanRepayAmount  loanRepayAccrualAmount   loanRepayDate
		//当前数据库中药修改的还款计划记录
		LoanRepayPlanInfo beforLoanRepayPlan = repayPlanInfoDao.getRepayPlanInfoById(loanRepayPlanInfo.getId());
		LoanApplyInfo applyInfo = applyInfoDao.getApplyInfoById(beforLoanRepayPlan.getLoanId());
		if (beforLoanRepayPlan != null && applyInfo != null) {
			beforLoanRepayPlan.setLoanRepayAmount(loanRepayPlanInfo.getLoanRepayAmount());
			beforLoanRepayPlan.setLoanRepayAccrualAmount(loanRepayPlanInfo.getLoanRepayAccrualAmount());
			beforLoanRepayPlan.setLoanRepayDate(loanRepayPlanInfo.getLoanRepayDate());
			beforLoanRepayPlan.setLoanCollectionState("Collection");
			beforLoanRepayPlan.setLoanCollectionUserId(loginUserId);

			//当前一条计划需要还款的总额
			BigDecimal beforeTotal = OperationUtil.plus(2, beforLoanRepayPlan.getLoanPrincipalAmount(), beforLoanRepayPlan.getLoanAccrualAmount());
			BigDecimal nowTotal = OperationUtil.plus(2, loanRepayPlanInfo.getLoanRepayAmount(), loanRepayPlanInfo.getLoanRepayAccrualAmount());
			if(nowTotal.compareTo(beforeTotal) == 0){
				//还款总额==应还款总额
				beforLoanRepayPlan.setLoanRepayState(1);
			}else{
				beforLoanRepayPlan.setLoanRepayState(0);
			}
			//当前还款是否逾期
			int compareDate = compareDate(beforLoanRepayPlan.getLoanRepayPlanDate(), loanRepayPlanInfo.getLoanRepayDate());
			if (compareDate < 1) {
				beforLoanRepayPlan.setLoanIsOverdue(0);
			}else{
				beforLoanRepayPlan.setLoanIsOverdue(1);
			}
			//催收时间
			beforLoanRepayPlan.setLoanCollectionDate(new Date());
			//更新还款机计划表的余额 修改：2017年12月18日
			beforLoanRepayPlan.setLoanAccrualBalanceAmount(loanRepayPlanInfo.getLoanAccrualBalanceAmount());
			//更新还款计划表
			repayPlanInfoDao.updateRepayPlanInfo(beforLoanRepayPlan);

			//贷款主表数据更新
			//首先计算出已经还款的金额
			List<LoanRepayPlanInfo> loanRepayPlanInfoList = repayPlanInfoDao.getLoanRepayPlanInfoListByLoanId(beforLoanRepayPlan.getLoanId());
			//要还本金
			BigDecimal needMoeny = new BigDecimal(0);
			//要还本金+利息
			BigDecimal needMoeny2 = new BigDecimal(0);
			//已还本金
			BigDecimal overMoeny = new BigDecimal(0);
			//已还本金+利息
			BigDecimal overMoeny2 = new BigDecimal(0);
			for(LoanRepayPlanInfo repayPlanInfo : loanRepayPlanInfoList){
				needMoeny = OperationUtil.plus(2,needMoeny, repayPlanInfo.getLoanPrincipalAmount());
				needMoeny2 = OperationUtil.plus(2,needMoeny2, repayPlanInfo.getLoanPrincipalAmount(),repayPlanInfo.getLoanAccrualAmount());
				overMoeny = OperationUtil.plus(2,overMoeny, repayPlanInfo.getLoanRepayAmount());
				overMoeny2 = OperationUtil.plus(2,overMoeny2, repayPlanInfo.getLoanRepayAmount(),repayPlanInfo.getLoanRepayAccrualAmount());
			}
			applyInfo.setLoanBalanceAmount(OperationUtil.minus(needMoeny, 2, overMoeny));
			//loanRepayPlanInfoList是按照计划还款时间升序查出
			int i = 0;
			for(LoanRepayPlanInfo repayPlanInfo : loanRepayPlanInfoList){
				if (repayPlanInfo.getLoanRepayState() == null || repayPlanInfo.getLoanRepayState() == 0) {
					applyInfo.setLoanRepayAmount(OperationUtil.plus(2, repayPlanInfo.getLoanPrincipalAmount(),
							repayPlanInfo.getLoanAccrualAmount()));
					applyInfo.setLoanRepayDate(repayPlanInfo.getLoanRepayPlanDate());
					break;
				}
				i++;
			}
			//防止还完还停留在还款计划页面继续操作
			applyInfo.setLoanCollectionState("Collection");
			applyInfo.setLoanProcessType(LoanProcessTypeEnum.AFTER_LOAN.type);
			//全部还完i=还款计划的条数
			if (i == loanRepayPlanInfoList.size() && needMoeny2.compareTo(overMoeny2) == 0) {
				applyInfo.setLoanCollectionState("CollectionComplete");
				//计划还款还款完毕,贷款进入下一个阶段
				applyInfo.setLoanProcessType(LoanProcessTypeEnum.CLEARANCE.type);
			}
			//更新主表贷款余额 修改：2017年12月18日
			applyInfo.setLoanBalanceAmount(loanRepayPlanInfo.getLoanAccrualBalanceAmount());
			applyInfoDao.updateApplyInfo(applyInfo);

			if (i == loanRepayPlanInfoList.size() && needMoeny2.compareTo(overMoeny2) == 0) {
				LoanApplyInfo loanApplyInfo = new LoanApplyInfo();
				loanApplyInfo.setLoanRepayDate(null);
				loanApplyInfo.setLoanRepayAmount(null);
				loanApplyInfo.setLoanId(applyInfo.getLoanId());
				applyInfoDao.updateApplyInfoIgnoreNull(loanApplyInfo);
			}
		}
	}
	public int compareDate(Date date1,Date date2) {
		return date1.getTime() < date2.getTime() ? 1 : date1.getTime() == date2.getTime() ? 0 : -1;
	}

	@Override
	public String checkPlans(Integer loanId, Map<String,Object> map) {
		DataTable datatable;
		Object repaymentMode, proposalLimit, money, loanRatioDate = null, loanBackDate = null,loanEndDate = null;
		boolean isLoan = true;
		String processType = LoanProcessTypeEnum.INVESTIGATE.type;
		//map == null 是调查提交审批时校验，map不为空时是放款时校验，放款时校验需要校验日期
		if (map == null) {
			isLoan = false;
			datatable = applyInfoService.getDataTableByLoanId("LBIZ_SURVEY_RESULT", LoanProcessTypeEnum.INVESTIGATE.type, Integer.valueOf(loanId));
			if (datatable == null || datatable.getRows() == null || datatable.getRow(0) == null) return "调查结论不完整";
			DataRow row = datatable.getRow(0);
			repaymentMode = row.get("REPAYMENT_MODE");
			proposalLimit = row.get("PROPOSAL_LIMIT");
			money = row.get("PROPOSAL_AMOUNT");
		} else {
			processType = LoanProcessTypeEnum.LOAN.type;
			List<Map<String, Object>> loanList = (List<Map<String, Object>>) map.get("LBIZ_LOAN_GRANT");
			if (CollectionUtils.isEmpty(loanList)) return "放款信息不完整";
			Map<String, Object> firstMap = loanList.get(0);
			repaymentMode = firstMap.get("field.loanBackMode");
			proposalLimit = firstMap.get("field.loanLimit");
			money = firstMap.get("field.loanAmount");
			loanRatioDate = firstMap.get("field.loanRatioDate");
			loanBackDate = firstMap.get("field.loanBackDate");
			loanEndDate = firstMap.get("field.loanEndDate");
		}
		if (repaymentMode == null) return "缺少还款方式";
		if (proposalLimit == null) return "缺少还款期限";
		if (isLoan && loanRatioDate == null) return "缺少起息日期";
		if (isLoan && loanBackDate == null) return "缺少还款日";
		if (isLoan && loanEndDate == null) return "缺少放款终止日期";
		String repaymentModeStr = String.valueOf(repaymentMode);
//		if (Integer.parseInt(repaymentModeStr) > 4)
//			repaymentModeStr = LoanRepayPlanEnum.FLEXIBLE_REPAYMENT.value;
//		if (StringUtils.isNotBlank(repaymentModeStr)) {
////			repayPlanInfoDao.deleteRepayPlanInfoByLoanIdAndMode(loanId, processType, repaymentModeStr);
//		}
		if ((!isLoan && LoanRepayPlanEnum.FLEXIBLE_REPAYMENT.value.equals(repaymentModeStr)) || (isLoan && !LoanRepayPlanEnum.EQUAL_PRINCIPAL_ACCRUAL.value.equals(repaymentModeStr))) {
			List<LoanRepayPlanInfoExtend> loanRepayPlanInfos = queryLoanRepayPlanInfoByLoanId(loanId, repaymentModeStr, processType);
			if ((CollectionUtils.isEmpty(loanRepayPlanInfos) || loanRepayPlanInfos.size() == 0)) return "还款计划未保存";
			else if (loanRepayPlanInfos.size() != Integer.parseInt(String.valueOf(proposalLimit)))  return "还款计划条数和期限不匹配";
			if(isLoan && LoanRepayPlanEnum.FLEXIBLE_REPAYMENT.value.equals(repaymentModeStr)){
				String loanEndDateStr = loanRepayPlanInfos.get(loanRepayPlanInfos.size()-1).getLoanRepayPlanDateF();
				if(!loanEndDateStr.equals(loanEndDate)){
					return "最后一期的还款日期必须和放款终止日期相同!";
				}
			}
//			if (((CollectionUtils.isEmpty(loanRepayPlanInfos) || loanRepayPlanInfos.size() == 0) && !LoanRepayPlanEnum.EQUAL_PRINCIPAL.value.equals(repaymentModeStr))
//					|| (loanRepayPlanInfos.size() != 0 && loanRepayPlanInfos.size() != Integer.parseInt(String.valueOf(proposalLimit)))) return "还款计划条数和期限不匹配";
//			if (isLoan) {
				if (LoanRepayPlanEnum.FLEXIBLE_REPAYMENT.value.equals(repaymentModeStr)){
					LoanRepayPlanInfo repayPlanInfo = loanRepayPlanInfos.get(0);
					Date planDate = repayPlanInfo.getLoanRepayPlanDate();
					if (planDate != null) {
						Date ratieDate = DateUtil.parser(String.valueOf(loanRatioDate), DateUtil.DEFAULT_DATE_FORMAT);
						//planDate != null && ratieDate != null 2017年11月15日18:45:56
						// 调查阶段跳过日期校验
						if (planDate != null && ratieDate != null && planDate.getTime() <= ratieDate.getTime()) return "计划还款日期应大于起息日期";
					}

					BigDecimal all = new BigDecimal(String.valueOf(money));
					for (LoanRepayPlanInfo loanRepayPlanInfo : loanRepayPlanInfos){
						all = all.subtract(loanRepayPlanInfo.getLoanPrincipalAmount());
					}
					if (all.doubleValue() != 0) return "还款计划总金额不匹配";
				} else {
//					if (CollectionUtils.isNotEmpty(loanRepayPlanInfos)) {
//						LoanRepayPlanInfo repayPlanInfo = loanRepayPlanInfos.get(0);
//						String date = changeDate(String.valueOf(loanRatioDate), Integer.parseInt(String.valueOf(loanBackDate)));
//						Date planDate = repayPlanInfo.getLoanRepayPlanDate();
//						String planDateStr = DateUtil.format(planDate, DateUtil.DEFAULT_DATE_FORMAT);
//						if (!date.equals(planDateStr)) return "计划还款日期有误";
//
//					}
				}
//			}
		} else {
			deleteRepayPlanInfoByLoanId(loanId, processType);
		}
		return "";
	}

	/**
	 * APP放款阶段还款计划校验
	 * @param repaymentMode
	 * @param proposalLimit
	 * @param money
	 * @param loanRatioDate
	 * @param loanBackDate
	 * @param loanId
	 * @param applyInfo
	 * @return
	 */
	@Override
	public String checkPlansLoan(Object repaymentMode, Object proposalLimit, Object money, Object loanRatioDate, Object loanBackDate,Integer loanId,LoanApplyInfo applyInfo) {
		if (repaymentMode == null) return "缺少还款方式";
		if (proposalLimit == null) return "缺少还款期限";
		if (loanRatioDate == null) return "缺少起息日期";
		if (loanBackDate == null) return "缺少还款日";
		String repaymentModeStr = String.valueOf(repaymentMode);
//		if (!LoanRepayPlanEnum.EQUAL_PRINCIPAL_ACCRUAL.value.equals(repaymentModeStr)) {
			List<LoanRepayPlanInfoExtend> loanRepayPlanInfos = queryLoanRepayPlanInfoByLoanId(loanId, repaymentModeStr, applyInfo.getLoanProcessType());
			if ((CollectionUtils.isEmpty(loanRepayPlanInfos) || loanRepayPlanInfos.size() == 0)) return "还款计划未保存";
			else if (loanRepayPlanInfos.size() != Integer.parseInt(String.valueOf(proposalLimit)))  return "还款计划条数和期限不匹配";

			if (LoanRepayPlanEnum.FLEXIBLE_REPAYMENT.value.equals(repaymentModeStr)){
				LoanRepayPlanInfo repayPlanInfo = loanRepayPlanInfos.get(0);
				Date planDate = repayPlanInfo.getLoanRepayPlanDate();
				if (planDate != null) {
					Date ratieDate = DateUtil.parser(String.valueOf(loanRatioDate), DateUtil.DEFAULT_DATE_FORMAT);
					//planDate != null && ratieDate != null 2017年11月15日18:45:56
					// 调查阶段跳过日期校验
					if (planDate != null && ratieDate != null && planDate.getTime() <= ratieDate.getTime()) return "计划还款日期应大于起息日期";
				}

				BigDecimal all = new BigDecimal(String.valueOf(money));
				for (LoanRepayPlanInfo loanRepayPlanInfo : loanRepayPlanInfos){
					all = all.subtract(loanRepayPlanInfo.getLoanPrincipalAmount());
				}
				if (all.doubleValue() != 0) return "还款计划总金额不匹配";
			} else {
//				if (CollectionUtils.isNotEmpty(loanRepayPlanInfos)) {
//					LoanRepayPlanInfo repayPlanInfo = loanRepayPlanInfos.get(0);
//					String date = changeDate(String.valueOf(loanRatioDate), Integer.parseInt(String.valueOf(loanBackDate)));
//					Date planDate = repayPlanInfo.getLoanRepayPlanDate();
//					String planDateStr = DateUtil.format(planDate, DateUtil.DEFAULT_DATE_FORMAT);
//					if (!date.equals(planDateStr)) return "计划还款日期有误";
//				}
			}
//		} else {
//			deleteRepayPlanInfoByLoanId(loanId, applyInfo.getLoanProcessType());
//		}
		return "";
	}

	/**
	 * @Author: yangdw
	 * @param loanId 贷款id
	 * @param repayDate 还款时间
	 * @param principalAmount 应还本金
	 * @Description:
	 * @Date: 15:36 2017/11/20
	 */
	public List<LoanRepayPlanInfo> getLoanRepayPlanInfoByLoanIdAndRepayDate(Integer loanId,Date repayDate,BigDecimal principalAmount){

		return repayPlanInfoDao.getLoanRepayPlanInfoByLoanIdAndRepayDate(loanId, repayDate, principalAmount);
	}

	/**
	 * @Author: yangdw
	 * @param loanId 贷款id
	 * @Description:根据贷款ID获取还款计划
	 * @Date: 15:36 2017/11/20
	 */
	public List<LoanRepayPlanInfo> getLoanRepayPlanInfoListByLoanId(Integer loanId) {
		return repayPlanInfoDao.getLoanRepayPlanInfoListByLoanId(loanId);
	}

	/**
	 * @Author: yangdw
	 * @param loanId 贷款id
	 * @Description:通过贷款ID查询还款计划,由还款计划更新贷款状态和贷款
	 * ========如有业务需要,南郊可以自行修改======
	 * @Date: 17:07 2017/11/22
	 */
	public void updateLoanStateAndMoenyByRepayPlans(Integer loanId) {
		if (loanId == null) {
			logger.error("贷款ID不能为空");
			return;
		}
		LoanApplyInfo apply = applyInfoDao.getApplyInfoById(loanId);
		if (apply == null) {
			logger.error("贷款不存在,贷款id为:" + loanId);
			return;
		}
		LoanApplyInfo applyInfo = new LoanApplyInfo();
		applyInfo.setLoanId(loanId);
		//贷款主表数据更新
		//首先计算出已经还款的金额
		List<LoanRepayPlanInfo> loanRepayPlanInfoList = repayPlanInfoDao.getLoanRepayPlanInfoListByLoanId(loanId);
		//要还本金
		BigDecimal needMoeny = new BigDecimal(0);
		//要还本金+利息
		BigDecimal needMoeny2 = new BigDecimal(0);
		//已还本金
		BigDecimal overMoeny = new BigDecimal(0);
		//已还本金+利息
		BigDecimal overMoeny2 = new BigDecimal(0);
		for(LoanRepayPlanInfo repayPlanInfo : loanRepayPlanInfoList){
			needMoeny = OperationUtil.plus(2,needMoeny, repayPlanInfo.getLoanPrincipalAmount());
			needMoeny2 = OperationUtil.plus(2,needMoeny2, repayPlanInfo.getLoanPrincipalAmount(),repayPlanInfo.getLoanAccrualAmount());
			overMoeny = OperationUtil.plus(2,overMoeny, repayPlanInfo.getLoanRepayAmount());
			overMoeny2 = OperationUtil.plus(2,overMoeny2, repayPlanInfo.getLoanRepayAmount(),repayPlanInfo.getLoanRepayAccrualAmount());
		}
		//更新贷款主表的贷款余额
		applyInfo.setLoanBalanceAmount(OperationUtil.minus(needMoeny, 2, overMoeny));
		//loanRepayPlanInfoList是按照计划还款时间升序查出
		int i = 0;
		for(LoanRepayPlanInfo repayPlanInfo : loanRepayPlanInfoList){
			if (repayPlanInfo.getLoanRepayState() == null || repayPlanInfo.getLoanRepayState() == 0) {
				//计算出最新一期需要还款的金额和计划还款时间,更新到主表中
				applyInfo.setLoanRepayAmount(OperationUtil.plus(2, repayPlanInfo.getLoanPrincipalAmount(),
						repayPlanInfo.getLoanAccrualAmount()));
				applyInfo.setLoanRepayDate(repayPlanInfo.getLoanRepayPlanDate());
				break;
			}
			i++;
		}
		applyInfo.setLoanCollectionState("Collection");
		applyInfo.setLoanProcessType(LoanProcessTypeEnum.AFTER_LOAN.type);
		//全部还完i=还款计划的条数
		if (i == loanRepayPlanInfoList.size() && needMoeny2.compareTo(overMoeny2) == 0) {
			applyInfo.setLoanCollectionState("CollectionComplete");
			//计划还款还款完毕,贷款进入下一个阶段
			applyInfo.setLoanProcessType(LoanProcessTypeEnum.CLEARANCE.type);
		}
		applyInfoDao.updateApplyInfo(applyInfo);

		//还款计划表中已经还完了,需要更新贷款到下一个阶段,并且移除最后一期的主表催收信息
		if (i == loanRepayPlanInfoList.size() && needMoeny2.compareTo(overMoeny2) == 0) {
			LoanApplyInfo loanApplyInfo = new LoanApplyInfo();
			loanApplyInfo.setLoanRepayDate(null);
			loanApplyInfo.setLoanRepayAmount(null);
			loanApplyInfo.setLoanId(applyInfo.getLoanId());
			applyInfoDao.updateApplyInfoIgnoreNull(loanApplyInfo);
		}
	}

	@Override
	public void copyPlansToApproval(Integer loanId) {
		List<LoanRepayPlanInfoExtend> plans = queryLoanRepayPlanInfoByLoanId(loanId, null, LoanProcessTypeEnum.INVESTIGATE.type);
		List<LoanRepayPlanInfoExtend> newPlans = new ArrayList<LoanRepayPlanInfoExtend>();
		for (LoanRepayPlanInfoExtend plan : plans) {
			plan.setId(null);
			plan.setLoanProcessType(LoanProcessTypeEnum.APPROVAL.type);
			newPlans.add(plan);
		}
		savePlanList(plans);
	}

	@Override
	public void deleteRepayPlanInfoByLoanIdAndMode(String loanId, String loanProcessType, String repaymentMode) {
		repayPlanInfoDao.deleteRepayPlanInfoByLoanIdAndMode(Integer.parseInt(loanId), loanProcessType, repaymentMode);
	}
}
