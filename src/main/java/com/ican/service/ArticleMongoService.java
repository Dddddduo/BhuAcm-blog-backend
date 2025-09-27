package com.ican.service;

import cn.hutool.core.bean.BeanUtil;
import com.ican.document.ArticleDocument;
import com.ican.model.vo.PageResult;
import com.ican.model.vo.query.ArticleQuery;
import com.ican.model.vo.request.ArticleReq;
import com.ican.model.vo.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * MongoDB文章服务实现
 * @author Dduo
 * @date 2025/09/27 15:35
 */
@Service
public class ArticleMongoService extends ArticleService {

    @Autowired
    private MongodbService mongodbService;
    
    /**
     * 添加文章（双写模式）
     */
    @Override
    public Integer addArticle(ArticleReq article) {
        ArticleReq mongoArticle = BeanUtil.copyProperties(article, ArticleReq.class);
        // 写入MySQL（保持原有逻辑）
        Integer id = super.addArticle(article);
        // 同步到MongoDB
        mongoArticle.setId(id);
        syncArticleToMongoDB(mongoArticle);
        return id;
    }

    /**
     * 查询文章后台列表（从MongoDB查询）
     */
    @Override
    public PageResult<ArticleBackResp> listArticleBackVO(ArticleQuery articleQuery) {
        // 拼接查询条件
        Query query = buildArticleQuery(articleQuery);
        // 查询文章数量
        List<ArticleDocument> articleList = mongodbService.findDocuments("article", query, ArticleDocument.class);
        if (articleList.size() == 0) {
            return new PageResult<>();
        }
        // 查询文章后台信息
        List<ArticleBackResp> articleBackRespList =new ArrayList<>();
        articleList.forEach(item -> {
            ArticleBackResp articleBackResp = new ArticleBackResp();
            BeanUtil.copyProperties(item, articleBackResp);
            List<String> tagNameList = item.getTagNameList();
            List<TagOptionResp> tagVOList = new ArrayList<>();
            tagNameList.forEach(tagName -> {
                TagOptionResp tagOptionResp = new TagOptionResp();
                tagOptionResp.setTagName(tagName);
                tagVOList.add(tagOptionResp);
            });
            articleBackResp.setTagVOList(tagVOList);
            articleBackRespList.add(articleBackResp);
        });
        return new PageResult<>(articleBackRespList, (long) articleBackRespList.size());
    }

    /**
     * 根据Id查询文章详情（从MongoDB查询）
     */
    @Override
    public ArticleResp getArticleHomeById(Integer articleId) {
        // 查询文章信息
        List<ArticleDocument> articleList = mongodbService.findDocumentById("article", articleId.toString(), ArticleDocument.class);
        if (articleList.size() == 0) {
            return null;
        }
        ArticleDocument articleDocument = articleList.get(0);
        ArticleResp article = new ArticleResp();
        BeanUtil.copyProperties(articleDocument, article);
        // 处理标签
        List<String> tagNameList = articleDocument.getTagNameList();
        List<TagOptionResp> tagVOList = new ArrayList<>();
        tagNameList.forEach(tagName -> {
            TagOptionResp tagOptionResp = new TagOptionResp();
            tagOptionResp.setTagName(tagName);
            tagVOList.add(tagOptionResp);
        });
        article.setTagVOList(tagVOList);
        // 浏览量增加,更新文档
        int viewCountNow = articleDocument.getViewCount() + 1;
        // 更新文档
        Update update = new Update();
        update.set("viewCount", viewCountNow);
        mongodbService.updateDocument(articleId.toString(), "article", update);
        articleDocument.setViewCount(viewCountNow);
        // 获取前后两篇文章
        List<ArticleDocument> articleNextList = mongodbService.findDocumentById("article", (articleId+1)+"", ArticleDocument.class);
        if(articleNextList.size() != 0){
            ArticleDocument articleNextDocument = articleNextList.get(0);
            ArticlePaginationResp articlePaginationResp = new ArticlePaginationResp();
            articlePaginationResp.setId(articleId+1);
            articlePaginationResp.setArticleCover(articleNextDocument.getArticleCover());
            articlePaginationResp.setArticleTitle(articleNextDocument.getArticleTitle());
            article.setNextArticle(articlePaginationResp);
        }
        List<ArticleDocument> articleLastList = mongodbService.findDocumentById("article", (articleId-1)+"", ArticleDocument.class);
        if(articleLastList.size() != 0){
            ArticleDocument articleLastDocument = articleLastList.get(0);
            ArticlePaginationResp articlePaginationResp = new ArticlePaginationResp();
            articlePaginationResp.setId(articleId-1);
            articlePaginationResp.setArticleCover(articleLastDocument.getArticleCover());
            articlePaginationResp.setArticleTitle(articleLastDocument.getArticleTitle());
            article.setLastArticle(articlePaginationResp);
        }
        return article;
    }

