package com.kepa.post;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.PATCH;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Component
class PostRouter {
    @Bean
    RouterFunction<ServerResponse> routes(com.kepa.post.PostHandler postHandler) {
        return RouterFunctions
                .route(DELETE("/api/posts/{id}")
                        , postHandler::delete)
                .andRoute(GET("/api/posts")
                        , postHandler::findAll)
                .andRoute(GET("/api/posts/{id}")
                        , postHandler::findById)
                .andRoute(POST("/api/posts").and(accept(MediaType.APPLICATION_JSON))
                        , postHandler::create)
                .andRoute(PATCH("/api/posts/{id}/comments").and(accept(MediaType.APPLICATION_JSON))
                        , postHandler::addComment)
                .andRoute(PUT("/api/posts/{id}").and(accept(MediaType.APPLICATION_JSON))
                        , postHandler::update)
                .andRoute(PATCH("/api/posts/{id}/like").and(accept(MediaType.APPLICATION_JSON))
                        , postHandler::likePost);

    }
}
