package banger.framework.sql.mapping;

import banger.framework.sql.builder.SqlString;
import banger.framework.sql.config.IDbConfig;
import banger.framework.sql.dialect.IDialect;

public class SqlItemCount extends SqlItem {
	private SqlString sqlString;
	
	public SqlItemCount(String sqlId,SqlString sqlString){
		super(sqlId);
		this.sqlString = sqlString;
	}

	/**
	 * 生成Sql语句
	 */
	@Override
	public SqlString toSqlString(Object ... params) {
		return this.sqlString;
	}

	/**
	 * 生成Sql语句
	 */
	@Override
	public SqlString toSqlString(IDialect dialect,Object ... params) {
		return this.sqlString;
	}
	
	/**
	 * 生成Sql语句
	 */
	@Override
	public SqlString toSqlString(IDbConfig config,IDialect dialect,Object ... params) {
		return this.sqlString;
	}
	
}
