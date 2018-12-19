package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.config.*;
import banger.framework.codetable.CodeTableUtil;
import banger.framework.pagesize.IPageList;
import banger.service.intf.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
 * 配置初审规则
 * Created by hgx on 2017/10/10.
 */
@Controller
@RequestMapping("/trialRuleDetail")
public class TrialRuleDetailController extends BaseController {
    @Autowired
    private ITrialRuleDetailService iTrialRuleDetailService;
    @Autowired
    private ITemplatesInfoService iTemplatesInfoService;
    @Autowired
    private ITemplatesFieldService iTemplatesFieldService;
    @Autowired
    private ITrialRuleParamsService iTrialRuleParamsService;


    private String BASE_PATH_CUSTOMER = "/config/vm/into/";

    /**
     * 配置初审规则页面
     *
     * @return
     */
    @RequestMapping("/getTrialRuleDetailListPage")
    public String getTrialRuleDetailListPage(@RequestParam(value = "ruleId", defaultValue = "0") String ruleId) {
        setAttribute("ruleId", ruleId);
        return BASE_PATH_CUSTOMER + "TrialRuleDetailList";
    }

    /**
     * 得到配置初审规则列表
     *
     * @return
     */
    @RequestMapping("/getTrialRuleDetailList")
    @ResponseBody
    public void getTrialRuleDetailList(@RequestParam(value = "ruleId", defaultValue = "0") String ruleId) {
        Map<String, Object> condition = new HashMap<String, Object>();
        if (Integer.parseInt(ruleId) > 0) {
            condition.put("ruleId", ruleId);
        }
        setAttribute("ruleId", ruleId);
        IPageList<IntoTrialRuleDetailQuery> intoTrialRuleDetailQueries = iTrialRuleDetailService.queryTrialRuleDetailList(condition, this.getPage());
        renderText(JsonUtil.toJson(intoTrialRuleDetailQueries, "optionId", "ruleId,templateId,fieldId,tableName,fieldName,optionValue").toString());
    }
//
//    /**
//     * 删除配置初审规则数据
//     * @return
//     */
//    @RequestMapping("/deleterialRuleInfo")
//    public void deletePotentialCustomers(@RequestParam(value ="ruleId" ,defaultValue ="")String ruleId) {
//        Integer id = Integer.parseInt(ruleId);
//        //首先判断是否存在
//        IntoTrialRuleInfo intoTrialRuleInfo = ruleInfoService.getTrialRuleInfoById(id);
//        if (intoTrialRuleInfo!=null)
//        {
//            //如果存在 进行删除
//            ruleInfoService.deleteTrialRuleInfoById(id);
//            renderText(SUCCESS);
//        }else {
//            renderText(ERROR);
//        }
//    }
//

    /**
     * 得到配置初审规则新增页面
     *
     * @return
     */
    @RequestMapping("/getTrialRuleDetailInsertPage")
    public String getTrialRuleDetailInsertPage(@RequestParam(value = "optionId", defaultValue = "") String optionId,
                                               @RequestParam(value = "ruleId", defaultValue = "0") String ruleId) {
        //如果id有值 就是修改
        if (StringUtils.isNotBlank(optionId)) {
            IntoTrialRuleDetailQuery intoTrialRuleDetail = iTrialRuleDetailService.getTrialRuleDetailById(Integer.parseInt(optionId));
            //存入字段名
            //先把表名下面的字段集合 全都拿出来
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("templateId", intoTrialRuleDetail.getTemplateId());
            List<IntoTemplatesFieldQuery> intoTemplatesFields = iTemplatesFieldService.queryTemplatesFieldList(condition);
            setAttribute("intoTrialRuleDetail", intoTrialRuleDetail);
            setAttribute("intoTemplatesFields", intoTemplatesFields);//把对应表名下面的对应字段集合放入
            setAttribute("isedit", 1);//用于判断是否是编辑
        } else {
            setAttribute("isedit", 0);
        }
        //把表名都弄出来
        Map<String, Object> condition = new HashMap<String, Object>();
        //联AUTO表，将 没有下拉，number，decimal，boolean子项的排除
        List<IntoTemplatesInfo> modeTemplateInfos = iTemplatesInfoService.queryTemplatesInfoList(condition);
        //把查询到的所有表名 都送到前台
        setAttribute("modeTemplateInfos", modeTemplateInfos);
        setAttribute("ruleId", ruleId);
        //不存入字段名 //如果是添加 就不存入字段名
        return BASE_PATH_CUSTOMER + "TrialRuleDetailSave";
    }

