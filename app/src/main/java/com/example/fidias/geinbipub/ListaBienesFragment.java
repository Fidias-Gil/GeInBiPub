package com.example.fidias.geinbipub;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
    ArrayList<Bien> al;
    FloatingActionButton fbAgregar;
    ListView lvBienes;



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

        inicializarListVew();
        fbAgregar = (FloatingActionButton)getActivity().findViewById(R.id.fbAgregar);
        fbAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("GEINBIPUB","fbAgregar.onClick()");
                Intent i = new Intent(getActivity(),AgregarActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        al = Bien.listar(getActivity());
//        if(al.size()>0) {
//            getActivity().findViewById(R.id.tvMensajeInicialListaBienes).setVisibility(View.INVISIBLE);
//        } else {
//            getActivity().findViewById(R.id.tvMensajeInicialListaBienes).setVisibility(View.VISIBLE);
//        }
//        ArrayAdapter<Bien> adapter = new ArrayAdapter<Bien>(getContext(),
//                android.R.layout.simple_list_item_1,
//                al);
//        lvBienes.setAdapter(adapter);
    }
    public void borrarBien(final int position, final ArrayAdapter<Bien> adapter) {
        Log.d("GEINBIPUB","ListaBienesFragment.borrarBien");
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getString(R.string.pregunta_completar_operacion));
        builder.setTitle(getString(R.string.borrar_incidencia));

        builder.setPositiveButton(getString(R.string.si), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("GEINBIPUB","ListaVienesFragment.borrarBien().new OnClickListener.onClick()");
                Bien.borrar(al.get(position), getContext());
                al.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), getString(R.string.operacion_realizada), Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), getString(R.string.operacion_cancelada), Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialogo = builder.create();
        dialogo.show();
        Log.d("GEINBIPUB","Saliendo de ListaBienesFragment.borrarBien()");
    }


    private void inicializarListVew () {
        Log.d("GEINBIPUB","ListaBienesFragment.inicializarListVew()");
        lvBienes = (ListView)getActivity().findViewById(R.id.lvBienes);
        al = Bien.listar(getActivity());
        if(al.size()>0) {
            getActivity().findViewById(R.id.tvMensajeInicialListaBienes).setVisibility(View.INVISIBLE);
        } else {
            getActivity().findViewById(R.id.tvMensajeInicialListaBienes).setVisibility(View.VISIBLE);
        }
        final ArrayAdapter<Bien> adapter = new ArrayAdapter<Bien>(getContext(),
                android.R.layout.simple_list_item_1,
                al);
        lvBienes.setAdapter(adapter);
        lvBienes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetalleBienFragment f = (DetalleBienFragment)(getFragmentManager().findFragmentById(R.id.fDetalleBien));
                if (f == null || !f.isVisible()) {
                    Log.d("GEINBIPUB", "f == null || !f.isVisible()" );
                    Intent i = new Intent(getContext(),DetalleActivity.class);
                    Log.d("GEINBIPUB", "ListaBienesFragment.inicializarListView().onItemClick --> position = "+ position);
                    i.putExtra("idBien", al.get(position).getId());
                    startActivity(i);
                } else  {
                    Log.d("GEINBIPUB", "f != null && f.isVisible()" );
                    Log.d("GEINBIPUB", ""+position);
                    Log.d("GEINBIPUB", al.get(position).toString() );
                    if(f==null) {
                        Log.d("GEINBIPUB","f es null!!!");
                    }
                    f.setBien(al.get(position));
                }
            }
        });
        lvBienes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("GEINBIPUB","ListaBienesFragment.OnItemLongClickListener()");
                borrarBien(position, adapter);
                return true;
            }
        });
    }
}
