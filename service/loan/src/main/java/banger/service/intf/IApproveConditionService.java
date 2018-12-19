package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.config.AutoTableColumn;
import banger.domain.loan.WfApproveCondition;
import banger.domain.loan.WfApproveInfoDef;

/**
 * 审批进程条件表业务访问接口
 */
public interface IApproveConditionService {

	/**
	 * 新增审批进程条件表
	 * @param approveCondition 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertApproveCondition(WfApproveCondition approveCondition,Integer loginUserId);

	/**
	 *修改审批进程条件表
	 * @param approveCondition 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateApproveCondition(WfApproveCondition approveCondition,Integer loginUserId);

	/**
	 * 通过主键删除审批进程条件表
	 * @param CONDITION_ID 主键Id
	 */
	void deleteApproveConditionById(Integer conditionId);

	/**
	 * 通过主键得到审批进程条件表
	 * @param CONDITION_ID 主键Id
	 */
	WfApproveCondition getApproveConditionById(Integer conditionId);

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
	
	String queryConditionInfo(WfApproveInfoDef approveInfoDef);
	
	void saveApproveCondition(AutoTableColumn tableColumn,Integer flowId,String count,String[] nums,Integer loginUserId);

}
