package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.moduleIntf.IImportHistoryProvider;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IImportHistoryDao;
import banger.service.intf.IImportHistoryService;
import banger.domain.system.SysImportHistory;

/**
 * 导入历史表业务访问类
 */
@Repository
public class ImportHistoryService implements IImportHistoryService,IImportHistoryProvider {

	@Autowired
	private IImportHistoryDao importHistoryDao;

	/**
	 * 新增导入历史表
	 * @param importHistory 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertImportHistory(SysImportHistory importHistory,Integer loginUserId){
		importHistory.setCreateDate(DateUtil.getCurrentDate());
		importHistory.setUpdateDate(DateUtil.getCurrentDate());
		this.importHistoryDao.insertImportHistory(importHistory);
	}

	/**
	 *修改导入历史表
	 * @param importHistory 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateImportHistory(SysImportHistory importHistory,Integer loginUserId){
		importHistory.setUpdateDate(DateUtil.getCurrentDate());
		this.importHistoryDao.updateImportHistory(importHistory);
	}

	/**
	 * 通过主键删除导入历史表
	 * @param id 主键Id
	 */
	public void deleteImportHistoryById(Integer id){
		this.importHistoryDao.deleteImportHistoryById(id);
	}

	/**
	 * 通过主键得到导入历史表
	 * @param id 主键Id
	 */
	public SysImportHistory getImportHistoryById(Integer id){
		return this.importHistoryDao.getImportHistoryById(id);
	}

	/**
	 * 查询导入历史表
	 * @param condition 查询条件
	 * @return
	 */
	public List<SysImportHistory> queryImportHistoryList(Map<String,Object> condition){
		return this.importHistoryDao.queryImportHistoryList(condition);
	}

	/**
	 * 分页查询导入历史表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<SysImportHistory> queryImportHistoryList(Map<String,Object> condition,IPageSize page){
		return this.importHistoryDao.queryImportHistoryList(condition,page);
	}

}
