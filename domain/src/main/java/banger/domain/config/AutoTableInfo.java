package banger.domain.config;

import java.io.Serializable;
import java.util.Date;

/**
 * 自定义表信息
 */
public class AutoTableInfo implements Serializable {
	private static final long serialVersionUID = 1207747833809190287L;
	private Integer tableId;					//主键ID
	private String tableDbName;					//数据库表名
	private String tableModuleName;					//表模版名
	private String tableDisplayName;					//表显示名称
	private String tableComment;					//表注释（不可修改）
	private Integer isActived;					//是否启用0 禁用1 启用
	private Integer isFixed;					//是否为内置
	private Integer tableType;					//表类型
	private Date createDate;					//创建时间
	private Date updateDate;					//更新时间
	private Integer createUser;					//创建用户
	private Integer updateUser;					//更新用户
	private String tableModule;

	public Integer getTableId(){
		return this.tableId;
	}

	public void setTableId(Integer tableId){
		this.tableId=tableId;
	}

	public String getTableDbName(){
		return this.tableDbName;
	}

	public void setTableDbName(String tableDbName){
		this.tableDbName=tableDbName;
	}

	public String getTableModuleName(){
		return this.tableModuleName;
	}

	public void setTableModuleName(String tableModuleName){
		this.tableModuleName=tableModuleName;
	}

	public String getTableDisplayName(){
		return this.tableDisplayName;
	}

	public void setTableDisplayName(String tableDisplayName){
		this.tableDisplayName=tableDisplayName;
	}

	public String getTableComment(){
		return this.tableComment;
	}

	public void setTableComment(String tableComment){
		this.tableComment=tableComment;
	}

	public Integer getIsActived(){
		return this.isActived;
	}

	public void setIsActived(Integer isActived){
		this.isActived=isActived;
	}

	public Integer getIsFixed(){
		return this.isFixed;
	}

	public void setIsFixed(Integer isFixed){
		this.isFixed=isFixed;
	}

	public Integer getTableType(){
		return this.tableType;
	}

	public void setTableType(Integer tableType){
		this.tableType=tableType;
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

	public String getTableModule() {
		return tableModule;
	}

	public void setTableModule(String tableModule) {
		this.tableModule = tableModule;
	}
}
