package banger.dao.intf;

import banger.domain.app.AppHomePageCount;
import banger.domain.permission.PmsRole;

import java.util.Date;
import java.util.List;

/**
 * Created by zhusw on 2017/6/21.
 */
public interface IAppHomePageDao {

    /**
     * 得到APP首页，客户经理统计数量
     * @param userId 用户ID
     * @param today 今天
     * @param monthFirstDay 本月的第一天
     * @return
     */
    AppHomePageCount getAppHomePageCount(Integer userId,Date today,Date monthFirstDay,Date collectionDate);

    /**
     * 得到APP首页，团队主管统计数量
     * @param userId
     * @param today
     * @param monthFirstDay
     * @return
     */
    AppHomePageCount getAppHomePageChargeConut(Integer userId,Date today,Date monthFirstDay,Integer[] roleIds,Date collectionDate);

}
