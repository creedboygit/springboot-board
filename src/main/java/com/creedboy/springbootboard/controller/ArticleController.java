package com.creedboy.springbootboard.controller;

import com.creedboy.springbootboard.domain.constant.FormStatus;
import com.creedboy.springbootboard.domain.constant.SearchType;
import com.creedboy.springbootboard.dto.ArticleDto;
import com.creedboy.springbootboard.dto.ArticleWithCommentsDto;
import com.creedboy.springbootboard.dto.UserAccountDto;
import com.creedboy.springbootboard.dto.request.ArticleRequest;
import com.creedboy.springbootboard.dto.response.ArticleResponse;
import com.creedboy.springbootboard.dto.response.ArticleWithCommentsResponse;
import com.creedboy.springbootboard.service.ArticleService;
import com.creedboy.springbootboard.service.PaginationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {

    private final ArticleService articleService;

    private final PaginationService paginationService;

    @GetMapping
    public String articles(
        @RequestParam(required = false) SearchType searchType,
        @RequestParam(required = false) String searchValue,
        @PageableDefault(size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable,
        ModelMap map
    ) {
        Page<ArticleDto> articleDtos = articleService.searchArticles(searchType, searchValue, pageable);
        Page<ArticleResponse> articles = articleDtos.map(ArticleResponse::from);

        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());

        map.addAttribute("articles", articles);
        map.addAttribute("paginationBarNumbers", barNumbers);
        map.addAttribute("searchTypes", SearchType.values());

        return "articles/index";
    }

    @GetMapping("/{articldId}")
    public String article(@PathVariable Long articldId, ModelMap map) {

        ArticleWithCommentsDto article = articleService.getArticleWithComments(articldId);
        ArticleWithCommentsResponse articlesResponse = ArticleWithCommentsResponse.from(article);

//        map.addAttribute("article", "article");
        map.addAttribute("article", articlesResponse);
        map.addAttribute("articleComments", articlesResponse.articleCommentsResponse());

        return "articles/detail";
    }

    @GetMapping("/search-hashtag")
    public String searchArticleHashtag(
        @RequestParam(required = false) String searchValue,
        @PageableDefault(size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable,
        ModelMap map
    ) {
        Page<ArticleResponse> articles = articleService.searchArticlesViaHashtag(searchValue, pageable).map(ArticleResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());
        List<String> hashtags = articleService.getHashtags();

        map.addAttribute("articles", articles);
        map.addAttribute("hashtags", hashtags);
        map.addAttribute("paginationBarNumbers", barNumbers);
        map.addAttribute("searchType", SearchType.HASHTAG);

        return "articles/search-hashtag";
    }

    @GetMapping("/form")
    public String articleForm(ModelMap map) {

        map.addAttribute("formStatus", FormStatus.CREATE);

        return "articles/form";
    }

    @PostMapping("/form")
    public String postNewArticle(ArticleRequest articleRequest) {

        // TODO : creed - 2023-08-26 - 인증 정보를 넣어줘야 함.
        articleService.saveArticle(articleRequest.toDto(UserAccountDto.of(
            1L, "creed", "password", "creed@creed.com", "creed", "creed", null, null, null, null
        )));

        return "redirect:/articles";
    }
}
