$(".ui.dropdown").dropdown({
	on: "hover"
});
$("#menu_toggle").click(function () {
	$(".nav_item").toggleClass('m-mobile-hide');
	$(".nav_search").toggleClass('no');
});

$(".funIcon").click(function () {
	$('.m-search').toggleClass('m-active');
	$('.search.line').toggleClass('no');
	$('.close').toggleClass('no');
	$('#footer').toggleClass('no');
	$('#header').toggleClass('no');
});

function logout(){
	$.ajax({
		url: "/logout",
		type: "GET",
		async: false,
		success: function (data) {
			layer.closeAll();
			layer.msg(data.message, {time: 2000}, function () {
				 window.location.replace("/admin/login.html");
			});
		},
		error: function (obj) {
			layer.closeAll();
			layer.msg("error，也许您的网络有问题");
		}
	});
}
//去除session中的用户值并复制
function getUserInfo() {
	$.ajax({
		url: "/userInfoByTicket",
		type: "GET",
		success: function (data) {
			layer.closeAll();
			if (data.code === 200) {
				setUserInfo(data.obj);
			} else {
				layer.msg(data.message);
			}
		},
		error: function (obj) {

			layer.closeAll();  layer.msg("error，也许您的网络有问题");
		}
	});
}

function setUserInfo(obj) {
	$('#userId').val(obj.id);
	$('#username').val(obj.username);
	$('#username').text(obj.username);
	$('#nickname').text(obj.nickname);
	$('#nickname2').val(obj.nickname);
	$('#nickname2').text(obj.nickname);
	$('#email').val(obj.email);
	$('#email').text(obj.email);
	$('#avatar').attr('src', obj.avatar);
	$('#avatar2').attr('src', obj.avatar);
	if (obj.type == '1') {
		$('#userType').text("superman");
	}
}
//展示loading
function g_showLoading(){
	var idx = layer.msg('处理中...', {icon: 16,shade: [0.5, '#f5f5f5'],scrollbar: false,offset: '0px', time:100000}) ;  
	return idx;
}
//salt
var g_passsword_salt="1a2b3c4d"
// 获取url参数
function g_getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r != null) return decodeURI(r[2]);
	//unescape
	return null;
};

function getUrl(name) {
	// http://xxx?type=list
	const paramsStr = window.location.search;
	const params = new URLSearchParams(paramsStr);
	return params.get(name);// list
};
//设定时间格式化函数，使用new Date().format("yyyy-MM-dd HH:mm:ss");
Date.prototype.format = function (format) {
	var args = {
		"M+": this.getMonth() + 1,
		"d+": this.getDate(),
		"H+": this.getHours(),
		"m+": this.getMinutes(),
		"s+": this.getSeconds(),
	};
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var i in args) {
		var n = args[i];
		if (new RegExp("(" + i + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length));
	}
	return format;
};
