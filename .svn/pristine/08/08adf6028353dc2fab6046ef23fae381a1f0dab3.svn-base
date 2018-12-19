package banger.service.impl;

import banger.dao.intf.IColumnFormulaParamsDao;
import banger.domain.config.AutoColumnFormulaParams;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.service.intf.IColumnFormulaParamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 自定义字段合计项公式参数业务访问类
 */
@Repository
public class ColumnFormulaParamsService implements IColumnFormulaParamsService {

	@Autowired
	private IColumnFormulaParamsDao columnFormulaParamsDao;

	/**
	 * 新增自定义字段合计项公式参数
	 * @param columnFormulaParams 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertColumnFormulaParams(AutoColumnFormulaParams columnFormulaParams,Integer loginUserId){
		columnFormulaParams.setCreateUser(loginUserId);
		columnFormulaParams.setCreateDate(DateUtil.getCurrentDate());
		columnFormulaParams.setUpdateUser(loginUserId);
		columnFormulaParams.setUpdateDate(DateUtil.getCurrentDate());
		this.columnFormulaParamsDao.insertColumnFormulaParams(columnFormulaParams);
	}

	/**
	 *修改自定义字段合计项公式参数
	 * @param columnFormulaParams 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateColumnFormulaParams(AutoColumnFormulaParams columnFormulaParams,Integer loginUserId){
		columnFormulaParams.setUpdateUser(loginUserId);
		columnFormulaParams.setUpdateDate(DateUtil.getCurrentDate());
		this.columnFormulaParamsDao.updateColumnFormulaParams(columnFormulaParams);
	}

	/**
	 * 通过主键删除自定义字段合计项公式参数
	 * @param ID 主键Id
	 */
	public void deleteColumnFormulaParamsById(Integer id){
		this.columnFormulaParamsDao.deleteColumnFormulaParamsById(id);
	}

	/**
	 * 通过主键得到自定义字段合计项公式参数
	 * @param ID 主键Id
	 */
	public AutoColumnFormulaParams getColumnFormulaParamsById(Integer id){
		return this.columnFormulaParamsDao.getColumnFormulaParamsById(id);
	}

	/**
	 * 查询自定义字段合计项公式参数
	 * @param condition 查询条件
	 * @return
	 */
	public List<AutoColumnFormulaParams> queryColumnFormulaParamsList(Map<String,Object> condition){
		return this.columnFormulaParamsDao.queryColumnFormulaParamsList(condition);
	}

	/**
	 * 分页查询自定义字段合计项公式参数
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<AutoColumnFormulaParams> queryColumnFormulaParamsList(Map<String,Object> condition,IPageSize page){
		return this.columnFormulaParamsDao.queryColumnFormulaParamsList(condition,page);
	}

}
