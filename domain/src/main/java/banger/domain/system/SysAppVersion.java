package banger.domain.system;

import java.io.Serializable;
import java.util.Date;

import banger.framework.entity.Entity;

/**
 * APP升级版本信息表
 */
public class SysAppVersion implements Serializable {
	private static final long serialVersionUID = 8063184654154290542L;
	private Integer id;					//主键ID
	private Integer userId;					//操作者
	private String userName;					//操作者名称
	private String apkName;					//升级包名称
	private String apkVersionShow;					//显示版本号
	private String apkVersionUpd;					//升级版本号
	private String isUpdate;					//是否强制升级
	private String updateContent;					//更新内容
	private Date createDate;					//升级时间
	private String apkUrl;					//升级路径
	private Integer isFileExists;			//文件是否存在,0不存在,1存在
	private Integer isDel;			// 是否禁用,0 未删除,1 删除

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Integer getIsFileExists() {
		return isFileExists;
	}

	public void setIsFileExists(Integer isFileExists) {
		this.isFileExists = isFileExists;
	}

	public String getApkUrl() {
		return apkUrl;
	}

	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getId(){
		return this.id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getUserId(){
		return this.userId;
	}

	public void setUserId(Integer userId){
		this.userId=userId;
	}

	public String getApkName(){
		return this.apkName;
	}

	public void setApkName(String apkName){
		this.apkName=apkName;
	}

	public String getApkVersionShow(){
		return this.apkVersionShow;
	}

	public void setApkVersionShow(String apkVersionShow){
		this.apkVersionShow=apkVersionShow;
	}

	public String getApkVersionUpd(){
		return this.apkVersionUpd;
	}

	public void setApkVersionUpd(String apkVersionUpd){
		this.apkVersionUpd=apkVersionUpd;
	}

	public String getIsUpdate(){
		return this.isUpdate;
	}

	public void setIsUpdate(String isUpdate){
		this.isUpdate=isUpdate;
	}

	public String getUpdateContent(){
		return this.updateContent;
	}

	public void setUpdateContent(String updateContent){
		this.updateContent=updateContent;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

}
