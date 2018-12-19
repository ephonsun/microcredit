package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.track.MapConfig;

/**
 * 地图配置表业务访问接口
 */
public interface IMapConfigService {

	/**
	 * 保存地图配置表
	 * @param config 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void saveConfig(MapConfig config, Integer loginUserId);


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
