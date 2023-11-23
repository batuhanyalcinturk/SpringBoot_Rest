package com.graysan.springbootrest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
//@Lazy
public class BeanFactory
{
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
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
