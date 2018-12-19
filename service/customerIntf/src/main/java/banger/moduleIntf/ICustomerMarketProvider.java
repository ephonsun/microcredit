package banger.moduleIntf;

import banger.domain.customer.CustMarketCustomer;

/**
 * 客户预申请
 * Created by zhusw on 2017/7/7.
 */
public interface ICustomerMarketProvider {

    /**
     * 更新客户的预申请贷款Id
     * @param marketCustomerId
     * @param loanId
     */
    void updateCustomerLoanId(Integer marketCustomerId,Integer loanId);


    CustMarketCustomer getCustMarketCustomerById(Integer marketCustomerId);

}
