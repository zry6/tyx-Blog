function getCommentHtml(val, adminCommentHtml, replyHtml) {
    let commentHtml = ' <div  class="comment"><a class="avatar"><img src="'
        + val.avatar
        + '"></a><div class="content"><a class="author m-font-color m-font-size"><span>'
        + val.nickname
        + '</span> '
        + adminCommentHtml
        + ' </a><div class="metadata"><span class="date">'
        + val.createTime
        + '</span><div class="actions"> <a class="reply ui blue mini button" onclick="reply('
        + val.id + ',' + "'" + val.email + "'" + ',' + "'" + val.nickname + "'"
        + ')">回复</a></div></div> <div id="comment-' + val.id + '"  class="text m-padded-tb-mini">'
        + val.content
        + '</div></div>'
        + replyHtml
        + '</div>';
    return commentHtml;
};

function getComment() {

    $.get("/comments/" + $("[name='blog.id']").val(), null, function (data, textStatus) {

        if (data.code == 200) {
            setComment(data.data);
        }
        layer.msg(data.msg);
    });
    //如果已经登录了那就不用再填写 评论信息了
    $.ajax({
        url: "/userInfoByTicket",
        type: "GET",
        success: function (res) {
            if (res.code === 200) {
                $("[name='nickname']").val(res.data.nickname);
                $("[name='email']").val(res.data.email);
                $("[name='login']").val(true);
                $("#avatar").attr("src", res.data.avatar);
                $("#div-QQ").remove();
            }
        },
        error: function (obj) {
            layer.closeAll();
            layer.msg(obj.code + "error，也许您的网络有问题");
        }
    });
};
function setComment(data) {
    let commentHtml = "";
//每页的大小
    var pageSiz = data.size;
    //数据
    var records = data.records;
    //数据总数
    var total = data.total;
    //当前页
    let current = data.current;
    //总页数
    var pages = data.pages;

    $.each(records, function (index, val) {
        let adminCommentHtml = "";
        if (val.adminComment == true) {
            adminCommentHtml += '<div class="ui mini basic teal left pointing label m-padded-mini">博主</div>';
        }

        let replyHtml = "";
        $.each(val.replyComments, function (index, reply) {

            if (index == 0) {
                replyHtml += '<div class="comments">';
            }
            let replyadminCommentHtml = "";
            if (reply.adminComment == true) {
                replyadminCommentHtml += '<div class="ui mini basic teal left pointing label m-padded-mini">博主</div>';
            }
            replyHtml += getCommentHtml(reply, replyadminCommentHtml, "");

        });
        if (replyHtml.length != 0) {
            replyHtml += "</div>";
        }
        commentHtml += getCommentHtml(val, adminCommentHtml, replyHtml);

    });
    $("#comment-before").after(commentHtml);
};


