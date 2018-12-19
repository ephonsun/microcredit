package banger.constant;

public class AppParamsConst {
	
	//产品
	public static final String PARAMS_REQUEST_QUERY_PRODUCT_LIST_DATA ="offset";
	public static final String PARAMS_RESPONSE_QUERY_PRODUCT_LIST_DATA = "loanType,loanTypeName,productId,productName,productTypeName,productInfo";
	public static final int PAGE_SIZE_PROUCT_LIST = 10;
	public static final String PARAMS_REQUEST_GET_PRODUCT_DETAIL = "productId";

	public static final String PARAMS_RESPONSE_GET_PRODUCT_DETAIL = "loanId,loanTypeId,loanProcessType,loanName,loanAge,loanSex,loanIdentifyType,loanIdentifyNum,loanTelNum,loanCreditDate,loanApplyAmount," +
			"loanProposeAmount,loanResultAmount,loanRepayDate,repaymentMode,loanCustomerId,loanCreditAmount,loanBalanceAmount,loanAccountAmount,loanAccountNo," +
			"loanContractNumber,loanContractBegin,loanContractEnd,iouNum,accountNum,enterAccount,nextRepaymentAmount,loanArrears,loanIrrevocableInterest,overdueLimit," +
			"loanTypeName,loanProcessTypeName,loanIdentifyTypeName,loanIdentifyNumX,loanTelNumX,loanResultAmountFormat,loanAfterState,loanAfterStateName,loanBalanceAmountFormat," +
			"nextRepaymentAmountFormat,loanArrearsFormat,loanIrrevocableInterestFormat,loanAccountAmountFormat";
	public static final String PARAMS_RESPONSE_GET_PRODUCT_DETAIL_FILE = "ppfId,ppfFileNameOld,ppfFileSize,ppfFileType";
	//意向客户
	public static final String PARAMS_REQUEST_QUERY_INTENT_CUSTOMER_LIST_DATA = "offset,userId";
	public static final String PARAMS_RESPONSE_QUERY_INTENT_CUSTOMER_LIST_DATA= "picId,picProductId,picName,picCreateDate,picProductName,picPhone,picPhoneX,picCertificateNum,picCertificateNumX";
	public static final int PAGE_SIZE_INTENT_CUSTOMER_LIST = 10;
	public static final String PARAMS_REQUEST_GET_INTENT_CUSTOMER_DETAIL="picId";
	public static final String PARAMS_RESPONSE_GET_INTENT_CUSTOMER_DETAIL= "picId,picProductId,picName,picPhoneX,picProductName,picCreateDate,picRemark";
	public static final String PARAMS_REQUEST_INSERT_INTENT_CUSTOMER = "userId,picName,picGender,picCertificateType,picCertificateNum,picPhone,picProductId,picProductName";
	
	public static final int PAGE_SIZE_TASK_LIST = 10;

	public static final int PAGE_SIZE_CUSTOMER_LIST = 10;
	
	//数据字典
	public static final String PARAMS_REQUEST_QUERY_DATA_DICT_COL_LIST_BY_NAME =  "dataDictName";
	public static final String PARAMS_RESPONSE_QUERY_DATA_DICT_COL_LIST_BY_NAME =  "dictName,dictValue";

	//贷款主键ID
	public static final String LOAN_ID ="loanId";

	//交叉检验
	public static final String PARAMS_REQUEST_LOANCROSSCHECKNETPROFIT_DATA ="loanId";
	public static final String PARAMS_RESPONSE_GET_CROSSCHECKNETPROFIT_BYLOANID= "id,checkNetProfitRatio1,infomation1,checkNetProfitRatio2,infomation2,checkNetProfitRatio3,infomation3";
	public static final String PARAMS_REQUEST_LOANCROSSCHECKNETPROFIT_DATA_PRIMARY ="loanClassId,loanId,userId";
	public static final String PARAMS_RESPONSE_GET_CROSSCHECK_INCOME_BYLOANID= "id,wageFlow,wageFlowDeviation,incomeCert,incomeCertDeviation,personTax,personTaxDeviation,otherIncome,otherIncomeDeviation,infomation";
	public static final String PARAMS_RESPONSE_GET_CROSSCHECKSALE_BYLOANID= "id,checkSaleAmount1,infomation1,checkSaleAmount2,infomation2,checkSaleAmount3,infomation3";//销售额
	public static final String PARAMS_RESPONSE_GET_CROSSCHECKQUANYIQUAN_BYLOANID= "id,initRightDate,initRight,initRightDesc,periodProfit,periodInjection,appreciation,increaseDesc,periodExtract,depreciation,reduceDesc,deservedRight,actualRight,deviation,deviationRatio";//权益


	public static final String PARAMS_RESPONSE_GET_CROSSCHECK_GROPRO_BYLOANID = "id,checkGrossProfitRatio1,infomation1,checkGrossProfitRatio2,infomation2,checkGrossProfitRatio3,infomation3";
	//财务分析
	public static final String PARAMS_REQUEST_LOAN_FINANCE_ANALYS_QUERY ="loanId,loanClassId";

	//资产负债
		//--账款
	public static final String LOAN_ASSETS_ACCOUNT_ITEM ="LOAN_ASSETS_ACCOUNT_ITEM,ASSETS_RECEIVABLE_AMOUNT,ASSETS_PAYMENT_AMOUNT,DEBTS_PAYABLE_AMOUNT,DEBTS_RECEIVED_AMOUNT";
		//--现金
	public static final String LOAN_ASSETS_AMOUNT_ITEM ="LOAN_ASSETS_AMOUNT_ITEM,ASSETS_CASH_AMOUNT,ASSETS_OPERATING_AMOUNT,ASSETS_NON_OPERATING_AMOUNT,ASSETS_OTHER_AMOUNT,ASSETS_INVEST_AMOUNT,ASSETS_EXTERNAL_CLAIMS,ASSETS_ADVANCE_PAYMENT_AMOUNT,DEBTS_BIZ_OTHER_AMOUNT";
		//--负债
	public static final String LOAN_ASSETS_DEBTS_ITEM ="LOAN_ASSETS_DEBTS_ITEM,DEBTS_SHORT_AMOUNT,DEBTS_LONG_AMOUNT,DEBTS_INVEST_AMOUNT,DEBTS_SELF_USER_AMOUNT,DEBTS_CONSUME_AMOUNT,DEBTS_OTHER_AMOUNT";
		//--固定资产
	public static final String LOAN_ASSETS_FIXED_ITEM ="ASSETS_FIXED_AMOUNT";
		//--存货
	public static final String LOAN_ASSETS_STOCK_ITEM ="ASSETS_STOCK_AMOUNT";

}

