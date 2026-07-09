package hct.infante.marco.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${services.vehiculo.url}")
    private String vehiculoUrl;

    @Value("${services.cliente.url}")
    private String clienteUrl;

    @Bean
    public WebClient vehiculoWebClient() {
        return WebClient.builder().baseUrl(vehiculoUrl).build();
    }

    @Bean
    public WebClient clienteWebClient() {
        return WebClient.builder().baseUrl(clienteUrl).build();
    }
}