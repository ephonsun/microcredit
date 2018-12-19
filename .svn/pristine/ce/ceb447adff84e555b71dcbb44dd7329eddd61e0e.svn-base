package banger.controller;

import banger.common.BaseController;
import banger.domain.permission.PmsDept;
import banger.domain.permission.PmsDept_Query;
import banger.framework.util.StringUtil;
import banger.moduleIntf.IPermissionService;
import banger.service.intf.IDeptService;
import banger.service.intf.IDeptTreeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/deptManager")
public class DeptManagerController extends BaseController {
	private static final long serialVersionUID = -8949785759342291150L;
	@Autowired
	private IDeptService deptService;
	@Autowired
	private IDeptTreeService deptTreeService;
	@Autowired
	private IPermissionService permissionModuleImpl;
	private PmsDept_Query dept;
	
	/**
	 * 得到机构页面
	 * @return
	 */
	@RequestMapping("/getDeptManagerPage")
	public String getDeptManagerPage(){
		Integer userId = this.getLoginInfo().getUserId();
		String roleIds = this.getLoginInfo().getRoleIds();
		String deptJson = this.deptTreeService.getDeptTreeByUserId(userId,roleIds);
		this.getRequest().setAttribute("deptJson", deptJson);
		return "/permission/vm/deptUserList";
	}
	
	/**
	 * 新增编辑机构
	 * @return
	 */
	@RequestMapping("addUpdateDept")
	public String addUpdateDept(PmsDept dept){
		Integer userId = this.getLoginInfo().getUserId();
		String itemIds = getParameter("itemIds");
		String deptIdStr = getParameter("dept.deptId");
		String deptParentIdStr = getParameter("dept.deptParentId");
		String deptName = getParameter("dept.deptName");
		String deptCode = getParameter("dept.deptCode");
		String deptRemark = getParameter("dept.deptRemark");

		if(StringUtils.isNotBlank(deptIdStr)&&StringUtils.isNumeric(deptIdStr)){
			dept.setDeptId(Integer.valueOf(deptIdStr));
		}
		if(StringUtils.isNotBlank(deptParentIdStr)&&StringUtils.isNumeric(deptParentIdStr)){
			dept.setDeptParentId(Integer.valueOf(deptParentIdStr));
		}
		if(StringUtils.isNotBlank(deptName)){
			dept.setDeptName(deptName);
		}
		if(StringUtils.isNotBlank(deptCode)){
			dept.setDeptCode(deptCode);
		}
		if(StringUtils.isNotBlank(deptRemark)){
			dept.setDeptRemark(deptRemark);
		}

		if(dept.getDeptId()==null){
			deptService.addDept(dept,userId);
//			deptService.updateDeptItem(itemIds,dept.getDeptId());
		}else{
			deptService.editDept(dept,userId);

			//TODO
//			Integer deptId = dept.getDeptId();
//			deptService.updateDeptItem(itemIds,deptId);
		}
		return null;
	}
	/**
	 * 跳转新建页面
	 * @return
	 */
	@RequestMapping("/getAddDeptPage")
	public String getAddDeptPage(HttpServletRequest request){
		String deptParentId = this.getParameter("deptParentId");
		String parentDeptName = deptService.getDeptNameById(Integer.parseInt(deptParentId));
		dept = new PmsDept_Query();
		dept.setDeptParentId(Integer.parseInt(deptParentId));
		request.setAttribute("parentDeptName", parentDeptName);
		request.setAttribute("dept", dept);
		initParentDeptTree();
		return "/permission/vm/deptDetail";
	}
	
	/**
	 * 编辑页面
	 * @return
	 */
	@RequestMapping("/getUpdateDeptPage")
	public String getUpdateDeptPage(HttpServletRequest request){
		String parentDeptName = null;
		String deptIdStr = this.getParameter("dept.deptId");
		dept = deptService.getDeptById(Integer.valueOf(deptIdStr));
		String deptParentId = this.getParameter("deptParentId");
		String isRoot = this.getParameter("isRoot");
		if(deptParentId.equals("null")){
			Integer parentId = dept.getDeptParentId();
			if(parentId == 0){
				parentDeptName = "无";
			}else{
				parentDeptName = deptService.getDeptById(parentId).getDeptName();
			}
//			parentDeptName = dept.getDeptName();
		}else{
			parentDeptName = deptService.getDeptNameById(Integer.parseInt(deptParentId));
		}
		request.setAttribute("parentDeptName", parentDeptName);
		request.setAttribute("isRoot", isRoot);
		request.setAttribute("dept", dept);
		initParentDeptTree();
		return "/permission/vm/deptDetail";
	}
	
