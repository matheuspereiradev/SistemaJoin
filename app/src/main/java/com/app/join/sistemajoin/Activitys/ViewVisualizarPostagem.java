package com.app.join.sistemajoin.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.app.join.sistemajoin.R;

public class ViewVisualizarPostagem extends AppCompatActivity {

    TextView tituloPostagem, textoPostagem, dataPostagem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_visualizar_postagem);
        tituloPostagem=(TextView) findViewById(R.id.tituloPostagem);
        textoPostagem=(TextView) findViewById(R.id.textoPostagem);
        dataPostagem=(TextView) findViewById(R.id.dataPostagem);
    }
}
