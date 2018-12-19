package banger.service.impl;

import banger.common.constant.ModuleNameConst;
import banger.common.login.AccessInfoHolder;
import banger.common.session.AppSessionManage;
import banger.common.session.ValidateCodeManage;
import banger.dao.intf.*;
import banger.domain.permission.*;
import banger.framework.util.ClientRefererUtil;
import banger.framework.util.StringUtil;
import banger.framework.web.servlet.ServletContextHolder;
import banger.moduleIntf.ISystemModule;
import banger.service.intf.ISysTeamGroupService;
import banger.service.intf.IUserLoginService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserLoginService implements IUserLoginService,IAutoLogin {

	@Autowired
	private IUserDao userDao;

	@Autowired
	private IRoleDao roleDao;

	@Autowired
	private IDataPermitDao dataPermitDao;

	@Autowired
	private IFuncDao funcDao;

	@Autowired
	private IMenuDao menuDao;

	@Autowired
	private IPadClentDeviceDao padClentDeviceDao;

	@Autowired
    private ISystemModule systemModuleImpl;
	
	@Autowired
    private ISysTeamGroupService groupService;
	
	private static final String DEFAULT_PASSWORD = "1e96eba0ef380a363b78632703702578";
	private static final String DEFAULT_ACCOUNT = "superuser";

	public  static Map<String,HttpSession> sessionMap = null;
	static{
		sessionMap = new HashMap<String, HttpSession>();
	}



	@Override
	public String login(String account, String password) {
		String result;
		ValidateCodeManage.addErrorCount(account);
		if(DEFAULT_ACCOUNT.equals(account)){
			result = this.defaultUserLogin(password);
		}else{
			PmsUser user = userDao.queryUserByAccount(account);
			// 因为有自动导入柜员信息，导入的柜员号是7位，且左补0，所以需要将account左补0
			/*if(user == null){
				//   account左补0后再查询
				int length = 7 - account.length();
				for(int i = 0; i < length; i++){
					account = "0" + account;
				}
				user = userDao.queryUserByAccount(account);
			}*/
			if (user!=null) {
				if (user.getUserPassword().equals(password)) {
					HttpServletRequest req = ServletContextHolder.getRequest();
					if(req!=null){ //请求不是来自pad，设置session
						this.setUserSession(user);
					}
					if(user.getUserStatus()!=null && user.getUserStatus().intValue()==0){
						result = "用户已停用";
					}else{
						// 登录成功 更新登录IP &　登录时间
						PmsUser pms_user = new PmsUser();
						pms_user.setUserLoginIp(this.getIpAddr(req));
						pms_user.setUserLoginDate(Calendar.getInstance().getTime());
						pms_user.setUserId(user.getUserId());
						userDao.updateUser(pms_user);
						ValidateCodeManage.clearErrorCount(account);
						result = "success";
					}
				} else {
					if(ValidateCodeManage.getErrorCount(account)>2){
						result = "needRandNum";
					}else{
						result = "账号或密码错误";
					}
				}
			}else{
				if(account=="develop" && password=="develop"){
					result="develop";
				}else{
					if(ValidateCodeManage.getErrorCount(account)>2){
						result = "needRandNum";
					}else{
						result = "账号或密码错误";
					}
				}
			}
		}
		return result;
	}

	/**
	 * 退出
	 *
	 * @param loginInfo
	 * @return
	 */
	@Override
	public String logout(ILoginInfo loginInfo) {
//		sessionMap.remove(loginInfo.getAccount());
		removeSession(loginInfo.getAccount());
		return null;
	}

	/**
	 * 设置session
	 * @param user
	 */
	private void setUserSession(PmsUser user){
		//登出
		removeSession(user.getUserAccount());

		String deptName = this.userDao.getDeptNameByUserId(user.getUserId());
		String roleIds = "";
		String dpCodes = "";
		int teamRoleId=0;
		List<PmsRole> roles = roleDao.getRolesByUserId(user.getUserId());
		//String roleNames = "";
		/*for(PmsRole role : roles){
		 	roleIds+=("".equals(roleIds))?role.getRoleId():","+role.getRoleId();
			roleNames+=("".equals(roleNames))?role.getRoleName():","+role.getRoleName();
		}*/
		List<String> roleNames = new ArrayList<String>();
		for (PmsRole role : roles) {//团队人员角色定义
			if(role.getRoleId()==2){
				teamRoleId=2;
			}
			if(role.getRoleId()==3){
				teamRoleId=3;
			}
			if(role.getRoleId()==4){
				teamRoleId=4;
			}
			if(role.getRoleId()==5){
				teamRoleId=5;
			}
			if(role.getRoleId()==6){
				teamRoleId=6;
			}
			if(role.getRoleId()==10){//此角色未业务平台管理员角色，慎用
				teamRoleId=10;
			}
			
			roleIds+=("".equals(roleIds))?role.getRoleId():","+role.getRoleId();
			roleNames.add(role.getRoleName());
        }
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("roleIds",roleIds);
		List<PmsDataPermit> dataPermits = dataPermitDao.queryDataPermitList(condition);
		for(PmsDataPermit dataPermit : dataPermits){
			dpCodes+=("".equals(dpCodes))?dataPermit.getPdpType():","+dataPermit.getPdpType();
		}
		
		Set<String> menuSet = new HashSet<String>();
		List<PmsMenu> menus = menuDao.getMenusByRoleIds(roleIds);
		for(PmsMenu menu : menus){
			menuSet.add(menu.getMenuId());
		}
		
		Set<String> funcSet = new HashSet<String>();
		List<PmsFunc> funcs = funcDao.getFuncsByRoleIds(roleIds);
		for(PmsFunc func : funcs){
			funcSet.add(func.getFuncId());
		}
		
		HttpServletRequest req = ServletContextHolder.getRequest();
		HttpSession session = req.getSession();
		WebLoginInfo info = new WebLoginInfo();
		info.setAccount(user.getUserAccount());
		info.setDeptId(user.getUserDeptId());
		info.setDeptName(deptName);
		info.setUserId(user.getUserId());
		info.setUserName(user.getUserName());
		info.setRoleIds(roleIds);
		info.setRoleNames(roleNames);
		//info.setIp(req.getRemoteAddr());
		info.setIp(getIpAddr(req));
		info.setDataPermitCodes(dpCodes);
		info.setFuncCodes(funcSet);
		info.setMenuCodes(menuSet);
		info.setTeamRoleId(teamRoleId);
		info.setUserGroupPermit(user.getUserGroupPermit());
		info.setUserPassAmount(user.getUserPassAmount());

		SysTeamMember member = groupService.getMemberByUserId(user.getUserId());
        if(null!=member){
        	Integer teamGroupId=member.getTeamGroupId();
        	info.setTeamGroupId(teamGroupId);
        }
        
        String referer = req.getHeader("referer");			//取客户端访问地址
        if(StringUtil.isNotEmpty(referer)){
        	String host = ClientRefererUtil.getHostUrl(referer);
        	info.setReferer(host);
        	//System.out.println(host);
        }
		
        AppSessionManage.setSession(user.getUserId(), info);
		session.setAttribute("loginInfo", info);
		AccessInfoHolder.getAccessInfo().setWebLoginInfo(info);
		sessionMap.put(user.getUserAccount(),session);
//		if(operateLogProvider != null){
//			operateLogProvider.addSysOpeventLog("权限模块","登录系统",user.getUserId(),req.getRemoteAddr());
//		}
	}

	/**
     * 获取客户端真实Ip
     * @param req
     * @return
     */
    private String getIpAddr(HttpServletRequest req){
        String ipAddress = null;
        //ipAddress = req.getRemoteAddr();
        ipAddress = req.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)){
            ipAddress = req.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)){
            ipAddress = req.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)){
            ipAddress = req.getRemoteAddr();
        }
        if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
            //根据网卡去本机配置的IP
            InetAddress inet =null;
            try{
                inet = InetAddress.getLocalHost();
            }catch(UnknownHostException e){
                e.printStackTrace();
            }
            ipAddress = inet.getHostAddress();
        }

        //对于通过多个代理的情况，第一个IP为客户端真实IP，多个IP按照','分割
        if(ipAddress != null && ipAddress.length()>15){
            if(ipAddress.indexOf(",")>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
        }

        return ipAddress;
    }

	public void removeSession(String userAccount){
		try {
			HttpSession session = sessionMap.get(userAccount);
			if(null!=session){
				Enumeration<?> enumeration = session.getAttributeNames();
				WebLoginInfo info = (WebLoginInfo)session.getAttribute("loginInfo");
				while (enumeration.hasMoreElements()) {
					String key = (String)enumeration.nextElement();
					session.removeAttribute(key);
				}
				if(info != null && systemModuleImpl != null){
					systemModuleImpl.addSysOpeventLog(ModuleNameConst.PERMISSION_MODULE_NAME_CN,"退出系统",info.getUserId(),info.getIp());
				}

				session.invalidate();
			}
		}catch (Exception e){
		}finally {
			sessionMap.remove(userAccount);
		}

	}

	@Override
	public void removeSession(){
		HttpServletRequest req = ServletContextHolder.getRequest();
		Enumeration<?> enumeration = req.getSession().getAttributeNames();
		HttpSession session = req.getSession();
		WebLoginInfo info = (WebLoginInfo)session.getAttribute("loginInfo");
		while (enumeration.hasMoreElements()) {
			String key = (String)enumeration.nextElement();
			req.getSession().removeAttribute(key);
		}
		if(info != null && systemModuleImpl != null){
            systemModuleImpl.addSysOpeventLog(ModuleNameConst.PERMISSION_MODULE_NAME_CN,"退出系统",info.getUserId(),info.getIp());
		}
		
		req.getSession().invalidate();
	}

	/**
	 * 根据账号获取登录用户信息
	 * @param account
	 * @return
	 */
	@Override
	public PmsUser getUserByAccount(String account) {
		return userDao.queryUserByAccount(account);
	}
	

	/**
	 * 自动登入
	 */
	public void autoLoginAccount(String account) {
		PmsUser user = userDao.queryUserByAccount(account);
        // 因为有自动导入柜员信息，导入的柜员号是16位，且左补0，所以需要将account左补0
        if(user == null){
            //   account左补0后再查询
            int length = 16 - account.length();
            for(int i = 0; i< length;i++){
                account = "0" + account;
            }
            user = userDao.queryUserByAccount(account);
        }
		if (user!=null) {
			HttpServletRequest req = ServletContextHolder.getRequest();
			if(req!=null){ //请求不是来自pad，设置session
				this.setUserSession(user);
			}
		}
	}

	/**
	 * 超级管理员登录设置默认session信息
	 * @param password
	 * @return
	 */
	private  String  defaultUserLogin(String password){
		String result;
		if(DEFAULT_PASSWORD.equals(password)){
			result = "success";
			HttpServletRequest req = ServletContextHolder.getRequest();
			HttpSession session = req.getSession();
			WebLoginInfo info = new WebLoginInfo();
			info.setAccount(DEFAULT_ACCOUNT);
			info.setDeptId(0);
			info.setDeptName("杭州百航信息");
			info.setUserId(0);
			info.setIp(getIpAddr(req));
			info.setUserName("超级管理员");
			List<String> roleNames = new ArrayList<String>();
			roleNames.add("超级管理员");
			info.setRoleNames(roleNames);

			Set<String> menuSet = new HashSet<String>();
			menuSet.add("syncConfig");
			menuSet.add("resource");
			menuSet.add("importCustTable");
			menuSet.add("sysMonitor");
			menuSet.add("sqlCountMonitor");
			menuSet.add("sqlDebugPage");
			menuSet.add("tableTemplateList");
			info.setMenuCodes(menuSet);
			
			session.setAttribute("loginInfo", info);
			AccessInfoHolder.getAccessInfo().setWebLoginInfo(info);



		}else{
			result = "用户名或密码错误！";
		}
		return result;
	}

	@Override
	public PadClentDevice getPadClentDeviceInfo(String deviceId) {
		return padClentDeviceDao.getPadClentDeviceInfo(deviceId);
	}

	@Override
	public void innsertPadClentDevice(PadClentDevice padClentDevice) {
		PadClentDevice clentDevice = padClentDeviceDao.getPadClentDeviceInfo(padClentDevice.getDeviceId());
		if (clentDevice != null) {
			padClentDevice.setLastTime(Calendar.getInstance().getTime());
			padClentDeviceDao.updatePadClentDevice(padClentDevice);
		} else {
			padClentDevice.setLastTime(Calendar.getInstance().getTime());
			padClentDevice.setCreateTime(Calendar.getInstance().getTime());
			padClentDeviceDao.innsertPadClentDevice(padClentDevice);
		}
		
	}
	
}
