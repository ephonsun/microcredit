package banger.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import banger.dao.intf.ITaskStatDao;
import banger.domain.task.TskTaskStatQuery;
import banger.framework.dao.PageSizeDao;

@Repository
public class TaskStatDao extends PageSizeDao<TskTaskStatQuery>implements ITaskStatDao{

	/**
	 * 查询团队与经理的任务情况
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TskTaskStatQuery> queryTaskStatList(Map<String, Object> condition) {
		return (List<TskTaskStatQuery>)this.queryEntities("queryTaskStatList",condition);
	}

	@Override
	public List<TskTaskStatQuery> queryPersonList(Map<String, Object> condition) {
		return (List<TskTaskStatQuery>)this.queryEntities("queryTaskStatList",condition);
	}

	/**
	 * 查询团队列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TskTaskStatQuery> queryGroupList() {
		return (List<TskTaskStatQuery>) this.queryEntities("queryAllGroupList");
	}


    @Override
	public List<TskTaskStatQuery> queryMemberByTeamGroupId(String teamGroupId){
	    return (List<TskTaskStatQuery>) this.queryEntities("queryMemberByTeamGroupId",teamGroupId);
	}
	@Override
	public List<TskTaskStatQuery> queryGroupListByGroupPermit(String groupPermit){
		return (List<TskTaskStatQuery>)this.queryEntities("queryGroupListByGroupPermit",groupPermit);
	}
	@Override
	public List<TskTaskStatQuery> queryCrossTeamList(String groupPermit, Date startDate,Date endDate){
		return (List<TskTaskStatQuery>)this.queryEntities("queryCrossTeamList",groupPermit,startDate,endDate);
	}

	@Override
	public Integer queryGroupAssignTargetByIds(Integer taskId, Integer teamGroupId) {
		return  this.queryInt("queryGroupAssignTargetByIds",taskId,teamGroupId);
	}

	@Override
	public Integer queryCrossTaskTargetById(Integer taskId) {
		return this.queryInt("queryCrossTaskTargetById",taskId);
	}

	@Override
	public List<TskTaskStatQuery> queryLoanTasksList(Map<String, Object> condition) {
		return (List<TskTaskStatQuery>)this.queryEntities("queryLoanTasksList",condition);
	}

	@Override
	public List<TskTaskStatQuery> queryLoanTasksListForTeam(Map<String, Object> condition) {
		return (List<TskTaskStatQuery>)this.queryEntities("queryLoanTasksListForTeam",condition);
	}
}
