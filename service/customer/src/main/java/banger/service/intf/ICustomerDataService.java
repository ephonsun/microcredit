package banger.service.intf;

import banger.framework.collection.DataTable;

public interface ICustomerDataService {

	/**
	 * 得到客户数据
	 * @param tableName
	 * @param id
	 * @return
	 */
	DataTable getDataTableById(String tableName,Integer id);
	
}
