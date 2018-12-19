package banger.dao.impl;

import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanFlowStepItem;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IFlowStepItemDao;

/**
 * 流程步骤明细表数据访问类
 */
@Repository
public class FlowStepItemDao extends PageSizeDao<LoanFlowStepItem> implements IFlowStepItemDao {

	/**
	 * 新增流程步骤明细表
	 * @param flowStepItem 实体对像
	 */
	public void insertFlowStepItem(LoanFlowStepItem flowStepItem){
		flowStepItem.setId(this.newId().intValue());
		this.execute("insertFlowStepItem",flowStepItem);
	}

	/**
	 *修改流程步骤明细表
	 * @param flowStepItem 实体对像
	 */
	public void updateFlowStepItem(LoanFlowStepItem flowStepItem){
		this.execute("updateFlowStepItem",flowStepItem);
	}

	/**
	 * 通过主键删除流程步骤明细表
	 * @param id 主键Id
	 */
	public void deleteFlowStepItemById(Integer id){
		this.execute("deleteFlowStepItemById",id);
	}

	/**
	 * 通过主键得到流程步骤明细表
	 * @param id 主键Id
	 */
	public LoanFlowStepItem getFlowStepItemById(Integer id){
		return (LoanFlowStepItem)this.queryEntity("getFlowStepItemById",id);
	}

	/**
	 * 查询流程步骤明细表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanFlowStepItem> queryFlowStepItemList(Map<String,Object> condition){
		return (List<LoanFlowStepItem>)this.queryEntities("queryFlowStepItemList", condition);
	}

	/**
	 * 分页查询流程步骤明细表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanFlowStepItem> queryFlowStepItemList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanFlowStepItem>)this.queryEntities("queryFlowStepItemList", page, condition);
	}

	/**
	 * 获取排序号
	 * @return
	 */
	public Integer getMaxSortNum(){
		return this.queryInt("getStepSortNum");
	}

	/*
	* 获取所有排好序的调查步骤
	* */
	public List<LoanFlowStepItem> queryAllFlowStepItem(){
		return (List<LoanFlowStepItem>)this.queryEntities("queryAllFlowStepItem");
	}


}
