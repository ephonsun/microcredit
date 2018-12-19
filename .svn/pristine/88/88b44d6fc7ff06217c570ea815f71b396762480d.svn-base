package banger.controller;

import banger.common.BaseController;
import banger.domain.loan.*;
import banger.framework.util.DateUtil;
import banger.framework.util.OperationUtil;
import banger.framework.util.StringUtil;
import banger.service.intf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created with 黄福州
 * Date: 2017/9/4
 * Time: 11:01
 * 待调查中客户资产负债操作类
 */
@Controller
@RequestMapping("/assets")
public class LoanAssetsController extends BaseController {
    private static final long serialVersionUID = -6123996727296645355L;

    private String basePath = "/loan/vm/";

    @Autowired
    private IAssetsInfoService assetsInfoService;

    @Autowired
    private IAssetsAmountItemService assetsAmountItemService;

    @Autowired
    private IAssetsDebtsItemService assetsDebtsItemService;

    @Autowired
    private IAssetsFixedItemService assetsFixedItemService;

    @Autowired
    private IAssetsAccountItemService assetsAccountItemService;

    @Autowired
    private IAssetsStockItemService assetsStockItemService;

    /**
     * 选择进入经营类还是消费类
     * @return
     */
    @RequestMapping("/getAssetsMain")
    public String getAssetsMain(){
        Integer loanClassId=Integer.valueOf(getParameter("loanClassId"));
        Integer loanId=Integer.valueOf(getParameter("loanId"));
        String showApply = getParameter("showApply");
        setAttribute("loanId",loanId);
        setAttribute("loanClassId",loanClassId);
        LoanAssetsInfo lasi=assetsInfoService.getAssetsInfoByLoanId(loanId);
        Map<String,Object> assetsMap=assetsInfoService.getAssetsMain(loanId);
        setAttribute("assetsOperMap",assetsMap);
        setAttribute("assetsInfo",lasi);
        setAttribute("showApply",showApply);
        if(null != loanClassId && loanClassId==1){
            return basePath+"assets/assetOperateMain";
        }else {
            return basePath + "assets/assetConsumeMain";
        }
    }

    /**
     * 即时刷新页面
     * @return
     */
    @RequestMapping("/reflushGrid")
    public String reflushGrid(){
        String columnName=getParameter("columnName");
        Integer loanId=Integer.valueOf(getParameter("loanId"));
        String loanClassId=getParameter("loanClassId");
        LoanAssetsInfo asInfo=assetsInfoService.getAssetsInfoByLoanId(loanId);
        Map<String,Object> assetsMap=assetsInfoService.getReflushAssets(loanId,columnName);
        setAttribute("assetsMap",assetsMap);
        setAttribute("asInfo",asInfo);
        if(columnName.equals("ASSETS_FIXED_AMOUNT")){
            columnName = columnName+loanClassId;
        }
        setAttribute("columnName",columnName);
        return basePath + "assets/assetReflushPage";
    }

