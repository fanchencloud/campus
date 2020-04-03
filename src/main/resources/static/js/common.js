/**
 * 点击重新请求图片验证码
 * @param img 图片验证码控件
 */
const changeCode = function (img) {
    img.src = "/Kaptcha.jpg?" + Math.floor(Math.random() * 100);
};

/**
 * 获取URL参数
 * @param paraName 参数名称
 * @returns {string} 参数值
 */
const getUrlParam = function (paraName) {
    const url = document.location.toString();
    const arrObj = url.split("?");

    if (arrObj.length > 1) {
        const arrPara = arrObj[1].split("&");
        let arr;
        for (let i = 0; i < arrPara.length; i++) {
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
 * 检查数据的有效性
 * @param value 数据
 * @param message 提示消息
 * @returns {boolean} 参数为空返回真，否则反复假
 */
const validateRequired = function (value, message) {
    if (validateParameterRequired(value)) {
        $.alert(message);
        return true;
    }
    return false;
};

/**
 * 检查数据的有效性
 * @param value 数据
 * @returns {boolean} 参数为空返回真，否则反复假
 */
const validateParameterRequired = function (value) {
    return !!(value === undefined //未初始化的判断
        || value == null //object类型的判断
        || (typeof (value) == 'string' && (value === '' || value.match(/\s+/)))
        || (typeof (value) == 'number' && isNaN(value)));
};

/**
 * post请求提交数据
 * @param postUrl 请求地址
 * @param postData 请求数据
 * @param checkButtonFunction 检查按钮函数
 * @param callbackFunction 回调函数
 */
const postRequest = function (postUrl, postData, checkButtonFunction, callbackFunction) {
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
            console.log(response);
            if (response.status === 200) {
                console.log("添加成功！");
                $.toast(response.msg);
                // 数据修改成功，重新跳转回查询界面
                if (callbackFunction != null) {
                    callbackFunction();
                }
                checkButtonFunction();
            } else {
                console.log("请求失败");
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
};

function removeAllChild(elementId) {
    let div = document.getElementById(elementId);
    //当div下还存在子节点时 循环继续
    while (div.hasChildNodes()) {
        div.removeChild(div.firstChild);
    }
}

const getObjectURL = function (file) {
    let url = null;
    if (file !== undefined) {
        if (window.createObjcectURL !== undefined) {
            url = window.createOjcectURL(file);
        } else if (window.URL !== undefined) {
            url = window.URL.createObjectURL(file);
        } else if (window.webkitURL !== undefined) {
            url = window.webkitURL.createObjectURL(file);
        }
    }
    return url;
};

Array.prototype.indexOf = function (val) {
    for (let i = 0; i < this.length; i++) {
        if (this[i] === val) return i;
    }
    return -1;
};

const generateUUID = function () {
    let d = new Date().getTime();
    return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        const r = (d + Math.random() * 16) % 16 | 0;
        d = Math.floor(d / 16);
        return (c === 'x' ? r : (r & 0x7 | 0x8)).toString(16);
    });
};

const removeDisable = function (button) {
    button.removeClass("weui-btn_disabled");
    submitFlag = true;
};

Array.prototype.remove = function (val) {
    let index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};

// 第二种方式：给日期对象原型添加一个方法
Date.prototype.format = function (format) {
    const date = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (const k in date) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length === 1 ?
                date[k] : ("00" + date[k]).substr(("" + date[k]).length));
        }
    }
    return format;
};