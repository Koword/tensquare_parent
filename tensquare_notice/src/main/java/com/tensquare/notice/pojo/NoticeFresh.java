package com.tensquare.notice.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * @Description 待推送消息实体
 * @Author tangkai
 * @Date 11:59 2019/12/18
 **/
@Data
@TableName("tb_notice_fresh")
public class NoticeFresh {

    private String userId;
    private String noticeId;

}
