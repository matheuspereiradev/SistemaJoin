package com.app.join.sistemajoin.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.app.join.sistemajoin.Adapter.EscolaAdapter;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.app.join.sistemajoin.Model.Escola;
import com.app.join.sistemajoin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewListarEscolas extends AppCompatActivity {


    private ListView listview;
    private ArrayAdapter<Escola> adapter;
    private ArrayList<Escola> lista;
    private Escola escola, escolaExcluir;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListener;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listar_escolas);

        lista = new ArrayList();
        listview = findViewById(R.id.lwEscolasCadastradas);
        adapter = new EscolaAdapter(this, lista);
        listview.setAdapter(adapter);
        firebase = ConfiguracaoFirebase.getFirebase().child("escola");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lista.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    escola = dados.getValue(Escola.class);

                    lista.add(escola);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                escolaExcluir = adapter.getItem(i);

                firebase = ConfiguracaoFirebase.getFirebase().child("escola");
                firebase.child(escolaExcluir.getId());
                Intent in = new Intent(getBaseContext(), ViewExibirInformacoesEscola.class);
                in.putExtra("key", escolaExcluir.getId());
                in.putExtra("nome", escolaExcluir.getNome());
                in.putExtra("tel", escolaExcluir.getTelefone());
                in.putExtra("email", escolaExcluir.getEmail());
                in.putExtra("cnpj", escolaExcluir.getCnpj());
                in.putExtra("status", escolaExcluir.getStatus());
                startActivity(in);

            }
        });

        /*

         */


    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListener);
    }
}
