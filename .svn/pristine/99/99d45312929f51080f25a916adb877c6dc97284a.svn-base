package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.config.*;
import banger.domain.enumerate.EnumFiledType;
import banger.framework.pagesize.IPageList;
import banger.moduleIntf.ISystemModule;
import banger.service.intf.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/intoTemplatesInfo")
public class IntoTemplateInfoController extends BaseController {
    @Autowired
    private ITemplatesInfoService templatesInfoService;
    @Autowired
    private ITemplatesFieldService templatesFieldService;
    @Autowired
    private ITableColumnService tableColumnService;
    @Autowired
	private IAutoTableColumnService iAutoTableColumnService;
	@Autowired
	private ITableInfoService tableInfoService;
	@Autowired
	private ISystemModule systemModule;

    /**
     * 获取进件信息页面
     * @return
     */
    @RequestMapping("/getIntoTemplatesInfoPage")
    public ModelAndView getModelTemplateInfoList(){
        ModelAndView mv = new ModelAndView("/config/vm/into/intoInfoList");
//        Map<String,Object> condition = new HashMap<String,Object>();
//        //联AUTO表，将 没有下拉，number，decimal，boolean子项的排除
//        List<IntoTemplatesInfo> intoTemplatesInfos = templatesInfoService.queryTemplatesInfoListModel(condition);
//        mv.addObject("intoTemplatesInfos",intoTemplatesInfos);
        return mv;
    }

	/**
	 * 获取进件信息列表
	 * @return
	 */
	@RequestMapping("/queryIntoInfoList")
	@ResponseBody
	public void queryIntoInfoList(){
		List<IntoAutoTableColumnQuery> intoAutoTableColumnQueries = iAutoTableColumnService.queryConnectAutoTable();
		renderText(JsonUtil.toJson(intoAutoTableColumnQueries, "fieldId","tableModelName,fieldName,fieldIsRequired,isActived").toString());
	}
    /**
     * 查询模块字段列表
     */
    @RequestMapping("queryIntoTemplatesInfoColunm")
    public void queryIntoTemplatesInfoColunm(){
        Map<String,Object> condition = new HashMap<String,Object>();
        String templateId=getParameter("templateId");
        if(templateId!= null && !"".equals(templateId)){
            condition.put("templateId",Integer.valueOf(templateId));
        }
        condition.put("fieldName",getParameter("fieldName"));
        IPageList<IntoTemplatesField> models = templatesFieldService.queryIntoTemplatesInfoColunm(condition,this.getPage());
        renderText(JsonUtil.toJson(models, "fieldId","fieldDisplay,tableName,fieldName,isMust,templateId").toString());
    }
    /**
     * 获取设置页
     * @return
     */
//    @RequestMapping("/getIntoTemplatesInfoSetPage")
//    public String getModelTemplateInfoSetPage() {
//        //根据模型名称和 类型查询
//        Map<String, Object> condition = new HashMap<String, Object>();
//        String intoTemplateInfoJson = templatesInfoService.queryIntoTemplatesInfoTree(condition);
//        if(StringUtils.isNotBlank(intoTemplateInfoJson)) {
//            setAttribute("intoTemplatesInfoJson", intoTemplateInfoJson);
//        }
//        //编辑是选中节点
//		String templateId = getParameter("templateId");
//		setAttribute("selTemplateId", templateId);
//		return "/config/vm/into/intoTemplatesInfoSet";
//	}

    /**
     * 点击节点时获取字段，显示名称和是否必填
     */
    @RequestMapping("/queryIntoTemplatesInfoColumnList")
    @ResponseBody
    public void queryModelTemplateInfoColumnList(@RequestParam("templateId") Integer templateId) {
        Map<String, Object> condition = new HashMap<String, Object>();
        IntoTemplatesInfo templatesInfoById = templatesInfoService.getTemplatesInfoById(templateId);
        //根据TEMPLATE_NAME去auto表中获取评分项，（下拉，number，decimal，boolean）
        condition.put("tableName",templatesInfoById.getTemplateName());
        condition.put("condition","Select,MultipleSelect,YesNo,Number,Decimal,Float,Ratio".split(","));
        List<IntoTemplatesField> list = tableColumnService.queryIntoTemplatesInfoColumn(condition);
        renderText(JsonUtil.toJson(list, "fieldId","fieldDisplay,isActived,fieldName,isMust").toString());
    }

