package banger.moduleImpl;

import banger.domain.product.*;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.moduleIntf.IProductModule;
import banger.service.intf.IIntentCustomerService;
import banger.service.intf.IProductFileService;
import banger.service.intf.IProductService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductModule implements IProductModule{

	@Autowired
	private IProductService productService;
	@Autowired
	private IProductFileService productFileService;
	@Autowired
	private IIntentCustomerService intentCustomerService;
	
	
	@Override
	public List<ProdProduct_Query> queryProductListData(Map<String,Object> condition,IPageSize page) {
		return productService.queryProductListApp(condition,page);
	}


	@Override
	public ProdProduct_Query getProductDetail(Integer productId) {
		return productService.getProductById(productId);
	}


	@Override
	public List<ProdProductFile> queryProductFileList(Map<String, Object> condition) {
		return productFileService.queryProductFileList(condition);
	}


	@Override
	public IPageList<ProdIntentCustomer_Query> queryIntentCustomerListData(Map<String, Object> condition, IPageSize page) {
		return intentCustomerService.queryIntentCustomerListApp(condition,page);
	}


	@Override
	public ProdIntentCustomer getIntentCustomerDetail(Integer picId) {
		return intentCustomerService.getIntentCustomerById(picId);
	}


	@Override
	public JSONObject insertIntentCustomer(ProdIntentCustomer intentCustomer,Integer loginUserId) {
		return intentCustomerService.insertIntentCustomer(intentCustomer, loginUserId);
	}


	@Override
	public ProdProductFile getProductFileById(Integer id) {
		return productFileService.getProductFileById(id);
	}


	public void updateProduct(ProdProduct_Query prodProduct,Integer loginUserId) {
		 productService.updateProductForApp(prodProduct,loginUserId);
	}

	@Override
	public List<ProdProduct> queryProductList(Map<String, Object> condition) {
		return productService.queryProductList(condition);
	}
}
