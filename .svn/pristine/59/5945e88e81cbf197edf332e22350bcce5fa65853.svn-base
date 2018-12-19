package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.component.dataimport.IImportResult;
import banger.framework.component.dataimport.context.ColumnInfo;
import banger.framework.component.progress.ProgressBar;
import banger.domain.customer.CustPotentialCustomerQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.customer.CustPotentialCustomers;

/**
 * 潜在客户表业务访问接口
 */
public interface IPotentialCustomersService {

	/**
	 * 新增潜在客户表
	 * @param potentialCustomers 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertPotentialCustomers(CustPotentialCustomers potentialCustomers, Integer loginUserId);

	/**
	 *修改潜在客户表
	 * @param potentialCustomers 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updatePotentialCustomers(CustPotentialCustomers potentialCustomers, Integer loginUserId);

	/**
	 *修改潜在客户表 意向时间可为null
	 * @param potentialCustomers 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updatePotentialCustomersByDate(CustPotentialCustomers potentialCustomers, Integer loginUserId);


	/**
	 * 通过主键删除潜在客户表

     * @param ID 主键Id
     */
	boolean deletePotentialCustomersById(Integer id);


	/**
	 * 通过主键得到潜在客户表
	 * @param id 主键Id
	 */
	CustPotentialCustomers getPotentialCustomersById(Integer id);

	/**
	 * 查询潜在客户表
	 * @param condition 查询条件
	 * @return
	 */
	List<CustPotentialCustomers> queryPotentialCustomersList(Map<String, Object> condition);

	/**
	 * 分页查询潜在客户表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<CustPotentialCustomerQuery> queryPotentialCustomersList(Map<String, Object> condition, IPageSize page);
	/**
	 *
	 *客户详细信息
	 */
    CustPotentialCustomerQuery getPotentialCustomersQueryById(Integer id);
	/**
	 * 检查是否唯一
	 * @param s
	 * @param customerName
	 * @param cardType
	 * @param cardNumber
	 * @return
	 */
	 boolean isUniqueCustomer(String s, String customerName, String cardType, String cardNumber);

	IImportResult getImportResultByUserId(String string);

	ProgressBar importExcel(String string, String saveFilename, List<ColumnInfo> columns, Map<String, String> map);

	Map<String,Object> getImportExcelHead(String saveFilename);

	void deletePotentialCustomersAll(Integer userId);
/**
 * 团队主管删除全部
 * @param **/
    void deletePotentialCustomersAllByGroupId(Integer groupId);
}
