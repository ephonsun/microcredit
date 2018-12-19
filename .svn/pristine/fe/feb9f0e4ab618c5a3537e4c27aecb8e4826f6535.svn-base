$(function(){
    initDeptListTreeGrid();
});
function  initDeptListTreeGrid(){
    $('#deptListTreeGrid').html('<div class=\'ui-loadbar\'><div class=\'icon\'>数据加载中，请耐心等待...</div></div>');
    $('#deptListTreeGrid').treegrid({
        fields: [
            { display: '机构名称', field: 'no', width: '20%', style: '' },
            { display: '机构编号', field: 'no', width: '20%', style: '' },
            { display: '操作', field: 'no', width: '20%', style: '' }
        ],
        rows: deptJson
    });
}

function editDept(deptId){
    tabs.add({
        id :'addOrUpdateDept'+deptId,
        pid:tabs.getSelfId(window),
        name:'addOrUpdateDept',
        title:'机构-详情',
        lock:false,
        url:'../dept/initPmsDeptPage.html?deptId=' + deptId
    });
}

function delDept(deptId,deptName){
    var url="";
    var postJson={};
    postJson['deptId'] = deptId;
    jQuery.ajax({
        type : 'POST',
        url : '../dept/checkDelete.html',
        async : false,
        data : postJson,
        success : function(result) {
            if(result){
                if('hasUser' == result){
                    showConfirm('机构“'+deptName+'”或其子机构下还有人员，无法删除！请将人员转移至其他机构，再进行删除');
                }
            }else{
                showConfirm({
                    icon: 'confirm',
                    content: '您确认要删除机构“'+deptName+'（包含下属机构）”吗',
                    ok: function() {
                        jQuery.ajax({
                            type : 'POST',
                            url : '../dept/deleteDept.html',
                            data : postJson,
                            success : function(result) {
                                showConfirm({
                                    icon: 'succeed',
                                    content: '删除成功'
                                });
                                refresh();
                            }
                        });
                    },
                    cancel: function() {}
                });
            }
        }
    });
}
function upDept(deptId,deptName){
    var url="";
    var postJson={};
    postJson['deptId'] = deptId;
    postJson['isUp']= "1";
    jQuery.ajax({
        type : 'POST',
        url : '../dept/upDept.html',
        async : false,
        data : postJson,
        success : function(result) {
        	refresh();

            }
        
    });
}

function downDept(deptId,deptName){
    var url="";
    var postJson={};
    postJson['deptId'] = deptId;
    postJson['isUp']= "-1";
    jQuery.ajax({
        type : 'POST',
        url : '../dept/downDept.html',
        async : false,
        data : postJson,
        success : function(result) {
           
        	refresh();
            }
        
    });
}
function refresh() {
    location.href="getDeptListPage.html?random="+Math.random();
}
