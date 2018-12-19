package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IModelConfigDao;
import banger.domain.system.SysModelConfig;

/**
 * 模型配置数据访问类
 */
@Repository
public class ModelConfigDao extends PageSizeDao<SysModelConfig> implements IModelConfigDao {

	/**
	 * 新增模型配置
	 * @param modelConfig 实体对像
	 */
	public void insertModelConfig(SysModelConfig modelConfig){
		modelConfig.setConfigId(this.newId().intValue());
		this.execute("insertModelConfig",modelConfig);
	}

	/**
	 *修改模型配置
	 * @param modelConfig 实体对像
	 */
	public void updateModelConfig(SysModelConfig modelConfig){
		this.execute("updateModelConfig",modelConfig);
	}

	/**
	 * 通过主键删除模型配置
	 * @param configId 主键Id
	 */
	public void deleteModelConfigById(Integer configId){
		this.execute("deleteModelConfigById",configId);
	}

	/**
	 * 通过主键得到模型配置
	 * @param configId 主键Id
	 */
	public SysModelConfig getModelConfigById(Integer configId){
		return (SysModelConfig)this.queryEntity("getModelConfigById",configId);
	}

	/**
	 * 查询模型配置
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysModelConfig> queryModelConfigList(Map<String,Object> condition){
		return (List<SysModelConfig>)this.queryEntities("queryModelConfigList", condition);
	}

	/**
	 * 分页查询模型配置
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<SysModelConfig> queryModelConfigList(Map<String,Object> condition,IPageSize page){
		return (IPageList<SysModelConfig>)this.queryEntities("queryModelConfigList", page, condition);
	}

}
