// 三个查询条件
let shopCategoryId;
// 商铺名字
let shopName;
// 区域id
let areaId;
// 记录行数
let rowIndex = 0;
// 分页数据大小
const pageSize = 7;
// 数据是否已经全部加载
let isOver = false;
// 单行展示商铺类别的数量
const btn_number = 4;
// 父类标签编号
let parentId;

// 在页面加载完成执行函数
$(function () {
    const $searchBar = $('#searchBar'),
        $searchText = $('#searchText'),
        $searchInput = $('#searchInput'),
        $searchClear = $('#searchClear'),
        $searchCancel = $('#searchCancel');

    $searchText.on('click', function () {
        $searchBar.addClass('weui-search-bar_focusing');
        $searchInput.focus();
    });
    $searchInput
    // 失去焦点事件
        .on('blur', function () {
            // if (!this.value.length) cancelSearch();
            // 判断是否需要查询数据
            changeShopName($('#searchInput').val())
        })
        .on('input', function () {
        })
    ;
    $searchClear.on('click', function () {
        console.log("清除输入~！");
        $searchInput.focus();
    });
    $searchCancel.on('click', function () {
        $searchInput.blur();
        $searchBar.removeClass('weui-search-bar_focusing');
        console.log("取消搜索！" + $('#searchInput').val());
    });
    // 填充区域列表
    $.get("/area/listArea", function (response) {
        $.toptip('操作成功', 'success');
        const areaList = response.rows;
        let areaListHtml = '';
        areaListHtml += '<option value="">' + "所有区域" + '</option>';
        areaList.map(function (item) {
            areaListHtml += '<option value="' + item.areaId + '">' + item.areaName + '</option>';
        });
        $("#areaList").append(areaListHtml);
    });
    // 填充店铺类别列表
    let memoryShopCategoryId = sessionStorage.getItem('shopCategoryId');
    if (memoryShopCategoryId === "undefined") {
        memoryShopCategoryId = '';
    }
    console.log("memoryShopCategoryId = " + memoryShopCategoryId);
    if (validateParameterRequired(memoryShopCategoryId)) {
        // 没有查询条件，显示所有一级列表
        $.get("/shopCategory/getShopCategoryList", function (response) {
            console.log(response);
            initShopCategoryList(response.data);
        });
    } else {
        parentId = memoryShopCategoryId;
        $.get("/shopCategory/getShopCategoryList", {id: memoryShopCategoryId}, function (response) {
            console.log(response);
            initShopCategoryList(response.data);
        });
    }
    // 初次填充数据
    searchShopList(true);
    parentId = '';
});

/**
 * 初始化商铺类别列表
 * @param shopCategoryList 商铺类别列表
 */
const initShopCategoryList = function (shopCategoryList) {
    if (shopCategoryList != null) {
        removeAllChild("ShopCategoryListContainer");
        const count = Object.keys(shopCategoryList).length + 1;
        console.log("数据大小！" + count);
        let size = Math.ceil(count / btn_number) * btn_number;
        let number = 1;
        let ShopCategoryListHtml = '<div class="weui-flex">' +
            '<div class="weui-flex__item"><a href="javascript:void(0);" onclick="changeShopCategory(null,this)" class="weui-btn weui-btn_mini weui-btn_primary btn-margin">所有</a></div>';
        number++;
        shopCategoryList.map(function (item) {
            if (number % btn_number === 1) {
                ShopCategoryListHtml += '<div class="weui-flex">';
            }
            ShopCategoryListHtml += '<div class="weui-flex__item"><a href="javascript:void(0);"'
                + ' onclick="changeShopCategory(' + item.shopCategoryId + ',this)" class="weui-btn weui-btn_default weui-btn_mini btn-margin">' + item.shopCategoryName + '</a></div>';
            if (number % btn_number === 0) {
                ShopCategoryListHtml += '</div>';
            }
            number++;
        });
        for (let i = number; i <= size; i++) {
            if (number % btn_number === 1) {
                ShopCategoryListHtml += '<div class="weui-flex">';
            }
            ShopCategoryListHtml += '<div class="weui-flex__item"></div>';
            if (number % btn_number === 0) {
                ShopCategoryListHtml += '</div>';
            }
            number++;
        }
        console.log(number);
        $("#ShopCategoryListContainer").append(ShopCategoryListHtml);
    }
};

