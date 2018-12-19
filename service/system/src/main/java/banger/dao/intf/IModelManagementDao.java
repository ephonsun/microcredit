package banger.dao.intf;

import banger.domain.system.SysModelConfigExtend;
import banger.domain.system.SysModelManagement;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * 模型列表数据访问接口
 */
public interface IModelManagementDao {

	/**
	 * 新增模型列表
	 * @param modelManagement 实体对像
	 */
	Integer insertModelManagement(SysModelManagement modelManagement);

	/**
	 *修改模型列表
	 * @param modelManagement 实体对像
	 */
	void updateModelManagement(SysModelManagement modelManagement);

	/**
	 * 通过主键删除模型列表
	 * @param modelId 主键Id
	 */
	void deleteModelManagementById(Integer modelId);

	/**
	 * 通过主键得到模型列表
	 * @param modelId 主键Id
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
	 * 根据projectName查询弹出页面
	 * @param projectName
	 * @return
	 */
    List<SysModelConfigExtend> queryShowPage(Integer projectId);
}
