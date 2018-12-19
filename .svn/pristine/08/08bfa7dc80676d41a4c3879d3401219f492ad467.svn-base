package banger.framework.sql.mapping.asserts;

import banger.framework.sql.SqlMapException;
import banger.framework.sql.dialect.IDialect;
import banger.framework.sql.mapping.ISqlClauseAssert;
import banger.framework.util.StringUtil;

/**
 * 判断是否小于
 */
public class IsLess implements ISqlClauseAssert {

	@Override
	public String getName() {
		return "isLess";
	}

	@Override
	public boolean invoke(IDialect dialect,Object... args) {
		if(args.length>1)
		{
			return false;
		}
		else throw new SqlMapException(StringUtil.format("断言 {0} 缺少参数  {1}",this.getName(),args.length));
	}

}
