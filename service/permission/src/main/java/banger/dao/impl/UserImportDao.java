package banger.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import banger.dao.intf.IRoleDao;
import banger.dao.intf.IUserImportDao;
import banger.dao.intf.IUserRoleDao;
import banger.domain.permission.PmsRole;
import banger.domain.permission.PmsUser;
import banger.domain.permission.PmsUserImport;
import banger.domain.permission.PmsUserRoles;
import banger.framework.dao.EntityDao;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-6-10
 * Time: 上午11:24
 * To change this template use File | Settings | File Templates.
 */

@Repository
public class UserImportDao extends EntityDao<PmsUser> implements IUserImportDao{

    private IUserRoleDao userRoleDao;
    private IRoleDao roleDao;

    public IRoleDao getRoleDao() {
        return roleDao;
    }

    public void setRoleDao(IRoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public IUserRoleDao getUserRoleDao() {
        return userRoleDao;
    }

    public void setUserRoleDao(IUserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    /**
     * 通过机构编码查询已存在的机构
     */
    @SuppressWarnings("unchecked")
    public List<PmsUserImport> getExistUserListByUserAccounts(Set<String> codes) {
        return (List<PmsUserImport>)this.queryEntities("getExistUserListByUserAccounts", codes);
    }

    /**
     * 得到所有机构
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<PmsUserImport> getAllExistUserList(){
        return (List<PmsUserImport>)this.queryEntities("getAllExistUserList", new Object[0]);
    }

    /**
     * 插入一批用户
     * @param userList
     */
    public List<PmsUserImport> insertUserListByImport(List<PmsUserImport> userList){
        if(userList.size()>0){
            List<PmsUserRoles> userRoleses = new ArrayList<PmsUserRoles>();
            List<PmsRole> roles = roleDao.getUserAllRoles();
            Map<String,Integer> roleMap = new HashMap<String, Integer>();
            if(roles != null && roles.size() > 0){
                for (PmsRole role : roles){
                    roleMap.put(role.getRoleName(),role.getRoleId());
                }
            }
            for (PmsUserImport user : userList){
                user.setUserId(this.newId().intValue());

                PmsUserRoles userRole = new PmsUserRoles();
                userRole.setPurRoleId(Integer.parseInt(user.getRoleIds()));
                userRole.setPurUserId(user.getUserId());
                userRole.setPurCreateDate(Calendar.getInstance().getTime());
                userRole.setPurUpdateDate(Calendar.getInstance().getTime());
                userRole.setPurCreateUser(user.getUserCreateUser());
                userRole.setPurUpdateUser(user.getUserCreateUser());
                userRoleses.add(userRole);
            }
            this.executes("insertUserImport", userList);
            userRoleDao.insertUserRoles(userRoleses);
        }
        return userList;
    }

    /**
     * 修改一批用户
     * @param userList
     */
    public void updateUserListByImport(List<PmsUserImport> userList){
    	for(PmsUserImport user:userList){
    		this.execute("updateUserListByImport", user);
           // userRoleDao.
    	}
    }

	@Override
	public List<PmsUserImport> getAllUserList() {
		return (List<PmsUserImport>)this.queryEntities("getAllUserList");
	}

	@Override
	public void insertUserByImport(PmsUserImport userImport) {
		userImport.setUserId(this.newId().intValue());
		this.execute("insertUserImport", userImport);
		PmsUserRoles userRole = new PmsUserRoles();
        //userRole.setPurRoleId(5);//默认为“理财经理/柜员”
        userRole.setPurRoleId(6);
        userRole.setPurUserId(userImport.getUserId());
        userRole.setPurCreateDate(Calendar.getInstance().getTime());
        userRole.setPurUpdateDate(Calendar.getInstance().getTime());
        userRole.setPurCreateUser(userImport.getUserCreateUser());
        userRole.setPurUpdateUser(userImport.getUserCreateUser());
		userRoleDao.insertUserRole(userRole);
	}

	@Override
	public void updateUserByImport(PmsUserImport userImport) {
		this.execute("updateUserListByImport", userImport);
	}
}
