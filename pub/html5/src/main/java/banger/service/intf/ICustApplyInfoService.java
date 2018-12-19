package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.domain.html5.IntoCustApplyInfoQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.html5.IntoCustApplyInfo;

/**
 * 进件申请信息表业务访问接口
 */
public interface ICustApplyInfoService {

	/**
	 * 新增进件申请信息表
	 * @param custApplyInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	Integer insertCustApplyInfo(IntoCustApplyInfo custApplyInfo, Integer loginUserId);

	/**
	 *修改进件申请信息表
	 * @param custApplyInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateCustApplyInfo(IntoCustApplyInfo custApplyInfo, Integer loginUserId);

	/**
	 * 通过主键删除进件申请信息表
	 * @param APPLY_ID 主键Id
	 */
	void deleteCustApplyInfoById(Integer applyId);

	/**
	 * 通过主键得到进件申请信息表
	 * @param APPLY_ID 主键Id
	 */
	IntoCustApplyInfo getCustApplyInfoById(Integer applyId);

	IntoCustApplyInfoQuery getCustApplyInfoByIdQuery(Integer applyId);
//	/**
//	 * 查询进件申请信息表
//	 * @param condition 查询条件
//	 * @return
//	 */
//	List<IntoCustApplyInfo> queryCustApplyInfoList(Map<String, Object> condition);
//
//	IPageList<IntoCustApplyInfo> queryCustApplyInfoList(Map<String, Object> condition, IPageSize page);

	/**
	 * 营销团队
	 * 分页查询进件申请信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
    IPageList<IntoCustApplyInfoQuery> IntoCustApplyInfoList(Map<String, Object> condition, IPageSize page);

	/**
	 * 修改客户分配状态
	 * @param condition
	 */
	void signIntoCustomer(Map<String,Object> condition);



}
