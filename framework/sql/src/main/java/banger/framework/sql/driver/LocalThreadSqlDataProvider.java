package banger.framework.sql.driver;

import banger.framework.sql.command.IDbConnection;
import banger.framework.sql.command.LocalThreadSqlConnection;
import banger.framework.sql.command.SqlConnection;
import banger.framework.sql.config.IDbConfig;
import banger.framework.sql.dialect.IDialect;
import banger.framework.util.StringUtil;

import java.sql.Connection;

/**
 * Created by zhusw on 2017/12/19.
 */
public class LocalThreadSqlDataProvider extends AbstractDataProvider {

    protected LocalThreadSqlDataProvider() {
    }

    public LocalThreadSqlDataProvider(IDbConfig config)
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
        this.connection = new LocalThreadSqlConnection(config);

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
