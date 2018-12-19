package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.domain.config.AutoTableInfo;
import banger.framework.collection.DataTable;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * 自定义表信息业务访问接口
 */
public interface IAutoTableInfoService {

	/**
	 * 新增自定义表信息
	 * 
	 * @param tableInfo
	 *            实体对像
	 * @param loginUserId
	 *            登入用户Id
	 */
	void insertTableInfo(AutoTableInfo tableInfo, Integer loginUserId);

	/**
	 * 修改自定义表信息
	 * 
	 * @param tableInfo
	 *            实体对像
	 * @param loginUserId
	 *            登入用户Id
	 */
	void updateTableInfo(AutoTableInfo tableInfo, Integer loginUserId);

	/**
	 * 通过主键删除自定义表信息
	 *            主键Id
	 */
	void deleteTableInfoById(Integer tableId);

	/**
	 * 通过主键得到自定义表信息
	 * 
	 *            主键Id
	 */
	AutoTableInfo getTableInfoById(Integer tableId);

	/**
	 * 查询自定义表信息
	 * @param condition
	 *            查询条件
	 * @return
	 */
	List<AutoTableInfo> queryTableInfoList(Map<String, Object> condition);

	/**
	 * 分页查询自定义表信息
	 * 
	 * @param condition
	 *            查询条件
	 * @param page
	 *            分页对像
	 * @return
	 */
	IPageList<AutoTableInfo> queryTableInfoList(Map<String, Object> condition, IPageSize page);
	
	/**
	 * 根据贷款id 贷款阶段查询贷款自定义信息
	 * @param tableName
	 * @param loanId
	 * @param precType
	 * @return
	 */
	DataTable getTemplateDataById(String tableName,Integer loanId,String precType);

	/**
	 * 根据贷款id 贷款阶段查询贷款自定义信息
	 * @param tableName
	 * @param loanId
	 * @param precType
	 * @param extendCondition
	 * @return
	 */
	DataTable getTemplateDataById(String tableName, Integer loanId,
										 String precType,String extendCondition);

}
