package banger.service.intf;


public interface IDeptTreeService {
	
	/**
	 * 得到上级机构树
	 * @param userId
	 * @return
	 */
	String getEditDeptTreeByUserId(Integer userId);
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	String getDeptTreeByUserId(Integer userId,String roleIds);
	
	/**
	 * 获取机构人员树
	 * @param userId
	 * @return
	 */
	public String getDeptUserTreeByUserId(Integer userId,String roleIds);


	/**
	 * 获取机构人员树
	 * @param userId
	 * @return
	 */
	String getDeptUserTreeByUserId(Integer userId,Boolean inculeNoUserDept,Boolean noManagerLimit, Boolean isBranch);


	/**
	 * 获取机构人员片区树
	 *
	 * @param userId
	 * @param isManageDept
	 * @return
	 */
	String getDeptUserAreaTreeByUserId(Integer userId, boolean isManageDept);

    /**
     *
     * @param userId
     * @return
     */
    String getDeptByUserId(Integer userId);
}
