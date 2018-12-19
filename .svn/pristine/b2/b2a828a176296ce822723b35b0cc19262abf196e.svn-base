package banger.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.dao.PageSizeDao;
import banger.dao.intf.ISysSocketLogDao;
import banger.domain.system.SysSocketLog;

/**
 * 数据访问类
 */
@Repository
public class SysSocketLogDao extends PageSizeDao<SysSocketLog> implements ISysSocketLogDao {

	/**
	 * 新增
	 * @param socketLog 实体对像
	 */
	public void insertSocketLog(SysSocketLog socketLog){
		socketLog.setId(this.newId().intValue());
		this.execute("insertSocketLog",socketLog);
	}

	/**
	 *修改
	 * @param socketLog 实体对像
	 */
	public void updateSocketLog(SysSocketLog socketLog){
		this.execute("updateSocketLog",socketLog);
	}

	/**
	 * 通过主键删除
	 * @param id 主键Id
	 */
	public void deleteSocketLogById(Integer id){
		this.execute("deleteSocketLogById",id);
	}

	/**
	 * 通过主键得到
	 * @param id 主键Id
	 */
	public SysSocketLog getSocketLogById(Integer id){
		return (SysSocketLog)this.queryEntity("getSocketLogById",id);
	}

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysSocketLog> querySocketLogList(Map<String,Object> condition){
		return (List<SysSocketLog>)this.queryEntities("querySocketLogList", condition);
	}

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPageList<SysSocketLog> querySocketLogList(Map<String,Object> condition,IPageSize page){
		return (IPageList<SysSocketLog>)this.queryEntities("querySocketLogList", page, condition);
	}

	public void addSysSocketLog(Integer loanId, String socketCode, String sendXml, String returnMassage, String remark) {

		SysSocketLog sysSocketLog = new SysSocketLog();
		sysSocketLog.setLoanId(loanId);
		sysSocketLog.setSocketCode(socketCode);
		sysSocketLog.setSendXml(sendXml);
		sysSocketLog.setReturnMassage(returnMassage);
		sysSocketLog.setRemark(remark);

		insertSocketLog(sysSocketLog);
	}

	@Override
	public void addSysSocketLog(Integer loanId, String socketCode, String sendXml, String returnMassage, String remark,String codeNum,String codeNumTwo) {

		SysSocketLog sysSocketLog = new SysSocketLog();
		sysSocketLog.setLoanId(loanId);
		sysSocketLog.setSocketCode(socketCode);
		sysSocketLog.setSendXml(sendXml);
		sysSocketLog.setReturnMassage(returnMassage);
		sysSocketLog.setRemark(remark);
		sysSocketLog.setCodeNum(codeNum);
		sysSocketLog.setCodeNumTwo(codeNumTwo);

		insertSocketLog(sysSocketLog);
	}
}
