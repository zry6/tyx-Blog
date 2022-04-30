
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
        success: function (data) {
            layer.closeAll();
            if (data.code === 201) {
                if ($('[name="published"]').val() == "true") {
                    layer.msg(data.message, {time: 2000}, function () {
                        window.location.replace("/admin/blog-s.html");
                    });
                }
            }
            $("#publish-btn").attr("disabled", false);
            $('[name="blogId"]').val(data.obj);
            layer.msg(data.message);
        },
        error: function (obj) {
            layer.closeAll();
            $("#publish-btn").attr("disabled", false);
            layer.msg("error，也许您的网络有问题");
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
        success: function (data) {
            layer.closeAll();
            if (data.code === 203) {
                if ($('[name="published"]').val() == "true") {
                    layer.msg(data.message, {time: 2000}, function () {
                        window.location.replace("/admin/blog-s.html");
                    });
                }
            }
            layer.msg("保存成功");
            $("#publish-btn").attr("disabled", false);
        },
        error: function (obj) {
            $("#publish-btn").attr("disabled", false);
            layer.closeAll();
            layer.msg("error，也许您的网络有问题");
        }
    });
};