package banger.controller;

import banger.common.BaseController;
import banger.domain.loan.LoanInfoAddedClass;
import banger.service.intf.IInfoAddedClassService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/loanInfoAddedClass")
public class LoanInfoAddedClassController extends BaseController {
    @Autowired
    private IInfoAddedClassService infoAddedClassService;

    /**
     * 跳转新增子类
     *
     * @return
     */
    @RequestMapping("/getLoanInfoAddedClassAddPage")
    public String getLoanInfoAddedClassAddPage(@RequestParam("ownerId") Integer ownerId) {
        setAttribute("ownerId", ownerId);
        return "/loan/vm/loanInfoAddedClassAdd";
    }

    /**
     * 页面中获取嵌入页
     * @return
     */
    @RequestMapping("/getLoanInfoAddedClassesAdd")
    public String getLoanInfoAddedClassesAdd(){
        return "/loan/vm/loanInfoAddedClassesAdd";
    }


    /**
     * 新增校验名字唯一性
     *
     * @param className
     */
    @RequestMapping("/checkClassName")
    public void checkClassName(@RequestParam("className") String className,@RequestParam("ownerId") Integer ownerId) {
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("ownerId",ownerId);
        condition.put("className",className);
        condition.put("isDel",0);
        List<LoanInfoAddedClass> list = infoAddedClassService.queryInfoAddedClassList(condition);

        if(null != list && list.size()>0){
            for (LoanInfoAddedClass loanInfoAddedClass : list) {
                if(StringUtils.equals(className,loanInfoAddedClass.getClassName())){
                    renderText(false, "", null);
                    return;
                }
            }
        }
        renderText(true, "", null);
    }

    /**
     * 校验更新名
     */
    @RequestMapping("/checkClasNameUpdate")
    public void checkClasNameUpdate(@RequestParam("classId") Integer classId,@RequestParam("className") String className){
        LoanInfoAddedClass infoAddedClassById = infoAddedClassService.getInfoAddedClassById(classId);
        if (null != infoAddedClassById && className.equals(infoAddedClassById.getClassName())) {
            renderText(true, "", null);
        }
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("className", className);
        condition.put("isDel", 0);
        condition.put("ownerId",infoAddedClassById.getOwnerId());
        List<LoanInfoAddedClass> list = infoAddedClassService.queryInfoAddedClassList(condition);
        // 如果数据库中存在相同的名字
        if (list.size() > 0) {
            renderText(false, "", null);
        } else {
            renderText(true, "", null);
        }
    }


    /**
     * 保存子类
     * @param className
     * @param ownerId
     */
    @RequestMapping("saveClass")
    public void saveClass(@RequestParam("className") String className,@RequestParam("ownerId") Integer ownerId){
        //查詢最大排序號
        Integer maxOrderNum = infoAddedClassService.queryMaxOrderNumByOwnerId(ownerId);
        if(maxOrderNum == null){
            maxOrderNum = 0;
        }
        LoanInfoAddedClass loanInfoAddedClass = new LoanInfoAddedClass();
        loanInfoAddedClass.setSortNo(maxOrderNum+1);
        loanInfoAddedClass.setOwnerId(ownerId);
        loanInfoAddedClass.setIsActived(1);
        loanInfoAddedClass.setClassName(className);
        loanInfoAddedClass.setIsDel(0);

        Integer loginUserId = getLoginInfo().getUserId();
        infoAddedClassService.insertInfoAddedClass(loanInfoAddedClass,loginUserId);
    }

    /**
     * 删除子类
     * @param classId
     */
    @RequestMapping("/deleteClass")
    public void deleteClass(@RequestParam("classId") Integer classId){
        LoanInfoAddedClass infoAddedClassById = infoAddedClassService.getInfoAddedClassById(classId);
        Integer loginUserId = getLoginInfo().getUserId();
        infoAddedClassById.setIsDel(1);
        infoAddedClassService.updateInfoAddedClass(infoAddedClassById,loginUserId);
    }

    /**
     * 跳转更新页
     */
    @RequestMapping("/getClassUpdatePage")
    public ModelAndView getClassUpdatePage(@RequestParam("classId") Integer classId){
        ModelAndView mv = new ModelAndView("/loan/vm/loanInfoAddedClassUpdate");
        LoanInfoAddedClass classById = infoAddedClassService.getInfoAddedClassById(classId);
        mv.addObject("classById",classById);
        return mv;
    }

    /**
     * 保存更新子类
     */
    @RequestMapping("/updateClass")
    public void updateClass(@RequestParam("className") String className,@RequestParam("classId") Integer classId){
        LoanInfoAddedClass infoAddedClassById = infoAddedClassService.getInfoAddedClassById(classId);
        Integer loginUserId = getLoginInfo().getUserId();
        infoAddedClassById.setClassName(className);
        infoAddedClassService.updateInfoAddedClass(infoAddedClassById,loginUserId);
    }

    /**
     * 上移下移
     * @param classId
     * @param type
     */
    @RequestMapping("/moveClassOrderNo")
    @ResponseBody
    public void moveClassOrderNo(@RequestParam("classId") Integer classId, @RequestParam("type") String type,@RequestParam("ownerId") Integer ownerId){
        infoAddedClassService.moveClassOrderNo(classId,type,ownerId);
        this.renderText(SUCCESS);
    }
}