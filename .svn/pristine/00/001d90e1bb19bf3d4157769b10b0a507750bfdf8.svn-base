package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.enumerate.LoanRepayPlanEnum;
import banger.domain.enumerate.RepayPlanOption;
import banger.domain.enumerate.SocketCodeTypeEnum;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanRepayPlanInfo;
import banger.domain.loan.LoanRepayPlanInfoExtend;
import banger.domain.system.SysBasicConfig;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.util.DateUtil;
import banger.framework.util.IdCardUtil;
import banger.framework.util.OperationUtil;
import banger.framework.web.dojo.JsonTools;
import banger.moduleIntf.IBasicConfigProvider;
import banger.service.intf.IApplyInfoService;
import banger.service.intf.ILoanApplyService;
import banger.service.intf.IRepayPlanInfoService;
import banger.socket.SocketDemo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/loanRepayPlanInfo")
public class LoanRepayPlanInfoController extends BaseController{

    @Autowired
    private IRepayPlanInfoService repayPlanInfoService;
    @Autowired
    private ILoanApplyService loanApplyService;
    @Autowired
    private IBasicConfigProvider basicConfigProvider;
    @Autowired
    private IApplyInfoService applyInfoService;
    @Autowired
    private SocketDemo socketDemo;


    private String basePath = "/loan/vm/repayPlan/";

    private final String LOAN_REPAY_INFO = "isAutoRepay,autoRepay,collection,userName,loanId,loanRepayPlanDate,loanPrincipalAmountF,loanAccrualAmountF" +
			",loanRepayAmountF,loanRepayAccrualAmountF,loanRepayState,loanRepayDate,loanIsOverdue,createUser";
    /**
     * 跳转贷后催收
     */
    @RequestMapping("getLoanRepayPlanInfoPage")
    public ModelAndView getLoanRepayPlanInfoPage(@RequestParam("loanId") Integer loanId){
        ModelAndView mv = new ModelAndView(basePath + "loanRepayPlanInfo");
        LoanApplyInfo applyInfoById = loanApplyService.getApplyInfoById(loanId);
        String repaymentMode = applyInfoById.getRepaymentMode();
        mv.addObject("module",getParameter("module"));
        mv.addObject("repaymentMode",repaymentMode);
        mv.addObject("loanId",loanId);
        mv.addObject("loanBalanceAmount",applyInfoById.getLoanBalanceAmount());
        mv.addObject("loanAccountAmount",applyInfoById.getLoanAccountAmount());
        mv.addObject("isManager",isCustomerManager());
        return mv;
    }

    /**
     * 跳转还款记录
     */
    @RequestMapping("getRepaymentListPage")
    public ModelAndView getRepaymentListPage(@RequestParam("loanId") Integer loanId){
        ModelAndView mv = new ModelAndView(basePath + "loanRepaymentList");
        mv.addObject("loanId",loanId);
        return mv;
    }


    @RequestMapping("/getRepayPlanTabs")
    public String getRepayPlanTabs(@RequestParam(value = "module", defaultValue = "") String module,
                                     @RequestParam(value = "loanId", defaultValue = "") String loanId,
                                     @RequestParam(value = "precType", defaultValue = "") String precType,
                                     @RequestParam(value = "loanTypeId", defaultValue = "") String loanTypeId,
                                     @RequestParam(value = "showApply", defaultValue = "") String showApply){

        precType = firstUpperCase(precType);
        if ((StringUtils.isNotBlank(showApply) && "1".equals(showApply)) || !precType.equals(firstUpperCase(module))) {
            showApply = "1";
        }

        setAttribute("loanId", loanId);
        setAttribute("precType", precType);
        setAttribute("module", module);
        setAttribute("loanTypeId", loanTypeId);
        setAttribute("showApply", showApply);
        return  basePath + "loanRepayPlanTabs";
    }

