$(function(){
	
    //初始化下拉单选框
    $('select').selectbox();
    var loginUserName = $("#loginUserName").val();
    var fields = [];
    if(loginUserName == "超级管理员"){
        fields.push(
            { display: '显示名称', field: 'fieldColumnDisplay', width: 200 ,align: 'center'},
            { display: '字段类型', field: 'fieldType', width: 150 ,align: 'center',data:cdFiledType},
            { display: '默认值', field: 'defaultValue', width: 150 ,align: 'center',data:{0:"昨天",1:"今天",2:"明天",3:""}},
            { display: '是否必填', field: 'fieldIsRequired', width: 100 ,align: 'center'},
            { display: 'web端显示', field: 'fieldWebIsShow', width: 100 ,align: 'center'},
            { display: 'APP显示', field: 'fieldAppIsShow', width: 100 ,align: 'center'},
            { display: '状态', field: 'isActived', width: 100 ,align: 'center',data:{0:"禁用",1:"启用"}})
    }else{
        fields.push(
            { display: '显示名称', field: 'fieldColumnDisplay', width: 200 ,align: 'center'},
            { display: '字段类型', field: 'fieldType', width: 150 ,align: 'center',data:cdFiledType},
            { display: '默认值', field: 'defaultValue', width: 150 ,align: 'center',data:{0:"昨天",1:"今天",2:"明天",3:""}},
            { display: '状态', field: 'isActived', width: 100 ,align: 'center',data:{0:"禁用",1:"启用"}})
    }
    if(loginUserName == "超级管理员"){
        $('#gridServiceRecord').flexigrid({
            height: 300,
            url: '../tableColumn/queryTableColumnListData.html?tableId=' + tableId,
            //multiSelect: true,
            fields: fields,
            extendCell: {
                'fieldIsRequired': function(value,row){
                    var _field='';
                    if(row.cols.fieldIsRequired){
                        _field = '<input type = \'checkbox\' checked=\'checked\' onclick=\"changeRequired('+row.id+','+!row.cols.fieldIsRequired+')\"></input>';
                    }else{
                        _field = '<input type = \'checkbox\' onclick=\"changeRequired('+row.id+','+!row.cols.fieldIsRequired+')\"></input>';
                    }
                    return _field;
                },
                'fieldWebIsShow': function(value,row){
                    var _web='';
                    if(row.cols.fieldWebIsShow){
                        _web = '<input type = \'checkbox\' checked=\'checked\' onclick=\"changeWeb('+row.id+','+!row.cols.fieldWebIsShow+')\"></input>';
                    }else{
                        _web = '<input type = \'checkbox\' onclick=\"changeWeb('+row.id+','+!row.cols.fieldWebIsShow+')\"></input>';
                    }
                    return _web;
                },
                'fieldAppIsShow': function(value,row){
                    var _app='';
                    if(row.cols.fieldAppIsShow){
                        _app = '<input type = \'checkbox\' checked=\'checked\' onclick=\"changeApp('+row.id+','+!row.cols.fieldAppIsShow+')\"></input>';
                    }else{
                        _app = '<input type = \'checkbox\' onclick=\"changeApp('+row.id+','+!row.cols.fieldAppIsShow+')\"></input>';
                    }
                    return _app;
                }

            },
            action: {
                display: '操作',
                width: 220,
                align: 'center',
                buttons: [
                    {
                        display: '启用',
                        onClick: function(tr, data){
                            showConfirm({
                                icon: 'confirm',
                                content: '您确认要启用吗',
                                ok: function () {
                                    jQuery.ajax({
                                        type: 'POST',
                                        url: '../tableColumn/enable/' + tableId + "/" + data.id +".html",
                                        success: function (result) {
                                            showConfirm({
                                                icon: 'succeed',
                                                content: '启用成功'
                                            });
                                            refresh();
                                        }
                                    });
                                },
                                cancel: function () {
                                }
                            });
                        },
                        isDisabled: function(data){
                            if(data.cols.isFixed == "1"){
                                return true;
                            }
                            return data.cols.isActived == 1;
                        }
                    },
                    {
                        display: '禁用',
                        onClick: function(tr, data){
                            showConfirm({
                                icon: 'confirm',
                                content: '您确认要禁用吗',
                                ok: function () {
                                    jQuery.ajax({
                                        type: 'POST',
                                        url: '../tableColumn/disable/' + tableId + "/" + data.id +".html",
                                        success: function (result) {
                                            showConfirm({
                                                icon: 'succeed',
                                                content: '禁用成功'
                                            });
                                            refresh();
                                        }
                                    });
                                },
                                cancel: function () {
                                }
                            });
                        },
                        isDisabled: function(data){
                            if(data.cols.isFixed == "1"){
                                return true;
                            }
                            return data.cols.isActived == 0;
                        }
                    },
                    {
                        display: '编辑',
                        onClick: function(tr, data){
                            var url = '../tableColumn/getTableColumnUpdatePage.html?fieldId='+data.id;
                            showDialog({
                                id: 'editTableColumn',
                                title : '编辑自定义字段',
                                url : url,
                                width : 380,
                                height : 300,
                                tabId: tabs.getSelfId(window)
                            });
                        }
                    },

                {
                	display: '数据源',
                	onClick: function(tr, data){
                		var url = '../sysDataDictCol/getDataDictColListPageByFieldId.html?fieldId='+data.id;
                		 tabs.add({
                             id: 'LoanTypeFieldDataDictList'+data.id,
                             name: 'LoanTypeFieldDataDictList',
                             pid: tabs.getSelfId(window),
                             title : '数据源',
                             url : url,
                             lock : false
                         });
//                		showDialog({
//                			id: '',
//                			title : '数据源',
//                			url : url,
//                			width : 500,
//                			height : 400,
//                			tabId: tabs.getSelfId(window)
//                		});
                	},
                isDisabled: function(data){
                    return data.cols.fieldType != 'Select' && data.cols.fieldType != 'MultipleSelect';
                }
                },
                    {
                        display: '上移',
                        onClick: function(tr, data){
                            move(data,'moveUp');
                        },
                        isDisabled : function(row, index, data) {
                            return index == 0;
                        }
                    },
                    {
                        display: '下移',
                        onClick: function(tr, data){
                            move(data,'moveDown');
                        },
                        isDisabled : function(row, index, data) {
                            return (++index == data.rows.length);
                        }
                    }

                ]
            },
            onComplete : function(data) {
                $('#lblStatisticsSR').text(data.total);
            },
            usePage: true
        });
    }else {
        $('#gridServiceRecord').flexigrid({
            height: 300,
            url: '../tableColumn/queryTableColumnListData.html?tableId=' + tableId,
           // multiSelect: true,
            fields: fields,
            extendCell: {
                'fieldIsRequired': function(value,row){
                    var _field='';
                    if(row.cols.fieldIsRequired){
                        _field = '<input type = \'checkbox\' checked=\'checked\' onclick=\"changeRequired('+row.id+','+!row.cols.fieldIsRequired+')\"></input>';
                    }else{
                        _field = '<input type = \'checkbox\' onclick=\"changeRequired('+row.id+','+!row.cols.fieldIsRequired+')\"></input>';
                    }
                    return _field;
                },
                'fieldWebIsShow': function(value,row){
                    var _web='';
                    if(row.cols.fieldWebIsShow){
                        _web = '<input type = \'checkbox\' checked=\'checked\' onclick=\"changeWeb('+row.id+','+!row.cols.fieldWebIsShow+')\"></input>';
                    }else{
                        _web = '<input type = \'checkbox\' onclick=\"changeWeb('+row.id+','+!row.cols.fieldWebIsShow+')\"></input>';
                    }
                    return _web;
                },
                'fieldAppIsShow': function(value,row){
                    var _app='';
                    if(row.cols.fieldAppIsShow){
                        _app = '<input type = \'checkbox\' checked=\'checked\' onclick=\"changeApp('+row.id+','+!row.cols.fieldAppIsShow+')\"></input>';
                    }else{
                        _app = '<input type = \'checkbox\' onclick=\"changeApp('+row.id+','+!row.cols.fieldAppIsShow+')\"></input>';
                    }
                    return _app;
                }

            },
            action: {
                display: '操作',
                width: 220,
                align: 'center',
                buttons: [
                    {
                        display: '启用',
                        onClick: function(tr, data){
                            showConfirm({
                                icon: 'confirm',
                                content: '您确认要启用吗',
                                ok: function () {
                                    jQuery.ajax({
                                        type: 'POST',
                                        url: '../tableColumn/enable/' + tableId + "/" + data.id +".html",
                                        success: function (result) {
                                            showConfirm({
                                                icon: 'succeed',
                                                content: '启用成功'
                                            });
                                            refresh();
                                        }
                                    });
                                },
                                cancel: function () {
                                }
                            });
                        },
                        isDisabled: function(data){
                            if(data.cols.isFixed == "1"){
                                return true;
                            }
                            return data.cols.isActived == 1;
                        }
                    },
                    {
                        display: '禁用',
                        onClick: function(tr, data){
                            showConfirm({
                                icon: 'confirm',
                                content: '您确认要禁用吗',
                                ok: function () {
                                    jQuery.ajax({
                                        type: 'POST',
                                        url: '../tableColumn/disable/' + tableId + "/" + data.id +".html",
                                        success: function (result) {
                                            showConfirm({
                                                icon: 'succeed',
                                                content: '禁用成功'
                                            });
                                            refresh();
                                        }
                                    });
                                },
                                cancel: function () {
                                }
                            });
                        },
                        isDisabled: function(data){
                            if(data.cols.isFixed == "1"){
                                return true;
                            }
                            return data.cols.isActived == 0;
                        }
                    },
                    {
                        display: '编辑',
                        onClick: function(tr, data){
                            var url = '../tableColumn/getTableColumnUpdatePage.html?fieldId='+data.id;
                            showDialog({
                                id: 'editTableColumn',
                                title : '编辑自定义字段',
                                url : url,
                                width : 380,
                                height : 300,
                                tabId: tabs.getSelfId(window)
                            });
                        }
                    },

//                 {
//                 	display: '数据源',
//                 	onClick: function(tr, data){
//                 		var url = '../sysDataDictCol/getDataDictColListPageByFieldId.html?fieldId='+data.id;
//                 		 tabs.add({
//                              id: 'LoanTypeFieldDataDictList'+data.id,
//                              name: 'LoanTypeFieldDataDictList',
//                              pid: tabs.getSelfId(window),
//                              title : '数据源',
//                              url : url,
//                              lock : false
//                          });
// //                		showDialog({
// //                			id: '',
// //                			title : '数据源',
// //                			url : url,
// //                			width : 500,
// //                			height : 400,
// //                			tabId: tabs.getSelfId(window)
// //                		});
//                 	},
//                 isDisabled: function(data){
//                     return data.cols.fieldType != 'Select';
//                 }
//                 },
                    {
                        display: '上移',
                        onClick: function(tr, data){
                            move(data,'moveUp');
                        },
                        isDisabled : function(row, index, data) {
                            return index == 0;
                        }
                    },
                    {
                        display: '下移',
                        onClick: function(tr, data){
                            move(data,'moveDown');
                        },
                        isDisabled : function(row, index, data) {
                            return (++index == data.rows.length);
                        }
                    }

                ]
            },
            onComplete : function(data) {
                $('#lblStatisticsSR').text(data.total);
            },
            usePage: true
        });
    }

  //移动
	function move(data,type) {
		jQuery.ajax({
			type : 'POST',
			url : '../tableColumn/moveTableColumnOrderNo.html?fieldId='+data.id+ '&type='+type+'&tableId='+$("#tableId").val(),
			data : {},
			success : function(result) {
				refresh();
			}
		});
	}
    //点击刷新
    $('#btnRefresh').click(function(){
        refresh();
    });

    //刷新
    function refresh() {
        $('#gridServiceRecord').flexReload();
    }

    //点击新建自定义字段btnNewClients
    $('#btnNewClients').click(function(){
       newClient();
    });

    //打开合计公式页面
    $('#btnStatistics').click(function(){
        showDialog({
            id: 'tableFormulaList',
            title : '表单合计',
            url : '../formula/getTableFormulaListPage.html?tableName='+ $("#tableName").val(),
            width : 600,
            height : 350,
            tabId: tabs.getSelfId(window)
        });
    });

    //新建自定义字段
    function newClient() {
       var  typeId = $('#tableId').val();//表id
        var url = '../tableColumn/getTemplateFieldInsertPage.html?&typeIds='+typeId;
        showDialog({
            id: 'insertTemplateField',
            title : '新建字段',
            url : url,
            width : 650,
            height : 400,
            tabId: tabs.getSelfId(window)
        });
    }
});
//是否必填
function changeRequired(id, required){
	var url = '../tableColumn/changeRequired.html?fieldId='+id+'&required='+required;
	jQuery.ajax({
		type : 'POST',
		url : url,
		data : {},
		success : function(result) {
			
		}
	});
}
//app是否显示
function changeApp(id, appIsShow){
	var url = '../tableColumn/changeApp.html?fieldId='+id+'&appIsShow='+appIsShow;
	jQuery.ajax({
		type : 'POST',
		url : url,
		data : {},
		success : function(result) {
			
		}
	});
}
//web是否显示
function changeWeb(id, webIsShow){
	var url = '../tableColumn/changeWeb.html?fieldId='+id+'&webIsShow='+webIsShow;
	jQuery.ajax({
		type : 'POST',
		url : url,
		data : {},
		success : function(result) {
			
		}
	});
}