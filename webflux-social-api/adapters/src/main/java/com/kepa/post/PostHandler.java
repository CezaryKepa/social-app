package com.kepa.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
class PostHandler {
    private final PostServiceImpl postServiceImpl;

    public Mono<ServerResponse> likePost(ServerRequest serverRequest) {
        return postServiceImpl.likePost(serverRequest.pathVariable("id"))
                .flatMap(p -> ServerResponse.noContent().build());
    }

    Mono<ServerResponse> create(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(PostInput.class)
                .flatMap(postServiceImpl::create)
                .flatMap(uuid -> ServerResponse.created(URI.create("/posts/" + uuid)).build());
    }

    Mono<ServerResponse> delete(ServerRequest serverRequest) {
        return postServiceImpl.delete(serverRequest.pathVariable("id"))
                .then(ServerResponse.noContent().build());
    }

    Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(postServiceImpl.findAll(), PostOutput.class);
    }

    Mono<ServerResponse> findById(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(postServiceImpl.findById(serverRequest.pathVariable("id")), PostOutput.class);
    }

    Mono<ServerResponse> update(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(PostInput.class)
                .flatMap(postInput -> postServiceImpl.update(serverRequest.pathVariable("id"), postInput))
                .flatMap(p -> ServerResponse.noContent().build());
    }

    Mono<ServerResponse> addComment(final ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(CommentInput.class)
                .flatMap(commentInput -> postServiceImpl.addComment(serverRequest.pathVariable("id"), commentInput))
                .flatMap(p -> ServerResponse.noContent().build());
    }
}
