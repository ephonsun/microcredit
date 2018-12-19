package banger.dao.intf;

import java.util.List;

import banger.domain.config.AutoTableColumnSync;
import banger.framework.collection.DataTable;

public interface ILoanTableColumnSyncDao {

	/**
	 * 得到需要同步的列
	 * @param actionName 同步类型
	 * @return
	 */
	List<AutoTableColumnSync> getAllAutoTableColumnSyncList(String actionName);
	
	/**
	 * 通过主健获取主表数据
	 * @param loanId
	 * @return
	 */
	DataTable getLoanInfoDataById(Integer loanId);
	
	/**
	 * 通过主健获取主表数据
	 * @param loanId
	 * @return
	 */
	DataTable getTemplateDataById(String tableName,Integer id);
	
	/**
	 * 通过主健获取主表数据
	 * @param loanId
	 * @return
	 */
	DataTable getTemplateDataById(String precType,String tableName,Integer loanId);
	
	/**
	 * 得到客户数据
	 * @param tableName
	 * @param customerId 客户Id
	 * @return
	 */
	DataTable getCustomerDataById(String tableName,Integer customerId);
	
	/**
	 * 保存代款模板信息
	 * @param table
	 */
	Integer saveLoanTemplateInfo(DataTable table);

	/**
	 * 得到预申请客户
	 * @param id
	 * @return
	 */
	DataTable getMarketCustomerDataById(Integer id);

	/**
	 * 进件用户
	 * 得到预申请客户
	 * @param id
	 * @return
	 */
	DataTable getIntoCustomerDataById(Integer id);
	
}
