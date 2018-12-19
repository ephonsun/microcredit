package banger.dao.impl;


import banger.dao.intf.ITrustedPaymentDao;
import banger.domain.loan.LoanTrustedPayment;
import banger.domain.loan.trusted.TrustedPayment;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 授权支付访问类
 */
@Repository
public class TrustedPaymentDao extends PageSizeDao<LoanTrustedPayment> implements ITrustedPaymentDao {

	public Integer getNewId() {
		return this.newId().intValue();
	}

	@Override
	public void insertTrustedPaymentInfo(LoanTrustedPayment trustedPayment) {
		trustedPayment.setId(this.newId().intValue());
		this.execute("insertTrustedPaymentInfo",trustedPayment);
	}

	@Override
	public void updateTrustedPaymentInfo(LoanTrustedPayment trustedPayment) {
		this.execute("updateTrustedPaymentInfo",trustedPayment);
	}

	@Override
	public void deleteTrustedPaymentInfoById(Integer id) {
		this.execute("deleteTrustedPaymentInfoById",id);
	}

	@Override
	public LoanTrustedPayment getTrustedPaymentInfoById(Integer id) {
		LoanTrustedPayment trustedPayment = (LoanTrustedPayment)this.queryEntity("getTrustedPaymentInfoById",id);
		return trustedPayment;
	}

	@Override
	public List<LoanTrustedPayment> getTrustedPaymentList(Map<String, Object> condition) {
		return (List<LoanTrustedPayment>)this.queryEntities("getTrustedPaymentList",condition);
	}

	@Override
	public IPageList<TrustedPayment> getTrustedPayment(Map<String, Object> map, IPageSize page) {
		return (IPageList<TrustedPayment>) this.queryEntities("getTrustedPayment",page,map);
	}
}
