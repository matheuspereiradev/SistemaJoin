package com.app.join.sistemajoin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class ViewListarEscolas extends AppCompatActivity {

    ListView listaEscolas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listar_escolas);

        //=========inicio da conexão entre java e xml
        listaEscolas = (ListView) findViewById(R.id.lwEscolasCadastradas);
        //fim da conexão xml e java ==========


    }
}
