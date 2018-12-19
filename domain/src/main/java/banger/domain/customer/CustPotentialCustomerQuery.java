package banger.domain.customer;

import banger.framework.codetable.CodeTable;
import banger.framework.util.IdCardUtil;
import banger.framework.util.StringUtil;
import org.apache.commons.lang.StringUtils;

/**
 * Created by hgx on 2017/9/26.
 */
public class CustPotentialCustomerQuery extends CustPotentialCustomers{

    protected String loanIntentionName;
    //归属人
    private String belongUserName;
    //团队名
    private String attributionTeamName;

    @CodeTable(name = "cdDictColByName", key = "cardType", params = "CD_IDENTIFY_TYPE")
    private String identifyTypeName;

    public String getIdentifyTypeName() {
        return identifyTypeName;
    }

    public void setIdentifyTypeName(String identifyTypeName) {
        this.identifyTypeName = identifyTypeName;
    }

    private String cardNumberX;

    private String telephoneNumberX;

    private String productName; //意向产品名称

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBelongUserName() {
        if(StringUtils.isNotBlank(belongUserName))
        {
            return belongUserName;

        }else {
            return attributionTeamName;
        }
    }

    public void setBelongUserName(String belongUserName) {
        this.belongUserName = belongUserName;
    }

    public String getLoanIntentionName() {
        if (getLoanIntention()==1)
        {
            return "有";
        }else if (getLoanIntention()==0) {
            return "无";
        }else {
            return "";
        }
    }

    public String getTelephoneNumberX() {
        return IdCardUtil.getTelNumX(getTelephoneNumber(),1);
    }

    public void setTelephoneNumberX(String telephoneNumberX) {
        this.telephoneNumberX = telephoneNumberX;
    }

    public String getCardNumberX() {
        return IdCardUtil.getIdentifyNumX(getCardNumber(), getCardType() ,1);
    }

    public void setCardNumberX(String cardNumberX) {
        this.cardNumberX = cardNumberX;
    }

    public String getAttributionTeamName() {
        return attributionTeamName;
    }

    public void setAttributionTeamName(String attributionTeamName) {
        this.attributionTeamName = attributionTeamName;
    }
}
