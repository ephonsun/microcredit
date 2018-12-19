package banger.service.impl;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import banger.dao.intf.ITaskStatDao;
import banger.domain.task.TskTaskStatQuery;
import banger.service.intf.ITaskStatService;
@Repository
public class TaskStatService implements ITaskStatService {

	@Autowired
	private ITaskStatDao taskStatDao;

	@Override
	public List<TskTaskStatQuery> queryLoanTasksListForTeam(Map<String, Object> condition) {
		return this.taskStatDao.queryLoanTasksListForTeam(condition);
	}

	@Override
	public List<TskTaskStatQuery> queryLoanTasksList(Map<String, Object> condition) {
		return this.taskStatDao.queryLoanTasksList(condition);
	}

	@Override
	public List<TskTaskStatQuery> queryPersonList(Map<String, Object> condition) {
		List<TskTaskStatQuery> list=this.taskStatDao.queryPersonList(condition);
		for(TskTaskStatQuery tskTaskStatQuery:list){
			convert(tskTaskStatQuery);
		}
		return list;
	}

	@Override
	public List<TskTaskStatQuery> queryTaskStatList(Map<String, Object> condition) {
		List<TskTaskStatQuery> listnew = new ArrayList<TskTaskStatQuery>();
		List<TskTaskStatQuery> ts= new ArrayList<TskTaskStatQuery>();
		List<TskTaskStatQuery> list=this.taskStatDao.queryTaskStatList(condition);
		for(TskTaskStatQuery tskTaskStatQuery:list){

			convert(tskTaskStatQuery);
		}
		Integer teamTarget;
		for (int i = 0; i<list.size() ; i++) {
			if (i!=list.size()-1) {
				if(!list.get(i).getTaskTitle().equals(list.get(i+1).getTaskTitle())){
					TskTaskStatQuery t=new TskTaskStatQuery();
					t.setGroupName(list.get(i).getGroupName());
					t.setTaskTitle(list.get(i).getTaskTitle());
					t.setUserName("合计");
//					listnew.add(list.get(i));
					ts.add(list.get(i));
					if(list.get(i).getTskLevel()==3){
						teamTarget = list.get(i).getTaskTarget();
					}else{
						teamTarget=this.taskStatDao.queryGroupAssignTargetByIds(list.get(i).getTaskId(),list.get(i).getTeamGroupId());
					}
					int assignNum=0;
					int finishNum=0;
					for(int j=0;j<ts.size();j++){
                    assignNum+=ts.get(j).getAssignNum();
                    finishNum+=ts.get(j).getFinishNum();
					}
					if(teamTarget!=null&&teamTarget>assignNum){
						TskTaskStatQuery t1=new TskTaskStatQuery();
						t1.setGroupName(list.get(i).getGroupName());
						t1.setTaskTitle(list.get(i).getTaskTitle());
						t1.setUserName("未分配");
						t1.setTaskMold(list.get(i).getTaskMold());
						t1.setAssignNum(teamTarget-assignNum);
						t1.setTaskPercent("");
						t1.setTaskType(list.get(i).getTaskType());
						t1.setTaskMold(list.get(i).getTaskMold());
						convert2(t1);
//						listnew.add(t1);
					}

					t.setAssignNum(teamTarget);
					t.setFinishNum(finishNum);
					t.setTaskType(list.get(i).getTaskType());
					t.setTaskMold(list.get(i).getTaskMold());
					convert(t);
					listnew.add(t);
					ts.clear();
				}else{
//					listnew.add(list.get(i));
					ts.add(list.get(i));
				}
			}else{
//				listnew.add(list.get(list.size()-1));
				ts.add(list.get(list.size()-1));
				TskTaskStatQuery t=new TskTaskStatQuery();
				t.setGroupName(list.get(list.size()-1).getGroupName());
				t.setTaskTitle(list.get(list.size()-1).getTaskTitle());
				t.setUserName("合计");
				if(list.get(i).getTskLevel()==2){
					teamTarget = list.get(i).getTaskTarget();
				}else{
					teamTarget=this.taskStatDao.queryGroupAssignTargetByIds(list.get(i).getTaskId(),list.get(i).getTeamGroupId());
				}
				int assignNum=0;
				int finishNum=0;
				for(int j=0;j<ts.size();j++){
					assignNum+=ts.get(j).getAssignNum();
					finishNum+=ts.get(j).getFinishNum();
				}
				if(teamTarget!=null&&teamTarget>assignNum){
					TskTaskStatQuery t1=new TskTaskStatQuery();
					t1.setGroupName(list.get(list.size()-1).getGroupName());
					t1.setTaskTitle(list.get(list.size()-1).getTaskTitle());
					t1.setUserName("未分配");
					t1.setAssignNum(teamTarget-assignNum);
					t1.setTaskPercent("");
					t1.setTaskType(list.get(list.size()-1).getTaskType());
					t1.setTaskMold(list.get(list.size()-1).getTaskMold());
					convert2(t1);
//					listnew.add(t1);
				}
				t.setAssignNum(teamTarget);
				t.setFinishNum(finishNum);
				t.setTaskType(list.get(list.size()-1).getTaskType());
				t.setTaskMold(list.get(list.size()-1).getTaskMold());
				convert(t);
				listnew.add(t);
			}
		}
		return listnew;
	}

