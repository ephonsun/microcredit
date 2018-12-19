package banger.dao.intf;

import banger.domain.config.AutoTableInfo;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * 数据访问接口
 */
public interface ITableInfoDao {

	/**
	 * 新增
	 * @param tableInfo 实体对像
	 */
	void insertTableInfo(AutoTableInfo tableInfo);

	/**
	 *修改
	 * @param tableInfo 实体对像
	 */
	void updateTableInfo(AutoTableInfo tableInfo);

	/**
	 * 通过主键删除
	 * @param tableId 主键Id
	 */
	void deleteTableInfoById(Integer tableId);

	/**
	 * 通过主键得到
	 * @param tableId 主键Id
	 */
	AutoTableInfo getTableInfoById(Integer tableId);
	
	/**
	 * 通过表名得到
	 * @param tableName 列名
	 */
	AutoTableInfo getTableInfoByTableName(String tableName);
	
	/**
	 * 得到所有模板数据
	 * @return
	 */
	List<AutoTableInfo> queryAllTableInfoList();

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	List<AutoTableInfo> queryTableInfoList(Map<String, Object> condition);
	
	/**
	 * 得到表单
	 * @param tableIds
	 * @return
	 */
	List<AutoTableInfo> getTableInfoListByTableIds(Integer[] tableIds);
	
	/**
	 * 得到表单
	 * @param tableNames
	 * @return
	 */
	List<AutoTableInfo> getTableInfoListByTableNames(String[] tableNames);

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<AutoTableInfo> queryTableInfoList(Map<String, Object> condition, IPageSize page);


	List<AutoTableInfo> getTableInfoListByLoanType(String loanTypeId, String precType, Integer tableId);

	/**
	 *
	 * @return
	 */
	List<AutoTableInfo> queryHiddenFormList(String formname);

	List<AutoTableInfo> queryControlFormList();
}
