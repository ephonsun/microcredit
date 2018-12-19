package banger.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import banger.dao.intf.ITaskInfoDao;
import banger.domain.task.TskTaskInfo;
import banger.domain.task.TskTaskInfoQuery;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * 任务表数据访问类
 */
@Repository
public class TaskInfoDao extends PageSizeDao<TskTaskInfo> implements ITaskInfoDao {

	/**
	 * 新增任务表
	 * @param taskInfo 实体对像
	 */
	public void insertTaskInfo(TskTaskInfo taskInfo){
		taskInfo.setTaskId(this.newId().intValue());
		this.execute("insertTaskInfo",taskInfo);
	}

	/**
	 *修改任务表
	 * @param taskInfo 实体对像
	 */
	public void updateTaskInfo(TskTaskInfo taskInfo){
		this.execute("updateTaskInfo",taskInfo);
	}

	/**
	 * 通过主键删除任务表
	 * @param taskId 主键Id
	 */
	public void deleteTaskInfoById(Integer taskId){
		this.execute("deleteTaskInfoById",taskId);
	}

	/**
	 * 通过主键得到任务表
	 * @param taskId 主键Id
	 */
	public TskTaskInfo getTaskInfoById(Integer taskId){
		return (TskTaskInfo)this.queryEntity("getTaskInfoById",taskId);
	}

//	/**
//	 * 查询任务表
//	 * @param condition 查询条件
//	 * @return
//	 */
//	public List<TskTaskInfoQuery> queryTaskInfoList(Map<String,Object> condition){
//		return (List<TskTaskInfoQuery>)this.queryEntities("queryTaskInfoList", condition);
//	}

	/**
	 * 分页查询任务表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<TskTaskInfoQuery> queryTaskInfoList(Map<String,Object> condition,IPageSize page){
		return (IPageList<TskTaskInfoQuery>)this.queryEntities("queryTaskList", page, condition);
	}

	/**
	 * 任务名称唯一性校验
	 * @param taskTitle
	 * @return
	 */
	@Override
	public List<TskTaskInfo> getTaskInfoByTitle(String taskTitle) {
		return (List<TskTaskInfo>)this.queryEntities("getTaskInfoByTitle", taskTitle);
	}

//	@Override
//	public IPageList<TskTaskInfoQuery> queryMyTaskInfoList(
//			Map<String, Object> condition, IPageSize page) {
//		return (IPageList<TskTaskInfoQuery>)this.queryEntities("queryMyTaskInfoList", page, condition);
//	}

	/**
	 * app端分页查询客户经理个人任务表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<TskTaskInfoQuery> queryTaskInfoListForApp(Map<String,Object> condition,IPageSize page){
		return (IPageList<TskTaskInfoQuery>)this.queryEntities("queryTaskInfoListForApp", page, condition);
	}

}
