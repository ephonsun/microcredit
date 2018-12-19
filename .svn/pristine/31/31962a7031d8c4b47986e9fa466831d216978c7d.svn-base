package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.WfApproveConditionParams;

/**
 * 审批进程条件参数表业务访问接口
 */
public interface IApproveConditionParamsService {
	
	void deleteParamsAndProcess(Integer conditionId,Integer flowId);

	/**
	 * 新增审批进程条件参数表
	 * @param approveConditionParams 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertApproveConditionParams(WfApproveConditionParams approveConditionParams,Integer loginUserId);

	/**
	 *修改审批进程条件参数表
	 * @param approveConditionParams 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateApproveConditionParams(WfApproveConditionParams approveConditionParams,Integer loginUserId);

	/**
	 * 通过主键删除审批进程条件参数表
	 * @param PARAMS_ID 主键Id
	 */
	void deleteApproveConditionParamsById(Integer paramsId);

	/**
	 * 通过主键得到审批进程条件参数表
	 * @param PARAMS_ID 主键Id
	 */
	WfApproveConditionParams getApproveConditionParamsById(Integer paramsId);

	/**
	 * 查询审批进程条件参数表
	 * @param condition 查询条件
	 * @return
	 */
	List<WfApproveConditionParams> queryApproveConditionParamsList(Map<String,Object> condition);

	/**
	 * 分页查询审批进程条件参数表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<WfApproveConditionParams> queryApproveConditionParamsList(Map<String,Object> condition,IPageSize page);

}
