package banger.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import banger.dao.intf.IGroupTaskAssignDao;
import banger.domain.task.GroupUnsignedTaskBean;
import banger.domain.task.TskGroupTaskAssign;
import banger.domain.task.TskGroupTaskAssignQuery;
import banger.framework.dao.PageSizeDao;

/**
 * 团队任务分配表数据访问类
 */
@Repository
public class GroupTaskAssignDao extends PageSizeDao<TskGroupTaskAssign> implements IGroupTaskAssignDao {

	/**
	 * 新增团队任务分配表
	 * @param groupTaskAssign 实体对像
	 */
	public void insertGroupTaskAssign(TskGroupTaskAssign groupTaskAssign){
		groupTaskAssign.setGroupTaskAssignId(this.newId().intValue());
		this.execute("insertGroupTaskAssign",groupTaskAssign);
	}

	/**
	 *修改团队任务分配表
	 * @param groupTaskAssign 实体对像
	 */
	public void updateGroupTaskAssign(TskGroupTaskAssign groupTaskAssign){
		this.execute("updateGroupTaskAssign",groupTaskAssign);
	}

	/**
	 * 通过主键删除团队任务分配表
	 * @param groupTaskAssignId 主键Id
	 */
	public void deleteGroupTaskAssignById(Integer groupTaskAssignId){
		this.execute("deleteGroupTaskAssignById",groupTaskAssignId);
	}

	/**
	 * 通过任务ID删除团队任务分配表
	 * @param taskId
	 */
	public void deleteGroupTaskAssignByTaskId(Integer taskId){
		this.execute("deleteGroupTaskAssignByTaskId",taskId);
	}

	/**
	 * 通过主键得到团队任务分配表
	 * @param groupTaskAssignId 主键Id
	 */
	public TskGroupTaskAssign getGroupTaskAssignById(Integer groupTaskAssignId){
		return (TskGroupTaskAssign)this.queryEntity("getGroupTaskAssignById",groupTaskAssignId);
	}

	/**
	 * 查询团队任务分配表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TskGroupTaskAssign> queryGroupTaskAssignList(Map<String,Object> condition){
		return (List<TskGroupTaskAssign>)this.queryEntities("queryGroupTaskAssignList", condition);
	}

	/**
	 * 查询团队任务分配表
	 * @param taskId 查询条件
	 * @return
	 */
	public List<TskGroupTaskAssign> getGroupTaskAssignListById(Integer taskId){
		return (List<TskGroupTaskAssign>)this.queryEntities("getGroupTaskAssignListById", taskId);
	}

	/**
	 * 根据贷款团队ID和放款日期，得到任务团队列表
	 * @param groupId
	 * @param loanDate
	 * @return
	 */
	public List<TskGroupTaskAssign> getGroupTaskAssignListByGroupIdAndDate(Integer groupId,Date loanDate,Integer taskMold){
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("groupId",groupId);
		condition.put("loanDate",loanDate);
		condition.put("taskMold",taskMold);
		return (List<TskGroupTaskAssign>)this.queryEntities("getGroupTaskAssignListByGroupIdAndDate", condition);
	}

	@Override
	public List<TskGroupTaskAssignQuery> queryGroupTaskAssignByTaskId(
			Map<String,Object> condition) {
		return (List<TskGroupTaskAssignQuery>)this.queryEntities("queryGroupTaskAssignByTaskId", condition);
	}

	@Override
	public List<GroupUnsignedTaskBean> queryUnsiginedGroupByTaskId(
			Map<String, Object> condition) {
		return (List<GroupUnsignedTaskBean>)this.queryEntities("queryUnsiginedGroupByTaskId", condition);
	}

	@Override
	public TskGroupTaskAssign queryGroupTaskAssignByTeam(
			Map<String, Object> condition) {
		return (TskGroupTaskAssign)this.queryEntity("queryGroupTaskAssignByTeam",condition);
	}


}
