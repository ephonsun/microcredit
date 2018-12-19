package banger.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import banger.domain.loan.LoanFlowStepContent;
import banger.framework.web.dojo.JsonTools;
import banger.service.intf.IFlowStepContentService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import banger.domain.loan.LoanFlowStepItem;
import banger.framework.pagesize.IPageList;
import banger.service.intf.IFlowStepItemService;
import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 流程步骤明细表页面访问类
 */
@Controller
@RequestMapping("/flowStep")
public class FlowStepItemController extends BaseController {
	private static final long serialVersionUID = 6393863402499269360L;
	@Autowired
	private IFlowStepItemService flowStepItemService;

	@Autowired
	private IFlowStepContentService flowStepContentService;
	private String basePath="loan/vm/surveyFlow/";
	/**
	 * 得到流程步骤明细表列表页面
	 * @return
	 */
	@RequestMapping("/getFlowStepItemListPage")
	public String getFlowStepItemListPage(){
		Map<String,Object> condition = new HashMap<String,Object>();
		IPageList<LoanFlowStepItem> flowStepItemList = flowStepItemService.queryFlowStepItemList(condition,this.getPage());
		setAttribute("flowStepItemList",flowStepItemList);
		return  basePath+"flowStepList";
	}

	/**
	 * 查询流程步骤明细表列表数据
	 * @return
	 */
	@RequestMapping("/queryFlowStepList")
	public void queryFlowStepItemListData(){
		Integer flowId = Integer.valueOf(this.getParameter("flowId"));
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("isDel",0);
		condition.put("flowId",flowId);
		IPageList<LoanFlowStepItem> flowStepItemList = flowStepItemService.queryFlowStepItemList(condition,this.getPage());
		renderText(JsonUtil.toJson(flowStepItemList, "id","flowId,stepName,isDel").toString());
	}

	/**
	 * 得到流程步骤明细表新增页面
	 * @return
	 */
	@RequestMapping("/getFlowStepInsertPage")
	public String getFlowStepItemInsertPage(@RequestParam("flowId") Integer flowId){
		LoanFlowStepItem flowStepItem = new LoanFlowStepItem();
		flowStepItem.setFlowId(flowId);
		setAttribute("flowStepItem",flowStepItem);
		return basePath+"flowStepSave";
	}

	/**
	 * 得到流程步骤明细表修改页面
	 * @return
	 */
	@RequestMapping("/getFlowStepUpdatePage")
	public String getFlowStepItemUpdatePage(@RequestParam Integer id){
		LoanFlowStepItem flowStepItem = flowStepItemService.getFlowStepItemById(id);
		setAttribute("flowStepItem",flowStepItem);
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("flowId",id);
		condition.put("isDel",0);
		List<LoanFlowStepContent> stepContent = this.flowStepContentService.queryFlowStepContentList(condition);
		setAttribute("stepContent",stepContent);
		return basePath +"flowStepSave";
	}

	/**
	 * 得到流程步骤明细表查看页面
	 * @return
	 */
	@RequestMapping("/getFlowStepDetailPage")
	public String getFlowStepItemDetailPage(){
		String id = getParameter("id");
		LoanFlowStepItem flowStepItem = flowStepItemService.getFlowStepItemById(Integer.parseInt(id));
		setAttribute("flowStepItem",flowStepItem);
		return SUCCESS;
	}

	/**
	 * 新增流程步骤明细表数据
	 * @return
	 */
	@RequestMapping("/insertFlowStep")
	public void insertFlowStepItem(@RequestParam("stepName") String stepName,@RequestParam("stepId") Integer stepId,@RequestParam("flowId") Integer flowId){
		String dataList=this.getParameter("dataList");
		Integer loginUserId = getLoginInfo().getUserId();
		boolean isAdd = false;
		try{
			if(null == stepId){			//判断是新增还是修改
				LoanFlowStepItem flowStepItem= new LoanFlowStepItem();
				flowStepItem.setFlowId(flowId);
				flowStepItem.setIsDel(0);
				flowStepItem.setStepName(stepName);
				isAdd = true;
				//获取排序号
				Integer maxSortNum = flowStepItemService.getMaxSortNum();
				if(null == maxSortNum){
					maxSortNum = 0;
				}
				flowStepItem.setSortNo(maxSortNum+1);
				this.flowStepItemService.insertFlowStepItem(flowStepItem,loginUserId);
				stepId = flowStepItem.getId();
			}else{
				LoanFlowStepItem flowStepItem= new LoanFlowStepItem();
				flowStepItem.setStepName(stepName);
				flowStepItem.setId(stepId);
				this.flowStepItemService.updateFlowStepItem(flowStepItem,loginUserId);
			}
			JSONArray jsonArray = new JSONArray(dataList);
			if(null != jsonArray && jsonArray.length()>0){
				for(int i = 0; i<jsonArray.length();i++){
					JSONObject subObj= jsonArray.getJSONObject(i);
						this.flowStepItemService.updaOrAddSub(subObj,stepId,loginUserId);
				}
			}
			if(isAdd){
				renderText(true,"新增成功","");
			}else{
				renderText(true,"修改成功","");
			}

		}catch (Exception e){
			if(isAdd){
				log.error("insertFlowStep err", e);
				renderText(false,"新增失败","");
			}else{
				log.error("updateFlowStep err", e);
				renderText(false,"修改失败","");
			}

		}

	}


	/**
	 * 修改流程步骤明细表数据
	 * @return
	 */
	@RequestMapping("/updateFlowStep")
	public String updateFlowStepItem(@ModelAttribute LoanFlowStepItem flowStepItem){
		Integer loginUserId = getLoginInfo().getUserId();
		flowStepItemService.updateFlowStepItem(flowStepItem,loginUserId);
		renderText(SUCCESS);
		return null;
	}

	/**
	 * 删除流程步骤明细表数据
	 * @return
	 */
	@RequestMapping("/deleteFlowStep")
	public void deleteFlowStepItem(){
		try {
			String id = getParameter("id");
			flowStepItemService.deleteFlowStepItemById(Integer.parseInt(id));
			renderText(true,"删除成功","");
		}catch (Exception e){
			renderText(false,"删除失败","");
		}
	}

	/**
	 * 上移下移
	 */
	@RequestMapping("/moveStepSortNo")
	@ResponseBody
	public void moveStepSortNo(@RequestParam("stepId") Integer stepId, @RequestParam("type") String type,@RequestParam("flowId") Integer flowId){
		flowStepItemService.moveStepSortNo(stepId,type,flowId);
		this.renderText(SUCCESS);
	}



}
