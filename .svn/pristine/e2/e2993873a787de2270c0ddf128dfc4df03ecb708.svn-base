package banger.controller;

import banger.common.BaseController;
import banger.common.interceptor.NoLoginInterceptor;
import banger.common.tools.StringUtil;
import banger.domain.loan.*;
import banger.framework.util.DateUtil;
import banger.framework.util.OperationUtil;
import banger.framework.web.dojo.JsonTools;
import banger.moduleIntf.*;
import banger.service.impl.ProfitBizIncomeMonthService;
import banger.service.intf.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


/**
 * 损益情况
 * @author xw
 *
 */
@RequestMapping("/loanInvestigate")
@Controller
public class LoanProLossInvestigateController extends BaseController {
    @Resource
    ILoanProfitLossRatioItemProvider loanProfitLossRatioItemProvider;
    @Resource
    ILoanApplyService loanApplyService;
    @Resource
    IProfitConsumIncomeItemProvider iProfitConsumIncomeItemProvider;
    @Resource
    IProfitLossRatioItemService profitLossRatioItemService;
    @Resource
    ILoanIncomeStatementProvider loanIncomeStatementProvider;
    @Resource
    IProfitBizIncomeItemProvider iProfitBizIncomeItemProvider;
    @Resource
    IProfitBizIncomeMonthProvider iProfitBizIncomeMonthProvider;
    @Resource
    IProfitBizIncomeMonthService iProfitBizIncomeMonthService;
    @Resource
    ProfitBizIncomeMonthService profitBizIncomeMonthService;
    @Resource
    IProfitLossInfoService iProfitLossInfoService;
    @Resource
    ICrossCheckQuanyiquanService iCrossCheckQuanyiquanService;





    /**
     * 即时刷新页面
     *经营类和消费类
     */
    @RequestMapping("reflushGrid")
    public String reflushGrid() {
        Map<String, Object> map = new HashedMap();

        Integer loanId = Integer.valueOf(this.getParameter("loanId"));
        LoanApplyInfo loanApplyInfo = loanApplyService.getApplyInfoById(loanId);
        Integer loanClassId = loanApplyInfo.getLoanClassId(); //得到loanClassId

        String columnName = this.getParameter("columnName");

        map.put("columnName", columnName);
        map.put("loanId", loanId);
        map.put("loanClassId", loanClassId);
        LoanProfitLossInfo lpli =	iProfitLossInfoService.getProfitLossInfoByLoanId(loanId);


        if(loanClassId==1){//经营类
            List<LoanProfitBizIncomeItem> itemList = iProfitBizIncomeItemProvider.queryProfitBizIncomeItemList(map);

            BigDecimal totalItem = new BigDecimal("0");

            if(columnName.equals("BUSINESS_INCOME_AMOUNT")){
                totalItem=lpli.getBusinessIncomeAmount();
                setAttribute("totalLibt", totalItem);//营业收入总和
                setAttribute("liBt", itemList);//营业收入
            }else if(columnName.equals("OTHER_INCOME_AMOUNT")){
                totalItem=lpli.getOtherIncomeAmount();
                setAttribute("totalLiot", totalItem);
                setAttribute("liOt", itemList);
            }else if(columnName.equals("FIXED_COST_DEFRAY_AMOUNT")){
                totalItem=lpli.getFixedCostDefrayAmount();
                setAttribute("totalLift", totalItem);
                setAttribute("liFt", itemList);
            }else if(columnName.equals("TEX_DEFRAY_AMOUNT")){
                totalItem=lpli.getTexDefrayAmount();
                setAttribute("totalLitt", totalItem);
                setAttribute("liTt", itemList);
            }else if(columnName.equals("OTHER_DEFRAY_AMOUNT")){
                totalItem=lpli.getOtherDefrayAmount();
                setAttribute("totalLiodt", totalItem);
                setAttribute("liOdt", itemList);
            }
        }else if(loanClassId==2){//消费类

            List<LoanProfitConsumIncomeItem> itemList = iProfitConsumIncomeItemProvider.queryProfitConsumIncomeItemList(map);
            BigDecimal totalItem = new BigDecimal("0");


            if(columnName.equals("HOME_INCOME_AMOUNT")){
                totalItem=lpli.getHomeIncomeAmount();
                setAttribute("totalLibt", totalItem);//家庭收入总和
                setAttribute("liBt", itemList);//家庭收入
            }else if(columnName.equals("OTHER_INCOME_AMOUNT")){
                totalItem=lpli.getOtherIncomeAmount();
                setAttribute("totalLiot", totalItem);
                setAttribute("liOt", itemList);
            }else if(columnName.equals("FIXED_DEFRAY_AMOUNT")){
                totalItem=lpli.getFixedDefrayAmount();
                setAttribute("totalLift", totalItem);
                setAttribute("liFt", itemList);
            }else if(columnName.equals("TEX_PERSONAL_AMOUNT")){
                totalItem=lpli.getTexPersonalAmount();
                setAttribute("totalLitt", totalItem);
                setAttribute("liTt", itemList);
            }else if(columnName.equals("OTHER_DEFRAY_AMOUNT")){
                totalItem=lpli.getOtherDefrayAmount();
                setAttribute("totalLiodt", totalItem);
                setAttribute("liOdt", itemList);
            }
        }

        //从主表中得到时间

        String createDate=lpli.getYearStart()+"-"+lpli.getMonthStart();

        String createDateEnd=lpli.getYearEnd()+"-"+lpli.getMonthEnd();

        setAttribute("columnName",columnName);
        setAttribute("loanId", loanId);
        setAttribute("loanClassId", loanClassId);
        setAttribute("createDate", createDate);
        setAttribute("createDateEnd", createDateEnd);

        return "/loan/vm/profitLoss/refresh";
    }



