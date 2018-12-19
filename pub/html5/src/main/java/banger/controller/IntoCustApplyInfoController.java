package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.common.tools.JsonUtil;
import banger.domain.config.IntoLoanUse;
import banger.domain.customer.IntoCustomerGroup;
import banger.domain.customer.IntoCustomerMember;
import banger.domain.html5.IntoCustApplyInfoQuery;
import banger.domain.html5.IntoFileInfo;
import banger.domain.permission.SysTeamGroup_Query;
import banger.framework.pagesize.IPageList;
import banger.moduleIntf.ILoanUseProvider;
import banger.moduleIntf.IPermissionModule;
import banger.service.impl.CustomerGroupService;
import banger.service.impl.CustomerMemberService;
import banger.service.intf.ICustApplyInfoService;
import banger.service.intf.IFileInfoService;
import banger.service.intf.IIntefaceRecordService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 进件客户信息
 */
@Controller
@RequestMapping("/intoCustApplyInfo")
public class IntoCustApplyInfoController extends BaseController{
    @Autowired
    private ICustApplyInfoService custApplyInfoService;
    @Autowired
    private ILoanUseProvider loanUseProvider;
    @Autowired
    private IIntefaceRecordService intefaceRecordService;
    @Autowired
    private IFileInfoService fileInfoService;
    @Autowired
    private IPermissionModule permissionModule;
    @Autowired
    private CustomerGroupService customerGroupService;
    @Autowired
	private CustomerMemberService customerMemberService;

    private  String BASE_PATH = "customer/vm/intoCustApply/";
    /**
     * 得到列表界面
     * @return
     */
    @RequestMapping("/getIntoCustApplyInfoList")
    public String getIntoCustApplyInfoList(){
        Map<String,Object>  condition = new HashedMap();
        List<IntoLoanUse> intoLoanUseList =  loanUseProvider.queryLoanUseList(condition );
        setAttribute("intoLoanUseList",intoLoanUseList);
        return "customer/vm/intoCustApply/intoCustApplyInfoListPage";
    }
    /**
	 * 营销团队
     * 查询进件客户别表列表数据
     *
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping("/queryIntoCustApplyInfoList")
    @ResponseBody
    public void IntoCustApplyInfoList(@RequestParam(value ="custName" ,defaultValue = "")String custName,
                                          @RequestParam(value ="idCard" ,defaultValue = "")String idCard,
                                          @RequestParam(value ="custPhone" ,defaultValue = "")String custPhone,
                                          @RequestParam(value ="loanUserOption" ,defaultValue = "")String loanUserOption) {
        Map<String, Object> condition = new HashMap<String, Object>();

        if(StringUtils.isNotBlank(custName)) {condition.put("custName",custName);}
        if(StringUtils.isNotBlank(idCard)) {condition.put("idCard",idCard);}
        if(StringUtils.isNotBlank(custPhone)) {condition.put("custPhone",custPhone);}
        if(StringUtils.isNotBlank(loanUserOption)) {condition.put("loanUserOption",loanUserOption);}



        IPageList<IntoCustApplyInfoQuery> intoCustApplyInfoList = custApplyInfoService.IntoCustApplyInfoList(condition, this.getPage());
        IPageList<IntoCustApplyInfoQuery> intoCustApplyInfoLists = intoCustApplyInfoList;

        renderText(JsonUtil.toJson(intoCustApplyInfoList, "applyId", "custName,cardType,idCard,custPhone,useType,applyAmount,loanUserOptionName").toString());

    }
    /**
     * 得到进件客户详细界面
     * @return
     */
    @RequestMapping("/getIntoCustApplyInfoPage")
    public String getIntoCustApplyInfoPage(Integer id){

        IntoCustApplyInfoQuery toCustApplyInfoList =  custApplyInfoService.getCustApplyInfoByIdQuery(id);
//		IntoCustApplyInfoQuery intoCustApplyInfoQuery = new IntoCustApplyInfoQuery();
//		intoCustApplyInfoQuery.setLoanUserOption(toCustApplyInfoList.getLoanUserOption());
//		setAttribute("intoCustApplyInfoQuery",intoCustApplyInfoQuery);
        setAttribute("intoCustApplyInfo",toCustApplyInfoList);
//        IntoLoanUse intoLoanUse =  loanUseProvider.getLoanUseById(toCustApplyInfoList.getUseId());
//        setAttribute("intoLoanUse",intoLoanUse);


        Map<String,Object> condition = new HashMap();
        condition.put("applyId",id);
        List<IntoFileInfo> intoFileInfoList =    fileInfoService.queryFileInfoList(condition);
        for (IntoFileInfo intoFileInfo : intoFileInfoList){
            if (intoFileInfo.getImageType()==1){
            	if(intoFileInfo.getFilePath().isEmpty()||intoFileInfo.getFileName().isEmpty()){
            	}else{
					String bytes ="data:image/jpg;base64,"+ imgToByte(intoFileInfo.getFilePath()+intoFileInfo.getFileName());
					setAttribute("image1",bytes);
				}
            }else if (intoFileInfo.getImageType()==2){
				if(intoFileInfo.getFilePath().isEmpty()||intoFileInfo.getFileName().isEmpty()){
				}else{
					String bytes ="data:image/jpg;base64,"+ imgToByte(intoFileInfo.getFilePath()+intoFileInfo.getFileName());
					setAttribute("image2",bytes);
				}
			}else  if (intoFileInfo.getImageType()==3){
				if(intoFileInfo.getFilePath().isEmpty()||intoFileInfo.getFileName().isEmpty()){
				}else{
					String bytes ="data:image/jpg;base64,"+ imgToByte(intoFileInfo.getFilePath()+intoFileInfo.getFileName());
					setAttribute("image3",bytes);
				}
			}
        }

        return "customer/vm/intoCustApply/intoCustApplyInfo";
    }

