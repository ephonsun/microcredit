package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanInfoAddedFilesExtend;
import banger.domain.loan.LoanUploadRecord;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanInfoAddedFiles;

/**
 * 贷款资料附件文件表数据访问接口
 */
public interface IInfoAddedFilesDao {

	/**
	 * 新增贷款资料附件文件表
	 *
	 * @param infoAddedFiles 实体对像
	 */
	void insertInfoAddedFiles(LoanInfoAddedFiles infoAddedFiles);

	/**
	 * 修改贷款资料附件文件表
	 *
	 * @param infoAddedFiles 实体对像
	 */
	void updateInfoAddedFiles(LoanInfoAddedFiles infoAddedFiles);

	/**
	 * 通过主键删除贷款资料附件文件表
	 *
	 * @param id 主键Id
	 */
	void deleteInfoAddedFilesById(Integer id);

	/**
	 * 是否存在,得到存在的ID
	 *
	 * @param infoAddedFiles
	 * @return
	 */
	Integer getAddedFileIdByRecord(LoanInfoAddedFiles infoAddedFiles);

	/**
	 * 通过主键得到贷款资料附件文件表
	 *
	 * @param id 主键Id
	 */
	LoanInfoAddedFiles getInfoAddedFilesById(Integer id);

	/**
	 * 通过附件fileId得到附件信息
	 * @param fileId
	 * @return
	 */
	LoanInfoAddedFiles getLoanAddedFileInfoByFileId(String fileId);

	/**
	 * 获取附件文件例表通过贷款id和过程类型
	 *
	 * @param loanId
	 * @param processTypes
	 * @return
	 */
	List<LoanInfoAddedFiles> getInfoAddedFilesListById(Integer loanId, String processTypes);

	/**
	 * 获取附件文件例表通过贷款id和文件类型
	 *
	 * @param loanId
	 * @param fileType
	 * @return
	 */
	List<LoanInfoAddedFiles> getInfoAddedFilesListByType(Integer loanId, String fileType);

	/**
	 * 查询贷款资料附件文件表
	 *
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanInfoAddedFiles> queryInfoAddedFilesList(Map<String, Object> condition);

	/**
	 * 分页查询贷款资料附件文件表
	 *
	 * @param condition 查询条件
	 * @param page      分页对像
	 * @return
	 */
	IPageList<LoanInfoAddedFiles> queryInfoAddedFilesList(Map<String, Object> condition, IPageSize page);

	/**
	 * 查询贷款资料列表
	 */
	List<LoanInfoAddedFilesExtend> queryLoanInfoFileByLoanId(Integer loanId);

	/**
	 * 查询贷款资料列表（多条件）
	 */
	List<LoanInfoAddedFilesExtend> queryLoanInfoFile(Map<String, Object> condition);

	/**
	 * 根据id查询贷款资料列表
	 *
	 * @param id
	 * @return
	 */
	LoanInfoAddedFilesExtend getInfoAddedFilesExtendById(Integer id);

	/**
	 * 查询贷款资料资料统计（excel导出）
	 */
	 List<LoanInfoAddedFilesExtend> getAddFileMount(Map<String,Object> condition);

	/**
	 * 查询分类类型
	 */
	List<LoanInfoAddedFilesExtend> queryOwnerNameByLoanId(Integer loanId);

	List<LoanInfoAddedFilesExtend> queryClassNameByOwnerId(Integer ownerId,String loanId);

}