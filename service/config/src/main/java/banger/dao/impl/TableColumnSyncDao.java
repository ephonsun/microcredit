package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.ITableColumnSyncDao;
import banger.domain.config.AutoTableColumnSync;

/**
 * 数据字段同步表数据访问类
 */
@Repository
public class TableColumnSyncDao extends PageSizeDao<AutoTableColumnSync> implements ITableColumnSyncDao {

	/**
	 * 新增数据字段同步表
	 * @param tableColumnSync 实体对像
	 */
	public void insertTableColumnSync(AutoTableColumnSync tableColumnSync){
		this.execute("insertTableColumnSync",tableColumnSync);
	}

	/**
	 *修改数据字段同步表
	 * @param tableColumnSync 实体对像
	 */
	public void updateTableColumnSync(AutoTableColumnSync tableColumnSync){
		this.execute("updateTableColumnSync",tableColumnSync);
	}

	/**
	 * 通过主键删除数据字段同步表
	 * @param sourceTable 主键Id
	 */
	public void deleteTableColumnSyncById(String sourceTable){
		this.execute("deleteTableColumnSyncById",sourceTable);
	}

	/**
	 * 通过主键得到数据字段同步表
	 * @param sourceTable 主键Id
	 */
	public AutoTableColumnSync getTableColumnSyncById(String sourceTable){
		return (AutoTableColumnSync)this.queryEntity("getTableColumnSyncById",sourceTable);
	}

	/**
	 * 查询数据字段同步表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AutoTableColumnSync> queryTableColumnSyncList(Map<String,Object> condition){
		return (List<AutoTableColumnSync>)this.queryEntities("queryTableColumnSyncList", condition);
	}

	/**
	 * 分页查询数据字段同步表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<AutoTableColumnSync> queryTableColumnSyncList(Map<String,Object> condition,IPageSize page){
		return (IPageList<AutoTableColumnSync>)this.queryEntities("queryTableColumnSyncList", page, condition);
	}

}
