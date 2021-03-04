// 用户类别
let userType = 0;

const registerHtml = '<div class="center" id="registerBox">\n' +
    '                <header class="demos-header">\n' +
    '                    <h1 class="demos-title title-text-center">注册</h1>\n' +
    '                </header>\n' +
    '                <div class="weui-cells weui-cells_form">\n' +
    '                    <div class="weui-cell">\n' +
    '                        <div class="weui-cell__hd"><label class="weui-label">账号</label></div>\n' +
    '                        <div class="weui-cell__bd weui-cell_primary">\n' +
    '                            <input type="text" class="weui-input" id="registerName" placeholder="请输入用户名"/>\n' +
    '                        </div>\n' +
    '                    </div>\n' +
    '                    <div class="weui-cell">\n' +
    '                        <div class="weui-cell__hd">\n' +
    '                            <label class="weui-label">密码</label>\n' +
    '                        </div>\n' +
    '                        <div class="weui-cell__bd weui-cell_primary">\n' +
    '                            <input type="password" id="registerPassword" class="weui-input" placeholder="请输入密码"/>\n' +
    '                        </div>\n' +
    '                    </div>\n' +
    '                    <div class="weui-cell">\n' +
    '                        <div class="weui-cell__hd">\n' +
    '                            <label class="weui-label">确认密码</label>\n' +
    '                        </div>\n' +
    '                        <div class="weui-cell__bd weui-cell_primary">\n' +
    '                            <input type="password" id="confirmPassword" class="weui-input" placeholder="请再次输入密码"/>\n' +
    '                        </div>\n' +
    '                    </div>\n' +
    '                    <div class="weui-cell">\n' +
    '                        <div class="weui-cell__hd"><label for="name" class="weui-label">用户类型</label></div>\n' +
    '                        <div class="weui-cell__bd">\n' +
    '                           <input class="weui-input" id="userType" type="text" value="普通用户" readonly="" data-values="1">\n' +
    '                        </div>\n' +
    '                    </div>' +
    '                    <!-- 验证码    -->\n' +
    '                    <div class="weui-cell weui-cell_vcode">\n' +
    '                        <div class="weui-cell__hd"><label class="weui-label">验证码</label></div>\n' +
    '                        <div class="weui-cell__bd">\n' +
    '                            <input class="weui-input" id="verifyCode" type="text" placeholder="请输入验证码">\n' +
    '                        </div>\n' +
    '                        <div class="weui-cell__ft">\n' +
    '                            <img class="weui-vcode-img" id="verifyCodeImg" src="/Kaptcha.jpg" alt="图片验证码"\n' +
    '                                 onclick="changeCode(this)">\n' +
    '                        </div>\n' +
    '                    </div>\n' +
    '                    <div class="weui-cells__tips"></div>\n' +
    '                    <div class="weui-btn-area">\n' +
    '                        <a href="javascript:;" onclick="registerFunction()" id="registerButton"\n' +
    '                           class="weui-btn weui-btn_primary">注册</a>\n' +
    '                    </div>\n' +
    '                    <div class="otherLogin">\n' +
    '                        <a href="javascript:;" onclick="loginPage()" class="important-tips">返回登录</a>\n' +
    '                    </div>\n' +
    '                </div>\n' +
    '            </div>';

// 注册模块
const registerPage = function () {
    let $tab2 = $("#tab2");
    // 清空容器界面，将登陆界面添加进去
    $tab2.empty();
    $tab2.append(registerHtml);
    $("#userType").select({
        title: "选择用户类型",
        items: [
            {
                title: "普通用户",
                value: "1",
            },
            {
                title: "商家",
                value: "2",
            }
        ]
    });
};

let operating = false;

