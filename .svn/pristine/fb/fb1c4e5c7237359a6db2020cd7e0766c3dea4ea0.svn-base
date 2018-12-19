package banger.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IModelConfigDao;
import banger.service.intf.IModelConfigService;
import banger.domain.system.SysModelConfig;

/**
 * 模型配置业务访问类
 */
@Repository
public class ModelConfigService implements IModelConfigService {

	@Autowired
	private IModelConfigDao modelConfigDao;

	/**
	 * 新增模型配置
	 * @param modelConfig 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertModelConfig(SysModelConfig modelConfig,Integer loginUserId){
		modelConfig.setCreateUser(loginUserId);
		modelConfig.setCreateDate(DateUtil.getCurrentDate());
		modelConfig.setUpdateUser(loginUserId);
		modelConfig.setUpdateDate(DateUtil.getCurrentDate());
		this.modelConfigDao.insertModelConfig(modelConfig);
	}

	/**
	 *修改模型配置
	 * @param modelConfig 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateModelConfig(SysModelConfig modelConfig,Integer loginUserId){
		modelConfig.setUpdateUser(loginUserId);
		modelConfig.setUpdateDate(DateUtil.getCurrentDate());
		this.modelConfigDao.updateModelConfig(modelConfig);
	}

	/**
	 * 通过主键删除模型配置
	 * @param CONFIG_ID 主键Id
	 */
	public void deleteModelConfigById(Integer configId){
		this.modelConfigDao.deleteModelConfigById(configId);
	}

	/**
	 * 通过主键得到模型配置
	 * @param CONFIG_ID 主键Id
	 */
	public SysModelConfig getModelConfigById(Integer configId){
		return this.modelConfigDao.getModelConfigById(configId);
	}

	/**
	 * 查询模型配置
	 * @param condition 查询条件
	 * @return
	 */
	public List<SysModelConfig> queryModelConfigList(Map<String,Object> condition){
		return this.modelConfigDao.queryModelConfigList(condition);
	}

	/**
	 * 分页查询模型配置
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<SysModelConfig> queryModelConfigList(Map<String,Object> condition,IPageSize page){
		return this.modelConfigDao.queryModelConfigList(condition,page);
	}

}
