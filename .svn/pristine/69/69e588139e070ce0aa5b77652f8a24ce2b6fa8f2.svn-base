package banger.service.impl;

import banger.dao.intf.*;
import banger.domain.enumerate.*;
import banger.domain.loan.*;
import banger.domain.permission.PmsUser;
import banger.framework.util.RandomUtil;
import banger.framework.util.StringUtil;
import banger.framework.util.TypeUtil;
import banger.moduleIntf.ICustomerBlackProvider;
import banger.moduleIntf.ILoanAuditProvider;
import banger.moduleIntf.IPermissionService;
import banger.moduleIntf.ITeamGroupProvider;
import banger.service.intf.ICurrentAuditStatusService;
import banger.service.intf.ILoanExamineService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 贷款审核服务类
 * Created by zhusw on 2017/6/19.
 */
@Service
public class LoanExamineService implements ILoanExamineService,ILoanAuditProvider {

    @Autowired
    private IApplyInfoDao applyInfoDao;

    @Autowired
    private IApproveInfoDefDao approveInfoDefDao;

    @Autowired
    private IApproveConditionDao approveConditionDao;

    @Autowired
    private IApproveConditionParamsDao approveConditionParamsDao;

    @Autowired
    private IApproveProcessDao approveProcessDao;

    @Autowired
    private IApproveProcessReviewDao approveProcessReviewDao;

    @Resource
    private ITeamGroupProvider teamGroupProvider;

    @Resource
    private ICustomerBlackProvider customerBlackProvider;

    @Autowired
    private ICurrentAuditStatusService currentAuditStatusService;

    @Resource
    private IPermissionService permissionService;

    @Override
    public LoanExamine getLoanExamine(Integer loanId) {
        return getLoanExamine(loanId,false,new Integer[0]);
    }

    /**
     * 得到调查审核环节和人员信息
     * @param loanId 贷款Id
     * @return
     */
    public LoanExamine getNextLoanExamine(Integer loanId){
        return getNextLoanExamine(loanId, false);
    }

    /**
     * 得到调查审核环节和人员信息
     * @param loanId 贷款Id
     * @param filterSameUsers 是否过滤重复的人
     * @return
     */
    public LoanExamine getNextLoanExamine(Integer loanId, boolean filterSameUsers){
        Integer[] filterUserIds = new Integer[0];
        if (filterSameUsers)
            filterUserIds = getFilterUserIds(loanId);
        return getLoanExamine(loanId,true,filterUserIds);
    }

    private Integer[] getFilterUserIds(Integer loanId) {
        Integer[] filterUserIds = new Integer[0];
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("loanId", loanId);
        paramMap.put("isValid",1);
        List<LoanCurrentAuditStatus> loanCurrentAuditStatuses = currentAuditStatusService.queryCurrentAuditStatusList(paramMap);
        if (CollectionUtils.isNotEmpty(loanCurrentAuditStatuses)) {
            filterUserIds = new Integer[loanCurrentAuditStatuses.size()];
            for (int i = 0; i < loanCurrentAuditStatuses.size(); i++) {
                filterUserIds[i] = loanCurrentAuditStatuses.get(i).getAuditUserId();
            }
        }
        return filterUserIds;
    }


