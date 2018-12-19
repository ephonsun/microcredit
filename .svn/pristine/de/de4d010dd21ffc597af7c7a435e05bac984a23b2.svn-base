package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IMobileRecordDao;
import banger.domain.system.SysMobileRecord;

/**
 * 系统登录记录数据访问类
 */
@Repository
public class MobileRecordDao extends PageSizeDao<SysMobileRecord> implements IMobileRecordDao {

	/**
	 * 新增系统登录记录
	 * @param mobileRecord 实体对像
	 */
	public void insertMobileRecord(SysMobileRecord mobileRecord){
		mobileRecord.setId(this.newId().intValue());
		this.execute("insertMobileRecord",mobileRecord);
	}

	/**
	 *修改系统登录记录
	 * @param mobileRecord 实体对像
	 */
	public void updateMobileRecord(SysMobileRecord mobileRecord){
		this.execute("updateMobileRecord",mobileRecord);
	}

	/**
	 * 通过主键删除系统登录记录
	 * @param id 主键Id
	 */
	public void deleteMobileRecordById(Integer id){
		this.execute("deleteMobileRecordById",id);
	}

	/**
	 * 通过主键得到系统登录记录
	 * @param id 主键Id
	 */
	public SysMobileRecord getMobileRecordById(Integer id){
		return (SysMobileRecord)this.queryEntity("getMobileRecordById",id);
	}

	/**
	 * 查询系统登录记录
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysMobileRecord> queryMobileRecordList(Map<String,Object> condition){
		return (List<SysMobileRecord>)this.queryEntities("queryMobileRecordList", condition);
	}

	/**
	 * 分页查询系统登录记录
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<SysMobileRecord> queryMobileRecordList(Map<String,Object> condition,IPageSize page){
		return (IPageList<SysMobileRecord>)this.queryEntities("queryMobileRecordList", page, condition);
	}

}
