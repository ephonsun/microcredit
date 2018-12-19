package banger.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.InitializingBean;

import banger.common.constant.GlobalConst;
import banger.dao.intf.IDeptDao;
import banger.dao.intf.IDeptImportDao;
import banger.dao.intf.IPmsBancsBrhmDao;
import banger.dao.intf.IPmsBancsTelmDao;
import banger.dao.intf.IRoleDao;
import banger.dao.intf.IUserImportDao;
import banger.dao.intf.IUserRoleDao;
import banger.domain.permission.PmsBancsBrhm;
import banger.domain.permission.PmsBancsTelm;
import banger.domain.permission.PmsDept;
import banger.domain.permission.PmsDeptImport;
import banger.domain.permission.PmsRole;
import banger.domain.permission.PmsUserImport;
import banger.domain.permission.PmsUserRoles;
import banger.framework.component.dataimport.AbstractImportHandler;
import banger.framework.component.dataimport.IImportContext;
import banger.framework.component.dataimport.IImportResult;
import banger.framework.component.dataimport.IRecordReader;
import banger.framework.component.dataimport.context.ColumnInfo;
import banger.framework.component.dataimport.context.DefaultImportContext;
import banger.framework.component.progress.IProgressBarManager;
import banger.framework.component.progress.ProgressBar;
import banger.framework.util.ArrayUtil;
import banger.framework.util.DateUtil;
import banger.framework.util.FileUtil;
import banger.framework.util.FtpServerUtil;
import banger.framework.util.Md5Encrypt;
import banger.framework.util.ReflectorUtil;
import banger.framework.util.StringUtil;
import banger.framework.util.ZipUtil;
import banger.service.intf.IUserImportService;

/**
 * Created with IntelliJ IDEA.
 * User: zhangfp
 * Date: 15-6-10
 * Time: 上午10:52
 * To change this template use File | Settings | File Templates.
 */
public class UserImportService implements InitializingBean,IUserImportService {

    private Map<String, AbstractImportHandler> handlerMap;
    private IProgressBarManager progressBarManager;
    private IUserImportDao userImportDao;
    private IDeptImportDao deptImportDao;
    private IDeptDao deptDao;
    private IRoleDao roleDao;
    private IUserRoleDao userRoleDao;
    private IPmsBancsBrhmDao pmsBancsBrhmDao;
    private IPmsBancsTelmDao pmsBancsTelmDao;
    private boolean quartzJobEnable ;//自动任务开关
    private String fileRootPath;        // 文件根目录
    private Integer ftpServerPort2; // ftp服务器端口2
    private String ftpServerIp2;//ftp服务器ip2
    private String ftpServerUserName2; // ftp服务器用户名2
    private String ftpServerUserPassword2; // ftp服务器密码2
    private String autoImportFilePath2;// 自动导入任务，源文件存放路径2
    private String loginUserId;
    private String userDeptId;

    public String getUserDeptId() {
        return userDeptId;
    }

    public void setUserDeptId(String userDeptId) {
        this.userDeptId = userDeptId;
    }

