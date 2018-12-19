package banger.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import banger.dao.intf.IDataPermitDao;
import banger.domain.permission.PmsDataPermit;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * 数据访问类
 */
@Repository
public class DataPermitDao extends PageSizeDao<PmsDataPermit> implements IDataPermitDao {

	/**
	 * 新增
	 * @param dataPermit 实体对像
	 */
	public void insertDataPermit(PmsDataPermit dataPermit){
		dataPermit.setPdpId(this.newId().intValue());
		this.execute("insertDataPermit",dataPermit);
	}

	/**
	 *修改
	 * @param dataPermit 实体对像
	 */
	public void updateDataPermit(PmsDataPermit dataPermit){
		this.execute("updateDataPermit", dataPermit);
	}

	/**
	 * 通过主键删除
	 * @param pdpId 主键Id
	 */
	public void deleteDataPermitById(Integer pdpId){
		this.execute("deleteDataPermitById",pdpId);
	}

	/**
	 * 通过主键得到
	 * @param pdpId 主键Id
	 */
	public PmsDataPermit getDataPermitById(Integer pdpId){
		return (PmsDataPermit)this.queryEntity("getDataPermitById",pdpId);
	}

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PmsDataPermit> queryDataPermitList(Map<String,Object> condition){
		return (List<PmsDataPermit>)this.queryEntities("queryDataPermitList", condition);
	}

	/**
	 * 通过用户ID查询PmsDataPermit列表
	 *
	 *
	 * @param roleId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PmsDataPermit> queryDataPermitListByRoleId(Integer roleId) {
		return (List<PmsDataPermit>)queryEntities("queryDataPermitListByRoleId", roleId);
	}

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<PmsDataPermit> queryDataPermitList(Map<String,Object> condition,IPageSize page){
		return (IPageList<PmsDataPermit>)this.queryEntities("queryDataPermitList", condition, page);
	}

	/**
	 * 判断是否具有改类型的数据权限
	 *
	 *
	 * @param dataPermitType
	 * @param userId
	 * @return
	 */
	@Override
	public boolean haveDataPermit(String dataPermitType, Integer userId) {
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("dataPermitType",dataPermitType);
		condition.put("userId",userId);
		int count= queryInt("getDataPermitsCount",condition);
		if(count>0){
			return true;
		}else{
			return false;
		}
	}

}
