package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.domain.loan.LoanStatQuery;
import banger.framework.util.DateUtil;
import banger.framework.util.FileUtil;
import banger.service.intf.ILoanStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("loanExport")
public class LoanExportController extends BaseController {
    @Autowired
    private ILoanStatService loanStatService;

    @RequestMapping("exportByUserId")
    @NoLoginInterceptor
    public void exportByUserIdAndYm(HttpServletRequest request, HttpServletResponse response,
                                    @RequestParam(value ="userId",defaultValue ="") Integer userId,
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
                                    @RequestParam(value = "loanType", defaultValue = "") String loanType)throws IOException {
        List<LoanStatQuery> list=null;
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("isLocalPerson",isLocalPerson);
        map.put("isHaveHouse",isHaveHouse);
        map.put("loanType",loanType);
        addMapInfo(map);
        if("1".equals(ttype)){
            Date startDate= DateUtil.getDate(Integer.parseInt(startYear),Integer.parseInt(startMonth),1);
            Date endDate=DateUtil.getDate(Integer.parseInt(endYear),Integer.parseInt(endMonth),31);
            map.put("startDate",startDate);
            map.put("endDate",endDate);
            map.put("userId",userId);
            list=loanStatService.queryLoanStatListByUserId(map);
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
            endDate=DateUtil.getDate(Integer.parseInt(endYear1),n,31);
            map.put("startDate",startDate);
            map.put("endDate",endDate);
            map.put("userId",userId);
            list=loanStatService.queryLoanStatListByUserIdAndQuarter(map);
        }

        if("3".equals(ttype)){
            Date startDate= DateUtil.getDate(Integer.parseInt(startYear2),1,1);
            Date endDate=DateUtil.getDate(Integer.parseInt(endYear2),12,31);
            map.put("startDate",startDate);
            map.put("endDate",endDate);
            map.put("userId",userId);
            list=loanStatService.queryLoanStatListByUserIdAndYear(map);
        }
        List<List<String>> reportList = new ArrayList<List<String>>();
        for (int i = 0; i < list.size(); i++) {
            LoanStatQuery loan=list.get(i);
            List<String> colunms = new ArrayList<String>();
            colunms.add(loan.getTime().toString());
            colunms.add(loan.getUserName().toString());
            colunms.add(loan.getLoanTotalNum().toString());
            colunms.add(loan.getLoanSuccessNum().toString());
            colunms.add(loan.getLoanPercent().toString());
            colunms.add(loan.getLoanTotalMoneyW().toString());
            colunms.add(loan.getLoanAvgW().toString());
            colunms.add(loan.getLoanAvgRatio().toString());
            colunms.add(loan.getLoanWeekNum().toString());
            colunms.add(loan.getLoanWeekSum().toString());
            colunms.add(loan.getLoanHasClearance().toString());
            colunms.add(loan.getApprovalRefuse().toString());
            colunms.add(loan.getOtherRefuse().toString());
            reportList.add(colunms);
        }
        List<String> head = new ArrayList<String>();
        head.add("时间段");
        head.add("客户经理");
        head.add("贷款总数(笔)");
        head.add("成功放贷(笔)");
        head.add("贷款通过率(%)");
        head.add("贷款总金额(万元)");
        head.add("贷款平均金额(万元)");
        head.add("累计平均利率(%)");
        head.add("本周放款笔数(笔)");
        head.add("本周放款总额(万元)");
        head.add("已结清(笔)");
        head.add("审批拒绝(笔)");
        head.add("其他拒绝(笔)");
//        head.add();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
        String fileName="exportReport"+sdf.format(new Date());
        String path = getRootPath()+"exportReport";
        String filePath = FileUtil.contact(path,fileName);
        File csvFile = loanStatService.createCSVFile(head, reportList, path, fileName);
        downFile(response,csvFile.getPath(),fileName+".csv");
    }

