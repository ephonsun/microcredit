package banger.controller;

import banger.common.BaseController;
import banger.common.constant.GlobalConst;
import banger.common.tools.JsonUtil;
import banger.common.tools.StringUtil;
import banger.domain.config.*;
import banger.domain.enumerate.EnumFiledType;
import banger.domain.system.SysDataDict;
import banger.framework.codetable.CodeTableUtil;
import banger.framework.codetable.ICodeTable;
import banger.framework.pagesize.IPageList;
import banger.framework.util.FileUtil;
import banger.moduleIntf.IDataDictProvider;
import banger.service.intf.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 页面访问类
 */
@Controller
@RequestMapping("/tableColumn")
public class TableColumnController extends BaseController {
	private static final long serialVersionUID = 6010212355093047317L;
	@Autowired
	private ITableColumnService tableColumnService;
	@Autowired
	private ITableSyncService tableSyncService;
	@Autowired
	private IDataDictProvider dataDictProvider;
	@Autowired
	private ITemplateFieldService templateFieldService;
	@Autowired
	private ITableInfoService tableInfoService;
	@Autowired
	private ITableColumnInsert tableColumnInsert;
	private static final String EXPORT_FILE_NAME = "TableColumnAll";
	/**
	 * 得到列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/getTableColumnListPage")
	public String getTableColumnListPage(@RequestParam Integer tableId) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("tableId", tableId);
		AutoTableInfo tableInfoById = tableInfoService.getTableInfoById(tableId);
		IPageList<AutoTableColumn> tableColumnList = tableColumnService.queryTableColumnList(condition, this.getPage());
		setAttribute("loginUserName",getLoginInfo().getUserName());
		setAttribute("tableDisplayName",tableInfoById.getTableDisplayName());
		setAttribute("tableType",tableInfoById.getTableType());
		setAttribute("tableColumnList", tableColumnList);
		setAttribute("tableName", tableInfoById.getTableDbName());
		setAttribute("tableId", tableId);
		return "/config/vm/tableColumnInfoList";
	}

	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:
	 * @Date: 16:38 2017/9/11
	 */
	@RequestMapping("/getDefaultValueByFieldDictName")
	public String getDefaultValueByFieldDictName(){
		String dictName = getParameter("dictName");
		List<AutoBaseOption> list = getAutoBaseOptionByName(dictName);
		renderText(JsonUtil.toJson(list, "id","value,name").toString());
		return null;
	}

