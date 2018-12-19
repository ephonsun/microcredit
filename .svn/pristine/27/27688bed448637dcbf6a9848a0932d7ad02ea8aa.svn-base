package banger.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import banger.dao.intf.IDeptImportDao;
import banger.domain.permission.PmsDeptImport;
import banger.framework.component.dataimport.AbstractImportHandler;
import banger.framework.component.dataimport.IImportContext;
import banger.framework.component.dataimport.IImportResult;
import banger.framework.component.dataimport.IRecordReader;
import banger.framework.component.dataimport.context.ColumnInfo;
import banger.framework.component.dataimport.context.DefaultImportContext;
import banger.framework.component.progress.IProgressBarManager;
import banger.framework.component.progress.ProgressBar;
import banger.framework.util.FileUtil;
import banger.framework.util.ReflectorUtil;
import banger.framework.util.StringUtil;
import banger.service.intf.IDeptImportService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 机构导入处理业务类
 *
 * @author zhusw
 */
public class DeptImportService implements IDeptImportService {
    private static final Logger logger = LoggerFactory.getLogger(DeptImportService.class);
    private Map<String, AbstractImportHandler> handlerMap;
    private IProgressBarManager progressBarManager;
    protected IDeptImportDao deptImportDao;
    protected final static int BATCH_NUM = 200;

    public void setProgressBarManager(IProgressBarManager progressBarManager) {
        this.progressBarManager = progressBarManager;
    }

    public IDeptImportDao getDeptImportDao() {
        return deptImportDao;
    }

    public void setDeptImportDao(IDeptImportDao deptImportDao) {
        this.deptImportDao = deptImportDao;
    }


    public DeptImportService() {
        this.handlerMap = new HashMap<String, AbstractImportHandler>();
    }

    /**
     * 通过导入的Excel文件得到表头
     *
     * @param excelFilename
     * @return
     */
    public List<String> getImportExcelHead(String excelFilename) {
        IImportContext context = new DefaultImportContext(excelFilename);
        context.setMaxRow(1);
        DeptImportHandler handler = new DeptImportHandler(this, null);
        handler.setContext(context);
        handler.start();
        List<String> columns = handler.getHead();
        return columns;
    }

    /**
     * 开始导入机构数据
     *
     * @param userId        用户Id
     * @param excelFilename 导入文件
     * @param columns       导入列和字段的对应关系
     * @return
     */
    public ProgressBar importExcel(String userId, String excelFilename, List<ColumnInfo> columns) {

        IImportContext context = new DefaultImportContext(excelFilename);
        context.setColumns(columns);
        context.setBatch(BATCH_NUM);

        DeptImportHandler handler = new DeptImportHandler(this, Integer.parseInt(userId));
        handler.setContext(context);
        this.handlerMap.put(userId, handler);

        ProgressBar bar = progressBarManager.add("deptImport_" + userId, userId);
        handler.setProgressBar(bar);
        handler.start();

        return bar;
    }

    /**
     * 得到导入结果
     *
     * @param userId
     * @return
     */
    public IImportResult getImportResultByUserId(String userId) {
        if (this.handlerMap.containsKey(userId)) {
            return this.handlerMap.get(userId);
        }
        return null;
    }

    /**
     * 处理Excel导入处理逻辑
     *
     * @author zhusw
     */
    class DeptImportHandler extends AbstractImportHandler {
        private DeptImportService deptImportService;
        private List<PmsDeptImport> list;
        private Map<String, PmsDeptImport> map;
        private Map<String, PmsDeptImport> oldImportMap;// 大于每次读取的最大条数时，用于记录已经导入数据
        private Integer loginUserId;
        private List<String> deptAccountList;//导入数据中的员工号集合
        private int batchCount;// 批量提交次数

        public DeptImportHandler(DeptImportService deptImportService, Integer userId) {
            this.deptImportService = deptImportService;
            this.list = new ArrayList<PmsDeptImport>();
            this.loginUserId = userId;
            this.deptAccountList = new ArrayList<String>();
            this.oldImportMap = new ConcurrentHashMap<String, PmsDeptImport>();
            this.map = new HashMap<String, PmsDeptImport>();
        }

