package banger.dao.impl;

import banger.dao.intf.IModelManagementDao;
import banger.domain.system.SysModelConfigExtend;
import banger.domain.system.SysModelManagement;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模型列表数据访问类
 */
@Repository
public class ModelManagementDao extends PageSizeDao<SysModelManagement> implements IModelManagementDao {

	/**
	 * 新增模型列表
	 * @param modelManagement 实体对像
	 */
	public Integer insertModelManagement(SysModelManagement modelManagement) {
		Integer id = this.newId().intValue();
		modelManagement.setModelId(id);
		this.execute("insertModelManagement",modelManagement);
		return id;
	}

	/**
	 *修改模型列表
	 * @param modelManagement 实体对像
	 */
	public void updateModelManagement(SysModelManagement modelManagement){
		this.execute("updateModelManagement",modelManagement);
	}

	/**
	 * 通过主键删除模型列表
	 * @param modelId 主键Id
	 */
	public void deleteModelManagementById(Integer modelId){
		this.execute("deleteModelManagementById",modelId);
	}

	/**
	 * 通过主键得到模型列表
	 * @param modelId 主键Id
	 */
	public SysModelManagement getModelManagementById(Integer modelId){
		return (SysModelManagement)this.queryEntity("getModelManagementById",modelId);
	}

	/**
	 * 查询模型列表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysModelManagement> queryModelManagementList(Map<String,Object> condition){
		return (List<SysModelManagement>)this.queryEntities("queryModelManagementList", condition);
	}

	/**
	 * 分页查询模型列表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<SysModelManagement> queryModelManagementList(Map<String,Object> condition,IPageSize page){
		return (IPageList<SysModelManagement>)this.queryEntities("queryModelManagementList", page, condition);
	}

	/**
	 * 查询最大排序号
	 * 
	 * @return
	 */
	public Integer queryModelMaxOrderNum() {
		Map<String, Object> condition = new HashMap<String, Object>();
		return this.queryInt("queryModelMaxOrderNum", condition);
	}

	/**
	 * 根据projectName查询弹出页面
	 * @return
	 */
    public List<SysModelConfigExtend> queryShowPage(Integer projectId) {
    	Map<String,Object> condition = new HashMap<String, Object>();
    	condition.put("projectId",projectId);
    	return (List<SysModelConfigExtend>) this.queryEntities("queryShowPage",condition);
    }

}
