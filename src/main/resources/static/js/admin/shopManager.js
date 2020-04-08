let operating = false;

/**
 * 为按钮添加禁用样式并设置一个变量为true
 * @param btn 按钮
 */
const addDisable = function (btn) {
    operating = true;
    // 为登录按钮添加禁用样式
    document.getElementById(btn).classList.add("disabled");
};

/**
 * 为按钮移除禁用样式并设置一个变量为false
 * @param btn 按钮
 */
const removeLoginDisable = function (btn) {
    operating = false;
    // 为登录按钮移除禁用样式
    document.getElementById(btn).classList.remove("disabled");
};
/**
 * 通过店铺的审核
 * @param shopId 店铺id
 */
const examinationPassed = function (shopId) {
    if (operating) {
        return;
    }
    // 禁用注册按钮
    addDisable("examinationPassedButton");
    console.log("执行通过店铺审核，店铺id = " + shopId);
    $.ajax({
        type: "post",
        url: "/admin/examinationPassed",
        dataType: "json",
        data: {
            shopId: shopId
        },
        success: function (response) {
            console.log(response);
            if (response.status === 200) {
                console.log("修改成功！");
                // 修改该商铺的可用状态
                const requestUrl = "/admin/getShopEnableStatus?shopId=" + shopId;
                $.ajax({
                    url: requestUrl,    //请求的url地址
                    dataType: "json",   //返回格式为json
                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                    data: {"shopId": shopId},    //参数值
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
                            const shop = data.data.shop;
                            let $1 = $("#shopStatus_" + shop.shopId);
                            $1.empty();
                            if (shop.enableStatus === 1) {
                                const t = '<span>审核通过</span>';
                                $1.append(t);
                            } else if (shop.enableStatus === -1) {
                                const t = '<span>审核未通过</span>';
                                $1.append(t);
                            } else {
                                const t = '<span>审核中    </span>';
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
            removeLoginDisable("examinationPassedButton");
        }
    });
};

/**
 * 禁用店铺的可用状态
 * @param shopId 店铺id
 */
const verifyDisable = function (shopId) {
    console.log("执行禁用店铺审核，店铺id = " + shopId);
    if (operating) {
        return;
    }
    // 禁用注册按钮
    addDisable("verifyDisableButton");
    $.ajax({
        type: "post",
        url: "/admin/verifyDisable",
        dataType: "json",
        data: {
            shopId: shopId
        },
        success: function (response) {
            console.log(response);
            if (response.status === 200) {
                console.log("修改成功！");
                // 修改该商铺的可用状态
                const requestUrl = "/admin/getShopEnableStatus?shopId=" + shopId;
                $.ajax({
                    url: requestUrl,    //请求的url地址
                    dataType: "json",   //返回格式为json
                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                    data: {"shopId": shopId},    //参数值
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
                            const shop = data.data.shop;
                            let $1 = $("#shopStatus_" + shop.shopId);
                            $1.empty();
                            if (shop.enableStatus === 1) {
                                const t = '<span>审核通过</span>';
                                $1.append(t);
                            } else if (shop.enableStatus === -1) {
                                const t = '<span>审核未通过</span>';
                                $1.append(t);
                            } else {
                                const t = '<span>审核中    </span>';
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
            removeLoginDisable("verifyDisableButton");
        }
    });
};