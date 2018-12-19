package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.common.tools.StringUtil;
import banger.domain.config.AutoTableColumn;
import banger.domain.config.AutoTableInfo;
import banger.domain.config.SysFormSettings;
import banger.domain.enumerate.EnumFiledType;
import banger.domain.system.SysDataDictCol;
import banger.moduleIntf.ISystemModule;
import banger.service.intf.IFormSettingsService;
import banger.service.intf.ITableColumnService;
import banger.service.intf.ITableInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表单关联设置表页面访问类
 */
@Controller
@RequestMapping("/SysFormSettings")
public class FormSettingsController extends BaseController {
	private static final long serialVersionUID = 4057838030130810623L;
	@Autowired
	private IFormSettingsService formSettingsService;
	@Autowired
	private ITableInfoService tableInfoService;
	@Autowired
	private ITableColumnService tableColumnService;
	@Autowired
	private ISystemModule systemModule;

	private String BASIC_PATH = "config/vm/formSettings/";

	/**
	 * 得到表单关联设置表列表页面
	 * @return
	 */
	@RequestMapping("/getFormSettingsListPage")
	public String getFormSettingsListPage(){

		return BASIC_PATH + "formSettingsList";
	}

	/**
	 * 查询表单关联设置表列表数据
	 * @return
	 */
	@RequestMapping("/queryFormSettingsListData")
	@ResponseBody
	public void queryFormSettingsListData(){
		Map<String,Object> condition = new HashMap<String,Object>();
		List<SysFormSettings> formSettingsList = formSettingsService.queryFormSettingsList(condition);
		renderText(JsonUtil.toJson(formSettingsList, "id","controlForm,controlFormName,controlItem,controlItemName,controlValue,controlValueName,hiddenForm,hiddenFormName,createUser,createDate,updateUser,updateDate").toString());
	}

	/**
	 * 查询表单关联设置表列表数据
	 * @return
	 */
	@RequestMapping("/queryFormSettingsListForLoan")
	@ResponseBody
	public void queryFormSettingsListForLoan(){
		Map<String,Object> condition = new HashMap<String,Object>();
		List<SysFormSettings> formSettingsList = formSettingsService.queryFormSettingsList(condition);
		renderText(JsonUtil.toJson(formSettingsList, "controlForm","controlForm,controlFormName,controlItemColumn,controlItem,controlItemName,controlValue,controlValueName,hiddenForm,hiddenFormName,createUser,createDate,updateUser,updateDate").toString());
	}

	/**
	 * 得到表单关联设置表新增页面
	 * @return
	 */
	@RequestMapping("/getFormSettingsInsertPage")
	public String getFormSettingsInsertPage(){
		Map<String,Object> condition = new HashMap<String,Object>();
		//控制表单信息
		List<AutoTableInfo> tableInfoList = tableInfoService.queryControlFormList();
		setAttribute("tableInfoList",tableInfoList);
		return BASIC_PATH + "formSettingsSave";
	}

	/**
	 * 获取控制项
	 */
	@RequestMapping("/getFormControlItem")
	public void getFormControlItem(@RequestParam String controlForm){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("tableName",controlForm);
		List<AutoTableColumn> tableColumnList = tableColumnService.queryTableColumnList(condition);
		//过滤出下拉框类型字段
		List<AutoTableColumn> selectColumns = new ArrayList<AutoTableColumn>();
		for(AutoTableColumn column : tableColumnList){
			if(StringUtils.equals(column.getFieldType(), EnumFiledType.SELECT.fieldType) && !isConfontColumn(column)){
				//转换成驼峰属性
//				column.setFieldColumn(StringUtil.camelName(column.getFieldColumn()));
				selectColumns.add(column);
			}
		}
		renderText(JsonUtil.toJson(selectColumns, "fieldId","fieldColumn,fieldDictName,fieldColumnDisplay").toString());
	}