const loginHtml = '<div class="center" id="loginBox">\n' +
    '                <header class="demos-header">\n' +
    '                    <h1 class="demos-title title-text-center">登录</h1>\n' +
    '                </header>\n' +
    '                <div class="weui-cells weui-cells_form">\n' +
    '                    <div class="weui-cell">\n' +
    '                        <div class="weui-cell__hd"><label class="weui-label">账户</label></div>\n' +
    '                        <div class="weui-cell__bd weui-cell_primary">\n' +
    '                            <input type="text" id="username" class="weui-input" placeholder="请输入用户名"/>\n' +
    '                        </div>\n' +
    '                    </div>\n' +
    '                    <div class="weui-cell">\n' +
    '                        <div class="weui-cell__hd">\n' +
    '                            <label class="weui-label">密码</label>\n' +
    '                        </div>\n' +
    '                        <div class="weui-cell__bd weui-cell_primary">\n' +
    '                            <input type="password" id="password" class="weui-input" placeholder="请输入密码"/>\n' +
    '                        </div>\n' +
    '                    </div>\n' +
    '                    <div class="weui-cells__tips"></div>\n' +
    '                    <div class="weui-btn-area">\n' +
    '                        <a href="javascript:;" onclick="loginFunction()" id="loginButton"\n' +
    '                           class="weui-btn weui-btn_primary">登录</a>\n' +
    '                    </div>\n' +
    '                    <div class="weui-footer otherLogin">\n' +
    '                        <p class="weui-footer__links">\n' +
    '                            <a href="javascript:;" onclick="registerPage()" class="weui-footer__link">没有账号，前往注册</a>\n' +
    '                            <!--<a href="javascript:;" class="weui-footer__link">微信登录</a>-->\n' +
    '                        </p>\n' +
    '                    </div>\n' +
    '                </div>\n' +
    '            </div>';

// 登录模块
const loginPage = function () {
    let $tab2 = $("#tab2");
    // 清空容器界面，将登陆界面添加进去
    $tab2.empty();
    $tab2.append(loginHtml);
};

const userInfoHtml = '<header class="demos-header">\n' +
    '    <h2 class="demos-title title-text-center">用户信息</h2>\n' +
    '</header>\n' +
    '<div class="userInfoHead">\n' +
    '    <div class="userHeadImage">\n' +
    '        <img id="userInfoImage" src="/images/wuman.png" alt="">\n' +
    '    </div>\n' +
    '    <div class="userInfoName" id="userInfoUsername">尘</div>\n' +
    '    <div></div>\n' +
    '</div>\n' +
    '<div class="weui-cells">\n' +
    '    <div class="weui-cell">\n' +
    '        <div class="weui-cell__bd">\n' +
    '            <p>性别</p>\n' +
    '        </div>\n' +
    '        <div class="weui-cell__ft" id="userInfoGender"></div>\n' +
    '    </div>\n' +
    '    <div class="weui-cell">\n' +
    '        <div class="weui-cell__bd">\n' +
    '            <p>联系方式</p>\n' +
    '        </div>\n' +
    '        <div class="weui-cell__ft" id="userInfoPhone"></div>\n' +
    '    </div>\n' +
    '    <div class="weui-cell">\n' +
    '        <div class="weui-cell__bd">\n' +
    '            <p>邮箱</p>\n' +
    '        </div>\n' +
    '    </div>\n' +
    '</div>\n' +
    '<div class="weui-cells">\n' +
    '    <a class="weui-cell weui-cell_access" href="javascript:;" onclick="startMyOperating()">\n' +
    '        <div class="weui-cell__bd">\n' +
    '            <p>操作</p>\n' +
    '        </div>\n' +
    '        <div class="weui-cell__ft">\n' +
    '        </div>\n' +
    '    </a>\n' +
    '</div>\n' +
    '<div class="signOut">\n' +
    '    <a href="javascript:;" onclick="signOut()" class="weui-btn weui-btn_warn">退出登录</a>\n' +
    '</div>';

// 用户信息模块
const userInfoPage = function () {
    let $tab2 = $("#tab2");
    // 清空容器界面，将登陆界面添加进去
    $tab2.empty();
    $tab2.append(userInfoHtml);
    initUserMessage();
};

