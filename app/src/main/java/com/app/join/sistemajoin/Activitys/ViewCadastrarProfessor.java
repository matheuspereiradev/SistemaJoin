package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.join.sistemajoin.Model.Escola;
import com.app.join.sistemajoin.Model.Professor;
import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.Base64Custon;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class ViewCadastrarProfessor extends AppCompatActivity {

    EditText ctEmailProf, ctNomeProf, ctDataNascProf;
    Button btProximoProf1;
    Professor professor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cadastrar_professor);

        //==========inicio da conexão java cm xml
        ctNomeProf = (EditText) findViewById(R.id.ctNomeProf);
        ctEmailProf = (EditText) findViewById(R.id.ctEmailProf);
        ctDataNascProf = (EditText) findViewById(R.id.ctDataNascProf);
        btProximoProf1 = (Button) findViewById(R.id.btProximoProf1);
        //fim da conexão==========


        //========aplicando mascara na data de nascimento
        SimpleMaskFormatter maskDate = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mascaraData = new MaskTextWatcher(ctDataNascProf, maskDate);
        ctDataNascProf.addTextChangedListener(mascaraData);
        //fim mascara======


        //função do botão prosseguir
        btProximoProf1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar(setDados());
                chamaTelaListaEscola();
            }
        });


    }

    private void chamaTelaListaEscola() {
        Intent i = new Intent(getBaseContext(), ViewListaProfessores.class);
        startActivity(i);
    }

    private void salvar(Professor p) {
        DatabaseReference data = ConfiguracaoFirebase.getFirebase().child("professor");
        data.child(p.getNome()).setValue(p);

    }

    private Professor setDados() {
        professor = new Professor();
        professor.setNome(ctNomeProf.getText().toString());
        professor.setDataNacimento(ctDataNascProf.getText().toString());
        professor.setEmail(ctEmailProf.getText().toString());
        String idUsuario = Base64Custon.codificadorBase64(professor.getEmail());
        professor.setIdProfessor(idUsuario);
        professor.setStatus("Ativo");
        professor.setTipo("Professor");
        professor.setSenha(geraSenha());
        professor.setRg("");
        professor.setCpf("");
        professor.setTelefone("88988888888");
        professor.setKeyTurma("sem Turma");
        return professor;
    }

    public String geraSenha() {
        String[] carct = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String senha = "";
        for (int x = 0; x < 9; x++) {
            int j = (int) (Math.random() * carct.length);
            senha += carct[j];
        }

        return senha;
    }


}
