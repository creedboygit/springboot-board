package com.creedboy.springbootboard.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(
//    name = "article_comment",
    indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
    })
@Entity
public class ArticleComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @Setter
//    @JoinColumn(name = "user_id")
//    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount; // 유저 정보 (ID)

    @Setter
    @ManyToOne(optional = false)
    private Article article; // 게시글 (ID)

    @Setter
    @Column(nullable = false, length = 500)
    private String content; // 내용

    private ArticleComment(Article article, UserAccount userAccount, String content) {

        this.article = article;
        this.userAccount = userAccount;
        this.content = content;
    }

    public static ArticleComment of(Article article, UserAccount userAccount, String content) {

        return new ArticleComment(article, userAccount, content);
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof ArticleComment that)) {
//            return false;
//        }
//        return Objects.equals(id, that.id) && Objects.equals(userAccount, that.userAccount) && Objects.equals(article, that.article) && Objects.equals(content, that.content);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, userAccount, article, content);
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticleComment that)) {
            return false;
        }

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
