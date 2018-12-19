package banger.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IMobileRecordDao;
import banger.service.intf.IMobileRecordService;
import banger.domain.system.SysMobileRecord;

/**
 * 系统登录记录业务访问类
 */
@Repository
public class MobileRecordService implements IMobileRecordService {

	@Autowired
	private IMobileRecordDao mobileRecordDao;

	/**
	 * 新增系统登录记录
	 * @param mobileRecord 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertMobileRecord(SysMobileRecord mobileRecord,Integer loginUserId){
		this.mobileRecordDao.insertMobileRecord(mobileRecord);
	}

	/**
	 *修改系统登录记录
	 * @param mobileRecord 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateMobileRecord(SysMobileRecord mobileRecord,Integer loginUserId){
		this.mobileRecordDao.updateMobileRecord(mobileRecord);
	}

	/**
	 * 通过主键删除系统登录记录
	 * @param ID 主键Id
	 */
	public void deleteMobileRecordById(Integer id){
		this.mobileRecordDao.deleteMobileRecordById(id);
	}

	/**
	 * 通过主键得到系统登录记录
	 * @param ID 主键Id
	 */
	public SysMobileRecord getMobileRecordById(Integer id){
		return this.mobileRecordDao.getMobileRecordById(id);
	}


	/**
	 * 查询系统登录记录
	 * @param condition 查询条件
	 * @return
	 */
	public List<SysMobileRecord> queryMobileRecordList(Map<String,Object> condition){
		return this.mobileRecordDao.queryMobileRecordList(condition);
	}

	/**
	 * 分页查询系统登录记录
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<SysMobileRecord> queryMobileRecordList(Map<String,Object> condition,IPageSize page){
		return this.mobileRecordDao.queryMobileRecordList(condition,page);
	}

}
