package com.recetatordeveloperteam.recetator3000;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class ActividadNuevaReceta extends Activity {
	
	private String[] tipoPlato = {"Aperitivos / Entrantes / Tapas", "Bebidas", "Bocadillos / Sandwich",
			"Plato ?nico", "Primer plato", "Segundo plato", "Postres", "Guarniciones y salsas"};
	private String[] ingredientePrincipal = {"Arroz", "Aves", "Cafes e infusiones", "Carne y caza",
			"Cremas / Sopas / Salsas", "Chocolate", "Embutidos", "Ensaladas", "Fritos", "Frutas",
			"Helados / Sorbetes / Granizados", "Hongos", "Huevos y l?cteos", "Legumbres y potajes",
			"Masas y pizzas", "Mermeladas / Confituras", "Pastas y cereales", "Patatas",
			"Pescados y mariscos", "Rellenos", "Tartas / Pasteles / Mousse / Bizcochos",
			"Verduras y hortalizas"};
	private String[] dificultad = {"Principiantes", "F?cil", "Media", "Alta", "Muy alta"};
	private String[] ingredientes = {"aceite", "agua", "ajo", "arroz", "at?n", "az?car", "bacon", "berenjena",
			"br?coli", "calabac?n", "cebolla", "champi?ones", "garbanzos", "guisantes", "huevos", "jam?n",
			"jud?as blancas", "leche", "lechuga", "lentejas", "macarrones", "mantequilla", "merluza", "nata",
			"pan", "patata", "pimienta", "pimientos rojos", "queso de untar", "queso fresco", "sal",
			"spaguettis", "tomate", "tomate frito", "yogurt", "zanahoria"};
	private String datoTipo, datoIngrediente, datoDificultad;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actividad_nueva_receta);
		
		Spinner listaTipo = (Spinner) findViewById(R.id.tipo_plato);
		listaTipo.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				tipoPlato));
		
		listaTipo.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				datoTipo = tipoPlato[position];
			}
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		
		Spinner listaIngrediente = (Spinner) findViewById(R.id.ingrediente_principal);
		listaIngrediente.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				ingredientePrincipal));
		
		listaIngrediente.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				datoIngrediente = ingredientePrincipal[position];
			}
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		
		Spinner listaDificultad = (Spinner) findViewById(R.id.dificultad);
		listaDificultad.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				dificultad));
		
		listaDificultad.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				datoDificultad = dificultad[position];
			}
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		
		MultiAutoCompleteTextView listaIngredientes = (MultiAutoCompleteTextView) findViewById(R.id.ingredientes);
		listaIngredientes.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,
				ingredientes));
		listaIngredientes.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
		
		int dimen = (int) getResources().getDimension(R.dimen.icono_boton);
		Drawable saveImage = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(((BitmapDrawable)
				getResources().getDrawable(R.drawable.save)).getBitmap(), dimen, dimen, false));
		((Button) findViewById(R.id.guardar)).setCompoundDrawablesWithIntrinsicBounds(saveImage, null, null, null);
		
		Drawable cancelImage = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(((BitmapDrawable)
				getResources().getDrawable(R.drawable.cancel)).getBitmap(), dimen, dimen, false));
		((Button) findViewById(R.id.cancelar)).setCompoundDrawablesWithIntrinsicBounds(cancelImage, null, null, null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actividad_nueva_receta, menu);
		return true;
	}

	public void guardar(View view) {
		String datoNombre = ((EditText) findViewById(R.id.nombre_receta)).getText().toString();
		String datoTiempo = ((EditText) findViewById(R.id.tiempo)).getText().toString();
		String datoCalorias = ((EditText) findViewById(R.id.calorias)).getText().toString();
		String datoIngredientes = ((MultiAutoCompleteTextView) findViewById(R.id.ingredientes)).getText().toString();
		String datoModo = ((EditText) findViewById(R.id.modo)).getText().toString();
		if (datoNombre.equals("") || datoTiempo.equals("") || datoCalorias.equals("") ||
				datoIngredientes.equals("") || datoModo.equals("")) {
			Toast.makeText(this, "Debe rellenar todos los campos", Toast.LENGTH_LONG).show();
		} else {
			boolean datoCeliacos = ((CheckBox) findViewById(R.id.celiacos)).isChecked();
			boolean datoLactosa = ((CheckBox) findViewById(R.id.lactosa)).isChecked();
			Receta receta = new Receta(datoNombre, datoTipo, datoIngrediente, datoDificultad, datoTiempo,
					datoCalorias, datoCeliacos, datoLactosa, datoIngredientes, datoModo, 0);
			
			// Por ahora se guarda en memoria del dispositivo
			int codigo = guardarBBDD(receta);
			
			Intent intent = new Intent(this, ActividadMostrarReceta.class);
			intent.putExtra("Receta", codigo);
			startActivity(intent);
			finish();
		}
	}
	
	private int guardarBBDD(Receta receta) {
		int codigo = 1000 + this.getFilesDir().list().length;
		File file = new File(this.getFilesDir(), String.valueOf(codigo).concat(".dat"));
		try {
			FileWriter archivo = new FileWriter(file);
			PrintWriter salida = new PrintWriter(archivo);
			salida.println(receta.getNombre());
			salida.println(receta.getTipoPlato());
			salida.println(receta.getIngredientePrincipal());
			salida.println(receta.getDificultad());
			salida.println(receta.getTiempo());
			salida.println(receta.getCalorias());
			salida.println(String.valueOf(receta.getCeliacos()));
			salida.println(String.valueOf(receta.getLactosa()));
			salida.println(receta.getIngredientes());
			salida.println(receta.getModo());
			archivo.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return codigo;
	}

	public void cancelar(View view) {
		finish();
	}

}
