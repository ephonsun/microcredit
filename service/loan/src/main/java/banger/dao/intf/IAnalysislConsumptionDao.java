package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanAnalysislConsumption;

/**
 * 消费贷财务分析详情总表数据访问接口
 */
public interface IAnalysislConsumptionDao {

	/**
	 * 新增消费贷财务分析详情总表
	 * @param analysislConsumption 实体对像
	 */
	void insertAnalysislConsumption(LoanAnalysislConsumption analysislConsumption);

	/**
	 *修改消费贷财务分析详情总表
	 * @param analysislConsumption 实体对像
	 */
	void updateAnalysislConsumption(LoanAnalysislConsumption analysislConsumption);

	/**
	 * 通过主键删除消费贷财务分析详情总表
	 * @param id 主键Id
	 */
	void deleteAnalysislConsumptionById(Integer id);

	/**
	 * 通过主键得到消费贷财务分析详情总表
	 * @param id 主键Id
	 */
	LoanAnalysislConsumption getAnalysislConsumptionById(Integer id);

	/**
	 * 查询消费贷财务分析详情总表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanAnalysislConsumption> queryAnalysislConsumptionList(Map<String, Object> condition);

	/**
	 * 分页查询消费贷财务分析详情总表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanAnalysislConsumption> queryAnalysislConsumptionList(Map<String, Object> condition, IPageSize page);

}