	//获取自动表的列
	private List<AutoBaseOption> getAutoBaseOptionByName(String dictName){
		List<AutoBaseOption> list = new ArrayList<AutoBaseOption>();
		if (StringUtil.isNotEmpty(dictName)) {
			List<ICodeTable.IItem> items = CodeTableUtil.getCodeTable("cdDictColByName", dictName);
			for(ICodeTable.IItem item : items){
				list.add(new AutoBaseOption(item.getValue(),item.getName()));
			}
		}
		return list;
	}
	/**
	 * 查询列表数据
	 * 
	 * @return
	 */
	@RequestMapping("/queryTableColumnListData")
	public void queryTableColumnListData(@RequestParam Integer tableId) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("tableId", tableId);
		IPageList<AutoTableColumn> tableColumnList = tableColumnService.queryTableColumnList(condition, this.getPage());
		for (AutoTableColumn column : tableColumnList) {
			if (StringUtil.isNotEmpty(column.getDefaultValue())) {
				if("Text".equals(column.getFieldType() ) || "Decimal".equals(column.getFieldType() ) || "Number".equals(column.getFieldType())
						|| "Float".equals(column.getFieldType()) || "Ratio".equals(column.getFieldType())) {
					//列表直接显示
				}else {
					if ("YesNo".equals(column.getFieldType())) {
						column.setDefaultValue(column.getDefaultValue().equals("1") ? "是" : "否");
					} else {
						List<ICodeTable.IItem> items = CodeTableUtil.getCodeTable("cdDictColByName", column.getFieldDictName());
						if ("MultipleSelect".equalsIgnoreCase(column.getFieldType())) {
							//多选
							String defaultValues = column.getDefaultValue();
							String[] split = defaultValues.split(",");
							String strDefault = "";
							for (int i = 0; i < split.length; i++) {
								for (ICodeTable.IItem item : items) {
									if (split[i].equals(item.getValue())) {
										strDefault += item.getName() + ",";
									}
								}
							}
							if (StringUtil.isNotEmpty(strDefault)) {
								column.setDefaultValue(strDefault.substring(0, strDefault.length() - 1));
							}
						} else {
							//单选
							for (ICodeTable.IItem item : items) {
								if (column.getDefaultValue().equals(item.getValue())) {
									column.setDefaultValue(item.getName());
									break;
								}
							}
						}
					}
				}
			}
		}
		renderText(JsonUtil
				.toJson(tableColumnList, "fieldId",
						"tableId,tableName,fieldName,fieldType,defaultValue,fieldNumberUnit,fieldConstraintRule,fieldColumn,fieldColumnDisplay,fieldAppIsShow,fieldWebIsShow,fieldIsQuery,fieldIsRequired,isActived,isFixed,fieldDictName,createDate,updateDate,createUser,updateUser")
				.toString());
	}

	/**
	 * 启用自定义字段
	 * 
	 * @return
	 */
	@RequestMapping("/enable/{tableId}/{filedId}")
	public void enableTableTemplate(@PathVariable Integer tableId, @PathVariable Integer filedId) {
		Integer loginUserId = getLoginInfo().getUserId();
		AutoTableColumn tableColumnInfo = new AutoTableColumn();
		tableColumnInfo.setTableId(tableId);
		tableColumnInfo.setFieldId(filedId);
		tableColumnInfo.setIsActived(GlobalConst.YesOrNo.YES);
		tableColumnService.updateTableColumn(tableColumnInfo, loginUserId);
		renderText(SUCCESS);
	}

	/**
	 * 禁用自定义字段
	 * 
	 * @return
	 */
	@RequestMapping("/disable/{tableId}/{filedId}")
	public void disableTableTemplate(@PathVariable Integer tableId, @PathVariable Integer filedId) {
		Integer loginUserId = getLoginInfo().getUserId();
		AutoTableColumn tableColumnInfo = new AutoTableColumn();
		tableColumnInfo.setTableId(tableId);
		tableColumnInfo.setFieldId(filedId);
		tableColumnInfo.setIsActived(GlobalConst.YesOrNo.NO);
		tableColumnService.updateTableColumn(tableColumnInfo, loginUserId);
		renderText(SUCCESS);
	}

	/**
	 * 得到新增页面
	 * 
	 * @return
	 */
	@RequestMapping("/getTableColumnInsertPage")
	public String getTableColumnInsertPage() {
		AutoTableColumn tableColumn = new AutoTableColumn();
		setAttribute("tableColumn", tableColumn);
		return SUCCESS;
	}

	/**
	 * 得到新增页面
	 * 
	 * @return
	 */
	@RequestMapping("/syncCustTableToLoadTable")
	public String syncCustTableToLoadTable() {
		tableSyncService.syncAllTableColumns();
		renderText(SUCCESS);
		return null;
	}

	/**
	 * 得到新增页面
	 * 
	 * @return
	 */
	@RequestMapping("/refreshTableToField")
	public String refreshTableToField() {
		tableSyncService.refreshAllTableToField();
		renderText(SUCCESS);
		return null;
	}


	/**
	 * 得到修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/getTableColumnUpdatePage")
	public String getTableColumnUpdatePage() {
		String fieldId = getParameter("fieldId");
		AutoTableColumn tableColumn = tableColumnService.getTableColumnById(Integer.parseInt(fieldId));
		// 遍历枚举
//		FieldConstraintRule[] values = FieldConstraintRule.values();
//		List<String> fieldConstraintRuleList = new ArrayList<String>();
		// 添加取消选项
//		fieldConstraintRuleList.add("");
//		for (int i = 0; i < values.length; i++) {
//			fieldConstraintRuleList.add(values[i].typeName);
//		}
//		setAttribute("fieldConstraintRuleList", fieldConstraintRuleList);
		if(StringUtil.isNotEmpty(tableColumn.getFieldDictName())){
			List<SysDataDict> AllList = dataDictProvider.getSysDataDictListByDictName("");
			setAttribute("AllList",AllList);
			List<AutoBaseOption> options = getAutoBaseOptionByName(tableColumn.getFieldDictName());
			setAttribute("options",options);

		}

		if (tableColumn != null && tableColumn.getFieldLength() != null) {
			if("Number".equals(tableColumn.getFieldType())){
				setAttribute("fieldLength", tableColumn.getFieldLength());
			}else{
				setAttribute("fieldLength", tableColumn.getFieldLength()/3);
			}
		}
		setAttribute("tableColumn", tableColumn);
		return "/config/vm/tableColumnInfoUpdate";
	}

	/**
	 * 得到查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/getTableColumnDetailPage")
	public String getTableColumnDetailPage() {
		String fieldId = getParameter("fieldId");
		AutoTableColumn tableColumn = tableColumnService.getTableColumnById(Integer.parseInt(fieldId));
		setAttribute("tableColumn", tableColumn);
		return SUCCESS;
	}

	/**
	 * 新增数据
	 * 
	 * @return
	 */
	@RequestMapping("/insertTableColumn")
	public String insertTableColumn(@ModelAttribute AutoTableColumn tableColumn) {
		Integer loginUserId = getLoginInfo().getUserId();
		tableColumnService.insertTableColumn(tableColumn, loginUserId);
		renderText(SUCCESS);
		return null;
	}

	/**
	 * 修改数据
	 * 
	 * @return
	 */
	@RequestMapping("/updateTableColumn")
	public String updateTableColumn(@ModelAttribute AutoTableColumn tableColumn) {
		Integer loginUserId = getLoginInfo().getUserId();
//		String fieldDictName = getParameter("fieldDictName");
//		if(StringUtil.isNotEmpty(fieldDictName)){
//
//		}
		//同步修改评分项字段表

		String tablecolumn = tableColumnService.getTableColumnById(tableColumn.getFieldId()).getFieldColumn();
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("talbeColumn",tablecolumn);
		List<ModeTemplateField> modeTemplateFields = templateFieldService.queryTemplateFieldList(condition);
		if(modeTemplateFields != null && modeTemplateFields.size() > 0){
			ModeTemplateField modeTemplateField = modeTemplateFields.get(0);
			String name = modeTemplateField.getFieldName();
			String display = modeTemplateField.getFieldDisplay();
			if(StringUtils.equals(name,display)){
				modeTemplateField.setFieldName(tableColumn.getFieldColumnDisplay());
				modeTemplateField.setFieldDisplay(tableColumn.getFieldColumnDisplay());
			}else{
				modeTemplateField.setFieldName(tableColumn.getFieldColumnDisplay());
			}
			templateFieldService.updateTemplateField(modeTemplateField,loginUserId);
		}

		tableColumnService.updateTableColumn(tableColumn, loginUserId);
		renderText(SUCCESS);
		return null;
	}

	/**
	 * 删除数据
	 * 
	 * @return
	 */
	@RequestMapping("/deleteTableColumn")
	public String deleteTableColumn() {
		String fieldId = getParameter("fieldId");
		tableColumnService.deleteTableColumnById(Integer.parseInt(fieldId));
		renderText(SUCCESS);
		return null;
	}

	/**
	 * 上移下移
	 */
	@RequestMapping("/moveTableColumnOrderNo")
	@ResponseBody
	public void moveTableInfoOrderNo(@RequestParam("tableId") Integer tableId, @RequestParam("type") String type,
			@RequestParam("fieldId") Integer fieldId) {
		tableColumnService.moveTableColumnOrderNo(type, tableId, fieldId);
		this.renderText(SUCCESS);
	}

	/**
	 * 是否必填
	 * 
	 *
	 * @param required
	 */
	@RequestMapping("/changeRequired")
	public void changeRequired(@RequestParam("fieldId") Integer fieldId, @RequestParam("required") Boolean required) {
		Integer loginUserId = getLoginInfo().getUserId();
		AutoTableColumn tableColumnInfo = new AutoTableColumn();
		tableColumnInfo.setFieldIsRequired(required ? 1 : 0);
		tableColumnInfo.setFieldId(fieldId);
		tableColumnService.updateTableColumn(tableColumnInfo, loginUserId);
		renderText(SUCCESS);
	}

	/**
	 * app是否显示
	 * 
	 *
	 * @param appIsShow
	 */
	@RequestMapping("/changeApp")
	public void changeApp(@RequestParam("fieldId") Integer fieldId, @RequestParam("appIsShow") Boolean appIsShow) {
		Integer loginUserId = getLoginInfo().getUserId();
		AutoTableColumn tableColumnInfo = new AutoTableColumn();
		tableColumnInfo.setFieldAppIsShow(appIsShow ? 1 : 0);
		tableColumnInfo.setFieldId(fieldId);
		tableColumnService.updateTableColumn(tableColumnInfo, loginUserId);
		renderText(SUCCESS);
	}

	/**
	 * web是否显示
	 * 
	 */
	@RequestMapping("/changeWeb")
	public void changeWeb(@RequestParam("fieldId") Integer fieldId, @RequestParam("webIsShow") Boolean webIsShow) {
		Integer loginUserId = getLoginInfo().getUserId();
		AutoTableColumn tableColumnInfo = new AutoTableColumn();
		tableColumnInfo.setFieldWebIsShow(webIsShow ? 1 : 0);
		tableColumnInfo.setFieldId(fieldId);
		tableColumnService.updateTableColumn(tableColumnInfo, loginUserId);
		renderText(SUCCESS);
	}

	/**
	 * 校验字段显示名称
	 * 
	 */
	@RequestMapping("checkfieldColumnNameRule")
	@ResponseBody
	public void checkfieldColumnNameRule(@RequestParam("fieldColumnDisplay") String fieldColumnDisplay,
			@RequestParam("fieldId") Integer fieldId) {

		Map<String, Object> condition = new HashMap<String, Object>();

		AutoTableColumn tableColumnById = tableColumnService.getTableColumnById(fieldId);
		//没有修改
		if (null != tableColumnById && fieldColumnDisplay.equals(tableColumnById.getFieldColumnDisplay())) {
			renderText(true, "", null);
		}
		//tableId
		Integer tableId = tableColumnById.getTableId();
		condition.put("tableId",tableId);
		condition.put("fieldColumnDisplay", fieldColumnDisplay);
		List<AutoTableColumn> list = tableColumnService.queryTableColumnList(condition);
		if (null == list) {
			return;
		}
		// 如果数据库中存在相同的名字
		if (list.size() > 0) {
			renderText(false, "字段显示名称已经存在，请重新输入", null);
		} else {
			renderText(true, "", null);
		}

	}

	/**
	 * 新增自定义字段页面
	 *
	 *
	 *
	 */
	@RequestMapping("/getTemplateFieldInsertPage")
	public String getTemplateFieldInsertPage(HttpServletRequest request, @RequestParam("typeIds") Integer typeIds ) {
		request.setAttribute("filedTypes",getFieldTypeList());
		request.setAttribute("typeId",typeIds);
		return  "config/vm/templateFieldInsert";
	}
	/**
	 * 新增自定义字段
	 *
	 *
	 *
	 */
	@RequestMapping("/insertTemplateField")
	@ResponseBody
	public  void insertTemplateField(HttpServletRequest request,
									 @RequestParam("fielConstraintRule") String fielConstraintRule,
									 @RequestParam("texts") String texts ,
			                         @RequestParam("codeData") String codeData,
									 @RequestParam("typeId") String typeId,
									 @RequestParam("saveType") String saveType,
									 @RequestParam("fieldColumnDisplay")String fieldColumnDisplay,
									 @RequestParam("fieldType") String fieldType,
									 @RequestParam("fieldColumn") String fieldColumn,
									 @RequestParam("fieldNumberUnit") String fieldNumberUnit,
									 @RequestParam("fieldIsRequired") String fieldIsRequired,
									 @RequestParam("fieldWebIsShow") String fieldWebIsShow,
									 @RequestParam("fieldAppIsShow") String fieldAppIsShow,
									 @RequestParam("fieldisplay") String fieldisplay,
									 @RequestParam("fieldQuery") String fieldQuery)  {

		Integer loginUserId = this.getLoginInfo().getUserId();

		AutoTableColumn autoTableColumn = new AutoTableColumn();

		autoTableColumn.setTableId(Integer.parseInt(typeId));
		autoTableColumn.setFieldName(fieldColumnDisplay);
		autoTableColumn.setFieldColumnDisplay(fieldColumnDisplay);
		autoTableColumn.setFieldConstraintRule(fielConstraintRule);
		autoTableColumn.setFieldType(fieldType);
		autoTableColumn.setFieldColumn(fieldColumn.toUpperCase());
		autoTableColumn.setFieldNumberUnit(fieldNumberUnit);
		autoTableColumn.setFieldIsRequired(Integer.parseInt(fieldIsRequired));
		autoTableColumn.setFieldWebIsShow(Integer.parseInt(fieldWebIsShow));
		autoTableColumn.setFieldAppIsShow(Integer.parseInt(fieldAppIsShow));
		autoTableColumn.setIsActived(Integer.parseInt(fieldisplay));
		autoTableColumn.setFieldIsQuery(0);
		autoTableColumn.setCreateUser(1);
		autoTableColumn.setCreateDate(new Date());
		autoTableColumn.setUpdateUser(1);
		autoTableColumn.setUpdateDate(new Date());
		autoTableColumn.setColumnModule("LOAN");
		autoTableColumn.setPopupUrl("");
		if ("Select".equals(fieldType) || "MultipleSelect".equals(fieldType)){
			autoTableColumn.setFieldDictName("CD_"+fieldColumn.toUpperCase());
		}
		if ("".equals(texts)){
			for (EnumFiledType filedType : EnumFiledType.values()) {
				if (fieldType.equals(filedType.fieldType)){
					autoTableColumn.setFieldLength(filedType.dataLength);
				}
			}
		}else {
			autoTableColumn.setFieldLength(Integer.parseInt(texts));
		}
		try {
			tableColumnInsert.insertTemplateField(autoTableColumn,loginUserId);
		} catch (Exception e) {
			e.printStackTrace();
			this.renderText(false,e.getMessage(),null);
			return;
		}

		renderText(true, "", null);
	}
	/**
	 * 根据typeId校验字段显示名称,列名
	 */
	@RequestMapping("checkfieldColumnNameByTableId")
	@ResponseBody
	public void checkfieldColumnNameByTableId(@RequestParam("fieldColumnDisplay") String fieldColumnDisplay,
										 @RequestParam("tableId") Integer tableIds,@RequestParam("fieldColumn")String fieldColumn) {

		Map<String, Object> condition = new HashMap<String, Object>();

		condition.put("tableId",tableIds);
		String sf = fieldColumn;
		//检验字段描述名
		if (fieldColumnDisplay!=null && !"".equals(fieldColumnDisplay)){
			condition.put("fieldColumnDisplay", fieldColumnDisplay);

		}else {//检验字段列名
			condition.put("fieldColumn", fieldColumn);
		}
		List<AutoTableColumn> list = tableColumnService.queryTableColumnList(condition);
		if (null == list) {
			return;
		}
		// 如果数据库中存在相同的名字
		if (list.size() > 0) {
			renderText(false, "已经存在，请重新输入", null);
		} else {
			renderText(true, "", null);
		}

	}

	/**
	 * 取数据 类型
	 */
	public List<Object> getFieldTypeList(){
		List<Object> list = new ArrayList<Object>();
		for (EnumFiledType filedType : EnumFiledType.values()) {
			list.add(filedType);
		}
		return list;
	}


	/**
	 * 导出自定义字段数据
	 *@zmd
	 * @return
	 */
	@RequestMapping("/exportTableFieldData")
	public String exportTableFieldData() {
		//tableColumnInsert.exportTableFieldData();
		List<AutoTableColumnQuery> autoTableColumnQueryList = tableColumnService.getAllTableColumnQueryList();	//查询所有自定义字段数据包括表名描述

		List<List<String>> exportList = new ArrayList<List<String>>();// 内容
		List<String> headContent = new ArrayList<String>();// 表头内容//表名	表名描述	字段 字段描述	字段类型	字段类型描述	单位	app显示	web显示	字段排序	是否启用	是否必填
		headContent.add("序号");
		headContent.add("表名"); //TABLE_NAME
		headContent.add("表名描述");// ...........tableDisplayName
		headContent.add("字段");//FIELD_COLUMN
		headContent.add("字段描述");//FIELD_COLUMN_DISPLAY
		headContent.add("字段类型");//
		headContent.add("字段类型描述");//短文本长文本。。。。。FIELD_TYPE  Text  Decimal
		headContent.add("单位");//FIELD_NUMBER_UNIT
		headContent.add("app显示");//FIELD_APP_IS_SHOW
		headContent.add("web显示");//FIELD_WEB_IS_SHOW
		headContent.add("字段排序");//FIELD_SORTNO
		headContent.add("是否启用");//IS_ACTIVED
		headContent.add("是否必填");//FIELD_IS_REQUIRED

		for(AutoTableColumnQuery autoTableColumn : autoTableColumnQueryList){
			List<String> exContent = new ArrayList<String>();// 内容
			exContent.add(autoTableColumn.getTableName());
			exContent.add(autoTableColumn.getTableDisplayName());
			exContent.add(autoTableColumn.getFieldColumn());
			exContent.add(autoTableColumn.getFieldColumnDisplay());
			for (EnumFiledType filedType : EnumFiledType.values()) {
				if (filedType.fieldType.equals(autoTableColumn.getFieldType())){
					if ("Decimal".equals(filedType.fieldType) || "Float".equals(filedType.fieldType)){
						exContent.add(filedType.valueType+"("+autoTableColumn.getFieldLength()+",2)");//类型
					}else if("Ratio".equals(filedType.fieldType)){
						exContent.add(filedType.valueType+"("+autoTableColumn.getFieldLength()+",6)");//类型
					}else if ("Date".equals(filedType.fieldType) || "Number".equals(filedType.fieldType)){
						exContent.add(filedType.valueType+"");//类型
					}else {
						exContent.add(filedType.valueType+"("+autoTableColumn.getFieldLength()+")");//类型
					}
					exContent.add(filedType.typeName+"("+autoTableColumn.getFieldType()+")");//类型描述
					break;
				}
			}
			exContent.add(autoTableColumn.getFieldNumberUnit());
			exContent.add(autoTableColumn.getFieldAppIsShow()==1?"是":"否");
			exContent.add(autoTableColumn.getFieldWebIsShow()==1?"是":"否");
			exContent.add(autoTableColumn.getFieldSortno()+"");
			exContent.add(autoTableColumn.getIsActived()==1?"是":"否");
			exContent.add(autoTableColumn.getFieldIsRequired()==1?"是":"否");

			exportList.add(exContent);
		}
		createEXCEL(headContent, exportList);

		return null;
	}
	/**
	 * 生成EXCEL
	 *
	 * @param headContent
	 * @param exContent
	 * @throws Exception
	 */
	public void createEXCEL(List<String> headContent, List<List<String>> exContent) {
		try {
			// 生成文件存放路径
			String timePath = new SimpleDateFormat("yyyyMMddHmsS").format(new Date());
			String savePath = FileUtil.contact(this.getRootPath(), "temp_file"+ File.separator+"tableColumn"+File.separator+"export"+File.separator+timePath);
			File filePath = new File(savePath);
			if (!filePath.exists()) {// 文件不存在则创建
				filePath.mkdirs();
			}
			// 获取带路径的文件名
			String pathAndEXCELName = FileUtil.contact(savePath, "自定义字段表名"+ ".xls"); // 路径+生成EXCEL文件名
			// 生成文件
			createHSSFWorkbook(headContent, exContent, pathAndEXCELName);
			// 导出文件
			exportEXCEl(pathAndEXCELName, "自定义字段表名");
			//删除临时文件以及文件夹
			File file = new File(pathAndEXCELName);
			if(file.exists()){
				file.delete();
			}
		} catch (Exception e) {
			log.info("生成EXCEL的过程中出现错误");
			e.printStackTrace();
		}
	}

	/**
	 * 创建Excel文件
	 *
	 * @param headContent
	 * @param exContent
	 * @param pathAndEXCELName
	 */
	private void createHSSFWorkbook(List<String> headContent, List<List<String>> exContent, String pathAndEXCELName) {
		HSSFWorkbook bookWorkbook = new HSSFWorkbook();
		HSSFSheet wbSheet = bookWorkbook.createSheet("全部字段表单");
		// 冻结第一行
		wbSheet.createFreezePane(1, 1);
		HSSFCell cell;
		HSSFRow row;

		// Sheet样式
		HSSFCellStyle sheetStyle = bookWorkbook.createCellStyle();
		// 背景色的设定
		//sheetStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		// 前景色的设定
		//sheetStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		// 填充模式
		//sheetStyle.setFillPattern(HSSFCellStyle.FINE_DOTS);
		// 设置列的样式
		for (int i = 0; i < headContent.size(); i++) {
			wbSheet.setDefaultColumnStyle((short) i, sheetStyle);
		}

		// 另一个字体样式
		HSSFFont columnHeadFont = bookWorkbook.createFont();
		columnHeadFont.setFontName("宋体");
		columnHeadFont.setFontHeightInPoints((short) 12);
		columnHeadFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 列头的样式
		HSSFCellStyle columnHeadStyle = bookWorkbook.createCellStyle();
		columnHeadStyle.setFont(columnHeadFont);
		columnHeadStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		columnHeadStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		columnHeadStyle.setLocked(true);
		columnHeadStyle.setWrapText(true);
//		columnHeadStyle.setLeftBorderColor(HSSFColor.BLACK.index);// 左边框的颜色
//		columnHeadStyle.setBorderLeft((short) 1);// 边框的大小
//		columnHeadStyle.setRightBorderColor(HSSFColor.BLACK.index);// 右边框的颜色
//		columnHeadStyle.setBorderRight((short) 1);// 边框的大小
//		columnHeadStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体
//		columnHeadStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色
		// 设置单元格的背景颜色（单元格的样式会覆盖列或行的样式）
		columnHeadStyle.setFillForegroundColor(HSSFColor.WHITE.index);

		HSSFFont font = bookWorkbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 11);
		// 普通单元格样式
		HSSFCellStyle style = bookWorkbook.createCellStyle();
		style.setFont(font);
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 左右居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);// 上下居中
		style.setWrapText(true);
