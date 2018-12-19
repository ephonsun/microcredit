package banger.domain.system;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据字典字段表
 */
public class SysDataDictCol implements Serializable {
	private static final long serialVersionUID = 6215071154808224130L;
	private Integer dictColId;					//
	
	private String dataDictName;				//字典表英文名称
	private Integer dataDictId;					//字典表id
	
	private Integer orderNo;					//排序值
	private Integer delFlag;					//删除状态 0：正常 1：删除
	private String dictValue;					//字典英文名称
	private String dictName;					//字典中文名称
	private String dictCode;					//字典编码
	private Integer isFixed;					//是否内置
	
	private Integer createUser;					//
	private Date createDate;					//
	private Integer updateUser;					//
	private Date updateDate;					//


	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public Integer getDictColId(){
		return this.dictColId;
	}

	public void setDictColId(Integer dictColId){
		this.dictColId=dictColId;
	}

	public Integer getOrderNo(){
		return this.orderNo;
	}

	public void setOrderNo(Integer orderNo){
		this.orderNo=orderNo;
	}

	public String getDataDictName(){
		return this.dataDictName;
	}

	public void setDataDictName(String dataDictName){
		this.dataDictName=dataDictName;
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

	public Integer getDataDictId(){
		return this.dataDictId;
	}

	public void setDataDictId(Integer dataDictId){
		this.dataDictId=dataDictId;
	}

	public String getDictName(){
		return this.dictName;
	}

	public void setDictName(String dictName){
		this.dictName=dictName;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Integer getUpdateUser(){
		return this.updateUser;
	}

	public void setUpdateUser(Integer updateUser){
		this.updateUser=updateUser;
	}

	public Integer getDelFlag(){
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag){
		this.delFlag=delFlag;
	}

	public String getDictValue(){
		return this.dictValue;
	}

	public void setDictValue(String dictValue){
		this.dictValue=dictValue;
	}

	public Integer getIsFixed() {
		return isFixed;
	}

	public void setIsFixed(Integer isFixed) {
		this.isFixed = isFixed;
	}

}
