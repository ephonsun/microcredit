package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.domain.task.TskTaskInfo;
import banger.domain.task.TskTaskInfoQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * 任务表数据访问接口
 */
public interface ITaskInfoDao {

	/**
	 * 新增任务表
	 * @param taskInfo 实体对像
	 */
	void insertTaskInfo(TskTaskInfo taskInfo);

	/**
	 *修改任务表
	 * @param taskInfo 实体对像
	 */
	void updateTaskInfo(TskTaskInfo taskInfo);

	/**
	 * 通过主键删除任务表
	 * @param taskId 主键Id
	 */
	void deleteTaskInfoById(Integer taskId);

	/**
	 * 通过主键得到任务表
	 * @param taskId 主键Id
	 */
	TskTaskInfo getTaskInfoById(Integer taskId);

//	/**
//	 * 查询任务表
//	 * @param condition 查询条件
//	 * @return
//	 */
//	List<TskTaskInfoQuery> queryTaskInfoList(Map<String,Object> condition);

	/**
	 * 分页查询任务表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<TskTaskInfoQuery> queryTaskInfoList(Map<String,Object> condition,IPageSize page);
	
	/**
	 * 根据任务名称查询任务
	 * @param taskTitle
	 * @return
	 */
	List<TskTaskInfo> getTaskInfoByTitle(String taskTitle);

	/**
//	 * 分页查询任务表
//	 * @param condition 查询条件
//	 * @param page 分页对像
//	 * @return
//	 */
//	IPageList<TskTaskInfoQuery> queryMyTaskInfoList(Map<String,Object> condition,IPageSize page);

	/**
	 * app端分页查询客户经理个人任务表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<TskTaskInfoQuery> queryTaskInfoListForApp(Map<String,Object> condition,IPageSize page);
}
