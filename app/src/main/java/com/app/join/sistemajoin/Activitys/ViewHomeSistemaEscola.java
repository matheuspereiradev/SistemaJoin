package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.join.sistemajoin.R;

public class ViewHomeSistemaEscola extends AppCompatActivity {

    Button btListProfessor,btListTurma,btNovoProfessor,btNovaTurma, btListAluno, btNovoaluno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_home_sistema_escola);

        btListProfessor=(Button)findViewById(R.id.btListProf);
        btListTurma=(Button)findViewById(R.id.btListTurmas);
        btNovoProfessor=(Button)findViewById(R.id.btNovoProfessor);
        btNovaTurma=(Button)findViewById(R.id.btNovaTurma);
        btListAluno = findViewById(R.id.btListarAlunos);
        btNovoaluno = findViewById(R.id.btNovoAluno);

        btListProfessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(getBaseContext(), ViewListaProfessores.class);
                startActivity(i1);
            }
        });

        btListTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(getBaseContext(), ViewListarTurmas.class);
                startActivity(i2);
            }
        });

        btNovoProfessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i3 = new Intent(getBaseContext(), ViewCadastrarProfessor.class);
                startActivity(i3);
            }
        });

        btNovaTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i4 = new Intent(getBaseContext(), ViewCadastrarTurma.class);
                startActivity(i4);
            }
        });

        btListAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i5 = new Intent(getBaseContext(), ViewListarAlunos.class);
                startActivity(i5);
            }
        });
        btNovoaluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i6 = new Intent(getBaseContext(), ViewCadastrarAluno.class);
                startActivity(i6);
            }
        });
    }

}
