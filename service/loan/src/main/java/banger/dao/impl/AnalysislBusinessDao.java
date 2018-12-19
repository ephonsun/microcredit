package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IAnalysislBusinessDao;
import banger.domain.loan.LoanAnalysislBusiness;

/**
 * 主键ID数据访问类
 */
@Repository
public class AnalysislBusinessDao extends PageSizeDao<LoanAnalysislBusiness> implements IAnalysislBusinessDao {

	/**
	 * 新增主键ID
	 * @param analysislBusiness 实体对像
	 */
	public void insertAnalysislBusiness(LoanAnalysislBusiness analysislBusiness){
		analysislBusiness.setId(this.newId().intValue());
		this.execute("insertAnalysislBusiness",analysislBusiness);
	}

	/**
	 *修改主键ID
	 * @param analysislBusiness 实体对像
	 */
	public void updateAnalysislBusiness(LoanAnalysislBusiness analysislBusiness){
		this.execute("updateAnalysislBusiness",analysislBusiness);
	}

	/**
	 * 通过主键删除主键ID
	 * @param id 主键Id
	 */
	public void deleteAnalysislBusinessById(Integer id){
		this.execute("deleteAnalysislBusinessById",id);
	}

	/**
	 * 通过主键得到主键ID
	 * @param id 主键Id
	 */
	public LoanAnalysislBusiness getAnalysislBusinessById(Integer id){
		return (LoanAnalysislBusiness)this.queryEntity("getAnalysislBusinessById",id);
	}

	/**
	 * 查询主键ID
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanAnalysislBusiness> queryAnalysislBusinessList(Map<String,Object> condition){
		return (List<LoanAnalysislBusiness>)this.queryEntities("queryAnalysislBusinessList", condition);
	}

	/**
	 * 分页查询主键ID
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanAnalysislBusiness> queryAnalysislBusinessList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanAnalysislBusiness>)this.queryEntities("queryAnalysislBusinessList", page, condition);
	}

}
