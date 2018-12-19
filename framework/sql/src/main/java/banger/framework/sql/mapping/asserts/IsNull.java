package banger.framework.sql.mapping.asserts;

import banger.framework.sql.dialect.IDialect;
import banger.framework.sql.mapping.ISqlClauseAssert;

/**
 * �ж�Ϊ��
 */
public class IsNull implements ISqlClauseAssert {

	@Override
	public String getName() {
		return "isNull";
	}

	@Override
	public boolean invoke(IDialect dialect, Object... args) {
		if(args.length==0)return true;
		return args[0]==null;
	}

}
