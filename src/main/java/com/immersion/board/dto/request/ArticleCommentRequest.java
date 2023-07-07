package com.immersion.board.dto.request;

import com.immersion.board.dto.ArticleCommentDto;
import com.immersion.board.dto.UserAccountDto;

public record ArticleCommentRequest(
        Long articleId,
        Long parentCommentId,
        String content
) {

    public static ArticleCommentRequest of(Long ArticleId, String content) {
        return new ArticleCommentRequest(ArticleId,null, content);
    }

    public static ArticleCommentRequest of(Long ArticleId, Long parentCommentId, String content) {
        return new ArticleCommentRequest(ArticleId, parentCommentId, content);
    }

    public ArticleCommentDto toDto(UserAccountDto accountDto) {
        return ArticleCommentDto.of(
                articleId,
                accountDto,
                parentCommentId,
                content
        );
    }
}
