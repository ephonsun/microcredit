package banger.domain.html5;

import java.io.Serializable;
import java.util.Date;

import banger.framework.entity.Entity;

/**
 * 进件附件信息
 */
public class IntoFileInfo implements Serializable {
	private static final long serialVersionUID = 3874390691946118792L;
	private Integer id;					//主键
	private String fileName;					//文件名
	private String filePath;					//文件路径
	private Integer fileSize;					//文件大小
	private String fileSrcName;					//文件原文件名
	private Integer applyId;					//申请信息ID
	private Integer requestId;					//进件接口ID
	private String fileThumbImageName;					//缩略图文件名
	private String fileThumbImagePath;					//缩略图文件路径
	private Date createTime;					//上传时间
	private Integer imageType;				//1表示身份证正面，2表示身份证反面，3表示人脸

	public Integer getImageType() {
		return imageType;
	}

	public void setImageType(Integer imageType) {
		this.imageType = imageType;
	}

	public Integer getId(){
		return this.id;
	}

	public void setId(Integer id){
		this.id=id;
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

	public Integer getApplyId(){
		return this.applyId;
	}

	public void setApplyId(Integer applyId){
		this.applyId=applyId;
	}

	public Integer getRequestId(){
		return this.requestId;
	}

	public void setRequestId(Integer requestId){
		this.requestId=requestId;
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

	public Date getCreateTime(){
		return this.createTime;
	}

	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}

}
