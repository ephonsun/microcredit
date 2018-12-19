package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.config.*;
import banger.framework.codetable.CodeTableUtil;
import banger.framework.codetable.ICodeTable;
import banger.moduleIntf.IAutoFieldProvider;
import banger.service.intf.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanggl on 2017/8/25.
 */
@Controller
@RequestMapping("modelScoreField")
public class ModelScoreFieldController extends BaseController{
    @Autowired
    private IScoreInfoService scoreInfoService;
    @Autowired
    private IScoreFieldService scoreFieldService;
    @Autowired
    private IScoreFieldParamsService scoreFieldParamsService;
    @Autowired
    private ITemplateInfoService templateInfoService;
    @Autowired
    private ITemplateFieldService templateFieldService;
	@Autowired
	private IAutoFieldProvider iAutoFieldProvider;

	/**
     * 跳转评分项页面
     */
    @RequestMapping("getModelScoreFieldPage")
    public ModelAndView getModelScoreFieldPage(@RequestParam("modeId") Integer modeId){
        ModelAndView mv = new ModelAndView("/config/vm/model/modelScoreFieldList");
        ModeScoreInfo scoreInfoById = scoreInfoService.getScoreInfoById(modeId);
        mv.addObject("scoreInfo",scoreInfoById);
        return mv;
    }

    /**
     * 查询评分模型的评分项列表
     * @param modeId
     */
    @RequestMapping("queryModelScoreInfoFieldList")
    public void queryModelScoreInfoFieldList(@RequestParam("modeId") Integer modeId){
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("modeId",modeId);
        List<ModeScoreFieldExtend> scoreFieldList = scoreFieldService.queryModelScoreInfoFieldList(condition);
        renderText(JsonUtil.toJson(scoreFieldList, "optionId","tableNameCn,fieldName,fieldDisplay").toString());
    }

