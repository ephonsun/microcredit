package banger.dao.impl;

import banger.dao.intf.ILoanTaskDao;
import banger.domain.task.LoanUserTask;
import banger.framework.dao.ObjectDao;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhusw on 2017/7/18.
 */
@Repository
public class LoanTaskDao extends ObjectDao implements ILoanTaskDao {

    /**
     * 统计用户放款信息
     * @param beginDate     任务开始日期
     * @param endDate       任务结束日期
     * @param userId        用户Id
     * @param groupId       团队Id
     * @return
     */
    public LoanUserTask getUserTaskAmountByCondtion(Date beginDate, Date endDate, Integer userId ,Integer groupId){
        Map<String,Object> codition = new HashMap<String, Object>();
        codition.put("beginDate",beginDate);
        codition.put("endDate",endDate);
        codition.put("userId",userId);
        codition.put("groupId",groupId);
        LoanUserTask loanUserTask = (LoanUserTask)this.queryObject("getUserTaskAmountByCondtion",codition);
        return loanUserTask;
    }

    /**
     * 统计用户放款信息
     * @param beginDate     任务开始日期
     * @param endDate       任务结束日期
     * @param groupId       团队Id
     * @return
     */
    public LoanUserTask getGroupTaskAmountByCondtion(Date beginDate, Date endDate,Integer groupId){
        Map<String,Object> codition = new HashMap<String, Object>();
        codition.put("beginDate",beginDate);
        codition.put("endDate",endDate);
        codition.put("groupId",groupId);
        LoanUserTask loanUserTask = (LoanUserTask)this.queryObject("getGroupTaskAmountByCondtion",codition);
        return loanUserTask;
    }

}
