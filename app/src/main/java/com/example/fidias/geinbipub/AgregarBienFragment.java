package com.example.fidias.geinbipub;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.LOCATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class AgregarBienFragment extends Fragment implements LocationListener {
    Button bGuardar;
    EditText etTitulo;
    EditText etDetalle;
    TextView tvLatitud;
    TextView tvLongitud;
    LocationManager lm;

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
    public void onPause() {
        super.onPause();
        Log.d("GEINBIPUB", "AgregarBienFragment.onPause()");
        lm.removeUpdates(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("GEINBIPUB", "AgregarBienFragment.onActivityCreated");
        bGuardar = (Button) getActivity().findViewById(R.id.bGuardar);
        etTitulo = (EditText) getActivity().findViewById(R.id.etTituloAgregar);
        etDetalle = (EditText) getActivity().findViewById(R.id.etDetalleAgregar);
        tvLatitud = (TextView)getActivity().findViewById(R.id.tvLatitudAgregar);
        tvLongitud = (TextView)getActivity().findViewById(R.id.tvLongitudAgregar);
        lm = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location!= null) {
                tvLatitud.setText(getString(R.string.latitud_label) + location.getLatitude());
                tvLongitud.setText(getString(R.string.longitud_label) + location.getLongitude());
            }
        }

        bGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        }
        activarGPS();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("xxx", "AgregarBienFragment.onRequestPermissionsResult()");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0:
                boolean permisoOtorgado = false;
                int i;
                for (i = 0; i < grantResults.length; i++) {
                    if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                        permisoOtorgado = true;
                        break;
                    }
                }
                if (permisoOtorgado) {
                    Log.d("GEINBIPUB", "Permisos otorgados");
                    // permission was granted, yay! Do the task you need to do.
                    activarGPS();

                } else {
                    Log.d("GEINBIPUB", "Permisos denegados");
                    // permission denied, boo! Disable the functionality that depends on this permission.
                    Toast.makeText(getActivity(), getString(R.string.mensaje_no_permiso_GPS), Toast.LENGTH_LONG).show();
                }
                break;
            default:
                Log.d("GEINBIPUB", "Solicitud de permisos no manejada. requestCode = " + requestCode);
        }
    }

    private void activarGPS() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, this);
            Log.d("GEINBIPUB", "activarGPS()");
        }

    }

    private void guardar() {
        Log.d("GEINBIPUB", "AgregarBienFragment.guardar()");
        Bien b = null;
        try {
            b = new Bien(Float.parseFloat(tvLatitud.getText().toString()),
                    Float.parseFloat(tvLongitud.getText().toString()),
                    etTitulo.getText().toString(),
                    etDetalle.getText().toString());
        } catch (NumberFormatException e) {
            b = new Bien(Bien.COORD_NO_VALIDA,
                    Bien.COORD_NO_VALIDA,
                    etTitulo.getText().toString(),
                    etDetalle.getText().toString());
        }
        b.guardar(getActivity());
        getActivity().finish();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("GEINBIPUB","AgregarBienFragment.onLocationChanged()");
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
