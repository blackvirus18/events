package com.events;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;
import java.util.HashMap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ClientProtocolException, IOException{
    	  HttpClient client = new DefaultHttpClient();
    	  HttpGet request = new HttpGet("https://graph.facebook.com/429539495634/feed?access_token=1524213444481981%7C8hnd6Ub7AxOOfi6GAQa5i2VGb7g");
    	  HttpResponse response = client.execute(request);
    	  ObjectMapper mapper = new ObjectMapper();
    	  Map<String, Object> jsonMap = mapper.readValue(response.getEntity().getContent(), new TypeReference<HashMap<String,Object>>() {});
    	  System.out.println(""+jsonMap);
    }
}
