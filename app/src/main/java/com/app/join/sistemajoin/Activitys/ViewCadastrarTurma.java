package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.join.sistemajoin.Model.Turma;
import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.Base64Custon;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.database.DatabaseReference;

public class ViewCadastrarTurma extends AppCompatActivity {

    private EditText nome, idadeMin, idadeMax;
    private Button salvar;
    private Intent intent;
    private String nomeTurma;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cadastrar_turma);

        nome = findViewById(R.id.ctNomeTurma);
        salvar = findViewById(R.id.btSalvarTurma);
        idadeMin = findViewById(R.id.etidadeMin);
        idadeMax = findViewById(R.id.etIdadeMax);

        SimpleMaskFormatter mask = new SimpleMaskFormatter("NNN");
        MaskTextWatcher mascaraMin = new MaskTextWatcher(idadeMin, mask);
        MaskTextWatcher mascaraMax = new MaskTextWatcher(idadeMax, mask);
        idadeMin.addTextChangedListener(mascaraMin);
        idadeMax.addTextChangedListener(mascaraMax);

        intent = getIntent();

        if (intent.getStringExtra("cdg").equals("edt")) {
            nomeTurma = intent.getStringExtra("nome");
            nome.setText(intent.getStringExtra("nome"));
            idadeMin.setText(intent.getStringExtra("faixa1"));
            idadeMax.setText(intent.getStringExtra("faixa2"));
            salvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (nome.getText().length() < 1 && idadeMin.length() < 1 && idadeMax.length() < 1) {
                        Toast.makeText(getBaseContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    } else {
                        Turma turma = new Turma();
                        turma.setNome(nome.getText().toString());
                        turma.setIdTurma(intent.getStringExtra("idturma"));
                        turma.setIdEscola(intent.getStringExtra("idEscola"));
                        turma.setIdadeMax(idadeMax.getText().toString());
                        turma.setIdadeMin(idadeMin.getText().toString());
                        editarTurma(turma);
                        chamaTelaListaTurma();
                        finish();
                    }
                }
            });
        } else {
            salvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (nome.getText().length() < 1) {
                        Toast.makeText(getBaseContext(), "Favor ,preencher todos os campos!", Toast.LENGTH_SHORT).show();
                    } else {
                        Turma turma = new Turma();
                        turma.setNome(nome.getText().toString());
                        String idUsuario = Base64Custon.codificadorBase64(turma.getNome());
                        turma.setIdTurma(idUsuario);
                        turma.setIdEscola(intent.getStringExtra("idEscola"));
                        turma.setIdadeMax(idadeMax.getText().toString());
                        turma.setIdadeMin(idadeMin.getText().toString());
                        salvarTurma(turma);
                        chamaTelaListaTurma();
                        finish();
                    }
                }
            });
        }


    }

    private void salvarTurma(Turma t) {
        DatabaseReference dataTurma = ConfiguracaoFirebase.getFirebase().child("turma");
        dataTurma.child(t.getNome()).setValue(t);
    }

    private void editarTurma(Turma t) {
        DatabaseReference dataTurma = ConfiguracaoFirebase.getFirebase().child("turma");
        dataTurma.child(nomeTurma).removeValue();
        dataTurma.child(t.getNome()).updateChildren(t.toMap());
    }

    private void chamaTelaListaTurma() {
        Intent listTurma = new Intent(ViewCadastrarTurma.this, ViewListarTurmas.class);
        listTurma.putExtra("idEscola", intent.getStringExtra("idEscola"));
        listTurma.putExtra("remetente", "home");
        startActivity(listTurma);
    }


}
