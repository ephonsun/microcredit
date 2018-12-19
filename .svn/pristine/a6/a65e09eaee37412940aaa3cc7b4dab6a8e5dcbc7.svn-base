package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanInfoAddedFiles;
import banger.domain.loan.LoanMonitorInfo;
import banger.moduleIntf.IInfoAddedFileProvider;
import banger.moduleIntf.ILoanHandOverProvider;
import banger.moduleIntf.IMonitorInfoPrivder;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequestMapping("/api")
@Controller
public class AppLoanMonitorInfoController extends BaseController{

    @Resource
    private ILoanHandOverProvider iApplyInfoService;

    @Resource
    private IMonitorInfoPrivder monitorInfoService;

    @Resource
    private IInfoAddedFileProvider iInfoAddedFilesService;

    /**
     * 跳转到新增页面
     *
     * @return
     */
    @RequestMapping("/v1/getMoniterInfoId")
    @NoLoginInterceptor
    public ResponseEntity<String> getMoniterInfoId() {
        try{
            Integer monitorId = monitorInfoService.getMonitorId();
            return new ResponseEntity<String>(AppJsonResponse.success(monitorId.toString()), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
        }
    }

    @RequestMapping("/v1/addLoanMonitor")
    @NoLoginInterceptor
    public ResponseEntity<String> addLoanMonitor(HttpServletRequest request, HttpServletResponse response){
        try {
            String loanId = this.getParameter("loanId");
            String userId = this.getParameter("userId");
            String loanAddRevisitType = this.getParameter("loanAddRevisitType");
            String loanMonitorState = this.getParameter("loanMonitorState");
            String loanResultContent = this.getParameter("loanResultContent");
            String createDate = this.getParameter("createDate");
            String monitorId = this.getParameter("monitorId");
            LoanMonitorInfo loanMonitorInfo = new LoanMonitorInfo();
            loanMonitorInfo.setLoanId(Integer.parseInt(loanId));
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
                loanMonitorInfo.setLoanMonitorUserId(Integer.parseInt(userId));
            }
            loanMonitorInfo.setLoanResultContent(loanResultContent);
            //监控时间
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(createDate);
            loanMonitorInfo.setLoanMonitorDate(date);
            Integer loginUserId = Integer.parseInt(userId);
            //添加一条临时监控
            if(StringUtils.isNotBlank(monitorId)){
                loanMonitorInfo.setId(Integer.parseInt(monitorId));
            }
            monitorInfoService.insertMonitorInfo(loanMonitorInfo,loginUserId);
            LoanApplyInfo applyInfoById = iApplyInfoService.getApplyInfoById(Integer.parseInt(loanId));
            LoanMonitorInfo topMonitor = monitorInfoService.getTopLoanMonitorInfo(Integer.parseInt(loanId));
            if(topMonitor!=null){
                applyInfoById.setLoanMonitorState(topMonitor.getLoanMonitorState());
                applyInfoById.setLoanMonitorDate(topMonitor.getLoanMonitorDate());
                applyInfoById.setLoanMonitorType(topMonitor.getLoanMonitorType());
                iApplyInfoService.updateApplyInfo(applyInfoById, loginUserId);
            }
            LoanInfoAddedFiles loanInfoAddedFiles = new LoanInfoAddedFiles();
            loanInfoAddedFiles.setMonitorId(loanMonitorInfo.getId());
            iInfoAddedFilesService.updateInfoAddedFiles(loanInfoAddedFiles,Integer.parseInt(userId));
            return new ResponseEntity<String>(AppJsonResponse.success(""), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
        }
    }
}
