package banger.moduleIntf;

import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanInfoAddedClass;
import banger.domain.loan.LoanInfoAddedFiles;
import banger.domain.loan.LoanInfoAddedOwner;

/**
 * 贷款附件服务类
 * @author zhusw
 *
 */
public interface ILoanAddedProvider {
	
	/**
	 * 得到该文件类型的保存路径
	 * @param fileType
	 * @return
	 */
	String getSavePath(String fileType);
	
	/**
	 * 得到图片缩略图路径
	 * @return
	 */
	String getImageThumbPath();
	
	/**
	 * 保存附件信息
	 */
	void saveLoanAddedFileInfo(LoanInfoAddedFiles info);

	/**
	 * 修改文件分类信息
	 */
	void saveLoanAddedFileCatalog(List<LoanInfoAddedFiles> files);
	
	/**
	 * 通过附件Id得到附件信息
	 * @param id
	 * @return
	 */
	LoanInfoAddedFiles getLoanAddedFileInfoById(Integer id);


	/**
	 * 通过附件fileId得到附件信息
	 * @param fileId
	 * @return
	 */
	LoanInfoAddedFiles getLoanAddedFileInfoByFileId(String fileId);
	
	/**
	 * 保存附件信息
	 */
	void saveLoanAddedFileInfos(List<LoanInfoAddedFiles> list);
	
	/**
	 *获取附件小类
	 * @return
	 */
	List<LoanInfoAddedClass> getAllInfoAddedClassList();
	
	/**
	 * 获取附件大类
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanInfoAddedOwner> getAllInfoAddedOwnerList();
	
	/**
	 * 查询贷款资料附件分类表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanInfoAddedClass> queryInfoAddedClassListByOwnerIds(String ownerIds);
	
	/**
	 * 查询贷款资料附件对像分类表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanInfoAddedOwner> queryInfoAddedOwnerListByOwnerIds(String ownerIds);
	
	/**
	 * 获取附件文件例表通过贷款id和过程类型
	 * @param loanId
	 * @param processTypes
	 * @return
	 */
	List<LoanInfoAddedFiles> getInfoAddedFilesListById(Integer loanId,String processTypes);

	/**
	 * 获取附件文件例表通过贷款id和文件类型
	 * @param loanId
	 * @param fileType
	 * @return
	 */
	List<LoanInfoAddedFiles> getInfoAddedFilesListByType(Integer loanId,String fileType);
	
}
