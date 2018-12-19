package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IAnalysislConsumptionDao;
import banger.domain.loan.LoanAnalysislConsumption;

/**
 * 消费贷财务分析详情总表数据访问类
 */
@Repository
public class AnalysislConsumptionDao extends PageSizeDao<LoanAnalysislConsumption> implements IAnalysislConsumptionDao {

	/**
	 * 新增消费贷财务分析详情总表
	 * @param analysislConsumption 实体对像
	 */
	public void insertAnalysislConsumption(LoanAnalysislConsumption analysislConsumption){
		analysislConsumption.setId(this.newId().intValue());
		this.execute("insertAnalysislConsumption",analysislConsumption);
	}

	/**
	 *修改消费贷财务分析详情总表
	 * @param analysislConsumption 实体对像
	 */
	public void updateAnalysislConsumption(LoanAnalysislConsumption analysislConsumption){
		this.execute("updateAnalysislConsumption",analysislConsumption);
	}

	/**
	 * 通过主键删除消费贷财务分析详情总表
	 * @param id 主键Id
	 */
	public void deleteAnalysislConsumptionById(Integer id){
		this.execute("deleteAnalysislConsumptionById",id);
	}

	/**
	 * 通过主键得到消费贷财务分析详情总表
	 * @param id 主键Id
	 */
	public LoanAnalysislConsumption getAnalysislConsumptionById(Integer id){
		return (LoanAnalysislConsumption)this.queryEntity("getAnalysislConsumptionById",id);
	}

	/**
	 * 查询消费贷财务分析详情总表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanAnalysislConsumption> queryAnalysislConsumptionList(Map<String,Object> condition){
		return (List<LoanAnalysislConsumption>)this.queryEntities("queryAnalysislConsumptionList", condition);
	}

	/**
	 * 分页查询消费贷财务分析详情总表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanAnalysislConsumption> queryAnalysislConsumptionList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanAnalysislConsumption>)this.queryEntities("queryAnalysislConsumptionList", page, condition);
	}

}
