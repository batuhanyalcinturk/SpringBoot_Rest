package com.graysan.springbootrest.configuration;

import java.io.IOException;
import java.util.Collections;
import com.graysan.springbootrest.model.Role;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//OncePerRequestFilter sayesinde security context 'ine attığımız sadece requestte geçerli oluyor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    // dofilter yerine spring security classları kullandığım için dointernalfilter
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException
    {
        // bu aşamada kullanıcı zaten doğrulandı ve token ı verildi
        String token = req.getHeader("Authorization");
        System.err.println("token kontrol");
        // Bearer 30948hgb57gbhg9wpuısgh==
        if (token != null)
        {
            try
            {
                // kullanıcı adı şifre kontrolü DEĞİL !!!
                // jwt geçerlilik kontrolü
                String user = JWT.require(Algorithm.HMAC512("MY_SECRET_KEY".getBytes())).build().verify(token.replace("Bearer ", "")).getSubject();
                System.err.println("------> kullanıcı adı ve rol = " + user);
                if (user != null)
                {
                    // user-USER
                    // admin-ADMIN
                    String username = user.split("-")[0];
                    Role auth = new Role(user.split("-")[1]);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(auth));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    chain.doFilter(req, res);
                }
            }
            catch (Exception e)
            {
                System.err.println(e.getMessage());
                res.setStatus(HttpStatus.UNAUTHORIZED.value());
                res.getWriter().write("Token exception => " + e.getMessage());
                // benden sonraki filtreler response umu bozuyor o yüzden chain yok
//				chain.doFilter(req, res);
            }
        }
        else
        {
            System.err.println("Token yok ama security config var");
            // token yok
            chain.doFilter(req, res);
        }
    }}
