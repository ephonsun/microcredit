package banger.controller;

import banger.common.BaseController;
import banger.common.constant.ModuleNameConst;
import banger.common.interceptor.NoLoginInterceptor;
import banger.domain.permission.UserLogin;
import banger.framework.util.JwtUtil;
import banger.moduleIntf.ISystemModule;
import banger.service.intf.IBackLoginService;
import banger.service.intf.IUserLoginService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 配置平台登入
 * Created by zhusw on 2017/8/10.
 */
@Controller
@RequestMapping("/login")
public class BackLoginController extends BaseController {


    @Autowired
    private IBackLoginService backLoginService;

    @Autowired
    private ISystemModule systemModuleImpl;

    private final static String DEFAULT_PASSWORD="96e79218965eb72c92a549dd5a330112"; //默认密码111111

    /**
     * 得到用户登入页面
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping("/getBackLoginPage")
    public String getLoginPage(){
        return "/permission/vm/backLogin";
    }

    /**
     * 执行用户登入
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping("/doBackLogin")
    @ResponseBody
    public void doLogin(@ModelAttribute UserLogin user){

        String account = this.getParameter("account");
        String password = this.getParameter("password");
        String checkCode = this.getParameter("checkCode");
        String needRandNum = this.getParameter("needRandNum");

        String result = backLoginService.login(account, password);
        if("success".equals(result)){
            if(needRandNum.equals("needRandNum")){
                if(StringUtils.isEmpty(checkCode)){
                    result = "请输入验证码！";
                    renderText(result);
                    return;
                }
                if(!checkCode.equals(getSession().getAttribute("rand"))){
                    result = "验证码不正确！";
                    renderText(result);
                    return;
                }
            }
            
            //登入成功添加token
			String token = JwtUtil.generToken("loan", "banger", "micro");
			renderText("{\"result\":\""+result+"\",\"token\":\""+token+"\"}");
			return;
        }
        renderText(result);
    }


    /**
     * 执行用户退出
     * @return
     */
    @RequestMapping("/doBackLogout")
    public void doLogout(){
        if(this.getLoginInfo() != null){
            backLoginService.logout(this.getLoginInfo());
            renderText("success");
        }
    }

}
