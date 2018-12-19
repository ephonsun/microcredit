package banger.controller;

import banger.common.BaseController;
import banger.common.constant.DateFormatConst;
import banger.common.interceptor.NoLoginInterceptor;
import banger.domain.loan.LoanActionHistory;
import banger.framework.util.StringUtil;
import banger.moduleIntf.ILoanModule;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.util.AppJsonUtil;
import net.sf.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 贷款历史
 * Created by zhusw on 2017/6/12.
 */
@RequestMapping("/api")
@Controller
public class AppLoanOperationHistoryController extends BaseController {
    public static final String RESULT_LOAN_HISTORY_LIST_PARAMS = "id,loanId,actionName,actionContent,actionDate,actionUserName";

    @Resource
    private ILoanModule loanModule;

    /**
     * 贷款历史列表
     * @param request
     * @param response
     */
    @NoLoginInterceptor
    @RequestMapping("/v1/queryLoanOperationList")
    public ResponseEntity<String> queryLoanOperationList(HttpServletRequest request, HttpServletResponse response){
        String loanId = this.getParameter("loanId");
        if(StringUtil.isNotEmpty(loanId)) {
            List<LoanActionHistory> list = loanModule.getLoanHistoryProvider().queryHistoryListByLoanId(Integer.parseInt(loanId));
            JSONArray resArray = AppJsonUtil.listToArray(list, RESULT_LOAN_HISTORY_LIST_PARAMS, DateFormatConst.DEFAULT_DATE_TIME_FORMAT);
            return new ResponseEntity<String>(AppJsonResponse.success(resArray), HttpStatus.OK);
        }else {
            return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
        }
    }

}
