package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.config.IntoLoanUse;
import banger.domain.config.IntoLoanUseExtend;
import banger.domain.config.IntoTrialRuleInfo;
import banger.framework.util.StringUtil;
import banger.service.intf.ILoanUseService;
import banger.service.intf.ITrialRuleInfoService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("intoLoanUse")
public class IntoLoanUseController extends BaseController {
    @Autowired
    private ILoanUseService loanUseService;
    @Autowired
    private ITrialRuleInfoService trialRuleInfoService;
    /**
     * 获取贷款用途页面
     */
    @RequestMapping("/getIntoLoanUsePage")
    public String getIntoLoanUsePage(){
        return "/config/vm/into/intoLoanUseList";
    }

    /**
     * 查询贷款用途列表
     */
    @RequestMapping("/queryIntoLoanUseList")
    @ResponseBody
    public void queryIntoLoanUseList(){
        List<IntoLoanUseExtend> list= loanUseService.queryIntoLoanUseList();
        String str="";
        //拼接初审规则名称
        if(list!=null){
            for(int i=0;i<list.size();i++){
                String ruleId=list.get(i).getRuleId();
                String ruleIds[]=ruleId.split(",");
                if(!"".equals(ruleId)){
                    for(String s:ruleIds){
                        IntoTrialRuleInfo trialRuleInfo= trialRuleInfoService.getTrialRuleInfoById(Integer.parseInt(s));
                        if(trialRuleInfo!=null){
                            str+=","+trialRuleInfo.getRuleName();
                        }
                    }
                    if(StringUtil.isNotEmpty(str)){
                        str=str.substring(1);
                    }
                    list.get(i).setRuleName(str);
                    str="";
                }
            }
        }
        renderText(JsonUtil.toJson(list, "useId","useSelect,loanTypeName,ruleName").toString());
    }

    /**
     * 物理删除用途
     * @param useId
     */
    @RequestMapping("/deleteIntoLoanUse")
    public void deleteIntoLoanUse(Integer useId){
        loanUseService.deleteLoanUseById(useId);
    }

    /**
     * 跳转到新增页面
     * @return
     */
    @RequestMapping("getIntoLoanUseSavePage")
    public String getIntoLoanUseSavePage(){
        return "/config/vm/into/intoLoanUseSave";
    }
    /**
     * 跳转编辑页面
     * @return
     */
    @RequestMapping("getIntoLoanUseUpdatePage")
    public String getIntoLoanUseUpdatePage(@RequestParam("useId") Integer useId){
        String str="";
        IntoLoanUse loanUse = loanUseService.getLoanUseById(useId);
        setAttribute("loanUse",loanUse);
        String ruleId=loanUse.getRuleId();
        String r[]=ruleId.split(",");
        if(StringUtil.isNotEmpty(ruleId)){
            for(String s:r){
                IntoTrialRuleInfo trialRuleInfo= trialRuleInfoService.getTrialRuleInfoById(Integer.parseInt(s));
                if(trialRuleInfo!=null){
                    str+=","+trialRuleInfo.getRuleName();
                }
            }
            if(StringUtil.isNotEmpty(str)){
                str=str.substring(1);
            }
            setAttribute("ruleIds",str);
        }
        return "/config/vm/into/intoLoanUseUpdate";
    }

    /**
     * 新增用途
     */
    @RequestMapping("/addIntoLoanUse")
    public void addIntoLoanUse(String useSelect,Integer loanTypeId,String ruleId){
        Integer userId=getLoginInfo().getUserId();
        IntoLoanUse intoLoanUse=new IntoLoanUse();
        intoLoanUse.setUseSelect(useSelect);
        intoLoanUse.setLoanTypeId(loanTypeId);
        intoLoanUse.setRuleId(ruleId);
        loanUseService.insertLoanUse(intoLoanUse,userId);
    }

    /**
     * 编辑用途
     */
    @RequestMapping("updateIntoLoanUse")
    public void updateIntoLoanUse(String useSelect,Integer loanTypeId,String ruleId,Integer useId){
        Integer userId=getLoginInfo().getUserId();
        IntoLoanUse intoLoanUse=new IntoLoanUse();
        intoLoanUse.setUseSelect(useSelect);
        intoLoanUse.setLoanTypeId(loanTypeId);
        intoLoanUse.setRuleId(ruleId);
        intoLoanUse.setUseId(useId);
        loanUseService.updateLoanUse(intoLoanUse,userId);
        renderText(SUCCESS);
    }

    /**
     * 校验姓名
     *
     */
    @RequestMapping("checkLoanUseNameRule")
    @ResponseBody
    public void checkLoanUseNameRule(@RequestParam("useSelect") String useSelect,
                                      @RequestParam("useId") Integer useId) {
        if(useId!= null){
            IntoLoanUse loanUse = loanUseService.getLoanUseById(useId);
            if (null != loanUse && useSelect.equals(loanUse.getUseSelect())) {
                renderText(true, "", null);
            }
        }
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("useSelect", useSelect);
        List<IntoLoanUse> list = loanUseService.queryLoanUseList(condition);

        // 如果数据库中存在相同的名字
        if (list.size() > 0) {
            renderText(false, "贷款用途已经存在，请重新输入", null);
        } else {
            renderText(true, "", null);
        }
    }

    @RequestMapping("getTrialRules")
    @ResponseBody
    public void getTrialRules(){
        JSONObject resultJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        Map<String,Object> condition =new HashMap<String, Object>();
        List<IntoTrialRuleInfo> intoTrialRuleInfos= trialRuleInfoService.queryTrialRuleInfoList(condition);
            for(IntoTrialRuleInfo t:intoTrialRuleInfos){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("ruleId",t.getRuleId());
                jsonObject.put("ruleName",t.getRuleName());
                jsonArray.add(jsonObject);
            }
        resultJson.put("data",jsonArray);
        renderJson(true,"",resultJson);
    }

}
