var modelTemplateInfoNodes;
var oTree;
// 根节点数据
var nodes;
// 当前节点数据 - templateId
var currentNode;
//要提交的所有数据
var datas = [];
//页面上替换的html
var tbody;
var html;

var modelTemplateInfoConfig = {
    callback : {
        onClick : treeOnclick
    },
    view : {
        showIcon : false,
        expandSpeed : ''
    },
    data : {
        simpleData : {
            enable : true
        }
    }
};

// 选择第一个节点
function initNodeSelect() {
    oTree = jQuery.fn.zTree.getZTreeObj('ulDataDicts');
    nodes = oTree.getNodes();
    // 如果节点数量>零
    if (nodes.length > 0) {
        currentNode = nodes[0];
        // 选中第一个节点
        oTree.selectNode(currentNode);
        // 点击节点
        //treeOnclick(event, currentNode.id, currentNode);
    }
}
// 点击节点
function treeOnclick(event, treeId, node) {
    currentNode = node;
    $(".data").html('');
    searchDataDictColList();
}

//获取显示机构树
function showModelTemplateNodesList(deptJson) {
    modelTemplateInfoNodes = jQuery.parseJSON(modelTemplateInfoJson);
    $.fn.zTree.init($('#ulDataDicts'), modelTemplateInfoConfig, modelTemplateInfoNodes);
}

// 页面加载完成时...
$(function() {

    if(modelTemplateInfoJson!=''){
        // 获取显示机构树
        showModelTemplateNodesList(modelTemplateInfoJson);
        //选中第一个节点
        initNodeSelect();
    }


    if(currentNode!=undefined) {
        query();
    }

    $("#btnSave").click(function(){
        save();
    })

    $("#btnCancel").click(function(){
        var dialog = getDialog('getModelTemplateInfoSetPage');
        setTimeout(function() {
            dialog.close();
        }, 0);
    })

});

//点击节点时
function searchDataDictColList() {
    query();
}