	/**
	 * 初始化上级机构数
	 */
	private void initParentDeptTree(){
		Integer userId = this.getLoginInfo().getUserId();
		String deptJson = this.deptTreeService.getEditDeptTreeByUserId(userId);
		this.getRequest().setAttribute("deptJson", deptJson);
	}
	/**
	 * 获取刷新机构树
	 */
	@RequestMapping("refreshTree")
	@ResponseBody
	public void refreshTree(){
		Integer userId = this.getLoginInfo().getUserId();
		String roleIds = this.getLoginInfo().getRoleIds();
		String deptJson = this.deptTreeService.getDeptTreeByUserId(userId,roleIds);
		renderText(deptJson);
	}
	/**
	 * 获得多选下拉单选项
	 */
	public String getManagementArea(){
		
		return null;
	}
	 /**
     * 校验删除机构时是否有人员
     * @return
     */
	 @RequestMapping("checkDelete")
    public String checkDelete(HttpServletRequest request){
        String deptId = this.getParameter("deptId");
        //校验是否可以删除机构
        this.renderText(deptService.checkCanDelete(deptId));
        return null;
    }
    /**
     * （伪）删除机构
     * @return
     */
	@RequestMapping("deleteDept")
    public void deleteDept(HttpServletRequest request) {
        int deptId = Integer.parseInt(this.getParameter("deptId"));
        dept=deptService.getDeptById(deptId);
        String deptSeachCode = dept.getDeptSearchCode();
        deptService.delDept(deptSeachCode);
        this.renderText(dept.getDeptParentId()+"");
    }
    /**
     * 上移机构
     * @return
     */
	@RequestMapping("moveupPmsDept")
    public void moveupPmsDept(HttpServletRequest request) {
        int currentDeptId = Integer.parseInt(this.getParameter("currentDeptId"));
        int prevDeptId = Integer.parseInt(this.getParameter("prevDeptId"));
        int userId = this.getLoginInfo().getUserId();
        deptService.moveupPmsDept(currentDeptId, prevDeptId, userId);
        this.renderText(currentDeptId+"");
    }

    /**
     * 下移机构
     * @return
     */
	@RequestMapping("movedownPmsDept")
    public void movedownPmsDept(HttpServletRequest request) {
        int currentDeptId = Integer.parseInt(this.getParameter("currentDeptId"));
        int nextDeptId = Integer.parseInt(this.getParameter("nextDeptId"));
        int userId = this.getLoginInfo().getUserId();
        deptService.movedownPmsDept(currentDeptId, nextDeptId, userId);
        this.renderText(currentDeptId+"");
    }

	/**
	 * 获取可管理的机构人员树
	 * @return
	 */
	public String initDeptUserTree(){
		Integer userId = this.getLoginInfo().getUserId();
		String roleIds = this.getLoginInfo().getRoleIds();
		/*
        String flag = this.getParameter("flag");
        boolean isBranch = false;
        if(!StringUtil.isNullOrEmpty(flag)){
            // 需要获取可管理的机构人员树不包含分行和支行的人员
            isBranch = true;
        }
        */
		String detpUserJson = this.deptTreeService.getDeptUserTreeByUserId(userId,roleIds);
		renderText(detpUserJson);
		return null;
	}


	public String initDeptTree(){
		Integer userId = this.getLoginInfo().getUserId();
		String roleIds = this.getLoginInfo().getRoleIds();
		String deptJson = this.deptTreeService.getDeptTreeByUserId(userId,roleIds);
		renderText(deptJson);
		return null;
	}


	public String initDeptUserAreaTree(){
		Integer userId = this.getLoginInfo().getUserId();
		boolean isManageDept = permissionModuleImpl.isManageDept(userId);
		String duAreaJson = this.deptTreeService.getDeptUserAreaTreeByUserId(userId, isManageDept);
		renderText(duAreaJson);
		return null;
	}
	
	/**
	 * 校验机构编号是否重复
	 * */
	@RequestMapping("checkDeptCodeIsExist")
 	public void checkDeptCodeIsExist(HttpServletRequest request){
	    String deptCode = this.getParameter("deptCode");
	    String deptId = this.getParameter("deptId");
	    Integer id = StringUtil.isNotEmpty(deptId)?Integer.parseInt(deptId):null;
	    if(deptService.checkUniqueDeptCode(deptCode, id)){
            renderText(false,"deptCodeRepeat","已存在相同的机构编号，请重新输入");
        }
	}
	
	/**
	 * 校验机构名称是否重复
	 * */
	@RequestMapping("checkDeptNameIsExist")
 	public void checkDeptNameIsExist(){
	    if(deptService.checkUniqueDeptName(dept)){
	        renderText(false,"deptNameRepeat","同层组织机构树中已存在相同的机构名称，请重新输入");
	    }

	}
    /**校验机构的归属机构是否当前机构的子机构*/
 public void checkBelongDept(){
     String deptId = this.getParameter("deptId");
     String belongToDeptId = this.getParameter("belongToDeptId");
     if(StringUtil.isNotEmpty(deptId) && StringUtil.isNotEmpty(belongToDeptId)){
         if(deptService.checkDeptBelong(Integer.parseInt(deptId),Integer.parseInt(belongToDeptId))){
             renderText(false,"deptBelong","为当前机构的子机构，请重新选择");
         }
     }
 }
}
