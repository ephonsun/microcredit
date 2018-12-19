package banger.domain.enumerate;

import banger.framework.collection.OptionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 贷款过程
 * 
 * @author zhusw
 * 枚举第一个参数，区分大小写，不要修改，不要修改，不要修改 重要的事情说三遍!!  <-----\ [^_-] )
 */
public enum LoanProcessTypeEnum {
	APPLY("Apply","申请", new int[] {1,4,2,3,13,12,8}, new int[] {1,2,3,4} ,new String[]{"Apply"}, "", "Allot"),
	ALLOT("Allot","分配", new int[] {}, new int[] {},new String[]{"Apply"},"Apply", "Investigate"),
	INVESTIGATE("Investigate","调查", new int[] {1,4,14,2,3,13,12,8}, new int[] {1,2,3,14},new String[]{"Investigate"}, "Allot", "Approval"),
	APPROVAL("Approval","审批", new int[] {}, new int[] {},new String[]{"Investigate"}, "Investigate", "Contract"),
	CONTRACT("Contract","贷款合同", new int[] {}, new int[] {},new String[]{"Contract"}, "Approval", "Sign"),
	SIGN("Sign","合同签订", new int[] {}, new int[] {},new String[]{"Sign"}, "Contract", "Loan"),
	LOAN("Loan","放款", new int[] {18,40}, new int[] {18},new String[]{"Investigate","Loan"}, "Approval", "AfterLoan"),
	AUTHORIZED("Authorized","授权", new int[] {18}, new int[] {18},new String[]{"Investigate","Loan"}, "Loan", "Undisclosed"),
	UNDISCLOSED("Undisclosed","出账未确认", new int[] {18}, new int[] {18},new String[]{"Investigate","Loan"}, "Authorized", "AfterLoan"),
	AFTER_LOAN("AfterLoan","贷后", new int[] {},new int[] {},new String[]{"Investigate","Loan"}, "Loan", "Clearance"),
	CLEARANCE("Clearance","结清", new int[] {}, new int[] {},new String[]{"Investigate","Loan"}, "AfterLoan", "Refuse"),
	REFUSE("Refuse","拒绝", new int[] {}, new int[] {},new String[]{""}, "Clearance", ""),
	CONTRACT_CANCEL("ContractCancel","合同注销", new int[] {}, new int[] {},new String[]{""}, "", "")
	;
	public final String type; // 贷款过程类型
	public final String typeName; // 贷款过程类型名称
	public final int[] tableIds; // 默认新增模板
	public final int[] fixedTableIds;		//必填模块
	public final String[] showTypes;		//安卓端显示类型
	public final String prevType;	//上一个流程节点
	public final String nextType;	//下一个流程节点

	LoanProcessTypeEnum(String type, String typeName, int[] tableIds,int[] fixedTableIds,String[] showTypes, String prevType, String nextType) {
		this.type = type;
		this.typeName = typeName;
		this.tableIds = tableIds;
		this.fixedTableIds = fixedTableIds;
		this.showTypes = showTypes;
		this.prevType = prevType;
		this.nextType = nextType;
	}
	
	public boolean equalType(String type){
		return this.type.equalsIgnoreCase(type);
	}
	
	public static LoanProcessTypeEnum valueOfType(String type){
		for(LoanProcessTypeEnum loanProcess : LoanProcessTypeEnum.values()){
			if(loanProcess.type.equalsIgnoreCase(type)){
				return loanProcess;
			}
		}
		return null;
	}

	public static String getTypeNameByType(String type){
		for(LoanProcessTypeEnum loanProcess : LoanProcessTypeEnum.values()){
			if(loanProcess.type.equalsIgnoreCase(type)){
				return loanProcess.typeName;
			}
		}
		return null;
	}

	public static String getNextType(String type) {
		for(LoanProcessTypeEnum loanProcess : LoanProcessTypeEnum.values()){
			if(loanProcess.type.equalsIgnoreCase(type)){
				return loanProcess.nextType;
			}
		}
		return null;
	}

	public static String getPrevType(String type) {
		for(LoanProcessTypeEnum loanProcess : LoanProcessTypeEnum.values()){
			if(loanProcess.type.equalsIgnoreCase(type)){
				return loanProcess.prevType;
			}
		}
		return null;
	}

    public static List<OptionItem> getOptionItems() {
		List<OptionItem> items = new ArrayList<OptionItem>();
		for (LoanProcessTypeEnum processTypeEnum : LoanProcessTypeEnum.values()) {
			OptionItem item = new OptionItem(processTypeEnum.type,processTypeEnum.typeName);
			items.add(item);
		}
		return items;
    }
}