    /**
     *
     * @param loanId
     * @param flag  审核提交 true 调查提交 false
     * @param filterUserIds 需要过滤的用户Id集合
     * @return
     */
    private LoanExamine getLoanExamine(Integer loanId,boolean flag,Integer[] filterUserIds) {
        LoanExamine loanExamine = new LoanExamine();
        loanExamine.setLoanId(loanId);
        LoanApplyInfo applyInfo = applyInfoDao.getApplyInfoById(loanId);
        if(applyInfo!=null){
            boolean isBlack = customerBlackProvider.isExistCustomerBlack(applyInfo.getLoanName(),applyInfo.getLoanIdentifyType(),applyInfo.getLoanIdentifyNum());
            if(!isBlack) {
                BigDecimal loanProposeAmount = applyInfo.getLoanProposeAmount();
                if(loanProposeAmount!=null) {
                    loanExamine.setProposalAmount(loanProposeAmount);
                    String processType = applyInfo.getLoanProcessType();
                    LoanProcessTypeEnum processTypeEnum = LoanProcessTypeEnum.valueOfType(processType);
                    Integer userId = applyInfo.getLoanBelongId();
                    if (processTypeEnum != null) {
                        boolean prcessFlag = flag ? LoanProcessTypeEnum.APPROVAL.equals(processTypeEnum)
                                : LoanProcessTypeEnum.INVESTIGATE.equals(processTypeEnum);
                        if (prcessFlag) {         //判断贷款阶段
                            Integer typeId = applyInfo.getLoanTypeId();
                            WfApproveInfoDef approveInfoDef = approveInfoDefDao.getApproveInfoDefByClassId(typeId);
                            if (approveInfoDef != null) {
                                Integer flowId = approveInfoDef.getFlowId();
                                loanExamine.setFlowId(flowId);
                                if (approveInfoDef.getIsCondition() != null && approveInfoDef.getIsCondition().intValue() > 0) {
                                    loanExamine.setHasCondition(true);
                                } else {
                                    loanExamine.setHasCondition(false);
                                }

                                List<WfApproveProcess> processList;
                                if (loanExamine.isHasCondition()) {
                                    if (applyInfo.getLoanAuditParamId() != null && applyInfo.getLoanAuditParamId().intValue() > 0 && flag) {  //审批提交，用参数查
                                        processList = approveProcessDao.getApproveProcessListByFlowId(flowId, applyInfo.getLoanAuditParamId());
                                    } else {
                                        processList = getApproveProcessList(loanId, processType, flowId, loanExamine);
                                    }
                                } else {
                                    processList = approveProcessDao.getApproveProcessListByFlowId(flowId, 0);
                                }

                                //获取审核人员
                                if (processList != null && processList.size() > 0) {
                                    WfApproveProcess approveProcess;
                                    if (flag) {
                                        approveProcess = getNextApproveProcess(processList, applyInfo.getLoanAuditProcessId());
                                        if (approveProcess == null) loanExamine.setLastProcess(true);
                                    } else {
                                        approveProcess = processList.get(0);
                                    }
                                    if (approveProcess != null) {
                                        loanExamine.setParamId(approveProcess.getParamId());
                                        setExamineUserList(userId, loanExamine, approveProcess,filterUserIds);
                                        initUserLimit(loanExamine);                 //初始化用户
                                        initRandomUser(loanExamine);                //产生随机人员
                                        setExamineCode(loanExamine);                //检查审合人
                                    }
                                } else {
                                    if(loanExamine.getExamineCode()==null || LoanExamineCode.PASS.equals(loanExamine.getExamineCode()))
                                        loanExamine.setExamineCode(LoanExamineCode.LOAN_PROCESS_UNDEFINED);         //流程环节未定义
                                }
                            } else {
                                loanExamine.setExamineCode(LoanExamineCode.LOAN_PROCESS_UNDEFINED);         //流程未定义
                            }
                        } else {
                            if (LoanProcessTypeEnum.APPROVAL.equals(processTypeEnum)) {
                                loanExamine.setExamineCode(LoanExamineCode.LOAN_PROCCESS_UNVALID_STATE);         //贷款已在审批中
                            } else {
                                loanExamine.setExamineCode(LoanExamineCode.LOAN_PROCCESS_UNVALID_STATE);         //贷款阶段错误
                            }
                        }
                    } else {
                        loanExamine.setExamineCode(LoanExamineCode.LOAN_DATA_ERROR);         //数据错误
                    }
                }else{
                    loanExamine.setExamineCode(LoanExamineCode.LOAN_PROPOSE_AMOUNT_ERROR);         //数据错误
                }
            }else{
                loanExamine.setExamineCode(LoanExamineCode.LOAN_IS_BLACK_CUSTOMER);        //黑名单客户
            }
        }else{
            loanExamine.setExamineCode(LoanExamineCode.LOAN_DATA_ERROR);        //数据错误
        }
        return loanExamine;
    }

