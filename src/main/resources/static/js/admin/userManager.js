/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2020年4月9日
 * Time: 00点49分
 * Description: 管理员账户控制
 *
 * @author chen
 */

// 操作限制
let operating = false;

/**
 * 为按钮添加禁用样式并设置一个变量为true
 * @param btn 按钮的id
 */
const addDisable = function (btn) {
    operating = true;
    // 为登录按钮添加禁用样式
    document.getElementById(btn).classList.add("disabled");
};

/**
 * 为按钮移除禁用样式并设置一个变量为false
 * @param btn 按钮id
 */
const removeLoginDisable = function (btn) {
    operating = false;
    // 为登录按钮移除禁用样式
    document.getElementById(btn).classList.remove("disabled");
};
/**
 * 通过该人员的账号审核
 * @param userId 账号id
 */
const examinationUserPassed = function (userId) {
    if (operating) {
        return;
    }
    // 禁用注册按钮
    addDisable("examinationUserPassedButton");
    console.log("执行通过人员账号审核，账号id = " + userId);
    $.ajax({
        type: "post",
        url: "/admin/examinationAccountPassed",
        dataType: "json",
        data: {
            userId: userId
        },
        success: function (response) {
            console.log(response);
            if (response.status === 200) {
                console.log("修改成功！");
                // 修改该商铺的可用状态
                const requestUrl = "/admin/getUserEnableStatus?userId=" + userId;
                $.ajax({
                    url: requestUrl,    //请求的url地址
                    dataType: "json",   //返回格式为json
                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                    data: {"userId": userId},    //参数值
                    type: "GET",   //请求方式
                    beforeSend: function (request) {
                        //请求前的处理
                        request.setRequestHeader("Content-type", "application/json");
                    },
                    success: function (data) {
                        //请求成功时处理
                        console.log("收到服务器数据：");
                        console.log(data);
                        if (data.status === 200) {
                            const user = data.data.user;
                            let $1 = $("#userStatus_" + user.id);
                            $1.empty();
                            if (user.enableStatus === 1) {
                                const t = '<span>正常使用</span>';
                                $1.append(t);
                            } else if (user.enableStatus === 0) {
                                const t = '<span>禁止使用</span>';
                                $1.append(t);
                            }
                        }
                    },
                    complete: function () {
                        //请求完成的处理
                    },
                    error: function () {
                        //请求出错处理
                    }
                });
            } else {
                console.log("请求失败");
            }
        },
        error: function (xhr) {
            console.log("错误提示： " + xhr + " ---- " + xhr.status + " " + xhr.statusText);
            // checkButtonFunction();
        },
        //请求完成后回调函数 (请求成功或失败之后均调用)。参数： XMLHttpRequest 对象和一个描述成功请求类型的字符串
        complete: function (XMLHttpRequest, textStatus) {
            console.log("函数调用完成，将按钮设置为可用状态");
            // 请求完成，将按钮重置为可用
            removeLoginDisable("examinationUserPassedButtons");
        }
    });
};

/**
 * 禁用用户账号的可用状态
 * @param userId 账号id
 */
const verifyUserDisable = function (userId) {
    console.log("执行禁用用户账号，用户id = " + userId);
    if (operating) {
        return;
    }
    // 禁用注册按钮
    addDisable("verifyUserDisableButton");
    $.ajax({
        type: "post",
        url: "/admin/verifyAccountDisable",
        dataType: "json",
        data: {
            userId: userId
        },
        success: function (response) {
            console.log(response);
            if (response.status === 200) {
                console.log("修改成功！");
                // 修改该商铺的可用状态
                const requestUrl = "/admin/getUserEnableStatus?userId=" + userId;
                $.ajax({
                    url: requestUrl,    //请求的url地址
                    dataType: "json",   //返回格式为json
                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                    data: {"userId": userId},    //参数值
                    type: "GET",   //请求方式
                    beforeSend: function (request) {
                        //请求前的处理
                        request.setRequestHeader("Content-type", "application/json");
                    },
                    success: function (data) {
                        //请求成功时处理
                        console.log("收到服务器数据：");
                        console.log(data);
                        if (data.status === 200) {
                            const user = data.data.user;
                            let $1 = $("#userStatus_" + user.id);
                            $1.empty();
                            if (user.enableStatus === 1) {
                                const t = '<span>正常使用</span>';
                                $1.append(t);
                            } else if (user.enableStatus === 0) {
                                const t = '<span>禁止使用</span>';
                                $1.append(t);
                            }
                        }
                    },
                    complete: function () {
                        //请求完成的处理
                    },
                    error: function () {
                        //请求出错处理
                    }
                });
            } else {
                console.log("请求失败");
            }
        },
        error: function (xhr) {
            console.log("错误提示： " + xhr + " ---- " + xhr.status + " " + xhr.statusText);
            // checkButtonFunction();
        },
        //请求完成后回调函数 (请求成功或失败之后均调用)。参数： XMLHttpRequest 对象和一个描述成功请求类型的字符串
        complete: function (XMLHttpRequest, textStatus) {
            console.log("函数调用完成，将按钮设置为可用状态");
            // 请求完成，将按钮重置为可用
            removeLoginDisable("verifyUserDisableButton");
        }
    });
};