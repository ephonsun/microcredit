package banger.dao.intf;

import java.util.List;
import java.util.Map;
import banger.domain.loan.LoanFlowStepItem;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * 流程步骤明细表数据访问接口
 */
public interface IFlowStepItemDao {

	/**
	 * 新增流程步骤明细表
	 * @param flowStepItem 实体对像
	 */
	void insertFlowStepItem(LoanFlowStepItem flowStepItem);

	/**
	 *修改流程步骤明细表
	 * @param flowStepItem 实体对像
	 */
	void updateFlowStepItem(LoanFlowStepItem flowStepItem);

	/**
	 * 通过主键删除流程步骤明细表
	 * @param id 主键Id
	 */
	void deleteFlowStepItemById(Integer id);

	/**
	 * 通过主键得到流程步骤明细表
	 * @param id 主键Id
	 */
	LoanFlowStepItem getFlowStepItemById(Integer id);

	/**
	 * 查询流程步骤明细表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanFlowStepItem> queryFlowStepItemList(Map<String, Object> condition);

	/**
	 * 分页查询流程步骤明细表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanFlowStepItem> queryFlowStepItemList(Map<String, Object> condition, IPageSize page);

	/**
	 * 获取排序号
	 * @return
	 */
	Integer getMaxSortNum();

	/*
	* 获取所有排好序的调查步骤
	* */
	List<LoanFlowStepItem> queryAllFlowStepItem();


}
