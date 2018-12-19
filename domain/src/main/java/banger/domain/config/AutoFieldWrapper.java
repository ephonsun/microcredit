package banger.domain.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.framework.collection.DataRow;
import banger.framework.reader.Reader;
import banger.framework.util.IdCardUtil;
import banger.framework.util.SensitiveUtil;
import banger.framework.util.StringUtil;
import org.apache.commons.lang.StringUtils;

public class AutoFieldWrapper extends AutoBaseField {
	private Map<String,String> optionMap;
	
	public AutoFieldWrapper(Integer id,String field,String column,String name,String type,boolean isAppShow,boolean isWebShow){
		this.setId(id);
		this.setField(field);
		this.setColumn(column);
		this.setName(name);
		this.setType(type);
		this.setLength(-1);
		this.setIsAppShow(isAppShow);
		this.setIsWebShow(isWebShow);
	}
	
	public AutoFieldWrapper(Integer id,String field,String column,String name,String type,String unit,boolean isQuired,boolean isAppShow,boolean isWebShow){
		this.setId(id);
		this.setField(field);
		this.setColumn(column);
		this.setName(name);
		this.setType(type);
		this.setUnit(unit);
		this.setNullable(isQuired);
		this.setLength(-1);
		this.setIsAppShow(isAppShow);
		this.setIsWebShow(isWebShow);
	}
	
	public AutoFieldWrapper(Integer id,String field,String column,String name,String type,String unit,boolean isQuired,Integer length,boolean isAppShow,boolean isWebShow){
		this.setId(id);
		this.setField(field);
		this.setColumn(column);
		this.setName(name);
		this.setType(type);
		this.setUnit(unit);
		this.setNullable(isQuired);
		this.setLength(length);
		this.setIsAppShow(isAppShow);
		this.setIsWebShow(isWebShow);
	}

	public AutoFieldWrapper(Integer id,String field,String column,String name,String type,String unit,boolean isQuired,Integer length,boolean isAppShow,boolean isWebShow,String rule){
		this.setId(id);
		this.setField(field);
		this.setColumn(column);
		this.setName(name);
		this.setType(type);
		this.setUnit(unit);
		this.setNullable(isQuired);
		this.setLength(length);
		this.setIsAppShow(isAppShow);
		this.setIsWebShow(isWebShow);
		this.setRule(rule);
	}

	public AutoFieldWrapper(Integer id,String field,String column,String name,String type,String unit,boolean isQuired,Integer length,boolean isAppShow,boolean isWebShow,String rule,String popupUrl){
		this.setId(id);
		this.setField(field);
		this.setColumn(column);
		this.setName(name);
		this.setType(type);
		this.setUnit(unit);
		this.setNullable(isQuired);
		this.setLength(length);
		this.setIsAppShow(isAppShow);
		this.setIsWebShow(isWebShow);
		this.setRule(rule);
		this.setPopupUrl(popupUrl);
	}

	public AutoFieldWrapper(Integer id,String field,String column,String name,String type,String unit,boolean isQuired,Integer length,boolean isAppShow,boolean isWebShow,String rule,String popupUrl,String defaultValue){
		this.setId(id);
		this.setField(field);
		this.setColumn(column);
		this.setName(name);
		this.setType(type);
		this.setUnit(unit);
		this.setNullable(isQuired);
		this.setLength(length);
		this.setIsAppShow(isAppShow);
		this.setIsWebShow(isWebShow);
		this.setRule(rule);
		this.setPopupUrl(popupUrl);
		this.setDefaultValue(defaultValue);
	}
	
	public AutoFieldWrapper(Integer id,String field,String column,String name,String type,String unit,boolean isQuired,List<AutoBaseOption> options,boolean isAppShow,boolean isWebShow){
		this.setId(id);
		this.setField(field);
		this.setColumn(column);
		this.setName(name);
		this.setType(type);
		this.setUnit(unit);
		this.setNullable(isQuired);
		this.setOptions(options);
		this.setIsAppShow(isAppShow);
		this.setIsWebShow(isWebShow);
	}
	
	public AutoFieldWrapper(Integer id,String field,String column,String name,String type,String unit,boolean isQuired,Integer length,List<AutoBaseOption> options,boolean isAppShow,boolean isWebShow){
		this.setId(id);
		this.setField(field);
		this.setColumn(column);
		this.setName(name);
		this.setType(type);
		this.setUnit(unit);
		this.setNullable(isQuired);
		this.setLength(length);
		this.setOptions(options);
		this.setIsAppShow(isAppShow);
		this.setIsWebShow(isWebShow);
	}

