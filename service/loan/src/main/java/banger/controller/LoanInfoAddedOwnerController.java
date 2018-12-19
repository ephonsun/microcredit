package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.loan.LoanInfoAddedClass;
import banger.domain.loan.LoanInfoAddedOwner;
import banger.service.intf.IInfoAddedClassService;
import banger.service.intf.IInfoAddedOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资料分类
 */
@Controller
@RequestMapping("/loanInfoAddedOwner")
public class LoanInfoAddedOwnerController extends BaseController {
    @Autowired
    private IInfoAddedOwnerService infoAddedOwnerService;
    @Autowired
    private IInfoAddedClassService infoAddedClassService;

    /**
     * 跳转列表页面
     *
     * @return
     */
    @RequestMapping("/getLoanInfoAddedOwnerPage")
    public String getLoanInfoAddedOwnerPage() {
        return "/loan/vm/loanInfoAddedOwner";
    }

    /**
     * 查询列表
     */
    @RequestMapping("/queryLoanInfoAddedOwnerList")
    public void queryLoanInfoAddedOwnerList() {
        List<LoanInfoAddedOwner> list = infoAddedOwnerService.queryInfoAddedOwnerOrder();

        renderText(JsonUtil.toJson(list, "ownerId", "ownerName,sortNo,isDel,isActived").toString());
    }

    /**
     * 跳转编辑页面
     *
     * @param ownerId
     * @return
     */
    @RequestMapping("/getLoanInfoAddedOwnerUpdatePage")
    public ModelAndView getLoanInfoAddedOwnerUpdatePage(@RequestParam("ownerId") Integer ownerId) {

        ModelAndView mv = new ModelAndView("/loan/vm/loanInfoAddedOwnerUpdate");
        LoanInfoAddedOwner infoAddedOwnerById = infoAddedOwnerService.getInfoAddedOwnerById(ownerId);
        mv.addObject("infoAddedOwnerById",infoAddedOwnerById);
        return mv;

    }

    /**
     * 查询列表
     * @param ownerId
     */
    @RequestMapping("/queryLoanOwnerClass")
    public void queryLoanOwnerClass(@RequestParam("ownerId") Integer ownerId){
        List<LoanInfoAddedClass> LoanInfoAddedClasses = infoAddedClassService.queryInfoAddedclassOrder(ownerId);
        renderText(JsonUtil.toJson(LoanInfoAddedClasses, "classId", "className,isActived,sortNo").toString());
    }

    /**
     * 更新资料类型
     *
     * @param ownerName
     * @param ownerId
     */
    @RequestMapping("/updateLoanInfoAddedOwner")
    public void updateLoanInfoAddedOwner(@RequestParam("ownerName") String ownerName, @RequestParam("ownerId") Integer ownerId,@RequestParam("isActived") Integer isActived) {
        LoanInfoAddedOwner infoAddedOwnerById = infoAddedOwnerService.getInfoAddedOwnerById(ownerId);

        Integer loginUserId = getLoginInfo().getUserId();
        infoAddedOwnerById.setIsActived(isActived);
        infoAddedOwnerById.setOwnerName(ownerName);

        infoAddedOwnerService.updateInfoAddedOwner(infoAddedOwnerById, loginUserId);
    }

    /**
     * 新增时校验名字
     */
    @RequestMapping("/checkName")
    public void checkName(@RequestParam("ownerName") String ownerName) {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("ownerName", ownerName);
        condition.put("isDel",0);
        List<LoanInfoAddedOwner> list = infoAddedOwnerService.queryInfoAddedOwnerList(condition);
        if (list.size() > 0) {
            renderText(false, "", null);
        } else {
            renderText(true, "", null);
        }
    }

    /**
     * 编辑时时校验名字
     */
    @RequestMapping("/checkNameEdit")
    public void checkNameEdit(@RequestParam("ownerName") String ownerName,@RequestParam("ownerId") Integer ownerId) {
        LoanInfoAddedOwner infoAddedOwnerById = infoAddedOwnerService.getInfoAddedOwnerById(ownerId);
        if (null != infoAddedOwnerById && ownerName.equals(infoAddedOwnerById.getOwnerName())) {
            renderText(true, "", null);
        }
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("ownerName", ownerName);
        condition.put("isDel",0);
        List<LoanInfoAddedOwner> list = infoAddedOwnerService.queryInfoAddedOwnerList(condition);
        // 如果数据库中存在相同的名字
        if (list.size() > 0) {
            renderText(false, "", null);
        } else {
            renderText(true, "", null);
        }
    }

