package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.moduleIntf.IAppVersionProvider;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IAppVersionDao;
import banger.service.intf.IAppVersionService;
import banger.domain.system.SysAppVersion;

/**
 * APP升级版本信息表业务访问类
 */
@Repository
public class AppVersionService implements IAppVersionService,IAppVersionProvider {

	@Autowired
	private IAppVersionDao appVersionDao;

	/**
	 * 新增APP升级版本信息表
	 * @param appVersion 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertAppVersion(SysAppVersion appVersion,Integer loginUserId){
		appVersion.setCreateDate(DateUtil.getCurrentDate());
		this.appVersionDao.insertAppVersion(appVersion);
	}

	/**
	 *修改APP升级版本信息表
	 * @param appVersion 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateAppVersion(SysAppVersion appVersion,Integer loginUserId){
		this.appVersionDao.updateAppVersion(appVersion);
	}

	/**
	 * 通过主键删除APP升级版本信息表
	 * @param ID 主键Id
	 */
	public void deleteAppVersionById(Integer id){
		this.appVersionDao.deleteAppVersionById(id);
	}

	/**
	 * 通过主键得到APP升级版本信息表
	 * @param ID 主键Id
	 */
	public SysAppVersion getAppVersionById(Integer id){
		return this.appVersionDao.getAppVersionById(id);
	}

	/**
	 * 查询APP升级版本信息表
	 * @param condition 查询条件
	 * @return
	 */
	public List<SysAppVersion> queryAppVersionList(Map<String,Object> condition){
		return this.appVersionDao.queryAppVersionList(condition);
	}

	/**
	 * 分页查询APP升级版本信息表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<SysAppVersion> queryAppVersionList(Map<String,Object> condition,IPageSize page){
		return this.appVersionDao.queryAppVersionList(condition,page);
	}

	/**
	 * @Author: yangdw
	 * @param
	 * @Description:获取最新的版本
	 * @Date: 15:04 2017/9/30
	 */
	public SysAppVersion getLastOneVersionUpd() {
		return this.appVersionDao.getLastOneVersionUpd();
	}
}
