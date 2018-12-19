package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IProductFileDao;
import banger.domain.product.ProdProductFile;

/**
 * 产品附件数据访问类
 */
@Repository
public class ProductFileDao extends PageSizeDao<ProdProductFile> implements IProductFileDao {

	/**
	 * 新增产品附件
	 * @param productFile 实体对像
	 */
	public void insertProductFile(ProdProductFile productFile){
		productFile.setPpfId(this.newId().intValue());
		this.execute("insertProductFile",productFile);
	}

	/**
	 *修改产品附件
	 * @param productFile 实体对像
	 */
	public void updateProductFile(ProdProductFile productFile){
		this.execute("updateProductFile",productFile);
	}

	/**
	 * 通过主键删除产品附件
	 * @param ppfId 主键Id
	 */
	public void deleteProductFileById(Integer ppfId){
		this.execute("deleteProductFileById",ppfId);
	}

	/**
	 * 通过主键得到产品附件
	 * @param ppfId 主键Id
	 */
	public ProdProductFile getProductFileById(Integer ppfId){
		return (ProdProductFile)this.queryEntity("getProductFileById",ppfId);
	}

	/**
	 * 查询产品附件
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProdProductFile> queryProductFileList(Map<String,Object> condition){
		return (List<ProdProductFile>)this.queryEntities("queryProductFileList", condition);
	}

	/**
	 * 分页查询产品附件
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<ProdProductFile> queryProductFileList(Map<String,Object> condition,IPageSize page){
		return (IPageList<ProdProductFile>)this.queryEntities("queryProductFileList", page, condition);
	}

}
