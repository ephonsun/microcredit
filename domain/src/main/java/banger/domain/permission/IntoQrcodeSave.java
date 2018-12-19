package banger.domain.permission;

import java.io.Serializable;

/**
 * 进件二维码存储表
 */
public class IntoQrcodeSave implements Serializable {
	private static final long serialVersionUID = 2100333608244954589L;
	private Integer id;					//二维码存储主键
	private String userAccount;					//客户经理账号
	private String qrPath;					//二维码路径
	private String qrName;					//二维码名称
	private String qrSize;					//二维码大小

	public String getQrName() {
		return qrName;
	}

	public void setQrName(String qrName) {
		this.qrName = qrName;
	}

	public Integer getId(){
		return this.id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public String getUserAccount(){
		return this.userAccount;
	}

	public void setUserAccount(String userAccount){
		this.userAccount=userAccount;
	}

	public String getQrPath(){
		return this.qrPath;
	}

	public void setQrPath(String qrPath){
		this.qrPath=qrPath;
	}



	public String getQrSize(){
		return this.qrSize;
	}

	public void setQrSize(String qrSize){
		this.qrSize=qrSize;
	}

}
