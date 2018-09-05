package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.app.join.sistemajoin.Adapter.EscolaAdapter;
import com.app.join.sistemajoin.Adapter.PostagemAdapter;
import com.app.join.sistemajoin.Model.Agenda;
import com.app.join.sistemajoin.Model.Escola;
import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewListarPostagens extends AppCompatActivity {

    private ListView listview;
    private ArrayAdapter<Agenda> adapter;
    private ArrayList<Agenda> lista;
    private Agenda agenda, variavel;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listar_postagens);

        lista = new ArrayList();
        listview = findViewById(R.id.tbPostagens);
        adapter = new PostagemAdapter(this, lista);
        listview.setAdapter(adapter);
        firebase = ConfiguracaoFirebase.getFirebase().child("agenda");

        Toast.makeText(ViewListarPostagens.this, "Clique na Lista para ver o conteuda da Anotação", Toast.LENGTH_LONG).show();

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lista.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    agenda = dados.getValue(Agenda.class);

                    lista.add(agenda);
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
                variavel = adapter.getItem(i);
                firebase = ConfiguracaoFirebase.getFirebase().child("agenda");
                firebase.child(variavel.getId());
                Intent in = new Intent(ViewListarPostagens.this, ViewVisualizarPostagem.class);
                in.putExtra("key", variavel.getId());
                in.putExtra("titulo", variavel.getTitulo());
                in.putExtra("msg", variavel.getMensagem());
                in.putExtra("nomeCriador", variavel.getNomeCriador());
                in.putExtra("data", variavel.getData());
                startActivity(in);

            }
        });
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
