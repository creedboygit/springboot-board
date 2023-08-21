package com.creedboy.springbootboard.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.creedboy.springbootboard.config.SecurityConfig;
import com.creedboy.springbootboard.dto.ArticleCommentDto;
import com.creedboy.springbootboard.dto.ArticleWithCommentsDto;
import com.creedboy.springbootboard.dto.UserAccountDto;
import com.creedboy.springbootboard.service.ArticleService;
import com.creedboy.springbootboard.service.PagenationService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("view 컨트롤러 - 게시글")
@Import(SecurityConfig.class)
@WebMvcTest({ArticleController.class})
class ArticleControllerTest {

    //    @Autowired
    private final MockMvc mvc;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private PagenationService pagenationService;

    public ArticleControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[view] [GET] 게시글 리스트 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticlesView_thenReturnsArticlesView() throws Exception {

        // Given
        given(articleService.searchArticles(eq(null), eq(null), any(Pageable.class))).willReturn(Page.empty());
        // 페이징
        given(pagenationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0, 1, 2, 3, 4));

        // When & Then
        mvc.perform(get("/articles"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(model().attributeExists("articles"))
            .andExpect(model().attributeExists("pagenationBarNumbers"))
            .andExpect(view().name("articles/index"));
//            .andExpect(view().name("searchTypes"));

        then(articleService).should().searchArticles(eq(null), eq(null), any(Pageable.class));
        then(pagenationService).should().getPaginationBarNumbers(anyInt(), anyInt());
    }

    @DisplayName("[view] [GET] 게시글 리스트 (게시판) 페이지 - 페이징, 정렬 기능")
    @Test
    public void givenPagingAndSortingParams_whenSearchingArticlesPage_thenReturnsArticlesPage() throws Exception {

        // Given
        String sortName = "title";
        String direction = "desc";
        int pageNumber = 0;
        int pageSize = 5;

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.desc(sortName)));
        List<Integer> barNumbers = List.of(1, 2, 3, 4, 5);

        given(articleService.searchArticles(null, null, pageable)).willReturn(Page.empty());
        given(pagenationService.getPaginationBarNumbers(pageable.getPageNumber(), Page.empty().getTotalPages())).willReturn(barNumbers);

        // When & Then
        mvc.perform(
                get("/articles")
                    .queryParam("page", String.valueOf(pageNumber))
                    .queryParam("size", String.valueOf(pageSize))
                    .queryParam("sort", sortName + "," + direction))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("articles/index"))
            .andExpect(model().attributeExists("articles"))
            .andExpect(model().attribute("pagenationBarNumbers", barNumbers));

        then(articleService).should().searchArticles(null, null, pageable);
        then(pagenationService).should().getPaginationBarNumbers(pageable.getPageNumber(), Page.empty().getTotalPages());
    }

    @DisplayName("[view] [GET] 게시글 상세 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticleView_thenReturnsArticleView() throws Exception {

        // Given
        Long articleId = 1L;
        given(articleService.getArticle(articleId)).willReturn(createArticleWithCommentDto());

        // When & Then
        mvc.perform(get("/articles/" + articleId))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(model().attributeExists("article"))
            .andExpect(model().attributeExists("articleComments"))
            .andExpect(view().name("articles/detail"));

        then(articleService).should().getArticle(articleId);
    }

    @Disabled("구현 중")
    @DisplayName("[view] [GET] 게시글 검색 전용 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticleSearchView_thenReturnsArticlesView() throws Exception {

        // Given

        // When & Then
        mvc.perform(get("/articles/search"))
            .andExpect(status().isOk())
            .andExpect(view().name("articles/search"))
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Disabled("구현 중")
    @DisplayName("[view] [GET] 게시글 해시태그 검색 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticleHashtagSearchView_thenReturnsArticlesView() throws Exception {

        // Given

        // When & Then
        mvc.perform(get("/articles/search-hashtag"))
            .andExpect(status().isOk())
            .andExpect(view().name("articles/hashtag"))
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    private ArticleWithCommentsDto createArticleWithCommentDto() {
        return ArticleWithCommentsDto.of(
            1L,
            createUserAccountDto(),
//            Set.of(),
            createArticleCommentDto(),
            "title",
            "content",
            "#java",
            LocalDateTime.now(),
            "creed",
            LocalDateTime.now(),
            "creed"
        );
    }

    private Set<ArticleCommentDto> createArticleCommentDto() {
        return Set.of(
            ArticleCommentDto.of(
                1L,
                createUserAccountDto(),
                "content입니다1"
            ),
            ArticleCommentDto.of(
                1L,
                createUserAccountDto(),
                "content입니다2"
            )
        );
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
            1L,
            "userid",
            "pw",
            "creed@creed.com",
            "nick",
            "memo",
            LocalDateTime.now(),
            "creed",
            LocalDateTime.now(),
            "creed"
        );
    }
}