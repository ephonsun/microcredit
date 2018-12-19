package banger.controller;

import banger.common.BaseController;
import banger.common.annotation.NoTokenAnnotation;
import banger.common.constant.DateFormatConst;
import banger.common.interceptor.NoLoginInterceptor;
import banger.domain.enumerate.GroupRolesEnum;
import banger.domain.loan.LoanActionHistory;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanInfoAddedFiles;
import banger.domain.product.ProdProductFile;
import banger.framework.util.FileUtil;
import banger.framework.util.StringUtil;
import banger.moduleIntf.*;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.util.AppJsonUtil;
import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 贷款资料
 * Created by zhusw on 2017/6/12.
 */
@RequestMapping("/api")
@Controller
public class AppLoanAddedController extends BaseController {
    public static final String RESULT_LOAN_ADDED_LIST_PARAMS = "id,loanId,fileName,fileSrcName,callTime,fileSize,fileType,createDate";

    @Resource
    private ILoanModule loanModule;

    @Resource
    private IInfoAddedFileProvider iInfoAddedFileProvider;

    /**
     * 贷款历史列表
     * @param request
     * @param response
     */
    @NoLoginInterceptor
    @RequestMapping("/v1/queryLoanAddedTypeFileList")
    public ResponseEntity<String> queryLoanAddedTypeFileList(HttpServletRequest request, HttpServletResponse response){
        String loanId = this.getParameter("loanId");
        String fileType = this.getParameter("fileType");
//        String userId = this.getParameter("userId");
//        Integer isDelete = 0;
        if(StringUtil.isNotEmpty(loanId) && StringUtil.isNotEmpty(fileType)) {
            List<LoanInfoAddedFiles> list = loanModule.getLoanAddedProvider().getInfoAddedFilesListByType(Integer.parseInt(loanId),fileType);
            JSONArray resArray = AppJsonUtil.listToArray(list, RESULT_LOAN_ADDED_LIST_PARAMS, DateFormatConst.DEFAULT_DATE_FORMAT);
            return new ResponseEntity<String>(AppJsonResponse.success(resArray), HttpStatus.OK);
        }else {
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
        }
    }

    /**
     * 下载贷款录像，录音，附件
     * @param request
     * @param response
     * @throws Exception
     */
    @NoLoginInterceptor
    @NoTokenAnnotation
    @RequestMapping("/v1/downloadLoanAddedTypeFileById")
    public void downloadLoanAddedTypeFileById(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String fileId = getParameter("fileId");
        String offset = getParameter("offset");
        long fileOffset = StringUtils.isNotBlank(offset)?Long.parseLong(offset):0;
        if(StringUtil.isNotEmpty(fileId)) {
            LoanInfoAddedFiles info = loanModule.getLoanAddedProvider().getLoanAddedFileInfoById(Integer.parseInt(fileId));
            if (info != null) {
                String path = info.getFilePath();
                String filename = info.getFileName();
                String addFilename = FileUtil.contact(path, filename);
                File file = new File(addFilename);
                if (file.exists()) {
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("application/force-download");// 设置强制下载不打开
                    String fileName = URLEncoder.encode(info.getFileSrcName(),"UTF-8");
                    response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                    long conLen = file.length() - fileOffset ;
                    response.addHeader("Content-Length", conLen+"");
                    OutputStream os = response.getOutputStream();
                    downloadProductFile(file, os, fileOffset);
                }
            }
        }
    }

    /**
     * 下载文件
     * @param file
     * @param os
     * @param offset
     * @throws Exception
     */
    private void downloadProductFile(File file, OutputStream os, long offset) throws Exception{
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            byte[] buffer = new byte[2048];
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            if(offset > 0)
                bis.skip(offset);
            int i ;
            while ((i = bis.read(buffer)) > 0) {
                os.write(buffer, 0, i);
            }
            os.flush();
        } finally {
            if(os != null){
                try {
                    os.close();
                } catch (IOException e) {}
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {}
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {}
            }
        }
    }

    /**
     * 删除文件
     * @param request
     * @param response
     * @throws Exception
     */
    @NoLoginInterceptor
    @RequestMapping("/v1/deleteLoanAddedTypeFileById")
    public  ResponseEntity<String> deleteLoanAddedTypeFileById(HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            String fileId = getParameter("fileId");
            if (StringUtil.isNotEmpty(fileId)) {
                iInfoAddedFileProvider.deleteInfoAddedFilesById(Integer.parseInt(fileId));
                return new ResponseEntity<String>(AppJsonResponse.success(""), HttpStatus.OK);
            }
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
        }catch (Exception e){
            log.error("delete loadFile error", e);
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
        }
        }
}
