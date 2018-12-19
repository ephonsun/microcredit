package banger.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.moduleIntf.ICustomerOptionProvider;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IOptionCollectDao;
import banger.service.intf.IOptionCollectService;
import banger.domain.customer.CustOptionCollect;

/**
 * 客户项选收集业务访问类
 */
@Repository
public class OptionCollectService implements IOptionCollectService,ICustomerOptionProvider {

	@Autowired
	private IOptionCollectDao optionCollectDao;

	/**
	 * 新增客户项选收集
	 * @param optionCollect 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertOptionCollect(CustOptionCollect optionCollect,Integer loginUserId){
		optionCollect.setCreateUser(loginUserId);
		optionCollect.setCreateDate(DateUtil.getCurrentDate());
		optionCollect.setUpdateUser(loginUserId);
		optionCollect.setUpdateDate(DateUtil.getCurrentDate());
		this.optionCollectDao.insertOptionCollect(optionCollect);
	}

	/**
	 *修改客户项选收集
	 * @param optionCollect 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateOptionCollect(CustOptionCollect optionCollect,Integer loginUserId){
		optionCollect.setUpdateUser(loginUserId);
		optionCollect.setUpdateDate(DateUtil.getCurrentDate());
		this.optionCollectDao.updateOptionCollect(optionCollect);
	}

	/**
	 * 通过主键删除客户项选收集
	 * @param ID 主键Id
	 */
	public void deleteOptionCollectById(Integer id){
		this.optionCollectDao.deleteOptionCollectById(id);
	}

	/**
	 * 通过主键得到客户项选收集
	 * @param ID 主键Id
	 */
	public CustOptionCollect getOptionCollectById(Integer id){
		return this.optionCollectDao.getOptionCollectById(id);
	}

	/**
	 * 查询客户项选收集
	 * @param condition 查询条件
	 * @return
	 */
	public List<CustOptionCollect> queryOptionCollectList(Map<String,Object> condition){
		return this.optionCollectDao.queryOptionCollectList(condition);
	}

	/**
	 * 分页查询客户项选收集
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<CustOptionCollect> queryOptionCollectList(Map<String,Object> condition,IPageSize page){
		return this.optionCollectDao.queryOptionCollectList(condition,page);
	}

	/**
	 * 得到客户收集选项
	 */
	public List<CustOptionCollect> getCustomerOptionById(Integer customerId){
		return this.optionCollectDao.getCustomerOptionById(customerId);
	}

	/**
	 * 保存客户收集选项
	 * @param options
	 */
	public void saveCustomerOptions(List<CustOptionCollect> options){
		if(options!=null && options.size()>0){
			Integer customerId = options.get(0).getCustomerId();
			List<CustOptionCollect> list = getCustomerOptionById(customerId);
			Map<String,CustOptionCollect> map = new HashMap<String, CustOptionCollect>();
			for(CustOptionCollect option : list){
				map.put(option.getOptionParam(),option);
			}
			for(CustOptionCollect option : options){
				if(map.containsKey(option.getOptionParam())){
					option.setId(map.get(option.getOptionParam()).getId());
					this.optionCollectDao.updateOptionCollect(option);
				}else{
					this.optionCollectDao.insertOptionCollect(option);
				}
			}
		}

	}
}
