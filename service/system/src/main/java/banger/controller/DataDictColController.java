package banger.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.config.AutoTableColumn;
import banger.domain.system.SysDataDict;
import banger.domain.system.SysDataDictCol;
import banger.moduleIntf.IConfigModule;
import banger.moduleIntf.ISystemModule;
import banger.service.intf.IDataDictColService;
import banger.service.intf.IDataDictService;
import net.sf.json.JSONObject;

/**
 * 数据字典字段表页面访问类
 */
@Controller
@RequestMapping("/sysDataDictCol")
public class DataDictColController extends BaseController {
	private static final long serialVersionUID = 1777378029183995695L;
	@Autowired
	private IDataDictColService dataDictColService;
	@Autowired
	private IDataDictService dataDictService;
	@Autowired
	private ISystemModule systemModuleImpl;
	@Autowired
	private IConfigModule configModule;

	/**
	 * 得到数据字典字段表列表页面
	 * @return
	 */
	@RequestMapping("/getDataDictColListPage")
	public String getDataDictColListPage(){
		Integer dataDictId = Integer.valueOf(getParameter("dataDictId"));
		SysDataDict dataDict = this.dataDictService.getDataDictById(dataDictId);
		setAttribute("dataDict", dataDict);
		return "/system/vm/dataDictColList";
	}
	
	
	/**
	 * 得到数据字典字段表列表页面
	 * @return
	 */
	@RequestMapping("/getDataDictColListPageByFieldId")
	public String getDataDictColListPageByFieldId(){
		Integer fieldId = Integer.valueOf(getParameter("fieldId"));
		AutoTableColumn tableColumn = configModule.getAutoFieldProvider().getTableColumnById(fieldId);
		SysDataDict dataDict = this.dataDictService.getDataDictByEnName(tableColumn.getFieldDictName());
		if(dataDict == null){
			Integer loginUserId = getLoginInfo().getUserId();
			dataDict = new SysDataDict();
			dataDict.setDictEnName(tableColumn.getFieldDictName());
			dataDict.setDictCnName(tableColumn.getFieldColumnDisplay());
			dataDict.setIsFixed(0);
			dataDict.setSysFlag(1);
			dataDict.setDelFlag(0);
			dataDictService.insertDataDict(dataDict, loginUserId);
		}
		setAttribute("dataDict", dataDict);
		return "/system/vm/dataDictColList";
	}
	

	/**
	 * 查询数据字典字段表列表数据
	 * @return
	 */
	@RequestMapping("/queryDataDictColListData")
	@ResponseBody
	public void queryDataDictColListData(){
		String dataDictId = getParameter("dataDictId");
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("dataDictId", Integer.parseInt(dataDictId));
		condition.put("delFlag", 0);
		List<SysDataDictCol> dataDictColList = dataDictColService.queryDataDictColList(condition);
		renderText(JsonUtil.toJson(dataDictColList, "dictColId","orderNo,dataDictName,createUser,createDate,dataDictId,dictName,dictCode,updateDate,updateUser,delFlag,dictValue,isFixed").toString());
	}

	/**
	 * 得到数据字典字段表新增页面
	 * @return
	 */
	@RequestMapping("/getDataDictColInsertPage")
	public String getDataDictColInsertPage(){
		Integer dataDictId = Integer.valueOf(getParameter("dataDictId"));
		SysDataDict dataDict = this.dataDictService.getDataDictById(dataDictId);
		setAttribute("dataDict", dataDict);
		return "/system/vm/dataDictColAdd";
	}

	/**
	 * 得到数据字典字段表修改页面
	 * @return
	 */
	@RequestMapping("/getDataDictColUpdatePage")
	public String getDataDictColUpdatePage(){
		String isShow = getParameter("isShow");
		Integer dictColId = Integer.valueOf(getParameter("dictColId"));
		SysDataDictCol dataDictCol = dataDictColService.getDataDictColById(dictColId);
		Integer dataDictId = Integer.valueOf(getParameter("dataDictId"));
		SysDataDict dataDict = this.dataDictService.getDataDictById(dataDictId);
		setAttribute("dataDictCol", dataDictCol);
		setAttribute("dataDict", dataDict);
		setAttribute("isShow", isShow);
		return "/system/vm/dataDictColUpdate";
	}

