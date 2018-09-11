package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.join.sistemajoin.R;

public class ViewTelaHomeAluno extends AppCompatActivity {

    Button btSairPai,btVisualizarPost,btVisualizarAvaliacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_home_aluno);

        btSairPai=(Button)findViewById(R.id.btSairPai);
        btVisualizarPost=(Button)findViewById(R.id.btVisualizarPost);
        btVisualizarAvaliacao=(Button)findViewById(R.id.btVisualizarAvaliacao);

        final Intent idAluno = getIntent();

        btVisualizarPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent verPost = new Intent(ViewTelaHomeAluno.this, ViewVisualizarPostagem.class);
                verPost.putExtra("idAluno", idAluno.getStringExtra("id"));
                startActivity(verPost);
            }
        });

        btSairPai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
    }
}
