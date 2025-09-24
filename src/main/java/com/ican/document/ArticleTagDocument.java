package com.ican.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 文章标签关联文档
 * @author Dduo
 */
@Document(collection = "article_tag")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleTagDocument {
    
    @Id
    private String id;
    
    /**
     * 文章ID
     */
    @Field("article_id")
    private Integer articleId;
    
    /**
     * 标签ID
     */
    @Field("tag_id")
    private Integer tagId;
}