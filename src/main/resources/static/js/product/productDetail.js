// 商品详情页面的数据
let shopDetailData = {
    // 商品价格
    CommodityPrice: '',
    // 商品折扣价格
    commodityDiscountPrices: '',
    // 商品名
    productName: '',
    UserSNickname: '',
    phone: '',
    city_picker: '',
    fullAddress: '',
    remark: '',
    operating: ''
};

$(function () {
    shopDetailData.operating = false;
    console.log("页面加载完成自动执行！");
    $.get("/product/modifyInfo?id=" + getUrlParam("productId"), function (response) {
        console.log("成功接收到来自服务器数据");
        if (response.status === 200) {
            console.log(response);
            const product = response.data.product;
            $("#productTitle").html(product.productName);
            shopDetailData.productName = product.productName;
            // 填充商品缩略图
            $("#productImage").attr('src', "/product/getProductThumbnail?productId=" + getUrlParam("productId"));
            // 原价
            const originalPrice = product.normalPrice;
            if (!validateParameterRequired(originalPrice)) {
                $("#originalPrice").html("价格：" + originalPrice);
                shopDetailData.CommodityPrice = originalPrice;
            }
            // 折扣价
            const discountPrice = product.promotionPrice;
            if (!validateParameterRequired(discountPrice)) {
                $("#originalPrice").html("折扣价：" + discountPrice);
                shopDetailData.commodityDiscountPrices = discountPrice;
            }
            // 商品介绍
            $("#productDescription").html(product.productDesc);
            // 商品详情图
            const productImageList = response.data.list;


            productImageList.map(function (productImagePath) {
                const picturePath = '/productImage/getProductImage?id=' + productImagePath;
                let productImageHtml = '   <div class="card demo-card-header-pic">\n' +
                    '        <div align="bottom" class="card-header color-white no-border no-padding">\n' +
                    '            <img class=\'card-cover\' alt="" src="' + picturePath + '">\n' +
                    '        </div>\n' +
                    '    </div>';
                $("#ProductImageContainer").append(productImageHtml);
            });
        } else {
            $.alert("后台数据错误！请联系管理员！");
        }
    });
    // 是否显示预定按钮
    $.get("/user/checkReservation", function (response) {
        console.log("成功接收到来自服务器数据");
        let target = document.getElementById("createOrderButton");
        if (response.status === 200) {
            // 显示创建订单按钮
            target.style.display = "block";
        } else {
            // $.alert("后台数据错误！请联系管理员！");
            target.style.display = "none";
        }
    });
});
const disableButton = function (target) {
    shopDetailData.operating = true;
    target.addClass("weui-btn_loading");
    target.text("正在加载...");
};

const enableButton = function (target) {
    shopDetailData.operating = false;
    target.removeClass("weui-btn_loading");
    target.text("确定");
};

/**
 * 提交订单进入Preview 页面
 */
const submitOrderForm = function () {
    if (shopDetailData.operating) {
        return;
    }
    let axis = $("#showTooltips");
    disableButton(axis);
    // 校验数据
    shopDetailData.UserSNickname = $("#UserSNickname").val();
    if (validateParameterRequired(shopDetailData.UserSNickname)) {
        $.toptip("订单用户名不能为空", 'error');
        enableButton(axis);
        return false;
    }
    shopDetailData.phone = $("#phone").val();
    if (validateParameterRequired(shopDetailData.phone)) {
        $.toptip("订单用户手机号不能为空", 'error');
        enableButton(axis);
        return false;
    }
    shopDetailData.city_picker = $("#city-picker").val();
    if (validateParameterRequired(shopDetailData.city_picker)) {
        $.toptip("订单用户地址不能为空", 'error');
        enableButton(axis);
        return false;
    }
    shopDetailData.fullAddress = $("#fullAddress").val();
    if (validateParameterRequired(shopDetailData.fullAddress)) {
        $.toptip("订单用户详细地址不能为空", 'error');
        enableButton(axis);
        return false;
    }
    shopDetailData.remark = $("#remark").val();
    // 校验通过
    enableButton(axis);
    // 关闭当前显示的Popup
    $.closePopup();
    $("#PreviewPage").popup();
    if (!validateParameterRequired(shopDetailData.commodityDiscountPrices)) {
        $("#paymentAmount").html("¥" + shopDetailData.commodityDiscountPrices);
    } else {
        $("#paymentAmount").html("¥" + shopDetailData.CommodityPrice);
    }
    $("#commodity").html(shopDetailData.productName);
    $("#orderUsername").html(shopDetailData.UserSNickname);
    $("#contactInformation").html(shopDetailData.phone);
    $("#orderAddress").html(shopDetailData.city_picker + " " + shopDetailData.fullAddress);
    $("#orderRemark").html(shopDetailData.remark);
};