package banger.service.intf;


import banger.domain.loan.LoanTrustedPayment;
import banger.domain.loan.trusted.TrustedPayment;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * 授权支付访问接口
 */
public interface ITrustedPaymentService {

	/**
	 * 新增受托支付表
	 */
	void insertTrustedPaymentInfo(LoanTrustedPayment trustedPayment);

	/**
	 *修改受托支付表
	 */
	void updateTrustedPaymentInfo(LoanTrustedPayment trustedPayment);


	/**
	 * 通过主键删除受托支付
	 */
	void deleteTrustedPaymentInfoById(Integer id);

	/**
	 * 通过主键得到受托支付表
	 */
	LoanTrustedPayment getTrustedPaymentInfoById(Integer id);

	/**
	 * 得到受托支付列表
	 * @return
	 */
	List<LoanTrustedPayment> getTrustedPaymentList(Map<String, Object> condition);

	/**
	 * 设置受托支付支付ID、支付状态
	 */
	String setPaymentIDAndPaymentStatus(Integer loanId);


	IPageList<TrustedPayment> getTrustedPayment(Map<String, Object> map, IPageSize page);

}
