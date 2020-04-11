// 在页面加载完成执行函数
$(function () {
    console.log("页面加载完成自动执行！");
    $.get("/productCategory/getShopProductCategoryList?shopId=" + getUrlParam("shopId"), function (response) {
        console.log("成功接收到来自服务器数据");
        if (response.status === 200) {
            var productCategory = response.data;
            // 完善商品类别下拉列表
            var productCategoryHtml = '';
            productCategory.map(function (item, index) {
                productCategoryHtml += '<option value="' + item.productCategoryId + '">' + item.productCategoryName + '</option>';
            });
            $("#productCategory").append(productCategoryHtml);
        } else {
            alert("后台数据错误！请联系管理员！");
        }
    });
});

// 保存商品缩略图的数据
let productThumbnail = [];
// 保存商品详情的数据
let productDetailsPicture = [];
let productThumbnailPath = [];
let productDetailsPicturePath = [];

/**
 * 上传商品缩略图
 */
const uploadProductThumbnail = function () {
    // 检查上传的图片数量
    let uploadProductThumbnailList = document.getElementById("uploadProductThumbnail");
    // 获取缩略图的数量
    // var size = document.getElementById("uploadProductThumbnail").childElementCount;
    let size = uploadProductThumbnailList.childElementCount;
    console.log(size);
    if (size === 1) {
        $.confirm("当前已上传缩略图，再次上传会覆盖，是否上传", function () {
            //点击确认后的回调函数
            console.log("上传！");
            // 清空文件框的数量
            const test = document.getElementById('uploadProductThumbnailInput');
            //虽然test的value不能设为有字符的值，但是可以设置为空值
            test.value = '';
            removeAllChild("uploadProductThumbnail");
            $("#productThumbnailSize").html("1/1");
            $("#uploadProductThumbnailInput").click();
        }, function () {
            //点击取消后的回调函数
            console.log("取消上传！");
        });
    } else if (size > 1) {
        $.toast("超过上传数量", "forbidden");
        return false;
    } else {
        // 清空文件框的数据
        const test = document.getElementById('uploadProductThumbnailInput');
        //虽然test的value不能设为有字符的值，但是可以设置为空值
        test.value = '';
        // 上传照片
        $("#uploadProductThumbnailInput").click();
    }
};

/**
 * 上传商品详情图
 */
const uploadProductDetailsPicture = function () {
    // 检查上传的详情图片数量
    let uploadProductThumbnailList = document.getElementById("uploadProductDetailsPictureList");
    // 获取详情图片的数量
    let size = uploadProductThumbnailList.childElementCount;
    console.log(size);
    if (size < 5) {
        // 允许上传
        $("#uploadProductDetailsPictureInput").click();
    } else {
        // 数量过多
        $.toast("详情图片超过限制", "forbidden");
    }
};

/**
 * 刷新商品详情图片
 */
const changeUploadProductDetailsPicture = function () {
    console.log("触发改变商品详情图片事件!");
    // 先清空原始网页数据
    removeAllChild("uploadProductDetailsPictureList");
    productDetailsPicturePath = [];
    // 重新填充数据
    for (let i = 0; i < productDetailsPicture.length; i++) {
        const imgDataUrl = getObjectURL(productDetailsPicture[i]);
        let fileInputContainer = $("#uploadProductDetailsPictureList");
        productDetailsPicturePath.push(imgDataUrl);
        let imageLi = '<li class="weui-uploader__file" style="background-image:url('
            + imgDataUrl + ')" onclick="showProductDetailsPictureImage(' + i + ')"></li>';
        fileInputContainer.append(imageLi);
    }
    // 修改显示
    $("#ProductDetailsPictureSize").html(productDetailsPicture.length + "/5");
};

/**
 * 刷新商品缩略图
 */
const changeProductThumbnail = function () {
    console.log("触发选择事件!");
    const files = productThumbnail[0];
    removeAllChild("uploadProductThumbnail");
    if (files !== undefined && files !== null) {
        const imgDataUrl = getObjectURL(files);
        let fileInputContainer = $("#uploadProductThumbnail");
        productThumbnailPath = [];
        productThumbnailPath.push(imgDataUrl);
        let imageLi = '<li class="weui-uploader__file" style="background-image:url('
            + imgDataUrl + ')" onclick="showProductThumbnailImage(0)"></li>';
        fileInputContainer.append(imageLi);

    }
    // 修改显示
    $("#productThumbnailSize").html(productThumbnail.length + "/1");
};

/**
 * 响应选择缩略图的函数
 */
const uploadProductThumbnailInput = function () {
    // 清空原数据
    productThumbnail = [];
    const list = this.files;
    for (let i = 0; i < list.length; i++) {
        console.log(list[i].name);
        productThumbnail.push(list[i]);
    }
    changeProductThumbnail();
    console.log("file number = " + list.length);
};

/**
 * 响应选择商品详情图片的函数
 */
const uploadProductDetailsPictureInput = function () {
    let size = productDetailsPicture.length;
    const list = this.files;
    size = size + list.length;
    if (size > 5) {
        $.toast("详情图片超过限制", "forbidden");
    } else {
        for (let i = 0; i < list.length; i++) {
            var str = list[i].name;
            var pos = str.lastIndexOf(".");
            var lastname = str.substring(pos, str.length);
            productDetailsPicture.push(list[i]);
            console.log(str + "   -- 后缀名为：" + lastname);
        }
        changeUploadProductDetailsPicture();
        console.log("file number = " + list.length);
    }
};

