package com.kepa.post;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

interface PostRepository {
    Mono<Void> deleteById(String id);

    Flux<Post> findAll();

    Mono<Post> findById(String id);

    Mono<Post> save(Post post);
}
