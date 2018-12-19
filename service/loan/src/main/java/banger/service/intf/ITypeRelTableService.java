package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanTypeRelTable;
import banger.domain.loan.LoanTypeRelTableExtend;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

/**
 * 贷款类型匹配表单业务访问接口
 */
public interface ITypeRelTableService {

	/**
	 * 新增贷款类型匹配表单
	 * 
	 * @param typeRelTable
	 *            实体对像
	 * @param loginUserId
	 *            登入用户Id
	 */
	void insertTypeRelTable(LoanTypeRelTable typeRelTable, Integer loginUserId);

	/**
	 * 修改贷款类型匹配表单
	 * 
	 * @param typeRelTable
	 *            实体对像
	 * @param loginUserId
	 *            登入用户Id
	 */
	void updateTypeRelTable(LoanTypeRelTable typeRelTable, Integer loginUserId);

	/**
	 * 通过主键删除贷款类型匹配表单
	 * 
	 * @param ID
	 *            主键Id
	 */
	void deleteTypeRelTableById(Integer id);

	/**
	 * 通过主键得到贷款类型匹配表单
	 * 
	 * @param ID
	 *            主键Id
	 */
	LoanTypeRelTable getTypeRelTableById(Integer id);

	/**
	 * 查询贷款类型匹配表单
	 * 
	 * @param condition
	 *            查询条件
	 * @return
	 */
	List<LoanTypeRelTable> queryTypeRelTableList(Map<String, Object> condition);

	/**
	 * 分页查询贷款类型匹配表单
	 * 
	 * @param condition
	 *            查询条件
	 * @param page
	 *            分页对像
	 * @return
	 */
	IPageList<LoanTypeRelTable> queryTypeRelTableList(Map<String, Object> condition, IPageSize page);

	List<LoanTypeRelTable> queryAutoTableInfoByCondition(Map<String, Object> condition);

	/**
	 * 查询最大排序号
	 * 
	 * @param typeId
	 * @param precType
	 * @return
	 */
	Integer queryLoanTypeRelTableMaxOrderNum(Integer typeId, String precType);

	/**
	 * 排序 待做
	 * 
	 * @param type
	 * @param tableId
	 * @param tableType
	 */
	void moveTableInfoOrderNo(String type, Integer typeId, String precType, Integer id);

	/**
	 * 
	 * @param typeID
	 *            贷款类型id
	 * @param precType
	 *            表单名
	 * @return
	 */
	List<LoanTypeRelTableExtend> queryAutoTableInfoByCondition(Integer typeId, String precType);

}
