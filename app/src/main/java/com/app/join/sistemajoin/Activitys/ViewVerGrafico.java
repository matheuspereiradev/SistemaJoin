package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.app.join.sistemajoin.Adapter.AvaliacaoAdapter;
import com.app.join.sistemajoin.Model.Avaliacao;
import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ViewVerGrafico extends AppCompatActivity {
    private ListView listview;
    private ArrayAdapter<Avaliacao> adapter;
    private ArrayList<Avaliacao> lista;
    private Avaliacao avaliacao, variavel;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListener;
    private Intent intent;
    private GraphView graph;
    LineGraphSeries<DataPoint> series;
    int x = 0, y = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ver_grafico);

        graph = (GraphView) findViewById(R.id.graph);

        intent = getIntent();
        lista = new ArrayList();
        listview = new ListView(this);
        adapter = new AvaliacaoAdapter(this, lista);
        listview.setAdapter(adapter);
        firebase = ConfiguracaoFirebase.getFirebase().child("avaliacao");
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lista.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    avaliacao = dados.getValue(Avaliacao.class);
                    if (avaliacao.getIdAluno().equals(intent.getStringExtra("idAluno"))) {
                    lista.add(avaliacao);
                    }
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        series = new LineGraphSeries<>(
                generateData()
        );

        graph.addSeries(series);

    }

    private DataPoint[] generateData() {
        //int tamanho = lista.size();
        SimpleDateFormat formato = new SimpleDateFormat("MM/yyyy");
         int tamanho = 8;
        DataPoint[] values = new DataPoint[tamanho];
        for (int i = 0; i < tamanho; i++) {
            // variavel = lista.get(i);
            // String data = variavel.getDataAv();
            // Date x = formato.parse(data);
            float x = i + 1;
            float y = i + 2;
            //float y = variavel.getAv();
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }
        return values;
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
