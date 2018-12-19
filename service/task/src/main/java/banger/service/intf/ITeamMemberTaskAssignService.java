package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.task.TskTeamMemberTaskAssign;
import banger.domain.task.UserTaskAssignBean;

/**
 * 团队成员任务分配明细表业务访问接口
 */
public interface ITeamMemberTaskAssignService {

	/**
	 * 新增团队成员任务分配明细表
	 * @param teamMemberTaskAssign 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertTeamMemberTaskAssign(TskTeamMemberTaskAssign teamMemberTaskAssign,Integer loginUserId);

	/**
	 *修改团队成员任务分配明细表
	 * @param teamMemberTaskAssign 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateTeamMemberTaskAssign(TskTeamMemberTaskAssign teamMemberTaskAssign,Integer loginUserId);


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
	 * 查询客户经理任务分配
	 * @param condition
	 * @return
	 */
	List<UserTaskAssignBean> queryUnsiginedTeamMemberById(Map<String,Object> condition);

	/**
	 * 根据客户经理id删除客户经理的任务
	 * @param teamMemberTaskAssignId
	 */
    void deleteByMemberId(Integer teamMemberTaskAssignId);

	/**
	 * 根据团队id删除团队内客户经理的任务
	 * @param groupTaskAssignId
	 */
	void deleteByGroupId(Integer groupTaskAssignId);
}
