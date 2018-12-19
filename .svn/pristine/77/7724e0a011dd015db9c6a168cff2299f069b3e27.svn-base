
var itemId = "0";
var loanId = "0";
var precType ="";
var userId ="0";
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
	//$('select').selectbox();

	itemId = $("#itemId").val();
    loanId = $("#loanId").val();
    precType = $("#precType").val();
    userId = $('#userId').val();
	// 加载列表
	$('#grid').flexigrid({
        height: 200,
        url: '../loan/queryCommPeoInfoListData.html?itemId=' + itemId + '&loanId=' + loanId +'&precType='+precType,
        usePage: true,
		//multiSelect:true,
		rowIdProperty: 'id',
		fields: [
        	{ display: '共有人名称', field: 'commPeoName', width: 100 ,align : 'center' },
            { display: '证件类型', field: 'commPeoCerTypeName', width: 100 ,align : 'center' },
        	{ display: '证件号码', field: 'commLicenseNo', width: 150 ,align : 'center' },
        	{ display: '不动产权证/共有证件号', field: 'commNo', width: 150 ,align : 'center' },
            { display: '保管人', field: 'commKeeperName', width: 100 ,align : 'center' },
            { display: '法人代表', field: 'corporation', width: 100 ,align : 'center' },
            { display: '联系电话', field: 'telephone', width: 100 ,align : 'center' },
            { display: '传真', field: 'facsimile', width: 100 ,align : 'center' },
            { display: '联系地址', field: 'address', width: 150 ,align : 'center' },
            { display: '配偶姓名', field: 'indivSpsName', width: 100 ,align : 'center' }
		],
        action: {
        	display: '操作',
        	width: 150,
            align : 'center',
        	buttons: [
                {
                    display: '查看',
                    onClick: function(tr, data){
                        viewCommPeo(data);
                    }
                },
            	{
            		display: '编辑',
            		onClick: function(tr, data){
            			updateCommPeo(data);
            		},
					isDisabled: function(tr, data){
                        if (tr.cols.userId==userId&&precType=='Investigate') {
                            return false;
                        }
						return true;
					}
        		},
            	{
            		display: '删除',
            		onClick: function(tr, data){
            			deleteCommPeo(data);
            		},
					isDisabled: function(tr, data){
                        if (tr.cols.userId == userId&&precType=='Investigate') {
                            return false;
                        }
						return true;
					}
        		}
            ]
        },
        onComplete : function(data) {
			$('#lblStatistics').text(data.total);
		}
    });

    $('#btnAddCommPeo').click(function(){
        addCommPeoInfo(itemId,loanId,precType);
    });

    $('#btnRefresh').click(function(){
        refreshList();
    });
});

function viewCommPeo(data){
    //tabs.add({
    //    id :'viewCustomer'+data.id,
    //    pid:tabs.getSelfId(window),
    //    name:'viewCustomer'+data.id,
    //    title:'客户-查看',
    //    url : '../customer/getCustomerTabs.html?id='+data.id+"&flag="+1,
    //    lock:false
    //});
    var url = '../loan/queryCommPeoInfoById.html?id='+data.id+'&editFlag='+1+'&random='+Math.random()*10000;
    showDialog({
        id: 'commPeoInfoPage',
        title: '添加共有人信息页面',
        url: url,
        width: 800,
        height: 400,
        tabId: tabs.getSelfId(window),
        pid:tabs.getSelfPid(window),
        close:function(){
            refreshList();
        }
    });

}

function updateCommPeo(data){
    var url = '../loan/updateCommPeoInfoById.html?id='+data.id+'&editFlag='+0+'&random='+Math.random()*10000;
    showDialog({
        id: 'commPeoInfoPage',
        title: '添加共有人信息页面',
        url: url,
        width: 800,
        height: 400,
        tabId: tabs.getSelfId(window),
        pid:tabs.getSelfPid(window),
        close:function(){
            refreshList();
        }
    });
}


function deleteCommPeo(data){
	showConfirm({
        icon:'confirm',
        content:'您确定要删除共有人“'+ data.cols.commPeoName+ '”吗？删除后将无法恢复。',
        ok:function(){
            var postJson = {};
            postJson['id'] = data.id;
            jQuery.ajax({
                type:'POST',
                dataType: 'json',
                url:'../loan/deleteCommPeoInfo.html',
                data:postJson,
                success:function(result){
                	if (result.success) {
                        showConfirm({
                            icon: 'succeed',
                            content: result.cause
                        });
                        refreshList();
					} else {
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

// 刷新人员表
function refreshList() {
	$('#grid').flexReload();
}

function addCommPeoInfo(itemId,loanId,precType){
    var url = '../loan/addCommPeoInfoPage.html?id='+itemId+'&loanId='+loanId+'&precType='+precType+'&editFlag=0'+'&random='+Math.random()*10000;
    showDialog({
        id: 'commPeoInfoPage',
        title: '添加共有人信息页面',
        url: url,
        width: 800,
        height: 400,
        tabId: tabs.getSelfId(window),
        pid:tabs.getSelfPid(window),
        close:function(){
            refreshList();
        }
    });
}