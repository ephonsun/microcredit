package banger.framework.sql.driver;

import banger.framework.sql.command.IDbConnection;
import banger.framework.sql.command.SqlConnection;
import banger.framework.sql.config.IDbConfig;
import banger.framework.sql.dialect.IDialect;
import banger.framework.util.StringUtil;

/**
 * 数据库Sql组件供应类
 */
public class SqlDataProvider extends AbstractDataProvider {

	protected SqlDataProvider() {
	}

	public SqlDataProvider(IDbConfig config)
    {
        this.onit(config);
    }

	/**
	 * 初始化数组库组件供应类
	 * @param config
	 */
    private void onit(IDbConfig config)
    {
        this.config = config;
        this.connection = new SqlConnection(config);
        
        if(!StringUtil.isNullOrEmpty(config.getDialectClass())){
        	try {
				Class<?> clazz = Class.forName(config.getDialectClass());
				IDialect dialect = (IDialect)clazz.newInstance();
				this.setDialect(dialect);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
        	
        }
		this.command = this.connection.createCommand();
    }
}
