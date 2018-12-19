package banger.domain.system;

import java.io.Serializable;

import banger.framework.entity.Entity;

/**
 * 
 */
public class SysSocketLog implements Serializable {
	private static final long serialVersionUID = 6433640347791080082L;
	private Integer id;					//
	private Integer loanId;					//
	private String socketCode;					//
	private String sendXml;					//
	private String returnMassage;					//
	private String remark;					//
	private String codeNum;//编号 可以是客户编号 抵质押编号 用于报文唯一查询
	private String codeNumTwo;//第二编号 用于联合code_num 确认唯一


	public String getCodeNumTwo() {
		return codeNumTwo;
	}

	public void setCodeNumTwo(String codeNumTwo) {
		this.codeNumTwo = codeNumTwo;
	}

	public String getCodeNum() {
		return codeNum;
	}

	public void setCodeNum(String codeNum) {
		this.codeNum = codeNum;
	}

	public Integer getId(){
		return this.id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getLoanId(){
		return this.loanId;
	}

	public void setLoanId(Integer loanId){
		this.loanId=loanId;
	}

	public String getSocketCode(){
		return this.socketCode;
	}

	public void setSocketCode(String socketCode){
		this.socketCode=socketCode;
	}

	public String getSendXml(){
		return this.sendXml;
	}

	public void setSendXml( String sendXml){
		this.sendXml=sendXml;
	}

	public String getReturnMassage(){
		return this.returnMassage;
	}

	public void setReturnMassage(String returnMassage){
		this.returnMassage=returnMassage;
	}

	public String getRemark(){
		return this.remark;
	}

	public void setRemark(String remark){
		this.remark=remark;
	}

}
