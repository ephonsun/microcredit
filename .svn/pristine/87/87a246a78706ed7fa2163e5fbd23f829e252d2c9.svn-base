package banger.common.tools;

import banger.domain.config.AutoTableColumn;
import banger.domain.config.AutoTableInfo;
import banger.domain.config.ModeTemplateField;
import banger.domain.loan.LoanScoreDetailInfo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelReportUtil {

    public static Map<String, String> getTableMap(List<AutoTableInfo> tables) {
        Map<String, String> tableMap = new HashMap<String, String>();
        for (AutoTableInfo tableInfo : tables) {
            tableMap.put(tableInfo.getTableComment(), tableInfo.getTableDbName());
            tableMap.put(tableInfo.getTableDisplayName(), tableInfo.getTableDbName());
        }
        return tableMap;
    }

    public static Map<String, Map<String, String>> getTableColumnMap(List<AutoTableColumn> tableColumns) {
        Map<String, Map<String, String>> columnMap = new HashMap<String, Map<String, String>>();
        for (AutoTableColumn tableColumn : tableColumns) {
            if (!columnMap.containsKey(tableColumn.getTableName())) {
                Map<String, String> subMap = new HashMap<String, String>();
                subMap.put(tableColumn.getFieldColumnDisplay(), tableColumn.getFieldColumn());
                columnMap.put(tableColumn.getTableName(), subMap);
            } else {
                columnMap.get(tableColumn.getTableName()).put(tableColumn.getFieldColumnDisplay(), tableColumn.getFieldColumn());
            }
        }
        return columnMap;
    }

    public static Map<String, List<String>> getScoreMap(List<ModeTemplateField> modeTemplateFields) {
        Map<String, List<String>> scoreMap = new HashMap<String, List<String>>();
        for (ModeTemplateField modeTemplateField : modeTemplateFields) {
            if (!scoreMap.containsKey(modeTemplateField.getFieldDisplay())) {
                List<String> columns = new ArrayList<String>();
                columns.add(modeTemplateField.getTalbeColumn());
                scoreMap.put(modeTemplateField.getFieldDisplay(), columns);
            } else {
                scoreMap.get(modeTemplateField.getFieldDisplay()).add(modeTemplateField.getTalbeColumn());
            }
        }
        return scoreMap;
    }


    /**
     * 获取合并单元格的值
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public static String getMergedRegionValue(XSSFSheet sheet, int row, int column){
        int sheetMergeCount = sheet.getNumMergedRegions();
        for(int i = 0 ; i < sheetMergeCount ; i++){
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if(row >= firstRow && row <= lastRow){

                if(column >= firstColumn && column <= lastColumn){
                    XSSFRow fRow = sheet.getRow(firstRow);
                    XSSFCell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell) ;
                }
            }
        }

        return null ;
    }

    /**
     * 判断指定的单元格是否是合并单元格
     * @param sheet
     * @param row 行下标
     * @param column 列下标
     * @return
     */
    public static int isMergedRegion(Sheet sheet, int row, int column, boolean isRowspan) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    if (isRowspan)
                        return lastRow - firstRow + 1;
                    else
                        return lastColumn - firstColumn + 1;
                }
            }
        }
        return 0;
    }
    /**
     * 获取单元格的值
     * @param cell
     * @return
     */
    public static String getCellValue(XSSFCell cell){
        if(cell == null) return "";
        if(cell.getCellType() == Cell.CELL_TYPE_STRING){
            return cell.getStringCellValue();
        }else if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
            return String.valueOf(cell.getBooleanCellValue());
        }else if(cell.getCellType() == Cell.CELL_TYPE_FORMULA){
            return String.valueOf(cell.getCellFormula()) ;
        }else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            return String.valueOf(cell.getNumericCellValue());
        }
        return "";
    }

    public static Map<String,Object> getLoanScoreMap(List<LoanScoreDetailInfo> scoreInfos) {
        Map<String, Object> scoreMap = new HashMap<String, Object>();
        for (LoanScoreDetailInfo scoreInfo : scoreInfos){
            scoreMap.put(scoreInfo.getFieldName(), scoreInfo.getFieldScore());
        }
        return scoreMap;
    }

    public static Map<String,String> getLoanInfoMap() {
        Map<String, String> loanInfoMap = new HashMap<String, String>();
        loanInfoMap.put("贷款评分", "modelScore");
        loanInfoMap.put("客户经理", "belongUserName");
        loanInfoMap.put("贷款类型", "loanTypeName");
        loanInfoMap.put("归属团队", "teamName");
        return loanInfoMap;
    }
}
