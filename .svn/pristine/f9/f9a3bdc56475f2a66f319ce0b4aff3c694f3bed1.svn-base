package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.system.SysMatchProject;
import banger.domain.system.SysModelConfig;
import banger.domain.system.SysModelConfigExtend;
import banger.domain.system.SysModelManagement;
import banger.service.intf.IMatchProjectService;
import banger.service.intf.IModelConfigService;
import banger.service.intf.IModelManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/modelManagement")
public class ModelManagementController extends BaseController {

	private static final long serialVersionUID = 1L;
	@Autowired
	private IModelManagementService modelManagementService;
	@Autowired
	private IMatchProjectService matchProjectService;
	/**
	 * 得到列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/getModelManagementPage")
	public String getModelManagementPage() {
		return "/system/vm/modelManagement";
	}

	/**
	 * 查询模型列表
	 */
	@RequestMapping("/queryModelManagementList")
	public void queryModelManagement() {
		Map<String, Object> condition = new HashMap<String, Object>();
		List<SysModelManagement> modelManagementList = modelManagementService.queryModelManagementList(condition);

		renderText(JsonUtil
				.toJson(modelManagementList, "modelId",
						"modelName,matchProjectName,modelOrderNum,modelCreateDate,modelUpdateDate,modelCreateUser,modelUpdateUser")
				.toString());
	}

	/**
	 * 跳转新增页面
	 */
	@RequestMapping("/getModelManagementInsertPage")
	public String getInsertModelManagement() {
		return "system/vm/modelManagementSave";
	}

	/**
	 * 保存新增模型
	 */
	@RequestMapping("/saveModelManagement")
	public void insertModelManagement(@RequestParam("modelName") String modelName,
			@RequestParam("configKeys") String[] configKeys, @RequestParam("configValues") String[] configValues,
			@RequestParam("dictCnName") String dictCnName) throws UnsupportedEncodingException {
		Integer loginUserId = getLoginInfo().getUserId();
		// 最大排序号
		Integer maxNum = modelManagementService.queryModelMaxOrderNum();
		if (null == maxNum) {
			maxNum = 0;
		}
		dictCnName = URLDecoder.decode(dictCnName,"UTF-8");
		SysModelManagement sysModelManagement = new SysModelManagement();
		sysModelManagement.setDictCnName(dictCnName);
		sysModelManagement.setModelName(modelName);
		sysModelManagement.setModelOrderNum(maxNum + 1);
		modelManagementService.insertModelManagement(sysModelManagement, loginUserId, configKeys, configValues);

	}

	/**
	 * 删除模型和模型配置
	 * 
	 * @param modelId
	 */
	@RequestMapping("/deleteModel")
	public void deleteModelManagement(@RequestParam("modelId") Integer modelId) {
        Integer loginUser = getLoginInfo().getUserId();
        modelManagementService.deleteModelManagementById(modelId,loginUser);
	}

	/**
	 * 校验名字
	 * @param modelName
	 */
	@RequestMapping("checkModelNameRule")
	public void checkModelNameRule(@RequestParam("modelName") String modelName,
			@RequestParam(value = "modelId", required = false) Integer modelId) {
			
		if(modelId != null){
			SysModelManagement modelManagement = modelManagementService.getModelManagementById(modelId);
			if (null != modelManagement && modelName.equals(modelManagement.getModelName())) {
				renderText(true, "", null);
			}
		}
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("modelName", modelName);
		List<SysModelManagement> list = modelManagementService.queryModelManagementList(condition);

		// 如果数据库中存在相同的名字
		if (list.size() > 0) {
			renderText(false, "模型名称已经存在，请重新输入", null);
		} else {
			renderText(true, "", null);
		}
	}

	/**
	 * 跳转模型更新页面
	 * 
	 * @return
	 */
	@RequestMapping("/getUpdateModelManagementPage")
	public ModelAndView getUpdateModelManagementPage(@RequestParam("modelId") Integer modelId){
		ModelAndView mv = new ModelAndView("/system/vm/modelManagementUpdate");
		SysModelManagement modelManagement = modelManagementService.getModelManagementById(modelId);
		String modelName = modelManagement.getModelName();
		String dictCnName = modelManagement.getDictCnName();
		mv.addObject("dictCnName",dictCnName);
		mv.addObject("modelId", modelId);
		mv.addObject("modelName", modelName);
		return mv;

	}

	/**
	 * 更新模型和模型配置
	 */
	@RequestMapping("/updateModelManagement")
	public void updateModelManagementPage(@RequestParam("modelId") Integer modelId,
			@RequestParam("modelName") String modelName, @RequestParam("configIds") Integer[] configIds,
			@RequestParam("configValues") String[] configValues) {
		Integer loginUserId = getLoginInfo().getUserId();
		SysModelManagement modelManagement = modelManagementService.getModelManagementById(modelId);
		modelManagement.setModelName(modelName);
		modelManagementService.updateModelManagement(modelManagement, loginUserId, configIds, configValues);
	}

	/**
	 * 小弹框显示
	 * @param projectName
	 * @return
	 */
	@RequestMapping("/getShowPage")
	public ModelAndView getShowPage(@RequestParam("projectId") Integer projectId) throws UnsupportedEncodingException {
		ModelAndView mv = new ModelAndView("system/vm/showPage");
		SysMatchProject matchProjectById = matchProjectService.getMatchProjectById(projectId);
		mv.addObject("projectName",matchProjectById.getProjectName());
		mv.addObject("projectId",projectId);
		return mv;
	}

	@RequestMapping("/queryShowPage")
	public void queryShowPage(@RequestParam("projectId")Integer projectId){
		List<SysModelConfigExtend> list = modelManagementService.queryShowPage(projectId);
		renderText(JsonUtil
				.toJson(list, "modelId",
						"projectName,configKey,configValue,dictCnName")
				.toString());
	}
}
