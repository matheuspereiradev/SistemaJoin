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

public class ViewSelecionarAlunos extends AppCompatActivity {

    private ListView listview;
    private ArrayAdapter<Aluno> adapter;
    private ArrayList<Aluno> lista;
    private Aluno aluno, variavel;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selecionar_alunos);

        lista = new ArrayList();
        listview = findViewById(R.id.lwSelecionaAluno);
        adapter = new AlunoAdapter(this, lista);
        listview.setAdapter(adapter);
        firebase = ConfiguracaoFirebase.getFirebase().child("aluno");

        Toast.makeText(ViewSelecionarAlunos.this, "Selecione o aluno para quem deseja enviar a anotação", Toast.LENGTH_LONG).show();

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lista.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    aluno = dados.getValue(Aluno.class);

                    lista.add(aluno);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        final Intent cdg = getIntent();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                variavel = adapter.getItem(i);
                firebase = ConfiguracaoFirebase.getFirebase().child("aluno");
<<<<<<< Updated upstream
                //firebase.child(variavel.getMatricola());
=======
>>>>>>> Stashed changes
                firebase.child(variavel.getIdAluno());

                if(cdg.getStringExtra("codigo").equals(2)){
                Intent in = new Intent(ViewSelecionarAlunos.this, ViewRealizarPostagem.class);
<<<<<<< Updated upstream
                //in.putExtra("key", variavel.getMatricola());
=======
>>>>>>> Stashed changes
                in.putExtra("key", variavel.getIdAluno());
                in.putExtra("nome", variavel.getNome());
                startActivity(in);
                finish();
                }else{
                    Intent in = new Intent(ViewSelecionarAlunos.this, ViewRealizarAvaliacao.class);
                    in.putExtra("codigo", 2);
<<<<<<< Updated upstream
                    //in.putExtra("key", variavel.getMatricola());
=======
>>>>>>> Stashed changes
                    in.putExtra("key", variavel.getIdAluno());
                    in.putExtra("nome", variavel.getNome());
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
