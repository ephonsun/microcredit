package banger.domain.enumerate;

import banger.framework.collection.OptionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 贷款资料附件
 * @author zhusw
 *
 */
public enum LoanAddedFileType {

	IMAGE("Image","图片","Image","Image/Thumb"),
	VIDEO("Video","视频","Video",""),
	AUDIO("Audio","录音","Audio",""),
	OTHER("Other","其他","Other",""),
	IDIMAGE("IDImage","身份证图片","IDImage","IDImage/Thumb"),
	QRIMAGE("QRIMAGE","二维码图片","QRImage","QRImage/Thumb"),
	;
	
	public final String type; // 贷款资料附件文件类型
	public final String typeName; // 贷款资料附件文件类型名称
	public final String savePath;	//保存文件路径
	public final String thumbPath;	//缩略图片路径
	
	LoanAddedFileType(String type, String typeName , String savePath ,String thumbPath) {
		this.type = type;
		this.typeName = typeName;
		this.savePath = savePath;
		this.thumbPath = thumbPath;
	}
	
	public static LoanAddedFileType valueOfType(String type){
		for(LoanAddedFileType fileType : LoanAddedFileType.values()){
			if(fileType.type.equalsIgnoreCase(type)){
				return fileType;
			}
		}
		return null;
	}

	public static List<OptionItem> getOptionItems() {
		List<OptionItem> items = new ArrayList<OptionItem>();
		for (LoanAddedFileType loanAddedFile : LoanAddedFileType.values()) {
			if(loanAddedFile.type.equals(LoanAddedFileType.IDIMAGE.type))
				continue;
			OptionItem item = new OptionItem(loanAddedFile.type,loanAddedFile.typeName);
			items.add(item);
		}
		return items;
	}
}
