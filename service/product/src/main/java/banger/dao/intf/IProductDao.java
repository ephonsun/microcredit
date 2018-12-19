package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.product.ProdProduct;
import banger.domain.product.ProdProduct_Query;

/**
 * 产品列表数据访问接口
 */
public interface IProductDao {

	/**
	 * 新增产品列表
	 * @param product 实体对像
	 */
	void insertProduct(ProdProduct product);

	/**
	 *修改产品列表
	 * @param product 实体对像
	 */
	void updateProduct(ProdProduct product);
	void updateProductForApp(ProdProduct_Query prodProduct_query);
	/**
	 * 通过主键删除产品列表
	 * @param productId 主键Id
	 */
	void deleteProductById(Integer productId);

	/**
	 * 通过主键得到产品列表
	 * @param productId 主键Id
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
	
	Integer queryProductCount(Map<String, Object> condition) ;

}
