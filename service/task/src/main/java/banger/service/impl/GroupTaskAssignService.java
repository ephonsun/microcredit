package banger.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import banger.dao.intf.IGroupTaskAssignDao;
import banger.dao.intf.ITaskInfoDao;
import banger.dao.intf.ITeamMemberTaskAssignDao;
import banger.domain.task.GroupUnsignedTaskBean;
import banger.domain.task.TskGroupTaskAssign;
import banger.domain.task.TskGroupTaskAssignQuery;
import banger.domain.task.TskTaskInfo;
import banger.domain.task.TskTeamMemberTaskAssignQuery;
import banger.framework.util.DateUtil;
import banger.service.intf.IGroupTaskAssignService;

/**
 * 团队任务分配表业务访问类
 */
@Repository
public class GroupTaskAssignService implements IGroupTaskAssignService {

	@Autowired
	private IGroupTaskAssignDao groupTaskAssignDao;
	@Autowired
	private ITaskInfoDao taskInfoDao;
	@Autowired
	private ITeamMemberTaskAssignDao teamMemberTaskAssignDao;	

	/**
	 * 新增团队任务分配表
	 * @param groupTaskAssign 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertGroupTaskAssign(TskGroupTaskAssign groupTaskAssign,Integer loginUserId){
		groupTaskAssign.setCreateUser(loginUserId);
		groupTaskAssign.setCreateDate(DateUtil.getCurrentDate());
		groupTaskAssign.setUpdateUser(loginUserId);
		groupTaskAssign.setUpdateDate(DateUtil.getCurrentDate());
		this.groupTaskAssignDao.insertGroupTaskAssign(groupTaskAssign);
	}

	/**
	 *修改团队任务分配表
	 * @param groupTaskAssign 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateGroupTaskAssign(TskGroupTaskAssign groupTaskAssign,Integer loginUserId){
		groupTaskAssign.setUpdateUser(loginUserId);
		groupTaskAssign.setUpdateDate(DateUtil.getCurrentDate());
		this.groupTaskAssignDao.updateGroupTaskAssign(groupTaskAssign);
	}

	/**
	 * 通过主键删除团队任务分配表
	 * @param groupTaskAssignId 主键Id
	 */
	public void deleteGroupTaskAssignById(Integer groupTaskAssignId){
		this.groupTaskAssignDao.deleteGroupTaskAssignById(groupTaskAssignId);
		teamMemberTaskAssignDao.deleteTeamMemberTaskAssignByGroupId(groupTaskAssignId);
	}

	/**
	 * 通过主键得到团队任务分配表
	 * @param groupTaskAssignId 主键Id
	 */
	public TskGroupTaskAssign getGroupTaskAssignById(Integer groupTaskAssignId){
		return this.groupTaskAssignDao.getGroupTaskAssignById(groupTaskAssignId);
	}

	/**
	 * 查询团队任务分配表
	 * @param condition 查询条件
	 * @return
	 */
	public List<TskGroupTaskAssign> queryGroupTaskAssignList(Map<String,Object> condition){
		return this.groupTaskAssignDao.queryGroupTaskAssignList(condition);
	}

	@Override
	public List<TskGroupTaskAssignQuery> queryGroupTaskAssignByTaskId(
			Map<String,Object> condition,Integer taskId) {
		List<TskGroupTaskAssignQuery> list=groupTaskAssignDao.queryGroupTaskAssignByTaskId(condition);
		//第一条数据  总计
		List<TskGroupTaskAssignQuery> newList=new ArrayList<TskGroupTaskAssignQuery>();
		TskTaskInfo task=taskInfoDao.getTaskInfoById(taskId);
		TskGroupTaskAssignQuery total=new TskGroupTaskAssignQuery();
		total.setTeamGroupName("合计");
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("##,###");
		total.setAssignTargetF(myformat.format(task.getTaskTarget()));//任务目标
		total.setAssignNumF(myformat.format(task.getTaskAssign()));//已分配
		if(task.getTaskAssign()==null){
			total.setUnSiginedNum(task.getTaskTarget().intValue());	
		} else{
			total.setUnSiginedNum(task.getTaskTarget().intValue()-task.getTaskAssign().intValue());	
		}
		total.setUnSiginedNumF(myformat.format(total.getUnSiginedNum()));
		total.setFinishNum(task.getTaskFinish());
		total.setFinishNumF(myformat.format(task.getTaskFinish()));//已分配
		total.setAssignTarget(task.getTaskTarget());
		total.setAssignNum(task.getTaskAssign());
		//已分配比例
		total.setSiginedPercent(culPercent(total.getAssignNum(),total.getAssignTarget()));
		//完成进度
		total.setFinishPercent(culPercent(total.getFinishNum(),total.getAssignTarget()));
		newList.add(total);
		for (TskGroupTaskAssignQuery tskGroupTaskAssignQuery : list) {
			if(tskGroupTaskAssignQuery.getAssignNum()==null){
				tskGroupTaskAssignQuery.setUnSiginedNum(tskGroupTaskAssignQuery.getAssignTarget().intValue());
			}else{
				tskGroupTaskAssignQuery.setUnSiginedNum(tskGroupTaskAssignQuery.getAssignTarget().intValue()-tskGroupTaskAssignQuery.getAssignNum().intValue());
			}
			if (tskGroupTaskAssignQuery.getAssignNum() != null)
				tskGroupTaskAssignQuery.setAssignNumF(myformat.format(tskGroupTaskAssignQuery.getAssignNum()));
			if (tskGroupTaskAssignQuery.getAssignTarget() != null)
				tskGroupTaskAssignQuery.setAssignTargetF(myformat.format(tskGroupTaskAssignQuery.getAssignTarget()));
			if (tskGroupTaskAssignQuery.getUnSiginedNum() != null)
				tskGroupTaskAssignQuery.setUnSiginedNumF(myformat.format(tskGroupTaskAssignQuery.getUnSiginedNum()));
			if (tskGroupTaskAssignQuery.getFinishNum() != null)
				tskGroupTaskAssignQuery.setFinishNumF(myformat.format(tskGroupTaskAssignQuery.getFinishNum()));
			//已分配比例
			tskGroupTaskAssignQuery.setSiginedPercent(culPercent(tskGroupTaskAssignQuery.getAssignNum(),tskGroupTaskAssignQuery.getAssignTarget()));
			//完成进度
			tskGroupTaskAssignQuery.setFinishPercent(culPercent(tskGroupTaskAssignQuery.getFinishNum(),tskGroupTaskAssignQuery.getAssignTarget()));

			newList.add(tskGroupTaskAssignQuery);
		}
		return newList;
	}
	
