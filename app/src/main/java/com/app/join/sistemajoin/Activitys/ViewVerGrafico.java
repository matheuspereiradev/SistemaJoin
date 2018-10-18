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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewVerGrafico extends AppCompatActivity {
    private ListView listview;
    private ArrayAdapter<Avaliacao> adapter;
    private ArrayList<Avaliacao> lista;
    private Avaliacao avaliacao, variavel;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListener;
    private Intent intent;
    private GraphView graph;
    int x = 0, y = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ver_grafico);

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

        graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = null;
        try {
            series = new LineGraphSeries<>(
                /*    new DataPoint[]{
                            new DataPoint(2,2),
                            new DataPoint(2, 5),
                            new DataPoint(03, 3),
                            new DataPoint(04, 2),
                            new DataPoint(05, 3),
                            new DataPoint(06, 4),
                            new DataPoint(07, 5)

                    }*/
                    generateData()
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        graph.addSeries(series);

    }

    private DataPoint[] generateData() throws ParseException {
        int tamanho = lista.size();
        SimpleDateFormat formato = new SimpleDateFormat("MM/yyyy");
        // int tamanho = 8;
        DataPoint[] values = new DataPoint[tamanho];
        for (int i = 0; i < tamanho; i++) {
            variavel = lista.get(i);
            String data = variavel.getDataAv();
            Date x = formato.parse(data);
            double y = variavel.getAv();
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
