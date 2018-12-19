package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanApplyInfo_Web_Query;
import banger.domain.loan.LoanAudit;
import banger.domain.permission.ILoginInfo;
import banger.domain.permission.SysTeamGroup_Query;
import banger.framework.pagesize.IPageList;
import banger.moduleIntf.ITeamGroupProvider;
import banger.service.intf.IAuditService;
import banger.service.intf.ILoanApplyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanggl on 2017/7/10.
 */
@Controller
@RequestMapping("/loanAuditManage")
public class LoanAuditManageController extends BaseController{
    @Resource
    private ITeamGroupProvider teamGroupProvider;
    @Resource
    ILoanApplyService loanApplyService;
    private Map<String, String> moduleMapForList = null;
    @Autowired
    private IAuditService auditService;

    /**
     * 跳转审计管理页面
     */
    @RequestMapping("getLoanAuditManagePage")
    public ModelAndView getLoanAuditManagePage(){
        ModelAndView mv = new ModelAndView("/loan/vm/audit/auditManageList");
        List<SysTeamGroup_Query> allGroups = teamGroupProvider.getAllGroups();
        mv.addObject("groupList",allGroups);
        return mv;
    }

    /**
     * 查询列表数据
     * @param processType 【各个阶段】
     * @param customer 【客户信息，模糊查询，姓名电话身份证号】
     * @param loanTypeId【贷款类型id】
     * @param startDate 【开始时间】
     * @param endDate【结束时间】
     * @param auditStartDate【审批开始时间】
     * @param auditEndDate【审批结束时间】
     * @param montiorState【监控状态】
     * @param afterState【贷款状态】
     * @param memberUserId【申请人id】
     */
    @RequestMapping(value = "/queryLoanInfoListData")
    @ResponseBody
    public void queryApplyInfoListData(@RequestParam(value = "module", defaultValue = "") String module,
                                       @RequestParam(value = "processType", defaultValue = "") String processType,
                                       @RequestParam(value = "customer", defaultValue = "") String customer,
                                       @RequestParam(value = "loanTypeId", defaultValue = "") String loanTypeId,
                                       @RequestParam(value = "startDate", defaultValue = "") String startDate,
                                       @RequestParam(value = "endDate", defaultValue = "") String endDate,
                                       @RequestParam(value = "auditStartDate", defaultValue = "") String auditStartDate,
                                       @RequestParam(value = "auditEndDate", defaultValue = "") String auditEndDate,
                                       @RequestParam(value = "montiorState", defaultValue = "") String montiorState,
                                       @RequestParam(value = "collectionState", defaultValue = "") String collectionState,
                                       @RequestParam(value = "afterState", defaultValue = "") String afterState,
                                       @RequestParam(value = "memberUser", defaultValue = "") String memberUser,
                                       @RequestParam(value = "teamId", defaultValue = "") String teamId,
                                       @RequestParam(value = "memberUserId", defaultValue = "") String memberUserId){
        module = firstUpperCase(module);

        Map<String,Object> condition = new HashMap<String,Object>();
        if ("All".equals(module)) {
            //贷款阶段有筛选值的时候，选择不选拒绝的列表，排除已拒绝的数据
            if (StringUtils.isNotBlank(processType)) {
                if (LoanProcessTypeEnum.REFUSE.type.equals(processType)) {
                    condition.put("refuse", "1");
                } else {
                    condition.put("notrefuse", "1");
                    condition.put("loanProcessType", processType);
                }
            }
        } else {
            condition.put("loanProcessType", module);
            condition.put("notrefuse", "1");
            //催收列表过滤条件属于贷款状态
            if ("Collection".equals(module)) {
                condition.put("loanProcessType", LoanProcessTypeEnum.AFTER_LOAN.type);
            }
        }
        condition.put("isDel", 0);
        condition.put("customerInfo", customer);
        condition.put("loanTypeId", loanTypeId);
        condition.put("montiorState", montiorState);
        condition.put("afterState", afterState);
        condition.put("collectionState", collectionState);
        condition.put("abc", "abc");

        ILoginInfo loginInfo = getLoginInfo();
        Integer userId = loginInfo.getUserId();
        if ("Approval".equals(module)) {
            condition.put("investigationBeginDate", startDate);
            condition.put("investigationEndDate", endDate);
            condition.put("auditUserId", userId);
        } else if ("Apply".equals(module) || "Investigate".equals(module) || "AfterLoan".equals(module) || "Collection".equals(module)) {
            condition.put("loanBelongId", userId);
            if ("Apply".equals(module)) {
                condition.put("createBeginDate", startDate);
                condition.put("createEndDate", endDate);
            } else if ("Investigate".equals(module)) {
                condition.put("assignmentBeginDate", startDate);
                condition.put("assignmentEndDate", endDate);
            } else if ("AfterLoan".equals(module)) {
                condition.put("creditBeginDate", startDate);
                condition.put("creditEndDate", endDate);
            } else if ("Collection".equals(module)) {
                condition.put("repayBeginDate", startDate);
                condition.put("repayEndDate", endDate);
                //condition.put("collection", "1");		//目前显示所有贷后信息
            }
        } else if ("All".equals(module) || "Allot".equals(module) || "Loan".equals(module)) {
            String groupIds = loginInfo.getTeamGroupIdByRole();
            condition.put("loanBelongId", userId);
            if (StringUtils.isNotBlank(groupIds))
                condition.put("groupIds", groupIds);
            else
                condition.put("groupIds", "-1");
            if ("All".equals(module)) {
                condition.put("belongUser", "true");
                condition.put("memberUser", memberUser);
                condition.put("createBeginDate", startDate);
                condition.put("createEndDate", endDate);
                if (StringUtils.isNotBlank(teamId)) {
                    condition.remove("loanBelongId");
                    condition.put("groupIds", teamId);
                }
                condition.put("investigationBeginDate", auditStartDate);
                condition.put("investigationEndDate", auditEndDate);
                condition.put("applyUser", "true");
                condition.put("investigateUser", "true");
                condition.put("team", "true");
            } else if ("Allot".equals(module) || "Loan".equals(module)) {
                condition.put("applyBeginDate", startDate);
                condition.put("applyEndDate", endDate);
                if ("Allot".equals(module)) {
                    condition.put("applyUser", "true");
                    if (StringUtils.isNotBlank(memberUserId)) {
                        condition.put("loanApplyUserId", Integer.parseInt(memberUserId));
                    }
                } else {
                    condition.put("auditBeginDate", auditStartDate);
                    condition.put("auditEndDate", auditEndDate);
                }
            }
        } else {
            condition.put("loanBelongId", 0);
        }
        condition.put("loanAuditState",getParameter("loanAuditState"));
        IPageList<LoanApplyInfo_Web_Query> allotInfos = loanApplyService.queryLoanAuditList(condition,this.getPage());
        String dateFormat = "yyyy-MM-dd HH:mm";
        if ("AfterLoan".equals(module))
            dateFormat = "yyyy-MM-dd";

        renderText(JsonUtil.toJson(allotInfos, "loanId", getModuleMapForList().get(module), dateFormat).toString());

    }

