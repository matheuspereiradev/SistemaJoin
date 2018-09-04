package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.join.sistemajoin.Model.Agenda;
import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.Base64Custon;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.app.join.sistemajoin.Tools.DadosLogados;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;

public class ViewRealizarPostagem extends AppCompatActivity {

    EditText ctTituloPost,ctMsgPost;
    Button btSelecionarAlunos;
    DadosLogados dadosLogados;
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
                salvarPost(setDadosAgenda());
                chamaListaPost();
                finish();
            }
        });

    }

    private void chamaListaPost() {
        Intent listPost = new Intent(ViewRealizarPostagem.this, ViewListarPostagens.class);
        startActivity(listPost);
    }

    private Agenda setDadosAgenda(){
        Intent post = getIntent();
        Agenda agenda = new Agenda();
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        agenda.setData(dateString);
        agenda.setTitulo(ctTituloPost.getText().toString());
        agenda.setIdDestino(post.getStringExtra("key"));
        agenda.setMensagem(ctMsgPost.getText().toString());
        String idUsuario = Base64Custon.codificadorBase64(agenda.getData());
        agenda.setId(idUsuario);
        agenda.setNomeCriador(dadosLogados.getNomeLogado());
        return agenda;
    }

    private void salvarPost(Agenda a){
        DatabaseReference data = ConfiguracaoFirebase.getFirebase().child("post");
        data.push().setValue(a);
    }
}
