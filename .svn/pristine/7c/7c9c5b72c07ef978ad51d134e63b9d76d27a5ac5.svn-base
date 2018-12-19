package banger.service.impl;

import banger.dao.intf.*;
import banger.domain.customer.CustPotentialCustomers;
import banger.domain.task.MarketingUserTask;
import banger.domain.task.TskGroupTaskAssign;
import banger.domain.task.TskTaskInfo;
import banger.domain.task.TskTeamMemberTaskAssign;
import banger.framework.util.DateUtil;
import banger.moduleIntf.ILoanApplyProvider;
import banger.moduleIntf.IMarketingTaskProvider;
import banger.moduleIntf.IPotentialCustomersProvider;
import banger.service.intf.IMarketingTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: yangdw
 * @param
 * @Description:营销任务业务接口
 * @Date: 10:14 2017/10/16
 */
@Repository
public class MarketingTaskService implements IMarketingTaskService,IMarketingTaskProvider {

    @Autowired
    private ILoanTaskDao loanTaskDao;
    @Autowired
    private IMarketingTaskDao marketingTaskDao;

    @Autowired
    private ITeamMemberTaskAssignDao teamMemberTaskAssignDao;

    @Autowired
    private IGroupTaskAssignDao groupTaskAssignDao;

    @Autowired
    private ITaskInfoDao taskInfoDao;

    @Resource
    private IPotentialCustomersProvider potentialCustomersProvider;

    /**
     * 统计用户放款信息
     * @param beginDate     任务开始日期
     * @param endDate       任务结束日期
     * @param userId        用户Id
     * @param groupId       团队Id
     * @return
     */
    private MarketingUserTask getUserTaskAmountByCondtion(Date beginDate, Date endDate,Integer userId,Integer groupId){
        return marketingTaskDao.getUserTaskAmountByCondtion(beginDate,endDate,userId,groupId);
    }

    /**
     * 统计用户放款信息
     * @param beginDate     任务开始日期
     * @param endDate       任务结束日期
     * @param groupId       团队Id
     * @return
     */
    private MarketingUserTask getGroupTaskAmountByCondtion(Date beginDate, Date endDate,Integer groupId){
        return marketingTaskDao.getGroupTaskAmountByCondtion(beginDate,endDate,groupId);
    }

    /**
     * 统计更新任务所有团队的贷款金额和数量
     * @param tskTaskInfo
     */
    private void updateTaskFinishAmount(TskTaskInfo tskTaskInfo){
        if(tskTaskInfo!=null && tskTaskInfo.getTaskId()!=null && tskTaskInfo.getTaskId().intValue()>0) {
            List<TskGroupTaskAssign> list = groupTaskAssignDao.getGroupTaskAssignListById(tskTaskInfo.getTaskId());
            Integer finishNum = 0;
            if(list!=null && list.size()>0){
                for(TskGroupTaskAssign groupAssign : list){
                    if(groupAssign.getFinishNum()!=null)
                        finishNum+=groupAssign.getFinishNum();
                }
            }
            tskTaskInfo.setTaskFinish(finishNum);
            if(tskTaskInfo.getTaskAssign()!=null && tskTaskInfo.getTaskFinish()!=null && tskTaskInfo.getTaskFinish().intValue()>=tskTaskInfo.getTaskTarget().intValue()){
                tskTaskInfo.setTaskStatus(2);
            }else{
                tskTaskInfo.setTaskStatus(1);
            }
            taskInfoDao.updateTaskInfo(tskTaskInfo);
        }
    }

