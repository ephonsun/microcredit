package banger.response;

public enum CodeEnum {

	CODE_ERROR(999, "自定义错误提示"),
	CODE_97(97,"登入用户为空"),
	CODE_99(99,"没有权限"),
	CODE_100(100,"成功"),
	CODE_101(101,"系统异常"),
	CODE_102(102,"参数为空"),
	CODE_103(103,"账户不存在"),
	CODE_104(104,"密码不正确"),
	CODE_105(105,"json解析出错"),
	CODE_106(106,"您的帐号已被禁用，请联系您的上级主管！"),
	CODE_107(107,"用户名或密码不正确，请重新输入！"),
	CODE_108(108,"该客户已添加过对当前产品的意向！"),
	CODE_109(109,"证件号码已存在！"),
	CODE_110(110,"json数据格式不正确"),
	CODE_111(111,"您不是客户经理，不允许登录客户端！"),
	CODE_112(112,"上传图片失败"),
	CODE_113(113,"上传图片为空"),
	CODE_117(117,"客户贷款受限"),
	CODE_118(118,"不能提交审批"),
	CODE_119(119,"记录不存在"),
	CODE_120(120,"缺少参数"),
	CODE_121(121,"信息不完善"),
	CODE_122(122,"审批流程配置信息或贷款信息不完善"),
	CODE_123(123,"不能清空有内容的必填项"),
	CODE_124(124,"贷款监控已完成"),
	CODE_125(125,"还款提醒已完成"),
	CODE_126(126,"当前贷款信息状态不正确"),
	CODE_127(127,"提交参数有误"),
	CODE_128(128,"身份证格式不正确"),
	CODE_129(129,"该类型贷款暂无调查流程"),
	CODE_130(130,"该客户不存在"),
	CODE_131(131,"手机号码不能为空"),
	CODE_132(132,"建议金额不能大于申请金额"),
	CODE_133(133,"该用户已存在，请重新输入！"),
	CODE_134(134,"还款日不正确"),
	CODE_135(135,"建议期限不能小于0"),
	CODE_136(136,"暂无版本信息"),
	CODE_137(137,"客户不归属于当前客户经理，信息保存失败"),
	CODE_138(138,"客户编码重复"),
	CODE_139(139,"客户内码只能由数字和英文组成"),
	CODE_140(140,"该设备已经被禁用"),
	CODE_141(141,"该设备已经被绑定"),
	CODE_142(142,"签订人不能是本人"),
	CODE_143(143,"签订人处于禁用状态或者不存在"),
	CODE_144(144,"授权确认失败"),
	CODE_145(145,"贷款已到账"),
	CODE_146(146,"授权取消失败"),
	CODE_147(147,"台账同步失败"),
	CODE_152(152,"请输入验证码！"),
	CODE_153(153,"验证码不正确！"),
	CODE_154(154,"用户名或密码不正确，请重新输入！"),
	CODE_155(155,"操作频繁，请稍后再试！"),
	;


	//响应码
	private final Integer  code;
     
	//响应消息
    private final String msg;
    
    
	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	private CodeEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static CodeEnum valueOf(Integer code){
		for(CodeEnum codeEnum : CodeEnum.values()){
			if(codeEnum.code.equals(code)){
				return codeEnum;
			}
		}
		return null;
	}
	
}
