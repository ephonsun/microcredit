package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.WfApproveInfoDef;

/**
 * 审批流程表信息定义表业务访问接口
 */
public interface IApproveInfoDefService {

	/**
	 * 新增审批流程表信息定义表
	 * @param approveInfoDef 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertApproveInfoDef(WfApproveInfoDef approveInfoDef,Integer loginUserId);

	/**
	 *修改审批流程表信息定义表
	 * @param approveInfoDef 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateApproveInfoDef(WfApproveInfoDef approveInfoDef,Integer loginUserId);

	/**
	 * 通过主键删除审批流程表信息定义表
	 * @param FLOW_ID 主键Id
	 */
	void deleteApproveInfoDefById(Integer flowId);

	/**
	 * 通过主键得到审批流程表信息定义表
	 * @param FLOW_ID 主键Id
	 */
	WfApproveInfoDef getApproveInfoDefById(Integer flowId);

	/**
	 * 查询审批流程表信息定义表
	 * @param condition 查询条件
	 * @return
	 */
	List<WfApproveInfoDef> queryApproveInfoDefList(Map<String,Object> condition);

	/**
	 * 分页查询审批流程表信息定义表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<WfApproveInfoDef> queryApproveInfoDefList(Map<String,Object> condition,IPageSize page);
	
	void insertApproveInfoDef(Integer isActived,Integer classId,Integer isCondition,Integer loginUserId);

	void updateVersionById(Integer flowId);
}
