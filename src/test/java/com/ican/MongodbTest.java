package com.ican;

import com.ican.service.MongodbService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("dev")
public class MongodbTest {

    @Autowired
    private MongodbService mongoDBService;

    // 测试用的数据库和集合名称（避免影响实际数据）
    private static final String TEST_DB = "test_db";
    private static final String TEST_COLLECTION = "test_collection";

    /**
     * 测试查看所有数据库
     */
    @Test
    void testGetDBs() {
        Set<String> dbs = mongoDBService.getDBs();
        System.out.println("=== 所有数据库 ===");
        dbs.forEach(System.out::println);
    }

    /**
     * 测试查看指定数据库的集合
     */
    @Test
    void testGetCollections() {
        // 先确保测试数据库存在（插入一条数据触发创建）
        mongoDBService.insertDocument(new TestEntity("init", 0), TEST_COLLECTION);

        Set<String> collections = mongoDBService.getCollections(TEST_DB);
        System.out.println("\n=== " + TEST_DB + " 中的集合 ===");
        collections.forEach(System.out::println);
    }

    /**
     * 测试创建集合
     */
    @Test
    void testCreateCollection() {
        String newCollection = "new_test_collection";
        mongoDBService.createCollection(newCollection);
        System.out.println("\n=== 创建集合 " + newCollection + " 后 ===");
        Set<String> collections = mongoDBService.getCollections(TEST_DB);
        collections.forEach(System.out::println);
    }

    /**
     * 测试插入和查询文档
     */
    @Test
    void testInsertAndGetDocuments() {
        // 插入测试文档
        TestEntity entity = new TestEntity("test_user", 25);
        TestEntity inserted = mongoDBService.insertDocument(entity, TEST_COLLECTION);
        System.out.println("\n=== 插入的文档 ===");
        System.out.println(inserted);

        // 查询所有文档
        List<TestEntity> documents = mongoDBService.getDocuments(TEST_COLLECTION, TestEntity.class);
        System.out.println("\n=== 集合中的所有文档 ===");
        documents.forEach(System.out::println);
    }

    /**
     * 测试更新文档
     */
    @Test
    void testUpdateDocument() {
        // 先插入一条测试数据
        TestEntity entity = new TestEntity("update_user", 30);
        TestEntity inserted = mongoDBService.insertDocument(entity, TEST_COLLECTION);
        String id = inserted.getId();

        // 执行更新（修改年龄）
        Update update = new Update().set("age", 35);
        mongoDBService.updateDocument(id, TEST_COLLECTION, update);

        // 验证更新结果
        List<TestEntity> documents = mongoDBService.getDocuments(TEST_COLLECTION, TestEntity.class);
        System.out.println("\n=== 更新后的文档 ===");
        documents.forEach(doc -> {
            if (doc.getId().equals(id)) {
                System.out.println(doc); // 应显示年龄为35
            }
        });
    }

    /**
     * 测试删除文档
     */
    @Test
    void testDropDocument() {
        // 先插入一条测试数据
        TestEntity entity = new TestEntity("delete_user", 40);
        TestEntity inserted = mongoDBService.insertDocument(entity, TEST_COLLECTION);
        String id = inserted.getId();
        System.out.println("\n=== 插入待删除的文档 ===");
        System.out.println(inserted);

        // 执行删除
        mongoDBService.dropDocument(id, TEST_COLLECTION);

        // 验证删除结果
        List<TestEntity> documents = mongoDBService.getDocuments(TEST_COLLECTION, TestEntity.class);
        System.out.println("\n=== 删除后的剩余文档 ===");
        documents.forEach(System.out::println); // 不应包含被删除的文档
    }

    /**
     * 测试删除集合（最后执行，清理测试数据）
     */
    @Test
    void testDropCollection() {
        mongoDBService.dropCollection(TEST_COLLECTION);
        System.out.println("\n=== 删除集合 " + TEST_COLLECTION + " 后 ===");
        Set<String> collections = mongoDBService.getCollections(TEST_DB);
        collections.forEach(System.out::println); // 不应包含被删除的集合
    }

    // 测试用的实体类（映射文档）
    static class TestEntity {
        private String id;
        private String name;
        private Integer age;

        public TestEntity(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        // Getter和Setter
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Integer getAge() { return age; }
        public void setAge(Integer age) { this.age = age; }

        @Override
        public String toString() {
            return "TestEntity{id='" + id + "', name='" + name + "', age=" + age + "}";
        }
    }
}