    @RequestMapping("exportByTeamGroupId")
    @NoLoginInterceptor
    public void exportByTeamGroupIdAndYm(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam(value="teamGroupId",defaultValue ="-1") Integer teamGroupId,
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
                                         @RequestParam(value = "loanType", defaultValue = "") String loanType)throws IOException {
        List<LoanStatQuery> list=null;
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("isLocalPerson",isLocalPerson);
        map.put("isHaveHouse",isHaveHouse);
        map.put("loanType",loanType);
        addMapInfo(map);
        if("1".equals(ttype)){
            Date startDate= DateUtil.getDate(Integer.parseInt(startYear),Integer.parseInt(startMonth),1);
            Date endDate=DateUtil.getDate(Integer.parseInt(endYear),Integer.parseInt(endMonth),31);
            map.put("startDate",startDate);
            map.put("endDate",endDate);
            map.put("teamGroupId",teamGroupId);
            list=loanStatService.queryLoanStatListByTeamGroupId(map);
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
            endDate=DateUtil.getDate(Integer.parseInt(endYear1),n,31);
            map.put("startDate",startDate);
            map.put("endDate",endDate);
            map.put("teamGroupId",teamGroupId);
            list=loanStatService.queryLoanStatListByTeamGroupIdAndQuarter(map);
        }

        if("3".equals(ttype)){
            Date startDate= DateUtil.getDate(Integer.parseInt(startYear2),1,1);
            Date endDate=DateUtil.getDate(Integer.parseInt(endYear2),12,31);
            map.put("startDate",startDate);
            map.put("endDate",endDate);
            map.put("teamGroupId",teamGroupId);
            list=loanStatService.queryLoanStatListByTeamGroupIdAndYear(map);
        }
        List<List<String>> reportList = new ArrayList<List<String>>();
        for (int i = 0; i < list.size(); i++) {
            LoanStatQuery loan=list.get(i);
            List<String> colunms = new ArrayList<String>();
            colunms.add(loan.getTime().toString());
            colunms.add(loan.getGroupName().toString());
            colunms.add(loan.getLoanTotalNum().toString());
            colunms.add(loan.getLoanSuccessNum().toString());
            colunms.add(loan.getLoanPercent().toString());
            colunms.add(loan.getLoanTotalMoneyW().toString());
            colunms.add(loan.getLoanAvgW().toString());
            colunms.add(loan.getLoanAvgRatio().toString());
            colunms.add(loan.getLoanWeekNum().toString());
            colunms.add(loan.getLoanWeekSum().toString());
            colunms.add(loan.getLoanHasClearance().toString());
            colunms.add(loan.getApprovalRefuse().toString());
            colunms.add(loan.getOtherRefuse().toString());
            reportList.add(colunms);
        }
        List<String> head = new ArrayList<String>();
        head.add("时间段");
        head.add("团队");
        head.add("贷款总数(笔)");
        head.add("成功放贷(笔)");
        head.add("贷款通过率(%)");
        head.add("贷款总金额(万元)");
        head.add("贷款平均金额(万元)");
        head.add("累计平均利率(%)");
        head.add("本周放款笔数(笔)");
        head.add("本周放款总额(万元)");
        head.add("已结清(笔)");
        head.add("审批拒绝(笔)");
        head.add("其他拒绝(笔)");
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
        String fileName="exportReport"+sdf.format(new Date());
        String path = getRootPath()+"exportReport";
        String filePath = FileUtil.contact(path,fileName);
        File csvFile = loanStatService.createCSVFile(head, reportList, path, fileName);
        downFile(response,csvFile.getPath(),fileName+".csv");
    }

