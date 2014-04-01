package com.example.recetator3000;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ActividadNuevaReceta extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actividad_nueva_receta);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actividad_nueva_receta, menu);
		return true;
	}

}
