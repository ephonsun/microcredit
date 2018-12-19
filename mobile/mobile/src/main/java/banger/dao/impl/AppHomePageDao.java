package banger.dao.impl;

import banger.dao.intf.IAppHomePageDao;
import banger.domain.app.AppHomePageCount;
import banger.domain.permission.PmsRole;
import banger.framework.dao.ObjectDao;
import banger.framework.util.DateUtil;
import banger.framework.util.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/21.
 */
@Repository
public class AppHomePageDao extends ObjectDao implements IAppHomePageDao {

    /**
     * 得到APP首页，客户经理统计数量
     * @param userId 用户ID
     * @param today 今天
     * @param monthFirstDay 本月的第一天
     * @return
     */
    public AppHomePageCount getAppHomePageCount(Integer userId,Date today,Date monthFirstDay,Date collectionDate){
        Map<String,Object> condition = new HashMap<String, Object>();
        Date nowDate = DateUtil.getNeedTime(0,0,0,0);
        condition.put("userId",userId);
        condition.put("today",today);
        condition.put("monthFirstDay",monthFirstDay);
        condition.put("collectionDate",collectionDate);
        condition.put("nowDate",nowDate);
        return (AppHomePageCount)this.queryObject("getAppHomePageCount",condition);
    }

    /**
     * 得到APP首页，团队主管统计数量
     * @param userId
     * @param today
     * @param monthFirstDay
     * @return
     */
    @Override
    public AppHomePageCount getAppHomePageChargeConut(Integer userId, Date today, Date monthFirstDay,Integer[] roleIds,Date collectionDate) {
        Map<String,Object> condition = new HashMap<String, Object>();
        Date nowDate = DateUtil.getNeedTime(0,0,0,0);
        condition.put("userId",userId);
        condition.put("today",today);
        condition.put("monthFirstDay",monthFirstDay);
        condition.put("roleIds",roleIds);
        condition.put("collectionDate",collectionDate);
        condition.put("nowDate",nowDate);
        return (AppHomePageCount) this.queryObject("getAppHomePageChargeCount",condition);
    }

}
