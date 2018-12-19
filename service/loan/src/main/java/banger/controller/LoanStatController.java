package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.common.tools.StringUtil;
import banger.domain.enumerate.GroupRolesEnum;
import banger.domain.loan.LoanStatQuery;
import banger.domain.permission.PmsRole;
import banger.framework.util.DateUtil;
import banger.moduleIntf.IPermissionModule;
import banger.service.intf.ILoanStatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("loanStat")
public class LoanStatController extends BaseController {

    @Autowired
    private ILoanStatService loanStatService;
    @Resource
    private IPermissionModule permissionModule;

    @RequestMapping("getLoanStatPage")
    public String getLoanStatPage() {
        //获取权限
        String groupPermit = getLoginInfo().getTeamGroupIdByRole();
        Integer teamGroupId = getLoginInfo().getTeamGroupId();
        if (groupPermit == "" && teamGroupId != null) {
            groupPermit = teamGroupId.toString();
        }
        if (isCustomerManager()) {
            setAttribute("role", 1);
        } else if (isTeamManager()) {
            setAttribute("role", 2);
        } else {
            setAttribute("role", 3);
        }
        setAttribute("groupPermit", groupPermit);
        setAttribute("gp",getLoginInfo().getTeamGroupIdByRole());
        setAttribute("userName",getLoginInfo().getUserName());
        setAttribute("userId", getLoginInfo().getUserId());
        setAttribute("teamGroupID",teamGroupId);
        return "loan/vm/loanStat";
    }

