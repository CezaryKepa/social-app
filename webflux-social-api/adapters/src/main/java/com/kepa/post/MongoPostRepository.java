package com.kepa.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

interface MongoPostRepository extends ReactiveMongoRepository<Post, String> {
}

@Repository
@RequiredArgsConstructor
class PostRepositoryImpl implements PostRepository {
    private final MongoPostRepository mongoPostRepository;

    @Override
    public Mono<Void> deleteById(String id) {
        return mongoPostRepository.deleteById(id);
    }

    @Override
    public Flux<Post> findAll() {
        return mongoPostRepository.findAll();
    }

    @Override
    public Mono<Post> save(Post post) {
        return mongoPostRepository.save(post);
    }

    @Override
    public Mono<Post> findById(String id) {
        return mongoPostRepository.findById(id);
    }
}
