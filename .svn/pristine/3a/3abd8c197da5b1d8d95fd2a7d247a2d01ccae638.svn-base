package banger.dao.intf;

import banger.domain.permission.PmsUser;
import banger.domain.permission.PmsUserImport;
import banger.domain.permission.PmsUser_Query;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-6-10
 * Time: 上午11:24
 * To change this template use File | Settings | File Templates.
 */
public interface IUserImportDao {
    @SuppressWarnings("unchecked")
    List<PmsUserImport> getExistUserListByUserAccounts(Set<String> codes);

    @SuppressWarnings("unchecked")
    List<PmsUserImport> getAllExistUserList();

    List<PmsUserImport> insertUserListByImport(List<PmsUserImport> userList);

    void updateUserListByImport(List<PmsUserImport> userList);

	/**
	 * 获得所有用户
	 * @return
	 */
	List<PmsUserImport> getAllUserList();
	
    void insertUserByImport(PmsUserImport userImport);

    void updateUserByImport(PmsUserImport userImport);
}
