package banger.framework.web.layout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import banger.framework.util.StringUtil;
import banger.framework.xml.XmlNode;

/**
 * 表单对像
 */
public class Forms implements Cloneable
{
	private String id;					//表单的Id
	private String name;				//对像名称
	private int columns;				//列数
	private List<Field> fields;			//字段集合
	private Map<String,Field> fieldMap;	//字段集合
	private List<FormsRow> rows;		//行对像
	private boolean readonly;			//是否只读
	private double ratio;				//描述和内容的比率  0.2 表示 1:4 如:一行4列的情况下,描述td宽度 为 5% 内容为20%
	private XmlNode node;				//节点,继承用
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public XmlNode getNode() {
		return node;
	}

	public void setNode(XmlNode node) {
		this.node = node;
	}

	public Forms(){
		this.columns = 4;			//默认占四列
		this.readonly = false;			//默认可编辑
		this.name = "";
		this.ratio = 0.2;
		this.rows = new ArrayList<FormsRow>();
		this.fields = new ArrayList<Field>();
		this.fieldMap = new HashMap<String,Field>();
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	/**
	 * 添加字段
	 * @param field
	 */
	public void addField(Field field){
		if(!StringUtil.isNullOrEmpty(field.getName())){
			this.fieldMap.put(field.getName(), field);
		}
		this.fields.add(field);
	}
	
	/**
	 * 返回字段
	 * @param name
	 * @return
	 */
	public Field getFieldByName(String name){
		if(this.fieldMap.containsKey(name)){
			return this.fieldMap.get(name);
		}
		return null;
	}
	
	/**
	 * 是否包含字段
	 * @param name
	 * @return
	 */
	public boolean containsKey(String name){
		return this.fieldMap.containsKey(name);
	}
	
	/**
	 * 填充行
	 */
	public void fillRows(){
		List<boolean[]> matrix = new ArrayList<boolean[]>();
		Pos pos = new Pos(0,0);
		FormsRow row = null;
		
		for(int i=0;i<this.fields.size();i++){
			Field field = this.fields.get(i);
			if("Hidden".equalsIgnoreCase(field.getControl())){
				continue;
			}
			if(!StringUtil.isNullOrEmpty(field.getMerge()) && this.fieldMap.containsKey(field.getMerge())){
				Field mergeField = this.fieldMap.get(field.getMerge());
				if(mergeField.getCell() instanceof FormsCell){
					FormsCell cell = (FormsCell)mergeField.getCell();
					cell.addMergeField(field);
				}
			}else{
				pos = this.findEmptyPostion(field, pos.row, matrix);
				this.fillMatrix(field, pos, matrix);
				while(this.rows.size()<pos.row+1){
					row = new FormsRow(this);
					this.rows.add(row);
				}
				
				if(!StringUtil.isNullOrEmpty(field.getDisplay()))row.addCell(new FormsCell("head",this,field));
				row.addCell(new FormsCell("content",this,field));
			}
		}
	}
	
	/**
	 * 填充空白行
	 */
	public void stuffAllRow(){
		for(FormsRow row : this.rows){
			row.stuffCell();
		}
	}
	
	private Pos findEmptyPostion(Field field,int row,List<boolean[]> matrix){
		int cols = field.getCols();
		if(matrix.size()>0){
			while(row<matrix.size()){
				int emptyCount = 0;			//连续空格数量
				for(int i=0;i<this.columns;i++){
					if(!matrix.get(row)[i])emptyCount++;
					else emptyCount = 0;
					if(emptyCount==cols)return new Pos(row,i+1-cols);
				}
				row++;
			}
			return new Pos(row,0);
		}
		
		return new Pos(0,0);
	}
	
	private void fillMatrix(Field field,Pos pos,List<boolean[]> matrix){
		int rows = field.getRows();
		int cols = field.getCols();
		for(int i=0;i<rows;i++){
			while(matrix.size()<pos.row+i+1){
				boolean[] row = new boolean[this.columns];
				for(int j=0;j<this.columns;j++)row[j]=false;
				matrix.add(row);
			}
			for(int j=0;j<cols;j++){
				matrix.get(pos.row+i)[pos.col+j]=true;
			}
		}
	}
	
	/**
	 * 得到行集合
	 * @return
	 */
	public List<FormsRow> getRows(){
		return this.rows;
	}
	
	static class Pos{
		int row;
		int col;
		
		public Pos(int row,int col){
			this.row = row;
			this.col = col;
		}
	}
}
