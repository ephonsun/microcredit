package banger.dao.intf;

import banger.domain.loan.contract.LoanContractTemplateFile;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;

import java.util.List;
import java.util.Map;

/**
 * 数据访问接口
 */
public interface IContractTemplateFileDao {

	/**
	 * 新增
	 * @param contractTemplateFile 实体对像
	 */
	void insertContractTemplateFile(LoanContractTemplateFile contractTemplateFile);

	/**
	 *修改
	 * @param contractTemplateFile 实体对像
	 */
	void updateContractTemplateFile(LoanContractTemplateFile contractTemplateFile);

	/**
	 * 通过主键删除
	 * @param id 主键Id
	 */
	void deleteContractTemplateFileById(Integer id);

	/**
	 * 通过主键得到
	 * @param id 主键Id
	 */
	LoanContractTemplateFile getContractTemplateFileById(Integer id);

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanContractTemplateFile> queryContractTemplateFileList(Map<String, Object> condition);

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanContractTemplateFile> queryContractTemplateFileList(Map<String, Object> condition, IPageSize page);

	List<LoanContractTemplateFile> queryTemplateFileByTypeId(Integer typeId);


	List<LoanContractTemplateFile> queryTemplateFilesByLoanTypeId(Map<String, Object> condition);
}
