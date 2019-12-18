package com.tensquare.notice.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.entity.PageResult;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.notice.pojo.Notice;
import com.tensquare.notice.pojo.NoticeFresh;
import com.tensquare.notice.service.NoticeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author tangkai
 * @Date 12:52 2019/12/18
 **/
@RestController
@RequestMapping("/notice")
@CrossOrigin
@AllArgsConstructor
public class NoticeController {

    NoticeService noticeService;


    /**
     * @Description 根据id查询通知
     * @Author tangkai
     * @Date 14:53 2019/12/18
     * @Param [id]
     * @Return com.tensquare.entity.Result
     **/
    @RequestMapping(value = "/{id}", method = GET)
    public Result findById(@PathVariable(value = "id") String id) {
        Notice notice = noticeService.findById(id);
        return new Result(true, StatusCode.OK, "根据通知根据id查询成功!", notice);
    }

    /**
     * @Description 新增通知
     * @Author tangkai
     * @Date 14:53 2019/12/18
     * @Param [notice]
     * @Return com.tensquare.entity.Result
     **/
    @RequestMapping(method = POST)
    public Result add(@RequestBody Notice notice) {
        noticeService.add(notice);
        return new Result(true, StatusCode.OK, "新增通知消息成功!");
    }

    /**
     * @Description 分页查询消息通知
     * @Author tangkai
     * @Date 15:35 2019/12/18
     * @Param [page, size]
     * @Return com.tensquare.entity.Result
     **/
    @RequestMapping(value = "findPage/{page}/{size}", method = GET)
    public Result findPage(@PathVariable(value = "page") int page,
        @PathVariable(value = "size") int size) {
        Page<Notice> pageList = noticeService.findPage(page, size);
        PageResult pageResult = new PageResult<>(pageList.getTotal(), pageList.getRecords());

        return new Result(true, StatusCode.OK, "分页查询成功!", pageResult);
    }


    /**
     * @Description 更新通知
     * @Author tangkai
     * @Date 15:53 2019/12/18
     * @Param [notice]
     * @Return void
     **/
    @RequestMapping(value = "/{id}", method = PUT)
    public Result update(@PathVariable(value = "id") String id,
        @RequestBody Notice notice) {
        notice.setId(id);
        noticeService.update(notice);
        return new Result(true, StatusCode.OK, "修改通知成功!");
    }


    /**
     * @Description 分页查询待推动消息成功
     * @Author tangkai
     * @Date 16:15 2019/12/18
     * @Param [userId, page, size]
     * @Return com.tensquare.entity.Result
     **/
    @RequestMapping(value = "/freshPage/{userId}/{page}/{size}", method = GET)
    public Result freshPage(@PathVariable(value = "userId") String userId,
        @PathVariable(value = "page") int page,
        @PathVariable(value = "size") int size) {
        Page<NoticeFresh> pageList = noticeService.freshPage(userId, page, size);
        PageResult<NoticeFresh> pageResult = new PageResult<>(pageList.getTotal(), pageList.getRecords());
        return new Result(true, StatusCode.OK, "分页查询待推送消息成功!", pageResult);
    }

    /**
     * @Description 删除待推送消息
     * @Author tangkai
     * @Date 16:18 2019/12/18
     * @Param [noticeFresh]
     * @Return void
     **/
    @RequestMapping(value = "/fresh",method = DELETE)
    public Result freshDelete(@RequestBody NoticeFresh noticeFresh){
        noticeService.freshDelete(noticeFresh);
        return new Result(true,StatusCode.OK,"删除待推送消息成功!");
    }



}
