package banger.domain.customer;

import java.io.Serializable;
import java.util.Date;

import banger.framework.entity.Entity;

/**
 * 潜在客户附件文件表
 */
public class CustPotentialCustomersFiles implements Serializable {
	private static final long serialVersionUID = 4531457910998231992L;
	private Integer id;					//主键ID
	private Integer potentialId;					//潜在客户ID
	private Integer isDel;					//是否删除
	private String fileId;					//文件唯一标识
	private String fileType;					//文件类型
	private String fileFix;					//文件后缀
	private String fileName;					//文件名
	private String filePath;					//文件路径
	private Integer fileSize;					//文件大小
	private String fileSrcName;					//文件原文件名
	private String fileThumbImageName;					//缩略图文件名
	private String fileThumbImagePath;					//缩略图文件路径
	private Integer callTime;					//视频时长
	private Date beginTime;					//开始时间
	private Date endTime;					//结束时间
	private Date createDate;					//创建时间
	private Date updateDate;					//更新时间
	private Integer createUser;					//创建用户
	private Integer updateUser;					//更新用户

	public Integer getId(){
		return this.id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getPotentialId(){
		return this.potentialId;
	}

	public void setPotentialId(Integer potentialId){
		this.potentialId=potentialId;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public String getFileId(){
		return this.fileId;
	}

	public void setFileId(String fileId){
		this.fileId=fileId;
	}

	public String getFileType(){
		return this.fileType;
	}

	public void setFileType(String fileType){
		this.fileType=fileType;
	}

	public String getFileFix(){
		return this.fileFix;
	}

	public void setFileFix(String fileFix){
		this.fileFix=fileFix;
	}

	public String getFileName(){
		return this.fileName;
	}

	public void setFileName(String fileName){
		this.fileName=fileName;
	}

	public String getFilePath(){
		return this.filePath;
	}

	public void setFilePath(String filePath){
		this.filePath=filePath;
	}

	public Integer getFileSize(){
		return this.fileSize;
	}

	public void setFileSize(Integer fileSize){
		this.fileSize=fileSize;
	}

	public String getFileSrcName(){
		return this.fileSrcName;
	}

	public void setFileSrcName(String fileSrcName){
		this.fileSrcName=fileSrcName;
	}

	public String getFileThumbImageName(){
		return this.fileThumbImageName;
	}

	public void setFileThumbImageName(String fileThumbImageName){
		this.fileThumbImageName=fileThumbImageName;
	}

	public String getFileThumbImagePath(){
		return this.fileThumbImagePath;
	}

	public void setFileThumbImagePath(String fileThumbImagePath){
		this.fileThumbImagePath=fileThumbImagePath;
	}

	public Integer getCallTime(){
		return this.callTime;
	}

	public void setCallTime(Integer callTime){
		this.callTime=callTime;
	}

	public Date getBeginTime(){
		return this.beginTime;
	}

	public void setBeginTime(Date beginTime){
		this.beginTime=beginTime;
	}

	public Date getEndTime(){
		return this.endTime;
	}

	public void setEndTime(Date endTime){
		this.endTime=endTime;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Integer getCreateUser(){
		return this.createUser;
	}

	public void setCreateUser(Integer createUser){
		this.createUser=createUser;
	}

	public Integer getUpdateUser(){
		return this.updateUser;
	}

	public void setUpdateUser(Integer updateUser){
		this.updateUser=updateUser;
	}

}
