package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.WfApproveInfoDef;

/**
 * 审批流程表信息定义表数据访问接口
 */
public interface IApproveInfoDefDao {

	/**
	 * 新增审批流程表信息定义表
	 * @param approveInfoDef 实体对像
	 */
	void insertApproveInfoDef(WfApproveInfoDef approveInfoDef);

	/**
	 *修改审批流程表信息定义表
	 * @param approveInfoDef 实体对像
	 */
	void updateApproveInfoDef(WfApproveInfoDef approveInfoDef);

	/**
	 * 通过主键删除审批流程表信息定义表
	 * @param flowId 主键Id
	 */
	void deleteApproveInfoDefById(Integer flowId);

	/**
	 * 通过主键得到审批流程表信息定义表
	 * @param flowId 主键Id
	 */
	WfApproveInfoDef getApproveInfoDefById(Integer flowId);

	/**
	 * 得到审批流程定义
	 * @param classId
	 * @return
	 */
	WfApproveInfoDef getApproveInfoDefByClassId(Integer classId);

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
	
	void updateVersionById(String version,Integer flowId);

}
