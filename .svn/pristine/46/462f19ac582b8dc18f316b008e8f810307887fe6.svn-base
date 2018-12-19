package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IApproveConditionParamsDao;
import banger.domain.loan.WfApproveConditionParams;

/**
 * 审批进程条件参数表数据访问类
 */
@Repository
public class ApproveConditionParamsDao extends PageSizeDao<WfApproveConditionParams> implements IApproveConditionParamsDao {

	/**
	 * 新增审批进程条件参数表
	 * @param approveConditionParams 实体对像
	 */
	public void insertApproveConditionParams(WfApproveConditionParams approveConditionParams){
		approveConditionParams.setParamsId(this.newId().intValue());
		this.execute("insertApproveConditionParams",approveConditionParams);
	}

	/**
	 *修改审批进程条件参数表
	 * @param approveConditionParams 实体对像
	 */
	public void updateApproveConditionParams(WfApproveConditionParams approveConditionParams){
		this.execute("updateApproveConditionParams",approveConditionParams);
	}

	/**
	 * 通过主键删除审批进程条件参数表
	 * @param paramsId 主键Id
	 */
	public void deleteApproveConditionParamsById(Integer paramsId){
		this.execute("deleteApproveConditionParamsById",paramsId);
	}

	/**
	 * 通过主键得到审批进程条件参数表
	 * @param paramsId 主键Id
	 */
	public WfApproveConditionParams getApproveConditionParamsById(Integer paramsId){
		return (WfApproveConditionParams)this.queryEntity("getApproveConditionParamsById",paramsId);
	}


	/**
	 * 通过条件ID得到审批进程条件参数表
	 * @param conditionId 主键Id
	 */
	public List<WfApproveConditionParams> getApproveConditionParamsByConditionId(Integer conditionId){
		return (List<WfApproveConditionParams>)this.queryEntities("getApproveConditionParamsByConditionId",conditionId);
	}

	/**
	 * 查询审批进程条件参数表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WfApproveConditionParams> queryApproveConditionParamsList(Map<String,Object> condition){
		return (List<WfApproveConditionParams>)this.queryEntities("queryApproveConditionParamsList", condition);
	}

	/**
	 * 分页查询审批进程条件参数表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<WfApproveConditionParams> queryApproveConditionParamsList(Map<String,Object> condition,IPageSize page){
		return (IPageList<WfApproveConditionParams>)this.queryEntities("queryApproveConditionParamsList", page, condition);
	}

	public void deleteApproveConditionParamsByConditionId(Integer conditionId){
		this.execute("deleteApproveConditionParamsByConditionId",conditionId);
	}

}
