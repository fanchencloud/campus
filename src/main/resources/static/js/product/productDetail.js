$(function () {
    console.log("页面加载完成自动执行！");
    $.get("/product/modifyInfo?id=" + getUrlParam("productId"), function (response) {
        console.log("成功接收到来自服务器数据");
        if (response.status === 200) {
            console.log(response);
            const product = response.data.product;
            $("#productTitle").html(product.productName);
            // 填充商品缩略图
            $("#productImage").attr('src', "/product/getProductThumbnail?productId=" + getUrlParam("productId"));
            // 原价
            const originalPrice = product.normalPrice;
            if (!validateParameterRequired(originalPrice)) {
                $("#originalPrice").html("价格：" + originalPrice);
            }
            // 折扣价
            const discountPrice = product.promotionPrice;
            if (!validateParameterRequired(discountPrice)) {
                $("#originalPrice").html("折扣价：" + discountPrice);
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
            $.alert("后台数据错误！请联系管理员！");
            target.style.display = "none";
        }
    });
});