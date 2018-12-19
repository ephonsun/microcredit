package banger.job;

import banger.common.tools.FtpUtil;
import banger.common.tools.ServerRealPathUtil;
import banger.domain.config.AutoBaseField;
import banger.domain.config.AutoBaseTemplate;
import banger.domain.config.AutoFieldWrapper;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.enumerate.TableInputType;
import banger.domain.loan.LoanApplyInfo;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.spring.SpringContext;
import banger.framework.util.DateUtil;
import banger.framework.util.FileUtil;
import banger.framework.util.FormatUtil;
import banger.moduleIntf.IConfigService;
import banger.moduleIntf.ITmpLoanDataService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.digester.plugins.RuleLoader;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.*;


/**
 * Created by kangbiao
 */
public class WriteLendLoanJob implements Job {

    //必要的引用
    ITmpLoanDataService tmpLoanDataService = (ITmpLoanDataService) SpringContext.instance().get("tmpLoanDataService");
    IConfigService configService = (IConfigService) SpringContext.instance().get("configServiceImpl");
    //日志
    private static final Logger log = LoggerFactory.getLogger(WriteLendLoanJob.class);

    //今天日期  昨天日期
    Date today = new Date();
    Date yesterday = DateUtil.addDay(today, -1);
    String startDate = FormatUtil.formatDate(yesterday, "yyyy-MM-dd");
    String bizDate = FormatUtil.formatDate(yesterday, "yyyyMMdd");




    @Override//执行入口 放贷完成的贷款 同步客户信息给市信贷 做征信用
    public void execute(JobExecutionContext jobExecutionContext)  {
        long beginTimes = System.currentTimeMillis();
        log.info("//==============WriteLendLoanJob begin..."+startDate+" ==============");
        try {

            //初始化
            initWriteConfig();

            //action
            writeCustFile();

            //文件编码转换 并且上传ftp
            iconvAndFtp();

        }catch (Exception e){
            log.error("市信贷文件写入失败:"+(StringUtils.isNotBlank(e.getMessage())?e.getMessage():e.getClass()),e);
        }
        log.info("//==============WriteLendLoanJob end, in "+(System.currentTimeMillis()-beginTimes)+"ms... ============== ");
    }


    //查询贷款 写文件
    private void writeCustFile() throws IOException {
        //查询全部 有贷款账户的 代表已经成功碰对过  1全量 2增量
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(syncType)&&"2".equals(syncType)){
            paramMap.put("loanCreditDate",yesterday);
        }
        paramMap.put("loanAccountExist","true");

