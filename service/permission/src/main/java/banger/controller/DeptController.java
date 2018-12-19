package banger.controller;

import banger.common.BaseController;
import banger.domain.permission.PmsDept;
import banger.framework.util.StringUtil;
import banger.moduleIntf.ISystemModule;
import banger.service.impl.DeptTreeService;
import banger.service.intf.IDeptListService;
import banger.service.intf.IDeptTreeService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 15-4-20.
 */
@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController {
    private static final long serialVersionUID = 2358343229263381470L;
    private IDeptListService deptListService;
    private IDeptTreeService deptTreeService;
    private PmsDept dept;
    private Integer deptId;
    private ISystemModule systemModuleImpl;
    public void setDeptTreeService(DeptTreeService deptTreeService) {
        deptTreeService = deptTreeService;
    }

    public void setDeptListService(IDeptListService deptListService) {
        deptListService = deptListService;
    }

    public void setDept(PmsDept dept) {
        dept = dept;
    }

    public PmsDept getDept() {
        return dept;
    }

    public void setDeptId(Integer deptId) {
        deptId = deptId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    /**
     * 跳转到新增或编辑机构页面
     * @return
     */
//    @RequestMapping("/getWorkbench")
    public String initPmsDeptPage(HttpServletRequest request){
        Integer userId = getLoginInfo().getUserId();
        String deptId = this.getParameter("deptId");
        if(!StringUtil.isNullOrEmpty(deptId)){
            dept = deptListService.getDeptByDeptId(Integer.parseInt(deptId.toString()));
        }
        String deptJson = deptTreeService.getDeptByUserId(userId);
        if(dept!=null && dept.getDeptParentId()==0){
            request.setAttribute("isRoot", "0");
        }
        request.setAttribute("deptJson",deptJson);
        return SUCCESS;
    }
    /**得到导入机构界面*/
    public String initImportDept(){
        return SUCCESS;
    }
    /**
     * 新增或编辑机构
     * @return
     */
    @RequestMapping("updatePmsDept")
    public String updatePmsDept(HttpServletRequest request){
        Integer loginUserId = getLoginInfo().getUserId();
        //处理机构新增和编辑业务
        if(dept.getDeptId()==null) {
            //新增机构
            deptListService.insertDept(dept, loginUserId);
            addSystemLog(1);
        }else{
            //编辑机构
        	deptListService.updateSearchCode(dept.getDeptId(),dept.getDeptParentId());
            deptListService.updateDept(dept, loginUserId);
            addSystemLog(2);
        }
        renderText(true, "", "");
        return null;
    }

    /**
     * 跳转到机构管理页面
     * @return
     */
    public String getDeptListPage(HttpServletRequest request){
        Integer userId = getLoginInfo().getUserId();
        String roleIds = getLoginInfo().getRoleIds();
        List<PmsDept> list = deptListService.getDeptListTree(userId);
        Map<String, String> topDeptIdMap =getTopDeptIdMap();
        Map<String, String> bottomDeptIdMap =getBottomDeptIdMap();
        JSONArray deptJson = new JSONArray();
        for(PmsDept dept : list){
            JSONObject jo = new JSONObject();
            jo.put("id",dept.getDeptId());
            if(dept.getDeptParentId().equals(0)){
                jo.put("pId",-1);
            }else{
                jo.put("pId",dept.getDeptParentId());
            }
            String[] s = new String[3];
            s[0] = dept.getDeptName();
            s[1] = dept.getDeptCode();
            if(dept.getDeptId()==-2){
                s[2] = "<div style=\"width: 100px; text-align: left;\" ></div>";
            }else if(dept.getDeptParentId()<=0){
                s[2] = "<div style=\"width: 100px; text-align: left;\" ><label class=\"ui-link mr5\" onclick=\"editDept('"+dept.getDeptId()+"')\">编辑</label><label class=\"ui-link mr5  ui-link-disabled\" disabled=\"disabled\" onclick=\"delDept('"+dept.getDeptId()+"','"+dept.getDeptName()+"')\" >删除</label></div>";
            }else{
                s[2] = "<div style=\"width: 100px; text-align: left;\" >";
                if(hasFuncPermit("editDept")){
                    s[2] += "<label class=\"ui-link mr5\" onclick=\"editDept('"+dept.getDeptId()+"')\">编辑</label>";
                }else{
                    s[2] += "<label class=\"ui-link mr5 ui-link-disabled\" disabled=\"disabled\" >编辑</label>";
                }
                if(hasFuncPermit("delDept")){
                    s[2] += "<label class=\"ui-link mr5\" onclick=\"delDept('"+dept.getDeptId()+"','"+dept.getDeptName()+"')\" >删除</label>";
                }else{
                    s[2] += "<label class=\"ui-link mr5 ui-link-disabled\" disabled=\"disabled\" >删除</label>";
                }
                if(userId==1 || userId== 2){
                    if(topDeptIdMap.containsValue(dept.getDeptId().toString())){
                        s[2] += "<label class=\"ui-link mr5 ui-link-disabled\" disabled=\"disabled\" onclick=\"upDept('"+dept.getDeptId()+"','"+dept.getDeptName()+"')\" >上移</label>";
                    }else{
                        s[2] += "<label class=\"ui-link mr5 \" onclick=\"upDept('"+dept.getDeptId()+"','"+dept.getDeptName()+"')\" >上移</label>";
                    }
                    if(bottomDeptIdMap.containsValue(dept.getDeptId().toString())){
                        s[2] += "<label class=\"ui-link mr5  ui-link-disabled\" disabled=\"disabled\"  onclick=\"downDept('"+dept.getDeptId()+"','"+dept.getDeptName()+"')\" >下移</label>";
                    }else{
                        s[2] += "<label class=\"ui-link mr5\" onclick=\"downDept('"+dept.getDeptId()+"','"+dept.getDeptName()+"')\" >下移</label>";
                    }
                }
                s[2] += "</div>";
            }
            jo.put("cols",s);
            deptJson.add(jo);
        }
        request.setAttribute("deptJson",deptJson);
        return SUCCESS;
    }

   /**校验删除机构时是否有人员*/
   public String checkDelete(HttpServletRequest request){
       String deptId = this.getParameter("deptId");
       //校验是否可以删除机构
       renderText(deptListService.checkCanDelete(deptId));
       return null;
   }
    /**
     * （伪）删除机构
     * @return
     */
    public void deleteDept(HttpServletRequest request) {
        int deptId = Integer.parseInt(this.getParameter("deptId"));
        dept=deptListService.getDeptById(deptId);
        String deptSeachCode = dept.getDeptSearchCode();
        deptListService.delDept(deptSeachCode);
        addSystemLog(3);
        renderText(dept.getDeptParentId()+"");
    }

    public void upDept(HttpServletRequest request){
    	 int firstdeptId = Integer.parseInt(this.getParameter("deptId"));
    	 int isUp = Integer.parseInt(this.getParameter("isUp"));
    	 dept=deptListService.getDeptById(deptId);
    	 int firstDeptSort=dept.getDeptSort();
    	 int deptParentId=dept.getDeptParentId();
		 int i=0;
    	 Map<String, Object> deptMap=new HashMap<String, Object>();
        	 deptMap.put("deptParentId", deptParentId);
        	 List<PmsDept> deptList =deptListService.selectSecondDeptId(deptMap);
        if(isUp==1){	 
        	if(firstDeptSort>deptList.get(0).getDeptSort()){	 
       		   for(PmsDept deptChange:deptList){
    			    if(deptChange.equals(dept)){
    			     	 break;
    			    }
    			    i++;
    		      }
        		 int temp=firstDeptSort;
    			 int secondDeptsort=temp;
    			 firstDeptSort=deptList.get(i-isUp).getDeptSort();
    			 deptMap.put("deptId", firstdeptId);
            	 deptMap.put("deptSort", firstDeptSort);
            	 deptListService.updateDeptSort(deptMap);  
          		 deptMap.clear(); 
          		 deptMap.put("deptId", deptList.get(i-isUp).getDeptId());
           	     deptMap.put("deptSort", secondDeptsort);
           	     deptListService.updateDeptSort(deptMap);
                addSystemLog(4);
                }
        }        
        if(isUp==-1){	 
        	if(firstDeptSort<deptList.get(deptList.size()-1).getDeptSort()){	 
       		   for(PmsDept deptChange:deptList){
    			    if(deptChange.equals(dept)){
    			     	 break;
    			    }
    			    i++;
    		      }
        		 int temp=firstDeptSort;
    			 int secondDeptsort=temp;
    			 firstDeptSort=deptList.get(i-isUp).getDeptSort();
    			 deptMap.put("deptId", firstdeptId);
            	 deptMap.put("deptSort", firstDeptSort);
            	 deptListService.updateDeptSort(deptMap);  
          		 deptMap.clear(); 
          		 deptMap.put("deptId", deptList.get(i-isUp).getDeptId());
           	     deptMap.put("deptSort", secondDeptsort);
           	     deptListService.updateDeptSort(deptMap);
                addSystemLog(5);
                }
        }
    	 
    }

    /**
     * 校验机构是否已经删除
     * */
    public void checkDeptIsExist(HttpServletRequest request){
        String deptId = this.getParameter("deptId");
        if(!StringUtil.isNullOrEmpty(deptId)){
            dept = deptListService.getDeptByDeptId(Integer.parseInt(deptId.toString()));
            if(dept!=null&&dept.getDeptId()!=0){
                renderText(true,"deptBelong","");
            }
        }
    }

    /**
     * 查询出每个部门中排序最上端的部门
     * @return
     */
    private Map<String, String> getTopDeptIdMap(){
        List<PmsDept> topList =deptListService.getTopDeptList();
        Map<String, String> topDeptIdMap = new HashMap<String, String>();
        for(PmsDept dept : topList){
            topDeptIdMap.put(dept.getDeptId().toString(),dept.getDeptId().toString());
        }
        return topDeptIdMap;
    }

    /**
     * 查询出每个部门中排序下端的部门
     * @return
     */
    private Map<String, String> getBottomDeptIdMap(){
        List<PmsDept> bottomList =deptListService.getBottomDeptList();
        Map<String,String> bottomDeptIdMap = new HashMap<String, String>();
        for(PmsDept dept : bottomList){
            bottomDeptIdMap.put(dept.getDeptId().toString(),dept.getDeptId().toString());
        }
        return bottomDeptIdMap;
    }

    public void setSystemModuleImpl(ISystemModule systemModuleImpl) {
        systemModuleImpl = systemModuleImpl;
    }

    /**
     * 添加系统日志
     *
     * @param type
     */
    private void addSystemLog(int type) {
        String opeVentAction = null;
        switch (type) {
            case 1:
                opeVentAction = "新建机构";
                break;
            case 2:
                opeVentAction = "修改机构";
                break;
            case 3:
                opeVentAction = "删除机构";
                break;
            case 4:
                opeVentAction = "机构上移";
                break;
            case 5:
                opeVentAction = "机构下移";
                break;
            default:
                break;
        }
        // 新增系统日志
        // insert系统日志表   模块名称、操作对象名称、操作者userId、操作者ip地址
        systemModuleImpl.addSysOpeventLog("权限模块", opeVentAction, getLoginInfo().getUserId(), getLoginInfo().getIp());
    }
}
