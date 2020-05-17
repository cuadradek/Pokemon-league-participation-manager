package cz.muni.fi.pa165.plpm.rest.config;

import cz.muni.fi.pa165.plpm.sampledata.SampleDataConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Karolina Kolouchova
 */
@EnableWebMvc
@Configuration
@Import(SampleDataConfiguration.class)
@ComponentScan(basePackages = "cz.muni.fi.pa165.plpm.rest.controllers")
public class RestConfiguration implements WebMvcConfigurer {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        converters.add(new MappingJackson2HttpMessageConverter());
    }
}
