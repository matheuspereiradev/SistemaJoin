package com.app.join.sistemajoin.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.join.sistemajoin.R;

public class ViewExibirInformacoesProfessor extends AppCompatActivity {

    TextView cmpNomeProf,cmpTelProf,cmpEmailProf,cmpCPFProf,cmpDataNascProf,cmpRGProf,cmpFormacaoProf,cmpLoginProf,cmpSenhaProf;
    ImageButton btEditProf,btConfigProf,btExcluirProf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exibir_informacoes_professor);
        cmpNomeProf=(TextView)findViewById(R.id.cmpNomeProf);
        cmpTelProf=(TextView)findViewById(R.id.cmpTelProf);
        cmpEmailProf=(TextView)findViewById(R.id.cmpEmailEsc);
        cmpCPFProf = (TextView) findViewById(R.id.cmpCNPJEscola);
        cmpDataNascProf=(TextView)findViewById(R.id.cmpDataNascProf);
        cmpRGProf=(TextView)findViewById(R.id.cmpRGProf);
        cmpFormacaoProf=(TextView)findViewById(R.id.cmpFormacaoProf);
        cmpLoginProf=(TextView)findViewById(R.id.cmpLoginProf);
        cmpSenhaProf=(TextView)findViewById(R.id.cmpSenhaProf);
        btEditProf=(ImageButton)findViewById(R.id.btEditProf);
        btConfigProf=(ImageButton) findViewById(R.id.btConfigProf);
        btExcluirProf=(ImageButton)findViewById(R.id.btExcluirProf);
    }
}
