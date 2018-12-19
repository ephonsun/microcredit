package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import net.sf.json.JSONObject;
import banger.domain.system.SysDataDict;

/**
 * 系统数据字典表业务访问接口
 */
public interface IDataDictService {

	/**
	 * 新增系统数据字典表
	 * @param dataDict 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertDataDict(SysDataDict dataDict,Integer loginUserId);

	/**
	 *修改系统数据字典表
	 * @param dataDict 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateDataDict(SysDataDict dataDict,Integer loginUserId);
	
	JSONObject checkDataCnName(SysDataDict dataDict);
	
	JSONObject checkDataEnName(SysDataDict dataDict);
	
	String getDataEnName(String dictCnName,String dictEnName,int s);
	
	SysDataDict getDataDictByEnName(String dictEnName);

	/**
	 * 通过主键删除系统数据字典表
	 * @param DATA_DICT_ID 主键Id
	 */
	void deleteDataDictById(SysDataDict dataDict,Integer loginUserId);

	/**
	 * 通过主键得到系统数据字典表
	 * @param DATA_DICT_ID 主键Id
	 */
	SysDataDict getDataDictById(Integer dataDictId);

	/**
	 * 查询系统数据字典表
	 * @param condition 查询条件
	 * @return
	 */
	List<SysDataDict> queryDataDictList(Map<String,Object> condition);
	
	String queryDataDictTree(Map<String,Object> condition);

	/**
	 * 分页查询系统数据字典表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<SysDataDict> queryDataDictList(Map<String,Object> condition,IPageSize page);

}
