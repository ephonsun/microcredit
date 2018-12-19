package banger.service.impl;

import java.util.*;

import banger.dao.intf.IDeptDao;
import banger.dao.intf.IUserDao;
import banger.domain.permission.PmsDept_Query;
import banger.framework.util.DateUtil;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.dao.intf.IDeptListDao;
import banger.framework.util.StringUtil;
import banger.service.intf.IDeptListService;
import banger.domain.permission.PmsDept;

/**
 * 机构表业务访问类
 */
public class DeptListService implements IDeptListService {
	private IDeptListDao deptListDao;
    private IDeptDao deptDao;
    private final int searchCodeLen = 3;
    private IUserDao userDao;
    public void setDeptListDao(IDeptListDao deptListDao) {
        this.deptListDao = deptListDao;
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    public void setDeptDao(IDeptDao deptDao) {
        this.deptDao = deptDao;
    }

    /**
	 * 新增机构表
	 * @param dept 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void insertDept(PmsDept dept,Integer loginUserId){
        String searchCode = this.getSearchCode(dept);
//        Integer sortNo = this.getSortNo(dept.getDeptParentId());
        Integer depth = this.getDepth(dept.getDeptParentId());
        dept.setDeptDepth(depth);
//        dept.setDeptSort(sortNo);
        dept.setDeptSearchCode(searchCode);
		dept.setDeptCreateUser(loginUserId);
		dept.setDeptCreateDate(DateUtil.getCurrentDate());
		dept.setDeptUpdateUser(loginUserId);
		dept.setDeptUpdateDate(DateUtil.getCurrentDate());
		this.deptListDao.insertDept(dept);
	}
    /**
     * 得到机构查询码
     * @return
     */
    private String getSearchCode(PmsDept pmsDept){
        Integer parentDeptId = pmsDept.getDeptParentId();
        int searchCode = 0;
        String parentSearchCode = "";
        PmsDept parentDept = this.deptDao.getDeptById(parentDeptId);
        if(parentDept!=null){
            parentSearchCode = parentDept.getDeptSearchCode();
            List<PmsDept> depts = this.deptDao.getChildrenByParentId(parentDeptId);
            for(PmsDept dept : depts){
                String code = dept.getDeptSearchCode();
                int deptSearchCode = Integer.parseInt(code.substring(code.length()-searchCodeLen));
                if(deptSearchCode>searchCode)searchCode=deptSearchCode;
            }
            pmsDept.setDeptSort(searchCode + 1);
        }
        return parentSearchCode+ StringUtil.padLeft(String.valueOf(searchCode + 1), searchCodeLen, '0');
    }
    /**
     * 得到机构排序号
     * @param parentDeptId
     * @return
     */
    private Integer getSortNo(Integer parentDeptId){
        int sortNo = 0;
        List<PmsDept> depts = this.deptDao.getChildrenByParentId(parentDeptId);
        for(PmsDept dept : depts){
            if(sortNo<dept.getDeptSort().intValue())sortNo=dept.getDeptSort();
        }
        return sortNo+1;
    }

    /**
     * 取得节点深度
     * @param parentDeptId
     * @return
     */
    private Integer getDepth(Integer parentDeptId){
        PmsDept parentDept = this.deptDao.getDeptById(parentDeptId);
        return parentDept.getDeptDepth()+1;
    }

    /**
	 *修改机构表
	 * @param dept 实体对像
	 * @param loginUserId 登入用户Id
	 */
	public void updateDept(PmsDept dept,Integer loginUserId){
		dept.setDeptUpdateUser(loginUserId);
		dept.setDeptUpdateDate(DateUtil.getCurrentDate());
		this.deptListDao.updateDept(dept);
	}

    /*
     * 更改归属机构，重新生成sercherCode
     */
    public void updateSearchCode(Integer deptId,Integer parentDeptId){
        PmsDept_Query dept = this.deptDao.getDeptById(deptId);
        List<PmsDept> updateList = new ArrayList<PmsDept>();
        if(dept!=null && !dept.getDeptParentId().equals(parentDeptId)){
            dept.setDeptParentId(parentDeptId);
            List<PmsDept> list = this.deptDao.getDeptByDeptSeachCode(dept.getDeptSearchCode());
            String searchCode = this.getSearchCode(dept);
            int n = dept.getDeptSearchCode().length();
            dept.setDeptSearchCode(searchCode);
            dept.setDeptDepth(searchCode.length()/3-1);
            updateList.add(dept);
            for(PmsDept d : list){
                if(!d.getDeptId().equals(deptId)){
                    if(StringUtil.isNotEmpty(d.getDeptSearchCode()) && d.getDeptSearchCode().length()>n){
                        String code = d.getDeptSearchCode().substring(n);
                        d.setDeptSearchCode(searchCode+code);
                        d.setDeptDepth(d.getDeptSearchCode().length()/3-1);
                        updateList.add(d);
                    }
                }
            }
        }

        for(PmsDept d : updateList){
            this.deptDao.updateDeptSearchCodeById(d.getDeptId(),d.getDeptSearchCode(),d.getDeptDepth());
        }
    }

	/**
	 * 通过主键删除机构表
	 * @param
	 */
	public void deleteDeptById(Integer deptId){
		this.deptListDao.deleteDeptById(deptId);
	}

	/**
	 * 通过主键得到机构表
	 * @param
	 */
	public PmsDept getDeptById(Integer deptId){
		return this.deptListDao.getDeptById(deptId);
	}

	/**
	 * 查询机构表
	 * @param condition 查询条件
	 * @return
	 */
	public List<PmsDept> queryDeptList(Map<String,Object> condition){
		return this.deptListDao.queryDeptList(condition);
	}

	/**
	 * 分页查询机构表
	 * @param condition 查询条件
	 * @param page 分页对像
	 * @return
	 */
	public IPageList<PmsDept> queryDeptList(Map<String,Object> condition,IPageSize page){
		return this.deptListDao.queryDeptList(condition,page);
	}
    /**
     * 加载树形Grid
     * */
    public List<PmsDept> getDeptListTree(Integer userId){
        List<PmsDept> list = this.deptListDao.getDeptListTree(userId);
        Map<Integer,PmsDept> deptMap = new HashMap<Integer, PmsDept>();
        PmsDept root = null;
        for(PmsDept dept : list){
            deptMap.put(dept.getDeptId(),dept);
        }
        for(PmsDept dept : list){
            if(dept.getDeptParentId()>0 && !deptMap.containsKey(dept.getDeptParentId())){
                if(root==null){
                    root = new PmsDept();
                    root.setDeptId(-2);
                    root.setDeptCode("");
                    root.setDeptParentId(0);
                    root.setDeptName("管辖机构");
                }
                dept.setDeptParentId(-2);
            }
        }
        if(root!=null){
            List<PmsDept> newList = new ArrayList<PmsDept>();
            newList.add(root);
            newList.addAll(list);
            return newList;
        }else{
            return list;
        }
    }


    public PmsDept getDeptByDeptId(Integer deptId) {
        return this.deptListDao.getDeptByDeptId(deptId);
    }


    /**校验删除机构时是否有人员*/
    public String checkCanDelete(String deptId) {
        //获得机构
        PmsDept pmsDept = deptListDao.getDeptById(Integer.parseInt(deptId));
        //获得本机构及其子机构
        List<PmsDept> pmsDepts = deptListDao.getDeptByDeptSeachCode(pmsDept.getDeptSearchCode());
        for(PmsDept dept:pmsDepts){
            //查看该机构下是否有人员
            if(userDao.getUserDeptIdcount(dept.getDeptId())>0){
                return "hasUser";
            }
        }
        return "";
    }

    @Override
    /**
     * 删除机构(伪删除)
     * @param deptSeachCode
     */
    public void delDept(String deptSeachCode) {
        List<PmsDept> deptList = deptListDao.getDeptByDeptSeachCode(deptSeachCode);
        //（伪）删除该节点的所有n级子节点
        for(PmsDept pmsDept:deptList){
            pmsDept.setDeptIsdel(1);
            deptListDao.updateDept(pmsDept);
        }
    }

    /**
     *
     * @param userId
     * @return
     */
    public List<String> getDeptSearchCodesByUserId(Integer userId,Integer roleId){
        List<?> list = deptListDao.getDeptSearchCodesByUserId(userId,roleId);
        List<String> codes = new ArrayList<String>();
        Set<Object> set = new HashSet<Object>();
        for(Object obj : list){
            set.add(obj);
        }
        for(Object obj : list){
            String str = (String)obj;
            if(str.length()>5){
                String pre = str.substring(str.length()-3);
                if(!set.contains(pre)){
                    codes.add(str);
                }
            }else{
                codes.add(str);
            }
        }

        return codes;
    }

	@Override
	  /**
    *  修改DeptSort
    */
	public void updateDeptSort(Map<String, Object> deptMap) {
		// TODO Auto-generated method stub
		 this.deptListDao.updateDeptSort(deptMap);
		 return ;
	}

	@Override
	  /**
	    *  查找被挤掉的DeptId
	    */
	public List<PmsDept> selectSecondDeptId(Map<String, Object> deptMap) {
		// TODO Auto-generated method stub
		return this.deptListDao.selectSecondDeptId(deptMap);
	}

    /**
     * 查找出每个部门排序最上方部门list
     * @return
     */
    public List<PmsDept> getTopDeptList() {
        return this.deptListDao.getTopDeptList();
    }

    /**
     * 查找出每个部门排序最上方部门list
     * @return
     */
    public List<PmsDept> getBottomDeptList() {
        return this.deptListDao.getBottomDeptList();
    }
}
