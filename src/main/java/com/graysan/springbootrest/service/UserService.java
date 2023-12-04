package com.graysan.springbootrest.service;

import com.graysan.springbootrest.model.SystemUser;
import com.graysan.springbootrest.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        System.err.println("DB 'ye bakılıyor");
        // kendi user sınıfımı spring security 'nin istediği user sınıfına dönüştürmek
        // için
        SystemUser myUser = userRepository.getByUserName(username);
//		if (4 > 2)
//		{
//			throw new MusteriActiveOlmamisException("bla bla bla");
//		}
        User.UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(myUser.getUsername());
        builder.password(myUser.getPassword());
        List<GrantedAuthority> userRoles = userRepository.getUserRoles(myUser.getUsername());
        builder.authorities(userRoles);
        return builder.build();
    }
}