    /**
     * 即时刷新页面
     *毛利率
     */
    @RequestMapping("reflushMao")
    public String reflushGridMao() {
        Integer loanId = Integer.valueOf(this.getParameter("loanId"));
        List<LoanProfitLossRatioItem> lplri = loanProfitLossRatioItemProvider.queryGrossProfitMarginList(loanId, "GROSS_PROFIT_RATIO");
        BigDecimal totalLplri = new BigDecimal("0");

        LoanProfitLossInfo lpli =	iProfitLossInfoService.getProfitLossInfoByLoanId(loanId);

        //加权毛利率总和
        totalLplri=lpli.getGrossProfitRatio();

       /* //加权毛利率总和
        for (LoanProfitLossRatioItem Lplri : lplri) {
            totalLplri = OperationUtil.plus(totalLplri, Lplri.getWeightingProfitRatio());
        }*/
        setAttribute("loanId", loanId);
        setAttribute("lplri", lplri);
        if (totalLplri == null) {
            setAttribute("totalLplri", totalLplri);//加权毛利率总和
        }else{
//          2017-11-21确认保留两位有效数字,采用四舍五入
            setAttribute("totalLplri",  OperationUtil.divide(new BigDecimal(1), 2, totalLplri));//加权毛利率总和
        }
        return "/loan/vm/profitLoss/refreshMao";
    }



    /**
     * 弹出时间编辑界面
     *
     */
    @NoLoginInterceptor
    @RequestMapping("getUpdateTimePage")
    public String getupdateTimePage(@RequestParam(value = "loanId" ) String loanId,@RequestParam(value = "createDate" ) String createDate,@RequestParam(value = "createDateEnd" ) String createDateEnd) {
        setAttribute("loanId",loanId);
        setAttribute("createDate",createDate);
        setAttribute("createDateEnd",createDateEnd);
        return "/loan/vm/profitLoss/updateTime";
    }

    /**
     * 编辑时间后操作
     *
     */

