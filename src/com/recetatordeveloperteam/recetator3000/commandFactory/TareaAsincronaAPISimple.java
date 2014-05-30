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
import android.os.AsyncTask;
import android.util.Log;

import com.recetatordeveloperteam.recetator3000.commandFactory.eventos.IdEvent;
import com.recetatordeveloperteam.recetator3000.commandFactory.httpconexion.HttpHelper;

public class TareaAsincronaAPISimple<D> extends AsyncTask<Void, Integer, List<D>> {

	String url;
	IdEvent idEvento;
	Context contexto;
	String jsonName;
	
	public TareaAsincronaAPISimple(Context context, IdEvent evento, String urlapi, String jsonNameEntity) {
		
		url = urlapi;
		idEvento = evento;
		contexto = context;
		jsonName = jsonNameEntity;
	}

	@Override
	protected List<D> doInBackground(Void... params) {
		
		HttpResponse response = null;
		try {
			
			List<NameValuePair> paresAtributos = new ArrayList<NameValuePair>();
			paresAtributos.add(new BasicNameValuePair("idevento", idEvento.name()));
			
			response = HttpHelper.ejecutarHttpPost(url, paresAtributos);
			
		} catch (ClientProtocolException cp) {
			cp.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		processResponse(response.toString());
		
		return null;
	}
	
	@Override
	protected void onPreExecute() {
		
	}
	
	@Override
	protected void onCancelled() {
		
	}
	
	@Override
	protected void onPostExecute(List<D> d) {
		
	}
	
	private List<D> processResponse(String response) {
        List<D> lista = new ArrayList<D>();
        
        if ( response != null ) {
	        try {
	            JSONObject responseObject = new JSONObject(response); // Creates a new JSONObject with name/value mappings from the JSON string.
	            JSONArray results = responseObject.getJSONArray(this.jsonName); // Returns the value mapped by name if it exists and is a JSONArray.
	            
	            Log.println(Log.INFO, "RECETATOR", "IMPRIMIENDO RECETAS");
	            
//	            lista = parseJSON(results, jsonName);
	        }catch (JSONException e) {
	            e.printStackTrace();
	        }
        }
        
        return lista;
    }
}
