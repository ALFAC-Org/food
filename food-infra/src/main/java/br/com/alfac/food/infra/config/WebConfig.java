package br.com.alfac.food.infra.config;

import br.com.alfac.food.infra.interceptor.JwtAuthInterceptor;
import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final int MULTIPLICADOR_TIMEOUT = 1000;
    private final JwtAuthInterceptor jwtAuthInterceptor;

    @Value("${food.http.timeout}")
    private int timeout;

    @Autowired
    public WebConfig(JwtAuthInterceptor jwtAuthInterceptor) {
        this.jwtAuthInterceptor = jwtAuthInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/v1/clientes/**")
                .excludePathPatterns("/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs/**", "/webjars/**", "/api-docs/**");

    }

    @Bean
    public WebClient webClient() {

        return WebClient.builder()
                .clientConnector(
                        new ReactorClientHttpConnector(
                                HttpClient.create()
                                        .responseTimeout(Duration.ofSeconds(timeout))
                                        .tcpConfiguration(client ->
                                                client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout * MULTIPLICADOR_TIMEOUT))
                        )
                ).build();
    }
}