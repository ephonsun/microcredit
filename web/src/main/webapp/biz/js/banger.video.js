function newGuid(){
    var guid = "";
    for (var i = 1; i <= 32; i++){
        var n = Math.floor(Math.random()*16.0).toString(16);
        guid +=   n;
        if((i==8)||(i==12)||(i==16)||(i==20))
            guid += "-";
    }
    return guid;
}

/* 触发事件  */
function event_raise(){
    var reFlag =true;
    var evtHandler,params;
    evtHandler =arguments[0];
    try{
        if (evtHandler==null ||evtHandler=="")return reFlag;
    }catch(e){
    }
    if (typeof(evtHandler)=="function"){
        reFlag =evtHandler(arguments[1],arguments[2],arguments[3],arguments[4],arguments[5]);
        if (reFlag==null)reFlag =true;
        return reFlag;
    }else if (evtHandler.constructor ==Array ||typeof(evtHandler)=="object"){
        for (var index=0; index<evtHandler.length; index++){
            var eFun =evtHandler[index];
            if (eFun ==null)continue;
            reFlag =eFun(arguments[1],arguments[2],arguments[3],arguments[4],arguments[5]);
            if (reFlag==null)reFlag =true;
            if (!reFlag)break;
        }
        return reFlag;
    }
    params ="";
    for(var index=1;index<arguments.length;index++){
        if (arguments[index]!=null){
            if (arguments[index].constructor==String) params =params +"'" +escape(arguments[index])+"',";
            else params =params +"arguments[" +index +"],";
        }
        else params =params +"null,";
    }
    if (params.length>0) params ="(" +params.substr(0,params.length-1)+")";
    if (evtHandler.indexOf(";")>-1){
        var funs =evtHandler.split(';');
        for(var index=0; index<funs.length; index++){
            var fun =funs[index];
            if (fun.indexOf("(")==-1) fun +=params;
            reFlag =eval(fun);
            if (reFlag==null)reFlag =true;
            if (!reFlag)break;
        }
    }else{
        if (evtHandler.indexOf("(")==-1) evtHandler +=params;
        reFlag =eval(evtHandler);
    }
    if (reFlag==null)reFlag =true;
    return reFlag;
};

function banger_namespace(){	//添加命名空间的功能
    var a=arguments, o=null, i, j, d, rt;
    for (i=0; i<a.length; ++i) {
        d=a[i].split(".");
        rt = d[0];
        eval('if (typeof ' + rt + ' == "undefined"){' + rt + ' = {};} o = ' + rt + ';');
        for (j=1; j<d.length; ++j) {
            o[d[j]]=o[d[j]] || {};
            o=o[d[j]];
        }
    }
}

banger_namespace("banger.client");

