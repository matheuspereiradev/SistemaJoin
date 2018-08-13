package com.app.join.sistemajoin.Activitys;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.app.join.sistemajoin.DAO.ConfiguracaoFirebase;
import com.app.join.sistemajoin.Model.AdmJoin;
import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.ConexaoWeb;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ViewTelaLogin extends AppCompatActivity {

    Switch swManterConectado;
    Button btEntrar;
    EditText ctSenhaUsr, ctLoginUsr;

    private FirebaseAuth autenticacao;
    private AdmJoin admJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tela_login);

        //=========inicio da conexão xml com java
        swManterConectado = (Switch) findViewById(R.id.swManterConectado);
        btEntrar = (Button) findViewById(R.id.btEntrar);
        ctSenhaUsr = (EditText) findViewById(R.id.ctSenhaUsr);
        ctLoginUsr = (EditText) findViewById(R.id.ctLoginUsr);
        //fim da conexão==========

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
                    Intent in = new Intent(ViewTelaLogin.this, ViewHomeSistemaAdministrativo.class);
                    startActivity(in);
                    Toast.makeText(ViewTelaLogin.this, "Login OK", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ViewTelaLogin.this, "Email ou Senha Invalido", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

}
