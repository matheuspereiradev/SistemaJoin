package com.app.join.sistemajoin.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.Base64Custon;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.database.DatabaseReference;

public class ViewExibirInformacoesAluno extends AppCompatActivity {

    private TextView tvNomeAluno, tvTelResponsavel, tvNomeResponsavel, tvCPFResponsavel, tvSenhaAluno, tvEmailResp, tvTurmaAluno;
    private ImageButton btExcluirAluno, btEditarAluno, btConfigAluno;
    private DatabaseReference firebase;
    private AlertDialog alertDialog;
    private String key = "";
    private Intent in = null;

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
        tvTurmaAluno = findViewById(R.id.tvTurma);
        btConfigAluno = (ImageButton) findViewById(R.id.btConfigAluno);
        btEditarAluno = (ImageButton) findViewById(R.id.btEditarAluno);
        btExcluirAluno = (ImageButton) findViewById(R.id.btExcluirAluno);

        SimpleMaskFormatter simpleMaskTelCPFP = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mascaraTelCPFP = new MaskTextWatcher(tvCPFResponsavel, simpleMaskTelCPFP);
        tvCPFResponsavel.addTextChangedListener(mascaraTelCPFP);

        in = getIntent();
        key = in.getStringExtra("key");
        preencheCampos();

        btExcluirAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewExibirInformacoesAluno.this);
                builder.setTitle("Excluir!");
                builder.setMessage("Deseja realmente excluir o aluno(a)?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebase = ConfiguracaoFirebase.getFirebase().child("aluno");
                        firebase.child(in.getStringExtra("key")).removeValue();
                        Intent listAluno = new Intent(ViewExibirInformacoesAluno.this, ViewListarAlunos.class);
                        listAluno.putExtra("idEscola", in.getStringExtra("idEscola"));
                        startActivity(listAluno);
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
                cadAluno.putExtra("senha", in.getStringExtra("senha"));
                cadAluno.putExtra("turma", in.getStringExtra("turma"));
                cadAluno.putExtra("nomeRes", in.getStringExtra("nomeRes"));
                cadAluno.putExtra("idEscola", in.getStringExtra("idEscola"));
                startActivity(cadAluno);
                finish();
            }
        });

        btConfigAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cadAluno = new Intent(getBaseContext(), ViewListarTurmas.class);
                cadAluno.putExtra("idAluno", in.getStringExtra("key"));
                cadAluno.putExtra("nome", in.getStringExtra("nome"));
                cadAluno.putExtra("tel", in.getStringExtra("tel"));
                cadAluno.putExtra("email", in.getStringExtra("email"));
                cadAluno.putExtra("cpf", in.getStringExtra("cpf"));
                cadAluno.putExtra("senha", in.getStringExtra("senha"));
                cadAluno.putExtra("turma", in.getStringExtra("turma"));
                cadAluno.putExtra("nomeRes", in.getStringExtra("nomeRes"));
                cadAluno.putExtra("idEscola", in.getStringExtra("idEscola"));
                cadAluno.putExtra("remetente", "aluno");
                startActivity(cadAluno);
                finish();
            }
        });


    }

    private void preencheCampos() {
        tvNomeAluno.setText(in.getStringExtra("nome"));
        tvTelResponsavel.setText(in.getStringExtra("tel"));
        tvNomeResponsavel.setText(in.getStringExtra("nomeRes"));
        tvEmailResp.setText(in.getStringExtra("email"));
        tvCPFResponsavel.setText(in.getStringExtra("cpf"));
        tvSenhaAluno.setText(in.getStringExtra("senha"));
        tvTurmaAluno.setText(in.getStringExtra("turma"));
    }
}
