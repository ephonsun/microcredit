package banger.dao.impl;

import banger.dao.intf.IContractExportRecordDao;
import banger.domain.loan.contract.LoanContractExportRecord;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 数据访问类
 */
@Repository
public class ContractExportRecordDao extends PageSizeDao<LoanContractExportRecord> implements IContractExportRecordDao {

	/**
	 * 新增
	 * @param contractExportRecord 实体对像
	 */
	public void insertContractExportRecord(LoanContractExportRecord contractExportRecord){
		contractExportRecord.setId(this.newId().intValue());
		this.execute("insertContractExportRecord",contractExportRecord);
	}

	/**
	 *修改
	 * @param contractExportRecord 实体对像
	 */
	public void updateContractExportRecord(LoanContractExportRecord contractExportRecord){
		this.execute("updateContractExportRecord",contractExportRecord);
	}

	/**
	 * 通过主键删除
	 * @param id 主键Id
	 */
	public void deleteContractExportRecordById(Integer id){
		this.execute("deleteContractExportRecordById",id);
	}

	/**
	 * 通过主键得到
	 * @param id 主键Id
	 */
	public LoanContractExportRecord getContractExportRecordById(Integer id){
		return (LoanContractExportRecord)this.queryEntity("getContractExportRecordById",id);
	}

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanContractExportRecord> queryContractExportRecordList(Map<String,Object> condition){
		return (List<LoanContractExportRecord>)this.queryEntities("queryContractExportRecordList", condition);
	}

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanContractExportRecord> queryContractExportRecordList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanContractExportRecord>)this.queryEntities("queryContractExportRecordList", page, condition);
	}

}