// 页面初始化完成执行的函数
$(function () {
    $('#myCarousel').carousel({
        //设置自动播放/3 秒
        interval: 3000,
    });
    $.get("/user/isLogin", function (response) {
        if (response.status === 500) {
            loginPage();
        } else if (response.status === 200) {
            userInfoPage();
        }
    });
    console.log("页面加载完成自动执行，请求加载首页数据！");
    $.get("/front/homePageInfo", function (response) {
        console.log("成功接收到来自服务器数据");
        if (response.status === 200) {
            // 头条信息列表
            const headlineList = response.data.headlineList;
            // 一级商铺列表
            const shopCategoryList = response.data.shopCategoryList;
            // 填充头条信息数据
            let carouselListHtml = '';
            let carouselImageListHtml = '';
            headlineList.map(function (item, index) {
                if (index === 0) {
                    carouselListHtml += '<li data-target="#myCarousel" data-slide-to="0" class="active"></li>';
                    carouselImageListHtml += '<div class="item active">'
                        + '<img src="/headline/image?id=' + item.uuid + '" alt="First slide">'
                        + '</div>';
                } else {
                    carouselListHtml += '<li data-target="#myCarousel" data-slide-to="' + index + '"></li>';
                    carouselImageListHtml += '<div class="item">'
                        + '<img src="/headline/image?id=' + item.uuid + '" alt="First slide">'
                        + '</div>';
                }
            });
            $("#carouselList").append(carouselListHtml);
            $("#carouselPicture").append(carouselImageListHtml);
            // 填充一级店铺信息
            let shopCategoryListHtml = '';
            let flag = false;
            shopCategoryList.map(function (item, index) {
                if ((index % 2) === 0) {
                    shopCategoryListHtml += '<div class="weui-flex">\n' +
                        '    <div class="weui-flex__item">\n' +
                        '        <a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg" onclick="enterShopList(' + item.shopCategoryId + ')">\n' +
                        '            <div class="weui-media-box__hd">\n' +
                        '                <img class="weui-media-box__thumb" src="/shopCategory/image?id=' + item.uuid + '" alt="">\n' +
                        '            </div>\n' +
                        '            <div class="weui-media-box__bd">\n' +
                        '                <h4 class="weui-media-box__title">' + item.shopCategoryName + '</h4>\n' +
                        '                <p class="weui-media-box__desc">' + item.shopCategoryDesc + '</p>\n' +
                        '            </div>\n' +
                        '        </a>\n' +
                        '    </div>';
                    flag = true;
                } else {
                    shopCategoryListHtml += '<div class="weui-flex__item">\n' +
                        '        <a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg" onclick="enterShopList(' + item.shopCategoryId + ')">\n' +
                        '            <div class="weui-media-box__hd">\n' +
                        '                <img class="weui-media-box__thumb" src="/shopCategory/image?id=' + item.uuid + '" alt="">\n' +
                        '            </div>\n' +
                        '            <div class="weui-media-box__bd">\n' +
                        '                <h4 class="weui-media-box__title">' + item.shopCategoryName + '</h4>\n' +
                        '                <p class="weui-media-box__desc">' + item.shopCategoryDesc + '</p>\n' +
                        '            </div>\n' +
                        '        </a>\n' +
                        '    </div>\n' +
                        '</div>';
                    flag = false;
                }
            });
            if (flag === true) {
                shopCategoryListHtml += '<div class="weui-flex__item"></div></div>';
            }
            $("#graphicCombinationList").append(shopCategoryListHtml);
        } else {
            alert("后台数据错误！请联系管理员！");
        }
    });
});

const enterShopList = function (shopCategoryId) {
    sessionStorage.setItem("shopCategoryId", shopCategoryId);
    window.location.href = "/frontShop/list";
};

/**
 * 登录
 */
const loginFunction = function () {
    if (operating) {
        return;
    }
    // 禁用登录按钮
    addDisable("loginButton");
    console.log("执行登录函数");
    // 检查用户名密码
    let username = $("#username").val();
    if (validateParameterRequired(username)) {
        $.toptip("用户名不能为空", 'error');
        removeLoginDisable("loginButton");
        return false;
    }
    let password = $("#password").val();
    if (validateParameterRequired(password)) {
        $.toptip("密码不能为空", 'error');
        removeLoginDisable("loginButton");
        return false;
    }
    // 开始登录
    $.ajax({
        type: "POST",
        url: "/user/login",
        dataType: "json",
        data: {
            username: username,
            password: password
        },
        success: function (response) {
            if (response.status === 200) {
                // 请求加载用户数据
                userInfoPage();
                $.toast(response.msg, "text");
            } else {
                $.toast(response.msg, "forbidden");
            }
            removeLoginDisable("loginButton");
        }
    });
};

/**
 * 登录成功加载用户数据
 */