    /**
     * 删除文章
     */
    @Override
    public void deleteArticle(List<Integer> articleIdList) {
        articleIdList.forEach(articleId -> {mongodbService.dropDocument(articleId.toString(), "article");});
    }

    /**
     * 更新文章
     */
    @Override
    public void updateArticle(ArticleReq articleReq) {
        // 转换为文档对象
        ArticleDocument articleDocument = new ArticleDocument();
        BeanUtil.copyProperties(articleReq, articleDocument);
        // 更新文档
        Update update = new Update();
        update.set("title", articleDocument.getArticleTitle());
        update.set("content", articleDocument.getArticleContent());
        update.set("tagNameList", articleDocument.getTagNameList());
        update.set("categoryId", articleDocument.getCategoryId());
        update.set("updateTime", LocalDateTime.now());
        mongodbService.updateDocument(articleReq.getId().toString(), "article", update);
    }

    /**
     * 将文章写入到MongoDB
     */
    private void syncArticleToMongoDB(ArticleReq articleReq) {
        // 转换为文档对象
        ArticleDocument articleDocument = new ArticleDocument();
        BeanUtil.copyProperties(articleReq, articleDocument);
        articleDocument.setLikeCount(0);
        articleDocument.setViewCount(0);
        articleDocument.setUpdateTime(LocalDateTime.now());
        articleDocument.setCreateTime(LocalDateTime.now());
        // 保存到MongoDB
        mongodbService.insertDocument(articleDocument, "article");
    }

    /**
     * 根据ArticleQuery构建MongoDB查询条件
     */
    public Query buildArticleQuery(ArticleQuery articleQuery) {
        Criteria criteria = new Criteria();
        // 搜索内容（标题和内容包含关键字）
        if (articleQuery.getKeyword() != null && !articleQuery.getKeyword().trim().isEmpty()) {
            String keyword = articleQuery.getKeyword().trim();
            criteria.orOperator(
                    Criteria.where("title").regex(keyword, "i"),  // 标题模糊匹配，不区分大小写
                    Criteria.where("content").regex(keyword, "i") // 内容模糊匹配，不区分大小写
            );
        }
        // 分类ID匹配
        if (articleQuery.getCategoryId() != null) {
            criteria.and("categoryId").is(articleQuery.getCategoryId());
        }
        // 标签ID匹配（假设文章中存储标签ID集合）
        if (articleQuery.getTagId() != null) {
            criteria.and("tagIds").in(articleQuery.getTagId());
        }

        // 是否删除状态匹配
        if (articleQuery.getIsDelete() != null) {
            criteria.and("isDelete").is(articleQuery.getIsDelete());
        }
        // 文章状态匹配
        if (articleQuery.getStatus() != null) {
            criteria.and("status").is(articleQuery.getStatus());
        }
        // 文章类型匹配
        if (articleQuery.getArticleType() != null) {
            criteria.and("articleType").is(articleQuery.getArticleType());
        }
        // 创建查询对象
        Query query = new Query(criteria);
        // 处理分页：PageQuery的getCurrent()已返回skip值，getSize()返回每页条数
        query.skip(articleQuery.getCurrent())  // 直接使用计算好的跳过记录数
                .limit(articleQuery.getSize());   // 使用每页显示条数
        return query;
    }

}