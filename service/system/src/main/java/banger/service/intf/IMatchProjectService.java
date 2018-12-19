package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.system.SysMatchProject;

/**
 * 匹配项目表业务访问接口
 */
public interface IMatchProjectService {

	/**
	 * 新增匹配项目表
	 * @param matchProject 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertMatchProject(SysMatchProject matchProject,Integer loginUserId);

	/**
	 *修改匹配项目表
	 * @param matchProject 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateMatchProject(SysMatchProject matchProject,Integer loginUserId);

	/**
	 * 通过主键删除匹配项目表
	 * @param PROJECT_ID 主键Id
	 */
	void deleteMatchProjectById(Integer projectId);

	/**
	 * 通过主键得到匹配项目表
	 * @param PROJECT_ID 主键Id
	 */
	SysMatchProject getMatchProjectById(Integer projectId);

	/**
	 * 查询匹配项目表
	 * @param condition 查询条件
	 * @return
	 */
	List<SysMatchProject> queryMatchProjectList(Map<String,Object> condition);

	/**
	 * 分页查询匹配项目表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<SysMatchProject> queryMatchProjectList(Map<String,Object> condition,IPageSize page);

}
