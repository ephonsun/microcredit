package banger.domain.permission;

import java.io.Serializable;

import banger.framework.entity.Entity;

/**
 * 配置平台
 */
public class PmsSu implements Serializable {
	private static final long serialVersionUID = 5336290895003651614L;
	private Integer id;					//主键
	private String su;					//用户
	private String sn;					//名称
	private String pw;					//密码

	public Integer getId(){
		return this.id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public String getSu(){
		return this.su;
	}

	public void setSu(String su){
		this.su=su;
	}

	public String getPw(){
		return this.pw;
	}

	public void setPw(String pw){
		this.pw=pw;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}
}
