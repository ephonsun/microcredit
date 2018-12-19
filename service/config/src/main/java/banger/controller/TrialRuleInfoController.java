package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.config.IntoTrialRuleDetail;
import banger.domain.config.IntoTrialRuleDetailQuery;
import banger.domain.config.IntoTrialRuleInfo;
import banger.domain.config.IntoTrialRuleParams;
import banger.domain.customer.CustPotentialCustomers;
import banger.domain.product.ProdProduct;
import banger.framework.pagesize.IPageList;
import banger.framework.web.json.parser.LabelBlock;
import banger.service.intf.ITrialRuleDetailService;
import banger.service.intf.ITrialRuleInfoService;
import banger.service.intf.ITrialRuleParamsService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *初审规则 总表
 * Created by hgx on 2017/10/10.
 */
@Controller
@RequestMapping("/trialRuleInfo")
public class TrialRuleInfoController extends BaseController {
    @Autowired
    private ITrialRuleInfoService ruleInfoService;
    @Autowired
    private ITrialRuleParamsService iTrialRuleParamsService;

    @Autowired
    private ITrialRuleDetailService iTrialRuleDetailService;


    private String BASE_PATH_CUSTOMER = "/config/vm/into/";
    /**
     * 页面
     * @return
     */
    @RequestMapping("/getTrialRuleInfoListPage")
    public String getPotentialCustomersListPage() {
        return BASE_PATH_CUSTOMER + "TrialRuleInfoList";
    }

    /**
     * 得到初审规则列表
     * @return
     */
    @RequestMapping("/getTrialRuleInfoList")
    @ResponseBody
    public void getTableInfoListPage(){
        Map<String,Object> condition = new HashMap<String,Object>();
        IPageList<IntoTrialRuleInfo> intoTrialRuleInfos = ruleInfoService.queryTrialRuleInfoList(condition,this.getPage());
        renderText(JsonUtil.toJson(intoTrialRuleInfos, "ruleId", "ruleName").toString());
    }

    /**
     * 删除初审规则数据
     * @return
     */
    @RequestMapping("/deleterialRuleInfo")
    public void deletePotentialCustomers(@RequestParam(value ="ruleId" ,defaultValue ="")String ruleId) {
        Integer id = Integer.parseInt(ruleId);
        //首先判断是否存在
        IntoTrialRuleInfo intoTrialRuleInfo = ruleInfoService.getTrialRuleInfoById(id);
        if (intoTrialRuleInfo!=null)
        {
            //如果存在 进行删除
            ruleInfoService.deleteTrialRuleInfoById(id);
            //删除明细表
            //先查出来
            Map<String ,Object> map = new HashedMap();
            map.put("ruleId",intoTrialRuleInfo.getRuleId());
            List<IntoTrialRuleDetailQuery> list = iTrialRuleDetailService.queryTrialRuleDetailList(map);
            for(int i=0;i<list.size();i++)
            {
                iTrialRuleDetailService.deleteTrialRuleDetailById(list.get(i).getOptionId());
                //删除参数表
                Map<String, Object> condition = new HashMap<String, Object>();
                condition.put("optionId",list.get(i).getOptionId());
                List<IntoTrialRuleParams> intoTrialRuleParams = iTrialRuleParamsService.queryTrialRuleParamsList(condition);
                iTrialRuleParamsService.deleteTrialRuleParamsById(intoTrialRuleParams.get(0).getOptionId());
            }
            renderText(SUCCESS);
        }else {
            renderText(ERROR);
        }
    }

    /**
     * 得到初审规则新增页面
     * @return
     */
    @RequestMapping("/getTrialRuleInfoInsertPage")
    public String getIntentCustomerInsertPage(@RequestParam(value ="ruleId" ,defaultValue ="")String ruleId){
        if(StringUtils.isNotBlank(ruleId)){
            IntoTrialRuleInfo trialRuleInfo = ruleInfoService.getTrialRuleInfoById(Integer.parseInt(ruleId));
            setAttribute("trialRuleInfo", trialRuleInfo);
            setAttribute("isedit",1);
        }else {
            setAttribute("isedit",0);
        }

        return BASE_PATH_CUSTOMER+"TrialRuleInfoSave";
    }

    /**
     * 新增初审规则
     * @return
     */
    @RequestMapping("/InsertTrialRuleInfoInsert")
    @ResponseBody
    public void insertIntentCustomer(IntoTrialRuleInfo trialRuleInfo){
        if (trialRuleInfo.getRuleId()==null)
        {
            try {
                Integer loginUserId = getLoginInfo().getUserId();
                ruleInfoService.insertTrialRuleInfo(trialRuleInfo,loginUserId);
                renderText(true,"新建成功！",null);
            } catch (Exception e) {
                log.error("insertIntentCustomer error",e);
                renderText(false,"新建失败！",null);
            }
        }else {
            //说明是修改
            ruleInfoService.updateTrialRuleInfo(trialRuleInfo,getLoginInfo().getUserId());
            renderText(true,"修改成功！",null);
        }

    }

    /**
     * 规则名称唯一性校验
     * @param ruleName
     */
    @RequestMapping("/checkTrialRuleInfoNmae")
    @ResponseBody
    public void checkTrialRuleInfoNmae(@RequestParam(value = "ruleName",defaultValue = "")String ruleName,@RequestParam(value = "ruleId",defaultValue = "0")String ruleId ){
       if (StringUtils.isNotBlank(ruleName))
       {
           Map<String,Object> condition = new HashMap<String,Object>();
           condition.put("ruleName",ruleName);
           List<IntoTrialRuleInfo> list = ruleInfoService.queryTrialRuleInfoList(condition);
           if (list.size()>0 && (Integer.parseInt(ruleId)!=list.get(0).getRuleId()))
           {
               renderText(false,"规则名称重复！",null);

           }else{
               renderText(true,"",null);
           }
       }

    }

}
