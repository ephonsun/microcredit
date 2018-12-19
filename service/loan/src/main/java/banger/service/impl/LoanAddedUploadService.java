package banger.service.impl;

import banger.dao.intf.IInfoAddedFilesDao;
import banger.domain.enumerate.CommonFilePath;
import banger.domain.loan.LoanInfoAddedFiles;
import banger.domain.loan.LoanUploadRecord;
import banger.framework.util.DateUtil;
import banger.framework.util.FileUtil;
import banger.framework.util.StringUtil;

import banger.moduleIntf.IMessageListener;
import net.sf.json.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhusw on 2017/6/8.
 */
public class LoanAddedUploadService implements IMessageListener {

    @Value("${file_upload_speed}")
    private String fileUploadSpeed;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    // 继承BaseController的都是单例，不能使用全局变量
    protected transient final Log log = LogFactory.getLog(getClass());

    private String uploadPath;

    private Integer uploadSpeed = 100;

    private IInfoAddedFilesDao infoAddedFilesDao;

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public void setInfoAddedFilesDao(IInfoAddedFilesDao infoAddedFilesDao) {
        this.infoAddedFilesDao = infoAddedFilesDao;
    }

    /**
     * 接收文件上传消息
     * @param messageId 消息ID
     * @param content  消息内容，JSON格式
     * @return 返回为空继续，执行下一个IMessageListener接口实
     */
    @Override
    public String onMessage(String messageId, String content) {
        if("sendFileBegin".equals(messageId)){
            LoanUploadRecord record = ParserRecordXml.parser(content);
            if(record!=null){
                if(fileUploadSpeed != null){
                    uploadSpeed = Integer.parseInt(fileUploadSpeed);
                }
                Integer speed = uploadSpeed;
                String fix = FileUtil.getFileFix(record.getFileName());
                String path = this.getTempUploadPath(fix);
                Long fileSize = getTempFileSize(path,record.getFileId()+".temp");
                return "{\"result\":\"success\",\"fileSize\":"+fileSize+",\"speed\":"+speed+",\"error\":\"\"}";
            }else{
                return "{\"result\":\"fail\",\"error\":\"JSON消息解析错出\"}";
            }
        }else if("sendFileEnd".equals(messageId)){
            LoanUploadRecord record = ParserRecordXml.parser(content);
            if(record!=null){
                String message = this.uploadRecordFileComplete(record);
                if("success".equals(message)) {
                    return "{\"result\":\"success\",\"error\":\"\"}";
                }else{
                    return "{\"result\":\"fail\",\"error\":\""+message+"\"}";
                }
            }else{
                return "{\"result\":\"fail\",\"error\":\"JSON消息解析错出\"}";
            }
        }
        return null;
    }

    /**
     * 更新上传文件
     * @param record
     */
    public String uploadRecordFileComplete(LoanUploadRecord record){
        if (!StringUtil.isNullOrEmpty(record.getFileName())){
            String fix = FileUtil.getFileFix(record.getFileName());
            String tempPath = this.getTempUploadPath(fix);
            FileUtil.createDirectory(tempPath);
            String tempFile = FileUtil.contact(tempPath,record.getFileId()+"."+"temp");
            File file = new File(tempFile);
            if(file.exists()){
                long fileSize = FileUtil.getFilesize(tempFile);
                if(fileSize>0 && fileSize == record.getFileSize().longValue()){
                    String path = this.getRecordPath(fix);
                    FileUtil.createDirectory(path);
                    String newFilename = FileUtil.contact(path,record.getFileName());
                    record.setFilePath(path);
                    record.setFileSize((int)fileSize);
                    record.setFileFix(fix);
                    String fileType = getFileType(fix);
                    record.setFileType(fileType);
                    this.saveFileInfo(record);
                    copyAndDelete(file,new File(newFilename));
                    return "success";
                }else{
                    String errorMessage = "上传文件 "+tempFile+" 大小不一至 实际大小:"+fileSize+" 目标大小:"+record.getFileSize();
                    System.out.println(errorMessage);
                    log.error(errorMessage);
                    file.delete();
                }
            }else{
                String errorMessage = "文件上传失败:"+tempFile+"不存在";
                System.out.println(errorMessage);
                log.error(errorMessage);
            }
        }
        return "文件名不能为空";
    }

