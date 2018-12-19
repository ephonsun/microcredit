package banger.service.impl;

import banger.dao.intf.IApplyInfoDao;
import banger.dao.intf.ILoanTableColumnSyncDao;
import banger.dao.intf.ILoanTemplateExtendDao;
import banger.domain.config.AutoTableColumnSync;
import banger.domain.enumerate.DataSyncType;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanTemplateExtend;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.reader.Reader;
import banger.service.intf.ILoanInfoSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 贷款信息同频接口
 * @author zhusw
 *
 */
@Repository
public class LoanInfoSyncService implements ILoanInfoSyncService {
	
	@Autowired
	ILoanTableColumnSyncDao loanTableColumnSyncDao;
	
	@Autowired
	IApplyInfoDao applyInfoDao;

	@Autowired
	ILoanTemplateExtendDao loanTemplateExtendDao;

	public void syncLoanToBiz(Integer loanId){
		syncLoanToBiz(loanId,-1);
	}

	/**
	 * 进件客户同步主表信息到子表
	 * @param loanId
	 */
	public void  syncLoanToBiz(Integer loanId,Integer applyId){
		DataTable sourceTable = loanTableColumnSyncDao.getLoanInfoDataById(loanId);
		if(sourceTable!=null && sourceTable.rowSize()>0){
			DataRow sourceRow = sourceTable.getRow(0);
			String processType = Reader.stringReader().getValue(sourceRow,"LOAN_PROCESS_TYPE");
			List<AutoTableColumnSync> list = loanTableColumnSyncDao.getAllAutoTableColumnSyncList(DataSyncType.LOAN_TO_BIZ.type);
			Map<String,List<AutoTableColumnSync>> tableMap = new HashMap<String,List<AutoTableColumnSync>>();
			for(AutoTableColumnSync column : list){
				if(!tableMap.containsKey(column.getTargetTable()))
					tableMap.put(column.getTargetTable(), new ArrayList<AutoTableColumnSync>());
				tableMap.get(column.getTargetTable()).add(column);
			}
			Map<String,Integer> idMap = new HashMap<String, Integer>();
			for(String tableName : tableMap.keySet()){
				List<AutoTableColumnSync> columns = tableMap.get(tableName);
				if(columns.size()>0){
					DataTable table = new DataTable(tableName);
					for(AutoTableColumnSync column : columns){
						table.addColumn(column.getTargetTableColumn());
					}
					DataRow targetRow = table.newRow();
					for(AutoTableColumnSync column : columns){
						targetRow.set(column.getTargetTableColumn(), sourceRow.get(column.getSourceTableColumn()));
					}
					DataTable targetTable = loanTableColumnSyncDao.getTemplateDataById(processType, tableName, loanId);
					if(targetTable!=null && targetTable.rowSize()>0){
						targetRow.set("ID", targetTable.getRow(0).get("ID"));
					}else{
						targetRow.set("LOAN_PROCESS_TYPE",sourceRow.get("LOAN_PROCESS_TYPE"));
						targetRow.set("LOAN_ID",sourceRow.get("LOAN_ID"));
					}
					Integer id = loanTableColumnSyncDao.saveLoanTemplateInfo(table);
					idMap.put(tableName,id);
				}
			}
			if(applyId.intValue()>0){
				this.syncIntoToLoan(applyId,idMap);
			}
		}
	}


	/**
	 * 进件
	 * 预申请同步到贷款
	 * @param applyId
	 * @param idMap
	 */
	private void syncIntoToLoan(Integer applyId,Map<String,Integer> idMap) {
		DataTable sourceTable = loanTableColumnSyncDao.getIntoCustomerDataById(applyId);
		if (sourceTable != null && sourceTable.rowSize() > 0) {
			DataRow sourceRow = sourceTable.getRow(0);
			List<AutoTableColumnSync> list = loanTableColumnSyncDao.getAllAutoTableColumnSyncList(DataSyncType.MARKET_TO_LOAN.type);
			Map<String, List<AutoTableColumnSync>> tableMap = new HashMap<String, List<AutoTableColumnSync>>();
			for (AutoTableColumnSync column : list) {
				if (!tableMap.containsKey(column.getTargetTable()))
					tableMap.put(column.getTargetTable(), new ArrayList<AutoTableColumnSync>());
				tableMap.get(column.getTargetTable()).add(column);
			}

			for (String tableName : tableMap.keySet()) {
				if (idMap.containsKey(tableName)) {
					Integer id = idMap.get(tableName);
					List<AutoTableColumnSync> columns = tableMap.get(tableName);
					if (columns.size() > 0) {
						DataTable table = new DataTable(tableName);
						for (AutoTableColumnSync column : columns) {
							table.addColumn(column.getTargetTableColumn());
						}

						DataRow targetRow = table.newRow();
						targetRow.set("ID", id);
						for (AutoTableColumnSync column : columns) {
							targetRow.set(column.getTargetTableColumn(), sourceRow.get(column.getSourceTableColumn()));
						}

						loanTableColumnSyncDao.saveLoanTemplateInfo(table);
					}
				}
			}
		}
	}


