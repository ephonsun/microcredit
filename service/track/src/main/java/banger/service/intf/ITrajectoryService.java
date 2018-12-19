package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.domain.app.AppScreenTrajectory;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.track.MapTrajectory;

/**
 * 地图经纬度座标轨迹业务访问接口
 */
public interface ITrajectoryService {

	/**
	 * 新增地图经纬度座标轨迹
	 * @param trajectory 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertTrajectory(MapTrajectory trajectory, Integer loginUserId);

	/**
	 *修改地图经纬度座标轨迹
	 * @param trajectory 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateTrajectory(MapTrajectory trajectory, Integer loginUserId);

	/**
	 * 通过主键删除地图经纬度座标轨迹
	 * @param TRA_ID 主键Id
	 */
	void deleteTrajectoryById(Integer traId);

	/**
	 * 通过主键得到地图经纬度座标轨迹
	 * @param TRA_ID 主键Id
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
	 * 捞出投屏的客户经理的经纬度信息
	 * @return
	 */
	List<AppScreenTrajectory> getMapInfo();
}
