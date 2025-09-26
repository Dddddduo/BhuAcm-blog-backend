package com.ican.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 数据库配置属性
 * @author Dduo
 */
@Data
@Component
@ConfigurationProperties(prefix = "datatype")
public class DatabaseProperties {
    
    /**
     * 数据库类型
     */
    private String type = "mysql";
    
    /**
     * MongoDB配置
     */
    private MongoDbProperties mongodb = new MongoDbProperties();
    
    /**
     * 是否启用MongoDB
     */
    public boolean isMongoDbEnabled() {
        return "mongodb".equalsIgnoreCase(type) || mongodb.isEnabled();
    }
    
    @Data
    public static class MongoDbProperties {
        
        /**
         * 是否启用MongoDB
         */
        private boolean enabled = false;
        
        /**
         * MongoDB集合前缀
         */
        private String collectionPrefix = "blog_";
        
        /**
         * 是否启用自动同步
         */
        private boolean autoSync = true;
        
        /**
         * 同步批次大小
         */
        private int syncBatchSize = 100;
    }
}