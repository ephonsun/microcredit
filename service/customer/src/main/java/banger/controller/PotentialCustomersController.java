package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.common.tools.StringUtil;
import banger.domain.customer.CustPotentialCustomerQuery;
import banger.domain.customer.CustPotentialCustomers;
import banger.domain.permission.PmsUser;
import banger.domain.permission.SysTeamMember_Query;
import banger.domain.product.ProdProduct;
import banger.domain.product.ProdProduct_Query;
import banger.domain.system.SysBasicConfig;
import banger.domain.system.SysImportHistory;
import banger.framework.component.dataimport.IImportResult;
import banger.framework.component.dataimport.context.ColumnInfo;
import banger.framework.component.progress.IProgressBarManager;
import banger.framework.component.progress.ProgressBar;
import banger.framework.pagesize.IPageList;
import banger.framework.sql.command.SqlTransaction;
import banger.framework.util.FileUtil;
import banger.framework.util.OperationUtil;
import banger.moduleIntf.*;
import banger.service.intf.IPotentialCustomersService;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 潜在客户表页面访问类
 */
@Controller
@RequestMapping("/custPotentialCustomers")
public class PotentialCustomersController extends BaseController {
	private static final long serialVersionUID = 7691721734798062682L;
	@Autowired
	private IPotentialCustomersService potentialCustomersService;
	@Resource
	private IProgressBarManager progressBarManager;
	@Resource
	IImportHistoryProvider importHistoryProvider;
	@Resource
	IMarketingTaskProvider marketingTaskProvider;
	@Autowired
	private ITeamGroupProvider iTeamGroupProvider;
	@Autowired
	private IPermissionModule permissionModule;
	@Autowired
	private IProductModule productModule;

	@Autowired
	private IBasicConfigProvider basicConfigProvider;

	private String BASE_PATH_CUSTOMER = "/customer/vm/potentialCustomer/";
	private String BASE_PATH_INTENTPROD = "/customer/vm/intentProd/";
	/**
	 * 得到潜在客户表列表页面
	 *
	 * @return
	 */
	@RequestMapping("/getPotentialCustomersListPage")
	public String getPotentialCustomersListPage() {
		if(isCustomerManager())
		{
			setAttribute("selectMy", "1");
		}
		setAttribute("userId",this.getLoginInfo().getUserId());
		setAttribute("userTeamIdss",getLoginInfo().getTeamGroupId());
		return BASE_PATH_CUSTOMER + "potentialCustomerListPage";
	}

