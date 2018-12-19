package banger.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import banger.framework.dao.PageSizeDao;
import banger.dao.intf.ITableSyncDao;
import banger.domain.config.AutoTableSync;

/**
 * 数据访问类
 */
@Repository
public class TableSyncDao extends PageSizeDao<AutoTableSync> implements ITableSyncDao {

	/**
	 * 得到所有数据
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AutoTableSync> getAllTableSyncList(){
		return (List<AutoTableSync>)this.queryEntities("getAllTableSyncList","");
	}


}
