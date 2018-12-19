package banger.controller;

import banger.common.BaseController;
import banger.common.constant.ModuleNameConst;
import banger.common.interceptor.NoLoginInterceptor;
import banger.common.session.ValidateCodeManage;
import banger.domain.permission.PmsUserPassExpiration;
import banger.domain.permission.UserLogin;
import banger.framework.sql.command.SqlTransaction;
import banger.framework.util.DateUtil;
import banger.framework.util.JwtUtil;
import banger.framework.util.StringUtil;
import banger.moduleIntf.ISystemModule;
import banger.service.intf.IUserLoginService;
import banger.service.intf.IUserPassExpirationService;
import banger.service.intf.IUserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;

import static banger.framework.util.DateUtil.DEFAULT_DATETIME_FORMAT;


@Controller
@RequestMapping("/login")
public class UserLoginAction extends BaseController {
	
	private static final long serialVersionUID = 1523049456219326684L;

	@Autowired
	private IUserLoginService userLoginService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IUserPassExpirationService userPassExpirationService;

	@Autowired
    private ISystemModule systemModuleImpl;
	private final static String DEFAULT_PASSWORD="96e79218965eb72c92a549dd5a330112"; //默认密码111111

	/**
	 * 得到用户登入页面
	 * @return
	 */
	@NoLoginInterceptor
	@RequestMapping("/getLoginPage")
	public String getLoginPage(){
		return "/permission/vm/login";
	}
	
	/**
	 * 执行用户登入
	 * @return
	 */
	@NoLoginInterceptor
	@RequestMapping("/doLogin")
	@ResponseBody
	public void doLogin(@ModelAttribute UserLogin user){

		String account = this.getParameter("account");
		String password = this.getParameter("password");
		String checkCode = this.getParameter("checkCode");
		//String needRandNum = this.getParameter("needRandNum");
		
		String result = "";
		if(StringUtil.isNullOrEmpty(account)){
			result = "请输入用户名！";
			renderText(result);
			return;
		}else if(StringUtil.isNullOrEmpty(password)){
			result = "请输入密码！";
			renderText(result);
			return;
		}else{
			Integer errorCount = ValidateCodeManage.getErrorCount(account);
			if(errorCount>2){
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
		}

        result = userLoginService.login(account, password);
        if("success".equals(result)){
			if(DEFAULT_PASSWORD.equals(password)){
				result = "firstlogin";
			}else{
				systemModuleImpl.addSysOpeventLog(ModuleNameConst.PERMISSION_MODULE_NAME_CN,"登录系统", this.getLoginInfo().getUserId(), this.getLoginInfo().getIp());				
		    }

			//用户密码是否过期的比较
			if (StringUtil.isNotEmpty(account)){
				if (account.equals("admin")||account.equals("superuser"))
				{}
				else {
					Date userPasswordDate = userService.queryUserByAccount(account).getUserPasswordDate();
					PmsUserPassExpiration userPasswordDateExpire = userPassExpirationService.getUserPassExpiration();
					if(userPasswordDateExpire!=null){
						int expirationday = userPasswordDateExpire.getExpirationDay();
						Integer isActived = userPasswordDateExpire.getIsActived();
						String userPasswordDated = DateUtil.format(userPasswordDate, DEFAULT_DATETIME_FORMAT);
						Date userPasswordDatedd = DateUtil.parser(userPasswordDated, DEFAULT_DATETIME_FORMAT);
						Date nowDate = DateUtil.getCurrentDate();
						Date expirationTime = DateUtil.addDay(userPasswordDatedd, expirationday);
						if (isActived == 1) {
							if (!nowDate.before(expirationTime)) {
								result = "passexpiration";
							}

						}
					}

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
	@NoLoginInterceptor
	@RequestMapping("/doLogout")
	public void doLogout(){
        if(this.getLoginInfo() != null){
			 userLoginService.logout(this.getLoginInfo());
			 renderText("success");
        }
	}

	@RequestMapping("/getModifyDefaultPws")
	public String getModifyDefaultPws(HttpServletRequest request){
		if(this.getLoginInfo()!=null){
			request.setAttribute("userId",this.getLoginInfo().getUserId());
			request.setAttribute("userAccount",this.getLoginInfo().getAccount());
		}
		return "/permission/vm/modifyDefaultPassword";
	}

	@RequestMapping("/passExpirationChangePass")
	public String passExpirationChangePass(HttpServletRequest request){
		if(this.getLoginInfo()!=null){
			request.setAttribute("userId",this.getLoginInfo().getUserId());
			request.setAttribute("userAccount",this.getLoginInfo().getAccount());
		}
		return "/permission/vm/passExpirationChangePass";
	}

	@RequestMapping("/hasUnsafeChar")
	public String hasUnsafeChar(){
		return "hasunsafechar";
	}
}
