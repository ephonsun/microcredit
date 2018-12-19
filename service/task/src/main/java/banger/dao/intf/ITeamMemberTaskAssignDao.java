package banger.dao.intf;

import java.util.Date;
import java.util.List;
import java.util.Map;

import banger.domain.task.TskTeamMemberTaskAssign;
import banger.domain.task.TskTeamMemberTaskAssignQuery;
import banger.domain.task.UserTaskAssignBean;

/**
 * 团队成员任务分配明细表数据访问接口
 */
public interface ITeamMemberTaskAssignDao {

	/**
	 * 新增团队成员任务分配明细表
	 * @param teamMemberTaskAssign 实体对像
	 */
	void insertTeamMemberTaskAssign(TskTeamMemberTaskAssign teamMemberTaskAssign);

	/**
	 *修改团队成员任务分配明细表
	 * @param teamMemberTaskAssign 实体对像
	 */
	void updateTeamMemberTaskAssign(TskTeamMemberTaskAssign teamMemberTaskAssign);

	/**
	 * 删除配置信息
	 * @param teamMemberTaskAssignId
	 */
	void deleteTeamMemberTaskAssignById(Integer teamMemberTaskAssignId);

	/**
	 * 删除配置信息
	 * @param taskId 任务ID
	 */
	void deleteTeamMemberTaskAssignByTaskId(Integer taskId);


	/**
	 * 通过主键得到团队成员任务分配明细表
	 * @param teamMemberTaskAssignId 主键Id
	 */
	TskTeamMemberTaskAssign getTeamMemberTaskAssignById(Integer teamMemberTaskAssignId);

	/**
	 * 查询团队成员任务分配明细表
	 * @param condition 查询条件
	 * @return
	 */
	List<TskTeamMemberTaskAssign> queryTeamMemberTaskAssignList(Map<String,Object> condition);

	/**
	 * 查询团队成员任务分配明细表
	 * @param taskId 查询条件
	 * @return
	 */
	List<TskTeamMemberTaskAssign> getTeamMemberTaskAssignListByTaskId(Integer taskId);

	/**
	 * 查询团队成员任务分配明细表
	 * @param groupId 查询条件
	 * @param userId 查询条件
	 * @param loanDate 查询条件
	 * @param taskMold
	 * @return
	 */
	List<TskTeamMemberTaskAssign> getTeamMemberTaskAssignListByUserIdAndDate(Integer groupId, Integer userId, Date loanDate, Integer taskMold);
	
	/**
	 * 查询团队成员任务分配明细
	 * @param condition
	 * @return
	 */
	List<TskTeamMemberTaskAssignQuery> queryTeamMemberTaskAssignDetail(Map<String,Object> condition);
	
	/**
	 * 查询客户经理任务分配
	 * @param condition
	 * @return
	 */
	List<UserTaskAssignBean> queryUnsiginedTeamMemberById(Map<String,Object> condition);

	/**
	 * 根据团队id删除团队内客户经理的任务
	 * @param groupTaskAssignId
	 */
    void deleteTeamMemberTaskAssignByGroupId(Integer groupTaskAssignId);
}
