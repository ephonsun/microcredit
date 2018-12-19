package banger.domain.loan;

import java.util.Date;

/**
 * Created by Administrator on 2017/6/8.
 */
public class LoanUploadRecord {
    private String deviceId;            //设备ID
    private Integer loanId;             //贷款ID
    private String fileId;             //文件名唯一标识,一般是文件名去后缀
    private Date beginTime;             //开始时间
    private Date endTime;               //结束时间
    private String fileName;            //文件名
    private String filePath;            //文件保存路径
    private String fileFix;             //文件名后缀
    private String fileType;            //文件类型
    private Integer fileSize;           //文件大小
    private Integer timeLen;            //文件时长
    private String clientIp;            //客户端IP
    private Integer userId;             //用户ID
    private String account;             //帐号
    private String loanProcessType;     //贷款过程
    private String srcName;             //原文件名

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileFix() {
        return fileFix;
    }

    public void setFileFix(String fileFix) {
        this.fileFix = fileFix;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getTimeLen() {
        return timeLen;
    }

    public void setTimeLen(Integer timeLen) {
        this.timeLen = timeLen;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getLoanProcessType() {
        return loanProcessType;
    }

    public void setLoanProcessType(String loanProcessType) {
        this.loanProcessType = loanProcessType;
    }

    public String getSrcName() {
        return srcName;
    }

    public void setSrcName(String srcName) {
        this.srcName = srcName;
    }
}
