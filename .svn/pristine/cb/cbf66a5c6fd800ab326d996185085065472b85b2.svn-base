package banger.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import banger.dao.intf.IIntentCustomerDao;
import banger.dao.intf.IProductDao;
import banger.domain.product.ProdProduct;
import banger.domain.product.ProdProduct_Query;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.service.intf.IProductService;
import net.sf.json.JSONObject;

/**
 * 产品列表业务访问类
 */
@Service
public class ProductService implements IProductService {

	@Resource
	private IProductDao productDao;
	@Resource
	private IIntentCustomerDao intentCustomerDao;

	/**
	 * 新增产品列表
	 * @param product 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertProduct(ProdProduct product,Integer loginUserId){
		product.setProductCreateUser(loginUserId);
		product.setProductCreateDate(DateUtil.getCurrentDate());
		product.setProductUpdateUser(loginUserId);
		product.setProductUpdateDate(DateUtil.getCurrentDate());
		this.productDao.insertProduct(product);
	}

	/**
	 *修改产品列表
	 * @param product 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateProduct(ProdProduct product,Integer loginUserId){
		product.setProductUpdateUser(loginUserId);
		product.setProductUpdateDate(DateUtil.getCurrentDate());
		this.productDao.updateProduct(product);
	}

	@Override
	public void updateProductForApp(ProdProduct_Query prodProduct_query, Integer loinUserId) {
		prodProduct_query.setProductUpdateUser(loinUserId);
		prodProduct_query.setProductUpdateDate(DateUtil.getCurrentDate());
		this.productDao.updateProductForApp(prodProduct_query);
	}

	/**
	 * 通过主键删除产品列表
	 * @param PRODUCT_ID 主键Id
	 */
	public void deleteProductById(Integer productId){
		intentCustomerDao.deleteIntentCustomerByProdId(productId);
		productDao.deleteProductById(productId);
	}

	/**
	 * 通过主键得到产品列表
	 * @param PRODUCT_ID 主键Id
	 */
	public ProdProduct_Query getProductById(Integer productId){
		return this.productDao.getProductById(productId);
	}

	/**
	 * 查询产品列表
	 * @param condition 查询条件
	 * @return
	 */
	public List<ProdProduct> queryProductList(Map<String,Object> condition){
		return this.productDao.queryProductList(condition);
	}

	/**
	 * 分页查询产品列表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<ProdProduct_Query> queryProductList(Map<String,Object> condition,IPageSize page){
		return this.productDao.queryProductList(condition,page);
	}
	
	public List<ProdProduct_Query> queryProductListApp(Map<String,Object> condition,IPageSize page){
		return this.productDao.queryProductListApp(condition,page);
	}

	@Override
	public JSONObject checkProductCode(String productCode,String productId) {
		JSONObject resJson = new JSONObject();
		boolean success = false;
		String cause = "";
		if(productCode != null){
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("productIsdel", 0);
			condition.put("productCode", productCode);
			if(StringUtils.isNotBlank(productId))
				condition.put("productId", productId);
			Integer count = this.productDao.queryProductCount(condition);
			if(count.intValue() > 0)
				cause = "已存在相同产品编码，请重新输入";
			else 
				success = true;
		}else {
			cause = "产品代码必须填写";
		}
		resJson.put("success", success);
		resJson.put("cause", cause);
		return resJson;
	}

	@Override
	public JSONObject checkProductName(String productName,String productId) {
		JSONObject resJson = new JSONObject();
		boolean success = false;
		String cause = "";
		if(productName != null){
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("productIsdel", 0);
			condition.put("productName", productName);
			if(StringUtils.isNotBlank(productId))
				condition.put("productId", productId);
			Integer count = this.productDao.queryProductCount(condition);
			if(count.intValue() > 0)
				cause = "已存在相同产品名称，请重新输入";
			else 
				success = true;
		}else {
			cause = "产品名称必须填写";
		}
		resJson.put("success", success);
		resJson.put("cause", cause);
		return resJson;
	}

	@Override
	public JSONObject checkAddOrUpdate(ProdProduct product) {
		JSONObject resJson = new JSONObject();
		boolean success = false;
		String cause = "";
		if(StringUtils.isNotBlank(product.getProductType())){
			JSONObject resCode = checkProductCode(product.getProductCode(),product.getProductId()+"");
			if(resCode.getBoolean("success")){
				JSONObject resName = checkProductName(product.getProductName(),product.getProductId()+"");
				if(resName.getBoolean("success")){
					success = true;
				}else {
					return resName;
				}
			}else {
				return resCode;
			}
		}else {
			cause = "请选择产品类型";
		}
		resJson.put("success", success);
		resJson.put("cause", cause);
		return resJson;
	}

	/*@Override
	public JSONObject canDelProduct(String productId) {
		JSONObject resJson = new JSONObject();
		boolean success = false;
		String cause = "";
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("picIsdel", 0);
		condition.put("picProductId", Integer.valueOf(productId));
		Integer count = this.intentCustomerDao.queryIntentCustomerCount(condition);
		if(count.intValue() > 0)
			cause = "该产品存在意向客户无法删除";
		else 
			success = true;
		resJson.put("success", success);
		resJson.put("cause", cause);
		return resJson;
	}*/

}
