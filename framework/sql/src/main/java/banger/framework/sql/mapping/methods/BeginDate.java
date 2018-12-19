package banger.framework.sql.mapping.methods;

import java.util.Date;

import banger.framework.sql.builder.Parameter;
import banger.framework.sql.builder.SqlString;
import banger.framework.sql.config.IDbConfig;
import banger.framework.sql.dialect.IDialect;
import banger.framework.sql.mapping.ISqlMethod;
import banger.framework.util.DateUtil;
import banger.framework.util.TypeUtil;

public class BeginDate implements ISqlMethod {

	public String getName() {
		return "beginDate";
	}

	public SqlString invoke(IDbConfig dbConfig,IDialect dialect,Object... args) {
		if(args.length>0){
			Object arg = args[0];
			if(arg!=null){
				boolean paramFlag = arg instanceof Parameter;
				Object date = TypeUtil.changeType((paramFlag)?((Parameter)arg).getValue():arg,Date.class);
				if(date instanceof Date){
					Date val = DateUtil.clearTime((Date)date);
					if(paramFlag){
						return (SqlString)dialect.toDate(new Parameter(val));
					}else{
						return new SqlString(dialect.toDate(val));
					}
				}
			}
		}
		return null;
	}
}