package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.join.sistemajoin.R;

public class ViewTelaHomeAluno extends AppCompatActivity {

    private Intent intent;
    private Button btSairPai,btVisualizarPost,btVisualizarAvaliacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_home_aluno);

        btSairPai=(Button)findViewById(R.id.btSairPai);
        btVisualizarPost=(Button)findViewById(R.id.btVisualizarPost);
        btVisualizarAvaliacao=(Button)findViewById(R.id.btVisualizarAvaliacao);

        intent = getIntent();

        btVisualizarPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent verPost = new Intent(ViewTelaHomeAluno.this, ViewSelecionarFilho.class);
                verPost.putExtra("idAluno", intent.getStringExtra("idAluno"));
                verPost.putExtra("cpfRes", intent.getStringExtra("cpfRes"));
                verPost.putExtra("idEscola", intent.getStringExtra("idEscola"));
                verPost.putExtra("post", "post");
                startActivity(verPost);
            }
        });

        btVisualizarAvaliacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent verPost = new Intent(ViewTelaHomeAluno.this, ViewSelecionarFilho.class);
                verPost.putExtra("idAluno", intent.getStringExtra("idAluno"));
                verPost.putExtra("cpfRes", intent.getStringExtra("cpfRes"));
                verPost.putExtra("idEscola", intent.getStringExtra("idEscola"));
                verPost.putExtra("post", "graph");
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
