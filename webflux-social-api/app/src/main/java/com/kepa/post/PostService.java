package com.kepa.post;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

interface PostService {

    Mono<String> create(PostInput postInput);

    Mono<Void> delete(String id);

    Flux<PostOutput> findAll();

    Mono<PostOutput> findById(String id);

    Mono<Post> update(String id, PostInput postInput);

    Mono<Post> likePost(String id);

    Mono<Post> addComment(String id, CommentInput commentInput);
}
