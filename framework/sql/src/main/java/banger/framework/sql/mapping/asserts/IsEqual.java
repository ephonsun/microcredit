package banger.framework.sql.mapping.asserts;

import banger.framework.sql.SqlMapException;
import banger.framework.sql.dialect.IDialect;
import banger.framework.sql.mapping.ISqlClauseAssert;
import banger.framework.util.StringUtil;

/**
 * 判断是否相等 例如 assert="isEqual([propertyName],'1')" 或  assert="isEqual([propertyName],'a','b','c')"
 */
public class IsEqual implements ISqlClauseAssert {

	@Override
	public String getName() {
		return "isEqual";
	}

	@Override
	public boolean invoke(IDialect dialect,Object... args) {
		if(args.length>1)
		{
			if(args[0]==null){
				for(int i=1;i<args.length;i++){
					if(args[i]==null)return true;
				}
			}else{
				for(int i=1;i<args.length;i++){
					if(args[0].equals(args[i]))return true;
				}
			}
			return false;
		}
		else throw new SqlMapException(StringUtil.format("断言 {0} 缺少参数  {1}",this.getName(),args.length));
	}
	
}
