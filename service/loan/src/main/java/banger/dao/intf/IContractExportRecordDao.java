package banger.dao.intf;

import banger.domain.loan.contract.LoanContractExportRecord;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * 数据访问接口
 */
public interface IContractExportRecordDao {

	/**
	 * 新增
	 * @param contractExportRecord 实体对像
	 */
	void insertContractExportRecord(LoanContractExportRecord contractExportRecord);

	/**
	 *修改
	 * @param contractExportRecord 实体对像
	 */
	void updateContractExportRecord(LoanContractExportRecord contractExportRecord);

	/**
	 * 通过主键删除
	 * @param id 主键Id
	 */
	void deleteContractExportRecordById(Integer id);

	/**
	 * 通过主键得到
	 * @param id 主键Id
	 */
	LoanContractExportRecord getContractExportRecordById(Integer id);

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanContractExportRecord> queryContractExportRecordList(Map<String, Object> condition);

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanContractExportRecord> queryContractExportRecordList(Map<String, Object> condition, IPageSize page);

}
