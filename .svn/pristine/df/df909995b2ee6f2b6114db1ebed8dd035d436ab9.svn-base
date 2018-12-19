package banger.util;

import banger.common.listener.SpringContextUtil;
import banger.common.tools.ExcelReportUtil;
import banger.domain.config.AutoTableColumn;
import banger.domain.config.AutoTableInfo;
import banger.domain.config.ModeTemplateField;
import banger.domain.loan.excel.ExcelTD;
import banger.domain.loan.excel.ExcelTR;
import banger.framework.spring.SpringContext;
import banger.service.intf.ITableColumnService;
import banger.service.intf.ITableInfoService;
import banger.service.intf.ITemplateFieldService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelImportUitl {

    private static Map<String, List<String>> scoreMap;
    private static Map<String, String> tableMap;
    private static Map<String, Map<String, String>> columMap;
    private static List<String> tableNames;
    private static Map<String, String> loanInfoMap;
//    private static Map<String, String> map = new HashMap<String, String>();

    public static String getRowHtmlForShow(String filePath, ITableInfoService tableInfoService, ITableColumnService tableColumnService, ITemplateFieldService templateFieldService) {
        setImportData(tableInfoService, tableColumnService, templateFieldService);
        String rowHtml = getRowHtml(filePath, true);
        clearData();
        return rowHtml;
    }

    public static String reloadVm(Integer id, String filePath, ITableInfoService tableInfoService, ITableColumnService tableColumnService, ITemplateFieldService templateFieldService) {
        setImportData(tableInfoService, tableColumnService, templateFieldService);
        String text = getRowHtml(filePath, false);
        writeVM(text, id);
        StringBuffer dataTable = new StringBuffer();
        int i = 0;
        for (String tableName : tableNames) {
            if (i != 0) {
                dataTable.append(",");
            } else {
                i++;
            }
            dataTable.append(tableName);
        }
        clearData();
        return dataTable.toString();
    }


    private static void setImportData(ITableInfoService tableInfoService, ITableColumnService tableColumnService, ITemplateFieldService templateFieldService){
        List<AutoTableInfo> tables = tableInfoService.queryAllTableInfoList();
        List<AutoTableColumn> tableColumns = tableColumnService.getAllTableColumnList();
        List<ModeTemplateField> modeTemplateFields = templateFieldService.queryTemplateFieldNames();
        tableMap = ExcelReportUtil.getTableMap(tables);
        columMap = ExcelReportUtil.getTableColumnMap(tableColumns);
        scoreMap = ExcelReportUtil.getScoreMap(modeTemplateFields);
        loanInfoMap = ExcelReportUtil.getLoanInfoMap();
        tables.clear();
        tableColumns.clear();
        modeTemplateFields.clear();
    }
    private static void clearData(){
        loanInfoMap.clear();
        tableMap.clear();
        columMap.clear();
        scoreMap.clear();
        tableNames.clear();
    }

    private static String getRowHtml(String path, boolean forShow) {
        StringBuffer text = new StringBuffer();
        //整体样式
        text.append("<style>\r");
        text.append(".reportTable .b{border:1px solid #333;}\r");
        for (int i = 11; i < 30; i++) {
            text.append(".reportTable .f");
            text.append(i);
            text.append("{font-size:");
            text.append(i);
            text.append("px; }\r");
        }
        text.append(".reportTable .l{text-align:LEFT;}\r");
        text.append(".reportTable .c{text-align:CENTER;}\r");
        text.append(".reportTable .r{text-align:RIGHT;}\r");
        text.append(".reportTable td{padding: 5px;}\r");
        text.append(".reportTable .w{font-weight:600;}\r");
        text.append("</style>\r");
        //遍历sheet
        for (int i = 0; i < 10; i++) {
            List<ExcelTR> rows = readExcelToObj(path, i);
            if (rows!=null && rows.size()>0) {
                text.append("<div style=\"overflow: auto;    padding: 5px;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"reportTable b\" >\r");
                for (ExcelTR row : rows) {
                    if (!forShow) {
                    }
                    text.append("<tr style=\" ");
                    text.append("height:");
                    text.append(row.getHeight());
                    text.append("px;\">");
                    for (ExcelTD excelTD : row.getExcelTDS()) {
                        String vm = "";
                        String cellName = excelTD.getCellName();
                        if (cellName.contains("[") || cellName.contains("<")) {
                            vm = getVm(cellName);
                        }
                        text.append("<td class=\"b ");
                        text.append("f");
                        text.append(excelTD.getSize());
                        if (excelTD.isBold()) {
                            text.append(" w ");
                        }
                        String algin = excelTD.getAlgin();
                        if (StringUtils.isNotBlank(algin)) {
                            text.append(" ");
                            algin = algin.substring(0, 1).toLowerCase();
                            text.append(algin);
                        }

                        text.append("\" ");
                        text.append(" style=\"");
                        int width = excelTD.getWidth();
                        if (width != 0) {
                            text.append(" width:");
                            text.append(excelTD.getWidth());
                            text.append("px;");
                        }
                        if (forShow) {
                            if (cellName.contains("[") || cellName.contains("<")) {
                                if (StringUtils.isNotBlank(vm)) {
                                    text.append("color:green;");
                                } else {
                                    text.append("color:red;");
                                }
                            }
                        }
                        text.append("\"");
                        if (excelTD.getColspan() > 1) {
                            text.append(" colspan=\"");
                            text.append(excelTD.getColspan());
                            text.append("\"");
                        }
                        if (excelTD.getRowspan() > 1) {
                            text.append(" rowspan=\"");
                            text.append(excelTD.getRowspan());
                            text.append("\"");
                        }
                        text.append(" >");

                        if (!forShow) {
                            if (cellName.contains("[") || cellName.contains("<")) {
                                text.append(vm);
                            } else {
                                text.append(excelTD.getCellName());
                            }
                        } else {
                            if (!cellName.contains("[") && !cellName.contains("<")) {
                                text.append(excelTD.getCellName());
                            } else {
                                if (StringUtils.isNotBlank(vm)) {
                                    text.append(excelTD.getCellName());
                                } else {
                                    text.append("【未匹配到数据】");
                                }
                            }
                        }
                        text.append("</td>\r");
                    }
                    text.append("</tr>\r");
                }
                text.append("</table></div>");
            }

        }
        return text.toString();
    }

    private static String getVm(String cellName){
        String type = "";
        String index = "";
        if (cellName.contains("<")) {
            type = "list";
        }
        cellName = cellName.replace("[", "").replace("]", "")
                .replace("<", "").replace(">", "");

        if (loanInfoMap.containsKey(cellName)){
            return "$!loanApplyInfo." + loanInfoMap.get(cellName);
        } else if (cellName.indexOf("评分项") == 0){
            int indexo = cellName.indexOf(".");
            int indexo2 = cellName.indexOf("-");
            if (indexo < 1  && indexo2 < 1) return "";
            cellName = cellName.substring(indexo+1, cellName.length());
            if (!scoreMap.containsKey(cellName)) return "";
            List<String> keys = scoreMap.get(cellName);
            StringBuffer vm = new StringBuffer();
            for (String key : keys) {
                vm.append("$!").append(key);
            }
            return vm.toString();
        }
        int indexo = cellName.indexOf(".");
        int indexo2 = cellName.indexOf("-");
        if (indexo < 1  && indexo2 < 1) return "";
        indexo = indexo < 1 ? indexo2 : indexo;
        String rowName = cellName.substring(0, indexo);
        String tableName = tableMap.get(rowName);
        if (tableNames == null)
            tableNames = new ArrayList<String>();
        if (!tableNames.contains(tableName)) {
            tableNames.add(tableName);
        }
        cellName = cellName.substring(indexo+1, cellName.length());
        if ("list".equals(type)) {
            index = cellName.substring(cellName.length() - 1, cellName.length());
            cellName = cellName.substring(0, cellName.length() -1);
        }
        Map<String, String> colum = columMap.get(tableName);
        if (colum == null)
            return "";

        if (colum.get(cellName) == null )
            return "";
        return "$!{" + tableName + index + "." + colum.get(cellName) + "}";
    }


    private static void writeVM(String text, Integer id) {
        try {
            String path = getClassPath();
            path = path.replace("//","/");
            path = path.replace("\\","/");
            path = path.replace("/WEB-INF/classes/", "/loan/vm/investigate/investigateReport" +id+ ".vm");
            String regx = "^[a-zA-Z]{1}:.*";
            if (!path.matches(regx)) {
                path = "/" + path;
            }
            System.out.println(path);
            File fileCopy = new File(path);
            if (!fileCopy.exists()) {
                fileCopy.createNewFile();
            }
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileCopy),"UTF-8")));
            out.write(text);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getClassPath() {
        String path = Thread.currentThread().getContextClassLoader() .getResource("").toString();
        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String temp = path.replaceFirst("file:/", "");
        String separator = System.getProperty("file.separator");
        String resultPath = temp.replaceAll("/", separator + separator);
        return resultPath;
    }


    /**
     * 读取excel数据
     */
    private static List<ExcelTR> readExcelToObj(String path, int sheetIndex) {
        List<ExcelTR> rows = new ArrayList<ExcelTR>();
        try {
            File file = new File(path);
            InputStream inputStream = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(inputStream);
            readExcel(rows, wb, sheetIndex, 0);
        } catch (FileNotFoundException e) {
            System.out.println("未找到上传的文件:" + path);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rows;
    }

    /**
     * 读取excel文件
     * @param rows
     * @param wb
     * @param sheetIndex sheet页下标：从0开始
     * @param startReadLine 开始读取的行:从0开始
     */
    private static void readExcel(List<ExcelTR> rows, XSSFWorkbook wb, int sheetIndex, int startReadLine) {
        int sheetSize = wb.getNumberOfSheets();
        if (sheetSize <= sheetIndex) return;
        XSSFSheet sheet = wb.getSheetAt(sheetIndex);
        XSSFRow row = null;
        int limit = 10;
        int endrow = 0;
        int endcell = 0;
        List<String> list = new ArrayList<String>();
        for(int i=startReadLine; i<sheet.getLastRowNum() + 1; i++) {
            if (endrow != 0) break;
            List<ExcelTD> excelTDS = new ArrayList<ExcelTD>();
            row = sheet.getRow(i);
            if (row == null) break;
            int height = row.getHeight()/14;
            for (int j = 0; j < limit; j++) {
                if (endcell != 0 && j == endcell) break;
                if (list.contains(i + "" + j)) continue;
                int width = sheet.getColumnWidth(j)/17;
                XSSFCell cell = row.getCell(j);

                if (cell != null) {
                    int rowspan = ExcelReportUtil.isMergedRegion(sheet, i, j, true);
                    int colspan = ExcelReportUtil.isMergedRegion(sheet, i, j, false);
                    for (int k = 0; k < rowspan; k++) {
                        for (int l = 0; l < colspan; l++) {
                            if (k == 0) {
                                width += sheet.getColumnWidth(j + l)/17;
                            }
                            list.add((i + k) + "" + (j+l));
                        }
                    }
                    String cellName = "";
                    if (rowspan > 1 || colspan > 1 ) {
                        cellName = ExcelReportUtil.getMergedRegionValue(sheet, i, j );
                    } else {
                        cellName = ExcelReportUtil.getCellValue(cell);
                    }
                    if ("完".equals(cellName)) {
                        endcell = j;
                        break;
                    }
                    excelTDS.add(getExcelCell(width, cellName, cell, wb, rowspan, colspan));
                }
            }
            ExcelTR excelRow = new ExcelTR(height, excelTDS);
            rows.add(excelRow);
        }
    }

    private static ExcelTD getExcelCell(int width, String cellValue, XSSFCell cell, XSSFWorkbook wb, int rowspan, int colspan){
        ExcelTD excelTD = new ExcelTD();
        excelTD.setRowspan(rowspan);
        excelTD.setColspan(colspan);
        if (colspan <10) {
            excelTD.setWidth(width);
        } else {
            excelTD.setWidth(0);
        }
        excelTD.setCellName(cellValue);
        XSSFCellStyle style = cell.getCellStyle();
        if (style != null) {
            HorizontalAlignment alignment = style.getAlignmentEnum();
            excelTD.setAlgin(alignment.toString());
            XSSFFont font = style.getFont();
            if (font.getBoldweight() ==XSSFFont.BOLDWEIGHT_BOLD)
                excelTD.setBold();
            excelTD.setSize(font.getFontHeightInPoints());
        }
        return excelTD;
    }
}
