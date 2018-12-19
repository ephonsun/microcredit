package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.system.SysBasicConfig;
import banger.service.impl.BasicConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 	接口开关
 */
@Controller
@RequestMapping("/interfaceSwitch")
public class InterfaceSwitchController extends BaseController {

	private static final long serialVersionUID = -3995140519364471215L;

	@Autowired
	private BasicConfigService basicConfigService;

	@RequestMapping("/interfaceSwitchIndex")
	public String interfaceSwitchIndex(){
		return "/system/vm/interfaceSwitch";
	}

	/**
	 * 获取基础参数数据列表
	 */
	@RequestMapping("/interfaceSwitchList")
	@ResponseBody
	public void queryInterfaceSwitchList(){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("isDel",0);
		condition.put("configType",3);
		List<SysBasicConfig> sysBasicConfigList =basicConfigService.querySysBasicConfigList(condition);
		renderText(JsonUtil.toJson(sysBasicConfigList, "basicConfigId","basicConfigName,basicConfigKey,configStatus,configValue,isDel,createUser,createDate,updateUser,updateDate,remarks").toString());
	}
}
