package banger.framework.sql.mapping.asserts;

import banger.framework.sql.SqlMapException;
import banger.framework.sql.dialect.IDialect;
import banger.framework.sql.mapping.ISqlClauseAssert;
import banger.framework.util.StringUtil;

/**
 * Created by Administrator on 2017/8/4.
 */
public class IsDbType implements ISqlClauseAssert {

    @Override
    public String getName() {
        return "isDbType";
    }

    @Override
    public boolean invoke(IDialect dialect,Object... args) {
        if(args.length>0){
            if(args[0]!=null){
                return args[0].equals(dialect.getDbType());
            }
        }
        return false;
    }
}
