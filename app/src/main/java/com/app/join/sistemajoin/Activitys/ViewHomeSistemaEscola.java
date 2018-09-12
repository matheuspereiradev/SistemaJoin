package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.app.join.sistemajoin.R;

public class ViewHomeSistemaEscola extends AppCompatActivity {

    Button LTurma, NTurma, NProf, LProf, LAluno, NAluno, btSairEsc;
    Intent pegaDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_home_sistema_escola);

        LProf = (Button) findViewById(R.id.LProf);
        LTurma = (Button) findViewById(R.id.LTurma);
        NProf = (Button) findViewById(R.id.NProf);
        NTurma = (Button) findViewById(R.id.NTurma);
        LAluno = (Button) findViewById(R.id.LAluno);
        NAluno = (Button) findViewById(R.id.NAluno);
        btSairEsc= (Button) findViewById(R.id.btSairEsc);

        pegaDados = getIntent();

        LProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChamaListarProfessor();
            }
        });

        NProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChamaCadastroProfessor();
            }
        });

        NTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChamaCadastroTurma();
            }
        });

        LTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChamaListarTurma();
            }
        });

        NAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChamaCadastroAluno();
            }
        });

        LAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChamaListarAluno();
            }
        });

        btSairEsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });

    }

    public void ChamaCadastroAluno() {
        Intent cadAluno = new Intent(ViewHomeSistemaEscola.this, ViewCadastrarAluno.class);
        cadAluno.putExtra("id", pegaDados.getStringExtra("id"));
        startActivity(cadAluno);
    }

    public void ChamaCadastroProfessor() {
        Intent cadProf = new Intent(ViewHomeSistemaEscola.this, ViewCadastrarProfessor.class);
        cadProf.putExtra("id", pegaDados.getStringExtra("id"));
        startActivity(cadProf);
    }

    public void ChamaCadastroTurma() {
        Intent cadTurma = new Intent(ViewHomeSistemaEscola.this, ViewCadastrarTurma.class);
        cadTurma.putExtra("id", pegaDados.getStringExtra("id"));
        startActivity(cadTurma);
    }

    public void ChamaListarTurma() {
        Intent listTurma = new Intent(ViewHomeSistemaEscola.this, ViewListarTurmas.class);
        listTurma.putExtra("id", pegaDados.getStringExtra("id"));
        startActivity(listTurma);
    }

    public void ChamaListarProfessor() {
        Intent listProf = new Intent(ViewHomeSistemaEscola.this, ViewListaProfessores.class);
        listProf.putExtra("id", pegaDados.getStringExtra("id"));
        startActivity(listProf);
    }

    public void ChamaListarAluno() {
        Intent listaluno = new Intent(ViewHomeSistemaEscola.this, ViewListarAlunos.class);
        listaluno.putExtra("id", pegaDados.getStringExtra("id"));
        startActivity(listaluno);
    }
}