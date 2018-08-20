package com.app.join.sistemajoin.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.app.join.sistemajoin.Adapter.EscolaAdapter;
import com.app.join.sistemajoin.Adapter.ProfessorAdapter;
import com.app.join.sistemajoin.Model.Escola;
import com.app.join.sistemajoin.Model.Professor;
import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewListaProfessores extends AppCompatActivity {

    private ListView listview;
    private ArrayAdapter<Professor> adapter;
    private ArrayList<Professor> lista;
    private Professor professor, variavel;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_lista_professores);


        lista = new ArrayList();
        listview = findViewById(R.id.lwEscolasCadastradas);
        adapter = new ProfessorAdapter(this, lista);
        listview.setAdapter(adapter);
        firebase = ConfiguracaoFirebase.getFirebase().child("professor");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lista.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    professor = dados.getValue(Professor.class);

                    lista.add(professor);
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
                firebase = ConfiguracaoFirebase.getFirebase().child("professor");
                firebase.child(variavel.getIdProfessor());
                Intent in = new Intent(getBaseContext(), ViewExibirInformacoesEscola.class);
                in.putExtra("key", variavel.getIdProfessor());
                /*
                in.putExtra("nome", variavel.getNome());
                in.putExtra("tel", variavel.getTelefone());
                in.putExtra("email", variavel.getEmail());
                in.putExtra("cnpj", variavel.getCnpj());
                in.putExtra("status", variavel.getStatus());
                in.putExtra("senha", variavel.getSenha());
                */
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
