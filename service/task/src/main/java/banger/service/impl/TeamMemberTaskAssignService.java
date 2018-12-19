package banger.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import banger.dao.intf.ITeamMemberTaskAssignDao;
import banger.domain.task.TskTeamMemberTaskAssign;
import banger.domain.task.UserTaskAssignBean;
import banger.framework.util.DateUtil;
import banger.service.intf.ITeamMemberTaskAssignService;

/**
 * 团队成员任务分配明细表业务访问类
 */
@Repository
public class TeamMemberTaskAssignService implements ITeamMemberTaskAssignService {

	@Autowired
	private ITeamMemberTaskAssignDao teamMemberTaskAssignDao;

	/**
	 * 新增团队成员任务分配明细表
	 * @param teamMemberTaskAssign 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertTeamMemberTaskAssign(TskTeamMemberTaskAssign teamMemberTaskAssign,Integer loginUserId){
		teamMemberTaskAssign.setCreateUser(loginUserId);
		teamMemberTaskAssign.setCreateDate(DateUtil.getCurrentDate());
		teamMemberTaskAssign.setUpdateUser(loginUserId);
		teamMemberTaskAssign.setUpdateDate(DateUtil.getCurrentDate());
		teamMemberTaskAssign.setIsDel(0);
		this.teamMemberTaskAssignDao.insertTeamMemberTaskAssign(teamMemberTaskAssign);
	}

	/**
	 *修改团队成员任务分配明细表
	 * @param teamMemberTaskAssign 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateTeamMemberTaskAssign(TskTeamMemberTaskAssign teamMemberTaskAssign,Integer loginUserId){
		teamMemberTaskAssign.setUpdateUser(loginUserId);
		teamMemberTaskAssign.setUpdateDate(DateUtil.getCurrentDate());
		this.teamMemberTaskAssignDao.updateTeamMemberTaskAssign(teamMemberTaskAssign);
	}

	/**
	 * 通过主键得到团队成员任务分配明细表
	 * @param TEAM_MEMBER_TASK_ASSIGN_ID 主键Id
	 */
	public TskTeamMemberTaskAssign getTeamMemberTaskAssignById(Integer teamMemberTaskAssignId){
		return this.teamMemberTaskAssignDao.getTeamMemberTaskAssignById(teamMemberTaskAssignId);
	}

	/**
	 * 查询团队成员任务分配明细表
	 * @param condition 查询条件
	 * @return
	 */
	public List<TskTeamMemberTaskAssign> queryTeamMemberTaskAssignList(Map<String,Object> condition){
		return this.teamMemberTaskAssignDao.queryTeamMemberTaskAssignList(condition);
	}


	@Override
	public List<UserTaskAssignBean> queryUnsiginedTeamMemberById(
			Map<String, Object> condition) {
		return teamMemberTaskAssignDao.queryUnsiginedTeamMemberById(condition);
	}

	@Override
	public void deleteByMemberId(Integer teamMemberTaskAssignId) {
		teamMemberTaskAssignDao.deleteTeamMemberTaskAssignById(teamMemberTaskAssignId);
	}

	/**
	 * 根据团队id删除团队内客户经理的任务
	 * @param groupTaskAssignId
	 */
	@Override
	public void deleteByGroupId(Integer groupTaskAssignId){
		teamMemberTaskAssignDao.deleteTeamMemberTaskAssignByGroupId(groupTaskAssignId);
	}

}
