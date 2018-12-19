package banger.service.impl;


import banger.dao.intf.ITrustedPaymentDao;

import banger.domain.loan.*;

import banger.domain.loan.trusted.TrustedPayment;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.service.intf.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;

/**
 * 授权支付访问类
 */
@Service
public class TrustedPaymentService implements ITrustedPaymentService {

	@Resource
	private ITrustedPaymentDao trustedPaymentDao;

	//设置受托支付的支付ID和支付状态
	@Override
	public String setPaymentIDAndPaymentStatus(Integer loanId) {
		try {
			Map<String,Object> condition = new HashMap<String, Object>();
			condition.put("loanId",loanId);
			List<LoanTrustedPayment> trustedPayments = trustedPaymentDao.getTrustedPaymentList(condition);
			int i=0;
			for(LoanTrustedPayment trustedPayment:trustedPayments){
				i++;
				String paymentId = "000"+i;
				paymentId =  paymentId.substring(paymentId.length()-3);
				trustedPayment.setPaymentId(paymentId);
//				trustedPayment.setPaymentStatus("01");
				trustedPaymentDao.updateTrustedPaymentInfo(trustedPayment);
			}
		}catch (Exception e){
			return "fail";
		}
		return "success";
	}

	@Override
	public void insertTrustedPaymentInfo(LoanTrustedPayment trustedPayment) {
		trustedPaymentDao.insertTrustedPaymentInfo(trustedPayment);
	}

	@Override
	public void updateTrustedPaymentInfo(LoanTrustedPayment trustedPayment) {
		trustedPaymentDao.updateTrustedPaymentInfo(trustedPayment);
	}

	@Override
	public void deleteTrustedPaymentInfoById(Integer id) {
		trustedPaymentDao.deleteTrustedPaymentInfoById(id);
	}

	@Override
	public LoanTrustedPayment getTrustedPaymentInfoById(Integer id) {
		return trustedPaymentDao.getTrustedPaymentInfoById(id);
	}

	@Override
	public List<LoanTrustedPayment> getTrustedPaymentList(Map<String, Object> condition) {
		return trustedPaymentDao.getTrustedPaymentList(condition);
	}

	@Override
	public IPageList<TrustedPayment> getTrustedPayment(Map<String, Object> map, IPageSize page) {
		return trustedPaymentDao.getTrustedPayment(map,page);
	}
}
