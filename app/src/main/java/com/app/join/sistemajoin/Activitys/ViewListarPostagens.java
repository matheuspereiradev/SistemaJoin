package com.app.join.sistemajoin.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.app.join.sistemajoin.Adapter.EscolaAdapter;
import com.app.join.sistemajoin.Adapter.PostagemAdapter;
import com.app.join.sistemajoin.Model.Agenda;
import com.app.join.sistemajoin.Model.Escola;
import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewListarPostagens extends AppCompatActivity {

    private ListView listview;
    private ArrayAdapter<Agenda> adapter;
    private ArrayList<Agenda> lista;
    private Agenda agenda, variavel;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListener;
    private AlertDialog alertDialog;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listar_postagens);

        intent = getIntent();

        lista = new ArrayList();
        listview = findViewById(R.id.tbPostagens);
        adapter = new PostagemAdapter(this, lista);
        listview.setAdapter(adapter);
        firebase = ConfiguracaoFirebase.getFirebase().child("agenda");
        firebase.orderByChild("data").limitToLast(20);
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lista.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    agenda = dados.getValue(Agenda.class);
                    if (intent.getStringExtra("idAluno").equals(agenda.getIdDestino())) {
                        lista.add(agenda);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewListarPostagens.this);
                builder.setTitle("Falta o titulo");
                builder.setMessage("Editar ou Excluir?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // firebase = ConfiguracaoFirebase.getFirebase().child("agenda");
                        // firebase.child(variavel.getData()).removeValue();
                        Intent in = new Intent(getBaseContext(), ViewListarPostagens.class);
                        in.putExtra("idEscola", in.getStringExtra("idEscola"));
                        startActivity(in);
                        finish();
                    }
                });
                builder.setNegativeButton("não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
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
