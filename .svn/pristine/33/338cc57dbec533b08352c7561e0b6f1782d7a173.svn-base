package banger.common.aspect;

import banger.domain.enumerate.SexEnum;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.metadata.IDbMetaStore;
import banger.framework.reader.Reader;
import banger.framework.spring.SpringContext;
import banger.framework.sql.meta.Column;
import banger.framework.sql.util.SqlHelper;
import banger.framework.util.IdCardUtil;
import banger.framework.util.StringUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 拦截DataDao操作，提取身份证里的生日，年龄，性别信息
 * Created by zhusw on 2017/12/20.
 */
@Component
@Aspect
public class DataDaoAspect {

    private static final String DbMetaStoreKey = "DbMetaStore";

    protected IDbMetaStore getDbMetaStore(){
        return (IDbMetaStore) SpringContext.instance().get(DbMetaStoreKey);
    }

    @Pointcut(value = "@annotation(banger.common.annotation.DataDaoAnnotation)")
    private void pointcut() {

    }

    @Before(value = "pointcut()")
    public void beforeAdvice(JoinPoint point) {
        DataTable table = (DataTable)point.getArgs()[0];
        if(table!=null && table.rowSize()>0){
            if("CUST_BASIC_INFO".equalsIgnoreCase(table.getName())){
                for(DataRow row : table.getRows()){
                    setInfoToTableByIdCard(row,"IDENTIFY_TYPE","IDENTIFY_NUM","CUSTOMER_SEX","CUSTOMER_AGE","CUSTOMER_BIRTHDAY");
                }
            }else if("LBIZ_PERSONAL_INFO".equalsIgnoreCase(table.getName())){
                for(DataRow row : table.getRows()){
                    setInfoToTableByIdCard(row,"IDENTIFY_TYPE","IDENTIFY_NUM","CUSTOMER_SEX","CUSTOMER_AGE","CUSTOMER_BIRTHDAY");
                }
            }
        }
    }

    private void setInfoToTableByIdCard(DataRow row,String idCardTypeColumn,String idCardColumn,String sexColumn,String ageColumn,String birthdayColumn){
        String idCardType = Reader.stringReader().getValue(row,idCardTypeColumn);
        String idCard = Reader.stringReader().getValue(row,idCardColumn);
        if(StringUtil.isNotEmpty(idCard)) {
            if ("01".equals(idCardType) || "1".equals(idCardType)) {
                if(row.getTable().contains(sexColumn)) {
                    String sex = Reader.stringReader().getValue(row, sexColumn);
                    if (StringUtil.isNullOrEmpty(sex)) {
                        sex = IdCardUtil.getSexByIdCard(idCard);
                        if (StringUtil.isNotEmpty(sex)) {
                            String sexType = SexEnum.getTypeName(sex);
                            row.set(sexColumn, sexType);
                        }
                    }
                }
                if(row.getTable().contains(ageColumn)) {
                    String age = Reader.stringReader().getValue(row, ageColumn);
                    if (StringUtil.isNullOrEmpty(age)) {
                        Integer val = IdCardUtil.getAgeByIdCard(idCard);
                        if (val!=null)
                            row.set(ageColumn, val);
                    }
                }
                if(row.getTable().contains(birthdayColumn) || isExistColumnInTable(row.getTable().getName(),birthdayColumn)) {
                    String birthday = Reader.stringReader().getValue(row, birthdayColumn);
                    if (StringUtil.isNullOrEmpty(birthday)) {
                        Date val = IdCardUtil.getBirthdayByIdCard(idCard);
                        if (val!=null)
                            row.set(birthdayColumn, val);
                    }
                }
            }
        }
    }

    /**
     * 判断数据库里是否有这个字段
     * @param tableName
     * @param columnName
     * @return
     */
    private boolean isExistColumnInTable(String tableName,String columnName){
        return getDbMetaStore().getColumn(tableName, columnName)!=null;
    }

}
