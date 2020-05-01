package cz.muni.fi.pa165.plpm.sampledata;

import cz.muni.fi.pa165.plpm.service.config.ServiceConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

@Configuration
@Import(ServiceConfiguration.class)
@ComponentScan(basePackages = "cz.muni.fi.pa165.plpm.sampledata")
public class SampleDataConfiguration {

    @PostConstruct
    public void loadData() {
        //TODO
    }
}
