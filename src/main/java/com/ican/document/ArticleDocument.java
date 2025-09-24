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
 * 文章文档
 * @author Dduo
 */
@Document(collection = "article")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDocument {
    
    @Id
    private String id;
    
    /**
     * 文章作者ID
     */
    @Field("user_id")
    private Integer userId;
    
    /**
     * 文章分类ID
     */
    @Field("category_id")
    private Integer categoryId;
    
    /**
     * 文章缩略图
     */
    @Field("article_cover")
    private String articleCover;
    
    /**
     * 文章标题
     */
    @Field("article_title")
    private String articleTitle;
    
    /**
     * 文章摘要
     */
    @Field("article_desc")
    private String articleDesc;
    
    /**
     * 文章内容
     */
    @Field("article_content")
    private String articleContent;
    
    /**
     * 文章类型 (1原创 2转载 3翻译)
     */
    @Field("article_type")
    private Integer articleType;
    
    /**
     * 是否置顶 (0否 1是)
     */
    @Field("is_top")
    private Integer isTop;
    
    /**
     * 是否删除 (0否 1是)
     */
    @Field("is_delete")
    private Integer isDelete;
    
    /**
     * 是否推荐 (0否 1是)
     */
    @Field("is_recommend")
    private Integer isRecommend;
    
    /**
     * 状态 (1公开 2私密 3草稿)
     */
    @Field("status")
    private Integer status;
    
    /**
     * 发表时间
     */
    @Field("create_time")
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @Field("update_time")
    private LocalDateTime updateTime;
}