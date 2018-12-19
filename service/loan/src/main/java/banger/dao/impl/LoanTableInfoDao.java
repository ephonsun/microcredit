package banger.dao.impl;

import banger.dao.intf.ILoanTableInfoDao;
import banger.domain.config.AutoTableInfo;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 自定义表信息数据访问类
 */
@Repository
public class LoanTableInfoDao extends PageSizeDao<AutoTableInfo> implements ILoanTableInfoDao {

	/**
	 * 新增自定义表信息
	 * 
	 * @param tableInfo
	 *            实体对像
	 */
	public void insertTableInfo(AutoTableInfo tableInfo) {
		tableInfo.setTableId(this.newId().intValue());
		this.execute("insertTableInfo", tableInfo);
	}

	/**
	 * 修改自定义表信息
	 * 
	 * @param tableInfo
	 *            实体对像
	 */
	public void updateTableInfo(AutoTableInfo tableInfo) {
		this.execute("updateTableInfo", tableInfo);
	}

	/**
	 * 通过主键删除自定义表信息
	 * 
	 * @param tableId
	 *            主键Id
	 */
	public void deleteTableInfoById(Integer tableId) {
		this.execute("deleteTableInfoById", tableId);
	}

	/**
	 * 通过主键得到自定义表信息
	 * 
	 * @param tableId
	 *            主键Id
	 */
	public AutoTableInfo getTableInfoById(Integer tableId) {
		return (AutoTableInfo) this.queryEntity("getTableInfoById", tableId);
	}

	/**
	 * 查询自定义表信息
	 * 
	 * @param condition
	 *            查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AutoTableInfo> queryTableInfoList(Map<String, Object> condition) {
		return (List<AutoTableInfo>) this.queryEntities("queryTableInfoList", condition);
	}

	/**
	 * 分页查询自定义表信息
	 * 
	 * @param condition
	 *            查询条件
	 * @param page
	 *            分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<AutoTableInfo> queryTableInfoList(Map<String, Object> condition, IPageSize page) {
		return (IPageList<AutoTableInfo>) this.queryEntities("queryTableInfoList", page, condition);
	}

}
