package banger.framework.sql.mapping.methods;

import banger.framework.sql.builder.Parameter;
import banger.framework.sql.builder.SqlString;
import banger.framework.sql.config.IDbConfig;
import banger.framework.sql.dialect.IDialect;
import banger.framework.sql.mapping.ISqlMethod;
import banger.framework.sql.util.SqlType;
import banger.framework.util.TypeUtil;

public class Like implements ISqlMethod {
	@Override
	public String getName() {
		return "like";
	}

	@Override
	public SqlString invoke(IDbConfig config,IDialect dialect, Object... args) {
		if(args.length>0)
		{
			Object arg = args[0];
			if(arg!=null)
			{
				if(arg instanceof Parameter)
				{
					String val = (String)TypeUtil.changeType(((Parameter)arg).getValue(),String.class);
					return new SqlString(new Object[]{" like ",new Parameter("%"+val+"%")} );
				}
				else
				{
					String val = SqlType.toSqlString(arg);
					if(val.charAt(0)=='\'' && val.charAt(val.length()-1)=='\'')
					{
						val = "'%"+val.substring(1,val.length()-1)+"%'";
					}
					else {
						val = "'%"+val+"%'";
					}
					return new SqlString(" like "+val);
				}
			}
		}
		return null;
	}
}
