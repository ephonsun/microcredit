package banger.dao.intf;

import banger.domain.html5.IntoCustApplyInfo;
import banger.domain.html5.IntoCustApplyInfoQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.Map;

/**
 * 进件申请信息表数据访问接口
 */
public interface ICustApplyInfoDao {

	/**
	 * 新增进件申请信息表
	 * @param custApplyInfo 实体对像
	 */
	Integer insertCustApplyInfo(IntoCustApplyInfo custApplyInfo);

	/**
	 *修改进件申请信息表
	 * @param custApplyInfo 实体对像
	 */
	void updateCustApplyInfo(IntoCustApplyInfo custApplyInfo);

	/**
	 * 通过主键删除进件申请信息表
	 * @param applyId 主键Id
	 */
	void deleteCustApplyInfoById(Integer applyId);

	/**
	 * 通过主键得到进件申请信息表
	 * @param applyId 主键Id
	 */
	IntoCustApplyInfo getCustApplyInfoById(Integer applyId);

	IntoCustApplyInfoQuery getCustApplyInfoByIdQuery(Integer applyId);
	/**
	 * App端
	 * 通过主键得到进件申请信息表
	 * @param applyId 主键Id
	 */
	IntoCustApplyInfoQuery getAppCustApplyInfoById(Integer applyId);


//	/**
//	 * 查询进件申请信息表
//	 * @param condition 查询条件
//	 * @return
//	 */
//	List<IntoCustApplyInfo> queryCustApplyInfoList(Map<String, Object> condition);
//
//	IPageList<IntoCustApplyInfo> queryCustApplyInfoList(Map<String, Object> condition, IPageSize page);

	/**
	 * 分页查询进件申请信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<IntoCustApplyInfoQuery> IntoCustApplyInfoList(Map<String, Object> condition, IPageSize page);

//	/**
//	 * App端
//	 * 分页查询进件申请信息表
//	 * @param condition 查询条件
//	 * @param page 分页对像
//	 * @return
//	 */
//	IPageList<IntoCustApplyInfoQuery> IntoAppCustApplyInfoList(Map<String, Object> condition, IPageSize page);

	/**
	 * 团队主管
	 * 分页查询进件申请信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */

	IPageList<IntoCustApplyInfoQuery> queryIntoCustApplyInfoList(Map<String, Object> condition, IPageSize page);


	/**
	 * 客户经理分页查询进件申请信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<IntoCustApplyInfoQuery> IntoCustApplyInfoMemberList(Map<String, Object> condition, IPageSize page);


	/**
	 * App
	 * 客户经理分页查询进件申请信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<IntoCustApplyInfoQuery> IntoAppCustApplyInfoMemberList(Map<String, Object> condition, IPageSize page);

	/**
	 * 修改客户分配状态
	 * @param condition
	 */
	void signIntoCustomer(Map<String,Object> condition);

	/**
	 * 通过主键删除营销客户表
	 * @param applyId 主键Id
	 */
	void deleteIntoCustomerById(Integer applyId);

	/**
	 * 更新客户的预申请贷款Id
	 * @param applyId
	 * @param loanId
	 */
	void updateIntoCustomerLoanId(Integer applyId,Integer loanId);

}
