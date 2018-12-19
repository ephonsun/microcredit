package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.ICrossCheckGrossProfitDao;
import banger.domain.loan.LoanCrossCheckGrossProfit;

/**
 * 交叉检验毛利表数据访问类
 */
@Repository
public class CrossCheckGrossProfitDao extends PageSizeDao<LoanCrossCheckGrossProfit> implements ICrossCheckGrossProfitDao {

	/**
	 * 新增交叉检验毛利表
	 * @param crossCheckGrossProfit 实体对像
	 */
	public void insertCrossCheckGrossProfit(LoanCrossCheckGrossProfit crossCheckGrossProfit){
		crossCheckGrossProfit.setId(this.newId().intValue());
		this.execute("insertCrossCheckGrossProfit",crossCheckGrossProfit);
	}

	/**
	 *修改交叉检验毛利表
	 * @param crossCheckGrossProfit 实体对像
	 */
	public void updateCrossCheckGrossProfit(LoanCrossCheckGrossProfit crossCheckGrossProfit){
		this.execute("updateCrossCheckGrossProfit",crossCheckGrossProfit);
	}

	/**
	 * 通过主键删除交叉检验毛利表
	 * @param id 主键Id
	 */
	public void deleteCrossCheckGrossProfitById(Integer id){
		this.execute("deleteCrossCheckGrossProfitById",id);
	}

	/**
	 * 通过主键得到交叉检验毛利表
	 * @param id 主键Id
	 */
	public LoanCrossCheckGrossProfit getCrossCheckGrossProfitById(Integer id){
		return (LoanCrossCheckGrossProfit)this.queryEntity("getCrossCheckGrossProfitById",id);
	}

	/**
	 * 查询交叉检验毛利表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanCrossCheckGrossProfit> queryCrossCheckGrossProfitList(Map<String,Object> condition){
		return (List<LoanCrossCheckGrossProfit>)this.queryEntities("queryCrossCheckGrossProfitList", condition);
	}

	/**
	 * 分页查询交叉检验毛利表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanCrossCheckGrossProfit> queryCrossCheckGrossProfitList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanCrossCheckGrossProfit>)this.queryEntities("queryCrossCheckGrossProfitList", page, condition);
	}


	/**
	 * 根据贷款id获取毛利表信息
	 * @param loanId
	 * @return
	 */
	public LoanCrossCheckGrossProfit getCrossCheckGrossProfitByLoanId(Integer loanId){
		return (LoanCrossCheckGrossProfit)this.queryEntity("getCrossCheckGrossProfitByLoanId",loanId);
	}

	/**
	 *更新交叉检验毛利表 偏差字段
	 * @param condition
	 */
	public void updateCrossCheckGrossProfitDeviationRatio(Map<String,Object> condition){
		this.execute("updateCrossCheckGrossProfitDeviationRatio",condition);
	}

	/**
	 *修改交叉检验毛利表(app端传入空值时赋值字段为null)
	 * @param crossCheckGrossProfit 实体对像
	 */
	public void updateNullCrossCheckGrossProfit(LoanCrossCheckGrossProfit crossCheckGrossProfit){
		this.execute("updateNullCrossCheckGrossProfit",crossCheckGrossProfit);
	}
}
