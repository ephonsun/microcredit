package banger.service.intf;

import banger.domain.loan.contract.LoanContractExportRecord;
import banger.domain.loan.contract.LoanContractRelateItem;
import banger.domain.loan.contract.LoanContractTemplateFile;
import banger.domain.loan.contract.LoanContractTemplateType;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * 业务访问接口
 */
public interface IContractService {


	/**
	 * 新增
	 * @param contractExportRecord 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertContractExportRecord(LoanContractExportRecord contractExportRecord,Integer loginUserId);

	/**
	 *修改
	 * @param contractExportRecord 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateContractExportRecord(LoanContractExportRecord contractExportRecord,Integer loginUserId);

	/**
	 * 通过主键删除
	 * @param ID 主键Id
	 */
	void deleteContractExportRecordById(Integer id);

	/**
	 * 通过主键得到
	 * @param ID 主键Id
	 */
	LoanContractExportRecord getContractExportRecordById(Integer id);

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanContractExportRecord> queryContractExportRecordList(Map<String,Object> condition);

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanContractExportRecord> queryContractExportRecordList(Map<String,Object> condition,IPageSize page);


	/**
	 * 新增
	 * @param contractRelateItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertContractRelateItem(LoanContractRelateItem contractRelateItem,Integer loginUserId);

	/**
	 *修改
	 * @param contractRelateItem 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateContractRelateItem(LoanContractRelateItem contractRelateItem,Integer loginUserId);

	/**
	 * 通过主键删除
	 * @param ID 主键Id
	 */
	void deleteContractRelateItemById(Integer id);

	/**
	 * 通过主键得到
	 * @param ID 主键Id
	 */
	LoanContractRelateItem getContractRelateItemById(Integer id);

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanContractRelateItem> queryContractRelateItemList(Map<String,Object> condition);

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanContractRelateItem> queryContractRelateItemList(Map<String,Object> condition,IPageSize page);


	/**
	 * 新增
	 * @param contractTemplateFile 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertContractTemplateFile(LoanContractTemplateFile contractTemplateFile,Integer loginUserId);

	/**
	 *修改
	 * @param contractTemplateFile 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateContractTemplateFile(LoanContractTemplateFile contractTemplateFile,Integer loginUserId);

	/**
	 * 通过主键删除
	 * @param ID 主键Id
	 */
	void deleteContractTemplateFileById(Integer id);

	/**
	 * 通过主键得到
	 * @param ID 主键Id
	 */
	LoanContractTemplateFile getContractTemplateFileById(Integer id);

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanContractTemplateFile> queryContractTemplateFileList(Map<String,Object> condition);

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanContractTemplateFile> queryContractTemplateFileList(Map<String,Object> condition,IPageSize page);

	/**
	 * 新增
	 * @param contractTemplateType 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertContractTemplateType(LoanContractTemplateType contractTemplateType,Integer loginUserId);

	/**
	 *修改
	 * @param contractTemplateType 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateContractTemplateType(LoanContractTemplateType contractTemplateType,Integer loginUserId);

	/**
	 * 通过主键删除
	 * @param ID 主键Id
	 */
	void deleteContractTemplateTypeById(Integer id);

	/**
	 * 通过主键得到
	 * @param ID 主键Id
	 */
	LoanContractTemplateType getContractTemplateTypeById(Integer id);

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanContractTemplateType> queryContractTemplateTypeList(Map<String,Object> condition);

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanContractTemplateType> queryContractTemplateTypeList(Map<String,Object> condition,IPageSize page);

	List<LoanContractTemplateFile> queryTemplateFileByTypeId(Integer integer);

	String getTemplateSelectFile(String loanTypeId);

	void deleteContractRelateItemByLoanTypeId(Integer loanTypeId);

	String getExportTemplateFile(String loanTypeId);
}
