package com.app.join.sistemajoin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class ViewTelaLogin extends AppCompatActivity {


    Switch swManterConectado;
    Button btEntrar;
    EditText ctSenhaUsr,ctLoginUsr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tela_login);

        //=========inicio da conexão xml com java
        swManterConectado =(Switch)findViewById(R.id.swManterConectado);
        btEntrar=(Button)findViewById(R.id.btEntrar);
        ctSenhaUsr=(EditText)findViewById(R.id.ctSenhaUsr);
        ctLoginUsr=(EditText)findViewById(R.id.ctLoginUsr);
        //fim da conexão==========


    }
}
