package banger.service.intf;

import net.sf.json.JSONObject;

/**
 * 代款模板服务
 * @author zhusw
 *
 */
public interface IAppLoanTemplateService {

	/**
	 * 保存App提交上来的Json数据
	 * @param data
	 */
	String saveLoanTemplateInfo(JSONObject data);
	
}
