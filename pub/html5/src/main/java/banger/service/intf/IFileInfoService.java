package banger.service.intf;

import java.util.List;
import java.util.Map;

import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.domain.html5.IntoFileInfo;

/**
 * 进件附件信息业务访问接口
 */
public interface IFileInfoService {

	/**
	 * 新增进件附件信息
	 * @param fileInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void insertFileInfo(IntoFileInfo fileInfo, Integer loginUserId);

	/**
	 *修改进件附件信息
	 * @param fileInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	void updateFileInfo(IntoFileInfo fileInfo, Integer loginUserId);

	/**
	 * 通过主键删除进件附件信息
	 * @param ID 主键Id
	 */
	void deleteFileInfoById(Integer id);

	/**
	 * 通过主键得到进件附件信息
	 * @param ID 主键Id
	 */
	IntoFileInfo getFileInfoById(Integer id);

	/**
	 * 查询进件附件信息
	 * @param condition 查询条件
	 * @return
	 */
	List<IntoFileInfo> queryFileInfoList(Map<String, Object> condition);

	/**
	 * 分页查询进件附件信息
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	IPageList<IntoFileInfo> queryFileInfoList(Map<String, Object> condition, IPageSize page);

/**
 * 新增进件附件信息 返回id
 * @param intoFileInfo 实体对像
 * */
	Integer insertFileInfoReturnId(IntoFileInfo intoFileInfo);
}