    @RequestMapping("getPersonListPage")
    @NoLoginInterceptor
    public String getPersonListPage(@RequestParam(value = "userId", defaultValue = "-1") Integer userId,
                                    @RequestParam(value = "ttype", defaultValue = "1") String ttype,
                                    @RequestParam(value = "startYear", defaultValue = "") String startYear,
                                    @RequestParam(value = "endYear", defaultValue = "") String endYear,
                                    @RequestParam(value = "startMonth", defaultValue = "") String startMonth,
                                    @RequestParam(value = "endMonth", defaultValue = "") String endMonth,
                                    @RequestParam(value = "startYear1", defaultValue = "") String startYear1,
                                    @RequestParam(value = "endYear1", defaultValue = "") String endYear1,
                                    @RequestParam(value = "startMonth1", defaultValue = "") String startMonth1,
                                    @RequestParam(value = "endMonth1", defaultValue = "") String endMonth1,
                                    @RequestParam(value = "startYear2", defaultValue = "") String startYear2,
                                    @RequestParam(value = "isLocalPerson", defaultValue = "") String isLocalPerson,
                                    @RequestParam(value = "isHaveHouse", defaultValue = "") String isHaveHouse,
                                    @RequestParam(value = "endYear2", defaultValue = "") String endYear2,
                                    @RequestParam(value = "loanType", defaultValue = "") String loanType,
                                    @RequestParam(value = "userName", defaultValue = "") String userName){
        List<LoanStatQuery> list=null;
        String sd=null;
        String ed=null;
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("isLocalPerson",isLocalPerson);
        map.put("isHaveHouse",isHaveHouse);
        map.put("loanType",loanType);
        addMapInfo(map);
        if("1".equals(ttype)){
            Date startDate= DateUtil.getDate(Integer.parseInt(startYear),Integer.parseInt(startMonth),1);
            Date endDate = DateUtil.getDate(Integer.parseInt(endYear), Integer.parseInt(endMonth), getDays(Integer.parseInt(endYear),Integer.parseInt(endMonth)),23,59,59);
            map.put("startDate",startDate);
            map.put("endDate",endDate);
            map.put("userId",userId);
            list=loanStatService.queryLoanStatListByUserId(map);
            sd=startYear+"年"+startMonth+"月";
            ed=endYear+"年"+endMonth+"月";
        }
        if("2".equals(ttype)){
            Date startDate=null;
            Date endDate=null;
            int m=0;
            int n=0;
            if("1".equals(startMonth1)){
                m=1;
            }else if ("2".equals(startMonth1)){
                m=4;
            }else if("3".equals(startMonth1)){
                m=7;
            }else if("4".equals(startMonth1)){
                m=10;
            }
            if("1".equals(endMonth1)){
                n=3;
            }else if("2".equals(endMonth1)){
                n=6;
            }else if("3".equals(endMonth1)){
                n=9;
            }else if("4".equals(endMonth1)){
                n=12;
            }
            startDate=DateUtil.getDate(Integer.parseInt(startYear1),m,1);
            endDate = DateUtil.getDate(Integer.parseInt(endYear1), n, getDays(Integer.parseInt(endYear1),n),23,59,59);
            map.put("startDate",startDate);
            map.put("endDate",endDate);
            map.put("userId",userId);
            list=loanStatService.queryLoanStatListByUserIdAndQuarter(map);
            sd=startYear1+"年第"+startMonth1+"季度";
            ed=endYear1+"年第"+endMonth1+"季度";
        }

        if("3".equals(ttype)){
            Date startDate= DateUtil.getDate(Integer.parseInt(startYear2),1,1);
            Date endDate=DateUtil.getDate(Integer.parseInt(endYear2),12,31,23,59,59);
            map.put("startDate",startDate);
            map.put("endDate",endDate);
            map.put("userId",userId);
            list=loanStatService.queryLoanStatListByUserIdAndYear(map);
            sd=startYear2+"年";
            ed=endYear2+"年";
        }
        setAttribute("list",list);
        setAttribute("sd",sd);
        setAttribute("ed",ed);
        setAttribute("name",userName);
        setAttribute("d",DateUtil.format(new Date(),DateUtil.DEFAULT_DATETIME_FORMAT));
        return "/loan/vm/showList/personList";
    }
    @RequestMapping("getGroupListPage")
    @NoLoginInterceptor
    public String getGroupListPage(@RequestParam(value="teamGroupId",defaultValue ="-1") Integer teamGroupId,
                                   @RequestParam(value = "ttype",defaultValue ="1") String ttype,
                                   @RequestParam(value="startYear",defaultValue ="") String startYear,
                                   @RequestParam(value="endYear",defaultValue ="") String endYear,
                                   @RequestParam(value="startMonth",defaultValue = "") String startMonth,
                                   @RequestParam(value="endMonth",defaultValue ="") String endMonth,
                                   @RequestParam(value="startYear1",defaultValue ="") String startYear1,
                                   @RequestParam(value="endYear1",defaultValue ="") String endYear1,
                                   @RequestParam(value="startMonth1",defaultValue = "") String startMonth1,
                                   @RequestParam(value="endMonth1",defaultValue ="") String endMonth1,
                                   @RequestParam(value="startYear2",defaultValue ="") String startYear2,
                                   @RequestParam(value="endYear2",defaultValue ="") String endYear2,
                                   @RequestParam(value = "isLocalPerson", defaultValue = "") String isLocalPerson,
                                   @RequestParam(value = "isHaveHouse", defaultValue = "") String isHaveHouse,
                                   @RequestParam(value = "loanType", defaultValue = "") String loanType,
                                   @RequestParam(value = "userName", defaultValue = "") String userName){
        List<LoanStatQuery> list=null;
        String sd=null;
        String ed=null;
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("isLocalPerson",isLocalPerson);
        map.put("isHaveHouse",isHaveHouse);
        map.put("loanType",loanType);
        addMapInfo(map);
        if("1".equals(ttype)){
            Date startDate= DateUtil.getDate(Integer.parseInt(startYear),Integer.parseInt(startMonth),1);
            Date endDate = DateUtil.getDate(Integer.parseInt(endYear), Integer.parseInt(endMonth), getDays(Integer.parseInt(endYear),Integer.parseInt(endMonth)),23,59,59);
            map.put("startDate",startDate);
            map.put("endDate",endDate);
            map.put("teamGroupId",teamGroupId);
            list=loanStatService.queryLoanStatListByTeamGroupId(map);
            sd=startYear+"年"+startMonth+"月";
            ed=endYear+"年"+endMonth+"月";
        }
        if("2".equals(ttype)){
            Date startDate=null;
            Date endDate=null;
            int m=0;
            int n=0;
            if("1".equals(startMonth1)){
                m=1;
            }else if ("2".equals(startMonth1)){
                m=4;
            }else if("3".equals(startMonth1)){
                m=7;
            }else if("4".equals(startMonth1)){
                m=10;
            }
            if("1".equals(endMonth1)){
                n=3;
            }else if("2".equals(endMonth1)){
                n=6;
            }else if("3".equals(endMonth1)){
                n=9;
            }else if("4".equals(endMonth1)){
                n=12;
            }
            startDate=DateUtil.getDate(Integer.parseInt(startYear1),m,1);
            endDate = DateUtil.getDate(Integer.parseInt(endYear1), n, getDays(Integer.parseInt(endYear1),n),23,59,59);
            map.put("startDate",startDate);
            map.put("endDate",endDate);
            map.put("teamGroupId",teamGroupId);
            list=loanStatService.queryLoanStatListByTeamGroupIdAndQuarter(map);
            sd=startYear1+"年第"+startMonth1+"季度";
            ed=endYear1+"年第"+endMonth1+"季度";
        }
        if("3".equals(ttype)){
            Date startDate= DateUtil.getDate(Integer.parseInt(startYear2),1,1);
            Date endDate=DateUtil.getDate(Integer.parseInt(endYear2),12,31,23,59,59);
            map.put("startDate",startDate);
            map.put("endDate",endDate);
            map.put("teamGroupId",teamGroupId);
            list=loanStatService.queryLoanStatListByTeamGroupIdAndYear(map);
            sd=startYear2+"年";
            ed=endYear2+"年";
        }
        setAttribute("list",list);
        setAttribute("sd",sd);
        setAttribute("ed",ed);
        setAttribute("name",userName);
        setAttribute("d",DateUtil.format(new Date(),DateUtil.DEFAULT_DATETIME_FORMAT));
        return "/loan/vm/showList/groupList";
    }
    @RequestMapping("getCrossListPage")
    @NoLoginInterceptor
    public String getCrossListPage(@RequestParam(value = "groupPermit", defaultValue = "") String groupPermit,
                                   @RequestParam(value = "ttype", defaultValue = "1") String ttype,
                                   @RequestParam(value = "startYear", defaultValue = "") String startYear,
                                   @RequestParam(value = "endYear", defaultValue = "") String endYear,
                                   @RequestParam(value = "startMonth", defaultValue = "") String startMonth,
                                   @RequestParam(value = "endMonth", defaultValue = "") String endMonth,
                                   @RequestParam(value = "startYear1", defaultValue = "") String startYear1,
                                   @RequestParam(value = "endYear1", defaultValue = "") String endYear1,
                                   @RequestParam(value = "startMonth1", defaultValue = "") String startMonth1,
                                   @RequestParam(value = "endMonth1", defaultValue = "") String endMonth1,
                                   @RequestParam(value = "startYear2", defaultValue = "") String startYear2,
                                   @RequestParam(value = "endYear2", defaultValue = "") String endYear2,
                                   @RequestParam(value = "isLocalPerson", defaultValue = "") String isLocalPerson,
                                   @RequestParam(value = "isHaveHouse", defaultValue = "") String isHaveHouse,
                                   @RequestParam(value = "loanType", defaultValue = "") String loanType,
                                   @RequestParam(value = "userName", defaultValue = "") String userName) {
        List<LoanStatQuery> list=null;
        String sd=null;
        String ed=null;
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("isLocalPerson",isLocalPerson);
        map.put("isHaveHouse",isHaveHouse);
        map.put("loanType",loanType);
        addMapInfo(map);
        if ("1".equals(ttype)) {
            Date startDate = DateUtil.getDate(Integer.parseInt(startYear), Integer.parseInt(startMonth), 1);
            Date endDate = DateUtil.getDate(Integer.parseInt(endYear), Integer.parseInt(endMonth), getDays(Integer.parseInt(endYear),Integer.parseInt(endMonth)),23,59,59);
            map.put("startDate",startDate);
            map.put("endDate",endDate);
            map.put("teamGroupId",groupPermit);
             list = loanStatService.queryCrossLoanStatListByMonth(map);
            sd=startYear+"年"+startMonth+"月";
            ed=endYear+"年"+endMonth+"月";
        }
        if ("2".equals(ttype)) {
            Date startDate = null;
            Date endDate = null;
            int m = 0;
            int n = 0;
            if ("1".equals(startMonth1)) {
                m = 1;
            } else if ("2".equals(startMonth1)) {
                m = 4;
            } else if ("3".equals(startMonth1)) {
                m = 7;
            } else if ("4".equals(startMonth1)) {
                m = 10;
            }
            if ("1".equals(endMonth1)) {
                n = 3;
            } else if ("2".equals(endMonth1)) {
                n = 6;
            } else if ("3".equals(endMonth1)) {
                n = 9;
            } else if ("4".equals(endMonth1)) {
                n = 12;
            }
            startDate = DateUtil.getDate(Integer.parseInt(startYear1), m, 1);
            endDate = DateUtil.getDate(Integer.parseInt(endYear1), n, getDays(Integer.parseInt(endYear1),n),23,59,59);
            map.put("startDate",startDate);
            map.put("endDate",endDate);
            map.put("teamGroupId",groupPermit);
            list = loanStatService.queryCrossLoanStatListByQuarter(map);
            sd=startYear1+"年第"+startMonth1+"季度";
            ed=endYear1+"年第"+endMonth1+"季度";

        }
        if ("3".equals(ttype)) {
            Date startDate = DateUtil.getDate(Integer.parseInt(startYear2), 1, 1);
            Date endDate = DateUtil.getDate(Integer.parseInt(endYear2), 12, 31,23,59,59);
            map.put("startDate",startDate);
            map.put("endDate",endDate);
            map.put("teamGroupId",groupPermit);
            list = loanStatService.queryCrossLoanStatListByYear(map);
            sd=startYear2+"年";
            ed=endYear2+"年";
        }
        setAttribute("list",list);
        setAttribute("sd",sd);
        setAttribute("ed",ed);
        setAttribute("name",userName);
        setAttribute("d",DateUtil.format(new Date(),DateUtil.DEFAULT_DATETIME_FORMAT));
        return "/loan/vm/showList/groupList";
    }
    public int getDays(int year,int month){
        int day=0;
            switch(month){
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    day = 31;
                    break;
                case 2:
                    if((year % 4 ==0 &&year % 100 !=0)||(year % 400 == 0)){
                        day = 29;
                    }else{
                        day = 28;
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    day = 30;
                    break;
            }
            return day;
        }

    private void addMapInfo(Map map){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1);
        int year = c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        c.set(year,month,day,00,00,00);
        map.put("weekStart",sdf.format(c.getTime()));

        Calendar c1 = Calendar.getInstance();
        int day_of_week1 = c1.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week1 == 0)
            day_of_week1 = 7;
        c1.add(Calendar.DATE, -day_of_week1 + 7);
        int year1 = c1.get(Calendar.YEAR);
        int month1=c1.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);

        c1.set(year1,month1,day1,23,59,59);

        map.put("weekEnd",sdf.format(c1.getTime()));
    }

    @RequestMapping("getAppLoanStatPage")
    @NoLoginInterceptor
    public String getAppLoanStatPage(HttpServletRequest request) {
        //获取权限
        String userId = this.getParameter("userId");
        if(StringUtil.isNullOrEmpty(userId)){
            return null;
        }
        String groupPermit = getTeamGroupIdByRole(userId,true);
        request.setAttribute("gp", groupPermit);
        Integer teamGroupId = permissionModule.getGroupIdByUserId(Integer.valueOf(userId));
        List<PmsRole> roles = permissionModule.getRolesByUserId(Integer.valueOf(userId));
        for(PmsRole role : roles){
            if(role.getRoleId()==4){
                request.setAttribute("role", 1);
            }else if (role.getRoleId()==3) {
                request.setAttribute("role", 2);
            }else {
                request.setAttribute("role", 3);
            }
        }
        if (groupPermit == "" && teamGroupId != null) {
            groupPermit = teamGroupId.toString();
        }
        request.setAttribute("groupPermit", groupPermit);
        request.setAttribute("userName", permissionModule.getPmsUserByUserId(Integer.valueOf(userId)).getUserName());
        request.setAttribute("userId", userId);
        request.setAttribute("teamGroupID", teamGroupId);
        return "loan/vm/appLoanStat";
    }

    /**
     * 贷款列表默认客户经理没有整个团队的数据权限，其他的默认有本团队的数据权限
     * @param userId
     * @return
     */
    public String getTeamGroupIdByRole(String userId,Boolean isLoan){
        String groupIds = "";
        String teamGroupIds = permissionModule.queryUserGroupPermitByUserId(userId).getUserGroupPermit();
        String roleIds="";
        List<PmsRole> roles =  permissionModule.getRolesByUserId(Integer.valueOf(userId));
        for(PmsRole role:roles){
            roleIds+=role.getRoleId()+",";
        }
        String manageRoleId = String.valueOf(GroupRolesEnum.MANAGER.getRoleId());
        Integer userTeamId = permissionModule.getGroupIdByUserId(Integer.valueOf(userId));
        boolean hasTeam = userTeamId != null;
        if (roleIds != null) {
            boolean flag = !(roleIds.equals(manageRoleId) || roleIds.contains(","+manageRoleId+",")	|| roleIds.endsWith(","+manageRoleId)	|| roleIds.startsWith(manageRoleId + ","));
            if (StringUtils.isNotBlank(teamGroupIds)) {
                groupIds = teamGroupIds;
                if (isLoan) {
                    if (hasTeam && flag && userTeamId != null)
                        groupIds = addThisTeamId(groupIds, userTeamId);
                } else {
                    if (permissionModule.getGroupIdByUserId(Integer.valueOf(userId))!=null && userTeamId != null)
                        groupIds = addThisTeamId(groupIds, userTeamId);
                }
            } else {
                if (isLoan) {
                    if (flag){
                        groupIds = userTeamId == null ? "" : String.valueOf(userTeamId);
                    }
                } else {
                    groupIds = userTeamId == null ? "" : String.valueOf(userTeamId);
                }
            }
        }
        return groupIds;
    }

    private String addThisTeamId(String groupIds, Integer userTeamId){
        boolean hasThisTeamId = false;
        String [] groupIdList = groupIds.split(",");
        for (String s : groupIdList) {
            if (s.equals(userTeamId)) {
                hasThisTeamId = true;
                break;
            }
        }
        if (!hasThisTeamId)
            groupIds = groupIds + "," + String.valueOf(userTeamId);
        return groupIds;
    }
}