package com.example.recetator3000;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class ActividadMostrarReceta extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actividad_mostrar_receta);
		// Show the Up button in the action bar.
		setupActionBar();
		int codigo = getIntent().getIntExtra("Receta", 0);
		
		// Por ahora cargara el archivo de la memoria del dispositivo
		Receta receta = cargar(codigo);
		((TextView) findViewById(R.id.titulo_mostrar)).setText(receta.getNombre());
		((TextView) findViewById(R.id.dificultad_mostrar)).setText(receta.getDificultad());
		((TextView) findViewById(R.id.tiempo_mostrar)).setText(receta.getTiempo());
		((TextView) findViewById(R.id.calorias_mostrar)).setText(receta.getCalorias());
		((TextView) findViewById(R.id.tipo_mostrar)).setText(receta.getTipoPlato());
		String apto = "";
		if (!receta.getCeliacos())
			apto = apto.concat(getResources().getString(R.string.no_celiacos));
		if (!receta.getLactosa())
			apto = apto.concat(getResources().getString(R.string.no_lactosa));
		if (!apto.equals(""))
			((TextView) findViewById(R.id.apto_mostrar)).setText(apto);
	}
	
	private Receta cargar(int codigo) {
		File archivo = new File(this.getFilesDir(), String.valueOf(codigo).concat(".dat"));
		if (archivo.exists()) {
			String[] datos = new String[10];
			try {
				BufferedReader entrada = new BufferedReader(new FileReader(archivo));
				String linea;
				int pos = 0;
				while((linea=entrada.readLine()) != null) {
					datos[pos] = linea.toString();
					pos++;
				}
				entrada.close();
				return new Receta(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5],
						Boolean.parseBoolean(datos[6]), Boolean.parseBoolean(datos[7]), datos[8], datos[9], 0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actividad_mostrar_receta, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
