package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.dao.intf.IActionHistoryDao;
import banger.domain.loan.LoanActionHistory;
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
 * Created by wanggl on 2017/6/27.
 */
@Controller
@RequestMapping("/loanActionHistory")
public class LoanActionHistoryController extends BaseController{
    @Autowired
    private IActionHistoryDao actionHistoryDao;

    /**
     * 跳转列表页
     * @param loanId
     * @return
     */
    @RequestMapping("/getLoanActionHistoryPage")
    public ModelAndView getLoanActionHistoryPage(@RequestParam("loanId") Integer loanId){
           ModelAndView mv = new ModelAndView("/loan/vm/loanActionHistory");
           mv.addObject("loanId",loanId);
           return mv;
    }

    /**
     * 查询历史记录
     * @param loanId
     */
    @RequestMapping("/queryHistory")
    @ResponseBody
    public void queryHistory(@RequestParam("loanId") Integer loanId){
        Map<String,Object> condition =new HashMap<String,Object>();
        condition.put("loanId",loanId);
        List<LoanActionHistory> loanActionHistories = actionHistoryDao.queryActionHistoryList(condition);
        String dateFormat = "yyyy-MM-dd HH:mm";
        renderText(JsonUtil.toJson(loanActionHistories, "id", "loanProcessType,actionName,actionContent,actionDate,actionUserName",dateFormat).toString());
    }
}
