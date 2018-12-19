package banger.dao.intf;

import java.util.List;

import banger.domain.loan.LoanFieldExtend;

public interface ILoanFieldExtendDao {

	/**
	 * 得到贷款显示字段
	 * @param loadTypeId 贷款类型ID
	 * @param precType	贷款过程
	 * @return
	 */
	List<LoanFieldExtend> getLoanFieldList(Integer loanTypeId,String precType);

	/**
	 * 得到贷款显示字段
	 * @param loadTypeId 贷款类型ID
	 * @param precType	贷款过程
	 * @return
	 */
	List<LoanFieldExtend> getLoanFieldList(Integer loanTypeId,String[] precTypes);
	
	/**
	 * 得到贷款显示字段
	 * @param loadTypeId 贷款类型ID
	 * @param precType	贷款过程
	 * @param tableId	模板ID
	 * @return
	 */
	List<LoanFieldExtend> getLoanFieldList(Integer loanTypeId,String precType,Integer tableId);

	/**
	 * 得到贷款审批显示字段
	 * @param processId
	 * @param tableId
	 * @return
	 */
	List<LoanFieldExtend> getTemplateFieldListByProcessId(Integer processId,Integer tableId);

	/**
	 * 得到贷款审批显示字段
	 * @param tableId
	 * @return
	 */
	List<LoanFieldExtend> getLoanFieldListByTableId(Integer tableId);
	
}
