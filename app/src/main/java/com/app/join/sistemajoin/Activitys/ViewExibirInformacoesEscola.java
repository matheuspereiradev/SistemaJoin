package com.app.join.sistemajoin.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.join.sistemajoin.Model.Escola;
import com.app.join.sistemajoin.R;
import com.app.join.sistemajoin.Tools.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ViewExibirInformacoesEscola extends AppCompatActivity {

    TextView cmpNomeEsc, cmpTelEsc, cmpEmailEsc, cmpCNPJEsc, cmpLoginEsc, cmpSenhaEsc;
    ImageButton btEditarEsc, btExcluirEsc, btConfigEsc;

    private DatabaseReference firebase;
    private ValueEventListener valueEventListener;
    private Escola escola;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exibir_informacoes_escola);

        //==========inicio conexão java com xml
        cmpNomeEsc = (TextView) findViewById(R.id.cmpNomeEsc);
        cmpTelEsc = (TextView) findViewById(R.id.cmpTelEsc);
        cmpEmailEsc = (TextView) findViewById(R.id.cmpEmailEsc);
        cmpCNPJEsc = (TextView) findViewById(R.id.cmpCNPJEscola);
        cmpLoginEsc = (TextView) findViewById(R.id.cmpLoginEsc);
        cmpSenhaEsc = (TextView) findViewById(R.id.cmpSenhaEscola);
        btConfigEsc = (ImageButton) findViewById(R.id.btConfigEsc);
        btEditarEsc = (ImageButton) findViewById(R.id.btEditarEsc);
        btExcluirEsc = (ImageButton) findViewById(R.id.btExcluirEsc);
        //fim conexão java com xml===========

        final Intent intent = getIntent();

        final String key = intent.getStringExtra("key");
        cmpNomeEsc.setText(intent.getStringExtra("nome"));
        cmpTelEsc.setText(intent.getStringExtra("tel"));
        cmpCNPJEsc.setText(intent.getStringExtra("cnpj"));
        cmpEmailEsc.setText(intent.getStringExtra("email"));
        cmpLoginEsc.setText(intent.getStringExtra("status"));
        cmpSenhaEsc.setText(intent.getStringExtra("senha"));

        btExcluirEsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewExibirInformacoesEscola.this);
                builder.setTitle("Excluir!");
                builder.setMessage("Deseja realmente excluir Escola?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebase = ConfiguracaoFirebase.getFirebase().child("escola");
                        firebase.child(intent.getStringExtra("key")).removeValue();
                        Intent in = new Intent(getBaseContext(), ViewListarEscolas.class);
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
        btEditarEsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getBaseContext(), ViewCadastrarEscola.class);
                in.putExtra("key", key);
                in.putExtra("nome", intent.getStringExtra("nome"));
                in.putExtra("tel", intent.getStringExtra("tel"));
                in.putExtra("email", intent.getStringExtra("email"));
                in.putExtra("cnpj", intent.getStringExtra("cnpj"));
                in.putExtra("status", intent.getStringExtra("status"));
                in.putExtra("senha", intent.getStringExtra("senha"));
                startActivity(in);
                finish();
            }
        });

        btConfigEsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
