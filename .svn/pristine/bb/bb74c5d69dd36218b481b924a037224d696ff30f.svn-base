package banger.controller;

import banger.common.interceptor.NoLoginInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类解释
 *
 * @Version 1.0 2017/3/30
 * @Author Merlin
 */
@Controller
@RequestMapping("/")
public class RouteController {

    @RequestMapping("/error")
    @NoLoginInterceptor
    public String error(){
        return "error";
    }

    @RequestMapping("/timeout")
    @NoLoginInterceptor
    public String timeout(){
        return "timeout";
    }

    @RequestMapping("/nopermitform")
    @NoLoginInterceptor
    public String nopermitform(){
        return "nopermitform";
    }

    @RequestMapping("/hasunsafechar")
    @NoLoginInterceptor
    public String hasunsafechar(){
        return "hasunsafechar";
    }

}