    @RequestMapping("exportCross")
    @NoLoginInterceptor
    public void exportCrossByYm(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam(value="groupPermit",defaultValue ="") String groupPermit,
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
                                @RequestParam(value = "loanType", defaultValue = "") String loanType)throws IOException {
        List<LoanStatQuery> list=null;
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("isLocalPerson",isLocalPerson);
        map.put("isHaveHouse",isHaveHouse);
        map.put("loanType",loanType);
        addMapInfo(map);
        if("1".equals(ttype)){
            Date startDate= DateUtil.getDate(Integer.parseInt(startYear),Integer.parseInt(startMonth),1);
            Date endDate=DateUtil.getDate(Integer.parseInt(endYear),Integer.parseInt(endMonth),31);
            map.put("startDate",startDate);
            map.put("endDate",endDate);
            map.put("teamGroupId",groupPermit);
             list=loanStatService.queryCrossLoanStatListByMonth(map);
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
            endDate=DateUtil.getDate(Integer.parseInt(endYear1),n,31);
            map.put("startDate",startDate);
            map.put("endDate",endDate);
            map.put("teamGroupId",groupPermit);
             list=loanStatService.queryCrossLoanStatListByQuarter(map);
        }

        if("3".equals(ttype)){
            Date startDate= DateUtil.getDate(Integer.parseInt(startYear2),1,1);
            Date endDate=DateUtil.getDate(Integer.parseInt(endYear2),12,31);
            map.put("startDate",startDate);
            map.put("endDate",endDate);
            map.put("teamGroupId",groupPermit);
            list=loanStatService.queryCrossLoanStatListByYear(map);
        }
        List<List<String>> reportList = new ArrayList<List<String>>();
        for (int i = 0; i < list.size(); i++) {
            LoanStatQuery loan=list.get(i);
            List<String> colunms = new ArrayList<String>();
            colunms.add(loan.getTime().toString());
            colunms.add(loan.getGroupName().toString());
            colunms.add(loan.getLoanTotalNum().toString());
            colunms.add(loan.getLoanSuccessNum().toString());
            colunms.add(loan.getLoanPercent().toString());
            colunms.add(loan.getLoanTotalMoneyW().toString());
            colunms.add(loan.getLoanAvgW().toString());
            colunms.add(loan.getLoanAvgRatio().toString());
            colunms.add(loan.getLoanWeekNum().toString());
            colunms.add(loan.getLoanWeekSum().toString());
            colunms.add(loan.getLoanHasClearance().toString());
            colunms.add(loan.getApprovalRefuse().toString());
            colunms.add(loan.getOtherRefuse().toString());
            reportList.add(colunms);
        }
        List<String> head = new ArrayList<String>();
        head.add("时间段");
        head.add("团队");
        head.add("贷款总数(笔)");
        head.add("成功放贷(笔)");
        head.add("贷款通过率(%)");
        head.add("贷款总金额(万元)");
        head.add("贷款平均金额(万元)");
        head.add("累计平均利率(%)");
        head.add("本周放款笔数(笔)");
        head.add("本周放款总额(万元)");
        head.add("已结清(笔)");
        head.add("审批拒绝(笔)");
        head.add("其他拒绝(笔)");
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
        String fileName="exportReport"+sdf.format(new Date());
        String path = getRootPath()+"exportReport";
        String filePath = FileUtil.contact(path,fileName);
        File csvFile = loanStatService.createCSVFile(head, reportList, path, fileName);
        downFile(response,csvFile.getPath(),fileName+".csv");
    }


    private void downFile(HttpServletResponse response, String path,String filename) {
        File file = new File(path);
        try {
            if (file.exists()) {


                InputStream ins = new FileInputStream(file);
                BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面


                OutputStream outs = response.getOutputStream();// 获取文件输出IO流
                BufferedOutputStream bouts = new BufferedOutputStream(outs);
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader(
                        "Content-disposition",
                        "attachment;filename="
                                + filename);// 设置头部信息
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                // 开始向网络传输文件流
                while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
                    bouts.write(buffer, 0, bytesRead);
                }
                bouts.flush();// 这里一定要调用flush()方法
                ins.close();
                bins.close();
                outs.close();
                bouts.close();
            }
        } catch (IOException e) {
            log.error("文件下载出错", e);
        }finally {
            if(file.exists()){
                file.delete();
            }
        }
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
}
