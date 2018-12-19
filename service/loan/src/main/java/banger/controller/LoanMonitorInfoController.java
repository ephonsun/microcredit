package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.common.tools.JsonUtil;
import banger.domain.enumerate.LoanMonitorState;
import banger.domain.enumerate.LoanMonitorType;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanInfoAddedFiles;
import banger.domain.loan.LoanMonitorInfo;
import banger.domain.loan.LoanMonitorInfoExtend;
import banger.domain.system.SysDataDictCol;
import banger.framework.util.XSSFilterUtil;
import banger.moduleIntf.IDataDictColProvider;
import banger.service.intf.IApplyInfoService;
import banger.service.intf.IInfoAddedFilesService;
import banger.service.intf.IMonitorInfoService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/loanMonitorInfo")
public class LoanMonitorInfoController extends BaseController {
    @Autowired
    private IMonitorInfoService monitorInfoService;
    @Autowired
    private IApplyInfoService iApplyInfoService;
    @Autowired
    private IDataDictColProvider iDataDictColProvider;
    @Autowired
    private IInfoAddedFilesService infoAddedFilesService;
    @Autowired
    private IInfoAddedFilesService iInfoAddedFilesService;

    @Value("${file_root_path}")
    private String fileRootPath;

    /**
     * 跳转贷款监控详情页
     */
    @RequestMapping("/getMonitorInfoPage")
    public ModelAndView getMonitorInfoPage(@RequestParam("loanId") Integer loanId) {
        ModelAndView mv = new ModelAndView("/loan/vm/loanMonitorInfoLIst");
        LoanApplyInfo loanApplyInfo = iApplyInfoService.getApplyInfoById(loanId);
        HashMap<String, Object> condition = new HashMap<String, Object>();
        condition.put("dataDictName", "CD_LOAN_AFTERLOAN_STATUS");
        List<SysDataDictCol> sysDataDictCols = iDataDictColProvider.queryDataDictColList(condition);
        mv.addObject("list", sysDataDictCols);
        mv.addObject("afterState", loanApplyInfo.getLoanAfterState());
        //回访类型
        condition.clear();
        condition.put("dataDictName", "CD_MONITOR_REVISIT_TYPE");
        List<SysDataDictCol> reType = iDataDictColProvider.queryDataDictColList(condition);
        //查询是否已经有监控
        Boolean isMoniting = monitorInfoService.isMoniting(loanId);
        mv.addObject("isMoniting", !isMoniting);
        mv.addObject("module", getParameter("module"));
        mv.addObject("showApply", getParameter("showApply"));
        mv.addObject("reType", reType);
        mv.addObject("loanId", loanId);
        mv.addObject("userId",getLoginInfo().getUserId());
        return mv;
    }

    /**
     * 查询监控信息
     */
    @RequestMapping("/queryMonitorInfo")
    @ResponseBody
    public void queryMonitorInfo(@RequestParam("loanId") Integer loanId) {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("loanId", loanId);
        List<LoanMonitorInfoExtend> list = monitorInfoService.queryMonitorInfoExtend(condition);
        Boolean isManager=isCustomerManager();
        for(int i=0;i<list.size();i++){
            list.get(i).setManager(isManager);
        }
        renderText(JsonUtil.toJson(list, "id", "manager,loanId,loanMonitorDate,loanMonitorType,loanMonitorUserId,loanCompleteDate,loanRevisitType,loanResultContent,loanMonitorState,userName,dictName,reType").toString());
    }

