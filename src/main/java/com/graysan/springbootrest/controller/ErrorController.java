package com.graysan.springbootrest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestController
//@RestControllerAdvice
public class ErrorController
{
//	@ExceptionHandler
//	public ResponseEntity<String> errorhandler(Exception e)
//	{
//		if (e.getClass() == BadSqlGrammarException.class)
//		{
//			return ResponseEntity.status(500).body("Arka planda bir hata var");
//		}
//		else
//		{
//			return ResponseEntity.status(500).body("Sistemde geçici bir sorun bulunmaktadır");
//		}
//	}

	// MVC için
//	@GetMapping(path = "/error")
//	public String errorhandler()
//	{
//		System.err.println("error endpointi yakaladım");
//		return "error endpointi";
//	}
}
