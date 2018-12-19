package banger.service.intf;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.product.ProdProductFile;

/**
 * 产品附件业务访问接口
 */
public interface IProductFileService {

	/**
	 * 新增产品附件
	 * @param productFile 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertProductFile(ProdProductFile productFile,Integer loginUserId);

	/**
	 *修改产品附件
	 * @param productFile 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateProductFile(ProdProductFile productFile,Integer loginUserId);
	
	void updateProductFile(Integer ppfIsdel,Integer ppfProductId,String[] fileId,String[]  fileSize,Integer loginUserId);
	
	void deleteProductFileById(String[] deleteFileIds);

	/**
	 * 通过主键删除产品附件
	 * @param PPF_ID 主键Id
	 */
	void deleteProductFileById(Integer ppfId);

	/**
	 * 通过主键得到产品附件
	 * @param PPF_ID 主键Id
	 */
	ProdProductFile getProductFileById(Integer ppfId);

	/**
	 * 查询产品附件
	 * @param condition 查询条件
	 * @return
	 */
	List<ProdProductFile> queryProductFileList(Map<String,Object> condition);
	
	List<ProdProductFile> queryProductFileList(Integer productId,Integer fileType);

	/**
	 * 分页查询产品附件
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<ProdProductFile> queryProductFileList(Map<String,Object> condition,IPageSize page);
	
	ProdProductFile uploadFile(String fileName, String filePath, InputStream is,Integer fileType);
	
	void downloadFile(File file,OutputStream os);

}
