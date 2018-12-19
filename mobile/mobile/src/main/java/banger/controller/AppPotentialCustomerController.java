package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.constant.AppParamsConst;
import banger.domain.customer.CustPotentialCustomerQuery;
import banger.domain.customer.CustPotentialCustomers;
import banger.domain.permission.PmsRole;
import banger.domain.permission.PmsUser;
import banger.domain.permission.SysTeamMember;
import banger.domain.product.ProdProduct_Query;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.IdCardUtil;
import banger.framework.util.StringUtil;
import banger.moduleIntf.*;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.util.AppJsonUtil;
import banger.util.HttpParseUtil;
import banger.util.PageSizeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hgx on 2017/9/25.
 */
@Controller
@RequestMapping("/api")
public class AppPotentialCustomerController extends BaseController{


    private static final long serialVersionUID = -7383981582463171371L;

    @Autowired
    IPotentialCustomersProvider iPotentialCustomersProvider;

    @Autowired
    IPermissionService iPermissionService;

    @Autowired
    private ITeamGroupProvider iTeamGroupProvider;

    @Autowired
    private IPermissionModule permissionModule;

    @Resource
    IMarketingTaskProvider marketingTaskProvider;

	@Autowired
	private IProductModule productModule;
    @NoLoginInterceptor
    @RequestMapping(value = "/v1/AppqueryPotentialCustomerList", method = RequestMethod.POST)
    public ResponseEntity<String> queryCustomerList(HttpServletRequest request, HttpServletResponse response) {
        //首先判断传递的值是否为空
        String reqJson= HttpParseUtil.getJsonStr(request);
        if(StringUtils.isEmpty(reqJson)){
            log.error("潜在客户列表，参数为空");
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
        }
        //获取传递的值
        JSONObject jsonObject = new JSONObject().fromObject(reqJson);
        //获取参数
        String userid = jsonObject.containsKey("userid")?jsonObject.getString("userid"):"";
        String userids = jsonObject.containsKey("userids")?jsonObject.getString("userids"):"";
        String userName = jsonObject.containsKey("userName")?jsonObject.getString("userName"):"";
        String createDate = jsonObject.containsKey("createDate")?jsonObject.getString("createDate"):"";
        String createDateEnd = jsonObject.containsKey("createDateEnd")?jsonObject.getString("createDateEnd"):"";
        //如果 ids 和userName 都为空才用id去查
        Map<String,Object> condition = new HashMap<String,Object>();
        IPageList<CustPotentialCustomerQuery> list=null;
        //如果id
        if (StringUtils.isNotBlank(userid)) {
            //不用判断是否为客户经理了  权限都一样了
            Integer[] roles = iPermissionService.getRoleIdsByUserId(Integer.parseInt(userid));
            condition.put("userId", Integer.parseInt(userid));
            condition.put("roleIds", roles);

            //如果筛选和搜索都不为空 就都加上
            if (StringUtils.isNotBlank(userids))
            {
                //把userids 转化为数组
                condition.put("attributionManagers",userids.split("\\,"));
            }else {
                //根据id查询属于哪个团队
                List<PmsUser> pmsUsers =iTeamGroupProvider.getTeamGroupUserListByUserId(Integer.parseInt(userid));
                String ids="";
                if(pmsUsers.size()>0)
                {

                    for (int i=0;i<pmsUsers.size();i++)
                    {
                        if(i==0)
                        {
                            ids+=pmsUsers.get(i).getUserId();
                        }else {
                            ids+=","+pmsUsers.get(i).getUserId();
                        }
                    }
                }else
                {
                    ids=userid;
                }
                //把userids 转化为数组
                condition.put("attributionManager",ids.split("\\,"));
            }
            if (StringUtils.isNotBlank(userName))
            {
                condition.put("userName",userName);
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

            IPageSize page = PageSizeUtil.getPageLimit(jsonObject, AppParamsConst.PAGE_SIZE_CUSTOMER_LIST);
            list = iPotentialCustomersProvider.queryPotentialCustomersList(condition, page);
        } else {
            log.error("潜在客户列表，参数不正确");
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
        }

        JSONArray resArray = AppJsonUtil.toJsonArray(list, "id,loanId,customerName,telephoneNumber,cardType," +
                "cardNumber,customerSex,age,address,loanIntention,loanIntentionName,intentionDate,loanUse,attributionTeam,attributionManager,belongUserName,telephoneNumberX,cardNumberX,marketingSuccess,remark,createDate,updateDate,createUser,updateUser");

        return new ResponseEntity<String>(AppJsonResponse.success(resArray), HttpStatus.OK);
    }

    @NoLoginInterceptor
    @RequestMapping(value = "v1/getPotentialCustomerById", method = RequestMethod.POST)
    public ResponseEntity<String> getPotentialCustomerInfo(HttpServletRequest request, HttpServletResponse response){
        String reqJson= HttpParseUtil.getJsonStr(request);
        //String reqJson="{'id':1,'userId':120}";
        if(StringUtils.isEmpty(reqJson)){
            log.error("潜在用户信息，参数为空");
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
        }

        JSONObject jsonObject = new JSONObject().fromObject(reqJson);
        Integer id = jsonObject.containsKey("id")?jsonObject.getInt("id"):null;
        Integer userId = jsonObject.containsKey("userId")?jsonObject.getInt("userId"):null;

        JSONObject jo = new JSONObject();
        CustPotentialCustomerQuery custPotentialCustomers = new CustPotentialCustomerQuery();
        if(null!=id && null!=userId) {
            custPotentialCustomers = iPotentialCustomersProvider.getPotentialCustomersQueryById(id);
            if (custPotentialCustomers==null){
                log.error("未找到潜在用户信息");
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_130), HttpStatus.OK);
            }

            jo.put("customerName",custPotentialCustomers.getCustomerName());
            jo.put("productId",custPotentialCustomers.getProductId());
            jo.put("productName",custPotentialCustomers.getProductName());

            jo.put("customerSex",custPotentialCustomers.getCustomerSex());
            if ("2".equals(custPotentialCustomers.getCustomerSex())){
                jo.put("customerSexName","女");
            }else if("1".equals(custPotentialCustomers.getCustomerSex())){
                jo.put("customerSexName","男");
            }else{
                jo.put("customerSexName","");
            }

            jo.put("cardType",custPotentialCustomers.getCardType());

            jo.put("cardTypeName",custPotentialCustomers.getIdentifyTypeName());
            if (custPotentialCustomers.getMarketingSuccess()==0){
                jo.put("marketingSuccess","否");
            }else{
                jo.put("marketingSuccess","是");
            }
//权限
            SysTeamMember team = iPotentialCustomersProvider.getTeamIdByUserId(userId);
            Integer teamId = 0;
            if (team!=null){
                Integer teamGroupId = team.getTeamGroupId();
                if (teamGroupId!= null){
                    teamId =teamGroupId ;
                }else {
                    teamId = 0 ;
                }
            }
            if (custPotentialCustomers.getAttributionManager().equals(userId) || (teamId.equals(custPotentialCustomers.getAttributionTeam()) && custPotentialCustomers.getAttributionManager()==0)){
                jo.put("rol","1");
            }else {
                jo.put("rol","0");
            }

            jo.put("id",custPotentialCustomers.getId());
            jo.put("address",custPotentialCustomers.getAddress());
            jo.put("age",custPotentialCustomers.getAge());
            jo.put("telephoneNumber",custPotentialCustomers.getTelephoneNumber());
            jo.put("cardNumber",custPotentialCustomers.getCardNumber());

            SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
            if (custPotentialCustomers.getCreateDate()!=null) {
                String s = formatter.format(custPotentialCustomers.getCreateDate());
                jo.put("createDate", formatter.format(custPotentialCustomers.getCreateDate()));
            }else {
                jo.put("createDate", "");
            }
//            JsonConfig jsonConfig = new JsonConfig();
//            jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
//            jo = JSONObject.fromObject(custPotentialCustomers,jsonConfig);

        }else{
            jo = null;
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
        }


