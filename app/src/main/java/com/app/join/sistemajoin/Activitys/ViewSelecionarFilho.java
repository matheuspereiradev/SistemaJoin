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

public class ViewSelecionarFilho extends AppCompatActivity {

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
        setContentView(R.layout.activity_view_selecionar_filho);

        intent = getIntent();
        lista = new ArrayList();
        listview = findViewById(R.id.idSelecionarFilho);
        adapter = new AlunoAdapter(this, lista);
        listview.setAdapter(adapter);
        firebase = ConfiguracaoFirebase.getFirebase().child("aluno");
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lista.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    aluno = dados.getValue(Aluno.class);
                    if (intent.getStringExtra("cpfRes").equals(aluno.getCpfResponsavel())) {
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
                if (intent.getStringExtra("post").equals("post")) {
                    Intent verPost = new Intent(ViewSelecionarFilho.this, ViewListarPostagens.class);
                    verPost.putExtra("idAluno", intent.getStringExtra("idAluno"));
                    verPost.putExtra("cpfRes", intent.getStringExtra("cpfRes"));
                    verPost.putExtra("idEscola", intent.getStringExtra("idEscola"));
                    verPost.putExtra("remetente", "aluno");
                    startActivity(verPost);
                } else {
                    Intent verPost = new Intent(ViewSelecionarFilho.this, ViewVerGrafico.class);
                    verPost.putExtra("idAluno", intent.getStringExtra("idAluno"));
                    verPost.putExtra("cpfRes", intent.getStringExtra("cpfRes"));
                    verPost.putExtra("idEscola", intent.getStringExtra("idEscola"));
                    startActivity(verPost);
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
