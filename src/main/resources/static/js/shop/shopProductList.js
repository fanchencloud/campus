/*
店铺商品类别管理
*/

const baseRequestUrl = "/shop";

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
 * 在页面加载完成执行函数
 */
$(function () {
    console.log("页面加载完成自动执行！");
    // 获取店铺列表
    $.get(baseRequestUrl + "/getShopProductCategoryList?shopId=" + getUrlParam("shopId"), function (response) {
        console.log("成功接收到来自服务器数据");
        if (response.status === 200) {
            console.log(response);
            const productCategoryList = response.data.productCategoryList;
            initProductCategoryList(productCategoryList);
        } else {
            alert("后台数据错误！请联系管理员！");
        }
    });
});

const initProductCategoryList = function (productCategoryList) {
    if (validateRequired(productCategoryList)) {
        return;
    }
    const productCategoryContainer = $('#productCategoryContainer');
    // 清空内容
    productCategoryContainer.html("");
    let productCategoryHtml = '';
    productCategoryList.map(function (item, index) {
        productCategoryHtml += '<div class="weui-cell weui-cell_swiped" id="swipe1">'
            + '<div class="weui-cell__bd" style="transform: translate3d(0px, 0px, 0px);">'
            + ' <div class="weui-cell">'
            + ' <div class="weui-cell__bd">'
            + ' <p>' + item.productCategoryName + '</p>'
            + ' </div>'
            + ' <div class="weui-cell__ft">' + item.priority + '</div>'
            + ' </div>'
            + ' </div>'
            + ' <div class="weui-cell__ft">'
            + '<a class="weui-swiped-btn weui-swiped-btn_warn delete-swipeout" href="javascript:deleteProductCategory(' + item.productCategoryId + ')">删除</a>'
            + '<a class="weui-swiped-btn weui-swiped-btn_primary close-swipeout" href="javascript:modifyProductCategory(' + item.productCategoryId + ')">修改</a>'
            + '<a class="weui-swiped-btn weui-swiped-btn_default close-swipeout" href="javascript:closeSwiped()">关闭</a>'
            + '</div>'
            + '</div>';
    });
    productCategoryContainer.append(productCategoryHtml);
    //动态生成的DOM，或者在JS加载之后的DOM，那么这样初始化
    $('.weui-cell_swiped').swipeout();
};

const deleteProductCategory = function (productCategoryId) {
    $.get(baseRequestUrl + "/deleteProductCategory?productCategoryId=" + productCategoryId, function (result) {
        addProductCategoryCallback(result);
    });
};

const modifyProductCategory = function (productCategoryId) {
    let productCategory;
    $.get("/productCategory/getProductCategory?productCategoryId=" + productCategoryId, function (result) {
        productCategory = result.data;
        const modelHtml =
            '<div class="weui-cells__title">请修改商品分类名称和权重以及描述</div>'
            + '<div class="weui-cells weui-cells_form">'
            + '<div class="weui-cell">'
            + ' <div class="weui-cell__hd"><label class="weui-label">商品类别</label></div>'
            + ' <div class="weui-cell__bd">'
            + '   <input class="weui-input" id="ProductCategoryName" value="' + productCategory.productCategoryName + '" type="text" placeholder="请输入商品类别">'
            + '    </div>'
            + '     </div>'
            + '      <div class="weui-cell">'
            + '       <div class="weui-cell__hd"><label class="weui-label">权重</label></div>'
            + '   <div class="weui-cell__bd">'
            + '     <input class="weui-input" type="number" id="ProductCategoryPriority" value="' + productCategory.priority + '" pattern="[0-9]*" placeholder="请输入权重">'
            + '      </div>'
            + '      </div>'
            + '     <div class="weui-cell">'
            + '    <div class="weui-cell__hd"><label class="weui-label">类别说明</label></div>'
            + '    <div class="weui-cell__bd">'
            + '   <input class="weui-input" type="text" id="ProductCategoryDescription" value="' + productCategory.productCategoryDescription + '" placeholder="请输入商品分类说明">'
            + '     </div>'
            + '     </div>'
            + '    </div>';
        $.modal({
            title: "修改商品类别",
            text: modelHtml,
            buttons: [
                {
                    text: "取消", className: "default", onClick: function () {
                        $.closeModal();
                    }
                },
                {
                    text: "提交", onClick: function () {
                        const name = $("#ProductCategoryName").val();
                        const priority = $("#ProductCategoryPriority").val();
                        const ProductCategoryDescription = $("#ProductCategoryDescription").val();
                        const ProductCategory = {};
                        ProductCategory.productCategoryName = name;
                        ProductCategory.shopId = getUrlParam("shopId");
                        ProductCategory.productCategoryId = productCategory.productCategoryId;
                        if (!validateRequired(ProductCategoryDescription)) {
                            ProductCategory.productCategoryDescription = ProductCategoryDescription;
                        }
                        if (!validateRequired(priority)) {
                            ProductCategory.priority = priority;
                        }
                        postRequest(baseRequestUrl + "/modifyProductCategory", ProductCategory, null, addProductCategoryCallback);
                    }
                }
            ],
            autoClose: false
        });
    });

};

