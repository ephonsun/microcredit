package banger.domain.enumerate;

import org.apache.commons.lang.enums.Enum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/3.
 */
public class GroupRolesEnum extends Enum {

    private static final long serialVersionUID = 537040876572674967L;
    private Integer roleId ;
    private String roleName ;


    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public GroupRolesEnum(){
        super("");
    }

    public GroupRolesEnum(Integer roleId,String roleName){
        super(String.valueOf(roleId));
        this.setRoleId(roleId);
        this.setRoleName(roleName);
    }

    public static final GroupRolesEnum LEADER = new GroupRolesEnum(2, "主管");
    public static final GroupRolesEnum TEAM_LEADER = new GroupRolesEnum(3, "团队主管");
    public static final GroupRolesEnum MANAGER = new GroupRolesEnum(4, "客户经理");
    public static final GroupRolesEnum BACKER = new GroupRolesEnum(5, "后台人员");
    public static final GroupRolesEnum ALL_GROUP_ROLE = new GroupRolesEnum(0, "3,4,5");

    public static String getRoleName(String roleId) throws Exception{
        GroupRolesEnum rolesEnum = (GroupRolesEnum) GroupRolesEnum.getEnum(GroupRolesEnum.class, roleId);
        if(null!=rolesEnum){
            return rolesEnum.getRoleName();
        }else{
            return "";
        }
    }

    /**
     * //TODO 这个是谁建的，干啥用的，来个注释
     * @return
     */
    public static List<GroupRolesEnum> getGroupRoleList(){

        List<GroupRolesEnum> list = new ArrayList<GroupRolesEnum>();
        list.add(TEAM_LEADER);
        list.add(MANAGER);
        list.add(BACKER);
		/*for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getE_name()+"--"+list.get(i).getE_code());
		}*/
        return list;
    }

}
