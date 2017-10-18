package com.example.fidias.geinbipub;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleBienFragment extends Fragment {
    TextView tvLatitud;
    TextView tvLongitud;
    TextView tvTitulo;
    TextView tvDetalle;

    public DetalleBienFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalle_bien, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvTitulo = (TextView)getActivity().findViewById(R.id.tvTituloMostrar);
        tvDetalle = (TextView)getActivity().findViewById(R.id.tvDetalleMostrar);
        tvLatitud = (TextView)getActivity().findViewById(R.id.tvLatitudMostrar);
        tvLongitud = (TextView)getActivity().findViewById(R.id.tvLongitudMostrar);

        Intent i = getActivity().getIntent();
        Bien b = new Bien(i.getIntExtra("idBien",0),getActivity());
        setBien(b);
    }

    public void setBien(Bien b) {
        tvTitulo.setText(b.getTitulo());
        tvDetalle.setText(b.getDetalle());
        tvLatitud.setText(getString(R.string.latitud_label) + b.getLatitud());
        tvLongitud.setText(getString(R.string.longitud_label) + b.getLongitud());
    }

}
