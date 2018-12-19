package banger.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hundsun.common.lang.StringUtil;

import banger.dao.intf.IApplyInfoDao;
import banger.dao.intf.ILoanTableColumnSyncDao;
import banger.domain.config.AutoTableColumn;
import banger.domain.customer.Customer;
import banger.domain.enumerate.TableSyncEnum;
import banger.domain.loan.LoanApplyInfo;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.moduleIntf.IAutoFieldProvider;
import banger.moduleIntf.ICustomerProvider;
import banger.service.intf.ILoanSyncCustomerService;

@Repository
public class LoanSyncCustomerService implements ILoanSyncCustomerService {
	
	@Autowired
	private IApplyInfoDao applyInfoDao;
	
	@Resource
	private ICustomerProvider customerProvider;
	
	@Autowired
	private ILoanTableColumnSyncDao loanTableColumnSyncDao;
	
	@Resource
	private IAutoFieldProvider autoFieldProvider;
	
	/**
	 * 代款同步客户数据
	 */
	@Override
	public Integer syncLoanCustomerInfo(Integer loanId) {
		if(loanId!=null && loanId.intValue()>0){
			LoanApplyInfo applyInfo = applyInfoDao.getApplyInfoById(loanId);
			if(applyInfo!=null){
				if(applyInfo.getLoanCustomerId()!=null && applyInfo.getLoanCustomerId().intValue()>0){		//贷款的客户ID存在，贷款同步到客户
					syncLoanToCustomerById(loanId,applyInfo.getLoanProcessType(),applyInfo.getLoanCustomerId());
				}else{
					Integer customerId = null;
					if(StringUtil.isNotEmpty(applyInfo.getLoanName()) && StringUtil.isNotEmpty(applyInfo.getLoanIdentifyType()) && StringUtil.isNotEmpty(applyInfo.getLoanIdentifyNum())){
						customerId = customerProvider.getCustomerIdByCardNameType(applyInfo.getLoanName(), applyInfo.getLoanIdentifyType(), applyInfo.getLoanIdentifyNum());
					}
					if(customerId!=null && customerId.intValue()>0){
						syncLoanTelNumToCustomer(applyInfo.getLoanTelNum(),customerId);
						syncCustomerToLoanById(loanId,applyInfo.getLoanProcessType(),customerId);		//贷款的客户ID不存在，客户同步到贷款
					}else{
						customerId = createCustomerByLoanApply(applyInfo);
						if(customerId!=null && customerId.intValue()>0){
							syncLoanToCustomerById(loanId,applyInfo.getLoanProcessType(),customerId);
						}
					}
					applyInfoDao.updateCustomerIdById(customerId, loanId);
					return customerId;
				}
			}
		}
		return null;
	}
	
	/**
	 * 代款同步客户数据
	 */
	@Override
	public void syncLoanCustomerInfo(Integer loanId,String tableName) {
		if(loanId!=null && loanId.intValue()>0){
			LoanApplyInfo applyInfo = applyInfoDao.getApplyInfoById(loanId);
			if(applyInfo!=null && applyInfo.getLoanCustomerId()!=null){
				for(TableSyncEnum sync : TableSyncEnum.values()){
					if(sync.targetTableName.equals(tableName)){
						DataTable sourceTable = loanTableColumnSyncDao.getTemplateDataById(applyInfo.getLoanProcessType(), sync.targetTableName, loanId);
						DataTable targetTable = loanTableColumnSyncDao.getCustomerDataById(sync.sourceTableName, applyInfo.getLoanCustomerId());
						
						if(sourceTable!=null && sourceTable.rowSize()>0){
							DataRow sourceRow = sourceTable.getRow(0);
							List<AutoTableColumn> list = autoFieldProvider.getFieldListByTableName(sync.targetTableName);
							if(targetTable!=null && targetTable.rowSize()>0){
								targetTable.setName(sync.sourceTableName);
								DataRow targetRow = targetTable.getRow(0);
								if(sync.equals(TableSyncEnum.PERSONAL) && applyInfo.getLoanBelongId()!=null)
									targetRow.set("BELONG_USER_ID", applyInfo.getLoanBelongId());
								for(AutoTableColumn column : list){
									Object value = sourceRow.get(column.getFieldColumn());
									targetRow.set(column.getFieldColumn(), value);
								}
								customerProvider.updateCustomerDataByLoanApply(targetTable);
							}else{
								targetTable = new DataTable(sync.sourceTableName);
								for(AutoTableColumn column : list){
									targetTable.addColumn(column.getFieldColumn());
								}
								DataRow targetRow = targetTable.newRow();
								targetRow.set("ID", applyInfo.getLoanCustomerId());
								if(sync.equals(TableSyncEnum.PERSONAL) && applyInfo.getLoanBelongId()!=null)
									targetRow.set("BELONG_USER_ID", applyInfo.getLoanBelongId());
								for(AutoTableColumn column : list){
									Object value = sourceRow.get(column.getFieldColumn());
									targetRow.set(column.getFieldColumn(), value);
								}
								customerProvider.insertCustomerDataByLoanApply(targetTable);
							}
							
						}
					}
				}
			}
		}
	}
	
