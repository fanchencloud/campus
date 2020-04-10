package cn.fanchencloud.campus.controller.comment;

import cn.fanchencloud.campus.entity.Comment;
import cn.fanchencloud.campus.entity.LocalAccount;
import cn.fanchencloud.campus.model.CommentDetail;
import cn.fanchencloud.campus.model.JsonResponse;
import cn.fanchencloud.campus.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2020/4/10
 * Time: 16:36
 * Description: 评论控制器
 *
 * @author chen
 */
@Controller
@RequestMapping(value = "/comment")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    /**
     * 注入 评论模块服务
     */
    private CommentService commentService;

    /**
     * 请求编号为shopId的所有评论消息
     *
     * @param shopId 商铺Id
     * @return 评论列表
     */
    @GetMapping(value = "/getCommentList")
    @ResponseBody
    public JsonResponse getCommentList(@RequestParam("shopId") int shopId) {
        List<CommentDetail> commentDetailList = commentService.getCommentList(shopId);
        Map<String, Object> map = new HashMap<>(4);
        map.put("commentDetailList", commentDetailList);
        map.put("size", commentDetailList.size());
        return JsonResponse.ok(map);
    }

    /**
     * 提交用户评论信息
     *
     * @param request 请求会话
     * @param shopId  评论商铺id
     * @param detail  评论内容
     * @return 提交结果
     */
    @PostMapping(value = "/submitReviewData")
    @ResponseBody
    public JsonResponse submitReviewData(HttpServletRequest request,
                                         @RequestParam("shopId") int shopId,
                                         @RequestParam("detail") String detail) {
        logger.debug("shopId = " + shopId);
        logger.debug("detail = " + detail);
        // 校验是否具有资格进行评论
        boolean flag = true;
        if (flag) {
            Comment comment = new Comment();
            comment.setShopId(shopId);
            comment.setDetail(detail);
            LocalAccount user = (LocalAccount) request.getSession().getAttribute("user");
            comment.setUserId(user.getUserId());
            if (commentService.submitReviewData(comment)) {
                logger.debug("返回的记录id = " + comment.getId());
                Map<String, Object> map = new HashMap<String, Object>(2);
                map.put("commitId", comment.getId());
                return JsonResponse.ok("评论成功！", map);
            } else {
                return JsonResponse.errorMsg("评论失败");
            }
        } else {
            return JsonResponse.errorMsg("无权限进行评论操作！");
        }
    }

    @GetMapping(value = "/getCommentById")
    @ResponseBody
    public JsonResponse getCommentById(@RequestParam("id") int id) {
        CommentDetail comment = commentService.getCommentById(id);
        return JsonResponse.ok(comment);
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }
}
