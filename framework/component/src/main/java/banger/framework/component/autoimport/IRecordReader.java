package banger.framework.component.autoimport;

import java.util.List;

/**
 * Created by zhusw on 2017/9/28.
 */
public interface IRecordReader {

    /**
     * 得到行数据
     * @return
     */
    Object getData();

    /**
     * 行号
     * @return
     */
    int rownum();

    /**
     * 下一行
     */
    void next();

}
