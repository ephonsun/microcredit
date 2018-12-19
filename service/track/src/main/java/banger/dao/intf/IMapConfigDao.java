package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.track.MapConfig;

/**
 * 地图配置表数据访问接口
 */
public interface IMapConfigDao {

	/**
	 * 新增地图配置表
	 * @param config 实体对像
	 */
	void insertConfig(MapConfig config);

	/**
	 *修改地图配置表
	 * @param config 实体对像
	 */
	void updateConfig(MapConfig config);

	/**
	 * 通过主键删除地图配置表
	 * @param id 主键Id
	 */
	void deleteConfigById(Integer id);

	/**
	 * 通过主键得到地图配置表
	 * @param id 主键Id
	 */
	MapConfig getConfigById(Integer id);

	/**
	 * 查询地图配置表
	 * @param condition 查询条件
	 * @return
	 */
	List<MapConfig> queryConfigList(Map<String, Object> condition);

	/**
	 * 分页查询地图配置表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<MapConfig> queryConfigList(Map<String, Object> condition, IPageSize page);

}
