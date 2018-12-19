package banger.moduleImpl;

import banger.dao.intf.*;
import banger.domain.loan.*;
import banger.moduleIntf.ILoanAnalysislBusinessAndConsumProvider;
import banger.moduleIntf.ILoanAssetsProvider;
import banger.service.intf.ILoanAnalysislBusinessAndConsumService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Repository
public class LoanAssetsProviderImpl implements ILoanAssetsProvider {


    @Autowired
    private IAssetsInfoDao assetsInfoDao;

    @Autowired
    private IAssetsAccountItemDao assetsAccountItemDao;

    @Autowired
    private IAssetsAmountItemDao assetsAmountItemDao;

    @Autowired
    private IAssetsDebtsItemDao assetsDebtsItemDao;

    @Autowired
    private IAssetsFixedItemDao assetsFixedItemDao;

    @Autowired
    private IAssetsStockItemDao assetsStockItemDao;

    @Autowired
    private ICrossCheckQuanyiquanDao crossCheckQuanyiquanDao;
    @Autowired
    private ILoanAnalysislBusinessAndConsumProvider analysislBusinessAndConsumProvider;

    @Override
    public LoanAssetsInfo getAssetsInfoByLoanId(Integer loanId) {
        return assetsInfoDao.getAssetsInfoByLoanId(loanId);
    }

    @Override
    public void insertAssetsAccountItem(LoanAssetsAccountItem accountItem) {
        assetsAccountItemDao.insertAssetsAccountItem(accountItem);
    }

    @Override
    public void updateAssetsAccountItem(LoanAssetsAccountItem accountItem) {
        assetsAccountItemDao.updateAssetsAccountItem(accountItem);
    }

    @Override
    public void insertAssetsAmountItem(LoanAssetsAmountItem amountItem) {
        assetsAmountItemDao.insertAssetsAmountItem(amountItem);
    }

    @Override
    public void updateAssetsAmountItem(LoanAssetsAmountItem amountItem) {
        assetsAmountItemDao.updateAssetsAmountItem(amountItem);
    }

    @Override
    public void insertAssetsDebtsItem(LoanAssetsDebtsItem assetsDebtsItem) {
        assetsDebtsItemDao.insertAssetsDebtsItem(assetsDebtsItem);
    }

    @Override
    public void updateAssetsDebtsItem(LoanAssetsDebtsItem assetsDebtsItem) {
        assetsDebtsItemDao.updateAssetsDebtsItem(assetsDebtsItem);
    }

    @Override
    public void insertAssetsFixedItem(LoanAssetsFixedItem assetsFixedItem) {
        assetsFixedItemDao.insertAssetsFixedItem(assetsFixedItem);
    }

    @Override
    public void updateAssetsFixedItem(LoanAssetsFixedItem assetsFixedItem) {
        assetsFixedItemDao.updateAssetsFixedItem(assetsFixedItem);
    }

    @Override
    public void insertAssetsStockItem(LoanAssetsStockItem assetsStockItem) {
        assetsStockItemDao.insertAssetsStockItem(assetsStockItem);
    }

    @Override
    public void updateAssetsStockItem(LoanAssetsStockItem assetsStockItem) {
        assetsStockItemDao.updateAssetsStockItem(assetsStockItem);
    }

    @Override
    public LoanAssetsAccountItem getAssetsAccountById(Integer id) {
        return assetsAccountItemDao.getAssetsAccountItemById(id);
    }

    @Override
    public LoanAssetsDebtsItem getAssetsDebtsById(Integer id) {
        return assetsDebtsItemDao.getAssetsDebtsItemById(id);
    }
    @Override
    public LoanAssetsFixedItem getAssetsFixedById(Integer id) {
        return assetsFixedItemDao.getAssetsFixedItemById(id);
    }
    @Override
    public LoanAssetsStockItem getAssetsStockById(Integer id) {
        return assetsStockItemDao.getAssetsStockItemById(id);
    }

    @Override
    public LoanAssetsAmountItem getAssetsAmountById(Integer id) {
        return assetsAmountItemDao.getAssetsAmountItemById(id);
    }

