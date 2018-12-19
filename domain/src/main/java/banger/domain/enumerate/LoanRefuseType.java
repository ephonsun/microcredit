package banger.domain.enumerate;

/**
 * 拒绝类型
 * @author zhusw
 *
 */
public enum LoanRefuseType {
	
	 CUSTOMER("Customer", "客户拒绝"),
	 BANK("Bank", "银行拒绝"),
	 ;
	 
	 public final String type; // 拒绝类型
	 public final String typeName; // 拒绝类型名称
	 
	 LoanRefuseType(String type,String typeName){
		 this.type = type;
		 this.typeName = typeName;
	 }
	 
	 public boolean equalType(String type){
		 return this.type.equalsIgnoreCase(type);
	 }
	 
	 public static LoanRefuseType valueOfType(String type){
		 if(LoanRefuseType.CUSTOMER.type.equals(type)){
			 return LoanRefuseType.CUSTOMER;
		 }else if(LoanRefuseType.BANK.type.equals(type)){
			 return LoanRefuseType.BANK;
		 }
		 return null;
	 }

	 public static String getNameByType(String type){
		 for(LoanRefuseType refuseType : LoanRefuseType.values()){
			 if(refuseType.type.equalsIgnoreCase(type)){
				 return refuseType.typeName;
			 }
		 }
		 return "";
	 }
	
}
