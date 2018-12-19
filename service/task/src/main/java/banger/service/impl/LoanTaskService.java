package banger.service.impl;

import banger.dao.intf.IGroupTaskAssignDao;
import banger.dao.intf.ILoanTaskDao;
import banger.dao.intf.ITaskInfoDao;
import banger.dao.intf.ITeamMemberTaskAssignDao;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.task.LoanUserTask;
import banger.domain.task.TskGroupTaskAssign;
import banger.domain.task.TskTaskInfo;
import banger.domain.task.TskTeamMemberTaskAssign;
import banger.moduleIntf.ILoanApplyProvider;
import banger.moduleIntf.ILoanTaskProvider;
import banger.service.intf.ILoanTaskService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by zhusw on 2017/7/18.
 */
@Repository
public class LoanTaskService implements ILoanTaskService,ILoanTaskProvider {

    @Autowired
    private ILoanTaskDao loanTaskDao;

    @Autowired
    private ITeamMemberTaskAssignDao teamMemberTaskAssignDao;

    @Autowired
    private IGroupTaskAssignDao groupTaskAssignDao;

    @Autowired
    private ITaskInfoDao taskInfoDao;

    @Resource
    private ILoanApplyProvider loanApplyProvider;

    /**
     * 统计用户放款信息
     * @param beginDate     任务开始日期
     * @param endDate       任务结束日期
     * @param userId        用户Id
     * @param groupId       团队Id
     * @return
     */
    private LoanUserTask getUserTaskAmountByCondtion(Date beginDate, Date endDate,Integer userId,Integer groupId){
        return loanTaskDao.getUserTaskAmountByCondtion(beginDate,endDate,userId,groupId);
    }

