package banger.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.common.annotation.DataDaoAnnotation;
import banger.framework.util.TypeUtil;
import org.springframework.stereotype.Repository;

import banger.dao.intf.ILoanTemplateExtendDao;
import banger.domain.loan.LoanTemplateExtend;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.dao.ObjectDao;
import banger.framework.sql.util.ISqlHelper;
import banger.framework.sql.util.SqlHelper;

@Repository
public class LoanTemplateExtendDao extends ObjectDao implements ILoanTemplateExtendDao {

	/**
	 * 获取贷款模板
	 * @param loanTypeId
	 * @param precType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanTemplateExtend> getLoanTemplateList(Integer loanTypeId,String precType){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("loanTypeId", loanTypeId);
		condition.put("precType", precType);
		List<LoanTemplateExtend> list = (List<LoanTemplateExtend>)this.queryList("getLoanTemplateList", condition);
		return list;
	}

	/**
	 * 获取贷款模板
	 * @param loanTypeId
	 * @param precTypes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanTemplateExtend> getLoanTemplateList(Integer loanTypeId,String[] precTypes){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("loanTypeId", loanTypeId);
		condition.put("precTypes", precTypes);
		List<LoanTemplateExtend> list = (List<LoanTemplateExtend>)this.queryList("getLoanTemplateList", condition);
		return list;
	}
	
	/**
	 * 获取贷款模板
	 * @param loanTypeId
	 * @param precType
	 * @return
	 */
	public LoanTemplateExtend getLoanTemplateById(Integer loanTypeId,String precType,Integer tableId){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("loanTypeId", loanTypeId);
		condition.put("precType", precType);
		condition.put("tableId", tableId);
		LoanTemplateExtend temp = (LoanTemplateExtend)this.queryObject("getLoanTemplateList", condition);
		return temp;
	}

	/**
	 * 获取贷款模板
	 * @param loanTypeId
	 * @param precTypes
	 * @return
	 */
	public LoanTemplateExtend getLoanTemplateById(Integer loanTypeId,String[] precTypes,Integer tableId){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("loanTypeId", loanTypeId);
		condition.put("precTypes", precTypes);
		condition.put("tableId", tableId);
		LoanTemplateExtend temp = (LoanTemplateExtend)this.queryObject("getLoanTemplateList", condition);
		return temp;
	}

	
	/**
	 * 保存代款模板信息
	 * @param table
	 */
	@DataDaoAnnotation
	public Integer[] saveLoanTemplateInfo(DataTable table,Boolean updateFlag){
		Integer[] ids = new Integer[table.rowSize()];
		for(int i=0;i<table.rowSize();i++){
			DataRow row = table.getRow(i);
			Object val = row.get("ID");
			if(val instanceof Integer && ((Integer)val)>0){
				ids[i] = (Integer)val;
			}else{
				Integer id = this.newId(table.getName(),"ID").intValue();
				row.set("ID", id);
				ids[i] = id;
			}
		}
		if(updateFlag){
			this.updateData(table,"ID");
		}else{
			this.insertData(table,"ID");
		}
		return ids;
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
			Integer id = (Integer)val;
			if(isExistTableId(table.getName(),id)) {
				this.updateData(table, "ID");
			}else{
				this.insertData(table,"ID");
			}
			return (Integer)val;
		}else{
			Integer id = this.newId(table.getName(),"ID").intValue();
			row.set("ID", id);
			this.insertData(table,"ID");
			return id;
		}
	}

	/**
	 * 判断ID在数据库里是否存在
	 * @param tableName
	 * @param id
	 * @return
	 */
	private boolean isExistTableId(String tableName,Integer id){
		String sqlString = "SELECT COUNT(*) FROM "+tableName+" WHERE ID = "+id;
		ISqlHelper ish = SqlHelper.instance();
		Object val = ish.executeScalar(sqlString);
		Integer count = (Integer) TypeUtil.changeType(val,Integer.class);
		ish.dispose();
		return count!=null && count.intValue()>0;
	}
	
	/**
	 * 得到贷款模板数据
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
	 * 得到贷款模板数据
	 * @return
	 */
	public DataTable getTemplateDataById(String precType,String tableName,Integer loanId,String extendCondtion){
		String sqlString = "SELECT * FROM "+tableName+" WHERE LOAN_ID = "+loanId+" AND LOAN_PROCESS_TYPE = '"+precType+"' " + extendCondtion;
		ISqlHelper ish = SqlHelper.instance();
		DataTable table = ish.getDataTable(sqlString);
		ish.dispose();
		return table;
	}

	/**
	 * 得到贷款模板数据
	 * @param tableName
	 * @param id
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
	 * 删除贷款申请所有模板数据
	 * @param tableName
	 * @param id
	 * @return
	 */
	public void deleteLoanTemplateDataById(String tableName,Integer id){
		String sqlString = "DELETE FROM "+tableName+" WHERE ID = "+id;
		ISqlHelper ish = SqlHelper.instance();
		ish.executeNoneQuery(sqlString);
		ish.dispose();
	}

	/**
	 * 得到贷款申请所有模板数据
	 * @param tableName
	 * @param loanId
	 * @return
	 */
	public DataTable getLoanTemplateDataByLoanId(String tableName,Integer loanId){
		String sqlString = "SELECT * FROM "+tableName+" WHERE ID = "+loanId;
		ISqlHelper ish = SqlHelper.instance();
		DataTable table = ish.getDataTable(sqlString);
		ish.dispose();
		return table;
	}

	/**
	 * 产生表的主键
	 * @param tableName
	 * @param pk
	 * @return
	 */
	public Integer newTableId(String tableName,String pk){
		Integer id = this.newId(tableName,pk).intValue();
		return id;
	}
	
}
