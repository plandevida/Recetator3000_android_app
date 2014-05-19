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

import com.recetatordeveloperteam.recetator3000.Receta;
import com.recetatordeveloperteam.recetator3000.commandFactory.eventos.IdEvent;
import com.recetatordeveloperteam.recetator3000.commandFactory.httpconexion.HttpHelper;

/**
 * Clase borracha para realizar las llamadas asincronas al API
 * http://developer.android.com/reference/android/content/AsyncTaskLoader.html
 * @author Daniel Serrano
 * @param <D>
 *
 * @param <D> Tipo de dato que queremos devolver.
 */
public class TareaAsincronaAPI<D> extends AsyncTaskLoader<List<D>> {

	private String url;
	private String jsonName;
	
	public TareaAsincronaAPI(Context context, String urlapi, String jsonNameEntity) {
		super(context);
		
		url = urlapi;
		jsonName = jsonNameEntity;
	}

	@Override
	public List<D> loadInBackground() {
        String response = sendRequest(url);
        return processResponse(response);
    }


    private String sendRequest(String url) {
    	
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

    @SuppressWarnings("unchecked")
	private List<D> processResponse(String response) {
        List<D> lista = new ArrayList<D>();
        
        if ( response != null ) {
	        try {
	            JSONObject responseObject = new JSONObject(response); // Creates a new JSONObject with name/value mappings from the JSON string.
	            JSONArray results = responseObject.getJSONArray(this.jsonName); // Returns the value mapped by name if it exists and is a JSONArray.
	            	
	            lista = (List<D>) parseJSON(results, jsonName);
	        }catch (JSONException e) {
	            e.printStackTrace();
	        }
        }
        
        return lista;
    }
    
    private Object parseJSON(JSONArray arrayJ, String nameJson) {
    	
    	List<Object> lista = new ArrayList<>();
    	
    	switch (nameJson) {
		case "recetas":
			JSONObject jo = null;
			for ( int i = 0; i < arrayJ.length(); i++) {

				try {
					jo = arrayJ.getJSONObject(i);
					lista.add(new Receta(jo.getString("nombre"), jo.getString("tipoplato"), jo.getString("ingredienteprincipal"), jo.getString("dificultad"), jo.getString("tiempo"), jo.getString("calorias"), jo.getBoolean("celiacos"), jo.getBoolean("lactosa"), jo.getString("ingredientes"), jo.getString("modo"), jo.getInt("puntuacion")));	
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			break;
		}
    	
    	return lista;
    }
}
