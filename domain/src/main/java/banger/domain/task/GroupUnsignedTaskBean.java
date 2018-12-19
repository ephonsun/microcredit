package banger.domain.task;

public class GroupUnsignedTaskBean {

	private Integer teamGroupId;
	private String teamGroupName;
	private Integer assignTarget;
	private Integer groupTaskAssignId;
	private Integer teamAssign;
	
	
	public Integer getTeamGroupId() {
		return teamGroupId;
	}
	public void setTeamGroupId(Integer teamGroupId) {
		this.teamGroupId = teamGroupId;
	}
	public String getTeamGroupName() {
		return teamGroupName;
	}
	public void setTeamGroupName(String teamGroupName) {
		this.teamGroupName = teamGroupName;
	}
	public Integer getAssignTarget() {
		return assignTarget;
	}
	public void setAssignTarget(Integer assignTarget) {
		this.assignTarget = assignTarget;
	}
	public Integer getGroupTaskAssignId() {
		return groupTaskAssignId;
	}
	public void setGroupTaskAssignId(Integer groupTaskAssignId) {
		this.groupTaskAssignId = groupTaskAssignId;
	}

	public Integer getTeamAssign() {
		return teamAssign;
	}

	public void setTeamAssign(Integer teamAssign) {
		this.teamAssign = teamAssign;
	}
}
