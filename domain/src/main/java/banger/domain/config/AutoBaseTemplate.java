package banger.domain.config;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AutoBaseTemplate {
	private Integer id;			//主键ID
	private String name;		//名称
	private String tableName;	//表名
	private String lowerTableName;	//实体名
	private String type;		//模板类型
	private String module;		//模块
	private List<AutoBaseField> fields;
	private Object data;
	private String jsonFields;//非持久化字段 fields的json形式
	private String tableFormula ;//非持久化字段 表单合计
	private Map<String, Object> attributeMap;
	private String showOrHide;   //表单显示和隐藏

	public String getShowOrHide() {
		return showOrHide;
	}

	public void setShowOrHide(String showOrHide) {
		this.showOrHide = showOrHide;
	}

	public AutoBaseTemplate(){
		this.fields = new ArrayList<AutoBaseField>();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<AutoBaseField> getFields() {
		return fields;
	}
	public void setFields(List<AutoBaseField> fields) {
		this.fields = fields;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
		if(tableName!=null && tableName.length()>0){
			String[] parts = tableName.split("_");
			String lowerName = "";
			for(int i=0;i<parts.length;i++){
				if(i==0){
					lowerName+=parts[i].toLowerCase();
				}else{
					lowerName+=parts[i].substring(0,1)+parts[i].substring(1).toLowerCase();
				}
			}
			this.lowerTableName=lowerName;
		}else{
			this.lowerTableName="";
		}
	}
	
	public String getLowerTableName() {
		return lowerTableName;
	}
	
	public void setLowerTableName(String lowerTableName) {
		this.lowerTableName = lowerTableName;
	}

	public String getJsonFields() {
		return jsonFields;
	}

	public void setJsonFields(String jsonFields) {
		this.jsonFields = jsonFields;
	}

	public Map<String, Object> getAttributeMap() {
		return attributeMap;
	}

	public void setAttributeMap(Map<String, Object> attributeMap) {
		this.attributeMap = attributeMap;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getTableFormula() {
		return tableFormula;
	}

	public void setTableFormula(String tableFormula) {
		this.tableFormula = tableFormula;
	}

	public String getUnitByColumnName(String columnName){
		if(CollectionUtils.isNotEmpty(fields)){
			for (AutoBaseField field : fields){
				if(columnName.equals(field.getColumn())){
					return field.getUnit();
				}
			}
		}
		return "";
	}

	public String getTypeByColumnName(String columnName){
		if(CollectionUtils.isNotEmpty(fields)){
			for (AutoBaseField field : fields){
				if(columnName.equals(field.getColumn())){
					return field.getType();
				}
			}
		}
		return "";
	}
}
