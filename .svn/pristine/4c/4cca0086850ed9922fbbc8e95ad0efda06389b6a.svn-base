package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.common.tools.StringUtil;
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
 * 	地图参数
 */
@Controller
@RequestMapping("/mapParamsSet")
public class MapParamsSetController extends BaseController {

	private static final long serialVersionUID = -3995140519364471215L;

	@Autowired
	private BasicConfigService basicConfigService;

	@RequestMapping("/mapParamsIndex")
	public String functionSwitchIndex(){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("isDel",0);
		condition.put("configType",4);//3表示地图参数记录
		List<SysBasicConfig> sysBasicConfigList =basicConfigService.querySysBasicConfigList(condition);
		for (SysBasicConfig basicConfig : sysBasicConfigList) {
			if ("qzdw".equals(basicConfig.getBasicConfigKey())) {
				setAttribute("qzdw",basicConfig);
			}else if("jwdcjzq".equals(basicConfig.getBasicConfigKey())){
				setAttribute("jwdcjzq",basicConfig);
			}else if("jwdsqzq".equals(basicConfig.getBasicConfigKey())){
				setAttribute("jwdsqzq",basicConfig);
			}else if("jwdyxsj".equals(basicConfig.getBasicConfigKey())){
				setAttribute("jwdyxsj",basicConfig);
			}
		}

		return "/system/vm/mapParamsSet";
	}

	/**
	 * 获取基础参数数据列表
	 */
	@RequestMapping("/mapParamsList")
	@ResponseBody
	public void queryMapParamsList(){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("isDel",0);
		condition.put("configType",3);//3表示地图参数记录
		List<SysBasicConfig> sysBasicConfigList =basicConfigService.querySysBasicConfigList(condition);
		renderText(JsonUtil.toJson(sysBasicConfigList, "basicConfigId","basicConfigName,basicConfigKey,configStatus,configValue,isDel,createUser,createDate,updateUser,updateDate,remarks").toString());

	}

	/**
	 * 根据对应id修改基础数据
	 */
	@RequestMapping("/updateMapParams")
	@ResponseBody
	public void updateMapParams(){
		String basicConfigId = getParameter("basicConfigId");
		String configStatus = getParameter("configStatus");
		String configValue = getParameter("configValue");
		SysBasicConfig basicConfig = new SysBasicConfig();
		basicConfig.setBasicConfigId(Integer.parseInt(basicConfigId));
		basicConfig.setConfigStatus(configStatus);
		if(StringUtil.isNotEmpty(configValue))
			basicConfig.setConfigValue(Integer.valueOf(configValue));
		Integer loginUserId = getLoginInfo().getUserId();
		basicConfigService.updateBasicConfig(basicConfig,loginUserId);
		renderText(true, "修改成功!", null);
	}


}
