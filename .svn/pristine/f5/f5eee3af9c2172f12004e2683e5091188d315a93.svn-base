package banger.service.impl;

import java.util.*;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IApproveProcessReviewDao;
import banger.service.intf.IApproveProcessReviewService;
import banger.domain.loan.WfApproveProcessReview;

/**
 * 审批流环节审核人员表业务访问类
 */
@Repository
public class ApproveProcessReviewService implements IApproveProcessReviewService {

	@Autowired
	private IApproveProcessReviewDao approveProcessReviewDao;

	/**
	 * 新增审批流环节审核人员表
	 * @param approveProcessReview 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertApproveProcessReview(WfApproveProcessReview approveProcessReview,Integer loginUserId){
		approveProcessReview.setCreateUser(loginUserId);
		approveProcessReview.setCreateDate(DateUtil.getCurrentDate());
		approveProcessReview.setUpdateUser(loginUserId);
		approveProcessReview.setUpdateDate(DateUtil.getCurrentDate());
		this.approveProcessReviewDao.insertApproveProcessReview(approveProcessReview);
	}

	/**
	 *修改审批流环节审核人员表
	 * @param approveProcessReview 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateApproveProcessReview(WfApproveProcessReview approveProcessReview,Integer loginUserId){
		approveProcessReview.setUpdateUser(loginUserId);
		approveProcessReview.setUpdateDate(DateUtil.getCurrentDate());
		this.approveProcessReviewDao.updateApproveProcessReview(approveProcessReview);
	}

	/**
	 * 通过主键删除审批流环节审核人员表
	 * @param id 主键Id
	 */
	public void deleteApproveProcessReviewById(Integer id){
		this.approveProcessReviewDao.deleteApproveProcessReviewById(id);
	}

	/**
	 * 通过主键得到审批流环节审核人员表
	 * @param id 主键Id
	 */
	public WfApproveProcessReview getApproveProcessReviewById(Integer id){
		return this.approveProcessReviewDao.getApproveProcessReviewById(id);
	}

	/**
	 * 查询审批流环节审核人员表
	 * @param condition 查询条件
	 * @return
	 */
	public List<WfApproveProcessReview> queryApproveProcessReviewList(Map<String,Object> condition){
		return this.approveProcessReviewDao.queryApproveProcessReviewList(condition);
	}

	/**
	 * 查询审批流环节审核人员表
	 * @param processId 环节ID
	 * @return
	 */
	public List<WfApproveProcessReview> getApproveProcessReviewListByProcessId(Integer processId){
		return this.approveProcessReviewDao.getApproveProcessReviewListByProcessId(processId);
	}

	/**
	 * 分页查询审批流环节审核人员表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<WfApproveProcessReview> queryApproveProcessReviewList(Map<String,Object> condition,IPageSize page){
		return this.approveProcessReviewDao.queryApproveProcessReviewList(condition,page);
	}

	/**
	 * 保存审核人员组
	 * @param reviewList
	 */
	public void saveApproveProcessReviews(Integer processId,Integer userId,List<WfApproveProcessReview> reviewList){
		List<Integer> ids = new ArrayList<Integer>();
		for(WfApproveProcessReview approveProcessReview : reviewList){
			approveProcessReview.setUpdateDate(new Date());
			approveProcessReview.setCreateDate(new Date());
			approveProcessReview.setCreateUser(userId);
			approveProcessReview.setUpdateUser(userId);
			approveProcessReview.setProcessId(processId);

			if(approveProcessReview.getId()!=null && approveProcessReview.getId().intValue()>0){
				this.approveProcessReviewDao.updateApproveProcessReview(approveProcessReview);
			}else{
				this.approveProcessReviewDao.insertApproveProcessReview(approveProcessReview);
			}
			ids.add(approveProcessReview.getId());
		}

		this.approveProcessReviewDao.deleteApproveProcessReviewByProcessFilterId(processId,ids.toArray(new Integer[0]));
	}

}
