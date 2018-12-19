package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.domain.enumerate.GroupRolesEnum;
import banger.domain.permission.PmsRole;
import banger.domain.permission.PmsUser;
import banger.framework.util.StringUtil;
import banger.moduleIntf.IPermissionModule;
import banger.moduleIntf.ITeamGroupProvider;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.util.AppJsonUtil;
import net.sf.json.JSONArray;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 团队
 * Created by zhusw on 2017/6/19.
 */
@RequestMapping("/api")
@Controller
public class AppUserGroupController extends BaseController {
    public static final String RESULT_TEAM_USER_LIST_PARAMS = "userId,userName";


    @Resource
    private ITeamGroupProvider teamGroupProvider;
    @Resource
    private IPermissionModule permissionModule;

    /**
     * 得到团队成员列表数据
     * @param request
     * @param response
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/v1/getTeamGroupUserList")
    public ResponseEntity<String> getTeamGroupUserList(HttpServletRequest request, HttpServletResponse response){
        String userId = this.getParameter("userId");
        try {
            if(StringUtil.isNotEmpty(userId)){
                List<PmsUser> applyList = teamGroupProvider.getTeamGroupUserListByUserId(Integer.parseInt(userId));
                JSONArray resArray = AppJsonUtil.toJsonArray(applyList, RESULT_TEAM_USER_LIST_PARAMS);
                return new ResponseEntity<String>(AppJsonResponse.success(resArray), HttpStatus.OK);
            }else{
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("得到团队成员列表数据异常 error:"+e.getMessage(),e);
        }
        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
    }

    /**
     * 得到团队成员列表数据
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/v1/getAllTeamGroupUserList")
    @NoLoginInterceptor
    public ResponseEntity<String> getAllTeamGroupUserList(HttpServletRequest request, HttpServletResponse response){
        String userId = this.getParameter("userId");
        try {
            if(StringUtil.isNotEmpty(userId)){
                List<PmsUser> applyList = new ArrayList<PmsUser>();
                List<PmsRole> roles =  permissionModule.getRolesByUserId(Integer.valueOf(userId));
                String teamGroupIds = permissionModule.queryUserGroupPermitByUserId(userId).getUserGroupPermit();
                for(PmsRole role : roles){
                    if(role.getRoleId()==2||role.getRoleId()==3){
                        if(StringUtil.isNotEmpty(teamGroupIds)){
                            teamGroupIds = ","+ getTeamGroupIdByRole(userId,true);
                        }
                        applyList = teamGroupProvider.getAllCusManageListGroupId(teamGroupIds);
                        applyList.add(permissionModule.getPmsUserByUserId(Integer.valueOf(userId)));
                        break;
                    }else{
                        if(!"".equals(teamGroupIds)){
                            applyList = teamGroupProvider.getAllCusManageListGroupId(teamGroupIds);
                        }
                        applyList.add(permissionModule.getPmsUserByUserId(Integer.valueOf(userId)));
                        break;
                    }
                }

                //去重
                Map<Integer,PmsUser> map = new HashMap<Integer, PmsUser>();
                for(PmsUser pmsUser:applyList){
                    map.put(pmsUser.getUserId(),pmsUser);
                }

                if(MapUtils.isNotEmpty(map)){
                    applyList = new ArrayList<PmsUser>(map.values());
                }

                JSONArray resArray = AppJsonUtil.listToArray(applyList, RESULT_TEAM_USER_LIST_PARAMS+",userLoginDate,userAccount","yyyy-MM-dd HH:mm:ss");
                return new ResponseEntity<String>(AppJsonResponse.success(resArray), HttpStatus.OK);
            }else{
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("得到团队成员列表数据异常 error:"+e.getMessage(),e);
        }
        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
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
