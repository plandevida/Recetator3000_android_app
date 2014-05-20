package com.recetatordeveloperteam.recetator3000.framgmentos;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.support.v4.content.Loader.OnLoadCompleteListener;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.recetatordeveloperteam.recetator3000.R;
import com.recetatordeveloperteam.recetator3000.commandFactory.TareaAsincronaAPI;
import com.recetatordeveloperteam.recetator3000.commandFactory.eventos.IdEvent;
import com.recetatordeveloperteam.recetator3000.entidades.Receta;

public class BusquedaRecetasFragment extends Fragment {
	
	private TareaAsincronaAPI<Receta> tareaAsincrona;
	private ViewGroup root;
	private ProgressBar progressBar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final ListView listaView = (ListView) getActivity().findViewById(R.id.listaRecetas);
		
		progressBar = new ProgressBar(getActivity());
		LayoutParams params = new LayoutParams();
		params.gravity = Gravity.CENTER;
		params.height = LayoutParams.WRAP_CONTENT;
		params.width = LayoutParams.WRAP_CONTENT;
        progressBar.setLayoutParams(params);
        progressBar.setIndeterminate(true);
//        listaView.setEmptyView(progressBar);
        
        root = (ViewGroup) getActivity().findViewById(android.R.id.content);
        root.addView(progressBar);
		
        
        Log.println(Log.INFO, "RECETATOR", "CREANDO LA TAREA ASINCRONA");
		tareaAsincrona = new TareaAsincronaAPI<Receta>(getActivity().getApplication(), IdEvent.GETALL_RECIPES, "http://recetator-api.appspot.com", "recetas");
		tareaAsincrona.registerListener(1, new OnLoadCompleteListener<List<Receta>>() {
			@Override
			public void onLoadComplete(Loader<List<Receta>> arg0, List<Receta> recetas) {
				
				root.removeView(progressBar);
				
				RecetaAdapter adapter = new RecetaAdapter(getActivity().getApplication(), recetas);
				listaView.setAdapter(adapter);
			}
		});
	}
	
	@Override
	public void onInflate(Activity activity, AttributeSet attributes, Bundle savedInstanceState) {
		onCreate(savedInstanceState);
	}
	
	@Override
	public void onDestroy() {
		
	}
	
	public class RecetaAdapter implements ListAdapter {

		private Context context;
		private List<Receta> lista;
		
		public RecetaAdapter(Context contexto, List<Receta> recetas ) {
			
			context = contexto;
			lista = recetas;
		}
		
		@Override
		public int getCount() {
			return lista.size();
		}

		@Override
		public Object getItem(int position) {
			return lista.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public int getItemViewType(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return new RecetaView(context, lista.get(position));
		}

		@Override
		public int getViewTypeCount() {
			return 0;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isEmpty() {
			return lista.isEmpty();
		}

		@Override
		public void registerDataSetObserver(DataSetObserver observer) {
		}

		@Override
		public void unregisterDataSetObserver(DataSetObserver observer) {
		}

		@Override
		public boolean areAllItemsEnabled() {
			return false;
		}

		@Override
		public boolean isEnabled(int arg0) {
			return false;
		}
	}
	
	public class RecetaView extends LinearLayout {

		private TextView nombre, dificultad;
		
		public RecetaView(Context context, Receta receta) {
			super(context);
			
			setOrientation(LinearLayout.VERTICAL);
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

			params.setMargins(5, 3, 5, 0);

			nombre = new TextView(context);
			nombre.setText(receta.getNombre());
			nombre.setTextSize(16f);
			addView(nombre, params);

			dificultad = new TextView(context);
			dificultad.setText("Dificultad: " + receta.getDificultad());
			addView(dificultad, params);
		}
	}
}
