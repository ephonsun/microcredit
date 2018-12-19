package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.domain.loan.LoanStatQuery;
import banger.framework.util.DateUtil;
import banger.service.intf.ILoanStatService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("loanStatPic")
public class LoanStatPicController extends BaseController {
    @Autowired
    private ILoanStatService loanStatService;

    @RequestMapping("queryLoanStatPicListByUserId")
    @ResponseBody
    @NoLoginInterceptor
    public void queryLoanStatPicListByUserId(@RequestParam(value = "userId", defaultValue = "-1") Integer userId,
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
                                             @RequestParam(value = "loanType", defaultValue = "") String loanType) {
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
            map.put("userId",userId);
            List<LoanStatQuery> list = loanStatService.queryLoanStatListByUserId(map);
            if (list != null) {
                fn(list);
            }

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
            map.put("userId",userId);
            List<LoanStatQuery> list = loanStatService.queryLoanStatListByUserIdAndQuarter(map);
            if (list != null) {
                fn(list);
            }
        }

        if ("3".equals(ttype)) {
            Date startDate = DateUtil.getDate(Integer.parseInt(startYear2), 1, 1);
            Date endDate = DateUtil.getDate(Integer.parseInt(endYear2), 12, 31,23,59,59);
            map.put("startDate",startDate);
            map.put("endDate",endDate);
            map.put("userId",userId);
            List<LoanStatQuery> list = loanStatService.queryLoanStatListByUserIdAndYear(map);
            if (list != null) {
                fn(list);
            }
        }
    }

    @RequestMapping("queryLoanStatPicListByTeamGroupId")
    @ResponseBody
    @NoLoginInterceptor
    public void  queryLoanStatPicListByTeamGroupId(@RequestParam(value="teamGroupId",defaultValue ="-1") Integer teamGroupId,
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
                                                   @RequestParam(value = "loanType", defaultValue = "") String loanType){
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
            List<LoanStatQuery>list=loanStatService.queryLoanStatListByTeamGroupId(map);
            if (list != null) {
                fn(list);
            }
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
            List<LoanStatQuery> list=loanStatService.queryLoanStatListByTeamGroupIdAndQuarter(map);
            if (list != null) {
                fn(list);
            }
        }
        if("3".equals(ttype)){
            Date startDate= DateUtil.getDate(Integer.parseInt(startYear2),1,1);
            Date endDate = DateUtil.getDate(Integer.parseInt(endYear2), 12, 31,23,59,59);
            map.put("startDate",startDate);
            map.put("endDate",endDate);
            map.put("teamGroupId",teamGroupId);
            List<LoanStatQuery> list=loanStatService.queryLoanStatListByTeamGroupIdAndYear(map);
            if (list != null) {
                fn(list);
            }
        }

    }
    @RequestMapping("queryCrossPicList")
    @ResponseBody
    @NoLoginInterceptor
    public void queryCrossPicList(@RequestParam(value = "groupPermit", defaultValue = "") String groupPermit,
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
                                  @RequestParam(value = "loanType", defaultValue = "") String loanType){
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
            List<LoanStatQuery>list = loanStatService.queryCrossLoanStatByMonth(map);
            if (list != null) {
                fn(list);
            }
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
            List<LoanStatQuery> list = loanStatService.queryCrossLoanStatByQuarter(map);
            if (list != null) {
                fn(list);
            }
        }
        if ("3".equals(ttype)) {
            Date startDate = DateUtil.getDate(Integer.parseInt(startYear2), 1, 1);
            Date endDate = DateUtil.getDate(Integer.parseInt(endYear2), 12, 31,23,59,59);
            map.put("startDate",startDate);
            map.put("endDate",endDate);
            map.put("teamGroupId",groupPermit);
            List<LoanStatQuery>list = loanStatService.queryCrossLoanStatByYear(map);
            if (list != null) {
                fn(list);
            }
        }
    }

    public void fn(List<LoanStatQuery> list) {
            String[] result1 = new String[list.size()];
            Object[] data1 = new Object[list.size()];
            Object[] data2 = new Object[list.size()];
            Object[] data3 = new Object[list.size()];
            Object[] data4 = new Object[list.size()];
            Object[] data5 = new Object[list.size()];

            JSONArray result2 = new JSONArray();
            JSONObject resultObject = new JSONObject();
            for (int i = 0; i < list.size(); i++) {
               LoanStatQuery l = list.get(i);
                result1[i] = l.getTime();

                data1[i] = l.getLoanTotalNum();
                data2[i] = l.getLoanSuccessNum();
                data3[i] = l.getLoanHasClearance();
                data4[i] = l.getApprovalRefuse();
                data5[i] = l.getOtherRefuse();
            }
            JSONObject json = new JSONObject();
            json.put("name", "贷款总数");
            json.put("data", data1);
            result2.add(json);
            JSONObject json2 = new JSONObject();
            json2.put("name", "成功放贷");
            json2.put("data", data2);
            result2.add(json2);
            JSONObject json3 = new JSONObject();
            json3.put("name", "已结清");
            json3.put("data", data3);
            result2.add(json3);
            JSONObject json4 = new JSONObject();
            json4.put("name", "审批拒绝");
            json4.put("data", data4);
            result2.add(json4);
            JSONObject json5 = new JSONObject();
            json5.put("name", "其他拒绝");
            json5.put("data", data5);
            result2.add(json5);
            resultObject.put("a",result1);
            resultObject.put("b",result2);
            renderJson(true,"",resultObject);
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
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
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
}