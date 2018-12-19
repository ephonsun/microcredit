package banger.service.impl;

import banger.dao.intf.IMatchProjectDao;
import banger.dao.intf.IModelConfigDao;
import banger.dao.intf.IModelManagementDao;
import banger.domain.system.SysMatchProject;
import banger.domain.system.SysModelConfig;
import banger.domain.system.SysModelConfigExtend;
import banger.domain.system.SysModelManagement;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.service.intf.IModelManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模型列表业务访问类
 */
@Repository
public class ModelManagementService implements IModelManagementService {

	@Autowired
	private IModelManagementDao modelManagementDao;
	@Autowired
	private IModelConfigDao modelConfigDao;
	@Autowired
	private IMatchProjectDao matchProjectDao;
	/**
	 * 新增模型列表
	 * @param modelManagement 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertModelManagement(SysModelManagement modelManagement, Integer loginUserId, String[] configKes,
			String[] configValues) {
		// 保存模型
		modelManagement.setCreateUser(loginUserId);
		modelManagement.setCreateDate(DateUtil.getCurrentDate());
		modelManagement.setUpdateUser(loginUserId);
		modelManagement.setUpdateDate(DateUtil.getCurrentDate());
		// 修改Dao方法使之返回主键
		Integer modelId = this.modelManagementDao.insertModelManagement(modelManagement);
		// 保存模型配置
		String modelName = modelManagement.getModelName();

		for (int i = 0; i < configKes.length; i++) {
			SysModelConfig modelConfig = new SysModelConfig();
			String configKey = configKes[i];
			String configValue = configValues[i];
			modelConfig.setConfigKey(configKey);
			modelConfig.setConfigValue(configValue);
			modelConfig.setModelId(modelId);
			modelConfig.setModelName(modelName);

			modelConfig.setCreateUser(loginUserId);
			modelConfig.setCreateDate(DateUtil.getCurrentDate());
			modelConfig.setUpdateUser(loginUserId);
			modelConfig.setUpdateDate(DateUtil.getCurrentDate());

			modelConfigDao.insertModelConfig(modelConfig);
		}

	}

	/**
	 * 修改模型列表和模型配置
	 * 
	 * @param modelManagement
	 *            实体对像
	 * @param loginUserId
	 *            登入用户Id
	 */
	public void updateModelManagement(SysModelManagement modelManagement, Integer loginUserId, Integer[] configIds,
			String[] configValues) {
		// 更新模型
		modelManagement.setUpdateUser(loginUserId);
		modelManagement.setUpdateDate(DateUtil.getCurrentDate());
		this.modelManagementDao.updateModelManagement(modelManagement);

		// 更新模型配置
		for (int i = 0; i < configIds.length; i++) {
			Integer configId = configIds[i];
			SysModelConfig modelConfig = this.modelConfigDao.getModelConfigById(configId);
			modelConfig.setConfigValue(configValues[i]);
			modelConfig.setModelName(modelManagement.getModelName());

			modelConfig.setUpdateUser(loginUserId);
			modelConfig.setUpdateDate(DateUtil.getCurrentDate());

			this.modelConfigDao.updateModelConfig(modelConfig);
		}
		//更新模型匹配
		Map<String,Object> condition = new HashMap<String, Object>();
        condition.put("modelId",modelManagement.getModelId());
        for (SysMatchProject sysMatchProject : this.matchProjectDao.queryMatchProjectList(condition)) {
            sysMatchProject.setModelName(modelManagement.getModelName());
            this.matchProjectDao.updateMatchProject(sysMatchProject);
        }

    }

	/**
	 * 通过主键删除模型列表
	 * @param modelId 主键Id
	 */
	public void deleteModelManagementById(Integer modelId,Integer loginUser){
		//删除模型
		this.modelManagementDao.deleteModelManagementById(modelId);

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("modelId", modelId);
		List<SysModelConfig> configList = this.modelConfigDao.queryModelConfigList(condition);
		//删除模型配置
		for (SysModelConfig sysModelConfig : configList) {
			this.modelConfigDao.deleteModelConfigById(sysModelConfig.getConfigId());
		}
		//置空模型匹配中的字段和id
		condition.clear();
		condition.put("modelId",modelId);
		List<SysMatchProject> matchProjectList = this.matchProjectDao.queryMatchProjectList(condition);
		for (SysMatchProject sysMatchProject : matchProjectList) {
			//置空
			sysMatchProject.setModelName("");
			//sql配置中断言存在，不能设置为null，设置为-1
			sysMatchProject.setModelId(-1);
			sysMatchProject.setUpdateUser(loginUser);
			sysMatchProject.setUpdateDate(DateUtil.getCurrentDate());
			this.matchProjectDao.updateMatchProject(sysMatchProject);
		}
	}

	/**
	 * 通过主键得到模型列表
	 * @param modelId 主键Id
	 */
	public SysModelManagement getModelManagementById(Integer modelId){
		return this.modelManagementDao.getModelManagementById(modelId);
	}

	/**
	 * 查询模型列表
	 * @param condition 查询条件
	 * @return
	 */
	public List<SysModelManagement> queryModelManagementList(Map<String,Object> condition){
		return this.modelManagementDao.queryModelManagementList(condition);
	}

	/**
	 * 分页查询模型列表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<SysModelManagement> queryModelManagementList(Map<String,Object> condition,IPageSize page){
		return this.modelManagementDao.queryModelManagementList(condition,page);
	}

	/**
	 * 查询最大排序号
	 * 
	 * @return
	 */
	public Integer queryModelMaxOrderNum() {
		return this.modelManagementDao.queryModelMaxOrderNum();
	}

	/**
	 * 插入匹配项目
	 * @param loginUserId
	 * @param modelManagement
	 */
    public void insertProjectNames(Integer loginUserId, SysModelManagement modelManagement) {
		modelManagement.setUpdateUser(loginUserId);
		modelManagement.setUpdateDate(DateUtil.getCurrentDate());
		this.modelManagementDao.updateModelManagement(modelManagement);
    }

    /**
     * 根据projectName查询弹出页面
     * @param projectName
     * @return
     */
    public List<SysModelConfigExtend> queryShowPage(Integer projectId) {
        return this.modelManagementDao.queryShowPage(projectId);
    }

}
