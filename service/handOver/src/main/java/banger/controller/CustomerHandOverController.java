package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.customer.CustBasicInfo;
import banger.domain.customer.CustBasicInfoQuery;
import banger.domain.customer.Customer;
import banger.domain.customer.CustomerQuery;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.permission.ILoginInfo;
import banger.domain.permission.PmsUser;
import banger.domain.system.SysDataDictCol;
import banger.framework.pagesize.IPageList;
import banger.framework.util.IdCardUtil;
import banger.framework.util.SensitiveUtil;
import banger.framework.util.StringUtil;
import banger.moduleIntf.IBasicInfoProvider;
import banger.moduleIntf.IDataDictColProvider;
import banger.moduleIntf.ILoanHandOverProvider;
import banger.moduleIntf.ITeamGroupProvider;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by wanggl on 2017/7/3.
 */
@Controller
@RequestMapping("/customerHandOver")
public class CustomerHandOverController extends BaseController {
    @Resource
    private ILoanHandOverProvider loanHandOverProvider;
    @Resource
    private IDataDictColProvider dataDictColProvider;
    @Resource
    private IBasicInfoProvider basicInfoProvider;
    @Resource
    private ITeamGroupProvider teamGroupProvider;
    /**
     * 跳转客户转移
     *
     * @return
     */
    @RequestMapping("/getCustomerHandOverPage")
    public ModelAndView getCustomerHandOverPage() {
        //主管id
        Integer loginUserId = getLoginInfo().getUserId();
        String userGroupPermit = getLoginInfo().getUserGroupPermit();
        Set<PmsUser> allManage = new HashSet<PmsUser>();
        //如果是主管
        String roleIds = getLoginInfo().getRoleIds();
        String[] roles = roleIds.split(",");
        for (int i = 0; i < roles.length; i++) {
            if("2".equals(roles[i])){
                //权限中的客户经理
                if(!"".equals(userGroupPermit)) {
                    String[] split = userGroupPermit.split(",");
                    for (int j = 0; j < split.length; j++) {
                        List<PmsUser> teamGroupUserList = teamGroupProvider.getCusManageListGroupId(Integer.valueOf(split[j]));
                        allManage.addAll(teamGroupUserList);
                    }
                }
            }
        }
        //如果有数据权限
        if(StringUtils.isNotBlank(userGroupPermit)){
            //权限中的客户经理
            String[] split = userGroupPermit.split(",");
            for (int i = 0; i < split.length; i++) {
                List<PmsUser> teamGroupUserList = teamGroupProvider.getCusManageListGroupId(Integer.valueOf(split[i]));
                allManage.addAll(teamGroupUserList);
            }
        }
        Integer teamGroupId = getLoginInfo().getTeamGroupId();
        if(teamGroupId == null){
            teamGroupId = -1;
        }
        List<PmsUser> teamGroupUserList = teamGroupProvider.getCusManageListGroupId(teamGroupId);
        allManage.addAll(teamGroupUserList);
        //证件类型
        Map<String,Object> condition = new HashMap<String, Object>();
        condition.put("dataDictName","CD_IDENTIFY_TYPE");
        List<SysDataDictCol> sysDataDictCols = dataDictColProvider.queryDataDictColList(condition);
        ModelAndView mv = new ModelAndView("/handOver/vm/customerHandOver");
        mv.addObject("sysDataDictCols",sysDataDictCols);
        mv.addObject("userGroupPermit",getLoginInfo().getTeamGroupId());
        mv.addObject("userList",allManage);
        return mv;
    }

