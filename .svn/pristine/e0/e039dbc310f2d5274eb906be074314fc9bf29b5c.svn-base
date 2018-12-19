package banger.service.impl;

import banger.domain.config.AutoBaseField;
import banger.domain.config.AutoBaseOption;
import banger.domain.config.AutoBaseTemplate;
import banger.domain.customer.CustBasicInfo;
import banger.domain.enumerate.EnumFiledType;
import banger.domain.enumerate.TableInputType;
import banger.domain.enumerate.TableSyncEnum;
import banger.domain.track.MapTagging;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.reader.Reader;
import banger.framework.util.StringUtil;
import banger.moduleIntf.IConfigModule;
import banger.moduleIntf.ICustomerModuleIntf;
import banger.moduleIntf.IMapTaggingProvider;
import banger.response.CodeEnum;
import banger.service.intf.IAppCustomerService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AppCustomerService implements IAppCustomerService {
	@Resource
	IConfigModule configModule;

	@Resource
	private ICustomerModuleIntf customerModule;
	@Autowired
	private IMapTaggingProvider mapTaggingProvider;

	/**
	 * 根据模版name得到模板和字段
	 * @param
	 * @return
	 */
	public String getCustomerTemplateFieldJsonString(String[] templateNameList){
		JSONObject root = new JSONObject();
		JSONArray tja = new JSONArray();

		List<AutoBaseTemplate> tempList = configModule.getAutoTemplateProvider().getTemplateListByTables(templateNameList);
		for(AutoBaseTemplate temp : tempList){
			JSONObject tjo = new JSONObject();
			tjo.put("id", temp.getId());
			tjo.put("name", temp.getName());
			tjo.put("type", temp.getType());
			tjo.put("tableName", temp.getTableName());
			tjo.put("lowerTableName", temp.getLowerTableName());
			tjo.put("editable", true);
			
			if(temp.getFields()!=null){
				JSONArray fja = new JSONArray();
				for(AutoBaseField field : temp.getFields()){
					if(field.getIsAppShow()){
						JSONObject fjo = new JSONObject();
						fjo.put("field", field.getField());
						fjo.put("name", field.getName());
						fjo.put("type", field.getType());
						fjo.put("column", field.getColumn());
						if(field.getInputLength()>0)
							fjo.put("length", field.getInputLength());
						if(StringUtil.isNotEmpty(field.getUnit()))
							fjo.put("unit", field.getUnit());
						if(field.getNullable()!=null && field.getNullable().equals(true))
							fjo.put("required", true);
						if(field.getOptions()!=null){
							JSONArray oja = new JSONArray();

							/* 单选添加一个空白项 */
							if(EnumFiledType.SELECT.equalType(field.getType())) {
								JSONObject ojo = new JSONObject();
								ojo.put("value", "");
								ojo.put("name", "");
								oja.add(ojo);
							}

							for(AutoBaseOption option : field.getOptions()){
								JSONObject joo = new JSONObject();
								joo.put("value", option.getValue());
								joo.put("name", option.getName());
								oja.add(joo);
							}
							fjo.put("options", oja);
						}
						fja.add(fjo);
					}
				}
				tjo.put("fields", fja);
			}
			tja.add(tjo);
		}
		
		root.put("templates", tja);
		
		setSucces(root);
		
		return root.toString();
	}
	
	/**
	 * 根据模版name得到模板和字段
	 * @param
	 * @return
	 */
	public String getCustomerTemplateFieldJsonString(String tableName,Integer customerId,Integer userId){
		JSONObject root = new JSONObject();
		JSONArray tja = new JSONArray();

		List<AutoBaseTemplate> tempList = configModule.getAutoTemplateProvider().getTemplateListByTables(new String[]{tableName});
		DataTable dataTable = customerModule.getDataTableById(TableSyncEnum.valueOfTarget(tableName).sourceTableName,customerId);

		boolean editable = true;
		CustBasicInfo custInfo =  customerModule.getBasicInfoById(customerId);
		if(custInfo!=null && custInfo.getBelongUserId()!=null && userId!=null){
			editable = custInfo.getBelongUserId().equals(userId);
		}

		//
		putTemplateValue(tempList,dataTable);
		Integer id = null;
		if(dataTable!=null && dataTable.rowSize()>0){
			id = Reader.intReader().getValue(dataTable.getRow(0), "ID");
		}
		
		for(AutoBaseTemplate temp : tempList){
			JSONObject tjo = new JSONObject();
			tjo.put("id", id);
			tjo.put("tableId", temp.getId());
			tjo.put("name", temp.getName());
			tjo.put("type", temp.getType());
			tjo.put("tableName", temp.getTableName());
			tjo.put("lowerTableName", temp.getLowerTableName());
			tjo.put("editable", editable);
			
			if(temp.getFields()!=null){
				JSONArray fja = new JSONArray();
				for(AutoBaseField field : temp.getFields()){
					if(field.getIsAppShow()){
						JSONObject fjo = new JSONObject();
						fjo.put("field", field.getField());
						fjo.put("name", field.getName());
						fjo.put("type", field.getType());
						fjo.put("value", field.getValue());
						fjo.put("column", field.getColumn());
						if("CUSTOMER_NUM".equals(field.getColumn())) {
							fjo.put("editable", true);
						}else{
							fjo.put("editable", editable);
						}
						if(field.getInputLength()>0)
							fjo.put("length", field.getInputLength());
						if(StringUtil.isNotEmpty(field.getUnit()))
							fjo.put("unit", field.getUnit());
						if(field.getNullable()!=null && field.getNullable().equals(true))
							fjo.put("required", true);
						if(field.getOptions()!=null){
							JSONArray oja = new JSONArray();

							/* 单选添加一个空白项 */
							if(EnumFiledType.SELECT.equalType(field.getType())) {
								JSONObject ojo = new JSONObject();
								ojo.put("value", "");
								ojo.put("name", "");
								oja.add(ojo);
							}

							for(AutoBaseOption option : field.getOptions()){
								JSONObject joo = new JSONObject();
								joo.put("value", option.getValue());
								joo.put("name", option.getName());
								oja.add(joo);
							}
							fjo.put("options", oja);
						}
						if("telephone".equalsIgnoreCase(field.getRule())){
							fjo.put("telephone", true);
						}
						if(EnumFiledType.ADDRESS.equalType(field.getType())){ //app显示地图有没有标注过
							Map<String,Object> condition = new HashMap<String, Object>();
							condition.put("tableName",temp.getTableName());
							condition.put("columnName",field.getColumn());
							condition.put("customerId", customerId);
							List<MapTagging> taggingList = mapTaggingProvider.queryTaggingList(condition);
							fjo.put("tagging",taggingList.size()>0?true:false);
						}
						fja.add(fjo);
					}
				}
				tjo.put("fields", fja);
			}
			tja.add(tjo);
		}
		
		root.put("templates", tja);
		
		setSucces(root);
		
		return root.toString();
	}
	
	private void setSucces(JSONObject resultJson){
		resultJson.put("code", CodeEnum.CODE_100.getCode());
		resultJson.put("msg", CodeEnum.CODE_100.getMsg());
	}
	
	private void putTemplateValue(List<AutoBaseTemplate> templateList, DataTable dataTable) {
		for(AutoBaseTemplate template : templateList){
			if(dataTable!=null && dataTable.rowSize()>0){
				DataRow row = dataTable.getRow(0);
				for(AutoBaseField field : template.getFields()){
					Object object = row.get(field.getColumn());
					if("Date".equals(field.getType())&&null!=object){
						object = new SimpleDateFormat("yyyy-MM-dd").format(object);
					}
					field.setValue(object);
				}
			}
		}

	}
	
}
