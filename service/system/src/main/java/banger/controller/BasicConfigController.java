package banger.controller;

import banger.common.BaseController;
import banger.common.tools.StringUtil;
import banger.domain.enumerate.InterestUnitEnum;
import banger.domain.system.SysBasicConfig;
import banger.service.impl.BasicConfigService;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 	基础参数
 */
@Controller
@RequestMapping("/sysBasicConfig")
public class BasicConfigController extends BaseController {

	@Autowired
	private BasicConfigService basicConfigService;

	private static final long serialVersionUID = 929737245736874517L;

	@RequestMapping("/basicConfig")
	public ModelAndView sysBasicConfigIndex(){
		ModelAndView mv = new ModelAndView("/system/vm/sysBasicConfig");
		Map<String,Object> condition = new HashMap<String,Object>();
		List<SysBasicConfig> sysBasicConfigs = basicConfigService.querySysBasicConfigList(condition);

		for (SysBasicConfig sysBasicConfig : sysBasicConfigs) {
			if(StringUtils.equals(sysBasicConfig.getBasicConfigKey(),"jcdc")){
				mv.addObject("jcdc",sysBasicConfig);
			}
			if(StringUtils.equals(sysBasicConfig.getBasicConfigKey(),"cfsz")){
				mv.addObject("cfsz",sysBasicConfig);
			}
			if(StringUtils.equals(sysBasicConfig.getBasicConfigKey(),"scjk")){
				mv.addObject("scjk",sysBasicConfig);
			}
			if(StringUtils.equals(sysBasicConfig.getBasicConfigKey(),"cgjkzc")){
				mv.addObject("cgjkzc",sysBasicConfig);
			}
			if(StringUtils.equals(sysBasicConfig.getBasicConfigKey(),"cgjkgz")){
				mv.addObject("cgjkgz",sysBasicConfig);
			}
			if(StringUtils.equals(sysBasicConfig.getBasicConfigKey(),"qxsz")){
				mv.addObject("qxsz",sysBasicConfig);
			}
			if(StringUtils.equals(sysBasicConfig.getBasicConfigKey(),"lxdw")){
				mv.addObject("lxdw",sysBasicConfig);
			}
			if(StringUtils.equals(sysBasicConfig.getBasicConfigKey(),"sxh")){
				String  value = String.valueOf(sysBasicConfig.getConfigValue());
				StringBuffer sb = new StringBuffer(value);
				for(int i=0;i<9-value.length();i++){
					 sb.insert(0,"0");
				}
				mv.addObject("sxh",sb.toString());
			}
			if(StringUtils.equals(sysBasicConfig.getBasicConfigKey(),"lssxh")){
				String  value = String.valueOf(sysBasicConfig.getConfigValue());
				StringBuffer sb = new StringBuffer(value);
				for(int i=0;i<7-value.length();i++){
					sb.insert(0,"0");
				}
				mv.addObject("lssxh",sb.insert(0,"1").toString());
			}
		}
		return mv;
	}

	/**
	 * 更新基础参数
	 */
	@RequestMapping("/updateBasicConfig")
	public void updateBasicConfig(){
		Integer loginUserId = getLoginInfo().getUserId();

//		SysBasicConfig jcdc = basicConfigService.getBasicConfigById(Integer.valueOf(getParameter("jcdc_id")));
//		jcdc.setConfigValue(Integer.valueOf(getParameter("jcdc")));
//		basicConfigService.updateBasicConfig(jcdc,loginUserId);

		SysBasicConfig cfsz = basicConfigService.getBasicConfigById(Integer.valueOf(getParameter("cfsz_id")));
		cfsz.setConfigValue(Integer.valueOf(getParameter("cfsz")));
		basicConfigService.updateBasicConfig(cfsz,loginUserId);

		SysBasicConfig scjk = basicConfigService.getBasicConfigById(Integer.valueOf(getParameter("scjk_id")));
		scjk.setConfigValue(Integer.valueOf(getParameter("scjk")));
		basicConfigService.updateBasicConfig(scjk,loginUserId);

		SysBasicConfig cgjkzc = basicConfigService.getBasicConfigById(Integer.valueOf(getParameter("cgjkzc_id")));
		cgjkzc.setConfigValue(Integer.valueOf(getParameter("cgjkzc")));
		basicConfigService.updateBasicConfig(cgjkzc,loginUserId);

		SysBasicConfig cgjkgz = basicConfigService.getBasicConfigById(Integer.valueOf(getParameter("cgjkgz_id")));
		cgjkgz.setConfigValue(Integer.valueOf(getParameter("cgjkgz")));
		basicConfigService.updateBasicConfig(cgjkgz,loginUserId);

		SysBasicConfig qxsz = basicConfigService.getBasicConfigById(Integer.valueOf(getParameter("qxsz_id")));
		qxsz.setConfigValue(Integer.valueOf(getParameter("qxsz")));
		basicConfigService.updateBasicConfig(qxsz,loginUserId);

		SysBasicConfig lxdw = basicConfigService.getBasicConfigById(Integer.valueOf(getParameter("lxdw_id")));
		lxdw.setConfigValue(Integer.valueOf(getParameter("lxdw")));
		basicConfigService.updateBasicConfig(lxdw,loginUserId);
	}

//	/**
//	 * 获取基础参数数据列表
//	 */
//	@RequestMapping("/sysBasicConfigList")
//	@ResponseBody
//	public void querySysBasicConfigList(){
//		Map<String,Object> condition = new HashMap<String,Object>();
//		List<SysBasicConfig> sysBasicConfigList =basicConfigService.querySysBasicConfigList(condition);
//	}
//
//	/**
//	 * 获取修改的数据
//	 * @return
//	 */
//	@RequestMapping("/getBasicConfig")
//	public String getBasicConfig(){
//		this.setAttribute("basicConfigId", getParameter("basicConfigId"));
//		this.setAttribute("configValue", getParameter("configValue"));
//		return "/system/vm/sysBasicConfigUpdate";
//	}
//
//	/**
//	 * 根据对应id修改基础数据
//	 */
//	@RequestMapping("/updateBasicConfig")
//	@ResponseBody
//	public void updateSysBasicConfig(){
//		String basicConfigId = getParameter("basicConfigId");
//		String configValue = getParameter("configValue");
//		JSONObject resJson = new JSONObject();
//		resJson.put("success", true);
//		if (resJson.getBoolean("success")) {
//			SysBasicConfig basicConfig = new SysBasicConfig();
//			basicConfig.setBasicConfigId(Integer.parseInt(basicConfigId));
//			basicConfig.setConfigValue(Integer.parseInt(configValue));
//			Integer loginUserId = getLoginInfo().getUserId();
//			basicConfigService.updateBasicConfig(basicConfig,loginUserId);
//			renderText(true, "修改成功!", null);
//			renderText(resJson.toString());
//		}
//	}


	@RequestMapping("/getBasicDataPage")
	public String getBasicConfig(){
		return "/system/vm/sysBasicData";
	}
}
