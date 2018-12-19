package banger.service.intf;

import banger.domain.app.AppHomePageCount;

import java.util.Date;


/**
 * Created by zhusw on 2017/6/21.
 */
public interface IAppHomePageService {

    /**
     * 得到APP首页，统计数量
     * @param userId
     * @param collectionDate
     * @return
     */
    AppHomePageCount getAppHomePageCount(Integer userId, Date collectionDate);

    /**
     * 得到APP首页，团队主管统计数量
     * @param userId
     * @param collectionDate
     * @return
     */
    AppHomePageCount getAppHomePageChargeConut(Integer userId, Integer[] roleIds, Date collectionDate);
}
