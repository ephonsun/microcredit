package banger.moduleIntf;

import banger.domain.customer.CustCustomerBlack;

/**
 * 客户黑名单
 * Created by zhusw on 2017/7/5.
 */
public interface ICustomerBlackProvider {

    /**
     * 保存黑名称客户
     * @param custCustomerBlack
     */
    void saveCustomerBlack(CustCustomerBlack custCustomerBlack);

    /**
     * 是否是黑名单客户
     * @param loanId
     */
    boolean isExistCustomerBlack(Integer loanId);

    /**
     * 是否是黑名单客户
     * @param customerName
     * @param cardType
     * @param cardNo
     * @return
     */
    boolean isExistCustomerBlack(String customerName,String cardType,String cardNo);

}
