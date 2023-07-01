package com.immersion.board.controller;

import com.immersion.board.dto.request.ArticleCommentRequest;
import com.immersion.board.dto.security.BoardPrincipal;
import com.immersion.board.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/comments")
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    public String postNewArticleComment(ArticleCommentRequest articleCommentRequest,
                                        @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
        articleCommentService.saveArticleComment(articleCommentRequest.toDto(boardPrincipal.toDto()));
        return "redirect:/articles/" + articleCommentRequest.articleId();
    }

    @PostMapping("/{commentId}/delete")
    public String deleteArticleComment(@PathVariable Long commentId,
                                       Long articleId,
                                       @AuthenticationPrincipal BoardPrincipal boardPrincipal) {
        String userId = boardPrincipal.getUsername();
        articleCommentService.deleteArticleComment(commentId, userId);
        return "redirect:/articles/" + articleId;
    }
}