/**
 * 添加店铺商品类别
 */
const addProductCategory = function () {
    const modelHtml =
        '<div class="weui-cells__title">请输入商品分类名称和权重以及描述（选填）</div>'
        + '<div class="weui-cells weui-cells_form">'
        + '<div class="weui-cell">'
        + ' <div class="weui-cell__hd"><label class="weui-label">商品类别</label></div>'
        + ' <div class="weui-cell__bd">'
        + '   <input class="weui-input" id="ProductCategoryName" type="text" placeholder="请输入商品类别">'
        + '    </div>'
        + '     </div>'
        + '      <div class="weui-cell">'
        + '       <div class="weui-cell__hd"><label class="weui-label">权重</label></div>'
        + '   <div class="weui-cell__bd">'
        + '     <input class="weui-input" type="number" id="ProductCategoryPriority" pattern="[0-9]*" placeholder="请输入权重">'
        + '      </div>'
        + '      </div>'
        + '     <div class="weui-cell">'
        + '    <div class="weui-cell__hd"><label class="weui-label">类别说明</label></div>'
        + '    <div class="weui-cell__bd">'
        + '   <input class="weui-input" type="text" id="ProductCategoryDescription" placeholder="请输入商品分类说明">'
        + '     </div>'
        + '     </div>'
        + '    </div>';
    $.modal({
        title: "添加商品类别",
        text: modelHtml,
        buttons: [
            {
                text: "取消", className: "default", onClick: function () {
                    $.closeModal();
                }
            },
            {
                text: "提交", onClick: function () {
                    const name = $("#ProductCategoryName").val();
                    const priority = $("#ProductCategoryPriority").val();
                    const ProductCategoryDescription = $("#ProductCategoryDescription").val();
                    const ProductCategory = {};
                    ProductCategory.productCategoryName = name;
                    ProductCategory.shopId = getUrlParam("shopId");
                    if (!validateRequired(ProductCategoryDescription)) {
                        ProductCategory.productCategoryDescription = ProductCategoryDescription;
                    }
                    if (!validateRequired(priority)) {
                        ProductCategory.priority = priority;
                    }
                    postRequest(baseRequestUrl + "/addProductCategory", ProductCategory, null, addProductCategoryCallback);
                }
            }
        ],
        autoClose: false
    });
};

const addProductCategoryCallback = function (response) {
    console.log("response:" + response);
    if (response.status === 200) {
        $.closeModal();
        $.toptip('操作成功', 'success');
        // 重新请求刷新数据
        // 获取店铺列表
        $.get(baseRequestUrl + "/getShopProductCategoryList?shopId=" + getUrlParam("shopId"), function (response) {
            console.log("成功接收到来自服务器数据");
            if (response.status === 200) {
                console.log(response);
                const productCategoryList = response.data.productCategoryList;
                initProductCategoryList(productCategoryList);
            } else {
                alert("后台数据错误！请联系管理员！");
            }
        });
    }
};

/**
 * 检查数据的有效性
 * @param value 数据
 * @param message 提示消息
 * @returns {boolean} 返回
 */
const validateRequired = function (value, message) {

    if (value === undefined //未初始化的判断
        || value == null //object类型的判断
        || (typeof (value) == 'string' && (value === '' || value.match(/\s+/)))
        || (typeof (value) == 'number' && isNaN(value))) {
        $.alert(message);
        return true;
    }
    return false;
};

/**
 * post请求提交数据
 * @param postUrl 请求地址
 * @param postData 请求数据
 * @param checkButton 检查按钮
 * @param callbackFunction 回调函数
 */
const postRequest = function (postUrl, postData, checkButton, callbackFunction) {
    // 封装数据
    $.ajax({
        type: "POST",
        url: postUrl,
        data: postData,
        success: function (response) {
            callbackFunction(response);
        },
        error: function (xhr) {
            console.log("错误提示： " + xhr + " ---- " + xhr.status + " " + xhr.statusText);
            removeDisable(checkButton);
        },
        //请求完成后回调函数 (请求成功或失败之后均调用)。参数： XMLHttpRequest 对象和一个描述成功请求类型的字符串
        complete: function (XMLHttpRequest, textStatus) {
            console.log("函数调用完成");
        }
    });
};

const closeSwiped = function () {
    $('.weui-cell_swiped').swipeout('close') //关闭
};