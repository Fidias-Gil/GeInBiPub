package com.example.fidias.geinbipub;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class AgregarBienFragment extends Fragment {
    Button bGuardar;
    EditText etTitulo;
    EditText etDetalle;

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
        bGuardar = (Button)getActivity().findViewById(R.id.bGuardar);
        etTitulo = (EditText)getActivity().findViewById(R.id.etTituloAgregar);
        etDetalle = (EditText)getActivity().findViewById(R.id.etDetalleAgregar);

        bGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
    }

    private void guardar() {

    }
}
