/**
 * bootstrap版评论框
 *
 * @date 2018-01-05 10:57
 * @author zhyd(yadong.zhang0415#gmail.com)
 * @link https://github.com/zhangyd-c
 */
let errorFlag = 200;

const myDateFormat = function (time) {
    let newDate = new Date();
    newDate.setTime(time);
    return newDate.format('yyyy-MM-dd h:m:s');
};
// 页面加载完成执行
$(function () {
    Date.prototype.format = function (format) {
        var date = {
            "M+": this.getMonth() + 1,
            "d+": this.getDate(),
            "h+": this.getHours(),
            "m+": this.getMinutes(),
            "s+": this.getSeconds(),
            "q+": Math.floor((this.getMonth() + 3) / 3),
            "S+": this.getMilliseconds()
        };
        if (/(y+)/i.test(format)) {
            format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
        }
        for (var k in date) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ?
                    date[k] : ("00" + date[k]).substr(("" + date[k]).length));
            }
        }
        return format;
    };
    // 检查用户是否登录，是否具有权限操作
    $.ajax({
        type: "get",
        url: "/user/checkPermissions",
        async: true,
        data: {
            shopId: shopId
        },
        success: function (response) {
            if (response.data.status === 400) {
                $('#commentErrorMessage').text(response.data.message);
                errorFlag = 400;
            } else if (response.data.status === 401) {
                $('#commentErrorMessage').text(response.data.message);
                errorFlag = 401;
            } else {
                $('#commentErrorMessage').text(response.data.message);
                errorFlag = response.data.status;
            }
        },
        error: function (data) {
            console.error(data);
        }
    });
    /**
     * 初始化评论列表
     */
    $.ajax({
        type: "get",
        url: "/comment/getCommentList",
        dataType: "json",
        data: {
            shopId: shopId
        },
        success: function (response) {
            console.log(response);
            if (response.status === 200) {
                console.log("接收评论信息成功！");
                // 修改该商铺的评论数量
                $("#commentCount").text(response.data.size);
                // 填充评论数据
                $("#commentContainer").empty();
                let commentHtml = '';
                response.data.commentDetailList.map(function (review, index) {
                    commentHtml += ' <li>\n' +
                        '                    <div class="comment-body" id="comment-1">\n' +
                        '                        <div class="cheader">\n' +
                        '                            <span>\n' +
                        '                                <img class="userImage" src="/user/getUserImageById?userId=' + review.userId + '">\n' +
                        '                                <strong>' + review.username + '</strong>\n' +
                        '                            </span>\n' +
                        '                            <div class="timer">\n' +
                        '                                <i class="fa fa-clock-o fa-fw"></i>' + myDateFormat(review.createTime) +
                        '                            </div>\n' +
                        '                        </div>\n' +
                        '                        <div class="content">\n' + review.detail +
                        '                        </div>\n' +
                        '                    </div>\n' +
                        '                </li>';
                });
                $("#commentContainer").append(commentHtml);
            } else {
                console.log("请求评论数据失败");
            }
        },
        error: function (xhr) {
            console.log("错误提示： " + xhr + " ---- " + xhr.status + " " + xhr.statusText);
        },
        //请求完成后回调函数 (请求成功或失败之后均调用)。参数： XMLHttpRequest 对象和一个描述成功请求类型的字符串
        complete: function (XMLHttpRequest, textStatus) {
            console.log("函数调用完成，将按钮设置为可用状态");
        }
    });
});
// 提交评论数据的函数
const submitReviewData = function () {
    $.ajax({
        type: "post",
        url: "/comment/submitReviewData",
        dataType: "json",
        data: {
            shopId: shopId,
            detail: editor.txt.html()
        },
        success: function (response) {
            console.log(response);
            if (response.status === 200) {
                console.log("评论成功成功！");
                $.toast("评论成功成功");
                // 修改该商铺的评论数量
                let commentSize = parseInt($("#commentCount").text()) + 1;
                $("#commentCount").text(commentSize);
                // 将新添加的评论添加进去
                $.ajax({
                    url: "/comment/getCommentById",    //请求的url地址
                    dataType: "json",   //返回格式为json
                    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
                    data: {"id": response.data.commitId},    //参数值
                    type: "GET",   //请求方式
                    beforeSend: function (request) {
                        //请求前的处理
                        request.setRequestHeader("Content-type", "application/json");
                    },
                    success: function (responseData) {
                        //请求成功时处理
                        console.log("收到服务器数据：");
                        console.log(responseData);
                        if (responseData.status === 200) {
                            const review = responseData.data;
                            let commentHtml = ' <li>\n' +
                                '                    <div class="comment-body" id="comment-1">\n' +
                                '                        <div class="cheader">\n' +
                                '                            <span>\n' +
                                '                                <img class="userImage" src="/user/getUserImageById?userId=' + review.userId + '">\n' +
                                '                                <strong>' + review.username + '</strong>\n' +
                                '                            </span>\n' +
                                '                            <div class="timer">\n' +
                                '                                <i class="fa fa-clock-o fa-fw"></i>' + myDateFormat(review.createTime) +
                                '                            </div>\n' +
                                '                        </div>\n' +
                                '                        <div class="content">\n' + review.detail +
                                '                        </div>\n' +
                                '                    </div>\n' +
                                '                </li>';
                            // 将评论添加到顶部
                            $("#commentContainer").prepend(commentHtml);
                        }
                    },
                    complete: function () {
                        //请求完成的处理
                    },
                    error: function () {
                        //请求出错处理
                    }
                });
            } else {
                console.log("请求失败");
                $.toast(response.msg, "forbidden");
            }
        },
        error: function (xhr) {
            console.log("错误提示： " + xhr + " ---- " + xhr.status + " " + xhr.statusText);
            // checkButtonFunction();
        },
        //请求完成后回调函数 (请求成功或失败之后均调用)。参数： XMLHttpRequest 对象和一个描述成功请求类型的字符串
        complete: function (XMLHttpRequest, textStatus) {
            console.log("函数调用完成，将按钮设置为可用状态");
        }
    });
};
$.extend({
    comment: {
        submit: function (target) {
            const $this = $(target);
            $this.button('loading');
            // 可以直接提交
            if (errorFlag === 200) {
                console.log("提交数据！");
                submitReviewData();
                console.log(editor.txt.html());
                $this.button('reset');
            } else if (errorFlag === 400) {//你还没登录，请登录！
                console.log("需要登录");
                $('#detail-modal').modal('show');
                $(".close").click(function () {
                    setTimeout(function () {
                        $this.html("<i class='fa fa-close'></i>取消操作...");
                        setTimeout(function () {
                            $this.button('reset');
                        }, 1000);
                    }, 500);
                });
                // 模态框抖动
                $("#detail-form-btn").click(function () {
                    // 检查用户名密码
                    let username = $("#loginUserName").val();
                    if (validateParameterRequired(username)) {
                        $.toast("用户名不能为空", "forbidden");
                        return false;
                    }
                    let password = $("#loginPassword").val();
                    if (validateParameterRequired(password)) {
                        $.toast("密码不能为空", "forbidden");
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
                                // 提交评论数据
                                submitReviewData();
                                // 隐藏弹框
                                $('#detail-modal').modal('hide');
                            } else {
                                $.toast(response.msg, "forbidden");
                            }
                            $this.button('reset');
                        }
                    });

                    // $.ajax({
                    //     type: "get",
                    //     url: "./server/comment.json",
                    //     async: true,
                    //     success: function (json) {
                    //         if (json.statusCode == 200) {
                    //             console.log(json.message);
                    //         } else {
                    //             console.error(json.message);
                    //         }
                    //         // 隐藏弹框
                    //         $('#detail-modal').modal('hide');
                    //
                    //         setTimeout(function () {
                    //             $this.html("<i class='fa fa-check'></i>" + json.message);
                    //             setTimeout(function () {
                    //                 $this.button('reset');
                    //                 // window.location.reload();
                    //             }, 1000);
                    //         }, 1000);
                    //     },
                    //     error: function (data) {
                    //         console.error(data);
                    //     }
                    // });
                });
            } else {
                $this.button('reset');
                $.toast("无权限进行操作！", "forbidden");
            }
        },
        reply: function (pid, c) {
            console.log(pid);
            $('#comment-pid').val(pid);
            $('#cancel-reply').show();
            $('.comment-reply').show();
            $(c).hide();
            $(c).parents('.comment-body').append($('#comment-post'));
            //			$(c).parent().parent().parent().append($('#comment-post'));
        },
        cancelReply: function (c) {
            $('#comment-pid').val("");
            $('#cancel-reply').hide();
            $(c).parents(".comment-body").find('.comment-reply').show();
            //			$(c).parent().parent().parent().find('.comment-reply').show();
            $("#comment-place").append($('#comment-post'));
        }
    }
});

$(function () {
    $('[data-toggle="tooltip"]').tooltip();
    $("#comment-form-btn").click(function () {
        $.comment.submit($(this));
    });
});