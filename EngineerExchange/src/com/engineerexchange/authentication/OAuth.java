package com.engineerexchange.authentication;

import com.github.scribejava.apis.GitHubApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.engineerexchange.model.User;
import com.engineerexchange.utils.*;

public class OAuth {
	private final OAuth20Service service = new ServiceBuilder(Constants.clientId)
            .apiSecret(Constants.clientSecret)
            .state(Constants.secretState)
            .callback(Constants.callback)
            .build(GitHubApi.instance());
	
	
	public OAuth(){
		super();
	}
	
	
	public User getUser(String code) 
	{
		OAuth2AccessToken accessToken;
		Map<String, Object> map = null;
		try {
			accessToken = service.getAccessToken(code);
			final OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.github.com/user");
		    service.signRequest(accessToken, request);

		    final Response response = service.execute(request);
		    String jsonResponse = response.getBody();
		    Gson gson = new Gson();
		    map = gson.fromJson(jsonResponse, new TypeToken<Map<String, Object>>(){}.getType());
		    
		} catch (IOException | InterruptedException | ExecutionException e) {
			Logger.getGlobal().log(Level.SEVERE, "Error authenticating", e);
		}
		User u = new User();
		u.setName((String)map.get("name"));
		u.setLogin((String)map.get("login"));
		return u;
	}
	
	
	public String getAuthURL() {
	    return service.getAuthorizationUrl();
	}
    
	
}