    //获取审批环节
    private List<WfApproveProcess> getApproveProcessList(Integer loanId,String processType,Integer flowId,LoanExamine loanExamine){
        List<WfApproveProcess> processList = null;
        WfApproveCondition approveCondition = approveConditionDao.getApproveConditionByFlowId(flowId);
        if(approveCondition!=null){
            Integer conditionId = approveCondition.getConditionId();
            String fieldType = approveCondition.getFieldType();
            Object fieldValue = this.getConditionValue(loanId,processType,approveCondition);
            loanExamine.setFieldValue(fieldValue);
            if(checkValidParamValue(loanExamine)){          //检查条件参数不能为空
                List<WfApproveConditionParams> paramList = approveConditionParamsDao.getApproveConditionParamsByConditionId(conditionId);
                if(paramList!=null && paramList.size()>0){
                    WfApproveConditionParams condtionParam = getCurentCondtionParam(fieldType,fieldValue,paramList);
                    if(condtionParam!=null){
                        processList = approveProcessDao.getApproveProcessListByFlowId(flowId,condtionParam.getParamsId());
                    }else{
                        loanExamine.setExamineCode(LoanExamineCode.LOAN_CONDITION_PARAM_RANGE);         //匹配不到参数
                    }
                }else{
                    loanExamine.setExamineCode(LoanExamineCode.LOAN_CONDITION_PARAM_UNDEFINED);         //未设置审批流程条件参数
                }
            }
        }else{
            loanExamine.setExamineCode(LoanExamineCode.LOAN_PROCCESS_UNVALID_STATE);         //当前贷款阶段不正确
        }
        return processList;
    }

    //获取审核人
    private void setExamineUserList(Integer userId,LoanExamine loanExamine,WfApproveProcess approveProcess,Integer[] filterUserIds){
        Integer processId = approveProcess.getProcessId();
        List<WfApproveProcessReview> reviewList = approveProcessReviewDao.getApproveProcessReviewListByProcessId(processId);
        loanExamine.setProcessId(processId);
        if(reviewList!=null && reviewList.size()>0){
            for(WfApproveProcessReview approveProcessReview : reviewList) {
                Integer reviewCount = approveProcessReview.getReviewCount();
                String reviewMode = approveProcessReview.getReviewMode();
                String dataPower = approveProcessReview.getReviewDataPower();
                Integer roleId = approveProcessReview.getRevievRoleId();
                Integer isLimitControl = approveProcessReview.getIsLimitControl();
                LoanExamineReview loanExamineReview = new LoanExamineReview();
                loanExamineReview.setId(approveProcessReview.getId());
                loanExamineReview.setRoleId(approveProcessReview.getRevievRoleId());
                String roleName = permissionService.getRoleNameByRoleId(approveProcessReview.getRevievRoleId());
                loanExamineReview.setRoleName(roleName);
                loanExamineReview.setReviewCount(reviewCount);
                loanExamineReview.setType(LoanExamineType.valueOfType(reviewMode));
                loanExamineReview.setRight(LoanExamineRight.valueOfRight(dataPower));
                if(isLimitControl!=null && isLimitControl.intValue()==1){
                    loanExamineReview.setLimit(true);
                }else{
                    loanExamineReview.setLimit(false);
                }

                List<PmsUser> userList;
                if(LoanExamineRight.TEAM.equals(loanExamineReview.getRight())){        //团队内
                    userList = teamGroupProvider.getTeamExamineUserListByUserId(roleId,userId);
                }else{
                    userList = teamGroupProvider.getExamineUserListByRoleId(roleId,userId);
                }
                if(userList!=null && userList.size()>0){
                    if(filterUserIds!=null && filterUserIds.length>0){              //过滤已审批过的人员
                        Set<Integer> userIdSet = new HashSet<Integer>();
                        for(Integer filterUserId : filterUserIds){
                            userIdSet.add(filterUserId);
                        }
                        List<PmsUser> filterUserList = new ArrayList<PmsUser>();
                        for(PmsUser user : userList){
                            if(!userIdSet.contains(user.getUserId()))
                                filterUserList.add(user);
                        }
                        userList = filterUserList;
                    }
                    for(PmsUser user : userList){
                        LoanExamineUser examineUser = new LoanExamineUser();
                        examineUser.setLimitAmount(user.getUserPassAmount());
                        examineUser.setUserName(user.getUserName());
                        examineUser.setUserId(user.getUserId());
                        examineUser.setLimitAmount(user.getUserPassAmount());
                        loanExamineReview.getExamineUserList().add(examineUser);
                    }
                }else{
                    loanExamine.setExamineCode(LoanExamineCode.FIND_NOT_VALID_USER);         //找不到审批人
                }
                loanExamine.getReviews().add(loanExamineReview);
            }
        }
    }