function query(){
    //如果数组中没有数据
    jQuery.ajax({
        type: 'post',
        dataType: 'json',
        url: '../modelTemplateInfo/queryModelTemplateInfoColumnList.html',
        data: {
            'templateId': currentNode.id
        },
        success: function (data) {
            var isOld = false;
            var oldJson = {};

            var fieldName ;
            var fieldColumnDisplay ;
            var fieldId ;
            var isActived ;
            var ischanged;


            // 遍历
            if (data.rows) {
                for (var j = 0; j < datas.length; j++) {
                    if (datas[j]["templateId"] == currentNode.id) {
                        //记录旧数据
                        oldJson = datas[j]["json"];
                        isOld = true;
                    }
                }
                //如果新数据
                if (!isOld) {
                    for (var i = 0; i < data.rows.length; i++) {
                         var row = data.rows[i];
                         fieldName = row.cols.fieldName;
                         fieldColumnDisplay = row.cols.fieldDisplay;
                         fieldId = row.id;
                         isActived = row.cols.isActived;
                         // ischanged = !(fieldName == fieldColumnDisplay);
                        // alert(name);
                        //主容器
                        tbody = $("#datagrid");
                        if (isActived) {
                            // if(ischanged){
                                html = ' <tr class="data">\n' +
                                    '                                <td >\n' +
                                    '                                    <div style="padding-left: 110px">\n' +
                                    '                                        <input type="checkbox" class="checkboxes" ids="' + fieldId + '" checked>\n' +
                                    '                                        <span style=" text-align:center;" fieldName="' + fieldName + '" >' + fieldName + '</span>\n' +
                                    '                                    </div>\n' +
                                    '                                </td>\n' +
                                    '                                <td>\n' +
                                    '                                    <div style="text-align: center" class="ui-text">\n' +
                                    '                                        <input type="text" class="ui-text-text" oldValue="'+ fieldColumnDisplay +'" value="' + fieldColumnDisplay + '"  maxlength="20" >\n' +
                                    '                                    </div>\n' +
                                    '                                </td>\n' +
                                    '                            </tr>'
                            // }else{
                            //     html = ' <tr class="data">\n' +
                            //         '                                <td >\n' +
                            //         '                                    <div style="padding-left: 110px">\n' +
                            //         '                                        <input type="checkbox" class="checkboxes" ids="' + fieldId + '" checked>\n' +
                            //         '                                        <span style=" text-align:center;" fieldName="' + fieldName + '" >' + fieldName + '</span>\n' +
                            //         '                                    </div>\n' +
                            //         '                                </td>\n' +
                            //         '                                <td>\n' +
                            //         '                                    <div style="text-align: center" class="ui-text">\n' +
                            //         '                                        <input type="text" class="ui-text-text" oldValue="'+ fieldColumnDisplay +'" placeholder="' + fieldColumnDisplay + '"  maxlength="20" >\n' +
                            //         '                                    </div>\n' +
                            //         '                                </td>\n' +
                            //         '                            </tr>'
                            // }
                        } else {
                            // if(ischanged){
                                html = ' <tr class="data">\n' +
                                    '                                <td >\n' +
                                    '                                    <div style="padding-left: 110px">\n' +
                                    '                                        <input type="checkbox" class="checkboxes" ids="' + fieldId + '" >\n' +
                                    '                                        <span style=" text-align:center;" fieldName="' + fieldName + '" >' + fieldName + '</span>\n' +
                                    '                                    </div>\n' +
                                    '                                </td>\n' +
                                    '                                <td>\n' +
                                    '                                    <div style="text-align: center" class="ui-text">\n' +
                                    '                                        <input type="text" class="ui-text-text" oldValue="'+ fieldColumnDisplay +'" value="' + fieldColumnDisplay + '" maxlength="20" >\n' +
                                    '                                    </div>\n' +
                                    '                                </td>\n' +
                                    '                            </tr>'
                            // }else{
                            //     html = ' <tr class="data">\n' +
                            //         '                                <td >\n' +
                            //         '                                    <div style="padding-left: 110px">\n' +
                            //         '                                        <input type="checkbox" class="checkboxes" ids="' + fieldId + '" >\n' +
                            //         '                                        <span style=" text-align:center;" fieldName="' + fieldName + '" >' + fieldName + '</span>\n' +
                            //         '                                    </div>\n' +
                            //         '                                </td>\n' +
                            //         '                                <td>\n' +
                            //         '                                    <div style="text-align: center" class="ui-text">\n' +
                            //         '                                        <input type="text" class="ui-text-text" oldValue="'+ fieldColumnDisplay +'" placeholder="' + fieldColumnDisplay + '" maxlength="20" >\n' +
                            //         '                                    </div>\n' +
                            //         '                                </td>\n' +
                            //         '                            </tr>'
                            // }
                        }
                        tbody.append(html);
                    }
                }else{
                    //是旧数据
                    var fieldIdsArr = oldJson["fieldIds"].split(",");
                    var isActivedsArr = oldJson["isActiveds"].split(",");
                    var fieldNamesArr = oldJson["fieldNames"].split(",");
                    var fieldColumnDisplaysArr = oldJson["fieldColumnDisplays"].split(",");
                    for(var q = 0; q<fieldIdsArr.length;q++){
                        //重新赋值
                        fieldName = fieldNamesArr[q];
                        fieldColumnDisplay = fieldColumnDisplaysArr[q];
                        fieldId = fieldIdsArr[q];
                        isActived = isActivedsArr[q];

                        ischanged = !(fieldName == fieldColumnDisplay);

                        //主容器
                        tbody = $("#datagrid");
                        if (isActived == 1) {
                            // if(ischanged){
                                html = ' <tr class="data">\n' +
                                    '                                <td >\n' +
                                    '                                    <div style="padding-left: 110px">\n' +
                                    '                                        <input type="checkbox" class="checkboxes" ids="' + fieldId + '" checked>\n' +
                                    '                                        <span style=" text-align:center;" fieldName="' + fieldName + '" >' + fieldName + '</span>\n' +
                                    '                                    </div>\n' +
                                    '                                </td>\n' +
                                    '                                <td>\n' +
                                    '                                    <div style="text-align: center" class="ui-text">\n' +
                                    '                                        <input type="text" class="ui-text-text" oldValue="'+ fieldColumnDisplay +'" value="' + fieldColumnDisplay + '"  maxlength="20">\n' +
                                    '                                    </div>\n' +
                                    '                                </td>\n' +
                                    '                            </tr>'
                            // }else{
                            //     html = ' <tr class="data">\n' +
                            //         '                                <td >\n' +
                            //         '                                    <div style="padding-left: 110px">\n' +
                            //         '                                        <input type="checkbox" class="checkboxes" ids="' + fieldId + '" checked>\n' +
                            //         '                                        <span style=" text-align:center;" fieldName="' + fieldName + '" >' + fieldName + '</span>\n' +
                            //         '                                    </div>\n' +
                            //         '                                </td>\n' +
                            //         '                                <td>\n' +
                            //         '                                    <div style="text-align: center" class="ui-text">\n' +
                            //         '                                        <input type="text" class="ui-text-text" oldValue="'+ fieldColumnDisplay +'" placeholder="' + fieldColumnDisplay + '"  maxlength="20">\n' +
                            //         '                                    </div>\n' +
                            //         '                                </td>\n' +
                            //         '                            </tr>'
                            // }
                        } else {
                            // if(ischanged){
                                html = ' <tr class="data">\n' +
                                    '                                <td >\n' +
                                    '                                    <div style="padding-left: 110px">\n' +
                                    '                                        <input type="checkbox" class="checkboxes" ids="' + fieldId + '" >\n' +
                                    '                                        <span style=" text-align:center;" fieldName="' + fieldName + '" >' + fieldName + '</span>\n' +
                                    '                                    </div>\n' +
                                    '                                </td>\n' +
                                    '                                <td>\n' +
                                    '                                    <div style="text-align: center" class="ui-text">\n' +
                                    '                                        <input type="text" class="ui-text-text" oldValue="'+ fieldColumnDisplay +'" value="' + fieldColumnDisplay + '" maxlength="20">\n' +
                                    '                                    </div>\n' +
                                    '                                </td>\n' +
                                    '                            </tr>'
                            // }else{
                            //     html = ' <tr class="data">\n' +
                            //         '                                <td >\n' +
                            //         '                                    <div style="padding-left: 110px">\n' +
                            //         '                                        <input type="checkbox" class="checkboxes" ids="' + fieldId + '" >\n' +
                            //         '                                        <span style=" text-align:center;" fieldName="' + fieldName + '" >' + fieldName + '</span>\n' +
                            //         '                                    </div>\n' +
                            //         '                                </td>\n' +
                            //         '                                <td>\n' +
                            //         '                                    <div style="text-align: center" class="ui-text">\n' +
                            //         '                                        <input type="text" class="ui-text-text" oldValue="'+ fieldColumnDisplay +'" placeholder="' + fieldColumnDisplay + '" maxlength="20">\n' +
                            //         '                                    </div>\n' +
                            //         '                                </td>\n' +
                            //         '                            </tr>'
                            // }
                        }
                        tbody.append(html);
                    }
                }

                //浏览器不支持 placeholder 时才执行
                // if (!('placeholder' in document.createElement('input'))) {
                //     $('[placeholder]').each(function () {
                //         var $tag = $(this); //当前 input
                //         var $copy = $tag.clone();   //当前 input 的复制
                //         if ($copy.val() == "") {
                //             $copy.css("color", "#999");
                //             $copy.val($copy.attr('placeholder'));
                //         }
                //         $copy.focus(function () {
                //             if (this.value == $copy.attr('placeholder')) {
                //                 this.value = '';
                //                 this.style.color = '#000';
                //             }
                //         });
                //         $copy.blur(function () {
                //             if (this.value=="") {
                //                 this.value = $copy.attr('placeholder');
                //                 $tag.val("");
                //                 this.style.color = '#a9a9a9';
                //             } else {
                //                 $tag.val(this.value);
                //             }
                //         });
                //         $tag.hide().after($copy.show());    //当前 input 隐藏 ，具有 placeholder 功能js的input显示
                //     });
                // }




                //当值改变时，绑定change事件
                $(".checkboxes").click(function () {
                    changeValue(currentNode.id);
                });

                $(".ui-text-text").change(function () {
                    //非空，唯一性校验
                    //记录改变的值
                    var changeDisplayName = [];
                    $(".ui-text-text").each(function(){
                        changeDisplayName.push($(this).attr("oldValue"))
                    });

                        // //如果不输入，或者为空
                    var checkVal = $(this).val().trim();
                    if(checkVal == ""){
                        // if($(this).parent().parent().prev().find('span').attr('fieldName') == $(this).attr("oldValue")){
                        //     $(this).attr('placeholder',$(this).attr("oldValue"))
                        //     $(this).val("")
                        // }else{
                            $(this).val($(this).attr("oldValue"))
                        // }
                        showConfirm({
                            icon: 'warning',
                            content: '不能为空！'
                        });
                        // return;
                    }
                    if ($.inArray(checkVal, changeDisplayName) > -1) {
                        //排除自己
                        if($(this).val() != $(this).attr("oldValue")){
                            // if($(this).parent().parent().prev().find('span').attr('fieldName') == $(this).attr("oldValue")){
                            //     $(this).val('')
                            //     $(this).attr('placeholder',$(this).attr("oldValue"))
                            // }else{
                                $(this).val($(this).attr("oldValue"))
                            // }
                            showConfirm({
                                icon: 'warning',
                                content: '字段重复了！'
                            });
                        }
                    } else {
                        $(this).attr("oldValue", $(this).val());
                    }
                    changeValue(currentNode.id);
                });
            }
        }
    });
}

