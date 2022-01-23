package com.kepa.post;

import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
class PostOutputMapper implements Function<Post, PostOutput> {
    @Override
    public PostOutput apply(Post post) {
        return new PostOutput(
                post.getId(),
                post.getContent(),
                post.getTitle(),
                post.getLikes(),
                post.getComments().stream()
                        .map(comment -> new CommentOutput(comment.getContent()))
                        .collect(Collectors.toList())
        );
    }
}
