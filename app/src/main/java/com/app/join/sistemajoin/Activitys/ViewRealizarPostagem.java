package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.join.sistemajoin.Model.Agenda;
import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.Base64Custon;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;

public class ViewRealizarPostagem extends AppCompatActivity {

    private TextView tvnomealunopost;
    private EditText ctTituloPost, ctMsgPost;
    private Button btEnviarPost;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_realizar_postagem);

        tvnomealunopost = (TextView) findViewById(R.id.tvnomealunopost);
        ctTituloPost = (EditText) findViewById(R.id.ctTituloPost);
        ctMsgPost = (EditText) findViewById(R.id.ctMsgPost);
        btEnviarPost = (Button) findViewById(R.id.btEnviarPost);

        intent = getIntent();
        tvnomealunopost.setText(intent.getStringExtra("nome"));

        if (intent.getStringExtra("remetente").equals("editar")) {
            preencheCampos();
            btEnviarPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ctTituloPost.equals("") && ctMsgPost.equals("")) {
                        Toast.makeText(ViewRealizarPostagem.this, "Favor Preencha todos os campos!", Toast.LENGTH_LONG).show();
                    } else {
                        Agenda post = setDadosEditarAgenda();
                        editarPost(post);
                        chamaListaPost();
                        finish();
                    }
                }
            });
        } else {
            btEnviarPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ctTituloPost.equals("") && ctMsgPost.equals("")) {
                        Toast.makeText(ViewRealizarPostagem.this, "Favor Preencha todos os campos!", Toast.LENGTH_LONG).show();
                    } else {
                        Agenda post = setDadosAgenda();
                        salvarPost(post);
                        chamaListaPost();
                        finish();
                    }
                }
            });

        }
    }

    private void chamaListaPost() {
        Intent listPost = new Intent(ViewRealizarPostagem.this, ViewHomeProfessor.class);
        listPost.putExtra("idProfessor", intent.getStringExtra("idProfessor"));
        listPost.putExtra("idAluno", intent.getStringExtra("idAluno"));
        listPost.putExtra("remetente", intent.getStringExtra("professor"));
        startActivity(listPost);
    }

    private Agenda setDadosAgenda() {
        Agenda agenda = new Agenda();
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        agenda.setData(dateString);
        agenda.setTitulo(ctTituloPost.getText().toString());
        agenda.setIdDestino(intent.getStringExtra("idAluno"));
        agenda.setMensagem(ctMsgPost.getText().toString());
        agenda.setIdProfessor(intent.getStringExtra("idprofessor"));
        String idUsuario = Base64Custon.codificadorBase64(agenda.getMensagem() + agenda.getData());
        agenda.setIdAgenda(idUsuario);
        return agenda;
    }

    private Agenda setDadosEditarAgenda() {
        Agenda agenda = new Agenda();
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = sdf.format(date);
        agenda.setData(dateString);
        agenda.setTitulo(ctTituloPost.getText().toString());
        agenda.setIdDestino(intent.getStringExtra("idAluno"));
        agenda.setMensagem(ctMsgPost.getText().toString());
        agenda.setIdProfessor(intent.getStringExtra("idprofessor"));
        agenda.setIdAgenda(intent.getStringExtra("idAgenda"));
        return agenda;
    }

    private void salvarPost(Agenda a) {
        DatabaseReference data = ConfiguracaoFirebase.getFirebase().child("agenda");
        data.child(a.getIdAgenda()).setValue(a);
    }

    private void preencheCampos() {
        ctTituloPost.setText(intent.getStringExtra("titulo"));
        ctMsgPost.setText(intent.getStringExtra("msg"));
    }

    private void editarPost(Agenda a) {
        DatabaseReference data = ConfiguracaoFirebase.getFirebase().child("agenda");
        data.child(intent.getStringExtra("idAgenda")).updateChildren(a.toMap());
    }
}
