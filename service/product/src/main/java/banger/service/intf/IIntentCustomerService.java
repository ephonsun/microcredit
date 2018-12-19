package banger.service.intf;

import banger.domain.product.ProdIntentCustomer;
import banger.domain.product.ProdIntentCustomer_Query;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 意向客户列表业务访问接口
 */
public interface IIntentCustomerService {

	/**
	 * 新增意向客户列表
	 * @param intentCustomer 实体对像
	 * @param loginUserId 登入用户Id
	 */
	JSONObject insertIntentCustomer(ProdIntentCustomer intentCustomer,Integer loginUserId);

	/**
	 *修改意向客户列表
	 * @param intentCustomer 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateIntentCustomer(ProdIntentCustomer intentCustomer,Integer loginUserId);

	/**
	 * 通过主键删除意向客户列表
	 * @param PIC_ID 主键Id
	 */
	void deleteIntentCustomerById(Integer picId);

	/**
	 * 通过主键得到意向客户列表
	 * @param PIC_ID 主键Id
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

    IPageList<ProdIntentCustomer_Query> queryIntentCustomerListAll(Map<String, Object> condition, IPageSize page);
}