    /**
     * 得到审核状态
     * @return
     */
    private void setExamineCode(LoanExamine loanExamine){
        LoanExamineCode examineCode = LoanExamineCode.PASS;
        if (loanExamine.getProcessId() != null && loanExamine.getProcessId().intValue() > 0) {
            for(LoanExamineReview review : loanExamine.getReviews()) {
                if (review.getReviewCount() != null && review.getReviewCount().intValue() > 0) {
                    Integer userCount = this.getExamineUserCount(review);
                    if (review.isLimit()) {
                        Integer limitCount = this.getLimitExamineUserCount(review);
                        if (limitCount.intValue() == 0) {           //至少有一个符合限额条件的审核人员
                            examineCode = LoanExamineCode.FIND_NOT_VALID_USER;
                        }
                        if (userCount.intValue() < review.getReviewCount().intValue()) {
                            examineCode = LoanExamineCode.NOT_ENOUGH_VALID_USER;
                        }
                    } else {
                        if (userCount.intValue() < review.getReviewCount().intValue()) {
                            examineCode = LoanExamineCode.NOT_ENOUGH_VALID_USER;
                        }
                    }
                } else {
                    examineCode = LoanExamineCode.REVIEW_COUNT_POSITIVE;
                }
            }
        }else {
            examineCode = LoanExamineCode.FIND_NOT_LOAN_PROCESS;
        }
        loanExamine.setExamineCode(examineCode);
    }

    /**
     * 得到审批条件的值
     * @param approveCondition
     * @return
     */
    private Object getConditionValue(Integer loanId,String processType,WfApproveCondition approveCondition){
        String tableName = approveCondition.getTableName();
        String columnName = approveCondition.getTableColumn();
        String fieldType = approveCondition.getFieldType();
        Object val = approveConditionDao.getConditionValue(loanId,processType,tableName,columnName);
        if(fieldType.equalsIgnoreCase("Select")){
            return TypeUtil.changeType(val,String.class);
        }else if(fieldType.equalsIgnoreCase("Number")){
            return TypeUtil.changeType(val,Integer.class);
        }else if(fieldType.equalsIgnoreCase("Decimal")){
            return TypeUtil.changeType(val,BigDecimal.class);
        }else{
            return val;
        }
    }

