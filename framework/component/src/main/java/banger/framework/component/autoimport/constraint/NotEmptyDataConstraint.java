package banger.framework.component.autoimport.constraint;

import banger.framework.component.autoimport.IDataConstraint;
import banger.framework.component.autoimport.IPropertyInfo;
import banger.framework.reader.Reader;
import banger.framework.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 非空效验
 * Created by zhusw on 2017/9/27.
 */
public class NotEmptyDataConstraint extends AbstractDataConstraint {

    @Override
    public String[] valid(Object data, IPropertyInfo ... propertyInfos) {

        List<String> messageList = new ArrayList<String>();
        for(IPropertyInfo propertyInfo : propertyInfos){
            String propertyName = propertyInfo!=null?propertyInfo.getPropertyName():"";
            String displayName = propertyInfo!=null?propertyInfo.getPropertyDisplay():"";
            if(StringUtil.isNotEmpty(propertyName)){
                Object val = Reader.objectReader().getValue(data,propertyName);
                if(val!=null) {
                    if ("".equals(val)) {
                        messageList.add(displayName + " 不能为空");
                    }
                }else{
                    messageList.add(displayName + " 不能为空");
                }
            }
        }

        if(messageList.size()>0){
            return messageList.toArray(new String[0]);
        }else {
            return pass;
        }
    }

}
