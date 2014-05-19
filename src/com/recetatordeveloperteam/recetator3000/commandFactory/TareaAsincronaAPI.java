package com.recetatordeveloperteam.recetator3000.commandFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.recetatordeveloperteam.recetator3000.commandFactory.eventos.IdEvent;
import com.recetatordeveloperteam.recetator3000.commandFactory.httpconexion.HttpHelper;

/**
 * Clase borracha para realizar las llamadas asincronas al API
 * http://developer.android.com/reference/android/content/AsyncTaskLoader.html
 * @author Daniel Serrano
 *
 * @param <D> Tipo de dato que queremos devolver.
 */
public class TareaAsincronaAPI<D> extends AsyncTaskLoader<D> {

	private String url;
	private String jsonName;
	
	public TareaAsincronaAPI(Context context, String urlapi, String jsonNameEntity) {
		super(context);
		
		url = urlapi;
		jsonName = jsonNameEntity;
	}

	@Override
	public D loadInBackground() {
        String response = sendRequest(url);
        return processResponse(response);
    }


    private String sendRequest(String url) {
//        BufferedReader input = null; // get the json
//        HttpURLConnection httpCon = null; // the http connection object
//        StringBuilder response = new StringBuilder(); // hold all the data from the jason in string separated with "\n"
//
//        try {
//            URL apiUrl = new URL(url);
//            httpCon = (HttpURLConnection) apiUrl.openConnection();
//
//            if (httpCon.getResponseCode() != HttpURLConnection.HTTP_OK) { // check for connectivity with server
//                return null;
//            }
//            input = new BufferedReader(new InputStreamReader(httpCon.getInputStream())); // pull all the json from the site
//            String line;
//            while ((line = input.readLine()) != null) {
//                response.append(line + "\n");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (input != null) {
//                try {
//                    input.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (httpCon != null) {
//                httpCon.disconnect();
//            }
//        }
//        return response.toString();
    	
		HttpResponse response = null;
		try {
			
			List<NameValuePair> paresAtributos = new ArrayList<NameValuePair>();
			paresAtributos.add(new BasicNameValuePair("idevento", IdEvent.GETALL_RECIPES.name()));
			
			response = HttpHelper.ejecutarHttpPost(url, paresAtributos);
			
		} catch (ClientProtocolException cp) {
			cp.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		return (response != null) ? response.toString() : "";
    }

    private D processResponse(String response) {
        D deals = (D) new ArrayList<Object>();
        
        if ( response != null ) {
	        try {
	            JSONObject responseObject = new JSONObject(response); // Creates a new JSONObject with name/value mappings from the JSON string.
	            JSONArray results = responseObject.getJSONArray(this.jsonName); // Returns the value mapped by name if it exists and is a JSONArray.
	            	
	            for(int i = 0; i < results.length(); i++) {
	            	JSONObject d = results.getJSONObject(i);
	            }
	        }catch (JSONException e) {
	            e.printStackTrace();
	        }
        }
        
        return deals;
    }
}
