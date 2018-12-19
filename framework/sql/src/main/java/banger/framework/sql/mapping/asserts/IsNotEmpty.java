package banger.framework.sql.mapping.asserts;

import banger.framework.sql.dialect.IDialect;
import banger.framework.sql.mapping.ISqlClauseAssert;

/**
 * Created by Administrator on 2017/7/14.
 */
public class IsNotEmpty implements ISqlClauseAssert {
    @Override
    public String getName() {
        return "isNotEmpty";
    }

    @Override
    public boolean invoke(IDialect dialect,Object... args) {
        if(args.length==0)return true;
        for(int i=0;i<args.length;i++){
            Object arg = args[i];
            if(arg!=null && !"".equals(arg)){
                return true;
            }
        }
        return false;
    }
}
