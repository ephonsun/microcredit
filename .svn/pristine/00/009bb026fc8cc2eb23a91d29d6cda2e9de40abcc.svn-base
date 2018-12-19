package banger.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IColumnFormulaDao;
import banger.service.intf.IColumnFormulaService;
import banger.domain.config.AutoColumnFormula;

/**
 * 自定义字段合计项公式业务访问类
 */
@Repository
public class ColumnFormulaService implements IColumnFormulaService {

	@Autowired
	private IColumnFormulaDao columnFormulaDao;

	/**
	 * 新增自定义字段合计项公式
	 * @param columnFormula 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertColumnFormula(AutoColumnFormula columnFormula,Integer loginUserId){
		columnFormula.setCreateUser(loginUserId);
		columnFormula.setCreateDate(DateUtil.getCurrentDate());
		columnFormula.setUpdateUser(loginUserId);
		columnFormula.setUpdateDate(DateUtil.getCurrentDate());
		this.columnFormulaDao.insertColumnFormula(columnFormula);
	}

	/**
	 *修改自定义字段合计项公式
	 * @param columnFormula 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateColumnFormula(AutoColumnFormula columnFormula,Integer loginUserId){
		columnFormula.setUpdateUser(loginUserId);
		columnFormula.setUpdateDate(DateUtil.getCurrentDate());
		this.columnFormulaDao.updateColumnFormula(columnFormula);
	}

	/**
	 * 通过主键删除自定义字段合计项公式
	 * @param ID 主键Id
	 */
	public void deleteColumnFormulaById(Integer id){
		this.columnFormulaDao.deleteColumnFormulaById(id);
	}

	/**
	 * 通过主键得到自定义字段合计项公式
	 * @param ID 主键Id
	 */
	public AutoColumnFormula getColumnFormulaById(Integer id){
		return this.columnFormulaDao.getColumnFormulaById(id);
	}

	/**
	 * 查询自定义字段合计项公式
	 * @param condition 查询条件
	 * @return
	 */
	public List<AutoColumnFormula> queryColumnFormulaList(Map<String,Object> condition){
		return this.columnFormulaDao.queryColumnFormulaList(condition);
	}

	/**
	 * 分页查询自定义字段合计项公式
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<AutoColumnFormula> queryColumnFormulaList(Map<String,Object> condition,IPageSize page){
		return this.columnFormulaDao.queryColumnFormulaList(condition,page);
	}

	@Override
	public List<AutoColumnFormula> queryTableFormulaList(String tableName) {
		return this.columnFormulaDao.queryTableFormulaList(tableName);
	}

}
