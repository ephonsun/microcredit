package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.config.AutoTableColumn;
import banger.domain.config.ModeTemplateField;
import banger.domain.config.ModeTemplateInfo;
import banger.framework.pagesize.IPageList;
import banger.service.intf.ITableColumnService;
import banger.service.intf.ITemplateFieldService;
import banger.service.intf.ITemplateInfoService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanggl on 2017/8/22.
 */
@Controller
@RequestMapping("modelTemplateInfo")
public class ModelTemplateInfoController extends BaseController{

    @Autowired
    private ITemplateInfoService templateInfoService;
    @Autowired
    private ITableColumnService tableColumnService;
    @Autowired
    private ITemplateFieldService templateFieldService;
    /**
     * 获取评分项列表
     * @return
     */
    @RequestMapping("/getModelTemplateInfoPage")
    public ModelAndView getModelTemplateInfoList(){
        ModelAndView mv = new ModelAndView("/config/vm/model/modelTemplateInfoList");
        Map<String,Object> condition = new HashMap<String,Object>();
        //联AUTO表，将 没有下拉，number，decimal，boolean子项的排除
        List<ModeTemplateInfo> modeTemplateInfos = templateInfoService.queryTemplateInfoListModel(condition);
        mv.addObject("modeTemplateInfos",modeTemplateInfos);
        return mv;
    }

    /**
     * 查询模块列表
     */
    @RequestMapping("queryModelTemplateInfoColunm")
    public void queryModelTemplateInfoColunm(){
        Map<String,Object> condition = new HashMap<String,Object>();

        if(getParameter("templateId") != null && !"".equals(getParameter("templateId"))){
            condition.put("templateId",Integer.valueOf(getParameter("templateId")));
        }
        condition.put("fieldName",getParameter("fieldName"));
        IPageList<ModeTemplateField> models = templateFieldService.queryModelTemplateInfoColunm(condition,this.getPage());
        renderText(JsonUtil.toJson(models, "fieldId","fieldDisplay,tableName,fieldName").toString());
    }

    /**
     * 获取设置页
     * @return
     */
    @RequestMapping("/getModelTemplateInfoSetPage")
    public String getModelTemplateInfoSetPage() {
        //根据模型名称和 类型查询
        Map<String, Object> condition = new HashMap<String, Object>();
        String modelTemplateInfoJson = templateInfoService.queryModelTemplateInfoTree(condition);
        if(StringUtils.isNotBlank(modelTemplateInfoJson)) {
            setAttribute("modelTemplateInfoJson", modelTemplateInfoJson);
        }

        return "/config/vm/model/modelTemplateInfoSet";
    }

    /**
     * 点击节点时获取评分项和显示名称
     */
    @RequestMapping("/queryModelTemplateInfoColumnList")
    @ResponseBody
    public void queryModelTemplateInfoColumnList(@RequestParam("templateId") Integer templateId) {
        Map<String, Object> condition = new HashMap<String, Object>();
        ModeTemplateInfo templateInfoById = templateInfoService.getTemplateInfoById(templateId);
        //根据TEMPLATE_NAME去auto表中获取评分项，（下拉，number，decimal，boolean）
        condition.put("tableName",templateInfoById.getTemplateName());
        condition.put("condition","Select,MultipleSelect,YesNo,Number,Decimal,Float,Ratio".split(","));
        List<ModeTemplateField> list = tableColumnService.queryModelTemplateInfoColumn(condition);
        renderText(JsonUtil.toJson(list, "fieldId","fieldDisplay,isActived,fieldName").toString());
    }

    /**
     * 保存
     */
    @RequestMapping("saveModeTemplate")
    public void saveModeTemplate(@RequestParam("jsons") String jsons ){
        Integer loginUserId = getLoginInfo().getUserId();
        JSONArray jsonArray = JSONArray.fromObject(jsons);
        //遍历
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            //templateId
            Integer templateId = (Integer) jsonObject.get("templateId");
            //子表数据
            JSONObject arrayJson = new JSONObject().fromObject(jsonObject.get("json"));
            String fieldId = (String) arrayJson.get("fieldIds");
            String[] fieldIds = fieldId.split(",");
            String isActived = (String) arrayJson.get("isActiveds");
            String[] isActiveds = isActived.split(",");
            String fieldName = (String) arrayJson.get("fieldNames");
            String[] fieldNames = fieldName.split(",");
            String fieldColumnDisplay = (String) arrayJson.get("fieldColumnDisplays");
            String[] fieldColumnDisplays = fieldColumnDisplay.split(",");
            //根据templateId查出templateName
            String templateName = templateInfoService.getTemplateInfoById(templateId).getTemplateName();
            //新增或者更新
            templateFieldService.saveOrUpdate(templateId,templateName,fieldIds,isActiveds,fieldNames,fieldColumnDisplays,loginUserId);
        }
    }
}