/**
 * 展示商品详情图片
 * @param imageIndex 图片的序号
 */
const showProductDetailsPictureImage = function (imageIndex) {
    // 展示图片
    showImage(productDetailsPicturePath[imageIndex]);
    let flag = true;
    // 为删除按钮添加事件
    $("#galleryHref").bind("click", function () {
        if (flag === true) {
            flag = false;
            // 删除该图片
            productDetailsPicture.splice(imageIndex, 1);
            window.URL.revokeObjectURL(productDetailsPicturePath[imageIndex]);
            // 重新刷新布局
            changeUploadProductDetailsPicture();
            // 隐藏画廊
            $("#gallery").fadeOut("slow");
            flag = true;
        }
    });
};

/**
 * 展示商品缩略图图片
 * @param imageIndex 图片的序号
 */
const showProductThumbnailImage = function (imageIndex) {
    // 展示图片
    showImage(productThumbnailPath[imageIndex]);
    // 为删除按钮添加事件
    $("#galleryHref").bind("click", function () {
        // 删除该图片
        productThumbnail.splice(imageIndex, 1);
        window.URL.revokeObjectURL(productThumbnailPath[imageIndex]);
        // 重新刷新布局
        changeProductThumbnail();
        // 隐藏画廊
        $("#gallery").fadeOut("slow");
    });
};

/**
 * 以画廊展示图片
 * @param imageUrl 展示图片的路径
 */
const showImage = function (imageUrl) {
    // 设置图片
    const fileInputContainer = document.getElementById("gallerySpan");
    fileInputContainer.style.backgroundImage = "url(" + imageUrl + ")";
    // 添加span的点击事件
    fileInputContainer.onclick = function () {
        $("#gallery").fadeOut("slow");
    };
    $("#gallery").fadeIn("slow");
};

let submitFlag = true;

/**
 * 提交商品数据
 */
const submitFunction = function () {
    if (submitFlag === true) {
        console.log("进入提交修改数据函数");
        submitFlag = false;
        // 将上传按钮设置位不可用状态
        var checkButton = $("#registerShopSubmit");
        checkButton.addClass("weui-btn_disabled");
        var product = {};
        // 检查上传参数
        // 商品名称
        var productName = $("#productName").val();
        if (validateRequired(productName, "商品名称不能为空")) {
            removeDisable(checkButton);
            return false;
        }

        // 商品分类
        var productCategory = $("#productCategory").val();
        if (validateRequired(productCategory, "商品类别不能为空")) {
            removeDisable(checkButton);
            return false;
        }

        // 优先级别
        var productPriority = $("#productPriority").val();
        if (validateParameterRequired(productPriority)) {
            productPriority = 0;
        }

        // 原价
        var originalPrice = $("#originalPrice").val();
        if (validateRequired(originalPrice, "原价不能为空")) {
            removeDisable(checkButton);
            return false;
        }

        // 验证缩略图
        if (productThumbnail.length < 1) {
            $.toptip('请至少添加一张商品缩略图', 'error');
            removeDisable(checkButton);
            return false;
        }

        // 验证商品详情图
        if (productDetailsPicture.length < 1) {
            $.toptip('请至少添加一张商品详情图', 'error');
            removeDisable(checkButton);
            return false;
        }

        // 验证码
        var verifyCode = $("#verifyCode").val();
        if (validateRequired(verifyCode, "验证码不能为空")) {
            removeDisable(checkButton);
            return false;
        }

        // 商品名称
        product.productName = productName;
        // 商品类别
        product.productCategoryId = productCategory;
        // 商品描述
        product.productDesc = $("#productDescription").val();
        // 原价
        product.normalPrice = originalPrice;
        // 折扣价
        var promotionPrice = $("#currentPrice").val();
        if (validateParameterRequired(promotionPrice)) {
            console.log("没填折扣价！");
        } else {
            product.promotionPrice = promotionPrice;
        }
        // 权重
        product.priority = productPriority;
        // 商店id
        product.shopId = getUrlParam("shopId");

        // 处理缩略图
        const file = productThumbnail[0];
        const str = file.name;
        const pos = str.lastIndexOf(".");
        const suffixName = str.substring(pos, str.length);
        // 创建新文件对象
        const newThumbnail = new File([file], "thumbnail" + suffixName, {type: "image/*"});

        let formData = new FormData();
        for (let i = 0; i < productDetailsPicture.length; i++) {
            formData.append("productDetailsPicture", productDetailsPicture[i]);
        }
        formData.append("thumbnail", newThumbnail);
        formData.append("product", JSON.stringify(product));
        formData.append("verifyCode", verifyCode);
        postRequest("/product/addProduct", formData, checkButtonFunction, callBackFunction);
    }
};

const callBackFunction = function () {
    window.location.href = "/shop/managerProduct?shopId=" + shopId;
};

const checkButtonFunction = function () {
    // 将上传按钮设置位不可用状态
    var checkButton = $("#registerShopSubmit");
    removeDisable(checkButton);
};