package banger.controller;

import banger.common.BaseController;
import banger.common.tools.JsonUtil;
import banger.domain.loan.LoanProcessType;
import banger.service.intf.IProcessTypeService;
import banger.service.intf.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/loanProcessType")
public class LoanProcessTypeController extends BaseController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IProcessTypeService processTypeService;
	@Autowired
	private ITypeService typeService;

	/**
	 * 跳转列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/getLoanProcessTypePage")
	public ModelAndView getLoanProcessTypePage(
			@RequestParam("typeId") Integer typeId){
		ModelAndView mv = new ModelAndView("/loan/vm/loanProcessType");
		// 获取贷款类型
		String loanTypeName = typeService.getTypeById(typeId).getLoanTypeName();
		mv.addObject("LoanType", loanTypeName);
		mv.addObject("typeId", typeId);
		return mv;
	}

	/**
	 * 查询列表
	 * 
	 * @param id
	 */
	@RequestMapping("/queryLoanProcessTypeList")
	@ResponseBody
	public void queryLoanProcessTypeList() {
		Map<String, Object> condition = new HashMap<String, Object>();
		List<LoanProcessType> loanProcessTypeList = processTypeService.queryProcessTypeList(condition);
		List<LoanProcessType> list = new ArrayList<LoanProcessType>();
		if(null != loanProcessTypeList && loanProcessTypeList.size()>0){
		for (LoanProcessType loanProcessType : loanProcessTypeList) {
				if (loanProcessType.getIsActived() == 1) {
					list.add(loanProcessType);
				}
			}
		}
		renderText(JsonUtil.toJson(list, "id", "precTypeName,precType,isActived").toString());

	}
}
