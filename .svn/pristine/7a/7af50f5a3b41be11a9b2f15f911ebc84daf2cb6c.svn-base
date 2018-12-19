package banger.service.impl;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import banger.dao.intf.IGroupTaskAssignDao;
import banger.dao.intf.ITeamMemberTaskAssignDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import banger.dao.intf.ITaskInfoDao;
import banger.domain.task.TskTaskInfo;
import banger.domain.task.TskTaskInfoQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.service.intf.ITaskInfoService;

/**
 * 任务表业务访问类
 */
@Repository
public class TaskInfoService implements ITaskInfoService {

	@Autowired
	private ITaskInfoDao taskInfoDao;

	@Autowired
	private IGroupTaskAssignDao groupTaskAssignDao;

	@Autowired
	private ITeamMemberTaskAssignDao teamMemberTaskAssignDao;

	/**
	 * 新增任务表
	 * @param taskInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertTaskInfo(TskTaskInfo taskInfo,Integer loginUserId){
		taskInfo.setCreateUser(loginUserId);
		taskInfo.setCreateDate(DateUtil.getCurrentDate());
		taskInfo.setUpdateUser(loginUserId);
		taskInfo.setUpdateDate(DateUtil.getCurrentDate());
		
		taskInfo.setTaskFinish(0);
		taskInfo.setIsDel(0);
		taskInfo.setTaskStatus(1);
		this.taskInfoDao.insertTaskInfo(taskInfo);
	}

	/**
	 *修改任务表
	 * @param taskInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateTaskInfo(TskTaskInfo taskInfo,Integer loginUserId){
		taskInfo.setUpdateUser(loginUserId);
		taskInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.taskInfoDao.updateTaskInfo(taskInfo);
	}

	/**
	 * 通过主键删除任务表
	 * @param taskId 主键Id
	 */
	public void deleteTaskInfoById(Integer taskId){
		this.taskInfoDao.deleteTaskInfoById(taskId);
		this.groupTaskAssignDao.deleteGroupTaskAssignByTaskId(taskId);
		this.teamMemberTaskAssignDao.deleteTeamMemberTaskAssignByTaskId(taskId);
	}

	/**
	 * 通过主键得到任务表
	 * @param taskId 主键Id
	 */
	public TskTaskInfo getTaskInfoById(Integer taskId){
		return this.taskInfoDao.getTaskInfoById(taskId);
	}

//	/**
//	 * 查询任务表
//	 * @param condition 查询条件
//	 * @return
//	 */
//	public List<TskTaskInfoQuery> queryTaskInfoList(Map<String,Object> condition){
//		return this.taskInfoDao.queryTaskInfoList(condition);
//	}

	/**
	 * 分页查询任务表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<TskTaskInfoQuery> queryTaskInfoList(Map<String,Object> condition,IPageSize page){
		IPageList<TskTaskInfoQuery> list=this.taskInfoDao.queryTaskInfoList(condition,page);
		for (TskTaskInfoQuery tskTaskInfoQuery : list) {			
			convert(tskTaskInfoQuery);
		}
		return list;
	}


	
	private void convert(TskTaskInfoQuery tskTaskInfoQuery){
		//任务期限
		String start=DateUtil.format(tskTaskInfoQuery.getStartDate(),"yyyy/MM/dd");
		String end=DateUtil.format(tskTaskInfoQuery.getEndDate(),"yyyy/MM/dd");
		tskTaskInfoQuery.setTaskDateLimit(start+" -- "+end);
		//完成进度
		if(tskTaskInfoQuery.getTaskFinish()==null || tskTaskInfoQuery.getTaskFinish()==0){
			tskTaskInfoQuery.setTaskPercent("0%");
		}else{
			if (tskTaskInfoQuery.getTaskTarget().intValue() == 0) {
				tskTaskInfoQuery.setTaskPercent("--");
			}else{
				double percent=(tskTaskInfoQuery.getTaskFinish()/(double)tskTaskInfoQuery.getTaskTarget())*100;
				//保留2位小数
				tskTaskInfoQuery.setTaskPercent(String.format("%.2f",percent)+"%");
			}
		}
		if (tskTaskInfoQuery.getTaskTarget() != null) {
			if (tskTaskInfoQuery.getTaskMold() == 0) {
				//贷款任务
				if(tskTaskInfoQuery.getTaskType()==1){
					DecimalFormat myformat = new DecimalFormat();
					myformat.applyPattern("##,###");
					tskTaskInfoQuery.setTarget(myformat.format(tskTaskInfoQuery.getTaskTarget())+"元");
				}else{
					tskTaskInfoQuery.setTarget(tskTaskInfoQuery.getTaskTarget()+"笔");
				}
			}else{
				//营销任务
				tskTaskInfoQuery.setTaskType(3);
				tskTaskInfoQuery.setTarget(tskTaskInfoQuery.getTaskTarget()+"个");
			}
		}
	}

	/**
	 * 任务名称唯一性校验
	 * @param taskTitle
	 * @return
	 */
	@Override
	public List<TskTaskInfo> getTaskInfoByTitle(String taskTitle) {
		return taskInfoDao.getTaskInfoByTitle(taskTitle);
	}

//	@Override
//	public IPageList<TskTaskInfoQuery> queryMyTaskInfoList(
//			Map<String, Object> condition, IPageSize page) {
//		IPageList<TskTaskInfoQuery> list=taskInfoDao.queryMyTaskInfoList(condition, page);
//		for (TskTaskInfoQuery tskTaskInfoQuery : list) {
//			convert(tskTaskInfoQuery);
//		}
//		return list;
//	}
	/**
	 * app端分页查询客户经理个人任务表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<TskTaskInfoQuery> queryTaskInfoListForApp(Map<String,Object> condition,IPageSize page){
		IPageList<TskTaskInfoQuery> list=this.taskInfoDao.queryTaskInfoListForApp(condition,page);
		for (TskTaskInfoQuery tskTaskInfoQuery : list) {
			convert(tskTaskInfoQuery);
		}
		return list;
	}

}