    /**
     * 将首字母变为大写
     * @param module
     * @return
     */
    private String firstUpperCase(String module){
        if (StringUtils.isNotBlank(module)) {
            return module.substring(0,1).toUpperCase()+module.substring(1);
        } else {
            return "";
        }
    }


    /**
     * 查询列表
     * @param loanId
     */
    @RequestMapping("/queryLoanRepayPlanInfo")
    @ResponseBody
    public void queryLoanRepayPlanInfo(@RequestParam("loanId") Integer loanId){
//        Boolean isSj= getLoginInfo().getRoleIds().contains("7");
//		Boolean manager=isCustomerManager();
		//这里需要根据功能开关处理,暂时定位true
//		Boolean isAutoRepay = true;
        SysBasicConfig hkfs = basicConfigProvider.getSysBasicConfigByKey("hkfs");
        //1:手动还款,2自动还款
        Boolean isAutoRepay = "1".equals(hkfs.getConfigStatus()) ? true : false;
        List<LoanRepayPlanInfoExtend> list = repayPlanInfoService.queryLoanRepayPlanInfoByLoanId(loanId);

        //显示催收贷款(当前日期+催收设置天数>=应还款时间)
        SysBasicConfig cssz = basicConfigProvider.getSysBasicConfigByKey("cfsz");
//        //当前时间的零点
//        Date start = getNeedTime(0,0,0,0);
//        Date collectionDate = DateUtil.addDay(start,cssz.getConfigValue());
        Date collectionDate = DateUtil.addDay(DateUtil.getCurrentDate(),cssz.getConfigValue());
        for(LoanRepayPlanInfoExtend repayInfo : list){
            repayInfo.setAutoRepay(isAutoRepay);
            repayInfo.setLoanPrincipalAmountF(IdCardUtil.getFormatMoney(repayInfo.getLoanPrincipalAmount()));
            repayInfo.setLoanAccrualAmountF(IdCardUtil.getFormatMoney(repayInfo.getLoanAccrualAmount()));
            repayInfo.setLoanRepayAmountF(IdCardUtil.getFormatMoney(repayInfo.getLoanRepayAmount()));
            repayInfo.setLoanRepayAccrualAmountF(IdCardUtil.getFormatMoney(repayInfo.getLoanRepayAccrualAmount()));
            int compareDate = compareDate(collectionDate, repayInfo.getLoanRepayPlanDate());
            if (compareDate < 1) {
                repayInfo.setCollection(true);
            }else{
                repayInfo.setCollection(false);
            }
            //应还本金和利息==0,不能编辑
            if (OperationUtil.plus(2, repayInfo.getLoanPrincipalAmount(), repayInfo.getLoanAccrualAmount()).compareTo(new BigDecimal(0)) == 0) {
                repayInfo.setCollection(false);
            }
            if(!isCustomerManager()){
                repayInfo.setCollection(false);
            }
            //还款状态显示
            if (repayInfo.getLoanRepayState() != null && repayInfo.getLoanRepayState().intValue() == 0) {
                if (repayInfo.getLoanRepayPlanDate().compareTo(collectionDate) == 1) {
                    repayInfo.setLoanRepayState(null);
                }
            }
        }

//        LoanApplyInfo applyInfo = loanApplyService.getApplyInfoById(loanId);
        JSONObject jsonObject = JsonUtil.toJson(list, "id", LOAN_REPAY_INFO);
//        jsonObject.put("totalMoney", IdCardUtil.getFormatMoney(applyInfo.getLoanBalanceAmount()));
        renderText(jsonObject.toString());
    }

