package com.creedboy.springbootboard.service;

import com.creedboy.springbootboard.domain.Article;
import com.creedboy.springbootboard.domain.constant.SearchType;
import com.creedboy.springbootboard.dto.ArticleDto;
import com.creedboy.springbootboard.dto.ArticleWithCommentsDto;
import com.creedboy.springbootboard.repository.ArticleRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    public static final String HASHTAG_SHARP = "#";
    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {

        if (searchKeyword == null || StringUtils.isBlank(searchKeyword)) {
            return articleRepository.findAll(pageable).map(ArticleDto::from);
        }

        return switch (searchType) {

            case TITLE -> articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
            case CONTENT -> articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
            case ID -> articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(ArticleDto::from);
            case NICKNAME -> articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable).map(ArticleDto::from);
            case HASHTAG -> articleRepository.findByHashtag(HASHTAG_SHARP + searchKeyword, pageable).map(ArticleDto::from);
        };
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticle(Long articleId) {

        return articleRepository.findById(articleId)
            .map(ArticleWithCommentsDto::from)
            .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    public void saveArticle(ArticleDto dto) {
        articleRepository.save(dto.toEntity());
    }

    public void updateArticle(ArticleDto dto) {

        try {
            Article article = articleRepository.getReferenceById(dto.id());

            if (dto.title() != null) {
                article.setTitle(dto.title());
            }

            if (dto.content() != null) {
                article.setContent(dto.content());
            }

            article.setHashtag(dto.hashtag());

//        articleRepository.save(article);

        } catch (EntityNotFoundException e) {

            log.debug("======= 게시판 업데이트 실패: dto: {}", dto);
        }
    }

    public void deleteArticle(long articleId) {
        articleRepository.deleteById(articleId);
    }
}
