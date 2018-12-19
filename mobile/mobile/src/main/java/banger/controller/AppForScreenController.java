package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.domain.app.AppScreenCount;
import banger.domain.app.AppScreenLoan;
import banger.domain.app.AppScreenTrajectory;
import banger.domain.loan.*;
import banger.domain.permission.PmsUser_Query;
import banger.moduleIntf.*;
import banger.response.AppJsonResponse;
import banger.service.intf.IForScreeService;
import banger.service.intf.IForScreeService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.*;

/**
 * 投屏信息app
 * @author zmd
 *
 */
@RequestMapping("/api/v1")
@Controller
public class AppForScreenController extends BaseController{
    private static final long serialVersionUID = 1083049233501259543L;
    @Autowired
    IForScreeService forScreeService;
    @Autowired
    ILoanApplyProvider loanApplyProvider;
    @Autowired
    IPermissionService permissionService;
    @Autowired
    IMapTrajectoryProvider mapTrajectoryProvider;

    /**
     * 获取更新时间
     *
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/getForScreenTime", method = RequestMethod.POST)
    public ResponseEntity<String> getForScreenTimeInfo(){

        JSONObject jo = new JSONObject();
        //累计放款
        jo.put("totalLoan",60*60*24);
        //当年放款
        jo.put("whenLoan",60*60*24);
        //地图
        jo.put("map",5*60);
        //贷款阶段
        jo.put("loanPhase",5*60);
        //放款趋势
        jo.put("loanTrend",60*60*24);
        //当月放款排行榜
        jo.put("topLoanMonth",60*60*24);

        return new ResponseEntity<String>(AppJsonResponse.success(jo), HttpStatus.OK);
    }

    /**1
     * 累计放款
     *
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/getTotalLoan", method = RequestMethod.POST)
    public ResponseEntity<String> getTotalLoanInfo(){
        JSONObject jo = new JSONObject();
        Map<String,BigDecimal> map = new HashMap<String, BigDecimal>();

        List<LoanType> loanTypeList = loanApplyProvider.getAllLoanTypeList();
        for (LoanType loanType : loanTypeList){
            map.put(loanType.getLoanTypeName(), BigDecimal.valueOf(0));
        }
        List<AppScreenLoan>  loanApplyInfoAppForScreeList = this.forScreeService.getTotalLoanInfo();
        int i =1;
        for (AppScreenLoan appScreenLoan : loanApplyInfoAppForScreeList){
            map.put(appScreenLoan.getLoanTypeName(),appScreenLoan.getMoney().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
            if (i==1) {
                jo.put("allNum", appScreenLoan.getAllNum());
                jo.put("allMoney", appScreenLoan.getAllMoney().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
            }
            i++;
        }

        List<Map<String ,String>> mapList = new ArrayList<Map<String, String>>();
        for(String dataKey : map.keySet())  {
            Map<String, String> m = new HashMap<String, String>();
            m.put("loanType",dataKey);
            m.put("money",map.get(dataKey)+"");
            mapList.add(m);
        }
        jo.put("loans",mapList);

        return new ResponseEntity<String>(AppJsonResponse.success(jo), HttpStatus.OK);
    }

    /**2
     * 当年放款
     *
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/getWhenLoan", method = RequestMethod.POST)
    public ResponseEntity<String> getWhenLoanInfo(){
        JSONObject jo = new JSONObject();
        Map<String,BigDecimal> map = new HashMap<String, BigDecimal>();

        List<LoanType> loanTypeList = loanApplyProvider.getAllLoanTypeList();
        for (LoanType loanType : loanTypeList){
            map.put(loanType.getLoanTypeName(), BigDecimal.valueOf(0));
        }
        List<AppScreenLoan>  loanApplyInfoAppForScreeList = this.forScreeService.getWhenLoan();
        int i =1;
        for (AppScreenLoan appScreenLoan : loanApplyInfoAppForScreeList){
            map.put(appScreenLoan.getLoanTypeName(),appScreenLoan.getMoney().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
            if (i==1) {
                jo.put("allNum", appScreenLoan.getAllNum());
                jo.put("allMoney", appScreenLoan.getAllMoney().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
            }
            i++;
        }

        List<Map<String ,String>> mapList = new ArrayList<Map<String, String>>();
        for(String dataKey : map.keySet())  {
            Map<String,String> m = new HashMap();
            m.put("loanType",dataKey);
            m.put("money",map.get(dataKey)+"");
            mapList.add(m);
        }
        jo.put("loans",mapList);

        return new ResponseEntity<String>(AppJsonResponse.success(jo), HttpStatus.OK);
    }

    /**3
     * 地图
     *
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/getMap", method = RequestMethod.POST)
    public ResponseEntity<String> getMapInfo(){

        JSONObject jo = new JSONObject();
        List<AppScreenTrajectory> mapTrajectoryListq= this.mapTrajectoryProvider.getMapInfo();
        List<Map<String,String>> mapList = new ArrayList<Map<String, String>>();
        for(AppScreenTrajectory mq : mapTrajectoryListq){
            Map<String,String> map = new HashMap<String, String>();
            map.put("userName",mq.getUserName());
            map.put("deviceId",mq.getDeviceId());
            //纬度
            map.put("traLatitude", String.valueOf(mq.getTraLatitude()));
            //经度
            map.put("traLongitude", String.valueOf(mq.getTraLongitude()));
            mapList.add(map);
        }
        jo.put("mapList",mapList);


        return new ResponseEntity<String>(AppJsonResponse.success(jo), HttpStatus.OK);
    }

    /**4
     * 贷款阶段
     *
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/getLoanPhase", method = RequestMethod.POST)
    public ResponseEntity<String> getLoanPhaseInfo(){
        String loanProcessType = "Apply,Allot,Investigate,Approval,Loan";
        JSONObject jo = new JSONObject();
        List<AppScreenCount> loanApplyInfoList = this.forScreeService.getLoanPhaseInfo();
        if (loanApplyInfoList != null && loanApplyInfoList.size()!=0) {
            for (AppScreenCount lai : loanApplyInfoList){
                if (loanProcessType.contains(lai.getProcessType())){
                    jo.put(lai.getProcessType(),lai.getNum() == null ? 0 : lai.getNum());
                }
            }
        }
        String[] loanProcessTypes = loanProcessType.split(",");
        for (String processType : loanProcessTypes) {
            if (!jo.containsKey(processType)) {
                jo.put(processType, 0);
            }
        }
        return new ResponseEntity<String>(AppJsonResponse.success(jo), HttpStatus.OK);
    }
    /**5
     * 放款趋势
     *
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/getLoanTrend", method = RequestMethod.POST)
    public ResponseEntity<String> getLoanTrend(){

        JSONObject jo = this.forScreeService.getLoanTrend();
        return new ResponseEntity<String>(AppJsonResponse.success(jo), HttpStatus.OK);
    }
    /**6
     * 获取当月排行榜
     *
     */
    @NoLoginInterceptor
    @RequestMapping(value = "/getTopLoanMonth", method = RequestMethod.POST)
    public ResponseEntity<String> getTopLoanMonthInfo(){
        JSONObject root = new JSONObject();
        List<AppScreenCount> loanApplyInfoList = this.forScreeService.getTopLoanMonthInfo();
        JSONArray ja = new JSONArray();
        if (loanApplyInfoList != null && loanApplyInfoList.size()!=0) {
            for (AppScreenCount laf : loanApplyInfoList) {
                JSONObject jo = new JSONObject();
                PmsUser_Query pmsUserQuery = permissionService.getUserById(laf.getUserId());
                if (pmsUserQuery != null) {
                    jo.put("name", pmsUserQuery.getUserName());
                } else {
                    jo.put("name", "");
                }
                if (laf.getMoney() != null) {
                    jo.put("money", laf.getMoney().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP) + "");
                } else {
                    jo.put("money", "0");
                }
                ja.add(jo);
            }
        } else {
            JSONObject jo = new JSONObject();
            jo.put("name", "");
            jo.put("money", "0");
            ja.add(jo);
        }
        root.put("listTop",ja);

        return new ResponseEntity<String>(AppJsonResponse.success(root), HttpStatus.OK);
    }

}
