package banger.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.loan.LoanCurrentAuditStatus;
import banger.framework.pagesize.IPageList;
import banger.service.intf.ICurrentAuditStatusService;

/**
 * 审批进度审核状态表页面访问类
 */
@Controller
@RequestMapping("/LoanCurrentAuditStatus")
public class CurrentAuditStatusController extends BaseController {
	private static final long serialVersionUID = 6959618917189368115L;
	@Autowired
	private ICurrentAuditStatusService currentAuditStatusService;

	/**
	 * 得到审批进度审核状态表列表页面
	 * @return
	 */
	@RequestMapping("/getCurrentAuditStatusListPage")
	public String getCurrentAuditStatusListPage(){
		Map<String,Object> condition = new HashMap<String,Object>();
		IPageList<LoanCurrentAuditStatus> currentAuditStatusList = currentAuditStatusService.queryCurrentAuditStatusList(condition,this.getPage());
		setAttribute("currentAuditStatusList",currentAuditStatusList);
		return SUCCESS;
	}

	/**
	 * 查询审批进度审核状态表列表数据
	 * @return
	 */
	@RequestMapping("/queryCurrentAuditStatusListData")
	public String queryCurrentAuditStatusListData(){
		Map<String,Object> condition = new HashMap<String,Object>();
		IPageList<LoanCurrentAuditStatus> currentAuditStatusList = currentAuditStatusService.queryCurrentAuditStatusList(condition,this.getPage());
		renderText(JsonUtil.toJson(currentAuditStatusList, "id","loanId,processId,auditUserId,auditUserName,auditResult,auditOpinion,auditResolution,auditVersion,createDate,updateDate,createUser,updateUser").toString());
		return null;
	}

	/**
	 * 得到审批进度审核状态表新增页面
	 * @return
	 */
	@RequestMapping("/getCurrentAuditStatusInsertPage")
	public String getCurrentAuditStatusInsertPage(){
		LoanCurrentAuditStatus currentAuditStatus = new LoanCurrentAuditStatus();
		setAttribute("currentAuditStatus",currentAuditStatus);
		return SUCCESS;
	}

	/**
	 * 得到审批进度审核状态表修改页面
	 * @return
	 */
	@RequestMapping("/getCurrentAuditStatusUpdatePage")
	public String getCurrentAuditStatusUpdatePage(){
		String id = getParameter("id");
		LoanCurrentAuditStatus currentAuditStatus = currentAuditStatusService.getCurrentAuditStatusById(Integer.parseInt(id));
		setAttribute("currentAuditStatus",currentAuditStatus);
		return SUCCESS;
	}

	/**
	 * 得到审批进度审核状态表查看页面
	 * @return
	 */
	@RequestMapping("/getCurrentAuditStatusDetailPage")
	public String getCurrentAuditStatusDetailPage(){
		String id = getParameter("id");
		LoanCurrentAuditStatus currentAuditStatus = currentAuditStatusService.getCurrentAuditStatusById(Integer.parseInt(id));
		setAttribute("currentAuditStatus",currentAuditStatus);
		return SUCCESS;
	}

	/**
	 * 新增审批进度审核状态表数据
	 * @return
	 */
	@RequestMapping("/insertCurrentAuditStatus")
	public String insertCurrentAuditStatus(@ModelAttribute LoanCurrentAuditStatus currentAuditStatus){
		Integer loginUserId = getLoginInfo().getUserId();
		currentAuditStatusService.insertCurrentAuditStatus(currentAuditStatus,loginUserId);
		renderText(SUCCESS);
		return null;
	}

	/**
	 * 修改审批进度审核状态表数据
	 * @return
	 */
	@RequestMapping("/updateCurrentAuditStatus")
	public String updateCurrentAuditStatus(@ModelAttribute LoanCurrentAuditStatus currentAuditStatus){
		Integer loginUserId = getLoginInfo().getUserId();
		currentAuditStatusService.updateCurrentAuditStatus(currentAuditStatus,loginUserId);
		renderText(SUCCESS);
		return null;
	}

	/**
	 * 删除审批进度审核状态表数据
	 * @return
	 */
	@RequestMapping("/deleteCurrentAuditStatus")
	public String deleteCurrentAuditStatus(){
		String id = getParameter("id");
		currentAuditStatusService.deleteCurrentAuditStatusById(Integer.parseInt(id));
		renderText(SUCCESS);
		return null;
	}

}
