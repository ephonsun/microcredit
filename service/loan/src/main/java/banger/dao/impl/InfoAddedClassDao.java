package banger.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.framework.util.StringUtil;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IInfoAddedClassDao;
import banger.domain.loan.LoanInfoAddedClass;

/**
 * 贷款资料附件分类表数据访问类
 */
@Repository
public class InfoAddedClassDao extends PageSizeDao<LoanInfoAddedClass> implements IInfoAddedClassDao {

	/**
	 * 新增贷款资料附件分类表
	 * @param infoAddedClass 实体对像
	 */
	public void insertInfoAddedClass(LoanInfoAddedClass infoAddedClass){
		infoAddedClass.setClassId(this.newId().intValue());
		this.execute("insertInfoAddedClass",infoAddedClass);
	}

	/**
	 *修改贷款资料附件分类表
	 * @param infoAddedClass 实体对像
	 */
	public void updateInfoAddedClass(LoanInfoAddedClass infoAddedClass){
		this.execute("updateInfoAddedClass",infoAddedClass);
	}

	/**
	 * 通过主键删除贷款资料附件分类表
	 * @param classId 主键Id
	 */
	public void deleteInfoAddedClassById(Integer classId){
		this.execute("deleteInfoAddedClassById",classId);
	}

	/**
	 * 通过主键得到贷款资料附件分类表
	 * @param classId 主键Id
	 */
	public LoanInfoAddedClass getInfoAddedClassById(Integer classId){
		return (LoanInfoAddedClass)this.queryEntity("getInfoAddedClassById",classId);
	}

	public List<LoanInfoAddedClass> queryInfoAddedClassListByOwnerIds(String ownerIds){
		Map<String,Object> condition = new HashMap<String,Object>();
		if(StringUtil.isNotEmpty(ownerIds)) {
			String[] ids = ownerIds.split("\\,");
			condition.put("ownerIds", ids);
		}
		condition.put("isDel", 0);
		condition.put("isActived", 1);
		return (List<LoanInfoAddedClass>)this.queryEntities("queryInfoAddedClassListByOwnerIds",condition);
	}
	
	/**
	 * 查询贷款资料附件分类表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanInfoAddedClass> getAllInfoAddedClassList(){
		return (List<LoanInfoAddedClass>)this.queryEntities("getAllInfoAddedClassList", new Object[0]);
	}

	/**
	 * 查询贷款资料附件分类表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanInfoAddedClass> queryInfoAddedClassList(Map<String,Object> condition){
		return (List<LoanInfoAddedClass>)this.queryEntities("queryInfoAddedClassList", condition);
	}

	/**
	 * 分页查询贷款资料附件分类表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanInfoAddedClass> queryInfoAddedClassList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanInfoAddedClass>)this.queryEntities("queryInfoAddedClassList", page, condition);
	}

    @Override
    public Integer queryMaxOrderNumByOwnerId(Integer ownerId) {
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("ownerId",ownerId);
		return this.queryInt("queryMaxOrderNumByOwnerId",condition);
    }
	/**
	 * 查询排序列表
	 * @return
	 * @param ownerId
	 */
    public List<LoanInfoAddedClass> queryAllClassOrder(Integer ownerId) {
    	Map<String,Object> condition = new HashMap<String, Object>();
    	condition.put("ownerId",ownerId);
    	return (List<LoanInfoAddedClass>) this.queryEntities("queryCLassOrderByOwnerId",condition);
    }

}
