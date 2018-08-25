package com.app.join.sistemajoin.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.app.join.sistemajoin.R;

public class ViewListarAlunos extends AppCompatActivity {

    ListView listaDeAlunos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listar_alunos);

        listaDeAlunos=(ListView)findViewById(R.id.lwListaAlunos);
    }
}