    /**
     * 客户经理id~归属ID
     * @param belongId
     */
    @RequestMapping("/queryCustomerList")
    @ResponseBody
    public void queryCustomerList(@RequestParam(value="belongId",defaultValue = "-1") String belongId,
                                  @RequestParam(value="customer",defaultValue = "") String customer) {
        Map<String,Object> condition = new HashMap<String,Object>();
        if (belongId == null) {
        }else{
            condition.put("belongId",belongId);
        }
        if(StringUtil.isNotEmpty(customer)){
            condition.put("customer",customer);
        }
        IPageList<CustBasicInfoQuery> list = basicInfoProvider.queryCusListByBelongId(condition,this.getPage());
        if(list != null && list.size() >0){
            for (int i = 0; i < list.size(); i++) {
                CustBasicInfo c = list.get(i);
                //身份证脱敏
                String identifyNum = c.getIdentifyNum();
                String identifyType = c.getIdentifyType();
                c.setIdentifyNum(IdCardUtil.getIdentifyNumX(identifyNum,identifyType,1));
                //手机号码脱敏
                String loanTelNumX = c.getPhoneNumber();
                c.setPhoneNumber(IdCardUtil.getTelNumX(loanTelNumX,1));
            }
        }
        renderText(JsonUtil.toJson(list, "id", "customerName,customerLevelName,customerSex,customerAge,phoneNumber,identifyType,identifyNum,loanId,loanBelongId").toString());

    }

    /**
     * 获取移交的页面
     * @return
     */
    @RequestMapping("/getCusHandOverUpdatePage")
    public ModelAndView getHandOverPage(@RequestParam("ids") String ids){
        ModelAndView mv = new ModelAndView("/handOver/vm/cusHandOverUpdate");
        //要移交的客户的id
        mv.addObject("ids",ids);
        //当前客户经理所在团队
//        Integer teamGroupId = getLoginInfo().getTeamGroupId();
        String belongId = getParameter("belongId");
        Integer teamGroupId = teamGroupProvider.queryGroupIdByUserId(belongId);

        //团队所有成员的集合
        List<PmsUser> cusManageListGroupId = teamGroupProvider.getCusManageListGroupId(teamGroupId);
        //排除当前经理
        if(belongId != null && cusManageListGroupId != null && cusManageListGroupId.size() > 0) {
            for (int i = 0; i < cusManageListGroupId.size(); i++) {
                Integer userId = cusManageListGroupId.get(i).getUserId();
                if(userId == Integer.valueOf(belongId)) {
                    cusManageListGroupId.remove(cusManageListGroupId.get(i));
                }
            }
        }
//        String belongId = getParameter("belongId");
        mv.addObject("belongId",belongId);
        mv.addObject("userList",cusManageListGroupId);
        return mv;
    }

    /**
     * 保存更新
     * @param ids
     * @param newBelongId
     */
    @RequestMapping("saveHandOver")
    public void saveHandOver(@RequestParam("ids") String ids,@RequestParam("newBelongId") Integer newBelongId,@RequestParam("oldBelongId") Integer oldBelongId){
        Integer loginUserId = getLoginInfo().getUserId();
        //查询要移交客户们所拥有的贷款且归属为这个客户经理的贷后贷款
        String[] idsArr = ids.split(",");
        Map<String,Object> condition = new HashMap<String, Object>();
        //需要移交的贷后贷款
        String[] cusId = ids.split(",");
        condition.put("loanBelongId",oldBelongId);
        condition.put("loanProcessType","AfterLoan");
        for (int i = 0; i < cusId.length; i++) {
            condition.put("loanCustomerId",Integer.valueOf(cusId[i]));
            List<LoanApplyInfo> list = loanHandOverProvider.queryHandOverLoanIds(condition);
            if(list != null && list.size() > 0){
                for (int j = 0; j < list.size(); j++) {
                    LoanApplyInfo loanApplyInfo = list.get(j);
                    //更新贷款所属
                    loanApplyInfo.setLoanBelongId(newBelongId);
                    loanHandOverProvider.updateApplyInfo(loanApplyInfo,loginUserId);
                }
            }
        }
        //更新客户所属
        for (int i = 0; i < idsArr.length; i++) {
            Customer basicInfoById = basicInfoProvider.getCustomerDetailById(Integer.valueOf(idsArr[i]));
            //如果贷款拥有客户的归属人 与 贷款归属人 相同，移交
                if (basicInfoById != null && basicInfoById.getBelongUserId() == oldBelongId) {
                basicInfoById.setBelongUserId(newBelongId);basicInfoProvider.updateBasicInfo(basicInfoById,loginUserId);
            }
        }
    }
}