        /**
         * 逐行读取Excel数据
         */
        @Override
        public void read(IRecordReader reader) {
            if (reader.rownum() > 0) {
                List<ColumnInfo> columns = this.getContext().getColumns();
                PmsDeptImport dept = new PmsDeptImport();
                for (ColumnInfo column : columns) {
                    if (!StringUtil.isNullOrEmpty(column.getFieldName())) {
                        String val = (String) reader.getValue(column.getIndex());
                        if(val == null){
                            val="";
                        }
                        ReflectorUtil.setPropertyValue(dept, column.getFieldName(), val.trim());
                    }
                }
                if (this.validImportDeptData(dept,reader)) {
                    deptAccountList.add(dept.getDeptName());
                    list.add(dept);
                }
                this.rowCount++;
            }
            super.read(reader);                //读进度条
        }

        /**
         * 批量提交
         */
        public void batchCommit() {
            //int startNum = batchCount++ * this.getContext().getBatch();
            if (this.list.size() > 0) {
                Set<String> deptCodeSet = new HashSet<String>();
                //同机构编码不允许同名称
                Set<String> deptNameSet = new HashSet<String>();
                for (int i = successCount; i < this.list.size(); i++) {
                    PmsDeptImport dept = this.list.get(i);
                    if(!deptCodeSet.contains(dept.getDeptCode())&&!deptNameSet.contains(dept.getDeptName())){
                    	deptNameSet.add(dept.getDeptName());
                    	deptCodeSet.add(dept.getDeptCode());
                    }
                }
                Map<String, PmsDeptImport> existMap = new HashMap<String, PmsDeptImport>();
                if(deptCodeSet.size() > 0){
                    List<PmsDeptImport> existList = deptImportService.deptImportDao.getExistDeptListByDeptCodes(deptCodeSet);
                    for (PmsDeptImport exist : existList) {
                        existMap.put(exist.getDeptCode(), exist);
                    }
                }
                List<PmsDeptImport> newList = new ArrayList<PmsDeptImport>();
                List<PmsDeptImport> insertList = new ArrayList<PmsDeptImport>();
                List<PmsDeptImport> updateList = new ArrayList<PmsDeptImport>();
                Map<String, PmsDeptImport> insertMap = new HashMap<String, PmsDeptImport>();
                Map<String, PmsDeptImport> updateMap = new HashMap<String, PmsDeptImport>();

                for (int i = successCount; i < this.list.size(); i++) {
                    PmsDeptImport dept = this.list.get(i);
                    if (existMap.containsKey(dept.getDeptCode())) {
                        PmsDeptImport existDept = existMap.get(dept.getDeptCode());
                        existDept.setDeptName(StringUtil.trim(dept.getDeptName()));
                        existDept.setDeptUpdateUser(loginUserId);
                        existDept.setDeptUpdateDate(Calendar.getInstance().getTime());
                        updateList.add(existDept);
                        updateMap.put(dept.getDeptCode(), dept);
                    } else if(deptCodeSet.contains(dept.getDeptCode())&&deptNameSet.contains(dept.getDeptName())) {
                        newList.add(dept);
                        insertMap.put(dept.getDeptCode(), dept);
                    }else{
                    	this.failCount++;
                    }
                }
                setDeptListParentProperties(newList, this.map, insertMap);
                for (PmsDeptImport dept : newList) {
                    if (hasParent(dept)) {
                    	dept.setDeptName(StringUtil.trim(dept.getDeptName()));
                        dept.setDeptCreateUser(loginUserId);
                        dept.setDeptUpdateUser(loginUserId);
                        dept.setDeptCreateDate(Calendar.getInstance().getTime());
                        dept.setDeptUpdateDate(Calendar.getInstance().getTime());
                        dept.setDeptIsdel(0);
                        insertList.add(dept);
                    } else {
                        this.writeLineErrorFile(dept, "找不到该上级机构编码的机构");
                    }
                }
                deptImportService.deptImportDao.insertDeptListByImport(insertList);
                for(int i =0;i<updateList.size();i++){
                    PmsDeptImport existDept = updateList.get(i);// 获取数据库中存在的机构
                    PmsDeptImport updateDept = updateMap.get(existDept.getDeptCode()); // 获取需要更新的机构信息
                    if(!StringUtil.isNullOrEmpty(existDept.getParentDeptCode()) && !updateDept.getParentDeptCode().equals(existDept.getParentDeptCode())){
                        // 数据库中已存在的机构，所属机构变更
                        PmsDeptImport parentDept = map.get(updateDept.getParentDeptCode());
                        if(parentDept != null){
                            String maxSearchCode = parentDept.getMaxSearchCode();
                            if (StringUtil.isNullOrEmpty(maxSearchCode)) maxSearchCode = "000";
                            Integer maxCode = Integer.parseInt(maxSearchCode);
                            String newSearchCode = StringUtil.padLeft(String.valueOf(maxCode + 1), 3, '0');

                            parentDept.setMaxSearchCode(newSearchCode);

                            existDept.setDeptSearchCode(parentDept.getDeptSearchCode() + newSearchCode);
                            if (parentDept.getDeptId() == null || parentDept.getDeptId().intValue() <= 0) {
                                Integer newId = deptImportService.deptImportDao.newDeptId();
                                parentDept.setDeptId(newId);
                            }
                            existDept.setDeptParentId(parentDept.getDeptId());
                            existDept.setDeptDepth(parentDept.getDeptDepth() + 1);
                            existDept.setDeptSort(maxCode + 1);
                        }
                    }
                }
                deptImportService.deptImportDao.updateDeptListByImport(updateList);
                this.insertCount += insertList.size();
                this.updateCount += updateList.size();
                this.successCount = this.insertCount + this.updateCount;
            }
        }

//        /**
//         * 设置导入机构的上级机构属性
//         *
//         * @param deptList
//         */
//        private void setDeptListParentProperties(List<PmsDeptImport> deptList) {
//            if (this.map == null) {
//                this.map = new HashMap<String, PmsDeptImport>();
//                List<PmsDeptImport> list = deptImportService.deptImportDao.getAllExistDeptList();            //因为机构表数据少，可以这么写，请勿模仿
//                for (PmsDeptImport exist : list) {
//                    this.map.put(exist.getDeptCode(), exist);
//                }
//            }
//
//            this.setMaxSearchCode(this.map);
//
//            for (PmsDeptImport dept : deptList) {
//                if (!this.map.containsKey(dept.getDeptCode())) {
//                    this.setDeptParentProperties(dept, this.map);
//                    this.map.put(dept.getDeptCode(), dept);
//                }
//            }
//
//        }

//        /**
//         * 设置最大机构查询码
//         */
//        private void setMaxSearchCode(Map<String, PmsDeptImport> map) {
//            for (PmsDeptImport dept : map.values()) {
//                if (!StringUtil.isNullOrEmpty(dept.getParentDeptCode())) {
//                    if (map.containsKey(dept.getParentDeptCode())) {
//                        PmsDeptImport parentDept = map.get(dept.getParentDeptCode());
//                        String maxSearchCode = parentDept.getMaxSearchCode();
//                        String minSearchCode = dept.getDeptSearchCode().substring(dept.getDeptSearchCode().length() - 3);
//                        if (StringUtil.isNullOrEmpty(maxSearchCode)) {
//                            parentDept.setMaxSearchCode(minSearchCode);
//                        } else {
//                            Integer maxCode = Integer.parseInt(maxSearchCode);
//                            Integer currCode = Integer.parseInt(minSearchCode);
//                            if (currCode > maxCode) {
//                                maxSearchCode = StringUtil.padLeft(String.valueOf(currCode), 3, '0');
//                                parentDept.setMaxSearchCode(maxSearchCode);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        /**
//         * 设置导入机构的上级机构属性
//         *
//         * @param dept
//         * @param map
//         */
//        private void setDeptParentProperties(PmsDeptImport dept, Map<String, PmsDeptImport> map) {
//            if (map.containsKey(dept.getParentDeptCode())) {
//                PmsDeptImport parentDept = map.get(dept.getParentDeptCode());
//                String maxSearchCode = parentDept.getMaxSearchCode();
//                if (StringUtil.isNullOrEmpty(maxSearchCode)) maxSearchCode = "000";
//                Integer maxCode = Integer.parseInt(maxSearchCode);
//                String newSearchCode = StringUtil.padLeft(String.valueOf(maxCode + 1), 3, '0');
//
//                parentDept.setMaxSearchCode(newSearchCode);
//
//                dept.setDeptSearchCode(parentDept.getDeptSearchCode() + newSearchCode);
//                if (parentDept.getDeptId() == null || parentDept.getDeptId().intValue() <= 0) {
//                    Integer newId = deptImportService.deptImportDao.newDeptId();
//                    parentDept.setDeptId(newId);
//                }
//                dept.setDeptParentId(parentDept.getDeptId());
//                dept.setDeptId(deptImportService.deptImportDao.newDeptId());
//                dept.setDeptDepth(parentDept.getDeptDepth() + 1);
//                dept.setDeptSort(maxCode + 1);
//            }
//        }

//        /**
//         * 是否找到上级机构
//         *
//         * @param dept
//         * @return
//         */
//        private boolean hasParent(PmsDeptImport dept) {
//            if (dept.getDeptParentId() != null && dept.getDeptParentId().intValue() > 0) {
//                return true;
//            }
//            return false;
//        }