    /**
     * 选择模块名称时  查询所有字段名
     *
     * @Author: hgx
     * @params: * @param null
     * @Description:
     */
    @RequestMapping("queryTrialRuleDetailByPid")
    public void queryTrialRuleDetailByPid(@RequestParam("templateId") Integer templateId) {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("isActived", 1);
        condition.put("templateId", templateId);
        List<IntoTemplatesFieldQuery> modeTemplateFields = iTemplatesFieldService.queryTemplatesFieldList(condition);
        renderText(JsonUtil.toJson(modeTemplateFields, "fieldId", "fieldName,fieldType").toString());
    }

    /**
     * @Author: hgx
     * @params: * @param null
     * @Description:重复性检测
     */
    @RequestMapping("checkTrialRuleDetail")
    public void checkTrialRuleDetail(IntoTrialRuleDetail modeScoreField) {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("optionId",modeScoreField.getOptionId());
        condition.put("templateId", modeScoreField.getTemplateId());
        condition.put("ruleId", modeScoreField.getRuleId());
        condition.put("fieldId", modeScoreField.getFieldId());
        Boolean b= iTrialRuleDetailService.checkTrialRuleDetaiOnly(condition);
        if (!b) {
            renderText(false, "", null);
            return;
        }
        renderText(true, "", null);
    }

    /**
     * 获取字典
     *
     * @param fieldId
     */
    @RequestMapping("getFiledDictionary")
    public void getFiledDictionary(@RequestParam(value = "fieldId", defaultValue = "0") String fieldId) {
        //拿id去表中获取字典
        if (Integer.parseInt(fieldId) == 0) {
            renderText(false, "", null);
            return;
        }
        IntoTemplatesFieldQuery intoTemplatesFieldQuery = iTemplatesFieldService.getTemplatesFieldById(Integer.parseInt(fieldId));
        String json = CodeTableUtil.getCodeTableJson("cdDictColByName", intoTemplatesFieldQuery.getFieldDiciName());
        renderText(true, "", json);

    }

