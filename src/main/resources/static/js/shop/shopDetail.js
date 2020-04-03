// 两个查询条件
// 商品类别
let productCategoryId;
// 商品名字
let productName;
// 记录行数
let rowIndex = 0;
// 分页数据大小
const pageSize = 7;
// 数据是否已经全部加载
let isOver = false;
// 单行展示商品类别的数量
const btn_number = 4;
// 商铺id
let shopId;

/**
 * 查看商品详情
 * @param productId 商品id
 */
const enterProduct = function (productId) {
    window.location.href = "/product/productDetail?productId=" + productId + "&shopId=" + shopId;
};

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
            // 判断是否需要查询数据
            changeProductName($('#searchInput').val())
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
        changeProductName($('#searchInput').val())
    });
    // 填充店铺的商品类别
    shopId = getUrlParam("shopId");
    if (validateParameterRequired(shopId)) {
        $.toast("请求异常", "forbidden");
        window.location.href = "/home";
    } else {
        $.get("/productCategory/getShopProductCategoryList", {shopId: shopId}, function (response) {
            initProductCategoryList(response.data);
        });
    }
    $.get("/frontShop/getShop", {shopId: shopId}, function (response) {
        const obj = document.getElementById("shopImage");
        obj.setAttribute("src", "/shop/getStorePhoto?shopId=" + response.data.shopId);//把图片修改为目标路径
        const shopLastEditTime = document.getElementById("shopLastEditTime");
        shopLastEditTime.innerText = conversionTime(response.data.lastEditTime);
        const shopDescription = document.getElementById("shopDescription");
        shopDescription.innerText = response.data.shopDescription;
        const shopAddress = document.getElementById("shopAddress");
        shopAddress.innerText = response.data.shopAddress;
        const shopPhone = document.getElementById("shopPhone");
        shopPhone.innerText = response.data.phone;
    });
    // 初次填充数据
    searchProductList(true);
});

/**
 * 初始化商铺的商品类别列表
 * @param productCategoryList 商铺商品类别数据
 */
