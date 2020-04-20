const addArea = function () {
    console.log("点击添加区域！");
    let addAreaContainer = document.getElementById("addAreaContainer");
    let clickText = document.getElementById("addAreaButton");
    if (addAreaContainer.style.display === "block") {
        addAreaContainer.style.display = "none";
        clickText.innerText = "添加区域";
    } else {
        addAreaContainer.style.display = "block";
        clickText.innerText = '取消添加';
    }
};

const checkIsNullOrEmpty = function (value) {
    //正则表达式用于判斷字符串是否全部由空格或换行符组成
    const reg = /^\s*$/;
    //返回值为true表示不是空字符串
    return (value != null && value !== undefined && !reg.test(value));
};
/**
 * 检查数据的有效性
 * @param value 数据
 * @returns {boolean} 参数为空返回真，否则反复假
 */
const validateParameterRequired = function (value) {
    // return !!(value === undefined //未初始化的判断
    //     || value == null //object类型的判断
    //     || (typeof (value) == 'string' && (value === '' || value.match(/\s+/)))
    //     || (typeof (value) == 'number' && isNaN(value)));
    return !checkIsNullOrEmpty(value);
};

const submitAddArea = function () {
    let addAreaName = $("#addAreaName").val();
    let errorMessage = $("#errorMessage");
    if (validateParameterRequired(addAreaName)) {
        errorMessage.text("区域名不能为空！");
        return false;
    }
    let addAreaDescription = $("#addAreaDescription");
    if (validateParameterRequired(addAreaDescription)) {
        errorMessage.text("区域描述不能为空！");
        return false;
    }
    let addRegionalWeight = $("#addRegionalWeight");
    if (validateParameterRequired(addRegionalWeight)) {
        errorMessage.text("区域权重不能为空！");
        return false;
    }

    $.ajax({
        type: "post",
        url: "/area/addAreaAdmin",
        dataType: "json",
        data: {
            addAreaName: addAreaName,
            addAreaDescription: addAreaDescription,
            addRegionalWeight: addRegionalWeight
        },
        success: function (rtn) {
            console.log(rtn);
            if (rtn.status === 200) {
                errorMessage.empty();
                const t = '<span>' + rtn.msg + '</span>';
                errorMessage.append(t);
            } else {
                errorMessage.empty();
                const t = '<span>' + rtn.msg + '</span>';
                errorMessage.append(t);
            }
        },
        error: function (xhr) {
            console.log("错误提示： " + xhr + " ---- " + xhr.status + " " + xhr.statusText);
        },
        //请求完成后回调函数 (请求成功或失败之后均调用)。参数： XMLHttpRequest 对象和一个描述成功请求类型的字符串
        complete: function (XMLHttpRequest, textStatus) {
            console.log("函数调用完成，将按钮设置为可用状态");
            // 请求完成，将按钮重置为可用
            window.location.href = "/area/areaManagerPage";
        }
    });
};