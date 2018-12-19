package banger.domain.system;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统数据字典表
 */
public class SysDataDict implements Serializable {
	private static final long serialVersionUID = 7612122212073086088L;
	private Integer dataDictId;					//
	private Integer updateUser;					//
	private String dictCnName;					//字典中文名称
	private Date createDate;					//
	private String dictEnName;					//字典英文名称
	private Date updateDate;					//
	private Integer sysFlag;					//是否系统级  1:系统级别，不允许删除   0:不是系统级别
	private Integer delFlag;					//删除标志  0：正常 1：删除
	private Integer isFixed;					//是否内置
	private Integer createUser;					//

	public Integer getDataDictId(){
		return this.dataDictId;
	}

	public void setDataDictId(Integer dataDictId){
		this.dataDictId=dataDictId;
	}

	public Integer getUpdateUser(){
		return this.updateUser;
	}

	public void setUpdateUser(Integer updateUser){
		this.updateUser=updateUser;
	}

	public String getDictCnName(){
		return this.dictCnName;
	}

	public void setDictCnName(String dictCnName){
		this.dictCnName=dictCnName;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public String getDictEnName(){
		return this.dictEnName;
	}

	public void setDictEnName(String dictEnName){
		this.dictEnName=dictEnName;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Integer getSysFlag(){
		return this.sysFlag;
	}

	public void setSysFlag(Integer sysFlag){
		this.sysFlag=sysFlag;
	}

	public Integer getDelFlag(){
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag){
		this.delFlag=delFlag;
	}

	public Integer getCreateUser(){
		return this.createUser;
	}

	public void setCreateUser(Integer createUser){
		this.createUser=createUser;
	}

	public Integer getIsFixed() {
		return isFixed;
	}

	public void setIsFixed(Integer isFixed) {
		this.isFixed = isFixed;
	}

}
