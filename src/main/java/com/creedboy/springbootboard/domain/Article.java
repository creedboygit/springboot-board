package com.creedboy.springbootboard.domain;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
//@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
//@Builder
@Table(
//    name = "article",
    indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashTag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
    })
@Entity
public class Article extends BaseEntity {

    // TODO : creed - 2023-08-20 - 소스 주석 정리

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //        @JoinColumn(name = "user_id", referencedColumnName = "userId")
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    @JoinColumn(name = "user_id")
//    @JoinColumn(name = "userAccount_id")
//    @ManyToOne(optional = false, fetch = FetchType.LAZY)
//    @ManyToOne(optional = false)
    @Setter
    @JoinColumn(name = "user_account_id")
    @ManyToOne(optional = false)
    private UserAccount userAccount; // 유저 정보 (ID)

//    @Column
//    private String userId; // userId

    @Setter
    @Column(nullable = false)
    private String title; // 제목

    @Setter
    @Column(nullable = false, length = 10000)
    private String content; // 내용

    @Setter
    private String hashtag; // 해시태그

    @ToString.Exclude
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    public Article() {
    }

    private Article(UserAccount userAccount, String title, String content, String hashtag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(UserAccount userAccount, String title, String content, String hashtag) {
        return new Article(userAccount, title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Article that)) {
            return false;
        }

//        return id.equals(article.id);
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
//        return id.hashCode();
        return Objects.hash(this.getId());
    }
}
