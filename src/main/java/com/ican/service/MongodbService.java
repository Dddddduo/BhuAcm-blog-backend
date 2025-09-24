package com.ican.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MongodbService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoClient mongoClient;

    /** 1. 查看所有数据库（关键修正：通过MongoClient获取） */
    public Set<String> getDBs() {
        // 从MongoClient获取所有数据库名称（3.x驱动正确用法）
        MongoIterable<String> dbNames = mongoClient.listDatabaseNames();
        return dbNames.into(new HashSet<>()); // 收集为Set返回
    }

    /** 2. 查看指定数据库中的所有集合（保持不变） */
    public Set<String> getCollections(String dbName) {
        MongoDatabase mongoDatabase = mongoTemplate.getMongoDatabaseFactory().getMongoDatabase(dbName);
        MongoIterable<String> collectionNames = mongoDatabase.listCollectionNames();
        return collectionNames.into(new HashSet<>());
    }

    /** 3. 创建集合（保持不变） */
    public void createCollection(String collectionName) {
        if (!mongoTemplate.collectionExists(collectionName)) {
            mongoTemplate.createCollection(collectionName);
        }
    }

    /** 4. 删除集合（保持不变） */
    public void dropCollection(String collectionName) {
        if (mongoTemplate.collectionExists(collectionName)) {
            mongoTemplate.dropCollection(collectionName);
        }
    }

    /** 5. 查看集合中的所有文档（保持不变） */
    public <T> List<T> getDocuments(String collectionName, Class<T> entityClass) {
        return mongoTemplate.findAll(entityClass, collectionName);
    }

    /** 6. 插入文档（保持不变） */
    public <T> T insertDocument(T document, String collectionName) {
        return mongoTemplate.insert(document, collectionName);
    }

    /** 7. 更新文档（示例：根据ID更新） */
    public void updateDocument(String id, String collectionName, Update update) {
        Query query = Query.query(Criteria.where("_id").is(id));
        mongoTemplate.updateFirst(query, update, collectionName);
    }

    /** 8. 删除文档（示例：根据ID删除） */
    public void dropDocument(String id, String collectionName) {
        Query query = Query.query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, collectionName);
    }
}