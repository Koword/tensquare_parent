package com.tensquare.article.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.article.dao.ArticleMapper;
import com.tensquare.article.pojo.Article;
import com.tensquare.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author GaoLeng_Tang 🍭
 * @Date: 2019-11-24 23:57:36
 **/
@Transactional(rollbackFor = Exception.class)
@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    IdWorker idWorker;

    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 查询全部
     * @Date: 2019-11-25 0025 0:00
     * @Param * @param
     * @Return: java.util.List<com.tensquare.article.pojo.Article>
     */
    public List<Article> selectAll() {
        return articleMapper.selectList(null);
    }


    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 根据id查询文章
     * @Date: 2019-11-25 0025 0:40
     * @Param * @param articleId
     * @Return: com.tensquare.article.pojo.Article
     */
    public Article selectById(String articleId) {
        return articleMapper.selectById(articleId);
    }


    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 新增文章
     * @Date: 2019-11-25 0025 23:26
     * @Param * @param article
     * @Return: void
     */
    public void insert(Article article) {
        article.setId(idWorker.nextId() + "");
        article.setCreatetime(new Date());
        article.setIspublic("1");
        article.setVisits(0);
        article.setThumbup(0);
        article.setComment(0);
        article.setState("0");
        articleMapper.insert(article);
    }

    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 修改文章
     * @Date: 2019-11-25 0025 0:40
     * @Param * @param article
     * @Return: void
     */
    public void updateById(Article article) {
        //方式一
        articleMapper.updateById(article);
        //方式二
//        EntityWrapper<Article> wrapper = new EntityWrapper<>();
//        wrapper.eq("id", article.getId());
//        articleMapper.update(article, wrapper);
    }

    /**
     * @Author: GaoLeng_Tang 🍭
     * @Description: 删除文章
     * @Date: 2019-11-25 0025 0:40
     * @Param * @param articleId
     * @Return: void
     */
    public void deleteById(String articleId) {
        articleMapper.deleteById(articleId);
    }

    public Page<Article> search(Map map, int page, int size) {
        // 1.封装分页条件
        Page<Article> pageList = new Page<>(page, size);

        // 2.封装查询条件
        EntityWrapper<Article> entityWrapper = new EntityWrapper<>();
        if (!StringUtils.isEmpty(map.get("columnid"))) {
            entityWrapper.eq("columnid", map.get("columnid"));
        }
        if (!StringUtils.isEmpty(map.get("userid"))) {
            entityWrapper.eq("userid", map.get("userid"));
        }
        if (!StringUtils.isEmpty(map.get("title"))) {
            entityWrapper.like("title", "%" + map.get("title") + "%");
        }
        if (!StringUtils.isEmpty(map.get("content"))) {
            entityWrapper.like("content", "%" + map.get("content") + "%");
        }

        // 3.返回结果
        List<Article> articleList = articleMapper.selectPage(pageList, entityWrapper);
        return pageList.setRecords(articleList);
    }
}
