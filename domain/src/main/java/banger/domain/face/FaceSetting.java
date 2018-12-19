package banger.domain.face;

import java.io.Serializable;

import banger.framework.entity.Entity;

/**
 * 旷世人脸比对配置
 */
public class FaceSetting implements Serializable {
	private static final long serialVersionUID = 7883700140730110157L;
	private Integer faceId;					//主键ID
	private String faceAppKey;					//团队ID
	private String faceAppSecret;					//团队成员
	private String faceAppUrl;					//人脸比对接口地址
	private String faceOcrKey;					//身份证识别键
	private String faceOcrSecret;					//身份证识别秘钥
	private String faceOcrUrl;					//身份证识别接口地址

	public Integer getFaceId(){
		return this.faceId;
	}

	public void setFaceId(Integer faceId){
		this.faceId=faceId;
	}

	public String getFaceAppKey(){
		return this.faceAppKey;
	}

	public void setFaceAppKey(String faceAppKey){
		this.faceAppKey=faceAppKey;
	}

	public String getFaceAppSecret(){
		return this.faceAppSecret;
	}

	public void setFaceAppSecret(String faceAppSecret){
		this.faceAppSecret=faceAppSecret;
	}

	public String getFaceAppUrl(){
		return this.faceAppUrl;
	}

	public void setFaceAppUrl(String faceAppUrl){
		this.faceAppUrl=faceAppUrl;
	}

	public String getFaceOcrKey(){
		return this.faceOcrKey;
	}

	public void setFaceOcrKey(String faceOcrKey){
		this.faceOcrKey=faceOcrKey;
	}

	public String getFaceOcrSecret(){
		return this.faceOcrSecret;
	}

	public void setFaceOcrSecret(String faceOcrSecret){
		this.faceOcrSecret=faceOcrSecret;
	}

	public String getFaceOcrUrl(){
		return this.faceOcrUrl;
	}

	public void setFaceOcrUrl(String faceOcrUrl){
		this.faceOcrUrl=faceOcrUrl;
	}

}
