package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.WfApproveProcessReview;

/**
 * 审批流环节审核人员表业务访问接口
 */
public interface IApproveProcessReviewService {

	/**
	 * 新增审批流环节审核人员表
	 * @param approveProcessReview 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertApproveProcessReview(WfApproveProcessReview approveProcessReview, Integer loginUserId);

	/**
	 *修改审批流环节审核人员表
	 * @param approveProcessReview 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateApproveProcessReview(WfApproveProcessReview approveProcessReview, Integer loginUserId);

	/**
	 * 通过主键删除审批流环节审核人员表
	 * @param PROCESS_ID 主键Id
	 */
	void deleteApproveProcessReviewById(Integer processId);

	/**
	 * 通过主键得到审批流环节审核人员表
	 * @param PROCESS_ID 主键Id
	 */
	WfApproveProcessReview getApproveProcessReviewById(Integer processId);

	/**
	 * 查询审批流环节审核人员表
	 * @param processId 环节ID
	 * @return
	 */
	List<WfApproveProcessReview> getApproveProcessReviewListByProcessId(Integer processId);

	/**
	 * 保存审核人员组
	 * @param reviewList
	 */
	void saveApproveProcessReviews(Integer processId,Integer userId,List<WfApproveProcessReview> reviewList);

	/**
	 * 查询审批流环节审核人员表
	 * @param condition 查询条件
	 * @return
	 */
	List<WfApproveProcessReview> queryApproveProcessReviewList(Map<String, Object> condition);

	/**
	 * 分页查询审批流环节审核人员表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<WfApproveProcessReview> queryApproveProcessReviewList(Map<String, Object> condition, IPageSize page);

}
