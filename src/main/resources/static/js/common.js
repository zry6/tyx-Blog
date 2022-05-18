function browser() {
    var browser = {
        versions: function () {
            var u = navigator.userAgent, app = navigator.appVersion;
            return {     //移动终端浏览器版本信息
                trident: u.indexOf('Trident') > -1, //IE内核
                presto: u.indexOf('Presto') > -1, //opera内核
                webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
                gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
                mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
                ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
                android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或uc浏览器
                iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器
                iPad: u.indexOf('iPad') > -1, //是否iPad
                webApp: u.indexOf('Safari') == -1 //是否web应用程序，没有头部与底部
            };
        }(),
        language: (navigator.browserLanguage || navigator.language).toLowerCase()
    }
    if (browser.versions.mobile) {
        /*移动端移除animated特效*/
        $('#typeDiv').removeClass('animated');
        $('#tagDiv').removeClass('animated');
        $('#center-header').removeClass('animated');
        $('#center').removeClass('animated');
        $('#recommendDiv').removeClass('animated');
        $('#middleValue').removeClass('animated');
        $('#blogDiv').removeClass('animated');
    } else if (!browser.versions.mobile) { //非移动端，动态加载js和div
        var script1 = document.createElement('script');
        script1.type = 'text/javascript';
        script1.src = '/js/snowFall.js';

        var script2 = document.createElement('script');
        script2.type = 'text/javascript';
        script2.src = '/js/click_show_text.js';

        $('body').append(script1);
        $('body').append(script2);
    }

    getRecommends();
}


function QQData(QQ) {
    $.ajax({
        url: "https://api.usuuu.com/qq/" + QQ,
        type: "GET",
        dataType: "json",
        success: function (result) {
            return result;
        },
        error: function (res) {
            return "error";
        }
    });
};

// $(".ui.dropdown").dropdown({
// 	on: "hover"
// });
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

function logout() {
    $.ajax({
        url: "/logout",
        type: "post",
        async: false,
        success: function (res) {
            layer.closeAll();
            layer.msg(res.msg, {time: 1000}, function () {
                window.location.replace("/back/login.html");
            });
        },
        error: function (res) {
            layer.closeAll();
            layer.msg(res.code + "error，也许您的网络有问题");
        }
    });
}

//redis用户值并复制
function getUserInfo() {
    $.ajax({
        url: "/userInfoByTicket",
        type: "GET",
        success: function (res) {
            layer.closeAll();
            if (res.code === 200) {
                setUserInfo(res.data);
            } else {
                layer.msg(res.msg);
            }
        },
        error: function (obj) {
            layer.closeAll();
            layer.msg(obj.code + "error，也许您的网络有问题");
        }
    });
}

//redis用户值并复制
function getRecommends() {
    $.get("/recommendBlogs", null, function (data, textStatus) {
        if (data.code == 200) {
            let recommendHtml = "";
            $.each(data.data, function (index, val) {
                recommendHtml +=
                    '<a href="/p/blog.html?article='
                    + val.id
                    + '"class="item">'
                    + val.title
                    + '</a>';
            });
            $("#recommends").html(recommendHtml);
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
function g_showLoading() {
    var idx = layer.msg('处理中...', {icon: 16, shade: [0.5, '#f5f5f5'], scrollbar: false, offset: '0px', time: 100000});
    return idx;
}

//salt
var g_passsword_salt = "1a2b3c4d"

// 获取url参数
function g_getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
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
