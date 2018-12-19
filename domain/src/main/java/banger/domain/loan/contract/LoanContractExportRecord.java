package banger.domain.loan.contract;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 */
public class LoanContractExportRecord implements Serializable {
	private static final long serialVersionUID = 6888129301367557285L;
	private Integer id;					//
	private Integer loanId;					//
	private Integer templateFileId;					//合同模版文件id
	private String templateFileName;					//合同模版文件名称
	private Integer exportUserId;					//导出人
	private Date exportTime;					//导出时间

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

	public Integer getTemplateFileId(){
		return this.templateFileId;
	}

	public void setTemplateFileId(Integer templateFileId){
		this.templateFileId=templateFileId;
	}

	public String getTemplateFileName(){
		return this.templateFileName;
	}

	public void setTemplateFileName(String templateFileName){
		this.templateFileName=templateFileName;
	}

	public Integer getExportUserId(){
		return this.exportUserId;
	}

	public void setExportUserId(Integer exportUserId){
		this.exportUserId=exportUserId;
	}

	public Date getExportTime(){
		return this.exportTime;
	}

	public void setExportTime(Date exportTime){
		this.exportTime=exportTime;
	}

}
