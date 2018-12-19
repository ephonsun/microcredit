package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.moduleIntf.ITableInfoProvider;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.ITableInfoDao;
import banger.service.intf.ITableInfoService;
import banger.domain.config.AutoTableInfo;
import org.springframework.stereotype.Service;

/**
 * 业务访问类
 */
@Service
public class TableInfoService implements ITableInfoService, ITableInfoProvider {

	@Autowired
	private ITableInfoDao tableInfoDao;

	/**
	 * 新增
	 * @param tableInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertTableInfo(AutoTableInfo tableInfo,Integer loginUserId){
		tableInfo.setCreateUser(loginUserId);
		tableInfo.setCreateDate(DateUtil.getCurrentDate());
		tableInfo.setUpdateUser(loginUserId);
		tableInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.tableInfoDao.insertTableInfo(tableInfo);
	}

	/**
	 *修改
	 * @param tableInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateTableInfo(AutoTableInfo tableInfo,Integer loginUserId){
		tableInfo.setUpdateUser(loginUserId);
		tableInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.tableInfoDao.updateTableInfo(tableInfo);
	}

	/**
	 * 通过主键删除
	 */
	public void deleteTableInfoById(Integer tableId){
		this.tableInfoDao.deleteTableInfoById(tableId);
	}

	/**
	 * 通过主键得到
	 */
	public AutoTableInfo getTableInfoById(Integer tableId){
		return this.tableInfoDao.getTableInfoById(tableId);
	}


	/**
	 * 查询
	 * @return
	 */
	public List<AutoTableInfo> queryAllTableInfoList(){
		return tableInfoDao.queryAllTableInfoList();
	}
	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	public List<AutoTableInfo> queryTableInfoList(Map<String,Object> condition){
		return this.tableInfoDao.queryTableInfoList(condition);
	}

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<AutoTableInfo> queryTableInfoList(Map<String,Object> condition,IPageSize page){
		return this.tableInfoDao.queryTableInfoList(condition,page);
	}

	@Override
	public List<AutoTableInfo> queryHiddenFormList(String formname) {
		return this.tableInfoDao.queryHiddenFormList(formname);
	}

	@Override
	public List<AutoTableInfo> queryControlFormList() {
		return this.tableInfoDao.queryControlFormList();
	}

}
