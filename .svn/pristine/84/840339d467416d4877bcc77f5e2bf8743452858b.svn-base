package banger.domain.config;

import java.io.Serializable;
import java.util.Date;

/**
 * 自定义字段合计项公式
 */
public class AutoColumnFormula implements Serializable {
	private static final long serialVersionUID = 3482058375529755588L;
	private Integer id;					//主键ID
	private String tableName;					//表名
	private String fieldColumn;					//字段列名
	private String formulaExpressions;					//公式表达式
	private Integer isStatistics;					//是否合计项1 合计项 （统计列表项的和）0 非合计项 （字段组合计算公式的值）
	private Integer isActived;					//是否启用0 禁用1 启用
	private Date createDate;					//创建时间
	private Date updateDate;					//更新时间
	private Integer createUser;					//创建用户
	private Integer updateUser;					//更新用户
	private String formulaExpressionsName;		//表单合计字段中文 非持久化字段

	public String getFormulaExpressionsName() {
		return formulaExpressionsName;
	}

	public void setFormulaExpressionsName(String formulaExpressionsName) {
		this.formulaExpressionsName = formulaExpressionsName;
	}

	public Integer getId(){
		return this.id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public String getTableName(){
		return this.tableName;
	}

	public void setTableName(String tableName){
		this.tableName=tableName;
	}

	public String getFieldColumn(){
		return this.fieldColumn;
	}

	public void setFieldColumn(String fieldColumn){
		this.fieldColumn=fieldColumn;
	}

	public String getFormulaExpressions(){
		return this.formulaExpressions;
	}

	public void setFormulaExpressions(String formulaExpressions){
		this.formulaExpressions=formulaExpressions;
	}

	public Integer getIsStatistics(){
		return this.isStatistics;
	}

	public void setIsStatistics(Integer isStatistics){
		this.isStatistics=isStatistics;
	}

	public Integer getIsActived(){
		return this.isActived;
	}

	public void setIsActived(Integer isActived){
		this.isActived=isActived;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Integer getCreateUser(){
		return this.createUser;
	}

	public void setCreateUser(Integer createUser){
		this.createUser=createUser;
	}

	public Integer getUpdateUser(){
		return this.updateUser;
	}

	public void setUpdateUser(Integer updateUser){
		this.updateUser=updateUser;
	}

}
