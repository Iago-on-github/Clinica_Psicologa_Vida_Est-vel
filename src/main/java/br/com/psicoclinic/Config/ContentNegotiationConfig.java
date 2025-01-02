package br.com.psicoclinic.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class ContentNegotiationConfig implements WebMvcConfigurer {
    private static final MediaType MEDIA_TYPE_APPLICATION_YAML = MediaType.valueOf("application/x-yaml");
    private static final MediaType MEDIA_TYPE_APPLICATION_XML = MediaType.valueOf("application/xml");
    private static final MediaType MEDIA_TYPE_APPLICATION_JSON = MediaType.valueOf("application/json");

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorParameter(false)
                .ignoreAcceptHeader(false)
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                    .mediaType("json", MEDIA_TYPE_APPLICATION_JSON)
                    .mediaType("xml", MEDIA_TYPE_APPLICATION_XML)
                    .mediaType("x-yaml", MEDIA_TYPE_APPLICATION_YAML);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new YamlJackson2HttpMessageConverter());
    }
}
