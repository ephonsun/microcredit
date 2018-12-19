package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.common.tools.StringUtil;
import banger.domain.config.ModeScoreInfo;
import banger.service.intf.IScoreInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanggl on 2017/8/24.
 */
@Controller
@RequestMapping("modelScoreInfo")
public class ModelScoreInfoController extends BaseController{

    @Autowired
    private IScoreInfoService scoreInfoService;

    /**
     * 获取评分模型列表
     * @return
     */
    @RequestMapping("getModelScoreInfoPage")
    public String  getModelScoreInfoPage(){
        return "/config/vm/model/modelScoreInfoList";
    }

    /**
     * 查询评分模型数据
     */
    @RequestMapping("queryModelScoreInfoList")
    public void queryModelScoreInfoList(){
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("isActived",1);
        condition.put("isDel",0);
        List<ModeScoreInfo> modeScoreInfos = scoreInfoService.queryScoreInfoList(condition);
        renderText(JsonUtil.toJson(modeScoreInfos, "modeId","modeName").toString());
    }

    /**
     * 跳转模型新智能
     */
    @RequestMapping("getModelScoreInfoSavePage")
    public String getModelScoreInfoSavePage(){
        return "/config/vm/model/modelScoreInfoSave";
    }

    /**
     * 校验评分模型新增
     */
    @RequestMapping("checkModelNameSave")
    public void checkModelNameSave(@RequestParam("modeName") String modeName){
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("modeName",modeName);
        condition.put("isDel",0);
        List<ModeScoreInfo> modeScoreInfos = scoreInfoService.queryScoreInfoList(condition);
        if(modeScoreInfos != null && modeScoreInfos.size() > 0){
            renderText(false, "", null);
            return;
        }
        renderText(true, "", null);
    }

    /**
     * 校验评分模型更新
     */
    @RequestMapping("checkModelNameUpdate")
    public void checkModelNameUpdate(@RequestParam("modeName") String modeName,@RequestParam("modeId") Integer modelId){

        if(StringUtils.equals(scoreInfoService.getScoreInfoById(modelId).getModeName(),modeName)){
            renderText(true, "", null);
            return;
        }
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("modeName",modeName);
        List<ModeScoreInfo> modeScoreInfos = scoreInfoService.queryScoreInfoList(condition);
        if(modeScoreInfos != null && modeScoreInfos.size() > 0){
            renderText(false, "", null);
            return;
        }
        renderText(true, "", null);
    }

    /**
     * 保存评分模型
     */
    @RequestMapping("saveModelScoreInfo")
    public void saveModelScoreInfo(@RequestParam("modeName") String modeName){
        Integer loginUserId = getLoginInfo().getUserId();
        ModeScoreInfo msi = new ModeScoreInfo();
        msi.setModeName(modeName);
        msi.setIsActived(1);
        msi.setIsDel(0);
        msi.setRemark("");
        scoreInfoService.insertScoreInfo(msi,loginUserId);
    }

    /**
     * 跳转评分模型编辑页面
     * @return
     */
    @RequestMapping("getModelScoreInfoUpdatePage")
    public ModelAndView getModelScoreInfoUpdatePage(@RequestParam("modeId") Integer modeId){
        ModelAndView mv = new ModelAndView("/config/vm/model/modelScoreInfoUpdate");
        ModeScoreInfo modeInfo = scoreInfoService.getScoreInfoById(modeId);
        mv.addObject("modeInfo",modeInfo);
        return mv;
    }

    /**
     * 更新评分模型
     * @param modeId
     * @param modeName
     */
    @RequestMapping("updateModelScoreInfo")
    public void updateModelScoreInfo(@RequestParam("modeId") Integer modeId,@RequestParam("modeName") String modeName){
        Integer loginUserId = getLoginInfo().getUserId();
        ModeScoreInfo scoreInfoById = scoreInfoService.getScoreInfoById(modeId);
        scoreInfoById.setModeName(modeName);
        scoreInfoService.updateScoreInfo(scoreInfoById,loginUserId);
    }

    /**
     * 删除评分模型
     * @param modeId
     */
    @RequestMapping("deleteModelScoreInfo")
    public void deleteModelScoreInfo(@RequestParam("modeId") Integer modeId){
        Integer loginUserId = getLoginInfo().getUserId();
        ModeScoreInfo scoreInfoById = scoreInfoService.getScoreInfoById(modeId);
        scoreInfoById.setIsDel(1);
        scoreInfoService.updateScoreInfo(scoreInfoById,loginUserId);
    }
}
