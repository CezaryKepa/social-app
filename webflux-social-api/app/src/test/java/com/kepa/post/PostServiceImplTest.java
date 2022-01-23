package com.kepa.post;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.lang.reflect.Field;

import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private PostOutputMapper postOutputMapper;
    @Mock
    private PostMapper postMapper;
    @InjectMocks
    private PostServiceImpl postService;


    @Test
    void create_shouldCallPostMapperAndPostRepositorySave() throws NoSuchFieldException, IllegalAccessException {
        final Post post = new Post("test", "title");
        final String id = "61e5d325f549203ea607a730";
        setPostId(post, id);
        final PostInput postInput = new PostInput("test", "title");
        given(postRepository.save(any()))
                .willReturn(Mono.just(post));
        given(postMapper.apply(any()))
                .willReturn(post);

        final Mono<String> create = postService.create(postInput);

        StepVerifier
                .create(create)
                .expectNext(id)
                .verifyComplete();
        then(postMapper).should().apply(postInput);
        then(postRepository).should().save(post);
    }

    @Test
    void delete_shouldCallPostRepositoryDelete() {
        final String id = "61e5d325f549203ea607a730";
        given(postRepository.deleteById(any()))
                .willReturn(Mono.empty());

        final Mono<Void> delete = postService.delete(id);

        StepVerifier
                .create(delete)
                .verifyComplete();
        then(postRepository).should().deleteById(id);
    }

    @Test
    void findAll_shouldCallPostOutputMapperAndPostRepositoryFindAll() throws NoSuchFieldException, IllegalAccessException {
        final Post content1 = new Post("content1", "title");
        final Post content2 = new Post("content2", "title");
        setPostId(content1, "61e5d325f549203ea607a730");
        setPostId(content2, "81e5d325f549203ea607a730");
        given(postRepository.findAll())
                .willReturn(Flux.just(content1, content2));
        final PostOutput postOutput1 = new PostOutput("61e5d325f549203ea607a730", "content1", "title1",0L, emptyList());
        final PostOutput postOutput2 = new PostOutput("81e5d325f549203ea607a730", "content2", "title2",0L, emptyList());
        given(postOutputMapper.apply(any()))
                .willReturn(postOutput1).willReturn(postOutput2);

        final Flux<PostOutput> all = postService.findAll();

        StepVerifier
                .create(all)
                .expectNext(postOutput1)
                .expectNext(postOutput2)
                .verifyComplete();
        then(postRepository).should().findAll();
        then(postOutputMapper).should().apply(content1);
        then(postOutputMapper).should().apply(content2);
    }

    @Test
    void findById_shouldCallPostOutputMapperAndPostRepositoryFindById() throws NoSuchFieldException, IllegalAccessException {
        final Post content = new Post("content1", "title");
        final String id = "61e5d325f549203ea607a730";
        setPostId(content, id);
        given(postRepository.findById(any()))
                .willReturn(Mono.just(content));
        final PostOutput postOutput = new PostOutput("61e5d325f549203ea607a730", "content1", "title1",0L, emptyList());
        given(postOutputMapper.apply(any()))
                .willReturn(postOutput);

        final Mono<PostOutput> byId = postService.findById(id);

        StepVerifier
                .create(byId)
                .expectNext(postOutput)
                .verifyComplete();
        then(postRepository).should().findById(id);
        then(postOutputMapper).should().apply(content);
    }

    @Test
    void update_shouldCallPostRepositoryFindByIdAndPostRepositorySave() throws NoSuchFieldException, IllegalAccessException {
        final PostInput postInput = new PostInput("after", "title");
        final Post after = new Post("after", "title");
        final Post before = new Post("before", "title");
        final String id = "61e5d325f549203ea607a730";
        setPostId(after, id);
        setPostId(before, id);
        given(postRepository.findById(any()))
                .willReturn(Mono.just(before));
        given(postRepository.save(any()))
                .willReturn(Mono.just(after));

        final Mono<Post> update = postService.update(id, postInput);

        StepVerifier
                .create(update)
                .expectNext(after)
                .verifyComplete();
        then(postRepository).should().findById(id);
        then(postRepository).should().save(after);
    }

    private void setPostId(final Post post, final String id) throws NoSuchFieldException, IllegalAccessException {
        Field idField = post.getClass()
                .getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(post, id);
    }
}