	/**
	 * 查询潜在客户表列表数据
	 *。。。。。。。。。。。。。。。。。。。。。。。
	 * @return
	 */
	@RequestMapping("/queryPotentialCustomersListData")
	@ResponseBody
	public void queryPotentialCustomersListData(@RequestParam(value ="userName" ,defaultValue = "")String userName,
												@RequestParam(value ="attributionManagerName" ,defaultValue = "")String attributionManagerName,
												@RequestParam(value ="selectMy" ,defaultValue = "2")String selectMy,
												@RequestParam(value ="loanIntention" ,defaultValue = "")String loanIntention,
												@RequestParam(value ="marketSuccess" ,defaultValue = "")String marketSuccess,
												@RequestParam(value ="isRemark" ,defaultValue = "")String isRemark,
												@RequestParam(value ="createDate" ,defaultValue = "")String createDate,
												@RequestParam(value ="createDateEnd" ,defaultValue = "")String createDateEnd,
												@RequestParam(value ="intentionDate" ,defaultValue = "")String intentionDate,
												@RequestParam(value ="intentionDateEnd" ,defaultValue = "")String intentionDateEnd
//												String createDate, String createDateEnd, String intentionDate, String intentionDateEnd
	) {
		//接收筛选条件
		//首先判断当前登录用户是不是客户经理
		//没什么用 都能新增
		String roleId = this.getLoginInfo().getRoleIds();
//		if (Integer.parseInt(roleId) == 4)
//		{
//			String selectMy=1+"";
//			setAttribute("selectMy",selectMy);
//		}
		//在域中增加userID 团队ID
		setAttribute("userId",getLoginInfo().getUserId());
		setAttribute("userTeamIdss",getLoginInfo().getTeamGroupId());

		Map<String, Object> condition = new HashMap<String, Object>();
		String[]  roleids=roleId.split(",");

		if(Integer.parseInt(selectMy)==2)
		{
			//如果等于2 说明是第一次进入页面  判断是否为客户经理
			if(isCustomerManager())
			{
				selectMy="1";
			}else {
				selectMy="0";
			}
		}

		//我的
		if(Integer.parseInt(selectMy)==1) {
			condition.put("isMy", this.getLoginInfo().getUserId());
			//condition.put("attributionTeam", this.getLoginInfo().getTeamGroupId());//修改为 本团队的都展示 不是本团队的 也展示

		}else {
			if(this.getLoginInfo().getTeamGroupId()!=null){
				if (this.getLoginInfo().getTeamGroupId()!=0){
					List<PmsUser> list =iTeamGroupProvider.getTeamGroupUserListByUserId(this.getLoginInfo().getUserId());
					String ids="" ;
					for (int i=0;i<list.size();i++) {
						if(i==0) {
							ids+=list.get(i).getUserId();
						}else {
							ids+=","+list.get(i).getUserId();
						}

					}
					//把userids 转化为数组
					condition.put("attributionManager",ids.split("\\,"));
//					ids+=","+0;
					condition.put("attributionManagers",ids.split("\\,"));
				}
			}else{
				String teamGroupIdByRole = this.getLoginInfo().getTeamGroupIdByRole(false);
				if (!StringUtil.isNotEmpty(teamGroupIdByRole)) {
					teamGroupIdByRole = "-1";
				}
				condition.put("attributionManager",teamGroupIdByRole.split("\\,"));
				condition.put("attributionManagers",teamGroupIdByRole.split("\\,"));
			}
			condition.put("userId", this.getLoginInfo().getUserId());
			condition.put("roleIds", this.getLoginInfo().getRoleIds());
		}
		//判断筛选条件
		if(StringUtils.isNotBlank(userName))
		{
			condition.put("userName",userName);
		}
		if(StringUtils.isNotBlank(attributionManagerName)) //归属人模糊查询
		{
			condition.put("attributionManagerName",attributionManagerName);
		}
		if(StringUtils.isNotBlank(loanIntention))
		{
			condition.put("loanIntention",loanIntention);
		}
		//时间填入
		if(StringUtils.isNotBlank(createDate))
		{
			condition.put("createDate",createDate);
		}
		if(StringUtils.isNotBlank(createDateEnd))
		{
			condition.put("createDateEnd",createDateEnd);
		}
		if(StringUtils.isNotBlank(intentionDate))
		{
			condition.put("intentionDate",intentionDate);
		}
		if(StringUtils.isNotBlank(intentionDateEnd))
		{
			condition.put("intentionDateEnd",intentionDateEnd);
		}
		//有无备注
		if(StringUtils.isNotBlank(isRemark))
		{
			if(Integer.parseInt(isRemark)==0)
			{
				condition.put("remark","=''");
			}else {
				condition.put("remark","<>''");
			}
		}
		//营销成功
		if(StringUtils.isNotBlank(marketSuccess))
		{
			if(Integer.parseInt(marketSuccess)==0)
			{
				condition.put("marketingSuccess",0);
			}else {
				condition.put("marketingSuccess",1);
			}
		}
		String productId = getParameter("productId");
		if (StringUtil.isNotEmpty(productId)) {
			ProdProduct_Query productDetail = productModule.getProductDetail(Integer.valueOf(productId));
			condition.put("productCode", productDetail.getProductCode());
		}
		IPageList<CustPotentialCustomerQuery> potentialCustomersList = potentialCustomersService.queryPotentialCustomersList(condition, this.getPage());
		renderText(JsonUtil.toJson(potentialCustomersList, "id", "attributionTeam,loanIntentionName,customerName,telephoneNumberX,cardNumberX,cardType,createDate,identifyTypeName,intentionDate,belongUserName,attributionManager,marketingSuccess,productName","yyyy-MM-dd HH:mm").toString());
	}

	/**
	 *  检查用户名是否重复0
	 * @return
	 */
	@RequestMapping("/checkCustomerIsRepeatByNameAndPhone")
	public void checkCustomerIsRepeatByNameAndPhone(){
		String customerName = this.getParameter("customerName");
		String telephoneNumber = this.getParameter("telephoneNumber");
		String id = this.getParameter("id");
		Map<String, Object> map = new HashedMap();
		map.put("customerName", customerName);
		map.put("telephoneNumber", telephoneNumber);
		List<CustPotentialCustomers> customerses = potentialCustomersService.queryPotentialCustomersList(map);
		if (StringUtil.isNotEmpty(id)) {
			//编辑校验
			if (customerses != null && customerses.size() > 1) {
				renderText(false, "customerRepeat", "已存在相同客户，请重新输入");
			} else if (customerses != null && customerses.size() == 1) {
				CustPotentialCustomers customersById = customerses.get(0);
				if (customersById.getId().toString().equals(id)) {
					renderText(true, "customerNoRepeat", "");
					return;
				}else{
					renderText(false, "customerRepeat", "已存在相同客户，请重新输入");
					return;
				}
			}else{
				renderText(true, "customerNoRepeat", "");
				return;
			}

		}else{
			//新增校验
			if (customerses != null && customerses.size() > 0) {
				renderText(false, "customerRepeat", "已存在相同客户，请重新输入");
				return;
			}else{
				renderText(true, "customerNoRepeat", "");
				return;
			}
		}

	}

