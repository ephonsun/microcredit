package banger.service.impl;

import banger.dao.intf.IUserDao;
import banger.dao.intf.IUserRoleDeptDao;
import banger.domain.permission.PmsUserRoleDept;
import banger.service.intf.IUserRoleDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 15-4-9.
 */
@Service
public class UserRoleDeptService implements IUserRoleDeptService{
    @Autowired
    private IUserRoleDeptDao userRoleDeptDao;
    @Autowired
    private IUserDao userDao;
    private final int passwordExpiredDays = 90;		//密码过期天数
    private static final String DEFAULT_PASSWORD="111111";

    public void setUserRoleDeptDao(IUserRoleDeptDao userRoleDeptDao) {
        this.userRoleDeptDao = userRoleDeptDao;
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 根据用户ID删除用户角色关联的机构
     * @param userId
     */

    @Override
    public void DeleteUserRoleDept(Integer userId) {
        userRoleDeptDao.DeleteUserRoleDept(userId);
    }
    /**
     * 根据用户ID插入角色关联的机构
     * @param
     */
    @Override
    public void InsertUserRoleDept(List<PmsUserRoleDept> uRoleDeptList) {
         userRoleDeptDao.InsertUserRoleDept(uRoleDeptList);
    }
    /**
     * 查询用户角色关联的机构
     * @param userId
     * @return
     */
    @Override
    public List<PmsUserRoleDept> getPmsUserRoleDeptList(Integer userId) {
        return userRoleDeptDao.getPmsUserRoleDeptList(userId);
    }

    /**
     *
     * @param deptIds
     * @param roleId
     */
    /*    public void saveUserRoleDepts(String deptIds,String roleId,Integer userId,Integer loginUserId){
        String[] ids = deptIds.split(",",-1);
        Map<String,Boolean> idMap = new HashMap<String,Boolean>();
        for(String id : ids){
            idMap.put(id, false);
        }
        List<PmsUserRoleDept> deptList = userRoleDeptDao.getPmsUserRoleDeptByRoleAndUserId(roleId,userId);
        for(PmsUserRoleDept dept : deptList){
            if(!idMap.containsKey(dept.getDeptId())){
                userRoleDeptDao.deletePmsUserRoleDeptById(dept.getRoleDeptId());
            }else{
                idMap.put(dept.getDeptId().toString(),true);
            }
        } 
        for(String id : idMap.keySet()){
            if(!idMap.get(id)){
                PmsUserRoleDept userRoleDept = new PmsUserRoleDept();
                userRoleDept.setDeptId(Integer.parseInt(id));
                userRoleDept.setRoleId(Integer.parseInt(roleId));
                userRoleDept.setUserId(userId);
                userRoleDept.setCreateUser(loginUserId);
                userRoleDept.setCreateDate(DateUtil.getCurrentDate());
                userRoleDeptDao.insertUserRoleDept(userRoleDept);
            }
        }
    }
*/
}
