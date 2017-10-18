package com.example.fidias.geinbipub;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleBienFragment extends Fragment implements OnMapReadyCallback {
    TextView tvLatitud;
    TextView tvLongitud;
    TextView tvTitulo;
    TextView tvDetalle;
    ImageButton ibCompartir;
    private AdView mAdView;

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
        Log.d("GEINBIPUB","onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
        tvTitulo = (TextView)getActivity().findViewById(R.id.tvTituloMostrar);
        tvDetalle = (TextView)getActivity().findViewById(R.id.tvDetalleMostrar);
        tvLatitud = (TextView)getActivity().findViewById(R.id.tvLatitudMostrar);
        tvLongitud = (TextView)getActivity().findViewById(R.id.tvLongitudMostrar);
        ibCompartir = (ImageButton)getActivity().findViewById(R.id.ibCompartir);

        Intent i = getActivity().getIntent();
        final Bien b = new Bien(i.getIntExtra("idBien",0),getActivity());
        setBien(b);

        ibCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, b.toString());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        mAdView = (AdView) getActivity().findViewById(R.id.adView);
        if(mAdView == null) {
            Log.d("GEINBIPUB", "madView es null");
            return;
        }
        AdRequest adRequest = new AdRequest.Builder().build();
        if(adRequest == null) {
            Log.d("GEINBIPUB", "adRequest es null");
        } else {
            mAdView.loadAd(adRequest);
            Log.d("GEINBIPUB", "AdView cargado");
            Toast.makeText(getActivity(), "Debió haber salido el Ad", Toast.LENGTH_SHORT).show();
        }

        FragmentManager f = getActivity().getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)f.findFragmentById(R.id.mapafragment);
        if(mapFragment == null) {
            Toast.makeText(getActivity(), "ID mapafragment"+R.id.mapafragment, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getActivity(), getActivity().getSupportFragmentManager().toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity(), "mapFragment es null", Toast.LENGTH_SHORT).show();
            return;
        }
        mapFragment.getMapAsync(this);
    }

    public void setBien(Bien b) {
        tvTitulo.setText(b.getTitulo());
        tvDetalle.setText(b.getDetalle());
        if(b.getLatitud()==Bien.COORD_NO_VALIDA || b.getLongitud()==Bien.COORD_NO_VALIDA){
            tvLatitud.setVisibility(View.INVISIBLE);
            tvLongitud.setVisibility(View.INVISIBLE);
            return;
        }
        tvLatitud.setText(getString(R.string.latitud_label) + b.getLatitud());
        tvLongitud.setText(getString(R.string.longitud_label) + b.getLongitud());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Habilita/deshabilita la interacción
        googleMap.getUiSettings().setAllGesturesEnabled(true);
        //Habilita/deshabilita los controles de zoom
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        //Habilita/deshabilita el zoom
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        //Habilita/deshabilita el compass (Rotando el mapa)
        googleMap.getUiSettings().setCompassEnabled(true);
        //Habilita/deshabilita la toolbar de Google Maps (Maps & Ir) (Pulsando sobre items)
        googleMap.getUiSettings().setMapToolbarEnabled(true);

        //Posicionamiento de la cámara
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(40.29389, -3.73547));

        CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);
        googleMap.moveCamera(center);//Mueve la cámara hasta el punto indicado
        googleMap.animateCamera(zoom);//Acerca la cámara la nivel de zoom indicado
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
}
