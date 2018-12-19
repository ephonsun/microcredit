

// 页面加载完成时...
$(function(){
	//浏览器不支持 placeholder 时才执行
    if (!('placeholder' in document.createElement('input'))) {
        $('[placeholder]').each(function () {
            var $tag = $(this); //当前 input
            var $copy = $tag.clone();   //当前 input 的复制
            if ($copy.val() == "") {
                $copy.css("color", "#999");
                $copy.val($copy.attr('placeholder'));
            }
            $copy.focus(function () {
                if (this.value == $copy.attr('placeholder')) {
                    this.value = '';
                    this.style.color = '#000';
                }
            });
            $copy.blur(function () {
                if (this.value=="") {
                    this.value = $copy.attr('placeholder');
                    $tag.val("");
                    this.style.color = '#a9a9a9';
                } else {
                    $tag.val(this.value);
                }
            });
            $tag.hide().after($copy.show());    //当前 input 隐藏 ，具有 placeholder 功能js的input显示
        });
    }
	$('select').selectbox();


	// 加载列表
	$('#grid').flexigrid({
        height: 500,
        url: '../faceContrast/queryFaceContrastInfoList.html',
        usePage: true,
		multiSelect:true,
		rowIdProperty: 'id',
       fields: [

            { display: '接口类别', field: 'faceIntoType', width: 200 ,align : 'center' },
        	{ display: '调用结果', field: 'isSuccessed', width: 200 ,align : 'center' },
           { display: '错误码', field: 'errorMessage', width: 200 ,align : 'center' },
           { display: '调用时间', field: 'time', width: 200 ,align : 'center' }

		],
        action: {
        	display: '操作',
        	width: 150,
            align : 'center',
        	buttons: [
                {
                    display: '查看',
                    onClick: function(tr, data){
                        viewCustomer(data);
                    }
                }
            ]
        },
        onComplete : function(data) {

			$('#lblStatistics').text(data.total);//总条数
		}
    });
	// 清空搜索条件
	$('#btnClean').click(function() {
		toCleanForm('#form');
        setTimeout(function(){$("input[name='userName']").focus();},100);
        setTimeout(function(){$("input[name='userName']").blur();},100);
	});
	// 搜索按钮
	$('#btnSearch').click(function() {
        searchList();
	});
	//刷新
	$('#btnRefresh').click(function(){
		refreshList();
	});


});



function viewCustomer(data){
	tabs.add({
		id :'viewCustomer'+data.id,
		pid:tabs.getSelfId(window),
		name:'viewCustomer'+data.id,
		title:'记录-查看',
		url : '../faceContrast/IntoIntefaceRecordQuery.html?id='+data.id,
		lock:false
	});
}


function searchList() {

	var postJson = {};


    postJson['txtStartDate'] = $('#txtStartDate').val();
    postJson['txtEndDate'] = $('#txtEndDate').val();
    postJson['interfaceType'] = $('#interfaceType').val();
    postJson['isSuccess'] = $('#isSuccess').val();
  //  alert(postJson.applyName+":"+postJson.applyCardNumber+":"+postJson.applyTel+":"+postJson.belongName+":" +postJson.txtStartDate+":"+postJson.txtEndDate+":"+postJson.faceType+":"+postJson.isSuccessed+":");

	$('#grid').flexSearch(postJson);

}
// 刷新
function refreshList() {
	$('#grid').flexReload();
}

// 加载操作时间
$('#txtStartDate').datepicker({
    maxDate: '#F{$dp.$D(\'txtEndDate\')}'
});
$('#txtEndDate').datepicker({
    minDate: '#F{$dp.$D(\'txtStartDate\')}'
});
// 加载操作时间
$('#auditStartDate').datepicker({
    maxDate: '#F{$dp.$D(\'auditEndDate\')}'
});
$('#auditEndDate').datepicker({
    minDate: '#F{$dp.$D(\'auditStartDate\')}'
});