(function($,δ,win){
    win.banger.client=δ={
        version:"2.0.1.1",timeHandler:-1,timeRate:500,cometState:-1,events:{},clientId:newGuid()
        ,result:{"success":0,"fail":-1,"validTelNum":201,"limitTelNum":202,"timeout":100,"nophone":101
            ,"online":102,"noVideo":103,"playing":107,"nofile":104,"diskFUll":105}
        ,video:function(){			//话机对像
            this.timer=null;
            this.ocx=null;

            this.testConnect=function(callback){
                //1.判断托盘是否启动
                δ.fn.jsonpRequest({"msgType":"testConnect","params":""},null,function(status){
                    //2.如果未启动,判断守护进程是否启动
                    if(status!="success"){
                        //3. 如果守护进程已启动,则检测安装
                        setTimeout(function(){
                            try{
                                δ.fn.protectRequest({"msgType":"isInstall","params":"{\"runTray\":\"0\"}","message":""},function(args){
                                    //4 如果返回失败,则提示托盘未安装 如果已安装,则自动启动托盘
                                    if (args.result != '0')
                                    {
                                        event_raise(callback,"未安装话机软件");
                                    }
                                },function(status){
                                    if(status!="success"){
                                        event_raise(callback,"未安装话机软件");
                                    }
                                })
                            }catch(e){}},1000);
                    }else{
                        //2.1 如果已启动,则不做处理
                    }

                });
            };
            //---------------------------------------------下面是视频------------------------------------------------//
            this.getCameraId=function(callback){		//设备ID
                δ.fn.jsonpRequest({"msgType":"getCameraId","params":"","message":"获取摄像头ID"},callback);
            };
            this.openVideoWin=function(callback){		//预览视频
                δ.fn.jsonpRequest({"msgType":"openVideoWin","params":"","message":"打开摄像头"},callback);
            };
            this.closeVideoWin=function(callback){		//关闭预览视频
                δ.fn.jsonpRequest({"msgType":"closeVideoWin","params":"","message":"关闭摄像头"},callback);
            };
            this.openScreenWin=function(param,callback){		//
                δ.fn.jsonpRequest({"msgType":"openScreenWin","params":"{\"param\":\""+param+"\"}","message":"打开录制页面"},callback);
            };
            this.closeScreenWin=function(param,callback){		//
                δ.fn.jsonpRequest({"msgType":"closeScreenWin","params":"{\"param\":\""+param+"\"}","message":"关闭录制页面"},callback);
            };
            this.startVideo=function(exserial,callback){		//开始视频
                δ.fn.jsonpRequest({"msgType":"startVideo","params":"{\"exserial\":\""+exserial+"\"}","message":"开始视频"},callback);
            };
            this.getState=function(callback){			//
                δ.fn.jsonpRequest({"msgType":"getState","params":""},callback);
            };
            this.stopVideo=function(exserial,callback){			//结束视频
                δ.fn.jsonpRequest({"msgType":"stopVideo","params":"{\"exserial\":\""+exserial+"\"}","message":"结束视频"},callback);
            };
            this.uploadVideo=function(exserial,callback){			//上传视频
                δ.fn.jsonpRequest({"msgType":"uploadVideo","params":"{\"exserial\":\""+exserial+"\"}","message":"开始上传视频"},callback);
            };
            this.deleteLastVideo=function(exserial,callback){			//删除视频
                δ.fn.jsonpRequest({"msgType":"deleteLastVideo","params":"{\"exserial\":\""+exserial+"\"}","message":"删除视频"},callback);
            };
            this.playVideo=function(exserial,callback){			//播放视频
                δ.fn.jsonpRequest({"msgType":"playVideo","params":"{\"exserial\":\""+exserial+"\"}","message":"开始播放视频"},callback);
            };
            this.setSchema=function(schema,callback){			//设置模式
                δ.fn.jsonpRequest({"msgType":"setSchema","params":"{\"schema\":\""+schema+"\"}","message":"设置模式成功"},callback);
            };
            this.setAccount=function(account,callback){
                δ.fn.jsonpRequest({"msgType":"setAccount","params":"{\"account\":\""+account+"\"}","message":"设置帐号成功"},callback);
            };
            this.openPhotoWin=function(exserial,callback){
                δ.fn.jsonpRequest({"msgType":"openPhotoWin","params":"{\"id\":\""+exserial+"\"}","message":"打开拍照界面"},callback);
            };
            this.getScreenPicture=function(id,callback){
                δ.fn.jsonpRequest({"msgType":"getScreenPicture","params":"{\"id\":\""+id+"\"}","message":"截屏成功"},callback);
            };
            this.setIpAddress=function(params,callback){
                δ.fn.jsonpRequest({"msgType":"setIpAddress","params":params,"message":"设置托盘服务器信息成功"},callback);
            };
            this.setParams=function(params,callback){
                δ.fn.jsonpRequest({"msgType":"setParams","params":params,"message":"设置参数成功"},callback);
            };
            this.addEvents=function(events){			//添加事件监听
                for(var nm in events){
                    if(!δ.events[nm])δ.events[nm]=events[nm];
                }
                if(δ.timeHandler>0)win.clearInterval(δ.timeHandler);
                δ.timeHandler = win.setInterval(function(){if(δ.cometState<0)δ.fn.jsonpComet();},δ.timeRate);
            };
        }
        ,create:function(){
            var video = new δ.video();
            return video;
        }
        ,fn:{
            jsonpRequest:function(data,callback,completeback){
                δ.fn.jsonpBaseReq("http://127.0.0.1:54321/reqeust.jsonp?clientId="+δ.clientId,data,callback,completeback);
            },
            protectRequest:function(data,callback,completeback){
                δ.fn.jsonpBaseReq("http://127.0.0.1:54322/reqeust.jsonp?clientId="+δ.clientId,data,callback,completeback);
            },
            jsonpBaseReq:function(url,data,callback,completeback){
                $.ajax({
                    url:url,
                    type:"get",
                    data:data,
                    timeout:10000,				//超时10秒
                    async:false,
                    jsonp:"callback",			//服务端用于接收callback调用的function名的参数
                    jsonpCallback:"localCallback",			//callback的function名称
                    dataType:"jsonp",
                    success:function(msg){
                        var args = (msg.content)?$.parseJSON(msg.content):{};
                        var result = (args.result)?parseInt(args.result):0;
                        if(result==δ.result.nophone){
                            args["result"]=result;
                            args["message"]="无可用话机";
                            δ.fn.raiseCallback(args,callback);
                        }else if(result==δ.result.online){
                            args["result"]=result;
                            args["message"]="请先挂断后再拨";
                            δ.fn.raiseCallback(args,callback);
                        }else if(result==δ.result.playing){
                            args["result"]=result;
                            args["message"]="视频正在播放中,请先关闭播放窗口!";
                            δ.fn.raiseCallback(args,callback);
                        }else if(result==δ.result.noVideo){
                            args["result"]=result;
                            args["message"]="没有可用摄像设备,请先确认摄像设备是否连接正常!";
                            δ.fn.raiseCallback(args,callback);
                        }else if(result==δ.result.nofile){
                            args["result"]=result;
                            args["message"]="视频录制失败，请重试!";
                            δ.fn.raiseCallback(args,callback);
                        }else if(result==δ.result.diskFUll) {
                            args["result"] = result;
                            args["message"] = "磁盘空间将满，请清理!";
                            δ.fn.raiseCallback(args, callback);
                            diskFUll
                        }else{
                            args["result"]=δ.result.success;
                            args["message"]=data.message;
                            δ.fn.raiseCallback(args,callback);
                        }
                    },
                    error:function(XHR,status,errorThrown){

                    },
                    complete:function(XHR,status){
                        if(completeback){
                            if(status.readyState){
                                if(status!="success")event_raise(completeback,'error');
                            }else{
                                event_raise(completeback,status);
                            }
                        }
                        //if(completeback){alert(status);
                        //	event_raise(completeback,status);
                        //}
                    }
                });// ie 8+, chrome and some other browsers
                var head = document.head || $('head')[0] || document.documentElement; // code from jquery
                var script = $(head).find('script')[0];
                script.onerror = function(evt) {
                    if(completeback){
                        event_raise(completeback,'error');
                    }
                    // do some clean

                    // delete script node
                    if (script.parentNode) {
                        script.parentNode.removeChild(script);
                    }
                    // delete jsonCallback global function
                    var src = script.src || '';
                    var idx = src.indexOf('jsoncallback=');
                    if (idx != -1) {
                        var idx2 = src.indexOf('&');
                        if (idx2 == -1) {
                            idx2 = src.length;
                        }
                        var jsonCallback = src.substring(idx + 13, idx2);
                        delete window[jsonCallback];
                    }
                };
            }
            ,jsonpComet:function(){
                $.ajax({
                    url:"http://127.0.0.1:54321/comet.jsonp?request=comet&clientId="+δ.clientId,
                    type:"get",
                    data:{},
                    timeout:2000,				//超时5分钟
                    async:false,
                    jsonp:"callback",			//服务端用于接收callback调用的function名的参数
                    jsonpCallback:"localCallback",			//callback的function名称
                    dataType:"jsonp",
                    beforeSend:function(){
                        δ.cometState=1;			//长连接启动
                    },
                    success:function(msg){
                        δ.cometState=-1;
                        if(msg.msgType!="timeout"){
                            var callback = δ.events[msg.msgType];
                            var args = (msg.content)?$.parseJSON(msg.content):{};
                            δ.fn.raiseCallback(args,callback);
                        }
                    },
                    error:function(XHR,status,errorThrown){

                    },
                    complete:function(XHR,status){ 		//请求完成后最终执行参数
                        δ.cometState=-1;
                    }
                });
            }
            ,jsonpCallback:function(msg){

            }
            ,raiseCallback:function(args,callback){
                try{
                    if(callback)event_raise(callback,args);
                }catch(e){

                }
            }
        }
    }
})(jQuery,banger.client,window);

var localCallback = banger.client.fn.jsonpCallback;
