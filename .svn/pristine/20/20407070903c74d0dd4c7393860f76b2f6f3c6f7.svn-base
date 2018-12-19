package banger.dao.impl;

import banger.dao.intf.IInfoAddedOwnerDao;
import banger.domain.loan.LoanInfoAddedOwner;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 贷款资料附件对像分类表数据访问类
 */
@Repository
public class InfoAddedOwnerDao extends PageSizeDao<LoanInfoAddedOwner> implements IInfoAddedOwnerDao {

	/**
	 * 新增贷款资料附件对像分类表
	 * @param infoAddedOwner 实体对像
	 */
	public void insertInfoAddedOwner(LoanInfoAddedOwner infoAddedOwner){
		infoAddedOwner.setOwnerId(this.newId().intValue());
		this.execute("insertInfoAddedOwner",infoAddedOwner);
	}

	/**
	 *修改贷款资料附件对像分类表
	 * @param infoAddedOwner 实体对像
	 */
	public void updateInfoAddedOwner(LoanInfoAddedOwner infoAddedOwner){
		this.execute("updateInfoAddedOwner",infoAddedOwner);
	}

	/**
	 * 通过主键删除贷款资料附件对像分类表
	 * @param ownerId 主键Id
	 */
	public void deleteInfoAddedOwnerById(Integer ownerId){
		this.execute("deleteInfoAddedOwnerById",ownerId);
	}

	/**
	 * 通过主键得到贷款资料附件对像分类表
	 * @param ownerId 主键Id
	 */
	public LoanInfoAddedOwner getInfoAddedOwnerById(Integer ownerId){
		return (LoanInfoAddedOwner)this.queryEntity("getInfoAddedOwnerById",ownerId);
	}
	
	/**
	 * 查询贷款资料附件对像分类表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanInfoAddedOwner> getAllInfoAddedOwnerList(){
		return (List<LoanInfoAddedOwner>)this.queryEntities("getAllInfoAddedOwnerList", new Object[0]);
	}

	/**
	 *
	 * @param ownerIds
	 * @return
	 */
	public List<LoanInfoAddedOwner> queryInfoAddedOwnerListByOwnerIds(String ownerIds){
		Map<String,Object> condition = new HashMap<String,Object>();
		if(StringUtil.isNotEmpty(ownerIds)) {
			String[] ids = ownerIds.split("\\,");
			condition.put("ownerIds", ids);
		}
		condition.put("isDel", 0);
		condition.put("isActived", 1);
		return (List<LoanInfoAddedOwner>)this.queryEntities("queryInfoAddedOwnerListByOwnerIds", condition);
	}

	/**
	 * 查询贷款资料附件对像分类表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanInfoAddedOwner> queryInfoAddedOwnerList(Map<String,Object> condition){
		return (List<LoanInfoAddedOwner>)this.queryEntities("queryInfoAddedOwnerList", condition);
	}

	/**
	 * 分页查询贷款资料附件对像分类表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanInfoAddedOwner> queryInfoAddedOwnerList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanInfoAddedOwner>)this.queryEntities("queryInfoAddedOwnerList", page, condition);
	}

	/**
	 * 查询最大排序号
	 * @return
	 */
    public Integer queryInfoAddedOwnerSortNum() {
		return this.queryInt("getAddedOwnerSortNum");
    }

	/**
	 * 上移下移
	 */
    public List<LoanInfoAddedOwner> queryAllOwnerOrder() {
		Map<String, Object> condition = new HashMap<String, Object>();
		return (List<LoanInfoAddedOwner>) this.queryEntities("queryAllOwnerOrder",condition);
	}

}
