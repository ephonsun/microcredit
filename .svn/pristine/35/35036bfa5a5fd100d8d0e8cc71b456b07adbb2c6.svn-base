package banger.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import banger.dao.intf.ICustomerDataDao;
import banger.framework.collection.DataTable;
import banger.service.intf.ICustomerDataService;

/**
 * 
 * @author zhusw
 *
 */
@Repository
public class CustomerDataService implements ICustomerDataService {
	
	@Autowired
	private ICustomerDataDao customerDataDao;

	/**
	 * 得到客户数据
	 * @param tableName
	 * @param id
	 * @return
	 */
	public DataTable getDataTableById(String tableName,Integer id){
		return customerDataDao.getDataTableById(tableName, id);
	}
	
}