    /**
     * @Author: yangdw
     * @params:  * @param null
     * @Description:跳转到新增或者修改评分项页面
     * @Date: 17:04 2017/8/25
     */
    @RequestMapping("saveModelScoreFieldPage")
    public ModelAndView saveModelScoreFieldPage(@RequestParam("modeId") Integer modeId,@RequestParam("optionId") Integer optionId){
        ModelAndView mv = new ModelAndView("/config/vm/model/saveModelScoreFieldPage");
        mv.addObject("modeId",modeId);
        Map<String,Object> condition = new HashMap<String,Object>();
        //联AUTO表，将 没有下拉，number，decimal，boolean子项的排除
        List<ModeTemplateInfo> modeTemplateInfos = templateInfoService.queryTemplateInfoListModel(condition);
        mv.addObject("modeTemplateInfos",modeTemplateInfos);
        //编辑操作跳转
        if (optionId != null) {
            //查询评分模型评分项
            ModeScoreField modeScoreField = scoreFieldService.getScoreFieldById(optionId);
//            mv.addObject("modeScoreField",modeScoreField);
            //查询评分项明细表
            condition.clear();
            condition.put("isActived",1);
            condition.put("templateId",modeScoreField.getTemplateId());
            //需要联合auto_table_column表查询出field_type,
            List<ModeTemplateFieldExtend> modeTemplateFields = templateFieldService.queryTemplateFieldAndTypeList(condition);
            mv.addObject("modeTemplateFields",modeTemplateFields);
            //查询评分模型评分项参数表
            condition.clear();
            condition.put("modeId", modeScoreField.getModeId());
            condition.put("templateId", modeScoreField.getTemplateId());
            condition.put("fieldId", modeScoreField.getFieldId());
            condition.put("optionId", modeScoreField.getOptionId());
            List<ModeScoreFieldParams> modeScoreFieldParamses = scoreFieldParamsService.queryScoreFieldParamsList(condition);
//            mv.addObject("modeScoreFieldParamses",modeScoreFieldParamses);
            //页面编辑标识
            mv.addObject("isEdit","yes");
            //判断当前编辑的评分模型评分项明细表的field的fieldType
            for (ModeTemplateFieldExtend extend : modeTemplateFields) {
                //Decimal,Number
                if (extend.getFieldId().equals(modeScoreField.getFieldId())){
                        if("Decimal".equals(extend.getFieldType() ) || "Number".equals(extend.getFieldType())
                                || "Float".equals(extend.getFieldType()) || "Ratio".equals(extend.getFieldType())) {
                            modeScoreField.setFieldNumberUnit(extend.getFieldNumberUnit());
                            //数字字段显示模式
                            mv.addObject("editType","number");
                            mv.addObject("size",modeScoreFieldParamses.size());
                            mv.addObject("modeScoreFieldParamses",modeScoreFieldParamses);
                            break;
                        }else {
                            modeScoreField.setFieldNumberUnit(extend.getFieldNumberUnit());
                            //字典项固定值显示模式
                            mv.addObject("editType","text");
                            //需要校验字典表的数据源是否发生变化
                            List<AutoBaseOptionExtends> list = new ArrayList<AutoBaseOptionExtends>();
                            if ("YesNo".equals(extend.getFieldType())) {
                                list.add(new AutoBaseOptionExtends("1","是"));
                                list.add(new AutoBaseOptionExtends("0","否"));
                            }else{
                                ModeTemplateField templateField = templateFieldService.getTemplateFieldById(modeScoreField.getFieldId());
                                Map<String, Object> map = new HashedMap();
                                map.put("fieldColumn", templateField.getTalbeColumn());
                                map.put("tableName", templateField.getTableName());
                                List<AutoTableColumn> tableColumn = iAutoFieldProvider.queryTableColumnList(map);

                                List<ICodeTable.IItem> items = CodeTableUtil.getCodeTable("cdDictColByName", tableColumn.get(0).getFieldDictName());
                                for(ICodeTable.IItem item : items){
                                    list.add(new AutoBaseOptionExtends(item.getValue(),item.getName()));
                                }
                            }
                            for (AutoBaseOptionExtends option : list) {
                                for (int i = 0; i < modeScoreFieldParamses.size(); i++) {
                                    ModeScoreFieldParams param = modeScoreFieldParamses.get(i);
                                    if (option.getName().equals(param.getOptionParam1())) {
                                        option.setValue(param.getOptionParam2());
                                        option.setParamScore(param.getParamScore());
                                    }
                                }
                            }

                            mv.addObject("modeScoreFieldParamses",list);
                            break;
                        }
                }
            }
            mv.addObject("modeScoreField",modeScoreField);
        }
        return mv;
    }

    /**
     * @Author: yangdw
     * @params:  * @param null
     * @Description:新增或者修改
     * @Date: 17:17 2017/8/25
     */
    @RequestMapping("saveModelScoreField")
    public void saveModelScoreField(ModeScoreField modeScoreField){
        scoreFieldService.saveModelScoreField(modeScoreField);
        renderText(true, "", null);
    }

    /**
     * @Author: yangdw
     * @params: * @param null
     * @Description:
     * @Date: 10:44 2017/8/28
     */
    @RequestMapping("queryModelScoreFieldByPid")
    public void queryModelScoreFieldByPid(@RequestParam("templateId") Integer templateId) {
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("isActived",1);
        condition.put("templateId",templateId);
        List<ModeTemplateFieldExtend> modeTemplateFields = templateFieldService.queryTemplateFieldAndTypeList(condition);
        renderText(JsonUtil.toJson(modeTemplateFields, "fieldId","fieldName,fieldType,fieldDisplay,fieldNumberUnit").toString());
    }

