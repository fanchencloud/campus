const baseRequestUrl = "/shop";
let submitFlag = true;

// 在页面加载完成执行函数
$(function () {
    console.log("页面加载完成自动执行！");
    // 获取店铺列表
    $.get(baseRequestUrl + "/getShopList", function (response) {
        console.log("成功接收到来自服务器数据");
        if (response.status === 200) {
            console.log(response);
            const username = response.data.user;
            const span = document.getElementById("username");
            span.innerHTML = username;
            const shopList = response.data.shopList;
            let shopHtml = '';
            shopList.map(function (item, index) {
                if (item.enableStatus === 1) {
                    shopHtml += '<a class="weui-cell weui-cell_access" href="javascript:testFunction(' + item.shopId + ',' + item.enableStatus + ');">'
                        + '<div class="weui-cell__bd"><p>' + item.shopName + '</p></div>'
                        + '<div class="weui-cell__ft">' + getShopStatus(item.enableStatus) + '</div></a>';
                } else {
                    shopHtml += '<a class="weui-cell weui-cell_access">'
                        + '<div class="weui-cell__bd"><p>' + item.shopName + '</p></div>'
                        + '<div class="weui-cell__ft">' + getShopStatus(item.enableStatus) + '</div></a>';
                }
            });

            $('#shopListContainer').append(shopHtml);
        } else {
            alert("后台数据错误！请联系管理员！");
        }
    });
});

const getShopStatus = function (index) {
    if (index === -1) {
        return "审核不通过";
    } else if (index === 0) {
        return "审核中";
    } else {
        return "审核通过";
    }
};

const addShop = function () {
    window.location.href = "/shop/registerShop";
};


const testFunction = function (shopId, shopStatus) {
    $.actions({
        title: "请选择操作",
        actions: [{
            text: "店铺信息",
            className: "color-success",
            onClick: function () {
                //do something
                console.log('店铺信息');
                window.location.href = baseRequestUrl + "/modifyShop?shopId=" + shopId;
            }
        }, {
            text: "商品管理",
            className: "color-success",
            onClick: function () {
                //do something
                console.log('删除');
                window.location.href = baseRequestUrl + "/managerProduct?shopId=" + shopId;
            }
        }, {
            text: "类别管理",
            className: "color-success",
            onClick: function () {
                //do something
                console.log('删除');
                window.location.href = baseRequestUrl + "/listShopProductCategory?shopId=" + shopId;
            }
        }]
    });
};