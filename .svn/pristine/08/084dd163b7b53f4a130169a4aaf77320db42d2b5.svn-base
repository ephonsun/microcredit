package banger.moduleIntf;

import banger.domain.loan.LoanCrossCheckQuanyiquan;
import banger.domain.loan.LoanInfoAddedClass;
import banger.domain.loan.LoanInfoAddedFiles;
import banger.domain.loan.LoanInfoAddedOwner;

import java.util.List;

/**
 * 贷款附件服务类
 * @author zhusw
 *
 */
public interface ILoanCrossCheckProvider {

	/**
	 * 同时修改损益表毛利率之后更新毛利表和净利表中的毛利净利偏差
	 * @param loanId
	 * 计算并修改交叉检验销售表偏差率 （用在检查销售表发生改变时使用）
	 * @param loanId 贷款id
	 */
	void updateGroProAndNetProDeviation(Integer loanId);

	/**
	 * 计算并修改交叉检验销售表偏差率 （用在损益情况信息表发生改变时使用）
	 * @param loanId 贷款id
	 */
	void updateSaleDev(Integer loanId);

	/**
	 * 计算并修改交叉检验权益表 （用在资产负责表信息表和损益情况信息表发生改变时使用）
	 * @param loanId 贷款id
	 */
	void updateQuanyiquanDev(Integer loanId);
}