    /**
     * @Author: yangdw
     * @params:  * @param null
     * @Description:根据字段类型id获取字典表中对应的子项
     * @Date: 10:31 2017/8/29
     */
    @RequestMapping("getTemplateFieldCodeTable")
    public void getTemplateFieldCodeTable(@RequestParam("fieldId") Integer fieldId) {
        ModeTemplateField templateField = templateFieldService.getTemplateFieldById(fieldId);
		Map<String, Object> map = new HashedMap();
		map.put("fieldColumn", templateField.getTalbeColumn());
		map.put("tableName", templateField.getTableName());
		List<AutoTableColumn> tableColumn = iAutoFieldProvider.queryTableColumnList(map);
		List<AutoBaseOption> list = new ArrayList<AutoBaseOption>();
		List<ICodeTable.IItem> items = CodeTableUtil.getCodeTable("cdDictColByName", tableColumn.get(0).getFieldDictName());
		for(ICodeTable.IItem item : items){
            list.add(new AutoBaseOption(item.getValue(),item.getName()));
		}
        renderText(JsonUtil.toJsonArray(list, "id","value,name").toString());
    }
    /**
     * @Author: yangdw
     * @params:  * @param null
     * @Description:校验评分项重复性
     * @Date: 13:46 2017/8/29
     */
    @RequestMapping("checkModelScoreFieldRule")
    public void checkModelScoreFieldRule(ModeScoreField modeScoreField){
        Map<String,Object> condition = new HashMap<String,Object>();
//        condition.put("templateId",modeScoreField.getTemplateId());
        condition.put("modeId",modeScoreField.getModeId());
//        condition.put("fieldId",modeScoreField.getFieldId());
        condition.put("fieldName",modeScoreField.getFieldName());
        List<ModeScoreField> modeScoreFields = scoreFieldService.queryScoreFieldList(condition);
        if(modeScoreField != null && modeScoreFields.size() > 0){
            renderText(false, "", null);
            return;
        }
        renderText(true, "", null);
    }
    /**
     * 删除评分项
     */
    @RequestMapping("deleteModelScoreInfoField")
    public void deleteModelScoreInfoField(@RequestParam("optionId") Integer optionId){
        scoreFieldService.deleteScoreFieldById(optionId);
        //联动删除参数表
        scoreFieldParamsService.deleteScoreFieldParamsByOptionId(optionId);
    }

    /**
     * 跳转模型复制页面
     */
    @RequestMapping("getCopyModelScoreInfoPage")
    public ModelAndView getCopyModelScoreInfoPage(@RequestParam("modeId") Integer modeId){
        ModelAndView mv = new ModelAndView("/config/vm/model/copyModelScoreInfo");
        mv.addObject("modeId",modeId);

        Map<String,Object> condition =new HashMap<String,Object>();
        condition.put("modeId",modeId);
        List<ModeScoreInfo> copyModels = scoreInfoService.queryModelScoreInfoCopy(condition);
        mv.addObject("copyModels",copyModels);
        return mv;
    }

    /**
     * @param modeId 当前模型id
     * @param copyModeId 要复制的模型id，0为全新模型，删除所有数据
     */
    @RequestMapping("saveCopyModel")
    public void saveCopyModel(@RequestParam("modeId") Integer modeId,@RequestParam("copyModeId") Integer copyModeId){
        Integer loginUserId = getLoginInfo().getUserId();

        //删掉当前模型的所有数据
        Map<String,Object> conditionDelete = new HashMap<String,Object>();  //评分项表
        conditionDelete.put("modeId",modeId);
        scoreFieldService.deleteScoreFieldByModeId(conditionDelete);
        //评分项参数表
        scoreFieldParamsService.deleteScoreFieldParamsByModeId(conditionDelete);

        //查出所有要复制的模型的数据
        Map<String,Object> conditionQuery = new HashMap<String,Object>();
        conditionQuery.put("modeId",copyModeId);
        //评分项表
        List<ModeScoreField> modeScoreFields = scoreFieldService.queryScoreFieldList(conditionQuery);
        //评分项参数表
        List<ModeScoreFieldParams> modeScoreFieldParams = scoreFieldParamsService.queryScoreFieldParamsList(conditionQuery);
        //赋值给当前模型
        scoreFieldService.copyModel(modeId,copyModeId,modeScoreFields,modeScoreFieldParams,loginUserId);
    }
}
