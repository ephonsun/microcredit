package banger.moduleIntf;

import banger.domain.loan.LoanInfoAddedFiles;

/**
 * Created by panl on 2017/11/29
 */
public interface IInfoAddedFileProvider {

	/**
	 * 新增贷款资料附件文件表
	 * @param infoAddedFiles 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertInfoAddedFiles(LoanInfoAddedFiles infoAddedFiles, Integer loginUserId);

	/**
	 * 通过主键删除贷款资料附件文件表
	 * @param id 主键Id
	 */
	void deleteInfoAddedFilesById(Integer id);

	/**
	 *修改贷款资料附件文件表
	 * @param infoAddedFiles 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateInfoAddedFiles(LoanInfoAddedFiles infoAddedFiles, Integer loginUserId);

}