    @RequestMapping("updateTime")
    @ResponseBody
    public void updateTime() {
        try {
            String loanId = getParameter("loanId");
            String createDate = getParameter("createDate");
            String createDateEnd = getParameter("createDateEnd");
            setAttribute("createDate",createDate);
            setAttribute("createDateEnd",createDateEnd);

            //获取int年月
            loanIncomeStatementProvider.updateInterval(createDate, createDateEnd, getParameter("loanId"), "1", String.valueOf(getLoginInfo().getUserId()));
            //更新主表的收入总计
            //loanIncomeStatementProvider.updateLoanProfitLossInfoByOpt(Integer.parseInt(getParameter("loanId")), 1, Integer.valueOf(getLoginInfo().getUserId()));

            renderText(true, "修改成功！", null);
            return;
        } catch (Exception e) {
            log.error("修改报错", e);
        }
        renderText(false, "修改失败！", null);
        return;

    }





    /**
     * 跳转加权毛利率详情界面
     *
     */
    @NoLoginInterceptor
    @RequestMapping("getMaolilvPage")
    public String getMaolilvPage(@RequestParam(value = "id" ) String id) {
        LoanProfitLossRatioItem lplriA=profitLossRatioItemService.getProfitLossRatioItemById(Integer.parseInt(id));
        setAttribute("lplriA", lplriA);//毛利率明细
        return "/loan/vm/profitLoss/showMaolilv";
    }


    /**
     * 跳转编辑加权毛利率详情界面
     */
    @NoLoginInterceptor
    @RequestMapping("getMaolilvUpdatePage")
    public String getMaolilvUpdatePage(@RequestParam(value = "id" ) String id) {
        LoanProfitLossRatioItem lplriA=profitLossRatioItemService.getProfitLossRatioItemById(Integer.parseInt(id));
        setAttribute("lplriA", lplriA);//毛利率明细
        return "/loan/vm/profitLoss/updateMaolilv";
    }


    /**
     * 编辑加权毛利率详情
     *
     */
    @RequestMapping("updateMaolilv")
    @ResponseBody
    public void updateMaolilv(LoanProfitLossRatioItem lplri) {
        try {
            profitLossRatioItemService.updateProfitLossRatioItem(lplri,getLoginInfo().getUserId());
            renderText(true, "修改成功！", null);
            return;
        } catch (Exception e) {
            log.error("修改报错", e);
        }
        renderText(false, "修改失败！", null);
        return;
    }



    /**
     * 跳转新增加权毛利率详情界面
     *
     */
    @NoLoginInterceptor
    @RequestMapping("getMaolilvAddPage")
    public String getMaolilvAddPage(@RequestParam(value = "loanId" ) String loanId) {
        setAttribute("loanId",loanId);
        return "/loan/vm/profitLoss/addMaolilv";
    }

    /**
     * 新增加权毛利率详情
     *
     */
    @RequestMapping("addMaolilv")
    @ResponseBody
    public void addMaolilv(LoanProfitLossRatioItem lplri) {
        try {

            lplri.setColumnName("GROSS_PROFIT_RATIO");
            lplri.setLoanClassId(1);
            lplri.setTableName("LOAN_PROFIT_LOSS_RATIO_ITEM");

            LoanCrossCheckQuanyiquan lccq=iCrossCheckQuanyiquanService.getCrossCheckQuanyiquanByLoanId(lplri.getLoanId());
            if(lccq==null){//如果权益表字段为空 先新增字段
                lccq=new LoanCrossCheckQuanyiquan();
                lccq.setLoanClassId(1);
                lccq.setLoanId(lplri.getLoanId());
                iCrossCheckQuanyiquanService.saveCrossCheckQuanyiquan(lccq,getLoginInfo().getUserId());
            }
                profitLossRatioItemService.insertProfitLossRatioItem(lplri,getLoginInfo().getUserId());
            renderText(true, "新增成功！", null);
            return ;
        } catch (Exception e) {
            log.error("新增报错", e);
        }
        renderText(false, "新增失败！", null);
        return ;
    }

