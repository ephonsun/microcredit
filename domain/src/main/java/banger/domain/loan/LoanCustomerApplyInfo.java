package banger.domain.loan;

import banger.domain.enumerate.LoanCollectionState;
import banger.domain.enumerate.LoanMonitorState;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.enumerate.LoanRefuseType;
import banger.framework.codetable.CodeTable;
import banger.framework.util.StringUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 客户贷款信息
 * @author zhusw
 *
 */
public class LoanCustomerApplyInfo extends LoanApplyInfo {
	private static final long serialVersionUID = 5641950695678354105L;
	
	@CodeTable(name="cdLoanType",key="loanTypeId",params="")
	private String loanTypeName;						//代款类型名称

	@CodeTable(name = "cdDictColByName", key = "loanRefuseReason", params = "CD_LOAN_REFUSE_REASON")
	private String loanRefuseReasonDisplay;				//拒绝原因

	public String getLoanTypeName() {
		return loanTypeName;
	}

	public void setLoanTypeName(String loanTypeName) {
		this.loanTypeName = loanTypeName;
	}

	public String getLoanProcessTypeName(){
		LoanProcessTypeEnum type = LoanProcessTypeEnum.valueOfType(this.getLoanProcessType());
		if(type!=null){
			return type.typeName;
		}
		return "";
	}

	//拒绝原因
	public String getLoanRefuseReasonDisplay() {
		return loanRefuseReasonDisplay;
	}

	//贷款过程名称
	public String getLoanProcessStateDisplay() {
		if(LoanProcessTypeEnum.REFUSE.equalType(this.getLoanProcessType())){
			if(LoanRefuseType.CUSTOMER.equalType(this.getLoanRefuseType())){
				return LoanRefuseType.CUSTOMER.typeName;
			}else if(LoanRefuseType.BANK.equalType(this.getLoanRefuseType())){
				return LoanRefuseType.BANK.typeName;
			}
		}else{
			String typeName = LoanProcessTypeEnum.getTypeNameByType(this.getLoanProcessType());
			return typeName;
		}
		return "";
	}

	//贷款催收状态名称
	public String getLoanCollectionStateDisplay() {
		return LoanCollectionState.getNameByState(this.getLoanCollectionState());
	}

	//贷款监控状态名称
	public String getLoanMonitorStateDisplay() {
		return LoanMonitorState.getNameByState(this.getLoanMonitorState());
	}

	//格式化后的代款金额
	public String getLoanAmountFormat() {
		if(StringUtil.isNotEmpty(this.getLoanProcessType())){
			if(LoanProcessTypeEnum.APPLY.equalType(this.getLoanProcessType())
					|| LoanProcessTypeEnum.ALLOT.equalType(this.getLoanProcessType())
					|| LoanProcessTypeEnum.REFUSE.equalType(this.getLoanProcessType())){
				return getDecimalFormat(this.getLoanApplyAmount());
			}else if(LoanProcessTypeEnum.ALLOT.equalType(this.getLoanProcessType())){
				return getDecimalFormat(this.getLoanProposeAmount());
			}else{
				return getDecimalFormat(this.getLoanResultAmount());
			}
		}
		return "";
	}

	public BigDecimal getLoanAmount(){
		if(StringUtil.isNotEmpty(this.getLoanProcessType())){
			if(LoanProcessTypeEnum.APPLY.equalType(this.getLoanProcessType())
					|| LoanProcessTypeEnum.ALLOT.equalType(this.getLoanProcessType())
					|| LoanProcessTypeEnum.REFUSE.equalType(this.getLoanProcessType())){
				return this.getLoanApplyAmount();
			}else if(LoanProcessTypeEnum.ALLOT.equalType(this.getLoanProcessType())){
				return this.getLoanProposeAmount();
			}else{
				return this.getLoanResultAmount();
			}
		}
		return new BigDecimal(0.0);
	}

	/**
	 *
	 * @param amount
	 * @return
	 */
	private String getDecimalFormat(BigDecimal amount){
		if(amount!=null){

			return new DecimalFormat(",###.##").format(amount);
		}
		return "";
	}
}
