package banger.service.impl;

import banger.dao.intf.IIntentCustomerDao;
import banger.domain.product.ProdIntentCustomer;
import banger.domain.product.ProdIntentCustomer_Query;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.DateUtil;
import banger.service.intf.IIntentCustomerService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 意向客户列表业务访问类
 */
@Service
public class IntentCustomerService implements IIntentCustomerService {

	@Resource
	private IIntentCustomerDao intentCustomerDao;

	/**
	 * 新增意向客户列表
	 * @param intentCustomer 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public JSONObject insertIntentCustomer(ProdIntentCustomer intentCustomer,Integer loginUserId){
		Map<String,Object> condition = new HashMap<String, Object>();
		condition.put("picIsdel", 0);
		condition.put("picCertificateNum", intentCustomer.getPicCertificateNum());
		condition.put("picProductId", intentCustomer.getPicProductId());
		condition.put("picCertificateType", intentCustomer.getPicCertificateType());
		condition.put("picName", intentCustomer.getPicName());
		Integer count = intentCustomerDao.queryIntentCustomerCount(condition);
		JSONObject resObj = new JSONObject();
		boolean success = false;
		String cause = "";
		if(count.intValue()>0){
			cause = "该客户已添加过对当前产品的意向！";
		}else{
			intentCustomer.setPicCreateUser(loginUserId);
			intentCustomer.setPicCreateDate(DateUtil.getCurrentDate());
			intentCustomer.setPicUpdateUser(loginUserId);
			intentCustomer.setPicUpdateDate(DateUtil.getCurrentDate());
			this.intentCustomerDao.insertIntentCustomer(intentCustomer);
			success = true;
			cause = "新建成功！";
		}
		resObj.put("success", success);
		resObj.put("cause", cause);
		return resObj;
	}

	/**
	 *修改意向客户列表
	 * @param intentCustomer 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateIntentCustomer(ProdIntentCustomer intentCustomer,Integer loginUserId){
		intentCustomer.setPicUpdateUser(loginUserId);
		intentCustomer.setPicUpdateDate(DateUtil.getCurrentDate());
		this.intentCustomerDao.updateIntentCustomer(intentCustomer);
	}

	/**
	 * 通过主键删除意向客户列表
	 * @param PIC_ID 主键Id
	 */
	public void deleteIntentCustomerById(Integer picId){
		this.intentCustomerDao.deleteIntentCustomerById(picId);
	}

	/**
	 * 通过主键得到意向客户列表
	 * @param PIC_ID 主键Id
	 */
	public ProdIntentCustomer getIntentCustomerById(Integer picId){
		return this.intentCustomerDao.getIntentCustomerById(picId);
	}

	/**
	 * 查询意向客户列表
	 * @param condition 查询条件
	 * @return
	 */
	public List<ProdIntentCustomer> queryIntentCustomerList(Map<String,Object> condition){
		return this.intentCustomerDao.queryIntentCustomerList(condition);
	}

	/**
	 * 分页查询意向客户列表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<ProdIntentCustomer_Query> queryIntentCustomerList(Map<String,Object> condition,IPageSize page){
		return this.intentCustomerDao.queryIntentCustomerList(condition,page);
	}

	@Override
	public IPageList<ProdIntentCustomer_Query> queryIntentCustomerListApp(Map<String, Object> condition, IPageSize page) {
		return this.intentCustomerDao.queryIntentCustomerListApp(condition,page);
	}

	public IPageList<ProdIntentCustomer_Query> queryIntentCustomerListAll(Map<String, Object> condition, IPageSize page) {
		return this.intentCustomerDao.queryIntentCustomerListAll(condition,page);
	}

}