    /**
     * 删除加权毛利率详情
     *
     */
    @RequestMapping("deleteMaolilv")
    @ResponseBody
    public void deleteMaolilv(@RequestParam(value = "id" ) String id) {
        try {

            if (StringUtil.isNotEmpty(id)) {
                LoanProfitLossRatioItem loanProfitLossRatioItem = loanProfitLossRatioItemProvider.getProfitLossRatioItemById(Integer.parseInt(id));
                if (loanProfitLossRatioItem != null) {


                    //原来主表毛利率
                    BigDecimal preProfitRatio = loanIncomeStatementProvider.getProfitLossInfoByLoanId(loanProfitLossRatioItem.getLoanId()).getGrossProfitRatio();
                    //删除的加权毛利率
                    BigDecimal profitRatio = loanProfitLossRatioItem.getWeightingProfitRatio();
                    //剩余毛利率
                    BigDecimal newProfitRatio = OperationUtil.minus(preProfitRatio, profitRatio);

                    //更新毛利率
                    if(newProfitRatio.doubleValue()==0.00){
                        newProfitRatio=null;

                    }

                        LoanProfitLossInfo loanProfitLossInfo=new LoanProfitLossInfo();
                        loanProfitLossInfo.setLoanId(loanProfitLossRatioItem.getLoanId());
                        loanProfitLossInfo.setGrossProfitRatio(newProfitRatio);
                        //更新主表
                        loanIncomeStatementProvider.updateProfitLossInfoByLoanId(loanProfitLossInfo,getLoginInfo().getUserId());

                        loanProfitLossRatioItemProvider.deleteProfitLossRatioItemById(Integer.parseInt(id));
                    renderText(true, "删除成功！", null);
                    return;
                } else {//记录不存在
                    renderText(false, "删除失败！", null);
                }
            } else {
                renderText(false, "删除失败！", null);
            }
        } catch (Exception e) {
            log.error("删除报错", e);
        }
        renderText(false, "删除失败！", null);
        return;
    }




    /**
     * 获取明细界面
     *
     */
    @NoLoginInterceptor
    @RequestMapping("getDetailPage")
    public String getDetailPage(@RequestParam(value = "id" ) String id,@RequestParam(value = "columnName" ) String columnName,@RequestParam(value = "loanId" ) String loanId,@RequestParam(value = "just" ) String just) {
        List<LoanProfitBizIncomeMonth> monthList=profitBizIncomeMonthService.getProfitBizIncomeMonthByIncomeId(Integer.parseInt(id));
        LoanProfitBizIncomeItem item =iProfitBizIncomeItemProvider.getProfitBizIncomeItemById(Integer.parseInt(id));
        String remark=item.getRemark();
        setAttribute("remark", remark);
        setAttribute("monthList", monthList);
        setAttribute("columnName", columnName);//columnName
        setAttribute("loanId", loanId);//loanId

        if("just".equals(just)){
            return "/loan/vm/profitLoss/showJustDetail";
        }else{
            return "/loan/vm/profitLoss/showDetail";
        }

    }


    //修改明细（月份金额）
    @RequestMapping("updateDetail")
    @ResponseBody
    public void updateDetail(@RequestParam(value = "json", defaultValue = "") String json) {
        try {

            Map<String, Object> map = JsonTools.parseJSON2Map(json);
            BigDecimal totalAmount=null;
            Integer monthCount=0;
            Integer incomeId=0;
            for (Map.Entry<String, Object> entry: map.entrySet()) {
                BigDecimal monthAmount =new BigDecimal(String.valueOf(entry.getValue()));
                LoanProfitBizIncomeMonth month=profitBizIncomeMonthService.getProfitBizIncomeMonthById(Integer.parseInt(entry.getKey()));
                month.setId(Integer.parseInt(entry.getKey()));
                month.setMonthAmount(monthAmount);
                incomeId=month.getIncomeId();
                monthCount++;
                totalAmount=OperationUtil.plus(totalAmount,monthAmount);//总金额
                profitBizIncomeMonthService.updateProfitBizIncomeMonth(month,getLoginInfo().getUserId());//月份金额的修改
            }

            BigDecimal monthC = new BigDecimal(monthCount);//次数
            BigDecimal averageAmount=OperationUtil.divide(monthC,2,totalAmount);//平均金额

            //修改经营类收入支出表
            LoanProfitBizIncomeItem item=iProfitBizIncomeItemProvider.getProfitBizIncomeItemById(incomeId);
            String remark= getParameter("remark");
            item.setRemark(remark);
            item.setTotalAmount(totalAmount);
            item.setAverageAmount(averageAmount);

            iProfitBizIncomeItemProvider.updateProfitBizIncomeItem(item,getLoginInfo().getUserId());

            //修改总表

            //更新主表的收入总计
            loanIncomeStatementProvider.updateLoanProfitLossInfoByOpt(item.getLoanId(), 1, Integer.valueOf(getLoginInfo().getUserId()));

            renderText(true, "修改成功！", null);
            return;
        }catch (Exception e){
            e.printStackTrace();
        }
        renderText(false, "修改失败！", null);
        return;
    }




