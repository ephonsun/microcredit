package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.domain.loan.WfApproveProcess_Query;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.WfApproveProcess;

/**
 * 审批流环节定义表数据访问接口
 */
public interface IApproveProcessDao {

	/**
	 * 新增审批流环节定义表
	 * @param approveProcess 实体对像
	 */
	void insertApproveProcess(WfApproveProcess approveProcess);

	/**
	 *修改审批流环节定义表
	 * @param approveProcess 实体对像
	 */
	void updateApproveProcess(WfApproveProcess approveProcess);

	/**
	 * 通过主键删除审批流环节定义表
	 * @param processId 主键Id
	 */
	void deleteApproveProcessById(Integer processId);

	/**
	 * 通过主键得到审批流环节定义表
	 * @param processId 主键Id
	 */
	WfApproveProcess getApproveProcessById(Integer processId);

	/**
	 * 通过流程ID和参数ID，得到审批环节
	 * @param flowId 流程ID
	 * @param paramId 参数ID
	 * @return
	 */
	List<WfApproveProcess> getApproveProcessListByFlowId(Integer flowId,Integer paramId);

	/**
	 * 查询审批流环节定义表
	 * @param condition 查询条件
	 * @return
	 */
	List<WfApproveProcess_Query> queryApproveProcessList(Map<String,Object> condition);

	/**
	 * 分页查询审批流环节定义表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<WfApproveProcess> queryApproveProcessList(Map<String,Object> condition,IPageSize page);
	
	Integer getApproveProcessCount(Map<String,Object> condition);
	
	Integer getApproveProcessMaxNo(Map<String,Object> condition);
	
	void deleteApproveProcessByParamId(Integer paramId);

}