	/**
	 * 新增数据字典字段表数据
	 * @return
	 */
	@RequestMapping("/insertDataDictCol")
	@ResponseBody
	public void insertDataDictCol(){
		try {
			Integer dataDictId = Integer.parseInt(getParameter("dataDictId"));
			String dictName = getParameter("dictName");
			String dictCode = getParameter("dictCode");
			JSONObject resJson = dataDictColService.checkDictName(dictName, dataDictId,null);
			if (resJson.getBoolean("success")) {
				SysDataDict dataDict = dataDictService.getDataDictById(dataDictId);
				String dataDictName = null;
				if(dataDict != null)
					dataDictName = dataDict.getDictEnName();
				else {
					renderText(false, "查询数据源失败，请重试", null);
					return;
				}
				SysDataDictCol dataDictCol = new SysDataDictCol();
				Integer loginUserId = getLoginInfo().getUserId();
				dataDictCol.setDictName(dictName);
				dataDictCol.setDataDictName(dataDictName);
				dataDictCol.setDataDictId(dataDictId);
				dataDictCol.setDictCode(dictCode);
				dataDictCol.setIsFixed(0);
				
				//字典码在插入语句设置了 主键值
				dataDictColService.insertDataDictCol(dataDictCol, loginUserId,dataDictId);
				renderText(true, "新建成功！", null);
			}else {
				renderText(resJson.toString());
			}
			addSystemLog(1);
			return;
		} catch (Exception e) {
			log.error("insertDataDictCol error",e);
		}
		renderText(false, "新建失败！", null);
	}

	/**
	 * 修改数据字典字段表数据
	 * @return
	 */
	@RequestMapping("/updateDataDictCol")
	@ResponseBody
	public void updateDataDictCol(){
		try {
			String dataDictId = getParameter("dataDictId");
			String dictColId = getParameter("dictColId");
			String dictName = getParameter("dictName");
			String dictCode = getParameter("dictCode");
			JSONObject resJson = dataDictColService.checkDictName(dictName, Integer.parseInt(dataDictId),dictColId);
			if (resJson.getBoolean("success")) {
				SysDataDictCol dataDictCol = new SysDataDictCol();
				Integer loginUserId = getLoginInfo().getUserId();
				dataDictCol.setDictName(dictName);
				dataDictCol.setDictCode(dictCode);
				dataDictCol.setDictColId(Integer.parseInt(dictColId));
				dataDictColService.updateDataDictCol(dataDictCol, loginUserId);
				renderText(true, "修改成功!", null);
			} else {
				renderText(resJson.toString());
			}
			addSystemLog(2);
			return;
		} catch (Exception e) {
			log.error("updateDataDictCol error",e);
		}
		renderText(false, "修改失败！", null);
	}

	/**
	 * 删除数据字典字段表数据
	 * @return
	 */
	@RequestMapping("/deleteDataDictCol")
	@ResponseBody
	public void deleteDataDictCol(){
		try {
			String dictColId = getParameter("dictColId");
			SysDataDictCol dataDictCol = new SysDataDictCol();
			dataDictCol.setDelFlag(1);
			dataDictCol.setDictColId(Integer.parseInt(dictColId));
			Integer loginUserId = getLoginInfo().getUserId();
			dataDictColService.updateDataDictCol(dataDictCol, loginUserId);
			renderText(true, "删除成功！", null);
			addSystemLog(3);
			return;
		} catch (Exception e) {
			log.error("deleteDataDictCol error",e);
		}
		renderText(false, "删除失败！", null);
	}
	
	@RequestMapping("/checkDictNameRule")
	@ResponseBody
	public void checkDictNameRule() {
		String dictName = getParameter("dictName");
		String dataDictId = getParameter("dataDictId");
		String dictColId = getParameter("dictColId");
		JSONObject resJson = dataDictColService.checkDictName(dictName, Integer.parseInt(dataDictId),dictColId);
		renderText(resJson.toString());
	}

	@RequestMapping("/checkDictCodeRule")
	@ResponseBody
	public void checkDictCodeRule() {
		String dictCode = getParameter("dictCode");
		String dataDictId = getParameter("dataDictId");
		String dictColId = getParameter("dictColId");
		JSONObject resJson = dataDictColService.checkDictCode(dictCode, Integer.parseInt(dataDictId),dictColId);
		renderText(resJson.toString());
	}

	@RequestMapping("/moveDictOrderNo")
	@ResponseBody
	public void moveDictOrderNo(){
		String dictColId = getParameter("dictColId");
		String dataDictId = getParameter("dataDictId");
		String type = getParameter("type");
		if(StringUtils.isNotBlank(dictColId)&&StringUtils.isNotBlank(dataDictId)){
			dataDictColService.moveDictOrderNo(type, Integer.valueOf(dictColId), Integer.valueOf(dataDictId));
			this.renderText(SUCCESS);
			addSystemLog(4);
		}
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
				opeVentAction = "新建字典值";
				break;
			case 2:
				opeVentAction = "修改字典值";
				break;
			case 3:
				opeVentAction = "删除字典值";
				break;
			case 4:
				opeVentAction = "移动字典值";
				break;
			default:
				break;
		}
		// 新增系统日志
		// insert系统日志表   模块名称、操作对象名称、操作者userId、操作者ip地址
		this.systemModuleImpl.addSysOpeventLog("字典模块", opeVentAction, this.getLoginInfo().getUserId(), this.getLoginInfo().getIp());
	}

}
