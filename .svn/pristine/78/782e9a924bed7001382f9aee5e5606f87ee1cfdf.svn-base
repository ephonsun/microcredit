package banger.service.intf;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import banger.domain.loan.LoanInfoAddedFilesExtend;
import banger.domain.loan.LoanUploadRecord;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.loan.LoanInfoAddedFiles;

/**
 * 贷款资料附件文件表业务访问接口
 */
public interface IInfoAddedFilesService {

	/**
	 * 新增贷款资料附件文件表
	 * @param infoAddedFiles 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertInfoAddedFiles(LoanInfoAddedFiles infoAddedFiles, Integer loginUserId);

	/**
	 *修改贷款资料附件文件表
	 * @param infoAddedFiles 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateInfoAddedFiles(LoanInfoAddedFiles infoAddedFiles, Integer loginUserId);

	/**
	 * 通过主键删除贷款资料附件文件表
	 * @param id 主键Id
	 */
	void deleteInfoAddedFilesById(Integer id);

	/**
	 * 通过主键得到贷款资料附件文件表
	 * @param id 主键Id
	 */
	LoanInfoAddedFiles getInfoAddedFilesById(Integer id);

	/**
	 * 查询贷款资料附件文件表
	 * @param condition 查询条件
	 * @return
	 */
	List<LoanInfoAddedFiles> queryInfoAddedFilesList(Map<String, Object> condition);

	/**
	 * 分页查询贷款资料附件文件表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<LoanInfoAddedFiles> queryInfoAddedFilesList(Map<String, Object> condition, IPageSize page);

	/**
	 * 查询贷款资料列表
	 */
    List<LoanInfoAddedFilesExtend> queryLoanInfoFileByLoanId(Integer loanId);

	/**
	 * 上传音视频文件信息
	 * @param uploadRecord
	 */
	void saveFileInfo(LoanUploadRecord uploadRecord);

	/**
	 * 上传
	 * @param fileName
	 * @param filePath
	 * @param inputStream
	 * @param annex
	 * @param loanId
	 * @param precType
	 * @param size
	 * @param loginUserId
	 */
    void uploadFile(String fileName, String filePath, InputStream inputStream, String annex, Integer loanId, String precType, Long size, Integer loginUserId);


	/**
	 * 查询贷款资料列表（多条件）
	 */
	List<LoanInfoAddedFilesExtend> queryLoanInfoFile(Map<String, Object> condition);

	/**
	 *根据id查询贷款资料列表
	 * @param id
	 * @return
	 */
	LoanInfoAddedFilesExtend getInfoAddedFilesExtendById(Integer id);

	/**
	 * 查询贷款资料资料统计（excel导出）
	 */
	List<LoanInfoAddedFilesExtend> getAddFileMount(Map<String,Object> condition);

	/*生成excel统计文件*/
	File exportFileMount(List<LoanInfoAddedFilesExtend> loanMountList,String fileRootPath) throws IOException;

	/**
	 * 查询分类类型
	 */
	 List<LoanInfoAddedFilesExtend> queryOwnerNameByLoanId(Integer loanId);

	 List<LoanInfoAddedFilesExtend> queryClassNameByOwnerId(Integer ownerId,String loanId);

}
