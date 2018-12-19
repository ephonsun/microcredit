package banger.framework.sql.mapping.asserts;

import banger.framework.sql.dialect.IDialect;
import banger.framework.sql.mapping.ISqlClauseAssert;

/**
 * �ж�Ϊ�ջ���ַ�
 */
public class IsNullOrEmpty implements ISqlClauseAssert {

	@Override
	public String getName() {
		return "isNullOrEmpty";
	}

	@Override
	public boolean invoke(IDialect dialect, Object... args) {
		if(args.length==0)return true;
		Object arg = args[0];
		if(arg==null || "".equals(arg)){
			return true;
		}
		return false;
	}

}