    /**
     * 新增明细详情
     *
     */
    @RequestMapping("addMingxi")
    @ResponseBody
    public void addMingxi(LoanProfitBizIncomeItem lpbii) {
        try {
            //获取月份
            Date createDate = DateUtil.parser(getParameter("startCreateDate"), "yyyy-MM");
            Date createDateEnd = DateUtil.parser(getParameter("endCreateDateEnd"), "yyyy-MM");

            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            c1.setTime(createDate);
            int createYear=c1.get(Calendar.YEAR);
            int createMonth=c1.get(Calendar.MONTH)+1;
            c2.setTime(createDateEnd);
            int createEndYear=c2.get(Calendar.YEAR);
            int createEndMonth=c2.get(Calendar.MONTH)+1;
            //总共几个月
            int Months=(createEndYear-createYear)*12+(createEndMonth-createMonth)+1;
            BigDecimal bigMonths = new BigDecimal( Months);
            lpbii.setLoanClassId(1);
            lpbii.setTableName("LOAN_PROFIT_BIZ_INCOME_ITEM");
            BigDecimal totalAmout=null;
            if(lpbii.getAverageAmount()!=null ){
                totalAmout=OperationUtil.multiply(lpbii.getAverageAmount(),bigMonths);
            }
            lpbii.setTotalAmount(totalAmout);

            //添加明细表
            iProfitBizIncomeItemProvider.insertProfitBizIncomeItem(lpbii,getLoginInfo().getUserId());
            LoanProfitBizIncomeMonth lpbim=new LoanProfitBizIncomeMonth();
            lpbim.setIncomeId(lpbii.getId());//获取到明细表ID*/
            lpbim.setMonthAmount(lpbii.getAverageAmount());

            //加一个循环
            lpbim.setYearVal(createYear);
            lpbim.setMonthVal(c1.get(Calendar.MONTH)+1);
            iProfitBizIncomeMonthService.insertProfitBizIncomeMonth(lpbim);

            while(c1.getTime().before(createDateEnd)){//少一个循环
                c1.add(Calendar.MONTH, 1);
                int createYearC=c1.get(Calendar.YEAR);
                int createMonthC=c1.get(Calendar.MONTH)+1;
                lpbim.setYearVal(createYearC);
                lpbim.setMonthVal(createMonthC);
                iProfitBizIncomeMonthService.insertProfitBizIncomeMonth(lpbim);
            }


            LoanCrossCheckQuanyiquan lccq=iCrossCheckQuanyiquanService.getCrossCheckQuanyiquanByLoanId(lpbii.getLoanId());
            if(lccq==null){//如果权益表字段为空 先新增字段
                lccq=new LoanCrossCheckQuanyiquan();
                lccq.setLoanClassId(1);
                lccq.setLoanId(lpbii.getLoanId());
                iCrossCheckQuanyiquanService.saveCrossCheckQuanyiquan(lccq,getLoginInfo().getUserId());
            }
            //更新主表的收入总计
            loanIncomeStatementProvider.updateLoanProfitLossInfoByOpt(lpbii.getLoanId(), 1, Integer.valueOf(getLoginInfo().getUserId()));
            renderText(true, "新增成功！", null);
            return ;
        } catch (Exception e) {
            log.error("新增报错", e);
        }
        renderText(false, "新增失败！", null);
        return ;
    }

