package banger.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import banger.moduleIntf.IDataDictColProvider;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import banger.dao.intf.IDataDictColDao;
import banger.domain.system.SysDataDictCol;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.service.intf.IDataDictColService;
import net.sf.json.JSONObject;

/**
 * 数据字典字段表业务访问类
 */
@Service
public class DataDictColService implements IDataDictColService,IDataDictColProvider {

	private static final int DICT_NAME_MAX_LEN = 15;
	private static final int DICT_CODE_MAX_LEN = 20;

	@Resource
	private IDataDictColDao dataDictColDao;

	/**
	 * 新增数据字典字段表
	 * 
	 * @param dataDictCol
	 *            实体对像
	 * @param loginUserId
	 *            登入用户Id
	 */
	public void insertDataDictCol(SysDataDictCol dataDictCol, Integer loginUserId, Integer dataDictId) {
		Integer maxNo = dataDictColDao.getDataDictColMaxNo(dataDictId);
		if(maxNo == null)
			maxNo = 0;
		int no = maxNo + 1;
		dataDictCol.setDelFlag(0);
		dataDictCol.setOrderNo(no);
		// 只有一位 需要前面补一位
		String value = String.valueOf(no);
		if (no < 10)
			value = "0" + no;
		dataDictCol.setDictValue(value);

		dataDictCol.setCreateUser(loginUserId);
		dataDictCol.setCreateDate(DateUtil.getCurrentDate());
		dataDictCol.setUpdateUser(loginUserId);
		dataDictCol.setUpdateDate(DateUtil.getCurrentDate());
		this.dataDictColDao.insertDataDictCol(dataDictCol);
	}

	/**
	 * 修改数据字典字段表
	 * 
	 * @param dataDictCol
	 *            实体对像
	 * @param loginUserId
	 *            登入用户Id
	 */
	public void updateDataDictCol(SysDataDictCol dataDictCol, Integer loginUserId) {
		dataDictCol.setUpdateUser(loginUserId);
		dataDictCol.setUpdateDate(DateUtil.getCurrentDate());
		this.dataDictColDao.updateDataDictCol(dataDictCol);
	}

	/**
	 * 通过主键删除数据字典字段表
	 * 
	 * @param dataDictCol
	 *            主键Id
	 */
	public void deleteDataDictColById(SysDataDictCol dataDictCol,Integer loginUserId) {
		dataDictCol.setUpdateUser(loginUserId);
		dataDictCol.setUpdateDate(DateUtil.getCurrentDate());
		this.dataDictColDao.deleteDataDictColById(dataDictCol);
	}

	/**
	 * 通过主键得到数据字典字段表
	 * 
	 * @param dictColId
	 *            主键Id
	 */
	public SysDataDictCol getDataDictColById(Integer dictColId) {
		return this.dataDictColDao.getDataDictColById(dictColId);
	}

	/**
	 * 查询数据字典字段表
	 * 
	 * @param condition
	 *            查询条件
	 * @return
	 */
	public List<SysDataDictCol> queryDataDictColList(Map<String, Object> condition) {
		return this.dataDictColDao.queryDataDictColList(condition);
	}

	/**
	 * 分页查询数据字典字段表
	 * 
	 * @param condition
	 *            查询条件
	 * @param page
	 *            分页对像
	 * @return
	 */
	public IPageList<SysDataDictCol> queryDataDictColList(Map<String, Object> condition, IPageSize page) {
		return this.dataDictColDao.queryDataDictColList(condition, page);
	}

	/**
	 * 判断中文名称是否合法
	 * 
	 * @param dictName
	 * @return
	 */
	public JSONObject checkDictName(String dictName, Integer dataDictId, String dictColId) {
		JSONObject resJson = new JSONObject();
		boolean success = false;
		String cause = "";
		if (dictName != null) {
			int len = dictName.length();
			if (len <= DICT_NAME_MAX_LEN) {
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("delFlag", 0);
				condition.put("dictName", dictName);
				condition.put("dataDictId", dataDictId);
				if (StringUtils.isNotBlank(dictColId))
					condition.put("dictColId", dictColId);
				Integer count = this.dataDictColDao.queryDataDictColCount(condition);
				if (count.intValue() > 0)
					cause = "已存在相同字典值，请重新输入";
				else
					success = true;
			} else {
				cause = "字典值长度不能大于" + DICT_NAME_MAX_LEN + "位";
			}
		} else {
			cause = "字典值名称必须填写";
		}
		resJson.put("success", success);
		resJson.put("cause", cause);
		return resJson;
	}

	/**
	 * 判断中文名称是否合法
	 *
	 * @param dictCode
	 * @return
	 */
	public JSONObject checkDictCode(String dictCode, Integer dataDictId, String dictColId) {
		JSONObject resJson = new JSONObject();
		boolean success = false;
		String cause = "";
		if (dictCode != null && !"".equals(dictCode)) {
			int len = dictCode.length();
			if (len <= DICT_CODE_MAX_LEN) {
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("delFlag", 0);
				condition.put("dictCode", dictCode);
				condition.put("dataDictId", dataDictId);
				if (StringUtils.isNotBlank(dictColId))
					condition.put("dictColId", dictColId);
				Integer count = this.dataDictColDao.queryDataDictColCount(condition);
				if (count.intValue() > 0)
					cause = "已存在相同字典编码，请重新输入";
				else
					success = true;
			} else {
				cause = "字典编码长度不能大于" + DICT_CODE_MAX_LEN + "位";
			}
		} else {
			success = true;
			cause = "字典编码名称可以为空";
		}
		resJson.put("success", success);
		resJson.put("cause", cause);
		return resJson;
	}

	@Override
	public void moveDictOrderNo(String type, Integer dictColId, Integer dataDictId) {
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("dataDictId", dataDictId);
		condition.put("delFlag", 0);
		List<SysDataDictCol> list = dataDictColDao.queryDataDictColList(condition);
		SysDataDictCol moveCol = null;
		SysDataDictCol changeCol = null;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getDictColId().equals(dictColId)) {
				moveCol = list.get(i);
				if ("moveDown".equals(type)) {
					int last = i + 1;
					if (last < list.size()) {
						changeCol = list.get(last);
					}
				} else if ("moveUp".equals(type)) {
					int pre = i - 1;
					if (i > 0) {
						changeCol = list.get(pre);
					}
				}
			}
		}
		if (changeCol != null && moveCol != null) {
			int orderNo = moveCol.getOrderNo();
			moveCol.setOrderNo(changeCol.getOrderNo());
			changeCol.setOrderNo(orderNo);
			this.dataDictColDao.updateDataDictColNoById(changeCol);
			this.dataDictColDao.updateDataDictColNoById(moveCol);
		}
	}

	/**
	 * 查询行业分类
	 * @return
	 */
    public List<SysDataDictCol> queryIndustryGuidelines() {
		return dataDictColDao.queryIndustryGuidelines();
    }

	@Override
	public List<SysDataDictCol> getSysDataDictListByDictName(String name) {
    	Map<String,Object> condition = new HashMap<String, Object>();
    	condition.put("dataDictName",name);
		return dataDictColDao.queryDataDictColList(condition);
	}
}
