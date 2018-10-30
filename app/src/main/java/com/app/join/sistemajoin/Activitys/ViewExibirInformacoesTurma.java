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

    private TextView cmpNomeTurma, cmpFaixaEtaria;
    private ImageButton btEditTurma, btExcluirTurma;
    private ListView listadealunos,listadeprofessores;
    private ArrayAdapter<Aluno> adapter;
    private ArrayList<Aluno> lista;
    private Aluno aluno;
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
        cmpFaixaEtaria = (TextView) findViewById(R.id.cmpFaixaEtaria);
        btEditTurma = (ImageButton) findViewById(R.id.btEditTurma);
        btExcluirTurma = (ImageButton) findViewById(R.id.btExcluirTurma);

        in = getIntent();
        preencheCampos();

        lista = new ArrayList();
        listadealunos = findViewById(R.id.listadealunos);
        adapter = new AlunoAdapter(this, lista);
        listadealunos.setAdapter(adapter);
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
                        firebase.child(in.getStringExtra("nome")).removeValue();
                        Intent listTurma = new Intent(getBaseContext(), ViewListarTurmas.class);
                        listTurma.putExtra("remetente", "home");
                        listTurma.putExtra("idEscola", in.getStringExtra("idEscola"));
                        startActivity(listTurma);
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

        btEditTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edt = new Intent(ViewExibirInformacoesTurma.this, ViewCadastrarTurma.class);
                edt.putExtra("nome", in.getStringExtra("nome"));
                edt.putExtra("faixa1", in.getStringExtra("faixa1"));
                edt.putExtra("faixa2", in.getStringExtra("faixa2"));
                edt.putExtra("idEscola", in.getStringExtra("idEscola"));
                edt.putExtra("idTurma", in.getStringExtra("idTurma"));
                edt.putExtra("cdg", "edt");
                startActivity(edt);
                finish();
            }
        });


    }

    private void preencheCampos() {
        cmpNomeTurma.setText(in.getStringExtra("nome"));

    }

}
