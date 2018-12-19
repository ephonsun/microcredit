package banger.domain.task;

public class TskTaskStatQuery extends TskTaskInfo {


	private static final long serialVersionUID = 1260420537677987822L;
	
	private Integer userId;
	private Integer assignNum;//个人分配数量
	private Integer finishNum;//个人完成数量
	private Integer groupAssignNum;//团队分配数量
	private Integer groupFinshNum;//团队完成数
	//查询列表显示
	private String groupName;//团队名
	private String userName;//经理名
	private String taskPercent;//完成百分比
	private String target;//目标
	private String finished;//完成数
	private String unfinished;//完成数
	//统计
	private Integer taskSum;//任务总数
	private Integer taskFinished;//完成

	public String getUnfinished() {
		return unfinished;
	}

	public void setUnfinished(String unfinished) {
		this.unfinished = unfinished;
	}

	private Integer taskNoFinished;//未完成
		private Integer taskIng;//进行中的任务

	public Integer getTaskSum() {
		return taskSum;
	}

	public void setTaskSum(Integer taskSum) {
		this.taskSum = taskSum;
	}

	public Integer getTaskFinished() {
		return taskFinished;
	}

	public void setTaskFinished(Integer taskFinished) {
		this.taskFinished = taskFinished;
	}

	public Integer getTaskNoFinished() {
		return taskNoFinished;
	}

	public void setTaskNoFinished(Integer taskNoFinished) {
		this.taskNoFinished = taskNoFinished;
	}

	public Integer getTaskIng() {
		return taskIng;
	}

	public void setTaskIng(Integer taskIng) {
		this.taskIng = taskIng;
	}

	public Integer getUserId() {
				return userId;
			}
	public void setUserId(Integer userId) {
				this.userId = userId;
			}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTaskPercent() {
		return taskPercent;
	}
	public void setTaskPercent(String taskPercent) {
		this.taskPercent = taskPercent;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}

	public Integer getAssignNum() {
		return assignNum;
	}
	public void setAssignNum(Integer assignNum) {
		this.assignNum = assignNum;
	}
	public Integer getFinishNum() {
		return finishNum;
	}
	public void setFinishNum(Integer finishNum) {
		this.finishNum = finishNum;
	}
	public String getFinished() {
		return finished;
	}
	public void setFinished(String finished) {
		this.finished = finished;
	}

	public Integer getGroupAssignNum() {
		return groupAssignNum;
	}

	public void setGroupAssignNum(Integer groupAssignNum) {
		this.groupAssignNum = groupAssignNum;
	}

	public Integer getGroupFinshNum() {
		return groupFinshNum;
	}

	public void setGroupFinshNum(Integer groupFinshNum) {
		this.groupFinshNum = groupFinshNum;
	}
}
