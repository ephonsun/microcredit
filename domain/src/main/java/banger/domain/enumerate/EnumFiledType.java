package banger.domain.enumerate;

import banger.framework.sql.command.ValueType;

/**
 * 类解释
 *
 * @Version 1.0 2017/5/2
 * @Author Merlin
 */
public enum EnumFiledType {
    DATE("Date", "日期", ValueType.TimeStamp, 1, 16),
    TEXT("Text", "短文本", ValueType.Varchar, 2, new int[]{60,90,120,150}),
    TEXTAREA("TextArea", "长文本", ValueType.Varchar, 3, new int[]{600,900,1200,1500}),
    SELECT("Select", "单选下拉框", ValueType.Varchar, 4, 30),
    MULTIPLE_SELECT("MultipleSelect", "多选下拉框", ValueType.Varchar , 5, 180),
    YES_NO("YesNo", "是否", ValueType.Varchar , 6, 3),
    NUMBER("Number", "整数", ValueType.Integer, 7, 4),
    FLOAT("Float", "小数", ValueType.Decimal, 8, 17),
    DECIMAL("Decimal", "金额", ValueType.Decimal, 9, 18),
    ADDRESS("Address", "地址", ValueType.Varchar, 10, 300),
    RATIO("Ratio","百分比",ValueType.Decimal, 11, 15),
    QUEST("Quest","问卷",ValueType.Varchar,12,450),
    DROP_POPUP("DropPopup","弹出选择页面",ValueType.Varchar,12,60),
    ;
    public final String fieldType;			//字段类型
    public final String typeName;
    public final int order;
    public final int dataLength;				//数据长度
    public final int[] dataLengths;				//数据长度
    public final ValueType valueType;			//数据库类型

    EnumFiledType(String fieldType, String typeName,ValueType valueType, int order,int dataLength) {
        this.fieldType = fieldType;
        this.typeName = typeName;
        this.valueType = valueType;
        this.order = order;
        this.dataLength = dataLength;
        this.dataLengths = new int[]{dataLength};
    }
    
    EnumFiledType(String fieldType, String typeName,ValueType valueType, int order,int[] dataLengths) {
        this.fieldType = fieldType;
        this.typeName = typeName;
        this.valueType = valueType;
        this.order = order;
        this.dataLength = dataLengths[dataLengths.length-1];
        this.dataLengths = dataLengths;
    }

    public boolean equalType(String type){
        return this.fieldType.equalsIgnoreCase(type);
    }
    
    public int getInputLength(int level){
    	return dataLengths[level];
    }

    public String getFieldType() {
        return fieldType;
    }

    public String getTypeName() {
        return typeName;
    }

    /**
     * 判断是字符串类型
     * @return
     */
    public boolean isStringType(){
    	return this==TEXT || this == TEXTAREA || this == SELECT || this == MULTIPLE_SELECT || this == YES_NO || this == ADDRESS || this == QUEST || this == DROP_POPUP;
    }
    
    public static EnumFiledType valueOfType(String type){
    	for(EnumFiledType fieldType : EnumFiledType.values()){
			if(fieldType.fieldType.equalsIgnoreCase(type)){
				return fieldType;
			}
		}
		return null;
    }

}
