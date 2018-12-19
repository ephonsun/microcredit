package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.customer.CustCreditCheckFile;

/**
 * 客户征信调查附件表业务访问接口
 */
public interface ICustCreditCheckFileService {

	/**
	 * 新增客户征信调查附件表
	 * @param custCreditCheckFile 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertCustCreditCheckFile(CustCreditCheckFile custCreditCheckFile,Integer loginUserId);

	/**
	 *修改客户征信调查附件表
	 * @param custCreditCheckFile 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateCustCreditCheckFile(CustCreditCheckFile custCreditCheckFile,Integer loginUserId);

	/**
	 * 通过主键删除客户征信调查附件表
	 * @param CREDIT_CHECK_FILE_ID 主键Id
	 */
	void deleteCustCreditCheckFileById(Integer creditCheckFileId);

	/**
	 * 通过主键得到客户征信调查附件表
	 * @param CREDIT_CHECK_FILE_ID 主键Id
	 */
	CustCreditCheckFile getCustCreditCheckFileById(Integer creditCheckFileId);

	/**
	 * 查询客户征信调查附件表
	 * @param condition 查询条件
	 * @return
	 */
	List<CustCreditCheckFile> queryCustCreditCheckFileList(Map<String,Object> condition);

}
