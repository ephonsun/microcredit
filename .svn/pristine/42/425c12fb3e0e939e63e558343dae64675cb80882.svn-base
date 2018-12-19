package banger.domain.task;


/**
 * 任务表查询对象
 */
public class TskTaskInfoQuery extends TskTaskInfo{
	

	//是否过期
	private Integer isOverDate;

	//我的  下属的   所有
	private Integer myTask;
	
	//查询列表显示
	private String taskDateLimit;
	private String createUserName;
	private String taskPercent;
	private String target;


	public String getTaskMoldStr() {
		if (this.getTaskMold() != null) {
			return this.getTaskMold().intValue() == 0 ? "贷款任务" : "营销任务";
		}
		return "";
	}
	public Integer getIsOverDate() {
		return isOverDate;
	}

	public void setIsOverDate(Integer isOverDate) {
		this.isOverDate = isOverDate;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	public String getTaskPercent() {
		return taskPercent;
	}

	public void setTaskPercent(String taskPercent) {
		this.taskPercent = taskPercent;
	}

	public String getTaskDateLimit() {
		return taskDateLimit;
	}

	public void setTaskDateLimit(String taskDateLimit) {
		this.taskDateLimit = taskDateLimit;
	}

	public Integer getMyTask() {
		return myTask;
	}

	public void setMyTask(Integer myTask) {
		this.myTask = myTask;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}
