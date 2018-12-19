package banger.moduleImpl;

import banger.dao.intf.IApplyInfoDao;
import banger.domain.customer.CustCustomerCreditCheck;
import banger.domain.enumerate.LoanPersonTable;
import banger.domain.enumerate.SocketCodeTypeEnum;
import banger.domain.loan.LoanApplyInfo_Web_Query;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.reader.Reader;
import banger.framework.util.StringUtil;
import banger.moduleIntf.*;
import banger.service.intf.IAutoTableInfoService;
import banger.socket.SocketDemo;
import banger.util.CodedProduceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 贷款模块
 * @author zhusw
 *
 */
@Repository
public class LoanModuleImpl implements ILoanModule {
	@Resource
	private ILoanApplyProvider loanApplyProvider;
	
	@Resource
	private ILoanAddedProvider loanAddedProvider;
	
	@Resource
	private IAutoTableInfoService autoTableInfoService;

	@Resource
	private ILoanHistoryProvider loanHistoryProvider;

	@Resource
	private ILoanAuditProvider loanAuditProvider;
	@Resource
	private IApplyInfoDao applyInfoDao;

	@Resource
	private IAssetsInfoProvider assetsInfoProvider;

	@Resource
	private ILoanFinanceAnalysisService loanFinanceAnalysisService;
	@Resource
	private ILoanApprovalProvider loanApprovalProvider;
	@Autowired
	private SocketDemo socketDemo;


	/**
	 * 
	 */
	@Override
	public ILoanApplyProvider getLoanApplyProvider() {
		return loanApplyProvider;
	}

	@Override
	public ILoanApprovalProvider getLoanApprovalProvider() {
		return loanApprovalProvider;
	}


	/**
	 * 返回贷款附件服务
	 * @return
	 */
	@Override
	public ILoanAddedProvider getLoanAddedProvider(){
		return loanAddedProvider;
	}

	/**
	 * 贷款审批
	 * @return
	 */
	public ILoanAuditProvider getLoanAuditProvider() {
		return loanAuditProvider;
	}

	/**
	 * 得到贷款历史
	 * @return
	 */
	public ILoanHistoryProvider getLoanHistoryProvider(){
		return loanHistoryProvider;
	}


	/**
	 * 资产分析
	 * @return
	 */
	@Override
	public IAssetsInfoProvider getAssetsInfoProvider(){
		return assetsInfoProvider;
	}

	/**
	 * 资产分析
	 * @return
	 */
	@Override
	public ILoanFinanceAnalysisService getLoanFinanceAnalysisService(){
		return loanFinanceAnalysisService;
	}

