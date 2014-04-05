package com.example.recetator3000;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ActividadNuevaReceta extends Activity {
	
	private String[] tipoPlato = {"Aperitivos / Entrantes / Tapas", "Bebidas", "Bocadillos / Sandwich",
			"Plato único", "Primer plato", "Segundo plato", "Postres", "Guarniciones y salsas"};
	private String[] ingrediente = {"Arroz", "Aves", "Cafes e infusiones", "Carne y caza",
			"Cremas / Sopas / Salsas", "Chocolate", "Embutidos", "Ensaladas", "Fritos", "Frutas",
			"Helados / Sorbetes / Granizados", "Hongos", "Huevos y lácteos", "Legumbres y potajes",
			"Masas y pizzas", "Mermeladas / Confituras", "Pastas y cereales", "Patatas",
			"Pescados y mariscos", "Rellenos", "Tartas / Pasteles / Mousse / Bizcochos",
			"Verduras y hortalizas"};
	private String[] dificultad = {"Principiantes", "Fácil", "Media", "Alta", "Muy alta"};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actividad_nueva_receta);
		
		Spinner listaTipo = (Spinner) findViewById(R.id.tipo_plato);
		ArrayAdapter<CharSequence> adapter1 = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item,
				android.R.id.text1, tipoPlato);
		listaTipo.setAdapter(adapter1);
		
		Spinner listaIngrediente = (Spinner) findViewById(R.id.ingrediente_principal);
		ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item,
				android.R.id.text1, ingrediente);
		listaIngrediente.setAdapter(adapter2);
		
		Spinner listaDificultad = (Spinner) findViewById(R.id.dificultad);
		ArrayAdapter<CharSequence> adapter3 = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item,
				android.R.id.text1, dificultad);
		listaDificultad.setAdapter(adapter3);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actividad_nueva_receta, menu);
		return true;
	}

}
