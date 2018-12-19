package banger.socket;

import banger.domain.config.AutoBaseField;
import banger.domain.config.AutoBaseTemplate;
import banger.domain.config.AutoFieldWrapper;
import banger.domain.enumerate.LoanProcessTypeEnum;
import banger.domain.enumerate.RepayPlanOption;
import banger.domain.enumerate.SocketCodeTypeEnum;
import banger.domain.enumerate.TableInputType;
import banger.domain.loan.LoanApplyInfo;
import banger.domain.loan.LoanRepayPlanInfoExtend;
import banger.framework.collection.DataRow;
import banger.framework.collection.DataTable;
import banger.framework.util.DateUtil;
import banger.framework.util.FormatUtil;
import banger.framework.util.StringUtil;
import banger.moduleIntf.IConfigModule;
import banger.moduleIntf.ISystemModule;
import banger.service.intf.IApplyInfoService;
import banger.service.intf.IRepayPlanInfoService;
import banger.util.SocketUtil;
import banger.util.constant.*;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created
 */
@Repository
public class SocketDemo {

    protected transient final Log log = LogFactory.getLog(getClass());
    @Value("${socket.ip}")
    private String socketIp;
    @Value("${socket.port}")
    private String socketPort;





    @Autowired
    private ISystemModule systemModule;

    @Autowired
    private IApplyInfoService applyInfoService;
    @Resource
    IConfigModule configModule;
    @Autowired
    private IRepayPlanInfoService repayPlanInfoService;

    //

    /**
     * 查询受托支付状态
     * @return
     */
    public Map<String,Object> getTrustedPayment(Map<String,String> map,Integer loanId){

        Map<String,Object> resultMap = new HashMap<String, Object>();
        try{

            Document document = sendSocketPost(loanId, Socket_99CMI0230054.getSocketCode(), Socket_99CMI0230054.getAllElement(),
                    map,map.get("受托编号"),map.get("支付ID"));
            if(StringUtil.isNotEmpty(document.getRootElement().element("Body").element("pay_state").getText())){
                resultMap.put("code","success");
                resultMap.put("data",document.getRootElement().element("Body").element("pay_state").getText());
                return resultMap;
            }
            resultMap.put("code","fail");
            resultMap.put("data","查询失败 支付状态为空");
        }catch (Exception e){
            log.error("syncTrustedPayment",e);
            resultMap.put("code","fail");
            resultMap.put("data","查询失败"+e.getMessage());
        }
        return resultMap;
    }



    //合同签订  //合同保存生成合同编码 生成借据编码  抵质押物保存生成抵质押物编码

    //1同步客户信息
        //对私客户信息同步接口-99CHNCMI2039
        //客户信息子表同步接口-99CHNCMI2002
    public String syncCustomerInfo(Integer loanId){
        try {
            Map<String,String> map = getLoanInfoMap(loanId);
            //借款人客户信息
            if(!"01".equals(map.get("个人信息.是否存量客户"))){
                Document document_99CHNCMI2039 = sendSocketPost(loanId, Socket_99CHNCMI2039.getSocketCode(), Socket_99CHNCMI2039.getAllElement(),
                        map,map.get("个人信息.客户编码"),map.get("个人信息.证件号码"));
                Document document_99CHNCMI2002 = sendSocketPost(loanId, Socket_99CHNCMI2002.getSocketCode(), Socket_99CHNCMI2002.getAllElement(),
                        map,map.get("个人信息.客户编码"),map.get("个人信息.证件号码"));
            }
        } catch (Exception e) {
            return "同步客户信息:"+e.getMessage();
        }
        return "success";
    }


    //2同步担保信息
        //是否有担保合同编码 是--> /****循环保证人****/
                                   //担保合同主表同步接口-99CHNCMI2006
                                   //保证人-99CHNCMI2009
                                  //担保合同签订、注销同步接口-99CHNCMI2035
    public String syncGuaranteeInfo(Integer loanId){
        try {
            Map<String,String> map = getLoanInfoMap(loanId);
            if(StringUtils.isNotBlank(MapUtils.getString(map, "保证人合同号"))){
                map.put("担保合同号",MapUtils.getString(map, "保证人合同号"));
                //查询保证人列表
                AutoBaseTemplate template = configModule.getAutoTemplateProvider().getTemplateListByTables(new String[]{"LBIZ_LOAN_GUARANTEE"}).get(0);
                DataTable datatable = applyInfoService.getDataTableByLoanId(template.getTableName(), LoanProcessTypeEnum.INVESTIGATE.type, loanId);
                if(datatable==null || datatable.rowSize()<=0){
                    return "没有添加担保人信息";
                }
                List<Map<String,String>> list = new ArrayList<Map<String, String>>();
                for (int r = 0; r < datatable.getRows().length; r++) {
                    Map<String,String> rowMap = new HashMap<String, String>();
                    template.setData(datatable.getRows()[r]);
                    List<AutoBaseField> fieldList = template.getFields();
                    for (int i = 0; i < fieldList.size(); i++) {
                        AutoFieldWrapper field = (AutoFieldWrapper)fieldList.get(i);
                        Object value = "";
                        if(null!=template.getData()){
                            value = banger.framework.reader.Reader.objectReader().getValue(template.getData(), field.getColumn());
                        }
                        String key = template.getName() + "." + field.getFieldName();
                        map.put(key, null!=value?value.toString():"");
                    }
                    //保证人客户信息
                    //担保人=抵押人 不用同步担保人客户信息，因为抵押人一定是正式客户 且优先同步抵押人，只需要把担保人客户号更新就好
                    if(!"01".equals(map.get("担保人.是否存量客户")) && checkCusInfo(map,loanId,2)){
                        Document document_99CHNCMI2039_1 = sendSocketPost(loanId,Socket_99CHNCMI2039.getSocketCode(),Socket_99CHNCMI2039_1.getAllElement(),map,
                                map.get("担保人.客户编码"),map.get("担保人.证件号码"));
                        Document document_99CHNCMI2002_1 = sendSocketPost(loanId, Socket_99CHNCMI2002.getSocketCode(), Socket_99CHNCMI2002_1.getAllElement(), map,
                                map.get("担保人.客户编码"),map.get("担保人.证件号码"));
                    }
                    boolean isExistGurId = true;
                    if(StringUtil.isNullOrEmpty(map.get("担保人.担保编码"))){
                        map.put("担保人.担保编码",map.get("担保人.客户编码"));
                        isExistGurId = false;
                    }
                    //中间表
                    Document document_99CHNCMI2042_1 = sendSocketPost(loanId,Socket_99CHNCMI2042.getSocketCode(),Socket_99CHNCMI2042_1.getAllElement(),map,
                            map.get("担保人.客户编码"),map.get("担保合同号"));
                    if(!isExistGurId){
                        //保证人
                        Document document_99CHNCMI2009 = sendSocketPost(loanId, Socket_99CHNCMI2009.getSocketCode(), Socket_99CHNCMI2009.getAllElement(), map,
                                map.get("担保人.客户编码"),map.get("担保人.证件号码"));
                    }

//                    rowMap.put("guaranty_id",map.get("担保人.客户编码"));
//                    rowMap.put("gage_type","10009");
//                    rowMap.put("used_amt",map.get("审批决议.决议金额"));
//                    list.add(rowMap);
                }
                 //担保合同主表同步接口
                Document document_99CHNCMI2006 = sendSocketPost(loanId, Socket_99CHNCMI2006.getSocketCode(), Socket_99CHNCMI2006.getAllElement(), map,
                        map.get("担保合同号"),map.get("担保人.客户编码"));
//                map.put("99CHNCMI2035", JSONArray.fromObject(list).toString());
//                map.put("担保方式","30");
                //担保合同签订、注销同步接口
//                Document document_99CHNCMI2035 = sendSocketPost(loanId, Socket_99CHNCMI2035.getSocketCode(), Socket_99CHNCMI2035.getAllElement(), map,
//                        map.get("担保合同号"),map.get("担保合同号"));
            }
        } catch (Exception e) {
            return "同步担保信息:"+e.getMessage();
        }
        return "success";
    }

        //是否有抵押合同编码 是--> //担保合同主表同步接口-99CHNCMI2006
                                    /****循环抵押品****/
                                   //抵押品基本信息-99CHNCMI2010
                                   //其他抵押品-99CHNCMI2020
                                   //担保合同与押品关系同步接口-99CHNCMI2042

