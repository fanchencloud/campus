const baseProductURL = "/product";
const baseProductImageURL = "/productImage";

// server 上存在的缩略图图片
let serverThumbnail = [];
// server 上存在的商品详情图片
let serverProductImage = [];
// 本地存储的图片数据
// 保存商品缩略图的数据
let localProductThumbnail = [];
// 保存商品详情的数据
let localProductDetailsPicture = new Map();
// 本地保存的商品缩略图的地址
let localProductThumbnailPath = [];
// 本地保存的商品详情图的地址
let localProductDetailsPicturePath = new Map();


// 在页面加载完成执行函数
$(function () {
    console.log("页面加载完成自动执行！");
    $.get(baseProductURL + "/modifyInfo?id=" + getUrlParam("productId"), function (response) {
        console.log("成功接收到来自服务器数据");
        if (response.status === 200) {
            const productCategoryList = response.data.productCategoryList;
            // 完善商品类别下拉列表
            let productCategoryHtml = '';
            productCategoryList.map(function (item) {
                productCategoryHtml += '<option value="' + item.productCategoryId + '">' + item.productCategoryName + '</option>';
            });
            let $productCategory = $("#productCategory");
            $productCategory.append(productCategoryHtml);
            // 商品数据
            let product = response.data.product;
            // 商品名称
            $("#productName").val(product.productName);
            // 商品分类 对于商品分类下拉列表选中为后台数据
            $productCategory.val(product.productCategoryId);
            // 优先级别
            $("#productPriority").val(product.priority);
            // 原价
            $("#originalPrice").val(product.normalPrice);
            // 现价（可选）
            $("#currentPrice").val(product.promotionPrice);
            // 商品简介
            $("#productDescription").val(product.productDesc);
            // 填充商品缩略图
            let test = product.imgPath;
            if (!validateParameterRequired(test)) {
                const thumbnail = baseProductURL + '/getProductThumbnail?productId=' + getUrlParam("productId");
                const productThumbnailHtml = '<li class="weui-uploader__file" style="background-image:url('
                    + thumbnail + ')" onclick="showProductThumbnailPictureImage()"></li>';
                serverThumbnail.push(getUrlParam("productId"));
                $("#uploadProductThumbnail").append(productThumbnailHtml);
            }
            // 修改显示
            $("#productThumbnailSize").html(serverThumbnail.length + "/1");
            // 填充商品详情图片
            const productImageList = response.data.list;
            let productImageHtml = '';
            productImageList.map(function (productImagePath) {
                const picturePath = baseProductImageURL + '/getProductImage?id=' + productImagePath;
                productImageHtml += '<li class="weui-uploader__file" style="background-image:url('
                    + picturePath + ')" onclick="showProductDetailPictureImage(' + '\'' + productImagePath + '\'' + ', 1)"></li>';
                serverProductImage.push(productImagePath);
            });
            $("#uploadProductDetailsPictureList").append(productImageHtml);
            // 修改显示
            $("#ProductDetailsPictureSize").html(serverProductImage.length + "/5");
            console.log("服务器数据大小：" + serverProductImage.length);
        } else {
            $.alert("后台数据错误！请联系管理员！");
        }
    });
});

/**
 * 展示商品详情图片
 * @param uuid 图片的uuid
 * @param dataSource 图片的来源
 */
const showProductDetailPictureImage = function (uuid, dataSource) {
    let picturePath, deletePath;
    // 判断数据来源是本地还是数据服务器上的 1、表示的是数据服务器上的 2、表示的是本地的数据
    if (dataSource === 1) {
        // 拼接图片地址
        picturePath = baseProductImageURL + '/getProductImage?id=' + uuid;
        deletePath = baseProductImageURL + "/delete?id=" + uuid;
    } else if (dataSource === 2) {
        picturePath = localProductDetailsPicturePath.get(uuid);
    }
    // 展示图片
    showImage(picturePath);
    let flag = true;
    // 为删除按钮添加事件
    $("#galleryHref").bind("click", function () {
        if (flag === true) {
            flag = false;
            // 删除图片数据
            if (dataSource === 1) {
                // 如果图片数据来自数据服务器，请求服务器删除数据
                $.get(deletePath, function (responseData) {
                    if (responseData.status === 200) {
                        // 删除本地数据
                        serverProductImage.remove(uuid);
                        // 显示删除结果
                        $.toptip(responseData.msg, 'success');
                        // 重新刷新布局
                        changeUploadProductDetailsPicture();
                    }
                });
            } else if (dataSource === 2) {
                // 数据来自本地数据、删除数据集合中的数据
                localProductDetailsPicture.delete(uuid);
                // 释放URL 对象
                URL.revokeObjectURL(localProductDetailsPicturePath.get(uuid));
                // 删除本地数据
                localProductDetailsPicturePath.delete(uuid);
                // 重新刷新布局
                changeUploadProductDetailsPicture();
            }
            // 隐藏画廊
            $("#gallery").fadeOut("slow");
            flag = true;
        }
    });
};