    /**
     * 得到限制的审核人员数
     * @param loanExamineReview
     * @return
     */
    private Integer getLimitExamineUserCount(LoanExamineReview loanExamineReview){
        Integer count = 0;
        if(loanExamineReview.getType().equals(LoanExamineType.RANDOM_USER)) {
            if (loanExamineReview.getRandomUserList() != null) {
                for (LoanExamineUser examineUser : loanExamineReview.getRandomUserList()) {
                    if (examineUser.isLimitEnable()) {
                        count++;
                    }
                }
            }
        }else{
            if (loanExamineReview.getExamineUserList() != null) {
                for (LoanExamineUser examineUser : loanExamineReview.getExamineUserList()) {
                    if (examineUser.isLimitEnable()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * 得到审核人员数
     * @param loanExamineReview
     * @return
     */
    private Integer getExamineUserCount(LoanExamineReview loanExamineReview){
        Integer count = 0;
        if(loanExamineReview.getType().equals(LoanExamineType.RANDOM_USER)){
            if (loanExamineReview.getRandomUserList() != null) {
                return loanExamineReview.getRandomUserList().size();
            }
        }else {
            if (loanExamineReview.getExamineUserList() != null) {
                return loanExamineReview.getExamineUserList().size();
            }
        }
        return count;
    }

    /**
     * 初始化审核人员，限额
     * @return
     */
    private void initUserLimit(LoanExamine loanExamine){
        if(loanExamine.getProposalAmount()!=null){
            for(LoanExamineReview review : loanExamine.getReviews()){
                if(review.isLimit() && review.getExamineUserList()!=null){
                    for(LoanExamineUser examineUser : review.getExamineUserList()){
                        if(examineUser.getLimitAmount()!=null && examineUser.getLimitAmount().intValue()>0){
                            boolean enable = examineUser.getLimitAmount().doubleValue()>=loanExamine.getProposalAmount().doubleValue();
                            examineUser.setLimitEnable(enable);
                        }else{
                            examineUser.setLimitEnable(true);
                        }
                    }
                } else {
                    for(LoanExamineUser examineUser : review.getExamineUserList()){
                        examineUser.setLimitEnable(true);
                    }
                }
            }
        }
    }

    /**
     * 初始化随机用户
     * @param loanExamine
     */
    private void initRandomUser(LoanExamine loanExamine){
        for(LoanExamineReview review : loanExamine.getReviews()){
            if (review.getType().equals(LoanExamineType.RANDOM_USER)) {
                int reviewCount = review.getReviewCount();
                List<LoanExamineUser> examineUserList = review.getExamineUserList();
                if (examineUserList != null && examineUserList.size() > 0) {
                    if (review.isLimit()) {
                        List<LoanExamineUser> limitList = new ArrayList<LoanExamineUser>();
                        for (LoanExamineUser examineUser : review.getExamineUserList()) {
                            if (examineUser.isLimitEnable())
                                limitList.add(examineUser);
                        }
                        if(limitList.size()>0) {
                            int no1 = new Random().nextInt(limitList.size());               //先在限额用户中，随机选取一个用户
                            LoanExamineUser first = limitList.get(no1);
                            List<LoanExamineUser> restList = new ArrayList<LoanExamineUser>();
                            for (LoanExamineUser examineUser : review.getExamineUserList()) {
                                if (!examineUser.equals(first))
                                    restList.add(examineUser);
                            }
                            review.getRandomUserList().add(first);
                            reviewCount--;                          //
                            if(reviewCount>0 && restList.size()>=reviewCount) {
                                int[] indexes = RandomUtil.randomCommon(restList.size(), reviewCount);
                                for (int index : indexes) {
                                    review.getRandomUserList().add(restList.get(index));
                                }
                            }
                        }
                    } else {
                        if(reviewCount>0 && examineUserList.size()>=reviewCount) {
                            int[] indexes = RandomUtil.randomCommon(examineUserList.size(), reviewCount);
                            for (int index : indexes) {
                                review.getRandomUserList().add(examineUserList.get(index));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 条件参数是否有效
     * @param loanExamine
     * @return
     */
    private boolean checkValidParamValue(LoanExamine loanExamine){
        if(loanExamine.isHasCondition()) {
            Object fieldValue = loanExamine.getFieldValue();
            if(loanExamine.getFieldValue() == null) {
                loanExamine.setExamineCode(LoanExamineCode.CONDITION_PARAM_REQUIRED);
                return false;
            }
            if("".equals(fieldValue)){
                loanExamine.setExamineCode(LoanExamineCode.CONDITION_PARAM_REQUIRED);
                return false;
            }else if(fieldValue instanceof BigDecimal && ((BigDecimal)fieldValue).doubleValue()<=0){
                loanExamine.setExamineCode(LoanExamineCode.CONDITION_PARAM_POSITIVE);
                return false;
            }else if(fieldValue instanceof Integer && ((Integer)fieldValue).intValue()<=0){
                loanExamine.setExamineCode(LoanExamineCode.CONDITION_PARAM_POSITIVE);
                return false;
            }
        }
        return true;
    }

    /**
     * 选取符合的条件参数
     * @return
     */
    private WfApproveConditionParams getCurentCondtionParam(String fieldType,Object fieldValue,List<WfApproveConditionParams> paramList){
        if(fieldValue!=null){
            if(fieldType.equalsIgnoreCase("Select")){
                String val = (String)fieldValue;
                for(WfApproveConditionParams param : paramList){
                    if(val.equalsIgnoreCase(param.getParam2()))
                        return param;
                }
            }else if(fieldType.equalsIgnoreCase("Number")){
                Integer val = (Integer)fieldValue;
                for(int i=0;i<paramList.size();i++) {
                    WfApproveConditionParams param = paramList.get(i);
                    Integer p1 = (StringUtil.isNotEmpty(param.getParam1()))?Integer.parseInt(param.getParam1()):null;
                    Integer p2 = (StringUtil.isNotEmpty(param.getParam2()))?Integer.parseInt(param.getParam2()):null;
                    if(i==0){
                        if(p1!=null && val.intValue()<p1.intValue()){       //最小值
                            return param;
                        }
                    }else{
                        if(p1!=null && p2!=null){
                            if(val.intValue()>=p1.intValue() && val.intValue()<p2.intValue()){
                                return param;
                            }
                        }else if(p1!=null && val.intValue()>=p1.intValue()){
                            return param;
                        }
                    }
                }
            }else if(fieldType.equalsIgnoreCase("Decimal") || fieldType.equalsIgnoreCase("Float")){
                Double val = ((BigDecimal)fieldValue).doubleValue();
                for(int i=0;i<paramList.size();i++) {
                    WfApproveConditionParams param = paramList.get(i);
                    Double p1 = (StringUtil.isNotEmpty(param.getParam1()))?Double.parseDouble(param.getParam1()):null;
                    Double p2 = (StringUtil.isNotEmpty(param.getParam2()))?Double.parseDouble(param.getParam2()):null;
                    if(i==0){
                        if(p1 !=null && val.doubleValue()<p1.doubleValue()){       //最小值
                            return param;
                        }
                    }else{
                        if(p1!=null && p2!=null){
                            if(val.doubleValue()>=p1.doubleValue() && val.doubleValue()<p2.doubleValue()){
                                return param;
                            }
                        }else if(p1!=null && val.doubleValue()>=p1.doubleValue()){
                            return param;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 取到下一个环节
     * @param processList
     * @param currentProcessId
     * @return
     */
    private WfApproveProcess getNextApproveProcess(List<WfApproveProcess> processList,Integer currentProcessId){
        for(int i=0;i<processList.size();i++){
            WfApproveProcess process = processList.get(i);
            if(process.getProcessId().equals(currentProcessId)){
                int index = i+1;
                if(index<processList.size()){
                    return processList.get(index);
                }
            }
        }
        return null;
    }

    /**
     * 保存审批计录
     * @param auditList
     */
    public void saveLoanAuditFlowInfo(List<LoanCurrentAuditStatus> auditList){
        for(LoanCurrentAuditStatus auditStatus : auditList) {
            currentAuditStatusService.insertCurrentAuditStatus(auditStatus,auditStatus.getCreateUser());
        }
    }

    /**
     * 根据条件查询审核表
     * @param condition
     * @return
     */
    @Override
    public List<LoanCurrentAuditStatus> queryLoanAuditByCondition(Map<String, Object> condition) {
        return currentAuditStatusService.queryCurrentAuditStatusList(condition);
    }

}