const initProductCategoryList = function (productCategoryList) {
    if (!validateParameterRequired(productCategoryList)) {
        removeAllChild("ProductCategoryListContainer");
        const count = Object.keys(productCategoryList).length + 1;
        console.log("数据大小！" + count);
        let size = Math.ceil(count / btn_number) * btn_number;
        let number = 1;
        let ShopCategoryListHtml = '<div class="weui-flex">' +
            '<div class="weui-flex__item"><a href="javascript:void(0);" onclick="changeProductCategory(null,this)" class="weui-btn weui-btn_mini weui-btn_primary btn-margin">所有</a></div>';
        number++;
        productCategoryList.map(function (item) {
            if (number % btn_number === 1) {
                ShopCategoryListHtml += '<div class="weui-flex">';
            }
            ShopCategoryListHtml += '<div class="weui-flex__item"><a href="javascript:void(0);"'
                + ' onclick="changeProductCategory(' + item.productCategoryId + ',this)" class="weui-btn weui-btn_default weui-btn_mini btn-margin">' + item.productCategoryName + '</a></div>';
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
        $("#ProductCategoryListContainer").append(ShopCategoryListHtml);
    }
};

/**
 * 修改搜索条件 商品类型id
 * @param id id
 * @param obj dom节点
 */
const changeProductCategory = function (id, obj) {

    if (productCategoryId !== id) {
        productCategoryId = id;
        isOver = false;
        searchProductList(true);
    }

    traversingNodes();
    obj.classList.remove("weui-btn_default");
    obj.classList.add("weui-btn_primary");
    // removeClass(obj, "weui-btn_default");
    // addClass(obj, "weui-btn_primary");
};

/**
 * 修改搜索条件 商品名称
 * @param keyword 搜索关键字
 */
const changeProductName = function (keyword) {
    if (productName === keyword) {
        return false;
    } else {
        productName = keyword;
        isOver = false;
        searchProductList(true);
    }
};


//判空
function strIsEmpty(str) {
    return str === "" || str == null || typeof (str) == "undefined";

}

/**
 * 查询商品列表 查询条件：商品名模糊搜索、商品类别
 * @param reloadPage 是否重新加载页面
 */
const searchProductList = function (reloadPage) {
        if (isOver) {
            // 数据已经全部加载完成
            return;
        }
        // 显示加载动画
        document.getElementById("loadAnimation").style.visibility = "visible";
        // 隐藏数据已经全部加载
        document.getElementById("no-data").style.visibility = "hidden";
        document.getElementById("allIsLoad").style.visibility = "hidden";
        let $productListContainer = $("#productListContainer");
        // 需要重新加载全部数据
        if (reloadPage) {
            // 清空商品列表
            $productListContainer.empty();
            // 重置记录行数
            rowIndex = 0;
        }
        // 对搜索条件进行数据搜索
        let searchCondition = {};
        if (!strIsEmpty(productName)) {
            searchCondition.productName = productName;
        }
        console.log("productCategoryId === " + productCategoryId);
        if (!validateParameterRequired(productCategoryId)) {
            searchCondition.id = productCategoryId;
        }
        searchCondition.shopId = shopId;
        searchCondition.rowIndex = rowIndex;
        searchCondition.pageSize = pageSize;
        console.log("搜索条件：" + searchCondition);
        $.ajax({
            type: 'get',
            url: '/productFront/getProductList',
            data: searchCondition,
            dataType: 'json',
            success: function (response) {
                const productList = response.data;
                if (validateParameterRequired(productList)) {
                    // 显示暂时无数据
                    isOver = true;
                    document.getElementById("no-data").style.visibility = "visible";
                } else {
                    const count = Object.keys(productList).length;
                    if (count === 0) {
                        isOver = true;
                    } else if (count > 0 && count < pageSize) {
                        console.log("数据全部加载完成，本地数据量 ： " + count);
                        isOver = true;
                        // 显示数据已经全部加载，到底了
                        document.getElementById("allIsLoad").style.visibility = "visible";
                    }
                    // 填充数据
                    productList.map(function (product) {
                        const productListHtml = '<div class="blackBorder">' +
                            '        <h4 class="weui-media-box__title shopName">' + product.productName + '</h4>' +
                            '        <div class="weui-media-box weui-media-box_appmsg">' +
                            '            <div class="weui-media-box__hd">' +
                            '                <img class="weui-media-box__thumb" src="/product/getProductThumbnail?productId=' + product.productId + '" alt="">' +
                            '            </div>' +
                            '            <div class="weui-media-box__bd">' +
                            '                <p class="weui-media-box__desc">' + product.productDesc + '</p>' +
                            '            </div>' +
                            '        </div>' +
                            '        <div class="weui-panel__ft">' +
                            '            <div class="shopUpdateTime">' + conversionTime(product.lastEditTime) + ' 更新</div>' +
                            '            <div class="enterShop">' +
                            '                <a href="javascript:void(0);" onclick="enterProduct(' + product.productId + ')">' +
                            '                    点击查看' +
                            '                </a>' +
                            '            </div>' +
                            '            <div style="clear:both;"></div>' +
                            '        </div>' +
                            '    </div>';
                        $productListContainer.append(productListHtml);
                    });
                }
                // 隐藏加载动画
                document.getElementById("loadAnimation").style.visibility = "hidden";
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log("请求对象XMLHttpRequest: " + XMLHttpRequest);
                console.log("错误类型textStatus: " + textStatus);
                console.log("异常对象errorThrown: " + errorThrown);
                // 隐藏加载动画
                document.getElementById("loadAnimation").style.visibility = "hidden";
                $.toptip('数据请求失败！', 'error');
                isOver = true;
            }
        });
    }
;

/**
 * 遍历商品类别节点
 */
const traversingNodes = function () {
    const divList = document.getElementById("ProductCategoryListContainer").children;
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

// 下拉继续加载数据
// 调用JS初始化滚动加载插件
$(document.body).infinite();
$(document.body).infinite(100);
//状态标记
let loading = false;
$(document.body).infinite().on("infinite", function () {
    if (loading || isOver) return;
    console.log("isOver = " + isOver);
    loading = true;
    rowIndex += pageSize;
    setTimeout(function () {
        searchProductList(false);
        loading = false;
    }, 1500);   //模拟延迟
});

$(window).keyup(function (event) {
    if (event.keyCode === 13) {
        changeProductName($('#searchInput').val());
    }
});