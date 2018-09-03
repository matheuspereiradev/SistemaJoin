package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.join.sistemajoin.R;

public class ViewRealizarPostagem extends AppCompatActivity {

    EditText ctTituloPost,ctMsgPost;
    Button btSelecionarAlunos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_realizar_postagem);

        ctTituloPost=(EditText)findViewById(R.id.ctTituloPost);
        ctMsgPost=(EditText)findViewById(R.id.ctMsgPost);
        btSelecionarAlunos=(Button)findViewById(R.id.btSelecionarAlunos);


        btSelecionarAlunos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getBaseContext(),ViewSelecionarAlunos.class);
                i.putExtra("titulo", ctTituloPost.getText());
                i.putExtra("msg", ctMsgPost.getText());
                startActivity(i);
            }
        });


    }
}