function postData(comment) {
    $.ajax({
        url: "/comments",
        type: "POST",
        data: JSON.stringify(
            comment,
        ),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        async: false,
        success: function (data) {
            layer.closeAll();
            if (data.code === 200) {
                let val = data.data;
                let commentHtml = '';
                let adminCommentHtml = "";
                if (val.adminComment == true) {
                    adminCommentHtml += '<div class="ui mini basic teal left pointing label m-padded-mini">博主</div>';
                }
                commentHtml += getCommentHtml(val, adminCommentHtml, "");
                $("#comment-before").after(commentHtml);
                clearContent();
            }
            layer.msg(data.msg);
        },
        error: function () {
            layer.msg("出现意料之外的错误，请检查网络");
            layer.closeAll();
        }
    });
};
$('#commentpost-btn').click(function () {
    var boo = $('#comment-form').form('validate form');
    if (boo) {
        let data = {
            'parentComment.email': $("#blogInfo-user-email").val(),
            "blogId": $("[name='blog.id']").val(),
            "nickname": $("[name='nickname']").val(),
            "email": $("[name='email']").val(),
            "content": $("[name='content']").val(),
            "avatar": $("#avatar").attr("src")
        };
        postData(data);
        console.log('校验成功');
    } else {
        console.log('校验失败');
    }
});
function reply(parentCommentId, parentCommentEmail, parentCommentNickname) {

    $('#reply-form').remove();
    let replyFormHtml = "";
    replyFormHtml +=
        '<div id="reply-form" class="ui form">'
        + '<input type="hidden" name="reply.parentId" value="'
        + parentCommentId
        + '">'
        + '<input type="hidden" name="reply.parentEmail" value="'
        + parentCommentEmail
        + '">'
        + '<input type="hidden" name="reply.parentNickname" value="'
        + parentCommentNickname
        + '">'
        + '<div class="field"> <textarea name="reply.content" placeholder="评论千万条，友善第一条"></textarea></div>'
        + '<div class="fields"><div class="ui left icon input"><img id="reply-avatar" src="/images/avatar/2.png" class="ui mini circular image" style="margin: 5px 0px;">'
        + '<div class="field m-mobile-wide m-margin-bottom-small"></div></div>'
        + '<div id="reply-div-QQ" class="field m-mobile-wide m-margin-bottom-small"><div class="ui left icon input"> <i class="qq icon"></i><input id="reply-QQ" onblur="getQQ(this)" type="text" name="qq" placeholder="输入qq号自动获取昵称头像"></div></div>'
        + ' <div class="field m-mobile-wide m-margin-bottom-small"><div class="ui left icon input"><i class="user icon"></i><input type="text" name="reply.nickname" placeholder="昵称(必填)"></div></div>'
        + ''
        + '<div class="field m-mobile-wide m-margin-bottom-small"><div class="ui left icon input"><i class="mail icon"></i><input type="text" name="reply.email" placeholder="邮箱(将保密)"></div></div>'
        + '<div class="field m-mobile-wide m-margin-bottom-small"><button onclick="replyPost()" class="ui blue button m-mobile-wide"><i class="edit icon"></i>发布</button></div>'
        + '</div>';

    $('#comment-' + parentCommentId).after(replyFormHtml);


    if ($("[name='login']").val()) {
        $("#reply-div-QQ").remove();
        $("#reply-avatar").attr("src", $("#blog-user-avatar").attr('src'));
        $("[name='reply.nickname']").val($("[name='nickname']").val());
        $("[name='reply.email']").val($("[name='email']").val());
    }

};

function replyPost() {
    //绑定事件
    $('#reply-form').form({
        fields: {
            replyContent: {
                identifier: 'reply.content',
                rules: [{
                    type: "empty",
                    prompt: "提示：请输入评论内容"
                }]
            },
            replyNickname: {
                identifier: 'reply.nickname',
                rules: [{
                    type: "empty",
                    prompt: "提示：请输入你的昵称"
                }]
            },
            replyEmail: {
                identifier: 'reply.email',
                rules: [{
                    type: "email",
                    prompt: "请填写正确的邮箱地址"
                }]
            }
        }

    });
    var boo = $('#reply-form').form('validate form');
    if (boo) {
        let parentComment = {
            'id': $("[name='reply.parentId']").val(),
            'email': $("[name='reply.parentNickname']").val()
        };
        let reply = {};
        reply.blogId = $("[name='blog.id']").val();
        reply.nickname = $("[name='reply.nickname']").val();
        reply.email = $("[name='reply.email']").val();
        reply.content = $("[name='reply.content']").val();
        reply.avatar = $("#reply-avatar").attr("src");
        reply.parentComment = parentComment;

        $.ajax({
            url: "/comments",
            type: "POST",
            data: JSON.stringify(
                reply
            ),
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            async: false,
            success: function (data) {
                layer.closeAll();
                if (data.code === 200) {
                    //刷新评论
                    console.info("刷新评论");
                    $(".comment").remove();
                    getComment();
                }
            },
            error: function () {
                layer.msg("出现意料之外的错误，请检查网络");
                layer.closeAll();
            }
        });
    } else {
        layer.msg("请填入合法的参数");
    }
};


function clearContent() {
    $("[name='content']").val('');
    $("[name='parentComment.id']").val('');
    $("[name='parentComment.email']").val('');
    $("[name='content']").attr("placeholder", "欢迎你的评论...");
};
