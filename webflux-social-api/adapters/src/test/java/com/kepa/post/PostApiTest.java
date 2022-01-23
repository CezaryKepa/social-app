package com.kepa.post;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.util.Collections.emptyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PostRouter.class, PostHandler.class})
@WebFluxTest(controllers = PostRouter.class)
class PostApiTest {
    @Autowired
    private WebTestClient webClient;
    @MockBean
    private PostServiceImpl postServiceImpl;

    @Test
    void create_shouldCallPostServiceCreateAndReturnLocation() {
        final PostInput postInput = new PostInput("test", "title");
        final String id = "61e5d325f549203ea607a730";
        given(postServiceImpl.create(postInput)).willReturn(Mono.just(id));

        webClient.post()
                .uri("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(postInput))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().valueEquals("Location", "/posts/" + id);

        then(postServiceImpl).should().create(postInput);
    }

    @Test
    void delete_shouldCallPostServiceDeleteAndReturnNoContent() {
        final String id = "61e5d325f549203ea607a730";
        given(postServiceImpl.delete(id)).willReturn(Mono.empty());

        webClient.delete()
                .uri("/api/posts/" + id)
                .exchange()
                .expectStatus().isNoContent();

        then(postServiceImpl).should().delete(id);
    }

    @Test
    void findAll_shouldCallPostServiceFindAllAndReturnPosts() {
        final String id1 = "61e5d325f549203ea607a730";
        final String id2 = "73e5d325f549203ea607a730";
        given(postServiceImpl.findAll())
                .willReturn(Flux.just(
                        new PostOutput(id1, "content1","title1", 0L, emptyList()),
                        new PostOutput(id2, "content2", "title2",0L, emptyList())
                ));

        webClient.get()
                .uri("/api/posts")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(PostOutput.class)
                .contains(
                        new PostOutput(id1, "content1","title1", 0L, emptyList()),
                        new PostOutput(id2, "content2","title2", 0L, emptyList())
                );

        then(postServiceImpl).should().findAll();
    }

    @Test
    void findById_shouldCallPostServiceFindByIdAndReturnPost() {
        final String id = "61e5d325f549203ea607a730";
        final PostOutput postOutput = new PostOutput(id, "content1","title2", 0L, emptyList());
        given(postServiceImpl.findById(id)).willReturn(Mono.just(postOutput));

        webClient.get()
                .uri("/api/posts/" + id)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(PostOutput.class)
                .isEqualTo(postOutput);

        then(postServiceImpl).should().findById(id);
    }

    @Test
    void update_shouldCallPostServiceUpdateAndReturnNoContent() {
        final PostInput postInput = new PostInput("test", "title");
        final String id = "61e5d325f549203ea607a730";
        given(postServiceImpl.update(id, postInput)).willReturn(Mono.just(new Post("test", "title")));

        webClient.put()
                .uri("/api/posts/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(postInput))
                .exchange()
                .expectStatus().isNoContent();

        then(postServiceImpl).should().update(id, postInput);
    }
}
