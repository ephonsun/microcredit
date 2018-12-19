package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.domain.customer.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.ISpouseInfoDao;
import banger.service.intf.ISpouseInfoService;
import banger.domain.customer.CustSpouseInfo;

/**
 * 业务访问类
 */
@Repository
public class SpouseInfoService implements ISpouseInfoService {

	@Autowired
	private ISpouseInfoDao spouseInfoDao;

	/**
	 * 新增
	 * @param spouseInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertSpouseInfo(Customer spouseInfo,Integer loginUserId){
		this.spouseInfoDao.insertSpouseInfo(spouseInfo);
	}

	/**
	 *修改
	 * @param spouseInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateSpouseInfo(Customer spouseInfo,Integer loginUserId){
		this.spouseInfoDao.updateSpouseInfo(spouseInfo);
	}

	/**
	 * 通过主键删除
	 * @param ID 主键Id
	 */
	public void deleteSpouseInfoById(Integer id){
		this.spouseInfoDao.deleteSpouseInfoById(id);
	}

	/**
	 * 通过主键得到
	 * @param ID 主键Id
	 */
	public CustSpouseInfo getSpouseInfoById(Integer id){
		return this.spouseInfoDao.getSpouseInfoById(id);
	}

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	public List<CustSpouseInfo> querySpouseInfoList(Map<String,Object> condition){
		return this.spouseInfoDao.querySpouseInfoList(condition);
	}

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<CustSpouseInfo> querySpouseInfoList(Map<String,Object> condition,IPageSize page){
		return this.spouseInfoDao.querySpouseInfoList(condition,page);
	}

}
