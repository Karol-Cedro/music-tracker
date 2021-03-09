package com.kcedro.musictracker;

import com.kcedro.musictracker.filters.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MusicTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicTrackerApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> filterRegistrationBean(){
        FilterRegistrationBean<AuthFilter> registrationBean=new FilterRegistrationBean<>();
        AuthFilter authFilter= new AuthFilter();
        registrationBean.setFilter(authFilter);
        registrationBean.addUrlPatterns("/api/songs/*");
        registrationBean.addUrlPatterns("/api/playlists/*");
        return registrationBean;
    }
}
