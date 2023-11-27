package com.graysan.springbootrest.model;

public class TokenInfo {
	private String username;
	private String token;

	public TokenInfo()
	{
	}

	public TokenInfo(String username, String token)
	{
		this.username = username;
		this.token = token;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	@Override
	public String toString()
	{
		return "{\"username\":\"" + username + "\",\"token\":\"" + token + "\"}";
	}
}
