function getBlogs(page, typeId, tagId) {
    let url = "/blogs?page=" + page;
    if (typeId != '' && typeId != null) {
        url += "&typeId=" + typeId;
        $("[name='typeId']").val(typeId);
    } else if (tagId != '' && tagId != null) {
        url += "&tagId=" + tagId;
        $("[name='tagId']").val(tagId);
    }
    $.ajax({
        url: url,
        type: "GET",
        success: function (data) {
            layer.closeAll();
            if (data.code === 200) {
                setBlogPage(data.obj);
            }
            layer.msg(data.message);
        },
        error: function (obj) {
            layer.closeAll();
            layer.msg("error，也许您的网络有问题");
        }
    });
}

function getTags() {
    $.ajax({
        url: "/tags/blogCount",
        type: "GET",
        success: function (data) {
            layer.closeAll();
            if (data.code === 200) {
                setTags(data.obj);
            } else {
                layer.msg(data.message);
            }
        },
        error: function (obj) {
            layer.closeAll();
            layer.msg("error，也许您的网络有问题");
        }
    });
}

function getTypes() {
    $.ajax({
        url: "/types/blogCount",
        type: "GET",
        success: function (data) {
            layer.closeAll();
            if (data.code === 200) {
                setTypes(data.obj);
            } else {
                layer.msg(data.message);
            }
        },
        error: function (obj) {
            layer.closeAll();
            layer.msg("error，也许您的网络有问题");
        }
    });
}
