package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.ICrossCheckNetProfitDao;
import banger.domain.loan.LoanCrossCheckNetProfit;

/**
 * 交叉检验净利表数据访问类
 */
@Repository
public class CrossCheckNetProfitDao extends PageSizeDao<LoanCrossCheckNetProfit> implements ICrossCheckNetProfitDao {

	/**
	 * 新增交叉检验净利表
	 * @param crossCheckNetProfit 实体对像
	 */
	public void insertCrossCheckNetProfit(LoanCrossCheckNetProfit crossCheckNetProfit){
		crossCheckNetProfit.setId(this.newId().intValue());
		this.execute("insertCrossCheckNetProfit",crossCheckNetProfit);
	}

	/**
	 *修改交叉检验净利表
	 * @param crossCheckNetProfit 实体对像
	 */
	public void updateCrossCheckNetProfit(LoanCrossCheckNetProfit crossCheckNetProfit){
		this.execute("updateCrossCheckNetProfit",crossCheckNetProfit);
	}

	/**
	 * 通过主键删除交叉检验净利表
	 * @param id 主键Id
	 */
	public void deleteCrossCheckNetProfitById(Integer id){
		this.execute("deleteCrossCheckNetProfitById",id);
	}

	/**
	 * 通过主键得到交叉检验净利表
	 * @param id 主键Id
	 */
	public LoanCrossCheckNetProfit getCrossCheckNetProfitById(Integer id){
		return (LoanCrossCheckNetProfit)this.queryEntity("getCrossCheckNetProfitById",id);
	}

	/**
	 * 查询交叉检验净利表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanCrossCheckNetProfit> queryCrossCheckNetProfitList(Map<String,Object> condition){
		return (List<LoanCrossCheckNetProfit>)this.queryEntities("queryCrossCheckNetProfitList", condition);
	}

	/**
	 * 分页查询交叉检验净利表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanCrossCheckNetProfit> queryCrossCheckNetProfitList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanCrossCheckNetProfit>)this.queryEntities("queryCrossCheckNetProfitList", page, condition);
	}

	/**
	 * app端通过贷款id获取净利检验详情
	 * @param loanId 贷款id
	 * @return
	 */
	public LoanCrossCheckNetProfit getCrossCheckNetProfitByLoanId(Integer loanId){
		return (LoanCrossCheckNetProfit)this.queryEntity("getCrossCheckNetProfitByLoanId",loanId);
	}

	/**
	 * 修改交叉检验净利表偏差字段
	 * @param condition
	 */
	public void updateCroCheNetProDevRatio(Map<String,Object> condition){
		this.execute("updateCroCheNetProDevRatio",condition);
	}


	/**
	 *修改交叉检验毛利表(app端传入空值时赋值字段为null)
	 * @param crossCheckNetProfit 实体对像
	 */
	public void updateNullCrossCheckNetProfit(LoanCrossCheckNetProfit crossCheckNetProfit){
		this.execute("updateNullCrossCheckNetProfit",crossCheckNetProfit);
	}

}
