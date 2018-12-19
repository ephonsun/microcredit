package banger.service.impl;

import banger.framework.sql.builder.SqlString;
import banger.framework.sql.config.IDbConfig;
import banger.framework.sql.dialect.IDialect;
import banger.framework.sql.mapping.ISqlMethod;
import banger.service.intf.IDeptListService;

import java.util.List;

/**
 * Created by Administrator on 15-5-8.
 */
public class DeptPermitMethod implements ISqlMethod {
    private IDeptListService deptListService;

    public void setDeptListService(IDeptListService deptListService) {
        this.deptListService = deptListService;
    }

    public String getName() {
        return "deptDataPermit";
    }

    public SqlString invoke(IDbConfig config, IDialect dialect, Object... args) {
        Integer userId = (Integer)args[0];
        Integer roleId = (Integer)args[1];
        List<String> codes = deptListService.getDeptSearchCodesByUserId(userId,roleId);
        if(codes.size()>0){
            String sql = "";
            for(String code : codes){
               if("".equals(sql)){
                   sql+="DEPT_SEARCH_CODE like '"+code+"%'";
               }else{
                   sql+=" OR DEPT_SEARCH_CODE like '"+code+"%'";
               }
            }
            return new SqlString(sql);
        }else{
            return new SqlString("1=0");
        }
    }
}
