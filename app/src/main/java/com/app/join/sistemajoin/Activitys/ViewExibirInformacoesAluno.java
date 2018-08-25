package com.app.join.sistemajoin.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.join.sistemajoin.R;

public class ViewExibirInformacoesAluno extends AppCompatActivity {

    TextView tvNomeAluno,tvMatriculaAluno,tvTelResponsavel,tvNomeResponsavel,tvCPFResponsavel,tvSenhaAluno;
    ImageButton btExcluirAluno,btEditarAluno,btConfigAluno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exibir_informacoes_aluno);

        tvNomeAluno=(TextView)findViewById(R.id.tvNomeAluno);
        tvMatriculaAluno=(TextView)findViewById(R.id.tvTelResponsavel);
        tvTelResponsavel=(TextView)findViewById(R.id.tvTelResponsavel);
        tvNomeResponsavel=(TextView)findViewById(R.id.tvNomeResponsavel);
        tvCPFResponsavel=(TextView)findViewById(R.id.tvCPFResponsavel);
        tvSenhaAluno=(TextView)findViewById(R.id.tvSenhaAluno);
        btConfigAluno = (ImageButton) findViewById(R.id.btConfigAluno);
        btEditarAluno = (ImageButton) findViewById(R.id.btEditarAluno);
        btExcluirAluno = (ImageButton) findViewById(R.id.btExcluirAluno);
    }
}