    /**
     * 启用
     */
    @RequestMapping("/enable")
    public void enable(@RequestParam("ownerId") Integer ownerId) {
        LoanInfoAddedOwner infoAddedOwnerById = infoAddedOwnerService.getInfoAddedOwnerById(ownerId);
        infoAddedOwnerById.setIsActived(1);
        Integer loginUserId = getLoginInfo().getUserId();
        infoAddedOwnerService.updateInfoAddedOwner(infoAddedOwnerById, loginUserId);
    }

    /**
     * 禁用
     */
    @RequestMapping("/disable")
    public void disable(@RequestParam("ownerId") Integer ownerId) {
        LoanInfoAddedOwner infoAddedOwnerById = infoAddedOwnerService.getInfoAddedOwnerById(ownerId);
        infoAddedOwnerById.setIsActived(0);
        Integer loginUserId = getLoginInfo().getUserId();
        infoAddedOwnerService.updateInfoAddedOwner(infoAddedOwnerById, loginUserId);
    }

    /**
     * 伪删
     *
     * @param ownerId
     */
    @RequestMapping("/delete")
    public void delete(@RequestParam("ownerId") Integer ownerId) {
        LoanInfoAddedOwner infoAddedOwnerById = infoAddedOwnerService.getInfoAddedOwnerById(ownerId);
        infoAddedOwnerById.setIsDel(1);
        Integer loginUserId = getLoginInfo().getUserId();
        infoAddedOwnerService.updateInfoAddedOwner(infoAddedOwnerById, loginUserId);
        //删除子类
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("ownerId",ownerId);
        List<LoanInfoAddedClass> loanInfoAddedClasses = infoAddedClassService.queryInfoAddedClassList(condition);
        for (int i = 0; i < loanInfoAddedClasses.size(); i++) {
            LoanInfoAddedClass loanInfoAddedClass = loanInfoAddedClasses.get(i);
            loanInfoAddedClass.setIsDel(1);
            infoAddedClassService.updateInfoAddedClass(loanInfoAddedClass,loginUserId);
        }
    }

    /**
     * 跳转新增页面
     *
     * @return
     */
    @RequestMapping("/getAddLoanInfoAddedOwnerPage")
    public String getAddLoanInfoAddedOwnerPage() {
        return "/loan/vm/loanInfoAddedOwnerSave";
    }

    /**
     * 验证ownerName唯一性
     */

    /**
     * 新增分类资料
     */
    @RequestMapping("/saveAddLoanInfoAddedOwner")
    public void saveAddLoanInfoAddedOwner(@RequestParam("ownerName") String ownerName, @RequestParam("classNames") String classNames, @RequestParam("isActived") Integer isActived) {
        Integer loginUserId = getLoginInfo().getUserId();
        //获取排序号
        Integer maxSortNum = infoAddedOwnerService.getMaxSortNum();
        if(null == maxSortNum){
            maxSortNum = 0;
        }
        //保存分类
        LoanInfoAddedOwner loanInfoAddedOwner = new LoanInfoAddedOwner();
        loanInfoAddedOwner.setIsActived(isActived);
        loanInfoAddedOwner.setIsDel(0);
        loanInfoAddedOwner.setSortNo(maxSortNum + 1);
        loanInfoAddedOwner.setOwnerName(ownerName);
        infoAddedOwnerService.insertInfoAddedOwner(loanInfoAddedOwner, loginUserId);

        //获取ownerId
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("ownerName", ownerName);
        List<LoanInfoAddedOwner> list = infoAddedOwnerService.queryInfoAddedOwnerList(condition);
        if (null != list) {
            Integer ownerId = list.get(0).getOwnerId();
            //保存子类
            String[] classNamsArr = classNames.split(",");
            for (int i = 0; i < classNamsArr.length; i++) {
                LoanInfoAddedClass loanInfoAddedClass = new LoanInfoAddedClass();
                String className = classNamsArr[i];

                loanInfoAddedClass.setIsDel(0);
                loanInfoAddedClass.setClassName(className);
                loanInfoAddedClass.setIsActived(1);
                loanInfoAddedClass.setOwnerId(ownerId);
                loanInfoAddedClass.setSortNo(i+1);

                infoAddedClassService.insertInfoAddedClass(loanInfoAddedClass,loginUserId);
            }
        }
    }

    /**
     * 上移下移
     */
    @RequestMapping("/moveOwnerOrderNo")
    @ResponseBody
    public void moveOwnerOrderNo(@RequestParam("ownerId") Integer ownerId, @RequestParam("type") String type){
        infoAddedOwnerService.moveOwnerOrderNo(ownerId,type);
        this.renderText(SUCCESS);
    }

    /**
     * 提交保存
     */
    @RequestMapping("saveOwnerCalss")
    public void saveOwnerCalss(@RequestParam("ownerId") Integer ownerId,@RequestParam("ownerName") String ownerName,@RequestParam("isActived") Integer isActived){

    }
}
