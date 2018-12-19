package banger.dao.impl;

import banger.dao.intf.IMarketingTaskDao;
import banger.domain.task.LoanUserTask;
import banger.domain.task.MarketingUserTask;
import banger.framework.dao.ObjectDao;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhusw on 2017/7/18.
 */
@Repository
public class MarketingTaskDao extends ObjectDao implements IMarketingTaskDao {

    /**
     * 统计用户放款信息
     * @param beginDate     任务开始日期
     * @param endDate       任务结束日期
     * @param userId        用户Id
     * @param groupId       团队Id
     * @return
     */
    public MarketingUserTask getUserTaskAmountByCondtion(Date beginDate, Date endDate, Integer userId , Integer groupId){
        Map<String,Object> codition = new HashMap<String, Object>();
        codition.put("beginDate",beginDate);
        codition.put("endDate",endDate);
        codition.put("userId",userId);
        codition.put("groupId",groupId);
        MarketingUserTask marketingUserTask = (MarketingUserTask)this.queryObject("getMarketingUserTaskAmountByCondtion",codition);
        return marketingUserTask;
    }

    /**
     * 统计用户放款信息
     * @param beginDate     任务开始日期
     * @param endDate       任务结束日期
     * @param groupId       团队Id
     * @return
     */
    public MarketingUserTask getGroupTaskAmountByCondtion(Date beginDate, Date endDate,Integer groupId){
        Map<String,Object> codition = new HashMap<String, Object>();
        codition.put("beginDate",beginDate);
        codition.put("endDate",endDate);
        codition.put("groupId",groupId);
        MarketingUserTask loanUserTask = (MarketingUserTask)this.queryObject("getMarketingGroupTaskAmountByCondtion",codition);
        return loanUserTask;
    }

}
