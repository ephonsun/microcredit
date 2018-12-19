package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.domain.html5.IntoPhoneMsgCodeQuery;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.html5.IntoPhoneMsgCode;

/**
 * 短信验证码表业务访问接口
 */
public interface IPhoneMsgCodeService {

	/**
	 * 新增短信验证码表
	 * @param phoneMsgCode 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertPhoneMsgCode(IntoPhoneMsgCode phoneMsgCode, Integer loginUserId);

	/**
	 *修改短信验证码表
	 * @param phoneMsgCode 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updatePhoneMsgCode(IntoPhoneMsgCode phoneMsgCode, Integer loginUserId);

	/**
	 * 通过主键删除短信验证码表
	 * @param ID 主键Id
	 */
	void deletePhoneMsgCodeById(Integer id);

	/**
	 * 通过主键得到短信验证码表
	 * @param ID 主键Id
	 */
	IntoPhoneMsgCode getPhoneMsgCodeById(Integer id);

	/**
	 * 查询短信验证码表
	 * @param condition 查询条件
	 * @return
	 */
	List<IntoPhoneMsgCode> queryPhoneMsgCodeList(Map<String, Object> condition);

	/**
	 * 分页查询短信验证码表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<IntoPhoneMsgCode> queryPhoneMsgCodeList(Map<String, Object> condition, IPageSize page);


	/**
	 * 根据PHONE查询当天已查询次数
	 * @param condition
	 * @return
	 */
	IntoPhoneMsgCodeQuery queryCountByPhone(Map<String, Object> condition);

	/**
	 * 根据IP查询当天已查询次数
	 * @param condition
	 * @return
	 */

	IntoPhoneMsgCodeQuery queryCountByIp(Map<String, Object> condition);

	/**
	 * 根据PHONE获取最新的验证码
	 */
	IntoPhoneMsgCode queryMsgInfoByPhone(String phone);
}
