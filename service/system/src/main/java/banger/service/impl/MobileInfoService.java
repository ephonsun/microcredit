package banger.service.impl;

import banger.dao.intf.IMobileInfoDao;
import banger.domain.system.SysMobileInfo;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.service.intf.IMobileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 业务访问类
 */
@Repository
public class MobileInfoService implements IMobileInfoService {

	@Autowired
	private IMobileInfoDao mobileInfoDao;

	/**
	 * 新增
	 * @param mobileInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertMobileInfo(SysMobileInfo mobileInfo,Integer loginUserId){
		mobileInfo.setCreateUser(loginUserId);
		mobileInfo.setCreateDate(DateUtil.getCurrentDate());
		mobileInfo.setUpdateUser(loginUserId);
		mobileInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.mobileInfoDao.insertMobileInfo(mobileInfo);
	}

	/**
	 *修改
	 * @param mobileInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateMobileInfo(SysMobileInfo mobileInfo,Integer loginUserId){
		mobileInfo.setUpdateUser(loginUserId);
		mobileInfo.setUpdateDate(DateUtil.getCurrentDate());
		this.mobileInfoDao.updateMobileInfo(mobileInfo);
	}

	/**
	 * 通过主键删除
	 * @param SERIAL_NO 主键Id
	 */
	public void deleteMobileInfoById(String serialNo){
		this.mobileInfoDao.deleteMobileInfoById(serialNo);
	}

	/**
	 * 通过主键得到
	 * @param SERIAL_NO 主键Id
	 */
	public SysMobileInfo getMobileInfoById(String serialNo){
		return this.mobileInfoDao.getMobileInfoById(serialNo);
	}

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	public List<SysMobileInfo> queryMobileInfoList(Map<String,Object> condition){
		return this.mobileInfoDao.queryMobileInfoList(condition);
	}

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<SysMobileInfo> queryMobileInfoList(Map<String,Object> condition,IPageSize page){
		return this.mobileInfoDao.queryMobileInfoList(condition,page);
	}

}
