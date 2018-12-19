package banger.domain.loan;

import banger.domain.enumerate.LoanExamineRight;
import banger.domain.enumerate.LoanExamineType;
import banger.framework.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by zhusw on 2017/7/10.
 */
public class LoanExamineReview {
    private Integer id;                             //主键ID
    private Integer roleId;                         //角色ID
    private String roleName;                        //角色名称
    private Integer reviewCount;                    //审核人数
    private LoanExamineRight right;      //权限        团队内或所有
    private LoanExamineType type;      //类型        指定随机
    private boolean limit = false;       //是否额度控制
    private List<LoanExamineUser> examineUserList;     //审核人员
    private List<LoanExamineUser> randomUserList;       //随机选中的用户

    public LoanExamineReview(){
        this.right = LoanExamineRight.TEAM;
        this.type = LoanExamineType.SELECT_USER;
        this.examineUserList = new ArrayList<LoanExamineUser>();
        this.randomUserList = new ArrayList<LoanExamineUser>();
    }

    /**
     * 得到所有审核人员
     * @return
     */
    public List<LoanExamineUser> getExamineUserList(){
        return this.examineUserList;
    }

    public void setExamineUserList(List<LoanExamineUser> examineUserList) {
        this.examineUserList = examineUserList;
    }

    /**
     * 得到随机审核人员
     * @return
     */
    public List<LoanExamineUser> getRandomUserList(){
        return this.randomUserList;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public LoanExamineRight getRight() {
        return right;
    }

    public void setRight(LoanExamineRight right) {
        this.right = right;
    }

    public LoanExamineType getType() {
        return type;
    }

    public void setType(LoanExamineType type) {
        this.type = type;
    }

    public boolean isLimit() {
        return limit;
    }

    public void setLimit(boolean limit) {
        this.limit = limit;
    }

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