/**
 * 修改搜索条件 商铺类型id
 * @param id id
 * @param obj dom节点
 */
const changeShopCategory = function (id, obj) {

    if (shopCategoryId !== id) {
        shopCategoryId = id;
        isOver = false;
        searchShopList(true);
    }

    traversingNodes();
    obj.classList.remove("weui-btn_default");
    obj.classList.add("weui-btn_primary");
    // removeClass(obj, "weui-btn_default");
    // addClass(obj, "weui-btn_primary");
};

/**
 * 修改搜索条件 商铺名称
 * @param keyword 搜索关键字
 */
const changeShopName = function (keyword) {
    if (shopName === keyword) {
        return false;
    } else {
        shopName = keyword;
        isOver = false;
        searchShopList(true);
    }
};

$("#areaList").change(function () {
    const opt = $("#areaList").val();
    console.log(opt);
    changeShopArea(opt);
});

/**
 * 修改搜索条件 商铺区域
 * @param area 区域
 */
const changeShopArea = function (area) {
    if (areaId === area) {
        return false;
    } else {
        areaId = area;
        isOver = false;
        searchShopList(true);
    }
};

//判空
function strIsEmpty(str) {
    return str === "" || str == null || typeof (str) == "undefined";

}

/**
 * 查询商铺列表 查询条件：商铺名模糊搜索、商铺类别、商铺区域
 * @param reloadPage 是否重新加载页面
 */
const searchShopList = function (reloadPage) {
        if (isOver) {
            // 数据已经全部加载完成
            return;
        }
        // 显示加载动画
        document.getElementById("loadAnimation").style.visibility = "visible";
        // 隐藏数据已经全部加载
        document.getElementById("no-data").style.visibility = "hidden";
        let $shopListContainer = $("#shopListContainer");
        // 需要重新加载全部数据
        if (reloadPage) {
            // 清空商铺列表
            $shopListContainer.empty();
            // 重置记录行数
            rowIndex = 0;
        }
        // 对搜索条件进行数据搜索
        let searchCondition = {};
        if (!strIsEmpty(shopName)) {
            searchCondition.shopName = shopName;
        }
        console.log("shopCategoryId === " + shopCategoryId);
        if (!validateParameterRequired(shopCategoryId)) {
            searchCondition.id = shopCategoryId;
        } else {
            // 检查父类标签
            // 填充店铺类别列表
            let memoryShopCategoryId = sessionStorage.getItem('shopCategoryId');
            console.log("父类标签id = " + memoryShopCategoryId);
            if (memoryShopCategoryId !== "undefined") {
                // 有父类标签
                searchCondition.parentId = memoryShopCategoryId;
            }
        }
        if (areaId !== null) {
            if (!validateParameterRequired(areaId)) {
                searchCondition.areaId = areaId;
            }
        }
        searchCondition.rowIndex = rowIndex;
        searchCondition.pageSize = pageSize;
        $.ajax({
            type: 'get',
            url: '/front/getShopList',
            data: searchCondition,
            dataType: 'json',
            success: function (response) {
                const shopList = response.data;
                if (validateParameterRequired(shopList)) {
                    isOver = true;
                    let shopListHtml = '<div class="weui-loadmore weui-loadmore_line">' +
                        '  <span class="weui-loadmore__tips">暂无数据</span>' +
                        '</div>';
                    $shopListContainer.append(shopListHtml);
                } else {
                    const count = Object.keys(shopList).length;
                    if (count < pageSize) {
                        console.log("数据全部加载完成，本地数据量 ： " + count);
                        isOver = true;
                        // 显示数据已经全部加载
                        document.getElementById("no-data").style.visibility = "visible";
                    }
                    // 填充数据
                    shopList.map(function (shop) {
                        let shopListHtml = '';
                        shopListHtml += '<div class="blackBorder">' +
                            '        <h4 class="weui-media-box__title shopName">' + shop.shopName + '</h4>' +
                            '        <div class="weui-media-box weui-media-box_appmsg">' +
                            '            <div class="weui-media-box__hd">' +
                            '                <img class="weui-media-box__thumb" src="/shop/getStorePhoto?shopId=' + shop.shopId + '" alt="">' +
                            '            </div>' +
                            '            <div class="weui-media-box__bd">' +
                            '                <p class="weui-media-box__desc">' + shop.shopDescription + '</p>' +
                            '            </div>' +
                            '        </div>' +
                            '        <div class="weui-panel__ft">' +
                            '            <div class="shopUpdateTime">' + myFormatDateTime(shop.lastEditTime) + ' 更新</div>' +
                            '            <div class="enterShop">' +
                            '                <a href="javascript:void(0);" onclick="enterShop(' + shop.shopId + ')">' +
                            '                    点击查看' +
                            '                </a>' +
                            '            </div>' +
                            '            <div style="clear:both;"></div>' +
                            '        </div>' +
                            '    </div>';
                        $shopListContainer.append(shopListHtml);
                    });
                }
                // 隐藏加载动画
                document.getElementById("loadAnimation").style.visibility = "hidden";
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                // alert("请求对象XMLHttpRequest: " + XMLHttpRequest);
                // alert("错误类型textStatus: " + textStatus);
                // alert("异常对象errorThrown: " + errorThrown);
                // 隐藏加载动画
                document.getElementById("loadAnimation").style.visibility = "hidden";
                $.toptip('数据请求失败！', 'error');
            }
        });
    }
