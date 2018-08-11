package com.app.join.sistemajoin;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.app.join.sistemajoin.Tools.ConexaoWeb;

public class ViewTelaLogin extends AppCompatActivity {

    Switch swManterConectado;
    Button btEntrar;
    EditText ctSenhaUsr, ctLoginUsr;

    String url = "";
    String parametros = "";

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

                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null || networkInfo.isConnected()) {
                    String email = ctLoginUsr.getText().toString();
                    String senha = ctSenhaUsr.getText().toString();
                    if (email.isEmpty() || senha.isEmpty()) {
                        Toast.makeText(ViewTelaLogin.this, "Campo(s) vazio", Toast.LENGTH_SHORT).show();
                    } else {
                        url = "http://10.11.15.118/login/logar.php";
                        parametros = "email=" + email + "&senha=" + senha;
                        new SolicitaDados().execute(url);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Não há Conexão", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private class SolicitaDados extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return ConexaoWeb.postDados(urls[0], parametros);

        }

        @Override
        protected void onPostExecute(String resultado) {
            String dados[] = resultado.split(",");
            if (resultado.contains("Login_Ok")) {
                Intent abreInicio = new Intent(ViewTelaLogin.this, MainActivity.class);
                abreInicio.putExtra("idUsuario", dados[1]);
                abreInicio.putExtra("nomeUsuario", dados[2]);
                startActivity(abreInicio);
            } else {
                Toast.makeText(getApplicationContext(), "Usuário ou senha incorretos", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