	/**
	 * 同步子表信息到贷款主表
	 * @param id
	 * @param tableName
	 * @param processType
	 */
	public void syncBizToLoan(Integer id,String tableName,String processType){
		List<AutoTableColumnSync> list = loanTableColumnSyncDao.getAllAutoTableColumnSyncList(DataSyncType.BIZ_TO_LOAN.type);
		List<AutoTableColumnSync> columns = new ArrayList<AutoTableColumnSync>();
		
		for(AutoTableColumnSync column : list){
			if(tableName.equals(column.getSourceTable())){
				columns.add(column);
			}
		}
		
		if(columns.size()>0){
			DataTable sourceTable = loanTableColumnSyncDao.getTemplateDataById(tableName, id);
			if(sourceTable!=null && sourceTable.rowSize()>0){
				DataRow sourceRow = sourceTable.getRow(0);
				Object loanId = sourceRow.get("LOAN_ID");
				if(loanId!=null){
					DataTable table = new DataTable("LOAN_APPLY_INFO");
					for(AutoTableColumnSync column : columns){
						table.addColumn(column.getTargetTableColumn());
					}
					DataRow targetRow = table.newRow();
					targetRow.set("LOAN_ID", loanId);
					for(AutoTableColumnSync column : columns){
						targetRow.set(column.getTargetTableColumn(), sourceRow.get(column.getSourceTableColumn()));
					}
					
					applyInfoDao.updateApplyInfo(table);
				}
			}
		}
	}

	/**
	 * 贷款申请信息同步到贷款调查信息
	 * @param loanId
	 */
	public void syncApplyToInvestigate(Integer loanId){
		LoanApplyInfo loanInfo = applyInfoDao.getApplyInfoById(loanId);
		if(loanInfo!=null) {
			List<LoanTemplateExtend> applyList = loanTemplateExtendDao.getLoanTemplateList(loanInfo.getLoanTypeId(), LoanProcessTypeEnum.APPLY.type);
			List<LoanTemplateExtend> invertList = loanTemplateExtendDao.getLoanTemplateList(loanInfo.getLoanTypeId(), LoanProcessTypeEnum.INVESTIGATE.type);
			if(applyList!=null && invertList!=null){
				for(LoanTemplateExtend invertTemp : invertList){
					for(LoanTemplateExtend applyTemp : applyList){
						if(invertTemp.getTableDbName().equals(applyTemp.getTableDbName())){
							syncApplyToInvestigate(loanId,invertTemp.getTableDbName());
						}
					}
				}
			}
		}
	}

	/**
	 * 贷款申请信息同步到贷款调查信息
	 * @param loanIds
	 */
	public void syncApplyToInvestigate(Integer[] loanIds){
		if(loanIds!=null && loanIds.length>0){
			for(int i=0;i<loanIds.length;i++){
				syncApplyToInvestigate(loanIds[i]);
			}
		}
	}

	private void syncApplyToInvestigate(Integer loanId,String tableName){
		DataTable applyTable = loanTemplateExtendDao.getTemplateDataById(LoanProcessTypeEnum.APPLY.type,tableName,loanId);
		applyTable.setName(tableName);
		if(applyTable!=null && applyTable.rowSize()>0) {
			DataTable invertTable = loanTemplateExtendDao.getTemplateDataById(LoanProcessTypeEnum.INVESTIGATE.type, tableName, loanId);
			if(invertTable!=null && invertTable.rowSize()>0){			//有数据不复制
				//do nothing
			}else{
				for(int i=0;i<applyTable.rowSize();i++){
					DataRow row = applyTable.getRow(i);
					row.set("ID",null);
					row.set("LOAN_PROCESS_TYPE",LoanProcessTypeEnum.INVESTIGATE.type);
				}
				loanTemplateExtendDao.saveLoanTemplateInfo(applyTable,false);
			}
		}
	}
	
}
