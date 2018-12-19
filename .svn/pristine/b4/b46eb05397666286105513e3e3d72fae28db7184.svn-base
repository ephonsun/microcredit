package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IFlowStepContentDao;
import banger.domain.loan.LoanFlowStepContent;

/**
 * 调查流程步骤内容表数据访问类
 */
@Repository
public class FlowStepContentDao extends PageSizeDao<LoanFlowStepContent> implements IFlowStepContentDao {

	/**
	 * 新增调查流程步骤内容表
	 * @param flowStepContent 实体对像
	 */
	public void insertFlowStepContent(LoanFlowStepContent flowStepContent){
		flowStepContent.setId(this.newId().intValue());
		this.execute("insertFlowStepContent",flowStepContent);
	}

	/**
	 *修改调查流程步骤内容表
	 * @param flowStepContent 实体对像
	 */
	public void updateFlowStepContent(LoanFlowStepContent flowStepContent){
		this.execute("updateFlowStepContent",flowStepContent);
	}

	/**
	 * 通过主键删除调查流程步骤内容表
	 * @param id 主键Id
	 */
	public void deleteFlowStepContentById(Integer id){
		this.execute("deleteFlowStepContentById",id);
	}

	/**
	 * 通过主键得到调查流程步骤内容表
	 * @param id 主键Id
	 */
	public LoanFlowStepContent getFlowStepContentById(Integer id){
		return (LoanFlowStepContent)this.queryEntity("getFlowStepContentById",id);
	}

	/**
	 * 查询调查流程步骤内容表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanFlowStepContent> queryFlowStepContentList(Map<String,Object> condition){
		return (List<LoanFlowStepContent>)this.queryEntities("queryFlowStepContentList", condition);
	}

	/**
	 * 分页查询调查流程步骤内容表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanFlowStepContent> queryFlowStepContentList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanFlowStepContent>)this.queryEntities("queryFlowStepContentList", page, condition);
	}

}
