$(function(){
    var potentialId = $('#potentialId').val();
	// 加载列表
	$('#files-grid').flexigrid({
        height: 280,
        url: '../potentialCustomersFiles/queryPotentialCustomersFilesListData.html?potentialId=' + potentialId,
        usePage: true,
		rowIdProperty: 'id',
		fields: [
        	{ display: '名称', field: 'fileName', width: 280 ,align : 'center' },
            { display: '上传时间', field: 'createDate', width: 180 ,align : 'center' },
            { display: '操作者', field: 'createUserName', width: 100 ,align : 'center' },
            { display: '操作', field: 'operate', width: 100 ,align : 'center' }
		],
        extendCell: {
            'fileName': function(value,row){
                var _required = '<img style="height: 30px; margin-right: 10px;" src="../potentialCustomersFiles/potentialImage.html?id=' + row.id + '" />';
                _required += row.cols.fileName;
                return _required;
            },
            'operate': function (value,row) {
                return '<a data-magnify="gallery-list" href="../potentialCustomersFiles/potentialImage.html?id='+ row.id+'&isOriginal=1"><lable class="ui-link mr5">查看</lable></a>' +
                    '<a href="###" onclick="deleteFiles(\''+ row.id +'\')"><lable class="ui-link mr5">删除</lable></a>';
            }
        },
        // action: {
        // 	display: '操作',
        // 	width: 150,
        //     align : 'center',
        // 	buttons: [{display: '<a data-magnify="gallery-list" href="../potentialCustomersFiles/potentialImage.html?id='+ data.id+'&isOriginal=1">查看</a>',onClick: function(tr, data){viewCustomer(data);}}]
        // },
        onComplete : function(data) {
            var options = {
                'title': false,
                'headToolbar':['maximize','close'],
                'footToolbar':[
                    'zoomIn',
                    'zoomOut',
                    'prev',
                    'next',
                    'rotateLeft',
                    'rotateRight',
                    // 'mydelete'
                ],
                'fixedContent': false
            };
            $('[data-magnify=gallery-list]').magnify(options);
			// $('#lblStatistics').text(data.total);
		}
    });
});

function deleteFiles(id) {
    showConfirm({
        icon:'confirm',
        content:'您确定要删除吗？删除后将无法恢复。',
        ok:function(){
            var postJson = {};
            postJson['id'] = id;
            jQuery.ajax({
                type:'POST',
                url:'../potentialCustomersFiles/deletePotentialCustomersFiles.html',
                data:postJson,
                success:function(text){
                    if ("SUCCESS" == text) {
                        showConfirm({
                            icon: 'succeed',
                            content: '删除成功'
                        });
                        refreshList();
                    } else {
                        showConfirm({
                            icon: 'warning',
                            content: '删除失败'
                        });
                        refreshList();
                    }
                }
            });
        },
        cancel: function() {}
    });
}
// 刷新列表
function refreshList() {
    $('#files-grid').flexReload();
}
function viewCustomer(data){
	// tabs.add({
	// 	id :'viewCustomer'+data.id,
	// 	pid:tabs.getSelfId(window),
	// 	name:'viewCustomer'+data.id,
	// 	title:'客户-查看',
	// 	url : '../custPotentialCustomers/getPotentialCustomersTabs.html?id='+data.id+"&isShow=1",
	// 	lock:false
	// });
}