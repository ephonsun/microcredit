package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.system.SysBasicConfig;
import banger.service.impl.BasicConfigService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 	功能开关
 */
@Controller
@RequestMapping("/functionSwitch")
public class FunctionSwitchController extends BaseController {

	private static final long serialVersionUID = -3995140519364471215L;

	@Autowired
	private BasicConfigService basicConfigService;

	@RequestMapping("/functionSwitchIndex")
	public String functionSwitchIndex(){
		return "/system/vm/functionSwitch";
	}

	/**
	 * 获取基础参数数据列表
	 */
	@RequestMapping("/functionSwitchList")
	@ResponseBody
	public void queryFunctionSwitchList(){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("isDel",0);
		condition.put("configType",2);
		List<SysBasicConfig> sysBasicConfigList =basicConfigService.querySysBasicConfigList(condition);
		renderText(JsonUtil.toJson(sysBasicConfigList, "basicConfigId","basicConfigName,basicConfigKey,configStatus,configValue,isDel,createUser,createDate,updateUser,updateDate,remarks").toString());

	}

	/**
	 * 根据对应id修改基础数据
	 */
	@RequestMapping("/updateFunctionSwitch")
	@ResponseBody
	public void updateFunctionSwitch(){
		String basicConfigId = getParameter("basicConfigId");
		String configStatus = getParameter("configStatus");
		SysBasicConfig basicConfig = new SysBasicConfig();
		basicConfig.setBasicConfigId(Integer.parseInt(basicConfigId));
		basicConfig.setConfigStatus(configStatus);
		Integer loginUserId = getLoginInfo().getUserId();
		basicConfigService.updateBasicConfig(basicConfig,loginUserId);
		renderText(true, "修改成功!", null);
	}
}
