package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.app.join.sistemajoin.Adapter.AvaliacaoAdapter;
import com.app.join.sistemajoin.Model.Avaliacao;
import com.app.join.sistemajoin.Model.Escola;
import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ViewListarAvaliacoes extends AppCompatActivity {

    private ListView listview;
    private ArrayAdapter<Avaliacao> adapter;
    private ArrayList<Avaliacao> lista;
    private Avaliacao avaliacao, variavel;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListener;
    private Intent intent;
    private String cdg = "salvar";
    private String idAv;
    private int rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listar_avaliacoes);

        intent = getIntent();
        lista = new ArrayList();
        listview = findViewById(R.id.tbListaAvaliacoes);
        adapter = new AvaliacaoAdapter(this, lista);
        listview.setAdapter(adapter);
        firebase = ConfiguracaoFirebase.getFirebase().child("avaliacao");
        if(intent.getStringExtra("codigo").equals("av")){
            long date = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
            final String dateString = sdf.format(date);
            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    lista.clear();
                    for (DataSnapshot dados : dataSnapshot.getChildren()) {
                        avaliacao = dados.getValue(Avaliacao.class);
                        if (avaliacao.getDataAv().equals(dateString)) {
                            cdg = "editar";
                            idAv = avaliacao.getIdAvaliacao();
                            rating = avaliacao.getAv();
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            Intent in = new Intent(ViewListarAvaliacoes.this, ViewRealizarAvaliacao.class);
            in.putExtra("cdg", cdg);
            in.putExtra("idAvaliacao", idAv);
            in.putExtra("rating", rating);
            startActivity(in);
            finish();
        }else {
            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    lista.clear();
                    for (DataSnapshot dados : dataSnapshot.getChildren()) {
                        avaliacao = dados.getValue(Avaliacao.class);
                        if (intent.getStringExtra("idAluno").equals(avaliacao.getIdAluno())) {
                            lista.add(avaliacao);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
        }

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
