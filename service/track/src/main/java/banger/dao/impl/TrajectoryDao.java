package banger.dao.impl;

import banger.dao.intf.ITrajectoryDao;
import banger.domain.app.AppScreenTrajectory;
import banger.domain.track.MapTrajectory;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 地图经纬度座标轨迹数据访问类
 */
@Repository
public class TrajectoryDao extends PageSizeDao<MapTrajectory> implements ITrajectoryDao {

	/**
	 * 新增地图经纬度座标轨迹
	 * @param trajectory 实体对像
	 */
	public void insertTrajectory(MapTrajectory trajectory){
		trajectory.setTraId(this.newId().intValue());
		this.execute("insertTrajectory",trajectory);
	}

	/**
	 *修改地图经纬度座标轨迹
	 * @param trajectory 实体对像
	 */
	public void updateTrajectory(MapTrajectory trajectory){
		this.execute("updateTrajectory",trajectory);
	}

	/**
	 * 通过主键删除地图经纬度座标轨迹
	 * @param traId 主键Id
	 */
	public void deleteTrajectoryById(Integer traId){
		this.execute("deleteTrajectoryById",traId);
	}

	/**
	 * 通过主键得到地图经纬度座标轨迹
	 * @param traId 主键Id
	 */
	public MapTrajectory getTrajectoryById(Integer traId){
		return (MapTrajectory)this.queryEntity("getTrajectoryById",traId);
	}

	/**
	 * 查询地图经纬度座标轨迹
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MapTrajectory> queryTrajectoryList(Map<String,Object> condition){
		return (List<MapTrajectory>)this.queryEntities("queryTrajectoryList", condition);
	}

	/**
	 * 分页查询地图经纬度座标轨迹
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<MapTrajectory> queryTrajectoryList(Map<String,Object> condition,IPageSize page){
		return (IPageList<MapTrajectory>)this.queryEntities("queryTrajectoryList", page, condition);
	}

	/**
	 *
	 * @param trajectoryList
	 */
	public void insertTrajectory(List<MapTrajectory> trajectoryList){
		if(trajectoryList!=null && trajectoryList.size()>0) {
			Long[] ids = this.newId(trajectoryList.size());
			for(int i=0;i<ids.length;i++)
				trajectoryList.get(i).setTraId(ids[i].intValue());
			this.executes("insertTrajectory", trajectoryList);
		}
	}

	/**3
	 * 地图
	 *
	 */
	@Override
	public List<AppScreenTrajectory> getMapInfo() {
		String nowDate = DateUtil.format(new Date(), DateUtil.DEFAULT_DATE_FORMAT);
		return  (List<AppScreenTrajectory>)this.queryEntities("getMapInfo", nowDate);
	}
}
