package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.app.join.sistemajoin.Adapter.AlunoAdapter;
import com.app.join.sistemajoin.Adapter.EscolaAdapter;
import com.app.join.sistemajoin.Adapter.PostagemAdapter;
import com.app.join.sistemajoin.Adapter.ProfessorAdapter;
import com.app.join.sistemajoin.Model.Agenda;
import com.app.join.sistemajoin.Model.Aluno;
import com.app.join.sistemajoin.Model.Escola;
import com.app.join.sistemajoin.Model.Professor;
import com.app.join.sistemajoin.Tools.Base64Custon;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.app.join.sistemajoin.Model.AdmJoin;
import com.app.join.sistemajoin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ViewTelaLogin extends AppCompatActivity {

    Switch swManterConectado;
    Button btEntrar;
    EditText ctSenhaUsr, ctLoginUsr;

    private FirebaseAuth autenticacao;
    private AdmJoin admJoin;
    private ListView listviewEscola;
    private ArrayAdapter<Escola> adapterEscola;
    private ArrayList<Escola> listaEscola;
    private Escola escola, variavelEscola;
    private DatabaseReference firebaseEscola;
    private ValueEventListener valueEventListenerEscola;

    private ListView listviewAluno;
    private ArrayAdapter<Aluno> adapterAluno;
    private ArrayList<Aluno> listaAluno;
    private Aluno aluno, variavelAluno;
    private DatabaseReference firebaseAluno;
    private ValueEventListener valueEventListenerAluno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tela_login);

        swManterConectado = (Switch) findViewById(R.id.swManterConectado);
        btEntrar = (Button) findViewById(R.id.btEntrar);
        ctSenhaUsr = (EditText) findViewById(R.id.ctSenhaUsr);
        ctLoginUsr = (EditText) findViewById(R.id.ctLoginUsr);

        listaEscola = new ArrayList();
        listviewEscola = new ListView(this);
        adapterEscola = new EscolaAdapter(this, listaEscola);
        listviewEscola.setAdapter(adapterEscola);
        firebaseEscola = ConfiguracaoFirebase.getFirebase().child("escola");
        valueEventListenerEscola = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaEscola.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    escola = dados.getValue(Escola.class);
                    listaEscola.add(escola);
                }
                adapterEscola.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        listaAluno = new ArrayList();
        listviewAluno = new ListView(this);
        adapterAluno = new AlunoAdapter(this, listaAluno);
        listviewAluno.setAdapter(adapterAluno);
        firebaseAluno = ConfiguracaoFirebase.getFirebase().child("aluno");
        valueEventListenerAluno = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaAluno.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    aluno = dados.getValue(Aluno.class);
                    listaAluno.add(aluno);
                }
                adapterAluno.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


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
                    String id = Base64Custon.codificadorBase64(admJoin.getEmail());
                    if (admJoin.getEmail().equals("projetojoin.thread@gmail.com")) {
                        Intent in = new Intent(ViewTelaLogin.this, ViewHomeSistemaAdministrativo.class);
                        startActivity(in);
                    } else if (confereEscola()) {
                        Intent in = new Intent(ViewTelaLogin.this, ViewHomeSistemaEscola.class);
                        in.putExtra("id", id);
                        startActivity(in);
                    } else {
                        Intent in = new Intent(ViewTelaLogin.this, ViewHomeProfessor.class);
                        in.putExtra("idProfessor", id);
                        startActivity(in);
                    }
                } else {
                    if (confereAluno()) {
                        // confereAluno();
                        Intent in = new Intent(ViewTelaLogin.this, ViewTelaHomeAluno.class);
                        in.putExtra("cpfRes", aluno.getCpfResponsavel());
                        startActivity(in);
                    } else {
                        Toast.makeText(ViewTelaLogin.this, "Email ou Senha Inválido", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean confereEscola() {
        int tamanho = 0;
        int position = 0;
        boolean resposta = false;
        tamanho = listaEscola.size();
        while (position < tamanho) {
            variavelEscola = listaEscola.get(position);
            if (variavelEscola.getEmail().equals(admJoin.getEmail())) {
                resposta = true;
            }
            position++;
        }
        return resposta;
    }

    private boolean confereAluno() {
        int tamanho = 0;
        int position = 0;
        boolean resposta = false;
        tamanho = listaAluno.size();
        while (position < tamanho) {
            variavelAluno = listaAluno.get(position);
            if (variavelAluno.getCpfResponsavel().equals(admJoin.getEmail()) && variavelAluno.getSenha().equals(admJoin.getSenha())) {
                resposta = true;
                aluno = variavelAluno;
            }
            position++;
        }
        return resposta;
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseEscola.removeEventListener(valueEventListenerEscola);
        firebaseAluno.removeEventListener(valueEventListenerAluno);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseEscola.addValueEventListener(valueEventListenerEscola);
        firebaseAluno.addValueEventListener(valueEventListenerAluno);
    }

}
