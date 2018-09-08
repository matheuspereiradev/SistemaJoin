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
    EditText nome;
    Button salvar;
    Spinner turno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cadastrar_turma);

        nome = findViewById(R.id.ctNomeTurma);
        salvar = findViewById(R.id.btSalvarTurma);
        //turno=findViewById(R.id.spnTurno);

        ArrayAdapter adapterTurno=  ArrayAdapter.createFromResource(getBaseContext(),R.array.turno,
                R.layout.support_simple_spinner_dropdown_item);

        turno.setAdapter(adapterTurno);
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nome.getText().equals("")) {
                    Toast.makeText(getBaseContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                } else {
                    Turma turma = new Turma();
                    turma.setNome(nome.getText().toString());
                    String idUsuario = Base64Custon.codificadorBase64(turma.getNome());
                    turma.setId(idUsuario);
                    turma.setNomeProfessor("sem Professor");
                    salvarTurma(turma);
                    chamaTelaListaTurma();
                    finish();
                }
            }
        });


    }

    private void salvarTurma(Turma t) {
        DatabaseReference dataTurma = ConfiguracaoFirebase.getFirebase().child("turma");
        dataTurma.child(t.getId()).setValue(t);
    }

    private void chamaTelaListaTurma() {
        Intent listTurma = new Intent(ViewCadastrarTurma.this, ViewListarTurmas.class);
        startActivity(listTurma);
    }


}
