package com.app.join.sistemajoin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class ViewRealizarAvaliacao extends AppCompatActivity {

    RatingBar rbComp,rbAtvd,rbPart;
    Button btEnviarAv;
    TextView tvNomeAluno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_realizar_avaliacao);

        //==========conexão java com xml
        rbComp=(RatingBar)findViewById(R.id.rbComp);
        rbAtvd=(RatingBar)findViewById(R.id.rbAtvd);
        rbPart=(RatingBar)findViewById(R.id.rbPart);
        btEnviarAv=(Button) findViewById(R.id.btEnviarAv);
        tvNomeAluno=(TextView)findViewById(R.id.tvNomeAluno);
        //fim da conexão java com xml=================
    }
}
