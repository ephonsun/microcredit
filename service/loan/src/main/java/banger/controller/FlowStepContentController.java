package banger.controller;

import java.util.Map;
import java.util.HashMap;
import org.springframework.ui.Model;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import banger.framework.pagesize.IPageList;
import banger.service.intf.IFlowStepContentService;
import banger.domain.loan.LoanFlowStepContent;
import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import org.springframework.web.bind.annotation.*;

/**
 * 调查流程步骤内容表页面访问类
 */
@Controller
@RequestMapping("/LoanFlowStepContent")
public class FlowStepContentController extends BaseController {
	private static final long serialVersionUID = 4739642823057827025L;
	@Autowired
	private IFlowStepContentService flowStepContentService;

	/**
	 * 得到调查流程步骤内容表列表页面
	 * @return
	 */
	@RequestMapping("/getFlowStepContentListPage")
	public String getFlowStepContentListPage(){
		Map<String,Object> condition = new HashMap<String,Object>();
		IPageList<LoanFlowStepContent> flowStepContentList = flowStepContentService.queryFlowStepContentList(condition,this.getPage());
		setAttribute("flowStepContentList",flowStepContentList);
		return SUCCESS;
	}

	/**
	 * 查询调查流程步骤内容表列表数据
	 * @return
	 */
	@RequestMapping("/queryFlowStepContentListData")
	public String queryFlowStepContentListData(){
		Map<String,Object> condition = new HashMap<String,Object>();
		IPageList<LoanFlowStepContent> flowStepContentList = flowStepContentService.queryFlowStepContentList(condition,this.getPage());
		renderText(JsonUtil.toJson(flowStepContentList, "id","flowId,substance,sortNo,createUser,updateUser,createDate,updateDate").toString());
		return null;
	}

	/**
	 * 得到调查流程步骤内容表新增页面
	 * @return
	 */
	@RequestMapping("/getFlowStepContentInsertPage")
	public String getFlowStepContentInsertPage(){
		LoanFlowStepContent flowStepContent = new LoanFlowStepContent();
		setAttribute("flowStepContent",flowStepContent);
		return SUCCESS;
	}

	/**
	 * 得到调查流程步骤内容表修改页面
	 * @return
	 */
	@RequestMapping("/getFlowStepContentUpdatePage")
	public String getFlowStepContentUpdatePage(){
		String id = getParameter("id");
		LoanFlowStepContent flowStepContent = flowStepContentService.getFlowStepContentById(Integer.parseInt(id));
		setAttribute("flowStepContent",flowStepContent);
		return SUCCESS;
	}

	/**
	 * 得到调查流程步骤内容表查看页面
	 * @return
	 */
	@RequestMapping("/getFlowStepContentDetailPage")
	public String getFlowStepContentDetailPage(){
		String id = getParameter("id");
		LoanFlowStepContent flowStepContent = flowStepContentService.getFlowStepContentById(Integer.parseInt(id));
		setAttribute("flowStepContent",flowStepContent);
		return SUCCESS;
	}

	/**
	 * 新增调查流程步骤内容表数据
	 * @return
	 */
	@RequestMapping("/insertFlowStepContent")
	public String insertFlowStepContent(@ModelAttribute LoanFlowStepContent flowStepContent){
		Integer loginUserId = getLoginInfo().getUserId();
		flowStepContentService.insertFlowStepContent(flowStepContent,loginUserId);
		renderText(SUCCESS);
		return null;
	}

	/**
	 * 修改调查流程步骤内容表数据
	 * @return
	 */
	@RequestMapping("/updateFlowStepContent")
	public String updateFlowStepContent(@ModelAttribute LoanFlowStepContent flowStepContent){
		Integer loginUserId = getLoginInfo().getUserId();
		flowStepContentService.updateFlowStepContent(flowStepContent,loginUserId);
		renderText(SUCCESS);
		return null;
	}

	/**
	 * 删除调查流程步骤内容表数据
	 * @return
	 */
	@RequestMapping("/deleteFlowStepContent")
	public void deleteFlowStepContent(){
		try {
			String id = getParameter("subId");
			flowStepContentService.deleteFlowStepContentById(Integer.parseInt(id));
			renderText(true,"删除成功","");
		}catch (Exception e){
			log.error("deleteFlowStepContent error",e);
			renderText(false,"删除失败","");
		}
	}
}
