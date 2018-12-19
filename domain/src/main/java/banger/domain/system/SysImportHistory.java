package banger.domain.system;

import java.io.Serializable;
import java.util.Date;

import banger.framework.entity.Entity;

/**
 * 导入历史表
 */
public class SysImportHistory implements Serializable {
	private static final long serialVersionUID = 3975796925958902640L;
	private Integer id;					//主键ID
	private Integer userId;					//用户ID
	private String userName;					//用户名称
	private String progressCode;					//导入进度编码
	private String progressName;					//导入进度名称
	private String progressRate;					//导入进度百分比
	private String importModuleName;					//导入模块名称
	private Integer isComplete;					//是否导入完成,0未完成,1导入完成
	private Integer importTotal;					//导入数据量
	private String remark;					//备注
	private Date createDate;					//创建时间
	private Date updateDate;					//更新时间

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProgressRate() {
		return progressRate;
	}

	public void setProgressRate(String progressRate) {
		this.progressRate = progressRate;
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

	public String getProgressCode(){
		return this.progressCode;
	}

	public void setProgressCode(String progressCode){
		this.progressCode=progressCode;
	}

	public String getProgressName(){
		return this.progressName;
	}

	public void setProgressName(String progressName){
		this.progressName=progressName;
	}

	public String getImportModuleName(){
		return this.importModuleName;
	}

	public void setImportModuleName(String importModuleName){
		this.importModuleName=importModuleName;
	}

	public Integer getIsComplete(){
		return this.isComplete;
	}

	public void setIsComplete(Integer isComplete){
		this.isComplete=isComplete;
	}

	public Integer getImportTotal(){
		return this.importTotal;
	}

	public void setImportTotal(Integer importTotal){
		this.importTotal=importTotal;
	}

	public String getRemark(){
		return this.remark;
	}

	public void setRemark(String remark){
		this.remark=remark;
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

}
