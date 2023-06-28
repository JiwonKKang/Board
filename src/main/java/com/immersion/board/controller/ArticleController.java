package com.immersion.board.controller;

import com.immersion.board.domain.type.SearchType;
import com.immersion.board.dto.ArticleWithCommentsDto;
import com.immersion.board.response.ArticleResponse;
import com.immersion.board.response.ArticleWithCommentResponse;
import com.immersion.board.service.ArticleService;
import com.immersion.board.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final PaginationService paginationService;

    @GetMapping
    public String articles(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map) {

        Page<ArticleResponse>  res = articleService.searchArticles(searchType, searchValue, pageable).map(ArticleResponse::from);
        List<Integer> paginationBarNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), res.getTotalPages());

        map.addAttribute("articles", res);
        map.addAttribute("paginationBarNumbers", paginationBarNumbers);
        map.addAttribute("searchTypes", SearchType.values());
        return "articles/index";
    }

    @GetMapping("/{articleId}")
    public String article(@PathVariable Long articleId, ModelMap map) {

        ArticleWithCommentsDto article = articleService.getArticle(articleId);
        ArticleWithCommentResponse res = ArticleWithCommentResponse.from(article);

        map.addAttribute("article", res);
        map.addAttribute("articleComments", res.articleCommentsResponse());

        return "articles/detail";
    }
}
