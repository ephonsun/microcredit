package banger.domain.html5;

import java.io.Serializable;
import java.util.Date;

import banger.framework.entity.Entity;

/**
 * 进件接口记录表
 */
public class IntoIntefaceRecord implements Serializable {
	private static final long serialVersionUID = 1059363739922489776L;
	private Integer requestId;					//主键
	private Integer interfaceType;					//接口类型0表示OCR，1表示人脸识别
	private Integer isSuccess;					//调用结果0失败，1成功
	private Integer errorType;					//错误类型
	private String errorMessage;					//错误信息
	private Integer applyId;					//申请信息ID
	private Date callTime;					//调用时间

	public Integer getRequestId(){
		return this.requestId;
	}

	public void setRequestId(Integer requestId){
		this.requestId=requestId;
	}

	public Integer getInterfaceType(){
		return this.interfaceType;
	}

	public void setInterfaceType(Integer interfaceType){
		this.interfaceType=interfaceType;
	}

	public Integer getIsSuccess(){
		return this.isSuccess;
	}

	public void setIsSuccess(Integer isSuccess){
		this.isSuccess=isSuccess;
	}

	public Integer getErrorType(){
		return this.errorType;
	}

	public void setErrorType(Integer errorType){
		this.errorType=errorType;
	}

	public String getErrorMessage(){
		return this.errorMessage;
	}

	public void setErrorMessage(String errorMessage){
		this.errorMessage=errorMessage;
	}

	public Integer getApplyId(){
		return this.applyId;
	}

	public void setApplyId(Integer applyId){
		this.applyId=applyId;
	}

	public Date getCallTime(){
		return this.callTime;
	}

	public void setCallTime(Date callTime){
		this.callTime=callTime;
	}

}
