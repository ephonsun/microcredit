package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.ICustCreditCheckFileDao;
import banger.domain.customer.CustCreditCheckFile;

/**
 * 客户征信调查附件表数据访问类
 */
@Repository
public class CustCreditCheckFileDao extends PageSizeDao<CustCreditCheckFile> implements ICustCreditCheckFileDao {

	/**
	 * 新增客户征信调查附件表
	 * @param custCreditCheckFile 实体对像
	 */
	public void insertCustCreditCheckFile(CustCreditCheckFile custCreditCheckFile){
		custCreditCheckFile.setCreditCheckFileId(this.newId().intValue());
		this.execute("insertCustCreditCheckFile",custCreditCheckFile);
	}

	/**
	 *修改客户征信调查附件表
	 * @param custCreditCheckFile 实体对像
	 */
	public void updateCustCreditCheckFile(CustCreditCheckFile custCreditCheckFile){
		this.execute("updateCustCreditCheckFile",custCreditCheckFile);
	}

	/**
	 * 通过主键删除客户征信调查附件表
	 * @param creditCheckFileId 主键Id
	 */
	public void deleteCustCreditCheckFileById(Integer creditCheckFileId){
		this.execute("deleteCustCreditCheckFileById",creditCheckFileId);
	}

	/**
	 * 通过主键得到客户征信调查附件表
	 * @param creditCheckFileId 主键Id
	 */
	public CustCreditCheckFile getCustCreditCheckFileById(Integer creditCheckFileId){
		return (CustCreditCheckFile)this.queryEntity("getCustCreditCheckFileById",creditCheckFileId);
	}

	/**
	 * 查询客户征信调查附件表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CustCreditCheckFile> queryCustCreditCheckFileList(Map<String,Object> condition){
		return (List<CustCreditCheckFile>)this.queryEntities("queryCustCreditCheckFileList", condition);
	}


}
