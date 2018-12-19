package banger.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.HashMap;
import banger.domain.loan.LoanFlowStepItem;
import banger.service.intf.IFlowStepItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import banger.framework.pagesize.IPageList;
import banger.service.intf.ISurveyFlowInfoService;
import banger.domain.loan.LoanSurveyFlowInfo;
import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import org.springframework.web.bind.annotation.*;

/**
 * 调查流程表页面访问类../SurveyFlow/getSurveyFlowListPage.html
 */
@Controller
@RequestMapping("/SurveyFlow")
public class SurveyFlowInfoController extends BaseController {
	private static final long serialVersionUID = 6217047851754010310L;
	@Autowired
	private ISurveyFlowInfoService surveyFlowInfoService;
	@Autowired
	private IFlowStepItemService flowStepItemService;
	private String basePath="loan/vm/surveyFlow/";

	/**
	 * 得到调查流程表列表页面
	 * @return
	 */
	@RequestMapping("/getSurveyFlowListPage")
	public String getSurveyFlowInfoListPage(){
		return basePath+"surveyFlowList";
	}

	/**
	 * 查询调查流程表列表数据
	 * @return
	 */
	@RequestMapping("/querySurveyFlowInfoListData")
	@ResponseBody
	public void querySurveyFlowInfoListData(){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("isDel",0);
		IPageList<LoanSurveyFlowInfo> surveyFlowInfoList = surveyFlowInfoService.querySurveyFlowInfoList(condition,this.getPage());
		renderText(JsonUtil.toJson(surveyFlowInfoList, "id","flowName,isDel,createUser,updateUser,createDate,updateDate").toString());
	}

	/**
	 * 得到调查流程表新增页面
	 * @return
	 */
	@RequestMapping("/getSurveyFlowInsertPage")
	public String getSurveyFlowInfoInsertPage(){
		return basePath+"surveyFlowSave";
	}

	/**
	 * 得到调查流程表修改页面
	 * @return
	 */
	@RequestMapping("/getSurveyFlowUpdatePage")
	public String getSurveyFlowInfoUpdatePage(@RequestParam Integer id) throws IOException {
		LoanSurveyFlowInfo  surveyFlow= this.surveyFlowInfoService.getSurveyFlowInfoById(id);
		setAttribute("surveyFlow",surveyFlow);
		return basePath+"/surveyFlowSave";
	}

	/**
	 * 得到调查流程表查看页面
	 * @return
	 */
	@RequestMapping("/getSurveyFlowInfoDetailPage")
	public String getSurveyFlowInfoDetailPage(){
		String id = getParameter("id");
		LoanSurveyFlowInfo surveyFlowInfo = surveyFlowInfoService.getSurveyFlowInfoById(Integer.parseInt(id));
		setAttribute("surveyFlowInfo",surveyFlowInfo);
		return SUCCESS;
	}

	/**
	 * 新增调查流程表数据
	 * @return
	 */
	@RequestMapping("/insertSurveyFlowInfo")
	public void insertSurveyFlowInfo(@ModelAttribute LoanSurveyFlowInfo surveyFlowInfo){
		try{
			Integer loginUserId = getLoginInfo().getUserId();
			surveyFlowInfoService.insertSurveyFlowInfo(surveyFlowInfo,loginUserId);
			Integer flowId = surveyFlowInfo.getId();
			String stepName = "步骤一";
			LoanFlowStepItem flowStepItem = new LoanFlowStepItem();
			flowStepItem.setFlowId(flowId);
			flowStepItem.setStepName(stepName);
			this.flowStepItemService.insertFlowStepItem(flowStepItem,loginUserId);
			renderText(true, "新建成功!", null);
		}catch (Exception e){
			log.error("insertSurveyFlowInfo error",e);
			renderText(false, "新建失败!", null);
		}
	}

	/**
	 * 修改调查流程表数据
	 * @return
	 */
	@RequestMapping("/updateSurveyFlowInfo")
	public void updateSurveyFlowInfo(@ModelAttribute LoanSurveyFlowInfo surveyFlowInfo){
		try{
			Integer loginUserId = getLoginInfo().getUserId();
			surveyFlowInfoService.updateSurveyFlowInfo(surveyFlowInfo,loginUserId);
			renderText(true, "修改成功!", null);
		}catch (Exception e){
			log.error("updateSurveyFlowInfo error",e);
			renderText(false, "修改失败!", null);
		}
	}

	/**
	 * 删除调查流程表数据
	 * @return
	 */
	@RequestMapping("/deleteSurveyFlowInfo")
	public void  deleteSurveyFlowInfo(){
		try {
			String id = getParameter("id");
			surveyFlowInfoService.deleteSurveyFlowInfoById(Integer.parseInt(id));
			renderText(true, "删除成功!", null);
		}catch (Exception e){
			log.error("deleteSurveyFlowInfo error",e);
			renderText(false, "删除失败!", null);
		}
	}

	/**
	 * 获取流程配置页面
	 * @return
	 */
	@RequestMapping("/getFlowStepList")
	public String getFlowStepList(@RequestParam("id") Integer id){
		LoanSurveyFlowInfo  surveyFlow= this.surveyFlowInfoService.getSurveyFlowInfoById(id);
		setAttribute("surveyFlow",surveyFlow);
		return basePath+"flowStepList";
	}

}
