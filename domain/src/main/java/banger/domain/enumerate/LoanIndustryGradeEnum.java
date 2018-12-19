package banger.domain.enumerate;

/**
 * @Author: yangdw
 * @Description:贷款等级分类说明枚举类
 * @Date: Created in 18:34 2017/9/20.
 */
public enum LoanIndustryGradeEnum {

	高于平均值_一般("一般", "", "well", "一般", "高于平均值"),
	低于平均值_一般("一般", "", "well", "一般", "低于平均值"),
	等于平均值_一般("一般", "", "well", "一般", "与平均值持平"),

	//优秀值到较差值是desc(降序)
	高于平均值_优秀("优秀", "", "great",   "优秀", "高于平均值"),
	高于平均值_良好("良好", "", "good",    "良好", "高于平均值"),
	低于平均值_较低("较低", "", "bad",     "较低", "低于平均值"),
	低于平均值_较差("较差", "", "trouble", "较差", "低于平均值"),
	//优秀值到较差值是asc(升序)
	低于平均值_优秀("优秀", "", "great",  	"优秀", "低于平均值"),
	低于平均值_良好("良好", "", "good",   	"良好", "低于平均值"),
	高于平均值_较低("较低", "", "bad",		"较低", "高于平均值"),
	高于平均值_较差("较差", "", "trouble",	"较差", "高于平均值"),

	资产_负债率("资产负债率（％）","assetLiabilityRatio","gradeCn","gradeEn","gradeExp"),
	净利率("销售（营业）利润率（％）","netInterestRate","gradeCn","gradeEn","gradeExp"),
	净资产收益率("净资产收益率（％）","returnOnEquity","gradeCn","gradeEn","gradeExp"),
	速动比("速动比率（％）","quickRatio","gradeCn","gradeEn","gradeExp"),
	总资产周转率("总资产周转率（次）","assetTurnover","gradeCn","gradeEn","gradeExp"),
	应收账款周转率("应收账款周转率（次）","accountsTeceivableTurnover","gradeCn","gradeEn","gradeExp"),
	存货周转率("存货周转率（次）","inventoryTurnover","gradeCn","gradeEn","gradeExp"),
	;
	public final String name;		//项目名称
	public final String item;		//项目字段
	public final String gradeCn;         //等级英文名称
	public final String gradeEn;      //等级中文名称
	public final String gradeExp;        //等级与平局值比较说明

	LoanIndustryGradeEnum(String name,String item,String gradeCn,String gradeEn,String gradeExp) {
		this.name = name;
		this.item = item;
		this.gradeCn = gradeCn;
		this.gradeEn = gradeEn;
		this.gradeExp = gradeExp;
	}

}
