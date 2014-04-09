package com.example.recetator3000;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Vector;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.MediaStore;

public class ActividadMostrarReceta extends Activity {

	private static int RESULT_LOAD_IMAGE = 1;
	private int codigo;
	private boolean favorita, pendiente;
	
	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actividad_mostrar_receta);
		// Show the Up button in the action bar.
		setupActionBar();
		codigo = getIntent().getIntExtra("Receta", 0);
		favorita = cargarFavoritas().contains(codigo);
		if (favorita) {
			Button corazon = (Button) findViewById(R.id.like);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
				corazon.setBackground(getResources().getDrawable(R.drawable.like));
			else
				corazon.setBackgroundDrawable(getResources().getDrawable(R.drawable.like));
		}
		
		pendiente = cargarPendientes().contains(codigo);
		if (pendiente) {
			Button calendario = (Button) findViewById(R.id.pendiente);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
				calendario.setBackground(getResources().getDrawable(R.drawable.calendar));
			else
				calendario.setBackgroundDrawable(getResources().getDrawable(R.drawable.calendar));
		}
		
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
			if (!receta.getLactosa()) {
				apto = apto.concat("\n");
				apto = apto.concat(getResources().getString(R.string.no_lactosa));
		} else if (!receta.getLactosa())
			apto = apto.concat(getResources().getString(R.string.no_lactosa));
		if (!apto.equals(""))
			((TextView) findViewById(R.id.apto_mostrar)).setText(apto);
		else
			((TextView) findViewById(R.id.apto_mostrar)).setEnabled(false);
		
		int dimen = (int) getResources().getDimension(R.dimen.icono_boton);
		
		Drawable listImage = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(((BitmapDrawable)
				getResources().getDrawable(R.drawable.list_ingredients)).getBitmap(), dimen, dimen, false));
		((Button) findViewById(R.id.list)).setCompoundDrawablesWithIntrinsicBounds(listImage, null, null, null);
		
		Drawable videoImage = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(((BitmapDrawable)
				getResources().getDrawable(R.drawable.start)).getBitmap(), dimen, dimen, false));
		((Button) findViewById(R.id.video)).setCompoundDrawablesWithIntrinsicBounds(videoImage, null, null, null);
		
		Drawable comentImage = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(((BitmapDrawable)
				getResources().getDrawable(R.drawable.comments)).getBitmap(), dimen, dimen, false));
		((Button) findViewById(R.id.comentarios)).setCompoundDrawablesWithIntrinsicBounds(comentImage, null, null, null);
		
		Button editarImagen = (Button) findViewById(R.id.edit_image);
		editarImagen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			((ImageView) findViewById(R.id.imagen_receta)).setImageBitmap(BitmapFactory.decodeFile(picturePath));
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
	
	private Vector<Integer> cargarFavoritas() {
		Vector<Integer> favoritas = new Vector<Integer>();
		File archivo = new File(this.getFilesDir(), "favoritas.dat");
		if (archivo.exists()) {
			try {
				BufferedReader entrada = new BufferedReader(new FileReader(archivo));
				String linea;
				while((linea=entrada.readLine()) != null)
					favoritas.add(Integer.valueOf(linea));
				entrada.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return favoritas;
	}
	
	private Vector<Integer> cargarPendientes() {
		Vector<Integer> pendientes = new Vector<Integer>();
		File archivo = new File(this.getFilesDir(), "pendientes.dat");
		if (archivo.exists()) {
			try {
				BufferedReader entrada = new BufferedReader(new FileReader(archivo));
				String linea;
				while((linea=entrada.readLine()) != null)
					pendientes.add(Integer.valueOf(linea));
				entrada.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return pendientes;
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

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@SuppressWarnings("deprecation")
	public void favorito(View view) {
		Button corazon = (Button) findViewById(R.id.like);
		if (!favorita) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
				corazon.setBackground(getResources().getDrawable(R.drawable.like));
			else
				corazon.setBackgroundDrawable(getResources().getDrawable(R.drawable.like));
			favorita = true;
		} else {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
				corazon.setBackground(getResources().getDrawable(R.drawable.no_like));
			else
				corazon.setBackgroundDrawable(getResources().getDrawable(R.drawable.no_like));
			favorita = false;
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@SuppressWarnings("deprecation")
	public void pendiente(View view) {
		Button calendario = (Button) findViewById(R.id.pendiente);
		if (!pendiente) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
				calendario.setBackground(getResources().getDrawable(R.drawable.calendar));
			else
				calendario.setBackgroundDrawable(getResources().getDrawable(R.drawable.calendar));
			pendiente = true;
		} else {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
				calendario.setBackground(getResources().getDrawable(R.drawable.no_calendar));
			else
				calendario.setBackgroundDrawable(getResources().getDrawable(R.drawable.no_calendar));
			pendiente = false;
		}
	}

	public void editar(View view) {

		Intent intent = new Intent(this, ActividadEditarReceta.class);
		intent.putExtra("Codigo", codigo);
		startActivity(intent);
		finish();
	}

	public void borrar(View view) {
		Toast.makeText(this, "Por ahora no borra, solo sale de la actividad", Toast.LENGTH_LONG).show();
		finish();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		Vector<Integer> favoritas = cargarFavoritas();
		if ((favorita && !favoritas.contains(codigo) || (!favorita && favoritas.contains(codigo)))) {
			if (favorita)
				favoritas.add(codigo);
			else
				favoritas.remove(Integer.valueOf(codigo));
			File file = new File(this.getFilesDir(), "favoritas.dat");
			try {
				FileWriter archivo = new FileWriter(file);
				PrintWriter salida = new PrintWriter(archivo);
				for (int i=0; i<favoritas.size(); i++)
					salida.println(favoritas.elementAt(i));
				archivo.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		Vector<Integer> pendientes = cargarPendientes();
		if ((pendiente && !pendientes.contains(codigo) || (!pendiente && pendientes.contains(codigo)))) {
			if (pendiente)
				pendientes.add(codigo);
			else
				pendientes.remove(Integer.valueOf(codigo));
			File file = new File(this.getFilesDir(), "pendientes.dat");
			try {
				FileWriter archivo = new FileWriter(file);
				PrintWriter salida = new PrintWriter(archivo);
				for (int i=0; i<pendientes.size(); i++)
					salida.println(pendientes.elementAt(i));
				archivo.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// Detiene la traza de métodos que iniciamos en onCreate()
		android.os.Debug.stopMethodTracing();
	}
}
