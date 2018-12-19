package banger.moduleIntf;

import banger.domain.loan.LoanIndustryGuidelines;

import java.util.List;
import java.util.Map;

/**
 * @Author: yangdw
 * @Description:
 * @Date: Created in 10:26 2017/9/11.
 */
public interface IIndustryGuidelinesProvider {
	List<LoanIndustryGuidelines> getIndustryGuidelinesByGroup(Map<String, Object> map);
}