    public String getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }

    /**
     * 开始导入机构数据
     *
     * @param userId        用户Id
     * @param excelFilename 导入文件
     * @param columns       导入列和字段的对应关系
     * @return
     */
    public ProgressBar importExcel(String userId,String userDeptId, String excelFilename, List<ColumnInfo> columns) {
        this.setLoginUserId(userId);
        this.setUserDeptId(userDeptId);
        IImportContext context = new DefaultImportContext(excelFilename);
        context.setColumns(columns);
        context.setBatch(200);

        Map<String,Integer> roleExistMap = new HashMap<String, Integer>();//已存在角色Map,roleName->roleId
        List<PmsRole> roles = roleDao.getUserAllRoles();
        if(roles != null && roles.size() > 0){
            for (PmsRole role : roles){
            	roleExistMap.put(role.getRoleName(),role.getRoleId());
            }
            roleExistMap.put("系统管理员",1);
        }

        UserImportHandler handler = new UserImportHandler(this, Integer.parseInt(userId),roleExistMap);
        handler.setContext(context);
        this.handlerMap.put(userId, handler);

        ProgressBar bar = progressBarManager.add("userImport_" + userId, userId);
        handler.setProgressBar(bar);
        handler.start();

        return bar;
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
        UserImportHandler handler = new UserImportHandler(this, null, null);
        handler.setContext(context);
        handler.start();
        List<String> columns = handler.getHead();
        return columns;
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

    @Override
    public void afterPropertiesSet() throws Exception {
    	GlobalConst.QUARTZ_JOB_ENABLE = this.quartzJobEnable;
    	GlobalConst.FILE_ROOT_PATH = this.fileRootPath;
        GlobalConst.FTP_SERVER_IP_2 = this.ftpServerIp2;
        GlobalConst.FTP_SERVER_PORT_2 = this.ftpServerPort2;
        GlobalConst.FTP_SERVER_USER_NAME_2 = this.ftpServerUserName2;
        GlobalConst.FTP_SERVER_PASSWORD_2 = this.ftpServerUserPassword2;
        GlobalConst.AUTO_IMPORT_FILE_PATH_2 = this.autoImportFilePath2;
    }

    /**
     * 处理Excel导入处理逻辑
     *
     * @author zhusw
     */
    class UserImportHandler extends AbstractImportHandler {
        private UserImportService userImportService;
        private List<PmsUserImport> list;
        private Map<String, PmsDeptImport> map;
        private Map<String, Integer> roleExistMap;
        private Integer loginUserId;
        private int batchCount;// 批量提交次数
        private List<String> userAccountList;//导入数据中的员工号集合

        public UserImportHandler(UserImportService userImportService, Integer userId, Map<String, Integer> roleExistMap) {
            this.userImportService = userImportService;
            this.list = new ArrayList<PmsUserImport>();
            this.loginUserId = userId;
            this.userAccountList = new ArrayList<String>();
            this.roleExistMap = roleExistMap;
        }

        /**
         * 逐行读取Excel数据
         */
        @Override
        public void read(IRecordReader reader) {
            if (this.map == null) {
                this.map = new HashMap<String, PmsDeptImport>();
                List<PmsDeptImport> list = deptImportDao.getAllExistDeptList();
                for (PmsDeptImport exist : list) {
                    this.map.put(exist.getDeptCode(), exist);
                }
            }
            if (reader.rownum() > 0) {
                List<ColumnInfo> columns = this.getContext().getColumns();
                PmsUserImport user = new PmsUserImport();
                for (ColumnInfo column : columns) {
                    if (!StringUtil.isNullOrEmpty(column.getFieldName())) {
                        String val = (String) reader.getValue(column.getIndex());
                        ReflectorUtil.setPropertyValue(user, column.getFieldName(), val);
                    }
                }
                if (this.validImportDeptData(user, reader)) {
                	userAccountList.add(user.getUserAccount());
                    int roleId = this.roleExistMap.get(user.getRoleName());//默认输入一个角色
                    user.setRoleIds(String.valueOf(roleId));
                    list.add(user);
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
                Set<String> userAccountSet = new HashSet<String>();
                for (int i = successCount; i < this.list.size(); i++) {
                    PmsUserImport user = this.list.get(i);
                    userAccountSet.add(user.getUserAccount());
                }
                List<PmsUserImport> existList = userImportService.userImportDao.getExistUserListByUserAccounts(userAccountSet);
                Map<String, PmsUserImport> existMap = new HashMap<String, PmsUserImport>();
                for (PmsUserImport exist : existList) {
                    existMap.put(exist.getUserAccount(), exist);
                }
                List<PmsUserImport> newList = new ArrayList<PmsUserImport>();
                List<PmsUserImport> insertList = new ArrayList<PmsUserImport>();
                List<PmsUserImport> updateList = new ArrayList<PmsUserImport>();

                for (int i = successCount; i < this.list.size(); i++) {
                    PmsUserImport user = this.list.get(i);
                    if (existMap.containsKey(user.getUserAccount())) {
                        PmsUserImport existDept = existMap.get(user.getUserAccount());
                        existDept.setUserDeptId(user.getUserDeptId());
                        existDept.setUserName(user.getUserName());
                        existDept.setUserUpdateUser(loginUserId);
                        existDept.setUserUpdateDate(Calendar.getInstance().getTime());
                        existDept.setProdClassIds(user.getProdClassIds());
                        updateList.add(existDept);
                        List<PmsUserRoles> userRoles = userImportService.userRoleDao.getUserRolesByUserId(existDept.getUserId());
                        Set<String> roleSet = ArrayUtil.asSet(user.getRoleIds().split(","));
                        for(PmsUserRoles userRole : userRoles){
                            if(!roleSet.contains(userRole.getPurRoleId().toString())){
                                userImportService.userRoleDao.deleteUserRoleById(userRole.getPurUserRolesId());
                            }else{
                                roleSet.remove(userRole.getPurRoleId().toString());
                            }
                        }
                        for(String roleId : roleSet){
                            PmsUserRoles userRole = new PmsUserRoles();
                            userRole.setPurUserId(existDept.getUserId());
                            userRole.setPurRoleId(Integer.parseInt(roleId));
                            userRole.setPurCreateUser(loginUserId);
                            userRole.setPurUpdateUser(loginUserId);
                            userRole.setPurCreateDate(Calendar.getInstance().getTime());
                            userRole.setPurUpdateDate(Calendar.getInstance().getTime());
                            userImportService.userRoleDao.insertUserRole(userRole);
                        }
                    } else {
                        newList.add(user);
                    }
                }
                for (PmsUserImport user : newList) {
                    if (user.getUserDeptId() != null && user.getUserDeptId() > 0) {
                        user.setUserCreateUser(loginUserId);
                        user.setUserUpdateUser(loginUserId);
                        user.setUserCreateDate(Calendar.getInstance().getTime());
                        user.setUserUpdateDate(Calendar.getInstance().getTime());
                        user.setUserStatus(1);
                        user.setUserIsdel(0);
                        user.setUserPassword(Md5Encrypt.encrypt("111111"));
                        insertList.add(user);
                    }
                }
                List<PmsUserImport> userList= new ArrayList<PmsUserImport>();
                userList.addAll(userImportService.userImportDao.insertUserListByImport(insertList));
                userImportService.userImportDao.updateUserListByImport(updateList);
                userList.addAll(updateList);
                this.insertCount += insertList.size();
                this.updateCount += updateList.size();
                this.successCount = this.insertCount + this.updateCount;
            }
        }

        /**
         * 校验导入用户的数据是否正确
         *
         * @param user
         * @return
         */
        private boolean validImportDeptData(PmsUserImport user, IRecordReader reader) {
            String error = "";
            String importResult = "成功";
            boolean result = true;
            if (isNullOrEmpty(user.getUserAccount())) {
                error = "用户名不能为空";
                importResult = "失败";
                this.writeErrorFileByLine(reader, "", error);
                result = false;
            }else if(isIncludeSpecialLetter(user.getUserAccount())){
                error = "用户名不能包含特殊字符";
                importResult = "失败";
                this.writeErrorFileByLine(reader, "", error);
                result = false;
            }else if (isNullOrEmpty(user.getUserName())) {
                error = "用户名称不能为空";
                importResult = "失败";
                this.writeErrorFileByLine(reader, "", error);
                result = false;
            } else if (isNullOrEmpty(user.getBelongDeptCode())) {
                error = "用户归属机构编码不能为空";
                importResult = "失败";
                this.writeErrorFileByLine(reader, "", error);
                result = false;
            } else if (!setUserListBelongDeptProperties(user)) {
                error = "所属机构不存在";
                importResult = "失败";
                this.writeErrorFileByLine(reader, "", error);
                result = false;
            } else if (isNullOrEmpty(user.getRoleName())) {
                error = "角色名称不能为空";
                importResult = "失败";
                this.writeErrorFileByLine(reader, "", error);
                result = false;
            } else if(!roleExistMap.containsKey(user.getRoleName())){
	           	 error = "角色名称不存在";
	             importResult = "失败";
	             this.writeErrorFileByLine(reader, "", error);
	             result = false;
            } else if (roleExistMap.get(user.getRoleName()) == 4 && isNullOrEmpty(user.getProdClassStr())) {
                 error = "产品类型不能为空";
                 importResult = "失败";
                 this.writeErrorFileByLine(reader, "", error);
                 result = false;
            }
            else if (userAccountList.contains(user.getUserAccount())) {
                error = "用户名已重复";
                importResult = "失败";
                this.writeErrorFileByLine(reader, "", error);
                result = false;
            } else if (isParentDept(user.getBelongDeptCode())) {
                error = "归属机构为上级部门，无权限导入";
                importResult = "失败";
                this.writeErrorFileByLine(reader, "", error);
                result = false;
            }
            this.writeLineReportFile(reader, importResult, "", error);
            return result;
        }

        /**
         * 判断是否是上级部门用户
         * @param parentCode
         * @return
         */
        private boolean isParentDept(String parentCode){
            boolean flag = false;
            Map<String, Object> paramap = new HashMap<String, Object>();
            paramap.put("deptCode",parentCode);
            PmsDept dept = deptDao.getDeptByCode(paramap);
            String userdeptSearch=deptDao.getSearchCodeByUserId(Integer.parseInt(getLoginUserId()));
            if(!dept.getDeptSearchCode().startsWith(userdeptSearch)){
                flag = true;
            }
            return flag;
        }

        /**
         * 设置导入用户的归属机构属性
         *
         * @param pmsUserImport
         */
        private boolean setUserListBelongDeptProperties(PmsUserImport pmsUserImport) {
            if (this.map.containsKey(pmsUserImport.getBelongDeptCode())) {
                pmsUserImport.setUserDeptId(this.map.get(pmsUserImport.getBelongDeptCode()).getDeptId());
                return true;
            } else {
                return false;
            }

        }
    }

    private boolean isNullOrEmpty(String str) {
        if (str == null || str.equals("") || "null".equals(str)) {
            return true;
        } else {
            return str.trim().length() == 0;
        }
    }

    private boolean isIncludeSpecialLetter(String str){
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     *自动导入用户信息
     */
    public String autoImportUserInfo() throws Exception{
    	Date startTime = Calendar.getInstance().getTime();//开始导入执行时间
    	// 通过ftp自动获取柜员数据到本服务器中间表 、用户表（时间为T+1）
        String dateNow = new SimpleDateFormat("yyyyMMdd").format(DateUtil.addDay(Calendar.getInstance().getTime(), -1))+0;
        // 链接ftp服务器
        boolean isConn = FtpServerUtil.connectServer(GlobalConst.FTP_SERVER_IP_2, GlobalConst.FTP_SERVER_PORT_2.intValue(), GlobalConst.FTP_SERVER_USER_NAME_2, GlobalConst.FTP_SERVER_PASSWORD_2, GlobalConst.AUTO_IMPORT_FILE_PATH_2);
        if(!isConn){
           return "连接到ftp服务器异常！";
        }
        String targetFilePath = FileUtil.contact(GlobalConst.FILE_ROOT_PATH, "auto_import"+File.separator+"user_import");
        // 从ftp服务器获取文件到本地服务器
        FtpServerUtil.downFile(GlobalConst.AUTO_IMPORT_FILE_PATH_2, targetFilePath, "0100006D.k07.gz."+ dateNow, "." + dateNow);
        FtpServerUtil.downFile(GlobalConst.AUTO_IMPORT_FILE_PATH_2, targetFilePath, "0100006D.k01.gz."+ dateNow, "." + dateNow);

        StringBuffer opeVentActionDetail = new StringBuffer();	//操作详情日志
        // 获取用户表数据
        List<String> userImportList = ZipUtil.readFileByLine(FileUtil.contact(targetFilePath, "0100006D.k07.gz"), "|||||Begin", "gbk");
        // 获取机构表信息
        List<String> deptImportList = ZipUtil.readFileByLine(FileUtil.contact(targetFilePath, "0100006D.k01.gz"), "|||||Begin", "gbk");
        opeVentActionDetail.append("下发数据：" + userImportList.size() + "条,");
        // 关闭链接
        FtpServerUtil.closeServer();
        
        Map<String, PmsDeptImport> deptExistMap = new HashMap<String, PmsDeptImport>();	//系统中已存在机构Map,机构编码->实体
        // 因为机构表数据少，可以这么写，请勿模仿
        List<PmsDeptImport> deptExitlist = this.deptImportDao.getAllExistDeptList();	
        for (PmsDeptImport dept : deptExitlist) {
        	deptExistMap.put(dept.getDeptCode(), dept);
        }
        
        String provBarNoNeed = "00002";		//中行北方机构编号
        String capableNeed = "05";	//需要导入EHR用户条件1
        List<String> userTypeNeedList = new ArrayList<String>(Arrays.asList(
        		"001","002","003","004","005","030","031","032",
        		"033","034","035","050","051","052","053","060",
        		"061","062","063","071","070","715","716"));	//需要导入EHR用户条件2
        List<String> brNoNeedList = new ArrayList<String>();	//中行北分下机构的机构编号列表
        String importUserType1 = "EHR用户";
        String importUserType2 = "非EHR用户";
        String importErr1 = "失败：用户名不能为空";
        String importErr2 = "失败：用户名不能为空";
        String importErr3 = "失败：归属机构编号不能为空";
        String importErr4 = "失败：归属机构在系统中不存在";
        String importSuc = "成功";
        String roleName = "理财经理/柜员";
    	
    	// 清除中间表数据
        this.pmsBancsBrhmDao.deletePmsBancsBrhm();
        this.pmsBancsTelmDao.deletePmsBancsTelm();
        
        // 插入全量机构到机构中间表，获得中行北分（00002）下的机构编号列表
        List<PmsBancsBrhm> insertBancsBrhmList = new ArrayList<PmsBancsBrhm>();		//全量中间表机构列表
        for(String org : deptImportList){
        	String[] tempStrs = org.split("\\|");
        	PmsBancsBrhm  dept = new PmsBancsBrhm();
        	dept.setOro1BrNo(tempStrs[3].trim());
        	dept.setOro1ProvBrNo(tempStrs[163].trim());
        	if(provBarNoNeed.equals(dept.getOro1ProvBrNo())){
        		brNoNeedList.add(dept.getOro1BrNo());
        	}
        	insertBancsBrhmList.add(dept);
        }
        this.pmsBancsBrhmDao.insertPmsBancsBrhm(insertBancsBrhmList);
        deptImportList.clear();
        insertBancsBrhmList.clear();
        
        // 插入全量用户到用户中间表,获得需要导入到系统用户表的EHR用户列表
        List<PmsBancsTelm> insertBancsTelmList = new ArrayList<PmsBancsTelm>();		//全量中间表用户列表
        List<PmsUserImport> userInfoImportList = new ArrayList<PmsUserImport>();	//待同步EHR用户列表
        //String replaceStr = "^0+";
        Set<String> importUserAccountSet = new HashSet<String>();		//待同步EHR用户员工号集合
        int ehrUserCount = 0;
        
        for(String tempStr : userImportList){
            String[] tempStrs = tempStr.split("\\|");
            PmsBancsTelm user = new PmsBancsTelm();
            user.setCwo8TellerNo(tempStrs[3].trim());
            user.setCwo8BrchNo(tempStrs[6].trim());
            user.setCwo8Capable(tempStrs[18].trim());
            user.setCwo8TellerName(StringUtil.trimEx(tempStrs[33]));
            user.setCwo8UserType(tempStrs[80].trim());
            user.setCwo8Stat(tempStrs[16].trim());//add EHR用户状态
            if(brNoNeedList.contains(user.getCwo8BrchNo()) && 
            		capableNeed.equals(user.getCwo8Capable()) && 
            		userTypeNeedList.contains(user.getCwo8UserType()) && !user.getCwo8Stat().equals("09") && !user.getCwo8Stat().equals("99")){//对需要录入的EHR用户进行验证,add(柜员状态为非09-记录删除(修改) 99-记录删除(删除))
            	user.setImportResult(importUserType1);
            	ehrUserCount++;
                if(isNullOrEmpty(user.getCwo8TellerName())){
                    user.setImportResultDetail(importErr1);
                } else if(isNullOrEmpty(user.getCwo8TellerName())) {
                    user.setImportResultDetail(importErr2);
                } else if(isNullOrEmpty(user.getCwo8BrchNo())){
                    user.setImportResultDetail(importErr3);
                } else if(deptExistMap.get(user.getCwo8BrchNo()) == null) {
                    user.setImportResultDetail(importErr4);
                } else {
                    user.setImportResultDetail(importSuc);
                    PmsUserImport pmsUserImport = new PmsUserImport();
                    // 用户名称
                    pmsUserImport.setUserName(user.getCwo8TellerName());
                    String userAccount = user.getCwo8TellerNo();
                    if(user.getCwo8TellerNo().length()>7){// 员工号（截取后7位）
                    	userAccount = userAccount.substring(9,userAccount.length());
                    }
                    // 员工号
                    pmsUserImport.setUserAccount(userAccount);
                    // 所属机构
                    pmsUserImport.setUserDeptId(deptExistMap.get(user.getCwo8BrchNo()).getDeptId());
                    // 角色名称
                    pmsUserImport.setRoleName(roleName);
                    //添加到list集合
                    userInfoImportList.add(pmsUserImport);
                    // 待同步EHR用户员工号集合
                    importUserAccountSet.add(pmsUserImport.getUserAccount());
                }
            }else{//非EHR用户
            	user.setImportResult(importUserType2);
            }
            insertBancsTelmList.add(user);
        }
        this.pmsBancsTelmDao.insertPmsBancsTelm(insertBancsTelmList);
        userImportList.clear();
        insertBancsTelmList.clear();
        deptExistMap.clear();
        
        opeVentActionDetail.append("EHR用户：" + ehrUserCount + "条，待同步：" + userInfoImportList.size() + "条。");


        Map<String, PmsUserImport> userExistMap = new HashMap<String, PmsUserImport>();		//系统中已存在用户在待同步EHR用户集合中已存在的集合
        if(importUserAccountSet.size() > 0){
        	List<PmsUserImport> userExistList = this.userImportDao.getAllUserList();
            for (PmsUserImport user : userExistList) {
            	if(importUserAccountSet.contains(user.getUserAccount())){
            		userExistMap.put(user.getUserAccount(), user);
            	}
            }
            importUserAccountSet.clear();
        }

        // 遍历待同步EHR用户列表
        if (userInfoImportList.size() > 0) {
//            List<PmsUserImport> userListNew = new ArrayList<PmsUserImport>();		//待同步EHR用户列表中在系统中不存在的用户
//            List<PmsUserImport> userListInsert = new ArrayList<PmsUserImport>();	//待新增用户列表
//            List<PmsUserImport> userListUpdate = new ArrayList<PmsUserImport>();	//待更新用户列表（待同步EHR用户列表中在系统中已存在的用户）
            int insertNum= 0;
            int updateNum = 0;
            for (int i = 0; i < userInfoImportList.size(); i++) {
                PmsUserImport user = userInfoImportList.get(i);
                if (userExistMap.containsKey(user.getUserAccount())) {//待同步EHR用户列表中在系统中已存在用户
                    PmsUserImport existDept = userExistMap.get(user.getUserAccount());
                    existDept.setUserName(user.getUserName());
                    existDept.setUserDeptId(user.getUserDeptId());
                    existDept.setUserUpdateUser(1);
                    existDept.setUserUpdateDate(Calendar.getInstance().getTime());
//                    userListUpdate.add(existDept);
                    this.userImportDao.updateUserByImport(existDept);
                    updateNum++;
                } else {
//                	userListNew.add(user);
                	user.setUserCreateUser(1);
                	user.setUserUpdateUser(1);
                	user.setUserCreateDate(Calendar.getInstance().getTime());
                	user.setUserUpdateDate(Calendar.getInstance().getTime());
                	user.setUserStatus(1);
                	user.setUserIsdel(0);
                	user.setUserPassword(Md5Encrypt.encrypt("111111"));
                	this.userImportDao.insertUserByImport(user);
                	insertNum++;
                }
            }
            opeVentActionDetail.append("新增:" + insertNum + "条，修改:" + updateNum + "条。");
//            for (PmsUserImport user : userListNew) {
//                if (user.getUserDeptId() != null && user.getUserDeptId() > 0) {
//                    user.setUserCreateUser(1);
//                    user.setUserUpdateUser(1);
//                    user.setUserCreateDate(Calendar.getInstance().getTime());
//                    user.setUserUpdateDate(Calendar.getInstance().getTime());
//                    user.setUserStatus(1);
//                    user.setUserIsdel(0);
//                    user.setUserPassword(Md5Encrypt.encrypt("111111"));
//                    userListInsert.add(user);
//                }
//            }
//            userListNew.clear();
//            
//            if(userListInsert.size() > 0){//循环批量新增
////                List<PmsUserImport> insertUserList = new ArrayList<PmsUserImport>();
//               for(int i = 0; i < userListInsert.size(); i++){
////                   insertUserList.add(userListInsert.get(i));
////                   if((i > 0 && insertUserList.size() % 200 == 0) || i == userListInsert.size() - 1){
////                       this.userImportDao.insertUserListByImport(insertUserList);
////                       insertUserList.clear();
////                   }
//                   this.userImportDao.insertUserByImport(userListInsert.get(i));
//               }
//               opeVentActionDetail.append("新增:" + userListInsert.size() + "条，");
//               userListInsert.clear();
//            }
//            if(userListUpdate.size() > 0){//循环批量修改
////                List<PmsUserImport> updateUserList = new ArrayList<PmsUserImport>();
//                for(int i = 0; i < userListUpdate.size(); i++){
////                    updateUserList.add(userListUpdate.get(i));
////                    if((i > 0 && updateUserList.size() % 200 == 0) || i == userListInsert.size() - 1){
////                        this.userImportDao.updateUserListByImport(updateUserList);
////                        updateUserList.clear();
////                    }
//                	this.userImportDao.updateUserByImport(userListUpdate.get(i));
//                }
//                opeVentActionDetail.append("修改:" + userListUpdate.size() + "条。");
//                userListUpdate.clear();
//            }
        }
        Date endTime = Calendar.getInstance().getTime();//结束导入执行时间
        String spendTime = DateUtil.getDatSpanMin(endTime, startTime);
        opeVentActionDetail.append("共耗时:"+spendTime);
        return opeVentActionDetail.toString();
    }

    public UserImportService() {
        this.handlerMap = new HashMap<String, AbstractImportHandler>();
    }

    public IDeptImportDao getDeptImportDao() {
        return deptImportDao;
    }

    public void setDeptImportDao(IDeptImportDao deptImportDao) {
        this.deptImportDao = deptImportDao;
    }

	public IRoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

    public IDeptDao getDeptDao() {
        return deptDao;
    }

    public void setDeptDao(IDeptDao deptDao) {
        this.deptDao = deptDao;
    }

	public IPmsBancsBrhmDao getPmsBancsBrhmDao() {
        return pmsBancsBrhmDao;
    }

    public void setPmsBancsBrhmDao(IPmsBancsBrhmDao pmsBancsBrhmDao) {
        this.pmsBancsBrhmDao = pmsBancsBrhmDao;
    }

    public IPmsBancsTelmDao getPmsBancsTelmDao() {
        return pmsBancsTelmDao;
    }

    public void setPmsBancsTelmDao(IPmsBancsTelmDao pmsBancsTelmDao) {
        this.pmsBancsTelmDao = pmsBancsTelmDao;
    }

    public Map<String, AbstractImportHandler> getHandlerMap() {
        return handlerMap;
    }

    public void setHandlerMap(Map<String, AbstractImportHandler> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public IProgressBarManager getProgressBarManager() {
        return progressBarManager;
    }

    public void setProgressBarManager(IProgressBarManager progressBarManager) {
        this.progressBarManager = progressBarManager;
    }

    public IUserImportDao getUserImportDao() {
        return userImportDao;
    }

    public void setUserImportDao(IUserImportDao userImportDao) {
        this.userImportDao = userImportDao;
    }

	public boolean isQuartzJobEnable() {
		return quartzJobEnable;
	}

	public void setQuartzJobEnable(boolean quartzJobEnable) {
		this.quartzJobEnable = quartzJobEnable;
	}

	public String getFileRootPath() {
		return fileRootPath;
	}

	public void setFileRootPath(String fileRootPath) {
		this.fileRootPath = fileRootPath;
	}

	public Integer getFtpServerPort2() {
		return ftpServerPort2;
	}

	public void setFtpServerPort2(Integer ftpServerPort2) {
		this.ftpServerPort2 = ftpServerPort2;
	}

	public String getFtpServerIp2() {
		return ftpServerIp2;
	}

	public void setFtpServerIp2(String ftpServerIp2) {
		this.ftpServerIp2 = ftpServerIp2;
	}

	public String getFtpServerUserName2() {
		return ftpServerUserName2;
	}

	public void setFtpServerUserName2(String ftpServerUserName2) {
		this.ftpServerUserName2 = ftpServerUserName2;
	}

	public String getFtpServerUserPassword2() {
		return ftpServerUserPassword2;
	}

	public void setFtpServerUserPassword2(String ftpServerUserPassword2) {
		this.ftpServerUserPassword2 = ftpServerUserPassword2;
	}

    public IUserRoleDao getUserRoleDao() {
        return userRoleDao;
    }
    public void setUserRoleDao(IUserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }
	public String getAutoImportFilePath2() {
		return autoImportFilePath2;
	}
	public void setAutoImportFilePath2(String autoImportFilePath2) {
		this.autoImportFilePath2 = autoImportFilePath2;
	}
}
