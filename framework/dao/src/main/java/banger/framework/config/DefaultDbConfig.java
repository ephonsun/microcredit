package banger.framework.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import banger.framework.sql.SqlDriverException;
import banger.framework.sql.config.IDbConfig;
import banger.framework.util.StringUtil;

public class DefaultDbConfig implements IDbConfig {
	protected String dbType;					//数量库类型
	protected String name;					//名称
	protected DataSource dataSource;			//数据源
	protected String dialectClass;				//方言
	protected String driverClass;
	protected Map<String,String> dialects;		//
	protected Map<String,String> drivers;		//
	private Map<String,String> options;
	protected boolean isDefault;				//

	public String getDbType() {
		return dbType;
	}
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Connection getConnection() throws SQLException {
		if(this.dataSource!=null){
			return this.dataSource.getConnection();
		}else throw new SqlDriverException("数据库连接未配置");
	}
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setDialectClass(String dialectClass) {
		this.dialectClass = dialectClass;
	}
	public void setDialects(Map<String, String> dialects) {
		this.dialects = dialects;
	}
	public void setOptions(Map<String, String> options) {
		this.options = options;
	}
	public String getDialectClass() {
		if(!StringUtil.isNullOrEmpty(this.dialectClass))
			return this.dialectClass;
		else if(!StringUtil.isNullOrEmpty(this.getDbType()))
		{
			Object obj=this.dialects.get(this.getDbType().toLowerCase());
			return (obj!=null)?(String)obj:null;
		}
		return null;
	}
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}
	public void setDrivers(Map<String, String> drivers) {
		this.drivers = drivers;
	}
	public String getDriverClass() {
		if(!StringUtil.isNullOrEmpty(this.driverClass))
			return this.driverClass;
		else if(!StringUtil.isNullOrEmpty(this.getDbType())){
			Object obj=this.drivers.get(this.getDbType().toLowerCase());
			return (obj!=null)?(String)obj:null;
		}
		return null;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	public int compareTo(IDbConfig config) {
		return this.getName().compareTo(config.getName());
	}
	public String getOption(String key){
		if(this.options!=null && this.options.containsKey(key)){
			return this.options.get(key);
		}
		return null;
	}
	
}