package com.tensquare.notice.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.notice.dao.NoticeDao;
import com.tensquare.notice.dao.NoticeFreshDao;
import com.tensquare.notice.pojo.Notice;
import com.tensquare.notice.pojo.NoticeFresh;
import com.tensquare.util.IdWorker;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description
 * @Author tangkai
 * @Date 12:42 2019/12/18
 **/
@Service
@Transactional
@AllArgsConstructor
public class NoticeService {


    NoticeDao noticeDao;

    IdWorker idWorker;

    NoticeFreshDao noticeFreshDao;


    /**
     * @Description 根据id查询通知
     * @Author tangkai
     * @Date 14:53 2019/12/18
     * @Param [id]
     * @Return com.tensquare.entity.Result
     **/
    public Notice findById(String id) {
        return noticeDao.selectById(id);
    }


    /**
     * @Description 新增通知
     * @Author tangkai
     * @Date 14:53 2019/12/18
     * @Param [notice]
     * @Return com.tensquare.entity.Result
     **/
    public void add(Notice notice) {
        String id = idWorker.nextId() + "";

        // 1.设置初始值
        notice.setState("0");
        notice.setCreatetime(new Date());
        notice.setId(id);
        noticeDao.insert(notice);

        // 2.待推送消息入库，新消息提醒
        NoticeFresh noticeFresh = new NoticeFresh();
        // 设置消息id
        noticeFresh.setNoticeId(id);
        // 待通知用户的id
        noticeFresh.setUserId(notice.getReceiverId());
        noticeFreshDao.insert(noticeFresh);

    }


    /**
     * @Description 分页查询消息通知
     * @Author tangkai
     * @Date 15:01 2019/12/18
     * @Param [page, size]
     * @Return com.baomidou.mybatisplus.plugins.Page<com.tensquare.notice.pojo.Notice>
     **/
    public Page<Notice> findPage(int page, int size) {
        Page<Notice> pageList = new Page<>(page, size);
        List<Notice> list = noticeDao.selectPage(pageList, new EntityWrapper<Notice>());
        pageList.setRecords(list);
        return pageList;
    }


    /**
     * @Description 更新通知
     * @Author tangkai
     * @Date 15:53 2019/12/18
     * @Param [notice]
     * @Return void
     **/
    public void update(Notice notice) {
        noticeDao.updateById(notice);
    }


    /**
     * @Description 分页查询待推送通知
     * @Author tangkai
     * @Date 16:09 2019/12/18
     * @Param [userId, page, size]
     * @Return com.baomidou.mybatisplus.plugins.Page<com.tensquare.notice.pojo.NoticeFresh>
     **/
    public Page<NoticeFresh> freshPage(String userId, int page, int size) {
        // 分页条件
        Page<NoticeFresh> pageList = new Page<>(page, size);

        // 查询条件
        EntityWrapper<NoticeFresh> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("userId", userId);

        // 分页查询
        List<NoticeFresh> list = noticeFreshDao.selectPage(pageList, entityWrapper);
        // 设置总记录数
        pageList.setRecords(list);
        return pageList;
    }

    /**
     * @Description 删除待推送消息
     * @Author tangkai
     * @Date 16:18 2019/12/18
     * @Param [noticeFresh]
     * @Return void
     **/
    public void freshDelete(NoticeFresh noticeFresh) {
        noticeFreshDao.delete(new EntityWrapper<>(noticeFresh));
    }


}
