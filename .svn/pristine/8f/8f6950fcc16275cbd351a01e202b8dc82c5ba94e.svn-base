package banger.domain.html5;

import banger.framework.util.StringUtil;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class IntoIntefaceRecordQuery extends  IntoIntefaceRecord{


	private String faceIntoType;
	private String isSuccessed;

	private String time;



	public String getFaceIntoType() {
     	if ("0".equals(faceIntoType.substring(0,1))){
     		return  "ocr";
		}else if ("1".equals(faceIntoType.substring(0,1))){
			return  "人脸识别";
		}else {
			return "";
		}

	}

	public void setFaceIntoType(String faceIntoType) {

		this.faceIntoType = faceIntoType;
	}

	public String getIsSuccessed() {

			if ("0".equals(isSuccessed.substring(0,1))){
				return  "失败";
			}else if ("1".equals(isSuccessed.substring(0,1))){
				return "成功";
			}else {
				return "";
			}

	}

	public void setIsSuccessed(String isSuccessed) {
		this.isSuccessed = isSuccessed;
	}



	public String getTime() throws ParseException {
		DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSSSSS");
		Date date = fmt.parse(time);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	public void setTime(String time) {
		this.time = time;
	}
}