    /**
     * 保存
     */
    @RequestMapping("/saveIntoTemplate")
    public void saveIntoTemplate(@RequestParam("jsons") String jsons ){
        Integer loginUserId = getLoginInfo().getUserId();
        JSONArray jsonArray = JSONArray.fromObject(jsons);
        //遍历
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            //templateId
            Integer templateId = (Integer) jsonObject.get("templateId");
            //子表数据
            JSONObject arrayJson = new JSONObject().fromObject(jsonObject.get("json"));
            String fieldId = (String) arrayJson.get("fieldIds");
            String[] fieldIds = fieldId.split(",");
            String isActived = (String) arrayJson.get("isActiveds");
            String[] isActiveds = isActived.split(",");
            String fieldName = (String) arrayJson.get("fieldNames");
            String[] fieldNames = fieldName.split(",");
            String fieldColumnDisplay = (String) arrayJson.get("fieldColumnDisplays");
            String[] fieldColumnDisplays = fieldColumnDisplay.split(",");
            String isMust=(String)arrayJson.get("isMusts");
            String[] isMusts=isMust.split(",");
            //根据templateId查出templateName
            String templateName =templatesInfoService.getTemplatesInfoById(templateId).getTemplateName();
            //新增或者更新
            templatesFieldService.saveOrUpdate(templateId,templateName,fieldIds,isActiveds,fieldNames,fieldColumnDisplays,isMusts,loginUserId);
        }
    }

//	/**
//	 * 禁用自定义字段
//	 *
//	 * @return
//	 */
//	@RequestMapping("/disable/{filedId}")
//	public void disableTableTemplate(@PathVariable Integer filedId) {
//		Integer loginUserId = getLoginInfo().getUserId();
//		IntoTemplatesField intoTemplatesField = new IntoTemplatesField();
//		intoTemplatesField.setFieldId(filedId);
//		intoTemplatesField.setIsActived(GlobalConst.YesOrNo.NO);
//		templatesFieldService.updateTemplatesField(intoTemplatesField, loginUserId);
//		renderText(SUCCESS);
//	}

//	/**
//	 * 上移下移
//	 */
//	@RequestMapping("/moveIntoTemplatesFieldOrderNo")
//	@ResponseBody
//	public void moveIntoTemplatesFieldOrderNo(@RequestParam("templateId") Integer templateId, @RequestParam("fieldId") Integer fieldId,@RequestParam("type") String type) {
//		templatesFieldService.moveIntoTemplatesFieldOrderNo(templateId,fieldId,type);
//		this.renderText(SUCCESS);
//	}

	/**
	 * 上移下移
	 */
	@RequestMapping("/moveIntoInfoFieldId")
	@ResponseBody
	public void moveIntoInfoFieldId(@RequestParam("fieldId") Integer fieldId,@RequestParam("type") String type){
		iAutoTableColumnService.moveIntoInfoFieldId(fieldId,type);
		this.renderText(SUCCESS);
	}

	/**
	 * 禁用自定义字段
	 */
	@RequestMapping("/disable")
	@ResponseBody
	public void disable(@RequestParam("fieldId") Integer fieldId){
		Integer loginUserId = getLoginInfo().getUserId();
		IntoAutoTableColumn intoAutoTableColumn = iAutoTableColumnService.getAutoTableColumnById(fieldId);
		intoAutoTableColumn.setIsActived(0);
		iAutoTableColumnService.updateAutoTableColumn(intoAutoTableColumn,loginUserId);
	}

	/**
	 * 启用字段
	 */
	@RequestMapping("/enable")
	@ResponseBody
	public void enable(@RequestParam("fieldId") Integer fieldId){
		Integer loginUserId = getLoginInfo().getUserId();
		IntoAutoTableColumn intoAutoTableColumn = iAutoTableColumnService.getAutoTableColumnById(fieldId);
		intoAutoTableColumn.setIsActived(1);
		iAutoTableColumnService.updateAutoTableColumn(intoAutoTableColumn,loginUserId);

	}

	/**
	 * 跳转新增页面
	 */
	@RequestMapping("/getIntoInfoPage")
	public String getIntoInfoPage(){
		String fieldId = getParameter("fieldId");
		if(fieldId != null){
			IntoAutoTableColumnQuery intoAutoTableColumnQuery = iAutoTableColumnService.queryConnectAutoTableById(Integer.parseInt(fieldId));
			setAttribute("intoAutoTableColumn",intoAutoTableColumnQuery);
		}
		Map<String,Object> condition = new HashMap<String,Object>();
		//控制表单信息
		List<AutoTableInfo> tableInfoList = tableInfoService.queryAllTableInfoList();
		List<AutoTableInfo> tableInfoList1 = new ArrayList<AutoTableInfo>();
		for (AutoTableInfo autoTableInfo:tableInfoList){
			if (autoTableInfo.getTableModule().equals("LOAN") && autoTableInfo.getTableType() == 1){
				tableInfoList1.add(autoTableInfo);
			}
		}
		setAttribute("tableInfoList",tableInfoList1);
		return "/config/vm/into/intoInfoAdd";
	}

