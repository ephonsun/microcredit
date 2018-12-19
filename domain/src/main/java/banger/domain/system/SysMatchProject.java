package banger.domain.system;

import java.io.Serializable;
import java.util.Date;

import banger.framework.entity.Entity;

/**
 * 匹配项目表
 */
public class SysMatchProject implements Serializable {
	private static final long serialVersionUID = 5338548084590432216L;
	private Integer projectId;					//匹配项目id
	private String projectName;					//匹配项目名
	private Integer modelId;					//匹配模型id
	private String modelName;					//匹配模型名
	private Integer createUser;					//
	private Date createDate;					//
	private Integer updateUser;					//
	private Date updateDate;					//

	public Integer getProjectId(){
		return this.projectId;
	}

	public void setProjectId(Integer projectId){
		this.projectId=projectId;
	}

	public String getProjectName(){
		return this.projectName;
	}

	public void setProjectName(String projectName){
		this.projectName=projectName;
	}

	public Integer getModelId(){
		return this.modelId;
	}

	public void setModelId(Integer modelId){
		this.modelId=modelId;
	}

	public String getModelName(){
		return this.modelName;
	}

	public void setModelName(String modelName){
		this.modelName=modelName;
	}

	public Integer getCreateUser(){
		return this.createUser;
	}

	public void setCreateUser(Integer createUser){
		this.createUser=createUser;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Integer getUpdateUser(){
		return this.updateUser;
	}

	public void setUpdateUser(Integer updateUser){
		this.updateUser=updateUser;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

}
