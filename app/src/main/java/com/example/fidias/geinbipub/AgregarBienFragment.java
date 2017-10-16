package com.example.fidias.geinbipub;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.content.Context.LOCATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class AgregarBienFragment extends Fragment implements LocationListener{
    Button bGuardar;
    EditText etTitulo;
    EditText etDetalle;
    private LocationManager lm;

    public AgregarBienFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agregar_bien, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bGuardar = (Button) getActivity().findViewById(R.id.bGuardar);
        etTitulo = (EditText) getActivity().findViewById(R.id.etTituloAgregar);
        etDetalle = (EditText) getActivity().findViewById(R.id.etDetalleAgregar);
        lm = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        bGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, this);
    }

    private void guardar() {
        Log.d("GEINBIPUB","AgregarBienFragment.guardar()");
        Bien b = new Bien(2,2,
                etTitulo.getText().toString(),
                etDetalle.getText().toString());
        b.guardar(getActivity());
        getActivity().finish();
    }

    @Override
    public void onLocationChanged(Location location) {
        TextView tvLatitud = (TextView)getActivity().findViewById(R.id.tvLatitudAgregar);
        TextView tvLongitud = (TextView)getActivity().findViewById(R.id.tvLongitudAgregar);

        tvLatitud.setText(getString(R.string.latitud_label) + location.getLatitude());
        tvLongitud.setText(getString(R.string.longitud_label) + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
