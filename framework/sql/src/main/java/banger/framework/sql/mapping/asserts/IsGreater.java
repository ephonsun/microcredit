package banger.framework.sql.mapping.asserts;

import banger.framework.sql.SqlMapException;
import banger.framework.sql.dialect.IDialect;
import banger.framework.sql.mapping.ISqlClauseAssert;
import banger.framework.util.StringUtil;
import banger.framework.util.TypeUtil;

/**
 * 判断是否大于
 */
public class IsGreater implements ISqlClauseAssert {

	@Override
	public String getName() {
		return "isGreater";
	}

	@Override
	public boolean invoke(IDialect dialect,Object... args) {
		if(args.length>1)
		{
			Object arg1 = args[0];
			Object arg2 = args[1];
			return TypeUtil.compareTo(arg1, arg2)==1;
		}
		else throw new SqlMapException(StringUtil.format("断言 {0} 缺少参数  {1}",this.getName(),args.length));
	}

}
