package banger.domain.customer;

import banger.framework.codetable.CodeTable;
import banger.framework.util.IdCardUtil;
import banger.framework.util.SensitiveUtil;
import org.apache.commons.lang.StringUtils;

/**
 * Created by zhusw on 2017/7/13.
 */
public class CustBasicInfoQueryForMap extends CustBasicInfo {
    private String tagLatitude;
    private String tagLongitude;
    private String columnName;

    public String getTagLatitude() {
        return tagLatitude;
    }

    public void setTagLatitude(String tagLatitude) {
        this.tagLatitude = tagLatitude;
    }

    public String getTagLongitude() {
        return tagLongitude;
    }

    public void setTagLongitude(String tagLongitude) {
        this.tagLongitude = tagLongitude;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
