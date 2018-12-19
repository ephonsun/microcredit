package banger.service.impl;

import banger.common.constant.MenuConst;
import banger.common.constant.ModuleNameConst;
import banger.common.login.AccessInfoHolder;
import banger.dao.intf.ISuDao;
import banger.domain.permission.ILoginInfo;
import banger.domain.permission.PmsSu;
import banger.domain.permission.PmsUser;
import banger.domain.permission.WebLoginInfo;
import banger.framework.web.servlet.ServletContextHolder;
import banger.service.intf.IBackLoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 配置平台登入
 * Created by zhusw on 2017/8/10.
 */
@Service
public class BackLoginService implements IBackLoginService {

    @Autowired
    private ISuDao suDao;

    private static final Map<String,Integer> loginCountMap = new ConcurrentHashMap<String,Integer>();

    /**
     * 登录
     * @param account
     * @param password
     * @return
     */
    public String login(String account,String password){
        String result;
        if(loginCountMap.containsKey(account)){
            int  i = loginCountMap.get(account)+1;
            loginCountMap.put(account, i);
        }else{
            loginCountMap.put(account,1);
        }

        PmsSu su = suDao.getSuByAccount(account);
        if (su!=null) {
            if (su.getPw().equals(password)) {
                HttpServletRequest req = ServletContextHolder.getRequest();
                HttpSession session = req.getSession();
                WebLoginInfo info = new WebLoginInfo();
                info.setAccount(su.getSu());
                info.setDeptId(0);
                info.setDeptName("配置平台");
                info.setUserId(0);
                info.setIp(getIpAddr(req));
                info.setUserName(su.getSn());
                List<String> roleNames = new ArrayList<String>();
                roleNames.add("配置管理员");
                info.setRoleNames(roleNames);
                
                String[] menuIds = MenuConst.SU_MENU_IDS.split("\\,");
                Set<String> menuSet = new HashSet<String>();
                for(String menuId : menuIds)
                	menuSet.add(menuId);
                info.setMenuCodes(menuSet);
                
                session.setAttribute("loginInfo", info);
                AccessInfoHolder.getAccessInfo().setWebLoginInfo(info);
                result = "success";
            } else {
                if(loginCountMap.containsKey(account) && loginCountMap.get(account)>2){
                    result = "needRandNum";
                }else{
                    result = "账号或密码错误";
                }
            }
        }else{
            if(loginCountMap.containsKey(account) && loginCountMap.get(account)>2){
                result = "needRandNum";
            }else{
                result = "账号或密码错误";
            }
        }
        return result;
    }

    /**
     * 退出
     * @param loginInfo
     * @return
     */
    public String logout(ILoginInfo loginInfo){
        removeSession();
        return null;
    }

    private void removeSession(){
        HttpServletRequest req = ServletContextHolder.getRequest();
        Enumeration<?> enumeration = req.getSession().getAttributeNames();
        HttpSession session = req.getSession();
        WebLoginInfo info = (WebLoginInfo)session.getAttribute("loginInfo");
        while (enumeration.hasMoreElements()) {
            String key = (String)enumeration.nextElement();
            req.getSession().removeAttribute(key);
        }
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

}
