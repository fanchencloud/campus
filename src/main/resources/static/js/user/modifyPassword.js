let isFlag = false;
/**
 * 修改登录密码
 */
const modifyPassword = function () {
    if (isFlag) {
        return false;
    }
    isFlag = true;
    document.getElementById("modifyButton").classList.add("weui-btn_disabled");
    const originalPassword = $("#originalPassword").val();
    const newPassword = $("#newPassword").val();
    const confirmPassword = $("#confirmPassword").val();
    if (validateParameterRequired(originalPassword)) {
        $.toptip("原始密码不能为空", 'error');
        isFlag = false;
        document.getElementById("modifyButton").classList.remove("weui-btn_disabled");
        return false;
    }
    if (validateParameterRequired(newPassword)) {
        $.toptip("新密码不能为空", 'error');
        isFlag = false;
        document.getElementById("modifyButton").classList.remove("weui-btn_disabled");
        return false;
    }
    if (validateParameterRequired(confirmPassword)) {
        $.toptip("确认密码不能为空", 'error');
        isFlag = false;
        document.getElementById("modifyButton").classList.remove("weui-btn_disabled");
        return false;
    }
    if (newPassword !== confirmPassword) {
        $.toptip("两次密码不一致", 'error');
        isFlag = false;
        document.getElementById("modifyButton").classList.remove("weui-btn_disabled");
        return false;
    }
    // 开始提交数据
    $.ajax({
        type: "POST",
        url: "/user/modifyPassword",
        dataType: "json",
        data: {
            originalPassword: originalPassword,
            newPassword: newPassword
        },
        success: function (response) {
            if (response.status === 200) {
                $.toast(response.msg);
                setTimeout(" window.location.href = \"/home\";", 2000);
                // 跳转回首页

            } else {
                $.toast(response.msg, "forbidden");
            }
            isFlag = false;
            document.getElementById("modifyButton").classList.remove("weui-btn_disabled");
        }
    });
};