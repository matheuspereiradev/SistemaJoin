package com.app.join.sistemajoin.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.app.join.sistemajoin.R;

public class ViewCadastrarAluno extends AppCompatActivity {

    Button btSalvarAluno;
    EditText ctNomeAluno,ctMatricAluno,ctTelAluno,ctNomeResponsavel,ctCPFResp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cadastrar_aluno);

        ctNomeAluno=(EditText)findViewById(R.id.ctNomeAluno);
        ctMatricAluno=(EditText)findViewById(R.id.ctMatricAluno);
        ctTelAluno =(EditText)findViewById(R.id.ctTelAluno);
        ctNomeResponsavel=(EditText)findViewById(R.id.ctNomeResponsavel);
        ctCPFResp=(EditText)findViewById(R.id.ctCPFResp);
        btSalvarAluno=(Button)findViewById(R.id.btSalvarAluno);

    }
}
