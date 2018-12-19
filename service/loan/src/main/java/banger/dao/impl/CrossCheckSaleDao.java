package banger.dao.impl;

import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanCrossCheckIncome;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.ICrossCheckSaleDao;
import banger.domain.loan.LoanCrossCheckSale;

/**
 * 交叉检验销售额表数据访问类
 */
@Repository
public class CrossCheckSaleDao extends PageSizeDao<LoanCrossCheckSale> implements ICrossCheckSaleDao {

	/**
	 * 新增交叉检验销售额表
	 * @param crossCheckSale 实体对像
	 */
	public void insertCrossCheckSale(LoanCrossCheckSale crossCheckSale){
		crossCheckSale.setId(this.newId().intValue());
		this.execute("insertCrossCheckSale",crossCheckSale);
	}

	/**
	 *修改交叉检验销售额表
	 * @param crossCheckSale 实体对像
	 */
	public void updateCrossCheckSale(LoanCrossCheckSale crossCheckSale){
		this.execute("updateCrossCheckSale",crossCheckSale);
	}

	/**
	 * 通过主键删除交叉检验销售额表
	 * @param id 主键Id
	 */
	public void deleteCrossCheckSaleById(Integer id){
		this.execute("deleteCrossCheckSaleById",id);
	}

	/**
	 * 通过主键得到交叉检验销售额表
	 * @param id 主键Id
	 */
	public LoanCrossCheckSale getCrossCheckSaleById(Integer id){
		return (LoanCrossCheckSale)this.queryEntity("getCrossCheckSaleById",id);
	}

	/**
	 * 查询交叉检验销售额表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanCrossCheckSale> queryCrossCheckSaleList(Map<String,Object> condition){
		return (List<LoanCrossCheckSale>)this.queryEntities("queryCrossCheckSaleList", condition);
	}

	/**
	 * 分页查询交叉检验销售额表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanCrossCheckSale> queryCrossCheckSaleList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanCrossCheckSale>)this.queryEntities("queryCrossCheckSaleList", page, condition);
	}



	/**
	 * 交叉销售表的查询
	 * @param loanId
	 * @return
	 */
	@Override
	public LoanCrossCheckSale getCrossCheckSaleByLoanId(Integer loanId) {
		return (LoanCrossCheckSale)this.queryEntity("getCrossCheckSaleByLoanId",loanId);
	}



	/**
	 * 修改交叉检验销售表偏差字段
	 * @param condition
	 */
	public void updateCrossCheckSaleDevRatio(Map<String,Object> condition){
		this.execute("updateCroCheSaleDevRatio",condition);
	}
}