	/**
	 * @Author: yangdw
	 * @param
	 * @Description: 跳转到分配潜在客户页面
	 * @Date: 17:01 2017/11/23
	 */
	@RequestMapping("/allotPotentialaCustPage")
	public String allotPotentialaCustPage() {
		String ids = this.getParameter("ids");
		//根据权限查询所有团队的客户经理
		String teamGroupId=getLoginInfo().getTeamGroupIdByRole(false);
		List<SysTeamMember_Query> members=permissionModule.getMangerByGroupId(teamGroupId);

		setAttribute("teamMembers",members);
		setAttribute("custIds",ids);
		return BASE_PATH_CUSTOMER + "potentialAllotPage";
	}

	/**
	 * @Author: yangdw
	 * @param
	 * @Description: 潜在客户分配保存
	 * @Date: 17:01 2017/11/23
	 */
	@RequestMapping("/saveAllotPotentialaCust")
	public void saveAllotPotentialaCust() {
		String userId = this.getParameter("userId");
		String custIds = this.getParameter("custIds");
		if(StringUtil.isNullOrEmpty(userId) || custIds.length() == 0){
			log.info("分配客户userId：" + userId + ",潜在客户id集合custIds：" + custIds);
			renderText(false, "参数有误！", null);
			return;
		}
		Integer groupId = permissionModule.getGroupIdByUserId(Integer.valueOf(userId));
		String[] custIdArray = custIds.split(",");
		CustPotentialCustomers potential = new CustPotentialCustomers();
		potential.setAttributionManager(Integer.valueOf(userId));
		potential.setAttributionTeam(groupId);
		for (int i = 0; i < custIdArray.length; i++) {
			potential.setId(Integer.valueOf(custIdArray[i]));
			potentialCustomersService.updatePotentialCustomers(potential,this.getLoginInfo().getUserId());
		}
		renderText(true, "分配成功！", null);
	}

	/**

	 * 得到潜在客户表新增页面
	 *
	 * @return
	 */

