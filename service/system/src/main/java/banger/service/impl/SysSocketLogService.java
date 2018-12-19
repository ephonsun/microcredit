package banger.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.ISysSocketLogDao;
import banger.service.intf.ISysSocketLogService;
import banger.domain.system.SysSocketLog;

/**
 * 业务访问类
 */
@Repository
public class SysSocketLogService implements ISysSocketLogService {

	@Autowired
	private ISysSocketLogDao socketLogDao;

	/**
	 * 新增
	 * @param socketLog 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertSocketLog(SysSocketLog socketLog,Integer loginUserId){
		this.socketLogDao.insertSocketLog(socketLog);
	}

	/**
	 *修改
	 * @param socketLog 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateSocketLog(SysSocketLog socketLog,Integer loginUserId){
		this.socketLogDao.updateSocketLog(socketLog);
	}

	/**
	 * 通过主键删除
	 * @param ID 主键Id
	 */
	public void deleteSocketLogById(Integer id){
		this.socketLogDao.deleteSocketLogById(id);
	}

	/**
	 * 通过主键得到
	 * @param ID 主键Id
	 */
	public SysSocketLog getSocketLogById(Integer id){
		return this.socketLogDao.getSocketLogById(id);
	}

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	public List<SysSocketLog> querySocketLogList(Map<String,Object> condition){
		return this.socketLogDao.querySocketLogList(condition);
	}

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<SysSocketLog> querySocketLogList(Map<String,Object> condition,IPageSize page){
		return this.socketLogDao.querySocketLogList(condition,page);
	}

}
