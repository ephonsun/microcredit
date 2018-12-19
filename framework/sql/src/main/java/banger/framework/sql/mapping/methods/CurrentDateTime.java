package banger.framework.sql.mapping.methods;

import banger.framework.sql.builder.SqlString;
import banger.framework.sql.config.IDbConfig;
import banger.framework.sql.dialect.IDialect;
import banger.framework.sql.mapping.ISqlMethod;

public class CurrentDateTime implements ISqlMethod {

	public String getName() {
		return "currentDateTime";
	}

	public SqlString invoke(IDbConfig config,IDialect dialect, Object... args) {
		return new SqlString(dialect.getCurrentDateTime());
	}

}