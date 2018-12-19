package banger.framework.component.autoimport;

/**
 * Created by zhusw on 2017/9/27.
 */
public interface IDataConstraint {

    /**
     * 数据验证
     * @param data
     * @param propertyInfos
     * @return 返回的数组是空或空字符串，则通过，否则为不通过
     */
    String[] valid(Object data, IPropertyInfo ... propertyInfos);

}