	@RequestMapping("/getPotentialCustomersTabs")
	public String getPotentialCustomersTabs(@RequestParam(value = "id", defaultValue = "") String id,
											@RequestParam(value = "isShow", defaultValue = "") String isShow,
											@RequestParam(value = "productCode", defaultValue = "") String productCode) {
		if(StringUtils.isNotBlank(productCode)){
			try {
				productCode = URLDecoder.decode(this.getRequest().getParameter("productCode"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		setAttribute("id",id);
		setAttribute("isShow",isShow);
		setAttribute("productCode",productCode);
		return BASE_PATH_CUSTOMER + "potentialCustomerTabs";
	}
	/**
	 * 得到潜在客户表新增页面2
	 *。。。。。。。。。。。
	 * @return
	 */
	@RequestMapping("/getPotentialCustomersInsertPage")
	public String getPotentialCustomersInsertPage(String id,String productCode) {
		if(StringUtil.isNotEmpty(productCode)){
			try {
				productCode = URLDecoder.decode(this.getRequest().getParameter("productCode"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		CustPotentialCustomerQuery custPotentialCustomerQuery=null;
		if (StringUtils.isNotBlank(id))
		{
			if (Integer.parseInt(id)!=0)
			{
				//如果id不为0 说明是编辑 不是新增
				custPotentialCustomerQuery=potentialCustomersService.getPotentialCustomersQueryById(Integer.parseInt(id));
				productCode = custPotentialCustomerQuery.getProductCode();
				if (custPotentialCustomerQuery.getIntentionDate()!=null)
				{
					String intentionDate = new SimpleDateFormat("yyyy-MM-dd").format(custPotentialCustomerQuery.getIntentionDate());
					setAttribute("intentionDate",intentionDate);
				}

			}
		}
		//处理新增需求,意向客户合并到潜在客户
		if (StringUtil.isNotEmpty(productCode)) {
			//意向客户合并到潜在客户,意向产品代码是productCode
			if (StringUtil.isNotEmpty(productCode)) {
				Map<String, Object> condition = new HashedMap();
				condition.put("productCode", productCode);
				List<ProdProduct> prodProducts = productModule.queryProductList(condition);
				if(CollectionUtils.isNotEmpty(prodProducts)){
					setAttribute("productCode",productCode);
					setAttribute("productName",prodProducts.get(0).getProductName());
				}
			}
		}
		setAttribute("id",id);
		setAttribute("custPotentialCustomerQuery",custPotentialCustomerQuery);
		setAttribute("belongName",this.getLoginInfo().getUserName());
		return BASE_PATH_CUSTOMER + "potentialCustomerInsert";

	}

	/**
	 * 得到潜在客户表修改页面
	 *
	 * @return
	 */
	@RequestMapping("/getPotentialCustomersUpdatePage")
	public String getPotentialCustomersUpdatePage() {
		String id = getParameter("id");
		CustPotentialCustomers potentialCustomers = potentialCustomersService.getPotentialCustomersById(Integer.parseInt(id));
		setAttribute("potentialCustomers", potentialCustomers);
		return BASE_PATH_CUSTOMER + "potentialCustomerInsert";
	}

	/**
	 * 得到潜在客户表查看页面
	 *..................................................................................................................
	 * @return
	 */
	@RequestMapping(value = "/getPotentialCustomersDetailPage")
	public String getPotentialCustomersDetailPage() {
		String id = getParameter("id");
		String flag = getParameter("flag");
		//判断 如果是客户经理就允许转申请，不是 就不允许转申请
		String userRoles=getLoginInfo().getRoleIds();
		String[] roles_user = userRoles.split(",");
		flag=0+"";
//		for (int i=0;roles_user.length>i;i++)
//		{
//			if(Integer.parseInt(roles_user[i])==4)
//			{
//				flag=1+"";
//			}
//		}



		String fl;
		String mks;

		CustPotentialCustomerQuery potentialCustomers = potentialCustomersService.getPotentialCustomersQueryById(Integer.parseInt(id));

		//2017-12-07确认需要归属人是自己的或者归属本团队的
		if (isCustomerManager() && (potentialCustomers.getAttributionManager().equals(this.getLoginInfo().getUserId()) || (this.getLoginInfo().getTeamGroupId().equals(potentialCustomers.getAttributionTeam()) && potentialCustomers.getAttributionManager() == 0))) {
			flag = "1";
		}

			DateFormat df1 = DateFormat.getDateInstance();
			DateFormat df2 = DateFormat.getDateTimeInstance();

			if ("2".equals(potentialCustomers.getCustomerSex())) {
				potentialCustomers.setCustomerSex("女");
			} else if ("1".equals(potentialCustomers.getCustomerSex())) {
				potentialCustomers.setCustomerSex("男");
			} else {
				potentialCustomers.setCustomerSex("");
			}
			if (0 == potentialCustomers.getLoanIntention()) {
				fl = "无";
			} else if (1 == potentialCustomers.getLoanIntention()) {
				fl = "有";
			} else {
				fl = " ";
			}
			if (0 == potentialCustomers.getMarketingSuccess()) {
				mks = "否";
			} else {
				mks = "是";
			}
			if (potentialCustomers.getIntentionDate() != null)
				setAttribute("df1", df1.format(potentialCustomers.getIntentionDate()));
			setAttribute("df2", df2.format(potentialCustomers.getCreateDate()));
			;
			setAttribute("mks", mks);
			setAttribute("fl", fl);
			//客户移交所需数据
			potentialCustomers.setRemark(StringUtil.blackDisplayForDetail(potentialCustomers.getRemark()));
			setAttribute("custPotentialCustomerQuery", potentialCustomers);
			setAttribute("id", id);
			setAttribute("flag", flag);
			return BASE_PATH_CUSTOMER + "potentialCustomerInfo";
		}

	/**
	 * 新增潜在客户表数据
	 *
	 * @return
	 */
	@RequestMapping("/insertPotentialCustomers")
	public String insertPotentialCustomers(@ModelAttribute CustPotentialCustomers potentialCustomers) {
		Integer loginUserId = getLoginInfo().getUserId();
		potentialCustomersService.insertPotentialCustomers(potentialCustomers, loginUserId);
		renderText(SUCCESS);
		return null;
	}

	/**
	 * 新增潜在客户表数据
	 *。。。。。。。。。。。
	 * @return
	 */
	@RequestMapping("/updatePotentialCustomers")
	@ResponseBody
	public void updatePotentialCustomers(String json,@RequestParam(value ="id" ,defaultValue ="0") String id) throws ParseException {
		Integer loginUserId = getLoginInfo().getUserId();
		CustPotentialCustomers c = (CustPotentialCustomers) JSONObject.toBean(JSONObject.fromObject(json),CustPotentialCustomers.class);
		JSONObject j =JSONObject.fromObject(json);
		/*if(!createCustomer(c)){
			renderText(false,"已存在相同客户，请重新输入",null);
			return ;
		}*/
		if (Integer.parseInt(id)!=0) {

			c.setMarketingSuccess(0);
			if (!StringUtils.isNotBlank(j.getString("productCode"))) {
				c.setLoanIntention(0);
			}else{
				c.setLoanIntention(1);
			}

			if(c.getCustomerSex()==null)
			{
				c.setCustomerSex("2");
			}
			if(!StringUtils.isNotBlank(j.getString("intentionDate")))
			{
				c.setIntentionDate(null);
				potentialCustomersService.updatePotentialCustomersByDate(c, loginUserId);
			}else {
				SimpleDateFormat ss=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
				Date date=ss.parse(j.getString("intentionDate"));
				c.setIntentionDate(date);
				potentialCustomersService.updatePotentialCustomers(c, loginUserId);
			}
			renderText(true, "修改成功", String.valueOf(0));
		} else {
			/*if(!createCustomer(c)){
				renderText(false,"已存在相同客户，请重新输入",null);
				return ;
			}*/
			c.setCreateDate(new Date());
			c.setUpdateDate(new Date());
			c.setUpdateUser(loginUserId);
			c.setCreateUser(loginUserId);
			c.setAttributionManager(loginUserId);
			//团队id,2017-12-21跨团队查询不能显示另一个团队
			c.setAttributionTeam(getLoginInfo().getTeamGroupId());
//			c.setAttributionTeam(0);
			c.setMarketingSuccess(0);
			if (!StringUtils.isNotBlank(j.getString("productCode"))) {
				c.setLoanIntention(0);
			}else{
				c.setLoanIntention(1);
			}
			if(!StringUtils.isNotBlank(j.getString("intentionDate")))
			{
				c.setIntentionDate(null);
			}
			//只有客户经理新建的潜在客户才能记入营销任务
			if(isCustomerManager())
				//记录类型,0 导入记录,1新建记录
				c.setRecordType(1);
			else
				c.setRecordType(0);

			c.setCreateUserTeam(this.getLoginInfo().getTeamGroupId());
			potentialCustomersService.insertPotentialCustomers(c, loginUserId);
			//更新营销任务的数量
			if(isCustomerManager())
				marketingTaskProvider.updateMarketingTaskAmount(c.getId(),this.getLoginInfo().getTeamGroupId());
			renderText(true, "保存成功", String.valueOf(0));
		}
	}

	/**
	 * 根据三要素判断是否可以新建潜在客户 true可以新建 false 不可以新建
	 * 已确认采用之前的姓名和联系方式做去重校验
	 * @param cust
	 * @return
	 */
	private boolean createCustomer(CustPotentialCustomers cust){
		//开关是否关闭，2 校验，1 不校验
		SysBasicConfig sysBasicConfig = basicConfigProvider.getSysBasicConfigByKey("qzkhdr");
		if(sysBasicConfig != null && StringUtils.equals(sysBasicConfig.getConfigStatus(),"2")){
			String id = "";
			if(cust.getId() != null){
				id = cust.getId().toString();
			}
			String custName = cust.getCustomerName();
			String cardType = cust.getCardType();
			String cardNumber = cust.getCardNumber();
			//根据三要素去数据库捞数据做校验
			Map<String,Object> condition = new HashMap<String,Object>();
			List<CustPotentialCustomers> lists = potentialCustomersService.queryPotentialCustomersList(condition);
			for(CustPotentialCustomers customer : lists){
				if(StringUtils.equals(customer.getCustomerName(),custName) &&
						StringUtils.equals(customer.getCardType(),cardType) &&
						StringUtils.equals(customer.getCardNumber(),cardNumber)){
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 删除潜在客户表数据
	 *----------------------------------------------可用 -------------------------------
	 * @return
	 */
	@RequestMapping("/deletePotentialCustomers")
	@SqlTransaction
	public void deletePotentialCustomers(String json,@RequestParam(value ="ids" ,defaultValue ="") String ids) {
		boolean delete= true;
		if (StringUtils.isNotBlank(ids)) {
			String[] idList = ids.split(",");
			//判断是否有客户不属于当前客户经理
			if(!custIsDelete(idList)){
				renderText(ERROR);
				return ;
			}
			for (String id : idList) {
				delete =	potentialCustomersService.deletePotentialCustomersById(Integer.parseInt(id));
				if(!delete) break;
			}
		}
		renderText(delete ? SUCCESS:ERROR);
	}

	/**
	 * 判断选中的客户是否有权限删除 true可以删除 false没有删除权限
	 * @param ids
	 */
	private boolean custIsDelete(String[] ids){
		Map<String,Object> condition = new HashMap<String,Object>();
		List<CustPotentialCustomers> lists = potentialCustomersService.queryPotentialCustomersList(condition);
		for(int i = 0;i<ids.length;i++){
			for(CustPotentialCustomers cust : lists){
				if(cust.getAttributionManager().intValue() != 0){
					//选中的客户不属于团队，属于客户经理
					if((cust.getId().intValue() == Integer.parseInt(ids[i])) && (cust.getAttributionManager().intValue() != this.getLoginInfo().getUserId().intValue()))
						return false;
				}else{
					if((cust.getId().intValue() == Integer.parseInt(ids[i])) && (this.getLoginInfo().getTeamGroupId().intValue() != cust.getAttributionTeam().intValue()))
						return false;
				}
			}
		}
		return true;
	}

	/**
	 * 全部删除潜在客户表数据
	 *----------------------------------------------可用 -------------------------------
	 * @return
	 */
	@RequestMapping("/deletePotentialCustomersAll")
	public void deletePotentialCustomersList() {
		Integer userId = this.getLoginInfo().getUserId();
		//全部删除，11.10和夏俊确认
		potentialCustomersService.deletePotentialCustomersAll(userId);
		Integer groupId =	this.getLoginInfo().getTeamGroupId();
		potentialCustomersService.deletePotentialCustomersAllByGroupId(groupId);
		renderText(SUCCESS);
	}

	/**
	 * @Author: yangdw
	 * @param:
	 * @Description: 跳转到潜在客户导入页面
	 * @Date: 11:03 2017/9/26
	 */
	@RequestMapping("/getPotentialCustPage")
	public ModelAndView getPotentialCustPage() {
		ModelAndView mv = new ModelAndView("/customer/vm/potentialCustomer/importPotentialCustomer");
		return mv;
	}

	/**
	 * @Author: yangdw
	 * @param
	 * @Description:
	 * @Date: 11:12 2017/9/26
	 */
	@RequestMapping("/downloadExcelFile")
	public void downloadExcelFile() {
		String filename = FileUtil.contact(getRootPath(), "template"+ File.separator+"importPotential.xlsx");
		File file = new File(filename);
		if (file.exists()) {
			try {
				FileInputStream fis = new FileInputStream(file);
				getResponse().addHeader(
						"Content-Disposition",
						"attachment;filename="
								+ URLEncoder.encode("潜在客户导入模板.xlsx",
								"utf-8").replace("+", "%20"));
				getResponse().setContentType("xlsx/*"); // 设置返回的文件类型
				OutputStream output = getResponse().getOutputStream(); // 得到向客户端输出二进制数据的对象
				BufferedInputStream bis = new BufferedInputStream(fis);// 输入缓冲流
				BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流
				byte data[] = new byte[4096];// 缓冲字节数
				int size = 0;
				size = bis.read(data);
				while (size != -1) {
					bos.write(data, 0, size);
					size = bis.read(data);
				}
				bis.close();
				bos.flush();// 清空输出缓冲流
				bos.close();
				output.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				log.error("下载潜在客户Excel模板 error:"+e.getMessage(),e);
			} catch (IOException e) {
				e.printStackTrace();
				log.error("下载潜在客户Excel模板 error:"+e.getMessage(),e);
			}
		}
	}
	/**
	 * 判断是否是客户经理
	 * @return
	 */
	public boolean isCustomerManager(){
		int teamRoleId=this.getLoginInfo().getTeamRoleId();
		if(teamRoleId==4){
			return true;
		}
		return false;
	}

	/**
	 * 检查用户唯一
	 */
	@RequestMapping("/checkCustomerIsRepeat")
	@ResponseBody
	public void checkCustomerIsRepeat(){
		String customerName = this.getParameter("customerName");
		String identifyType = this.getParameter("cardType");
		String identifyNum = this.getParameter("cardNumber");
		String id = this.getParameter("id");
		if(StringUtils.isNotBlank(customerName)&&StringUtils.isNotBlank(identifyType)&&StringUtils.isNotBlank(identifyNum)){
			if(!potentialCustomersService.isUniqueCustomer(id,customerName,identifyType,identifyNum)){
				renderText(false, "customerRepeat", "已存在相同姓名、证件类型、证件号码客户，请重新输入");
			}
		}
	}
	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:文件上传
	 * @Date: 13:39 2017/9/7
	 */
	@RequestMapping("/doUpload")
	public void doUpload(HttpServletRequest request, @RequestParam("uplodeFile") MultipartFile uplodeFile) throws IOException {
		if (uplodeFile.getSize() > 5 * 1024 * 1024) {
			//判断文件大小不能超过5M
			renderText("文件不能超过5M");
		}else{
//			fullFilename = URLDecoder.decode(getRequest().getParameter("fullFilename"), "UTF-8");
			String timePath = new SimpleDateFormat("yyyyMMddHmsS").format(Calendar.getInstance().getTime());
			String savePath = FileUtil.contact(getRootPath(), "temp_file"+File.separator+"importPotential"+File.separator+timePath);
			File savedDir = new File(savePath);
			if (!savedDir.exists()) {// 文件不存在则创建
				savedDir.mkdirs();
			}
			String saveFilename =  FileUtil.contact(savePath,"importPotential.xlsx");
			uplodeFile.transferTo(new File(saveFilename));
			renderText(saveFilename);
		}

	}

	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:得到导入Excel的表头信息
	 * @Date: 13:55 2017/9/26
	 */
	@RequestMapping("/getExcelHeadData")
	public String getExcelHeadData(HttpServletRequest request) throws IOException {

		String groupPermit = getLoginInfo().getTeamGroupIdByRole();
		Integer teamGroupId = getLoginInfo().getTeamGroupId();

		if (groupPermit == "" && teamGroupId != null) {
			groupPermit = teamGroupId.toString();
		}
		String saveFilename = URLDecoder.decode(this.getParameter("saveFilename"), "UTF-8");
		Map<String, Object> importExcelHead = potentialCustomersService.getImportExcelHead(saveFilename);
		List<String> headList = (List<String>) importExcelHead.get("columns");
		Object total = importExcelHead.get("total");
		List<ColumnInfo> list = new ArrayList<ColumnInfo>();
		Map<String,String> map = new HashMap<String,String>();

		map.put("客户姓名（必填）","customerName");
		map.put("联系电话（必填）","telephoneNumber");
		map.put("证件类型","cardType");
		map.put("证件号码","cardNumber");
		map.put("性别","customerSex");
		map.put("年龄","age");
		map.put("居住地址","address");
		map.put("产品编号","productCode");
		map.put("贷款用途","loanUse");
		map.put("备注","remark");
		for(int i=0;i<headList.size();i++){
			String head = headList.get(i);
//			if(map.containsKey(head)){
			if(true){
				String field = (map.containsKey(head))?map.get(head):"";
				ColumnInfo col = new ColumnInfo(i,head,field);
				list.add(col);
			}else{
				renderText("warning");
				return null;
			}
		}
		setAttribute("columnInfos",list);
		setAttribute("headList",headList);
		setAttribute("total",total);
		setAttribute("groupPermit", groupPermit);
		setAttribute("teamGroupID",teamGroupId);
		return "customer/vm/potentialCustomer/importPotentialColumn";
	}
	/**
	 * 下载错误数据Excel
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/downloadFailFile")
	public void downloadFailFile(HttpServletRequest request) throws IOException {
		String filename = URLDecoder.decode(this.getParameter("filename"), "UTF-8");
		File file = new File(filename);
		if (file.exists()) {
			try {
				FileInputStream fis = new FileInputStream(file);
				getResponse().addHeader(
						"Content-Disposition",
						"attachment;filename="
								+ URLEncoder.encode("潜在客户导入错误数据.csv",
								"utf-8").replace("+", "%20"));
				getResponse().setContentType("csv/*"); // 设置返回的文件类型
				OutputStream output = getResponse().getOutputStream(); // 得到向客户端输出二进制数据的对象
				BufferedInputStream bis = new BufferedInputStream(fis);// 输入缓冲流
				BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流
				byte data[] = new byte[4096];// 缓冲字节数
				int size = 0;
				size = bis.read(data);
				while (size != -1) {
					bos.write(data, 0, size);
					size = bis.read(data);
				}
				bis.close();
				bos.flush();// 清空输出缓冲流
				bos.close();
				output.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				log.error("下载潜在客户导入错误数据Excel模板 error:"+e.getMessage(),e);
			} catch (IOException e) {
				e.printStackTrace();
				log.error("下载潜在客户导入错误数据Excel模板 error:"+e.getMessage(),e);
			}
		}
	}

	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:执行潜在客户导入操作
	 * @Date: 15:05 2017/9/26
	 */
	@RequestMapping("/doImportPotentialCustomer")
	public void doImportPotentialCustomer(HttpServletRequest request){
		try {
			String leftColumns = this.getParameter("leftColumns");
			String rightFields = this.getParameter("rightFields");
			String potentialTyte = this.getParameter("potentialTyte");
			String potentialValue = this.getParameter("potentialValue");
			String importTotal = this.getParameter("importTotal");
			String saveFilename = URLDecoder.decode(this.getParameter("saveFilename"), "UTF-8");
			String[] excelColumns = leftColumns.split(",");
			String[] fields = rightFields.split(",");
			List<ColumnInfo> columns = new ArrayList<ColumnInfo>();
			for(int i=0;i<excelColumns.length;i++){
				ColumnInfo info = new ColumnInfo();
				info.setIndex(i);
				info.setColumnName(excelColumns[i]);
				if(!StringUtil.isNullOrEmpty(fields[i]))info.setFieldName(fields[i]);
				columns.add(info);
			}
			Map<String, String> map = new HashedMap();
			map.put("type", potentialTyte);
			map.put("value", potentialValue);

			Integer userId = getLoginInfo().getUserId();

			//插入导入历史表
			SysImportHistory sysImportHistory = new SysImportHistory();
			sysImportHistory.setUserId(userId);
			sysImportHistory.setImportModuleName("潜在客户");
			sysImportHistory.setIsComplete(0);
			sysImportHistory.setProgressCode("potentialCustImport_"+userId);
			sysImportHistory.setProgressName("潜在客户正在导入");
			sysImportHistory.setImportTotal(Integer.valueOf(importTotal));
			importHistoryProvider.insertImportHistory(sysImportHistory,userId);
			ProgressBar progressBar = potentialCustomersService.importExcel(userId.toString(), saveFilename, columns,map);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("导入潜在客户报错 error:"+e.getMessage());
			//更新导入状态
			Map<String, Object> condition = new HashedMap();
			condition.put("userId", getLoginInfo().getUserId());
			condition.put("isComplete", 0);
			condition.put("progressCode", "potentialCustImport_" + getLoginInfo().getUserId());
			List<SysImportHistory> list = importHistoryProvider.queryImportHistoryList(condition);
			for (SysImportHistory history : list) {
				if (history.getIsComplete() == 0) {
					history.setIsComplete(1);
					history.setProgressName("导入潜在客户系统报错");
					importHistoryProvider.updateImportHistory(history,getLoginInfo().getUserId());
				}
			}
		}

	}

	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:得到潜在客户导入进度信息
	 * @Date: 14:56 2017/9/26
	 */
	@RequestMapping("/getImportProgress")
	public void getImportProgress(HttpServletRequest request){
		String importTotal = this.getParameter("importTotal");
		BigDecimal decimal = new BigDecimal(importTotal);
		ProgressBar bar = progressBarManager.getProgressBarById("potentialCustImport_"+ getLoginInfo().getUserId());
		JSONObject jo = new JSONObject();
		if(bar!=null){
			jo.put("progressId", bar.getId());
			jo.put("total", bar.getTotal());
			jo.put("rate", OperationUtil.divide(decimal, 1, new BigDecimal(bar.getCurrent())));
			jo.put("current", bar.getCurrent());
			jo.put("isCompleted", bar.isCompleted());
			jo.put("isClosed", bar.isClosed());
		}
		renderText(jo.toString());
	}

	/**
	 * @Author: yangdw
	 * @params:  * @param null
	 * @Description:得到导入结果信息
	 * @Date: 15:20 2017/9/26
	 */
	@RequestMapping("/getImportResult")
	public void getImportResult(){
		Integer userId = getLoginInfo().getUserId();
		JSONObject jo = new JSONObject();
		IImportResult result = potentialCustomersService.getImportResultByUserId(userId.toString());
		if(result!=null){
			jo.put("total", result.getRowCount());
			jo.put("success", result.getSuccessCount());
			jo.put("insert", result.getInsertCount());
			jo.put("update", result.getUpdateCount());
			jo.put("fail", result.getFailCount());
			jo.put("filename", result.getFailFilename());
			jo.put("reportFileName", result.getReportFileName());
		}
		//导入历史记录更新数据为导入完成
		Map<String, Object> condition = new HashedMap();
		condition.put("userId", userId);
		condition.put("isComplete", 0);
		condition.put("progressCode", "potentialCustImport_" + userId);
		List<SysImportHistory> list = importHistoryProvider.queryImportHistoryList(condition);
		for (SysImportHistory history : list) {
			history.setIsComplete(1);
			history.setProgressName("潜在客户导入成功");
			importHistoryProvider.updateImportHistory(history,userId);
		}
		renderText(jo.toString());
	}
}