    /**
     * 跳转新增明细界面
     */
    @NoLoginInterceptor
    @RequestMapping("getMingxiAddPage")
    public String getMingxiAddPage(@RequestParam(value = "columnName" ) String columnName,@RequestParam(value = "loanId" ) String loanId,@RequestParam(value = "createDateEnd" ) String createDateEnd,@RequestParam(value = "createDate" ) String createDate) {
        setAttribute("createDateEnd", createDateEnd);
        setAttribute("createDate", createDate);
        setAttribute("columnName", columnName);//columnName
        setAttribute("loanId", loanId);//loanId
        return "/loan/vm/profitLoss/addMingxi";
    }



    /**
     * 删除营业收入以及其他
     *
     */
    @RequestMapping("deleteBsIncome")
    @ResponseBody
    public void deleteBsIncome(@RequestParam(value = "id" ) String id) {
        try {

            if (StringUtil.isNotEmpty(id)) {

                LoanProfitBizIncomeItem itme = iProfitBizIncomeItemProvider.getProfitBizIncomeItemById(Integer.valueOf(id));
                iProfitBizIncomeItemProvider.deleteProfitBizIncomeItemById(Integer.valueOf(id));
                iProfitBizIncomeMonthProvider.deleteProfitBizIncomeItemMonthByItemId(Integer.valueOf(id));
                //更新主表的收入总计
                loanIncomeStatementProvider.updateLoanProfitLossInfoByOpt(itme.getLoanId(), 1, Integer.valueOf(getLoginInfo().getUserId()));

                renderText(true, "删除成功！", null);
                return;
            }
        } catch (Exception e) {
            log.error("删除报错", e);
        }
        renderText(false, "删除失败！", null);
        return;
    }





