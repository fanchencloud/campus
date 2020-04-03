const myOperating = function () {
    $.actions({
        actions: [{
            text: "修改我的信息",
            onClick: function () {
                //do something
            }
        }, {
            text: "查看我的商铺",
            onClick: function () {
                //do something
            }
        }]
    });
};

/**
 * 登录成功加载用户数据
 */
const initUserMessage = function () {
    $.get("/user/getUserInfo", function (response) {
        if (response.status === 200) {
            let user = response.data;
            console.log(user);
            $("#userInfoUsername").html(user.name);
            $("#userInfoGender").html(user.gender);
            $("#userInfoPhone").html(user.phone);
            $("#userInfoEmail").html(user.email);
            let $userInfoImage = $("#userInfoImage");
            let src = $userInfoImage[0].src;
            console.log("原始路径：" + src);
            $userInfoImage.attr('src', "/user/getUserImage");
            src = $userInfoImage[0].src;
            console.log("修改路径：" + src);
        }
    });
};