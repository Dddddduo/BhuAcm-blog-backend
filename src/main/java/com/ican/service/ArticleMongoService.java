package com.ican.service;

import cn.hutool.core.bean.BeanUtil;
import com.ican.document.ArticleDocument;
import com.ican.mapper.ArticleMapper;
import com.ican.model.vo.request.ArticleReq;
import com.ican.model.vo.response.ArticleBackResp;
import com.ican.model.vo.response.ArticleHomeResp;
import com.ican.model.vo.response.ArticleInfoResp;
import com.ican.model.vo.response.ArticleResp;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Integer addArticle(ArticleReq article) {
        ArticleReq mongoArticle = BeanUtil.copyProperties(article, ArticleReq.class);
        // 写入MySQL（保持原有逻辑）
        Integer id = super.addArticle(article);
        // 同步到MongoDB
        mongoArticle.setId(id);
        syncArticleToMongoDB(mongoArticle);
        return id;
    }

    private void syncArticleToMongoDB(ArticleReq articleReq) {
        // 转换为文档对象
        ArticleDocument articleDocument = new ArticleDocument();
        BeanUtil.copyProperties(articleReq, articleDocument);
        List<ArticleDocument.ArticleTag> articleTagList = new ArrayList();
        for (String s : articleReq.getTagNameList()) {
            ArticleDocument.ArticleTag articleTag = new ArticleDocument.ArticleTag();
            articleTag.setTagName(s);
            articleTagList.add(articleTag);
        }
        articleDocument.setTags(articleTagList);
        // 保存到MongoDB
        mongodbService.insertDocument(articleDocument, "article");
    }

}