    /**
     * 审计详情页
     * @param loanId
     * @return
     */
    @RequestMapping("getDetailPage")
    public ModelAndView getDetailPage(@RequestParam("loanId") Integer loanId){
        ModelAndView mv  = new ModelAndView("/loan/vm/audit/auditDetail");
        HashMap<String,Object> condition = new HashMap<String, Object>();
        condition.put("loanId",loanId);
        List<LoanAudit> list = auditService.queryAuditList(condition);
        //是否已经审计完成 1 true 0 false
        Integer isAudited = 0;
        if(list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                //已经审计通过
                if(list.get(i).getLoanAuditState() == 1){
                    isAudited = 1;
                };
            }
        }
        mv.addObject("isNotRefuse",loanApplyService.getApplyInfoById(loanId).getLoanRefuseUser() == 0);
        mv.addObject("isAudited",isAudited);
        mv.addObject("loanAuditList",list);
        mv.addObject("loanId",loanId);
        return mv;
    }

    /**
     * 更新审计详情
     */
    @RequestMapping("/updateAuditDetail")
    public void updateAuditDetail(@RequestParam("loanAuditRemark") String loanAuditRemark,@RequestParam("loanAuditState") Integer loanAuditState,@RequestParam("auditLoanId") Integer auditLoanId){
        Integer loginUserId = getLoginInfo().getUserId();
        //更新审计
        LoanAudit loanAudit = new LoanAudit();
        loanAudit.setLoanId(auditLoanId);
        loanAudit.setLoanAuditState(loanAuditState);
        loanAudit.setLoanAuditRemark(loanAuditRemark);
        loanAudit.setLoanAuditors(getLoginInfo().getUserName());
        auditService.insertAudit(loanAudit,loginUserId);
        //更新主表
        LoanApplyInfo applyInfoById = loanApplyService.getApplyInfoById(auditLoanId);
        applyInfoById.setLoanAuditState(loanAuditState);
        applyInfoById.setLoanAuditors(getLoginInfo().getUserName());
        loanApplyService.updateApplyInfo(applyInfoById,loginUserId);
    }




    /**
     * 私有方法，配置贷款相关各个列表要显示的字段，注意列属性名称一致
     * @return
     */
    private Map<String, String> getModuleMapForList(){
//		开发阶段实时获取
//		if (moduleMapForList == null) {
        moduleMapForList = new HashMap<String, String>();
        //申请列表
        moduleMapForList.put("All","loanName,customerInfo,cardInfo,loanTypeName,loanApplyAmountFormat,loanProcessTypeName,loanAfterStateName,applyUserName,investigateUserName,teamName,createDate,loanInvestigationDate,loanAuditFlowId,loanAuditors,loanAuditState");
        //申请列表
        moduleMapForList.put("Apply","loanName,customerInfo,cardInfo,loanTypeName,loanApplyAmountFormat,createDate");
        //待分配列表
        moduleMapForList.put("Allot","loanName,customerInfo,cardInfo,loanTypeName,loanApplyAmountFormat,applyUserName,loanApplyDate");
        //待调查列表
        moduleMapForList.put("Investigate","loanName,customerInfo,cardInfo,loanTypeName,loanApplyAmountFormat,loanAssignmentDate");
        //待审批列表
        moduleMapForList.put("Approval","loanName,customerInfo,cardInfo,loanTypeName,loanApplyAmountFormat,loanInvestigationDate");
        //待放款列表
        moduleMapForList.put("Loan","loanName,customerInfo,cardInfo,loanTypeName,loanApplyAmountFormat,loanAuditDate,loanApplyDate");
        //贷后列表
        moduleMapForList.put("AfterLoan","loanName,customerInfo,cardInfo,loanTypeName,loanApplyAmountFormat,loanMonitorStateName,loanCreditDate,loanAfterStateName");
        //催收列表
        moduleMapForList.put("Collection","loanName,customerInfo,cardInfo,loanTypeName,loanRepayAmount,loanRepayDate");

        return moduleMapForList;
//		} else
//			return moduleMapForList;
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
     * 将首字母变为小写
     * @param module
     * @return
     */
    private String firstLowerCase(String module){
        if (StringUtils.isNotBlank(module)) {
            return module.substring(0,1).toLowerCase()+module.substring(1);
        } else {
            return "";
        }
    }
}