 /**
     * 查询列表
     * @param loanId
     */
    @RequestMapping("/queryLoanRepaymentList")
    @ResponseBody
    public void queryLoanRepaymentList(@RequestParam("loanId") Integer loanId){

        try {
            //根据接口查询 排序 返回
            final LoanApplyInfo applyInfo = loanApplyService.getApplyInfoById(loanId);
            if(null!=applyInfo&&StringUtils.isNotBlank(applyInfo.getLoanAccountNo())){
                HashMap<String,String> map = new HashMap<String, String>(){{put("贷款账号",applyInfo.getLoanAccountNo());}};
                String returnStr = socketDemo.queryCusInfo1(loanId,map, SocketCodeTypeEnum.QRY10401);
                if(StringUtils.isNotBlank(returnStr)&& JSONUtils.mayBeJSON(returnStr)){
                    JSONObject jsonObject = JSONObject.fromObject(returnStr);
                    JSONObject jsonBody = jsonObject.getJSONObject("Body");
                    if(jsonBody.containsKey("code")&&"0000".equals(jsonBody.getString("code"))&&jsonBody.getInt("tot_num")>0&&jsonBody.containsKey("details")){
                        JSONArray jsonDetail = jsonBody.getJSONArray("details");
                        renderText(JsonUtil.jsonArrayToJson(jsonDetail, jsonBody.getInt("tot_num")).toString());
                        return;
                    }
                }
            }
        }catch (Exception e){
            log.error(e);
        }

        renderText("");
    }


    /**
     * 跳转新增页面
     * @return
     */
    @RequestMapping("getAddPage")
    public ModelAndView getAddPage(@RequestParam("loanId") Integer loanId){
        ModelAndView mv = new ModelAndView("/loan/vm/repayPlan/loanRepayPlanInfoSave");
        mv.addObject("loanId", loanId);
        return mv;
    }

    /**
     * 新增校验日期唯一性
     */
    @RequestMapping("checkLoanRepayPlanDateRuleAdd")
    public void checkLoanRepayPlanDateRule(@RequestParam String date,@RequestParam("loanId") Integer loanId) throws ParseException {
        Map<String,Object> condition = new HashMap<String, Object>();
        condition.put("loanRepayPlanDate",date);
        condition.put("loanId",loanId);
        List<LoanRepayPlanInfo> loanRepayPlanInfos = repayPlanInfoService.queryListByLoanRepayPlanDate(condition);
        if (loanRepayPlanInfos != null && loanRepayPlanInfos.size() > 0) {
            renderText(false, "", null);
            return;
        }
        renderText(true, "", null);

    }

