package banger.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.domain.product.ProdIntentCustomer;
import banger.moduleIntf.IPotentialCustomersProvider;
import banger.service.intf.IIntentCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.product.ProdProduct;
import banger.domain.product.ProdProductFile;
import banger.domain.product.ProdProduct_Query;
import banger.framework.pagesize.IPageList;
import banger.service.intf.IProductFileService;
import banger.service.intf.IProductService;
import net.sf.json.JSONObject;

/**
 * 产品列表页面访问类
 */
@Controller
@RequestMapping("/prodProduct")	
public class ProductController extends BaseController {
	private static final long serialVersionUID = 2438917958458840475L;
	@Autowired
	private IProductService productService;
	@Autowired
	private IProductFileService productFileService;
	@Autowired
	private IIntentCustomerService iIntentCustomerService;
	@Autowired
	private IPotentialCustomersProvider potentialCustomersProvider;

	@Value("${file_root_path}")  
    private String fileRootPath; 

	/**
	 * 得到产品列表列表页面
	 * @return
	 */
	@RequestMapping("/getProductListPage")
	public String getProductListPage(){
		return "/product/vm/productList";
	}

	/**
	 * 查询产品列表列表数据
	 * @return
	 */
	@RequestMapping("/queryProductListData")
	@ResponseBody
	public void queryProductListData(){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("productIsdel", 0);
		condition.put("productCode", getParameter("productCode"));
		condition.put("productName", getParameter("productName"));
		condition.put("productType", getParameter("productType"));
		condition.put("beginTime", getParameter("beginTime"));
		condition.put("endTime", getParameter("endTime"));
		IPageList<ProdProduct_Query> productList = productService.queryProductList(condition,this.getPage());
		renderText(JsonUtil.toJson(productList, "productId","productCreateUser,productInfo,productCode,productName,productIsdel,productCreateDate,productUpdateUser,productUpdateDate,productType,productTypeName").toString());
	}

	/**
	 * 得到产品列表新增页面
	 * @return
			 */
	@RequestMapping("/getProductInsertPage")
	public String getProductInsertPage(){
		return "/product/vm/productSave";
	}

	/**
	 * 得到产品列表修改页面
	 * @return
	 */
	@RequestMapping("/getProductUpdatePage")
	public String getProductUpdatePage(){
		Integer productId = Integer.valueOf(getParameter("productId"));
		ProdProduct product = productService.getProductById(productId);
		List<ProdProductFile> imageFiles = productFileService.queryProductFileList(productId,0);
		List<ProdProductFile> videoFiles = productFileService.queryProductFileList(productId,1);
		setAttribute("product",product);
		setAttribute("imageFiles", imageFiles);
		setAttribute("videoFiles", videoFiles);
		return "/product/vm/productSave";
	}

	/**
	 * 得到产品列表查看页面
	 * @return
	 */
	@RequestMapping("/getProductDetailPage")
	public String getProductDetailPage(){
		Integer productId = Integer.valueOf(getParameter("productId"));
		ProdProduct product = productService.getProductById(productId);
		List<ProdProductFile> imageFiles = productFileService.queryProductFileList(productId,0);
		List<ProdProductFile> videoFiles = productFileService.queryProductFileList(productId,1);
		setAttribute("product",product);
		setAttribute("imageFiles", imageFiles);
		setAttribute("videoFiles", videoFiles);
		return "/product/vm/productDetail";
	}

	/**
	 * 新增产品列表数据
	 * @return
	 */
	@RequestMapping("/insertProduct")
	@ResponseBody
	public void insertProduct(ProdProduct product,String[]  fileIdImages,String[] fileIdVideos,String[]  fileSizeImages,String[] fileSizeVideos){
		try {
			JSONObject resJson = productService.checkAddOrUpdate(product);
			if(resJson.getBoolean("success")){
				Integer loginUserId = getLoginInfo().getUserId();
				product.setProductIsdel(0);
				productService.insertProduct(product,loginUserId);
				productFileService.updateProductFile(0, product.getProductId(), fileIdImages, fileSizeImages, loginUserId);
				productFileService.updateProductFile(0, product.getProductId(), fileIdVideos, fileSizeVideos, loginUserId);
				renderText(true, "新建成功！", null);
			}else {
				renderText(resJson.toString());
			}
			return;
		} catch (Exception e) {
			log.error("insertProduct error",e);
		}
		renderText(false, "新建失败！", null);
	}
	

	/**
	 * 修改产品列表数据
	 * @return
	 */
	@RequestMapping("/updateProduct")
	@ResponseBody
	public void updateProduct(ProdProduct product,String[]  fileIdImages,String[] fileIdVideos,String[]  fileSizeImages,String[] fileSizeVideos,String[] deleteFileIds){
		try {
			JSONObject resJson = productService.checkAddOrUpdate(product);
			if(resJson.getBoolean("success")){
				Integer loginUserId = getLoginInfo().getUserId();
				productService.updateProduct(product,loginUserId);
				//更新意向客户的产品信息
				Map<String,Object> condition = new HashMap<String, Object>();
				condition.put("picProductId",product.getProductId());
				List<ProdIntentCustomer> prodIntentCustomers = iIntentCustomerService.queryIntentCustomerList(condition);
				if (prodIntentCustomers != null && prodIntentCustomers.size() > 0) {
					for (int i = 0; i < prodIntentCustomers.size(); i++) {
						ProdIntentCustomer intentCustomer = prodIntentCustomers.get(i);
						intentCustomer.setPicProductName(product.getProductName());
						iIntentCustomerService.updateIntentCustomer(intentCustomer,loginUserId);
					}
				}
				productFileService.updateProductFile(0, product.getProductId(), fileIdImages, fileSizeImages, loginUserId);
				productFileService.updateProductFile(0, product.getProductId(), fileIdVideos, fileSizeVideos, loginUserId);
				productFileService.deleteProductFileById(deleteFileIds);
				renderText(true, "修改成功!", null);
			}else {
				renderText(resJson.toString());
			}
			return;
		} catch (Exception e) {
			log.error("updateProduct error",e);
		}
		renderText(false, "修改失败！", null);
	}

	/**
	 * 删除产品列表数据
	 * @return
	 */
	@RequestMapping("/deleteProduct")
	@ResponseBody
	public void deleteProduct(){
		try {
			String productId = getParameter("productId");
			ProdProduct_Query product = productService.getProductById(Integer.valueOf(productId));
			productService.deleteProductById(Integer.valueOf(productId));
			//同时需要删除存在该产品的潜在客户
			potentialCustomersProvider.updatePotentialCustomersByProductCode(product.getProductCode());
			renderText(true, "删除成功!", null);
			return ;
		} catch (Exception e) {
			log.error("deleteProduct error",e);
		}
		renderText(false,"删除失败！",null);
	}
	
	//校验产品码
	@RequestMapping("/checkProductCode")
	@ResponseBody
	public void checkProductCode(){
		String productCode = getParameter("productCode");
		String productId = getParameter("productId");
		JSONObject resJson = productService.checkProductCode(productCode,productId);
		renderText(resJson.toString());
	}
	
	//校验产品名称
	@RequestMapping("/checkProductName")
	@ResponseBody
	public void checkProductName(){
		String productName = getParameter("productName");
		String productId = getParameter("productId");
		JSONObject resJson = productService.checkProductName(productName,productId);
		renderText(resJson.toString());
	}

	/**
	 * 潜在客户选择产品,得到产品列表列表页面
	 * @return
	 */
	@RequestMapping("/getProductListPageForPotentialCust")
	public String getProductListPageForPotentialCust(){
		return "/product/vm/productListForCust";
	}
}
