package banger.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import banger.domain.html5.IntoIntefaceRecordQuery;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.IIntefaceRecordDao;
import banger.domain.html5.IntoIntefaceRecord;

/**
 * 进件接口记录表数据访问类
 */
@Repository
public class IntefaceRecordDao extends PageSizeDao<IntoIntefaceRecord> implements IIntefaceRecordDao {

	/**
	 * 新增进件接口记录表
	 * @param intefaceRecord 实体对像
	 */
	public Integer insertIntefaceRecord(IntoIntefaceRecord intefaceRecord){
		Integer requestId = this.newId().intValue();
		intefaceRecord.setRequestId(requestId);
		this.execute("insertIntefaceRecord",intefaceRecord);
		return requestId;
	}

	/**
	 *修改进件接口记录表
	 * @param intefaceRecord 实体对像
	 */
	public void updateIntefaceRecord(IntoIntefaceRecord intefaceRecord){
		this.execute("updateIntefaceRecord",intefaceRecord);
	}

	/**
	 * 通过主键删除进件接口记录表
	 * @param requestId 主键Id
	 */
	public void deleteIntefaceRecordById(Integer requestId){
		this.execute("deleteIntefaceRecordById",requestId);
	}

	/**
	 * 通过主键得到进件接口记录表
	 * @param requestId 主键Id
	 */
	public IntoIntefaceRecord getIntefaceRecordById(Integer requestId){
		return (IntoIntefaceRecord)this.queryEntity("getIntefaceRecordById",requestId);
	}

	/**
	 * 查询进件接口记录表
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<IntoIntefaceRecord> queryIntefaceRecordList(Map<String,Object> condition){
		return (List<IntoIntefaceRecord>)this.queryEntities("queryIntefaceRecordList", condition);
	}

	/**
	 * 分页查询进件接口记录表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<IntoIntefaceRecordQuery> queryIntefaceRecordList(Map<String,Object> condition, IPageSize page){

		return (IPageList<IntoIntefaceRecordQuery>)this.queryEntities("queryIntefaceRecordListQuery", page, condition);
	}
	/**
	 * 查询ocr,人脸识别详情
	 *
	 * @return
	 */
	@Override
	public IntoIntefaceRecordQuery IntoIntefaceRecordQuery(Integer id) {
		return (IntoIntefaceRecordQuery)this.queryEntity("IntoIntefaceRecordQuery",id);
	}

}
