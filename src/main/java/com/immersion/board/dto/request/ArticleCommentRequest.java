package com.immersion.board.dto.request;

import com.immersion.board.dto.ArticleCommentDto;
import com.immersion.board.dto.UserAccountDto;

public record ArticleCommentRequest(
        Long articleId,
        String content
) {

    public static ArticleCommentRequest of (Long ArticleId, String content) {
        return new ArticleCommentRequest(ArticleId, content);
    }

    public ArticleCommentDto toDto(UserAccountDto accountDto) {
        return ArticleCommentDto.of(
                articleId,
                accountDto,
                content
        );
    }
}
