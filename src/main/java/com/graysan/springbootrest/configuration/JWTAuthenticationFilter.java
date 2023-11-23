package com.graysan.springbootrest.configuration;

import com.graysan.springbootrest.exception.MusteriActiveOlmamisException;
import com.graysan.springbootrest.model.SystemUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

//yetkilendirme
//JWT üreten class ve component değil, çünkü sisteme manuel dahil ediliyor
//login endpointine username password gönderildiğinde
// UsernamePasswordAuthenticationFilter özel bir filter 'dır ve /login endpointine karşılık verir
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager)
	{
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException
	{
		System.err.println("şifre kontrol");
		try
		{
			// burası /login 'e karşılık geliyor
			SystemUser creds = new ObjectMapper().readValue(req.getInputStream(), SystemUser.class);
			// userservice 'imi invoke eder
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(), new ArrayList<GrantedAuthority>()));
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException
	{
		System.err.println("şifre doğru");
		// MY_SECRET_KEY önemli
		User principal = ((User) auth.getPrincipal());
		String rolestring = principal.getAuthorities().toArray()[0].toString();
		String str = principal.getUsername() + "-" + rolestring;
		System.err.println(str);
		String token = JWT.create().withSubject(str).withExpiresAt(new Date(System.currentTimeMillis() + 900000)).sign(Algorithm.HMAC512("MY_SECRET_KEY".getBytes()));
//		System.err.println(token);
		// token = 30948hgb57gbhg9wpuısgh==
		// react js tarafı için gerekecek
		String body = "(" + principal.getUsername() + ") " + token;
		System.err.println(body);
		res.getWriter().write(body);
		res.getWriter().flush();
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException
	{
		System.err.println("şifre yanlış");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		if (failed.getClass() == DisabledException.class)
		{
			// bunun için exception fırlatmama gerek yok
			response.getWriter().write("Kullanıcı disabled olmuş");
		}
		else if (failed.getClass() == MusteriActiveOlmamisException.class)
		{
			response.getWriter().write("Kullanıcı müşteri hizmetlerine gitmeli");
		}
		else if (failed.getClass() == BadCredentialsException.class)
		{
			response.getWriter().write("Kullanıcı adı şifre yanlış");
		}
		else
		{
			response.getWriter().write("Sistemde bir hata oluştu");
		}
		response.getWriter().flush();
	}
}