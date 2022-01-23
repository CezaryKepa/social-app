package com.kepa.post;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
class PostMapper implements Function<PostInput, Post> {
    @Override
    public Post apply(PostInput postInput) {
        return new Post(
                postInput.content(),
                postInput.title()
        );
    }
}
