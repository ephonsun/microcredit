package banger.framework.component.autoimport;

/**
 * Created by Administrator on 2017/9/27.
 */
public interface IPropertyInfo {

    /**
     * 得到属性名称
     * @return
     */
    String getPropertyName();

    /**
     * 得到属性
     * @return
     */
    String getPropertyDisplay();

    /**
     * 代码表参数
     * @return
     */
    Object[] getCodeTableParams();

}
