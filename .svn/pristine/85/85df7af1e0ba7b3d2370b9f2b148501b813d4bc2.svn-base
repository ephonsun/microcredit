package banger.dao.intf;
import java.util.List;
import banger.domain.permission.PmsUserRoleDept;
/**
 * Created by Administrator on 15-4-14.
 */
public interface IUserRoleDeptDao {
    List<PmsUserRoleDept> getUserRolesByUserId(Integer userId);
    void DeleteUserRoleDept(Integer userId);
    void InsertUserRoleDept(List<PmsUserRoleDept> uRoleDeptList);
    List<PmsUserRoleDept> getPmsUserRoleDeptList(Integer userId);
    void deleteRoleDeptByUserId(Integer userId);

    List<PmsUserRoleDept> getPmsUserRoleDeptByRoleAndUserId(String roleId,Integer userId);

    void deletePmsUserRoleDeptById(Integer deptId);

    void insertUserRoleDept(PmsUserRoleDept userRoleDept);
}