        /**
         * 校验导入机构的数据是否正确
         *
         * @param dept
         * @return
         */

        private boolean validImportDeptData(PmsDeptImport dept, IRecordReader reader) {
            String error = "";
            String importResult = "成功";
            boolean result = true;

            List<PmsDeptImport> deptList = deptImportDao.getAllExistDeptList();
            List<PmsDeptImport> deptListByParentCode = deptImportDao.getExistDeptListByParentCode(dept.getParentDeptCode());
            boolean isExitDeptCode = false;
            boolean isExitDeptName = false;
            for (PmsDeptImport pmsDeptImport: deptList){
                if (pmsDeptImport.getDeptCode().equals(dept.getDeptCode())) {
                    isExitDeptCode = true;
                    break;
                }
            }
            for(PmsDeptImport pmsDept: deptListByParentCode){
                if (pmsDept.getDeptName().equals(dept.getDeptName())) {
                    isExitDeptName = true;
                    break;
                }
            }

            if (isExitDeptCode) {
                error = "机构编码已经存在";
                importResult = "失败";
                this.writeErrorFileByLine(reader, "", error);
                result = false;
            } else if (isNullOrEmpty(dept.getDeptCode())) {
                error = "机构编码不能为空";
                importResult = "失败";
                this.writeErrorFileByLine(reader, "", error);
                result = false;
            }else if(dept.getDeptCode().length()>20){;
                error = "机构编码不能大于20位";
                importResult = "失败";
                this.writeErrorFileByLine(reader, "", error);
                result = false;
            } else if (isNullOrEmpty(dept.getDeptName())) {
                error = "机构名称不能为空";
                importResult = "失败";
                this.writeErrorFileByLine(reader, "", error);
                result = false;
            }else if(dept.getDeptName().length()>20){
                error = "机构名称不能大于20位";
                importResult = "失败";
                this.writeErrorFileByLine(reader, "", error);
                result = false;

            }else if(deptAccountList.contains(dept.getDeptName())){
                error = "机构名称重复";
                importResult = "失败";
                this.writeErrorFileByLine(reader, "", error);
                result = false;
            }else if(isExitDeptName){
                error = "同层机构树下不能有相同的机构名称";
                importResult = "失败";
                this.writeErrorFileByLine(reader, "", error);
                result = false;
            } else if (isNullOrEmpty(dept.getParentDeptCode())) {
                error = "上级机构编码为空";
                importResult = "失败";
                this.writeErrorFileByLine(reader, "", error);
                result = false;
            }
            this.writeLineReportFile(reader, importResult, "", error);
            return result;
        }

