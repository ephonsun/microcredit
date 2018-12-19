package banger.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import banger.dao.intf.IProductFileDao;
import banger.domain.product.ProdProductFile;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.framework.util.FileUtil;
import banger.framework.util.ImageCompressUtil;
import banger.service.intf.IProductFileService;

/**
 * 产品附件业务访问类
 */
@Service
public class ProductFileService implements IProductFileService {
	
	private final Log log = LogFactory.getLog(FileUtil.class);
	
	@Autowired
	private IProductFileDao productFileDao;

	/**
	 * 新增产品附件
	 * @param productFile 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertProductFile(ProdProductFile productFile,Integer loginUserId){
		productFile.setPpfCreateUser(loginUserId);
		productFile.setPpfCreateDate(DateUtil.getCurrentDate());
		productFile.setPpfUpdateUser(loginUserId);
		productFile.setPpfUpdateDate(DateUtil.getCurrentDate());
		this.productFileDao.insertProductFile(productFile);
	}

	/**
	 *修改产品附件
	 * @param productFile 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateProductFile(ProdProductFile productFile,Integer loginUserId){
		productFile.setPpfUpdateUser(loginUserId);
		productFile.setPpfUpdateDate(DateUtil.getCurrentDate());
		this.productFileDao.updateProductFile(productFile);
	}
	
	public void updateProductFile(Integer ppfIsdel,Integer ppfProductId,String[] fileId,String[]  fileSize,Integer loginUserId){
		if(fileId == null || fileId.length <=0)
			return;
		ProdProductFile productFile = new ProdProductFile();
		productFile.setPpfIsdel(0);
		productFile.setPpfProductId(ppfProductId);
		productFile.setPpfUpdateUser(loginUserId);
		productFile.setPpfUpdateDate(DateUtil.getCurrentDate());
		for (int i = 0; i < fileId.length; i++) {
			productFile.setPpfFileSize(Integer.valueOf(fileSize[i]));
			productFile.setPpfId(Integer.valueOf(fileId[i]));
			this.productFileDao.updateProductFile(productFile);
		}
	}
	
	public void deleteProductFileById(String[] deleteFileIds){
		if(deleteFileIds == null || deleteFileIds.length <=0)
			return;
		for (int i = 0; i < deleteFileIds.length; i++) {
			this.productFileDao.deleteProductFileById(Integer.valueOf(deleteFileIds[i]));
		}
	}

	/**
	 * 通过主键删除产品附件
	 * @param PPF_ID 主键Id
	 */
	public void deleteProductFileById(Integer ppfId){
		this.productFileDao.deleteProductFileById(ppfId);
	}

	/**
	 * 通过主键得到产品附件
	 * @param PPF_ID 主键Id
	 */
	public ProdProductFile getProductFileById(Integer ppfId){
		return this.productFileDao.getProductFileById(ppfId);
	}

	/**
	 * 查询产品附件
	 * @param condition 查询条件
	 * @return
	 */
	public List<ProdProductFile> queryProductFileList(Map<String,Object> condition){
		return this.productFileDao.queryProductFileList(condition);
	}
	
	public List<ProdProductFile> queryProductFileList(Integer productId,Integer fileType){
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("ppfProductId", productId);
		condition.put("ppfFileType", fileType);
		condition.put("ppfIsdel", 0);
		return this.productFileDao.queryProductFileList(condition);
	}

	/**
	 * 分页查询产品附件
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<ProdProductFile> queryProductFileList(Map<String,Object> condition,IPageSize page){
		return this.productFileDao.queryProductFileList(condition,page);
	}
	
	public ProdProductFile uploadFile(String fileName, String filePath, InputStream is,Integer fileType) {
		try {
			Date date = new Date();
			filePath = FileUtil.contact(filePath, new SimpleDateFormat("yyyyMMdd").format(date));
			String compressPath = null;
			if(fileType == 0){
				compressPath = FileUtil.contact(filePath, "compress");
				File compressFile = new File(compressPath);
				if(!compressFile.exists())
					compressFile.mkdirs();
				filePath = FileUtil.contact(filePath, "source");
			}
			File f = new File(filePath);
			if (!f.exists()) { //目录不存在，则创建相应的目录
			    f.mkdirs();
			}
			String newFileName = new SimpleDateFormat("HHmmssSSS").format(date) + fileName;
			String saveFilePath = FileUtil.contact(filePath, newFileName);
			File obj = new File(saveFilePath);
			//将文件上传到服务器
			FileUtils.copyToFile(is,obj);
			ProdProductFile productFile= new ProdProductFile();
			productFile.setPpfFilePath(saveFilePath);
			productFile.setPpfFileName(newFileName);
			productFile.setPpfFileNameOld(fileName);
			if(fileType == 0){
				try {
					compressPath = FileUtil.contact(compressPath, newFileName);
					ImageCompressUtil.saveMinPhoto(saveFilePath, compressPath, 0.8d);
					productFile.setPpfCutFileName(newFileName);
					productFile.setPpfCutFilePath(compressPath);
				} catch (Exception e) {
					log.error("compress image error",e);
				}
			}
			productFile.setPpfFileType(fileType);
			productFile.setPpfIsdel(1);
	        return productFile;
		} catch (Exception e) {
			log.error("uploadFile error",e);
		}
		return null;
    }
	
	public void downloadFile(File file,OutputStream os){
		if (file.exists()) {
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			try {
				byte[] buffer = new byte[2048];
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				int i ;
				while ((i = bis.read(buffer)) > 0) {
					os.write(buffer, 0, i);
				}
				os.flush();
			} catch (Exception e) {
				log.error("downloadFile error",e);
			} finally {
				if(os != null){
					try {
						os.close();
					} catch (IOException e) {}
				}
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {}
				}
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {}
				}
			}
		}
	}

}
