package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.app.join.sistemajoin.Adapter.AlunoAdapter;
import com.app.join.sistemajoin.Model.Aluno;
import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class ViewSelecionarAlunos extends AppCompatActivity {

    private ListView listview;
    private ArrayAdapter<Aluno> adapter;
    private ArrayList<Aluno> lista;
    private Aluno aluno, variavel;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListener;
    private Intent cdg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selecionar_alunos);

        cdg = getIntent();

        lista = new ArrayList();
        listview = findViewById(R.id.lwSelecionaAluno);
        adapter = new AlunoAdapter(this, lista);
        listview.setAdapter(adapter);
        firebase = ConfiguracaoFirebase.getFirebase().child("aluno");
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lista.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    aluno = dados.getValue(Aluno.class);
                    if(cdg.getStringExtra("keyTurma").equals(aluno.getKeyTurma())) {
                        lista.add(aluno);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        final String codigo = cdg.getStringExtra("codigo");
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                variavel = adapter.getItem(i);
                if (codigo.equals("2")) {
                    Intent in = new Intent(ViewSelecionarAlunos.this, ViewRealizarPostagem.class);
                    in.putExtra("idAluno", variavel.getIdAluno());
                    in.putExtra("nome", variavel.getNome());
                    in.putExtra("idProfessor", cdg.getStringExtra("idProfessor"));
                    in.putExtra("keyTurma", cdg.getStringExtra("keyTurma"));
                    in.putExtra("remetente", "post");
                    startActivity(in);
                    finish();
                } else if(codigo.equals("3")){
                    Intent in = new Intent(ViewSelecionarAlunos.this, ViewListarAvaliacoes.class);
                    in.putExtra("idAluno", variavel.getIdAluno());
                    in.putExtra("nome", variavel.getNome());
                    in.putExtra("codigo", "list");
                    startActivity(in);
                    finish();
                }else if(codigo.equals("4")){
                    Intent in = new Intent(ViewSelecionarAlunos.this, ViewListarPostagens.class);
                    in.putExtra("idAluno", variavel.getIdAluno());
                    in.putExtra("nome", variavel.getNome());
                    in.putExtra("idProfessor", cdg.getStringExtra("idProfessor"));
                    in.putExtra("remetente", "professor");
                    startActivity(in);
                    finish();
                }else {
                    Intent in = new Intent(ViewSelecionarAlunos.this, ViewRealizarAvaliacao.class);
                    in.putExtra("idAluno", variavel.getIdAluno());
                    in.putExtra("nome", variavel.getNome());
                    in.putExtra("keyTurma", cdg.getStringExtra("keyTurma"));
                    startActivity(in);
                    finish();
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

}