const initUserMessage = function () {
    $.get("/user/getUserInfo", function (response) {
        if (response.status === 200) {
            let user = response.data;
            console.log(user);
            $("#userInfoUsername").html(user.name);
            $("#userInfoGender").html(user.gender);
            $("#userInfoPhone").html(user.phone);
            $("#userInfoEmail").html(user.email);
            let $userInfoImage = $("#userInfoImage");
            let src = $userInfoImage[0].src;
            // 修改用户类型
            userType = user.userType;
            console.log("原始路径：" + src);
            $userInfoImage.attr('src', "/user/getUserImage");
            src = $userInfoImage[0].src;
            console.log("修改路径：" + src);
        }
    });
};

/**
 * 为按钮添加禁用样式并设置一个变量为true
 * @param btn 按钮
 */
const addDisable = function (btn) {
    operating = true;
    // 为登录按钮添加禁用样式
    document.getElementById(btn).classList.add("weui-btn_disabled");
};

/**
 * 为按钮移除禁用样式并设置一个变量为false
 * @param btn 按钮
 */
const removeLoginDisable = function (btn) {
    operating = false;
    // 为登录按钮移除禁用样式
    document.getElementById(btn).classList.remove("weui-btn_disabled");
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
 * @param cName 样式名s
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
 * 注册用户函数
 */
const registerFunction = function () {
    if (operating) {
        return;
    }
    // 禁用注册按钮
    addDisable("registerButton");
    console.log("执行注册函数");
    // 检查用户名密码
    let username = $("#registerName").val();
    if (validateParameterRequired(username)) {
        $.toptip("用户名不能为空", 'error');
        removeLoginDisable("registerButton");
        return false;
    }
    let password = $("#registerPassword").val();
    if (validateParameterRequired(password)) {
        $.toptip("密码不能为空", 'error');
        removeLoginDisable("registerButton");
        return false;
    }
    let confirmPassword = $("#confirmPassword").val();
    if (validateParameterRequired(confirmPassword)) {
        $.toptip("重复密码不能为空", 'error');
        removeLoginDisable("registerButton");
        return false;
    }
    if (password !== confirmPassword) {
        $.toptip("两次密码不一致", 'error');
        removeLoginDisable("registerButton");
        return false;
    }
    let validateCode = $("#verifyCode").val();
    if (validateParameterRequired(validateCode)) {
        $.toptip("验证码不能为空", 'error');
        removeLoginDisable("registerButton");
        return false;
    }
    // 检查用户类型
    let userType = $("#userType").val();
    if (validateParameterRequired(userType)) {
        $.toptip("用户类型不能为空", 'error');
        removeLoginDisable("registerButton");
        return false;
    }
    // 开始注册
    $.ajax({
        type: "POST",
        url: "/user/register",
        dataType: "JSON",
        data: {
            username: username,
            password: password,
            validateCode: validateCode,
            userType: userType
        },
        success: function (response) {
            // 注册成功！
            if (response.status === 200) {
                // 跳转到登录界面
                removeLoginDisable("registerButton");
                loginPage();
                $.toast(response.msg, "text");
            } else {
                // 注册失败
                $.toast(response.msg, "forbidden");
                document.getElementById("verifyCodeImg").src = "/Kaptcha.jpg?" + Math.floor(Math.random() * 100);
            }
            removeLoginDisable("registerButton");
        }
    });
};

/**
 * 展示当前用户可以进行的操作
 */
const startMyOperating = function () {
    // 当前用户是普通用户
    if (userType === 1) {
        myOperatingOfUser();
    } else if (userType === 2) {
        /*  当前用户是商家  */
        myOperatingOfBusiness();
    }
};

/**
 * 普通用户可以进行的操作
 */
const myOperatingOfUser = function () {
    $.actions({
        actions: [{
            text: "修改我的信息",
            onClick: function () {
                window.location.href = "/person/modifyUser"
            }
        }, {
            text: "修改登录密码",
            onClick: function () {
                window.location.href = "/user/modifyPassword"
            }
        }]
    });
};

/**
 * 商家用户可以进行的操作
 */
const myOperatingOfBusiness = function () {
    $.actions({
        actions: [{
            text: "修改我的信息",
            onClick: function () {
                window.location.href = "/person/modifyUser"
            }
        }, {
            text: "修改登录密码",
            onClick: function () {
                window.location.href = "/user/modifyPassword"
            }
        }, {
            text: "查看我的商铺",
            onClick: function () {
                window.location.href = "/shop/listShop";
            }
        }]
    });
};

/**
 * 退出登录
 */
const signOut = function () {
    window.location.href = "/user/loginOut";
};