package com.immersion.board.domain;

import com.immersion.board.dto.ArticleCommentDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

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
    @Column(updatable = false)
    private Long parentCommentId;

    @ToString.Exclude
    @OrderBy("createdAt ASC")
    @OneToMany(mappedBy = "parentCommentId", cascade = CascadeType.ALL)
    private Set<ArticleComment> childComments = new LinkedHashSet<>();

    @Setter
    @Column(nullable = false, length = 500)
    private String content;

    protected ArticleComment() {}

    private ArticleComment(Article article, String content, Long parentCommentId, UserAccount userAccount) {
        this.article = article;
        this.userAccount = userAccount;
        this.parentCommentId = parentCommentId;
        this.content = content;
    }

    public static ArticleComment of(Article article, String content, UserAccount userAccount) {
        return new ArticleComment(article, content, null, userAccount);
    }

    public void addChildComment(ArticleComment articleComment) {
        this.getChildComments().add(articleComment);
        articleComment.setParentCommentId(this.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
