package banger.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.system.SysDataDict;
import banger.moduleIntf.ISystemModule;
import banger.service.intf.IDataDictService;
import net.sf.json.JSONObject;

/**
 * 系统数据字典表页面访问类
 */
@Controller
@RequestMapping("/sysDataDict")
public class DataDictController extends BaseController {
	private static final long serialVersionUID = 2205564697623549928L;
	@Autowired
	private IDataDictService dataDictService;
	@Autowired
	private ISystemModule systemModuleImpl;
	
	@RequestMapping("/getDataDictManagePage")
	public String getDataDictManagePage() {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("delFlag", 0);
		condition.put("sysFlag", 0);
		String dataDictTreeJson = dataDictService.queryDataDictTree(condition);
		if(StringUtils.isNotBlank(dataDictTreeJson))
			setAttribute("dataDictTreeJson", dataDictTreeJson);
		return "/system/vm/dataDictManage";
	}

	/**
	 * 得到系统数据字典表列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/getDataDictListPage")
	public String getDataDictListPage(Model model) {
		return "/system/vm/dataDictList";
	}

	/**
	 * 查询系统数据字典表列表数据
	 * 
	 * @return
	 */
	@RequestMapping("/queryDataDictListData")
	@ResponseBody
	public void queryDataDictListData() {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("delFlag", 0);
		List<SysDataDict> dataDictList = dataDictService.queryDataDictList(condition);
		renderText(JsonUtil.toJson(dataDictList, "dataDictId","updateUser,dictCnName,createDate,dictEnName,updateDate,sysFlag,delFlag,createUser,isFixed").toString());
	}

	/**
	 * 新增系统数据字典表数据
	 * 
	 * @return
	 */
	@RequestMapping("/insertDataDict")
	@ResponseBody
	public void insertDataDict(SysDataDict dataDict) {
		try {
			JSONObject resCnJson = dataDictService.checkDataCnName(dataDict);
			if (resCnJson.getBoolean("success")) {
				Integer loginUserId = getLoginInfo().getUserId();
				dataDict.setDictEnName(dataDictService.getDataEnName(dataDict.getDictCnName(), null, 0));
				dataDict.setDelFlag(0);
				dataDictService.insertDataDict(dataDict, loginUserId);
				renderText(true, "新建成功！", null);
			} else {
				renderText(resCnJson.toString());
			}
			addSystemLog(1);
			return;
		} catch (Exception e) {
			log.error("insertDataDict error",e);
		}
		renderText(false,"新建失败！",null);
	}

	/**
	 * 修改系统数据字典表数据
	 * 
	 * @return
	 */
	@RequestMapping("/updateDataDict")
	@ResponseBody
	public void updateDataDict(SysDataDict dataDict) {
		try {
			JSONObject resCnJson = dataDictService.checkDataCnName(dataDict);
			if (resCnJson.getBoolean("success")) {
				Integer loginUserId = getLoginInfo().getUserId();
				dataDictService.updateDataDict(dataDict, loginUserId);
				renderText(true, "修改成功!", null);
			} else {
				renderText(resCnJson.toString());
			}
			addSystemLog(2);
			return;
		} catch (Exception e) {
			log.error("updateDataDict error",e);
		}
		renderText(false,"修改失败！",null);
	}

	/**
	 * 删除系统数据字典表数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "/deleteDataDict", method = RequestMethod.POST)
	@ResponseBody
	public void deleteDataDict() {
		try {
			String dataDictId = getParameter("dataDictId");
			SysDataDict dataDict = dataDictService.getDataDictById(Integer.parseInt(dataDictId));
			boolean success = false;
			String cause = "删除失败！";
			if (dataDict != null && dataDict.getIsFixed() == 0) {
				dataDict.setDelFlag(1);
				Integer loginUserId = getLoginInfo().getUserId();
				dataDictService.deleteDataDictById(dataDict, loginUserId);
				success = true;
				cause = "删除成功！";
			} else if (dataDict != null && dataDict.getSysFlag() == 1) {
				cause = "系统值无法删除！";
			}
			renderText(success, cause, null);
			addSystemLog(3);
			return;
		} catch (Exception e) {
			log.error("deleteDataDict error",e);
		}
		renderText(false, "删除失败！",null);
	}

	/**
	 * 得到系统数据字典表新增页面
	 * 
	 * @return
	 */
	@RequestMapping("/getDataDictInsertPage")
	public String getDataDictInsertPage() {
		return "/system/vm/dataDictAdd";
	}

	/**
	 * 得到系统数据字典表修改页面
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/getDataDictUpdatePage")
	public String getDataDictUpdatePage() {
		String dataDictId = getParameter("dataDictId");
		SysDataDict dataDict = dataDictService.getDataDictById(Integer.valueOf(dataDictId));
		setAttribute("dataDict", dataDict);
		return "/system/vm/dataDictUpdate";
	}

	/**
	 * 检验数据源中文名称
	 * 
	 * @return
	 */
	@RequestMapping("/checkDataCnNameRule")
	@ResponseBody
	public void checkDataCnNameRule(SysDataDict dataDict) {
		JSONObject resJson = dataDictService.checkDataCnName(dataDict);
		renderText(resJson.toString());
	}
	
	/**
	 * 检验数据源编码
	 * 
	 * @return
	 */
	@RequestMapping("/checkDataEnNameRule")
	@ResponseBody
	public void checkDataEnNameRule(SysDataDict dataDict) {
		JSONObject resJson = dataDictService.checkDataEnName(dataDict);
		renderText(resJson.toString());
	}
	
	/**
	 * 添加系统日志
	 *
	 * @param type
	 */
	private void addSystemLog(int type) {
		String opeVentAction = null;
		switch (type) {
			case 1:
				opeVentAction = "新增数据源";
				break;
			case 2:
				opeVentAction = "修改数据源";
				break;
			case 3:
				opeVentAction = "删除数据源";
				break;
			default:
				break;
		}
		// 新增系统日志
		// insert系统日志表   模块名称、操作对象名称、操作者userId、操作者ip地址
		this.systemModuleImpl.addSysOpeventLog("数据源模块", opeVentAction, this.getLoginInfo().getUserId(), this.getLoginInfo().getIp());
	}

}
