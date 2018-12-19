package banger.moduleIntf;

import banger.domain.config.AutoBaseTemplate;
import banger.domain.config.AutoTableColumn;
import banger.domain.config.AutoTableInfo;
import banger.domain.loan.LoanAuditTableFieldExtend;

import java.util.List;

public interface IAutoTemplateProvider {

	/**
	 * 得到模板集合
	 * @return
	 */
	List<AutoBaseTemplate> getTemplateListByIds(Integer[] tableIds);


	List<AutoBaseTemplate> getApprovalTemplateListByIds(Integer[] tableIds, List<LoanAuditTableFieldExtend> fieldList);

	/**
	 * 得到模板集合
	 * @return
	 */
	List<AutoBaseTemplate> getTemplateListByTables(String[] tableNames);
	List<AutoBaseTemplate> getTemplateListByTables(String[] tableNames, boolean isShow);

	List<AutoBaseTemplate> getTemplateListByLoanType(String loanTypeId, String precType, Integer tableId);

	/**
	 * 通过Id得到tableInfo对像
	 * @param tableId
	 * @return
	 */
	AutoTableInfo getTableInfoById(Integer tableId);

	/**
	 * @return
	 */
	List<AutoTableInfo> getAllTableInfoList();

	List<AutoBaseTemplate> getAllTemplateList();
}
