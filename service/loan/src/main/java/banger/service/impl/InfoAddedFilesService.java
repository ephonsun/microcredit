package banger.service.impl;

import banger.dao.intf.IInfoAddedFilesDao;
import banger.dao.intf.IProcessTypeDao;
import banger.domain.enumerate.LoanAddedFileType;
import banger.domain.loan.LoanInfoAddedFiles;
import banger.domain.loan.LoanInfoAddedFilesExtend;
import banger.domain.loan.LoanUploadRecord;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.framework.util.FileUtil;
import banger.framework.util.StringUtil;
import banger.moduleIntf.IInfoAddedFileProvider;
import banger.service.intf.IInfoAddedFilesService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 贷款资料附件文件表业务访问类
 */
@Repository
public class InfoAddedFilesService implements IInfoAddedFilesService ,IInfoAddedFileProvider {

	private final Log log = LogFactory.getLog(FileUtil.class);

	@Autowired
	private IInfoAddedFilesDao infoAddedFilesDao;
	@Autowired
	private IProcessTypeDao iProcessTypeDao;

	/**
	 * 新增贷款资料附件文件表
	 * @param infoAddedFiles 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertInfoAddedFiles(LoanInfoAddedFiles infoAddedFiles,Integer loginUserId){
		infoAddedFiles.setCreateUser(loginUserId);
		infoAddedFiles.setCreateDate(DateUtil.getCurrentDate());
		infoAddedFiles.setUpdateUser(loginUserId);
		infoAddedFiles.setUpdateDate(DateUtil.getCurrentDate());
		this.infoAddedFilesDao.insertInfoAddedFiles(infoAddedFiles);
	}

	/**
	 *修改贷款资料附件文件表
	 * @param infoAddedFiles 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateInfoAddedFiles(LoanInfoAddedFiles infoAddedFiles,Integer loginUserId){
		infoAddedFiles.setUpdateUser(loginUserId);
		infoAddedFiles.setUpdateDate(DateUtil.getCurrentDate());
		this.infoAddedFilesDao.updateInfoAddedFiles(infoAddedFiles);
	}

	/**
	 * 通过主键删除贷款资料附件文件表
	 * @param id 主键Id
	 */
	public void deleteInfoAddedFilesById(Integer id){
		this.infoAddedFilesDao.deleteInfoAddedFilesById(id);
	}

	/**
	 * 通过主键得到贷款资料附件文件表
	 * @param id 主键Id
	 */
	public LoanInfoAddedFiles getInfoAddedFilesById(Integer id){
		return this.infoAddedFilesDao.getInfoAddedFilesById(id);
	}

	/**
	 * 查询贷款资料附件文件表
	 * @param condition 查询条件
	 * @return
	 */
	public List<LoanInfoAddedFiles> queryInfoAddedFilesList(Map<String,Object> condition){
		return this.infoAddedFilesDao.queryInfoAddedFilesList(condition);
	}

	/**
	 * 分页查询贷款资料附件文件表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<LoanInfoAddedFiles> queryInfoAddedFilesList(Map<String,Object> condition,IPageSize page){
		return this.infoAddedFilesDao.queryInfoAddedFilesList(condition,page);
	}

	/**
	 *查询贷款资料列表
	 */
    public List<LoanInfoAddedFilesExtend> queryLoanInfoFileByLoanId(Integer loanId) {
    	return this.infoAddedFilesDao.queryLoanInfoFileByLoanId(loanId);
    }

	/**
	 * 上传音视频文件信息
	 * @param uploadRecord
	 */
	public void saveFileInfo(LoanUploadRecord uploadRecord){
		LoanInfoAddedFiles record = new LoanInfoAddedFiles();
		record.setFileName(uploadRecord.getFileName());
		record.setFilePath(uploadRecord.getFilePath());
		record.setFileType(uploadRecord.getFileType());
		record.setBeginTime(uploadRecord.getBeginTime());
		record.setEndTime(uploadRecord.getEndTime());
		record.setCallTime(uploadRecord.getTimeLen());
		record.setFileId(uploadRecord.getFileId());
		record.setLoanId(uploadRecord.getLoanId());
		record.setCreateUser(uploadRecord.getUserId());
		record.setUpdateUser(uploadRecord.getUserId());
		record.setCreateDate(new Date());
		record.setUpdateDate(new Date());
		Integer id = this.infoAddedFilesDao.getAddedFileIdByRecord(record);
		if(id!=null && id.intValue()>0){
			record.setId(record.getId());
			this.infoAddedFilesDao.insertInfoAddedFiles(record);
		}else{
			this.infoAddedFilesDao.updateInfoAddedFiles(record);
		}
	}

