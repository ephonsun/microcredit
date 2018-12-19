package banger.service.impl;

import java.util.List;
import java.util.Map;

import banger.domain.html5.IntoIntefaceRecordQuery;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IIntefaceRecordDao;
import banger.service.intf.IIntefaceRecordService;
import banger.domain.html5.IntoIntefaceRecord;

/**
 * 进件接口记录表业务访问类
 */
@Repository
public class IntefaceRecordService implements IIntefaceRecordService {

	@Autowired
	private IIntefaceRecordDao intefaceRecordDao;

	/**
	 * 新增进件接口记录表
	 * @param intefaceRecord 实体对像
	 *
	 */
	public Integer insertIntefaceRecord(IntoIntefaceRecord intefaceRecord){
		return  this.intefaceRecordDao.insertIntefaceRecord(intefaceRecord);
	}

	/**
	 *修改进件接口记录表
	 * @param intefaceRecord 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateIntefaceRecord(IntoIntefaceRecord intefaceRecord,Integer loginUserId){
		this.intefaceRecordDao.updateIntefaceRecord(intefaceRecord);
	}

	/**
	 * 通过主键删除进件接口记录表
	 * @param REQUEST_ID 主键Id
	 */
	public void deleteIntefaceRecordById(Integer requestId){
		this.intefaceRecordDao.deleteIntefaceRecordById(requestId);
	}

	/**
	 * 通过主键得到进件接口记录表
	 * @param REQUEST_ID 主键Id
	 */
	public IntoIntefaceRecord getIntefaceRecordById(Integer requestId){
		return this.intefaceRecordDao.getIntefaceRecordById(requestId);
	}

	/**
	 * 查询进件接口记录表
	 * @param condition 查询条件
	 * @return
	 */
	public List<IntoIntefaceRecord> queryIntefaceRecordList(Map<String,Object> condition){
		return this.intefaceRecordDao.queryIntefaceRecordList(condition);
	}

	/**
	 * 分页查询进件接口记录表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<IntoIntefaceRecordQuery> queryIntefaceRecordList(Map<String,Object> condition, IPageSize page){
		return this.intefaceRecordDao.queryIntefaceRecordList(condition,page);
	}
	/**
	 * 查询ocr,人脸识别详情
	 *
	 * @return
	 */
	@Override
	public IntoIntefaceRecordQuery IntoIntefaceRecordQuery(Integer id) {
		return this.intefaceRecordDao.IntoIntefaceRecordQuery(id);
	}

}