	@Override
	public List<TskTaskStatQuery> queryGroupList() {
		
		return taskStatDao.queryGroupList();
	}

	@Override
	public List<TskTaskStatQuery> queryMemberByTeamGroupId(String teamGroupId) {
		return this.taskStatDao.queryMemberByTeamGroupId(teamGroupId);
	}

	@Override
	public List<TskTaskStatQuery> queryGroupListByGroupPermit(String groupPermit){
		return this.taskStatDao.queryGroupListByGroupPermit(groupPermit);
	}

	@Override
	public List<TskTaskStatQuery> queryCrossTeamList(String groupPermit, Date startDate,Date endDate){
		List<TskTaskStatQuery> listnew = new ArrayList<TskTaskStatQuery>();
		List<TskTaskStatQuery> ts= new ArrayList<TskTaskStatQuery>();
		List<TskTaskStatQuery> list=this.taskStatDao.queryCrossTeamList(groupPermit,startDate,endDate);
		for(TskTaskStatQuery tskTaskStatQuery:list){
			convert1(tskTaskStatQuery);
		}
		Integer teamTarget;
		for (int i = 0; i<list.size() ; i++) {
			if (i!=list.size()-1) {
				if(!list.get(i).getTaskTitle().equals(list.get(i+1).getTaskTitle())){
					TskTaskStatQuery t=new TskTaskStatQuery();
					t.setTaskTitle(list.get(i).getTaskTitle());
					t.setGroupName("合计");
					listnew.add(list.get(i));
					ts.add(list.get(i));
					teamTarget=this.taskStatDao.queryCrossTaskTargetById(list.get(i).getTaskId());
					int assignNum=0;
					int finishNum=0;
					for(int j=0;j<ts.size();j++){
						assignNum+=ts.get(j).getGroupAssignNum();
						finishNum+=ts.get(j).getGroupFinshNum();
					}
					if(teamTarget!=null&&teamTarget>assignNum){
						TskTaskStatQuery t1=new TskTaskStatQuery();
						t1.setGroupName("未分配");
						t1.setTaskTitle(list.get(i).getTaskTitle());
						t1.setAssignNum(teamTarget-assignNum);
						t1.setTaskPercent("");
						t1.setTaskType(list.get(i).getTaskType());
						t1.setTaskMold(list.get(i).getTaskMold());
						convert2(t1);
						listnew.add(t1);
					}
					t.setGroupAssignNum(teamTarget);
					t.setGroupFinshNum(finishNum);
					t.setTaskType(list.get(i).getTaskType());
					t.setTaskMold(list.get(i).getTaskMold());
					convert1(t);
					listnew.add(t);
					ts.clear();
				}else{
					listnew.add(list.get(i));
					ts.add(list.get(i));
				}
			}else{
				listnew.add(list.get(list.size()-1));
				ts.add(list.get(list.size()-1));
				TskTaskStatQuery t=new TskTaskStatQuery();
				t.setGroupName("合计");
				t.setTaskTitle(list.get(list.size()-1).getTaskTitle());
				teamTarget=this.taskStatDao.queryCrossTaskTargetById(list.get(i).getTaskId());
				int assignNum=0;
				int finishNum=0;
				for(int j=0;j<ts.size();j++){
					assignNum+=ts.get(j).getGroupAssignNum();
					finishNum+=ts.get(j).getGroupFinshNum();
				}
				if(teamTarget!=null&&teamTarget>assignNum){
					TskTaskStatQuery t1=new TskTaskStatQuery();
					t1.setGroupName("未分配");
					t1.setTaskTitle(list.get(list.size()-1).getTaskTitle());
					t1.setAssignNum(teamTarget-assignNum);
					t1.setTaskPercent("");
					t1.setTaskType(list.get(list.size()-1).getTaskType());
					t1.setTaskMold(list.get(i).getTaskMold());
					convert2(t1);
					listnew.add(t1);
				}
				t.setGroupAssignNum(teamTarget);
				t.setGroupFinshNum(finishNum);
				t.setTaskType(list.get(list.size()-1).getTaskType());
				t.setTaskMold(list.get(i).getTaskMold());

				convert1(t);
				listnew.add(t);
			}
		}
		return listnew;
	}

