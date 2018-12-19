/*

 * banger Inc.
 * Copyright (c) 2009-2012 All Rights Reserved.
 * ToDo       :This is Class Description...
 * Author     :yuanme
 * Create Date:2012-7-19
 */
package banger.framework.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * @author yuanme
 * @version JsonDateValueProcessor.java,v 0.1 2012-7-19 下午5:43:50
 */
public class JsonDateValueProcessor implements JsonValueProcessor {

    /**
     * datePattern
     */
    private String datePattern = "yyyy-MM-dd HH:mm:ss";

    /**
     * JsonDateValueProcessor
     */
    public JsonDateValueProcessor() {
        super();
    }

    /**
     * @param format
     */
    public JsonDateValueProcessor(String format) {
        super();
        this.datePattern = format;
    }

    /**
     * @param value
     * @param jsonConfig
     * @return Object
     */
    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        return process(value);
    }

    /**
     * @param key
     * @param value
     * @param jsonConfig
     * @return Object
     */
    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
        return process(value);
    }

    /**
     * process
     * @param value
     * @return
     */
    private Object process(Object value) {
        try {
            if (value instanceof Date) {
                SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
                return sdf.format((Date) value);
            }
          return value==null ? "": value.toString();
        } catch (Exception e) {
            return "";
        }

    }

    /**
     * @return the datePattern
     */
    public String getDatePattern() {
        return datePattern;
    }

    /**
     * @param pDatePattern the datePattern to set
     */
    public void setDatePattern(String pDatePattern) {
        datePattern = pDatePattern;
    }

}
