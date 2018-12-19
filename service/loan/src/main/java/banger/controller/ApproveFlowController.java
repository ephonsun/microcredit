package banger.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

import banger.domain.enumerate.LoanLinkTypeEnum;
import banger.domain.loan.*;
import banger.framework.util.StringUtil;
import banger.service.intf.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.config.AutoTableColumn;
import banger.moduleIntf.IConfigModule;
import banger.moduleIntf.ISystemModule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 贷款审批流配置
 * @author taowj
 */
@Controller
@RequestMapping("/loanApproveFlow")	
public class ApproveFlowController extends BaseController {
	
	private static final long serialVersionUID = -8166243698164600291L;
	
	@Autowired
	private IApproveConditionService approveConditionService;
	@Autowired
	private IApproveConditionParamsService approveConditionParamsService;
	@Autowired
	private IApproveInfoDefService approveInfoDefService;
	@Autowired
	private IApproveProcessService approveProcessService;
	@Autowired
	private IApproveProcessReviewService approveProcessReviewService;
	@Autowired
	private IAuditTableFieldService auditTableFieldService;
	@Autowired
	private IConfigModule configModule;
	@Autowired
	private ISystemModule systemModuleImpl;
	@Autowired
	private ITypeService typeService;
	/**
	 * 切换审批流生效条件
	 */
	@RequestMapping("/switchApproveFlowCondition")
	@ResponseBody
	public void switchApproveFlowCondition(String classId,String isCondition){
		try {
			Integer loginUserId = getLoginInfo().getUserId();
			Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("classId", Integer.valueOf(classId));
			condition.put("isDel", 0);
			List<WfApproveInfoDef> approveInfoDefs = approveInfoDefService.queryApproveInfoDefList(condition);
			if(approveInfoDefs != null && approveInfoDefs.size()>0){
				//如果有2条以上未删除记录
				if(approveInfoDefs.size() > 2){
					renderText(false, "数据异常！", null);
					return;
				}
				//根据isCondition 设置生效记录
				for (WfApproveInfoDef temp : approveInfoDefs) {
					//更新版本号
					temp.setAuditVersion(new Date().getTime()+"");
					if(String.valueOf(temp.getIsCondition()).equals(isCondition)){
						temp.setIsActived(1);
					}else{
						temp.setIsActived(0);
					}
					approveInfoDefService.updateApproveInfoDef(temp, loginUserId);
				}
			}else{
				//如果根本没有审批流条件
				approveInfoDefService.insertApproveInfoDef(1,Integer.valueOf(classId),Integer.valueOf(isCondition),loginUserId);
				Integer isC = 0;
				if("0".equals(isCondition))
					isC = 1;
				approveInfoDefService.insertApproveInfoDef(0,Integer.valueOf(classId),isC,loginUserId);
			}
			renderText(true, "切换成功！", null);
			addSystemLog(5);
			return;
		} catch (Exception e) {
			log.error("switchApproveFlowCondition error",e);
		}
		renderText(false, "切换失败！", null);
	}
	
	/**
	 * 审批流主页面
	 */
	@RequestMapping("/getApproveFlowListPage")
	public String getApproveFlowListPage(String classId){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("classId", Integer.valueOf(classId));
		condition.put("isDel", 0);
		List<WfApproveInfoDef> approveInfoDefs = approveInfoDefService.queryApproveInfoDefList(condition);
		Integer loginUserId = getLoginInfo().getUserId();
		//查询生效记录
		WfApproveInfoDef approveInfoDef = null;
		if(approveInfoDefs != null&&approveInfoDefs.size()>0){
			for (WfApproveInfoDef temp : approveInfoDefs) {
				if(temp.getIsActived() == 1){
					approveInfoDef = temp;
				}
			}
		}else{
			//如果没有生效记录 默认第一次进入插入生效无条件记录 禁用有条件记录  
			approveInfoDef = new WfApproveInfoDef();
			approveInfoDef.setClassId(Integer.valueOf(classId));
			approveInfoDef.setIsActived(1);
			approveInfoDef.setIsCondition(0);
			approveInfoDef.setAuditVersion(new Date().getTime()+"");
			approveInfoDefService.insertApproveInfoDef(approveInfoDef, loginUserId);
			approveInfoDefService.insertApproveInfoDef(0,Integer.valueOf(classId),1,loginUserId);
		}
		String conditionInfo = approveConditionService.queryConditionInfo(approveInfoDef);
		String loanTypeName = typeService.getTypeById(Integer.valueOf(classId)).getLoanTypeName();
		setAttribute("loanTypeName",loanTypeName);
		setAttribute("conditionInfo", conditionInfo);
		setAttribute("approveInfoDef", approveInfoDef);
		return "/loan/vm/approveFlowList";
	}
	
