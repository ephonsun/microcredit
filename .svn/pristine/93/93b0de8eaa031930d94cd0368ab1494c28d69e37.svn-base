package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IMatchProjectDao;
import banger.domain.system.SysMatchProject;

/**
 * 匹配项目表数据访问类
 */
@Repository
public class MatchProjectDao extends PageSizeDao<SysMatchProject> implements IMatchProjectDao {

	/**
	 * 新增匹配项目表
	 * @param matchProject 实体对像
	 */
	public void insertMatchProject(SysMatchProject matchProject){
		matchProject.setProjectId(this.newId().intValue());
		this.execute("insertMatchProject",matchProject);
	}

	/**
	 *修改匹配项目表
	 * @param matchProject 实体对像
	 */
	public void updateMatchProject(SysMatchProject matchProject){
		this.execute("updateMatchProject",matchProject);
	}

	/**
	 * 通过主键删除匹配项目表
	 * @param projectId 主键Id
	 */
	public void deleteMatchProjectById(Integer projectId){
		this.execute("deleteMatchProjectById",projectId);
	}

	/**
	 * 通过主键得到匹配项目表
	 * @param projectId 主键Id
	 */
	public SysMatchProject getMatchProjectById(Integer projectId){
		return (SysMatchProject)this.queryEntity("getMatchProjectById",projectId);
	}

	/**
	 * 查询匹配项目表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysMatchProject> queryMatchProjectList(Map<String,Object> condition){
		return (List<SysMatchProject>)this.queryEntities("queryMatchProjectList", condition);
	}

	/**
	 * 分页查询匹配项目表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<SysMatchProject> queryMatchProjectList(Map<String,Object> condition,IPageSize page){
		return (IPageList<SysMatchProject>)this.queryEntities("queryMatchProjectList", page, condition);
	}

}
