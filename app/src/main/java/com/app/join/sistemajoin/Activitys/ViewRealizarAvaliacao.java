package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.join.sistemajoin.Model.Avaliacao;
import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;

public class ViewRealizarAvaliacao extends AppCompatActivity {

    RatingBar ratingBar;
    Button btEnviarAv;
    TextView tvNomeAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_realizar_avaliacao);


        ratingBar=(RatingBar)findViewById(R.id.ratingBar);
        btEnviarAv=(Button) findViewById(R.id.btEnviarAv);
        tvNomeAluno=(TextView)findViewById(R.id.tvNomeAluno);

        final Intent cdg = getIntent();

        btEnviarAv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setAv(ratingBar.getNumStars());
                long date = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
                String dateString = sdf.format(date);
                avaliacao.setDataAv(dateString);
                avaliacao.setIdAluno(cdg.getStringExtra("key"));
                salvarAvaliacao(avaliacao);
                Intent in =new Intent(ViewRealizarAvaliacao.this, ViewSelecionarAlunos.class);
                in.putExtra("codigo", "1");
                startActivity(in);
            }
        });
    }

    private void salvarAvaliacao(Avaliacao av){
        DatabaseReference dataAv = ConfiguracaoFirebase.getFirebase().child("avaliacao");
        dataAv.push().setValue(av);
    }
}
