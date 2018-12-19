package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IIndustryGradeexpDao;
import banger.domain.loan.LoanIndustryGradeexp;

/**
 * 贷款行业指引等级说明表数据访问类
 */
@Repository
public class IndustryGradeexpDao extends PageSizeDao<LoanIndustryGradeexp> implements IIndustryGradeexpDao {

	/**
	 * 新增贷款行业指引等级说明表
	 * @param industryGradeexp 实体对像
	 */
	public void insertIndustryGradeexp(LoanIndustryGradeexp industryGradeexp){
		industryGradeexp.setId(this.newId().intValue());
		this.execute("insertIndustryGradeexp",industryGradeexp);
	}

	/**
	 *修改贷款行业指引等级说明表
	 * @param industryGradeexp 实体对像
	 */
	public void updateIndustryGradeexp(LoanIndustryGradeexp industryGradeexp){
		this.execute("updateIndustryGradeexp",industryGradeexp);
	}

	/**
	 * 通过主键删除贷款行业指引等级说明表
	 * @param id 主键Id
	 */
	public void deleteIndustryGradeexpById(Integer id){
		this.execute("deleteIndustryGradeexpById",id);
	}

	/**
	 * @Author: yangdw
	 * @param loanId
	 * @Description:通过loanId删除款行业指引等级说明表
	 * @Date: 10:00 2017/9/25
	 */
	public void deleteIndustryGradeexpByLoanId(Integer loanId) {
		this.execute("deleteIndustryGradeexpByLoanId",loanId);
	}

	/**
	 * 通过主键得到贷款行业指引等级说明表
	 * @param id 主键Id
	 */
	public LoanIndustryGradeexp getIndustryGradeexpById(Integer id){
		return (LoanIndustryGradeexp)this.queryEntity("getIndustryGradeexpById",id);
	}

	/**
	 * 查询贷款行业指引等级说明表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanIndustryGradeexp> queryIndustryGradeexpList(Map<String,Object> condition){
		return (List<LoanIndustryGradeexp>)this.queryEntities("queryIndustryGradeexpList", condition);
	}

	/**
	 * 分页查询贷款行业指引等级说明表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanIndustryGradeexp> queryIndustryGradeexpList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanIndustryGradeexp>)this.queryEntities("queryIndustryGradeexpList", page, condition);
	}

}
