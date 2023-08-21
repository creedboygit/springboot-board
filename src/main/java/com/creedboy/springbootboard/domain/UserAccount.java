package com.creedboy.springbootboard.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
//@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
//@Builder
@Table(
    indexes = {
        @Index(columnList = "userId", unique = true),
        @Index(columnList = "email", unique = true),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
    })
@Entity
public class UserAccount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    @JoinColumn(name = "user_account_id")
    @Column(name = "user_account_id")
    private Long id;

    @Column(length = 50)
    private String userId;

    @Column(nullable = false)
    private String userPassword;

    @Column(length = 100)
    private String email;

    @Column(length = 100)
    private String nickname;

    private String memo;

    public UserAccount() {
    }

//    private UserAccount(String userId, String userPassword, String email, String nickname, String memo) {
//        this.userId = userId;
//        this.userPassword = userPassword;
//        this.email = email;
//        this.nickname = nickname;
//        this.memo = memo;
//    }

    public static UserAccount of(Long id,
        String userId,
        String userPassword,
        String email,
        String nickname,
        String memo) {
        return new UserAccount(id, userId, userPassword, email, nickname, memo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAccount that)) {
            return false;
        }
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(userPassword, that.userPassword) && Objects.equals(email, that.email) && Objects.equals(
            nickname, that.nickname) && Objects.equals(memo, that.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, userPassword, email, nickname, memo);
    }
}
