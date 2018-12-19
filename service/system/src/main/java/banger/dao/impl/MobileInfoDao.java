package banger.dao.impl;

import banger.dao.intf.IMobileInfoDao;
import banger.domain.system.SysMobileInfo;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 数据访问类
 */
@Repository
public class MobileInfoDao extends PageSizeDao<SysMobileInfo> implements IMobileInfoDao {

	/**
	 * 新增
	 * @param mobileInfo 实体对像
	 */
	public void insertMobileInfo(SysMobileInfo mobileInfo){
		this.execute("insertMobileInfo",mobileInfo);
	}

	/**
	 *修改
	 * @param mobileInfo 实体对像
	 */
	public void updateMobileInfo(SysMobileInfo mobileInfo){
		this.execute("updateMobileInfo",mobileInfo);
	}

	/**
	 * 通过主键删除
	 * @param serialNo 主键Id
	 */
	public void deleteMobileInfoById(String serialNo){
		this.execute("deleteMobileInfoById",serialNo);
	}

	/**
	 * 通过主键得到
	 * @param serialNo 主键Id
	 */
	public SysMobileInfo getMobileInfoById(String serialNo){
		return (SysMobileInfo)this.queryEntity("getMobileInfoById",serialNo);
	}

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysMobileInfo> queryMobileInfoList(Map<String,Object> condition){
		return (List<SysMobileInfo>)this.queryEntities("queryMobileInfoList", condition);
	}

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<SysMobileInfo> queryMobileInfoList(Map<String,Object> condition,IPageSize page){
		return (IPageList<SysMobileInfo>)this.queryEntities("queryMobileInfoList", page, condition);
	}

}
