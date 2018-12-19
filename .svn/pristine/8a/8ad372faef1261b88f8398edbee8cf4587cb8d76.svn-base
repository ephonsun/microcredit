package banger.service.impl;

import banger.dao.intf.ILoanTableInfoDao;
import banger.dao.intf.ILoanTemplateExtendDao;
import banger.domain.config.AutoTableInfo;
import banger.framework.collection.DataTable;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.service.intf.IAutoTableInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 自定义表信息业务访问类
 */
@Repository
public class AutoTableInfoService implements IAutoTableInfoService {

	@Autowired
	private ILoanTableInfoDao tableInfoDao;
	
	@Autowired
	private ILoanTemplateExtendDao loanTemplateExtendDao;

	/**
	 * 新增自定义表信息
	 * 
	 * @param tableInfo
	 *            实体对像
	 * @param loginUserId
	 *            登入用户Id
	 */
	public void insertTableInfo(AutoTableInfo tableInfo, Integer loginUserId) {
		tableInfo.setCreateUser(loginUserId);
		tableInfo.setCreateDate(DateUtil.getCurrentDate());
		tableInfo.setUpdateUser(loginUserId);
		tableInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.tableInfoDao.insertTableInfo(tableInfo);
	}

	/**
	 * 修改自定义表信息
	 * 
	 * @param tableInfo
	 *            实体对像
	 * @param loginUserId
	 *            登入用户Id
	 */
	public void updateTableInfo(AutoTableInfo tableInfo, Integer loginUserId) {
		tableInfo.setUpdateUser(loginUserId);
		tableInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.tableInfoDao.updateTableInfo(tableInfo);
	}

	/**
	 * 通过主键删除自定义表信息
	 *            主键Id
	 */
	public void deleteTableInfoById(Integer tableId) {
		this.tableInfoDao.deleteTableInfoById(tableId);
	}

	/**
	 * 通过主键得到自定义表信息
	 *            主键Id
	 */
	public AutoTableInfo getTableInfoById(Integer tableId) {
		return this.tableInfoDao.getTableInfoById(tableId);
	}

	/**
	 * 查询自定义表信息
	 * 
	 * @param condition
	 *            查询条件
	 * @return
	 */
	public List<AutoTableInfo> queryTableInfoList(Map<String, Object> condition) {
		return this.tableInfoDao.queryTableInfoList(condition);
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
	public IPageList<AutoTableInfo> queryTableInfoList(Map<String, Object> condition, IPageSize page) {
		return this.tableInfoDao.queryTableInfoList(condition, page);
	}

	public DataTable getTemplateDataById(String tableName, Integer loanId,
			String precType) {
		return loanTemplateExtendDao.getTemplateDataById(precType, tableName, loanId);
	}

	public DataTable getTemplateDataById(String tableName, Integer loanId,
										 String precType,String extendCondition) {
		return loanTemplateExtendDao.getTemplateDataById(precType, tableName, loanId,extendCondition);
	}

}
