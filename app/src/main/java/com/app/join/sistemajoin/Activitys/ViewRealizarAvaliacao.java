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
    private ListView listviewAv;
    private ArrayAdapter<Avaliacao> adapterAv;
    private ArrayList<Avaliacao> listaAv;
    private Avaliacao avaliacao, variavelAv;
    private DatabaseReference firebaseAv;
    private ValueEventListener valueEventListenerAV;
    private String av = "0";


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

        preencheCampos(pegaAv());

        btmais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float var = Float.parseFloat(tvtotaladd.getText().toString());
                var++;
                tvtotaladd.setText(String.valueOf(var));
            }
        });

        btmenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float var = Float.parseFloat(tvtotaladd.getText().toString());
                var--;
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
                avaliacao.setAv(String.valueOf(notaAtual + notaNova));
                long date = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
                String dateString = sdf.format(date);
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
        firebaseAv.removeEventListener(valueEventListenerAV);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAv.addValueEventListener(valueEventListenerAV);
    }

    private void preencheCampos(String av){
        tvNomeAluno.setText(intent.getStringExtra("nome"));
        tvNotaAtual.setText(av);
        tvtotaladd.setText("0");
    }

    private String pegaAv(){
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
        final String dateString = sdf.format(date);
        listaAv = new ArrayList();
        adapterAv = new AvaliacaoAdapter(this, listaAv);
        firebaseAv = ConfiguracaoFirebase.getFirebase().child("avaliacao");
        valueEventListenerAV = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaAv.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    avaliacao = dados.getValue(Avaliacao.class);
                    if (avaliacao.getIdAluno().equals(intent.getStringExtra("idAluno")) && dateString.equals(avaliacao.getDataAv())) {
                        av = avaliacao.getAv();
                    }
                }
                adapterAv.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        return av;
    }

}
