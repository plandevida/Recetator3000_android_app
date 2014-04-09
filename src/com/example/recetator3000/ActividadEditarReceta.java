package com.example.recetator3000;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Vector;

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

public class ActividadEditarReceta extends Activity {

	private String[] tipoPlato = {"Aperitivos / Entrantes / Tapas", "Bebidas", "Bocadillos / Sandwich",
			"Plato único", "Primer plato", "Segundo plato", "Postres", "Guarniciones y salsas"};
	private String[] ingredientePrincipal = {"Arroz", "Aves", "Cafes e infusiones", "Carne y caza",
			"Cremas / Sopas / Salsas", "Chocolate", "Embutidos", "Ensaladas", "Fritos", "Frutas",
			"Helados / Sorbetes / Granizados", "Hongos", "Huevos y lácteos", "Legumbres y potajes",
			"Masas y pizzas", "Mermeladas / Confituras", "Pastas y cereales", "Patatas",
			"Pescados y mariscos", "Rellenos", "Tartas / Pasteles / Mousse / Bizcochos",
			"Verduras y hortalizas"};
	private String[] dificultad = {"Principiantes", "Fácil", "Media", "Alta", "Muy alta"};
	private String[] ingredientes = {"aceite", "agua", "ajo", "arroz", "atún", "azúcar", "bacon", "berenjena",
			"brócoli", "calabacín", "cebolla", "champiñones", "garbanzos", "guisantes", "huevos", "jamón",
			"judías blancas", "leche", "lechuga", "lentejas", "macarrones", "mantequilla", "merluza", "nata",
			"pan", "patata", "pimienta", "pimientos rojos", "queso de untar", "queso fresco", "sal",
			"spaguettis", "tomate", "tomate frito", "yogurt", "zanahoria"};
	private String datoTipo, datoIngrediente, datoDificultad;
	private int codigo = 0;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actividad_editar_receta);
		
		codigo = getIntent().getIntExtra("Codigo", 0);
		Receta receta = cargar(codigo);
		Vector<String> listaAux = new Vector<String>();
		for (int i=0; i<tipoPlato.length; i++) {
			listaAux.add(tipoPlato[i]);
		}
		
		Spinner listaTipo = (Spinner) findViewById(R.id.editar_tipo);
		listaTipo.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				tipoPlato));
		listaTipo.setSelection(listaAux.indexOf(receta.getTipoPlato(), 0));
		
		listaTipo.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				datoTipo = tipoPlato[position];
			}
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		
		Spinner listaIngrediente = (Spinner) findViewById(R.id.editar_ingrediente);
		listaIngrediente.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				ingredientePrincipal));
		listaAux.removeAllElements();
		for (int i=0; i<ingredientePrincipal.length; i++) {
			listaAux.add(ingredientePrincipal[i]);
		}
		listaIngrediente.setSelection(listaAux.indexOf(receta.getIngredientePrincipal(), 0));
		
		listaIngrediente.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				datoIngrediente = ingredientePrincipal[position];
			}
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		
		Spinner listaDificultad = (Spinner) findViewById(R.id.editar_dificultad);
		listaDificultad.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				dificultad));
		listaAux.removeAllElements();
		for (int i=0; i<dificultad.length; i++) {
			listaAux.add(dificultad[i]);
		}
		listaDificultad.setSelection(listaAux.indexOf(receta.getDificultad(), 0));
		
		listaDificultad.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				datoDificultad = dificultad[position];
			}
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		
		MultiAutoCompleteTextView listaIngredientes = (MultiAutoCompleteTextView) findViewById(R.id.editar_ingredientes);
		listaIngredientes.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,
				ingredientes));
		listaIngredientes.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
		listaIngredientes.setText(receta.getIngredientes());
		
		int dimen = (int) getResources().getDimension(R.dimen.icono_boton);
		Drawable saveImage = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(((BitmapDrawable)
				getResources().getDrawable(R.drawable.save)).getBitmap(), dimen, dimen, false));
		((Button) findViewById(R.id.guardar)).setCompoundDrawablesWithIntrinsicBounds(saveImage, null, null, null);
		
		Drawable cancelImage = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(((BitmapDrawable)
				getResources().getDrawable(R.drawable.cancel)).getBitmap(), dimen, dimen, false));
		((Button) findViewById(R.id.cancelar)).setCompoundDrawablesWithIntrinsicBounds(cancelImage, null, null, null);
		
		((EditText) findViewById(R.id.editar_nombre)).setText(receta.getNombre());
		((EditText) findViewById(R.id.editar_tiempo)).setText(receta.getTiempo());
		((EditText) findViewById(R.id.editar_calorias)).setText(receta.getCalorias());
		((EditText) findViewById(R.id.editar_modo)).setText(receta.getModo());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actividad_editar_receta, menu);
		return true;
	}

	public void guardar(View view) {
		String datoNombre = ((EditText) findViewById(R.id.editar_nombre)).getText().toString();
		String datoTiempo = ((EditText) findViewById(R.id.editar_tiempo)).getText().toString();
		String datoCalorias = ((EditText) findViewById(R.id.editar_calorias)).getText().toString();
		String datoIngredientes = ((MultiAutoCompleteTextView) findViewById(R.id.editar_ingredientes)).getText().toString();
		String datoModo = ((EditText) findViewById(R.id.editar_modo)).getText().toString();
		if (datoNombre.equals("") || datoTiempo.equals("") || datoCalorias.equals("") ||
				datoIngredientes.equals("") || datoModo.equals("")) {
			Toast.makeText(this, "Debe rellenar todos los campos", Toast.LENGTH_LONG).show();
		} else {
			boolean datoCeliacos = ((CheckBox) findViewById(R.id.editar_celiacos)).isChecked();
			boolean datoLactosa = ((CheckBox) findViewById(R.id.editar_lactosa)).isChecked();
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
	
	private int guardarBBDD(Receta receta) {
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
