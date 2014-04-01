package com.example.recetator3000;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class ActividadPrincipal extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actividad_principal);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actividad_principal, menu);
		return true;
	}
	
	public void perfil(View view) {
	}
	
	public void recetas(View view) {
		Intent intent = new Intent(this, ActividadRecetas.class);
		startActivity(intent);
	}
	
	public void buscar(View view) {
	}

}
