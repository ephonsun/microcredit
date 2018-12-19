package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.WfApproveProcessReview;

/**
 * 审批流环节审核人员表数据访问接口
 */
public interface IApproveProcessReviewDao {

	/**
	 * 新增审批流环节审核人员表
	 * @param approveProcessReview 实体对像
	 */
	void insertApproveProcessReview(WfApproveProcessReview approveProcessReview);

	/**
	 *修改审批流环节审核人员表
	 * @param approveProcessReview 实体对像
	 */
	void updateApproveProcessReview(WfApproveProcessReview approveProcessReview);

	/**
	 * 通过主键删除审批流环节审核人员表
	 * @param id 主键Id
	 */
	void deleteApproveProcessReviewById(Integer id);

	/**
	 * 通过主键删除审批流环节审核人员表
	 * @param processid 环节Id
	 * @param filterIds 排除的Ids
	 */
	void deleteApproveProcessReviewByProcessFilterId(Integer processid,Integer[] filterIds);

	/**
	 * 通过主键得到审批流环节审核人员表
	 * @param id 主键Id
	 */
	WfApproveProcessReview getApproveProcessReviewById(Integer id);

	/**
	 * 查询审批流环节审核人员表
	 * @param condition 查询条件
	 * @return
	 */
	List<WfApproveProcessReview> queryApproveProcessReviewList(Map<String, Object> condition);

	/**
	 *
	 * @param processId
	 * @return
	 */
	List<WfApproveProcessReview> getApproveProcessReviewListByProcessId(Integer processId);

	/**
	 * 分页查询审批流环节审核人员表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<WfApproveProcessReview> queryApproveProcessReviewList(Map<String, Object> condition, IPageSize page);

}
