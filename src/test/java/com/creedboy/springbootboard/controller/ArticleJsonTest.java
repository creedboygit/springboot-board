package com.creedboy.springbootboard.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.creedboy.springbootboard.domain.Article;
import com.creedboy.springbootboard.domain.UserAccount;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("local")
@Slf4j
//@SpringBootTest
@JsonTest
//@Import(JpaConfig.class)
public class ArticleJsonTest {

    @Autowired
    private JacksonTester<Article> json;

    @Test
    void testSerialize() throws IOException {

        UserAccount userAccount = UserAccount.of("creedboy", "a123123", "creed@creed.com", "nick", "memos", "createdby");

        Article article = Article.of(
            userAccount,
            "타이틀",
            "내용",
            "해시태그"
        );

//        Article article = Article.builder()
//            .content("content1")
//            .title("title1")
//            .createdAt(Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime())
//            .modifiedAt(Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime())
//            .createdBy("creed")
//            .modifiedBy("creed")
//            .build();

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(article);
//        System.out.println("jsonStr = " + jsonStr);
        log.debug("# jsonStr: {}", jsonStr);

        assertThat(json.write(article)).hasJsonPathStringValue("@.title");

        assertThat(json.write(article))
            .extractingJsonPathStringValue("@.title")
            .isEqualTo("타이틀");
    }

    @Test
    void testDeserialize() throws IOException {

        UserAccount userAccount = UserAccount.of("creedboy", "a123123", "creed@creed.com", "nick", "memos", "createdby");

        Article article = Article.of(
            userAccount,
            "타이틀",
            "내용",
            "해시태그"
        );

        String jsonString = "{\"id\":null,\"userId\":null,\"title\":\"타이틀\",\"content\":\"내용\",\"hashtag\":\"해시태그\",\"articleComments\":[],\"createdAt\":null,\"createdBy\":null,\"modifi edAt\":null,\"modifiedBy\":null}";

        log.debug("# json: {}", json.parse(jsonString));
        log.debug("# json: {}", json.parseObject(jsonString).getContent());

        assertThat(json.parse(jsonString)).isEqualTo(article);
        assertThat(json.parseObject(jsonString).getContent()).isEqualTo("내용");
    }
}
