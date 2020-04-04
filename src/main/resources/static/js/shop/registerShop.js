// 在页面加载完成执行函数
$(function () {
    console.log("注册店铺页面加载完成自动执行！");
    $.get(baseRequestUrl + "/getRegisterShopInfo", function (response) {
        console.log("成功接收到来自服务器的数据");
        if (response.status === 200) {
            const shopCategoryList = response.data.shopCategoryList;
            const areaList = response.data.areaList;
            console.log(shopCategoryList);
            console.log(areaList);
            let areaHtml = '';
            areaList.map(function (item, index) {
                areaHtml += '<option value="' + item.areaId + '">' + item.areaName + '</option>';
            });
            var shopCategoryHtml = '';
            shopCategoryList.map(function (value) {
                shopCategoryHtml += '<option value="' + value.shopCategoryId + '">' + value.shopCategoryName + '</option>';
            });
            $('#area').append(areaHtml);
            $('#shopCategory').append(shopCategoryHtml);
        } else {
            alert("后台数据错误！请联系管理员！");
        }
    });
});

/**
 * post请求提交数据
 * @param postUrl 请求地址
 * @param postData 请求数据
 * @param checkButton 检查按钮
 * @param callbackAddress 回调地址
 */
const postRequest = function (postUrl, postData, checkButton, callbackAddress) {
    // 封装数据
    $.ajax({
        type: "POST",
        url: postUrl,
        dataType: "json",
        cache: false,         //不设置缓存
        processData: false,  // 不处理数据
        contentType: false, // 不设置内容类型
        data: postData,
        success: function (response) {
            $.alert(response.msg, function () {
                if (response.status === 200) {
                    // 数据修改成功，重新跳转回查询界面
                    if (validateRequired(callbackAddress)) {
                        callbackAddress = "/shop/listShop";
                    }
                    window.location.href = callbackAddress;
                    removeDisable(checkButton);
                } else {
                    console.log("请求失败！");
                    $.toast(response.msg, "forbidden");
                }
            });
        },
        error: function (xhr) {
            console.log("错误提示： " + xhr + " ---- " + xhr.status + " " + xhr.statusText);
            removeDisable(checkButton);
        },
        //请求完成后回调函数 (请求成功或失败之后均调用)。参数： XMLHttpRequest 对象和一个描述成功请求类型的字符串
        complete: function (XMLHttpRequest, textStatus) {
            console.log("函数调用完成，将按钮设置为可用状态");
            // 请求完成，将按钮重置为可用
            removeDisable(checkButton);
        }
    });
};

const baseRequestUrl = "/shop";

let submitFlag = true;

/**
 * 检查数据的有效性
 * @param value 数据
 * @param message 提示消息
 * @returns {boolean} 返回
 */
var validateRequired = function (value, message) {

    if (value === undefined //未初始化的判断
        || value == null //object类型的判断
        || (typeof (value) == 'string' && (value === '' || value.match(/\s+/)))
        || (typeof (value) == 'number' && isNaN(value))) {
        $.alert(message);
        return true;
    }
    return false;
};

var removeDisable = function (button) {
    button.removeClass("weui-btn_disabled");
    submitFlag = true;
};

var addShopSubmit = function () {
    if (submitFlag === true) {
        // $.alert('Here goes alert text');
        console.log("进入提交函数");
        submitFlag = false;
        // 将上传按钮设置位不可用状态
        var checkButton = $("#registerShopSubmit");
        checkButton.addClass("weui-btn_disabled");
        var shop = {};
        // 检查输入
        var shopName = $("#shopName").val();
        if (validateRequired(shopName, "店铺名字不能为空")) {
            removeDisable(checkButton);
            return false;
        }
        var shopCategory = $("#shopCategory").val();
        if (validateRequired(shopCategory, "店铺类别不能为空")) {
            removeDisable(checkButton);
            return false;
        }

        var area = $("#area").val();
        if (validateRequired(area, "店铺区域不能为空")) {
            removeDisable(checkButton);
            return false;
        }

        var shopAddress = $("#shopAddress").val();
        if (validateRequired(shopAddress, "店铺地址不能为空")) {
            removeDisable(checkButton);
            return false;
        }

        var phone = $("#phone").val();
        if (validateRequired(phone, "店铺联系方式不能为空")) {
            removeDisable(checkButton);
            return false;
        }

        var verifyCode = $("#verifyCode").val();
        if (validateRequired(verifyCode, "验证码不能为空")) {
            removeDisable(checkButton);
            return false;
        }
        shop.shopName = shopName;
        shop.shopCategoryId = shopCategory;
        shop.areaId = area;
        shop.shopAddress = shopAddress;
        shop.phone = phone;
        shop.shopDescription = $("#shopDescription").val();

        var shopImg = document.getElementById("shopImage").files[0];
        var formData = new FormData();
        formData.append("shopImg", shopImg);
        formData.append("shopMessage", JSON.stringify(shop));
        formData.append("verificationCode", verifyCode);
        console.log(shop);
        postRequest("/shop/registerShop", formData, checkButton, "/shop/listShop");
    }
};

/**
 * 点击重新请求图片验证码
 * @param img 图片验证码控件
 */
var changeCode = function (img) {
    img.src = "/Kaptcha.jpg?" + Math.floor(Math.random() * 100);
};