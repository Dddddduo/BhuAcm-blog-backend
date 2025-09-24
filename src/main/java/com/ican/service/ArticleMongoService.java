package com.ican.service;

import cn.hutool.core.bean.BeanUtil;
import com.ican.document.ArticleDocument;
import com.ican.mapper.ArticleMapper;
import com.ican.model.vo.request.ArticleReq;
import com.ican.model.vo.response.ArticleBackResp;
import com.ican.model.vo.response.ArticleHomeResp;
import com.ican.model.vo.response.ArticleInfoResp;
import com.ican.model.vo.response.ArticleResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MongoDB文章服务实现
 * @author Dduo
 */
@Service
public class ArticleMongoService extends ArticleService {

    @Autowired
    private MongodbService mongodbService;
    
    @Autowired
    private ArticleMapper articleMapper;
    
    /**
     * 添加文章（双写模式）
     */
    @Override
    public void addArticle(ArticleReq article) {
        // 写入MySQL（保持原有逻辑）
        super.addArticle(article);
        // 同步到MongoDB
        syncArticleToMongoDB(article);
    }

    private void syncArticleToMongoDB(ArticleReq articleReq) {
        // 1. 转换为文档对象
        ArticleDocument articleDocument = new ArticleDocument();
        BeanUtil.copyProperties(articleReq, articleDocument);
        // 2. 保存到MongoDB
        mongodbService.insertDocument(articleReq, "article");
    }

}