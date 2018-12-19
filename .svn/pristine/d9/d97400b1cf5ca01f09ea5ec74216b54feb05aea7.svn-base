package banger.dao.intf;

import banger.domain.config.AutoTableInfo;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * 自定义表信息数据访问接口
 */
public interface ILoanTableInfoDao {

	/**
	 * 新增自定义表信息
	 * 
	 * @param tableInfo
	 *            实体对像
	 */
	void insertTableInfo(AutoTableInfo tableInfo);

	/**
	 * 修改自定义表信息
	 * 
	 * @param tableInfo
	 *            实体对像
	 */
	void updateTableInfo(AutoTableInfo tableInfo);

	/**
	 * 通过主键删除自定义表信息
	 * 
	 * @param tableId
	 *            主键Id
	 */
	void deleteTableInfoById(Integer tableId);

	/**
	 * 通过主键得到自定义表信息
	 * 
	 * @param tableId
	 *            主键Id
	 */
	AutoTableInfo getTableInfoById(Integer tableId);

	/**
	 * 查询自定义表信息
	 * 
	 * @param condition
	 *            查询条件
	 * @return
	 */
	List<AutoTableInfo> queryTableInfoList(Map<String, Object> condition);

	/**
	 * 分页查询自定义表信息
	 * 
	 * @param condition
	 *            查询条件
	 * @param page
	 *            分页对像
	 * @return
	 */
	IPageList<AutoTableInfo> queryTableInfoList(Map<String, Object> condition, IPageSize page);

}