    /**
     * 统计更新任务的分配团队的贷款金额和数量
     * @param tskTaskInfo
     */
    public void updateGroupTaskFinishAmount(TskTaskInfo tskTaskInfo, List<TskGroupTaskAssign> groupAssignList){
        if(tskTaskInfo!=null && tskTaskInfo.getStartDate()!=null && tskTaskInfo.getEndDate()!=null) {
            if(groupAssignList!=null && groupAssignList.size()>0) {
                for (TskGroupTaskAssign groupAssign : groupAssignList) {
                    if (groupAssign.getAssignTarget() != null && groupAssign.getAssignTarget().intValue() > 0) {
                        MarketingUserTask marketingUserTask = this.getGroupTaskAmountByCondtion(tskTaskInfo.getStartDate(), tskTaskInfo.getEndDate(), groupAssign.getTeamGroupId());
                        if (marketingUserTask != null) {
                            groupAssign.setFinishNum(marketingUserTask.getMarketingTotal());
                            if (groupAssign.getAssignTarget() != null && groupAssign.getFinishNum() != null && groupAssign.getFinishNum().intValue() >= groupAssign.getAssignTarget().intValue())
                                groupAssign.setTaskFinishStatus(2);
                            else {
                                groupAssign.setTaskFinishStatus(1);
                            }
                            groupTaskAssignDao.updateGroupTaskAssign(groupAssign);
                        }
                    }
                }
                this.updateTaskFinishAmount(tskTaskInfo);
            }
        }
    }

    /**
     * 统计更新任务的分配团队的贷款金额和数量
     * @param tskTaskInfo
     */
    public void updateGroupTaskFinishAmount(TskTaskInfo tskTaskInfo, TskGroupTaskAssign groupAssign){
        if(tskTaskInfo!=null && tskTaskInfo.getStartDate()!=null && tskTaskInfo.getEndDate()!=null) {
            if (groupAssign.getAssignTarget() != null && groupAssign.getAssignTarget().intValue() > 0) {
                MarketingUserTask marketingUserTask = this.getGroupTaskAmountByCondtion(tskTaskInfo.getStartDate(),tskTaskInfo.getEndDate(),groupAssign.getTeamGroupId());
                if(marketingUserTask!=null){
                    groupAssign.setFinishNum(marketingUserTask.getMarketingTotal());
                    if(groupAssign.getAssignTarget()!=null && groupAssign.getFinishNum()!=null && groupAssign.getFinishNum().intValue()>=groupAssign.getAssignTarget().intValue())
                        groupAssign.setTaskFinishStatus(2);
                    else{
                        groupAssign.setTaskFinishStatus(1);
                    }
                    groupTaskAssignDao.updateGroupTaskAssign(groupAssign);
                }
            }
            this.updateTaskFinishAmount(tskTaskInfo);
        }
    }

    /**
     * 统计更新任务的分配人的贷款金额和数量
     * @param tskTaskInfo
     * @param taskAssign
     */
    public void updateUserTaskFinishAmount(TskTaskInfo tskTaskInfo,TskTeamMemberTaskAssign taskAssign){
        if(tskTaskInfo!=null && tskTaskInfo.getStartDate()!=null && tskTaskInfo.getEndDate()!=null) {
            if (taskAssign.getAssignNum() != null && taskAssign.getAssignNum().intValue() > 0) {
                MarketingUserTask marketingUserTask = this.getUserTaskAmountByCondtion(tskTaskInfo.getStartDate(),tskTaskInfo.getEndDate(),taskAssign.getMemberUserId(),taskAssign.getTeamGroupId());
                if(marketingUserTask!=null){
                    taskAssign.setFinishNum(marketingUserTask.getMarketingTotal());
                    if(taskAssign.getAssignNum()!=null && taskAssign.getFinishNum()!=null && taskAssign.getFinishNum()>=taskAssign.getAssignNum())
                        taskAssign.setTaskFinishStatus(2);
                    teamMemberTaskAssignDao.updateTeamMemberTaskAssign(taskAssign);

                    //个人任务主表完成数量需要更新
                    if (tskTaskInfo.getTskLevel() == 3) {
                        tskTaskInfo.setTaskAssign(taskAssign.getAssignNum());
                        tskTaskInfo.setTaskFinish(marketingUserTask.getMarketingTotal());
                        if(tskTaskInfo.getTaskAssign()!=null && tskTaskInfo.getTaskFinish()!=null && tskTaskInfo.getTaskFinish().intValue()>=tskTaskInfo.getTaskTarget().intValue()){
                            tskTaskInfo.setTaskStatus(2);
                        }else{
                            tskTaskInfo.setTaskStatus(1);
                        }
                        taskInfoDao.updateTaskInfo(tskTaskInfo);
                    }
                }
            }
        }
    }

