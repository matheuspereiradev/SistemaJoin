package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.join.sistemajoin.Model.Professor;
import com.app.join.sistemajoin.Model.Turma;
import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.Base64Custon;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class ViewCadastrarTurma extends AppCompatActivity {
    EditText nome, idadeMin,idadeMax;
    Button salvar;
    private String idEscola;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cadastrar_turma);

        nome = findViewById(R.id.ctNomeTurma);
        salvar = findViewById(R.id.btSalvarTurma);
        idadeMin = findViewById(R.id.etidadeMin);
        idadeMax = findViewById(R.id.etIdadeMax);

        Intent idemail = getIntent();
        idEscola = idemail.getStringExtra("idEscola");


        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nome.getText().equals("")) {
                    Toast.makeText(getBaseContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                } else {
                    Turma turma = new Turma();
                    turma.setNome(nome.getText().toString());
                    String idUsuario = Base64Custon.codificadorBase64(turma.getNome());
                    turma.setIdTurma(idUsuario);
                    turma.setIdEscola(idEscola);
                    salvarTurma(turma);
                    chamaTelaListaTurma();
                    finish();
                }
            }
        });


    }

    private void salvarTurma(Turma t) {
        DatabaseReference dataTurma = ConfiguracaoFirebase.getFirebase().child("turma");
        dataTurma.child(t.getIdTurma()).setValue(t);
    }

    private void chamaTelaListaTurma() {
        Intent listTurma = new Intent(ViewCadastrarTurma.this, ViewListarTurmas.class);
        listTurma.putExtra("idEscola", idEscola);
        listTurma.putExtra("remetente", "home");
        startActivity(listTurma);
    }


}
