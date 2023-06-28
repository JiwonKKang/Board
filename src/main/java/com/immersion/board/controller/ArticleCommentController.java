package com.immersion.board.controller;

import com.immersion.board.dto.UserAccountDto;
import com.immersion.board.dto.request.ArticleCommentRequest;
import com.immersion.board.repository.ArticleCommentRepository;
import com.immersion.board.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/comments")
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    public String postNewArticleComment(ArticleCommentRequest articleCommentRequest) {
        //TODO Spring Security 로 인증기능 구현
        articleCommentService.saveArticleComment(articleCommentRequest.toDto(UserAccountDto.of(
                "uno", "pw", "kangji149@naver.com","uno","memo"
        )));
        return "redirect:/articles/" + articleCommentRequest.articleId();
    }

    @PostMapping("/{commentId}/delete")
    public String deleteArticleComment(@PathVariable Long commentId, Long articleId) {
        articleCommentService.deleteArticleComment(commentId);
        return "redirect:/articles/" + articleId;
    }
}