	/**
	 * 上传
	 * @param fileName
	 * @param filePath
	 * @param is
	 * @param other
	 * @param loanId
	 * @param precType
	 * @param size
	 * @param loginUserId
	 */
    public void uploadFile(String fileName, String filePath, InputStream is, String other, Integer loanId, String precType, Long size, Integer loginUserId) {
		try {
			Date date = new Date();

			File f = new File(filePath);
			if (!f.exists()) { //目录不存在，则创建相应的目录
				f.mkdirs();
			}
			String saveName = System.currentTimeMillis()+fileName;
			String saveFilePath = FileUtil.contact(filePath,saveName);
			File obj = new File(saveFilePath);
			//将文件上传到服务器
			FileUtils.copyToFile(is,obj);

			//插入表
			LoanInfoAddedFiles loanInfoAddedFiles = new LoanInfoAddedFiles();

			loanInfoAddedFiles.setLoanId(loanId);
			//获取贷款名
			loanInfoAddedFiles.setLoanProcessType(precType);
			loanInfoAddedFiles.setIsDel(0);
			//类型为附件
			loanInfoAddedFiles.setFileType(other);
			//后缀名
			String FileFix = fileName.substring(fileName.lastIndexOf("."));
			loanInfoAddedFiles.setFileFix(FileFix);
			loanInfoAddedFiles.setFileName(saveName);
			loanInfoAddedFiles.setFilePath(filePath);
			loanInfoAddedFiles.setFileSize(size.intValue());

			loanInfoAddedFiles.setFileSrcName(fileName);
			//图片
			//loanInfoAddedFiles.setFileThumbImageName(fileName);
			loanInfoAddedFiles.setCreateDate(date);
			loanInfoAddedFiles.setUpdateDate(date);
			loanInfoAddedFiles.setCreateUser(loginUserId);
			loanInfoAddedFiles.setUpdateUser(loginUserId);

			infoAddedFilesDao.insertInfoAddedFiles(loanInfoAddedFiles);
		} catch (Exception e) {
			log.error("uploadFile error",e);
		}
    }

	/**
	 *查询贷款资料列表(加条件)
	 */
	public List<LoanInfoAddedFilesExtend> queryLoanInfoFile(Map<String,Object> condition) {

		return infoAddedFilesDao.queryLoanInfoFile(condition);
	}


	/**
	 *根据id查询贷款资料列表
	 * @param id
	 * @return
	 */
	public LoanInfoAddedFilesExtend getInfoAddedFilesExtendById(Integer id){
		return infoAddedFilesDao.getInfoAddedFilesExtendById(id);
	}

	/**
	 * 查询贷款资料资料统计（excel导出）
	 */
	public List<LoanInfoAddedFilesExtend> getAddFileMount(Map<String,Object> condition){
		return infoAddedFilesDao.getAddFileMount(condition);
	}




	/**
	 * 查询分类类型
	 */
	public List<LoanInfoAddedFilesExtend> queryOwnerNameByLoanId(Integer loanId) {
		return infoAddedFilesDao.queryOwnerNameByLoanId(loanId);
	}

	public List<LoanInfoAddedFilesExtend> queryClassNameByOwnerId(Integer ownerId,String loanId) {
		return infoAddedFilesDao.queryClassNameByOwnerId(ownerId,loanId);
	}



	/*生成excel统计文件*/
	public File exportFileMount(List<LoanInfoAddedFilesExtend> loanMountList,String fileRootPath) throws IOException {
		List<List<String>> reportList = new ArrayList<List<String>>();
		for (int i = 0; i < loanMountList.size(); i++) {
			LoanInfoAddedFilesExtend loanFile=loanMountList.get(i);
			List<String> colunms = new ArrayList<String>();
			colunms.add(LoanAddedFileType.valueOfType(loanFile.getFileType()).typeName);
			if(StringUtil.isNotEmpty(loanFile.getOwnerName())){
				colunms.add(loanFile.getOwnerName());
			}else{
				colunms.add("");
			}
			if(StringUtil.isNotEmpty(loanFile.getClassName())){
				colunms.add(loanFile.getClassName());
			}else{
				colunms.add("");
			}
			colunms.add(loanFile.getFileSize().toString());

			reportList.add(colunms);
		}
		List<String> head = new ArrayList<String>();
		head.add("资料类型");
		head.add("资料分类");
		head.add("子类");
		head.add("文件数目");
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
		String fileName="exportReport"+sdf.format(new Date());
		String path = fileRootPath+"/exportReport";
		String filePath = FileUtil.contact(path,fileName);
		File csvFile = this.createCSVFile(head, reportList, path, fileName);
		return csvFile;
	}


	/**
	 * CSV文件生成方法
	 *
	 * @param head
	 * @param dataList
	 * @param outPutPath
	 * @param filename
	 * @return
	 */

	private File createCSVFile(List<String> head, List<List<String>> dataList, String outPutPath, String filename) throws IOException {
		File csvFile = null;
		BufferedWriter csvWtriter = null;
		try {
			csvFile = new File(outPutPath + File.separator + filename + ".csv");
			File parent = csvFile.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}
			csvFile.createNewFile();

			// GB2312使正确读取分隔符","
			csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
					csvFile), "GB2312"), 1024);
			// 写入文件头部
			writeRow(head, csvWtriter);

			// 写入文件内容
			for (List<String> row : dataList) {
				writeRow(row, csvWtriter);
			}
			csvWtriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				csvWtriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return csvFile;
	}

	/**
	 * 写一行数据方法
	 *
	 * @param row
	 * @param csvWriter
	 * @throws IOException
	 */
	private void writeRow(List<String> row, BufferedWriter csvWriter) throws IOException {
		// 写入文件头部
		for (String data : row) {
			StringBuffer sb = new StringBuffer();
			String rowStr = sb.append("\"").append(data).append("\",").toString();
			csvWriter.write(rowStr);
		}
		csvWriter.newLine();
	}
}