//值改变
function changeValue(templateId){
    var data = {};
    var json = {};
    var fieldIds = [];
    var isActiveds = [];
    var fieldNames = [];
    var fieldColumnDisplays = [];
    //这次更改是否包含在老数据中
    var isOld = false;

    //遍历总数组，看是否已经添加数据
    for(var i = 0;i<datas.length;i++) {
        if(datas[i]["templateId"] == templateId) {
            isOld = true;
            break;
        }
    }

    if(isOld){
        //更新
        $(".checkboxes").each(function(){
            isActiveds.push($(this).attr("checked")?1:0);
            fieldIds.push($(this).attr("ids"));
            fieldNames.push($(this).next().attr("fieldName"));
        });
        $(".ui-text-text").each(function(){
            //如果不输入，或者为空
            if(!$(this).val()){
                fieldColumnDisplays.push($(this).attr("placeholder"));
                // placeholders.push($(this).attr("placeholder"))
            }else{
                fieldColumnDisplays.push($(this).val());
                // placeholders.push($(this).attr("placeholder"))
            }
        });
        json["fieldIds"]= fieldIds.join(",");
        json["isActiveds"]= isActiveds.join(",");
        json["fieldNames"]= fieldNames.join(",");
        json["fieldColumnDisplays"] = fieldColumnDisplays.join(",");

        datas[i]["json"] = json;

    }else{
        //如果是新的数据,组装内部数据
        $(".checkboxes").each(function(){
            isActiveds.push($(this).attr("checked")?1:0);
            fieldIds.push($(this).attr("ids"));
            fieldNames.push($(this).next().attr("fieldName"));
        });
        $(".ui-text-text").each(function(){
            //如果不输入，或者为空
            if(!$(this).val()){
                // fieldColumnDisplays.push(" ");
                fieldColumnDisplays.push($(this).attr("oldValue"));
            }else{
                fieldColumnDisplays.push($(this).val());
                // placeholders.push($(this).attr("placeholder"));
            }
        });
        json["fieldIds"]= fieldIds.join(",");
        json["isActiveds"]= isActiveds.join(",");
        json["fieldNames"]= fieldNames.join(",");
        json["fieldColumnDisplays"] = fieldColumnDisplays.join(",");
        //外部data
        data["templateId"] = templateId;
        data["json"] = json;
        datas.push(data);
    }
}

