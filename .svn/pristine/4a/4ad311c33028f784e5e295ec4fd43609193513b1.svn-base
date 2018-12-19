package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IAppVersionDao;
import banger.domain.system.SysAppVersion;

/**
 * APP升级版本信息表数据访问类
 */
@Repository
public class AppVersionDao extends PageSizeDao<SysAppVersion> implements IAppVersionDao {

	/**
	 * 新增APP升级版本信息表
	 * @param appVersion 实体对像
	 */
	public void insertAppVersion(SysAppVersion appVersion){
		appVersion.setId(this.newId().intValue());
		this.execute("insertAppVersion",appVersion);
	}

	/**
	 *修改APP升级版本信息表
	 * @param appVersion 实体对像
	 */
	public void updateAppVersion(SysAppVersion appVersion){
		this.execute("updateAppVersion",appVersion);
	}

	/**
	 * 通过主键删除APP升级版本信息表
	 * @param id 主键Id
	 */
	public void deleteAppVersionById(Integer id){
		this.execute("deleteAppVersionById",id);
	}

	/**
	 * 通过主键得到APP升级版本信息表
	 * @param id 主键Id
	 */
	public SysAppVersion getAppVersionById(Integer id){
		return (SysAppVersion)this.queryEntity("getAppVersionById",id);
	}

	/**
	 * 查询APP升级版本信息表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysAppVersion> queryAppVersionList(Map<String,Object> condition){
		return (List<SysAppVersion>)this.queryEntities("queryAppVersionList", condition);
	}

	/**
	 * 分页查询APP升级版本信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<SysAppVersion> queryAppVersionList(Map<String,Object> condition,IPageSize page){
		return (IPageList<SysAppVersion>)this.queryEntities("queryAppVersionList", page, condition);
	}

	/**
	 * @Author: yangdw
	 * @param
	 * @Description:获取最新的版本
	 * @Date: 15:04 2017/9/30
	 */
	public SysAppVersion getLastOneVersionUpd() {
		return (SysAppVersion)this.queryEntity("getLastOneVersionUpd");
	}
}
