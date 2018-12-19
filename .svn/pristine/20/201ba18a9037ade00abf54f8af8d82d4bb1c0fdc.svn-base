package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.domain.customer.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IFamilyInfoDao;
import banger.service.intf.IFamilyInfoService;
import banger.domain.customer.CustFamilyInfo;

/**
 * 业务访问类
 */
@Repository
public class FamilyInfoService implements IFamilyInfoService {

	@Autowired
	private IFamilyInfoDao familyInfoDao;

	/**
	 * 新增
	 * @param familyInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertFamilyInfo(Customer familyInfo,Integer loginUserId){
		this.familyInfoDao.insertFamilyInfo(familyInfo);
	}

	/**
	 *修改
	 * @param familyInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateFamilyInfo(Customer familyInfo,Integer loginUserId){
		this.familyInfoDao.updateFamilyInfo(familyInfo);
	}

	/**
	 * 通过主键删除
	 * @param ID 主键Id
	 */
	public void deleteFamilyInfoById(Integer id){
		this.familyInfoDao.deleteFamilyInfoById(id);
	}

	/**
	 * 通过主键得到
	 * @param ID 主键Id
	 */
	public CustFamilyInfo getFamilyInfoById(Integer id){
		return this.familyInfoDao.getFamilyInfoById(id);
	}

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	public List<CustFamilyInfo> queryFamilyInfoList(Map<String,Object> condition){
		return this.familyInfoDao.queryFamilyInfoList(condition);
	}

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<CustFamilyInfo> queryFamilyInfoList(Map<String,Object> condition,IPageSize page){
		return this.familyInfoDao.queryFamilyInfoList(condition,page);
	}

}
