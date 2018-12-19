package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.product.ProdProductFile;

/**
 * 产品附件数据访问接口
 */
public interface IProductFileDao {

	/**
	 * 新增产品附件
	 * @param productFile 实体对像
	 */
	void insertProductFile(ProdProductFile productFile);

	/**
	 *修改产品附件
	 * @param productFile 实体对像
	 */
	void updateProductFile(ProdProductFile productFile);

	/**
	 * 通过主键删除产品附件
	 * @param ppfId 主键Id
	 */
	void deleteProductFileById(Integer ppfId);

	/**
	 * 通过主键得到产品附件
	 * @param ppfId 主键Id
	 */
	ProdProductFile getProductFileById(Integer ppfId);

	/**
	 * 查询产品附件
	 * @param condition 查询条件
	 * @return
	 */
	List<ProdProductFile> queryProductFileList(Map<String,Object> condition);

	/**
	 * 分页查询产品附件
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<ProdProductFile> queryProductFileList(Map<String,Object> condition,IPageSize page);

}