//保存
function save(){
    datas = JSON.stringify(datas);
    jQuery.ajax({
        type : 'post',
        url : '../modelTemplateInfo/saveModeTemplate.html',
        data : {
            "jsons":datas
        },
        success : function(result) {
            showConfirm({
                icon: 'succeed',
                content: '操作成功'
            });
            closeDialog();
        }
    });
}

//关闭dialog
function closeDialog(){
    var dialog = getDialog('getModelTemplateInfoSetPage');
    var win = tabs.getIframeWindow("modelTemplateInfo");
    win.location.reload(true);
    setTimeout(function() {
        dialog.close();
    }, 0);
}


//让IE支持 placeholder
(function($) {
    $.fn.placeholder = function(options) {
        var opts = $.extend({}, $.fn.placeholder.defaults, options);
        var isIE = document.all ? true : false;
        return this.each(function() {
            var _this = this,
                placeholderValue = _this.getAttribute("placeholder"); //缓存默认的placeholder值
            if (isIE) {
                _this.setAttribute("value", placeholderValue);
                _this.onfocus = function() {
                    $.trim(_this.value) == placeholderValue ? _this.value = "" : '';
                };
                _this.onblur = function() {
                    $.trim(_this.value) == "" ? _this.value = placeholderValue : '';
                };
            }
        });
    };
})(jQuery);