    /**
     * 跳转修改页面
     *
     * @return
     */
    @RequestMapping("/getUpdatePage")
    public ModelAndView getUpdatePage(@RequestParam("id") Integer id) {
        LoanMonitorInfo monitorInfoById = monitorInfoService.getMonitorInfoById(id);
        ModelAndView mv = new ModelAndView("/loan/vm/loanMonitorInfoUpdate");
        Map<String, Object> condition = new HashMap<String, Object>();
        LoanMonitorInfo infoById = monitorInfoService.getMonitorInfoById(id);
        //回访类型
        condition.put("dataDictName", "CD_MONITOR_REVISIT_TYPE");
        List<SysDataDictCol> dictColList = iDataDictColProvider.queryDataDictColList(condition);
        //监控类型
        LoanMonitorType[] values = LoanMonitorType.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].type.equals(infoById.getLoanMonitorType())) {
                infoById.setLoanMonitorType(values[i].typeName);
            }
        }
        //资料
        condition.clear();
        condition.put("monitorId", id);
        condition.put("isDel", 0);
        List<LoanInfoAddedFiles> fileList = infoAddedFilesService.queryInfoAddedFilesList(condition);

        mv.addObject("fileList", fileList);
        mv.addObject("dictColList", dictColList);
        mv.addObject("loanMonitorInfo", infoById);
        return mv;
    }

    /**
     * 上传
     */
    @RequestMapping("/uploadFile")
    @ResponseBody
    public void uploadFile(@RequestParam MultipartFile file, @RequestParam("loanId") Integer loanId, @RequestParam("monitorId") Integer monitorId) throws Exception {
        LoanInfoAddedFiles loanInfoAddedFiles = new LoanInfoAddedFiles();
        loanInfoAddedFiles.setBeginTime(new Date());
        //上传文件
        Long begin = System.currentTimeMillis();
        if (null != file) {
            String path = fileRootPath + "/monitor/";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String fileName = file.getOriginalFilename();
            File f = new File(path + fileName);
            FileUtils.copyToFile(file.getInputStream(), f);
        }
        Long end = System.currentTimeMillis();
        loanInfoAddedFiles.setEndTime(new Date());

        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String name = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));

        //数据库

        loanInfoAddedFiles.setLoanId(loanId);
        loanInfoAddedFiles.setLoanProcessType("monitor");
        loanInfoAddedFiles.setIsDel(0);
        loanInfoAddedFiles.setFileId(name);

        loanInfoAddedFiles.setFileType("Other");
        loanInfoAddedFiles.setFileFix(suffix);
        loanInfoAddedFiles.setFileName(file.getOriginalFilename());
        loanInfoAddedFiles.setFilePath(fileRootPath + "/monitor/");
        loanInfoAddedFiles.setFileSize(Integer.valueOf(file.getSize() + ""));
        loanInfoAddedFiles.setFileSrcName(file.getOriginalFilename());
        loanInfoAddedFiles.setCallTime(Integer.valueOf((end - begin) + ""));
        loanInfoAddedFiles.setMonitorId(monitorId);
        Integer loginUserId = getLoginInfo().getUserId();

        infoAddedFilesService.insertInfoAddedFiles(loanInfoAddedFiles, loginUserId);
