package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IImportHistoryDao;
import banger.domain.system.SysImportHistory;

/**
 * 导入历史表数据访问类
 */
@Repository
public class ImportHistoryDao extends PageSizeDao<SysImportHistory> implements IImportHistoryDao {

	/**
	 * 新增导入历史表
	 * @param importHistory 实体对像
	 */
	public void insertImportHistory(SysImportHistory importHistory){
		importHistory.setId(this.newId().intValue());
		this.execute("insertImportHistory",importHistory);
	}

	/**
	 *修改导入历史表
	 * @param importHistory 实体对像
	 */
	public void updateImportHistory(SysImportHistory importHistory){
		this.execute("updateImportHistory",importHistory);
	}

	/**
	 * 通过主键删除导入历史表
	 * @param id 主键Id
	 */
	public void deleteImportHistoryById(Integer id){
		this.execute("deleteImportHistoryById",id);
	}

	/**
	 * 通过主键得到导入历史表
	 * @param id 主键Id
	 */
	public SysImportHistory getImportHistoryById(Integer id){
		return (SysImportHistory)this.queryEntity("getImportHistoryById",id);
	}

	/**
	 * 查询导入历史表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysImportHistory> queryImportHistoryList(Map<String,Object> condition){
		return (List<SysImportHistory>)this.queryEntities("queryImportHistoryList", condition);
	}

	/**
	 * 分页查询导入历史表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<SysImportHistory> queryImportHistoryList(Map<String,Object> condition,IPageSize page){
		return (IPageList<SysImportHistory>)this.queryEntities("queryImportHistoryList", page, condition);
	}

}