    /**
     * 新增
     * @throws ParseException
     */
    @RequestMapping("saveLoanRepayPlanInfo")
    public void saveLoanRepayPlanInfo() throws ParseException {
        Integer loginUserId = getLoginInfo().getUserId();
        LoanRepayPlanInfo loanRepayPlanInfo = new LoanRepayPlanInfo();
        loanRepayPlanInfo.setLoanAccrualAmount(BigDecimal.valueOf(Double.valueOf(getParameter("loanAccrualAmount"))));
        loanRepayPlanInfo.setLoanPrincipalAmount(BigDecimal.valueOf(Double.valueOf(getParameter("loanPrincipalAmount"))));
        loanRepayPlanInfo.setLoanRepayAmount(loanRepayPlanInfo.getLoanAccrualAmount().add(loanRepayPlanInfo.getLoanPrincipalAmount()));

        loanRepayPlanInfo.setLoanCollectionState(getParameter("loanCollectionState"));
        loanRepayPlanInfo.setLoanId(Integer.valueOf(getParameter("loanId")));
        //判断催收时间，如果比当前时间早，直接附上状态
        Date current = DateUtil.getCurrentDate();
        Integer planValue = -basicConfigProvider.queryRepayPlanValue();
        Date planTime = DateUtil.addDay(DateUtil.parser(getParameter("loanRepayPlanDate"),"yyyy-MM-dd"),planValue);
        if(DateUtil.compareDate(current,planTime) == 1){
            loanRepayPlanInfo.setLoanCollectionState("Collection");
        }
        loanRepayPlanInfo.setLoanRepayPlanDate(DateUtil.parser(getParameter("loanRepayPlanDate"),"yyyy-MM-dd"));

        repayPlanInfoService.insertRepayPlanInfo(loanRepayPlanInfo,getLoginInfo().getUserId());

        //更新主表
        LoanApplyInfo applyInfoById = loanApplyService.getApplyInfoById(loanRepayPlanInfo.getLoanId());
        //如果列表中有未催收的，则显示最早的未催收，
        Map<String,Object> condition = new HashMap<String, Object>();
        condition.put("loanId",loanRepayPlanInfo.getLoanId());
        condition.put("loanCollectionState","Collection");
        List<LoanRepayPlanInfo> unConllection = repayPlanInfoService.queryUnConllection(condition);
        if(unConllection != null && unConllection.size() > 0){
            //查出最早未催收的计划，并更新主表
            //更新还款计划，总金额，状态
            applyInfoById.setLoanCollectionState("Collection");
            applyInfoById.setLoanRepayDate(unConllection.get(0).getLoanRepayPlanDate());
            applyInfoById.setLoanRepayAmount( unConllection.get(0).getLoanRepayAmount());
            loanApplyService.updateApplyInfo(applyInfoById,loginUserId);
        }
        if(unConllection == null || unConllection.size() == 0){
            //查询列表中状态为“”的数据
            condition.put("loanCollectionState","");
            List<LoanRepayPlanInfo> blankList = repayPlanInfoService.queryRepayPlanInfoList(condition);
            if(blankList != null && blankList.size() > 0){
                //如果列表中没有未催收的，且有“”的，则显示最后一条催收完成的
                condition.put("loanCollectionState","CollectionComplete");
                List<LoanRepayPlanInfo> collectionCompleteList = repayPlanInfoService.queryComplete(condition);
                if(collectionCompleteList != null && collectionCompleteList.size() > 0){
                    //更新还款计划，总金额，状态
                    applyInfoById.setLoanCollectionState("CollectionComplete");
                    applyInfoById.setLoanRepayDate(collectionCompleteList.get(0).getLoanRepayPlanDate());
                    applyInfoById.setLoanRepayAmount( collectionCompleteList.get(0).getLoanRepayAmount());
                    loanApplyService.updateApplyInfo(applyInfoById,loginUserId);
                }else{
                    //如果列表中没有未催收的，没有“”的，则显示未空
                    applyInfoById.setLoanCollectionState("");
                    applyInfoById.setLoanRepayDate(null);
                    applyInfoById.setLoanRepayAmount(null);
                    loanApplyService.updateApplyInfoIgnoreNull(applyInfoById,loginUserId);
                }
            }else{
                //如果列表中没有未催收的，没有“”的，则显示未空
                applyInfoById.setLoanCollectionState("");
                applyInfoById.setLoanRepayDate(null);
                applyInfoById.setLoanRepayAmount(null);
                loanApplyService.updateApplyInfoIgnoreNull(applyInfoById,loginUserId);
            }
        }
    }

    /**
     * 跳转编辑页面
     * @return
     */
    @RequestMapping("getUpdatePage")
    public ModelAndView getUpdatePage(@RequestParam("id") Integer id,@RequestParam("loanId") Integer loanId){
        ModelAndView mv =new ModelAndView("/loan/vm/repayPlan/loanRepayPlanInfoUpdate");
        LoanRepayPlanInfo repayPlanInfoById = repayPlanInfoService.getRepayPlanInfoById(id);
        mv.addObject("repayPlanInfoById",repayPlanInfoById);
        mv.addObject("nowDate",new Timestamp((new Date()).getTime()));
        LoanApplyInfo applyInfoById = loanApplyService.getApplyInfoById(loanId);
        String repaymentMode = applyInfoById.getRepaymentMode();
        if("1".equals(repaymentMode) || "2".equals(repaymentMode)){
            //是否为自定义还款，0为是，1为否
            mv.addObject("repayFlag",1);
        }else{
            mv.addObject("repayFlag",0);
        }
        return mv;
    }

