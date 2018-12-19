
$(function(){

	// 加载列表
	$('#grid').flexigrid({
        height: 500,
        url: '../sysAppVersion/queryAppVersionListData.html',
        usePage: true,
		multiSelect:false,
		rowIdProperty: 'id',
		fields: [
        	{ display: '升级包', field: 'apkName', width: 150 ,align : 'center' },
            { display: '显示版本号', field: 'apkVersionShow', width: 150 ,align : 'center' },
            { display: '升级版本号', field: 'apkVersionUpd', width: 150 ,align : 'center' },
        	{ display: '是否强制升级', field: 'isUpdate', width: 150 ,align : 'center' },
            { display: '操作者', field: 'userName', width: 150 ,align : 'center' },
        	{ display: '升级时间', field: 'createDate', width: 150 ,align : 'center' },
            { display: '状态', field: 'isDel', width: 150 ,align : 'center' ,data:{0:'',1:'已禁用',2:''}},
        ],
        action: {
            display: '操作',
            width: 150,
            align: 'center',
            buttons: [
                {
                    display: '禁用',
                    onClick: function(tr, data){
                        deleteAppVersion(data);
                    },
                    isDisabled: function(data){
                        if(data.cols.isDel == 1 || data.cols.isDel == 2){
                            return true;
                        }
                        return false;
                    }
                }
            ]
        },
        onComplete : function(data) {

			$('#lblStatistics').text(data.total);
		}
    });
});

function deleteAppVersion(data) {
    showConfirm({
        icon:'confirm',
        content:'您确定要彻底删除APP“'+data.cols.apkName+'”吗？删除后需要重新上传。',
        ok:function(){
            jQuery.ajax({
                url: '../sysAppVersion/updateAppVersion.html',
                type:'POST',
                dataType:'json',
                data: {"id": data.id},
                sync: false,
                success: function(result){
                    if(result.success){
                        showConfirm({
                            icon: 'succeed',
                            content: result.cause
                        });
                        $('#grid').flexReload();
                    }else {
                        showConfirm({
                            icon: 'warning',
                            content: result.cause
                        });
                    }
                }
            });
        },
        cancel: function() {}
    });
}

//升级
$("#btnUpgrade").click(function() {
    var url='../sysAppVersion/getUpgradeMobilePage.html';
    /*showDialog({
        id: 'appUpgrade',
        title: "APP升级包上传",
        url: url,
        width: 760,
        height: 370,
        tabId: tabs.getSelfId(window)
    });*/
    tabs.add({
        id : 'appUpgrade',
        name : 'appUpgrade',
        pid: tabs.getSelfId(window),
        title : 'APP升级包上传',
        url : url,
        lock : false
    });
});
//刷新
$('#btnRefresh').click(function(){
    refreshList();
});

// 刷新
function refreshList() {
	$('#grid').flexReload();
}