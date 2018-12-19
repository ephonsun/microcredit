package banger.dao.intf;

import banger.domain.app.AppScreenTrajectory;
import banger.domain.track.MapTrajectory;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * 地图经纬度座标轨迹数据访问接口
 */
public interface ITrajectoryDao {

	/**
	 * 新增地图经纬度座标轨迹
	 * @param trajectory 实体对像
	 */
	void insertTrajectory(MapTrajectory trajectory);

	/**
	 *修改地图经纬度座标轨迹
	 * @param trajectory 实体对像
	 */
	void updateTrajectory(MapTrajectory trajectory);

	/**
	 * 通过主键删除地图经纬度座标轨迹
	 * @param traId 主键Id
	 */
	void deleteTrajectoryById(Integer traId);

	/**
	 * 通过主键得到地图经纬度座标轨迹
	 * @param traId 主键Id
	 */
	MapTrajectory getTrajectoryById(Integer traId);

	/**
	 * 查询地图经纬度座标轨迹
	 * @param condition 查询条件
	 * @return
	 */
	List<MapTrajectory> queryTrajectoryList(Map<String, Object> condition);

	/**
	 * 分页查询地图经纬度座标轨迹
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<MapTrajectory> queryTrajectoryList(Map<String, Object> condition, IPageSize page);

	/**
	 *
	 * @param trajectoryList
	 */
	void insertTrajectory(List<MapTrajectory> trajectoryList);


	/**
	 * 捞出投屏的客户经理的经纬度信息
	 * @return
	 */
	List<AppScreenTrajectory> getMapInfo();
}
