package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.domain.app.AppScreenTrajectory;
import banger.moduleIntf.IMapTrajectoryProvider;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.ITrajectoryDao;
import banger.service.intf.ITrajectoryService;
import banger.domain.track.MapTrajectory;

/**
 * 地图经纬度座标轨迹业务访问类
 */
@Repository
public class TrajectoryService implements ITrajectoryService,IMapTrajectoryProvider {

	@Autowired
	private ITrajectoryDao trajectoryDao;

	/**
	 * 新增地图经纬度座标轨迹
	 * @param trajectory 实体对像
	 * @param loginUserId 登入用户Id
	 */
	@Override
	public void insertTrajectory(MapTrajectory trajectory, Integer loginUserId){
		trajectory.setCreateUser(loginUserId);
		trajectory.setCreateDate(DateUtil.getCurrentDate());
		this.trajectoryDao.insertTrajectory(trajectory);
	}

	/**
	 *修改地图经纬度座标轨迹
	 * @param trajectory 实体对像
	 * @param loginUserId 登入用户Id
	 */
	@Override
	public void updateTrajectory(MapTrajectory trajectory, Integer loginUserId){
		this.trajectoryDao.updateTrajectory(trajectory);
	}

	/**
	 * 通过主键删除地图经纬度座标轨迹
	 * @param traId 主键Id
	 */
	@Override
	public void deleteTrajectoryById(Integer traId){
		this.trajectoryDao.deleteTrajectoryById(traId);
	}

	/**
	 * 通过主键得到地图经纬度座标轨迹
	 * @param traId 主键Id
	 */
	@Override
	public MapTrajectory getTrajectoryById(Integer traId){
		return this.trajectoryDao.getTrajectoryById(traId);
	}

	/**
	 * 查询地图经纬度座标轨迹
	 * @param condition 查询条件
	 * @return
	 */
	@Override
	public List<MapTrajectory> queryTrajectoryList(Map<String,Object> condition){
		return this.trajectoryDao.queryTrajectoryList(condition);
	}

	/**
	 * 分页查询地图经纬度座标轨迹
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@Override
	public IPageList<MapTrajectory> queryTrajectoryList(Map<String,Object> condition, IPageSize page){
		return this.trajectoryDao.queryTrajectoryList(condition,page);
	}

	/**
	 * 新增地图经纬度座标轨迹
	 * @param trajectoryList
	 */
	@Override
	public void insertTrajectory(List<MapTrajectory> trajectoryList){
		this.trajectoryDao.insertTrajectory(trajectoryList);
	}

	/**3
	 * 地图
	 *
	 */
	@Override
	public List<AppScreenTrajectory> getMapInfo() {
		return this.trajectoryDao.getMapInfo();
	}

}