                                   //担保合同签订、注销同步接口-99CHNCMI2035
        public String syncMortgageInfo(Integer loanId){
            try {
                Map<String,String> map = getLoanInfoMap(loanId);
                if(StringUtils.isNotBlank(MapUtils.getString(map, "抵押合同号"))){
                    map.put("担保合同号",MapUtils.getString(map, "抵押合同号"));
                    //查询抵押物列表 1.	抵押     2.	质押
                    AutoBaseTemplate template = configModule.getAutoTemplateProvider().getTemplateListByTables(new String[]{"LBIZ_PLEDGE_ITEM"}).get(0);
                    DataTable datatable = applyInfoService.getPledgeDataTableByLoanId(LoanProcessTypeEnum.INVESTIGATE.type, loanId, 1);
                    if(datatable==null || datatable.rowSize()<=0){
                        return "没有添加抵质押物";
                    }
                    List<Map<String,String>> list = new ArrayList<Map<String, String>>();
                    for (int r = 0; r < datatable.getRows().length; r++) {
                        Map<String,String> rowMap = new HashMap<String, String>();
                        template.setData(datatable.getRows()[r]);
                        List<AutoBaseField> fieldList = template.getFields();
                        for (int i = 0; i < fieldList.size(); i++) {
                            AutoFieldWrapper field = (AutoFieldWrapper)fieldList.get(i);
                            Object value = "";
                            if(null!=template.getData()){
                                value = banger.framework.reader.Reader.objectReader().getValue(template.getData(), field.getColumn());
                            }
                            //有日期 有率
                            if (null != value && "" != value) {
                                if(field.getType().equalsIgnoreCase("Date")) {
                                    value = FormatUtil.formatDate(value, "yyyy-MM-dd");
                                }
                                if(StringUtils.isNotBlank(field.getUnit())&&field.getUnit().equalsIgnoreCase("%")) {
                                    value = new BigDecimal(value.toString()).divide(new BigDecimal(100)).toString();
                                }
                            }


                            String key = template.getName() + "." + field.getFieldName();
                            map.put(key, null!=value?value.toString():"");
                        }
                        //TODO 抵押人筛选
                        //抵质押客户信息同步
                        //抵押物的抵押人存在可能是借款人或者是担保人，这时候再同步可能客户信息会出问题，所以做校验处理
                        if(!"01".equals(map.get("抵质押物.是否存量客户")) && checkCusInfo(map,loanId,1)){
                            Document document_99CHNCMI2039_2 = sendSocketPost(loanId,Socket_99CHNCMI2039.getSocketCode(),Socket_99CHNCMI2039_2.getAllElement(),map,
                                    map.get("抵质押物.客户编码"),map.get("抵质押物.身份证号码"));
                            Document document_99CHNCMI2002_2 = sendSocketPost(loanId, Socket_99CHNCMI2002.getSocketCode(), Socket_99CHNCMI2002_2.getAllElement(), map,
                                    map.get("抵质押物.客户编码"),map.get("抵质押物.身份证号码"));
                        }
                        //抵押品基本信息
                        Document document_99CHNCMI2010 = sendSocketPost(loanId, Socket_99CHNCMI2010.getSocketCode(), Socket_99CHNCMI2010.getAllElement(), map,
                                map.get("抵质押物.抵质押物编码"),map.get("抵质押物.客户编码"));
                        //其他抵押品
                        Document document_99CHNCMI2020 = sendSocketPost(loanId, Socket_99CHNCMI2020.getSocketCode(), Socket_99CHNCMI2020.getAllElement(), map,
                                map.get("抵质押物.抵质押物编码"),map.get("抵质押物.抵质押物编码"));
                        //担保合同与押品关系同步接口
                        map.put("抵质押担保合同.担保方式",MapUtils.getString(map, "抵押担保合同.担保方式"));
                        Document document_99CHNCMI2042 = sendSocketPost(loanId, Socket_99CHNCMI2042.getSocketCode(), Socket_99CHNCMI2042.getAllElement(), map,
                                map.get("抵质押物.抵质押物编码"),map.get("抵质押物.客户编码"));
                        //添加35的row
                        rowMap.put("guaranty_id",map.get("抵质押物.抵质押物编码"));
                        rowMap.put("gage_type",map.get("抵质押物.抵质押物类型"));
                        rowMap.put("used_amt",map.get("审批决议.决议金额"));
                        list.add(rowMap);
                    }
                    Document document_99CHNCMI2006 = sendSocketPost(loanId, Socket_99CHNCMI2006.getSocketCode(), Socket_99CHNCMI2006_1.getAllElement(),
                              map,map.get("担保合同号"),map.get("抵质押物.客户编码"));
//                    map.put("99CHNCMI2035", JSONArray.fromObject(list).toString());
//                    map.put("担保方式","10");
//                    Document document_99CHNCMI2035 = sendSocketPost(loanId, Socket_99CHNCMI2035.getSocketCode(), Socket_99CHNCMI2035.getAllElement(), map,
//                            map.get("担保合同号"),map.get("担保合同号"));

//                    for (int r = 0; r < datatable.getRows().length; r++) {
//                        template.setData(datatable.getRows()[r]);
//                        List<AutoBaseField> fieldList = template.getFields();
//                        for (int i = 0; i < fieldList.size(); i++) {
//                            AutoFieldWrapper field = (AutoFieldWrapper)fieldList.get(i);
//                            Object value = "";
//                            if(null!=template.getData()){
//                                value = banger.framework.reader.Reader.objectReader().getValue(template.getData(), field.getColumn());
//                            }
//                            //有日期 有率
//                            if (null != value && "" != value) {
//                                if(field.getType().equalsIgnoreCase("Date")) {
//                                    value = FormatUtil.formatDate(value, "yyyy-MM-dd");
//                                }
//                                if(StringUtils.isNotBlank(field.getUnit())&&field.getUnit().equalsIgnoreCase("%")) {
//                                    value = new BigDecimal(value.toString()).divide(new BigDecimal(100)).toString();
//                                }
//                            }
//
//
//                            String key = template.getName() + "." + field.getFieldName();
//                            map.put(key, null!=value?value.toString():"");
//                        }
//                        //担保合同与押品关系同步接口
//                        map.put("抵质押担保合同.担保方式",MapUtils.getString(map, "抵押担保合同.担保方式"));
//                        Document document_99CHNCMI2042 = sendSocketPost(loanId, Socket_99CHNCMI2042.getSocketCode(), Socket_99CHNCMI2042.getAllElement(), map,
//                                map.get("抵质押物.抵质押物编码"),map.get("抵质押物.客户编码"));
//
//                    }


                }
            } catch (Exception e) {
                return "抵押担保信息:"+e.getMessage();
            }
            return "success";
        }

        //是否有质押合同编码 是--> //担保合同主表同步接口-99CHNCMI2006
                                    /****循环质押品****/
                                   //质押品基本信息-99CHNCMI2011
                                   //其他质押品-99CHNCMI2032
                                   //担保合同与押品关系同步接口-99CHNCMI2042
                                   //担保合同签订、注销同步接口-99CHNCMI2035
        public String syncPledgeInfo(Integer loanId){
            try {
                Map<String,String> map = getLoanInfoMap(loanId);
                if(StringUtils.isNotBlank(MapUtils.getString(map, "质押合同号"))){
                    map.put("担保合同号",MapUtils.getString(map, "质押合同号"));
                    //查询抵押物列表 1.	抵押     2.	质押
                    AutoBaseTemplate template = configModule.getAutoTemplateProvider().getTemplateListByTables(new String[]{"LBIZ_PLEDGE_ITEM"}).get(0);
                    DataTable datatable = applyInfoService.getPledgeDataTableByLoanId(LoanProcessTypeEnum.INVESTIGATE.type, loanId, 2);
                    if(datatable==null || datatable.rowSize()<=0){
                        return "没有添加质押物";
                    }
                    List<Map<String,String>> list = new ArrayList<Map<String, String>>();
                    for (int r = 0; r < datatable.getRows().length; r++) {
                        Map<String,String> rowMap = new HashMap<String, String>();
                        template.setData(datatable.getRows()[r]);
                        List<AutoBaseField> fieldList = template.getFields();
                        for (int i = 0; i < fieldList.size(); i++) {
                            AutoFieldWrapper field = (AutoFieldWrapper)fieldList.get(i);
                            Object value = "";
                            if(null!=template.getData()){
                                value = banger.framework.reader.Reader.objectReader().getValue(template.getData(), field.getColumn());
                            }
                            //有日期 有率
                            if (null != value && "" != value) {
                                if(field.getType().equalsIgnoreCase("Date")) {
                                    value = FormatUtil.formatDate(value, "yyyy-MM-dd");
                                }
                                if(StringUtils.isNotBlank(field.getUnit())&&field.getUnit().equalsIgnoreCase("%")) {
                                    value = new BigDecimal(value.toString()).divide(new BigDecimal(100)).toString();
                                }
                            }
                            String key = template.getName() + "." + field.getFieldName();
                            map.put(key, null!=value?value.toString():"");
                        }
                        //TODO 质押人筛选
//                        Document document_99CHNCMI2006 = sendSocketPost(loanId, Socket_99CHNCMI2006.getSocketCode(), Socket_99CHNCMI2006.getAllElement(), map);
                        //质押物客户
                        //抵押物的抵押人存在可能是借款人或者是担保人，这时候再同步可能客户信息会出问题，所以做校验处理
                        if(!"01".equals(map.get("抵质押物.是否存量客户")) && checkCusInfo(map,loanId,1)){
                            Document document_99CHNCMI2039_2 = sendSocketPost(loanId,Socket_99CHNCMI2039.getSocketCode(),Socket_99CHNCMI2039_2.getAllElement(),map,
                                    map.get("抵质押物.客户编码"),map.get("抵质押物.身份证号码"));
                            Document document_99CHNCMI2002_2 = sendSocketPost(loanId, Socket_99CHNCMI2002.getSocketCode(), Socket_99CHNCMI2002_2.getAllElement(), map,
                                    map.get("抵质押物.客户编码"),map.get("抵质押物.身份证号码"));
                        }
                        //抵押品基本信息
                        Document document_99CHNCMI2011 = sendSocketPost(loanId, Socket_99CHNCMI2011.getSocketCode(), Socket_99CHNCMI2011.getAllElement(), map,
                                map.get("抵质押物.抵质押物编码"),map.get("抵质押物.客户编码"));
                        //其他抵押品
                        Document document_99CHNCMI2020 = sendSocketPost(loanId, Socket_99CHNCMI2020.getSocketCode(), Socket_99CHNCMI2020.getAllElement(), map,
                                map.get("抵质押物.抵质押物编码"),map.get("抵质押物.抵质押物编码"));
                        //担保合同与押品关系同步接口
                        map.put("抵质押担保合同.担保方式",MapUtils.getString(map, "质押担保合同.担保方式"));
                        Document document_99CHNCMI2042 = sendSocketPost(loanId, Socket_99CHNCMI2042.getSocketCode(), Socket_99CHNCMI2042.getAllElement(), map,
                                map.get("抵质押物.抵质押物编码"),map.get("抵质押物.担保合同号"));
                        //添加35的row
//                        rowMap.put("guaranty_id",map.get("抵质押物.抵质押物编码"));
//                        rowMap.put("gage_type",map.get("抵质押物.抵质押物类型"));
//                        rowMap.put("used_amt",map.get("审批决议.决议金额"));
//                        list.add(rowMap);
                    }
                      Document document_99CHNCMI2006 = sendSocketPost(loanId, Socket_99CHNCMI2006.getSocketCode(), Socket_99CHNCMI2006_1.getAllElement(), map,
                              map.get("担保合同号"),map.get("抵质押物.客户编码"));
//                    map.put("99CHNCMI2035", JSONArray.fromObject(list).toString());
//                    map.put("担保方式","20");
//                    Document document_99CHNCMI2035 = sendSocketPost(loanId, Socket_99CHNCMI2035.getSocketCode(), Socket_99CHNCMI2035.getAllElement(), map,
//                            map.get("担保合同号"),map.get("担保合同号"));
//                    for (int r = 0; r < datatable.getRows().length; r++) {
//                        template.setData(datatable.getRows()[r]);
//                        List<AutoBaseField> fieldList = template.getFields();
//                        for (int i = 0; i < fieldList.size(); i++) {
//                            AutoFieldWrapper field = (AutoFieldWrapper)fieldList.get(i);
//                            Object value = "";
//                            if(null!=template.getData()){
//                                value = banger.framework.reader.Reader.objectReader().getValue(template.getData(), field.getColumn());
//                            }
//                            //有日期 有率
//                            if (null != value && "" != value) {
//                                if(field.getType().equalsIgnoreCase("Date")) {
//                                    value = FormatUtil.formatDate(value, "yyyy-MM-dd");
//                                }
//                                if(StringUtils.isNotBlank(field.getUnit())&&field.getUnit().equalsIgnoreCase("%")) {
//                                    value = new BigDecimal(value.toString()).divide(new BigDecimal(100)).toString();
//                                }
//                            }
//                            String key = template.getName() + "." + field.getFieldName();
//                            map.put(key, null!=value?value.toString():"");
//                        }
//                        //担保合同与押品关系同步接口
//                        map.put("抵质押担保合同.担保方式",MapUtils.getString(map, "质押担保合同.担保方式"));
//                        Document document_99CHNCMI2042 = sendSocketPost(loanId, Socket_99CHNCMI2042.getSocketCode(), Socket_99CHNCMI2042.getAllElement(), map,
//                                map.get("抵质押物.抵质押物编码"),map.get("抵质押物.担保合同号"));
//                    }

                }
            } catch (Exception e) {
                return "质押担保信息:"+e.getMessage();
            }
            return "success";
        }