	public AutoFieldWrapper(Integer id,String field,String column,String name,String type,String unit,boolean isQuired,Integer length,List<AutoBaseOption> options,boolean isAppShow,boolean isWebShow,String rule){
		this.setId(id);
		this.setField(field);
		this.setColumn(column);
		this.setName(name);
		this.setType(type);
		this.setUnit(unit);
		this.setNullable(isQuired);
		this.setLength(length);
		this.setOptions(options);
		this.setIsAppShow(isAppShow);
		this.setIsWebShow(isWebShow);
		this.setRule(rule);
	}

	public AutoFieldWrapper(Integer id,String field,String column,String name,String type,String unit,boolean isQuired,Integer length,List<AutoBaseOption> options,boolean isAppShow,boolean isWebShow,String rule,String popupUrl){
		this.setId(id);
		this.setField(field);
		this.setColumn(column);
		this.setName(name);
		this.setType(type);
		this.setUnit(unit);
		this.setNullable(isQuired);
		this.setLength(length);
		this.setOptions(options);
		this.setIsAppShow(isAppShow);
		this.setIsWebShow(isWebShow);
		this.setRule(rule);
		this.setPopupUrl(popupUrl);
	}

	public AutoFieldWrapper(Integer id,String field,String column,String name,String type,String unit,boolean isQuired,Integer length,List<AutoBaseOption> options,boolean isAppShow,boolean isWebShow,String rule,String popupUrl,String defaultValue){
		this.setId(id);
		this.setField(field);
		this.setColumn(column);
		this.setName(name);
		this.setType(type);
		this.setUnit(unit);
		this.setNullable(isQuired);
		this.setLength(length);
		this.setOptions(options);
		this.setIsAppShow(isAppShow);
		this.setIsWebShow(isWebShow);
		this.setRule(rule);
		this.setPopupUrl(popupUrl);
		this.setDefaultValue(defaultValue);
	}
	
	public String getFieldName(){
		return this.name;
	}
	
	public String getFieldId(){
		return this.id+"";
	}
	
	public String getFieldType(){
		return this.type;
	}
	
	public String getFieldNumberUnit(){
		return this.unit;
	}
	
	public Integer getFieldIsNullable(){
		return nullable?1:0;
	}

	public String getOptionValueByText(String text){
		if(text!=null){
			if(this.options!=null && this.options.size()>0){
				if(this.optionMap==null){
					this.optionMap = new HashMap<String,String>();
					for(AutoBaseOption option : this.options){
						this.optionMap.put(option.getName(), option.getValue());
					}
				}
				if(this.optionMap.containsKey(text))
					return this.optionMap.get(text);
			}
		}
		return "";
	}

//	public String getFieldNumberMoney(String money) {
//		return IdCardUtil.getFormatMoney(money);
//	}

	public String getOptionTextByValue(String value){
		if(value!=null){
			if(this.options!=null && this.options.size()>0){
				for(AutoBaseOption option : this.options){
					if(value.equals(option.getOptionValue())){
						return option.getOptionText();

					}
				}
			}
		}
		return "";
	}

	public String getOptionTextByValues(String values){
		String str = "";
		if(values!=null && values.length()>0){
			String[] vals = values.split("\\,");
			if(this.options!=null && this.options.size()>0){
				for(String val : vals) {
					for (AutoBaseOption option : this.options) {
						if (val.equals(option.getOptionValue())) {
							if ("".equals(str)) {
								str += option.getOptionText();
							} else {
								str += " " + option.getOptionText();
							}
						}
					}
				}
			}
		}
		return str;
	}
	