;

/**
 * 遍历商铺类别节点
 */
const traversingNodes = function () {
    const divList = document.getElementById("ShopCategoryListContainer").children;
    for (let i = 0; i < divList.length; i++) {
        if (divList[i].hasChildNodes()) {
            let temp = divList[i].children;
            for (let j = 0; j < temp.length; j++) {
                let aa = temp[j].children;
                for (let k = 0; k < aa.length; k++) {
                    // if (hasClass(aa[k], "weui-btn_primary")) {
                    removeClass(aa[k], "weui-btn_primary");
                    addClass(aa[k], "weui-btn_default");
                    // }
                }
            }
        }
    }
};

/**
 * 判断是否含有样式
 * @param elements dom节点
 * @param cName 样式名
 * @returns {boolean} 是否含有
 */
function hasClass(elements, cName) {
    return !!elements.className.match(new RegExp("(\\s|^)" + cName + "(\\s|$)")); // ( \\s|^ ) 判断前面是否有空格 （\\s | $ ）判断后面是否有空格 两个感叹号为转换为布尔值 以方便做判断
}

/**
 * 添加样式
 * @param elements dom节点
 * @param cName 样式名
 */
function addClass(elements, cName) {
    if (!hasClass(elements, cName)) {
        elements.className += " " + cName;
    }
}

/**
 * 删除样式
 * @param elements dom节点
 * @param cName 样式名称
 */
function removeClass(elements, cName) {
    if (hasClass(elements, cName)) {
        elements.className = elements.className.replace(new RegExp("(\\s|^)" + cName + "(\\s|$)"), " "); // replace方法是替换
    }
}

/**
 * 将时间戳转换成本地时间
 * @param timestampValue 时间戳
 * @returns {string} 本地时间
 */
const conversionTime = function (timestampValue) {
    const newDate = new Date();
    newDate.setTime(timestampValue);
    return newDate.toLocaleString()
};

// 数字补0操作
const addZero = function (num) {
    return num < 10 ? '0' + num : num;
};

const myFormatDateTime = function (date) {
    let time = new Date(Date.parse(date));
    time.setTime(time.setHours(time.getHours() + 8));

    let Y = time.getFullYear() + '-';
    let M = addZero(time.getMonth() + 1) + '-';
    let D = addZero(time.getDate()) + ' ';
    let h = addZero(time.getHours()) + ':';
    let m = addZero(time.getMinutes()) + ':';
    let s = addZero(time.getSeconds());
    return Y + M + D + h + m + s;
};

/**
 * 进入商铺
 * @param shopId 商铺id
 */
const enterShop = function (shopId) {
    window.location.href = "/frontShop/shop?shopId=" + shopId;
};

// 下拉继续加载数据
// 调用JS初始化滚动加载插件
$(document.body).infinite();
$(document.body).infinite(100);
//状态标记
let loading = false;
$(document.body).infinite().on("infinite", function () {
    console.log("进入下拉刷新");
    if (loading || isOver) return;
    loading = true;
    rowIndex += pageSize;
    setTimeout(function () {
        searchShopList(false);
        loading = false;
    }, 1500);   //模拟延迟
});

$(window).keyup(function (event) {
    if (event.keyCode === 13) {
        changeShopName($('#searchInput').val());
    }
});