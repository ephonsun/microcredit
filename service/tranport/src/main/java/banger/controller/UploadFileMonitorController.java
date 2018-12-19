package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.framework.spring.SpringContext;
import banger.framework.util.StringUtil;
import banger.zmq.ZMQMessageServer;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by zhusw on 2017/12/11.
 */
@RequestMapping("/monitor")
@Controller
@Scope(value="prototype")
public class UploadFileMonitorController extends BaseController {

    private ZMQMessageServer zmqMessageServer;

    public ZMQMessageServer getZmqMessageServer(){
        if(zmqMessageServer==null)
            zmqMessageServer = (ZMQMessageServer)SpringContext.instance().get("zmqMessageServer");
        return zmqMessageServer;
    }

    /**
     * 得到当期上传速度
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/getCurrentUploadSpeed", method = RequestMethod.GET)
    public void getCurrentUploadSpeed(HttpServletRequest request, HttpServletResponse response){
        String size = this.getParameter("size");
        String time = this.getParameter("time");

        long curSize = getZmqMessageServer().getCumulative();
        long curTime = new Date().getTime();
        long preSize = (StringUtil.isNotEmpty(size))?Long.parseLong(size):curSize;
        long preTime = (StringUtil.isNotEmpty(time))?Long.parseLong(time):curTime;
        long s = (curSize - preSize)/1024;
        long t = (curTime - preTime)/1000;
        float speed = (t>0)?s/t:0;

        this.renderText("{\"size\":"+curSize+",\"time\":"+curTime+",\"speed\":"+speed+"}");
    }

}
