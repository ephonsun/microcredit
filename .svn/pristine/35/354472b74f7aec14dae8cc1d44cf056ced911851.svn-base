
$(function(){
	var roleIdsArray = $('#roleIds').val().split(',');

	/*
	if($.inArray('6', roleIdsArray) >= 0){
		$('#userCertificateInfo').show();
	    // 加载系统日志数据网格
	    $('#userCertificateInfoGrid').flexigrid({
	        height: 300,
	        url: '../certificate/getMyCertificateInfoListPage.html',
	        dataType: 'json',
	        fields: [
	        	{ display: '销售资格', field: 'salesCertificateName', width: 140, align: 'center' },
	        	{ display: '证书编号', field: 'certificateNo', width: 160, align: 'center' },
	        	{ display: '有效期至', field: 'effectiveDate', width: 120, align: 'center' },
	        	{ display: '申请时间', field: 'puciCreateDate', width: 120, align: 'center' },
	        	{ display: '审核人', field: 'auditUserName', width: 120, align: 'center' },
	        	{ display: '审核时间', field: 'auditDate', width: 120, align: 'center' },
	        	{ display: '备注', field: 'puciExportName', width: 160, align: 'center' }
	        ],
	        rowIdProperty: 'id',
	        usePage: false, // 使用分页
	        onComplete : function(data) {
	            $('#lblStatistics').text(data.total);
	        }
	    });
	}
	*/

	// 修改密码
	$('#btnUpdate').click(function(){
        tabs.setTitle(tabs.getSelfId(window),'个人配置-修改密码');
        window.location.href = '../user/toChangePasswordPage.html';
    });
    var pwdLevel = $('input[name="passwordStr"]').val();
    checklevel(pwdLevel);
});

//密码强度
function checklevel(pwdLevel){
    var color_l = "#ff0000", color_m = "#ff9900", color_h = "#33cc00";
    if(pwdLevel != null){
    	switch (pwdLevel){
	        case '1':				
	            $('#strength').text('弱');
	            $('#strength').css("color",color_l);
	            break;
	        case '2':
	            $('#strength').text('中');
	            $('#strength').css("color",color_m);
	            break;
	        case '3':               
	            $('#strength').text('强');
	            $('#strength').css("color",color_h);
	            break;
			default:          
	    }
    }
    
};

dateFormat = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