    /**
     * 抵押物 补录更新
     * @param
     * @return
     */
    public String updatePledg(Integer id){

        AutoBaseTemplate template = configModule.getAutoTemplateProvider().getTemplateListByTables(new String[]{"LBIZ_PLEDGE_ITEM"}).get(0);
        DataTable dataTable = applyInfoService.getPledgeDataTableById(id);
        Integer loanId = (Integer) dataTable.getColumns()[1].getValues()[0];
        Map<String,String> map = getLoanInfoMap(loanId);
        for (int r = 0; r < dataTable.getRows().length; r++) {
            template.setData(dataTable.getRows()[r]);
            List<AutoBaseField> fieldList = template.getFields();
            for (int i = 0; i < fieldList.size(); i++) {
                AutoFieldWrapper field = (AutoFieldWrapper) fieldList.get(i);
                Object value = "";
                if (null != template.getData()) {
                    value = banger.framework.reader.Reader.objectReader().getValue(template.getData(), field.getColumn());
                }
                //有日期 有率
                if (null != value && "" != value) {
                    if(field.getType().equalsIgnoreCase("Date")) {
                        value = FormatUtil.formatDate(value, "yyyy-MM-dd");
                    }
                    if(StringUtils.isNotBlank(field.getUnit())&&field.getUnit().equalsIgnoreCase("%")) {
                        value = new BigDecimal(value.toString()).divide(new BigDecimal(100)).toString();
                    }
                }

                String key = template.getName() + "." + field.getFieldName();
                map.put(key, null != value ? value.toString() : "");
            }
            try {
                Document document_99CHNCMI2010 = sendSocketPost(loanId, Socket_99CHNCMI2010.getSocketCode(), Socket_99CHNCMI2010.getAllElement(), map,
                        map.get("抵质押物.抵质押物编码"),map.get("抵质押物.客户编码"));
            } catch (Exception e) {
                return "抵押物补录更新失败"+e.getMessage();
            }
        }
        return "success";
    }


    //3合同信息同步
        //借款合同主表同步接口-99CHNCMI2003
        /****如果有担保合同****/
        //业务合同与担保关系同步接口-99CHNCMI2005
        //签订、注销合同同步接口-99CHNCMI2034
        public String syncContractInfo(Integer loanId){
            try {
//                释放顺序号	抵押1 质押2 担保3
                int releaseNo = 0;
                Map<String,String> map = getLoanInfoMap(loanId);
                Document document_99CHNCMI2003 = sendSocketPost(loanId, Socket_99CHNCMI2003.getSocketCode(), Socket_99CHNCMI2003.getAllElement(), map, map.get("合同号"),map.get("个人信息.客户编码"));

                if(StringUtils.isNotBlank(MapUtils.getString(map, "抵押合同号"))){
                    releaseNo+=1;
                    map.put("释放顺序号",String.valueOf(releaseNo));
                    map.put("担保方式",MapUtils.getString(map, "抵押担保合同.担保方式"));
                    map.put("担保合同号",MapUtils.getString(map, "抵押合同号"));
                    Document document_99CHNCMI2005 = sendSocketPost(loanId, Socket_99CHNCMI2005.getSocketCode(), Socket_99CHNCMI2005.getAllElement(), map, map.get("担保合同号"),map.get("合同号"));
                }
                if(StringUtils.isNotBlank(MapUtils.getString(map, "质押合同号"))){
                    releaseNo+=1;
                    map.put("释放顺序号",String.valueOf(releaseNo));
                    map.put("担保方式",MapUtils.getString(map, "质押担保合同.担保方式"));
                    map.put("担保合同号",MapUtils.getString(map, "质押合同号"));
                    Document document_99CHNCMI2005 = sendSocketPost(loanId, Socket_99CHNCMI2005.getSocketCode(), Socket_99CHNCMI2005.getAllElement(), map, map.get("担保合同号"),map.get("合同号"));

                }
                if(StringUtils.isNotBlank(MapUtils.getString(map, "保证人合同号"))){
                    releaseNo+=1;
                    map.put("释放顺序号",String.valueOf(releaseNo));
                    map.put("担保方式",MapUtils.getString(map, "保证担保合同.担保方式"));
                    map.put("担保合同号",MapUtils.getString(map, "保证人合同号"));
                    Document document_99CHNCMI2005 = sendSocketPost(loanId, Socket_99CHNCMI2005.getSocketCode(), Socket_99CHNCMI2005.getAllElement(), map, map.get("担保合同号"),map.get("合同号"));
                }
                //TODO
//                Document document_99CHNCMI2034 = sendSocketPost(loanId, Socket_99CHNCMI2034.getSocketCode(), Socket_99CHNCMI2034.getAllElement(),map, map.get("合同号"),map.get("合同号"));
            } catch (Exception e) {
                return "主合同信息同步:"+e.getMessage();
            }
            return "success";
        }


    //放款授权
        //同步贷款台账-99CHNCMI2038
        //同步放款授权-99CHNCMI1033
    public String syncLoanAuthorizedInfo(Integer loanId){
        try {
            Map<String, String> map = getLoanInfoMap(loanId);
            setAuthorizedCloumn(map);
            Document document_99CHNCMI2038 = sendSocketPost(loanId, Socket_99CHNCMI2038.getSocketCode(), Socket_99CHNCMI2038.getAllElement(), map, map.get("借据号"),map.get("合同号"));
            Document document_99CHNCMI1033 = sendSocketPost(loanId, Socket_99CHNCMI1033.getSocketCode(), Socket_99CHNCMI1033.getAllElement(), map, map.get("授权码"),map.get("个人信息.客户编码"));
            //台帐同步成功 则修改合同累计发放金额，合同可用余额
            Document document_99CHNCMI2003_1 = sendSocketPost(loanId, Socket_99CHNCMI2003_1.getSocketCode(), Socket_99CHNCMI2003_1.getAllElement(), map, map.get("合同号"),map.get("个人信息.客户编码"));

        } catch (Exception e) {
            e.printStackTrace();
            return "贷款授权同步:"+e.getMessage();
        }
        return "success";
    }


    //同步受托支付-99CHNCMI1043
    public String syncTrustedPayment(Integer loanId){
        try {
            Map<String,String> map = getLoanInfoMap(loanId);
            if("02".equals(MapUtils.getString(map,"放贷信息.放款方式"))){
                //查询受托支付列表
                AutoBaseTemplate template = configModule.getAutoTemplateProvider().getTemplateListByTables(new String[]{"LBIZ_TRUSTED_PAYMENY"}).get(0);
                DataTable datatable = applyInfoService.getDataTableByLoanId(template.getTableName(), LoanProcessTypeEnum.LOAN.type, loanId);
                if(datatable==null || datatable.rowSize()<=0){
                    return "还款方式为自主支付";
                }
                for (int r = 0; r < datatable.getRows().length; r++) {
                    template.setData(datatable.getRows()[r]);
                    List<AutoBaseField> fieldList = template.getFields();
                    //追加支付ID和支付状态
                    setPaymentIdAndStatus(fieldList);
                    for (int i = 0; i < fieldList.size(); i++) {
                        AutoFieldWrapper field = (AutoFieldWrapper)fieldList.get(i);
                        Object value = null;
                        if(null!=template.getData()){
                            value = banger.framework.reader.Reader.objectReader().getValue(template.getData(), field.getColumn());
                        }
                        String key = template.getName() + "." + field.getFieldName();
                        map.put(key, null!=value?value.toString():"");
                    }
                    Document document_99CHNCMI1043 = sendSocketPost(loanId, Socket_99CHNCMI1043.getSocketCode(), Socket_99CHNCMI1043.getAllElement(), map,
                            map.get("受托支付.支付ID号"),map.get("借据号"));
                }
        }
        } catch (Exception e) {
            return "受托支付:"+e.getMessage();
        }
        return "success";
    }

    //合同注销 //贷款直接拒绝掉  暂时不能单独注销担保合同
        //签订、注销合同同步接口-99CHNCMI2034
        //担保合同签订、注销同步接口-99CHNCMI2035
    public String cancelContractInfo(Integer loanId){
        Map<String,String> map = getLoanInfoMap(loanId);
        map.put("合同状态","300");
        map.put("场景","2");
        try {
            Document document_99CHNCMI2034 = sendSocketPost(loanId, Socket_99CHNCMI2034.getSocketCode(), Socket_99CHNCMI2034.getAllElement(), map,
                    map.get("合同号"),map.get("合同号"));
        } catch (Exception e) {
            return "合同注销失败:"+e.getMessage();
        }
        try {
//            Document document_99CHNCMI2035 = sendSocketPost(loanId, Socket_99CHNCMI2035.getSocketCode(), Socket_99CHNCMI2035.getAllElement(), map,
//                    map.get("担保合同号"),map.get("担保合同号"));
        } catch (Exception e) {
            return "担保合同注销失败:"+e.getMessage();
        }
        return "success";
    }


