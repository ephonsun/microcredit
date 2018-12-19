package banger.dao.impl;

import java.util.List;
import java.util.Map;

import banger.framework.collection.DataTable;
import banger.framework.sql.util.ISqlEngine;
import banger.framework.sql.util.SqlEngine;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IApproveConditionDao;
import banger.domain.loan.WfApproveCondition;

/**
 * 审批进程条件表数据访问类
 */
@Repository
public class ApproveConditionDao extends PageSizeDao<WfApproveCondition> implements IApproveConditionDao {

	/**
	 * 新增审批进程条件表
	 * @param approveCondition 实体对像
	 */
	public void insertApproveCondition(WfApproveCondition approveCondition){
		approveCondition.setConditionId(this.newId().intValue());
		this.execute("insertApproveCondition",approveCondition);
	}

	/**
	 *修改审批进程条件表
	 * @param approveCondition 实体对像
	 */
	public void updateApproveCondition(WfApproveCondition approveCondition){
		this.execute("updateApproveCondition",approveCondition);
	}

	/**
	 * 通过主键删除审批进程条件表
	 * @param conditionId 主键Id
	 */
	public void deleteApproveConditionById(Integer conditionId){
		this.execute("deleteApproveConditionById",conditionId);
	}

	/**
	 * 通过主键得到审批进程条件表
	 * @param conditionId 主键Id
	 */
	public WfApproveCondition getApproveConditionById(Integer conditionId){
		return (WfApproveCondition)this.queryEntity("getApproveConditionById",conditionId);
	}

	/**
	 * 通过流程ID得到审批进程条件表
	 * @param flowId 流程Id
	 */
	public WfApproveCondition getApproveConditionByFlowId(Integer flowId){
		return (WfApproveCondition)this.queryEntity("getApproveConditionByFlowId",flowId);
	}

	/**
	 * 查询审批进程条件表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WfApproveCondition> queryApproveConditionList(Map<String,Object> condition){
		return (List<WfApproveCondition>)this.queryEntities("queryApproveConditionList", condition);
	}

	/**
	 * 分页查询审批进程条件表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<WfApproveCondition> queryApproveConditionList(Map<String,Object> condition,IPageSize page){
		return (IPageList<WfApproveCondition>)this.queryEntities("queryApproveConditionList", page, condition);
	}

	public void deleteApproveConditionByFlowId(Integer flowId) {
		this.execute("deleteApproveConditionByFlowId",flowId);
	}

	/**
	 * 得到审批条件的值
	 * @param loanId 贷款ID
	 * @param prcessType 贷款过程ID
	 * @param tableName	表名
	 * @param columnName 列表
	 * @return
	 */
	public Object getConditionValue(Integer loanId,String prcessType,String tableName,String columnName){
		ISqlEngine ish = ("".equals(dbConfig))? SqlEngine.instance():SqlEngine.instance(dbConfig);
		StringBuffer sql = new StringBuffer("SELECT "+columnName+" FROM "+tableName+" WHERE LOAN_ID="+loanId+" AND LOAN_PROCESS_TYPE='"+prcessType+"'");
		Object obj = ish.executeScalar(sql.toString());
		ish.dispose();
		return obj;
	}

}
