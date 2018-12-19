package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.html5.IntoPhoneMsgCode;
import banger.framework.pagesize.IPageList;
import banger.service.intf.IPhoneMsgCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangYi on 2017/11/2
 **/
@Controller
@RequestMapping("/msg")
public class MsgHistoryController extends BaseController{
    @Autowired
    private IPhoneMsgCodeService phoneMsgCodeService;


    /**
     * 得到短信历史记录列表
     * @return
     */
    @RequestMapping("/getMsgHistory")
    public String getMsgHistory(){
       return "/config/vm/into/intoMsgHistory";
    }
    /**
     * 查询列表
     */
    @RequestMapping("/getMsgList")
    @ResponseBody
    public void getMsgList(String phone,String addTime,String addTimeEnd){
        Map<String,Object> condition=new HashMap<String,Object>();
        condition.put("phone",phone);
        condition.put("addTime",addTime);
        condition.put("addTimeEnd",addTimeEnd);
        IPageList<IntoPhoneMsgCode> list=phoneMsgCodeService.queryPhoneMsgCodeList(condition,this.getPage());
        renderText(JsonUtil.toJson(list, "id", "phone,checkCode,addTime,ip").toString());
    }
}
