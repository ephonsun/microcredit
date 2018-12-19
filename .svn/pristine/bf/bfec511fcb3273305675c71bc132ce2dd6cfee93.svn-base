package banger.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IApproveProcessReviewDao;
import banger.domain.loan.WfApproveProcessReview;

/**
 * 审批流环节审核人员表数据访问类
 */
@Repository
public class ApproveProcessReviewDao extends PageSizeDao<WfApproveProcessReview> implements IApproveProcessReviewDao {

	/**
	 * 新增审批流环节审核人员表
	 * @param approveProcessReview 实体对像
	 */
	public void insertApproveProcessReview(WfApproveProcessReview approveProcessReview){
		approveProcessReview.setId(this.newId().intValue());
		this.execute("insertApproveProcessReview",approveProcessReview);
	}

	/**
	 *修改审批流环节审核人员表
	 * @param approveProcessReview 实体对像
	 */
	public void updateApproveProcessReview(WfApproveProcessReview approveProcessReview){
		this.execute("updateApproveProcessReview",approveProcessReview);
	}

	/**
	 * 通过主键删除审批流环节审核人员表
	 * @param id 主键Id
	 */
	public void deleteApproveProcessReviewById(Integer id){
		this.execute("deleteApproveProcessReviewById",id);
	}

	/**
	 * 通过主键删除审批流环节审核人员表
	 * @param processid 环节Id
	 * @param filterIds 排除的Ids
	 */
	public void deleteApproveProcessReviewByProcessFilterId(Integer processId,Integer[] filterIds){
		Map<String,Object> conditon = new HashMap<String, Object>();
		conditon.put("processId",processId);
		if(filterIds!=null && filterIds.length>0){
			conditon.put("filterIds",filterIds);
		}
		this.execute("deleteApproveProcessReviewByProcessFilterId",conditon);
	}

	/**
	 * 通过主键得到审批流环节审核人员表
	 * @param id 主键Id
	 */
	public WfApproveProcessReview getApproveProcessReviewById(Integer id){
		return (WfApproveProcessReview)this.queryEntity("getApproveProcessReviewById",id);
	}

	/**
	 *
	 * @param processId 查询条件
	 * @return
	 */
	public List<WfApproveProcessReview> getApproveProcessReviewListByProcessId(Integer processId){
		return (List<WfApproveProcessReview>)this.queryEntities("getApproveProcessReviewListByProcessId", processId);
	}

	/**
	 * 查询审批流环节审核人员表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WfApproveProcessReview> queryApproveProcessReviewList(Map<String,Object> condition){
		return (List<WfApproveProcessReview>)this.queryEntities("queryApproveProcessReviewList", condition);
	}

	/**
	 * 分页查询审批流环节审核人员表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<WfApproveProcessReview> queryApproveProcessReviewList(Map<String,Object> condition,IPageSize page){
		return (IPageList<WfApproveProcessReview>)this.queryEntities("queryApproveProcessReviewList", page, condition);
	}

}
