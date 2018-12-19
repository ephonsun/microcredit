package banger.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import banger.domain.enumerate.MapConfigEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import banger.service.intf.IMapConfigService;
import banger.domain.track.MapConfig;
import org.springframework.web.bind.annotation.*;

/**
 * 地图配置表页面访问类
 */
@Controller
@RequestMapping("/mapConfig")
public class ConfigController extends BaseMapController {
	private static final long serialVersionUID = 2808410450130220842L;
	@Autowired
	private IMapConfigService mapConfigService;

	/**
	 * 得到地图配置表列表页面
	 * @return
	 */
	@RequestMapping("/getConfigPage")
	public String getConfigListPage(){
		setBaiduMapJs();		//设置ngnix 转发mapJs
		List<MapConfig> configList = mapConfigService.queryConfigList(new HashMap<String,Object>());
		for (MapConfig mapConfig : configList) {
			setAttribute(MapConfigEnum.getNameByValue(mapConfig.getConfigType()), mapConfig.getConfigValue());
		}
		return getBasePath("config") + "configPage";
	}
	/**
	 * 修改地图配置表数据
	 * @return
	 */
	@RequestMapping("/updateConfig")
	@ResponseBody
	public void updateConfig( @RequestParam(value = "lng", defaultValue = "")String lng,
								@RequestParam(value = "lat", defaultValue = "")String lat,
								@RequestParam(value = "zoom", defaultValue = "")String zoom){
		Integer loginUserId = getLoginInfo().getUserId();
		MapConfig config = new MapConfig();
		config.setId(MapConfigEnum.LNG.value);
		config.setConfigValue(lng);
		mapConfigService.saveConfig(config,loginUserId);
		config.setId(MapConfigEnum.LAT.value);
		config.setConfigValue(lat);
		mapConfigService.saveConfig(config,loginUserId);
		config.setId(MapConfigEnum.ZOOM.value);
		config.setConfigValue(zoom);
		mapConfigService.saveConfig(config,loginUserId);
		renderText(true,"","");
	}
}
