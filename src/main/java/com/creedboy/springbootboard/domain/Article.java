package com.creedboy.springbootboard.domain;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
//@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(
//    name = "article",
    indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashTag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
    })
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userId; // userId

    @Setter
    @Column(nullable = false)
    private String title; // 제목

    @Setter
    @Column(nullable = false, length = 10000)
    private String content; // 내용

    @Setter
    private String hashtag; // 해시태그

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt; // 생성일시

    @CreatedBy
    @Column(nullable = false, length = 100)
    private String createdBy; // 생성자

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt; // 수정일시

    @LastModifiedBy
    @Column(nullable = false, length = 100)
    private String modifiedBy; // 수정자

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public Article() {

    }

    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof Article article)) {
//            return false;
//        }
//
//        return id != null && id.equals(article.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return id.hashCode();
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Article article)) {
            return false;
        }
        return Objects.equals(id, article.id) && Objects.equals(userId, article.userId) && Objects.equals(title, article.title) && Objects.equals(content, article.content) && Objects.equals(
            hashtag, article.hashtag) && Objects.equals(articleComments, article.articleComments) && Objects.equals(createdAt, article.createdAt) && Objects.equals(createdBy, article.createdBy)
            && Objects.equals(modifiedAt, article.modifiedAt) && Objects.equals(modifiedBy, article.modifiedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, title, content, hashtag, articleComments, createdAt, createdBy, modifiedAt, modifiedBy);
    }
}
