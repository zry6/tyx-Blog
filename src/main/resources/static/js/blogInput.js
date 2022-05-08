//更新博客 新增文章的js
function postBlog() {
    data = {
        title: $('[name="title"]').val(),
        content: $("#content").val(),
        typeId: $('[name="type.id"]').val(),
        tagIds: $('[name="tagIds"]').val(),
        firstPicture: $('[name="firstPicture"]').val(),
        description: $('[name="description"]').val(),
        published: $('[name="published"]').val(),
        flag: $('[name="flag"]').val(),
        shareStatement: $('[name="shareStatement"]').prop('checked'),
        recommend: $('[name="recommend"]').prop('checked'),
        appreciation: $('[name="appreciation"]').prop('checked'),
        commentable: $('[name="commentable"]').prop('checked'),
    };
    //新增文章
    $.ajax({
        url: "/admin/blogs",
        type: "POST",
        data: JSON.stringify(data),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        async: false,
        success: function (res) {
            layer.closeAll();
            if (res.code === 201) {
                if ($('[name="published"]').val() == "true") {
                    layer.msg(res.msg, {time: 2000}, function () {
                        window.location.replace("/back/blogs.html");
                    });
                }
            }
            $("#publish-btn").attr("disabled", false);
            $('[name="blogId"]').val(res.data);
            layer.msg(res.msg);
        },
        error: function (res) {
            layer.closeAll();
            $("#publish-btn").attr("disabled", false);
            layer.msg(res.code + "error，也许您的网络有问题");
        }
    });
};

function updateBlog() {
    data = {
        title: $('[name="title"]').val(),
        content: $("#content").val(),
        typeId: $('[name="type.id"]').val(),
        tagIds: $('[name="tagIds"]').val(),
        firstPicture: $('[name="firstPicture"]').val(),
        description: $('[name="description"]').val(),
        published: $('[name="published"]').val(),
        flag: $('[name="flag"]').val(),
        shareStatement: $('[name="shareStatement"]').prop('checked'),
        recommend: $('[name="recommend"]').prop('checked'),
        appreciation: $('[name="appreciation"]').prop('checked'),
        commentable: $('[name="commentable"]').prop('checked'),
    };
    //更新文章
    $.ajax({
        url: "/admin/blogs/" + $('[name="blogId"]').val(),
        type: "PUT",
        data: JSON.stringify(data),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        async: false,
        success: function (res) {
            layer.closeAll();
            if (res.code === 203) {
                if ($('[name="published"]').val() == "true") {
                    layer.msg(res.msg, {time: 1000}, function () {
                        window.location.replace("/back/blogs.html");
                    });
                }
            }
            layer.msg("保存成功");
            $("#publish-btn").attr("disabled", false);
        },
        error: function (res) {
            $("#publish-btn").attr("disabled", false);
            layer.closeAll();
            layer.msg(res.code + "error，也许您的网络有问题");
        }
    });
};