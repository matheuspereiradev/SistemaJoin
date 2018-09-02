package com.app.join.sistemajoin.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.app.join.sistemajoin.R;

public class ViewSelecionarAlunos extends AppCompatActivity {

    ListView listaSelecaoAlunos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selecionar_alunos);

        listaSelecaoAlunos=(ListView) findViewById(R.id.listaSelecaoAlunos);
    }
}