//        Integer is = loanInfoAddedFiles.getId();
//        setAttribute("loanInfoFilesId",loanInfoAddedFiles.getId());
    }

    /**
     * 文件下载
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @NoLoginInterceptor
    @RequestMapping("/downloadFile")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = Integer.valueOf(getParameter("id"));
        LoanInfoAddedFiles infoAddedFilesById = infoAddedFilesService.getInfoAddedFilesById(id);
        if (null == infoAddedFilesById) {
            return;
        }
        String path = infoAddedFilesById.getFilePath();
        File file = new File(path + "\\" + infoAddedFilesById.getFileSrcName());

        response.setCharacterEncoding("utf-8");

        response.setContentType("application/force-download");// 设置强制下载不打开
        String fileName = URLEncoder.encode(infoAddedFilesById.getFileSrcName(), "UTF-8");
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
        OutputStream os = response.getOutputStream();

        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            byte[] buffer = new byte[2048];
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis, 2048);
            int i;
            while ((i = bis.read(buffer)) > 0) {
                os.write(buffer, 0, i);
            }
            os.flush();
        } catch (Exception e) {
            log.error("downloadFile error", e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 删除文件
     *
     * @param id
     */
    @RequestMapping("deleteFile")
    public void deleteFile(@RequestParam("id") Integer id) {
        LoanInfoAddedFiles infoAddedFilesById = infoAddedFilesService.getInfoAddedFilesById(id);
        if (infoAddedFilesById != null) {
            File f = new File(infoAddedFilesById.getFilePath() + infoAddedFilesById.getFileName());
            if (f != null) {
                f.delete();
            }
        }
        infoAddedFilesService.deleteInfoAddedFilesById(id);
    }

    /**
     * 保存修改
     */
    @RequestMapping("updateLoanMonitorInfo")
    public void updateLoanMonitorInfo(@RequestParam("loanRevisitType") String loanRevisitType, @RequestParam("loanMonitorState") String loanMonitorState, @RequestParam("loanResultContent") String loanResultContent, @RequestParam("id") Integer id) {
        LoanMonitorInfo monitorInfoById = monitorInfoService.getMonitorInfoById(id);
        //回访类型
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("dictName", loanRevisitType);
        List<SysDataDictCol> revisitType = iDataDictColProvider.queryDataDictColList(condition);
        String revisit = revisitType.get(0).getDictValue();
        monitorInfoById.setLoanRevisitType(revisit);
        //老状态
        String oldLoanMonitorState = monitorInfoById.getLoanMonitorState();
        //状态
        LoanMonitorState[] values = LoanMonitorState.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].optionName.equals(loanMonitorState)) {
                monitorInfoById.setLoanMonitorState(values[i].state);
            }
        }
        //结果
        if(StringUtils.isNotBlank(loanResultContent)){
            monitorInfoById.setLoanResultContent( XSSFilterUtil.cleanXSS(loanResultContent));
        }

        //如果监控已完成，修改监控人 2017年11月28日17:57:00
        if(StringUtils.equals(LoanMonitorState.MONITOR_COMPLETE.state,monitorInfoById.getLoanMonitorState())){
            monitorInfoById.setLoanMonitorUserId(getLoginInfo().getUserId());
        }
        //完成日期
        String newState = LoanMonitorState.getStateByOptionName(loanMonitorState);
        /*if ("Monitor".equals(oldLoanMonitorState) && "MonitorComplete".equals(newState)) {
            monitorInfoById.setLoanCompleteDate(new Date());
        }*/
        if(StringUtils.equals(LoanMonitorState.MONITOR.state,oldLoanMonitorState) && StringUtils.equals(LoanMonitorState.MONITOR_COMPLETE.state,newState)){
            monitorInfoById.setLoanCompleteDate(new Date());
        }
        Integer loginUserId = getLoginInfo().getUserId();
        monitorInfoService.updateMonitorInfo(monitorInfoById, loginUserId);
        //更新主表
        LoanMonitorInfo loanMonitorInfo = monitorInfoService.queryMonitorDate(monitorInfoById.getLoanId());
        //如果loanMonitorInfo不为空，说明有未完成的状态且是时间最早的，更新主表
        if(loanMonitorInfo != null){
            LoanApplyInfo applyInfoById = iApplyInfoService.getApplyInfoById(monitorInfoById.getLoanId());
            applyInfoById.setLoanMonitorState(loanMonitorInfo.getLoanMonitorState());
            applyInfoById.setLoanMonitorDate(loanMonitorInfo.getLoanMonitorDate());
            applyInfoById.setLoanMonitorType(loanMonitorInfo.getLoanMonitorType());
            iApplyInfoService.updateApplyInfo(applyInfoById, loginUserId);
        }else{
            //否则都是完成状态
            LoanApplyInfo applyInfoById = iApplyInfoService.getApplyInfoById(monitorInfoById.getLoanId());
            applyInfoById.setLoanMonitorState("MonitorComplete");
            applyInfoById.setLoanMonitorDate(applyInfoById.getLoanMonitorDate());
            applyInfoById.setLoanMonitorType(applyInfoById.getLoanMonitorType());
            iApplyInfoService.updateApplyInfo(applyInfoById, loginUserId);
        }
    }


    /**
     * 查看
     *
     * @param id
     * @return
     */
    @RequestMapping("/getViewPage")
    public ModelAndView getViewPage(@RequestParam("id") Integer id) {
        LoanMonitorInfo monitorInfoById = monitorInfoService.getMonitorInfoById(id);
        ModelAndView mv = new ModelAndView("/loan/vm/loanMonitorInfoView");
        Map<String, Object> condition = new HashMap<String, Object>();
        LoanMonitorInfo infoById = monitorInfoService.getMonitorInfoById(id);
        //回访类型
        condition.put("dataDictName", "CD_MONITOR_REVISIT_TYPE");
        List<SysDataDictCol> dictColList = iDataDictColProvider.queryDataDictColList(condition);

        for (int i = 0; i < dictColList.size(); i++) {
            if (StringUtils.equals(dictColList.get(i).getDictValue(), infoById.getLoanRevisitType())) {
                mv.addObject("revisitType", dictColList.get(i).getDictName());
            }
        }

        //监控类型
        LoanMonitorType[] values = LoanMonitorType.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].type.equals(infoById.getLoanMonitorType())) {
                infoById.setLoanMonitorType(values[i].typeName);
            }
        }
        //资料
        condition.clear();
        condition.put("monitorId", id);
        condition.put("isDel", 0);
        List<LoanInfoAddedFiles> fileList = infoAddedFilesService.queryInfoAddedFilesList(condition);
        mv.addObject("fileList", fileList);
        mv.addObject("dictColList", dictColList);
        mv.addObject("loanMonitorInfo", infoById);
        return mv;
    }

    /**
     * 校验是否有附件
     */
    @RequestMapping("checkFileNumRule")
    public void checkFileNum(@RequestParam("monitorId") Integer monitorId) {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("monitorId", monitorId);
        condition.put("isDel", 0);
        List<LoanInfoAddedFiles> loanInfoAddedFiles = iInfoAddedFilesService.queryInfoAddedFilesList(condition);
        if (loanInfoAddedFiles != null && loanInfoAddedFiles.size() > 0) {
            renderText(true, "", null);
        } else {
            renderText(false, "", null);
        }
    }

    /**
     * 改变贷款状态
     */
    @RequestMapping("changeAfterLoanState")
    public void changeAfterLoanState(@RequestParam("loanId") Integer loanId, @RequestParam("afterLoanState") String afterLoanState) {
        Integer loginUserId = getLoginInfo().getUserId();
        LoanApplyInfo applyInfoById = iApplyInfoService.getApplyInfoById(loanId);
        applyInfoById.setLoanAfterState(afterLoanState);
        iApplyInfoService.updateApplyInfo(applyInfoById, loginUserId);
    }

    /**
     * 跳转到新增页面
     *
     * @return
     */
    @RequestMapping("getAddPage")
    public ModelAndView getAddPage(@RequestParam("loanId") Integer loanId) {
        ModelAndView mv = new ModelAndView("/loan/vm/loanMonitorInfoAdd");
        Integer monitorId = monitorInfoService.getMonitorId();
        mv.addObject("monitorId",monitorId);
        mv.addObject("currentTime", new Date());
        mv.addObject("loanId", loanId);
        return mv;
    }

    /**
     * 保存新增监控
     */
    @RequestMapping("addLoanMonitorInfo")
    public void addLoanMonitorInfo(@RequestParam("loanId") Integer loanId,
                                   @RequestParam("loanAddRevisitType") String loanAddRevisitType,
                                   @RequestParam("loanMonitorState") String loanMonitorState,
                                   @RequestParam("loanResultContent") String loanResultContent,
                                   @RequestParam("createDate") String createDate,
                                   @RequestParam("monitorId") String monitorId) {
        try {
            LoanMonitorInfo loanMonitorInfo = new LoanMonitorInfo();
            loanMonitorInfo.setLoanId(loanId);
            //监控类型  临时监控
            loanMonitorInfo.setLoanMonitorType("temporaryMonitor");
            //回访类型
            loanMonitorInfo.setLoanRevisitType(loanAddRevisitType);
            //监控状态
            if(StringUtils.equals(loanMonitorState,"0")){
                loanMonitorInfo.setLoanMonitorState("Monitor");
                loanMonitorInfo.setLoanCompleteDate(null);
            }else{
                loanMonitorInfo.setLoanMonitorState("MonitorComplete");
                //完成时间
                loanMonitorInfo.setLoanCompleteDate(new Date());
                //监控完成时，保存监控人
                loanMonitorInfo.setLoanMonitorUserId(this.getLoginInfo().getUserId());
            }
            //结果
            if(StringUtils.isNotBlank(loanResultContent)){
                loanMonitorInfo.setLoanResultContent( XSSFilterUtil.cleanXSS(loanResultContent));
            }
            //监控时间
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(createDate);
            loanMonitorInfo.setLoanMonitorDate(date);
            Integer loginUserId = getLoginInfo().getUserId();
            //添加一条临时监控
            if(StringUtils.isNotBlank(monitorId)){
                loanMonitorInfo.setId(Integer.parseInt(monitorId));
            }
            monitorInfoService.insertMonitorInfo(loanMonitorInfo,loginUserId);
            LoanApplyInfo applyInfoById = iApplyInfoService.getApplyInfoById(loanId);
            LoanMonitorInfo topMonitor = monitorInfoService.getTopLoanMonitorInfo(loanId);
            if(topMonitor!=null){
                applyInfoById.setLoanMonitorState(topMonitor.getLoanMonitorState());
                applyInfoById.setLoanMonitorDate(topMonitor.getLoanMonitorDate());
                applyInfoById.setLoanMonitorType(topMonitor.getLoanMonitorType());
                iApplyInfoService.updateApplyInfo(applyInfoById, loginUserId);
            }

            LoanInfoAddedFiles loanInfoAddedFiles = new LoanInfoAddedFiles();
            loanInfoAddedFiles.setMonitorId(loanMonitorInfo.getId());
            iInfoAddedFilesService.updateInfoAddedFiles(loanInfoAddedFiles,this.getLoginInfo().getUserId());
            /*
            LoanApplyInfo applyInfoById = iApplyInfoService.getApplyInfoById(loanId);
            //如果主表中贷款没有监控，直接修改主表；如果有监控，则比较监控的时间，监控时间早的更新进主表
            if(applyInfoById.getLoanMonitorDate() == null || (applyInfoById.getLoanMonitorDate().compareTo(loanMonitorInfo.getLoanMonitorDate())==1 && "Monitor".equals(loanMonitorInfo.getLoanMonitorState()))){
               log.info("------------------------------------------------------");
                applyInfoById.setLoanMonitorState(loanMonitorInfo.getLoanMonitorState());
                applyInfoById.setLoanMonitorDate(loanMonitorInfo.getLoanMonitorDate());
                applyInfoById.setLoanMonitorType(loanMonitorInfo.getLoanMonitorType());
                iApplyInfoService.updateApplyInfo(applyInfoById, loginUserId);
            }
            */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
