package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import net.sf.json.JSONObject;
import banger.domain.product.ProdProduct;
import banger.domain.product.ProdProduct_Query;

/**
 * 产品列表业务访问接口
 */
public interface IProductService {

	/**
	 * 新增产品列表
	 * @param product 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertProduct(ProdProduct product,Integer loginUserId);

	/**
	 *修改产品列表
	 * @param product 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateProduct(ProdProduct product,Integer loginUserId);
	void updateProductForApp(ProdProduct_Query prodProduct_query,Integer loinUserId);
	/**
	 * 通过主键删除产品列表
	 * @param PRODUCT_ID 主键Id
	 */
	void deleteProductById(Integer productId);

	/**
	 * 通过主键得到产品列表
	 * @param PRODUCT_ID 主键Id
	 */
	ProdProduct_Query getProductById(Integer productId);

	/**
	 * 查询产品列表
	 * @param condition 查询条件
	 * @return
	 */
	List<ProdProduct> queryProductList(Map<String,Object> condition);

	/**
	 * 分页查询产品列表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<ProdProduct_Query> queryProductList(Map<String,Object> condition,IPageSize page);
	
	List<ProdProduct_Query> queryProductListApp(Map<String,Object> condition,IPageSize page);
	
	JSONObject checkProductCode(String productCode,String productId);
	
	JSONObject checkProductName(String productName,String productId);
	
	JSONObject checkAddOrUpdate(ProdProduct product);
	
	//JSONObject canDelProduct(String productId);

}
