package banger.util;

import banger.domain.config.*;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.framework.collection.DataTable;
import banger.framework.util.DateUtil;
import banger.service.intf.IApplyInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class LoanExcelUtil {


    private static void setMapValue(Object data, AutoBaseField field, Map<String, Object> map) {
        AutoFieldWrapper fieldWrapper = (AutoFieldWrapper)field;
        Object value = fieldWrapper.getValue(data, true, false);
        map.put(field.getColumn(), value);
        if (value != null) {
            if (field.getType().equals("Select")) {
                value = fieldWrapper.getOptionTextByValue(String.valueOf(value));
                value = changeSelectValue(value, fieldWrapper);
                map.put(field.getColumn(), value);
            } else if (field.getType().equals("YesNo")) {
                if ("1".equals(value)) {
                    map.put(field.getColumn(), "是");
                } else {
                    map.put(field.getColumn(), "否");
                }
            } else if (field.getType().equals("Date")) {
                map.put(field.getColumn(), DateUtil.format((Date)value, DateUtil.DEFAULT_DATE_FORMAT));
            } else if (field.getType().equals("MultipleSelect")) {
                value = fieldWrapper.getOptionTextByValues(String.valueOf(value));
                value = changeSelectValue(value, fieldWrapper);
                map.put(field.getColumn(), value);
            }
        } else {
            value = "";
            if (field.getType().equals("Select")) {
                value = LoanExcelUtil.changeSelectValue(value, fieldWrapper);
            } else if (field.getType().equals("MultipleSelect")) {
                value = LoanExcelUtil.changeSelectValue(value, fieldWrapper);
            }
            map.put(field.getColumn(), value);
        }
    }

    public static Object changeSelectValue(Object value, AutoFieldWrapper fieldWrapper) {
        //原南郊定制化需求，多选下拉没选的情况显示框
//        if (value == null || "".equals(String.valueOf(value))) {
//            List<AutoBaseOption> options = fieldWrapper.getOptions();
//            if (CollectionUtils.isNotEmpty(options)) {
//                String[] s = new String[options.size()];
//                for (int i = 0; i < options.size(); i++) {
//                    s[i] = "□" + options.get(i).getName();
//                }
//                value = StringUtils.join(s," ");
//            }
//        }
        return value;
    }

    public static String[] getTableNames(ModeConfigFile configFile) {
        String dataTable = configFile.getDataTable();
        String[] tableNames = dataTable.split(",");
        return tableNames;
    }

    public static Map<String,Object> getOneMap(DataTable datatable, List<AutoBaseField> autoBaseFields) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (AutoBaseField field : autoBaseFields) {
            setMapValue(datatable.getRow(0), field, map);
        }
        return map;
    }

    public static List<Map<String,Object>> getListMaps(DataTable datatable, List<AutoBaseField> autoBaseFields) {
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        Object[] datas = (Object[]) datatable.getRows();
        if (datas != null) {
            for (Object object : datas) {
                Map<String, Object> subMap = new HashMap<String, Object>();
                for (AutoBaseField field : autoBaseFields) {
                    setMapValue(object, field, subMap);
                }
                maps.add(subMap);
            }
        }
        return maps;
    }
}
