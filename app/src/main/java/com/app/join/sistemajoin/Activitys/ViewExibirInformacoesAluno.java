package com.app.join.sistemajoin.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.join.sistemajoin.Model.Aluno;
import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class ViewExibirInformacoesAluno extends AppCompatActivity {

    TextView tvNomeAluno, tvMatriculaAluno, tvTelResponsavel, tvNomeResponsavel, tvCPFResponsavel, tvSenhaAluno,tvEmailResp;
    ImageButton btExcluirAluno, btEditarAluno, btConfigAluno;

    private DatabaseReference firebase;
    private AlertDialog alertDialog;
    private Aluno aluno;
    String key = "";
    Intent in = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exibir_informacoes_aluno);

        tvNomeAluno = (TextView) findViewById(R.id.tvNomeAluno);
        tvTelResponsavel = (TextView) findViewById(R.id.tvTelResponsavel);
        tvNomeResponsavel = (TextView) findViewById(R.id.tvNomeResponsavel);
        tvCPFResponsavel = (TextView) findViewById(R.id.tvCPFResponsavel);
        tvSenhaAluno = (TextView) findViewById(R.id.tvSenhaAluno);
        tvEmailResp = (TextView) findViewById(R.id.tvEmailResponsavel);
        btConfigAluno = (ImageButton) findViewById(R.id.btConfigAluno);
        btEditarAluno = (ImageButton) findViewById(R.id.btEditarAluno);
        btExcluirAluno = (ImageButton) findViewById(R.id.btExcluirAluno);

        in = getIntent();
        key = in.getStringExtra("key");
        preencheCampos();

        btExcluirAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewExibirInformacoesAluno.this);
                builder.setTitle("Excluir!");
                builder.setMessage("Deseja realmente excluir?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebase = ConfiguracaoFirebase.getFirebase().child("aluno");
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

        btEditarAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cadAluno = new Intent(getBaseContext(), ViewCadastrarAluno.class);
                cadAluno.putExtra("key", in.getStringExtra("key"));
                cadAluno.putExtra("nome", in.getStringExtra("nome"));
                cadAluno.putExtra("tel", in.getStringExtra("tel"));
                cadAluno.putExtra("email", in.getStringExtra("email"));
                cadAluno.putExtra("cpf", in.getStringExtra("cpf"));
                cadAluno.putExtra("status", in.getStringExtra("status"));
                cadAluno.putExtra("senha", in.getStringExtra("senha"));
                cadAluno.putExtra("nomeRes", in.getStringExtra("nomeRes"));
                cadAluno.putExtra("idEscola", in.getStringExtra("idEscola"));
                startActivity(cadAluno);
                finish();
            }
        });


    }

    private void preencheCampos() {
        tvNomeAluno.setText(in.getStringExtra("nome"));
        tvMatriculaAluno.setText(in.getStringExtra("key"));
        tvTelResponsavel.setText(in.getStringExtra("tel"));
        tvNomeResponsavel.setText(in.getStringExtra("nomeRes"));
        tvEmailResp.setText(in.getStringExtra("email"));
        tvCPFResponsavel.setText(in.getStringExtra("cpf"));
        tvSenhaAluno.setText(in.getStringExtra("senha"));

    }
}
