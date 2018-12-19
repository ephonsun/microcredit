package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.WfApproveConditionParams;

/**
 * 审批进程条件参数表数据访问接口
 */
public interface IApproveConditionParamsDao {

	/**
	 * 新增审批进程条件参数表
	 * @param approveConditionParams 实体对像
	 */
	void insertApproveConditionParams(WfApproveConditionParams approveConditionParams);

	/**
	 *修改审批进程条件参数表
	 * @param approveConditionParams 实体对像
	 */
	void updateApproveConditionParams(WfApproveConditionParams approveConditionParams);

	/**
	 * 通过主键删除审批进程条件参数表
	 * @param paramsId 主键Id
	 */
	void deleteApproveConditionParamsById(Integer paramsId);

	/**
	 * 通过主键得到审批进程条件参数表
	 * @param paramsId 主键Id
	 */
	WfApproveConditionParams getApproveConditionParamsById(Integer paramsId);

	/**
	 * 通过条件ID得到审批进程条件参数表
	 * @param conditionId 主键Id
	 */
	List<WfApproveConditionParams> getApproveConditionParamsByConditionId(Integer conditionId);

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
	
	void deleteApproveConditionParamsByConditionId(Integer conditionId);

}