        return new ResponseEntity<String>(AppJsonResponse.success(jo), HttpStatus.OK);
    }
    /**
     * 新增和修改潜在顾客
     * @param request
     * @param response
     * @return
     * @throws ParseException
     */
    @NoLoginInterceptor
    @RequestMapping(value = "v1/savePotentialCustomer", method = RequestMethod.POST)
    public ResponseEntity<String> savePotentialCustomer(HttpServletRequest request,HttpServletResponse response) throws ParseException {
        String reqJson= HttpParseUtil.getJsonStr(request);

        // String reqJson ="{'id':8,'loanId':22,'customerName':'6','telephoneNumber':'2','cardType':'2','cardNumber':'2','customerSex':'8','age':2,'address':'2','loanIntention':2,'intentionDate':'2017-05-06','loanUse':'2','attributionTeam':2,'attributionManager':2,'marketingSuccess':2,'remark':'2','createDate':'2017-05-06','createUser':2}";
        if(StringUtils.isEmpty(reqJson)){
            log.error("潜在用户，参数为空");
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
        }

        JSONObject jsonObject = new JSONObject().fromObject(reqJson);
        Integer id = jsonObject.containsKey("id")?jsonObject.getInt("id"):null;
        Integer userId = jsonObject.containsKey("userId")?jsonObject.getInt("userId"):null;
        if(null==userId){
            log.error("请先登录");
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_103), HttpStatus.OK);
        }

        boolean updateFlag = false;

        if(null!=id && id.intValue()>0){//修改
            updateFlag = true;

        } else{//新增
            id = iPotentialCustomersProvider.getNewId();
        }

        JSONObject jsonObjects = new JSONObject();
        CustPotentialCustomers custPotentialCustomers =(CustPotentialCustomers) jsonObjects.toBean(jsonObject,CustPotentialCustomers.class);

		Integer productId = custPotentialCustomers.getProductId();
		if (productId != null) {
			ProdProduct_Query productDetail = productModule.getProductDetail(productId);
			custPotentialCustomers.setProductCode(productDetail.getProductCode());
			custPotentialCustomers.setLoanIntention(1);
		}else{
            custPotentialCustomers.setProductCode("");
            custPotentialCustomers.setLoanIntention(0);
        }
		if (!updateFlag ){
            custPotentialCustomers.setAttributionManager(userId);

            //团队id,2017-12-21跨团队查询不能显示另一个团队
            SysTeamMember team = iPotentialCustomersProvider.getTeamIdByUserId(userId);
            if (team!=null){
                Integer teamGroupId = team.getTeamGroupId();
                if (teamGroupId!= null){
                    custPotentialCustomers.setAttributionTeam(teamGroupId );
                }else {
                    custPotentialCustomers.setAttributionTeam(0 );
                }
            }
//            custPotentialCustomers.setAttributionTeam(0);
        }




        String customerName = custPotentialCustomers.getCustomerName();
        String cardType = custPotentialCustomers.getCardType();
        String cardNumber = custPotentialCustomers.getCardNumber();
        String tel  = custPotentialCustomers.getTelephoneNumber();
        String result = null;
        //电话
        if (tel==null || "".equals(tel)){
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_131), HttpStatus.OK);
        }

        //身份证
        if(StringUtils.isNotBlank(cardType) && StringUtils.isNotBlank(cardNumber))
        {
            result = IdCardUtil.checkIdCard(cardType, cardNumber);
            if(!"pass".equals(result)){
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_128), HttpStatus.OK);
            }
        }
        //性别 年龄
        if ("1".equals(cardType)) {
            if (custPotentialCustomers.getCustomerSex()==null || "".equals(custPotentialCustomers.getCustomerSex())){

                String sex = IdCardUtil.getSexByIdCard(cardNumber);
                if ("女".equals(sex)){
                    custPotentialCustomers.setCustomerSex("2");
                }else{
                    custPotentialCustomers.setCustomerSex("1");
                }

            }

            if (custPotentialCustomers.getAge()==null ||custPotentialCustomers.getAge()==0){
                Integer age = IdCardUtil.getAgeByIdCard(cardNumber);
                custPotentialCustomers.setAge(age);
            }
        }


        //客户唯一性校验
        if(StringUtil.isNotEmpty(customerName)&& StringUtil.isNotEmpty(tel)){
            Map<String, Object> map = new HashedMap();
            map.put("customerName", customerName);
            map.put("telephoneNumber", tel);
            List<CustPotentialCustomers> customerses = iPotentialCustomersProvider.queryPotentialCustomersList(map);
//            if(!iPotentialCustomersProvider.isUniqueCustomer(String.valueOf(id),customerName,cardType,cardNumber)) {
//                log.error("潜在客户唯一性校验未过！");
//                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_109), HttpStatus.OK);
//            }
            if(updateFlag){//编辑校验
                if(customerses != null && customerses.size() > 1){
                    return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_133), HttpStatus.OK);
                }else if(customerses != null && customerses.size() == 1){
                    CustPotentialCustomers customersById = customerses.get(0);
                    if (customersById.getId().equals(id)) {
                        iPotentialCustomersProvider.updatePotentialCustomers(custPotentialCustomers,userId);
                    }else{
                        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_133), HttpStatus.OK);
                    }
                }else{
                    iPotentialCustomersProvider.updatePotentialCustomers(custPotentialCustomers,userId);
                }
            }else{//新增校验
                if(customerses != null && customerses.size() > 0){
                    return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_133), HttpStatus.OK);
                }else{
                    boolean isCustomerManager=false;
                    custPotentialCustomers.setRecordType(0);
                    custPotentialCustomers.setCreateUserTeam(0);
                    //只有客户经理新建的潜在客户才能记入营销任务
                    List<PmsRole> roles = permissionModule.getRoleIdByUID(userId);
                    for (PmsRole pmsRole : roles) {
                        if(pmsRole.getRoleId() == 4){
                            //记录类型,0 导入记录,1新建记录
                            custPotentialCustomers.setRecordType(1);
                            Integer teamGroupId = permissionModule.getGroupIdByUserId(userId);
                            custPotentialCustomers.setCreateUserTeam(teamGroupId);
                            isCustomerManager = true;
                            break;
                        }
                    }
                    iPotentialCustomersProvider.insertPotentialCustomers(custPotentialCustomers,userId);
                    if(isCustomerManager){
                        Integer teamGroupId = permissionModule.getGroupIdByUserId(userId);
                        marketingTaskProvider.updateMarketingTaskAmount(custPotentialCustomers.getId(),teamGroupId);
                    }
                }

            }

        }else{
            log.error("潜在客户必要信息不完整！");
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
        }

        return new ResponseEntity<String>(AppJsonResponse.success(), HttpStatus.OK);

    }


}
