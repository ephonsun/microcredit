package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.domain.html5.IntoPhoneMsgCodeQuery;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IPhoneMsgCodeDao;
import banger.service.intf.IPhoneMsgCodeService;
import banger.domain.html5.IntoPhoneMsgCode;

/**
 * 短信验证码表业务访问类
 */
@Repository
public class PhoneMsgCodeService implements IPhoneMsgCodeService {

	@Autowired
	private IPhoneMsgCodeDao phoneMsgCodeDao;

	/**
	 * 新增短信验证码表
	 * @param phoneMsgCode 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertPhoneMsgCode(IntoPhoneMsgCode phoneMsgCode,Integer loginUserId){
		this.phoneMsgCodeDao.insertPhoneMsgCode(phoneMsgCode);
	}

	/**
	 *修改短信验证码表
	 * @param phoneMsgCode 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updatePhoneMsgCode(IntoPhoneMsgCode phoneMsgCode,Integer loginUserId){
		this.phoneMsgCodeDao.updatePhoneMsgCode(phoneMsgCode);
	}

	/**
	 * 通过主键删除短信验证码表
	 * @param ID 主键Id
	 */
	public void deletePhoneMsgCodeById(Integer id){
		this.phoneMsgCodeDao.deletePhoneMsgCodeById(id);
	}

	/**
	 * 通过主键得到短信验证码表
	 * @param ID 主键Id
	 */
	public IntoPhoneMsgCode getPhoneMsgCodeById(Integer id){
		return this.phoneMsgCodeDao.getPhoneMsgCodeById(id);
	}

	/**
	 * 查询短信验证码表
	 * @param condition 查询条件
	 * @return
	 */
	public List<IntoPhoneMsgCode> queryPhoneMsgCodeList(Map<String,Object> condition){
		return this.phoneMsgCodeDao.queryPhoneMsgCodeList(condition);
	}

	/**
	 * 分页查询短信验证码表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<IntoPhoneMsgCode> queryPhoneMsgCodeList(Map<String,Object> condition,IPageSize page){
		return this.phoneMsgCodeDao.queryPhoneMsgCodeList(condition,page);
	}

	public IntoPhoneMsgCodeQuery queryCountByPhone(Map<String, Object> condition) {
		return this.phoneMsgCodeDao.queryCountByPhone(condition);
	}


	public IntoPhoneMsgCodeQuery queryCountByIp(Map<String, Object> condition) {
		return this.phoneMsgCodeDao.queryCountByIp(condition);
	}

	public IntoPhoneMsgCode queryMsgInfoByPhone(String phone) {
		return this.phoneMsgCodeDao.queryMsgInfoByPhone(phone);
	}
}
