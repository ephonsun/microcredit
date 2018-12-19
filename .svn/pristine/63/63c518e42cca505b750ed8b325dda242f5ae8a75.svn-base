package banger.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IFileInfoDao;
import banger.service.intf.IFileInfoService;
import banger.domain.html5.IntoFileInfo;

/**
 * 进件附件信息业务访问类
 */
@Repository
public class FileInfoService implements IFileInfoService {

	@Autowired
	private IFileInfoDao fileInfoDao;

	/**
	 * 新增进件附件信息
	 * @param fileInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertFileInfo(IntoFileInfo fileInfo,Integer loginUserId){
		this.fileInfoDao.insertFileInfo(fileInfo);
	}

	/**
	 *修改进件附件信息
	 * @param fileInfo 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateFileInfo(IntoFileInfo fileInfo,Integer loginUserId){
		this.fileInfoDao.updateFileInfo(fileInfo);
	}

	/**
	 * 通过主键删除进件附件信息
	 * @param ID 主键Id
	 */
	public void deleteFileInfoById(Integer id){
		this.fileInfoDao.deleteFileInfoById(id);
	}

	/**
	 * 通过主键得到进件附件信息
	 * @param ID 主键Id
	 */
	public IntoFileInfo getFileInfoById(Integer id){
		return this.fileInfoDao.getFileInfoById(id);
	}

	/**
	 * 查询进件附件信息
	 * @param condition 查询条件
	 * @return
	 */
	public List<IntoFileInfo> queryFileInfoList(Map<String,Object> condition){
		return this.fileInfoDao.queryFileInfoList(condition);
	}

	/**
	 * 分页查询进件附件信息
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<IntoFileInfo> queryFileInfoList(Map<String,Object> condition,IPageSize page){
		return this.fileInfoDao.queryFileInfoList(condition,page);
	}
	/**
	 * 新增进件附件信息 返回id
	 * @param intoFileInfo 实体对像
	 * */
	@Override
	public Integer insertFileInfoReturnId(IntoFileInfo intoFileInfo) {
		 return this.fileInfoDao.insertFileInfoReturnId(intoFileInfo);
	}

}
