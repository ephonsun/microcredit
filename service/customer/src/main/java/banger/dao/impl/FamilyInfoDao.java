package banger.dao.impl;

import banger.dao.intf.IFamilyInfoDao;
import banger.domain.customer.CustFamilyInfo;
import banger.domain.customer.Customer;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 数据访问类
 */
@Repository
public class FamilyInfoDao extends PageSizeDao<CustFamilyInfo> implements IFamilyInfoDao {

	/**
	 * 新增
	 * @param familyInfo 实体对像
	 */
	public void insertFamilyInfo(Customer familyInfo){
		this.execute("insertFamilyInfo",familyInfo);
	}

	/**
	 *修改
	 * @param familyInfo 实体对像
	 */
	public void updateFamilyInfo(Customer familyInfo){
		this.execute("updateFamilyInfo",familyInfo);
	}

	/**
	 * 通过主键删除
	 * @param id 主键Id
	 */
	public void deleteFamilyInfoById(Integer id){
		this.execute("deleteFamilyInfoById",id);
	}

	/**
	 * 通过主键得到
	 * @param id 主键Id
	 */
	public CustFamilyInfo getFamilyInfoById(Integer id){
		return (CustFamilyInfo)this.queryEntity("getFamilyInfoById",id);
	}

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CustFamilyInfo> queryFamilyInfoList(Map<String,Object> condition){
		return (List<CustFamilyInfo>)this.queryEntities("queryFamilyInfoList", condition);
	}

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<CustFamilyInfo> queryFamilyInfoList(Map<String,Object> condition,IPageSize page){
		return (IPageList<CustFamilyInfo>)this.queryEntities("queryFamilyInfoList", page, condition);
	}

}
