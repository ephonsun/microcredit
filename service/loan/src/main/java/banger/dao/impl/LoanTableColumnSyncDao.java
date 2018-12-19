package banger.dao.impl;

import banger.common.annotation.DataDaoAnnotation;
import banger.dao.intf.ILoanTableColumnSyncDao;
import banger.domain.config.AutoTableColumnSync;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.dao.EntityDao;
import banger.framework.sql.util.ISqlHelper;
import banger.framework.sql.util.SqlHelper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * @author zhusw
 *
 */
@Repository
public class LoanTableColumnSyncDao extends EntityDao<AutoTableColumnSync> implements ILoanTableColumnSyncDao {

	/**
	 * 得到需要同步的列
	 * @param actionName 同步类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AutoTableColumnSync> getAllAutoTableColumnSyncList(String actionName){
		return (List<AutoTableColumnSync>)this.queryEntities("getAllAutoTableColumnSyncList", actionName);
	}
	
	/**
	 * 通过主健获取主表数据
	 * @param loanId
	 * @return
	 */
	public DataTable getLoanInfoDataById(Integer loanId){
		String sqlString = "SELECT * FROM LOAN_APPLY_INFO WHERE LOAN_ID="+loanId;
		ISqlHelper ish = SqlHelper.instance();
		DataTable table = ish.getDataTable(sqlString);
		return table;
	}
	
	/**
	 * 通过贷款Id获取主表数据
	 * @param loanId
	 * @return
	 */
	public DataTable getTemplateDataById(String precType,String tableName,Integer loanId){
		String sqlString = "SELECT * FROM "+tableName+" WHERE LOAN_ID = "+loanId+" AND LOAN_PROCESS_TYPE = '"+precType+"'";
		ISqlHelper ish = SqlHelper.instance();
		DataTable table = ish.getDataTable(sqlString);
		ish.dispose();
		return table;
	}
	
	/**
	 * 通过主健获取主表数据
	 * @param tableName
	 * @return
	 */
	public DataTable getTemplateDataById(String tableName,Integer id){
		String sqlString = "SELECT * FROM "+tableName+" WHERE ID = "+id;
		ISqlHelper ish = SqlHelper.instance();
		DataTable table = ish.getDataTable(sqlString);
		ish.dispose();
		return table;
	}

	/**
	 * 得到预申请客户
	 * @param id
	 * @return
	 */
	public DataTable getMarketCustomerDataById(Integer id){
		String sqlString = "SELECT * FROM CUST_MARKET_CUSTOMER WHERE MARKET_CUSTOMER_ID = "+id;
		ISqlHelper ish = SqlHelper.instance();
		DataTable table = ish.getDataTable(sqlString);
		ish.dispose();
		return table;
	}

	/**
	 * 进件客户
	 * 得到预申请客户
	 * @param id
	 * @return
	 */
	public DataTable getIntoCustomerDataById(Integer id){
		String sqlString = "SELECT * FROM INTO_CUST_APPLY_INFO WHERE APPLY_ID = "+id;
		ISqlHelper ish = SqlHelper.instance();
		DataTable table = ish.getDataTable(sqlString);
		ish.dispose();
		return table;
	}
	
	/**
	 * 得到客户数据
	 * @param tableName
	 * @param customerId 客户Id
	 * @return
	 */
	public DataTable getCustomerDataById(String tableName,Integer customerId){
		String sqlString = "SELECT * FROM "+tableName+" WHERE ID = "+customerId;
		ISqlHelper ish = SqlHelper.instance();
		DataTable table = ish.getDataTable(sqlString);
		ish.dispose();
		return table;
	}
	
	/**
	 * 保存代款模板信息
	 * @param table
	 */
	@DataDaoAnnotation
	public Integer saveLoanTemplateInfo(DataTable table){
		DataRow row = table.getRow(0);
		Object val = row.get("ID");
		if(val instanceof Integer && ((Integer)val)>0){
			this.updateData(table,"ID");
			return ((Integer)val);
		}else{
			Integer id = this.getId(table.getName(),"ID").intValue();
			row.set("ID", id);
			this.insertData(table,"ID");
			return id;
		}
	}
	
}