    /**
     * 保存更新
     */
    @RequestMapping("updateRepayPlanInfo")
    public void updateRepayPlanInfo(){

		try {
			//loanRepayAmount  loanRepayAccrualAmount   loanRepayDate
			Integer loginUserId = getLoginInfo().getUserId();
			Integer repayPlanId = Integer.valueOf(getParameter("repayPlanId"));
			BigDecimal loanRepayAmount = BigDecimal.valueOf(Double.valueOf(getParameter("loanRepayAmount")));
			BigDecimal loanRepayAccrualAmount = BigDecimal.valueOf(Double.valueOf(getParameter("loanRepayAccrualAmount")));
			String loanRepayDateStr = getParameter("loanRepayDate");
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			Date loanRepayDate = sdf.parse(loanRepayDateStr);
			LoanRepayPlanInfo loanRepayPlanInfo = new LoanRepayPlanInfo();
			loanRepayPlanInfo.setId(repayPlanId);
			loanRepayPlanInfo.setLoanRepayAmount(loanRepayAmount);
			loanRepayPlanInfo.setLoanRepayAccrualAmount(loanRepayAccrualAmount);
			loanRepayPlanInfo.setLoanRepayDate(loanRepayDate);
			repayPlanInfoService.updateRepayPlanInfoById(loanRepayPlanInfo, loginUserId);
		} catch (ParseException e) {
			e.printStackTrace();
			log.error("updateRepayPlanInfo时间转换报错");
		}

	}

    /**
     * 根据id删除还款计划
     */
    @RequestMapping("deleteById")
    public void deleteById(@RequestParam("id") Integer id){
        Integer loginUserId = getLoginInfo().getUserId();
        LoanRepayPlanInfo loanRepayPlanInfo = repayPlanInfoService.getRepayPlanInfoById(id);
        repayPlanInfoService.deleteRepayPlanInfoById(id);
        //更新主表
        LoanApplyInfo applyInfoById = loanApplyService.getApplyInfoById(loanRepayPlanInfo.getLoanId());
        //如果列表中有未催收的，则显示最早的未催收，
        Map<String,Object> condition = new HashMap<String, Object>();
        condition.put("loanId", loanRepayPlanInfo.getLoanId());
        condition.put("loanCollectionState","Collection");
        List<LoanRepayPlanInfo> unConllection = repayPlanInfoService.queryUnConllection(condition);
        if(unConllection != null && unConllection.size() > 0){
            //查出最早未催收的计划，并更新主表
            //更新还款计划，总金额，状态
            applyInfoById.setLoanCollectionState("Collection");
            applyInfoById.setLoanRepayDate(unConllection.get(0).getLoanRepayPlanDate());
            applyInfoById.setLoanRepayAmount( unConllection.get(0).getLoanRepayAmount());
        }
        if(unConllection == null || unConllection.size() == 0){
            //查询列表中状态为“”的数据
            condition.put("loanCollectionState","");
            List<LoanRepayPlanInfo> blankList = repayPlanInfoService.queryRepayPlanInfoList(condition);
            if(blankList != null && blankList.size() > 0){
                //如果列表中没有未催收的，且有“”的，则显示最后一条催收完成的
                condition.put("loanCollectionState","CollectionComplete");
                List<LoanRepayPlanInfo> collectionCompleteList = repayPlanInfoService.queryComplete(condition);
                if(collectionCompleteList != null && collectionCompleteList.size() > 0){
                    //更新还款计划，总金额，状态
                    applyInfoById.setLoanCollectionState("CollectionComplete");
                    applyInfoById.setLoanRepayDate(collectionCompleteList.get(0).getLoanRepayPlanDate());
                    applyInfoById.setLoanRepayAmount( collectionCompleteList.get(0).getLoanRepayAmount());
                    loanApplyService.updateApplyInfo(applyInfoById,loginUserId);
                }else{
                    //如果列表中没有未催收的，没有“”的，则显示未空
                    applyInfoById.setLoanCollectionState("");
                    applyInfoById.setLoanRepayDate(null);
                    applyInfoById.setLoanRepayAmount(null);
                    loanApplyService.updateApplyInfoIgnoreNull(applyInfoById,loginUserId);
                }
            }else{
                //如果列表中没有未催收的，没有“”的，则显示未空
                applyInfoById.setLoanCollectionState("");
                applyInfoById.setLoanRepayDate(null);
                applyInfoById.setLoanRepayAmount(null);
                loanApplyService.updateApplyInfoIgnoreNull(applyInfoById,loginUserId);
            }
        }
    }





