package banger.dao.intf;

import banger.domain.product.ProdIntentCustomer;
import banger.domain.product.ProdIntentCustomer_Query;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * 意向客户列表数据访问接口
 */
public interface IIntentCustomerDao {

	/**
	 * 新增意向客户列表
	 * @param intentCustomer 实体对像
	 */
	void insertIntentCustomer(ProdIntentCustomer intentCustomer);

	/**
	 *修改意向客户列表
	 * @param intentCustomer 实体对像
	 */
	void updateIntentCustomer(ProdIntentCustomer intentCustomer);

	/**
	 * 通过主键删除意向客户列表
	 * @param picId 主键Id
	 */
	void deleteIntentCustomerById(Integer picId);
	
	void deleteIntentCustomerByProdId(Integer prodId);

	/**
	 * 通过主键得到意向客户列表
	 * @param picId 主键Id
	 */
	ProdIntentCustomer getIntentCustomerById(Integer picId);

	/**
	 * 查询意向客户列表
	 * @param condition 查询条件
	 * @return
	 */
	List<ProdIntentCustomer> queryIntentCustomerList(Map<String,Object> condition);

	/**
	 * 分页查询意向客户列表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<ProdIntentCustomer_Query> queryIntentCustomerList(Map<String,Object> condition,IPageSize page);

	IPageList<ProdIntentCustomer_Query> queryIntentCustomerListApp(Map<String,Object> condition,IPageSize page);
	
	Integer queryIntentCustomerCount(Map<String, Object> condition) ;

    IPageList<ProdIntentCustomer_Query> queryIntentCustomerListAll(Map<String, Object> condition, IPageSize page);
}
