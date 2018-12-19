package banger.domain.customer;

import banger.framework.codetable.CodeTable;
import banger.framework.util.IdCardUtil;
import banger.framework.util.SensitiveUtil;
import org.apache.commons.lang.StringUtils;

/**
 * Created by zhusw on 2017/7/13.
 */
public class CustBasicInfoQuery extends CustBasicInfo {
    private String belongUserName;
    @CodeTable(name = "cdDictColByName", key = "customerLevel", params = "CD_CUSTOMER_LEVEL")
    protected String customerLevelName;
    private String loanIdentifyNumX;
    private String loanTelNumX;
    private String loanId;
    private Boolean editable;

    @CodeTable(name="cdDictColByName",key="identifyType",params="CD_IDENTIFY_TYPE")
    private String identifyTypeName;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getBelongUserName() {
        return belongUserName;
    }

    public void setBelongUserName(String belongUserName) {
        this.belongUserName = belongUserName;
    }

    public String getIdentifyNumX() {
        loanIdentifyNumX = getIdentifyNum();
        String identifyType = getIdentifyType();
        return IdCardUtil.getIdentifyNumX(loanIdentifyNumX, identifyType,1);
    }

    public String getPhoneNumberX() {
        loanTelNumX = getPhoneNumber();
        if(StringUtils.isBlank(loanTelNumX))
            return "";
        return IdCardUtil.getTelNumX(loanTelNumX,1);
    }

    /**
     * 是否可以编辑
     * @return
     */
    public Boolean getEditable(){
        return this.editable;
    }

    public Boolean setEditable(Boolean editable){
        return this.editable = editable;
    }

    public String getIdentifyTypeName() {
        return identifyTypeName;
    }

    public void setIdentifyTypeName(String identifyTypeName) {
        this.identifyTypeName = identifyTypeName;
    }

    public String getCustomerLevelName() {
        return customerLevelName;
    }

    public void setCustomerLevelName(String customerLevelName) {
        this.customerLevelName = customerLevelName;
    }
}
