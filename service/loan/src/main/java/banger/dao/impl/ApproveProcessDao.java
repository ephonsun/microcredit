package banger.dao.impl;

import java.util.List;
import java.util.Map;

import banger.domain.loan.WfApproveProcess_Query;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IApproveProcessDao;
import banger.domain.loan.WfApproveProcess;

/**
 * 审批流环节定义表数据访问类
 */
@Repository
public class ApproveProcessDao extends PageSizeDao<WfApproveProcess> implements IApproveProcessDao {

	/**
	 * 新增审批流环节定义表
	 * @param approveProcess 实体对像
	 */
	public void insertApproveProcess(WfApproveProcess approveProcess){
		approveProcess.setProcessId(this.newId().intValue());
		this.execute("insertApproveProcess",approveProcess);
	}

	/**
	 *修改审批流环节定义表
	 * @param approveProcess 实体对像
	 */
	public void updateApproveProcess(WfApproveProcess approveProcess){
		this.execute("updateApproveProcess",approveProcess);
	}

	/**
	 * 通过主键删除审批流环节定义表
	 * @param processId 主键Id
	 */
	public void deleteApproveProcessById(Integer processId){
		this.execute("deleteApproveProcessById",processId);
	}

	/**
	 * 通过主键得到审批流环节定义表
	 * @param processId 主键Id
	 */
	public WfApproveProcess getApproveProcessById(Integer processId){
		return (WfApproveProcess)this.queryEntity("getApproveProcessById",processId);
	}

	/**
	 * 通过流程ID和参数ID，得到审批环节
	 * @param flowId 流程ID
	 * @param paramId 参数ID
	 * @return
	 */
	public List<WfApproveProcess> getApproveProcessListByFlowId(Integer flowId,Integer paramId){
		return (List<WfApproveProcess>)this.queryEntities("getApproveProcessListByFlowId", new Object[]{flowId,paramId});
	}

	/**
	 * 查询审批流环节定义表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WfApproveProcess_Query> queryApproveProcessList(Map<String,Object> condition){
		return (List<WfApproveProcess_Query>) this.queryEntities("queryApproveProcessList", condition);
	}

	/**
	 * 分页查询审批流环节定义表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<WfApproveProcess> queryApproveProcessList(Map<String,Object> condition,IPageSize page){
		return (IPageList<WfApproveProcess>)this.queryEntities("queryApproveProcessList", page, condition);
	}

	public Integer getApproveProcessCount(Map<String, Object> condition) {
		return this.queryInt("getApproveProcessCount",condition);
	}

	public Integer getApproveProcessMaxNo(Map<String, Object> condition) {
		return this.queryInt("getApproveProcessMaxNo",condition);
	}

	public void deleteApproveProcessByParamId(Integer paramId) {
		this.execute("deleteApproveProcessByParamId",paramId);
	}

}
