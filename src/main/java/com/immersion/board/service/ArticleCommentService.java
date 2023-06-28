package com.immersion.board.service;

import com.immersion.board.domain.Article;
import com.immersion.board.domain.ArticleComment;
import com.immersion.board.domain.UserAccount;
import com.immersion.board.dto.ArticleCommentDto;
import com.immersion.board.repository.ArticleCommentRepository;
import com.immersion.board.repository.ArticleRepository;
import com.immersion.board.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentService {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComments(Long articleId) {
        return articleCommentRepository.findByArticle_Id(articleId)
                .stream()
                .map(ArticleCommentDto::from)
                .toList();
    }

    public void saveArticleComment(ArticleCommentDto dto) {
        try {
            Article article = articleRepository.getReferenceById(dto.articleId());
            UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());
            articleCommentRepository.save(dto.toEntity(article, userAccount));
        } catch (EntityNotFoundException e) {
            log.warn("댓글 저장 실패. 댓글 작성에 필요한 정보를 찾을 수 없습니다. - {}", e.getLocalizedMessage());
        }
    }

    public void updateArticleComment(ArticleCommentDto dto) {
        try {
            ArticleComment comment = articleCommentRepository.getReferenceById(dto.id());
            if (StringUtils.hasText(dto.content())) { comment.setContent(dto.content()); }
        } catch (EntityNotFoundException e) {
            log.warn("수정하려는 댓글을 찾을 수 없습니다.");
        }

    }

    public void deleteArticleComment(Long articleCommentId) {
        articleCommentRepository.deleteById(articleCommentId);
    }
}