    /**
     * 得到各个列表页面
     * @return
     */
    @RequestMapping("/getPlanPage")
    public String getPlanPage(@RequestParam(value = "loanId", defaultValue = "") String loanId,
                              @RequestParam(value = "fieldFlag", defaultValue = "") String fieldFlag,//0标记 2查看
                              @RequestParam(value = "module", defaultValue = "") String module,//贷款阶段
                              @RequestParam(value = "repaymentMode", defaultValue = "") String repaymentMode,//还款方式
                              @RequestParam(value = "proposalAmount", defaultValue = "") String proposalAmount,//金额
                              @RequestParam(value = "proposalLimit", defaultValue = "") String proposalLimit,//期限
                              @RequestParam(value = "ratioDate", defaultValue = "") String ratioDate,//起息日
                              @RequestParam(value = "loanEndDate", defaultValue = "") String loanEndDate,//放款终止日期
                              @RequestParam(value = "date", defaultValue = "21") String date,//还款日
                              @RequestParam(value = "proposalRatio", defaultValue = "") String proposalRatio)//利率
    {
        repaymentMode = LoanRepayPlanEnum.getValueByName(repaymentMode);
        proposalAmount = proposalAmount.replace(",","");
        Boolean isRest = false;
        if (StringUtils.isNotBlank(loanId) && StringUtils.isNumeric(loanId)) {
            LoanApplyInfo applyInfo = applyInfoService.getApplyInfoById(Integer.parseInt(loanId));
            if (applyInfo != null)
                setAttribute("processType", applyInfo.getLoanProcessType());
            if ("clear".equals(module)) {
                isRest = true;
                module="investigate";
                repayPlanInfoService.deleteRepayPlanInfoByLoanId(Integer.parseInt(loanId), LoanProcessTypeEnum.INVESTIGATE.type);
            } else if ("clearLoan".equals(module)) {
                isRest = true;
                module="loan";
                repayPlanInfoService.deleteRepayPlanInfoByLoanId(Integer.parseInt(loanId), LoanProcessTypeEnum.LOAN.type);
            } else if ("clearApproval".equals(module)) {
                isRest = true;
                DataTable applyInfoData = applyInfoService.getApprovalDataTableByLoanId(Integer.valueOf(loanId));
                DataRow row = applyInfoData.getRow(0);
                if (StringUtils.isBlank(repaymentMode))
                    repaymentMode = row.get("REPAYMENT_MODE").toString();
                if (StringUtils.isBlank(proposalAmount))
                    proposalAmount =  row.get("RESULT_AMOUNT").toString();
                if (StringUtils.isBlank(proposalLimit))
                    proposalLimit =  row.get("RESULT_LIMIT").toString();
                if (StringUtils.isBlank(proposalRatio))
                    proposalRatio = row.get("RESULT_RATIO").toString();

                module="approval";
                repayPlanInfoService.deleteRepayPlanInfoByLoanId(Integer.parseInt(loanId), LoanProcessTypeEnum.APPROVAL.type);
            }
            Integer loginUserId = getLoginInfo().getUserId();
            List<LoanRepayPlanInfoExtend> loanRepayPlanInfos;
            if ("investigate".equals(module))
                loanRepayPlanInfos = repayPlanInfoService.getRepayPlanInfoListForLoan_ZS(loanId, repaymentMode, proposalAmount, proposalLimit, proposalRatio, ratioDate, loanEndDate, Integer.parseInt(date), loginUserId, LoanProcessTypeEnum.INVESTIGATE.type,isRest);
            else if ("approval".equals(module)){
                DataTable applyInfoData = applyInfoService.getApprovalDataTableByLoanId(Integer.valueOf(loanId));
                DataRow row = applyInfoData.getRow(0);
                if (StringUtils.isBlank(repaymentMode))
                    repaymentMode = row.get("REPAYMENT_MODE").toString();
                if (StringUtils.isBlank(proposalAmount))
                    proposalAmount =  row.get("RESULT_AMOUNT").toString();
                if (StringUtils.isBlank(proposalLimit))
                    proposalLimit =  row.get("RESULT_LIMIT").toString();
                if (StringUtils.isBlank(proposalRatio))
                    proposalRatio = row.get("RESULT_RATIO").toString();
                loanRepayPlanInfos = repayPlanInfoService.getRepayPlanInfoListForLoan_ZS(loanId, repaymentMode, proposalAmount, proposalLimit, proposalRatio, ratioDate, loanEndDate, Integer.parseInt(date), loginUserId, LoanProcessTypeEnum.APPROVAL.type,isRest);
            }else {
                loanRepayPlanInfos = repayPlanInfoService.getRepayPlanInfoListForLoan_ZS(loanId, repaymentMode, proposalAmount, proposalLimit, proposalRatio, ratioDate, loanEndDate, Integer.parseInt(date), loginUserId,LoanProcessTypeEnum.LOAN.type,isRest);
            }
            setAttribute("ratioDate", ratioDate);
            setAttribute("plans", loanRepayPlanInfos);
            //用第一个计划金额临时保存总金额（灵活还款使用），此处不要使用getRepayment，因为get方法已经重写
            //TODO 版本定制
//            if (!"1".equals(repaymentMode) && !"2".equals(repaymentMode)  && !"3".equals(repaymentMode))
//                repaymentMode = LoanRepayPlanEnum.FLEXIBLE_REPAYMENT.value;
//            if (CollectionUtils.isNotEmpty(loanRepayPlanInfos))
//                proposalAmount = loanRepayPlanInfos.get(0).repayment.toString();
            setAttribute("proposalAmount", proposalAmount);
        }

//        setAttribute("proposalAmount", proposalAmount);
        setAttribute("repaymentMode", repaymentMode);
        setAttribute("loanId", loanId);
        setAttribute("fieldFlag", fieldFlag);
        setAttribute("module", module);
        return basePath + "planPage";
    }


