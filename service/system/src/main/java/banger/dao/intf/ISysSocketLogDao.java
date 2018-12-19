package banger.dao.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.system.SysSocketLog;

/**
 * 数据访问接口
 */
public interface ISysSocketLogDao {

	/**
	 * 新增
	 * @param socketLog 实体对像
	 */
	void insertSocketLog(SysSocketLog socketLog);

	/**
	 *修改
	 * @param socketLog 实体对像
	 */
	void updateSocketLog(SysSocketLog socketLog);

	/**
	 * 通过主键删除
	 * @param id 主键Id
	 */
	void deleteSocketLogById(Integer id);

	/**
	 * 通过主键得到
	 * @param id 主键Id
	 */
	SysSocketLog getSocketLogById(Integer id);

	/**
	 * 查询
	 * @param condition 查询条件
	 * @return
	 */
	List<SysSocketLog> querySocketLogList(Map<String, Object> condition);

	/**
	 * 分页查询
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<SysSocketLog> querySocketLogList(Map<String, Object> condition, IPageSize page);

	void addSysSocketLog(Integer loanId, String socketCode, String sendXml, String returnMassage, String remark);

	void addSysSocketLog(Integer loanId, String socketCode, String sendXml, String returnMassage, String remark,String codeNum,String codeNumTwo);

}
