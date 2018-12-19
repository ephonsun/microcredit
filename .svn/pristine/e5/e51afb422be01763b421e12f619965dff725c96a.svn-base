package banger.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.ITableDictDao;
import banger.service.intf.ITableDictService;
import banger.domain.config.AutoTableDict;

/**
 * 业务访问类
 */
@Repository
public class TableDictService implements ITableDictService {

	@Autowired
	private ITableDictDao tableDictDao;

	/**
	 * 新增
	 * @param tableDict 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertTableDict(AutoTableDict tableDict,Integer loginUserId){
		tableDict.setCreateUser(loginUserId);
		tableDict.setCreateDate(DateUtil.getCurrentDate());
		tableDict.setUpdateUser(loginUserId);
		tableDict.setUpdateDate(DateUtil.getCurrentDate());
		this.tableDictDao.insertTableDict(tableDict);
	}

	/**
	 *修改
	 * @param tableDict 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateTableDict(AutoTableDict tableDict,Integer loginUserId){
		tableDict.setUpdateUser(loginUserId);
		tableDict.setUpdateDate(DateUtil.getCurrentDate());
		this.tableDictDao.updateTableDict(tableDict);
	}

	/**
	 * 通过主键删除
	 * @param DICT_ID 主键Id
	 */
	public void deleteTableDictById(Integer dictId){
		this.tableDictDao.deleteTableDictById(dictId);
	}

	/**
	 * 通过主键得到
	 * @param DICT_ID 主键Id
	 */
	public AutoTableDict getTableDictById(Integer dictId){
		return this.tableDictDao.getTableDictById(dictId);
	}

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	public List<AutoTableDict> queryTableDictList(Map<String,Object> condition){
		return this.tableDictDao.queryTableDictList(condition);
	}

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<AutoTableDict> queryTableDictList(Map<String,Object> condition,IPageSize page){
		return this.tableDictDao.queryTableDictList(condition,page);
	}

}
