package banger.domain.config;

import banger.framework.codetable.CodeTableUtil;

/**
 * Created by hgx on 2017/10/10.
 */
public class IntoTrialRuleDetailQuery extends  IntoTrialRuleDetail {
    private String tableName;  //模板名称
    private String fieldName; //字段名
    private String fieldDicisName;//字典
    private String optionParam1;//参数1
    private String optionParam2;//参数2
    private String optionParam3;//参数3
    private String optionParam4;//参数4
    private String fieldType;//字段类型
    private String optionValue; //参数值 拼接好的

    public String getFieldDicisName() {
        return fieldDicisName;
    }

    public void setFieldDicisName(String fieldDicisName) {
        this.fieldDicisName = fieldDicisName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOptionParam1() {
        return optionParam1;
    }

    public void setOptionParam1(String optionParam1) {
        this.optionParam1=optionParam1;
    }

    public String getOptionParam2() {
        return optionParam2;
    }

    public void setOptionParam2(String optionParam2) {
        this.optionParam2 =optionParam2;
    }

    public String getOptionParam3() {
        return optionParam3;
    }

    public void setOptionParam3(String optionParam3) {
        this.optionParam3 =optionParam3;
    }

    public String getOptionParam4() {
        return optionParam4;
    }

    public void setOptionParam4(String optionParam4) {
        this.optionParam4 = optionParam4;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getOptionValue() {
        //拼接 值
        //--判断是多选 还是数字
        if(fieldType.equals("Number"))
        {
            //如果是数字的话 就用~拼接 值1和值2
            this.optionValue=optionParam1+"~"+optionParam2;
        }else{
            //如果不是数字的话  就拼接所有的值 用,分割
            String[] ss = optionParam1.split(",");
            this.optionValue="";
            for(int i=0;i<ss.length;i++)
            {
                if(i==0)
                {
                    this.optionValue+= CodeTableUtil.getCodeTableValue("cdDictColByName", fieldDicisName,ss[i]);

                }else{
                    this.optionValue+=","+ CodeTableUtil.getCodeTableValue("cdDictColByName", fieldDicisName,ss[i]);
                }
            }

        }
        return optionValue;
    }

}
