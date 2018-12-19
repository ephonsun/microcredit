package banger.framework.component.autoimport;

import banger.framework.component.dataimport.IRecordReader;

/**
 * Created by zhusw on 2017/9/28.
 */
public interface IAutoImportHandler {

    /**
     * 开始执行导入
     */
    void start();

    /**
     * 停频导入
     */
    void stop();

    /**
     * 读取记录
     * @param reader
     */
    void read(IRecordReader reader);

    /**
     * 批量提交
     */
    void batchCommit();

}