//	/**
//	 * 跳转编辑页面
//	 */
//	@RequestMapping("/getIntoInfoUpdatePage")
//	public String getIntoInfoUpdatePage(@RequestParam("fieldId") Integer fieldId){
//		IntoAutoTableColumn intoAutoTableColumn = iAutoTableColumnService.getAutoTableColumnById(fieldId);
//		setAttribute("intoAutoTableColumn",intoAutoTableColumn);
//		return "/config/vm/into/intoInfoAdd";
//	}

//	/**
//	 * 获取字段
//	 */
//	@RequestMapping("/getFieldColumn")
//	public void getFieldColumn(@RequestParam String fieldColumn){
//		if(StringUtils.isNotBlank(fieldColumn)){
//			List<SysDataDictCol> sysDataDictCols = systemModule.queryDataDictColListByName(fieldColumn);
//			renderText(JsonUtil.toJson(sysDataDictCols, "dictColId","dictValue,dictName").toString());
//		}
//	}

	/**
	 * 获取表中的字段
	 */
	@RequestMapping("/getTableName")
	public void getTableName(@RequestParam String tableName){
		Map<String,Object> condition = new HashMap<String,Object>();
		if(tableName != null){
			condition.put("tableName",tableName);
		}
		List<AutoTableColumn> tableColumnList = tableColumnService.queryTableColumnList(condition);
		//过滤出下拉框类型字段
		List<AutoTableColumn> selectColumns = new ArrayList<AutoTableColumn>();
		for(AutoTableColumn column : tableColumnList){
			if(StringUtils.equals(column.getFieldType(), EnumFiledType.SELECT.fieldType)){
				//转换成驼峰属性
//				column.setFieldColumn(StringUtil.camelName(column.getFieldColumn()));
				selectColumns.add(column);
			}
		}
		renderText(JsonUtil.toJson(selectColumns, "fieldId","fieldColumn,fieldDictName,fieldColumnDisplay").toString());
	}

	/**
	 * 插入进件自定义字段
	 */
	@RequestMapping("/insertIntoInfo")
	public void insertIntoInfo(@ModelAttribute IntoAutoTableColumn intoAutoTableColumn){
		try {
			Integer loginUserId = getLoginInfo().getUserId();
			//判断该自定义字段是否已经存在
			if(isALikeIntoInfo(intoAutoTableColumn)){
				renderText(false,"已存在相同的自定义字段","");
			}else if (intoAutoTableColumn.getFieldId() == null){

				Integer maxSortNo = iAutoTableColumnService.queryMaxSortNo();
				if (maxSortNo == null){
					maxSortNo = 0;
				}
				intoAutoTableColumn.setIsActived(1);
				intoAutoTableColumn.setFieldSortno(maxSortNo+1);
				iAutoTableColumnService.insertAutoTableColumn(intoAutoTableColumn,loginUserId);
				renderText(true, "保存成功", "");
			}else{
				iAutoTableColumnService.updateAutoTableColumn(intoAutoTableColumn,loginUserId);
				renderText(true, "更新成功", "");
			}
		}catch (Exception e){
			renderText(false,"参数错误","");
		}
	}

	/**
	 * 编辑进件信息
	 */
	@RequestMapping("/updateIntoInfo")
	public void updateIntoInfo(@ModelAttribute IntoAutoTableColumn intoAutoTableColumn){
		try {
			Integer loginUserId = getLoginInfo().getUserId();
			//判断该自定义字段是否已经存在
			if(isALikeIntoInfo(intoAutoTableColumn)){
				renderText(false,"已存在相同的自定义字段","");
			}else{
				iAutoTableColumnService.updateAutoTableColumn(intoAutoTableColumn,loginUserId);
				renderText(true, "保存成功", "");
			}
		}catch (Exception e){
			renderText(false,"参数错误","");
		}
	}


	/**
	 * 判断数据库中是否已存在此自定义字段
	 */
	private boolean isALikeIntoInfo(IntoAutoTableColumn intoAutoTableColumn){
		Map<String,Object> condition = new HashMap<String,Object>();
		if(intoAutoTableColumn != null){
			condition.put("tableName",intoAutoTableColumn.getTableName());
			condition.put("fieldColumn",intoAutoTableColumn.getFieldColumn());
			List<IntoAutoTableColumn> list = iAutoTableColumnService.queryAutoTableColumnList(condition);
			if (list.size() > 0){
				return true;
			}
		}
		return false;
	}
}
