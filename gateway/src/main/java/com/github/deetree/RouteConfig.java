package com.github.deetree;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

/**
 * @author Mariusz Bal
 */
@Configuration
class RouteConfig {

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-route", configureAuthRoutes())
                .route("signin-route", configureSignInRoutes())
                .route("register-route", configureRegisterRoutes())
                .route("rooms-route", configureRoomsRoutes())
                .route("game-route", configureGameRoutes())
                .build();
    }

    private Function<PredicateSpec, Buildable<Route>> configureAuthRoutes() {
        return p -> p.path("/api/auth/**")
                .uri("http://auth:9090");
    }

    private Function<PredicateSpec, Buildable<Route>> configureSignInRoutes() {
        return p -> p.path("/signin", "/api/signin", "/js/signin.js",
                        "/css/signin.css", "/error/404.html")
                .uri("http://login:9010");
    }

    private Function<PredicateSpec, Buildable<Route>> configureRegisterRoutes() {
        return p -> p.path("/register", "/api/register", "/js/registration.js",
                        "/css/registration.css", "/error/404.html")
                .uri("http://register:9020");
    }

    private Function<PredicateSpec, Buildable<Route>> configureRoomsRoutes() {
        return p -> p.path("/rooms/**", "/room", "/api/rooms/**", "/js/rooms.js", "/js/room.js",
                        "/css/rooms.css", "/css/room.css", "/webjars/**", "/rooms-websocket/**")
                .uri("http://rooms:8090");
    }

    private Function<PredicateSpec, Buildable<Route>> configureGameRoutes() {
        return p -> p.path("/games/**", "/gameplays/**", "/api/games/**", "/js/game.js",
                        "/css/game.css", "/webjars/**", "/games-websocket/**")
                .uri("http://game:9000");
    }
}
