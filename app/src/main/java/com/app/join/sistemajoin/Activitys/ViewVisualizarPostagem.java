package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.app.join.sistemajoin.R;

public class ViewVisualizarPostagem extends AppCompatActivity {

    TextView tituloPostagem, textoPostagem, dataPostagem, nomeAutor;
    Intent post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_visualizar_postagem);
        tituloPostagem=(TextView) findViewById(R.id.tituloPostagem);
        textoPostagem=(TextView) findViewById(R.id.textoPostagem);
        dataPostagem=(TextView) findViewById(R.id.dataPostagem);
        nomeAutor = findViewById(R.id.tvRemetenteMsg);

        preencheCampos();

    }

    private void preencheCampos(){
        post = getIntent();
        tituloPostagem.setText(post.getStringExtra("titulo"));
        textoPostagem.setText(post.getStringExtra("msg"));
        dataPostagem.setText(post.getStringExtra("data"));
        nomeAutor.setText(post.getStringExtra("nomeCriador"));
    }
}
