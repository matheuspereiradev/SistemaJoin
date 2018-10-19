package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.app.join.sistemajoin.Model.Avaliacao;
import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.Base64Custon;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import java.text.SimpleDateFormat;

public class ViewRealizarAvaliacao extends AppCompatActivity {

    private RatingBar ratingBar;
    private Button btEnviarAv;
    private TextView tvNomeAluno;
    private ImageView triste, normal, feliz, muitoFeliz;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_realizar_avaliacao);


        ratingBar = (RatingBar) findViewById(R.id.tbAvaliacao);
        btEnviarAv = (Button) findViewById(R.id.btEnviarAv);
        tvNomeAluno = (TextView) findViewById(R.id.tvNomeAluno);
        triste = findViewById(R.id.imgTriste);
        normal = findViewById(R.id.imgNormal);
        feliz = findViewById(R.id.imgFeliz);
        muitoFeliz = findViewById(R.id.imgMuitoFeliz);

        intent = getIntent();


        tvNomeAluno.setText(intent.getStringExtra("nome"));

        btEnviarAv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setAv(ratingBar.getRating());
                long date = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
                String dateString = sdf.format(date);
                avaliacao.setDataAv(dateString);
                avaliacao.setIdAluno(intent.getStringExtra("idAluno"));
                String idAv = Base64Custon.codificadorBase64(avaliacao.getDataAv() + avaliacao.getIdAluno());
                avaliacao.setIdAvaliacao(idAv);
                if (intent.getStringExtra("cdg").equals("editar")) {
                    editarAvaliacao(avaliacao);
                } else {
                    salvarAvaliacao(avaliacao);
                }
                Intent in = new Intent(ViewRealizarAvaliacao.this, ViewSelecionarAlunos.class);
                in.putExtra("codigo", "1");
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

}
