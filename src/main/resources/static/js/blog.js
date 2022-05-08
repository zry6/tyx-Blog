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
        success: function (res) {
            layer.closeAll();
            if (res.code === 200) {
                setBlogPage(res.data);
            }
            layer.msg(res.msg);
        },
        error: function (res) {
            layer.closeAll();
            layer.msg(res.code + "error，也许您的网络有问题");
        }
    });
}

function getTags() {
    $.ajax({
        url: "/tags/blogCount",
        type: "GET",
        success: function (res) {
            layer.closeAll();
            if (res.code === 200) {
                setTags(res.data);
            } else {
                layer.msg(res.msg);
            }
        },
        error: function (res) {
            layer.closeAll();
            layer.msg(res.code + "error，也许您的网络有问题");
        }
    });
}

function getTypes() {
    $.ajax({
        url: "/types/blogCount",
        type: "GET",
        success: function (res) {
            layer.closeAll();
            if (res.code === 200) {
                setTypes(res.data);
            } else {
                layer.msg(res.code + "error，也许您的网络有问题");
            }
        },
        error: function (res) {
            layer.closeAll();
            layer.msg(res.code + "error，也许您的网络有问题");
        }
    });
}
