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
import com.app.join.sistemajoin.Adapter.ProfessorAdapter;
import com.app.join.sistemajoin.Model.Aluno;
import com.app.join.sistemajoin.Model.Professor;
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
    private ListView listadealunos, listadeprofessores;
    private ArrayAdapter<Aluno> adapterAluno;
    private ArrayList<Aluno> listaAluno;
    private Aluno aluno;
    private ValueEventListener valueEventListenerAluno;
    private ArrayAdapter<Professor> adapterPro;
    private ArrayList<Professor> listaPro;
    private Professor professor;
    private ValueEventListener valueEventListenerPro;
    private DatabaseReference firebaseAluno, firebasePro, firebase;
    private AlertDialog alertDialog;
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

        listaAluno = new ArrayList();
        listadealunos = findViewById(R.id.listadealunos);
        adapterAluno = new AlunoAdapter(this, listaAluno);
        listadealunos.setAdapter(adapterAluno);
        firebaseAluno = ConfiguracaoFirebase.getFirebase().child("aluno");
        valueEventListenerAluno = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaAluno.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    aluno = dados.getValue(Aluno.class);
                    if (in.getStringExtra("nome").equals(aluno.getKeyTurma())) {
                        listaAluno.add(aluno);
                    }
                }
                adapterAluno.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        listaPro = new ArrayList();
        listadeprofessores = findViewById(R.id.listadeprofessores);
        adapterPro = new ProfessorAdapter(this, listaPro);
        listadeprofessores.setAdapter(adapterPro);
        firebasePro = ConfiguracaoFirebase.getFirebase().child("professor");
        valueEventListenerPro = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaPro.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    professor = dados.getValue(Professor.class);
                    if (in.getStringExtra("nome").equals(professor.getKeyTurma())) {
                        listaPro.add(professor);
                    }
                }
                adapterPro.notifyDataSetChanged();
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
        cmpFaixaEtaria.setText(in.getStringExtra("faixa1") + " A " + in.getStringExtra("faixa2") + " anos");
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAluno.removeEventListener(valueEventListenerAluno);
        firebasePro.removeEventListener(valueEventListenerPro);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAluno.addValueEventListener(valueEventListenerAluno);
        firebasePro.addValueEventListener(valueEventListenerPro);
    }

}
