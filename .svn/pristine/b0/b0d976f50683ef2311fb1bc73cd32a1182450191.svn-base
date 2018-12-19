package banger.dao.impl;

import banger.dao.intf.IContractTemplateFileDao;
import banger.domain.loan.contract.LoanContractTemplateFile;
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
public class ContractTemplateFileDao extends PageSizeDao<LoanContractTemplateFile> implements IContractTemplateFileDao {

	/**
	 * 新增
	 * @param contractTemplateFile 实体对像
	 */
	public void insertContractTemplateFile(LoanContractTemplateFile contractTemplateFile){
		contractTemplateFile.setId(this.newId().intValue());
		this.execute("insertContractTemplateFile",contractTemplateFile);
	}

	/**
	 *修改
	 * @param contractTemplateFile 实体对像
	 */
	public void updateContractTemplateFile(LoanContractTemplateFile contractTemplateFile){
		this.execute("updateContractTemplateFile",contractTemplateFile);
	}

	/**
	 * 通过主键删除
	 * @param id 主键Id
	 */
	public void deleteContractTemplateFileById(Integer id){
		this.execute("deleteContractTemplateFileById",id);
	}

	/**
	 * 通过主键得到
	 * @param id 主键Id
	 */
	public LoanContractTemplateFile getContractTemplateFileById(Integer id){
		return (LoanContractTemplateFile)this.queryEntity("getContractTemplateFileById",id);
	}

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanContractTemplateFile> queryContractTemplateFileList(Map<String,Object> condition){
		return (List<LoanContractTemplateFile>)this.queryEntities("queryContractTemplateFileList", condition);
	}

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanContractTemplateFile> queryContractTemplateFileList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanContractTemplateFile>)this.queryEntities("queryContractTemplateFileList", page, condition);
	}

	@Override
	public List<LoanContractTemplateFile> queryTemplateFileByTypeId(Integer typeId) {
		return (List<LoanContractTemplateFile>)this.queryEntities("queryTemplateFileByTypeId", typeId);
	}

	@Override
	public List<LoanContractTemplateFile> queryTemplateFilesByLoanTypeId(Map<String,Object> condition) {
		return (List<LoanContractTemplateFile>)this.queryEntities("queryTemplateFilesByLoanTypeId", condition);
	}


}
