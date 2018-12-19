package banger.domain.customer;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户征信调查附件表
 */
public class CustCreditCheckFile implements Serializable {
	private static final long serialVersionUID = 7355946492442114676L;
	private Integer creditCheckFileId;					//
	private String smallFileName;					//缩略图文件名
	private String smallFilePath;					//缩略图文件路径
	private String fileName;					//文件名称
	private Integer fileType;					//文件类型 1：客户端提交资料  2：调查资料
	private Integer custCreditCheckId;					//征信调查表Id
	private Integer fileSize;					//文件大小
	private String fileNameOld;					//原文件名称
	private String fileViewName;					//图片显示名称
	private Date createDate;					//文件创建时间
	private Integer isDel;					//是否删除 0：正常 1：删除
	private Integer createUser;					//文件创建人
	private String filePath;					//文件路径

	public Integer getCreditCheckFileId(){
		return this.creditCheckFileId;
	}

	public void setCreditCheckFileId(Integer creditCheckFileId){
		this.creditCheckFileId=creditCheckFileId;
	}

	public String getSmallFileName(){
		return this.smallFileName;
	}

	public void setSmallFileName(String smallFileName){
		this.smallFileName=smallFileName;
	}

	public String getSmallFilePath(){
		return this.smallFilePath;
	}

	public void setSmallFilePath(String smallFilePath){
		this.smallFilePath=smallFilePath;
	}

	public String getFileName(){
		return this.fileName;
	}

	public void setFileName(String fileName){
		this.fileName=fileName;
	}

	public Integer getFileType(){
		return this.fileType;
	}

	public void setFileType(Integer fileType){
		this.fileType=fileType;
	}

	public Integer getCustCreditCheckId(){
		return this.custCreditCheckId;
	}

	public void setCustCreditCheckId(Integer custCreditCheckId){
		this.custCreditCheckId=custCreditCheckId;
	}

	public Integer getFileSize(){
		return this.fileSize;
	}

	public void setFileSize(Integer fileSize){
		this.fileSize=fileSize;
	}

	public String getFileNameOld(){
		return this.fileNameOld;
	}

	public void setFileNameOld(String fileNameOld){
		this.fileNameOld=fileNameOld;
	}

	public String getFileViewName(){
		return this.fileViewName;
	}

	public void setFileViewName(String fileViewName){
		this.fileViewName=fileViewName;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getCreateUser(){
		return this.createUser;
	}

	public void setCreateUser(Integer createUser){
		this.createUser=createUser;
	}

	public String getFilePath(){
		return this.filePath;
	}

	public void setFilePath(String filePath){
		this.filePath=filePath;
	}

}
