package banger.framework.component.autoimport.reader;

import banger.framework.component.autoimport.IRecordReader;
import banger.framework.sql.command.IDataReader;

/**
 * Created by zhusw on 2017/10/9.
 */
public class DataBaseReader implements IRecordReader {
    private int rownum;
    private IDataReader reader;

    public DataBaseReader(IDataReader reader){
        this.rownum = -1;
    }

    public Object getData() {
        return reader;
    }

    public int rownum() {
        return 0;
    }

    public void next() {

    }

}
