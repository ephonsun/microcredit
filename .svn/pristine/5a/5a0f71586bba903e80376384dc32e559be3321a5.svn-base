package banger.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IMatchProjectDao;
import banger.service.intf.IMatchProjectService;
import banger.domain.system.SysMatchProject;

/**
 * 匹配项目表业务访问类
 */
@Repository
public class MatchProjectService implements IMatchProjectService {

	@Autowired
	private IMatchProjectDao matchProjectDao;

	/**
	 * 新增匹配项目表
	 * @param matchProject 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertMatchProject(SysMatchProject matchProject,Integer loginUserId){
		matchProject.setCreateUser(loginUserId);
		matchProject.setCreateDate(DateUtil.getCurrentDate());
		matchProject.setUpdateUser(loginUserId);
		matchProject.setUpdateDate(DateUtil.getCurrentDate());
		this.matchProjectDao.insertMatchProject(matchProject);
	}

	/**
	 *修改匹配项目表
	 * @param matchProject 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateMatchProject(SysMatchProject matchProject,Integer loginUserId){
		matchProject.setUpdateUser(loginUserId);
		matchProject.setUpdateDate(DateUtil.getCurrentDate());
		this.matchProjectDao.updateMatchProject(matchProject);
	}

	/**
	 * 通过主键删除匹配项目表
	 * @param PROJECT_ID 主键Id
	 */
	public void deleteMatchProjectById(Integer projectId){
		this.matchProjectDao.deleteMatchProjectById(projectId);
	}

	/**
	 * 通过主键得到匹配项目表
	 * @param PROJECT_ID 主键Id
	 */
	public SysMatchProject getMatchProjectById(Integer projectId){
		return this.matchProjectDao.getMatchProjectById(projectId);
	}

	/**
	 * 查询匹配项目表
	 * @param condition 查询条件
	 * @return
	 */
	public List<SysMatchProject> queryMatchProjectList(Map<String,Object> condition){
		return this.matchProjectDao.queryMatchProjectList(condition);
	}

	/**
	 * 分页查询匹配项目表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<SysMatchProject> queryMatchProjectList(Map<String,Object> condition,IPageSize page){
		return this.matchProjectDao.queryMatchProjectList(condition,page);
	}

}
