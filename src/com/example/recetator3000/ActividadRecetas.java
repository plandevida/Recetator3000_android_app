package com.example.recetator3000;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class ActividadRecetas extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actividad_recetas);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actividad_recetas, menu);
		return true;
	}
	
	public void nueva(View view) {
		Intent intent = new Intent(this, ActividadNuevaReceta.class);
		startActivity(intent);
	}
	
	public void misRecetas(View view) {
		Intent intent = new Intent(this, ActividadMisRecetas.class);
		startActivity(intent);
	}
	
	public void favoritas(View view) {
		Intent intent = new Intent(this, ActividadRecetasFavoritas.class);
		startActivity(intent);
	}
	
	public void pendientes(View view) {
		Intent intent = new Intent(this, ActividadRecetasPendientes.class);
		startActivity(intent);
	}

}
