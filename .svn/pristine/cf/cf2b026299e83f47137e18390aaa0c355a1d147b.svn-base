package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IMapConfigDao;
import banger.domain.track.MapConfig;

/**
 * 地图配置表数据访问类
 */
@Repository
public class MapConfigDao extends PageSizeDao<MapConfig> implements IMapConfigDao {

	/**
	 * 新增地图配置表
	 * @param config 实体对像
	 */
	public void insertConfig(MapConfig config){
		config.setId(this.newId().intValue());
		this.execute("insertConfig",config);
	}

	/**
	 *修改地图配置表
	 * @param config 实体对像
	 */
	public void updateConfig(MapConfig config){
		this.execute("updateConfig",config);
	}

	/**
	 * 通过主键删除地图配置表
	 * @param id 主键Id
	 */
	public void deleteConfigById(Integer id){
		this.execute("deleteConfigById",id);
	}

	/**
	 * 通过主键得到地图配置表
	 * @param id 主键Id
	 */
	public MapConfig getConfigById(Integer id){
		return (MapConfig)this.queryEntity("getConfigById",id);
	}

	/**
	 * 查询地图配置表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MapConfig> queryConfigList(Map<String,Object> condition){
		return (List<MapConfig>)this.queryEntities("queryConfigList", condition);
	}

	/**
	 * 分页查询地图配置表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<MapConfig> queryConfigList(Map<String,Object> condition,IPageSize page){
		return (IPageList<MapConfig>)this.queryEntities("queryConfigList", page, condition);
	}

}