	/**
	 * 
	 * @param fieldFlag 0新增编辑 1查询 2查看
	 * @return
	 */
    public Integer getColSpan(Integer fieldFlag) {
    	String type = this.getType();
    	if("Date".equalsIgnoreCase(type) && (fieldFlag!= null && fieldFlag.intValue()==1))return 2;
    	if("Decimal".equalsIgnoreCase(type) && (fieldFlag!= null && fieldFlag.intValue()==1))return 2;
    	if("Number".equalsIgnoreCase(type) && (fieldFlag!= null && fieldFlag.intValue()==1))return 2;
		if("Float".equalsIgnoreCase(type) && (fieldFlag!= null && fieldFlag.intValue()==1))return 2;
    	if("Ratio".equalsIgnoreCase(type) && (fieldFlag!= null && fieldFlag.intValue()==1))return 2;
    	if("Address".equalsIgnoreCase(type) && (fieldFlag == null || fieldFlag.intValue()!=1))return 2;
		if("TextArea".equalsIgnoreCase(type) && (fieldFlag == null || fieldFlag.intValue()!=1))return 4;
		if("Quest".equalsIgnoreCase(type) && (fieldFlag == null || fieldFlag.intValue()!=1))return 4;
		return 1;
	}



//	/**
//	 * 15位身份证号：脱敏位数为7-12位；18位身份证号：脱敏数位7-14位
//	 * @return
//	 */
//	public String getLoanIdentifyNumX( String loanIdentifyNumX) {
//		return IdCardUtil.getIdentifyNumX(loanIdentifyNumX);
//	}

//	/**
//	 *
//	 * @param loanTelNumX
//	 * @return
//	 */
//	public String getLoanTelNumX(String loanTelNumX) {
//		return IdCardUtil.getTelNumX(loanTelNumX);
//	}
//
    /**
     * 得到数据库长度
     * @return
     */
    public Integer getMaxLength(){
    	String type = this.getType();
		if("Address".equalsIgnoreCase(type)){
			return this.getLength()/3;
		}
    	if("Text".equalsIgnoreCase(type)){
    		return this.getLength()/3;
    	}
		if("TextArea".equalsIgnoreCase(type)){
    		Integer maxLength = this.getLength()/3;
			return maxLength;
		}
		if("Quest".equalsIgnoreCase(type)){
			return this.getLength()/3;
		}
		if("Ratio".equalsIgnoreCase(type))return 10;
		if("Decimal".equalsIgnoreCase(type))return 20;
		if("Number".equalsIgnoreCase(type))return 9;
		if("Float".equalsIgnoreCase(type))return 13;
		if("Select".equalsIgnoreCase(type))return 30;
		if("MultipleSelect".equalsIgnoreCase(type))return 180;

		return -1;
    }
    
    /**
     * 
     * @param data
     * @return
     */
    public Object getValue(Object data){
    	return getValue(data, false);
    }


	public Object getValue(Object data, boolean isShow){
		return getValue(data, isShow, true);
	}

	/**
	 *
	 * @param data
	 * @param isShow 如果展示，进行脱敏，格式化处理
	 * @param tm 是否脱敏
	 * @return
	 */
	public Object getValue(Object data, boolean isShow, boolean tm){
		if(data!=null){
			if(data instanceof DataRow){
				String column = this.getColumn();
				Object val = Reader.objectReader().getValue(data, column);
				if (isShow && val != null) {
					if (tm) {
						if ("IDENTIFY_NUM".equals(column)) {
							Object identifyType = Reader.objectReader().getValue(data, "IDENTIFY_TYPE");
							val = IdCardUtil.getIdentifyNumX(String.valueOf(val), String.valueOf(identifyType),2);
						}else if ("SPOUSE_IDENTIFY_NUM".equals(column)) {
							Object identifyType = Reader.objectReader().getValue(data, "SPOUSE_IDENTIFY_TYPE");
							val = IdCardUtil.getIdentifyNumX(String.valueOf(val), String.valueOf(identifyType),2);
						}else if("ID_CARD".equals(column)){
							val = IdCardUtil.getIdentifyNumX(String.valueOf(val),2);
						}else if ("PHONE_NUMBER".equals(column) || "TEL_NUM".equals(column) || "SPOUSE_PHONE_NUM".equals(column) || "COMPANY_TEL_NUM".equals(column)) {
							val = IdCardUtil.getTelNumX(String.valueOf(val),2);
						}
					}
					if ("Decimal".equals(this.getFieldType())) {
						val = IdCardUtil.getFormatMoney(String.valueOf(val));
					}

				}
				return val;
			}else{
				String propertyName = getPropertyNameByColumnName(this.getColumn());
				Object val = Reader.objectReader().getValue(data, propertyName);
				return val;
			}
		}else{
			return this.getDefaultValue();
		}
	}



	public boolean isEmpty(Object data){
		Object val = getValue(data);
		if(val!=null){
			if(val instanceof String){
				String str = (String)val;
				return "".equals(str.trim());
			}
			return false;
		}
		return true;
	}

    
    public String getPropertyName(){
    	String columnName = this.getColumn();
    	if(StringUtil.isNotEmpty(columnName)){
    		return this.getPropertyNameByColumnName(columnName);
    	}
    	return "";
    }
    
    public String getPropertyEndName(){
    	return getPropertyName()+"End";
    }
    
    /**
     * 
     * @param columnName
     * @return
     */
    private String getPropertyNameByColumnName(String columnName){
    	String[] fs = columnName.split("\\_");
		String str="";
		for(int i=0;i<fs.length;i++){
			if(i==0)str+=fs[i].toLowerCase();
			else str+=fs[i].substring(0,1).toUpperCase()+fs[i].substring(1).toLowerCase();
		}
		return str;
    }
	
}
