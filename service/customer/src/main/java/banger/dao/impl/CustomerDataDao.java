package banger.dao.impl;

import org.springframework.stereotype.Repository;

import banger.dao.intf.ICustomerDataDao;
import banger.framework.collection.DataTable;
import banger.framework.dao.DataDao;
import banger.framework.sql.util.ISqlEngine;
import banger.framework.sql.util.SqlEngine;

@Repository
public class CustomerDataDao extends DataDao implements ICustomerDataDao {

	/**
	 * 得到客户数据
	 * @param tableName
	 * @param id
	 * @return
	 */
	public DataTable getDataTableById(String tableName,Integer id){
		ISqlEngine ish = ("".equals(dbConfig))?SqlEngine.instance():SqlEngine.instance(dbConfig);
		DataTable table = ish.getDataTable("SELECT * FROM "+tableName+" WHERE ID="+id);
		ish.dispose();
		return table;
	}
	
}
