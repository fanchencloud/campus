// 在页面加载完成执行函数
$(function () {
    console.log("页面加载完成自动执行！");
    $.get(baseRequestUrl + "/getModifyShopInfo?shopId=" + getUrlParam("shopId"), function (response) {
        console.log("成功接收到来自服务器数据");
        if (response.status === 200) {
            var shopCategory = response.data.shopCategory;
            var areaList = response.data.areaList;
            var shop = response.data.shop;
            // 店铺名称
            $("#shopName").val(shop.shopName);
            // 完善区域下拉列表
            var areaHtml = '';
            areaList.map(function (item, index) {
                areaHtml += '<option value="' + item.areaId + '">' + item.areaName + '</option>';
            });
            // 填充店铺类型数据
            $("#shopCategory").attr("value", shopCategory);
            var areaSelectList = $('#area');
            areaSelectList.append(areaHtml);
            // 对于区域下拉列表选中为后台数据
            areaSelectList.val(shop.areaId);
            // 店铺地址
            $("#shopAddress").val(shop.shopAddress);
            // 店铺联系方式
            $("#phone").val(shop.phone);
            // 店铺照片
            document.getElementById("shopImage").style.backgroundImage = 'url(' + baseRequestUrl + "/getStorePhoto?shopId=" + getUrlParam("shopId") + ')';
            // 店铺简介
            $("#shopDescription").val(shop.shopDescription);
        } else {
            alert("后台数据错误！请联系管理员！");
        }
    });
});

var changePicture = function () {
    $.confirm("添加图片会替换掉原来的店铺照片，是否继续", function () {
        //点击确认后的回调函数
        $("#modifyShopImage").click();
    }, function () {
        //点击取消后的回调函数
    });
};


var pictureOnChange = function () {
    console.log("触发选择事件!");
    var files = document.getElementById("modifyShopImage").files[0];
    var imgDataUrl = getObjectURL(files);
    console.log(imgDataUrl);
    var fileInputContainer = document.getElementById("shopImage");
    fileInputContainer.style.backgroundImage = "url('" + imgDataUrl + "')";
};

var getObjectURL = function (file) {
    var url = null;
    if (window.createObjcectURL !== undefined) {
        url = window.createOjcectURL(file);
    } else if (window.URL !== undefined) {
        url = window.URL.createObjectURL(file);
    } else if (window.webkitURL !== undefined) {
        url = window.webkitURL.createObjectURL(file);
    }
    return url;
};

/**
 * 获取URL参数
 * @param paraName 参数名称
 * @returns {string} 参数值
 */
var getUrlParam = function (paraName) {
    var url = document.location.toString();
    var arrObj = url.split("?");

    if (arrObj.length > 1) {
        var arrPara = arrObj[1].split("&");
        var arr;

        for (var i = 0; i < arrPara.length; i++) {
            arr = arrPara[i].split("=");
            if (arr != null && arr[0] === paraName) {
                return arr[1];
            }
        }
        return "";
    } else {
        return "";
    }
};


/**
 * post请求提交数据
 * @param postUrl 请求地址
 * @param postData 请求数据
 * @param checkButton 检查按钮
 * @param callbackAddress 回调地址
 */
var postRequest = function (postUrl, postData, checkButton, callbackAddress) {
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
                        callbackAddress = "/index";
                    }
                    window.location.href = callbackAddress;
                    removeDisable(checkButton);
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

var baseRequestUrl = "/shop";

var submitFlag = true;

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

var modifyShopSubmit = function () {
    if (submitFlag === true) {
        // $.alert('Here goes alert text');
        console.log("进入提交修改数据函数");
        submitFlag = false;
        // 将上传按钮设置位不可用状态
        var checkButton = $("#registerShopSubmit");
        checkButton.addClass("weui-btn_disabled");
        var shop = {};
        // 检查输入
        var shopName = $("#shopName").val();
        if (validateRequired(shopName, "店铺名称不能为空")) {
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
        console.log(verifyCode);
        shop.shopName = shopName;
        shop.areaId = area;
        shop.shopAddress = shopAddress;
        shop.phone = phone;
        shop.shopDescription = $("#shopDescription").val();

        var shopImg = document.getElementById("modifyShopImage").files[0];
        var formData = new FormData();
        formData.append("shopImg", shopImg);
        formData.append("shop", JSON.stringify(shop));
        formData.append("verifyCode", verifyCode);
        console.log(shop);
        postRequest("/shop/modifyShop", formData, checkButton, "/index");
    }
};

/**
 * 点击重新请求图片验证码
 * @param img 图片验证码控件
 */
var changeCode = function (img) {
    img.src = "/Kaptcha.jpg?" + Math.floor(Math.random() * 100);
};