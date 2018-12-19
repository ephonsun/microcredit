package banger.controller;

import banger.common.BaseController;
import banger.domain.system.SysMatchProject;
import banger.domain.system.SysModelManagement;
import banger.service.intf.IMatchProjectService;
import banger.service.intf.IModelManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/modelMatch")
public class ModelMatchProject extends BaseController {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IMatchProjectService matchProjectService;
	@Autowired
	private IModelManagementService modelManagementService;
	/**
	 * 跳转模型配置页面
	 */
	@RequestMapping("/getModelMatchProjectPage")
	public ModelAndView getModelMatchProjectPage() {
		Map<String, Object> condition = new HashMap<String, Object>();
		List<SysMatchProject> matchProjectList = matchProjectService.queryMatchProjectList(condition);
		List<SysModelManagement> modelManagementList = modelManagementService.queryModelManagementList(condition);
		
		ModelAndView mv = new ModelAndView("system/vm/modelMatchProject");
		mv.addObject("matchProjectList", matchProjectList);
		mv.addObject("modelManagementList", modelManagementList);
		return mv;
	}

	/**
	 *  保存模型配置
	 *
	 */
	@RequestMapping("/saveModelMatchProjectList")
	public void saveModelMatchProjectList(@RequestParam("projectIds") Integer[] projectIds,@RequestParam("modelIds") Integer[] modelIds,@RequestParam("modelNames") String[] modelNames) {
		Integer loginUserId = getLoginInfo().getUserId();
		for (int i = 0; i < projectIds.length; i++) {
			SysMatchProject matchProject = matchProjectService.getMatchProjectById(projectIds[i]);
			matchProject.setModelId(modelIds[i]);
			matchProject.setModelName(modelNames[i]);
			matchProjectService.updateMatchProject(matchProject,loginUserId);
		}

	}
}
