package banger.domain.config;

import java.io.Serializable;

import banger.framework.entity.Entity;

/**
 * 模板配置文件信息表，保存上传模板，合同相对应的路径
 */
public class ModeConfigFile implements Serializable {
	private static final long serialVersionUID = 1365554994327647375L;
	private Integer id;					//主键
	private String modeName;					//模板名称
	private String filePath;					//文件路径
	private Integer modeType;					//模板类型1：调查报告2：合同
	private String dataTable;					//要查询的数据表 申请信息-one 信贷历史-list
	private Integer isDel;					//是否删除

	public Integer getId(){
		return this.id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public String getModeName(){
		return this.modeName;
	}

	public void setModeName(String modeName){
		this.modeName=modeName;
	}

	public String getFilePath(){
		return this.filePath;
	}

	public void setFilePath(String filePath){
		this.filePath=filePath;
	}

	public Integer getModeType(){
		return this.modeType;
	}

	public void setModeType(Integer modeType){
		this.modeType=modeType;
	}

	public String getDataTable() {
		return dataTable;
	}

	public void setDataTable(String dataTable) {
		this.dataTable = dataTable;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

}
