package banger.dao.impl;

import java.util.Date;
import java.util.List;

import banger.dao.intf.IUserRoleDeptDao;
import banger.domain.permission.PmsUserRoleDept;
import banger.framework.dao.EntityDao;
import banger.framework.util.DateUtil;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 15-4-14.
 */
@Repository
public class UserRoleDeptDao extends EntityDao<PmsUserRoleDept> implements IUserRoleDeptDao{

    @SuppressWarnings("unchecked")
    public List<PmsUserRoleDept> getUserRolesByUserId(Integer userId) {
        return (List<PmsUserRoleDept>)this.queryEntities("getUserRolesByUserId", userId);
    }
    /**
     * 删除用户角色机构根据用户ID
     * */
    @Override
    public void DeleteUserRoleDept(Integer userId) {
        this.execute("deleteUserRoleDept", userId);
    }
    /**
     * 插入数据到用户角色机构表中
     * */
    @Override
    public void InsertUserRoleDept(List<PmsUserRoleDept> urDeptList) {
        Date createDate = DateUtil.getCurrentDate();
        for(PmsUserRoleDept roleDept : urDeptList){
            roleDept.setCreateDate(createDate);
            roleDept.setRoleDeptId(this.newId().intValue());
        }
        this.executes("insertUserRoleDept", urDeptList);
    }

    /**
     * 查询用户角色关联的机构
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<PmsUserRoleDept> getPmsUserRoleDeptList(Integer userId) {
        return (List<PmsUserRoleDept>) this.queryEntities("GetPmsUserRoleDeptList", userId);
    }
    /**
     * 删除用户角色关联的机构
     * @param userId
     * @return
     */
    @Override
    public void deleteRoleDeptByUserId(Integer userId) {
        this.execute("deleteRoleDeptByUserId", userId);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<PmsUserRoleDept> getPmsUserRoleDeptByRoleAndUserId(String roleId,Integer userId) {
        return (List<PmsUserRoleDept>)this.queryEntities("getPmsUserRoleDeptByRoleId", roleId,userId);
    }

    @Override
    /*
      删除角色下的机构
     */
    public void deletePmsUserRoleDeptById(Integer deptId) {
        this.execute("deletePmsUserRoleDeptById",deptId);
    }

    @Override
    /*
      插入数据
     */
    public void insertUserRoleDept(PmsUserRoleDept userRoleDept) {
/*        userRoleDept.setUserId(this.newId().intValue());
        userRoleDept.setRoleId(this.newId().intValue());
        userRoleDept.setDeptId(this.newId().intValue());*/
        userRoleDept.setRoleDeptId(this.newId().intValue());
        this.execute("insertUserRoleDept",userRoleDept);
    }

}
