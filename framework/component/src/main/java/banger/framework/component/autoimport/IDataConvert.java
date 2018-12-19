package banger.framework.component.autoimport;

/**
 * Created by zhusw on 2017/9/25.
 */
public interface IDataConvert {

    String[] convert(Object data, IPropertyInfo ... propertyInfos);

}