    /**
     * 跳转到客户团队分配页面
     * @return
     */
    @RequestMapping("/gotoGroupSignPage")
    public String gotoGroupSignPage(){
        String applyIds = getParameter("applyIds");

        //查询所有团队
        List<SysTeamGroup_Query> groups=permissionModule.queryAllSysTeamGroup();

        setAttribute("teamGroups",groups);
        setAttribute("applyIds",applyIds);
        return "customer/vm/intoCustApply/customerGroupSign";
    }

    /**
     * 分配客户-团队
     * @return
     */
    @RequestMapping("/intoCustGroupSign")
    @ResponseBody
    public void intoCustGroupSign(){
        String applyIds = getParameter("applyIds");
        Integer teamGroupId = Integer.parseInt(getParameter("teamGroupId"));
//		Integer teamGroupId=getLoginInfo().getTeamGroupId();
        try {
            //修改客户 分配状态
            Map<String,Object> condition=new HashMap<String,Object>();
            condition.put("applyIds", applyIds);
            condition.put("signSate", 2);
            custApplyInfoService.signIntoCustomer(condition);
            //往团队客户分配表插入数据
            String[] customerIdArray=applyIds.split(",");
            for (String customerIdStr : customerIdArray) {
                Integer customerId=Integer.valueOf(customerIdStr);
                Integer loginUserId = getLoginInfo().getUserId();
                IntoCustomerGroup intoCustomerGroup = new IntoCustomerGroup();
                //MarketCustomerGroup marketCustomerGroup=new MarketCustomerGroup();
                intoCustomerGroup.setCustomerId(customerId);
                intoCustomerGroup.setTeamGroupId(teamGroupId);
                intoCustomerGroup.setSignDate(new Date());
                customerGroupService.insertCustomerGroup(intoCustomerGroup, loginUserId);
            }

            renderText(true, "分配客户成功！", null);
            return;
        } catch (Exception e) {
            log.error("分配客户报错", e);
        }
        renderText(false, "分配客户失败！", null);

    }

	/**
	 * 分配客户-分配到客户经理
	 * @return
	 */
	@RequestMapping("/intoCustMemberSign")
	@ResponseBody
	public void intoCustMemberSign(){
		String applyIds = getParameter("applyIds");
		Integer teamGroupId=getLoginInfo().getTeamGroupId();
		Integer memberUserId=Integer.valueOf(getParameter("memberUserId"));
		try {
			//修改客户 分配状态
			Map<String,Object> condition=new HashMap<String,Object>();
			condition.put("applyIds", applyIds);
			condition.put("signSate", 3);
			custApplyInfoService.signIntoCustomer(condition);
			//往团队客户分配表插入数据
			String[] customerIdArray=applyIds.split(",");
			for (String customerIdStr : customerIdArray) {
				Integer customerId=Integer.valueOf(customerIdStr);
				Integer loginUserId = getLoginInfo().getUserId();
				IntoCustomerMember intoCustomerMember = new IntoCustomerMember();
				intoCustomerMember.setCustomerId(customerId);
				intoCustomerMember.setSignDate(new Date());
				intoCustomerMember.setSignUserId(loginUserId);
				intoCustomerMember.setTeamGroupId(teamGroupId);
				intoCustomerMember.setTeamMemberId(memberUserId);
				intoCustomerMember.setApplySate(1);
				customerMemberService.insertCustomerMember(intoCustomerMember, loginUserId);
			}
			renderText(true, "分配客户成功！", null);
			return;
		} catch (Exception e) {
			log.error("分配客户报错", e);
		}
		renderText(false, "分配客户失败！", null);

	}

	private String imgToByte(String path){
		FileInputStream fileInputStream = null;
		byte[] bytes = null;
		try {
			File file = new File(path);
			if(!file.exists()) {

			}else{
				fileInputStream = new FileInputStream(file);
				bytes = new byte[fileInputStream.available()];
				fileInputStream.read(bytes);
				fileInputStream.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BASE64Encoder encoder = new BASE64Encoder();
		if (bytes==null){
			return null;
		}
		return encoder.encode(bytes);//返回Base64编码过的字节数组字符串

		//return bytes;
	}
}
