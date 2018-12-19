package banger.domain.sub;

import java.io.Serializable;

import banger.framework.entity.Entity;

/**
 * 业务品种分类
 */
public class BizTypeSub implements Serializable {
	private static final long serialVersionUID = 7994215517505423477L;
	private String subCode;					//对于产品编码
	private String subName;					//
	private String parentCode;					//
	private String subOrder;					//
	private String prdCode;					//
	private String prdPk;					//
	private String prdName;					//产品名称
	private String cmiCode;//核心产品号
	private String cmiName;//核心产品名称

	public String getCmiCode() {
		return cmiCode;
	}

	public void setCmiCode(String cmiCode) {
		this.cmiCode = cmiCode;
	}

	public void setCmiName(String cmiName) {
		this.cmiName = cmiName;
	}

	public String getCmiName() {
		return cmiName;
	}

	public String getPrdPk(){
		return this.prdPk;
	}

	public void setPrdPk(String prdPk){
		this.prdPk=prdPk;
	}

	public String getPrdName(){
		return this.prdName;
	}

	public void setPrdName(String prdName){
		this.prdName=prdName;
	}

	public String getSubCode(){
		return this.subCode;
	}

	public void setSubCode(String subCode){
		this.subCode=subCode;
	}

	public String getSubName(){
		return this.subName;
	}

	public void setSubName(String subName){
		this.subName=subName;
	}

	public String getParentCode(){
		return this.parentCode;
	}

	public void setParentCode(String parentCode){
		this.parentCode=parentCode;
	}

	public String getSubOrder(){
		return this.subOrder;
	}

	public void setSubOrder(String subOrder){
		this.subOrder=subOrder;
	}

	public String getPrdCode(){
		return this.prdCode;
	}

	public void setPrdCode(String prdCode){
		this.prdCode=prdCode;
	}

}