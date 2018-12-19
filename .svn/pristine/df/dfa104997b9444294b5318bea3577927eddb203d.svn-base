package banger.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.dao.PageSizeDao;
import banger.dao.intf.ITeamMemberTaskAssignDao;
import banger.domain.task.TskTeamMemberTaskAssign;
import banger.domain.task.TskTeamMemberTaskAssignQuery;
import banger.domain.task.UserTaskAssignBean;

/**
 * 团队成员任务分配明细表数据访问类
 */
@Repository
public class TeamMemberTaskAssignDao extends PageSizeDao<TskTeamMemberTaskAssign> implements ITeamMemberTaskAssignDao {

	/**
	 * 新增团队成员任务分配明细表
	 * @param teamMemberTaskAssign 实体对像
	 */
	public void insertTeamMemberTaskAssign(TskTeamMemberTaskAssign teamMemberTaskAssign){
		teamMemberTaskAssign.setTeamMemberTaskAssignId(this.newId().intValue());
		this.execute("insertTeamMemberTaskAssign",teamMemberTaskAssign);
	}

	/**
	 *修改团队成员任务分配明细表
	 * @param teamMemberTaskAssign 实体对像
	 */
	public void updateTeamMemberTaskAssign(TskTeamMemberTaskAssign teamMemberTaskAssign){
		this.execute("updateTeamMemberTaskAssign",teamMemberTaskAssign);
	}

	/**
	 * 删除配置信息
	 * @param teamMemberTaskAssignId
	 */
	public void deleteTeamMemberTaskAssignById(Integer teamMemberTaskAssignId){
		this.execute("deleteTeamMemberTaskAssignById",teamMemberTaskAssignId);
	}

	/**
	 * 根据团队id删除团队内客户经理的任务
	 * @param groupTaskAssignId
	 */
	public void deleteTeamMemberTaskAssignByGroupId(Integer groupTaskAssignId){
		this.execute("deleteTeamMemberTaskAssignByGroupId",groupTaskAssignId);
	}



	/**
	 * 删除配置信息
	 * @param taskId 任务ID
	 */
	public void deleteTeamMemberTaskAssignByTaskId(Integer taskId){
		this.execute("deleteTeamMemberTaskAssignByTaskId",taskId);
	}

	/**
	 * 通过主键得到团队成员任务分配明细表
	 * @param teamMemberTaskAssignId 主键Id
	 */
	public TskTeamMemberTaskAssign getTeamMemberTaskAssignById(Integer teamMemberTaskAssignId){
		return (TskTeamMemberTaskAssign)this.queryEntity("getTeamMemberTaskAssignById",teamMemberTaskAssignId);
	}

	/**
	 * 查询团队成员任务分配明细表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TskTeamMemberTaskAssign> queryTeamMemberTaskAssignList(Map<String,Object> condition){
		return (List<TskTeamMemberTaskAssign>)this.queryEntities("queryTeamMemberTaskAssignList", condition);
	}

	/**
	 * 查询团队成员任务分配明细表
	 * @param taskId 查询条件
	 * @return
	 */
	public List<TskTeamMemberTaskAssign> getTeamMemberTaskAssignListByTaskId(Integer taskId){
		return (List<TskTeamMemberTaskAssign>)this.queryEntities("getTeamMemberTaskAssignListByTaskId", taskId);
	}

	/**
	 * 查询团队成员任务分配明细表
	 * @param groupId 查询条件
	 * @param userId 查询条件
	 * @param loanDate 查询条件
	 * @param i
	 * @return
	 */
	public List<TskTeamMemberTaskAssign> getTeamMemberTaskAssignListByUserIdAndDate(Integer groupId, Integer userId, Date loanDate, Integer taskMold){
		Map<String,Object> condtion = new HashMap<String, Object>();
		condtion.put("groupId",groupId);
		condtion.put("userId",userId);
		condtion.put("taskMold",taskMold);
		condtion.put("loanDate",loanDate);
		return (List<TskTeamMemberTaskAssign>)this.queryEntities("getTeamMemberTaskAssignListByUserIdAndDate", condtion);
	}


	@Override
	public List<TskTeamMemberTaskAssignQuery> queryTeamMemberTaskAssignDetail(
			Map<String, Object> condition) {
		return (List<TskTeamMemberTaskAssignQuery>)this.queryEntities("queryTeamMemberTaskAssignDetail", condition);
	}

	@Override
	public List<UserTaskAssignBean> queryUnsiginedTeamMemberById(
			Map<String, Object> condition) {
		return (List<UserTaskAssignBean>)this.queryEntities("queryUnsiginedTeamMemberById", condition);
	}

}
