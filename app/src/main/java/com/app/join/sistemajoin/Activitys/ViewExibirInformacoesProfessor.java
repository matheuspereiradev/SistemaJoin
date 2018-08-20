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
import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ViewExibirInformacoesProfessor extends AppCompatActivity {

    TextView cmpNomeProf,cmpTelProf,cmpEmailProf,cmpCPFProf,cmpDataNascProf,cmpRGProf,cmpFormacaoProf,cmpLoginProf,cmpSenhaProf;
    ImageButton btEditProf,btConfigProf,btExcluirProf;

    private DatabaseReference firebase;
    private AlertDialog alertDialog;
    String key = "";
    String nome = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exibir_informacoes_professor);
        cmpNomeProf=(TextView)findViewById(R.id.cmpNomeProf);
        cmpTelProf=(TextView)findViewById(R.id.cmpTelProf);
        cmpEmailProf=(TextView)findViewById(R.id.cmpEmailEsc);
        cmpCPFProf = (TextView) findViewById(R.id.cmpCNPJEscola);
        cmpDataNascProf=(TextView)findViewById(R.id.cmpDataNascProf);
        cmpRGProf=(TextView)findViewById(R.id.cmpRGProf);
        cmpFormacaoProf=(TextView)findViewById(R.id.cmpFormacaoProf);
        cmpLoginProf=(TextView)findViewById(R.id.cmpLoginProf);
        cmpSenhaProf=(TextView)findViewById(R.id.cmpSenhaProf);
        btEditProf=(ImageButton)findViewById(R.id.btEditProf);
        btConfigProf=(ImageButton) findViewById(R.id.btConfigProf);
        btExcluirProf=(ImageButton)findViewById(R.id.btExcluirProf);

        final Intent intent = getIntent();
        key = intent.getStringExtra("key");
        nome = intent.getStringExtra("nome");

        preencheCampos();

        btExcluirProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewExibirInformacoesProfessor.this);
                builder.setTitle("Excluir!");
                builder.setMessage("Deseja realmente excluir" + intent.getStringExtra("nome") + "?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebase = ConfiguracaoFirebase.getFirebase().child("professor");
                        firebase.child(intent.getStringExtra("key")).removeValue();
                        Toast.makeText(getBaseContext(), "Escola Excluida!", Toast.LENGTH_SHORT).show();
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
                in.putExtra("key", key);
                /*
                in.putExtra("nome", intent.getStringExtra("nome"));
                in.putExtra("tel", intent.getStringExtra("tel"));
                in.putExtra("email", intent.getStringExtra("email"));
                in.putExtra("cnpj", intent.getStringExtra("cnpj"));
                in.putExtra("status", intent.getStringExtra("status"));
                in.putExtra("senha", intent.getStringExtra("senha"));
                */
                startActivity(in);
            }
        });

    }

    private void preencheCampos() {
        firebase = ConfiguracaoFirebase.getFirebase().child("professor").child("nome");
        firebase.child("nome").equals(nome);
        cmpNomeProf.setText(nome);
        cmpEmailProf.setText("teste@teste.com");
        cmpCPFProf.setText("");
        cmpDataNascProf.setText("");
        cmpLoginProf.setText("");
        cmpRGProf.setText("");
        cmpSenhaProf.setText("");
        cmpTelProf.setText("");
    }
}
