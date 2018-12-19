package banger.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IMapConfigDao;
import banger.service.intf.IMapConfigService;
import banger.domain.track.MapConfig;

/**
 * 地图配置表业务访问类
 */
@Repository
public class MapConfigService implements IMapConfigService {

	@Autowired
	private IMapConfigDao configDao;


	public void saveConfig(MapConfig config,Integer loginUserId) {
		if (config.getId() == null)
			insertConfig(config,loginUserId);
		else
			updateConfig(config, loginUserId);
	}
	/**
	 * 新增地图配置表
	 * @param config 实体对像
	 * @param loginUserId 登入用户Id
	 */
	private void insertConfig(MapConfig config,Integer loginUserId){
		config.setCreateUser(loginUserId);
		config.setCreateDate(DateUtil.getCurrentDate());
		config.setUpdateUser(loginUserId);
		config.setUpdateDate(DateUtil.getCurrentDate());
		this.configDao.insertConfig(config);
	}

	/**
	 *修改地图配置表
	 * @param config 实体对像
	 * @param loginUserId 登入用户Id
	 */
	private void updateConfig(MapConfig config,Integer loginUserId){
		config.setUpdateUser(loginUserId);
		config.setUpdateDate(DateUtil.getCurrentDate());
		this.configDao.updateConfig(config);
	}

	/**
	 * 通过主键删除地图配置表
	 * @param ID 主键Id
	 */
	public void deleteConfigById(Integer id){
		this.configDao.deleteConfigById(id);
	}

	/**
	 * 通过主键得到地图配置表
	 * @param ID 主键Id
	 */
	public MapConfig getConfigById(Integer id){
		return this.configDao.getConfigById(id);
	}

	/**
	 * 查询地图配置表
	 * @param condition 查询条件
	 * @return
	 */
	public List<MapConfig> queryConfigList(Map<String,Object> condition){
		return this.configDao.queryConfigList(condition);
	}

	/**
	 * 分页查询地图配置表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<MapConfig> queryConfigList(Map<String,Object> condition,IPageSize page){
		return this.configDao.queryConfigList(condition,page);
	}

}