	/**
	 * 同步贷款信息到客户表
	 * @param loanId
	 * @param customerId
	 */
	private void syncCustomerToLoanById(Integer loanId,String processType,Integer customerId){
		for(TableSyncEnum sync : TableSyncEnum.values()){
			DataTable sourceTable = loanTableColumnSyncDao.getCustomerDataById(sync.sourceTableName, customerId);
			DataTable targetTable = loanTableColumnSyncDao.getTemplateDataById(processType, sync.targetTableName, loanId);
			if(sourceTable!=null && sourceTable.rowSize()>0){
				DataRow sourceRow = sourceTable.getRow(0);
				List<AutoTableColumn> list = autoFieldProvider.getFieldListByTableName(sync.targetTableName);
				if(targetTable!=null && targetTable.rowSize()>0){
					targetTable.setName(sync.targetTableName);
					DataRow targetRow = targetTable.getRow(0);
					for(AutoTableColumn column : list){
						Object value = sourceRow.get(column.getFieldColumn());
						targetRow.set(column.getFieldColumn(), value);
					}
				}else{
					targetTable = new DataTable(sync.targetTableName);
					targetTable.addColumn("LOAN_ID");
					targetTable.addColumn("LOAN_PROCESS_TYPE");
					for(AutoTableColumn column : list){
						targetTable.addColumn(column.getFieldColumn());
					}
					DataRow targetRow = targetTable.newRow();
					for(AutoTableColumn column : list){
						Object value = sourceRow.get(column.getFieldColumn());
						targetRow.set(column.getFieldColumn(), value);
					}
					targetRow.set("LOAN_ID", loanId);
					targetRow.set("LOAN_PROCESS_TYPE", processType);
				}
				loanTableColumnSyncDao.saveLoanTemplateInfo(targetTable);
			}
		}
	}

	/**
	 * 同步贷款申请信息的电话号码到客户的个人信息
	 * @param telNum
	 * @param customerId
	 */
	private void syncLoanTelNumToCustomer(String telNum,Integer customerId){
		if(StringUtil.isNotEmpty(telNum) && customerId!=null && customerId.intValue()>0) {
			customerProvider.upldateCustomerTelNumById(telNum, customerId);
		}
	}
	
	private Integer createCustomerByLoanApply(LoanApplyInfo applyInfo){
		String customerName = applyInfo.getLoanName();
		String identifyType = applyInfo.getLoanIdentifyType();
		String identifyNum = applyInfo.getLoanIdentifyNum();
		Integer userId = applyInfo.getCreateUser();
		Customer customer = new Customer();
		customer.setCustomerName(customerName);
		customer.setIdentifyType(identifyType);
		customer.setIdentifyNum(identifyNum);
		customer.setBelongUserId(userId);
		customer.setCreateUser(userId);
		customer.setUpdateUser(userId);
		customer.setCreateDate(new Date());
		customer.setUpdateDate(new Date());
		customer.setIsDel(0);
		customerProvider.insertCustomerByLoanApply(customer);
		return customer.getId();
	}
	
	private void syncLoanToCustomerById(Integer loanId,String processType,Integer customerId){
		for(TableSyncEnum sync : TableSyncEnum.values()){
			DataTable sourceTable = loanTableColumnSyncDao.getTemplateDataById(processType, sync.targetTableName, loanId);
			DataTable targetTable = loanTableColumnSyncDao.getCustomerDataById(sync.sourceTableName, customerId);
			if(sourceTable!=null && sourceTable.rowSize()>0){
				DataRow sourceRow = sourceTable.getRow(0);
				List<AutoTableColumn> list = autoFieldProvider.getFieldListByTableName(sync.targetTableName);
				if(targetTable!=null && targetTable.rowSize()>0){
					targetTable.setName(sync.sourceTableName);
					DataRow targetRow = targetTable.getRow(0);
					if(sync.equals(TableSyncEnum.PERSONAL)){
						Object loanBelongId = sourceRow.get("LOAN_BELONG_ID");
						if(loanBelongId!=null){
							targetRow.set("BELONG_USER_ID",loanBelongId);
						}
					}
					for(AutoTableColumn column : list){
						Object value = sourceRow.get(column.getFieldColumn());
						targetRow.set(column.getFieldColumn(), value);
					}
					customerProvider.updateCustomerDataByLoanApply(targetTable);
				}else{
					targetTable = new DataTable(sync.sourceTableName);
					for(AutoTableColumn column : list){
						targetTable.addColumn(column.getFieldColumn());
					}
					DataRow targetRow = targetTable.newRow();
					targetRow.set("ID", customerId);
					if(sync.equals(TableSyncEnum.PERSONAL)){
						Object loanBelongId = sourceRow.get("LOAN_BELONG_ID");
						if(loanBelongId!=null){
							targetRow.set("BELONG_USER_ID",loanBelongId);
						}
					}
					for(AutoTableColumn column : list){
						Object value = sourceRow.get(column.getFieldColumn());
						targetRow.set(column.getFieldColumn(), value);
					}
					customerProvider.insertCustomerDataByLoanApply(targetTable);
				}
			}
		}
	}

	@Override
	public void updateBelongId(Integer id, Integer belongId) {
		customerProvider.updateBelongId(id, belongId);
	}

}
