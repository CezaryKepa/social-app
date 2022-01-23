package com.kepa.post;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostTest {

    @Test
    void updateContent_shouldUpdatePostContent() {
        final Post post = new Post("before", "title");

        post.updateContent("after");

        assertThat(post.getContent()).isEqualTo("after");
        assertThat(post.getTitle()).isEqualTo("title");
    }

    @Test
    void incrementLikes_shouldIncrementLikes() {
        final Post post = new Post("content", "title");

        post.incrementLikes();

        assertThat(post.getLikes()).isEqualTo(1L);
    }
}
