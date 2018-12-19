package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.domain.system.SysModelConfigExtend;
import banger.domain.system.SysModelManagement;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * 模型列表业务访问接口
 */
public interface IModelManagementService {

	/**
	 * 新增模型列表
	 * 
	 * @param modelManagement
	 *            实体对像
	 * @param loginUserId
	 *            登入用户Id
	 * @param configValues
	 * @param configKeys
	 */
	void insertModelManagement(SysModelManagement modelManagement, Integer loginUserId, String[] configKeys,
			String[] configValues);

	/**
	 * 修改模型列表
	 * 
	 * @param modelManagement
	 *            实体对像
	 * @param loginUserId
	 *            登入用户Id
	 * @param configValues
	 * @param configIds
	 */
	void updateModelManagement(SysModelManagement modelManagement, Integer loginUserId, Integer[] configIds,
			String[] configValues);

	/**
	 * 通过主键删除模型列表
	 * @param MODEL_ID 主键Id
	 */
	void deleteModelManagementById(Integer modelId, Integer loginUser);

	/**
	 * 通过主键得到模型列表
	 * @param MODEL_ID 主键Id
	 */
	SysModelManagement getModelManagementById(Integer modelId);

	/**
	 * 查询模型列表
	 * @param condition 查询条件
	 * @return
	 */
	List<SysModelManagement> queryModelManagementList(Map<String,Object> condition);

	/**
	 * 分页查询模型列表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<SysModelManagement> queryModelManagementList(Map<String,Object> condition,IPageSize page);

	/**
	 * 查询最大排序号
	 * 
	 * @return
	 */
	Integer queryModelMaxOrderNum();

	/**
	 * 插入匹配项目
	 * @param loginUserId
	 * @param modelManagement
	 */
    void insertProjectNames(Integer loginUserId, SysModelManagement modelManagement);

	/**
	 * 根据projectName查询弹出页面
	 * @param projectName
	 * @return
	 */
	List<SysModelConfigExtend> queryShowPage(Integer projectId);
}