	@Override
	public List<CustCustomerCreditCheck> getAllLoanPersonByLoanId(
			Integer loanId, String loanProcessType) {
		List<CustCustomerCreditCheck> list=new ArrayList<CustCustomerCreditCheck>();
		//查询申请人
		//DataTable personalTable = autoTableInfoService.getTemplateDataById(LoanPersonTable.LBIZ_PERSONAL_INFO.table,loanId,loanProcessType," AND IDENTIFY_TYPE = '01'");
		DataTable personalTable = autoTableInfoService.getTemplateDataById(LoanPersonTable.LBIZ_PERSONAL_INFO.table,loanId,loanProcessType);
		if(personalTable!=null && personalTable.rowSize()>0){
			DataRow row=personalTable.getRow(0);
			CustCustomerCreditCheck cust=new CustCustomerCreditCheck();
			String identifyNum = Reader.stringReader().getValue(row,"IDENTIFY_NUM");
			String customerName = Reader.stringReader().getValue(row,"CUSTOMER_NAME");
			if(StringUtil.isNotEmpty(identifyNum) && StringUtil.isNotEmpty(customerName)){
				cust.setCardNo(identifyNum);
				cust.setCustomerName(customerName);
				cust.setCustomerType("借款人");
				list.add(cust);
			}
		}
		
		//查询共同借款人
		DataTable joinTable = autoTableInfoService.getTemplateDataById(LoanPersonTable.LBIZ_JOINT_BORROWER.table,loanId,loanProcessType);
		if(joinTable!=null && joinTable.rowSize()>0){
			for(DataRow row : joinTable.getRows()){
				CustCustomerCreditCheck cust=new CustCustomerCreditCheck();
				String idCard = Reader.stringReader().getValue(row,"ID_CARD");
				String fullName = Reader.stringReader().getValue(row,"FULL_NAME");
				if(StringUtil.isNotEmpty(idCard) && StringUtil.isNotEmpty(fullName)){
					cust.setCardNo(idCard);
					cust.setCustomerName(fullName);
					cust.setCustomerType("共同借款人");
					list.add(cust);
				}
			}
		}
		
		//查询担保人
		DataTable guaranteeTable = autoTableInfoService.getTemplateDataById(LoanPersonTable.LBIZ_LOAN_GUARANTEE.table,loanId,loanProcessType);
		if(guaranteeTable!=null && guaranteeTable.rowSize()>0){
			for(DataRow row : guaranteeTable.getRows()){
				CustCustomerCreditCheck cust=new CustCustomerCreditCheck();
				String idCard = Reader.stringReader().getValue(row,"ID_CARD");
				String fullName = Reader.stringReader().getValue(row,"FULL_NAME");
				if(StringUtil.isNotEmpty(idCard) && StringUtil.isNotEmpty(fullName)) {
					cust.setCardNo(idCard);
					cust.setCustomerName(fullName);
					cust.setCustomerType("担保人");
					list.add(cust);
				}
			}
		}
		
		return list;
	}

	@Override
	public IPageList<LoanApplyInfo_Web_Query> queryBasicInfoListForCredit(Map<String, Object> condition, IPageSize page) {
		return this.applyInfoDao.queryAllInfoList(condition,page);
	}

	@Override
	public String getContractCode(Integer operationCode,boolean type) {
		return CodedProduceUtil.getCode(operationCode,type);
	}
	@Override
	public String getAuthorizedSerialCode(boolean type){
		return CodedProduceUtil.getAuthorizedSerialCode(type);
	}
	@Override
	public String syncCustomerInfo(Integer loanId){
		return socketDemo.syncCustomerInfo(loanId);
	}

	@Override
	public String syncGuaranteeInfo(Integer loanId) {
		return socketDemo.syncGuaranteeInfo(loanId);
	}

	@Override
	public String syncMortgageInfo(Integer loanId) {
		return socketDemo.syncMortgageInfo(loanId);
	}

	@Override
	public String syncPledgeInfo(Integer loanId) {
		return socketDemo.syncPledgeInfo(loanId);
	}

	@Override
	public String syncContractInfo(Integer loanId) {
		return socketDemo.syncContractInfo(loanId);
	}

	@Override
	public String syncLoanAuthorizedInfo(Integer loanId) {
		return socketDemo.syncLoanAuthorizedInfo(loanId);
	}

	@Override
	public String cancelLoanAccountInfo(Integer loanId) {
		return socketDemo.cancelLoanAccountInfo(loanId);
	}

	@Override
	public Map<String, Object> selectLoanAccountInfo(Integer loanId) {
		return socketDemo.selectLoanAccountInfo(loanId);
	}

	@Override
	public String queryCusInfo(Integer loanId,Map map,SocketCodeTypeEnum socketCodeTypeEnum){
		return socketDemo.queryCusInfo(loanId,map,socketCodeTypeEnum);
	}

	public DataTable getPledgeDataTableByLoanId(String presType, Integer loanId, Integer mortgageOrPledge){
		return applyInfoDao.getPledgeDataTableByLoanId(presType, loanId, mortgageOrPledge);
	}

	public String relatedDataQuery(Integer loanId,Map<String,String> map,SocketCodeTypeEnum socketCodeTypeEnum){
		return socketDemo.relatedDataQuery(loanId,map,socketCodeTypeEnum);
	}

	@Override
	public void updateSurveyDataTableById( String loanId, Map<String, String> map) {
		applyInfoDao.updateSurveyDataTableById(loanId,map);
	}


}
