package banger.moduleIntf;

import banger.domain.app.AppScreenTrajectory;
import banger.domain.track.MapTrajectory;

import java.util.List;

/**
 * 地图轨迹
 * Created by zhusw on 2017/8/2.
 */
public interface IMapTrajectoryProvider {

    /**
     * 新增地图经纬度座标轨迹
     * @param trajectoryList
     */
    void insertTrajectory(List<MapTrajectory> trajectoryList);


    /**
     * 捞出投屏的客户经理的经纬度信息
     * @return
     */
    List<AppScreenTrajectory> getMapInfo();
}
