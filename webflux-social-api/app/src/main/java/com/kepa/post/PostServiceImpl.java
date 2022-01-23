package com.kepa.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostOutputMapper postOutputMapper;
    private final PostMapper postMapper;

    @Override
    public Mono<String> create(PostInput postInput) {
        return postRepository.save(postMapper.apply(postInput)).map(Post::getId);
    }

    @Override
    public Mono<Void> delete(String id) {
        return postRepository.deleteById(id);
    }

    @Override
    public Flux<PostOutput> findAll() {
        return postRepository.findAll().map(postOutputMapper);
    }

    @Override
    public Mono<PostOutput> findById(String id) {
        return postRepository.findById(id).map(postOutputMapper);
    }

    @Override
    public Mono<Post> update(String id, PostInput postInput) {
        return postRepository.findById(id)
                .flatMap(post -> {
                    post.updateContent(postInput.content());
                    post.updateTitle(postInput.title());
                    return postRepository.save(post);
                });
    }

    @Override
    public Mono<Post> likePost(String id) {
        return postRepository.findById(id)
                .flatMap(post -> {
                    post.incrementLikes();
                    return postRepository.save(post);
                });
    }

    @Override
    public Mono<Post> addComment(String id, CommentInput commentInput) {
        return postRepository.findById(id)
                .flatMap(post -> {
                    post.addComment(commentInput.content());
                    return postRepository.save(post);
                });
    }
}
