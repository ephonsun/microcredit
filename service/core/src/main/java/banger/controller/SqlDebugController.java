package banger.controller;

import banger.common.BaseController;
import banger.common.annotation.NoFilterAnnotation;
import banger.framework.collection.DataTable;
import banger.framework.sql.util.ISqlHelper;
import banger.framework.sql.util.SqlHelper;
import banger.framework.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* 系统调试页面访问类
*/
@Controller
@RequestMapping("/monitor")
public class SqlDebugController extends BaseController {

	private static final long serialVersionUID = -175716292650653895L;


	@RequestMapping("/getSqlDebugPage")
	public String getSqlDebugPage(){
		return "system/vm/sqlDebug";
	}

	/**
	 * sqlDebug页面
	 * @return
	 */
	@RequestMapping("/getSqlResultData")
	@NoFilterAnnotation
	public String getSqlResultData(HttpServletRequest request, HttpServletResponse response){
		try{
			String sqlString = this.getParameter("sqlString");
			if(StringUtil.isNotEmpty(sqlString)){
				String type = this.getParameter("type");
				ISqlHelper ish = SqlHelper.instance();
				if(type.equals("execute")){//执行
					String[] sqls = sqlString.split(";");
					StringBuffer sb = new StringBuffer();
					for(String sql:sqls){
						if(StringUtils.isNotBlank(sql)){
							int count = ish.executeNoneQuery(sql);
							if(count == -1){
								sb.append("-1");
							}else sb.append("执行成功："+count+"条。<br/>");
						}
					}
					this.renderText(sb.toString());
				}else{//查询
					DataTable table = ish.getDataTable(sqlString);
					this.getRequest().setAttribute("table", table);
					return "system/vm/sqlResultGrid";
				}
			}
		}catch (Exception e){
			this.renderText(StringUtil.isNotEmpty(e.getMessage())?e.getMessage():e.getCause().getMessage());
		}
		return null;
	}
}
