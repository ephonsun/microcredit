package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import net.sf.json.JSONObject;
import banger.domain.system.SysDataDictCol;

/**
 * 数据字典字段表业务访问接口
 */
public interface IDataDictColService {

	/**
	 * 新增数据字典字段表
	 * @param dataDictCol 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertDataDictCol(SysDataDictCol dataDictCol,Integer loginUserId,Integer dataDictId);

	/**
	 *修改数据字典字段表
	 * @param dataDictCol 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateDataDictCol(SysDataDictCol dataDictCol,Integer loginUserId);

	/**
	 * 通过主键删除数据字典字段表
	 * @param DICT_COL_ID 主键Id
	 */
	void deleteDataDictColById(SysDataDictCol dataDictCol,Integer loginUserId);

	/**
	 * 通过主键得到数据字典字段表
	 * @param DICT_COL_ID 主键Id
	 */
	SysDataDictCol getDataDictColById(Integer dictColId);

	/**
	 * 查询数据字典字段表
	 * @param condition 查询条件
	 * @return
	 */
	List<SysDataDictCol> queryDataDictColList(Map<String,Object> condition);

	/**
	 * 分页查询数据字典字段表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<SysDataDictCol> queryDataDictColList(Map<String,Object> condition,IPageSize page);
	
	JSONObject checkDictName(String dictName,Integer dataDictId,String dictColId);
	
	void moveDictOrderNo(String type, Integer dictColId, Integer dataDictId);

	/**
	 * 查询行业分类
	 * @return
	 */
    List<SysDataDictCol> queryIndustryGuidelines();

	JSONObject checkDictCode(String dictCode, Integer dataDictId, String dictColId);
}
