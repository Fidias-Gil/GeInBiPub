package com.example.fidias.geinbipub;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListaBienesFragment extends Fragment {

    FloatingActionButton fbAgregar;


    public ListaBienesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_bienes, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("GEINBIPUB","ListaBienesFragment.onActivityCreated()");
        Bien.listar(getActivity());
        fbAgregar = (FloatingActionButton)getActivity().findViewById(R.id.fbAgregar);
        fbAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),DetalleActivity.class);
                startActivity(i);
            }
        });
    }

}
