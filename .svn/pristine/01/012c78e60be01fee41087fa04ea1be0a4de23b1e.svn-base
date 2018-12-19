package banger.dao.intf;

import java.util.List;

import banger.domain.loan.LoanTemplateExtend;
import banger.framework.collection.DataTable;

public interface ILoanTemplateExtendDao {

	/**
	 * 获取贷款模板
	 * @param loanTypeId
	 * @param precType
	 * @return
	 */
	List<LoanTemplateExtend> getLoanTemplateList(Integer loanTypeId,String precType);

	/**
	 * 获取贷款模板
	 * @param loanTypeId
	 * @param precTypes
	 * @return
	 */
	List<LoanTemplateExtend> getLoanTemplateList(Integer loanTypeId,String[] precTypes);
	
	/**
	 * 获取贷款模板
	 * @param loanTypeId
	 * @param precType
	 * @return
	 */
	LoanTemplateExtend getLoanTemplateById(Integer loanTypeId,String precType,Integer tableId);

	/**
	 * 获取贷款模板
	 * @param loanTypeId
	 * @param precTypes
	 * @return
	 */
	LoanTemplateExtend getLoanTemplateById(Integer loanTypeId,String[] precTypes,Integer tableId);
	
	/**
	 * 保存代款模板信息
	 * @param table
	 */
	Integer[] saveLoanTemplateInfo(DataTable table,Boolean updateFlag);

	/**
	 * 保存代款模板信息
	 * @param table
	 */
	Integer saveLoanTemplateInfo(DataTable table);
	
	/**
	 * 得到贷款模板数据
	 * @return
	 */
	DataTable getTemplateDataById(String precType,String tableName,Integer loanId);

	/**
	 * 得到贷款模板数据
	 * @return
	 */
	DataTable getTemplateDataById(String precType,String tableName,Integer loanId,String extendCondtion);

	/**
	 * 得到贷款模板数据
	 * @param tableName
	 * @param id
	 * @return
	 */
	DataTable getTemplateDataById(String tableName,Integer id);

	/**
	 * 删除贷款申请所有模板数据
	 * @param tableName
	 * @param id
	 * @return
	 */
	void deleteLoanTemplateDataById(String tableName,Integer id);

	/**
	 * 得到贷款申请所有模板数据
	 * @param tableName
	 * @param loanId
	 * @return
	 */
	DataTable getLoanTemplateDataByLoanId(String tableName,Integer loanId);

	/**
	 * 产生表的主键
	 * @param tableName
	 * @param pk
	 * @return
	 */
	Integer newTableId(String tableName,String pk);
	
}
