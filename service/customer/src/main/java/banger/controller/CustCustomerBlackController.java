package banger.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.customer.CustCustomerBlack;
import banger.domain.customer.CustCustomerBlackQuery;
import banger.framework.pagesize.IPageList;
import banger.service.intf.IBasicInfoService;
import banger.service.intf.ICustCustomerBlackService;

/**
 * 客户黑名单表页面访问类
 */
@Controller
@RequestMapping("/customerBlack")
public class CustCustomerBlackController extends BaseController {
	private static final long serialVersionUID = 7241776572237124933L;
	@Autowired
	private ICustCustomerBlackService custCustomerBlackService;
	@Autowired
	private IBasicInfoService basicInfoService;

	private String BASE_PATH = "/customer/vm/customerBlack/";

	/** 
     * form表单提交 Date类型数据绑定 
     * <功能详细描述> 
     * @param binder 
     * @see [类、类#方法、类#成员] 
     */  
	@InitBinder    
	public void initBinder(WebDataBinder binder) {    
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");    
        dateFormat.setLenient(false);    
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));    
	}
	
	/**
	 * 得到客户黑名单表列表页面
	 * @return
	 */
	@RequestMapping("/getCustCustomerBlackListPage")
	public String getCustCustomerBlackListPage(){
		Integer approveStatus=Integer.valueOf(this.getRequest().getParameter("approveStatus"));
		if(approveStatus==1){
			return BASE_PATH + "customerBlackApproveList";
		}else{
			Integer loginUserId = getLoginInfo().getUserId();
			this.setAttribute("createUser", loginUserId);
			return BASE_PATH + "customerBlackList";
		}		
	}

	/**
	 * 查询客户黑名单表列表数据
	 * @return
	 */
	@RequestMapping("/queryCustomerBlackApproveListData")
	@ResponseBody
	public void queryCustCustomerBlackListData(CustCustomerBlackQuery customerBlackQuery){
		Map<String,Object> condition = new HashMap<String,Object>();
		
		condition.put("customerName", customerBlackQuery.getCustomerName());
		condition.put("userName", customerBlackQuery.getUserName());
		condition.put("startDate", customerBlackQuery.getStartDate());
		condition.put("endDate", customerBlackQuery.getEndDate());
		condition.put("approveStatus", customerBlackQuery.getApproveStatus());
		IPageList<CustCustomerBlackQuery> custCustomerBlackList = custCustomerBlackService.queryCustCustomerBlackList(condition,this.getPage());
		renderText(JsonUtil.toJson(custCustomerBlackList, "customerBlackId","customerId,customerName,cardType,cardTypeCN,cardNo,userName,createDate,createUser","yyyy-MM-dd").toString());
	}

	/**
	 * 得到客户黑名单表新增页面
	 * @return
	 */
	@RequestMapping("/getCustCustomerBlackInsertPage")
	public String getCustCustomerBlackInsertPage(){
		CustCustomerBlack custCustomerBlack = new CustCustomerBlack();
		setAttribute("custCustomerBlack",custCustomerBlack);
		return SUCCESS;
	}


	/**
	 * 得到客户黑名单表查看页面
	 * @return
	 */
	@RequestMapping("/getCustCustomerBlackDetailPage")
	public String getCustCustomerBlackDetailPage(){
		String customerBlackId = getParameter("customerBlackId");
		CustCustomerBlack custCustomerBlack = custCustomerBlackService.getCustCustomerBlackById(Integer.parseInt(customerBlackId));
		setAttribute("custCustomerBlack",custCustomerBlack);
		return SUCCESS;
	}


	/**
	 * 修改客户黑名单表数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/approve")
	public void updateCustCustomerBlack(){
		Integer loginUserId = getLoginInfo().getUserId();
		try {
			Integer customerBlackId=Integer.valueOf(getParameter("customerBlackId"));
			CustCustomerBlack custCustomerBlack=new CustCustomerBlack();
			custCustomerBlack.setCustomerBlackId(customerBlackId);
			custCustomerBlack.setApproveStatus(2);
			custCustomerBlackService.updateCustCustomerBlack(custCustomerBlack,loginUserId);
			renderText(true, "审批黑名单成功！", null);
			return;
		} catch (Exception e) {
			log.error("审批黑名单报错", e);
		}
		renderText(false, "审批黑名单失败！", null);
		return;
	}

	/**
	 * 删除客户黑名单表数据
	 * @return
	 */
	@RequestMapping("/deleteCustomerBlack")
	public void deleteCustCustomerBlack(){
		try {
			String customerBlackId = getParameter("customerBlackId");
			custCustomerBlackService.deleteCustCustomerBlackById(Integer.parseInt(customerBlackId));
			renderText(true, "恢复白名单成功！", null);
			return;
		} catch (Exception e) {
			log.error("恢复白名单报错", e);
		}
		renderText(false, "恢复白名单失败！", null);
	}
	
	@RequestMapping("/findCustomer")
	@ResponseBody
	public void findCustomer(){
		String customerName=this.getRequest().getParameter("customerName");
		String cardType=this.getRequest().getParameter("cardType");
		String cardNo=this.getRequest().getParameter("cardNo");
		Integer id=0;
		try {
			if(StringUtils.isNotBlank(cardType)&&StringUtils.isNotBlank(cardNo)){
				id = basicInfoService.getCustomerIdByCardNameType(customerName, cardType, cardNo);
				if(id==null){
					id=0;
				}
			}
		} catch (Exception e) {
			log.error("查询黑名单客户详情出错", e);
		}
		
		renderText(String.valueOf(id));
	}

}