        private boolean isNullOrEmpty(String str) {
            if (str == null || str.equals("")) {
                return true;
            } else {
                return str.trim().length() == 0;
            }
        }

        /**
         * 写错误文件内容
         *
         * @param dept
         */
        private void writeLineErrorFile(PmsDeptImport dept, String error) {
            String filename = this.getFailFilename();
            if (StringUtil.isNullOrEmpty(filename)) {
                String path = this.getFileParentPath();
                filename = FileUtil.contact(path, "errorExcel.csv");
                this.setFailFilename(filename);
                String content = "机构编码,机构名称,上级机构编码,错误信息";
                try {
                    FileUtils.write(new File(filename),content,"GBK",true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            String deptCode = (dept.getDeptCode() != null) ? dept.getDeptCode() : "";
            String deptName = (dept.getDeptName() != null) ? dept.getDeptName() : "";
            String parentDeptCode = (dept.getParentDeptCode() != null) ? dept.getParentDeptCode() : "";
            String content = "\r\n" + deptCode + "," + deptName + "," + parentDeptCode + "," + error;
            try {
                FileUtils.write(new File(filename),content,"GBK",true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.failCount++;
        }

        /**
         * 得到文件的路径
         *
         * @return
         */
        private String getFileParentPath() {
            String parentPath = FileUtil.getParentPath(this.context.getFilename());
            return parentPath;
        }

    }

    /**
     * 获取所有的机构信息
     * @return
     */
    public Map<String, PmsDeptImport> getAllExistDeptMap(){
        Map<String, PmsDeptImport> returnMap = new HashMap<String, PmsDeptImport>();
        // 因为机构表数据少，可以这么写，请勿模仿
        List<PmsDeptImport> list = this.deptImportDao.getAllExistDeptList();
        for (PmsDeptImport exist : list) {
            returnMap.put(exist.getDeptCode(), exist);
        }
        return returnMap;
    }

    /**
     * 设置导入机构的上级机构属性
     *
     * @param deptList
     * @param map
     */
    private void setDeptListParentProperties(List<PmsDeptImport> deptList, Map<String, PmsDeptImport> map, Map<String, PmsDeptImport> insertMap) {
//        if (map == null) {
//            map = new HashMap<String, PmsDeptImport>();
            List<PmsDeptImport> list = this.deptImportDao.getAllExistDeptList();            //因为机构表数据少，可以这么写，请勿模仿
            for (PmsDeptImport exist : list) {
                map.put(exist.getDeptCode(), exist);
            }
//        }

        setMaxSearchCode(map);

        for (PmsDeptImport dept : deptList) {
            if (!map.containsKey(dept.getDeptCode())) {
                setDeptParentProperties(dept, map, insertMap);
            }
        }

    }

    /**
     * 设置最大机构查询码
     */
    private void setMaxSearchCode(Map<String, PmsDeptImport> map) {
        for (PmsDeptImport dept : map.values()) {
            if (!StringUtil.isNullOrEmpty(dept.getParentDeptCode())) {
                if (map.containsKey(dept.getParentDeptCode())) {
                    PmsDeptImport parentDept = map.get(dept.getParentDeptCode());
                    String maxSearchCode = parentDept.getMaxSearchCode();
                    String minSearchCode = dept.getDeptSearchCode().substring(dept.getDeptSearchCode().length() - 3);
                    if (StringUtil.isNullOrEmpty(maxSearchCode)) {
                        parentDept.setMaxSearchCode(minSearchCode);
                    } else {
                        Integer maxCode = Integer.parseInt(maxSearchCode);
                        Integer currCode = Integer.parseInt(minSearchCode);
                        if (currCode > maxCode) {
                            maxSearchCode = StringUtil.padLeft(String.valueOf(currCode), 3, '0');
                            parentDept.setMaxSearchCode(maxSearchCode);
                        }
                    }
                }
            }
        }
    }

    /**
     * 设置导入机构的上级机构属性
     *
     * @param dept
     * @param map
     */
    private boolean setDeptParentProperties(PmsDeptImport dept, Map<String, PmsDeptImport> map, Map<String, PmsDeptImport> insertMap) {
        if (!map.containsKey(dept.getParentDeptCode())) {
            return false;
        }
        PmsDeptImport parentDept = map.get(dept.getParentDeptCode());
        String maxSearchCode = parentDept.getMaxSearchCode();
        if (StringUtil.isNullOrEmpty(maxSearchCode)) maxSearchCode = "000";
        Integer maxCode = Integer.parseInt(maxSearchCode);
        String newSearchCode = StringUtil.padLeft(String.valueOf(maxCode + 1), 3, '0');

        parentDept.setMaxSearchCode(newSearchCode);

        dept.setDeptSearchCode(parentDept.getDeptSearchCode() + newSearchCode);
        if (parentDept.getDeptId() == null || parentDept.getDeptId().intValue() <= 0) {
            Integer newId = this.deptImportDao.newDeptId();
            parentDept.setDeptId(newId);
        }
        dept.setDeptParentId(parentDept.getDeptId());
        dept.setDeptId(this.deptImportDao.newDeptId());
        dept.setDeptDepth(parentDept.getDeptDepth() + 1);
        dept.setDeptSort(maxCode + 1);
        insertMap.put(dept.getDeptCode(), dept);
        map.put(dept.getDeptCode(), dept);
        return true;
    }

    /**
     * 是否找到上级机构
     *
     * @param dept
     * @return
     */
    private boolean hasParent(PmsDeptImport dept) {
        if (dept.getDeptParentId() != null && dept.getDeptParentId().intValue() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 自动导入机构
     */
    public void autoImportDept() {
        Map<String, PmsDeptImport> map = null;
//        // 从中间机构表中获取所有子机构
//        List<PmsDeptImport> allChildrenDeptList = this.deptImportDao.getAllChildrenDeptList();
//        importDeptList(allChildrenDeptList, map);
    }

    /**
     * @param allParentDeptList
     * @param map
     */
    private void importDeptList(List<PmsDeptImport> allParentDeptList, Map<String, PmsDeptImport> map) {
        List<String> deptCodeList = new ArrayList<String>();
        for (PmsDeptImport dept : allParentDeptList) {
            // 循环机构的机构编号
            deptCodeList.add(dept.getDeptCode());
        }
        Map<String, PmsDeptImport> existMap = new HashMap<String, PmsDeptImport>();
        Set<String> deptCodeSet = new HashSet<String>();
        for(int i = 0; i < deptCodeList.size(); i++){
            deptCodeSet.add(deptCodeList.get(i));
            if((i > 0 && deptCodeSet.size() % 200 == 0) || i == deptCodeList.size() - 1){
                // 检查机构是否已经在双录系统中，存在则更新，不存在测新建
                List<PmsDeptImport> existList = this.deptImportDao.getExistDeptListByDeptCodes(deptCodeSet);
                for (PmsDeptImport exist : existList) {
                    existMap.put(exist.getDeptCode(), exist);
                }
                deptCodeSet.clear();
            }
        }
        List<PmsDeptImport> newList = new ArrayList<PmsDeptImport>();
        List<PmsDeptImport> insertList = new ArrayList<PmsDeptImport>();
        List<PmsDeptImport> updateList = new ArrayList<PmsDeptImport>();
        Map<String, PmsDeptImport> insertMap = new HashMap<String, PmsDeptImport>();
        Map<String, PmsDeptImport> updateMap = new HashMap<String, PmsDeptImport>();

        for (PmsDeptImport dept : allParentDeptList) {
            if (existMap.containsKey(dept.getDeptCode())) {
                PmsDeptImport existDept = existMap.get(dept.getDeptCode());
                existDept.setDeptName(dept.getDeptName());
                existDept.setDeptUpdateUser(1);
                existDept.setDeptUpdateDate(Calendar.getInstance().getTime());
                updateList.add(existDept);
                updateMap.put(dept.getDeptCode(), dept);
            } else {
                newList.add(dept);
                insertMap.put(dept.getDeptCode(), dept);
            }
        }
        setDeptListParentProperties(newList, map, insertMap);
        for (PmsDeptImport dept : newList) {
            if (hasParent(dept)) {
                dept.setDeptCreateUser(1);
                dept.setDeptUpdateUser(1);
                dept.setDeptCreateDate(Calendar.getInstance().getTime());
                dept.setDeptUpdateDate(Calendar.getInstance().getTime());
                dept.setDeptIsdel(0);
                insertList.add(dept);
            } else {
                logger.info(dept.getDeptName() + "******机构的父机构不存在");
            }
        }
        List<PmsDeptImport> list = new ArrayList<PmsDeptImport>();
        for (int i = 0; i < insertList.size(); i++) {
            list.add(insertList.get(i));
            if ((i > 0 && list.size() % 200 == 0) || i == insertList.size() - 1) {
                this.deptImportDao.insertDeptListByImport(list);
                list.clear();
            }
        }
        for (int i = 0; i < updateList.size(); i++) {
            PmsDeptImport existDept = updateList.get(i);// 获取数据库中存在的机构
            PmsDeptImport updateDept = updateMap.get(existDept.getDeptCode()); // 获取需要更新的机构信息
            if(!StringUtil.isNullOrEmpty(existDept.getParentDeptCode()) && !updateDept.getParentDeptCode().equals(existDept.getParentDeptCode())){
                // 数据库中已存在的机构，所属机构变更
                PmsDeptImport parentDept = map.get(updateDept.getParentDeptCode());
                if(parentDept != null){
                    String maxSearchCode = parentDept.getMaxSearchCode();
                    if (StringUtil.isNullOrEmpty(maxSearchCode)) maxSearchCode = "000";
                    Integer maxCode = Integer.parseInt(maxSearchCode);
                    String newSearchCode = StringUtil.padLeft(String.valueOf(maxCode + 1), 3, '0');

                    parentDept.setMaxSearchCode(newSearchCode);

                    existDept.setDeptSearchCode(parentDept.getDeptSearchCode() + newSearchCode);
                    if (parentDept.getDeptId() == null || parentDept.getDeptId().intValue() <= 0) {
                        Integer newId = this.deptImportDao.newDeptId();
                        parentDept.setDeptId(newId);
                    }
                    existDept.setDeptParentId(parentDept.getDeptId());
                    existDept.setDeptDepth(parentDept.getDeptDepth() + 1);
                    existDept.setDeptSort(maxCode + 1);
                }
            }
            list.add(existDept);
            if ((i > 0 && list.size() % 200 == 0) || i == updateList.size() - 1) {
                this.deptImportDao.updateDeptListByImport(list);
                list.clear();
            }
        }

    }
}

	