    /**
     * 统计更新任务的分配人的贷款金额和数量
     * @param tskTaskInfo
     * @param taskAssignList
     */
    public void updateUserTaskFinishAmount(TskTaskInfo tskTaskInfo, List<TskTeamMemberTaskAssign> taskAssignList){
        if(tskTaskInfo!=null && tskTaskInfo.getStartDate()!=null && tskTaskInfo.getEndDate()!=null) {
            if(taskAssignList!=null && taskAssignList.size()>0) {
                for (TskTeamMemberTaskAssign taskAssign : taskAssignList) {
                    if (taskAssign.getAssignNum() != null && taskAssign.getAssignNum().intValue() > 0) {
                        MarketingUserTask marketingUserTask = this.getUserTaskAmountByCondtion(tskTaskInfo.getStartDate(), tskTaskInfo.getEndDate(), taskAssign.getMemberUserId(), taskAssign.getTeamGroupId());
                        if (marketingUserTask != null) {
                            taskAssign.setFinishNum(marketingUserTask.getMarketingTotal());
                            if (taskAssign.getAssignNum() != null && taskAssign.getFinishNum() != null && taskAssign.getFinishNum() >= taskAssign.getAssignNum())
                                taskAssign.setTaskFinishStatus(2);
                            teamMemberTaskAssignDao.updateTeamMemberTaskAssign(taskAssign);
                        }
                    }
                }
            }
        }
    }

    /**
     * 更新营销任务的数量
     * @param id 潜在客户新建id
     */
    public void updateMarketingTaskAmount(Integer id,Integer groupId){
        CustPotentialCustomers potentialCustomer = potentialCustomersProvider.getPotentialCustomersById(id);
        if (potentialCustomer != null && groupId != null) {
//            Integer groupId = potentialCustomer.getAttributionTeam();
            Date createDate = potentialCustomer.getCreateDate();
            Integer userId = potentialCustomer.getCreateUser();
            this.updateMarketingTaskAmountByGroupId(groupId, createDate);
            this.updateMarketingTaskAmountByUserId(groupId, userId, createDate);
        }
    }

    /**
     * 更新团队营销客户数量
     * @param groupId
     */
    private void updateMarketingTaskAmountByGroupId(Integer groupId,Date createDate){
        if(groupId!=null && groupId.intValue()>0 && createDate!=null){
            List<TskGroupTaskAssign> groupTaskList = groupTaskAssignDao.getGroupTaskAssignListByGroupIdAndDate(groupId,createDate,1);
            if(groupTaskList!=null && groupTaskList.size()>0){
                for(TskGroupTaskAssign groupAssign : groupTaskList){
                    TskTaskInfo taskInfo = taskInfoDao.getTaskInfoById(groupAssign.getTaskId());
                    this.updateGroupTaskFinishAmount(taskInfo,groupAssign);
                }
            }
        }
    }

    /**
     * 更新个人营销客户数量
     * @param groupId
     * @param userId
     * @param createDate
     */
    private void updateMarketingTaskAmountByUserId(Integer groupId,Integer userId,Date createDate){
        if(userId!=null && userId.intValue()>0 && createDate!=null){
            List<TskTeamMemberTaskAssign> taskAssignList = teamMemberTaskAssignDao.getTeamMemberTaskAssignListByUserIdAndDate(groupId,userId,createDate, 1);
            if(taskAssignList!=null && taskAssignList.size()>0){
                for(TskTeamMemberTaskAssign taskAssign : taskAssignList){
                    TskTaskInfo taskInfo = taskInfoDao.getTaskInfoById(taskAssign.getTaskId());
                    this.updateUserTaskFinishAmount(taskInfo,taskAssign);
                }
            }
        }
    }

}