    //撤销授权
        //授权撤销-99CHNCMI2049
    public String cancelLoanAccountInfo(Integer loanId){
        try {
            //TODO 首先要判断是否已放款完成
            Map<String,String> map = getLoanInfoMap(loanId);
                Document document_99CHNCMI2049 = sendSocketPost(loanId, Socket_99CHNCMI2049.getSocketCode(), Socket_99CHNCMI2049.getAllElement(), map,
                        map.get("授权码"),map.get("授权码"));
        } catch (Exception e) {
            return e.getMessage();
        }
        return "success";
    }

//        判断贷款放款是否已经完成放款查询-99CHNCMI4041
    public Map<String,Object> selectLoanAccountInfo(Integer loanId){
        Map<String,Object> result = new HashMap<String, Object>();
        try {
            Map<String,String> map = getLoanInfoMap(loanId);
            Document document_99CHNCMI4041 = sendSocketPost(loanId, Socket_99CHNCMI4041.getSocketCode(), Socket_99CHNCMI4041.getAllElement(), map, map.get("借据号"), map.get("借据号"));
            if(StringUtil.isNotEmpty(document_99CHNCMI4041.getRootElement().element("Body").element("loan_account").getText()) && StringUtil.isNumber(document_99CHNCMI4041.getRootElement().element("Body").element("loan_account").getText())){
                result.put("code","success");
                result.put("data",document_99CHNCMI4041.getRootElement().element("Body").element("loan_account").getText());
                return result;
            }else{
                result.put("code","fail");
                result.put("data","未查到账号");
                return result;
            }
        } catch (Exception e) {
            log.error(e);
            result.put("code","fail");
            result.put("data","核心贷款账号查询报错:"+e.getMessage());
            return result;
        }
    }
    //核心查询接口
    public String queryCusInfo(Integer loanId,Map<String,String> map,SocketCodeTypeEnum socketCodeTypeEnum){
        try {
            StringBuffer v = new StringBuffer("banger.util.constant");
            Class<?> cla = Class.forName(v.append(".").append(socketCodeTypeEnum.socketClassName).toString());
            addSocketHeadMap(map);
            Document document = sendSocketPost2Query(loanId, cla.getMethod("getSocketCode").invoke(null).toString(), (List<BaseXmlBeanEnum>)cla.getMethod("getAllElement").invoke(null), map);
            return bodyElement2Json(document);
        }catch (Exception e){
            log.error(e);
            JSONObject json = new JSONObject();
            json.put("success",false);
            json.put("code",-1);
            json.put("msg",socketCodeTypeEnum.socketClassName+"查询失败:"+e.getMessage());
            return json.toString();
        }
    }

    //核心查询接口
    public String queryCusInfo1(Integer loanId,Map<String,String> map,SocketCodeTypeEnum socketCodeTypeEnum){
        try {
            StringBuffer v = new StringBuffer("banger.util.constant");
            Class<?> cla = Class.forName(v.append(".").append(socketCodeTypeEnum.socketClassName).toString());
            addSocketHeadMap(map);
            Document document = sendSocketPost2Query(loanId, cla.getMethod("getSocketCode").invoke(null).toString(), (List<BaseXmlBeanEnum>)cla.getMethod("getAllElement").invoke(null), map);
            return xml2json(document.asXML());
        }catch (Exception e){
            log.error(e);
            JSONObject json = new JSONObject();
            json.put("success",false);
            json.put("code",-1);
            json.put("msg",socketCodeTypeEnum.socketClassName+"查询失败:"+e.getMessage());
            return json.toString();
        }
    }

    public static String xml2json(String xmlString) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        JSON json = xmlSerializer.read(xmlString);
        return json.toString(1);
    }

    //省信贷查询接口
    public String relatedDataQuery(Integer loanId,Map<String,String> map,SocketCodeTypeEnum socketCodeTypeEnum){
        try {
            StringBuffer v = new StringBuffer("banger.util.constant");
            Class<?> cla = Class.forName(v.append(".").append(socketCodeTypeEnum.socketClassName).toString());
            addSocketHeadMap(map);
            Document document = sendSocketPost2Query(loanId, cla.getMethod("getSocketCode").invoke(null).toString(), (List<BaseXmlBeanEnum>)cla.getMethod("getAllElement").invoke(null), map);
            JSONObject json = new JSONObject();
            getElement(document.getRootElement().element("Body"),json);
            String code = json.getString("rep_code");
            if("00000".equals(code)){
                json.put("success",true);
            }else{
                json.put("success",false);
                json.put("rep_msg",json.getString("rep_msg"));
            }
            return json.toString();
        }catch (Exception e){
            JSONObject json = new JSONObject();
            json.put("success",false);
            json.put("rep_code",-1);
            json.put("rep_msg",socketCodeTypeEnum.socketClassName+"查询失败:"+e.getMessage());
            return json.toString();
        }
    }
    //放款成功、还款、冲正、出入库通知查询-99CHNCMI3036