    /**
     * 即时刷新标题
     * @return
     */
    @RequestMapping("/reflushTitle")
    public String reflushTitle(){
        Integer loanId=Integer.valueOf(getParameter("loanId"));
        String columnName=getParameter("columnName");
        LoanAssetsInfo asInfo=assetsInfoService.getAssetsInfoByLoanId(loanId);
        if(columnName.startsWith("ASSETS_")){
            BigDecimal assetsTotalAmount=asInfo.getAssetsTotalAmount();
            setAttribute("assetsTotalAmount",assetsTotalAmount);
            return basePath + "assets/assetReflushTitle";
        }else if(columnName.startsWith("DEBTS_")){
            BigDecimal debtsTotalAmount=asInfo.getDebtsTotalAmount();
            setAttribute("debtsTotalAmount",debtsTotalAmount);
            return basePath + "assets/assetReflushTitle";
        }
        return "";
    }
    /**
     * 得到编辑和详情界面
     * @return
     */
    @RequestMapping("/getUpdateAssetsPage")
    public String getUpdateAssetsPage(){
        String columnName=getParameter("columnName");
        this.setAttribute("columnName",columnName);
        Integer id=Integer.valueOf(getParameter("id"));
        Integer updordet=Integer.valueOf(getParameter("updordet"));
        String tableName=this.getTableName(columnName);
        if(columnName.equals("offAssets")){
            LoanAssetsInfo loanAssetsInfo = assetsInfoService.getAssetsInfoById(id);
            setAttribute("loanAssetsInfo",loanAssetsInfo);
            if(updordet==1) {
                return basePath + "assets/offAssetsSave";
            }else{
                return basePath + "assets/offAssetsDetail";
            }
        }else{
            Object entity =assetsInfoService.getAssetsInfoEntity(columnName,id,tableName,updordet);
           /* try {
                String ss =  entity.getClass().getDeclaredField("remark").get(entity).toString();
            } catch (Exception e) {
                e.printStackTrace();
            } */
            setAttribute("entity",entity);
            if(tableName.equals("LOAN_ASSETS_ACCOUNT_ITEM")){
                if(updordet==1) {
                    return basePath + "assets/assetsAccountSave";
                }else{
                    return basePath + "assets/assetsAccountDetail";
                }
            }else if(tableName.equals("LOAN_ASSETS_DEBTS_ITEM")){
                if(updordet==1){
                    return basePath+"assets/assetsDebtsSave";
                }else{
                    return basePath+"assets/assetsDebtsDetail";
                }

            }else if(tableName.equals("LOAN_ASSETS_AMOUNT_ITEM")){
                if(updordet==1) {
                    return basePath + "assets/assetsAmountSave";
                }else{
                    return basePath + "assets/assetsAmountDetail";
                }
            }else if(tableName.equals("LOAN_ASSETS_STOCK_ITEM")){
                if(updordet==1) {
                    return basePath + "assets/assetsStockSave";
                }else{
                    return basePath + "assets/assetsStockDetail";
                }
            }else if(tableName.equals("LOAN_ASSETS_FIXED_ITEM")){
                if(updordet==1) {
                    return basePath + "assets/assetsFixedSave";
                }else{
                    return basePath + "assets/assetsFixedDetail";
                }
            }
            return null;
        }
    }

