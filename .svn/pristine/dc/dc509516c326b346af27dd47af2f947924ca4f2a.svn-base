package banger.moduleIntf;

import banger.domain.product.*;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

public interface IProductModule {
	
	/**
	 * 查询产品列表
	 */
	List<ProdProduct_Query> queryProductListData(Map<String,Object> condition,IPageSize page);
	
	/**
	 * 查询产品详情
	 */
	ProdProduct_Query getProductDetail(Integer productId);

	void updateProduct(ProdProduct_Query prodProduct,Integer loginUserId);
	
	/**
	 * 查询产品文件列表
	 */
	List<ProdProductFile> queryProductFileList(Map<String,Object> condition);
	
	/**
	 * 查询意向客户列表
	 */
	IPageList<ProdIntentCustomer_Query> queryIntentCustomerListData(Map<String,Object> condition,IPageSize page);
	
	/**
	 * 查询意向客户详情
	 */
	ProdIntentCustomer getIntentCustomerDetail(Integer picId);
	
	/**
	 * 插入意向客户
	 */
	JSONObject insertIntentCustomer(ProdIntentCustomer intentCustomer,Integer loginUserId);
	
	/**
	 * 查询文件
	 */
	ProdProductFile getProductFileById(Integer id);

	/**
	 * 查询产品列表
	 * @param condition 查询条件
	 * @return
	 */
	List<ProdProduct> queryProductList(Map<String,Object> condition);
}