//    public


    //返回报文的body里xml转json
    public String bodyElement2Json(Document document) throws Exception{
        try {
            JSONObject json = new JSONObject();
            getElement(document.getRootElement().element("Body"),json);
            String code = json.getString("code");
            if("0000".equals(code)||"00000".equals(code)){
                json.put("success",true);
            }else{
                json.put("success",false);
                json.put("msg",json.getString("msg"));
            }
            return json.toString();
        }catch (Exception e){
            throw new Exception("解析失败</br>"+e.getMessage());
        }
    }

    public String getElement(Element element,JSONObject jsonObject){
        Iterator<Element> iterator = element.elementIterator();
        while (iterator.hasNext()){
            Element e = iterator.next();
            jsonObject.put(e.getName(),e.getStringValue().trim());
            if(e.elements().size()>0){
                JSONObject json = new JSONObject();
                getElement(e,json);
            }else{
                jsonObject.put(e.getName(),e.getStringValue().trim());
            }
        }
        return jsonObject.toString();
    }
    //合同注销接口
    //借款合同主表同步接口-99CHNCMI2003 opt-->U cont_state-->300
    //业务合同与担保关系同步接口-99CHNCMI2005 opt-->U  guar_cont_status-->104
    //担保合同主表同步接口-99CHNCMI2006 opt-->U guar_cont_state-->104
    public String cancelContract(Integer loanId){
        try {
//                释放顺序号	抵押1 质押2 担保3
            int releaseNo = 0;
            Map<String,String> map = getLoanInfoMap(loanId);
            map.put("操作类型","U");
            map.put("合同状态","300");
            Document document_99CHNCMI2003 = sendSocketPost(loanId, Socket_99CHNCMI2003.getSocketCode(), Socket_99CHNCMI2003.getAllElement(), map, map.get("合同号"),map.get("个人信息.客户编码"));
            map.put("担保合同状态","104");
            if(StringUtils.isNotBlank(MapUtils.getString(map, "抵押合同号"))){
                releaseNo+=1;
                map.put("释放顺序号",String.valueOf(releaseNo));
                map.put("担保方式",MapUtils.getString(map, "抵押担保合同.担保方式"));
                map.put("担保合同号",MapUtils.getString(map, "抵押合同号"));
                Document document_99CHNCMI2005 = sendSocketPost(loanId, Socket_99CHNCMI2005.getSocketCode(), Socket_99CHNCMI2005.getAllElement(), map, map.get("担保合同号"),map.get("合同号"));
            }
            if(StringUtils.isNotBlank(MapUtils.getString(map, "质押合同号"))){
                releaseNo+=1;
                map.put("释放顺序号",String.valueOf(releaseNo));
                map.put("担保方式",MapUtils.getString(map, "质押担保合同.担保方式"));
                map.put("担保合同号",MapUtils.getString(map, "质押合同号"));
                Document document_99CHNCMI2005 = sendSocketPost(loanId, Socket_99CHNCMI2005.getSocketCode(), Socket_99CHNCMI2005.getAllElement(), map, map.get("担保合同号"),map.get("合同号"));

            }
            if(StringUtils.isNotBlank(MapUtils.getString(map, "保证人合同号"))){
                releaseNo+=1;
                map.put("释放顺序号",String.valueOf(releaseNo));
                map.put("担保方式",MapUtils.getString(map, "保证担保合同.担保方式"));
                map.put("担保合同号",MapUtils.getString(map, "保证人合同号"));
                Document document_99CHNCMI2005 = sendSocketPost(loanId, Socket_99CHNCMI2005.getSocketCode(), Socket_99CHNCMI2005.getAllElement(), map, map.get("担保合同号"),map.get("合同号"));
            }
            //抵押合同
            if(StringUtils.isNotBlank(MapUtils.getString(map, "抵押合同号"))) {
                map.put("担保合同号", MapUtils.getString(map, "抵押合同号"));
                //查询抵押物列表 1.	抵押     2.	质押
                AutoBaseTemplate template = configModule.getAutoTemplateProvider().getTemplateListByTables(new String[]{"LBIZ_PLEDGE_ITEM"}).get(0);
                DataTable datatable = applyInfoService.getPledgeDataTableByLoanId(LoanProcessTypeEnum.INVESTIGATE.type, loanId, 1);
                if (datatable == null || datatable.rowSize() <= 0) {
                    return "没有添加抵质押物";
                }
                List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                for (int r = 0; r < datatable.getRows().length; r++) {
                    Map<String, String> rowMap = new HashMap<String, String>();
                    template.setData(datatable.getRows()[r]);
                    List<AutoBaseField> fieldList = template.getFields();
                    for (int i = 0; i < fieldList.size(); i++) {
                        AutoFieldWrapper field = (AutoFieldWrapper) fieldList.get(i);
                        Object value = "";
                        if (null != template.getData()) {
                            value = banger.framework.reader.Reader.objectReader().getValue(template.getData(), field.getColumn());
                        }
                        //有日期 有率
                        if (null != value && "" != value) {
                            if (field.getType().equalsIgnoreCase("Date")) {
                                value = FormatUtil.formatDate(value, "yyyy-MM-dd");
                            }
                            if (StringUtils.isNotBlank(field.getUnit()) && field.getUnit().equalsIgnoreCase("%")) {
                                value = new BigDecimal(value.toString()).divide(new BigDecimal(100)).toString();
                            }
                        }


                        String key = template.getName() + "." + field.getFieldName();
                        map.put(key, null != value ? value.toString() : "");
                    }
                    //担保合同与押品关系同步接口
                    map.put("抵质押担保合同.担保方式", MapUtils.getString(map, "抵押担保合同.担保方式"));
                    Document document_99CHNCMI2042 = sendSocketPost(loanId, Socket_99CHNCMI2042.getSocketCode(), Socket_99CHNCMI2042.getAllElement(), map,
                            map.get("抵质押物.抵质押物编码"), map.get("抵质押物.客户编码"));
                }
                Document document_99CHNCMI2006 = sendSocketPost(loanId, Socket_99CHNCMI2006.getSocketCode(), Socket_99CHNCMI2006_1.getAllElement(),
                        map, map.get("担保合同号"), map.get("抵质押物.客户编码"));
            }


            //质押合同
            if(StringUtils.isNotBlank(MapUtils.getString(map, "质押合同号"))){
                map.put("担保合同号",MapUtils.getString(map, "质押合同号"));
                //查询抵押物列表 1.	抵押     2.	质押
                AutoBaseTemplate template = configModule.getAutoTemplateProvider().getTemplateListByTables(new String[]{"LBIZ_PLEDGE_ITEM"}).get(0);
                DataTable datatable = applyInfoService.getPledgeDataTableByLoanId(LoanProcessTypeEnum.INVESTIGATE.type, loanId, 2);
                if(datatable==null || datatable.rowSize()<=0){
                    return "没有添加质押物";
                }
                List<Map<String,String>> list = new ArrayList<Map<String, String>>();
                for (int r = 0; r < datatable.getRows().length; r++) {
                    Map<String,String> rowMap = new HashMap<String, String>();
                    template.setData(datatable.getRows()[r]);
                    List<AutoBaseField> fieldList = template.getFields();
                    for (int i = 0; i < fieldList.size(); i++) {
                        AutoFieldWrapper field = (AutoFieldWrapper)fieldList.get(i);
                        Object value = "";
                        if(null!=template.getData()){
                            value = banger.framework.reader.Reader.objectReader().getValue(template.getData(), field.getColumn());
                        }
                        //有日期 有率
                        if (null != value && "" != value) {
                            if(field.getType().equalsIgnoreCase("Date")) {
                                value = FormatUtil.formatDate(value, "yyyy-MM-dd");
                            }
                            if(StringUtils.isNotBlank(field.getUnit())&&field.getUnit().equalsIgnoreCase("%")) {
                                value = new BigDecimal(value.toString()).divide(new BigDecimal(100)).toString();
                            }
                        }
                        String key = template.getName() + "." + field.getFieldName();
                        map.put(key, null!=value?value.toString():"");
                    }
                    //担保合同与押品关系同步接口
                    map.put("抵质押担保合同.担保方式",MapUtils.getString(map, "质押担保合同.担保方式"));
                    Document document_99CHNCMI2042 = sendSocketPost(loanId, Socket_99CHNCMI2042.getSocketCode(), Socket_99CHNCMI2042.getAllElement(), map,
                            map.get("抵质押物.抵质押物编码"),map.get("抵质押物.担保合同号"));
                }
                Document document_99CHNCMI2006 = sendSocketPost(loanId, Socket_99CHNCMI2006.getSocketCode(), Socket_99CHNCMI2006_1.getAllElement(), map,
                        map.get("担保合同号"),map.get("抵质押物.客户编码"));
            }

            //保证合同
            if(StringUtils.isNotBlank(MapUtils.getString(map, "保证人合同号"))){
                map.put("担保合同号",MapUtils.getString(map, "保证人合同号"));
                //查询保证人列表
                AutoBaseTemplate template = configModule.getAutoTemplateProvider().getTemplateListByTables(new String[]{"LBIZ_LOAN_GUARANTEE"}).get(0);
                DataTable datatable = applyInfoService.getDataTableByLoanId(template.getTableName(), LoanProcessTypeEnum.INVESTIGATE.type, loanId);
                if(datatable==null || datatable.rowSize()<=0){
                    return "没有添加担保人信息";
                }
                List<Map<String,String>> list = new ArrayList<Map<String, String>>();
                for (int r = 0; r < datatable.getRows().length; r++) {
                    Map<String,String> rowMap = new HashMap<String, String>();
                    template.setData(datatable.getRows()[r]);
                    List<AutoBaseField> fieldList = template.getFields();
                    for (int i = 0; i < fieldList.size(); i++) {
                        AutoFieldWrapper field = (AutoFieldWrapper)fieldList.get(i);
                        Object value = "";
                        if(null!=template.getData()){
                            value = banger.framework.reader.Reader.objectReader().getValue(template.getData(), field.getColumn());
                        }
                        String key = template.getName() + "." + field.getFieldName();
                        map.put(key, null!=value?value.toString():"");
                    }
                    if(StringUtil.isNullOrEmpty(map.get("担保人.担保编码"))){
                        map.put("担保人.担保编码",map.get("担保人.客户编码"));
                    }
                    //中间表
                    Document document_99CHNCMI2042_1 = sendSocketPost(loanId,Socket_99CHNCMI2042.getSocketCode(),Socket_99CHNCMI2042_1.getAllElement(),map,
                            map.get("担保人.客户编码"),map.get("担保合同号"));
                }
                //担保合同主表同步接口
                Document document_99CHNCMI2006 = sendSocketPost(loanId, Socket_99CHNCMI2006.getSocketCode(), Socket_99CHNCMI2006.getAllElement(), map,
                        map.get("担保合同号"),map.get("担保人.客户编码"));
            }

        } catch (Exception e) {
            return "合同注销失败:"+e.getMessage();
        }
        return "success";

    }



    //半小时查询一次更新 核心贷款账号查询-99CHNCMI4041
    //

    /**
     * 担保品出库入库 担保品出入库、撤销-99CHNCMI1037
     * @param loanId
     * @param guarWay 1 抵押物  2 质押物
     * @param type 1 入库  2 出库 3 撤销
     * @return
     */
    public String operationCollateral(Integer loanId,String code,String guarWay,String type){
            Map<String,String> map = getLoanInfoMap(loanId);
            map.put("担保编号",code);
            map.put("抵押/质押",guarWay);
            map.put("类型",type);
        try {
            Document document_99CHNCMI1037 = sendSocketPost(loanId, Socket_99CHNCMI1037.getSocketCode(), Socket_99CHNCMI1037.getAllElement(), map,map.get("担保编号"),map.get("担保编号"));
        } catch (Exception e) {
//            log.error("operationCollateral",e);
//            return "担保品操作失败 guarWay="+guarWay+" , type="+type +":"+e.getMessage();
            return e.getMessage();
        }
        return "success";
    }




    public Document sendSocketPost(Integer loanId, String socketCode, List<BaseXmlBeanEnum> bodyList, Map<String, String> map,String codeNum,String codeNumTwo) throws Exception {

        String sendXml = "", returnMassage = "", remark = "success";
        String resultCode = "";
        try {
            map.put("操作类型","A");
            // 校验接口是否已经同步成功过，
            if(systemModule.isSocketSuccess(loanId,socketCode,codeNum,codeNumTwo)){
                if("99COR017022".equals(socketCode)&&!RepayPlanOption.SELECT.type.equals(map.get("选项"))){
                    map.put("选项","A");
                }else{
                    map.put("操作类型","U");
                }
            }

            sendXml = SocketUtil.getSocketXml(socketCode, bodyList, map);
            log.info("===================socket:"+socketCode+", 请求报文："+sendXml+"===================");
            returnMassage = SocketUtil.socketPost(socketIp, socketPort, sendXml);
            log.info("===================socket:"+socketCode+", 返回报文："+returnMassage+"===================");


            Document document = DocumentHelper.parseText(returnMassage.substring(18));
            String repCode = document.getRootElement().element("Body").element("rep_code").getText();
            String repMsg = document.getRootElement().element("Body").element("rep_msg").getText();
            if(!repCode.equals("00000")&&!repCode.equals("0000")){
                //如果出现违反唯一性约束 则再update同步一次（可能出现死循环）
                if(repMsg.contains("违反了唯一性的原则")||repMsg.contains("记录已经存在")){
                    if("99COR017022".equals(socketCode)){
                        map.put("选项","A");
                    }else{
                        map.put("操作类型","U");
                    }
                    sendXml = SocketUtil.getSocketXml(socketCode, bodyList, map);
                    returnMassage = SocketUtil.socketPost(socketIp, socketPort, sendXml);
                    document = DocumentHelper.parseText(returnMassage.substring(18));
                    repCode = document.getRootElement().element("Body").element("rep_code").getText();
                    repMsg = document.getRootElement().element("Body").element("rep_msg").getText();
                    if(!repCode.equals("00000")&&!repCode.equals("0000")){
                        remark = "原因："+repMsg;
                        throw new Exception(remark);
                    }
                }else{
                    remark = "原因："+repMsg;
                    throw new Exception(remark);
                }
            }
            return document;

        } catch (Exception e) {
            remark = e.getMessage();
            throw new Exception(socketCode + ",同步失败</br>"+remark);
        } finally {
                systemModule.addSysSocketLog(loanId, socketCode, sendXml, returnMassage, remark, codeNum, codeNumTwo);
        }
    }
    //查询接口
    private Document sendSocketPost2Query(Integer loanId, String socketCode, List<BaseXmlBeanEnum> bodyList, Map<String, String> map) throws Exception{
        String sendXml = "", returnMassage = "", remark = "success";
        Document document = null;
        try {
            sendXml = SocketUtil.getSocketXml(socketCode, bodyList, map);
            log.info("===================socket:"+socketCode+", 请求报文："+sendXml+"===================");
            returnMassage = SocketUtil.socketPost(socketIp, socketPort, sendXml);
            log.info("===================socket:"+socketCode+", 返回报文："+returnMassage+"===================");

            document = DocumentHelper.parseText(returnMassage.substring(18));
//            String repCode = document.getRootElement().element("Body").element("code").getText().trim();
//            String repMsg = document.getRootElement().element("Body").element("msg").getText().trim();
//            if(!repCode.equals("0000")){
//                remark = "原因："+repMsg;
//                throw  new Exception(remark);
//            }
            return document;
        } catch (Exception e) {
//            remark = e.getMessage();
            throw new Exception(e.getMessage());
        } finally {
        }
    }

    private Map<String, String> getLoanInfoMap(Integer loanId) {
        Map<String, String> map = new HashMap<String, String>();
        //查询所有表单
        List<AutoBaseTemplate> templateList = configModule.getAutoTemplateProvider().getAllTemplateList();
        if (CollectionUtils.isEmpty(templateList)) {
            return map;
        }
        for (AutoBaseTemplate template : templateList) {
            if (TableInputType.LIST.type.equals(template.getType())) {
                continue;
            }
            DataTable datatable = null;
            if(template.getTableName().equals("LBIZ_APPROVAL_RESOLUTION")){
                datatable = applyInfoService.getApprovalDataTableByLoanId( loanId);
            }else{
                datatable = applyInfoService.getDataTableByLoanId(template.getTableName(), "", loanId);
            }
            DataRow[] rows = null;
            if (datatable != null && datatable.rowSize() > 0) {
                rows = datatable.getRows();
            }

            if (null == rows) {
                rows = new DataRow[1];
//				rows[0] = new DataRow(0);
            }

            for (int r = 0; r < rows.length; r++) {
                template.setData(rows[r]);
                List<AutoBaseField> fieldList = template.getFields();
                for (int i = 0; i < fieldList.size(); i++) {
                    AutoFieldWrapper field = (AutoFieldWrapper) fieldList.get(i);
//					Object value = field.getValue(template.getData());
                    Object value = "";
                    String display = "";
                    if (null != template.getData()) {
                        value = banger.framework.reader.Reader.objectReader().getValue(template.getData(), field.getColumn());
                    }
                    if (null != value && "" != value) {
                        display = value.toString();
                        if(field.getType().equalsIgnoreCase("Date")) {
                            display = FormatUtil.formatDate(value, "yyyy-MM-dd");
                        }
                        if(StringUtils.isNotBlank(field.getUnit())&&field.getUnit().equalsIgnoreCase("%")) {
                            display = new BigDecimal(value.toString()).divide(new BigDecimal(100)).toString();
                        }
                    }

                    // xxx.xxx
                    String key = template.getName() + "." + field.getFieldName();
                    map.put(key, display);

                }

            }

        }

        //贷款主表
        LoanApplyInfo loanApplyInfo = applyInfoService.getApplyInfoById(loanId);
        if (null != loanApplyInfo) {

            map.put("合同类型", String.valueOf(loanApplyInfo.getLoanContractTypeId()));
            map.put("合同号", loanApplyInfo.getLoanContractNumber());
            map.put("合同编码", loanApplyInfo.getContractCode());
            map.put("保证人合同号", loanApplyInfo.getGuaranteeContractNo());
            map.put("保证人合同编码", loanApplyInfo.getGuaranteeContractCode());
            map.put("抵押合同号", loanApplyInfo.getMortgageContractNo());
            map.put("抵押合同编码", loanApplyInfo.getMortgageContractCode());
            map.put("质押合同号", loanApplyInfo.getPledgeContractNo());
            map.put("质押合同编码", loanApplyInfo.getPledgeContractCode());
            map.put("借据号", loanApplyInfo.getIouNum());
            map.put("授权码", loanApplyInfo.getAuthorizationCode());
            map.put("授权交易码","11120002");                  // 交易码为固定值
            map.put("贷款账号",loanApplyInfo.getLoanAccountNo());

            map.put("贷款申请时间", getStringDate(loanApplyInfo.getLoanApplyDate(), "yyyy年MM月dd日"));
            map.put("贷款分配时间", getStringDate(loanApplyInfo.getLoanAssignmentDate(), "yyyy年MM月dd日"));
            map.put("贷款调查时间", getStringDate(loanApplyInfo.getLoanInvestigationDate(), "yyyy年MM月dd日"));
            map.put("贷款审批时间", getStringDate(loanApplyInfo.getLoanAuditDate(), "yyyy年MM月dd日"));
            map.put("贷款放款时间", getStringDate(loanApplyInfo.getLoanCreditDate(), "yyyy年MM月dd日"));
            //LS+法人机构（5位）+年份(4位)+“97”+顺序号（7位）
            map.put("借款合同主表.业务流水号","LS"+"06001"+loanApplyInfo.getLoanContractNumber().substring(3,16));

        }

        map.put("当前日期", getStringDate(new Date(), "yyyy-MM-dd"));
        //其他 报文头 时间
        map.put("请求方日期", getStringDate(new Date(), "yyyyMMdd"));
        map.put("请求方时间", getStringDate(new Date(), "HHmmss"));
        map.put("请求方流水号", "122"+String.valueOf(System.currentTimeMillis()));

        //贷款人
//        map.put("贷款人.名称", lenderName);
//        map.put("贷款人.法人代表", lenderRep);
//        map.put("贷款人.住所地", lenderAddress);
//        map.put("贷款人.统一社会信用代码", lenderUsci);


        return map;
    }


    private Map<String,String> setAuthorizedCloumn(Map<String,String> map){

        //期限类型	"1-12 0短期  13-60 1中期  61---2长期"
        String loanLimitStr = MapUtils.getString(map,"审批决议.决议期限");
        String value = "";
        if(StringUtils.isNotBlank(loanLimitStr)&&StringUtils.isNumeric(loanLimitStr)){
            int loanLimit = Integer.parseInt(loanLimitStr);
            if(loanLimit<=12){
                map.put("期限类型","0");
            }else if(loanLimit<=60){
                map.put("期限类型","1");
            }else {
                map.put("期限类型", "2");
            }
        }

        String intRateType = getMapStringVal(map, "省信贷合同要素.利率类型", "1",false);
//        map.put("field_num",66);
        map.put("fldvalue01", "c@cif_or_acct_no@" + MapUtils.getString(map, "个人信息.客户编码").substring(0,17));
        map.put("fldvalue02", "c@system@LON");
        map.put("fldvalue03", "c@customer_or_acct_no@C");
        map.put("fldvalue04", "c@product_type@" + MapUtils.getString(map, "调查结论.核心产品号").substring(0,4));
        map.put("fldvalue05", "c@product_sub_type@"+ MapUtils.getString(map, "调查结论.核心产品号").substring(4,8));
        map.put("fldvalue06", "c@cust_no@" + MapUtils.getString(map, "个人信息.客户编码").substring(0,17));
        map.put("fldvalue07", "c@cert_no@" +  MapUtils.getString(map, "个人信息.证件号码"));
        map.put("fldvalue08", "c@cust_name@" + MapUtils.getString(map, "个人信息.客户姓名"));
        map.put("fldvalue09", "c@lsm_load_no@" + MapUtils.getString(map, "借据号"));
        map.put("fldvalue10", "c@contract_no@" + MapUtils.getString(map, "合同号"));
        map.put("fldvalue11", "c@apply_sum@" + MapUtils.getString(map, "放贷信息.放款金额"));
        if (map.containsKey("放贷信息.放款起始日期")) {
            value = MapUtils.getString(map, "放贷信息.放款起始日期").replace("-", "");
            value = setTimeFormat(value);
        }
        map.put("fldvalue12", "c@load_apply_date@" + value);
        if (map.containsKey("放贷信息.放款终止日期")) {
            value = MapUtils.getString(map, "放贷信息.放款终止日期").replace("-", "");
            value = setTimeFormat(value);
        }
        map.put("fldvalue13", "c@contract_end_day@" + value);

        if(map.containsKey("审批决议.还款方式")){
            if("1".equals(MapUtils.getString(map, "审批决议.还款方式"))){
                value = "G";
            }else if("2".equals(MapUtils.getString(map, "审批决议.还款方式"))){
                value = "F";
            }else if("3".equals(MapUtils.getString(map, "审批决议.还款方式"))){
                value = "B";
            }else if("5".equals(MapUtils.getString(map, "审批决议.还款方式")) || "4".equals(MapUtils.getString(map, "审批决议.还款方式"))){
                value = "N";
            }
        }
        map.put("fldvalue14", "c@return_load_type@"+value);
        map.put("fldvalue15", "c@self_loan_indicator@0");

        map.put("fldvalue16", "c@interest_rate_type@"+(intRateType.equals("1")?"A":"B"));

        map.put("fldvalue17", "c@interest_rate_increase_option4@"+(intRateType.equals("1")?"0":"2"));

        map.put("fldvalue18", "c@interest_rate_increment@" + getMapStringVal(map, "省信贷合同要素.利率浮动比","0.000000000",true));
        map.put("fldvalue19", "c@fastness_interest_rate@"+ getMapStringVal(map, "省信贷合同要素.固定利率", "0.000000000",true));
        map.put("fldvalue20", "c@outload_acct_no@" + MapUtils.getString(map, "贷款合同.入账账号"));
        map.put("fldvalue21", "c@return_acct_no@" + MapUtils.getString(map, "贷款合同.还款帐号"));
        map.put("fldvalue22", "c@basic_term@M");
        map.put("fldvalue23", "c@return_load_date@"+ getMapStringVal(map, "省信贷合同要素.决议还款日", "21",false));
        map.put("fldvalue24", "c@return_load_cycle@1");
        map.put("fldvalue25", "c@not_count_interest_id@N");
        map.put("fldvalue26", "c@validafteradv@N");
        map.put("fldvalue27", "c@enough_pay_id@2");
        map.put("fldvalue28", "c@auto_pay_id@3");
        map.put("fldvalue29", "c@return_recalculation_id@1");
        map.put("fldvalue30", "c@coin@CNY");
        //

        map.put("fldvalue31", "c@punish_interest_increase3@"+ getMapStringVal(map, "省信贷合同要素.拖欠本金罚息利率增量", "50",true)); //拖欠本金罚息利率增量3
        map.put("fldvalue32", "c@punish_interest_increase_option3@"+getMapStringVal(map, "省信贷合同要素.拖欠本金罚息利率增量选项", "2", false));//拖欠本金罚息利率增量选项3
        map.put("fldvalue33", "c@punish_interest_rate_increase@"+ getMapStringVal(map, "省信贷合同要素.拖欠利息罚息利率增量", "50", true));//拖欠利息罚息利率增量
        map.put("fldvalue34", "c@punish_interest_rate_increse_option@"+getMapStringVal(map, "省信贷合同要素.拖欠利息罚息利率增量选项", "2", false));//拖欠利息罚息利率增量选项
        map.put("fldvalue35", "c@punish_interest_rate_increase2@"+ getMapStringVal(map, "省信贷合同要素.复利罚息利率增量", "50", true));//复利罚息罚息利率增量2
        map.put("fldvalue36", "c@punish_interest_rate_increase_option2@"+getMapStringVal(map, "省信贷合同要素.复利罚息利率增量选项", "2", false));//复利罚息罚息利率增量选项2
        map.put("fldvalue37", "c@punish_interest_rate_increase5@0");
        map.put("fldvalue38", "c@punish_interest_rate_increase_option5@0");
        map.put("fldvalue39", "c@fixed_punish_interest_rate@"+ getMapStringVal(map, "省信贷合同要素.拖欠利息固定罚息率", "0", true));//拖欠利息固定的罚息率
        map.put("fldvalue40", "c@fixed_punish_interest_rate2@"+getMapStringVal(map, "省信贷合同要素.复利固定罚息率", "0", true));//复利罚息罚息固定的罚息率2
        map.put("fldvalue41", "c@fastness_punish_interest_rate@"+getMapStringVal(map, "省信贷合同要素.拖欠本金固定的罚息率", "0.000000000", true));//拖欠本金固定的罚息率
        map.put("fldvalue42", "c@punish_interest_rate_increase6@"+getMapStringVal(map, "省信贷合同要素.挪用利率罚息利率增量", "100.000000000", true));//挪用罚息利率增量6
        //
        map.put("fldvalue43", "c@favourable_term2@00");
        //
        map.put("fldvalue44", "c@in_favourable_punish_interest_id1@N");
        map.put("fldvalue45", "c@after_favourable_punish_interest_id1@ND");
        map.put("fldvalue46", "c@in_favourable_punish_interest_id2@N");
        map.put("fldvalue47", "c@after_favourable_punish_interest_id2@ND");
        //
        map.put("fldvalue48", "c@punish_balance_interest_cycle@1");
        map.put("fldvalue49", "c@frist_interestrate_date@00000000");
        map.put("fldvalue50", "c@whether_auto_pay_extend_interest@Y");
        map.put("fldvalue51", "c@whether_print_return_notice@N");
        map.put("fldvalue52", "c@at_term_return_type@0");

        map.put("fldvalue53", "c@float_interestrate_use_id@"+(intRateType.equals("1")?"":"3"));//        固定 null 浮动 3次年一月一日生效
        map.put("fldvalue54", "c@note_counting_dates@0");
        map.put("fldvalue55", "c@consign_fund_protocol_no@00000000000000000");
        map.put("fldvalue56", "c@consign_loan_bgl_acct_no@0000000000000000");

        //-----------------------TODO-------------------------//
        map.put("fldvalue57", "c@licenseType@DKCZ");

        map.put("fldvalue58", "c@note_no@");

        //首次还款日标识 0:第一个还款日还款(非整期后)  1:一个整期后的还款日还款   2:次月还款  3:按标准月的还款日(3-6-9-12月)
        map.put("fldvalue59", "c@frist_return_date_id@"+getMapStringVal(map, "放贷信息.首次还款日标识", "0", false));
        //等额本息和等额本金的还款方式，首次还款日标识不能为1，可以为0
        if("F".equals(value)||"G".equals(value)){
            if("1".equals(getMapStringVal(map, "放贷信息.首次还款日标识", "0", false))){
                map.put("fldvalue59", "c@frist_return_date_id@0");
            }
        }
        //放贷信息 放款方式  01自主支付-->1-借款人自主支付   02受托支付-->2-贷款人受托支付
        String loanMode = MapUtils.getString(map, "放贷信息.放款方式","1");
        map.put("fldvalue60", "c@whether_print_detain_return@"+(loanMode.equals("01")?"1":"2"));

        map.put("fldvalue61", "c@other_pay_interest_scale@");
        map.put("fldvalue62", "c@other_pay_interest_acct_no@");
        map.put("fldvalue63", "c@system1@");
        map.put("fldvalue64", "c@pay_mca_acct_product_type@");
        map.put("fldvalue65", "c@pay_mca_acct_product_sub_type@");
        map.put("fldvalue66", "c@return_mca_sub_acct_type@");
        return map;
    }


    private String getMapStringVal(Map<String,String> map,String key,String defaultVal,boolean isRate){
        try {
            String value = MapUtils.getString(map,key);

            if(isRate&&StringUtils.isNotBlank(value)){
                value = new BigDecimal(value).multiply(new BigDecimal(100)).toString()+"0000000";
            }

            if(StringUtils.isBlank(value)){
                value=defaultVal;
            }

            return value;
        }catch (Exception e){
            return defaultVal;
        }

    }

    public void addSocketHeadMap(Map<String,String> map){
        map.put("请求方日期", getStringDate(new Date(), "yyyyMMdd"));
        map.put("请求方时间", getStringDate(new Date(), "HHmmss"));
        map.put("请求方流水号", "122"+String.valueOf(System.currentTimeMillis()));
        map.put("交易码","11120002");
    }

    private boolean checkCusInfo(Map<String,String> map,Integer loanId,Integer type){
        String loanName = "";
        String idCard = "";
        //1.抵质押物是否为借款人
        if(type==1) {
            loanName = MapUtils.getString(map,"抵质押物.抵押人名称","");
            idCard = MapUtils.getString(map,"抵质押物.身份证号码","");
            if (loanName.equals(map.get("个人信息.客户姓名")) && idCard.equals(map.get("个人信息.证件号码"))) {
                map.put("抵质押物.客户编码", map.get("个人信息.客户编码"));
                return false;
            }
        }else if(type==2){
        //2.担保人是否为抵质押物人
        //查询保证人列表
            loanName = MapUtils.getString(map,"担保人.姓名","");
            idCard = MapUtils.getString(map,"担保人.证件号码","");
        AutoBaseTemplate template = configModule.getAutoTemplateProvider().getTemplateListByTables(new String[]{"LBIZ_PLEDGE_ITEM"}).get(0);
        DataTable datatable = applyInfoService.getDataTableByLoanId(template.getTableName(), LoanProcessTypeEnum.INVESTIGATE.type, loanId);
        if(datatable==null || datatable.rowSize()<=0){
            return true;
        }
        for (int r = 0; r < datatable.getRows().length; r++) {
            template.setData(datatable.getRows()[r]);
            List<AutoBaseField> fieldList = template.getFields();
            for (int i = 0; i < fieldList.size(); i++) {
                AutoFieldWrapper field = (AutoFieldWrapper)fieldList.get(i);
                Object value = "";
                if(null!=template.getData()){
                    value = banger.framework.reader.Reader.objectReader().getValue(template.getData(), field.getColumn());
                }
                String key = template.getName() + "." + field.getFieldName();
                map.put(key, null!=value?value.toString():"");
            }
            if(loanName.equals(MapUtils.getString(map, "抵质押物.抵押人名称", "")) && idCard.equals(MapUtils.getString(map, "抵质押物.身份证号码", ""))){
//                map.put("抵质押物.客户编码",map.get("担保人.客户编码"));
                map.put("担保人.客户编码",map.get("抵质押物.客户编码"));
                return false;
            }
        }
        }
        return true;
    }

    private String setTimeFormat(String value){
        String time = "";
        time = value.substring(6);
        time += value.substring(4,6);
        time += value.substring(0,4);
        return time;
    }

    //还款计划接口
    public String syncRepaymentPlan(Integer loanId,RepayPlanOption option){
        String returnStr = "";
        switch (option){
            case ADD:
            case UPDATE:
                returnStr = synRepaymentPlanInfo(loanId,option.type);
                break;
            case SELECT:
                returnStr = selectRepaymentPlanInfo(loanId,option.type);
        }
        return returnStr;
    }

    private String synRepaymentPlanInfo(Integer loanId,String option){
        try {
            LoanApplyInfo applyInfo = applyInfoService.getApplyInfoById(loanId);
            String loanAccountNo = applyInfo.getLoanAccountNo();
            if(StringUtil.isNullOrEmpty(loanAccountNo)){
                return returnText(false, "该贷款无贷款账号，无法" + RepayPlanOption.getTypeNameByType(option) + "还款计划!", "");
            }
            List<LoanRepayPlanInfoExtend> list = repayPlanInfoService.queryLoanRepayPlanInfoByLoanId(loanId);
            if(CollectionUtils.isNotEmpty(list)){
                List<List<LoanRepayPlanInfoExtend>> planList = averageAssign(list,15);
                for(int recNo=1;recNo<=planList.size();recNo++){
                    Map<String,String> map = new HashMap<String, String>();
                    map.put("帐号",loanAccountNo);
                    map.put("选项",option);
                    map.put("屏数",recNo+"");
                    if(planList.size()>1&&recNo!=planList.size()){
                        //N 下一页 P 上一页
                        map.put("上一页下一页","N");
                    }
                    List<LoanRepayPlanInfoExtend> subList = planList.get(recNo-1);
                    for(int i=0;i<subList.size();i++){
                        LoanRepayPlanInfoExtend plan = subList.get(i);
                        map.put("还款起始日"+(i+1),DateUtil.format(plan.getLoanRepayPlanDate(),"ddMMyyyy"));
                        map.put("次数"+(i+1),"001");
                        map.put("类型"+(i+1),"M");
                        map.put("还款类型"+(i+1),"P");
                        map.put("还款金额"+(i+1),plan.getLoanPrincipalAmount().toString());
                    }
                    addSocketHeadMap(map);
                    Document document_Socket_99COR017022 = sendSocketPost(loanId,Socket_99COR017022.getSocketCode(),Socket_99COR017022.getAllElement(),map,map.get("帐号"),map.get("屏数"));
                }
                return returnText(true,RepayPlanOption.getTypeNameByType(option)+"成功","");
            }else{
                return returnText(false,"还款计划为空，无法同步","");
            }
        }catch (Exception e){
            return returnText(false,"还款计划" + RepayPlanOption.getTypeNameByType(option) + "失败!原因：" + e.getMessage(),"");
        }
    }

    private String selectRepaymentPlanInfo(Integer loanId,String option){
        //还款计划查询
        try {
            LoanApplyInfo applyInfo = applyInfoService.getApplyInfoById(loanId);
            String loanAccountNo = applyInfo.getLoanAccountNo();
            if(StringUtil.isNullOrEmpty(loanAccountNo)){
                return returnText(false, "该贷款无贷款账号，无法" + RepayPlanOption.getTypeNameByType(option) + "还款计划!", "");
            }
            List<LoanRepayPlanInfoExtend> list = repayPlanInfoService.queryLoanRepayPlanInfoByLoanId(loanId);
            if(CollectionUtils.isNotEmpty(list)){
                int remaider=list.size()%15,number=list.size()/15;
                int pages = remaider==0?number:(number+1);
                List<LoanRepayPlanInfoExtend> data = new ArrayList<LoanRepayPlanInfoExtend>();
                for(int recNo=1;recNo<=pages;recNo++){
                    Map<String,String> map = new HashMap<String, String>();
                    map.put("帐号",loanAccountNo);
                    map.put("选项",option);
                    map.put("屏数",recNo+"");
                    Document document = sendSocketPost(loanId, Socket_99COR017022.getSocketCode(), Socket_99COR017022.getAllElement(), map, "", "");
                    int size = recNo==pages?remaider:15;
                    Element body = document.getRootElement().element("Body");
                    for(int j=1;j<=size;j++){
                        LoanRepayPlanInfoExtend plan = new LoanRepayPlanInfoExtend();
                        plan.setLoanRepayPlanDate(DateUtil.parser(body.element("return_begin_date"+j).getStringValue(),"ddMMyyyy"));
                        plan.setLoanPrincipalAmount(new BigDecimal(body.element("return_sum"+j).getStringValue()));
                        data.add(plan);
                    }
                }
                return returnText(true,RepayPlanOption.getTypeNameByType(option)+"成功",data);
            }else{
                return returnText(false,"还款计划为空，无法同步","");
            }
        }catch (Exception e){
            return returnText(false,"还款计划" + RepayPlanOption.getTypeNameByType(option) + "失败!原因：" + e.getMessage(),"");
        }
    }

    public static <T> List<List<T>> averageAssign(List<T> source,int n){
        List<List<T>> result=new ArrayList<List<T>>();
        int remaider=source.size()%n;
        int number=source.size()/n;
        int pages = remaider==0?number:(number+1);
        for(int i=0;i<pages;i++){
            List<T> value=null;
            if(remaider==0){
                value = source.subList(i*n, (i+1)*n);
            }else{
                value = source.subList(i*n, i+1==pages?source.size():(i+1)*n);
            }
            result.add(value);
        }
        return result;
    }

    public String returnText(boolean success,String msg,Object data){
        JSONObject jo = new JSONObject();
        jo.put("success",success);
        jo.put("msg",msg);
        jo.put("data",data);
        return jo.toString();
    }
