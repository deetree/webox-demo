package com.github.deetree;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @author Mariusz Bal
 */
@Configuration
class ErrorPageConfig implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        factory.addErrorPages(
                new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.html")
        );
    }
}
