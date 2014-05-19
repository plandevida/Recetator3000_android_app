package com.recetatordeveloperteam.recetator3000.framgmentos;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;

import com.recetatordeveloperteam.recetator3000.Receta;
import com.recetatordeveloperteam.recetator3000.commandFactory.TareaAsincronaAPI;

public class BusquedaRecetasFragment extends Fragment {
	
	private TareaAsincronaAPI<List<Receta>> tareaAsincrona;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		tareaAsincrona = new TareaAsincronaAPI<List<Receta>>(getActivity().getApplication(), "http://recetator-api.appspot.com/getrecetas", "recetas");
	}
	
	@Override
	public void onInflate(Activity activity, AttributeSet attributes, Bundle savedInstanceState) {
		
	}
	
	@Override
	public void onDestroy() {
		
	}
}
