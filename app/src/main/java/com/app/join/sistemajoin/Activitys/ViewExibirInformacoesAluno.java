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
        import com.app.join.sistemajoin.Model.Professor;
        import com.app.join.sistemajoin.R;
        import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
        import com.google.firebase.database.DatabaseReference;

public class ViewExibirInformacoesAluno extends AppCompatActivity {

    TextView tvNomeAluno,tvMatriculaAluno,tvTelResponsavel,tvNomeResponsavel,tvCPFResponsavel,tvSenhaAluno;
    ImageButton btExcluirAluno,btEditarAluno,btConfigAluno;

    private DatabaseReference firebase;
    private AlertDialog alertDialog;
    private Aluno aluno;
    String key = "";
    Intent in = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exibir_informacoes_aluno);

        tvNomeAluno=(TextView)findViewById(R.id.tvNomeAluno);
        tvMatriculaAluno=(TextView)findViewById(R.id.tvTelResponsavel);
        tvTelResponsavel=(TextView)findViewById(R.id.tvTelResponsavel);
        tvNomeResponsavel=(TextView)findViewById(R.id.tvNomeResponsavel);
        tvCPFResponsavel=(TextView)findViewById(R.id.tvCPFResponsavel);
        tvSenhaAluno=(TextView)findViewById(R.id.tvSenhaAluno);
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
                builder.setMessage("Deseja realmente excluir " + in.getStringExtra("nome") + "?");
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
                Intent in = new Intent(getBaseContext(), ViewCadastrarAluno.class);
                Intent intent = getIntent();
                in.putExtra("key", in.getStringExtra("key"));
                in.putExtra("nome", in.getStringExtra("nome"));
                in.putExtra("tel", in.getStringExtra("tel"));
                //in.putExtra("email", in.getStringExtra("email"));
                in.putExtra("cpf", in.getStringExtra("cpf"));
                in.putExtra("status", in.getStringExtra("status"));
                in.putExtra("senha", in.getStringExtra("senha"));
                in.putExtra("nomeRes", in.getStringExtra("nomeRes"));
                startActivity(in);
                finish();
            }
        });


    }

    private void preencheCampos() {
        tvNomeAluno.setText(in.getStringExtra("nome"));
        tvMatriculaAluno.setText(in.getStringExtra("key"));
        tvTelResponsavel.setText(in.getStringExtra("tel"));
        tvNomeResponsavel.setText(in.getStringExtra("nomeRes"));
        tvCPFResponsavel.setText(in.getStringExtra("cof"));
        tvSenhaAluno.setText(in.getStringExtra("senha"));

    }
}
