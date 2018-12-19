package banger.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.ICustCreditCheckFileDao;
import banger.service.intf.ICustCreditCheckFileService;
import banger.domain.customer.CustCreditCheckFile;

/**
 * 客户征信调查附件表业务访问类
 */
@Repository
public class CustCreditCheckFileService implements ICustCreditCheckFileService {

	@Autowired
	private ICustCreditCheckFileDao custCreditCheckFileDao;

	/**
	 * 新增客户征信调查附件表
	 * @param custCreditCheckFile 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertCustCreditCheckFile(CustCreditCheckFile custCreditCheckFile,Integer loginUserId){
		custCreditCheckFile.setCreateUser(loginUserId);
		custCreditCheckFile.setCreateDate(DateUtil.getCurrentDate());
		this.custCreditCheckFileDao.insertCustCreditCheckFile(custCreditCheckFile);
	}

	/**
	 *修改客户征信调查附件表
	 * @param custCreditCheckFile 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateCustCreditCheckFile(CustCreditCheckFile custCreditCheckFile,Integer loginUserId){
		this.custCreditCheckFileDao.updateCustCreditCheckFile(custCreditCheckFile);
	}

	/**
	 * 通过主键删除客户征信调查附件表
	 * @param CREDIT_CHECK_FILE_ID 主键Id
	 */
	public void deleteCustCreditCheckFileById(Integer creditCheckFileId){
		this.custCreditCheckFileDao.deleteCustCreditCheckFileById(creditCheckFileId);
	}

	/**
	 * 通过主键得到客户征信调查附件表
	 * @param CREDIT_CHECK_FILE_ID 主键Id
	 */
	public CustCreditCheckFile getCustCreditCheckFileById(Integer creditCheckFileId){
		return this.custCreditCheckFileDao.getCustCreditCheckFileById(creditCheckFileId);
	}

	/**
	 * 查询客户征信调查附件表
	 * @param condition 查询条件
	 * @return
	 */
	public List<CustCreditCheckFile> queryCustCreditCheckFileList(Map<String,Object> condition){
		return this.custCreditCheckFileDao.queryCustCreditCheckFileList(condition);
	}

}
