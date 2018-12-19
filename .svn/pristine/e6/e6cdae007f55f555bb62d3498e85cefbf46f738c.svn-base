package banger.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import banger.dao.intf.IDataDictDao;
import banger.domain.system.SysDataDict;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.framework.util.PYUtil;
import banger.moduleIntf.IDataDictProvider;
import banger.service.intf.IDataDictService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 系统数据字典表业务访问类
 */
@Service
public class DataDictService implements IDataDictService, IDataDictProvider {

	@Resource
	private IDataDictDao dataDictDao;

	/**
	 * 新增系统数据字典表
	 * 
	 * @param dataDict
	 *            实体对像
	 * @param loginUserId
	 *            登入用户Id
	 */
	public void insertDataDict(SysDataDict dataDict, Integer loginUserId) {
		dataDict.setCreateUser(loginUserId);
		dataDict.setCreateDate(DateUtil.getCurrentDate());
		dataDict.setUpdateUser(loginUserId);
		dataDict.setUpdateDate(DateUtil.getCurrentDate());
		this.dataDictDao.insertDataDict(dataDict);
	}

	/**
	 * 修改系统数据字典表
	 * 
	 * @param dataDict
	 *            实体对像
	 * @param loginUserId
	 *            登入用户Id
	 */
	public void updateDataDict(SysDataDict dataDict, Integer loginUserId) {
		dataDict.setUpdateUser(loginUserId);
		dataDict.setUpdateDate(DateUtil.getCurrentDate());
		this.dataDictDao.updateDataDict(dataDict);
	}

	/**
	 * 判断中文名称是否合法
	 * 
	 * @param dictCnName
	 * @return
	 */
	public JSONObject checkDataCnName(SysDataDict dataDict) {
		String dictCnName = dataDict.getDictCnName();
		JSONObject resJson = new JSONObject();
		boolean success = false;
		String cause = "";
		if (dictCnName != null) {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("delFlag", 0);
			condition.put("dictCnName", dictCnName);
			condition.put("dataDictId", dataDict.getDataDictId());
			Integer count = this.dataDictDao.queryDataDictCount(condition);
			if (count.intValue() > 0)
				cause = "已存在相同名称的数据源，请重新输入";
			else
				success = true;
		} else {
			cause = "数据源名称必须填写";
		}
		resJson.put("success", success);
		resJson.put("cause", cause);
		return resJson;
	}

	/**
	 * 判断英文编码是否合法 (去掉已经删除的)
	 * 
	 * @param dictCnName
	 * @return
	 */
	public JSONObject checkDataEnName(SysDataDict dataDict) {
		String dictEnName = dataDict.getDictEnName();
		JSONObject resJson = new JSONObject();
		boolean success = false;
		String cause = "";
		if (dictEnName != null) {
			if (!matcher("^\\w+$", dictEnName)) {
				cause = "数据源编码必须由数字、字母或下划线组成";
			} else {
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("delFlag", 0);
				condition.put("dictEnName", dictEnName);
				condition.put("dataDictId", dataDict.getDataDictId());
				Integer count = this.dataDictDao.queryDataDictCount(condition);
				if (count.intValue() > 0)
					cause = "已存在相同编码的数据源，请重新输入";
				else
					success = true;
			}
		} else {
			cause = "数据源编码必须填写";
		}
		resJson.put("success", success);
		resJson.put("cause", cause);
		return resJson;
	}

	public String getDataEnName(String dictCnName, String dictEnName, int s) {
		if (dictEnName == null) {
			dictEnName = PYUtil.getPingYing(dictCnName);
		}
		String dictEnName_S = dictEnName + "_" + s;
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("delFlag", 0);
		condition.put("dictEnName", dictEnName_S);
		Integer count = this.dataDictDao.queryDataDictCount(condition);
		if (count.intValue() > 0) {
			s++;
			return getDataEnName(dictCnName, dictEnName, s);
		} else {
			return dictEnName_S;
		}
	}

	private boolean matcher(String rule, String data) {
		Pattern pattern = Pattern.compile(rule);
		Matcher matcher = pattern.matcher(data);
		return matcher.matches();
	}

	/**
	 * 通过主键删除系统数据字典表
	 * 
	 * @param DATA_DICT_ID
	 *            主键Id
	 */
	public void deleteDataDictById(SysDataDict dataDict, Integer loginUserId) {
		dataDict.setUpdateUser(loginUserId);
		dataDict.setUpdateDate(DateUtil.getCurrentDate());
		this.dataDictDao.deleteDataDictById(dataDict);
	}

	/**
	 * 通过主键得到系统数据字典表
	 * 
	 * @param DATA_DICT_ID
	 *            主键Id
	 */
	public SysDataDict getDataDictById(Integer dataDictId) {
		return this.dataDictDao.getDataDictById(dataDictId);
	}

	/**
	 * 查询系统数据字典表
	 * 
	 * @param condition
	 *            查询条件
	 * @return
	 */
	public List<SysDataDict> queryDataDictList(Map<String, Object> condition) {
		return this.dataDictDao.queryDataDictList(condition);
	}

	/**
	 * 分页查询系统数据字典表
	 * 
	 * @param condition
	 *            查询条件
	 * @param page
	 *            分页对像
	 * @return
	 */
	public IPageList<SysDataDict> queryDataDictList(Map<String, Object> condition, IPageSize page) {
		return this.dataDictDao.queryDataDictList(condition, page);
	}

	@Override
	public String queryDataDictTree(Map<String, Object> condition) {
		List<SysDataDict> dataDicts = this.dataDictDao.queryDataDictList(condition);
		if (dataDicts != null && dataDicts.size() > 0) {
			JSONArray ja = new JSONArray();
			JSONObject tempObj = new JSONObject();
			tempObj.put("pId", "0");
			tempObj.put("icon", "../core/imgs/icon/agency.gif");
			for (SysDataDict dataDict : dataDicts) {
				tempObj.put("id", dataDict.getDataDictId());
				tempObj.put("name", dataDict.getDictCnName());
				ja.add(tempObj);
			}
			return ja.toString();
		}
		return null;
	}

	@Override
	public boolean isExistDictName(String name) {
		Integer count = this.dataDictDao.getDataDictCountByName(name);
		if (count > 0)
			return true;
		return false;
	}

    @Override
    public List<SysDataDict> getSysDataDictListByDictName(String name) {
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("dictEnName",name);
        return this.dataDictDao.queryDataDictList(condition);
    }

    @Override
	public SysDataDict getDataDictByEnName(String dictEnName) {
		return this.dataDictDao.getDataDictByEnName(dictEnName);
	}

}
