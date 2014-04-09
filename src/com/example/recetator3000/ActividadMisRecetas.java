package com.example.recetator3000;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;

import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class ActividadMisRecetas extends Activity {

	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actividad_mis_recetas);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Vector<String> nombresRecetas = new Vector<String>();
		Vector<Integer> codigosRecetas = new Vector<Integer>();
		File[] archivos = this.getFilesDir().listFiles();
		if (archivos.length > 0) {
			// Se toman los nombres de las recetas para ponerlos en la lista de la actividad
			for (int i=0; i<archivos.length; i++) {
				if (archivos[i].getName().contains(".dat") && archivos[i].getName().contains("1")) {
					String nombre = null;
					try {
						BufferedReader entrada = new BufferedReader(new FileReader(archivos[i]));
						nombre = entrada.readLine();
						entrada.close();
						nombresRecetas.add(nombre);
						int codigo = Integer.parseInt(archivos[i].getName().substring(0,
								(archivos[i].getName().length()) - new String(".dat").length()));
						codigosRecetas.add(codigo);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			// A partir de aqui se crean los hijos de la lista dinamicamente
			int margin = (int) getResources().getDimension(R.dimen.margin_peque);
			int dimenImagen = (int) getResources().getDimension(R.dimen.icono_receta);
			LinearLayout misRecetas = (LinearLayout) findViewById(R.id.mis_recetas);
			for (int i=0; i<nombresRecetas.size(); i++) {
				LinearLayout fila = new LinearLayout(this);
				LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				params1.setMargins(0, 1, 0, 1);
				fila.setLayoutParams(params1);
				fila.setOrientation(LinearLayout.HORIZONTAL);
				fila.setGravity(Gravity.CENTER_VERTICAL);
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
					fila.setBackground(getResources().getDrawable(R.drawable.style_fila_receta));
				else
					fila.setBackgroundDrawable(getResources().getDrawable(R.drawable.style_fila_receta));
				
				ImageView icono = new ImageView(this);
				icono.setLayoutParams(new LinearLayout.LayoutParams(dimenImagen, dimenImagen));
				icono.setBackgroundColor(getResources().getColor(R.color.negro));
				fila.addView(icono);
				
				TextView nombre = new TextView(this);
				LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				params2.setMargins(margin, margin, margin, margin);
				nombre.setLayoutParams(params2);
				nombre.setTextColor(getResources().getColor(R.color.blanco));
				nombre.setTextSize(getResources().getDimension(R.dimen.letra_peque));
				nombre.setText(nombresRecetas.elementAt(i));
				fila.addView(nombre);
				
				fila.setTag(codigosRecetas.elementAt(i));
				fila.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(ActividadMisRecetas.this, ActividadMostrarReceta.class);
						intent.putExtra("Receta", Integer.parseInt((String.valueOf(arg0.getTag()))));
						startActivity(intent);
					}
				});
				
				misRecetas.addView(fila);
			}
		}
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
		getMenuInflater().inflate(R.menu.actividad_mis_recetas, menu);
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
