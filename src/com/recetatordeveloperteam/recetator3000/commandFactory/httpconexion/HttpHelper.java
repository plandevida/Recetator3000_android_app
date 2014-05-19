package com.recetatordeveloperteam.recetator3000.commandFactory.httpconexion;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpHelper {

	public static HttpPost nuevoPost(String urlapipost, List<NameValuePair> listaAtributos) throws UnsupportedEncodingException {
		
		HttpPost post = new HttpPost(urlapipost);
		post.setEntity(new UrlEncodedFormEntity(listaAtributos));
		
		return post;
	}
	
	public static HttpResponse ejecutarHttpPost(String urlapipost, List<NameValuePair> listaAtributos) throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		
		HttpPost post = HttpHelper.nuevoPost(urlapipost, listaAtributos);
		
		return client.execute(post);
	}
}
