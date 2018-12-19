package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.AutoTableColumnSync;

/**
 * 数据字段同步表数据访问接口
 */
public interface ITableColumnSyncDao {

	/**
	 * 新增数据字段同步表
	 * @param tableColumnSync 实体对像
	 */
	void insertTableColumnSync(AutoTableColumnSync tableColumnSync);

	/**
	 *修改数据字段同步表
	 * @param tableColumnSync 实体对像
	 */
	void updateTableColumnSync(AutoTableColumnSync tableColumnSync);

	/**
	 * 通过主键删除数据字段同步表
	 * @param sourceTable 主键Id
	 */
	void deleteTableColumnSyncById(String sourceTable);

	/**
	 * 通过主键得到数据字段同步表
	 * @param sourceTable 主键Id
	 */
	AutoTableColumnSync getTableColumnSyncById(String sourceTable);

	/**
	 * 查询数据字段同步表
	 * @param condition 查询条件
	 * @return
	 */
	List<AutoTableColumnSync> queryTableColumnSyncList(Map<String,Object> condition);

	/**
	 * 分页查询数据字段同步表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<AutoTableColumnSync> queryTableColumnSyncList(Map<String,Object> condition,IPageSize page);

}
