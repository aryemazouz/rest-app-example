package com.rest.app;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.app.model.Conf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.io.IOException;

@Slf4j
@SpringBootApplication
@PropertySource(ignoreResourceNotFound = true, value = "classpath:/application.properties")
public class App {
    private String appConfPath;
    private int cacheSize;

    /**
     * Constructor
     * @param confPath
     */
    public App(
            @Value("${rest.app.conf}") String confPath,
            @Value("${rest.app.cache.size}") int cacheSize) {
        this.appConfPath = confPath;
        this.cacheSize = cacheSize;
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AttackService attackService(ApplicationArguments arguments) throws IOException {

        //
        //Try input argument first
        if(arguments.getSourceArgs().length > 0){
            appConfPath = arguments.getSourceArgs()[0];
            try{
                if(! new File(appConfPath).exists()){
                    appConfPath = null;
                }
            }
            catch (Exception ex){
                log.info("First command line argument is not application conf file or file doesn't exists");
                appConfPath = null;
            }
        }

        if(appConfPath == null || appConfPath.trim().isEmpty()){
            throw new IllegalArgumentException("No app conf file found in (first) command line argument nor REST_APP_CONF ENV variable nor rest.app.conf value in application properties file");
        }

        ObjectMapper mapper = new ObjectMapper();
        Conf conf = mapper.readValue(new File(appConfPath), Conf.class);
        return new AttackService(conf, cacheSize);
    }

    /**
     * Main
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
