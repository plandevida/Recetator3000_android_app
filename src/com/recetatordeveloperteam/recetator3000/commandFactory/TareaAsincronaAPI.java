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
import android.util.Log;

import com.recetatordeveloperteam.recetator3000.commandFactory.eventos.IdEvent;
import com.recetatordeveloperteam.recetator3000.commandFactory.httpconexion.HttpHelper;
import com.recetatordeveloperteam.recetator3000.entidades.JSONParse;

/**
 * Clase borracha para realizar las llamadas asincronas al API
 * http://developer.android.com/reference/android/content/AsyncTaskLoader.html
 * @author Daniel Serrano
 * @param <D>
 *
 * @param <D> Tipo de dato que queremos devolver.
 */
public class TareaAsincronaAPI<D extends JSONParse<D>> extends AsyncTaskLoader<List<D>> {

	private String url;
	private String jsonName;
	private IdEvent eventoApi;
	
	public TareaAsincronaAPI(Context context, IdEvent evento, String urlapi, String jsonNameEntity) {
		super(context);
		
		eventoApi = evento;
		url = urlapi;
		jsonName = jsonNameEntity;
	}

	@Override
	public List<D> loadInBackground() {
		
		Log.println(Log.INFO, "RECETATOR", "INICIANDO ASYNKTASKLOADER");
		
        String response = sendRequest(url);
        return processResponse(response);
    }

    private String sendRequest(String url) {
    	
		HttpResponse response = null;
		try {
			
			List<NameValuePair> paresAtributos = new ArrayList<NameValuePair>();
			paresAtributos.add(new BasicNameValuePair("idevento", eventoApi.name()));
			
			response = HttpHelper.ejecutarHttpPost(url, paresAtributos);
			
		} catch (ClientProtocolException cp) {
			cp.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		return (response != null) ? response.toString() : "";
    }

	private List<D> processResponse(String response) {
        List<D> lista = new ArrayList<D>();
        
        if ( response != null ) {
	        try {
	            JSONObject responseObject = new JSONObject(response); // Creates a new JSONObject with name/value mappings from the JSON string.
	            JSONArray results = responseObject.getJSONArray(this.jsonName); // Returns the value mapped by name if it exists and is a JSONArray.
	            
	            Log.println(Log.INFO, "RECETATOR", "IMPRIMIENDO RECETAS");
	            
	            lista = parseJSON(results, jsonName);
	        }catch (JSONException e) {
	            e.printStackTrace();
	        }
        }
        
        return lista;
    }
	
	@Override
	public void deliverResult(List<D> listaDs) {
		
		if ( isStarted() && listaDs != null) {
			
			super.deliverResult(listaDs);
		}
	}
    
    private List<D> parseJSON(JSONArray arrayJ, String nameJson) {
    	
    	List<D> lista = new ArrayList<>();
    	JSONObject jsonO;
		for ( int i = 0; i < arrayJ.length(); i++) {
			try {
				jsonO = arrayJ.getJSONObject(i);
				Log.println(Log.INFO, "RECETATOR", jsonO.getString("nombre") + jsonO.getString("tipoplato") + jsonO.getString("ingredienteprincipal") + jsonO.getString("dificultad") + jsonO.getString("tiempo") + jsonO.getString("calorias") + jsonO.getBoolean("celiacos") + jsonO.getBoolean("lactosa") + jsonO.getString("ingredientes") + jsonO.getString("modo") + jsonO.getInt("puntuacion"));
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
//			lista.add(Receta.);
		}
    	
    	return lista;
    }
}
