package banger.domain.customer;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 */
public class CustFamilyInfo implements Serializable {
	private static final long serialVersionUID = 6910368313850182743L;
	private Integer id;					//
	private String housePlace;					//居住场所
	private Integer houseArea;					//居住面积
	private Date beginLiveTime;					//居住起始年月
	private String decorationStatus;					//装修情况
	private String houseLayout;					//格局
	private Integer familyMemberNum;					//家庭成员个数
	private Integer supportMemberNum;					//供养人数
	private String childrenSituation;					//子女情况
	private String parentSituation;					//父母情况

	public Integer getId(){
		return this.id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public String getHousePlace(){
		return this.housePlace;
	}

	public void setHousePlace(String housePlace){
		this.housePlace=housePlace;
	}

	public Integer getHouseArea(){
		return this.houseArea;
	}

	public void setHouseArea(Integer houseArea){
		this.houseArea=houseArea;
	}

	public Date getBeginLiveTime(){
		return this.beginLiveTime;
	}

	public void setBeginLiveTime(Date beginLiveTime){
		this.beginLiveTime=beginLiveTime;
	}

	public String getDecorationStatus(){
		return this.decorationStatus;
	}

	public void setDecorationStatus(String decorationStatus){
		this.decorationStatus=decorationStatus;
	}

	public String getHouseLayout() {
		return houseLayout;
	}

	public void setHouseLayout(String houseLayout) {
		this.houseLayout = houseLayout;
	}

	public Integer getFamilyMemberNum(){
		return this.familyMemberNum;
	}

	public void setFamilyMemberNum(Integer familyMemberNum){
		this.familyMemberNum=familyMemberNum;
	}

	public Integer getSupportMemberNum(){
		return this.supportMemberNum;
	}

	public void setSupportMemberNum(Integer supportMemberNum){
		this.supportMemberNum=supportMemberNum;
	}

	public String getChildrenSituation(){
		return this.childrenSituation;
	}

	public void setChildrenSituation(String childrenSituation){
		this.childrenSituation=childrenSituation;
	}

	public String getParentSituation(){
		return this.parentSituation;
	}

	public void setParentSituation(String parentSituation){
		this.parentSituation=parentSituation;
	}

}
