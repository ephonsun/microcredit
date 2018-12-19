package banger.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanInfoAddedFilesExtend;
import banger.domain.loan.LoanUploadRecord;
import com.hundsun.common.lang.StringUtil;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IInfoAddedFilesDao;
import banger.domain.loan.LoanInfoAddedFiles;

/**
 * 贷款资料附件文件表数据访问类
 */
@Repository
public class InfoAddedFilesDao extends PageSizeDao<LoanInfoAddedFiles> implements IInfoAddedFilesDao {

	/**
	 * 新增贷款资料附件文件表
	 * @param infoAddedFiles 实体对像
	 */
	public void insertInfoAddedFiles(LoanInfoAddedFiles infoAddedFiles){
		infoAddedFiles.setId(this.newId().intValue());
		this.execute("insertInfoAddedFiles",infoAddedFiles);
	}

	/**
	 *修改贷款资料附件文件表
	 * @param infoAddedFiles 实体对像
	 */
	public void updateInfoAddedFiles(LoanInfoAddedFiles infoAddedFiles){
		this.execute("updateInfoAddedFiles",infoAddedFiles);
	}

	/**
	 * 通过主键删除贷款资料附件文件表
	 * @param id 主键Id
	 */
	public void deleteInfoAddedFilesById(Integer id){
		this.execute("deleteInfoAddedFilesById",id);
	}

	/**
	 * 通过主键得到贷款资料附件文件表
	 * @param id 主键Id
	 */
	public LoanInfoAddedFiles getInfoAddedFilesById(Integer id){
		return (LoanInfoAddedFiles)this.queryEntity("getInfoAddedFilesById",id);
	}

	public LoanInfoAddedFiles getLoanAddedFileInfoByFileId(String fileId){
		return (LoanInfoAddedFiles)this.queryEntity("getInfoAddedFilesByFileId",fileId);
	}

	/**
	 * 查询贷款资料附件文件表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanInfoAddedFiles> queryInfoAddedFilesList(Map<String,Object> condition){
		return (List<LoanInfoAddedFiles>)this.queryEntities("queryInfoAddedFilesList", condition);
	}
	
	/**
	 * 获取附件文件例表通过贷款id和过程类型
	 * @param loanId
	 * @param processTypes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LoanInfoAddedFiles> getInfoAddedFilesListById(Integer loanId,String processTypes){
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("loanId",loanId);
		if(StringUtil.isNotEmpty(processTypes)){
			String[] types = processTypes.split("\\,");
			condition.put("processTypes",types);
		}
		return (List<LoanInfoAddedFiles>)this.queryEntities("getInfoAddedFilesListById", condition);
	}

	/**
	 * 获取附件文件例表通过贷款id和文件类型
	 * @param loanId
	 * @param fileType
	 * @return
	 */
	public List<LoanInfoAddedFiles> getInfoAddedFilesListByType(Integer loanId,String fileType){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("loanId", loanId);
		condition.put("fileType", fileType);
		return (List<LoanInfoAddedFiles>)this.queryEntities("getInfoAddedFilesListByType", condition);
	}

	/**
	 * 分页查询贷款资料附件文件表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<LoanInfoAddedFiles> queryInfoAddedFilesList(Map<String,Object> condition,IPageSize page){
		return (IPageList<LoanInfoAddedFiles>)this.queryEntities("queryInfoAddedFilesList", page, condition);
	}

	/**
	 * 查询贷款资料劣币哦啊
	 * @return
	 */
    public List<LoanInfoAddedFilesExtend> queryLoanInfoFileByLoanId(Integer loanId) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("loanId", loanId);
		condition.put("isDel",0);
		return (List<LoanInfoAddedFilesExtend>) this.queryEntities("queryLoanInfoPage", condition);
	}

	/**
	 * 是否存在,得到存在的ID
	 * @param infoAddedFiles
	 * @return
	 */
	public Integer getAddedFileIdByRecord(LoanInfoAddedFiles infoAddedFiles){
		Integer id = this.queryFirstInt("getAddedFileIdByRecord", infoAddedFiles);
		if(id!=null){
			return id;
		}
		return -1;
	}

	/**
	 * 查询贷款资料(分类)
	 * @return
	 */
	public List<LoanInfoAddedFilesExtend> queryLoanInfoFile(Map<String,Object> condition) {
		return (List<LoanInfoAddedFilesExtend>) this.queryEntities("queryLoanInfoPage", condition);
	}

	/**
	 *根据id查询贷款资料列表
	 * @param id
	 * @return
	 */
	public LoanInfoAddedFilesExtend getInfoAddedFilesExtendById(Integer id){
		return (LoanInfoAddedFilesExtend) this.queryEntity("getInfoAddedFilesExtendById", id);
	}

	/**
	 * 查询贷款资料资料统计（excel导出）
	 */
	public List<LoanInfoAddedFilesExtend> getAddFileMount(Map<String,Object> condition){
		return (List<LoanInfoAddedFilesExtend>) this.queryEntities("getAddFileMount", condition);
	}

	/**
	 * 查询分类类型
	 */
	public List<LoanInfoAddedFilesExtend> queryOwnerNameByLoanId(Integer loanId) {
		return (List<LoanInfoAddedFilesExtend>) this.queryEntities("queryOwnerNameByLoanId",loanId);
	}


	/**
	 * 查询子类
	 * @param ownerId
	 * @return
	 */
	public List<LoanInfoAddedFilesExtend> queryClassNameByOwnerId(Integer ownerId,String loanId){
		return (List<LoanInfoAddedFilesExtend>) this.queryEntities("queryClassNameByOwnerId",ownerId,loanId);
	}
}
