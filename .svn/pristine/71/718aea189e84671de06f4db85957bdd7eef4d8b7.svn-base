package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.WfApproveCondition;

/**
 * 审批进程条件表数据访问接口
 */
public interface IApproveConditionDao {

	/**
	 * 新增审批进程条件表
	 * @param approveCondition 实体对像
	 */
	void insertApproveCondition(WfApproveCondition approveCondition);

	/**
	 *修改审批进程条件表
	 * @param approveCondition 实体对像
	 */
	void updateApproveCondition(WfApproveCondition approveCondition);

	/**
	 * 通过主键删除审批进程条件表
	 * @param conditionId 主键Id
	 */
	void deleteApproveConditionById(Integer conditionId);
	
	/**
	 * 通过审批流ID删除审批进程条件表
	 * @param flowId
	 */
	void deleteApproveConditionByFlowId(Integer flowId);

	/**
	 * 得到审批条件的值
	 * @param loanId 贷款ID
	 * @param prcessType 贷款过程ID
	 * @param tableName	表名
	 * @param columnName 列表
	 * @return
	 */
	Object getConditionValue(Integer loanId,String prcessType,String tableName,String columnName);

	/**
	 * 通过主键得到审批进程条件表
	 * @param conditionId 主键Id
	 */
	WfApproveCondition getApproveConditionById(Integer conditionId);

	/**
	 * 通过流程ID得到审批进程条件表
	 * @param flowId 流程Id
	 */
	WfApproveCondition getApproveConditionByFlowId(Integer flowId);

	/**
	 * 查询审批进程条件表
	 * @param condition 查询条件
	 * @return
	 */
	List<WfApproveCondition> queryApproveConditionList(Map<String,Object> condition);

	/**
	 * 分页查询审批进程条件表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<WfApproveCondition> queryApproveConditionList(Map<String,Object> condition,IPageSize page);

}
