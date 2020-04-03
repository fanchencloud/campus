// 基本用户头像路径
const userImagePath = "/user";

// 服务器上存储的用户头像
let serverUserImage = [];
// 本地存储的用户头像
let localUserImage = [];
// 本地保存的用户头像的地址
let localUserImagePath = [];

// 用户ID
let userId;

/**
 * 页面加载完成进行初始化的函数
 */
$(function () {
    console.log("页面加载完成自动执行！");
    // 获取用户信息并填充数据
    $.get("/user/getUserInfo", function (response) {
        console.log("成功接收到来自服务器数据");
        if (response.status === 200) {
            const user = response.data;
            // 用户昵称
            $("#nickname").val(user.name);
            // 性别 将性别下拉列表选中为后台数据
            $("#sex").val(user.gender);
            // 电话
            $("#phone").val(user.phone);
            // 邮箱
            $("#mailbox").val(user.email);
            userId = user.id;

            // 填充用户头像
            let test = user.headPortrait;
            if (!validateParameterRequired(test)) {
                const thumbnail = '/user/getUserImage';
                const productThumbnailHtml = '<li class="weui-uploader__file" style="background-image:url('
                    + thumbnail + ')" onclick="showUserImage()"></li>';
                serverUserImage.push(getUrlParam("productId"));
                $("#uploadProductThumbnail").append(productThumbnailHtml);
            }
            // 修改显示
            $("#userImageSize").html(serverUserImage.length + "/1");
        } else {
            $.alert("后台数据错误！请联系管理员！");
        }
    });
});

/**
 * 展示用户头像图片
 * 1、如果在服务器上有数据，就展示服务器上的数据
 * 2、如果数据在本地，就展示本地的数据
 */
const showUserImage = function () {
    let picturePath, deletePath, dataSource = 0;
    // 判断数据来源是本地还是数据服务器上的 1、表示的是数据服务器上的 2、表示的是本地的数据
    if (serverUserImage.length > 0) {
        dataSource = 1;
        // 拼接图片地址
        picturePath = "/user/getUserImage";
        deletePath = "/person/deleteHeadImage";
    } else if (localUserImagePath.length !== 0) {
        dataSource = 2;
        picturePath = localUserImagePath[0];
    }
    // 展示图片
    showImage(picturePath);
    let flag = true;
    // 为删除按钮添加事件
    $("#galleryHref").bind("click", function () {
        if (flag === true) {
            flag = false;
            // 删除图片数据
            if (dataSource === 1) {
                // 如果图片数据来自数据服务器，请求服务器删除数据
                $.get(deletePath, function (responseData) {
                    if (responseData.status === 200) {
                        // 删除本地数据
                        serverProductImage = [];
                        // 显示删除结果
                        $.toptip(responseData.msg, 'success');
                    }
                });
            } else if (dataSource === 2) {
                // 数据来自本地数据、删除数据集合中的数据
                localUserImage = [];
                for (let i = 0; i < localUserImagePath.length; i++) {
                    URL.revokeObjectURL(localUserImagePath[i]);
                }
                // 删除本地数据
                localUserImagePath = [];
                // 显示删除结果
                $.toptip("删除成功", 'success');
            }
            // 重新刷新布局
            changeUserImage();
            // 隐藏画廊
            $("#gallery").fadeOut("slow");
            flag = true;
        }
    });
};

/**
 * 以画廊展示图片
 * @param imageUrl 展示图片的路径
 */
const showImage = function (imageUrl) {
    console.log("展示图片的地址：URL = " + imageUrl);
    // 设置图片
    const fileInputContainer = document.getElementById("gallerySpan");
    fileInputContainer.style.backgroundImage = "url(" + imageUrl + ")";
    // 添加span的点击事件
    fileInputContainer.onclick = function () {
        $("#gallery").fadeOut("slow");
    };
    $("#gallery").fadeIn("slow");
};

/**
 * 刷新用户头像图片
 */
const changeUserImage = function () {
    console.log("触发刷新用户头像图片的事件!");
    removeAllChild("uploadUserImage");
    let $uploadUserImage = $("#uploadUserImage");
    let userImageLength = 0;
    console.log("用户头像：服务器数据：" + serverUserImage.length + " 本地数据：" + localUserImage.length);
    // 图片数据来自服务器
    if (serverUserImage.length > 0) {
        // 填充用户头像
        const thumbnail = userImagePath + '/getUserImage';
        const userImageHtml = '<li class="weui-uploader__file" style="background-image:url('
            + thumbnail + ')" onclick="showUserImage()"></li>';
        $uploadUserImage.append(userImageHtml);
        userImageLength = 1;
    } else if (localUserImagePath.length > 0) {
        // 取本地保存的数据进行展示
        let imageLi = '<li class="weui-uploader__file" style="background-image:url('
            + localUserImagePath[0] + ')" onclick="showUserImage()"></li>';
        $uploadUserImage.append(imageLi);
        userImageLength = 1;
    }
    // 修改显示
    $("#userImageSize").html(userImageLength + "/1");
};

/**
 * 响应选择用户头像的函数
 */
