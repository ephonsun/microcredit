package banger.dao.impl;

import java.util.List;
import java.util.Map;

import banger.domain.html5.IntoPhoneMsgCodeQuery;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IPhoneMsgCodeDao;
import banger.domain.html5.IntoPhoneMsgCode;

/**
 * 短信验证码表数据访问类
 */
@Repository
public class PhoneMsgCodeDao extends PageSizeDao<IntoPhoneMsgCode> implements IPhoneMsgCodeDao {

	/**
	 * 新增短信验证码表
	 * @param phoneMsgCode 实体对像
	 */
	public void insertPhoneMsgCode(IntoPhoneMsgCode phoneMsgCode){
		phoneMsgCode.setId(this.newId().intValue());
		this.execute("insertPhoneMsgCode",phoneMsgCode);
	}

	/**
	 *修改短信验证码表
	 * @param phoneMsgCode 实体对像
	 */
	public void updatePhoneMsgCode(IntoPhoneMsgCode phoneMsgCode){
		this.execute("updatePhoneMsgCode",phoneMsgCode);
	}

	/**
	 * 通过主键删除短信验证码表
	 * @param id 主键Id
	 */
	public void deletePhoneMsgCodeById(Integer id){
		this.execute("deletePhoneMsgCodeById",id);
	}

	/**
	 * 通过主键得到短信验证码表
	 * @param id 主键Id
	 */
	public IntoPhoneMsgCode getPhoneMsgCodeById(Integer id){
		return (IntoPhoneMsgCode)this.queryEntity("getPhoneMsgCodeById",id);
	}

	/**
	 * 查询短信验证码表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<IntoPhoneMsgCode> queryPhoneMsgCodeList(Map<String,Object> condition){
		return (List<IntoPhoneMsgCode>)this.queryEntities("queryPhoneMsgCodeList", condition);
	}

	/**
	 * 分页查询短信验证码表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<IntoPhoneMsgCode> queryPhoneMsgCodeList(Map<String,Object> condition,IPageSize page){
		return (IPageList<IntoPhoneMsgCode>)this.queryEntities("queryPhoneMsgCodeList", page, condition);
	}


	public IntoPhoneMsgCodeQuery queryCountByPhone(Map<String, Object> condition) {
		return (IntoPhoneMsgCodeQuery) this.queryEntities("queryCountByPhone",condition);
	}


	public IntoPhoneMsgCodeQuery queryCountByIp(Map<String, Object> condition) {
		return (IntoPhoneMsgCodeQuery) this.queryEntity("queryCountByIp",condition);
	}

	@Override
	public IntoPhoneMsgCode queryMsgInfoByPhone(String phone) {
		return (IntoPhoneMsgCode)this.queryEntity("queryMsgInfoByPhone",phone);
	}
}
