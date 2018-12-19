package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.domain.customer.Customer;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.customer.CustFamilyInfo;

/**
 * 业务访问接口
 */
public interface IFamilyInfoService {

	/**
	 * 新增
	 * @param familyInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertFamilyInfo(Customer familyInfo, Integer loginUserId);

	/**
	 *修改
	 * @param familyInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateFamilyInfo(Customer familyInfo, Integer loginUserId);

	/**
	 * 通过主键删除
	 * @param ID 主键Id
	 */
	void deleteFamilyInfoById(Integer id);

	/**
	 * 通过主键得到
	 * @param ID 主键Id
	 */
	CustFamilyInfo getFamilyInfoById(Integer id);

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	List<CustFamilyInfo> queryFamilyInfoList(Map<String, Object> condition);

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<CustFamilyInfo> queryFamilyInfoList(Map<String, Object> condition, IPageSize page);

}
