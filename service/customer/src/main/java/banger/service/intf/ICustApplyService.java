package banger.service.intf;

import banger.domain.html5.IntoCustApplyInfoQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.Map;

/**
 * Created by panl on 2017/11/20
 */
public interface ICustApplyService {
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
	 * 通过主键删除进件客户表
	 * @param applyId 主键Id
	 */
	void deleteIntoCustomerById(Integer applyId);

	/**
	 * 修改客户分配状态
	 * @param condition
	 */
	void signIntoCustomer(Map<String,Object> condition);


}