/**
 * 展示商品缩略图片
 * 1、如果在服务器上有数据，就展示服务器上的数据
 * 2、如果数据在本地，就展示本地的数据
 */
const showProductThumbnailPictureImage = function () {
    let picturePath, deletePath, dataSource = 0;
    // 判断数据来源是本地还是数据服务器上的 1、表示的是数据服务器上的 2、表示的是本地的数据
    if (serverThumbnail.length > 0) {
        dataSource = 1;
        // 拼接图片地址
        picturePath = baseProductURL + '/getProductThumbnail?productId=' + serverThumbnail[0];
        deletePath = baseProductURL + "/deleteProductThumbnail?productId=" + serverThumbnail[0];
    } else if (localProductThumbnailPath.size !== 0) {
        dataSource = 2;
        picturePath = localProductThumbnailPath[0];
    }
    // 展示图片
    showImage(picturePath);
    let flag = true;
    // 为删除按钮添加事件
    $("#galleryHref").bind("click", function () {
        if (flag === true) {
            flag = false;
            // 删除图片数据
            if (dataSource === 1) {
                // 如果图片数据来自数据服务器，请求服务器删除数据
                $.get(deletePath, function (responseData) {
                    if (responseData.status === 200) {
                        // 删除本地数据
                        serverProductImage = [];
                        // 显示删除结果
                        $.toptip(responseData.msg, 'success');
                    }
                });
            } else if (dataSource === 2) {
                // 数据来自本地数据、删除数据集合中的数据
                localProductThumbnail = [];
                for (let i = 0; i < localProductThumbnailPath.length; i++) {
                    URL.revokeObjectURL(localProductThumbnailPath[i]);
                }
                // 删除本地数据
                localProductThumbnailPath = [];
                // 显示删除结果
                $.toptip("删除成功", 'success');
            }
            // 重新刷新布局
            changeProductThumbnail();
            // 隐藏画廊
            $("#gallery").fadeOut("slow");
            flag = true;
        }
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

/**
 * 刷新商品详情图片
 */
const changeUploadProductDetailsPicture = function () {
    console.log("触发改变商品详情图片事件!");
    // language=JQuery-CSS
    let $uploadProductDetailsPictureList = $("#uploadProductDetailsPictureList");
    // 先清空原始网页数据
    removeAllChild("uploadProductDetailsPictureList");
    console.log("商品详情图片：服务器数据：" + serverProductImage.length + " 本地数据：" + localProductDetailsPicture.size);
    // 数据数量
    let pictureNumber = 0;
    // 填充服务器数据
    let productImageHtml = '';
    for (let i = 0; i < serverProductImage.length; i++) {
        const picturePath = baseProductImageURL + '/getProductImage?id=' + serverProductImage[i];
        productImageHtml += '<li class="weui-uploader__file" style="background-image:url('
            + picturePath + ')" onclick="showProductDetailPictureImage(' + '\'' + picturePath + '\'' + ', 1)"></li>';
        pictureNumber++;
    }
    $uploadProductDetailsPictureList.append(productImageHtml);
    // 填充本地数据
    productImageHtml = '';
    localProductDetailsPicturePath.forEach(function (value, key, map) {
        productImageHtml += '<li class="weui-uploader__file" style="background-image:url('
            + value + ')" onclick="showProductDetailPictureImage(' + '\'' + key + '\'' + ', 2)"></li>';
        pictureNumber++;
    });
    $uploadProductDetailsPictureList.append(productImageHtml);
    // 修改显示
    $("#ProductDetailsPictureSize").html(pictureNumber + "/5");
};

/**
 * 刷新商品缩略图
 */
const changeProductThumbnail = function () {
    console.log("触发刷新商品缩略图的事件!");
    removeAllChild("uploadProductThumbnail");
    let $uploadProductThumbnail = $("#uploadProductThumbnail");
    let productThumbnailLength = 0;
    console.log("缩略图：服务器数据：" + serverThumbnail.length + " 本地数据：" + localProductThumbnail.length);
    // 图片数据来自服务器
    if (serverThumbnail.length > 0) {
        // 填充商品缩略图
        const thumbnail = baseProductURL + '/getProductThumbnail?productId=' + getUrlParam("productId");
        const productThumbnailHtml = '<li class="weui-uploader__file" style="background-image:url('
            + thumbnail + ')" onclick="showProductThumbnailPictureImage()"></li>';
        $uploadProductThumbnail.append(productThumbnailHtml);
        productThumbnailLength = 1;
    } else if (localProductThumbnailPath.length > 0) {
        // 取本地保存的数据进行展示
        let imageLi = '<li class="weui-uploader__file" style="background-image:url('
            + localProductThumbnailPath[0] + ')" onclick="showProductThumbnailPictureImage()"></li>';
        $uploadProductThumbnail.append(imageLi);
        productThumbnailLength = 1;
    }
    // 修改显示
    $("#productThumbnailSize").html(productThumbnailLength + "/1");
};

/**
 * 响应选择缩略图的函数
 */
const uploadProductThumbnailInput = function () {
    // 清空原数据
    localProductThumbnail = [];
    for (let i = 0; i < localProductThumbnailPath.length; i++) {
        URL.revokeObjectURL(localProductThumbnailPath[i]);
    }
    localProductThumbnailPath = [];
    const list = this.files;
    for (let i = 0; i < list.length; i++) {
        console.log(list[i].name);
        localProductThumbnail.push(list[i]);
        let objectURL = getObjectURL(list[i]);
        localProductThumbnailPath.push(objectURL);
    }
    changeProductThumbnail();
    console.log("file number = " + list.length);
};

/**
 * 响应选择商品详情图片的函数
 */
const uploadProductDetailsPictureInput = function () {
    let size = serverProductImage.length + localProductDetailsPicture.size;
    const list = this.files;
    size = size + list.length;
    if (size > 5) {
        $.toast("详情图片超过限制", "forbidden");
    } else {
        for (let i = 0; i < list.length; i++) {
            const str = list[i].name;
            const pos = str.lastIndexOf(".");
            const lastName = str.substring(pos, str.length);
            let uuid = generateUUID();
            localProductDetailsPicture.set(uuid, list[i]);
            let objectURL = getObjectURL(list[i]);
            localProductDetailsPicturePath.set(uuid, objectURL);
            console.log(str + "   -- 后缀名为：" + lastName);
        }
        changeUploadProductDetailsPicture();
        console.log("file number = " + list.length);
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
 * 上传商品缩略图
 */
const uploadProductThumbnail = function () {
    // 检查上传的图片数量
    let uploadProductThumbnailList = document.getElementById("uploadProductThumbnail");
    // 获取缩略图的数量
    // var size = document.getElementById("uploadProductThumbnail").childElementCount;
    let size = uploadProductThumbnailList.childElementCount;
    console.log(size);
    if (size >= 1) {
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
let submitFlag = true;
/**
 * 提交商品修改数据
 */
const submitFunction = function () {
    if (submitFlag === true) {
        console.log("进入提交修改数据函数");
        submitFlag = false;
        // 将上传按钮设置位不可用状态
        const checkButton = $("#registerShopSubmit");
        checkButton.addClass("weui-btn_disabled");
        let product = {};
        // 检查上传参数
        // 商品名称
        const productName = $("#productName").val();
        if (validateRequired(productName, "商品名称不能为空")) {
            removeDisable(checkButton);
            return false;
        }

        // 商品分类
        const productCategory = $("#productCategory").val();
        if (validateRequired(productCategory, "商品类别不能为空")) {
            removeDisable(checkButton);
            return false;
        }

        // 优先级别
        let productPriority = $("#productPriority").val();
        if (validateParameterRequired(productPriority)) {
            productPriority = 0;
        }

        // 原价
        const originalPrice = $("#originalPrice").val();
        if (validateRequired(originalPrice, "原价不能为空")) {
            removeDisable(checkButton);
            return false;
        }
        // 检查上传的详情图片数量
        let uploadProductThumbnailList = document.getElementById("uploadProductThumbnail");
        // 获取详情图片的数量
        let size = uploadProductThumbnailList.childElementCount;
        // 验证缩略图
        if (size < 1) {
            $.toptip('请至少添加一张商品缩略图', 'error');
            removeDisable(checkButton);
            return false;
        }

        // 检查上传的详情图片数量
        uploadProductThumbnailList = document.getElementById("uploadProductDetailsPictureList");
        // 获取详情图片的数量
        size = uploadProductThumbnailList.childElementCount;
        // 验证商品详情图
        if (size < 1) {
            $.toptip('请至少添加一张商品详情图', 'error');
            removeDisable(checkButton);
            return false;
        }

        // 验证码
        const verifyCode = $("#verifyCode").val();
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
        const promotionPrice = $("#currentPrice").val();
        if (validateParameterRequired(promotionPrice)) {
            console.log("没填折扣价！");
        } else {
            product.promotionPrice = promotionPrice;
        }
        // 权重
        product.priority = productPriority;
        // 商店id
        product.productId = getUrlParam("productId");
        let formData = new FormData();
        // 处理缩略图
        if (localProductThumbnail.length > 0) {
            const file = localProductThumbnail[0];
            const str = file.name;
            const pos = str.lastIndexOf(".");
            const suffixName = str.substring(pos, str.length);
            // 创建新文件对象
            const newThumbnail = new File([file], "thumbnail" + suffixName, {type: "image/*"});
            formData.append("thumbnail", newThumbnail);
        }


        localProductDetailsPicture.forEach(function (value, key, map) {
            formData.append(key, value);
        });

        formData.append("product", JSON.stringify(product));
        formData.append("verifyCode", verifyCode);
        postRequest("/product/modifyProduct", formData, checkButtonFunction, callBackFunction);
    }
};

const callBackFunction = function () {
    console.log("修改商品完成");
};

const checkButtonFunction = function () {
    // 将上传按钮设置位不可用状态
    const checkButton = $("#registerShopSubmit");
    removeDisable(checkButton);
};