     /**
     * 保存贷款申请信息
     * @param json 自定义表单信息
     * @return
     */
    @RequestMapping(value = "/savePlans", method = RequestMethod.POST)
    @ResponseBody
    public void savePlans(@RequestParam(value = "loanId", defaultValue = "") String loanId,
                          @RequestParam(value = "module", defaultValue = "") String module,
                          @RequestParam(value = "json", defaultValue = "") String json,
                          @RequestParam(value = "money", defaultValue = "0") BigDecimal money){
        try {
            if (StringUtils.isNotBlank(json) && StringUtils.isNotBlank(loanId) && StringUtils.isNumeric(loanId)) {
                Integer loginUserId = getLoginInfo().getUserId();
                BigDecimal amount = new BigDecimal(0);
                List<Map<String, Object>> maps = JsonTools.parseJSON2List(json);
                if (CollectionUtils.isNotEmpty(maps)) {
                    List<LoanRepayPlanInfoExtend> loanRepayPlanInfos = new ArrayList<LoanRepayPlanInfoExtend>();
                    for (Map<String, Object> map : maps) {
                        LoanRepayPlanInfoExtend repayPlanInfo = new LoanRepayPlanInfoExtend();
                        String loanPrincipalAmount = (String) map.get("loanPrincipalAmount");
                        String repaymentMode = (String) map.get("repaymentMode");
                        String loanRepayPlanDate = (String) map.get("loanRepayPlanDate");
                        String loanAccrualAmount = (String) map.get("loanAccrualAmount");
                        String loanAccrualDays = (String) map.get("loanAccrualDays");
//                        String id = (String) map.get("id");
//                        if (StringUtils.isNotBlank(id))
//                            repayPlanInfo.setId(Integer.parseInt(id));
                        if (StringUtils.isNotBlank(loanRepayPlanDate))
                            repayPlanInfo.setLoanRepayPlanDate(DateUtil.parser(loanRepayPlanDate, DateUtil.DEFAULT_DATE_FORMAT));
                        if (StringUtils.isNotBlank(repaymentMode))
                            repayPlanInfo.setRepaymentMode(repaymentMode);
                        repayPlanInfo.setLoanPrincipalAmount(new BigDecimal(loanPrincipalAmount));
                        repayPlanInfo.setLoanAccrualAmount(new BigDecimal(loanAccrualAmount));
                        repayPlanInfo.setLoanAccrualDays(Integer.parseInt(loanAccrualDays));
                        //贷款上期剩余应还总金额
//                        repayPlanInfo.setLoanRepayAccrualAmount(money.subtract(amount));

                        repayPlanInfo.setDefaultValue(loginUserId, Integer.parseInt(loanId));
                        if ("investigate".equals(module))
                            repayPlanInfo.setLoanProcessType(LoanProcessTypeEnum.INVESTIGATE.type);
                        else if ("loan".equals(module))
                            repayPlanInfo.setLoanProcessType(LoanProcessTypeEnum.LOAN.type);
                        else
                            repayPlanInfo.setLoanProcessType(LoanProcessTypeEnum.APPROVAL.type);

                        loanRepayPlanInfos.add(repayPlanInfo);

                        amount = amount.add(new BigDecimal(loanPrincipalAmount));
                    }

                    //保存之前先删除 清空这个环节的还款计划
                    if ("investigate".equals(module))
                        repayPlanInfoService.deleteRepayPlanInfoByLoanId(Integer.parseInt(loanId), LoanProcessTypeEnum.INVESTIGATE.type);
                    else if ("loan".equals(module))
                        repayPlanInfoService.deleteRepayPlanInfoByLoanId(Integer.parseInt(loanId), LoanProcessTypeEnum.LOAN.type);
                    else
                        repayPlanInfoService.deleteRepayPlanInfoByLoanId(Integer.parseInt(loanId), LoanProcessTypeEnum.APPROVAL.type);

                    repayPlanInfoService.savePlanList(loanRepayPlanInfos);
                    renderText(true, "保存成功", "");
                } else {
                    renderText(false,"参数错误","json");
                }

            } else {
                renderText(false,"参数错误","json");
            }
        } catch (Exception e) {
            log.error("保存还款计划异常|"+json,e);
            renderText(false,"保存失败",String.valueOf(""));
        }

    }
    @RequestMapping(value = "/syncRepaymentPlan", method = RequestMethod.POST)
    @ResponseBody
    public void syncRepaymentPlan(@RequestParam(value = "loanId", defaultValue = "") String loanId){
        String returnStr = socketDemo.syncRepaymentPlan(Integer.parseInt(loanId), RepayPlanOption.ADD);
        JSONObject jsonObject = JSONObject.fromObject(returnStr);
        if(jsonObject.getBoolean("success")){
            renderText(true,"同步成功",String.valueOf(""));
        }else{
            renderText(false,jsonObject.getString("msg"),String.valueOf(""));
        }
    }

    private Date getNeedTime(int hour,int minute,int second,int addDay,int ...args){
        Calendar calendar = Calendar.getInstance();
        if(addDay != 0){
            calendar.add(Calendar.DATE,addDay);
        }
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,second);
        if(args.length==1){
            calendar.set(Calendar.MILLISECOND,args[0]);
        }
        return calendar.getTime();
    }

    public int compareDate(Date date1,Date date2) {
        return date1.getTime() < date2.getTime() ? 1 : date1.getTime() == date2.getTime() ? 0 : -1;
    }
}
