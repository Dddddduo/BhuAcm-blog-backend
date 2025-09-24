package com.ican.factory;

import com.ican.config.properties.DatabaseProperties;
import com.ican.service.ArticleMongoService;
import com.ican.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 数据库服务工厂
 * @author Dduo
 */
@Component
public class DatabaseServiceFactory {
    
    @Autowired
    private DatabaseProperties databaseProperties;
    
    @Autowired
    private ArticleService articleService;
    
    @Autowired(required = false)
    private ArticleMongoService articleMongoService;
    
    /**
     * 获取文章服务
     */
    public ArticleService getArticleService() {
        if (databaseProperties.isMongoDbEnabled() && articleMongoService != null) {
            return articleMongoService;
        }
        return articleService;
    }
    
    /**
     * 获取当前数据库类型
     */
    public String getCurrentDatabaseType() {
        return databaseProperties.isMongoDbEnabled() ? "mongodb" : "mysql";
    }
}