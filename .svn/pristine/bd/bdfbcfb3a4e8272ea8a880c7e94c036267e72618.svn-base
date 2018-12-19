package banger.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.hundsun.common.lang.StringUtil;

import banger.dao.intf.IInfoAddedClassDao;
import banger.dao.intf.IInfoAddedFilesDao;
import banger.dao.intf.IInfoAddedOwnerDao;
import banger.domain.enumerate.LoanAddedFileType;
import banger.domain.loan.LoanInfoAddedClass;
import banger.domain.loan.LoanInfoAddedFiles;
import banger.domain.loan.LoanInfoAddedOwner;
import banger.framework.util.FileUtil;
import banger.service.intf.ILoanAddedService;

/**
 * 贷款附件服务类
 * @author zhusw
 *
 */
@Repository
public class LoanAddedService implements ILoanAddedService {
	
	@Value("${file_root_path}")  
    private String fileRootPath;
	
	@Autowired
	private IInfoAddedFilesDao infoAddedFilesDao;
	
	@Autowired
	private IInfoAddedClassDao infoAddedClassDao;
	
	@Autowired
	private IInfoAddedOwnerDao infoAddedOwnerDao;

	/**
	 * 得到该文件类型的保存路径
	 * @param fileType
	 * @return
	 */
	public String getSavePath(String fileType){
		String path = FileUtil.contact(fileRootPath, LoanAddedFileType.valueOfType(fileType).savePath) + "/";
		return path;
	}
	
	/**
	 * 得到图片缩略图路径
	 * @return
	 */
	public String getImageThumbPath(){
		String path = FileUtil.contact(fileRootPath, LoanAddedFileType.IMAGE.thumbPath) + "/";
		return path;
	}
	
	/**
	 * 保存附件信息
	 */
	public void saveLoanAddedFileInfo(LoanInfoAddedFiles info){
		infoAddedFilesDao.insertInfoAddedFiles(info);
	}
	
	/**
	 * 保存附件信息
	 */
	public void saveLoanAddedFileInfos(List<LoanInfoAddedFiles> list){
		for(LoanInfoAddedFiles loanInfoAddedFiles : list){
			this.saveLoanAddedFileInfo(loanInfoAddedFiles);
		}
	}
	
	/**
	 * 通过附件Id得到附件信息
	 * @param id
	 * @return
	 */
	public LoanInfoAddedFiles getLoanAddedFileInfoById(Integer id){
		return infoAddedFilesDao.getInfoAddedFilesById(id);
	}

	/**
	 * 通过附件fileId得到附件信息
	 * @param fileId
	 * @return
	 */
	public LoanInfoAddedFiles getLoanAddedFileInfoByFileId(String fileId){
		return infoAddedFilesDao.getLoanAddedFileInfoByFileId(fileId);
	}
	
	/**
	 * 获取附件文件例表通过贷款id和过程类型
	 * @param loanId
	 * @param processTypes
	 * @return
	 */
	public List<LoanInfoAddedFiles> getInfoAddedFilesListById(Integer loanId,String processTypes){
		return this.infoAddedFilesDao.getInfoAddedFilesListById(loanId,processTypes);
	}

	/**
	 * 获取附件文件例表通过贷款id和文件类型
	 * @param loanId
	 * @param fileType
	 * @return
	 */
	public List<LoanInfoAddedFiles> getInfoAddedFilesListByType(Integer loanId,String fileType){
		return this.infoAddedFilesDao.getInfoAddedFilesListByType(loanId,fileType);
	}

	/**
	 * 修改文件分类信息
	 */
	public void saveLoanAddedFileCatalog(List<LoanInfoAddedFiles> files){
		if(files!=null && files.size()>0) {
			for (LoanInfoAddedFiles file: files){
				this.infoAddedFilesDao.updateInfoAddedFiles(file);
			}
		}
	}
	
	/**
	 *获取附件小类
	 * @return
	 */
	public List<LoanInfoAddedClass> getAllInfoAddedClassList(){
		return infoAddedClassDao.getAllInfoAddedClassList();
	}
	
	/**
	 * 获取附件大类
	 * @return
	 */
	public List<LoanInfoAddedOwner> getAllInfoAddedOwnerList(){
		return infoAddedOwnerDao.getAllInfoAddedOwnerList();
	}

	/**
	 * 查询贷款资料附件分类表
	 * @param ownerIds
	 * @return
	 */
	public List<LoanInfoAddedClass> queryInfoAddedClassListByOwnerIds(String ownerIds){
		return infoAddedClassDao.queryInfoAddedClassListByOwnerIds(ownerIds);
	}

	/**
	 * 查询贷款资料附件对像分类表
	 * @param ownerIds
	 * @return
	 */
	public List<LoanInfoAddedOwner> queryInfoAddedOwnerListByOwnerIds(String ownerIds){
		return infoAddedOwnerDao.queryInfoAddedOwnerListByOwnerIds(ownerIds);
	}


	
}