        List<LoanApplyInfo> applyInfoList = tmpLoanDataService.queryApplyInfoList(paramMap);
        Map<String, String> map ;
        if(!CollectionUtils.isEmpty(applyInfoList)){
            for (LoanApplyInfo applyInfo : applyInfoList) {
                map = getLoanInfoMap(applyInfo.getLoanId());
                map.put("贷款账号",applyInfo.getLoanAccountNo());
                map.put("业务日期",bizDate);

                //客户
                writeFileByConfig(customerFileName, custConfigList, map);

                //配偶 离异3 丧偶4 初婚5 再婚6 未婚1 已婚2 复婚7 未说明婚姻状况8
                String maritalStatus = MapUtils.getString(map, "个人信息.婚姻状况", "8");
                if(maritalStatus.equals("5")||maritalStatus.equals("6")||maritalStatus.equals("2")||maritalStatus.equals("7")){
                    writeFileByConfig(customerFileName, custConfigList, map);
                }

                // 担保人
                AutoBaseTemplate template = configService.getTemplateListByTable("LBIZ_LOAN_GUARANTEE");
                if(null!=template){
                    DataTable datatable = tmpLoanDataService.getDataTableByLoanId("LBIZ_LOAN_GUARANTEE", "Investigate", applyInfo.getLoanId());
                    if(datatable!=null && datatable.rowSize()>0){
                        a:for ( DataRow row : datatable.getRows() ) {
                            //添加担保人数据
                            template.setData(row);
                            List<AutoBaseField> fieldList = template.getFields();
                            b:for (int i = 0; i < fieldList.size(); i++) {
                                AutoFieldWrapper field = (AutoFieldWrapper)fieldList.get(i);
                                Object value = "";
                                if(null!=template.getData()){
                                    value = banger.framework.reader.Reader.objectReader().getValue(template.getData(), field.getColumn());
                                }
                                if(null!=value){
                                    if(field.getType().equalsIgnoreCase("Date")) {
                                        value = FormatUtil.formatDate(value, "yyyyMMdd");
                                    }
                                    String key = template.getName() + "." + field.getFieldName();
                                    map.put(key, value.toString());
                                }else if(field.getColumn().equals("FULL_NAME")||field.getColumn().equals("ID_CARD")){
                                    continue a;
                                }
                            }
                            //写文件
                            writeFileByConfig(guarantorFileName, guarantorConfigList, map);
                        }
                    }
                }


            }
        }
    }

    private void writeFileByConfig(String fileName, List<WriteLoanConfigBean> configList, Map<String, String> map) throws IOException {

        OutputStreamWriter outputStreamWriter = null;
        PrintWriter printWriter = null;

        try {
            outputStreamWriter = new OutputStreamWriter(new FileOutputStream(fileName,true), localEncode);
            printWriter = new PrintWriter(outputStreamWriter);
            StringBuffer sbf = new StringBuffer("");
            for (WriteLoanConfigBean config : configList){
                sbf.append(getTextValue(MapUtils.getString(map, config.getParamName(), config.getDefaultValue()), config.getParamLength()));
            }
            printWriter.println(new String(sbf.toString().getBytes(), localEncode).substring(0, sbf.length() - 1));

            printWriter.flush();
            outputStreamWriter.flush();
            printWriter.close();
            outputStreamWriter.close();

        }catch (Exception e) {
            log.error("//******写入信息出错:"+fileName+"******//",e);
        }finally {
            if(null!=outputStreamWriter){
                outputStreamWriter.close();
            }
            if(null!=printWriter){
                printWriter.close();
            }
        }
    }


    //根据贷款id 查询贷款数据 form
    private Map<String, String> getLoanInfoMap(Integer loanId) {

        Map<String, String> map = new HashMap<String, String>();

        //查询所有表单
        List<AutoBaseTemplate> templateList = configService.getAllTemplateList();
        if(CollectionUtils.isEmpty(templateList)){
            return map;
        }
        for(AutoBaseTemplate template : templateList){
            //1单数据表单form  2table
            if(template.getType()!="1"){
                continue;
            }
            String presType = "";
            DataTable datatable = null;
            if(TableInputType.LIST.type.equals(template.getType())){
                presType = LoanProcessTypeEnum.INVESTIGATE.type;
            }

            if(template.getTableName().equals("LBIZ_APPROVAL_RESOLUTION")){
                datatable = tmpLoanDataService.getApprovalDataTableByLoanId( loanId);
            }else{
                datatable = tmpLoanDataService.getDataTableByLoanId(template.getTableName(), "", loanId);
            }
            //
            if(datatable!=null && datatable.rowSize()>0){
                template.setData(datatable.getRow(0));
                List<AutoBaseField> fieldList = template.getFields();
                for (int i = 0; i < fieldList.size(); i++) {
                    AutoFieldWrapper field = (AutoFieldWrapper)fieldList.get(i);
                    Object value = "";
                    if(null!=template.getData()){
                        value = banger.framework.reader.Reader.objectReader().getValue(template.getData(), field.getColumn());
                    }
                    if(null!=value){
                        if(field.getType().equalsIgnoreCase("Date")) {
                            value = FormatUtil.formatDate(value, "yyyyMMdd");
                        }
                        String key = template.getName() + "." + field.getFieldName();
                        map.put(key, value.toString());
                    }
                }
            }
        }
        return map;

    }


    private String getTextValue(Object value1, int length) {
        try{
            StringBuffer value = new StringBuffer("");
            if (null != value1) {
                value.append(value1);
            }
            length -= value.toString().getBytes().length;
            for (int i = 0; i < length; i++) {
                value.append(" ");
            }
            value.append(",");
            return value.toString();
        }catch (Exception e){
            return "";
        }
    }

    //==================================================初始化==============================================================

    //1全量同步 2增量同步
    private String syncType;
    //上传文件根路径
    private String uploadPath ;
    //客户文件配置
    private String custConfig;
    //配偶文件配置
    private String spouseConfig;
    //担保人文件配置
    private String guarantorConfig;
    //本地文件路径
    private String localFilePath ;
    //分隔符 ,
    private String delimiter ;
    //生成的文件编码
    private String localEncode ;
    //转换成为的文件编码
    private String targetEncode ;
    //
    private String ftpIp ;
    //
    private String ftpName ;
    //
    private String ftpPassword ;
    //
    private String ftpFilePath ;
    //
    private String globalProperties = ServerRealPathUtil.getServerRealPath() + "WEB-INF" + File.separator + "classes" + File.separator + "globalConfig.properties";
    //    InputStream syncInputStream =  RuleLoader.class.getClassLoader().getResourceAsStream("sync.properties");

    //客户信息文件名
    private String customerFileName = "loancustomer.del";
    //配偶信息文件名
    private String spouseFileName = "loanspouse.del";
    //担保人信息文件名
    private String guarantorFileName = "loanguarantor.del";

    private List<WriteLoanConfigBean> custConfigList = new ArrayList<WriteLoanConfigBean>();
    private List<WriteLoanConfigBean> spouseConfigList = new ArrayList<WriteLoanConfigBean>();
    private List<WriteLoanConfigBean> guarantorConfigList = new ArrayList<WriteLoanConfigBean>();

    //初始化配置数据
    private void initWriteConfig() throws Exception{
        //读取properties文件配置
        loadProperties();

        this.customerFileName = readConfig(custConfig, customerFileName, custConfigList);
        this.spouseFileName = readConfig(spouseConfig, spouseFileName, spouseConfigList);
        this.guarantorFileName = readConfig(guarantorConfig, guarantorFileName, guarantorConfigList);

        //创建文件
        createNewFile();
    }

    //读取配置
    private String readConfig(String config, String fileName, List<WriteLoanConfigBean> configList) throws Exception{

        if(StringUtils.isNotBlank(config)){
            String[] configSplit = config.split("\\|");
            if(null!=configSplit&&configSplit.length==2){
                String config_0 = configSplit[0];
                String config_1 = configSplit[1];
                if(StringUtils.isBlank(config_0)||StringUtils.isBlank(config_1)){
                    throw new Exception("字段配置格式错误:"+config);
                }
                fileName = config_0;
                //
                String[] fields = config_1.split(",");
                WriteLoanConfigBean configBean ;
                for (String field : fields) {
                    String[] fieldSplit = field.split("&");
                    if(null!=fieldSplit&&fieldSplit.length>=2){
                        String field_0 = fieldSplit[0];
                        String field_1 = fieldSplit[1];
                        String field_2 = "";
                        if(fieldSplit.length==3){
                            field_2 = fieldSplit[2];
                        }
                        if(StringUtils.isBlank(field_0)||StringUtils.isBlank(field_1)||!StringUtils.isNumeric(field_1)){
                            throw new Exception("字段配置格式错误:"+field);
                        }
                        configBean = new WriteLoanConfigBean(field_0,Integer.valueOf(field_1),field_2);
                        configList.add(configBean);

                    }else{
                        throw new Exception("字段配置格式错误:"+field);
                    }
                }
            }else{
                throw new Exception("字段配置格式错误:"+config);
            }
        }else{
            throw new Exception("字段配置格式错误:"+config);
        }

        return fileName;

    }


    //创建写入文件
    private void createNewFile() throws IOException {


        customerFileName  = (localFilePath + File.separator + customerFileName);
        spouseFileName = (localFilePath + File.separator + spouseFileName);
        guarantorFileName = (localFilePath + File.separator + guarantorFileName);

        // 文件
        File customerFile = new File(customerFileName);
        if(customerFile.exists()){
            customerFile.delete();
        }
        customerFile.createNewFile();

        File spouseFile = new File(spouseFileName);
        if(spouseFile.exists()){
            spouseFile.delete();
        }
        spouseFile.createNewFile();

        File guarantorFile = new File(guarantorFileName);
        if (guarantorFile.exists()) {
            guarantorFile.delete();
        }
        guarantorFile.createNewFile();

    }

    private void iconvAndFtp() {
        try {
            String customerFileNameIConv  = localFilePath + File.separator + "loancustomer.iconv";
            String spouseFileNameIConv  = localFilePath + File.separator + "loanspouse.iconv";
            String guarantorFileNameIConv  = localFilePath + File.separator + "loanguarantor.iconv";
            // 文件
            File customerFileIconv = new File(customerFileNameIConv);
            if(customerFileIconv.exists()){
                customerFileIconv.delete();
            }

            File spouseFileIconv = new File(spouseFileNameIConv);
            if(spouseFileIconv.exists()){
                spouseFileIconv.delete();
            }

            File guarantorFileIconv = new File(guarantorFileNameIConv);
            if (guarantorFileIconv.exists()) {
                guarantorFileIconv.delete();
            }


            Process p1=Runtime.getRuntime().exec("iconv -f "+localEncode+" -t "+targetEncode+" -c "+customerFileName+" -o "+customerFileNameIConv);
            Process p2=Runtime.getRuntime().exec("iconv -f "+localEncode+" -t "+targetEncode+" -c"+spouseFileName+" -o "+spouseFileNameIConv);
            Process p3=Runtime.getRuntime().exec("iconv -f "+localEncode+" -t "+targetEncode+" -c"+guarantorFileName+" -o "+guarantorFileNameIConv);

            FtpUtil ftp = null ;
            if(StringUtils.isNotEmpty(ftpIp)&&StringUtils.isNotEmpty(ftpName)&& StringUtils.isNotEmpty(ftpPassword)){
                ftp = new FtpUtil(ftpIp,ftpName,ftpPassword);
                ftp.connectServer();
            }else{
                log.error("上传失败！未连接到ftp:配置不全");
            }
            if(null!=ftp){
                boolean result = ftp.upload(localFilePath, ftpFilePath);
                log.info(result?"上传成功！":"上传失败！");
            }else{
                log.error("上传失败！未连接到ftp");
            }
        } catch (IOException e) {
            log.error("ftp上传文件失败："+e.getMessage(),e);
        }
    }


    private void loadProperties() {
        try {
            if (!FileUtil.isExistFilePath(globalProperties)) {
                globalProperties = RuleLoader.class.getClassLoader().getResource("globalConfig.properties").getPath().substring(1);
            }

            Properties prop = new Properties();
            prop.load(new InputStreamReader(new FileInputStream(globalProperties), "UTF-8"));

//            <property name="uploadPath" value="${file_root_path}" ></property>//上传文件根路径  ""
//            <property name="custConfig" value="${lend.cust.info}" ></property>//客户文件配置
//            <property name="spouseConfig" value="${lend.cust.spouse}" ></property>//配偶文件配置
//            <property name="guarantorConfig" value="${lend.cust.guarantor}" ></property> //担保人文件配置
//            <property name="syncType" value="${lend.sync.type}" ></property>//同步类型 1全量 2增量
//            <property name="localFilePath" value="${lend.file.localpath}" ></property>//本地文件路径  ""
//            <property name="delimiter" value="${lend.file.delimiter}" ></property> //分隔符 ,

            this.uploadPath = prop.getProperty("file_root_path", "").replace("yyyyMMdd",bizDate);
            this.custConfig = prop.getProperty("lend.cust.info", "").replace("yyyyMMdd", bizDate);
            this.spouseConfig = prop.getProperty("lend.cust.spouse", "").replace("yyyyMMdd", bizDate);
            this.guarantorConfig = prop.getProperty("lend.cust.guarantor", "").replace("yyyyMMdd", bizDate);
            this.syncType = prop.getProperty("lend.sync.type", "1");
            this.localFilePath = prop.getProperty("lend.file.localpath", "").replace("yyyyMMdd",bizDate);
            this.delimiter = prop.getProperty("lend.file.delimiter", ",");
            this.localEncode = prop.getProperty("lend.file.local.encode", "UTF-8");
            this.targetEncode = prop.getProperty("lend.file.target.encode", "GBK");

            this.ftpIp = prop.getProperty("lend.ftp.ip", "");
            this.ftpName = prop.getProperty("lend.ftp.name", "");
            this.ftpPassword = prop.getProperty("lend.ftp.password", "");
            this.ftpFilePath = prop.getProperty("lend.ftp.filepath", "").replace("yyyyMMdd", bizDate);

            //
            localFilePath = uploadPath + File.separator + localFilePath;
            if (!new File(localFilePath).exists()) {
                new File(localFilePath).mkdirs();
            }
        } catch (Exception e) {
            log.error("加载同步配置参数失败：" + e.getMessage(),e);
        }

    }

    private void setSyncProperties(Map<String,String> map)  throws Exception{

        log.info("更新同步配置参数\r\n");
        Properties prop = new Properties();

        prop.load(new InputStreamReader(new FileInputStream(globalProperties), "UTF-8"));

        for (Map.Entry<String,String> entry : map.entrySet()) {
            prop.setProperty(entry.getKey(), entry.getValue());
        }

        FileOutputStream fos = new FileOutputStream(globalProperties);
        prop.store(fos, null);
        fos.close();
        log.info("更新同步配置参数完成\r\n");
    }


}
