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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.recetatordeveloperteam.recetator3000.R;
import com.recetatordeveloperteam.recetator3000.Receta;
import com.recetatordeveloperteam.recetator3000.commandFactory.TareaAsincronaAPI;

public class BusquedaRecetasFragment extends Fragment {
	
	private TareaAsincronaAPI<Receta> tareaAsincrona;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final ListView listaView = (ListView) getActivity().findViewById(R.id.listaRecetas);
		//listaView.addView(new LoadingView(getActivity().getApplication()));
		
		ProgressBar progressBar = new ProgressBar(getActivity());
		LayoutParams params = new LayoutParams();
		params.gravity = Gravity.CENTER;
		params.height = LayoutParams.WRAP_CONTENT;
		params.width = LayoutParams.WRAP_CONTENT;
        progressBar.setLayoutParams(params);
        progressBar.setIndeterminate(true);
        listaView.setEmptyView(progressBar);
		
		tareaAsincrona = new TareaAsincronaAPI<Receta>(getActivity().getApplication(), "http://recetator-api.appspot.com/getrecetas", "recetas");
		tareaAsincrona.registerListener(1, new OnLoadCompleteListener<List<Receta>>() {
			@Override
			public void onLoadComplete(Loader<List<Receta>> arg0, List<Receta> arg1) {
				
				listaView.removeAllViews();
				for ( Receta r : arg1) {
					listaView.addView(new RecetaView(getActivity().getApplication(), r));
				}
			}
		});
	}
	
	@Override
	public void onInflate(Activity activity, AttributeSet attributes, Bundle savedInstanceState) {
		
	}
	
	@Override
	public void onDestroy() {
		
	}
	
	public class AdapterRecetas extends AdapterView<RecetaAdapter> {

		public AdapterRecetas(Context context) {
			super(context);
		}

		@Override
		public RecetaAdapter getAdapter() {
			return getAdapter();
		}

		@Override
		public View getSelectedView() {
			return new View(getContext());
		}

		@Override
		public void setAdapter(RecetaAdapter arg0) {
			setAdapter(arg0);
		}

		@Override
		public void setSelection(int arg0) {
			
		}
	}
	
	public class RecetaAdapter implements ListAdapter {

		private List<Receta> lista;
		
		public RecetaAdapter(List<Receta> recetas ) {
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
			return null;
		}

		@Override
		public int getViewTypeCount() {
			return 0;
		}

		@Override
		public boolean hasStableIds() {
			return true;
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
	
	public class RecetaView extends View {

		public RecetaView(Context context, Receta receta) {
			super(context);
		}
	}
	
	public class LoadingView extends View {

		public LoadingView(Context context) {
			super(context);
			
		}
	}
}