const uploadUserImageInput = function () {
    // 清空原数据
    localUserImage = [];
    for (let i = 0; i < localUserImagePath.length; i++) {
        URL.revokeObjectURL(localUserImagePath[i]);
    }
    localUserImagePath = [];
    const list = this.files;
    for (let i = 0; i < list.length; i++) {
        console.log(list[i].name);
        localUserImage.push(list[i]);
        let objectURL = getObjectURL(list[i]);
        localUserImagePath.push(objectURL);
    }
    changeUserImage();
    console.log("file number = " + list.length);
};

/**
 * 上传用户头像
 */
const uploadUserImage = function () {
    // 检查上传的图片数量
    let uploadUserImageList = document.getElementById("uploadUserImage");
    // 获取缩略图的数量
    // var size = document.getElementById("uploadProductThumbnail").childElementCount;
    let size = uploadUserImageList.childElementCount;
    console.log(size);
    if (size >= 1) {
        $.toast("超过上传数量", "forbidden");
        return false;
    } else {
        // 清空文件框的数据
        let test = document.getElementById('uploadUserImageInput');
        //虽然test的value不能设为有字符的值，但是可以设置为空值
        test.value = '';
        // 上传照片
        $("#uploadUserImageInput").click();
    }
};

let submitFlag = true;
/**
 * 提交用户信息修改数据
 */
const submitFunction = function () {
    if (submitFlag === true) {
        console.log("进入提交用户信息修改函数");
        submitFlag = false;
        // 将上传按钮设置位不可用状态
        const checkButton = $("#registerShopSubmit");
        checkButton.addClass("weui-btn_disabled");

        // 检查上传参数
        // 用户昵称
        const nickname = $("#nickname").val();
        if (validateRequired(nickname, "用户昵称不能为空")) {
            removeDisable(checkButton);
            return false;
        }

        // 性别
        const sex = $("#sex").val();
        if (validateRequired(sex, "性别不能为空")) {
            removeDisable(checkButton);
            return false;
        }

        // 电话
        const phone = $("#phone").val();
        if (validateRequired(phone, "电话不能为空")) {
            removeDisable(checkButton);
            return false;
        }
        // 检查上传的用户头像图片的数量
        let uploadUserImageList = document.getElementById("uploadUserImage");
        // 获取用户头像的数量
        let size = uploadUserImageList.childElementCount;
        // 验证缩略图
        // if (size < 1) {
        //     $.toptip('请至少添加一张用户头像！', 'error');
        //     removeDisable(checkButton);
        //     return false;
        // }
        // 邮箱
        const mailbox = $("#mailbox").val();
        if (validateRequired(mailbox, "邮箱不能为空")) {
            removeDisable(checkButton);
            return false;
        }

        // 验证码
        const verifyCode = $("#verifyCode").val();
        if (validateRequired(verifyCode, "验证码不能为空")) {
            removeDisable(checkButton);
            return false;
        }

        let user = {};
        // 用户昵称
        user.name = nickname;
        // 性别
        user.gender = sex;
        // 电话
        user.phone = phone;
        // 邮箱
        user.email = mailbox;
        // 用户id
        user.id = userId;
        let formData = new FormData();
        // 处理缩略图
        if (localUserImage.length > 0) {
            const file = localUserImage[0];
            // const str = file.name;
            // const pos = str.lastIndexOf(".");
            // const suffixName = str.substring(pos, str.length);
            // // 创建新文件对象
            // const newThumbnail = new File([file], "thumbnail" + suffixName, {type: "image/*"});
            formData.append("thumbnail", file);
        }
        console.log("user = " + JSON.stringify(user));

        formData.append("user", JSON.stringify(user));
        formData.append("verifyCode", verifyCode);
        $.ajax({
            type: "POST",
            url: "/person/modifyUser",
            dataType: "json",
            cache: false,         //不设置缓存
            processData: false,  // 不处理数据
            contentType: false, // 不设置内容类型
            data: formData,
            success: function (response) {
                console.log(response);
                if (response.status === 200) {
                    console.log("修改成功！");
                    $.toast(response.msg);
                    setTimeout(" window.location.href = \"/home\";", 2000);
                } else {
                    console.log("请求失败");
                    $.toast(response.msg, "forbidden");
                }
            },
            error: function (xhr) {
                console.log("错误提示： " + xhr + " ---- " + xhr.status + " " + xhr.statusText);
                checkButtonFunction();
            },
            //请求完成后回调函数 (请求成功或失败之后均调用)。参数： XMLHttpRequest 对象和一个描述成功请求类型的字符串
            complete: function (XMLHttpRequest, textStatus) {
                console.log("函数调用完成，将按钮设置为可用状态");
                // 请求完成，将按钮重置为可用
                checkButtonFunction();
            }
        });
    }
};


/**
 * 提交用户修改信息完成的回调函数
 */
const callBackFunction = function () {
    console.log("修改用户信息完成");
};

const checkButtonFunction = function () {
    // 将上传按钮设置位不可用状态
    const checkButton = $("#registerShopSubmit");
    removeDisable(checkButton);
};