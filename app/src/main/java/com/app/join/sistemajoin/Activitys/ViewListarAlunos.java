package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.app.join.sistemajoin.Adapter.AlunoAdapter;
import com.app.join.sistemajoin.Model.Aluno;
import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewListarAlunos extends AppCompatActivity {

    private ListView listview;
    private ArrayAdapter<Aluno> adapter;
    private ArrayList<Aluno> lista;
    private Aluno aluno, variavel;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListener;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listar_alunos);

        intent = getIntent();

        lista = new ArrayList();
        listview = findViewById(R.id.lwListaAlunos);
        adapter = new AlunoAdapter(this, lista);
        listview.setAdapter(adapter);
        firebase = ConfiguracaoFirebase.getFirebase().child("aluno");

        Toast.makeText(ViewListarAlunos.this, "Clique na Lista para ver as informações completas do aluno", Toast.LENGTH_LONG).show();

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lista.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    aluno = dados.getValue(Aluno.class);
                    if(intent.getStringExtra("idEscola").equals(aluno.getIdEscola())) {
                        lista.add(aluno);
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
                firebase.child(variavel.getIdAluno());
                Intent in = new Intent(getBaseContext(), ViewExibirInformacoesAluno.class);
                in.putExtra("key", variavel.getIdAluno());
                in.putExtra("nome", variavel.getNome());
                in.putExtra("tel", variavel.getTelefone());
                in.putExtra("email", variavel.getEmailResponsavel());
                in.putExtra("cpf", variavel.getCpfResponsavel());
                in.putExtra("status", variavel.getStatus());
                in.putExtra("senha", variavel.getSenha());
                in.putExtra("nomeRes", variavel.getNomeResponsavel());
                in.putExtra("idEscola", variavel.getIdEscola());
                startActivity(in);

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
