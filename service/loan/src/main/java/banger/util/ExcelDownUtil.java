package banger.util;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import banger.common.listener.SpringContextUtil;
import banger.common.tools.ExcelReportUtil;
import banger.domain.config.*;
import banger.domain.enumerate.LoanAuditResultEnum;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.enumerate.TableInputType;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanApplyInfo_Web_Query;
import banger.domain.loan.LoanCurrentAuditStatus;
import banger.domain.loan.LoanScoreDetailInfo;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.util.DateUtil;
import banger.framework.util.FileUtil;
import banger.moduleIntf.IAutoFieldProvider;
import banger.moduleIntf.IConfigModule;
import banger.moduleIntf.ITableInfoProvider;
import banger.service.intf.IApplyInfoService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;

/**
 * Created by Administrator on 2017/9/26.
 */
public class ExcelDownUtil {

//    private static List<String> scoreNames;
    private static Map<String, String> tableMap;
    private static Map<String, Map<String, String>> columMap;
    private static Map<String, Object> investigateMap;
    private static List<Map<String, Object>> approvalMaps;
    private static Map<String, Object> scoreMap;
    private static Map<String, String> loanInfoMap;

    /**
     * 数据从第row+2行开始写入
     */
    public static void doExportFromRow(HttpServletRequest request, HttpServletResponse response, IApplyInfoService applyInfoService, IConfigModule configModule, ITableInfoProvider tableInfoProvider, IAutoFieldProvider autoFieldProvider,
                                       LoanApplyInfo_Web_Query loanApplyInfo, ModeConfigFile configFile, List<Map<String, Object>> passMaps, List<LoanScoreDetailInfo> scoreInfos)  {
        setImportData(loanApplyInfo, tableInfoProvider, autoFieldProvider, scoreInfos);
        if (configFile != null) {
            String fileName = String.format("贷款资料下载【%s】", loanApplyInfo.getLoanName());
            String path = configFile.getFilePath();
            String[] tableNames = LoanExcelUtil.getTableNames(configFile);
            List<AutoBaseTemplate> autoBaseTemplates = configModule.getAutoTemplateProvider().getTemplateListByTables(tableNames);
            for(AutoBaseTemplate template : autoBaseTemplates){
                String tableName = template.getTableName();
                String type = template.getType();
                DataTable datatable;
                if ("LOAN".equals(template.getModule()))
                    datatable = applyInfoService.getDataTableByLoanId(tableName, LoanProcessTypeEnum.INVESTIGATE.type, loanApplyInfo.getLoanId());
                else
                    datatable = applyInfoService.getDataTableByLoanId(tableName, null, loanApplyInfo.getLoanId());
                List<AutoBaseField> autoBaseFields = template.getFields();
                if (TableInputType.FORMS.type.equals(type)) {
                    Map<String, Object> map = LoanExcelUtil.getOneMap(datatable, autoBaseFields);
                    investigateMap.put(tableName, map);
                } else {
                    List<Map<String, Object>> maps = LoanExcelUtil.getListMaps(datatable, autoBaseFields);
                    for (int i = 0; i < maps.size() && i < 5; i++) {
                        investigateMap.put(tableName + (i + 1), maps.get(i));
                    }
                }
            }
            approvalMaps = passMaps;
            try {
                int max=20;
                Random random = new Random();
                int s = random.nextInt(max);
                String outFilePath = "/loan/" + System.currentTimeMillis() + s + ".xlsx";
                String realPathRoot = request.getSession().getServletContext().getRealPath("");
                realPathRoot = realPathRoot.replace('\\', '/');
                String tempPath = realPathRoot + outFilePath;
                createTempExcel(path, tempPath);
                outPutFile(fileName, tempPath, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        clearData();
    }


    private static void setImportData(LoanApplyInfo_Web_Query loanApplyInfo, ITableInfoProvider tableInfoProvider, IAutoFieldProvider autoFieldProvider, List<LoanScoreDetailInfo> scoreInfos){
        investigateMap = new HashMap<String, Object>();
        try {
            investigateMap = BeanUtils.describe(loanApplyInfo);
        } catch (IllegalAccessException e) {

        } catch (InvocationTargetException e) {e.printStackTrace();
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
//        investigateMap.put("modelScore", loanApplyInfo.getModelScore());
        List<AutoTableInfo> tables = tableInfoProvider.queryAllTableInfoList();
        List<AutoTableColumn> tableColumns = autoFieldProvider.getAllTableColumnList();
        tableMap = ExcelReportUtil.getTableMap(tables);
        columMap = ExcelReportUtil.getTableColumnMap(tableColumns);
        scoreMap = ExcelReportUtil.getLoanScoreMap(scoreInfos);
        loanInfoMap = ExcelReportUtil.getLoanInfoMap();
        tables.clear();
        tableColumns.clear();
    }
    private static void clearData(){
        loanInfoMap.clear();
        tableMap.clear();
        columMap.clear();
        scoreMap.clear();
        investigateMap.clear();
        approvalMaps.clear();
    }


    private static void createTempExcel(String path, String tempPath) throws Exception {
        File file = new File(path);
        File targetFile = new File(tempPath);
        if (!targetFile.exists())
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        if (file.exists()) {
            FileUtil.copyFile(file, targetFile);
            InputStream inputStream = new FileInputStream(targetFile);
            XSSFWorkbook wb = new XSSFWorkbook(inputStream);
            FileOutputStream des = new FileOutputStream(tempPath,false);
            setDC(wb, investigateMap, tableMap, columMap);
            setSP(wb, approvalMaps);
            des.flush();
            wb.write(des);
            if (inputStream != null) inputStream.close();
            des.close();
        } else {
            throw new Exception("调查报告未配置");
        }


    }

    private static void setDC(XSSFWorkbook wb, Map<String, Object> investigateMap, Map<String, String> tableMap, Map<String, Map<String, String>> columMap) {
        //最多读取10个sheet，读到空跳出循环
        for (int index = 0; index < 10; index++) {
            int sheetSize = wb.getNumberOfSheets();
            if (sheetSize <= index) break;
            XSSFSheet sheet = wb.getSheetAt(index);
            XSSFRow row = null;
            int limit = 10;
//        int allRowNum = sheet.getLastRowNum();
            int endrow = 0;
            int endcell = 0;
            List<String> list = new ArrayList<String>();
            for(int i=0; i<sheet.getLastRowNum() + 1; i++) {
                if (endrow != 0) break;
                row = sheet.getRow(i);
                if (row == null) break;
                for (int j = 0; j < limit; j++) {
                    if (endcell != 0 && j == endcell) break;
                    if (list.contains(i + "" + j)) continue;
                    XSSFCell cell = row.getCell(j);
                    if (cell != null) {
                        int rowspan = ExcelReportUtil.isMergedRegion(sheet, i, j, true);
                        int colspan = ExcelReportUtil.isMergedRegion(sheet, i, j, false);
                        for (int k = 0; k < rowspan; k++) {
                            for (int l = 0; l < colspan; l++) {
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
                        if (cellName.contains("<")) {
                            String value = "";
                            cellName = cellName.replace("<","").replace(">", "");

                            int indexo = cellName.indexOf(".");

                            int indexo2 = cellName.indexOf("-");
                            if (indexo < 1  && indexo2 < 1)  {
                                cell.setCellValue("");
                                continue;
                            }
                            indexo = indexo < 1 ? indexo2 : indexo;
                            String rowName = cellName.substring(0, indexo);
                            cellName = cellName.substring(indexo+1, cellName.length());

                            String table = tableMap.get(rowName);
                            String d = cellName.substring(cellName.length() - 1, cellName.length());
                            cellName = cellName.substring(0, cellName.length() -1);
                            Map<String, String> colum = columMap.get(table);
                            Map<String, Object> map = (Map<String, Object>) investigateMap.get(table + d);
                            if (colum == null) {
                                cell.setCellValue("");
                            } else {
                                String newKey = colum.get(cellName);
                                if (map != null) {
                                    value = map.get(newKey) == null ? "" : String.valueOf(map.get(newKey));
                                    cell.setCellType(CellType.STRING);
                                    cell.setCellValue(value);
                                } else {
                                    cell.setCellValue("");
                                }
                            }
                        } else if (cellName.contains("[")) {
                            String value = "";
                            cellName = cellName.replace("[","").replace("]", "");
                            if (loanInfoMap.containsKey(cellName)){
                                Object o = investigateMap.get(loanInfoMap.get(cellName));
                                if (o != null) {
                                    value = String.valueOf(o);
                                    cell.setCellType(CellType.STRING);
                                }
                                cell.setCellValue(value);
                            } else if (cellName.indexOf("评分项") == 0) {
                                int indexo = cellName.indexOf(".");
                                int indexo2 = cellName.indexOf("-");
                                if (indexo < 1  && indexo2 < 1) cell.setCellValue("");
                                cellName = cellName.substring(indexo+1, cellName.length());
                                if (!scoreMap.containsKey(cellName)) cell.setCellValue("");
                                Object o = scoreMap.get(cellName);
                                if (o != null) {
                                    cell.setCellType(CellType.STRING);
                                    cell.setCellValue(String.valueOf(o));
                                } else {
                                    cell.setCellValue("");
                                }

                            } else {
                                int indexo = cellName.indexOf(".");
                                int indexo2 = cellName.indexOf("-");
                                if (indexo < 1  && indexo2 < 1)  {
                                    cell.setCellValue("");
                                    continue;
                                }
                                indexo = indexo < 1 ? indexo2 : indexo;

                                String rowName = cellName.substring(0, indexo);
                                cellName = cellName.substring(indexo+1, cellName.length());
                                String table = tableMap.get(rowName);
                                Map<String, String> colum = columMap.get(table);
                                Map<String, Object> map = (Map<String, Object>) investigateMap.get(table);
                                if (colum == null) {
                                    cell.setCellValue("");
                                } else {
                                    String newKey = colum.get(cellName);
                                    if (map != null) {
                                        value = map.get(newKey) == null ? "" : String.valueOf(map.get(newKey));
                                        cell.setCellType(CellType.STRING);
                                        cell.setCellValue(value);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    private static void setSP(XSSFWorkbook wb, List<Map<String, Object>> approvalMaps) {
        XSSFSheet sheet = wb.createSheet("审批决议");
        for (int i = 0; i < 8; i++) {
            if (i%2 == 0)
                sheet.setColumnWidth(i, 200*17);
            else
                sheet.setColumnWidth(i, 300*17);
        }
        int rangeNum = 7;
        XSSFCellStyle style=wb.createCellStyle();
        style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        int moreRow = 0;
        int m = 0;
        for (int i = 0; i < 2*approvalMaps.size() + moreRow; i+=2) {
            Map<String, Object> map = approvalMaps.get(m);
            m++;
            String title = String.format("※%s审信息", (String) map.get("processName"));
            XSSFRow row = sheet.createRow(i);
            row.setHeight((short) 500);
            XSSFCell cell = row.createCell(0);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(title);
            cell.setCellStyle(style);
            CellRangeAddress newCellRangeAddress = new CellRangeAddress(i, i, 0, rangeNum);
            RegionUtil.setBorderLeft(1, newCellRangeAddress, sheet, wb);
            RegionUtil.setBorderRight(1, newCellRangeAddress, sheet, wb);
            RegionUtil.setBorderTop(1, newCellRangeAddress, sheet, wb);
            RegionUtil.setBorderBottom(1, newCellRangeAddress, sheet, wb);
            sheet.addMergedRegion(newCellRangeAddress);

            List<AutoBaseTemplate> templates = (List<AutoBaseTemplate>) map.get("templates");
            for (int j = 0; j < templates.size(); j++) {
                AutoBaseTemplate template = templates.get(j);
                Map<String, Object> attributeMap = template.getAttributeMap();
                String desc = String.format("审批人：%s 【审批通过】%s", attributeMap.get("userName"), attributeMap.get("date"));
                XSSFRow nextRow = sheet.createRow(i + j + 1);
                nextRow.setHeight((short) 400);
                XSSFCell nextCell = nextRow.createCell(0);
                nextCell.setCellValue(desc);
                nextCell.setCellStyle(style);
                CellRangeAddress newCellRangeAddress2 = new CellRangeAddress(i+ j + 1, i+ j + 1, 0, rangeNum);
                RegionUtil.setBorderLeft(1, newCellRangeAddress2, sheet, wb);
                RegionUtil.setBorderRight(1, newCellRangeAddress2, sheet, wb);
                RegionUtil.setBorderTop(1, newCellRangeAddress2, sheet, wb);
                RegionUtil.setBorderBottom(1, newCellRangeAddress2, sheet, wb);
                sheet.addMergedRegion(newCellRangeAddress2);
                Object noData = attributeMap.get("noData");
                DataRow data = (DataRow) template.getData();
                if (noData == null || !"1".equals(String.valueOf(noData))) {
                    List<AutoBaseField> fileds = template.getFields();
                    int moreRowNum = 0;
                    int colNum = 0;
                    XSSFRow newRow = sheet.createRow(i + j + 2);
                    boolean isNewRow = false;
                    moreRowNum++;
                    for (AutoBaseField baseField : fileds) {
                        AutoFieldWrapper fieldWrapper = (AutoFieldWrapper)baseField;
                        int colspan = fieldWrapper.getColSpan(2)*2-1;
                        if (isNewRow || (colNum + colspan > 8)) {
                            newRow = sheet.createRow(i + j + moreRowNum + 2);
                            isNewRow = false;
                            moreRowNum++;
                            colNum = 0;
                        }
                        XSSFCell nextCell1 = newRow.createCell(colNum);
                        nextCell1.setCellValue(fieldWrapper.getFieldName() + "：");
                        XSSFCellStyle style1 = wb.createCellStyle();
                        style1.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                        style1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直

                        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
                        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
                        style1.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
                        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
                        nextCell1.setCellStyle(style1);
                        colNum++;
                        String value = getFileValue(data, baseField) + fieldWrapper.getUnit();
                        XSSFCell nextCell2 = newRow.createCell(colNum);
                        XSSFCellStyle style2 = wb.createCellStyle();
                        style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
                        style2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直
                        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
                        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
                        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
                        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
                        nextCell2.setCellStyle(style2);
                        if (StringUtils.isNotBlank(value))
                            nextCell2.setCellValue(value);
                        else
                            nextCell2.setCellValue("");
                        colNum +=colspan;
                        if (colspan >1){
                            CellRangeAddress newCellRangeAddress3 = new CellRangeAddress(i + j + moreRowNum + 1, i + j + moreRowNum + 1, colNum - colspan, colNum - 1);
                            RegionUtil.setBorderLeft(1, newCellRangeAddress3, sheet, wb);
                            RegionUtil.setBorderRight(1, newCellRangeAddress3, sheet, wb);
                            RegionUtil.setBorderTop(1, newCellRangeAddress3, sheet, wb);
                            RegionUtil.setBorderBottom(1, newCellRangeAddress3, sheet, wb);
                            sheet.addMergedRegion(newCellRangeAddress3);
                        }
                        if (colNum >= 8) {
                            isNewRow = true;
                        }
                    }
                    moreRow += moreRowNum;
                    i += moreRowNum;
                } else {
                    if (j != 0) {
                        moreRow += 1;
                        i += 1;
                    }
                }
            }


        }
    }

    private static String getFileValue(Object data, AutoBaseField field) {
        AutoFieldWrapper fieldWrapper = (AutoFieldWrapper)field;
        Object value = fieldWrapper.getValue(data, true, false);
        if (value != null) {
            if (field.getType().equals("Select")) {
                value = fieldWrapper.getOptionTextByValue(String.valueOf(value));
                value = LoanExcelUtil.changeSelectValue(value, fieldWrapper);
            } else if (field.getType().equals("YesNo")) {
                if ("1".equals(value)) {
                    value =   "是";
                } else {
                    value =   "否";
                }
            } else if (field.getType().equals("Date")) {
                value =   DateUtil.format((Date)value, DateUtil.DEFAULT_DATE_FORMAT);
            } else if (field.getType().equals("MultipleSelect")) {
                value = fieldWrapper.getOptionTextByValues(String.valueOf(value));
                value = LoanExcelUtil.changeSelectValue(value, fieldWrapper);
            }
        } else {
            value = "";
            if (field.getType().equals("Select")) {
                value = LoanExcelUtil.changeSelectValue(value, fieldWrapper);
            } else if (field.getType().equals("MultipleSelect")) {
                value = LoanExcelUtil.changeSelectValue(value, fieldWrapper);
            }
            return String.valueOf(value);
        }
        return String.valueOf(value);
    }

    /**
     * 导出指定name和path的文件
     * @param fileName
     * @param path
     * @param response
     */
    private static void outPutFile(String fileName, String path, HttpServletResponse response) {
        ServletOutputStream toClient = null;
        InputStream inputStream = null;
        try {
            // 将生成文件推出
            File file = new File(path);
            // 以流的形式下载文件。
            inputStream = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename="
                    + new String((fileName + ".xlsx").getBytes("gbk"), "ISO-8859-1"));
            response.setContentType("application/msexcel; charset=utf-8");
            response.addHeader("Content-Length", "" + file.length());
            // 输出文件
            toClient = response.getOutputStream();
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
            // 删除文件
            file.delete();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (toClient != null) {
                    toClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
















    private void setData(AutoBaseTemplate template, String type, Map<String, Object> investigateMap) {
        String tableName = template.getTableName();
        List<AutoBaseField> autoBaseFields = template.getFields();

        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        if ("one".equals(type)) {
            for (AutoBaseField field : autoBaseFields) {
                setMapValue(template.getData(), field, map);
            }
        } else {
            Object[] datas = (Object[]) template.getData();
            if (datas != null) {
                for (Object object : datas) {
                    Map<String, Object> subMap = new HashMap<String, Object>();
                    for (AutoBaseField field : autoBaseFields) {
                        setMapValue(object, field, subMap);
                    }
                    maps.add(subMap);
                }
            }
        }

        if ("one".equals(type)) {
            investigateMap.put(tableName, map);
        } else {
            for (int i = 0; i < maps.size() && i < 5; i++) {
                investigateMap.put(tableName + (i + 1), maps.get(i));
            }
//			investigateMap.put(tableName, maps);
        }

    }

    private void setMapValue(Object data, AutoBaseField field, Map<String, Object> map) {
        AutoFieldWrapper fieldWrapper = (AutoFieldWrapper)field;
        Object value = fieldWrapper.getValue(data, true, false);
        map.put(field.getColumn(), value);
        if (value != null) {
            if (field.getType().equals("Select")) {
                map.put(field.getColumn(), fieldWrapper.getOptionTextByValue(String.valueOf(value)));
            } else if (field.getType().equals("YesNo")) {
                if ("1".equals(value)) {
                    map.put(field.getColumn(), "是");
                } else {
                    map.put(field.getColumn(), "否");
                }
            } else if (field.getType().equals("Date")) {
                map.put(field.getColumn(), DateUtil.format((Date)value, DateUtil.DEFAULT_DATE_FORMAT));
            } else if (field.getType().equals("MultipleSelect")) {
                map.put(field.getColumn(), fieldWrapper.getOptionTextByValues(String.valueOf(value)));
            }
        }
    }






    /**
     * 复制一个单元格样式到目的单元格样式
     * @param fromStyle
     * @param toStyle
     */
    private static void copyCellStyle(XSSFCellStyle fromStyle,
                                      XSSFCellStyle toStyle) {
        toStyle.setAlignment(fromStyle.getAlignment());
        //边框和边框颜色
        toStyle.setBorderBottom(fromStyle.getBorderBottom());
        toStyle.setBorderLeft(fromStyle.getBorderLeft());
        toStyle.setBorderRight(fromStyle.getBorderRight());
        toStyle.setBorderTop(fromStyle.getBorderTop());
        toStyle.setTopBorderColor(fromStyle.getTopBorderColor());
        toStyle.setBottomBorderColor(fromStyle.getBottomBorderColor());
        toStyle.setRightBorderColor(fromStyle.getRightBorderColor());
        toStyle.setLeftBorderColor(fromStyle.getLeftBorderColor());

        //背景和前景
        toStyle.setFillBackgroundColor(fromStyle.getFillBackgroundColor());
        toStyle.setFillForegroundColor(fromStyle.getFillForegroundColor());

        toStyle.setDataFormat(fromStyle.getDataFormat());
        toStyle.setFillPattern(fromStyle.getFillPattern());
        toStyle.setFont(fromStyle.getFont());
        toStyle.setHidden(fromStyle.getHidden());
        toStyle.setIndention(fromStyle.getIndention());//首行缩进
        toStyle.setLocked(fromStyle.getLocked());
        toStyle.setRotation(fromStyle.getRotation());//旋转
        toStyle.setVerticalAlignment(fromStyle.getVerticalAlignment());
        toStyle.setWrapText(fromStyle.getWrapText());

    }
}
