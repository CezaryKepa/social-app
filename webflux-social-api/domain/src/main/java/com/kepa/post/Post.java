package com.kepa.post;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@NoArgsConstructor
@Document
class Post {
    @Id
    private String id;
    private String title;
    private String content;
    private Long likes;
    private List<Comment> comments = new ArrayList<>();


    Post(String content, String title) {
        this.content = content;
        this.title = title;
        this.likes = 0L;
    }

    void updateContent(String content) {
        this.content = content;
    }

    void updateTitle(String title) {
        this.title = title;
    }

    void incrementLikes() {
        likes++;
    }

    void addComment(String content) {
        comments.add(new Comment(content));
    }

    @Getter
    static class Comment {
        private String content;

        Comment(final String content) {
            this.content = content;
        }
    }
}
