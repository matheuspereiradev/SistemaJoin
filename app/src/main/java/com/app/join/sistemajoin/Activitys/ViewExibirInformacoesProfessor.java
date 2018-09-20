package com.app.join.sistemajoin.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.app.join.sistemajoin.Model.Escola;
import com.app.join.sistemajoin.Model.Professor;
import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ViewExibirInformacoesProfessor extends AppCompatActivity {

    TextView cmpNomeProf, cmpTelProf, cmpEmailProf, cmpCPFProf, cmpDataNascProf, cmpRGProf, cmpSenhaProf;
    ImageButton btEditProf, btConfigProf, btExcluirProf;

    private DatabaseReference firebase;
    private AlertDialog alertDialog;
    private Professor professor;
    String key = "";
    Intent in = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exibir_informacoes_professor);

        cmpNomeProf = (TextView) findViewById(R.id.cmpNomeProf);
        cmpTelProf = (TextView) findViewById(R.id.cmpTelProf);
        cmpEmailProf = (TextView) findViewById(R.id.cmpEmailProf);
        cmpCPFProf = (TextView) findViewById(R.id.cmpCPFProf);
        cmpSenhaProf = (TextView) findViewById(R.id.cmpSenhaProf);
        btEditProf = (ImageButton) findViewById(R.id.btEditProf);
        btConfigProf = (ImageButton) findViewById(R.id.btConfigProf);
        btExcluirProf = (ImageButton) findViewById(R.id.btExcluirProf);

        in = getIntent();
        key = in.getStringExtra("key");
        preencheCampos();

        btExcluirProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewExibirInformacoesProfessor.this);
                builder.setTitle("Excluir!");
                builder.setMessage("Deseja realmente excluir?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebase = ConfiguracaoFirebase.getFirebase().child("professor");
                        firebase.child(in.getStringExtra("key")).removeValue();
                        Intent in = new Intent(getBaseContext(), ViewHomeSistemaEscola.class);
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

        btEditProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getBaseContext(), ViewCadastrarProfessor.class);
                Intent intent = getIntent();
                in.putExtra("key", intent.getStringExtra("key"));
                in.putExtra("nome", intent.getStringExtra("nome"));
                in.putExtra("tel", intent.getStringExtra("tel"));
                in.putExtra("email", intent.getStringExtra("email"));
                in.putExtra("cpf", intent.getStringExtra("cpf"));
                in.putExtra("status", intent.getStringExtra("status"));
                in.putExtra("senha", intent.getStringExtra("senha"));
                in.putExtra("keyTurma", intent.getStringExtra("keyTurma"));
                in.putExtra("idEscola", intent.getStringExtra("idEscola"));
                startActivity(in);
                finish();
            }
        });

    }

    private void preencheCampos() {
        cmpNomeProf.setText(in.getStringExtra("nome"));
        cmpEmailProf.setText(in.getStringExtra("email"));
        cmpCPFProf.setText(in.getStringExtra("cpf"));
        cmpSenhaProf.setText(in.getStringExtra("senha"));
        cmpTelProf.setText(in.getStringExtra("tel"));

    }
}
