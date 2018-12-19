package banger.service.impl;

import banger.dao.intf.IColumnDictDao;
import banger.domain.config.AutoColumnDict;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.service.intf.IColumnDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 业务访问类
 */
@Repository
public class ColumnDictService implements IColumnDictService {

	@Autowired
	private IColumnDictDao columnDictDao;

	/**
	 * 新增
	 * @param columnDict 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertColumnDict(AutoColumnDict columnDict,Integer loginUserId){
		columnDict.setCreateUser(loginUserId);
		columnDict.setCreateDate(DateUtil.getCurrentDate());
		columnDict.setUpdateUser(loginUserId);
		columnDict.setUpdateDate(DateUtil.getCurrentDate());
		this.columnDictDao.insertColumnDict(columnDict);
	}

	/**
	 *修改
	 * @param columnDict 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateColumnDict(AutoColumnDict columnDict,Integer loginUserId){
		columnDict.setUpdateUser(loginUserId);
		columnDict.setUpdateDate(DateUtil.getCurrentDate());
		this.columnDictDao.updateColumnDict(columnDict);
	}

	/**
	 * 通过主键删除
	 * @param DICT_ID 主键Id
	 */
	public void deleteColumnDictById(Integer dictId){
		this.columnDictDao.deleteColumnDictById(dictId);
	}

	/**
	 * 通过主键得到
	 * @param DICT_ID 主键Id
	 */
	public AutoColumnDict getColumnDictById(Integer dictId){
		return this.columnDictDao.getColumnDictById(dictId);
	}

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	public List<AutoColumnDict> queryColumnDictList(Map<String,Object> condition){
		return this.columnDictDao.queryColumnDictList(condition);
	}

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<AutoColumnDict> queryColumnDictList(Map<String,Object> condition,IPageSize page){
		return this.columnDictDao.queryColumnDictList(condition,page);
	}

}
