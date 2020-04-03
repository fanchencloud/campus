/*
店铺商品管理
*/

/**
 * 下架商品
 * @param productId 商品id
 */
const obtainedProduct = function (productId) {
    $.get("/product/modifyProductStatus?productId=" + productId, function (response) {
        closeSwiped();
        // 重新刷新数据
        loadData();
    });

};

/**
 * 修改商品
 * @param productId 商品id
 */
const modifyProduct = function (productId) {
    window.location.href = "/product/modifyProduct?productId=" + productId + "&shopId=" + getUrlParam("shopId");
};

/**
 * 预览商品
 * @param productId 商品id
 */
const previewProduct = function (productId) {
    window.location.href = "/product/productDetail?productId=" + productId + "&shopId=" + getUrlParam("shopId");
};
/**
 * 上架商品
 * @param productId 商品id
 */
const shelfProduct = function (productId) {
    $.get("/product/modifyProductStatus?productId=" + productId, function (response) {
        closeSwiped();
        // 重新刷新数据
        loadData();
    });
};

const initProductList = function (productCategoryList) {
    if (validateRequired(productCategoryList)) {
        return;
    }
    const productCategoryContainer = $('#productContainer');
    // 清空内容
    productCategoryContainer.html("");
    let productCategoryHtml = '';
    productCategoryList.map(function (item, index) {
        productCategoryHtml += '<div class="weui-cell weui-cell_swiped" id="swipe1">'
            + '<div class="weui-cell__bd" style="transform: translate3d(0px, 0px, 0px);">'
            + ' <div class="weui-cell">'
            + ' <div class="weui-cell__bd">'
            + ' <p>' + item.productName + '</p>'
            + ' </div>'
            + ' <div class="weui-cell__ft">' + item.priority + '</div>'
            + ' </div>'
            + ' </div>'
            + ' <div class="weui-cell__ft">';
        if (item.enableStatus === 1) {
            productCategoryHtml += '<a class="weui-swiped-btn weui-swiped-btn_warn delete-swipeout" href="javascript:obtainedProduct(' + item.productId + ')">下架</a>';
        } else if (item.enableStatus === 0) {
            productCategoryHtml += '<a class="weui-swiped-btn weui-swiped-btn_warn delete-swipeout" href="javascript:shelfProduct(' + item.productId + ')">上架</a>';
        }
        productCategoryHtml += '<a class="weui-swiped-btn weui-swiped-btn_primary close-swipeout" href="javascript:modifyProduct(' + item.productId + ')">编辑</a>'
            + '<a class="weui-swiped-btn weui-swiped-btn_primary close-swipeout" href="javascript:previewProduct(' + item.productId + ')">预览</a>'
            + '<a class="weui-swiped-btn weui-swiped-btn_default close-swipeout" href="javascript:closeSwiped()">关闭</a>'
            + '</div>'
            + '</div>';
    });
    productCategoryContainer.append(productCategoryHtml);
    //动态生成的DOM，或者在JS加载之后的DOM，那么这样初始化
    $('.weui-cell_swiped').swipeout();
};

const loadData = function () {
    // 加载商品信息
    $.get("/product/getProductList?shopId=" + getUrlParam("shopId"), function (response) {
        if (response.status === 200) {
            console.log(response);
            const productList = response.data;
            initProductList(productList);
        } else {
            alert("后台数据错误！请联系管理员！");
        }
    });
};

// 在页面加载完成执行函数
$(function () {
    loadData();
});

/**
 * 添加商品
 */
const addProduct = function () {
    window.location.href = "/product/addProduct?shopId=" + getUrlParam("shopId") + "&shopId=" + getUrlParam("shopId");
};

const closeSwiped = function () {
    $('.weui-cell_swiped').swipeout('close') //关闭
};