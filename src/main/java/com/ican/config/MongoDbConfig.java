package com.ican.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;

/**
 * MongoDB配置类
 * @author Dduo
 */
@Configuration
@ConditionalOnProperty(name = "database.mongodb.enabled", havingValue = "true")
public class MongoDbConfig {
    
    @Autowired
    private org.springframework.core.env.Environment env;
    
    @Bean
    public MongoClient mongoClient() {
        String uri = env.getProperty("spring.data.mongodb.uri", 
            "mongodb://" + env.getProperty("spring.data.mongodb.host") + 
            ":" + env.getProperty("spring.data.mongodb.port") + 
            "/" + env.getProperty("spring.data.mongodb.database"));
        
        ConnectionString connectionString = new ConnectionString(uri);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build();
        
        return MongoClients.create(mongoClientSettings);
    }
    
    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory(MongoClient mongoClient) {
        String database = env.getProperty("spring.data.mongodb.database", "blog");
        return new SimpleMongoClientDatabaseFactory(mongoClient, database);
    }
    
    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory, 
                                     MongoConverter converter) {
        return new MongoTemplate(mongoDatabaseFactory);
    }
}