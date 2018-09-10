package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.app.join.sistemajoin.Model.Aluno;
import com.app.join.sistemajoin.Model.Escola;
import com.app.join.sistemajoin.Model.Professor;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.app.join.sistemajoin.Model.AdmJoin;
import com.app.join.sistemajoin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ViewTelaLogin extends AppCompatActivity {

    Switch swManterConectado;
    Button btEntrar;
    EditText ctSenhaUsr, ctLoginUsr;

    private FirebaseAuth autenticacao;
    private AdmJoin admJoin;
    private Escola escola, esc;
    private Professor professor;
    private DatabaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tela_login);

        swManterConectado = (Switch) findViewById(R.id.swManterConectado);
        btEntrar = (Button) findViewById(R.id.btEntrar);
        ctSenhaUsr = (EditText) findViewById(R.id.ctSenhaUsr);
        ctLoginUsr = (EditText) findViewById(R.id.ctLoginUsr);


        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ctLoginUsr.getText().toString().equals("") && !ctSenhaUsr.getText().toString().equals("")) {
                    admJoin = new AdmJoin();
                    admJoin.setEmail(ctLoginUsr.getText().toString());
                    admJoin.setSenha(ctSenhaUsr.getText().toString());
                    validaLogin();
                } else {
                    Toast.makeText(ViewTelaLogin.this, "Prencha todos os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void validaLogin() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(admJoin.getEmail(), admJoin.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    escola = confereEscola();
                    if (admJoin.getEmail().equals("projetojoin.thread@gmail.com")) {
                        Intent in = new Intent(ViewTelaLogin.this, ViewHomeSistemaAdministrativo.class);
                        startActivity(in);
                    } else if (confereEscola() != null) {
                        Intent in = new Intent(ViewTelaLogin.this, ViewHomeSistemaEscola.class);
                        in.putExtra("id", admJoin.getEmail());
                        startActivity(in);
                    } else if (admJoin.getEmail().equals("professor@join.com")) {
                        Intent in = new Intent(ViewTelaLogin.this, ViewHomeProfessor.class);
                        startActivity(in);
                    } else if (admJoin.getEmail().equals("aluno@join.com")) {
                        Intent in = new Intent(ViewTelaLogin.this, ViewTelaHomeAluno.class);
                        startActivity(in);
                    } else {
                        Toast.makeText(ViewTelaLogin.this, "Erro no login", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(ViewTelaLogin.this, "Email ou Senha Inválido", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Escola confereEscola() {
        esc = new Escola();
        firebase = ConfiguracaoFirebase.getFirebase();
        Query query = firebase.child("escola").orderByChild("email").equalTo(admJoin.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    esc = dados.getValue(Escola.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return esc;
    }

    private Professor confereProfessor() {
        firebase = ConfiguracaoFirebase.getFirebase().child("professor");
        Query query = firebase;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Professor pro = dados.getValue(Professor.class);
                    if (pro.getEmail().equals(admJoin.getEmail())) {
                        professor = pro;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return professor;
    }

}
