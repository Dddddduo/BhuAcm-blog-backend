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

    /**
     * 查看所有数据库（关键修正：通过MongoClient获取）
     * @return
     */
    public Set<String> getDBs() {
        // 从MongoClient获取所有数据库名称（3.x驱动正确用法）
        MongoIterable<String> dbNames = mongoClient.listDatabaseNames();
        return dbNames.into(new HashSet<>()); // 收集为Set返回
    }

    /**
     * 查看指定数据库中的所有集合（保持不变）
     * @param dbName
     * @return
     */
    public Set<String> getCollections(String dbName) {
        MongoDatabase mongoDatabase = mongoTemplate.getMongoDatabaseFactory().getMongoDatabase(dbName);
        MongoIterable<String> collectionNames = mongoDatabase.listCollectionNames();
        return collectionNames.into(new HashSet<>());
    }

    /**
     * 创建集合（保持不变）
     * @param collectionName
     */
    public void createCollection(String collectionName) {
        if (!mongoTemplate.collectionExists(collectionName)) {
            mongoTemplate.createCollection(collectionName);
        }
    }

    /**
     * 删除集合（保持不变）
     * @param collectionName
     */
    public void dropCollection(String collectionName) {
        if (mongoTemplate.collectionExists(collectionName)) {
            mongoTemplate.dropCollection(collectionName);
        }
    }

    /**
     * 查看集合中的所有文档（保持不变）
     * @param collectionName
     * @param entityClass
     * @return
     * @param <T>
     */
    public <T> List<T> getDocuments(String collectionName, Class<T> entityClass) {
        return mongoTemplate.findAll(entityClass, collectionName);
    }

    /**
     * 查看集合中符合条件的文档（保持不变）
     * @param collectionName
     * @param Query
     * @param entityClass
     * @return
     * @param <T>
     */
    public <T> List<T> findDocuments(String collectionName,Query query, Class<T> entityClass) {
        return mongoTemplate.find(query, entityClass,collectionName);
    }

    /**
     * 根据id查询文档
     * @param collectionName
     * @param id
     * @param entityClass
     * @return
     * @param <T>
     */
    public <T> List<T> findDocumentById(String collectionName,String id, Class<T> entityClass) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.find(query, entityClass,collectionName);
    }

    /**
     * 插入文档（保持不变）
     * @param document
     * @param collectionName
     * @return
     * @param <T>
     */
    public <T> T insertDocument(T document, String collectionName) {
        return mongoTemplate.insert(document, collectionName);
    }

    /**
     * 更新文档（示例：根据ID更新
     * @param id
     * @param collectionName
     * @param update
     */
    public void updateDocument(String id, String collectionName, Update update) {
        Query query = Query.query(Criteria.where("_id").is(id));
        mongoTemplate.updateFirst(query, update, collectionName);
    }

    /**
     * 删除文档（根据ID删除）
     * @param id
     * @param collectionName
     */
    public void dropDocument(String id, String collectionName) {
        Query query = Query.query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, collectionName);
    }


}