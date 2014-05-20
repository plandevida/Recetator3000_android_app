package com.recetatordeveloperteam.recetator3000.entidades;

import org.json.JSONException;
import org.json.JSONObject;

public interface JSONParse<D> {
	
	public D parseJSON(JSONObject jsonO) throws JSONException;
}
