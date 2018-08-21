package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.join.sistemajoin.R;

public class ViewHomeSistemaEscola extends AppCompatActivity {

    Button btListProfessor,btListTurma,btNovoProfessor,btNovaTurma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_home_sistema_escola);

        btListProfessor=(Button)findViewById(R.id.btListProfessor);
        btListTurma=(Button)findViewById(R.id.btListTurma);
        btNovoProfessor=(Button)findViewById(R.id.btNovoProfessor);
        btNovaTurma=(Button)findViewById(R.id.btNovaTurma);

        btListProfessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getBaseContext(), ViewListaProfessores.class);
                startActivity(in);
            }
        });

        btListTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getBaseContext(), ViewListaProfessores.class);
                startActivity(in);
            }
        });

        btNovoProfessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getBaseContext(), ViewCadastrarProfessor.class);
                startActivity(in);
            }
        });

        btNovaTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getBaseContext(), ViewTelaLogin.class);
                startActivity(in);
            }
        });
    }

}
