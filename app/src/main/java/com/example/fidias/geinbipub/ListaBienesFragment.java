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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListaBienesFragment extends Fragment {

    FloatingActionButton fbAgregar;
    ListView lvBienes;
    ArrayList<Bien> al;


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

        fbAgregar = (FloatingActionButton)getActivity().findViewById(R.id.fbAgregar);
        lvBienes = (ListView)getActivity().findViewById(R.id.lvBienes);


        lvBienes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        fbAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("GEINBIPUB","fbAgregar.onClick()");
                Intent i = new Intent(getActivity(),AgregarActivity.class);
                startActivity(i);
            }
        });

        //TODO: BORRAR
        getActivity().findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),DetalleActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        al = Bien.listar(getActivity());
        if(al.size()>0) {
            getActivity().findViewById(R.id.tvMensajeInicialListaBienes).setVisibility(View.INVISIBLE);
        } else {
            getActivity().findViewById(R.id.tvMensajeInicialListaBienes).setVisibility(View.VISIBLE);
        }
        ArrayAdapter<Bien> adapter = new ArrayAdapter<Bien>(getContext(),
                android.R.layout.simple_list_item_1,
                al);
        lvBienes.setAdapter(adapter);
    }


}