    /**
     * 修改资产负债金额（Amount项目）
     * @param loanAssetsAmount
     */
    @RequestMapping("/updateAmountAssets")
    public void updateAmountAssets(LoanAssetsAmountItem loanAssetsAmount) {
        try{
            String columnName = this.getParameter("columnName");
            Integer loanId = Integer.valueOf(this.getParameter("loanId"));
            Integer id = loanAssetsAmount.getId();
            BigDecimal oldAmount = new BigDecimal(0);
            BigDecimal amount=loanAssetsAmount.getAmount();
            if(null==amount){
                amount = new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP);
                loanAssetsAmount.setAmount(amount);
            }
            LoanAssetsAmountItem oldAmountItem = assetsAmountItemService.getAssetsAmountItemById(id);
            if (null != oldAmountItem.getAmount()){
                oldAmount = oldAmountItem.getAmount();
            }
            Integer loginUserId = getLoginInfo().getUserId();
            assetsAmountItemService.updateAssetsAmountItem(loanAssetsAmount, loginUserId);
            assetsInfoService.updateAssetsAmount(loanId,amount.subtract(oldAmount),columnName,loginUserId);
            renderText(true, "修改成功!", null);
        }catch(Exception e){
            log.error("updateAmountAssets error",e);
            renderText(false,"修改失败!",null);
        }
    }

    /**
     * 修改资产负债账款（Acount项目）
     * @param loanAssetsAccount
     */
    @RequestMapping("/updateAcountAssets")
    public void updateAcountAssets(LoanAssetsAccountItem loanAssetsAccount){
        try{
            String columnName = this.getParameter("columnName");
            Integer loanId = Integer.valueOf(this.getParameter("loanId"));
            Integer id = loanAssetsAccount.getId();
            BigDecimal oldAmount = new BigDecimal(0);
            BigDecimal amount=loanAssetsAccount.getAmount();
            if(null == amount){
                amount = new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP);;
                loanAssetsAccount.setAmount(amount);
            }
            LoanAssetsAccountItem oldAmountItem = assetsAccountItemService.getAssetsAccountItemById(id);
            if (null != oldAmountItem.getAmount()){
                oldAmount = oldAmountItem.getAmount();
            }
            Integer loginUserId = getLoginInfo().getUserId();
            assetsAccountItemService.updateAssetsAccountItem(loanAssetsAccount, loginUserId);
            assetsInfoService.updateAssetsAmount(loanId,amount.subtract(oldAmount),columnName,loginUserId);
            renderText(true, "修改成功!", null);
        }catch(Exception e){
            log.error("updateAcountAssets error",e);
            renderText(false,"修改失败!",null);
        }

    }

    /**
     * 修改资产负债项（Debts项目）
     * @param loanAssetsDebts
     */
    @RequestMapping("/updateDebtsAssets")
    public void updateDebtsAssets(LoanAssetsDebtsItem loanAssetsDebts){
        try{
            String issueDate=this.getParameter("NotIssueDate");
            if(StringUtil.isNotEmpty(issueDate)){
                loanAssetsDebts.setIssueDate(DateUtil.parser(issueDate, DateUtil.DEFAULT_DATE_FORMAT));
            }
            String columnName = this.getParameter("columnName");
            Integer loanId = Integer.valueOf(this.getParameter("loanId"));
            Integer id = loanAssetsDebts.getId();
            BigDecimal oldAmount = new BigDecimal(0);
            BigDecimal amount=loanAssetsDebts.getBalanceAmount();
            if(null == amount){
                amount = new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP);
                loanAssetsDebts.setBalanceAmount(amount);
            }
            LoanAssetsDebtsItem oldAmountItem = assetsDebtsItemService.getAssetsDebtsItemById(id);
            if (null != oldAmountItem.getBalanceAmount()){
                oldAmount = oldAmountItem.getBalanceAmount();
            }
            Integer loginUserId = getLoginInfo().getUserId();
            assetsDebtsItemService.updateAssetsDebtsItem(loanAssetsDebts, loginUserId);
            assetsInfoService.updateAssetsAmount(loanId,amount.subtract(oldAmount),columnName,loginUserId);
            renderText(true, "修改成功!", null);
        }catch(Exception e){
            log.error("updateDebtsAssets error",e);
            renderText(false,"修改失败!",null);
        }

    }

    /**
     * 修改固定资产项（Fixed项目）
     * @param loanAssetsFixed
     */
    @RequestMapping("/updateFixedAssets")
    public void updateFixedAssets(LoanAssetsFixedItem loanAssetsFixed){
        try{
            String columnName = this.getParameter("columnName");
            Integer loanId = Integer.valueOf(this.getParameter("loanId"));
            Integer id = loanAssetsFixed.getId();
            BigDecimal preAmount = new BigDecimal(0);
            BigDecimal oldAmount = loanAssetsFixed.getOldAmount();
            BigDecimal depreciationAmount = loanAssetsFixed.getDepreciationAmount();
            Integer itemAccount=Integer.valueOf(loanAssetsFixed.getItemAccount()==null?0:loanAssetsFixed.getItemAccount());
            BigDecimal newAmount=new BigDecimal(0);
            BigDecimal amount=new BigDecimal(0);
            BigDecimal depreciationRatio=new BigDecimal(0);
            loanAssetsFixed.setItemAccount(itemAccount);
            if(null == oldAmount){
                oldAmount = new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP);
                loanAssetsFixed.setOldAmount(oldAmount);
            }
            if(null == depreciationAmount){
                depreciationAmount = new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP);
                loanAssetsFixed.setDepreciationAmount(depreciationAmount);
            }
            if(depreciationAmount.compareTo(oldAmount)==1){
                renderText(false, "折旧金额不能大于原值!", null);
            }else {
                BigDecimal BigitemAccount = new BigDecimal(itemAccount);
                newAmount = oldAmount.subtract(depreciationAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
                amount = newAmount.multiply(BigitemAccount).setScale(2, BigDecimal.ROUND_HALF_UP);
                ;
                if (null != depreciationAmount && oldAmount != new BigDecimal(0)) {
                    depreciationRatio = OperationUtil.depreciation(oldAmount, 2, depreciationAmount);
                    loanAssetsFixed.setDepreciationRatio(depreciationRatio);
                }
                loanAssetsFixed.setNewAmount(newAmount);
                loanAssetsFixed.setAmount(amount);
                LoanAssetsFixedItem oldAmountItem = assetsFixedItemService.getAssetsFixedItemById(id);
                if (null != oldAmountItem.getAmount()) {
                    preAmount = oldAmountItem.getAmount();
                }
                Integer loginUserId = getLoginInfo().getUserId();
                assetsFixedItemService.updateAssetsFixedItem(loanAssetsFixed, loginUserId);
                assetsInfoService.updateAssetsAmount(loanId, amount.subtract(preAmount), columnName,loginUserId);
                renderText(true, "修改成功!", null);
            }
        }catch(Exception e){
            log.error("updateFixedAssets error",e);
            renderText(false,"修改失败!",null);
        }

    }

    /**
     * 修改资产负债存货（Stock项目）
     * @param loanAssetsStock
     */
    @RequestMapping("/updateStockAssets")
    public void updateStockAssets(LoanAssetsStockItem loanAssetsStock){
        try{
            String columnName = this.getParameter("columnName");
            Integer loanId = Integer.valueOf(this.getParameter("loanId"));
            Integer id = loanAssetsStock.getId();
            Integer itemCount = Integer.valueOf(loanAssetsStock.getItemCount()==null ? 0:loanAssetsStock.getItemCount());
            BigDecimal itemPrice = loanAssetsStock.getItemPrice();
            BigDecimal oldAmount = new BigDecimal(0);
            BigDecimal amount = new BigDecimal(0);
            loanAssetsStock.setItemCount(itemCount);
            if(null == itemPrice){
                itemPrice = new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP);
                loanAssetsStock.setItemPrice(itemPrice);
            }
            BigDecimal BigItemCount = new BigDecimal(itemCount);
            amount = BigItemCount.multiply(itemPrice).setScale(2,BigDecimal.ROUND_HALF_UP);
            loanAssetsStock.setAmount(amount);
            LoanAssetsStockItem oldAmountItem = assetsStockItemService.getAssetsStockItemById(id);
            if (null != oldAmountItem.getAmount()){
                oldAmount = oldAmountItem.getAmount();
            }
            Integer loginUserId = getLoginInfo().getUserId();
            assetsStockItemService.updateAssetsStockItem(loanAssetsStock, loginUserId);
            assetsInfoService.updateAssetsAmount(loanId,amount.subtract(oldAmount),columnName,loginUserId);//更新主表
            renderText(true, "修改成功!", null);
        }catch(Exception e){
            log.error("updateAcountAssets error",e);
            renderText(false,"修改失败!",null);
        }

    }


    /**
     * 修改资产负债表外（offAssets项目）
     * @param loanAssetsInfo
     */
    @RequestMapping("/updateOffAssets")
    public void updateOffAssets(LoanAssetsInfo loanAssetsInfo){
        try{
            Integer loginUserId = getLoginInfo().getUserId();
            assetsInfoService.updateAssetsInfo(loanAssetsInfo,loginUserId);
            renderText(true, "修改成功!", null);
        }catch(Exception e){
            log.error("updateAcountAssets error",e);
            renderText(false,"修改失败!",null);
        }

    }

    /**
     * 删除资产负债项（所有项）
     */
    @RequestMapping("/deleteAssets")
    public void deleteAssets(){
        try{
            Integer loginUserId = getLoginInfo().getUserId();
            Integer loanId=Integer.valueOf(getParameter("loanId"));
            String columnName=getParameter("columnName");
            Integer id=Integer.valueOf(getParameter("id"));
            if(columnName.equals("offAssets")){
                LoanAssetsInfo assetsInfo=new LoanAssetsInfo();
                assetsInfo.setOffAssetsAmount(new BigDecimal(0));
                assetsInfo.setOffDebtsAmount(new BigDecimal(0));
                assetsInfo.setOffRemark("");
                assetsInfo.setId(id);
                assetsInfoService.updateAssetsInfo(assetsInfo,loginUserId);
            }else {
                String tableName = this.getTableName(columnName);
                BigDecimal amount = assetsInfoService.deleteAssets(tableName, id);
                if (null != amount) {
                    assetsInfoService.updateAssetsAmount(loanId, amount.multiply(new BigDecimal(-1)), columnName,loginUserId);//更新主表
                }
            }
            renderText(true, "删除成功!", null);
        }catch(Exception e){
            log.error("deleteAssets error",e);
            renderText(false,"删除失败!",null);
        }

    }

    /**
     * 得到资产负债添加界面（所有项）
     */
    @RequestMapping("/getAddAssetsPage")
    public String getAddAssetsPage() {
        String columnName=this.getParameter("columnName");
        String loanClassId=this.getParameter("loanClassId");
        String tableName=this.getTableName(columnName);
        String loanId=this.getParameter("loanId");
        setAttribute("columnName",columnName);
        setAttribute("loanClassId",loanClassId);
        setAttribute("loanId",loanId);

        if(tableName.equals("LOAN_ASSETS_ACCOUNT_ITEM")){
            return basePath + "assets/assetsAccountSave";
        }else if(tableName.equals("LOAN_ASSETS_DEBTS_ITEM")){
            return basePath+"assets/assetsDebtsSave";
        }else if(tableName.equals("LOAN_ASSETS_AMOUNT_ITEM")){
            return basePath + "assets/assetsAmountSave";
        }else if(tableName.equals("LOAN_ASSETS_STOCK_ITEM")){
            return basePath + "assets/assetsStockSave";
        }else if(tableName.equals("LOAN_ASSETS_FIXED_ITEM")){
            return basePath + "assets/assetsFixedSave";
        }
        return null;
    }

    /**
     * 添加资产负债项（Amount）
     */
    @RequestMapping("/addAmountAssets")
    public void addAmountAssets(LoanAssetsAmountItem loanAssetsAmount) {
        try{
            Integer loginUserId = getLoginInfo().getUserId();
            String columnName=this.getParameter("columnName");
            String tableName=this.getTableName(columnName);
            Integer loanId=Integer.valueOf(this.getParameter("loanId"));
            Integer loanClassId=Integer.valueOf(this.getParameter("loanClassId"));
            BigDecimal amount=loanAssetsAmount.getAmount();
            if(null == amount){
                amount = new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP);
            }
            assetsInfoService.updateAssetsAmount(loanId,amount,columnName,loginUserId);//更新主表
            loanAssetsAmount.setColumnName(columnName);
            loanAssetsAmount.setTableName(tableName);
            loanAssetsAmount.setLoanId(loanId);
            loanAssetsAmount.setLoanClassId(loanClassId);
            assetsAmountItemService.insertAssetsAmountItem(loanAssetsAmount, loginUserId);
            renderText(true, "添加成功!", null);
        }catch(Exception e){
            log.error("addAmountAssets error",e);
            renderText(false,"添加失败!",null);
        }
    }

    /**
     * 添加资产负债项（Account）
     */
    @RequestMapping("/addAccountAssets")
    public void addAccountAssets(LoanAssetsAccountItem loanAssetsAccount) {
        try{
            Integer loginUserId = getLoginInfo().getUserId();
            String columnName=this.getParameter("columnName");
            String tableName=this.getTableName(columnName);
            Integer loanId=Integer.valueOf(this.getParameter("loanId"));
            Integer loanClassId=Integer.valueOf(this.getParameter("loanClassId"));
            loanAssetsAccount.setColumnName(columnName);
            loanAssetsAccount.setTableName(tableName);
            loanAssetsAccount.setLoanId(loanId);
            loanAssetsAccount.setLoanClassId(loanClassId);
            BigDecimal amount=loanAssetsAccount.getAmount();
            if(null != amount){
                assetsInfoService.updateAssetsAmount(loanId,amount,columnName,loginUserId);//更新主表
            }
            assetsAccountItemService.insertAssetsAccountItem(loanAssetsAccount, loginUserId);
            renderText(true, "添加成功!", null);
        }catch(Exception e){
            log.error("addAccountAssets error",e);
            renderText(false,"添加失败!",null);
        }
    }

    /**
     * 添加资产负债项（Debts）
     */
    @RequestMapping("/addDebtsAssets")
    public void addDebtsAssets(LoanAssetsDebtsItem loanAssetsDebts) {
        try{
            Integer loginUserId = getLoginInfo().getUserId();
            String issueDate=this.getParameter("NotIssueDate");
            String columnName=this.getParameter("columnName");
            String tableName=this.getTableName(columnName);
            Integer loanId=Integer.valueOf(this.getParameter("loanId"));
            Integer loanClassId=Integer.valueOf(this.getParameter("loanClassId"));
            loanAssetsDebts.setColumnName(columnName);
            loanAssetsDebts.setTableName(tableName);
            loanAssetsDebts.setLoanId(loanId);
            loanAssetsDebts.setLoanClassId(loanClassId);
            BigDecimal amount=loanAssetsDebts.getBalanceAmount();
            if(StringUtil.isNotEmpty(issueDate)){
                loanAssetsDebts.setIssueDate(DateUtil.parser(issueDate, DateUtil.DEFAULT_DATE_FORMAT));
            }
            if(null != amount){
                assetsInfoService.updateAssetsAmount(loanId,amount,columnName,loginUserId);//更新主表
            }
            assetsDebtsItemService.insertAssetsDebtsItem(loanAssetsDebts, loginUserId);
            renderText(true, "添加成功!", null);
        }catch(Exception e){
            log.error("addDebtsAssets error",e);
            renderText(false,"添加失败!",null);
        }
    }

    /**
     * 添加资产负债项（Fixed）
     */
    @RequestMapping("/addFixedAssets")
    public void addFixedAssets(LoanAssetsFixedItem loanAssetsFixed) {
        try{
            Integer loginUserId = getLoginInfo().getUserId();
            String columnName=this.getParameter("columnName");
            String tableName=this.getTableName(columnName);
            Integer loanId=Integer.valueOf(this.getParameter("loanId"));
            Integer loanClassId=Integer.valueOf(this.getParameter("loanClassId"));
            loanAssetsFixed.setColumnName(columnName);
            loanAssetsFixed.setTableName(tableName);
            loanAssetsFixed.setLoanId(loanId);
            loanAssetsFixed.setLoanClassId(loanClassId);
            BigDecimal ll=loanAssetsFixed.getOldAmount();
            BigDecimal oldAmount=new BigDecimal(0);
            BigDecimal depreciationAmount=new BigDecimal(0);
            BigDecimal itemAccount=new BigDecimal(0);
            BigDecimal newAmount=new BigDecimal(0);
            BigDecimal amount=new BigDecimal(0);
            BigDecimal depreciationRatio=new BigDecimal(0);
            if(null != loanAssetsFixed.getOldAmount()){
                oldAmount = loanAssetsFixed.getOldAmount();
            }
            if(null != loanAssetsFixed.getDepreciationAmount()){
                depreciationAmount = loanAssetsFixed.getDepreciationAmount();
            }
            if(depreciationAmount.compareTo(oldAmount)==1){
                renderText(false, "折旧金额不能大于原值!", null);
            }else {
                newAmount = oldAmount.subtract(depreciationAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
                loanAssetsFixed.setNewAmount(newAmount);
                if (null != loanAssetsFixed.getItemAccount()) {
                    itemAccount = new BigDecimal(loanAssetsFixed.getItemAccount());
                }
                amount = OperationUtil.multiply(newAmount, itemAccount);
                loanAssetsFixed.setAmount(amount);
                if (null != depreciationAmount && oldAmount != new BigDecimal(0)) {
                    depreciationRatio = OperationUtil.depreciation(oldAmount, 2, depreciationAmount);
                    loanAssetsFixed.setDepreciationRatio(depreciationRatio);
                }

                if (null != amount) {
                    assetsInfoService.updateAssetsAmount(loanId, amount, columnName,loginUserId);//更新主表
                }
                assetsFixedItemService.insertAssetsFixedItem(loanAssetsFixed, loginUserId);
                renderText(true, "添加成功!", null);
            }
        }catch(Exception e){
            log.error("addFixedAssets error",e);
            renderText(false,"添加失败!",null);
        }
    }

    /**
     * 添加资产负债项（Stock）
     */
    @RequestMapping("/addStockAssets")
    public void addStockAssets(LoanAssetsStockItem loanAssetsStock) {
        try{
            Integer loginUserId = getLoginInfo().getUserId();
            String columnName=this.getParameter("columnName");
            String tableName=this.getTableName(columnName);
            Integer loanId=Integer.valueOf(this.getParameter("loanId"));
            Integer loanClassId=Integer.valueOf(this.getParameter("loanClassId"));
            loanAssetsStock.setColumnName(columnName);
            loanAssetsStock.setTableName(tableName);
            loanAssetsStock.setLoanId(loanId);
            loanAssetsStock.setLoanClassId(loanClassId);
            Integer itemCount = Integer.valueOf(loanAssetsStock.getItemCount()==null?0:loanAssetsStock.getItemCount());
            BigDecimal itemPrice = loanAssetsStock.getItemPrice();
            BigDecimal amount = new BigDecimal(0);
            BigDecimal BigItemCount = new BigDecimal(itemCount);
            if(null == itemPrice){
                itemPrice = new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP);
            }
            amount = BigItemCount.multiply(itemPrice).setScale(2,BigDecimal.ROUND_HALF_UP);
            loanAssetsStock.setAmount(amount);
            assetsInfoService.updateAssetsAmount(loanId,amount,columnName,loginUserId);//更新主表
            assetsStockItemService.insertAssetsStockItem(loanAssetsStock, loginUserId);
            renderText(true, "添加成功!", null);
        }catch(Exception e){
            log.error("addAccountAssets error",e);
            renderText(false,"添加失败!",null);
        }
    }

    /**
     * 根据列名获取表名
     * @param columnName
     * @return
     */
    private String getTableName(String columnName){
        if (LoanUtil.LOAN_ASSETS_ACCOUNT_ITEM.contains(columnName)){
            return "LOAN_ASSETS_ACCOUNT_ITEM";
        }else if(LoanUtil.LOAN_ASSETS_DEBTS_ITEM.contains(columnName)){
            return "LOAN_ASSETS_DEBTS_ITEM";
        }else if(LoanUtil.LOAN_ASSETS_AMOUNT_ITEM.contains(columnName)){
            return "LOAN_ASSETS_AMOUNT_ITEM";
        }else if(LoanUtil.LOAN_ASSETS_STOCK_ITEM.contains(columnName)){
            return "LOAN_ASSETS_STOCK_ITEM";
        }else if(LoanUtil.LOAN_ASSETS_FIXED_ITEM.contains(columnName)){
            return "LOAN_ASSETS_FIXED_ITEM";
        }
        return null;
    }


}


