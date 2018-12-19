package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.common.tools.ServerRealPathUtil;
import banger.domain.loan.LoanCountBean;
import banger.domain.loan.QueryReport_Query;
import banger.domain.system.SysDataDictCol;
import banger.framework.pagesize.IPageList;
import banger.framework.util.FileUtil;
import banger.framework.util.StringUtil;
import banger.moduleIntf.IDataDictColProvider;
import banger.moduleIntf.IPermissionModule;
import banger.service.intf.IQueryReportService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("queryReport")
public class QueryReportController extends BaseController {


    @Autowired
    IDataDictColProvider iDataDictColProvider;
    @Autowired
    private IQueryReportService iQueryReportService;
    @Autowired
    private IPermissionModule permissionModule;
    /**
     * 查询报表页面
     * @return
     */
    @RequestMapping("getQueryReportPage")
    public String getQueryReportPage() {
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
        return "loan/vm/report/queryReport";
    }

    @RequestMapping("getQueryReportData")
    public String getQueryReportData(@RequestParam(value="applyStartDate",defaultValue ="") String applyStartDate,
                                     @RequestParam(value="applyEndDate",defaultValue ="") String applyEndDate,
                                     @RequestParam(value="investStartDate",defaultValue ="") String investStartDate,
                                     @RequestParam(value="investEndDate",defaultValue ="") String investEndDate,
                                     @RequestParam(value="approvalStartDate",defaultValue ="") String approvalStartDate,
                                     @RequestParam(value="approvalEndDate",defaultValue ="") String approvalEndDate,
                                     @RequestParam(value="loanStartDate",defaultValue ="") String loanStartDate,
                                     @RequestParam(value="loanEndDate",defaultValue ="") String loanEndDate,
                                     @RequestParam(value="role",defaultValue ="") String role,
                                     @RequestParam(value="userId",defaultValue ="") String userId,
                                     @RequestParam(value="teamGroupId",defaultValue ="") String teamGroupId,
                                     @RequestParam(value="groupPermit",defaultValue ="") String groupPermit){
        Map map = new HashMap();
        map.put("applyStartDate",applyStartDate);
        map.put("applyEndDate",applyEndDate);
        map.put("investStartDate",investStartDate);
        map.put("investEndDate",investEndDate);
        map.put("approvalStartDate",approvalStartDate);
        map.put("approvalEndDate",approvalEndDate);
        map.put("loanStartDate",loanStartDate);
        map.put("loanEndDate",loanEndDate);

        if("1".equals(role)){
            map.put("userId",userId);
        }else if("2".equals(role)){
            map.put("teamGroupId",teamGroupId);
        }else {
            map.put("teamGroupId",groupPermit);
        }
        List<QueryReport_Query> list = iQueryReportService.queryReportByCondition(map);
        String loginUser = this.getLoginInfo().getUserName();
        dealList(list);
        setAttribute("list",list);
        setAttribute("loginUser",loginUser);
        setAttribute("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        return "/loan/vm/report/queryReportData";
    }

    @RequestMapping("getQueryReportDataJson")
    @ResponseBody
    public void getQueryReportDataJson(@RequestParam(value="applyStartDate",defaultValue ="") String applyStartDate,
                                     @RequestParam(value="applyEndDate",defaultValue ="") String applyEndDate,
                                     @RequestParam(value="investStartDate",defaultValue ="") String investStartDate,
                                     @RequestParam(value="investEndDate",defaultValue ="") String investEndDate,
                                     @RequestParam(value="approvalStartDate",defaultValue ="") String approvalStartDate,
                                     @RequestParam(value="approvalEndDate",defaultValue ="") String approvalEndDate,
                                     @RequestParam(value="loanStartDate",defaultValue ="") String loanStartDate,
                                     @RequestParam(value="loanEndDate",defaultValue ="") String loanEndDate,
                                     @RequestParam(value="role",defaultValue ="") String role,
                                     @RequestParam(value="userId",defaultValue ="") String userId,
                                     @RequestParam(value="teamGroupId",defaultValue ="") String teamGroupId,
                                     @RequestParam(value="groupPermit",defaultValue ="") String groupPermit){
        Map map = new HashMap();
        map.put("applyStartDate",applyStartDate);
        map.put("applyEndDate",applyEndDate);
        map.put("investStartDate",investStartDate);
        map.put("investEndDate",investEndDate);
        map.put("approvalStartDate",approvalStartDate);
        map.put("approvalEndDate",approvalEndDate);
        map.put("loanStartDate",loanStartDate);
        map.put("loanEndDate",loanEndDate);

        if("1".equals(role)){
            map.put("userId",userId);
        }else if("2".equals(role)){
            map.put("teamGroupId",teamGroupId);
        }else {
            map.put("teamGroupId",groupPermit);
        }
        IPageList<QueryReport_Query> list = iQueryReportService.queryReportByCondition(map,this.getPage());
        dealList(list);
        renderText(JsonUtil.toJson(list, "id", "loanStatus,creditDate,customerName,approvalMoney,creditMoney,loanLimit,loanRatio,repayWay,guarantorWay,applyUserName,investigateUserName," +
                "productName,loanType,isNew,isAgriculture,customerLevel,industryCode,businessScope,isReferred,referredUser,referredDept,loanContractNum,loanAccountNo,loanContractBegin," +
                "loanContractEnd,isLocal,iouAmount,balanceAmount,businessLineName,loanRatioDate,loanEndDate,contractCode,orientationName").toString());

    }

    @RequestMapping("exportFile")
    public String exportFile(@RequestParam(value="applyStartDate",defaultValue ="") String applyStartDate,
                           @RequestParam(value="applyEndDate",defaultValue ="") String applyEndDate,
                           @RequestParam(value="investStartDate",defaultValue ="") String investStartDate,
                           @RequestParam(value="investEndDate",defaultValue ="") String investEndDate,
                           @RequestParam(value="approvalStartDate",defaultValue ="") String approvalStartDate,
                           @RequestParam(value="approvalEndDate",defaultValue ="") String approvalEndDate,
                           @RequestParam(value="loanStartDate",defaultValue ="") String loanStartDate,
                           @RequestParam(value="loanEndDate",defaultValue ="") String loanEndDate,
                           @RequestParam(value="role",defaultValue ="") String role,
                           @RequestParam(value="userId",defaultValue ="") String userId,
                           @RequestParam(value="teamGroupId",defaultValue ="") String teamGroupId,
                           @RequestParam(value="groupPermit",defaultValue ="") String groupPermit){
        try {
            Map map = new HashMap();
            map.put("applyStartDate",applyStartDate);
            map.put("applyEndDate",applyEndDate);
            map.put("investStartDate",investStartDate);
            map.put("investEndDate",investEndDate);
            map.put("approvalStartDate",approvalStartDate);
            map.put("approvalEndDate",approvalEndDate);
            map.put("loanStartDate",loanStartDate);
            map.put("loanEndDate",loanEndDate);

            if("1".equals(role)){
                map.put("userId",userId);
            }else if("2".equals(role)){
                map.put("teamGroupId",teamGroupId);
            }else {
                map.put("teamGroupId",groupPermit);
            }
            List<QueryReport_Query> list = iQueryReportService.queryReportByCondition(map);
            List<LoanCountBean> list1 = iQueryReportService.getLoanCountByCondition(map);
            dealList(list);
//            expotFile(list);
            downResultExcel(list,list1);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return  null;
    }

    private void expotFile(List<QueryReport_Query> list) {
        List<List<String>> reportList = new ArrayList<List<String>>();
        for (int i = 0; i < list.size(); i++) {
            QueryReport_Query query=list.get(i);
            List<String> colunms = new ArrayList<String>();
//            colunms.add(query.getLoanType());
            colunms.add(query.getCreditDate()+"\t");
            colunms.add(query.getIsLocal());
            colunms.add(query.getCustomerName());
            colunms.add(query.getApprovalMoney());
            colunms.add(query.getCreditMoney());
            colunms.add(query.getIouAmount());
            colunms.add(query.getLoanLimit());
            colunms.add(query.getLoanRatio());
            colunms.add(query.getRepayWay());
            colunms.add(query.getGuarantorWay());
            colunms.add(query.getApplyUserName());
            colunms.add(query.getInvestigateUserName());
            colunms.add(query.getProductName());
            colunms.add(query.getLoanType());
            colunms.add(query.getIsNew());
            colunms.add(query.getFiveMarks());
            colunms.add(query.getIsAgriculture());
            colunms.add(query.getBusinessLineName());
            colunms.add(query.getCustomerLevel());
            colunms.add(query.getIndustryCode());
            colunms.add(query.getOrientationName());
            colunms.add(query.getIsReferred());
            colunms.add(query.getReferredUser());
            colunms.add(query.getReferredDept());
            colunms.add(query.getLoanContractNum()+"\t");
            colunms.add(query.getLoanAccountNo());
            colunms.add(query.getContractCode());
            colunms.add(query.getLoanContractBegin());
            colunms.add(query.getLoanContractEnd());
            colunms.add(query.getLoanRatioDate());
            colunms.add(query.getLoanEndDate());
            reportList.add(colunms);
        }
        List<String> head = new ArrayList<String>();
//        head.add("贷款阶段");
        head.add("放款日期");
        head.add("是否居民");
        head.add("客户姓名");
        head.add("决议金额(元)");
        head.add("合同金额(元)");
        head.add("放款金额(元)");
        head.add("贷款期限(月)");
        head.add("贷款利率(%)");
        head.add("还款方式");
        head.add("担保方式");
        head.add("客户经理-申请");
        head.add("客户经理-调查");
        head.add("产品名称");
        head.add("贷款类型");
        head.add("新增/转贷");
        head.add("五级分类");
        head.add("是否涉农");
        head.add("业务品种");
        head.add("客户类型");
        head.add("所属行业");
        head.add("经营范围");
        head.add("投向名称");
        head.add("是否转介");
        head.add("转介人员");
        head.add("转介支行");
        head.add("合同号");
        head.add("合同编码");
        head.add("贷款账号");
        head.add("合同开始时间");
        head.add("合同结束时间");
        head.add("放款开始时间");
        head.add("放款结束时间");
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
        String fileName="exportReport"+sdf.format(new Date());
        String path = getRootPath()+"exportReport";
        String filePath = FileUtil.contact(path, fileName);
        File csvFile = null;
        try {
            csvFile = createCSVFile(head, reportList, path, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        downFile(getResponse(),csvFile.getPath(),fileName+".csv");
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

    private void dealList(List<QueryReport_Query> list) {
        for(QueryReport_Query query:list){
            if(!StringUtil.isNullOrEmpty(query.getApplyUserName())) {
                query.setApplyUserName(permissionModule.getPmsUserByUserId(Integer.valueOf(query.getApplyUserName())).getUserName());
            }
            if(!StringUtil.isNullOrEmpty(query.getInvestigateUserName())) {
                query.setInvestigateUserName(permissionModule.getPmsUserByUserId(Integer.valueOf(query.getInvestigateUserName())).getUserName());
            }
            query.setRepayWay(getDictName("CD_REPAYMENT_MODE",query.getRepayWay()));
            query.setGuarantorWay(getDictName("CD_GUARANTEE_MODE",query.getGuarantorWay()));
//            query.setProductName(getDictName("CD_PRODUCT_TYPE",query.getProductName()));
            query.setIsNew(getDictName("CD_IS_NEW",query.getIsNew()));
            query.setIsAgriculture(getDictName("CD_IS_AGRICULTURE",query.getIsAgriculture()));
            query.setIsReferred(getDictName("SHI_FOU_1",query.getIsReferred()));
            query.setCustomerLevel(getDictName("CD_CUSTOMER_LEVEL",query.getCustomerLevel()));
            query.setIndustryCode(getDictName("CD_INDUSTRY_CODE",query.getIndustryCode()));
        }
    }

    private String getDictName(String dataDicName,String dictValue){
        Map condition = new HashMap();
        condition.put("dataDictName",dataDicName);
        condition.put("dictValue",dictValue);
        if(!StringUtil.isNullOrEmpty(dictValue)) {
            List<SysDataDictCol> list = iDataDictColProvider.queryDataDictColList(condition);
            if (!CollectionUtils.isEmpty(list)) {
                return list.get(0).getDictName();
            }
        }
        return "";
    }

    private File createCSVFile(List<String> head, List<List<String>> dataList, String outPutPath, String filename) throws IOException {
        File csvFile = null;
        BufferedWriter csvWtriter = null;
        try {
            csvFile = new File(outPutPath + File.separator + filename + ".csv");
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            // GB2312使正确读取分隔符","
            csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    csvFile), "GB2312"), 1024);
            // 写入文件头部
            writeRow(head, csvWtriter);

            // 写入文件内容
            for (List<String> row : dataList) {
                writeRow(row, csvWtriter);
            }
            csvWtriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvWtriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }

    private void writeRow(List<String> row, BufferedWriter csvWriter) throws IOException {
        // 写入文件头部
        for (String data : row) {
            StringBuffer sb = new StringBuffer();
            String rowStr = sb.append("\"").append(data).append("\",").toString();
            csvWriter.write(rowStr);
        }
        csvWriter.newLine();
    }

    public void downResultExcel(List<QueryReport_Query> list,List<LoanCountBean> list1) {
        try {
            //贷款id
            String timePath = new SimpleDateFormat("yyyyMMddHmsS").format(new Date());
            String path = FileUtil.contact(this.getSession().getServletContext().getRealPath("/"), "tmp_file/export/贷款明细报表" + timePath + ".xls");
            String dir = path.substring(0, path.lastIndexOf("/")); // 取出目录
            File file = new File(dir);
            // 检查文件是否存在
            File obj = new File(path);
            if (!file.exists()) { // 目录不存在，则创建相应的目录
                file.mkdirs();
                if (!obj.exists()) // 接下来创建具体文件
                    obj.createNewFile(); // 就是在这个点抛出异常
            }
            getResolutionTableExportFile(list,list1, path);
            // 写流文件到前端浏览器
            ServletOutputStream out = this.getResponse().getOutputStream();
            this.getResponse().setHeader("Content-Disposition",
                    "attachment; filename=" + new String("贷款明细表.xls".getBytes("GB2312"), "ISO8859-1"));
            this.getResponse().setContentType("application/force-download");// 设置强制下载不打开
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;

            bis = new BufferedInputStream(new FileInputStream(path));
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bis.close();
            bos.close();
            obj.delete();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public void getResolutionTableExportFile(List<QueryReport_Query> list ,List<LoanCountBean> list1,String path){
        try{
            //取得贷款信息

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

            String rtefPath = this.getRequest().getSession().getServletContext().getResource("/template").getFile();
            if (!FileUtil.isExistFilePath(rtefPath)) {
                rtefPath = ServerRealPathUtil.getServerRealPath() + File.separator + "template";
            }
            rtefPath = rtefPath + File.separator + "queryReport.xls";
            //取Excel模板
            POIFSFileSystem fs=new POIFSFileSystem(new FileInputStream(rtefPath));
            HSSFWorkbook book = new HSSFWorkbook(fs);
            //填充第一个工作表
            HSSFSheet sheet = book.getSheetAt(0);
            for(int i=0;i<list.size();i++) {
                HSSFRow row = sheet.createRow(i+1);
//                row.createCell(0).setCellValue(list.get(i).getLoanStatus());
                row.createCell(0).setCellValue(list.get(i).getCreditDate());
                row.createCell(1).setCellValue(list.get(i).getIsLocal());
                row.createCell(2).setCellValue(list.get(i).getCustomerName());
                row.createCell(3).setCellValue(list.get(i).getApprovalMoney());
                row.createCell(4).setCellValue(list.get(i).getCreditMoney());
                row.createCell(5).setCellValue(list.get(i).getIouAmount());
                row.createCell(6).setCellValue(list.get(i).getLoanLimit());
                row.createCell(7).setCellValue(list.get(i).getLoanRatio());
                row.createCell(8).setCellValue(list.get(i).getRepayWay());
                row.createCell(9).setCellValue(list.get(i).getGuarantorWay());
                row.createCell(10).setCellValue(list.get(i).getApplyUserName());
                row.createCell(11).setCellValue(list.get(i).getInvestigateUserName());
                row.createCell(12).setCellValue(list.get(i).getProductName());
                row.createCell(13).setCellValue(list.get(i).getLoanType());
                row.createCell(14).setCellValue(list.get(i).getIsNew());
                row.createCell(15).setCellValue(list.get(i).getFiveMarks());
                row.createCell(16).setCellValue(list.get(i).getIsAgriculture());
                row.createCell(17).setCellValue(list.get(i).getBusinessLineName());
                row.createCell(18).setCellValue(list.get(i).getCustomerLevel());
                row.createCell(19).setCellValue(list.get(i).getIndustryCode());
                row.createCell(20).setCellValue(list.get(i).getBusinessScope());
                row.createCell(21).setCellValue(list.get(i).getOrientationName());
                row.createCell(22).setCellValue(list.get(i).getIsReferred());
                row.createCell(23).setCellValue(list.get(i).getReferredUser());
                row.createCell(24).setCellValue(list.get(i).getReferredDept());
                row.createCell(25).setCellValue(list.get(i).getLoanContractNum());
                row.createCell(26).setCellValue(list.get(i).getContractCode());
                row.createCell(27).setCellValue(list.get(i).getLoanAccountNo());
                row.createCell(28).setCellValue(list.get(i).getLoanContractBegin());
                row.createCell(29).setCellValue(list.get(i).getLoanContractEnd());
                row.createCell(30).setCellValue(list.get(i).getLoanRatioDate());
                row.createCell(31).setCellValue(list.get(i).getLoanEndDate());
            }
            //工资表
            HSSFSheet sheet1 = book.getSheetAt(1);
            for(int i=0;i<list1.size();i++) {
                HSSFRow row = sheet1.createRow(i+1);
                row.createCell(0).setCellValue(list1.get(i).getUserName());
                row.createCell(1).setCellValue(list1.get(i).getJyd1());
                row.createCell(2).setCellValue(list1.get(i).getXfd1());
                row.createCell(3).setCellValue(list1.get(i).getJyd2());
                row.createCell(4).setCellValue(list1.get(i).getXfd2());
            }
            FileOutputStream os = new FileOutputStream(new File(path));
            book.write(os);
            os.close();
        }catch(Exception e){
            log.error("", e);
        }
    }




}