    private boolean copyAndDelete(File source,File target){
        try {
            FileUtils.copyFile(source, target);
            Thread.sleep(1000);
            source.delete();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("拷贝文件出错 error:"+e.getMessage(),e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取临时文件上传路径
     * @param fix
     * @return
     */
    private String getTempUploadPath(String fix){
        String path = FileUtil.contact(uploadPath, CommonFilePath.RECORD_TEMP_PATH.getPath());
        String type = (fix.charAt(0)=='.')?fix.substring(1):fix;
        path = FileUtil.contact(path, type);
        return path;
    }

    /**
     * 获取录音文件存放路径
     * @param fix
     * @return
     */
    private String getRecordPath(String fix){
        String path = FileUtil.contact(uploadPath,CommonFilePath.RECORD_PATH.getPath());
        String type = (fix.charAt(0)=='.')?fix.substring(1):fix;
        path = FileUtil.contact(path, type);
        String today = sdf.format(new Date());
        path = FileUtil.contact(path, today);
        return path;
    }

    /**
     * 得到文件大小
     * @param filename
     * @return
     */
    private Long getTempFileSize(String path,String filename){
        String tempFile = FileUtil.contact(path,filename);
        long fileSize = FileUtil.getFilesize(tempFile);
        return fileSize;
    }

    private String getFileType(String fix){
        if("mp4".equals(fix) || ".mp4".equals(fix)){
            return "Video";
        }else if("acc".equals(fix) || ".acc".equals(fix)){
            return "Audio";
        }
        return "";
    }

    /**
     * 上传音视频文件信息
     * @param uploadRecord
     */
    public void saveFileInfo(LoanUploadRecord uploadRecord){
        LoanInfoAddedFiles record = new LoanInfoAddedFiles();
        record.setFileName(uploadRecord.getFileName());
        if(StringUtil.isNotEmpty(uploadRecord.getSrcName()))
            record.setFileSrcName(uploadRecord.getSrcName());
        else
            record.setFileSrcName(uploadRecord.getFileName());
        record.setFilePath(uploadRecord.getFilePath());
        record.setFileType(uploadRecord.getFileType());
        record.setBeginTime(uploadRecord.getBeginTime());
        record.setEndTime(uploadRecord.getEndTime());
        record.setCallTime(uploadRecord.getTimeLen());
        record.setFileId(uploadRecord.getFileId());
        record.setFileSize(uploadRecord.getFileSize());
        record.setFileFix(uploadRecord.getFileFix());
        record.setLoanProcessType(uploadRecord.getLoanProcessType());
        record.setLoanId(uploadRecord.getLoanId());
        record.setCreateUser(uploadRecord.getUserId());
        record.setUpdateUser(uploadRecord.getUserId());
        record.setCreateDate(new Date());
        record.setUpdateDate(new Date());
        Integer id = this.infoAddedFilesDao.getAddedFileIdByRecord(record);
        if(id!=null && id.intValue()>0){
            record.setId(record.getId());
            this.infoAddedFilesDao.updateInfoAddedFiles(record);
        }else{
            this.infoAddedFilesDao.insertInfoAddedFiles(record);
        }
    }

    static class ParserRecordXml {

        public static LoanUploadRecord parser(String recordJSON)  {
            JSONObject root = JSONObject.fromObject(recordJSON);

            LoanUploadRecord record = new LoanUploadRecord();

            String deviceId = getPropertyValue(root,"deviceId");
            String loanId = getPropertyValue(root,"loanId");
            String loanProcessType = getPropertyValue(root,"loanProcessType");
            String fileId = getPropertyValue(root,"fileId");

            String beginTime = getPropertyValue(root,"beginTime");
            String endTime = getPropertyValue(root,"endTime");
            String fileName = getPropertyValue(root,"fileName");
            String srcName = getPropertyValue(root,"srcName");
            String fileSize = getPropertyValue(root,"fileSize");
            String fileFix = getPropertyValue(root,"fileExt");

            String timeLen = getPropertyValue(root,"timeLen");
            String ip = getPropertyValue(root,"ip");

            String userId = getPropertyValue(root,"userId");
            String account = getPropertyValue(root,"account");

            if(StringUtil.isNotEmpty(deviceId))record.setDeviceId(deviceId);
            if(StringUtil.isNotEmpty(loanId))record.setLoanId(Integer.parseInt(loanId));
            if(StringUtil.isNotEmpty(loanProcessType))record.setLoanProcessType(loanProcessType);
            if(StringUtil.isNotEmpty(fileId))record.setFileId(fileId);

            if(StringUtil.isNotEmpty(beginTime)){
                record.setBeginTime(DateUtil.parser(beginTime, DateUtil.DEFAULT_DATETIME_FORMAT));
            }
            if(StringUtil.isNotEmpty(endTime)){
                record.setEndTime(DateUtil.parser(endTime, DateUtil.DEFAULT_DATETIME_FORMAT));
            }

            if(StringUtil.isNotEmpty(fileName))record.setFileName(fileName);
            if(StringUtil.isNotEmpty(fileSize))record.setFileSize(Integer.parseInt(fileSize));
            if(StringUtil.isNotEmpty(fileFix))record.setFileFix(fileFix);
            if(StringUtil.isNotEmpty(timeLen))record.setTimeLen(Integer.parseInt(timeLen));
            if(StringUtil.isNotEmpty(ip))record.setClientIp(ip);
            if(StringUtil.isNotEmpty(userId))record.setUserId(Integer.parseInt(userId));
            if(StringUtil.isNotEmpty(account))record.setAccount(account);
            if(StringUtil.isNotEmpty(srcName))record.setSrcName(srcName);

            return record;
        }

        private static String getPropertyValue(JSONObject root,String propertyName){
            if(root.containsKey(propertyName)){
                return root.getString(propertyName);
            }
            return "";
        }

    }



}