    @Override
    public void updateAssetsAmount(Integer loanId, Integer loanClassId, Integer userId, BigDecimal amount, String columnName) {

        LoanAssetsInfo assetsInfo = assetsInfoDao.getAssetsInfoByLoanId(loanId);
        if(null==assetsInfo){
            assetsInfo = new LoanAssetsInfo();
            assetsInfo.setLoanId(loanId);
            assetsInfo.setLoanClassId(loanClassId);
            assetsInfo.setCreateUser(userId);
            assetsInfo.setCreateDate(new Date());
            assetsInfoDao.insertAssetsInfo(assetsInfo);
        }

        if(StringUtils.isNotEmpty(columnName)){

            String propertyName = defineVar(columnName);
            Object propertyValue = getValueByProperty(assetsInfo, propertyName);
            BigDecimal currAmount = null!=propertyValue?new BigDecimal(propertyValue.toString()):new BigDecimal(0);
            setValueByProperty(assetsInfo, propertyName,   currAmount.add(amount));

            if(columnName.startsWith("ASSETS_")){
                BigDecimal totalAmount = assetsInfo.getAssetsTotalAmount();
                if(null==totalAmount){
                    totalAmount = new BigDecimal(0);
                }
                assetsInfo.setAssetsTotalAmount(totalAmount.add(amount));
            }else if(columnName.startsWith("DEBTS_")){
                BigDecimal totalAmount = assetsInfo.getDebtsTotalAmount();
                if(null==totalAmount){
                    totalAmount = new BigDecimal(0);
                }
                assetsInfo.setDebtsTotalAmount(totalAmount.add(amount));
            }

            assetsInfoDao.updateAssetsInfo(assetsInfo);


            //交叉检验---权益检验
            LoanCrossCheckQuanyiquan loanCrossCheck = crossCheckQuanyiquanDao.getCrossCheckQuanyiquanByLoanId(loanId);

            BigDecimal assetsTotalAmount = assetsInfo.getAssetsTotalAmount();
            if(null==assetsTotalAmount) assetsTotalAmount = new BigDecimal(0);
            BigDecimal debtsTotalAmount = assetsInfo.getDebtsTotalAmount();
            if(null==debtsTotalAmount) debtsTotalAmount = new BigDecimal(0);

            //实际权益
            BigDecimal actualRight = assetsTotalAmount.subtract(debtsTotalAmount);

            if(null==loanCrossCheck){
                loanCrossCheck = new LoanCrossCheckQuanyiquan();
                loanCrossCheck.setLoanId(loanId);
                loanCrossCheck.setActualRight(actualRight);
                loanCrossCheck.setCreateDate(new Date());
                loanCrossCheck.setCreateUser(userId);
                crossCheckQuanyiquanDao.insertCrossCheckQuanyiquan(loanCrossCheck);
            }else{
                loanCrossCheck.setActualRight(actualRight);
                //应有权益
                if(null==loanCrossCheck.getDeservedRight()){
                    loanCrossCheck.setDeservedRight(new BigDecimal(0));
                    //偏差率
                    loanCrossCheck.setDeviationRatio(new BigDecimal(0));
                }else{
                    //差别
                    loanCrossCheck.setDeviation(loanCrossCheck.getActualRight().subtract(loanCrossCheck.getDeservedRight()));
                    //偏差率
                    if(loanCrossCheck.getDeservedRight().compareTo(BigDecimal.ZERO)!=0){
                        loanCrossCheck.setDeviationRatio(loanCrossCheck.getDeviation().divide(loanCrossCheck.getDeservedRight(),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
                    }else{
                        loanCrossCheck.setDeviationRatio(null);
                    }
                }
                loanCrossCheck.setUpdateDate(new Date());
                loanCrossCheck.setUpdateUser(userId);
                crossCheckQuanyiquanDao.updateCrossCheckQuanyiquan(loanCrossCheck);
            }
        }
        //根据贷款id处理三表数据,另存财务分析详情明细
        analysislBusinessAndConsumProvider.saveAnalysislBusinessOrConsum(loanId);
    }
    @Override
    public List<LoanAssetsAmountItem> getAssetsItemListByColumnName(Integer loanId, String columnName, String tableName) {
        return assetsAmountItemDao.getAssetsItemListByColumnName(loanId,columnName,tableName);
    }

    @Override
    public void removeAssetsAccountById(Integer id) {
        assetsAccountItemDao.deleteAssetsAccountItemById(id);
    }

    @Override
    public void removeAssetsDebtsById(Integer id) {
        assetsDebtsItemDao.deleteAssetsDebtsItemById(id);
    }

    @Override
    public void removeAssetsFixedById(Integer id) {
        assetsFixedItemDao.deleteAssetsFixedItemById(id);
    }

    @Override
    public void removeAssetsAmountById(Integer id) {
        assetsAmountItemDao.deleteAssetsAmountItemById(id);
    }

    @Override
    public void removeAssetsStockById(Integer id) {
        assetsStockItemDao.deleteAssetsStockItemById(id);
    }

    @Override
    public void updateAssetsInfo(LoanAssetsInfo assetsInfo) {
        assetsInfoDao.updateAssetsInfo(assetsInfo);
    }

    @Override
    public void insertAssetsFixedItem(LoanAssetsInfo assetsInfo) {
        assetsInfoDao.insertAssetsInfo(assetsInfo);
    }


    public static String defineVar(String str){
        try{
            String retStr = "";
            String[] ss  = str.split("_");
            for (int i = 0; i < ss.length; i++) {
                char[] ch = ss[i].toLowerCase().toCharArray();
                if(i!=0&&ch[0] >= 'a' && ch[0] <= 'z'){
                    ch[0] = (char)(ch[0] - 32);
                }
                retStr+=new String(ch);
            }
            return retStr;
        }catch (Exception e){
            return null;
        }
    }

    public static void setValueByProperty(Object o, String propertyName,  Object propertyValue) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(propertyName, o.getClass());
            Method method = pd.getWriteMethod();
            method.invoke(o, propertyValue);
        }catch (Exception e){
        }
    }

    public static Object getValueByProperty(Object o, String propertyName) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(propertyName, o.getClass());
            Method method = pd.getReadMethod();
            return method.invoke(o);
        }catch (Exception e){
            return null;
        }
    }


}
