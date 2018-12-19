package banger.domain.config;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 */
public class AutoTableDict implements Serializable {
	private static final long serialVersionUID = 4411877286802174995L;
	private Integer dictId;					//
	private Date createDate;					//
	private Date updateDate;					//
	private Integer createUser;					//
	private Integer updateUser;					//
	private String tableName;					//
	private String tableRemark;					//
	private String tableDisplay;					//

	public Integer getDictId(){
		return this.dictId;
	}

	public void setDictId(Integer dictId){
		this.dictId=dictId;
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

	public String getTableName(){
		return this.tableName;
	}

	public void setTableName(String tableName){
		this.tableName=tableName;
	}

	public String getTableRemark(){
		return this.tableRemark;
	}

	public void setTableRemark(String tableRemark){
		this.tableRemark=tableRemark;
	}

	public String getTableDisplay(){
		return this.tableDisplay;
	}

	public void setTableDisplay(String tableDisplay){
		this.tableDisplay=tableDisplay;
	}

}
