package com.example.mytestproject.dto;


import com.example.mytestproject.entity.Article;
import com.example.mytestproject.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.JoinColumn;
import lombok.*;


@NoArgsConstructor
@ToString
@Getter
@Setter
public class CommentDto {
    private Long id;
    @JsonProperty("article_id")
    private Long articleId;
    private String nickname;
    private String body;

    public CommentDto(Long id, Long articleId, String nickname, String body) {
        this.id = id;
        this.articleId = articleId;
        this.nickname = nickname;
        this.body = body;
    }

    public static CommentDto createCommentDto(Comment c) {
        return new CommentDto(
                c.getId(),
                c.getArticle().getId(),
                c.getNickname(),
                c.getBody()
        );
    }

//    public Long getId() {
//        return id;
//    }
//
//    public Long getArticleId() {
//        return articleId;
//    }
//
//    public String getNickname() {
//        return nickname;
//    }
//
//    public String getBody() {
//        return body;
//    }
}
