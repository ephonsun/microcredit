package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.ISuDao;
import banger.domain.permission.PmsSu;

/**
 * 配置平台数据访问类
 */
@Repository
public class SuDao extends PageSizeDao<PmsSu> implements ISuDao {

	/**
	 * 新增配置平台
	 * @param su 实体对像
	 */
	public void insertSu(PmsSu su){
		su.setId(this.newId().intValue());
		this.execute("insertSu",su);
	}

	/**
	 *修改配置平台
	 * @param su 实体对像
	 */
	public void updateSu(PmsSu su){
		this.execute("updateSu",su);
	}

	/**
	 * 通过主键删除配置平台
	 * @param id 主键Id
	 */
	public void deleteSuById(Integer id){
		this.execute("deleteSuById",id);
	}

	/**
	 * 通过主键得到配置平台
	 * @param id 主键Id
	 */
	public PmsSu getSuById(Integer id){
		return (PmsSu)this.queryEntity("getSuById",id);
	}

	/**
	 * 通过帐号得到对像
	 * @param account
	 * @return
	 */
	public PmsSu getSuByAccount(String account){
		return (PmsSu)this.queryEntity("getSuByAccount",account);
	}

	/**
	 * 查询配置平台
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PmsSu> querySuList(Map<String,Object> condition){
		return (List<PmsSu>)this.queryEntities("querySuList", condition);
	}

	/**
	 * 分页查询配置平台
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<PmsSu> querySuList(Map<String,Object> condition,IPageSize page){
		return (IPageList<PmsSu>)this.queryEntities("querySuList", page, condition);
	}

}