    /**
     * 跳转损益情况主页面
     *
     * @return
     */
    @NoLoginInterceptor
    @RequestMapping("getProfitLossPage")
    public String getProfitLossPage(@RequestParam(value = "loanId" ) String loanId,@RequestParam(value = "showApply" ) String showApply) {

        String path="";
        System.err.print("loanId====>"+loanId);
        if (loanId!=null ) {
            setAttribute("loanId", Integer.parseInt(loanId));
            LoanApplyInfo loanApplyInfo = loanApplyService.getApplyInfoById(Integer.parseInt(loanId));
            Integer loanClassId = loanApplyInfo.getLoanClassId();
            if (loanClassId != null) {
                if(loanClassId==1){//经营类
                    //查询损益情况毛利率明细表
                    List<LoanProfitLossRatioItem> lplri = loanProfitLossRatioItemProvider.queryGrossProfitMarginList(Integer.parseInt(loanId), "GROSS_PROFIT_RATIO");

                    //查询损益情况经营类收入支出明细表
                    Map<String, Object> map = new HashedMap();
                    map.put("columnName", "BUSINESS_INCOME_AMOUNT");//营业收入
                    map.put("loanId", loanId);
                    map.put("loanClassId", loanClassId);
                    Map<String, Object> map1 = new HashedMap();
                    map1.put("columnName", "OTHER_INCOME_AMOUNT");//其它收入
                    map1.put("loanId", loanId);
                    map1.put("loanClassId", loanClassId);
                    Map<String, Object> map2 = new HashedMap();
                    map2.put("columnName", "FIXED_COST_DEFRAY_AMOUNT");//固定成本支出
                    map2.put("loanId", loanId);
                    map2.put("loanClassId", loanClassId);
                    Map<String, Object> map3 = new HashedMap();
                    map3.put("columnName", "TEX_DEFRAY_AMOUNT");//所得税支出
                    map3.put("loanId", loanId);
                    map3.put("loanClassId", loanClassId);
                    Map<String, Object> map4 = new HashedMap();
                    map4.put("columnName", "OTHER_DEFRAY_AMOUNT");//其它支出
                    map4.put("loanId", loanId);
                    map4.put("loanClassId", loanClassId);
                    List<LoanProfitBizIncomeItem> liBt = iProfitBizIncomeItemProvider.queryProfitBizIncomeItemList(map);
                    List<LoanProfitBizIncomeItem> liOt = iProfitBizIncomeItemProvider.queryProfitBizIncomeItemList(map1);
                    List<LoanProfitBizIncomeItem> liFt = iProfitBizIncomeItemProvider.queryProfitBizIncomeItemList(map2);
                    List<LoanProfitBizIncomeItem> liTt = iProfitBizIncomeItemProvider.queryProfitBizIncomeItemList(map3);
                    List<LoanProfitBizIncomeItem> liOdt = iProfitBizIncomeItemProvider.queryProfitBizIncomeItemList(map4);
                    BigDecimal totalLplri = new BigDecimal("0");
                    BigDecimal totalLibt = new BigDecimal("0");
                    BigDecimal totalLiot = new BigDecimal("0");
                    BigDecimal totalLift = new BigDecimal("0");
                    BigDecimal totalLitt = new BigDecimal("0");
                    BigDecimal totalLiodt = new BigDecimal("0");

                    //从主表中得到时间

                    LoanProfitLossInfo lpli =	iProfitLossInfoService.getProfitLossInfoByLoanId(Integer.parseInt(loanId));

                    String createDate=lpli.getYearStart()+"-"+lpli.getMonthStart();

                    String createDateEnd=lpli.getYearEnd()+"-"+lpli.getMonthEnd();

                    //加权毛利率总和,
                    totalLplri=lpli.getGrossProfitRatio();


                    //营业收入总和
                    totalLibt=lpli.getBusinessIncomeAmount();

                    //其他收入总和
                    totalLiot=lpli.getOtherIncomeAmount();

                    //固定成本支出总和
                    totalLift=lpli.getFixedCostDefrayAmount();

                    //所得税支出总和
                    totalLitt=lpli.getTexDefrayAmount();

                    //其他支出总和
                    totalLiodt=lpli.getOtherDefrayAmount();
                    /*for (LoanProfitBizIncomeItem liodt : liOdt) {
                        totalLiodt = OperationUtil.plus(totalLiodt, liodt.getTotalAmount());
                    }*/


                    if (totalLplri == null) {
                        setAttribute("totalLplri", totalLplri);//加权毛利率总和
                    }else{
//                      2017-11-21确认保留两位有效数字,采用四舍五入
                        setAttribute("totalLplri",  OperationUtil.divide(new BigDecimal(1), 2, totalLplri));//加权毛利率总和
                    }
                    setAttribute("totalLibt", totalLibt);//营业收入总和
                    setAttribute("totalLiot", totalLiot);//其他收入总和
                    setAttribute("totalLift", totalLift);//固定成本支出总和
                    setAttribute("totalLitt", totalLitt);//所得税支出总和
                    setAttribute("totalLiodt", totalLiodt);//其他支出总和
                    setAttribute("lplri", lplri);//毛利率明细
                    setAttribute("liBt", liBt);//营业收入
                    setAttribute("liOt", liOt);//其它收入
                    setAttribute("liFt", liFt);//固定成本支出
                    setAttribute("liTt", liTt);//所得税支出
                    setAttribute("liOdt", liOdt);//其它支出
                    setAttribute("loanClassId", loanClassId);

                    setAttribute("createDate", createDate);
                    setAttribute("createDateEnd", createDateEnd);
                    setAttribute("showApply", showApply);

                    path= "/loan/vm/profitLoss/ProfitLoss";

                }else{

                    //查询损益情况消费类收入支出明细表
                    Map<String, Object> map = new HashedMap();
                    map.put("columnName", "HOME_INCOME_AMOUNT");//家庭收入
                    map.put("loanId", loanId);
                    map.put("loanClassId", loanClassId);
                    Map<String, Object> map1 = new HashedMap();
                    map1.put("columnName", "OTHER_INCOME_AMOUNT");//其它收入
                    map1.put("loanId", loanId);
                    map1.put("loanClassId", loanClassId);
                    Map<String, Object> map2 = new HashedMap();
                    map2.put("columnName", "FIXED_DEFRAY_AMOUNT");//固定成本支出
                    map2.put("loanId", loanId);
                    map2.put("loanClassId", loanClassId);
                    Map<String, Object> map3 = new HashedMap();
                    map3.put("columnName", "TEX_PERSONAL_AMOUNT");//所得税支出
                    map3.put("loanId", loanId);
                    map3.put("loanClassId", loanClassId);
                    Map<String, Object> map4 = new HashedMap();
                    map4.put("columnName", "OTHER_DEFRAY_AMOUNT");//其它支出
                    map4.put("loanId", loanId);
                    map4.put("loanClassId", loanClassId);
                    List<LoanProfitConsumIncomeItem> liBt = iProfitConsumIncomeItemProvider.queryProfitConsumIncomeItemList(map);
                    List<LoanProfitConsumIncomeItem> liOt = iProfitConsumIncomeItemProvider.queryProfitConsumIncomeItemList(map1);
                    List<LoanProfitConsumIncomeItem> liFt = iProfitConsumIncomeItemProvider.queryProfitConsumIncomeItemList(map2);
                    List<LoanProfitConsumIncomeItem> liTt = iProfitConsumIncomeItemProvider.queryProfitConsumIncomeItemList(map3);
                    List<LoanProfitConsumIncomeItem> liOdt = iProfitConsumIncomeItemProvider.queryProfitConsumIncomeItemList(map4);

                    BigDecimal totalLibt = new BigDecimal("0");
                    BigDecimal totalLiot = new BigDecimal("0");
                    BigDecimal totalLift = new BigDecimal("0");
                    BigDecimal totalLitt = new BigDecimal("0");
                    BigDecimal totalLiodt = new BigDecimal("0");

                    //从主表中得到时间

                    LoanProfitLossInfo lpli =	iProfitLossInfoService.getProfitLossInfoByLoanId(Integer.parseInt(loanId));

                    String createDate=lpli.getYearStart()+"-"+lpli.getMonthStart();

                    String createDateEnd=lpli.getYearEnd()+"-"+lpli.getMonthEnd();

                    //家庭收入总和
                    totalLibt=lpli.getHomeIncomeAmount();

                    //其他收入总和
                    totalLiot=lpli.getOtherIncomeAmount();

                    //成本支出总和
                    totalLift=lpli.getFixedDefrayAmount();

                    //所得税支出总和
                    totalLitt=lpli.getTexPersonalAmount();

                    //其他支出总和
                    totalLiodt=lpli.getOtherDefrayAmount();

                    setAttribute("totalLibt", totalLibt);//家庭收入总和
                    setAttribute("totalLiot", totalLiot);//其他收入总和
                    setAttribute("totalLift", totalLift);//成本支出总和
                    setAttribute("totalLitt", totalLitt);//所得税支出总和
                    setAttribute("totalLiodt", totalLiodt);//其他支出总和
                    setAttribute("liBt", liBt);//家庭收入
                    setAttribute("liOt", liOt);//其它收入
                    setAttribute("liFt", liFt);//固定成本支出
                    setAttribute("liTt", liTt);//所得税支出
                    setAttribute("liOdt", liOdt);//其它支出
                    setAttribute("loanClassId", loanClassId);

                    setAttribute("createDate", createDate);
                    setAttribute("createDateEnd", createDateEnd);
                    setAttribute("showApply", showApply);

                    path= "/loan/vm/profitLoss/consumerProfitLoss";
                }
            }
        }
        return path;
    }
}