//    private Map<String, String> getLoanInfoMap1(Integer loanId) {
//        Map<String, String> map = new HashMap<String, String>();
//        String presType = "";
//        //查询所有表单
//        List<AutoBaseTemplate> templateList = configModule.getAutoTemplateProvider().getAllTemplateList();
//        if (CollectionUtils.isEmpty(templateList)) {
//            return map;
//        }
//        for (AutoBaseTemplate template : templateList) {
//            System.out.println(template.getName()+"<br/> <table  cellspacing=\"0\" cellpadding=\"0\">");
//            System.out.println("<thead>");
//            System.out.println("<tr style=\"background-color: lightslategrey\">");
//            System.out.print("<th >要素</th>");
//            System.out.print("<th >录入方式</th>");
//            System.out.print("<th >必填</th>");
//            System.out.print("<th >要素说明</th>");
//            System.out.println("<tr>");
//            System.out.println("</thead>");
//            List<AutoBaseField> fieldList = template.getFields();
//            for (int i = 0; i < fieldList.size(); i++) {
//                AutoBaseField field = fieldList.get(i);
//                String type = field.getType();
//                if(type.equals("Address")){
//                    type = "地址";
//                }else if(type.equals("Date")){
//                    type = "日期";
//                }else if(type.equals("Decimal")){
//                    type = "金额";
//                }else if(type.equals("MultipleSelect")){
//                    type = "复选框";
//                }else if(type.equals("Number")){
//                    type = "数值";
//                }else if(type.equals("Ratio")){
//                    type = "率%";
//                }else if(type.equals("Select")){
//                    type = "下拉菜单";
//                }else if(type.equals("Text")){
//                    type = "短文本";
//                }else if(type.equals("TextArea")){
//                    type = "长文本";
//                }else if(type.equals("YesNo")){
//                    type = "是/否";
//                }
//
//                System.out.println("<tr>");
//                System.out.print("<td >" + field.getName() + "</td>");
//                System.out.print("<td >"+type+"</td>");
//                System.out.print("<td >"+(field.getNullable() ? "必填" : "选填")+"</td>");
//
//                String optionStr = "";
//                List<AutoBaseOption>  options = field.getOptions();
//                if(CollectionUtils.isNotEmpty(options)){
//                    for (AutoBaseOption option : options) {
//                        if(StringUtils.isBlank(optionStr)){
//                            optionStr+="<br/>";
//                        }
//                        optionStr+=option.getValue()+" "+option.getName();
//                    }
//                }
//
//                System.out.print("<td >"+optionStr+"</td>");
//
//                System.out.println("<tr>");
//
//            }
//            System.out.println("</table>");
//
//
//
//
//        }
//
////        private Map<String, String> getLoanInfoMap1(Integer loanId) {
////            Map<String, String> map = new HashMap<String, String>();
////            String presType = "";
////            //查询所有表单
////            List<AutoBaseTemplate> templateList = configModule.getAutoTemplateProvider().getAllTemplateList();
////            if (CollectionUtils.isEmpty(templateList)) {
////                return map;
////            }
////            for (AutoBaseTemplate template : templateList) {
////                System.out.println("<br/>");
////                System.out.println(template.getName()+"<br/>");
////
////                List<AutoBaseField> fieldList = template.getFields();
////                for (int i = 0; i < fieldList.size(); i++) {
////                    AutoBaseField field = fieldList.get(i);
////                    String type = field.getType();
////                    if(type.equals("Address")){
////                        type = "地址";
////                    }else if(type.equals("Date")){
////                        type = "日期";
////                    }else if(type.equals("Decimal")){
////                        type = "金额";
////                    }else if(type.equals("MultipleSelect")){
////                        type = "复选框";
////                    }else if(type.equals("Number")){
////                        type = "数值";
////                    }else if(type.equals("Ratio")){
////                        type = "率%";
////                    }else if(type.equals("Select")){
////                        type = "下拉菜单";
////                    }else if(type.equals("Text")){
////                        type = "短文本";
////                    }else if(type.equals("TextArea")){
////                        type = "长文本";
////                    }else if(type.equals("YesNo")){
////                        type = "是/否";
////                    }
//////                String ss = "<label style=\"color: #0000ff\"></label>"
////
////                    System.out.print(field.getName() + "<label style=\"color: #0000ff\">" + "【" + type + "】" + "【" + (field.getNullable() ? "必填" : "选填") + "】</label>");
////
////
////                }
////
////
////
////            }
//
//        //贷款主表
//        LoanApplyInfo loanApplyInfo = applyInfoService.getApplyInfoById(loanId);
//        if (null != loanApplyInfo) {
//
//            map.put("贷款合同类型", String.valueOf(loanApplyInfo.getLoanContractTypeId()));
//            map.put("合同号", loanApplyInfo.getLoanContractNumber());
//            map.put("合同编码", loanApplyInfo.getContractCode());
//            map.put("保证人合同号", loanApplyInfo.getGuaranteeContractNo());
//            map.put("保证人合同编码", loanApplyInfo.getGuaranteeContractCode());
//            map.put("抵押合同号", loanApplyInfo.getMortgageContractNo());
//            map.put("抵押合同编码", loanApplyInfo.getMortgageContractCode());
//            map.put("质押合同号", loanApplyInfo.getPledgeContractNo());
//            map.put("质押合同编码", loanApplyInfo.getPledgeContractCode());
//            map.put("借据号", loanApplyInfo.getIouNum());
//            map.put("授权码", loanApplyInfo.getAuthorizationCode());
//        }
//
//        //其他 报文头 时间
//        map.put("请求方日期", getStringDate(new Date(), "yyyyMMdd"));
//        map.put("请求方时间", getStringDate(new Date(), "HHmmss"));
//        map.put("请求方流水号", String.valueOf(System.currentTimeMillis()));
//
//        return map;
//    }


    private String getStringDate(Date date, String pattern) {
        try{
            if(null!=date){
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                return sdf.format(date);
            }else{
                return "";
            }
        }catch (Exception e){
            return "";
        }
    }

    private List<AutoBaseField> setPaymentIdAndStatus(List<AutoBaseField> autoBaseFields){
        AutoBaseField autoBaseField = new AutoFieldWrapper(3101,"paymentId","PAYMENT_ID","支付ID号","Text","",true,150,true,true,"paymentId",null,null);
        AutoBaseField autoBaseField1 = new AutoFieldWrapper(3102,"paymentStatus","PAYMENT_STATUS","支付状态","Text","",true,150,true,true,"paymentStatus",null,null);
        autoBaseFields.add(autoBaseField);
        autoBaseFields.add(autoBaseField1);
            return autoBaseFields;
    }
}
