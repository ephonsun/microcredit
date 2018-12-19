package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.customer.CustCreditCheckFile;

/**
 * 客户征信调查附件表数据访问接口
 */
public interface ICustCreditCheckFileDao {

	/**
	 * 新增客户征信调查附件表
	 * @param custCreditCheckFile 实体对像
	 */
	void insertCustCreditCheckFile(CustCreditCheckFile custCreditCheckFile);

	/**
	 *修改客户征信调查附件表
	 * @param custCreditCheckFile 实体对像
	 */
	void updateCustCreditCheckFile(CustCreditCheckFile custCreditCheckFile);

	/**
	 * 通过主键删除客户征信调查附件表
	 * @param creditCheckFileId 主键Id
	 */
	void deleteCustCreditCheckFileById(Integer creditCheckFileId);

	/**
	 * 通过主键得到客户征信调查附件表
	 * @param creditCheckFileId 主键Id
	 */
	CustCreditCheckFile getCustCreditCheckFileById(Integer creditCheckFileId);

	/**
	 * 查询客户征信调查附件表
	 * @param condition 查询条件
	 * @return
	 */
	List<CustCreditCheckFile> queryCustCreditCheckFileList(Map<String,Object> condition);

}
