package cz.muni.fi.pa165.plpm.service.config;

import cz.muni.fi.pa165.plpm.PersistenceSampleApplicationContext;
import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import org.dozer.Mapper;

import java.util.Arrays;

@Configuration
@Import(PersistenceSampleApplicationContext.class)
@ComponentScan(basePackages = "cz.muni.fi.pa165.plpm.service")
public class ServiceConfiguration {


    @Bean
    public Mapper dozer() {
        DozerBeanMapper dozer = new DozerBeanMapper();
//        dozer.setMappingFiles(Arrays.asList("dozer.xml")); //for possible custom converters
        return dozer;
    }
}