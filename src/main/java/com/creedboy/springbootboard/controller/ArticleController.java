package com.creedboy.springbootboard.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/articles")
@Controller
public class ArticleController {

    @GetMapping
    public String articles(ModelMap map) {

        map.addAttribute("articles", List.of());

        return "articles/index";
    }

    @GetMapping("/{articldId}")
    public String article(@PathVariable Long articldId, ModelMap map) {

        map.addAttribute("article", "article"); // TODO : 데이터 추가
        map.addAttribute("articleComments", List.of());

        return "articles/detail";
    }
}
