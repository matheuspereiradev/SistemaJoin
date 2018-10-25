package com.app.join.sistemajoin.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.app.join.sistemajoin.Adapter.AlunoAdapter;
import com.app.join.sistemajoin.Model.Aluno;
import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class ViewExibirInformacoesTurma extends AppCompatActivity {

    private TextView cmpNomeTurma, cmpProfTurma;
    private ImageButton btEditTurma, btConfigTurma, btExcluirTurma;
    private ListView listview;
    private ArrayAdapter<Aluno> adapter;
    private ArrayList<Aluno> lista;
    private Aluno aluno, variavel;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListener;
    private AlertDialog alertDialog;
    private String key = "";
    private Intent in = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exibir_informacoes_turma);

        cmpNomeTurma = (TextView) findViewById(R.id.cmpNomeTurma);
        cmpProfTurma = (TextView) findViewById(R.id.cmpProfTurma);
        btEditTurma = (ImageButton) findViewById(R.id.btEditTurma);
      //  btConfigTurma = (ImageButton) findViewById(R.id.btConfigTurma);
        btExcluirTurma = (ImageButton) findViewById(R.id.btExcluirTurma);

        in = getIntent();
        key = in.getStringExtra("key");
        preencheCampos();


        lista = new ArrayList();
        listview = findViewById(R.id.listadealunos);
        adapter = new AlunoAdapter(this, lista);
        listview.setAdapter(adapter);
        firebase = ConfiguracaoFirebase.getFirebase().child("aluno");
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lista.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    aluno = dados.getValue(Aluno.class);
                    if(in.getStringExtra("idTurma").equals(aluno.getKeyTurma())) {
                        lista.add(aluno);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        btExcluirTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewExibirInformacoesTurma.this);
                builder.setTitle("Excluir!");
                builder.setMessage("Deseja realmente excluir " + in.getStringExtra("nome") + "?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebase = ConfiguracaoFirebase.getFirebase().child("turma");
                        firebase.child(in.getStringExtra("key")).removeValue();
                        Intent in = new Intent(getBaseContext(), ViewListarTurmas.class);
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

    private void preencheCampos() {
        cmpNomeTurma.setText(in.getStringExtra("nome"));

    }

}
