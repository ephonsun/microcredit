package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.product.ProdIntentCustomer;
import banger.domain.product.ProdIntentCustomer_Query;
import banger.domain.product.ProdProduct;
import banger.domain.system.SysDataDictCol;
import banger.framework.pagesize.IPageList;
import banger.moduleIntf.IDataDictColProvider;
import banger.service.intf.IIntentCustomerService;
import banger.service.intf.IProductService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 意向客户列表页面访问类
 */
@Controller
@RequestMapping("/prodIntentCustomer")
public class IntentCustomerController extends BaseController {
	private static final long serialVersionUID = 8159598537256923061L;
	@Autowired
	private IIntentCustomerService intentCustomerService;
	@Autowired
	private IProductService productService;
	@Autowired
	private IDataDictColProvider dataDictColProvider;

	/**
	 * 得到意向客户列表列表页面
	 * @return
	 */
	@RequestMapping("/getIntentCustomerListPage")
	public String getIntentCustomerListPage(String productId){
		Integer userId =  this.getLoginInfo().getUserId();
		JSONObject prodJson = new JSONObject();
		if(StringUtils.isNotBlank(productId)){
			ProdProduct product = productService.getProductById(Integer.valueOf(productId));
			setAttribute("product", product);
			prodJson = JSONObject.fromObject(product);
		}
		if(isCustomerManager()){
			setAttribute("myIntentCustomer", 1);
		}
		setAttribute("userId", userId);
		setAttribute("prodJson", prodJson.toString());
		return "/product/vm/intentCustomerList";
	}

	/**
	 * 查询意向客户列表列表数据
	 * @return
	 */
	@RequestMapping("/queryIntentCustomerListData")
	@ResponseBody
	public void queryIntentCustomerListData(String myIntentCustomer){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("customerInfo", getParameter("customerInfo"));
		String picProductId = getParameter("picProductId");
		if(StringUtils.isNotBlank(picProductId))
			condition.put("picProductId", picProductId);
		condition.put("picProductName", getParameter("picProductName"));
		condition.put("picRemark", getParameter("picRemark"));
		condition.put("picCreateUserName", getParameter("picCreateUserName"));
		condition.put("picIsdel",0);
		//判断是否查询自己创建得
		if(Boolean.parseBoolean(myIntentCustomer)){
			condition.put("picCreateUser", this.getLoginInfo().getUserId());
			condition.put("picIsdel",0);
			IPageList<ProdIntentCustomer_Query> intentCustomerList = intentCustomerService.queryIntentCustomerList(condition,this.getPage());
			renderText(JsonUtil.toJson(intentCustomerList, "picId","picAddress,picCertificateNum,picUpdateUser,picPhone,picUpdateDate,picCertificateType,picName,picCreateDate,picGender,picRemark,picCreateUser,picProductId,picProductName,picGenderName,picCertificateTypeName,picCreateUserName,picPhoneX,picCertificateNumX").toString());
    	}else{
			condition.put("picCreateUser", this.getLoginInfo().getUserId());
			condition.put("picIsdel",0);
			//查询用户的数据权限
			String groupIds = this.getLoginInfo().getUserGroupPermit();
			String teamId = this.getLoginInfo().getTeamGroupId()!=null?this.getLoginInfo().getTeamGroupId().toString():"";
			if(StringUtils.isNotBlank(groupIds)){
				if(!groupIds.contains(teamId) && StringUtils.isNotBlank(teamId))
					groupIds = groupIds+","+teamId;
				condition.put("groupIds", groupIds);
			}else if(StringUtils.isNotBlank(teamId)){
				condition.put("groupIds", teamId);
			}else{
				condition.put("picCreateUser", this.getLoginInfo().getUserId());
			}
			IPageList<ProdIntentCustomer_Query> intentCustomerList = intentCustomerService.queryIntentCustomerListAll(condition,this.getPage());
			renderText(JsonUtil.toJson(intentCustomerList, "picId","picAddress,picCertificateNum,picUpdateUser,picPhone,picUpdateDate,picCertificateType,picName,picCreateDate,picGender,picRemark,picCreateUser,picProductId,picProductName,picGenderName,picCertificateTypeName,picCreateUserName,picPhoneX,picCertificateNumX").toString());
    	}

	}

	/**
	 * 得到意向客户列表新增页面
	 * @return
	 */
	@RequestMapping("/getIntentCustomerInsertPage")
	public String getIntentCustomerInsertPage(String productId){
		if(StringUtils.isNotBlank(productId)){
			ProdProduct product = productService.getProductById(Integer.valueOf(productId));
			setAttribute("product", product);
		}
		return "/product/vm/intentCustomerSave";
	}

	/**
	 * 新增意向客户列表数据
	 * @return
	 */
	@RequestMapping("/insertIntentCustomer")
	@ResponseBody
	public void insertIntentCustomer(ProdIntentCustomer intentCustomer){
		try {
			Integer loginUserId = getLoginInfo().getUserId();
			JSONObject resObj = intentCustomerService.insertIntentCustomer(intentCustomer,loginUserId);
			renderText(resObj.toString());
			return ;
		} catch (Exception e) {
			log.error("insertIntentCustomer error",e);
		}
		renderText(false,"新建失败！",null);
	}

	/**
	 * 删除意向客户列表数据
	 * @return
	 */
	@RequestMapping("/deleteIntentCustomer")
	@ResponseBody
	public void deleteIntentCustomer(){
		try {
			String picId = getParameter("picId");
			intentCustomerService.deleteIntentCustomerById(Integer.parseInt(picId));
			renderText(true,"删除成功！",null);
			return ;
		} catch (Exception e) {
			log.error("deleteIntentCustomer error",e);
		}
		renderText(false,"删除失败！",null);
	}
	
	
	
	
	
	
	/**
	 * 得到意向客户列表查看页面
	 * @return
	 */
	@RequestMapping("/getIntentCustomerDetailPage")
	public String getIntentCustomerDetailPage(){
		String picId = getParameter("picId");
		ProdIntentCustomer intentCustomer = intentCustomerService.getIntentCustomerById(Integer.parseInt(picId));
		setAttribute("intentCustomer",intentCustomer);
		return "/product/vm/intentCustomerDetail";
	}
	
	/**
	 * 得到意向客户列表修改页面
	 * @return
	 */
	@RequestMapping("/getIntentCustomerUpdatePage")
	public String getIntentCustomerUpdatePage(){
		String picId = getParameter("picId");
		ProdIntentCustomer intentCustomer = intentCustomerService.getIntentCustomerById(Integer.parseInt(picId));
		//证件类型
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("dataDictName","CD_IDENTIFY_TYPE");
		List<SysDataDictCol> sysDataDictCols = dataDictColProvider.queryDataDictColList(condition);
		setAttribute("intentCustomer",intentCustomer);
		setAttribute("sysDataDictCols",sysDataDictCols);
		return "/product/vm/intentCustomerUpdate";
	}
	
	/**
	 * 修改意向客户列表数据
	 * @return
	 */
	@RequestMapping("/updateIntentCustomer")
	@ResponseBody
	public void updateIntentCustomer(ProdIntentCustomer intentCustomer){
		Integer loginUserId = getLoginInfo().getUserId();
		intentCustomerService.updateIntentCustomer(intentCustomer,loginUserId);
		renderText(true,"保存成功！",null);
	}

}
