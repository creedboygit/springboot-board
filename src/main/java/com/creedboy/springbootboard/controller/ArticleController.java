package com.creedboy.springbootboard.controller;

import com.creedboy.springbootboard.domain.constant.SearchType;
import com.creedboy.springbootboard.dto.ArticleDto;
import com.creedboy.springbootboard.dto.ArticleWithCommentsDto;
import com.creedboy.springbootboard.dto.response.ArticleResponse;
import com.creedboy.springbootboard.dto.response.ArticleWithCommentsResponse;
import com.creedboy.springbootboard.service.ArticleService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public String articles(
        @RequestParam(required = false) SearchType searchType,
        @RequestParam(required = false) String searchValue,
        @PageableDefault(size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable,
        ModelMap map
    ) {
        Page<ArticleDto> articleDtos = articleService.searchArticles(searchType, searchValue, pageable);
        Page<ArticleResponse> articleDtosMap = articleDtos.map(ArticleResponse::from);

        map.addAttribute("articles", articleDtosMap);

        return "articles/index";
    }

    @GetMapping("/{articldId}")
    public String article(@PathVariable Long articldId, ModelMap map) {

        ArticleWithCommentsDto article = articleService.getArticle(articldId);
        ArticleWithCommentsResponse articlesResponse = ArticleWithCommentsResponse.from(article);

//        map.addAttribute("article", "article");
        map.addAttribute("article", articlesResponse);
        map.addAttribute("articleComments", List.of());

        return "articles/detail";
    }
}