    /**
     * 统计用户放款信息
     * @param beginDate     任务开始日期
     * @param endDate       任务结束日期
     * @param groupId       团队Id
     * @return
     */
    private LoanUserTask getGroupTaskAmountByCondtion(Date beginDate, Date endDate,Integer groupId){
        return loanTaskDao.getGroupTaskAmountByCondtion(beginDate,endDate,groupId);
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
                        LoanUserTask loanUserTask = this.getGroupTaskAmountByCondtion(tskTaskInfo.getStartDate(), tskTaskInfo.getEndDate(), groupAssign.getTeamGroupId());
                        if (loanUserTask != null) {
                            if (tskTaskInfo.getTaskType() != null && tskTaskInfo.getTaskType().equals(1)) {
                                groupAssign.setFinishNum(loanUserTask.getLoanAmount().intValue());
                            } else if (tskTaskInfo.getTaskType() != null && tskTaskInfo.getTaskType().equals(2)) {
                                groupAssign.setFinishNum(loanUserTask.getLoanTotal());
                            }
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
                LoanUserTask loanUserTask = this.getGroupTaskAmountByCondtion(tskTaskInfo.getStartDate(),tskTaskInfo.getEndDate(),groupAssign.getTeamGroupId());
                if(loanUserTask!=null){
                    if(tskTaskInfo.getTaskType()!=null && tskTaskInfo.getTaskType().equals(1)){
                        groupAssign.setFinishNum(loanUserTask.getLoanAmount().intValue());
                    }else if(tskTaskInfo.getTaskType()!=null && tskTaskInfo.getTaskType().equals(2)){
                        groupAssign.setFinishNum(loanUserTask.getLoanTotal());
                    }
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
                LoanUserTask loanUserTask = this.getUserTaskAmountByCondtion(tskTaskInfo.getStartDate(),tskTaskInfo.getEndDate(),taskAssign.getMemberUserId(),taskAssign.getTeamGroupId());
                if(loanUserTask!=null){
                    if(tskTaskInfo.getTaskType()!=null && tskTaskInfo.getTaskType().equals(1)){
                        taskAssign.setFinishNum(loanUserTask.getLoanAmount().intValue());
                    }else if(tskTaskInfo.getTaskType()!=null && tskTaskInfo.getTaskType().equals(2)){
                        taskAssign.setFinishNum(loanUserTask.getLoanTotal());
                    }
                    if(taskAssign.getAssignNum()!=null && taskAssign.getFinishNum()!=null && taskAssign.getFinishNum()>=taskAssign.getAssignNum())
                        taskAssign.setTaskFinishStatus(2);
                    teamMemberTaskAssignDao.updateTeamMemberTaskAssign(taskAssign);

                    //个人任务主表完成数量需要更新
                    if (tskTaskInfo.getTskLevel() == 3) {
                        tskTaskInfo.setTaskAssign(taskAssign.getAssignNum());
                        if(tskTaskInfo.getTaskType()!=null && tskTaskInfo.getTaskType().equals(1)){
                            tskTaskInfo.setTaskFinish(loanUserTask.getLoanAmount().intValue());
                        }else if(tskTaskInfo.getTaskType()!=null && tskTaskInfo.getTaskType().equals(2)){
                            tskTaskInfo.setTaskFinish(loanUserTask.getLoanTotal());
                        }
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
                        LoanUserTask loanUserTask = this.getUserTaskAmountByCondtion(tskTaskInfo.getStartDate(), tskTaskInfo.getEndDate(), taskAssign.getMemberUserId(), taskAssign.getTeamGroupId());
                        if (loanUserTask != null) {
                            if (tskTaskInfo.getTaskType() != null && tskTaskInfo.getTaskType().equals(1)) {
                                taskAssign.setFinishNum(loanUserTask.getLoanAmount().intValue());
                            } else if (tskTaskInfo.getTaskType() != null && tskTaskInfo.getTaskType().equals(2)) {
                                taskAssign.setFinishNum(loanUserTask.getLoanTotal());
                            }
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
     * 更新贷款任务的金额和数量
     * @param loanId
     */
    public void updateLoanTaskAmount(Integer loanId){
        LoanApplyInfo loanApplyInfo = loanApplyProvider.getApplyInfoById(loanId);
        if(loanApplyInfo!=null){
            Integer groupId = loanApplyInfo.getLoanAfterGroupId();
            Date loanDate = loanApplyInfo.getLoanCreditDate();
            Integer userId = loanApplyInfo.getLoanInvestigationUserId();
            this.updateLoanTaskAmountByGroupId(groupId,loanDate);
            this.updateLoanTaskAmountByUserId(groupId,userId,loanDate);
        }
    }

    /**
     * 更新团队金额
     * @param groupId
     */
    private void updateLoanTaskAmountByGroupId(Integer groupId,Date loanDate){
        if(groupId!=null && groupId.intValue()>0 && loanDate!=null){
            List<TskGroupTaskAssign> groupTaskList = groupTaskAssignDao.getGroupTaskAssignListByGroupIdAndDate(groupId,loanDate,0);
            if(groupTaskList!=null && groupTaskList.size()>0){
                for(TskGroupTaskAssign groupAssign : groupTaskList){
                    TskTaskInfo taskInfo = taskInfoDao.getTaskInfoById(groupAssign.getTaskId());
                    this.updateGroupTaskFinishAmount(taskInfo,groupAssign);
                }
            }
        }
    }

    /**
     * 更新个人金额
     * @param groupId
     * @param userId
     * @param loanDate
     */
    private void updateLoanTaskAmountByUserId(Integer groupId,Integer userId,Date loanDate){
        if(userId!=null && userId.intValue()>0 && loanDate!=null){
            List<TskTeamMemberTaskAssign> taskAssignList = teamMemberTaskAssignDao.getTeamMemberTaskAssignListByUserIdAndDate(groupId,userId,loanDate,0);
            if(taskAssignList!=null && taskAssignList.size()>0){
                for(TskTeamMemberTaskAssign taskAssign : taskAssignList){
                    TskTaskInfo taskInfo = taskInfoDao.getTaskInfoById(taskAssign.getTaskId());
                    this.updateUserTaskFinishAmount(taskInfo,taskAssign);
                }
            }
        }
    }
}
