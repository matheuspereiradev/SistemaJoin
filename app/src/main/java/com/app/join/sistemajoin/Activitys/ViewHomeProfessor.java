package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.join.sistemajoin.R;

public class ViewHomeProfessor extends AppCompatActivity {

    Button btVerPost,btRealizaPost,btVerAvaliacao,btRealizarAvaliacao,btSairProf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_home_professor);

        btVerPost=(Button)findViewById(R.id.btVerPost);
        btRealizaPost=(Button)findViewById(R.id.btRealizaPost);
        btVerAvaliacao=(Button)findViewById(R.id.btVerAvaliacao);
        btRealizarAvaliacao=(Button)findViewById(R.id.btRealizarAvaliacao);
        btSairProf=(Button)findViewById(R.id.btSairProf);

        btRealizaPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chamaTelaPost();
            }
        });

    }

    private void chamaTelaPost(){
        Intent post = new Intent(ViewHomeProfessor.this, ViewRealizarPostagem.class);
        startActivity(post);
    }
}