	/**
	 * 查询审批环节
	 */
	@RequestMapping("/queryApproveProcessListData")
	@ResponseBody
	public void queryApproveProcessListData(String flowId,String paramId){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("flowId", Integer.valueOf(flowId));
		condition.put("isDel", 0);
		if(StringUtils.isNotBlank(paramId))
			condition.put("paramId", Integer.valueOf(paramId));
		List<WfApproveProcess_Query> list = approveProcessService.queryApproveProcessList(condition);

		String[] chineStrs = new String[]{"一","二","三","四","五","六","七","八","九","十"};
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				WfApproveProcess_Query approveProcess = list.get(i);
				if(i<10) {
					approveProcess.setProcessName(chineStrs[i]+"审");
				}else if(i<20){
					approveProcess.setProcessName("十"+chineStrs[i%10]+"审");
				}else{
					approveProcess.setProcessName(chineStrs[i/10]+"十"+chineStrs[i%10]+"审");
				}
			}
		}

		renderText(JsonUtil.toJson(list, "processId","processId,flowId,revievRoleName,paramId,processName,orderNo,reviewMode,reviewDataPower,revievRoleId,reviewCount,isActived,isDel,isFixed,createDate,updateDate,createUser,updateUser,isLimitControl,roleCount").toString());
	}
	
	/**
	 * 新增编辑审批环节页面
	 */
	@RequestMapping("/getApproveProcessSavePage")
	public String getApproveProcessInsertPage(String processId,String flowId,String paramId) throws UnsupportedEncodingException {
		WfApproveProcess approveProcess =  null;
		String paramNameStr = this.getParameter("paramName");
		String paramName = StringUtil.isNotEmpty(paramNameStr)?URLDecoder.decode(paramNameStr , "UTF-8"):"";
		String processNameStr = this.getParameter("processName");
		String processName = StringUtil.isNotEmpty(processNameStr)?URLDecoder.decode(processNameStr , "UTF-8"):"";
		String paramValue = this.getParameter("paramValue");
		String paramNo = this.getParameter("paramNo");
		String conditionId = this.getParameter("conditionId");
		List<WfApproveProcessReview> reviewList = new ArrayList<WfApproveProcessReview>();
		if(StringUtils.isNotBlank(processId)){
			approveProcess =  approveProcessService.getApproveProcessById(Integer.valueOf(processId));
			approveProcess.setProcessName(processName);
			reviewList = approveProcessReviewService.getApproveProcessReviewListByProcessId(Integer.parseInt(processId));
		}else{
			approveProcess = new WfApproveProcess();
			approveProcess.setFlowId(Integer.valueOf(flowId));
			approveProcess.setParamId(Integer.valueOf(paramId));
			approveProcessService.setApproveProcessName(approveProcess);
			reviewList.add(new WfApproveProcessReview());
		}

		setAttribute("processId",processId);
		setAttribute("processName",processName);
		setAttribute("flowId",flowId);
		setAttribute("conditionId",conditionId);
		setAttribute("paramNo",paramNo);
		setAttribute("paramId",paramId);
		setAttribute("paramName",paramName);
		setAttribute("paramValue",paramValue);

		setAttribute("approveProcess", approveProcess);
		setAttribute("reviewList",reviewList);
		return "/loan/vm/approveProcessSave";
	}

	/**
	 * 新增编辑审批环节
	 */
	@RequestMapping("/saveApproveProcess")
	@ResponseBody
	public void saveApproveProcess(HttpServletRequest request, HttpServletResponse response) {
		try {
			String processId = this.getParameter("processId");
			String paramId = this.getParameter("paramId");
			String paramName = this.getParameter("paramName");
			String paramValue = this.getParameter("paramValue");
			String orderNo = this.getParameter("orderNo");
			String paramNo = this.getParameter("paramNo");
			String flowId = this.getParameter("flowId");
			String conditionId = this.getParameter("conditionId");
			String postJsonString = this.getParameter("postJsonString");
			JSONArray ja = JSONArray.fromObject(postJsonString);
			Integer loginUserId = getLoginInfo().getUserId();
			List<WfApproveProcessReview> reviewList = new ArrayList<WfApproveProcessReview>();
			for(int i=0;i<ja.size();i++){
				JSONObject jo = (JSONObject)ja.get(i);
				String reviewId = jo.getString("reviewId");
				String revievRoleId = jo.getString("revievRoleId");
				String reviewDataPower = jo.getString("reviewDataPower");
				String reviewCount = jo.getString("reviewCount");
				String reviewMode = jo.getString("reviewMode");
				String isLimitControl = jo.getString("isLimitControl");
				WfApproveProcessReview review = new WfApproveProcessReview();
				if(StringUtil.isNotEmpty(reviewId))
					review.setId(Integer.parseInt(reviewId));
				review.setRevievRoleId(Integer.parseInt(revievRoleId));
				review.setReviewDataPower(reviewDataPower);
				review.setReviewCount(Integer.parseInt(reviewCount));
				review.setReviewMode(reviewMode);
				review.setIsLimitControl(Integer.parseInt(isLimitControl));
				reviewList.add(review);
			}

			if(paramId!=null && StringUtil.isNotEmpty(paramName) && StringUtil.isNotEmpty(paramValue)){
				int id = Integer.parseInt(paramId);
				if(id==0){
					WfApproveConditionParams params = new WfApproveConditionParams();
					params.setConditionId(Integer.parseInt(conditionId));
					params.setFlowId(Integer.parseInt(flowId));
					if(StringUtil.isNotEmpty(paramNo))
						params.setParamNo(Integer.parseInt(paramNo));
					params.setParam1(paramName);
					params.setParam2(paramValue);
					approveConditionParamsService.insertApproveConditionParams(params,loginUserId);
					paramId = params.getParamsId()+"";
				}
			}

			WfApproveProcess approveProcess = new WfApproveProcess();
			approveProcess.setIsActived(1);
			approveProcess.setIsDel(0);
			if(StringUtil.isNotEmpty(processId))
				approveProcess.setProcessId(Integer.parseInt(processId));
			if(StringUtil.isNotEmpty(flowId))
				approveProcess.setFlowId(Integer.parseInt(flowId));
			if(StringUtil.isNotEmpty(orderNo))
				approveProcess.setOrderNo(Integer.parseInt(orderNo));
			if(StringUtil.isNotEmpty(paramId))
				approveProcess.setParamId(Integer.parseInt(paramId));

			if(StringUtil.isNotEmpty(processId)){
				approveProcessService.updateApproveProcess(approveProcess, loginUserId);
				approveProcessReviewService.saveApproveProcessReviews(approveProcess.getProcessId(),loginUserId,reviewList);
				renderText(true, "保存成功！", null);
				addSystemLog(2);
			}else{
				approveProcessService.insertApproveProcess(approveProcess, loginUserId);
				approveProcessReviewService.saveApproveProcessReviews(approveProcess.getProcessId(),loginUserId,reviewList);
				renderText(true, "添加成功！", null);
				addSystemLog(1);
			}
		} catch (Exception e) {
			log.error("saveApproveProcess error",e);
			renderText(false,"保存失败！",null);
		}
	}
	
	/**
	 * 删除审批环节
	 */
	@RequestMapping("/deleteApproveProcess")
	@ResponseBody
	public void deleteApproveProcess(String processId,String flowId) {
		try {
			//Integer loginUserId = getLoginInfo().getUserId();
			if(StringUtils.isNumeric(processId)){
				//更新版本号
				approveInfoDefService.updateVersionById(Integer.valueOf(flowId));
				approveProcessService.deleteApproveProcessById(Integer.valueOf(processId));
				// 删除审批字段的配置
				auditTableFieldService.deleteAuditTableFieldByLinkId(Integer.parseInt(processId), LoanLinkTypeEnum.LOAN_AUDIT_PROCESS_ID);
				renderText(true, "删除成功！", null);
			}else{
				renderText(false, "删除失败！", null);
			}
			addSystemLog(3);
			return;
		} catch (Exception e) {
			log.error("deleteApproveProcess error",e);
		}
		renderText(false,"删除失败！",null);
	}
	
	/**
	 * 添加审批条件页面
	 */
	@RequestMapping("/getApproveConditionSavePage")
	public String getApproveConditionSavePage(String flowId){
		WfApproveInfoDef approveInfoDef = approveInfoDefService.getApproveInfoDefById(Integer.parseInt(flowId));
		List<AutoTableColumn> autoTableColumns = null;
		if(approveInfoDef!=null) {
			autoTableColumns = configModule.getAutoFieldProvider().getConditionFieldList(approveInfoDef.getClassId());
		}
		setAttribute("autoTableColumns", autoTableColumns);
		setAttribute("flowId", flowId);
		return "/loan/vm/approveConditionSave";
	}
	
	/**
	 * 新建编辑审批添加
	 */
	@RequestMapping("/saveApproveCondition")
	@ResponseBody
	public void saveApproveCondition(String flowId,String fieldId,String count,String[] nums) {
		try {
			Integer loginUserId = getLoginInfo().getUserId();
			AutoTableColumn tableColumn = configModule.getAutoFieldProvider().getTableColumnById(Integer.valueOf(fieldId));
			approveConditionService.saveApproveCondition(tableColumn, Integer.valueOf(flowId), count, nums,loginUserId);
			renderText(true, "设置条件成功！", null);
			addSystemLog(4);
			return;
		} catch (Exception e) {
			log.error("saveApproveCondition error",e);
		}
		renderText(false,"设置条件失败！",null);
	}
	
	/**
	 * 添加系统日志
	 *
	 * @param type
	 */
	private void addSystemLog(int type) {
		String opeVentAction = null;
		switch (type) {
			case 1:
				opeVentAction = "新增审批环节";
				break;
			case 2:
				opeVentAction = "修改审批环节";
				break;
			case 3:
				opeVentAction = "删除审批环节";
				break;
			case 4:
				opeVentAction = "设置审批条件";
				break;
			case 5:
				opeVentAction = "有无条件切换";
				break;
			default:
				break;
		}
		// 新增系统日志
		// insert系统日志表   模块名称、操作对象名称、操作者userId、操作者ip地址
		this.systemModuleImpl.addSysOpeventLog("审批流模块", opeVentAction, this.getLoginInfo().getUserId(), this.getLoginInfo().getIp());
	}
}
