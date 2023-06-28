package com.immersion.board.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@ToString(callSuper = true)
@Table(indexes ={
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
public class ArticleComment extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @Setter
    private Article article;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    @Setter
    private UserAccount userAccount;

    @Setter
    @Column(nullable = false, length = 500)
    private String content;

    protected ArticleComment() {}

    private ArticleComment(Article article, String content, UserAccount userAccount) {
        this.article = article;
        this.content = content;
        this.userAccount = userAccount;
    }

    public static ArticleComment of(Article article, String content, UserAccount userAccount) {
        return new ArticleComment(article, content, userAccount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment that)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
