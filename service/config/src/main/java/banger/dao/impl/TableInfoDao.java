package banger.dao.impl;

import banger.dao.intf.ITableInfoDao;
import banger.domain.config.AutoTableInfo;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据访问类
 */
@Repository
public class TableInfoDao extends PageSizeDao<AutoTableInfo> implements ITableInfoDao {

	/**
	 * 新增
	 * @param tableInfo 实体对像
	 */
	public void insertTableInfo(AutoTableInfo tableInfo){
		tableInfo.setTableId(this.newId().intValue());
		this.execute("insertTableInfo",tableInfo);
	}

	/**
	 *修改
	 * @param tableInfo 实体对像
	 */
	public void updateTableInfo(AutoTableInfo tableInfo){
		this.execute("updateTableInfo",tableInfo);
	}

	/**
	 * 通过主键删除
	 * @param tableId 主键Id
	 */
	public void deleteTableInfoById(Integer tableId){
		this.execute("deleteTableInfoById",tableId);
	}

	/**
	 * 通过主键得到
	 * @param tableId 主键Id
	 */
	public AutoTableInfo getTableInfoById(Integer tableId){
		return (AutoTableInfo)this.queryEntity("getTableInfoById",tableId);
	}
	
	/**
	 * 通过表名得到
	 * @param tableName 列名
	 */
	public AutoTableInfo getTableInfoByTableName(String tableName){
		return (AutoTableInfo)this.queryEntity("getTableInfoByTableName",tableName);
	}
	
	/**
	 * 查询
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AutoTableInfo> queryAllTableInfoList(){
		return (List<AutoTableInfo>)this.queryEntities("getAllTableInfoList", "");
	}
	
	/**
	 * 
	 * @param tableIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AutoTableInfo> getTableInfoListByTableIds(Integer[] tableIds){
		return (List<AutoTableInfo>)this.queryEntities("getTableInfoListByTableIds", new Object[]{tableIds});
	}
	
	/**
	 * 
	 * @param tableNames
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AutoTableInfo> getTableInfoListByTableNames(String[] tableNames){
		return (List<AutoTableInfo>)this.queryEntities("getTableInfoListByTableNames", new Object[]{tableNames});
	}

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AutoTableInfo> queryTableInfoList(Map<String,Object> condition){
		return (List<AutoTableInfo>)this.queryEntities("queryTableInfoList", condition);
	}

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<AutoTableInfo> queryTableInfoList(Map<String,Object> condition,IPageSize page){
		return (IPageList<AutoTableInfo>)this.queryEntities("queryTableInfoList", page, condition);
	}

	@Override
	public List<AutoTableInfo> getTableInfoListByLoanType(String loanTypeId, String precType, Integer tableId) {
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("loanTypeId",loanTypeId);
		condition.put("precType",precType);
		condition.put("tableId",tableId);

		return (List<AutoTableInfo>)this.queryEntities("getTableInfoListByLoanType", condition);
	}

	@Override
	public List<AutoTableInfo> queryHiddenFormList(String formname) {
		return (List<AutoTableInfo>)this.queryEntities("queryHiddenFormList",formname);
	}

	@Override
	public List<AutoTableInfo> queryControlFormList() {
		return (List<AutoTableInfo>)this.queryEntities("queryControleFormList");
	}

}
