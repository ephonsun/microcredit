package banger.dao.impl;

import banger.dao.intf.ISpouseInfoDao;
import banger.domain.customer.CustSpouseInfo;
import banger.domain.customer.Customer;
import banger.framework.dao.PageSizeDao;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 数据访问类
 */
@Repository
public class SpouseInfoDao extends PageSizeDao<CustSpouseInfo> implements ISpouseInfoDao {

	/**
	 * 新增
	 * @param spouseInfo 实体对像
	 */
	public void insertSpouseInfo(Customer spouseInfo){
		this.execute("insertSpouseInfo",spouseInfo);
	}

	/**
	 *修改
	 * @param spouseInfo 实体对像
	 */
	public void updateSpouseInfo(Customer spouseInfo){
		this.execute("updateSpouseInfo",spouseInfo);
	}

	/**
	 * 通过主键删除
	 * @param id 主键Id
	 */
	public void deleteSpouseInfoById(Integer id){
		this.execute("deleteSpouseInfoById",id);
	}

	/**
	 * 通过主键得到
	 * @param id 主键Id
	 */
	public CustSpouseInfo getSpouseInfoById(Integer id){
		return (CustSpouseInfo)this.queryEntity("getSpouseInfoById",id);
	}

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CustSpouseInfo> querySpouseInfoList(Map<String,Object> condition){
		return (List<CustSpouseInfo>)this.queryEntities("querySpouseInfoList", condition);
	}

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<CustSpouseInfo> querySpouseInfoList(Map<String,Object> condition,IPageSize page){
		return (IPageList<CustSpouseInfo>)this.queryEntities("querySpouseInfoList", page, condition);
	}

}
