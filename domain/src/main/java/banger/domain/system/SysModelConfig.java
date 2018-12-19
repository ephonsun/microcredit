package banger.domain.system;

import java.io.Serializable;
import java.util.Date;

import banger.framework.entity.Entity;

/**
 * 模型配置
 */
public class SysModelConfig implements Serializable {
	private static final long serialVersionUID = 7506007839485120559L;
	private Integer configId;					//配置ID
	private Integer modelId;					//模型id
	private String modelName;					//模型名
	private String configKey;					//配置键
	private String configValue;					//配置值
	private Date createDate;					//
	private Date updateDate;					//
	private Integer createUser;					//
	private Integer updateUser;					//

	public Integer getConfigId(){
		return this.configId;
	}

	public void setConfigId(Integer configId){
		this.configId=configId;
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

	public String getConfigKey(){
		return this.configKey;
	}

	public void setConfigKey(String configKey){
		this.configKey=configKey;
	}

	public String getConfigValue(){
		return this.configValue;
	}

	public void setConfigValue(String configValue){
		this.configValue=configValue;
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
