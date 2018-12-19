package banger.domain.customer;

import java.io.Serializable;

/**
 * 
 */
public class CustSpouseInfo implements Serializable {
	private static final long serialVersionUID = 6858543331525383959L;
	private Integer id;					//
	private String spouseName;					//配偶姓名
	private String spouseSex;					//配偶性别
	private Integer spouseAge;					//配偶年龄
	private String spousePhoneNum;					//联系电话
	private String spouseIdentifyType;					//证件类型
	private String spouseIdentifyNum;					//证件号码
	private String spouseDegree;					//学历
	private String spouseMonthlyIncome;					//

	public Integer getId(){
		return this.id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public String getSpouseName(){
		return this.spouseName;
	}

	public void setSpouseName(String spouseName){
		this.spouseName=spouseName;
	}

	public String getSpouseSex(){
		return this.spouseSex;
	}

	public void setSpouseSex(String spouseSex){
		this.spouseSex=spouseSex;
	}

	public Integer getSpouseAge(){
		return this.spouseAge;
	}

	public void setSpouseAge(Integer spouseAge){
		this.spouseAge=spouseAge;
	}

	public String getSpousePhoneNum(){
		return this.spousePhoneNum;
	}

	public void setSpousePhoneNum(String spousePhoneNum){
		this.spousePhoneNum=spousePhoneNum;
	}

	public String getSpouseIdentifyType(){
		return this.spouseIdentifyType;
	}

	public void setSpouseIdentifyType(String spouseIdentifyType){
		this.spouseIdentifyType=spouseIdentifyType;
	}

	public String getSpouseIdentifyNum(){
		return this.spouseIdentifyNum;
	}

	public void setSpouseIdentifyNum(String spouseIdentifyNum){
		this.spouseIdentifyNum=spouseIdentifyNum;
	}

	public String getSpouseDegree(){
		return this.spouseDegree;
	}

	public void setSpouseDegree(String spouseDegree){
		this.spouseDegree=spouseDegree;
	}

	public String getSpouseMonthlyIncome(){
		return this.spouseMonthlyIncome;
	}

	public void setSpouseMonthlyIncome(String spouseMonthlyIncome){
		this.spouseMonthlyIncome=spouseMonthlyIncome;
	}

}
