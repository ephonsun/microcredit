package banger.domain.task;

import java.math.BigDecimal;

/**
 * 统计营销任务的完成情况
 */
public class MarketingUserTask {
    private Integer userId;                     //用户ID
    private Integer groupId;                    //团队Id
    private Integer marketingTotal;             //营销总数

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMarketingTotal() {
        return marketingTotal;
    }

    public void setMarketingTotal(Integer marketingTotal) {
        this.marketingTotal = marketingTotal;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
