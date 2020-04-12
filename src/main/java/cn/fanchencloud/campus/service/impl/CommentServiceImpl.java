package cn.fanchencloud.campus.service.impl;

import cn.fanchencloud.campus.entity.Comment;
import cn.fanchencloud.campus.entity.LocalAccount;
import cn.fanchencloud.campus.entity.PersonInfo;
import cn.fanchencloud.campus.mapper.CommentMapper;
import cn.fanchencloud.campus.mapper.LocalAccountMapper;
import cn.fanchencloud.campus.mapper.PersonInfoMapper;
import cn.fanchencloud.campus.model.CommentDetail;
import cn.fanchencloud.campus.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2020/4/10
 * Time: 17:25
 * Description: 评论模块服务层实现类
 *
 * @author chen
 */
@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    /**
     * 注入用户信息持久化
     */
    private PersonInfoMapper personInfoMapper;

    /**
     * 注入评论信息数据持久化
     */
    private CommentMapper commentMapper;

    @Override
    public List<CommentDetail> getCommentList(int shopId) {
        // 查询编号为shopId所有的评论
        List<Comment> commentList = commentMapper.queryCommentByShopId(shopId);
        // 提取评论列表内的用户id
        List<Integer> userIdList = commentList.stream().map(Comment::getUserId).distinct().collect(Collectors.toList());
        if (userIdList.size() == 0) {
            return new ArrayList<CommentDetail>();
        }
        Map<Integer, PersonInfo> personInfoMap = personInfoMapper.getRecordsByUserIds(userIdList);
        return commentList.stream().map(comment -> {
            CommentDetail temp = new CommentDetail();
            temp.setData(comment);
            temp.setUsername(personInfoMap.get(comment.getUserId()).getName());
            return temp;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean submitReviewData(Comment comment) {
        comment.setCreateTime(new Date());
        return commentMapper.addRecord(comment) > 0;
    }

    @Override
    public CommentDetail getCommentById(int id) {
        Comment comment = commentMapper.getCommentById(id);
        PersonInfo personInfo = personInfoMapper.queryById(comment.getUserId());
        CommentDetail commentDetail = new CommentDetail();
        commentDetail.setData(comment);
        commentDetail.setUsername(personInfo.getName());
        return commentDetail;
    }

    @Autowired
    public void setPersonInfoMapper(PersonInfoMapper personInfoMapper) {
        this.personInfoMapper = personInfoMapper;
    }

    @Autowired
    public void setCommentMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }
}
