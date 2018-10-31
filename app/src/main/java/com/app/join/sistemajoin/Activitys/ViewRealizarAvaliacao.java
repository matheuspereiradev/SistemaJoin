package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.join.sistemajoin.Adapter.AvaliacaoAdapter;
import com.app.join.sistemajoin.Model.Avaliacao;
import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.Base64Custon;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ViewRealizarAvaliacao extends AppCompatActivity {


    private Button btEnviarAv, btmenos, btmais;
    private TextView tvNomeAluno, tvtotaladd, tvNotaAtual;
    private Intent intent;
    private ListView listview;
    private ArrayAdapter<Avaliacao> adapter;
    private ArrayList<Avaliacao> lista;
    private Avaliacao avaliacao, variavel;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListener;
    private String av;
    private String dateString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_realizar_avaliacao);

        btmenos = (Button) findViewById(R.id.btmenos);
        btmais = (Button) findViewById(R.id.btmais);
        tvtotaladd = (TextView) findViewById(R.id.tvtotaladd);
        btEnviarAv = (Button) findViewById(R.id.btEnviarAv);
        tvNomeAluno = (TextView) findViewById(R.id.tvNomeAluno);
        tvNotaAtual = (TextView) findViewById(R.id.tvNotaAtual);

        intent = getIntent();
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
        dateString = sdf.format(date);
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
                    if (avaliacao.getDataAv().equals(dateString) && avaliacao.getIdAluno().equals(intent.getStringExtra("idAluno"))) {
                        av = avaliacao.getAv();
                        tvNotaAtual.setText(av);
                        lista.add(avaliacao);
                    }else{
                        av = "0";
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        tvNomeAluno.setText(intent.getStringExtra("nome"));
        tvtotaladd.setText("0");

        btmais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float var = Float.parseFloat(tvtotaladd.getText().toString());
                var++;
                if (var == 10) {

                }
                btmenos.isEnabled();
                tvtotaladd.setText(String.valueOf(var));
            }
        });

        btmenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float var = Float.parseFloat(tvtotaladd.getText().toString());
                var--;
                if (var == -10) {

                }
                btmais.isEnabled();
                tvtotaladd.setText(String.valueOf(var));
            }
        });

        btEnviarAv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Avaliacao avaliacao = new Avaliacao();
                float notaAtual, notaNova;
                notaAtual = (Float.valueOf(tvNotaAtual.getText().toString()));
                notaNova = (Float.valueOf(tvtotaladd.getText().toString()));
                if (notaAtual + notaNova > 10) {
                    avaliacao.setAv("10");
                } else if (notaNova > notaAtual) {
                    avaliacao.setAv("0");
                } else {
                    avaliacao.setAv(String.valueOf(notaAtual + notaNova));
                }
                avaliacao.setDataAv(dateString);
                avaliacao.setIdAluno(intent.getStringExtra("idAluno"));
                String idAv = Base64Custon.codificadorBase64(avaliacao.getDataAv() + avaliacao.getIdAluno());
                avaliacao.setIdAvaliacao(idAv);

                salvarAvaliacao(avaliacao);

                Intent in = new Intent(ViewRealizarAvaliacao.this, ViewSelecionarAlunos.class);
                in.putExtra("codigo", "1");
                in.putExtra("keyTurma", intent.getStringExtra("keyTurma"));
                startActivity(in);
                finish();
            }
        });
    }

    private void salvarAvaliacao(Avaliacao av) {
        DatabaseReference dataAv = ConfiguracaoFirebase.getFirebase().child("avaliacao");
        dataAv.child(av.getIdAvaliacao()).setValue(av);
    }

    private void editarAvaliacao(Avaliacao av) {
        DatabaseReference dataAv = ConfiguracaoFirebase.getFirebase().child("avaliacao");
        dataAv.child(av.getIdAvaliacao()).updateChildren(av.toMap());
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
