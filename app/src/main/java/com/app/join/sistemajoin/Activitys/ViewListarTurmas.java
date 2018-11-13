package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.app.join.sistemajoin.Adapter.TurmaAdapter;
import com.app.join.sistemajoin.Model.Aluno;
import com.app.join.sistemajoin.Model.Professor;
import com.app.join.sistemajoin.Model.Turma;
import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewListarTurmas extends AppCompatActivity {

    private ListView listview;
    private ArrayAdapter<Turma> adapter;
    private ArrayList<Turma> lista;
    private Turma turma, variavel;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListener;
    private Intent intent;
    private Professor professor;
    private Aluno aluno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listar_turmas);

        intent = getIntent();
        lista = new ArrayList();
        listview = findViewById(R.id.lvTurmas);
        adapter = new TurmaAdapter(this, lista);
        listview.setAdapter(adapter);
        firebase = ConfiguracaoFirebase.getFirebase().child("turma");
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lista.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    turma = dados.getValue(Turma.class);
                    if (intent.getStringExtra("idEscola").equals(turma.getIdEscola())) {
                        lista.add(turma);
                    }
                }
                if (lista.size() == 0) {
                    if (intent.getStringExtra("remetente").equals("professor")) {
                        Intent listPro = new Intent(ViewListarTurmas.this, ViewListaProfessores.class);
                        listPro.putExtra("idEscola", intent.getStringExtra("idEscola"));
                        startActivity(listPro);
                        onStop();
                    } else {
                        Intent listAlu = new Intent(ViewListarTurmas.this, ViewListarAlunos.class);
                        listAlu.putExtra("idEscola", intent.getStringExtra("idEscola"));
                        startActivity(listAlu);
                        onStop();
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                variavel = adapter.getItem(i);
                if (intent.getStringExtra("remetente").equals("home")) {
                    Intent in = new Intent(getBaseContext(), ViewExibirInformacoesTurma.class);
                    in.putExtra("idTurma", variavel.getIdTurma());
                    in.putExtra("idEscola", variavel.getIdEscola());
                    in.putExtra("nome", variavel.getNome());
                    in.putExtra("faixa1", variavel.getIdadeMin());
                    in.putExtra("faixa2", variavel.getIdadeMax());
                    startActivity(in);
                    onStop();
                } else if (intent.getStringExtra("remetente").equals("professor")) {
                    Intent listPro = new Intent(ViewListarTurmas.this, ViewListaProfessores.class);
                    listPro.putExtra("idEscola", intent.getStringExtra("idEscola"));
                    professor = new Professor();
                    professor.setKeyTurma(variavel.getNome());
                    professor.setIdProfessor(intent.getStringExtra("key"));
                    professor.setNome(intent.getStringExtra("nome"));
                    professor.setTelefone(intent.getStringExtra("tel"));
                    professor.setEmail(intent.getStringExtra("email"));
                    professor.setCpf(intent.getStringExtra("cpf"));
                    professor.setSenha(intent.getStringExtra("senha"));
                    professor.setIdEscola(intent.getStringExtra("idEscola"));
                    editarProfessor(professor);
                    startActivity(listPro);
                    onStop();
                } else {
                    Intent listAlu = new Intent(ViewListarTurmas.this, ViewListarAlunos.class);
                    listAlu.putExtra("idEscola", intent.getStringExtra("idEscola"));
                    aluno = new Aluno();
                    aluno.setKeyTurma(variavel.getNome());
                    aluno.setIdAluno(intent.getStringExtra("idAluno"));
                    aluno.setNome(intent.getStringExtra("nome"));
                    aluno.setTelefone(intent.getStringExtra("tel"));
                    aluno.setEmailResponsavel(intent.getStringExtra("email"));
                    aluno.setCpfResponsavel(intent.getStringExtra("cpf"));
                    aluno.setNomeResponsavel(intent.getStringExtra("nomeRes"));
                    aluno.setSenha(intent.getStringExtra("senha"));
                    aluno.setIdEscola(intent.getStringExtra("idEscola"));
                    editarAluno(aluno);
                    startActivity(listAlu);
                    onStop();
                }
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

    private void editarProfessor(Professor p) {
        DatabaseReference data = ConfiguracaoFirebase.getFirebase().child("professor");
        data.child(p.getIdProfessor()).updateChildren(professor.toMap());
    }

    private void editarAluno(Aluno a) {
        DatabaseReference data = ConfiguracaoFirebase.getFirebase().child("aluno");
        data.child(a.getIdAluno()).updateChildren(a.toMap());
    }


}
