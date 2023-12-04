package com.graysan.springbootrest.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Component
//@Lazy
public class BeanFactory
{

    @Bean
    public LocaleResolver localeResolver(){
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }

    @Bean
    public ResourceBundleMessageSource bundleMessageSource()
    {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultLocale(null);
        return messageSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OpenAPI springShopOpenAPI()
    {
        return new OpenAPI().info(new Info()
                .title("OBS - Öğrenci Bilgi Sistemi")
                .description("OBS application")
                .version("V1.0")
                .license(new License().name("Apache 2.0")
                        .url("localhost:8080/license")))
                .externalDocs(new ExternalDocumentation().description("OBS rest dökümantasyonu")
                        .url("localhost:8080/externaldocs"));
    }

//	@Bean
//	public Person person()
//	{
//		return new Person("yusuf");
//	}
}

class Person
{
    private String name;

    public Person(String name)
    {
        super();
        this.name = name;
    }
}