//		style.setLeftBorderColor(HSSFColor.BLACK.index);
//		style.setBorderLeft((short) 1);
//		style.setRightBorderColor(HSSFColor.BLACK.index);
//		style.setBorderRight((short) 1);
		//style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体
		//style.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．
		//style.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色．

		// 创建第一行，表头
		row = wbSheet.createRow((short) 0);
		for (int i = 0; i < headContent.size(); i++) {
			cell = row.createCell(i);
			cell.setCellValue(headContent.get(i));
			cell.setCellStyle(columnHeadStyle);
		}

		// 设置每列的宽度
		setColumnWidths(wbSheet);

		// 循环创建结果集
		// 最后一行表头的行数
		int titleRow = 1;

		String tableName ="";
		String tableNameT ="";
		String  tableNameDisplay ="";
		Integer length = exContent.size();
		Integer i2 = 0;

		for (int i = 0; i < length; i++) {
			List<String> content = exContent.get(i2);
            boolean flag =false;

            //序号
				if (i>0){
					titleRow=0;//第一个表
				}
				row = wbSheet.createRow((short) (titleRow + i));
				cell = row.createCell(0);
				cell.setCellStyle(style);
				cell.setCellValue(i );

			if (!tableName.equals(tableNameT)){
				cell = row.createCell(4);
				cell.setCellStyle(columnHeadStyle);
				cell.setCellValue(content.get(1));
				//
				length =length+2;
				tableNameT =  tableName;

				//再建多个sheet----------------------------------------------------

				HSSFSheet sheet = bookWorkbook.createSheet(tableNameDisplay+"");//创建一个工作薄
				// 创建第一行，表头
				row = sheet.createRow((short) 0);
				for (int s = 0; s < headContent.size(); s++) {
					cell = row.createCell(s);
					cell.setCellValue(headContent.get(s));
					cell.setCellStyle(columnHeadStyle);
				}
				// 设置每列的宽度
				setColumnWidths(sheet);

             //分标题表名
				row = sheet.createRow((short) (1));
				cell = row.createCell(0);
				cell.setCellStyle(style);
				cell.setCellValue(1 );

				cell = row.createCell(4);
				cell.setCellStyle(columnHeadStyle);
				cell.setCellValue(tableNameDisplay);

             String name =tableNameT;//""
             String name2 =tableNameT;//""
             int sm = 2;

				//循环一下
				for (int is = i2; is < exContent.size(); is++) {
					row = sheet.createRow((short)(sm));
					cell = row.createCell(0);
					cell.setCellStyle(style);
					cell.setCellValue(sm );

					List<String> contents = exContent.get(is);

					for (int j = 0; j < contents.size(); j++) {
						if (j==0){//判断单表表名
							name=contents.get(0);
							if (!name.equals(name2)){
								break;
							}
						      }
						cell = row.createCell(j + 1);
						cell.setCellStyle(style);
						cell.setCellValue(contents.get(j));
					}

					sm++;
					if (!name.equals(name2)){

						break;
					}
				}
				//再建sheet----------------------------------------------------
			}else{

				for (int j = 0; j < content.size(); j++) {
					if (j==0){//判断单表表名
						tableName=content.get(j);
						tableNameDisplay = content.get(j+1);
						if (!tableName.equals(tableNameT)){
							break;
						}
					}
					cell = row.createCell(j + 1);
					cell.setCellStyle(style);
					cell.setCellValue(content.get(j));
					flag = true;
				}
			}

       if (flag){
		   i2++;
	   }
		}
		try {
			FileOutputStream outputStream;
			try {
				outputStream = new FileOutputStream(pathAndEXCELName);
				bookWorkbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
			} catch (FileNotFoundException e) {
				System.err.println("获取不到位置");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} finally {

		}
	}
	/**
	 * 设置EXCEL列宽
	 *setColumnWidth
	 *
	 */
	public void setColumnWidths(HSSFSheet  sheet){
		int k2 = 0;
		sheet.setColumnWidth(k2++, 2500);
		sheet.setColumnWidth(k2++, 7000);
		sheet.setColumnWidth(k2++, 5500);
		sheet.setColumnWidth(k2++, 7000);//4
		sheet.setColumnWidth(k2++, 7000);
		sheet.setColumnWidth(k2++, 5500);
		sheet.setColumnWidth(k2++, 5500);
		sheet.setColumnWidth(k2++, 4000);//8
		sheet.setColumnWidth(k2++, 4000);
		sheet.setColumnWidth(k2++, 4000);
		sheet.setColumnWidth(k2++, 4000);
		sheet.setColumnWidth(k2++, 4000);
		sheet.setColumnWidth(k2++, 4000);
	}

	/**
	 * 导出EXCEL
	 *
	 * @param pathAndEXCELName
	 */
	public void exportEXCEl(String pathAndEXCELName, String EXCELName) {

		File file = new File(pathAndEXCELName);
		if (file.exists()) {
			try {
				this.getResponse().setCharacterEncoding("utf-8");
				this.getResponse().setContentType("application/vnd.ms-excel; charset=UTF-8");
				this.getResponse().addHeader("Content-Disposition", "attachment;filename="
						+ URLEncoder.encode(EXCELName, "utf-8") + ".xls");
//                this.getResponse().setContentType("xls/*"); // 设置返回的文件类型
//                this.getResponse().addHeader(
//                        "Content-Disposition",
//                        "attachment;filename="
//                                + (URLEncoder.encode(EXCELName,
//                                "utf-8") + ".xls").replace("+", "%20"));
				OutputStream output = this.getResponse().getOutputStream(); // 得到向客户端输出二进制数据的对象
				FileInputStream fis = new FileInputStream(file);
				BufferedInputStream bis = new BufferedInputStream(fis);// 输入缓冲流
				BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流
				byte data[] = new byte[1024 * 1024];// 缓冲字节数
				int size = 0;
				size = bis.read(data);
				while (size != -1) {
					bos.write(data, 0, size);
					size = bis.read(data);
				}
				bis.close();
				bos.flush();// 清空输出缓冲流
				bos.close();

				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.error("导出EXCEL出错 error:" + e.getMessage(), e);
			}
		}
	}
}
