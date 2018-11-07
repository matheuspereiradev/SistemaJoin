package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.app.join.sistemajoin.Adapter.ProfessorAdapter;
import com.app.join.sistemajoin.Model.Aluno;
import com.app.join.sistemajoin.Model.Professor;
import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewHomeProfessor extends AppCompatActivity {

    private Button btVerPost, btRealizaPost, btVerAvaliacao, btRealizarAvaliacao;
    private Intent intent;
    private ListView listview;
    private TextView tvNomeProf;
    private ArrayAdapter<Professor> adapter;
    private ArrayList<Professor> lista;
    private Professor professor, variavel;
    private DatabaseReference firebase;
    private Button btSairProf;
    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_home_professor);


        btVerPost = (Button) findViewById(R.id.btVerPost);
        btRealizaPost = (Button) findViewById(R.id.btRealizaPost);
        btVerAvaliacao = (Button) findViewById(R.id.btVerAvaliacao);
        btRealizarAvaliacao = (Button) findViewById(R.id.btRealizarAvaliacao);
        btSairProf=(Button) findViewById(R.id.btSairProf);

        intent = getIntent();
        lista = new ArrayList();
        listview = new ListView(this);
        adapter = new ProfessorAdapter(this, lista);
        listview.setAdapter(adapter);
        firebase = ConfiguracaoFirebase.getFirebase().child("professor");
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lista.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    professor = dados.getValue(Professor.class);
                    if (intent.getStringExtra("idProfessor").equals(professor.getIdProfessor())) {
                        variavel = professor;
                        lista.add(professor);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };


        btRealizaPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent post = new Intent(ViewHomeProfessor.this, ViewSelecionarAlunos.class);
                post.putExtra("codigo", "2");
                post.putExtra("idProfessor", intent.getStringExtra("idProfessor"));
                post.putExtra("keyTurma", variavel.getKeyTurma());
                startActivity(post);
            }
        });
        btVerPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent verPost = new Intent(ViewHomeProfessor.this, ViewSelecionarAlunos.class);
                verPost.putExtra("idProfessor", intent.getStringExtra("idProfessor"));
                verPost.putExtra("codigo", "4");
                verPost.putExtra("keyTurma", variavel.getKeyTurma());

                startActivity(verPost);
            }
        });
        btRealizarAvaliacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent av = new Intent(ViewHomeProfessor.this, ViewSelecionarAlunos.class);
                av.putExtra("codigo", "1");
                av.putExtra("idProfessor", intent.getStringExtra("idProfessor"));
                av.putExtra("keyTurma", variavel.getKeyTurma());
                startActivity(av);
            }
        });
        btVerAvaliacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listav = new Intent(ViewHomeProfessor.this, ViewSelecionarAlunos.class);
                listav.putExtra("codigo", "3");
                listav.putExtra("idProfessor", intent.getStringExtra("idProfessor"));
                listav.putExtra("keyTurma", variavel.getKeyTurma());

                startActivity(listav);
            }
        });

        btSairProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logout = new Intent(ViewHomeProfessor.this, ViewTelaLogin.class);
                startActivity(logout);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListener);
    }

}
