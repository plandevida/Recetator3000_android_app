package com.recetatordeveloperteam.recetator3000.entidades;

import org.json.JSONException;
import org.json.JSONObject;

public class Receta implements JSONParse<Receta> {
	
	private String nombre;
	private String tipoPlato;
	private String ingredientePrincipal;
	private String dificultad;
	private String tiempo;
	private String calorias;
	private boolean celiacos;
	private boolean lactosa;
	private String ingredientes;
	private String modo;
	private int puntuacion;

	public Receta(String nombre, String tipoPlato, String ingredientePrincipal, String dificultad,
			String tiempo, String calorias, boolean celiacos, boolean lactosa, String ingredientes,
			String modo, int puntuacion) {
		this.nombre = nombre;
		this.tipoPlato = tipoPlato;
		this.ingredientePrincipal = ingredientePrincipal;
		this.dificultad = dificultad;
		this.tiempo = tiempo;
		this.calorias = calorias;
		this.celiacos = celiacos;
		this.lactosa = lactosa;
		this.ingredientes = ingredientes;
		this.modo = modo;
		this.puntuacion = puntuacion;
	}
	
	public String getNombre() { return nombre; }
	public String getTipoPlato() { return tipoPlato; }
	public String getIngredientePrincipal() { return ingredientePrincipal; }
	public String getDificultad() { return dificultad; }
	public String getTiempo() { return tiempo; }
	public String getCalorias() { return calorias; }
	public boolean getCeliacos() { return celiacos; }
	public boolean getLactosa() { return lactosa; }
	public String getIngredientes() { return ingredientes; }
	public String getModo() { return modo; }
	public int getPuntuacion() { return puntuacion; }

	@Override
	public Receta parseJSON(JSONObject jsonO) throws JSONException {
		
		return new Receta(jsonO.getString("nombre"), jsonO.getString("tipoplato"), jsonO.getString("ingredienteprincipal"), jsonO.getString("dificultad"), jsonO.getString("tiempo"), jsonO.getString("calorias"), jsonO.getBoolean("celiacos"), jsonO.getBoolean("lactosa"), jsonO.getString("ingredientes"), jsonO.getString("modo"), jsonO.getInt("puntuacion"));
	}
}
