package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanFlowStepContent;

/**
 * 调查流程步骤内容表数据访问接口
 */
public interface IFlowStepContentDao {

	/**
	 * 新增调查流程步骤内容表
	 * @param flowStepContent 实体对像
	 */
	void insertFlowStepContent(LoanFlowStepContent flowStepContent);

	/**
	 *修改调查流程步骤内容表
	 * @param flowStepContent 实体对像
	 */
	void updateFlowStepContent(LoanFlowStepContent flowStepContent);

	/**
	 * 通过主键删除调查流程步骤内容表
	 * @param id 主键Id
	 */
	void deleteFlowStepContentById(Integer id);

	/**
	 * 通过主键得到调查流程步骤内容表
	 * @param id 主键Id
	 */
	LoanFlowStepContent getFlowStepContentById(Integer id);

	/**
	 * 查询调查流程步骤内容表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanFlowStepContent> queryFlowStepContentList(Map<String, Object> condition);

	/**
	 * 分页查询调查流程步骤内容表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanFlowStepContent> queryFlowStepContentList(Map<String, Object> condition, IPageSize page);

}