	private void convert(TskTaskStatQuery tskTaskInfoQuery){

		//完成进度
		if(tskTaskInfoQuery.getFinishNum()==null || tskTaskInfoQuery.getFinishNum()==0){
			tskTaskInfoQuery.setTaskPercent("0%");
		}else{
			double percent=(tskTaskInfoQuery.getFinishNum()/(double)tskTaskInfoQuery.getAssignNum())*100;
			//保留2位小数
			tskTaskInfoQuery.setTaskPercent(String.format("%.2f",percent)+"%");
		}
		if(tskTaskInfoQuery.getAssignNum()!=null){
			int assignNum = tskTaskInfoQuery.getAssignNum();
			int finishNum = tskTaskInfoQuery.getFinishNum();
			if (tskTaskInfoQuery.getTaskMold()!=null && tskTaskInfoQuery.getTaskMold() == 0) {
				//贷款任务
				DecimalFormat myformat = new DecimalFormat();
				myformat.applyPattern("##,###");
				String assignStr = myformat.format(assignNum);
				String finishStr = myformat.format(assignNum);
				String unit = tskTaskInfoQuery.getTaskType().intValue() == 1 ? "元" : "笔";
				int unFinish = assignNum > finishNum ? assignNum - finishNum : 0;
				tskTaskInfoQuery.setTarget(assignStr + unit);
				tskTaskInfoQuery.setFinished(finishStr + unit);
				tskTaskInfoQuery.setUnfinished(unFinish + unit);
			}else if(tskTaskInfoQuery.getTaskMold()!=null && tskTaskInfoQuery.getTaskMold() == 1){
				tskTaskInfoQuery.setTarget(assignNum + "个");
				tskTaskInfoQuery.setFinished(finishNum + "个");
			}
		}

	}
	private void convert1(TskTaskStatQuery tskTaskInfoQuery){

		//完成进度
		if(tskTaskInfoQuery.getGroupFinshNum()==null || tskTaskInfoQuery.getGroupFinshNum()==0){
			tskTaskInfoQuery.setTaskPercent("0%");
		}else{
			double percent=(tskTaskInfoQuery.getGroupFinshNum()/(double)tskTaskInfoQuery.getGroupAssignNum())*100;
			//保留2位小数
			tskTaskInfoQuery.setTaskPercent(String.format("%.2f",percent)+"%");
		}
		if(tskTaskInfoQuery.getGroupAssignNum()!=null){
			if (tskTaskInfoQuery.getTaskMold()!=null && tskTaskInfoQuery.getTaskMold() == 0) {
				//贷款任务
				DecimalFormat myformat = new DecimalFormat();
				myformat.applyPattern("##,###");
				if(tskTaskInfoQuery.getTaskType()==1){
					tskTaskInfoQuery.setTarget(myformat.format(tskTaskInfoQuery.getGroupAssignNum())+"元");
					tskTaskInfoQuery.setFinished(myformat.format(tskTaskInfoQuery.getGroupFinshNum())+"元");
				}else{
					tskTaskInfoQuery.setTarget(tskTaskInfoQuery.getGroupAssignNum()+"笔");
					tskTaskInfoQuery.setFinished(tskTaskInfoQuery.getGroupFinshNum()+"笔");
				}
			}else if(tskTaskInfoQuery.getTaskMold()!=null && tskTaskInfoQuery.getTaskMold() == 1){
				tskTaskInfoQuery.setTarget(tskTaskInfoQuery.getGroupAssignNum()+"个");
				tskTaskInfoQuery.setFinished(tskTaskInfoQuery.getGroupFinshNum()+"个");
			}

		}

	}
	private void convert2(TskTaskStatQuery tskTaskInfoQuery){
		if(tskTaskInfoQuery.getAssignNum()!=null){
			if (tskTaskInfoQuery.getTaskMold()!=null && tskTaskInfoQuery.getTaskMold() == 0) {
				DecimalFormat myformat = new DecimalFormat();
				myformat.applyPattern("##,###");
				if(tskTaskInfoQuery.getTaskType()==1){
					tskTaskInfoQuery.setTarget(myformat.format(tskTaskInfoQuery.getAssignNum())+"元");
				}else{
					tskTaskInfoQuery.setTarget(tskTaskInfoQuery.getAssignNum()+"笔");
				}
			} else if (tskTaskInfoQuery.getTaskMold() != null && tskTaskInfoQuery.getTaskMold() == 1) {
				tskTaskInfoQuery.setTarget(tskTaskInfoQuery.getAssignNum()+"个");
			}
		}
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
