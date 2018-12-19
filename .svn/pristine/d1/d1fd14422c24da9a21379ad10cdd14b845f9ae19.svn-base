package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.system.SysModelConfig;

/**
 * 模型配置数据访问接口
 */
public interface IModelConfigDao {

	/**
	 * 新增模型配置
	 * @param modelConfig 实体对像
	 */
	void insertModelConfig(SysModelConfig modelConfig);

	/**
	 *修改模型配置
	 * @param modelConfig 实体对像
	 */
	void updateModelConfig(SysModelConfig modelConfig);

	/**
	 * 通过主键删除模型配置
	 * @param configId 主键Id
	 */
	void deleteModelConfigById(Integer configId);

	/**
	 * 通过主键得到模型配置
	 * @param configId 主键Id
	 */
	SysModelConfig getModelConfigById(Integer configId);

	/**
	 * 查询模型配置
	 * @param condition 查询条件
	 * @return
	 */
	List<SysModelConfig> queryModelConfigList(Map<String,Object> condition);

	/**
	 * 分页查询模型配置
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<SysModelConfig> queryModelConfigList(Map<String,Object> condition,IPageSize page);

}