	/**
	 * 过滤特定类型
	 * @param atc
	 * @return
	 */
	private boolean isConfontColumn(AutoTableColumn atc){
		String[] columns = {"IDENTIFY_TYPE","SPOUSE_IDENTIFY_TYPE"};
		for(int i = 0;i<columns.length;i++){
			if(StringUtils.equals(atc.getFieldColumn(),columns[i])){
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取控制值
	 * @param controlItem
	 */
	@RequestMapping("/getFormControlValue")
	public void getFormControlValue(@RequestParam String controlItem){
		if(StringUtils.isNotBlank(controlItem)){
			List<SysDataDictCol> sysDataDictCols = systemModule.queryDataDictColListByName(controlItem);
			renderText(JsonUtil.toJson(sysDataDictCols, "dictColId","dictValue,dictName").toString());
		}
	}

	/**
	 * 获取隐藏表单
	 * @param controlForm 控制表单
	 */
	@RequestMapping("/getHiddenForm")
	public void getHiddenForm(@RequestParam String controlForm){
		List<AutoTableInfo> hiddenForm = tableInfoService.queryHiddenFormList(controlForm);
		renderText(JsonUtil.toJson(hiddenForm, "tableId","tableDbName,tableDisplayName").toString());
	}

	/**
	 * 得到表单关联设置表修改页面
	 * @return
	 */
	@RequestMapping("/getFormSettingsUpdatePage")
	public String getFormSettingsUpdatePage(){
		String id = getParameter("id");
		Map<String,Object> condition = new HashMap<String,Object>();
		SysFormSettings formSettingsEdit = formSettingsService.getFormSettingsById(Integer.parseInt(id));
		List<AutoTableInfo> tableInfoList = tableInfoService.queryControlFormList();
		setAttribute("tableInfoList",tableInfoList);
		setAttribute("formSettingsEdit",formSettingsEdit);
		setAttribute("id",id);
		return BASIC_PATH + "formSettingsEdit";
	}

	/**
	 * 得到表单关联设置表查看页面
	 * @return
	 */
	@RequestMapping("/getFormSettingsDetailPage")
	public String getFormSettingsDetailPage(){
		String id = getParameter("id");
		SysFormSettings formSettings = formSettingsService.getFormSettingsById(Integer.parseInt(id));
		setAttribute("formSettings",formSettings);
		return SUCCESS;
	}

	/**
	 * 新增表单关联设置表数据
	 * @return
	 */
	@RequestMapping("/insertFormSettings")
	public void insertFormSettings(@ModelAttribute SysFormSettings formSettings){
		try{
			Integer loginUserId = getLoginInfo().getUserId();
			//判断在数据库中是否已经存在相同的控制项
			if(isAlikeFormSettings(formSettings)){
				renderText(false,"已存在相同的控制项","");
			}else{
				formSettingsService.insertFormSettings(formSettings,loginUserId);
				renderText(true, "保存成功", "");
			}
		}catch (Exception e){
			renderText(false,"参数错误","");
		}
	}

	/**
	 * 修改表单关联设置表数据
	 * @return
	 */
	@RequestMapping("/updateFormSettings")
	public void updateFormSettings(@ModelAttribute SysFormSettings formSettings){
		try{
			String id = getRequest().getParameter("id");
			SysFormSettings sysFormSettings = formSettingsService.getFormSettingsById(Integer.parseInt(id));
			Integer loginUserId = getLoginInfo().getUserId();
			//判断在数据库中是否已经存在相同的控制表单、控制项和隐藏表单
			if(isAlikeFormSettings(formSettings)&&!StringUtils.equals(sysFormSettings.getControlItem(),formSettings.getControlItem())){
				renderText(false,"已存在相同的控制项","");
			}else{
				formSettingsService.updateFormSettings(formSettings,loginUserId);
				renderText(true, "保存成功", "");
			}
		}catch (Exception e){
			renderText(false,"参数错误","");
		}
	}

	/**
	 * 判断在数据库中是否已经存在相同的控制表单、控制项和隐藏表单
	 * @param formSettings
	 * @return
	 */
	private boolean isAlikeFormSettings(SysFormSettings formSettings){
		Map<String,Object> condition = new HashMap<String,Object>();
		List<SysFormSettings> lists = formSettingsService.queryFormSettingsList(condition);
		for(SysFormSettings form : lists){
			if(StringUtils.equals(form.getControlItem(),formSettings.getControlItem())){
//				return true;
			}
		}
		return false;
	}

	/**
	 * 删除表单关联设置表数据
	 * @return
	 */
	@RequestMapping("/deleteFormSettings")
	@ResponseBody
	public void deleteFormSettings(){
		try{
			String id = getParameter("id");
			formSettingsService.deleteFormSettingsById(Integer.parseInt(id));
			renderText(true, "删除成功!", null);
			return ;
		}catch (Exception e){
			log.error("deleteFormSettings error",e);
		}
		renderText(false,"删除失败！",null);
	}

}
