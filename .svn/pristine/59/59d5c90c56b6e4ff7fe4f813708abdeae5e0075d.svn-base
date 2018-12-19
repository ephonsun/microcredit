package banger.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import banger.dao.intf.IApproveInfoDefDao;
import banger.domain.loan.WfApproveInfoDef;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * 审批流程表信息定义表数据访问类
 */
@Repository
public class ApproveInfoDefDao extends PageSizeDao<WfApproveInfoDef> implements IApproveInfoDefDao {

	/**
	 * 新增审批流程表信息定义表
	 * @param approveInfoDef 实体对像
	 */
	public void insertApproveInfoDef(WfApproveInfoDef approveInfoDef){
		approveInfoDef.setFlowId(this.newId().intValue());
		this.execute("insertApproveInfoDef",approveInfoDef);
	}

	/**
	 *修改审批流程表信息定义表
	 * @param approveInfoDef 实体对像
	 */
	public void updateApproveInfoDef(WfApproveInfoDef approveInfoDef){
		this.execute("updateApproveInfoDef",approveInfoDef);
	}

	/**
	 * 通过主键删除审批流程表信息定义表
	 * @param flowId 主键Id
	 */
	public void deleteApproveInfoDefById(Integer flowId){
		this.execute("deleteApproveInfoDefById",flowId);
	}

	/**
	 * 得到审批流程定义
	 * @param classId
	 * @return
	 */
	public WfApproveInfoDef getApproveInfoDefByClassId(Integer classId){
		return (WfApproveInfoDef)this.queryEntity("getApproveInfoDefByClassId",classId);
	}

	/**
	 * 通过主键得到审批流程表信息定义表
	 * @param flowId 主键Id
	 */
	public WfApproveInfoDef getApproveInfoDefById(Integer flowId){
		return (WfApproveInfoDef)this.queryEntity("getApproveInfoDefById",flowId);
	}

	/**
	 * 查询审批流程表信息定义表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WfApproveInfoDef> queryApproveInfoDefList(Map<String,Object> condition){
		return (List<WfApproveInfoDef>)this.queryEntities("queryApproveInfoDefList", condition);
	}

	/**
	 * 分页查询审批流程表信息定义表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<WfApproveInfoDef> queryApproveInfoDefList(Map<String,Object> condition,IPageSize page){
		return (IPageList<WfApproveInfoDef>)this.queryEntities("queryApproveInfoDefList", page, condition);
	}

	public void updateVersionById(String version, Integer flowId) {
		this.execute("updateVersionById",version,flowId);
	}

}
