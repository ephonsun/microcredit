package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.domain.loan.WfApproveProcess_Query;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.WfApproveProcess;

/**
 * 审批流环节定义表业务访问接口
 */
public interface IApproveProcessService {

	/**
	 * 新增审批流环节定义表
	 * @param approveProcess 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertApproveProcess(WfApproveProcess approveProcess,Integer loginUserId);

	/**
	 *修改审批流环节定义表
	 * @param approveProcess 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateApproveProcess(WfApproveProcess approveProcess,Integer loginUserId);

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
	
	
	void setApproveProcessName(WfApproveProcess approveProcess);
	
	void setApproveProcessNo(WfApproveProcess approveProcess);

}
