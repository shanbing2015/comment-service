package top.shanbing.webflux;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class RouterFunctionConf {

    @Bean
    public RouterFunction<ServerResponse> timerRouter() {
        Mono<ServerResponse>  responseMono = ok().contentType(MediaType.TEXT_PLAIN).body(Mono.just("Index " + LocalDateTime.now()), String.class);

        Mono<ServerResponse>  responseMonoStream = ok().contentType(MediaType.TEXT_EVENT_STREAM).body(
                Flux.interval(Duration.ofSeconds(1)).
                        map(l -> "Index " + LocalDateTime.now() ),
                String.class);
        System.out.println("");
        return route(GET("/"), req -> responseMono);
    }
}
