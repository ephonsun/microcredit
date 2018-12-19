package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanAnalysislConsumption;

/**
 * 消费贷财务分析详情总表业务访问接口
 */
public interface IAnalysislConsumptionService {

	/**
	 * 新增消费贷财务分析详情总表
	 * @param analysislConsumption 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertAnalysislConsumption(LoanAnalysislConsumption analysislConsumption, Integer loginUserId);

	/**
	 *修改消费贷财务分析详情总表
	 * @param analysislConsumption 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateAnalysislConsumption(LoanAnalysislConsumption analysislConsumption, Integer loginUserId);

	/**
	 * 通过主键删除消费贷财务分析详情总表
	 * @param ID 主键Id
	 */
	void deleteAnalysislConsumptionById(Integer id);

	/**
	 * 通过主键得到消费贷财务分析详情总表
	 * @param ID 主键Id
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
