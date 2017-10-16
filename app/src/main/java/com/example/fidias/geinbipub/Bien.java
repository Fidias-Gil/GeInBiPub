package com.example.fidias.geinbipub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.fidias.geinbipub.Bien.FldBienes.DESCRIPCION;
import static com.example.fidias.geinbipub.Bien.FldBienes.LATITUD;
import static com.example.fidias.geinbipub.Bien.FldBienes.LONGITUD;
import static com.example.fidias.geinbipub.Bien.FldBienes.TITULO;

/**
 * Created by cice on 13/10/17.
 */

public class Bien {

    protected enum FldBienes {
        ID ("_id", "INTEGER PRIMARY KEY AUTOINCREMENT"),
        LATITUD ("latitud", "NUMERIC"),
        LONGITUD ("longitud", "NUMERIC"),
        TITULO ("titulo", "TEXT"),
        DESCRIPCION ("descripcion", "TEXT");

        private final String campo;
        private final String tipo;

        FldBienes(String c, String t) {
            this.campo=c;
            this.tipo=t;
        }
    };
    private float latitud;
    private float longitud;
    private String titulo;
    private String detalle;
    private int id;

    public Bien(float latitud, float longitud, String titulo, String detalle) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.titulo = titulo;
        this.detalle = detalle;
        this.id = -1;
    }

    public Bien(float latitud, float longitud, String titulo, String detalle,int id) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.titulo = titulo;
        this.detalle = detalle;
        this.id = id;
    }

    public float getLatitud() {
        return latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void guardar(Context c) {
        DBBienH bdl = new DBBienH(c);
        bdl.guardar(this);
    }

    public  static ArrayList<Bien> listar(Context c) {
        Log.d("GEINBIPUB","Bien.listar()");
        DBBienH db = new DBBienH(c);
        return db.listar();
    }


    private static class DBBienH extends SQLiteOpenHelper {
        private static final int DB_VERSION = 12;
        private static final String DB_NAME = "bienes";
        private final String TBL_BIENES = "bien";


        public DBBienH(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            Log.d("GEINBIPUB", "DBBien()" );
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d("GEINBIPUB", "DBBien.onCreate()" );
            String createTblBien = "CREATE TABLE " + TBL_BIENES + " (";
            for (FldBienes c: FldBienes.values()) {
                if (c.ordinal() != 0) {
                    createTblBien += ", ";
                }
                createTblBien += c.campo + " " + c.tipo;
            }
            createTblBien += ")";
            db.execSQL(createTblBien);
            Log.d("GEINBIPUB",createTblBien);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("GEINBIPUB","onUpgrade()");
            db.execSQL("DROP TABLE IF EXISTS " + TBL_BIENES );
            this.onCreate(db);

        }


        public ArrayList<Bien> listar() {
            Log.d("GEINBIPUB", "DBBien.listar()" );
            SQLiteDatabase db = this.getReadableDatabase();
            String[] campos = new String[FldBienes.values().length];
            for (FldBienes c: FldBienes.values()) {
                campos[c.ordinal()] = c.campo;
                Log.d("GEINBIPUB",c.campo);
            }
            Cursor c = db.query(TBL_BIENES, campos, null,null,null,null,null);
            ArrayList<Bien> array = new ArrayList<Bien>();
            while(c.moveToNext()) {
                array.add(new Bien(c.getFloat(c.getColumnIndex(LATITUD.campo)),
                        c.getFloat(c.getColumnIndex(LONGITUD.campo)),
                        c.getString(c.getColumnIndex(TITULO.campo)),
                        c.getString(c.getColumnIndex(DESCRIPCION.campo)),
                        c.getInt(c.getColumnIndex(FldBienes.ID.campo))
                ));
            }
            return array;
        }

        public void guardar(Bien b) {
            ContentValues cv = new ContentValues();
            cv.put(LATITUD.campo,b.getLatitud());
            cv.put(LONGITUD.campo, b.getLongitud());
            cv.put(TITULO.campo, b.getTitulo());
            cv.put(DESCRIPCION.campo,b.getDetalle());
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(TBL_BIENES, null,cv);
            db.close();
        }
    }
}
