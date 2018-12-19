package banger.common.tools;

import banger.domain.enumerate.CodeDictEnum;
import banger.framework.codetable.CodeTable;
import banger.framework.codetable.CodeTableUtil;
import banger.framework.util.*;

/**
 * Created by zhusw on 2017/8/25.
 */
public class CodeDictUtil {

    /**
     * 把数据字典的值转化成名称
     * @param code
     * @param value
     * @return
     */
    public String getCodeDisplay(CodeDictEnum code, String value){
        if(code!=null && StringUtil.isNotEmpty(value)){
            String text = CodeTableUtil.getCodeTableValue(code.codeTable,code.params,value);
            return text;
        }
        return "";
    }

}
