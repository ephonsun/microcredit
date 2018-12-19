package banger.framework.component.progress;

/**
 * 进度条对像
 */
public class ProgressBar {
	private String id;		//唯一标识
	private String group;		//分组
	private int total;		//总数
	private int current;		//当前到第几行
	private boolean closed;			//是否停止
	private boolean completed;			//是否完成
	private String message;				//信息
	
	public ProgressBar(){
		this.id = "";
		this.group = "";
		this.total = 0;
		this.current = 0;
		this.closed = false;
		this.completed = false;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public int getTotal() {
		return total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	public int getCurrent() {
		return current;
	}
	
	public void setCurrent(int current) {
		this.current = current;
	}
	
	public boolean isClosed() {
		return closed;
	}
	
	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	
	public boolean isCompleted() {
		return completed;
	}
	
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	public float getRate(){
		if(this.total>0){
			return (float)this.current/(float)this.total;
		}
		return 0;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String toJsonString(){
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"progressId\":\""+this.id+"\"");
		sb.append(",\"groupId\":\""+this.group+"\"");
		sb.append(",\"total\":"+this.total);
		sb.append(",\"current\":"+this.current);
		sb.append(",\"closed\":"+this.closed);
		sb.append(",\"completed\":"+this.completed);
		sb.append(",\"rate\":"+this.getRate());
		sb.append(",\"message\":\""+this.message+"\"");
		sb.append("}");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (closed ? 1231 : 1237);
		result = prime * result + (completed ? 1231 : 1237);
		result = prime * result + current;
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + total;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProgressBar other = (ProgressBar) obj;
		if (closed != other.closed)
			return false;
		if (completed != other.completed)
			return false;
		if (current != other.current)
			return false;
		if (group == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (total != other.total)
			return false;
		return true;
	}
	
}
