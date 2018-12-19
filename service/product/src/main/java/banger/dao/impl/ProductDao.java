package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IProductDao;
import banger.domain.product.ProdProduct;
import banger.domain.product.ProdProduct_Query;

/**
 * 产品列表数据访问类
 */
@Repository
public class ProductDao extends PageSizeDao<ProdProduct> implements IProductDao {

	/**
	 * 新增产品列表
	 * @param product 实体对像
	 */
	public void insertProduct(ProdProduct product){
		product.setProductId(this.newId().intValue());
		this.execute("insertProduct",product);
	}

	/**
	 *修改产品列表
	 * @param product 实体对像
	 */
	public void updateProduct(ProdProduct product){
		this.execute("updateProduct",product);
	}

	@Override
	public void updateProductForApp(ProdProduct_Query prodProduct_query) {
		this.execute("updateProduct",prodProduct_query);
	}

	/**
	 * 通过主键删除产品列表
	 * @param productId 主键Id
	 */
	public void deleteProductById(Integer productId){
		this.execute("deleteProductById",productId);
	}

	/**
	 * 通过主键得到产品列表
	 * @param productId 主键Id
	 */
	public ProdProduct_Query getProductById(Integer productId){
		return (ProdProduct_Query)this.queryEntity("getProductById",productId);
	}

	/**
	 * 查询产品列表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProdProduct> queryProductList(Map<String,Object> condition){
		return (List<ProdProduct>)this.queryEntities("queryProductList", condition);
	}
	
	/**
	 * 分页查询产品列表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<ProdProduct_Query> queryProductList(Map<String,Object> condition,IPageSize page){
		return (IPageList<ProdProduct_Query>)this.queryEntities("queryProductList", page, condition);
	}
	
	@SuppressWarnings("unchecked")
	public List<ProdProduct_Query> queryProductListApp(Map<String,Object> condition,IPageSize page){
		return (List<ProdProduct_Query>)this.queryPageEntities("queryProductListForApp", page, condition);
	}

	@Override
	public Integer queryProductCount(Map<String, Object> condition) {
		return this.queryInt("queryProductCount",condition);
	}

}
