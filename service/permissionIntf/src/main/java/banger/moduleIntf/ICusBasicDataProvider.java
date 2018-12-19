package banger.moduleIntf;

/**
 * Created by Administrator on 14-3-8.
 */
public interface ICusBasicDataProvider {
	
	/**
	 * 根据BELONG_DEPT_ID查询未删除的客户
	 * @param belongDeptId
	 * @return
	 */
	int getBelongDeptIdCount(Integer belongDeptId);
	
	/**
	 * 针对存量无片区客户和潜在客户 清空其归属用户
	 * @param belongUserId
	 */
	void deleteBelongUserId(Integer belongUserId);

}