    //真正的保存
    @RequestMapping("/getTrialRuleDetailInsert")
    public void getTrialRuleDetailInsert(@RequestParam(value = "optionParam1", defaultValue = "")String optionParam1,
                                         @RequestParam(value = "optionParam2", defaultValue = "")String optionParam2,
                                         @RequestParam(value = "ruleId", defaultValue = "0") String ruleId,
                                         @RequestParam(value = "templateId", defaultValue = "0") String templateId,
                                         @RequestParam(value = "fieldId", defaultValue = "0") String fieldId,
                                         @RequestParam(value = "optionId", defaultValue = "0") String optionId) {

        Integer optionIdS=Integer.parseInt(optionId);
        if(optionIdS==0)
        {
            //说明是新增
            IntoTrialRuleDetail intoTrialRuleDetail = new IntoTrialRuleDetail();
            intoTrialRuleDetail.setFieldId(Integer.parseInt(fieldId));
            intoTrialRuleDetail.setRuleId(Integer.parseInt(ruleId));
            intoTrialRuleDetail.setTemplateId(Integer.parseInt(templateId));
            //首先插入初审规则明细表
            iTrialRuleDetailService.insertTrialRuleDetail(intoTrialRuleDetail,this.getLoginInfo().getUserId());
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("templateId", Integer.parseInt(templateId));
            condition.put("ruleId", Integer.parseInt(ruleId));
            condition.put("fieldId", Integer.parseInt(fieldId));
            List<IntoTrialRuleDetailQuery> intoTrialRuleDetailQuery = iTrialRuleDetailService.queryTrialRuleDetailList(condition);

            //插入初审规则参数表

            IntoTrialRuleParams intoTrialRuleParams = new IntoTrialRuleParams();
            intoTrialRuleParams.setTemplateId(Integer.parseInt(templateId));
            intoTrialRuleParams.setFieldId(Integer.parseInt(fieldId));
            intoTrialRuleParams.setOptionId(intoTrialRuleDetailQuery.get(0).getOptionId());
            intoTrialRuleParams.setOptionParam1(optionParam1);
            if(!optionParam2.equals(""))
            {
                intoTrialRuleParams.setOptionParam2(optionParam2);
            }
            iTrialRuleParamsService.insertTrialRuleParams(intoTrialRuleParams,this.getLoginInfo().getUserId());
        }else {
            //不为0 就是修改
            IntoTrialRuleDetail intoTrialRuleDetail = new IntoTrialRuleDetail();
            intoTrialRuleDetail.setOptionId(optionIdS);
            intoTrialRuleDetail.setFieldId(Integer.parseInt(fieldId));
            intoTrialRuleDetail.setRuleId(Integer.parseInt(ruleId));
            intoTrialRuleDetail.setTemplateId(Integer.parseInt(templateId));
            //首先插入初审规则明细表
            iTrialRuleDetailService.updateTrialRuleDetail(intoTrialRuleDetail,this.getLoginInfo().getUserId());

            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("templateId", Integer.parseInt(templateId));
            condition.put("ruleId", Integer.parseInt(ruleId));
            condition.put("fieldId", Integer.parseInt(fieldId));
            List<IntoTrialRuleParams> intoTrialRuleParamss = iTrialRuleParamsService.queryTrialRuleParamsList(condition);
            //如果没有查询出来 就新增  如果查询出来就修改
            if(intoTrialRuleParamss.size()>0)
            {
                intoTrialRuleParamss.get(0).setOptionParam1(optionParam1);
                if(!optionParam2.equals(""))
                {
                    intoTrialRuleParamss.get(0).setOptionParam2(optionParam2);
                }
                iTrialRuleParamsService.updateTrialRuleParams( intoTrialRuleParamss.get(0),this.getLoginInfo().getUserId());
            }else{
                IntoTrialRuleParams intoTrialRuleParams = new IntoTrialRuleParams();
                intoTrialRuleParams.setTemplateId(Integer.parseInt(templateId));
                intoTrialRuleParams.setFieldId(Integer.parseInt(fieldId));
                intoTrialRuleParams.setOptionId(intoTrialRuleDetail.getOptionId());
                intoTrialRuleParams.setOptionParam1(optionParam1);
                if(!optionParam2.equals(""))
                {
                    intoTrialRuleParams.setOptionParam2(optionParam2);
                }
                iTrialRuleParamsService.insertTrialRuleParams(intoTrialRuleParams,this.getLoginInfo().getUserId());
            }

        }
    }

    /**
     * 删除初审规则明细数据
     * @return
     */
    @RequestMapping("/deleTrialRuleDetail")
    public void deletePotentialCustomers(@RequestParam(value ="optionId" ,defaultValue ="")String optionId) {
        Integer id = Integer.parseInt(optionId);
        //首先判断是否存在
        IntoTrialRuleDetail intoTrialRuleDetail = iTrialRuleDetailService.getTrialRuleDetailById(id);
        //删除明细表
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("optionId",intoTrialRuleDetail.getOptionId());
        List<IntoTrialRuleParams> intoTrialRuleParams = iTrialRuleParamsService.queryTrialRuleParamsList(condition);
        if (intoTrialRuleDetail!=null && intoTrialRuleParams.size()>0)
        {
            //如果存在 进行删除
            iTrialRuleParamsService.deleteTrialRuleParamsById(intoTrialRuleParams.get(0).getOptionId());
            iTrialRuleDetailService.deleteTrialRuleDetailById(id);
            renderText(SUCCESS);
        }else {
            renderText(ERROR);
        }
    }
}
