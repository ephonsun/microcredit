$(function() {

	// 用户登录测试
	$('#doUserLoginIn').click(function() {
		var url = '../mobile/doUserLoginIn.html';
		var data = {
				'param' : "{'account' : 'admin','password' : '123123','deviceId' : 'ASDAD2313123','localIp' : '192.168.1.34','version' : '1.0'}"
		}
		$.post(url, data, function(msg) {
			console.log('登录接口 : ' + msg);
		})
	});
	// 产品分类查询接口
	$('#getBizTypeAndProdClass').click(function() {
		var url = '../mobile/getBizTypeAndProdClass.html';
		var data = {
			'param' : "{'account' : 'admin'}"
		}
		$.post(url, data, function(msg) {
			console.log('产品分类查询接口 : ' + msg);
		})
	});
	// 产品查询接口
	$('#queryProductPageList').click(function() {
		var url = '../mobile/queryProductPageList.html';
		var data = {
			'param' : "{'account' : 'admin','pageSize' : '10'," +
					"'pageNum' : '1','productName' : 'yim123'," +
					"'productCode' : '123123','bizType' : ''," +
					"'classType' : '','updateDate' : '2016-06-13'," +
					"'updateDateEnd' : '2016-06-13'" +
			"}"
		}
		$.post(url, data, function(msg) {
			console.log('产品查询接口 : ' + msg);
		})
	});
	// 录像记录上传
	$('#uploadVideoRecord').click(function() {
		var url = '../mobile/uploadVideoRecord.html';
		var data = {
			'param' : '<video><deviceId>SD-123SDA-ASDAD</deviceId><recordNo>2312312313123123</recordNo><fileId>11</fileId><transit>2</transit><ip>192.168.1.34</ip><beginTime>2016-06-18 10:00:00</beginTime><endTime>2016-06-18 10:10:00</endTime><fileName></fileName><fileSize></fileSize><recTime></recTime><account></account><schema>Android</schema><businessNo></businessNo><videoProcess>1</videoProcess></video>' 
		}
		$.post(url, data, function(msg) {
			console.log('录像记录上传: ' + msg);
		})
	});
	// 业务记录上传接口
	$('#uploadRecordInfo').click(function() {
		var url = '../mobile/uploadRecordInfo.html';
		var data = {
			'param' : '<record><businessNo>213123123</businessNo><customerName>yim123</customerName><certType>1</certType><certNum>123123</certNum><productId>1</productId><productName>sda</productName><businessType>1</businessType><classType>1</classType><buyMoney>111</buyMoney><buyType>1</buyType><dueDate>2016-10-10 00:00:00</dueDate></record>'
		}
		$.post(url, data, function(msg) {
			console.log('业务记录上传接口 : ' + msg);
		})
	});
	// 脱机记录上传接口
	$('#uploadVideoRecordInfo').click(function() {
		var url = '../mobile/uploadVideoRecordInfo.html';
		var data = {
			'param' : '<message><record><businessNo>123123</businessNo><customerName>yim123</customerName><certType>1</certType><certNum>123123</certNum><productId>1</productId><productName>asd</productName><businessType>1</businessType><classType>1</classType><buyMoney>12</buyMoney><buyType>1</buyType><dueDate>2016-10-10 00:00:00</dueDate></record><video><deviceId>sdasdasd</deviceId><recordNo>1231231232</recordNo><fileId>1232</fileId><transit>2</transit><ip>192.168.1.34</ip><beginTime>2016-10-10 10:00:00</beginTime><endTime>2016-10-10 10:10:00</endTime><fileName>123123</fileName><fileSize>123</fileSize><recTime>11</recTime><account>123</account><schema>android</schema><businessNo>123213</businessNo><videoProcess>1</videoProcess></video></message>'
		}
		$.post(url, data, function(msg) {
			console.log('脱机记录上传接口 : ' + msg);
		})
	});

});