	private String culPercent(Integer a,Integer b){
		if (b.intValue() == 0) {
			return "--";
		}
		if(a==null || a==0){
			return "0%";
		}
		if(b==null){
			return "0%";
		}
		if(a==b){
			return "100%";
		}
		double percent=(a/(double)b)*100;
		return String.format("%.2f",percent)+"%";		
	}

	@Override
	public List<GroupUnsignedTaskBean> queryUnsiginedGroupByTaskId(
			Map<String, Object> condition) {
		return groupTaskAssignDao.queryUnsiginedGroupByTaskId(condition);
	}

	@Override
	public List<TskTeamMemberTaskAssignQuery> queryTeamMemberTaskAssignDetail(
			Map<String, Object> condition) {
		List<TskTeamMemberTaskAssignQuery> list=teamMemberTaskAssignDao.queryTeamMemberTaskAssignDetail(condition);
		Integer groupTaskAssignId=(Integer)condition.get("groupTaskAssignId");
		TskGroupTaskAssign groupTaskAssign=groupTaskAssignDao.getGroupTaskAssignById(groupTaskAssignId);
		//合计
		TskTeamMemberTaskAssignQuery total=new TskTeamMemberTaskAssignQuery();
		total.setTeamMemberName("合计");
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("##,###");
		total.setAssignTargetF(myformat.format(groupTaskAssign.getAssignTarget()));//任务目标
		total.setAssignNumF(myformat.format(groupTaskAssign.getAssignNum()));//已分配
		total.setFinishNumF(myformat.format(groupTaskAssign.getFinishNum()));
		total.setAssignNum(groupTaskAssign.getAssignNum());
		total.setFinishNum(groupTaskAssign.getFinishNum());
		total.setAssignTarget(groupTaskAssign.getAssignTarget());

		//已分配比例
		total.setAssignPercent(culPercent(groupTaskAssign.getAssignNum(),groupTaskAssign.getAssignTarget()));
		//完成进度
		total.setFinishPercent(culPercent(groupTaskAssign.getFinishNum(),groupTaskAssign.getAssignTarget()));
		if(groupTaskAssign.getAssignNum()==null){
			total.setUnSiginedNum(groupTaskAssign.getAssignTarget().intValue());	
		}else{
			total.setUnSiginedNum(groupTaskAssign.getAssignTarget().intValue()-groupTaskAssign.getAssignNum().intValue());	
		}
		total.setUnSiginedNumF(myformat.format(total.getUnSiginedNum()));
		List<TskTeamMemberTaskAssignQuery> newList=new ArrayList<TskTeamMemberTaskAssignQuery>();
		newList.add(total);
		
		for (TskTeamMemberTaskAssignQuery tskTeamMemberTaskAssignQuery : list) {
			if (tskTeamMemberTaskAssignQuery.getAssignNum() != null)
				tskTeamMemberTaskAssignQuery.setAssignTargetF(myformat.format(tskTeamMemberTaskAssignQuery.getAssignNum()));
			if (tskTeamMemberTaskAssignQuery.getFinishNum() != null)
				tskTeamMemberTaskAssignQuery.setFinishNumF(myformat.format(tskTeamMemberTaskAssignQuery.getFinishNum()));
			//完成比率
			tskTeamMemberTaskAssignQuery.setFinishPercent(culPercent(tskTeamMemberTaskAssignQuery.getFinishNum(),tskTeamMemberTaskAssignQuery.getAssignNum()));
			//已分配比例
			tskTeamMemberTaskAssignQuery.setAssignPercent(culPercent(tskTeamMemberTaskAssignQuery.getAssignNum(),groupTaskAssign.getAssignTarget()));

			tskTeamMemberTaskAssignQuery.setAssignTarget(tskTeamMemberTaskAssignQuery.getAssignNum());
			tskTeamMemberTaskAssignQuery.setAssignNum(null);

			newList.add(tskTeamMemberTaskAssignQuery);
		}
		
		return newList;
	}

	@Override
	public TskGroupTaskAssign queryGroupTaskAssignByTeam(
			Map<String, Object> condition) {
		return groupTaskAssignDao.queryGroupTaskAssignByTeam(condition);
	}


}
