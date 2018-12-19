package banger.framework.sql.mapping.methods;

import banger.framework.sql.builder.SqlString;
import banger.framework.sql.config.IDbConfig;
import banger.framework.sql.dialect.IDialect;
import banger.framework.sql.mapping.ISqlMethod;
import banger.framework.sql.util.ISqlEngine;
import banger.framework.sql.util.SqlEngine;

public class GetSqlValue implements ISqlMethod {

	@Override
	public String getName() {
		return "getSqlValue";
	}

	@Override
	public SqlString invoke(IDbConfig config, IDialect dialect, Object... args) {
		ISqlEngine ish = SqlEngine.instance(config.getName());
		if(args.length>0){
			String sqlId = (String)args[0];
			Object[] sqlArgs = new Object[args.length-1];
			for(int i=1;i<args.length;i++)sqlArgs[i-1]=args[i];
			Object val = ish.queryValue(sqlId, sqlArgs);
			if(val!=null)return new SqlString(val.toString());
		}
		return null;
	}
	

}
