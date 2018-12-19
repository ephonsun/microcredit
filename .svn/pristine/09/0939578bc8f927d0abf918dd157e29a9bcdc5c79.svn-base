package banger.service.impl;

import banger.dao.intf.IAppHomePageDao;
import banger.domain.app.AppHomePageCount;
import banger.framework.util.DateUtil;
import banger.service.intf.IAppHomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 首页统计
 * Created by zhusw on 2017/6/21.
 */
@Repository
public class AppHomePageService implements IAppHomePageService {

    @Autowired
    private IAppHomePageDao appHomePageDao;

    /**
     * 得到APP首页，客户经理统计数量
     * @param userId
     * @param collectionDate
     * @return
     */
    public AppHomePageCount getAppHomePageCount(Integer userId, Date collectionDate){
        Date today = DateUtil.clearTime(new Date());
        int year = DateUtil.getYear(today);
        int month = DateUtil.getMonth(today);
        Date monthFirstDay = DateUtil.getDate(year,month,1);
        return appHomePageDao.getAppHomePageCount(userId,today,monthFirstDay,collectionDate);
    }

    /**
     * 团队主管/审计
     * @param userId
     * @param collectionDate
     * @return
     */
    @Override
    public AppHomePageCount getAppHomePageChargeConut(Integer userId, Integer[] roleIds, Date collectionDate) {
        Date today = DateUtil.clearTime(new Date());
        int year = DateUtil.getYear(today);
        int month = DateUtil.getMonth(today);
        Date monthFirstDay = DateUtil.getDate(year,month,1);
        return appHomePageDao.getAppHomePageChargeConut(userId,today,monthFirstDay,roleIds,collectionDate);
    }
}
