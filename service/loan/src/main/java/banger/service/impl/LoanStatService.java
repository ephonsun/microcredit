package banger.service.impl;

import banger.dao.intf.ILoanStatDao;
import banger.domain.loan.LoanStatQuery;
import banger.service.intf.ILoanStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class LoanStatService implements ILoanStatService {
    @Autowired
    private ILoanStatDao loanStatDao;
    public List<LoanStatQuery> queryLoanStatListByUserId(Map map){
        List<LoanStatQuery> list=loanStatDao.queryLoanStatListByUserId(map);
        for(LoanStatQuery loanStatQuery:list){
            convert(loanStatQuery);
        }
        return list;
    }

    @Override
    public List<LoanStatQuery> queryLoanStatListByUserIdAndQuarter(Map map) {
        List<LoanStatQuery> list=loanStatDao.queryLoanStatListByUserIdAndQuarter(map);
        for(LoanStatQuery loanStatQuery:list){
            convert1(loanStatQuery);
        }
        return list;
    }

    @Override
    public List<LoanStatQuery> queryLoanStatListByUserIdAndYear(Map map) {
        List<LoanStatQuery> list=loanStatDao.queryLoanStatListByUserIdAndYear(map);
        for(LoanStatQuery loanStatQuery:list){
            convert2(loanStatQuery);
        }
        return list;
    }

    @Override
    public List<LoanStatQuery> queryLoanStatListByTeamGroupId(Map map) {
        List<LoanStatQuery> list=loanStatDao.queryLoanStatListByTeamGroupId(map);
        for(LoanStatQuery loanStatQuery:list){
            convert(loanStatQuery);
        }
        return list;
    }

    @Override
    public List<LoanStatQuery> queryLoanStatListByTeamGroupIdAndYear(Map map) {
        List<LoanStatQuery> list=loanStatDao.queryLoanStatListByTeamGroupIdAndYear(map);
        for(LoanStatQuery loanStatQuery:list){
            convert2(loanStatQuery);
        }
        return list;
    }

    @Override
    public List<LoanStatQuery> queryLoanStatListByTeamGroupIdAndQuarter(Map map) {
        List<LoanStatQuery> list= loanStatDao.queryLoanStatListByTeamGroupIdAndQuarter(map);
        for(LoanStatQuery loanStatQuery:list){
            convert1(loanStatQuery);
        }
        return list;
    }

    @Override
    public List<LoanStatQuery> queryCrossLoanStatByYear(Map map) {
        List<LoanStatQuery> list= loanStatDao.queryCrossLoanStatByYear(map);
        for(LoanStatQuery loanStatQuery:list){
            convert2(loanStatQuery);
        }
        return list;
    }

    @Override
    public List<LoanStatQuery> queryCrossLoanStatByMonth(Map map) {
        List<LoanStatQuery> list= loanStatDao.queryCrossLoanStatByMonth(map);
        for(LoanStatQuery loanStatQuery:list){
            convert(loanStatQuery);
        }
        return list;
    }

    @Override
    public List<LoanStatQuery> queryCrossLoanStatByQuarter(Map map) {
        List<LoanStatQuery> list = loanStatDao.queryCrossLoanStatByQuarter(map);
        for(LoanStatQuery loanStatQuery:list){
            convert1(loanStatQuery);
        }
        return list;
    }

    @Override
    public List<LoanStatQuery> queryCrossLoanStatListByYear(Map map) {
        List<LoanStatQuery> list=loanStatDao.queryCrossLoanStatListByYear(map);
        for(LoanStatQuery loanStatQuery:list){
            convert2(loanStatQuery);
        }
        return list;
    }

    @Override
    public List<LoanStatQuery> queryCrossLoanStatListByQuarter(Map map) {
        List<LoanStatQuery> list=loanStatDao.queryCrossLoanStatListByQuarter(map);
        for(LoanStatQuery loanStatQuery:list){
            convert1(loanStatQuery);
        }
        return list;
    }

    @Override
    public List<LoanStatQuery> queryCrossLoanStatListByMonth(Map map) {
        List<LoanStatQuery> list= loanStatDao.queryCrossLoanStatListByMonth(map);
        for(LoanStatQuery loanStatQuery:list){
            convert(loanStatQuery);
        }
        return list;
    }

    public void convert(LoanStatQuery loanStatQuery){
        if(loanStatQuery.getYear()!=null&&!"".equals(loanStatQuery.getYear())&&loanStatQuery.getMonth()!=null&&!"".equals(loanStatQuery.getMonth())){
            loanStatQuery.setTime(loanStatQuery.getYear()+"年 "+loanStatQuery.getMonth()+"月");
        }
        if(loanStatQuery.getLoanSuccessNum()==null||loanStatQuery.getLoanSuccessNum()==0){
            loanStatQuery.setLoanPercent("0%");
        }else{

            double percent=(loanStatQuery.getLoanSuccessNum()/(double)loanStatQuery.getLoanTotalNum())*100;
            loanStatQuery.setLoanPercent(String.format("%.2f",percent)+"%");
        }
        if(loanStatQuery.getLoanTotalMoney()==null&&loanStatQuery.getLoanTotalMoney()==0){
            loanStatQuery.setLoanTotalMoneyW(0.0);
        }else{
            loanStatQuery.setLoanTotalMoneyW(Double.valueOf(String.format("%.2f",loanStatQuery.getLoanTotalMoney()/10000)));
        }
        if(loanStatQuery.getLoanSuccessNum()==0||loanStatQuery.getLoanSuccessNum()==null){
            loanStatQuery.setLoanAvgW(0.0);

        }else{
            loanStatQuery.setLoanAvgW(Double.valueOf( String.format("%.2f",loanStatQuery.getLoanTotalMoney()/10000/loanStatQuery.getLoanSuccessNum())));
        }
        loanStatQuery.setLoanWeekSum(loanStatQuery.getLoanWeekSum().divide(new BigDecimal(10000)));
        loanStatQuery.setLoanAvgRatio(loanStatQuery.getLoanAvgRatio().setScale(2, BigDecimal.ROUND_HALF_UP));
        loanStatQuery.setOtherRefuse(loanStatQuery.getAllRefuse()-loanStatQuery.getApprovalRefuse());

    }

    public void convert1(LoanStatQuery loanStatQuery){
        if(loanStatQuery.getYear()!=null&&!"".equals(loanStatQuery.getYear())&&loanStatQuery.getMonth()!=null&&!"".equals(loanStatQuery.getMonth())){
            String year=loanStatQuery.getYear();
            loanStatQuery.setTime(year+"年 第"+loanStatQuery.getMonth()+"季度");
        }
        if(loanStatQuery.getLoanSuccessNum()==null||loanStatQuery.getLoanSuccessNum()==0){
            loanStatQuery.setLoanPercent("0%");
        }else{

            double percent=(loanStatQuery.getLoanSuccessNum()/(double)loanStatQuery.getLoanTotalNum())*100;
            loanStatQuery.setLoanPercent(String.format("%.2f",percent)+"%");
        }
        if(loanStatQuery.getLoanTotalMoney()==null&&loanStatQuery.getLoanTotalMoney()==0){
            loanStatQuery.setLoanTotalMoneyW(0.0);
        }else{
            loanStatQuery.setLoanTotalMoneyW(Double.valueOf(String.format("%.2f",loanStatQuery.getLoanTotalMoney()/10000)));
        }
        if(loanStatQuery.getLoanSuccessNum()==0||loanStatQuery.getLoanSuccessNum()==null){
            loanStatQuery.setLoanAvgW(0.0);

        }else{
            loanStatQuery.setLoanAvgW(Double.valueOf( String.format("%.2f",loanStatQuery.getLoanTotalMoney()/10000/loanStatQuery.getLoanSuccessNum())));
        }
        loanStatQuery.setLoanWeekSum(loanStatQuery.getLoanWeekSum().divide(new BigDecimal(10000)));
        loanStatQuery.setLoanAvgRatio(loanStatQuery.getLoanAvgRatio().setScale(2, BigDecimal.ROUND_HALF_UP));
        loanStatQuery.setOtherRefuse(loanStatQuery.getAllRefuse()-loanStatQuery.getApprovalRefuse());

    }

    public void convert2(LoanStatQuery loanStatQuery){
        if(loanStatQuery.getYear()!=null&&!"".equals(loanStatQuery.getYear())){
            loanStatQuery.setTime(loanStatQuery.getYear()+"年 ");
        }
        if(loanStatQuery.getLoanSuccessNum()==null||loanStatQuery.getLoanSuccessNum()==0){
            loanStatQuery.setLoanPercent("0%");
        }else{

            double percent=(loanStatQuery.getLoanSuccessNum()/(double)loanStatQuery.getLoanTotalNum())*100;
            loanStatQuery.setLoanPercent(String.format("%.2f",percent)+"%");
        }
        if(loanStatQuery.getLoanTotalMoney()==null&&loanStatQuery.getLoanTotalMoney()==0){
            loanStatQuery.setLoanTotalMoneyW(0.0);
        }else{


            loanStatQuery.setLoanTotalMoneyW(Double.valueOf(String.format("%.2f",loanStatQuery.getLoanTotalMoney()/10000)));
        }
        if(loanStatQuery.getLoanSuccessNum()==0||loanStatQuery.getLoanSuccessNum()==null){
            loanStatQuery.setLoanAvgW(0.0);

        }else{

            loanStatQuery.setLoanAvgW(Double.valueOf( String.format("%.2f",loanStatQuery.getLoanTotalMoney()/10000/loanStatQuery.getLoanSuccessNum())));
        }
        loanStatQuery.setLoanWeekSum(loanStatQuery.getLoanWeekSum().divide(new BigDecimal(10000)));
        loanStatQuery.setLoanAvgRatio(loanStatQuery.getLoanAvgRatio().setScale(2, BigDecimal.ROUND_HALF_UP));
        loanStatQuery.setOtherRefuse(loanStatQuery.getAllRefuse()-loanStatQuery.getApprovalRefuse());

    }
    /**
     * CSV文件生成方法
     *
     * @param head
     * @param dataList
     * @param outPutPath
     * @param filename
     * @return
     */
    @Override
    public File createCSVFile(List<String> head, List<List<String>> dataList, String outPutPath, String filename) throws IOException {
        File csvFile = null;
        BufferedWriter csvWtriter = null;
        try {
            csvFile = new File(outPutPath + File.separator + filename + ".csv");
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            // GB2312使正确读取分隔符","
            csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    csvFile), "GB2312"), 1024);
            // 写入文件头部
            writeRow(head, csvWtriter);

            // 写入文件内容
            for (List<String> row : dataList) {
                writeRow(row, csvWtriter);
            }
            csvWtriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvWtriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }


    /**
     * 写一行数据方法
     *
     * @param row
     * @param csvWriter
     * @throws IOException
     */
    public void writeRow(List<String> row, BufferedWriter csvWriter) throws IOException {
        // 写入文件头部
        for (String data : row) {
            StringBuffer sb = new StringBuffer();
            String rowStr = sb.append("\"").append(data).append("\",").toString();
            csvWriter.write(rowStr);
        }
        csvWriter.newLine();
    }
}
