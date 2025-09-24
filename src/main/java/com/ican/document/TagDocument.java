package com.ican.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * 标签文档
 * @author Dduo
 */
@Document(collection = "tag")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDocument {
    
    @Id
    private String id;
    
    /**
     * 标签名
     */
    @Field("tag_name")
    private String tagName;
    
    /**
     * 创建时间
     */
    @Field("create_time")
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @Field("update_time")
    private LocalDateTime updateTime;
}