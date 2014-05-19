package com.recetatordeveloperteam.recetator3000;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class ActividadPrincipal extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawer_layout);
		
		LayoutInflater inflater = getLayoutInflater();
		RelativeLayout container = (RelativeLayout) findViewById(R.id.content_frame);
		inflater.inflate(R.layout.activity_actividad_principal, container);
		
		ListView mDrawerList = (ListView) findViewById(R.id.left_drawer);
		
		View header = getLayoutInflater().inflate(R.layout.header, null);
		mDrawerList.addHeaderView(header);

		NavigationAdapter navAdapter = new NavigationAdapter(this);
		// Set the adapter for the list view
		mDrawerList.setAdapter(navAdapter);
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (id==0) {
					Intent intent = new Intent(ActividadPrincipal.this, ActividadPerfil.class);
					startActivity(intent);
				} else if (id==1) {
					Intent intent = new Intent(ActividadPrincipal.this, ActividadRecetasFavoritas.class);
					startActivity(intent);
				} else if (id==2) {
					Intent intent = new Intent(ActividadPrincipal.this, ActividadRecetasPendientes.class);
					startActivity(intent);
				}
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actividad_principal, menu);
		return true;
	}
	
	public void recetas(View view) {
		Intent intent = new Intent(this, ActividadMisRecetas.class);
		startActivity(intent);
	}
	
	public void buscar(View view) {
		Intent intent = new Intent(this, ActividadBusquedaRecetas.class);
		startActivity(intent);
	}

}
