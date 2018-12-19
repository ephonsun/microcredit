package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.domain.app.AppHomePageCount;
import banger.domain.permission.PmsRole;
import banger.domain.system.SysBasicConfig;
import banger.framework.util.DateUtil;
import banger.framework.util.StringUtil;
import banger.moduleIntf.IBasicConfigProvider;
import banger.moduleIntf.IPermissionModule;
import banger.moduleIntf.ITeamGroupProvider;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.service.intf.IAppHomePageService;
import banger.util.AppJsonUtil;
import banger.util.ErrorUtil;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by zhusw on 2017/6/22.
 */
@Controller
@RequestMapping("/api")
public class AppHomePageController extends BaseController {
    public static final String RESULT_HOME_PAGE_COUNT_PARAMS = "task,schedule,expect,apply,invest,collect,monitor,totalCount,totalAmount,monthCount,monthAmount,audit,allot,collectReminder,collectBad,contract,sign,authorized,loanloan";


    @Autowired
    private IPermissionModule permissionModule;

    @Autowired
    private IAppHomePageService appHomePageService;

    @Autowired
    private IBasicConfigProvider basicConfigProvider;

    /**
     * 得到APP首页，统计数量
     * @param request
     * @param response
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/v1/getAppHomePageCount")
    public ResponseEntity<String> getAppHomePageCount(HttpServletRequest request, HttpServletResponse response){
        String userId = this.getParameter("userId");
        try {
            if(StringUtil.isNotEmpty(userId)){
                AppHomePageCount pageCount = this.getPageCountByRoleId(userId);
                if(pageCount != null){
                    pageCount.setMonthAmount(getTenThousandAmount(pageCount.getMonthAmount()));
                    pageCount.setTotalAmount(getTenThousandAmount(pageCount.getTotalAmount()));
                    JSONObject jo = AppJsonUtil.toJson(pageCount,RESULT_HOME_PAGE_COUNT_PARAMS);
                    return new ResponseEntity<String>(AppJsonResponse.success(jo), HttpStatus.OK);
                }else{
                    return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
                }
            }else{
                return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
            }
        } catch (Exception e) {
            ErrorUtil.logError("得到APP首页，统计数量接口异常",log,e,request);
        }
        return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_101), HttpStatus.OK);
    }

    private AppHomePageCount getPageCountByRoleId(String uid){
        int userId = Integer.parseInt(uid);
        AppHomePageCount pageCount = null;
        AppHomePageCount pageCount1 = null;
        List<PmsRole> roles = permissionModule.getRoleIdByUID(userId);

        Integer[] roleIds = new Integer[roles.size()];
        for(int i=0;i<roles.size();i++)
            roleIds[i] = roles.get(i).getRoleId();

        //显示催收贷款(当前日期+催收设置天数>=应还款时间)
        SysBasicConfig cssz = basicConfigProvider.getSysBasicConfigByKey("cfsz");
        Date collectionDate = DateUtil.addDay(DateUtil.getCurrentDate(),cssz.getConfigValue());

        if(isCustomerManager(roles)){       //客户经理
            pageCount = appHomePageService.getAppHomePageCount(userId,collectionDate);
        }else{
            pageCount = appHomePageService.getAppHomePageChargeConut(userId,roleIds,collectionDate);
        }
        return pageCount;
    }

    /**
     * 是否为客户经理
     * @param roles
     * @return
     */
    private boolean isCustomerManager(List<PmsRole> roles){
        for(PmsRole role : roles) {
            if (role.getRoleId() == 4) {  //客户经理
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param amount
     * @return
     */
    private BigDecimal getTenThousandAmount(BigDecimal amount){
        if(amount!=null){
            int v1 = amount.intValue();
            int v2 = v1 / 10000;
            int v3 = v1 / 1000;
            int v4 = (v3 - v2 * 10);
            return BigDecimal.valueOf(new Double(v2+"."+v4));
        }
        return new BigDecimal(0.0);
    }
}
