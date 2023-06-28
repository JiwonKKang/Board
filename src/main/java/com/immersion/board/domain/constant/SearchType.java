package com.immersion.board.domain.constant;

import lombok.Getter;

public enum SearchType {
    TITLE("제목"),
    ID("유저ID"),
    NICKNAME("닉네임"),
    HASHTAG("해시태그"),
    CONTENT("내용");

    @Getter private final String description;

    SearchType(String description) {
        this.description = description;
    }
}
