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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ViewExibirInformacoesEscola extends AppCompatActivity {

    TextView cmpNomeEsc, cmpTelEsc, cmpEmailEsc, cmpCNPJEsc, cmpCidadeEsc, cmpUFEsc, cmpCEPEsc, cmpRuaEsc, cmpNumEsc, cmpBairroEsc;
    ImageButton btEditarEsc, btExcluirEsc;

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
        cmpCNPJEsc = (TextView) findViewById(R.id.cmpCNPJEsc);
        cmpCidadeEsc = (TextView) findViewById(R.id.cmpCidadeEsc);
        cmpUFEsc = (TextView) findViewById(R.id.cmpUFEsc);
        cmpCEPEsc = (TextView) findViewById(R.id.cmpCEPEsc);
        cmpRuaEsc = (TextView) findViewById(R.id.cmpRuaEsc);
        cmpNumEsc = (TextView) findViewById(R.id.cmpNumEsc);
        cmpBairroEsc = (TextView) findViewById(R.id.cmpBairroEsc);
        btEditarEsc = (ImageButton) findViewById(R.id.btEditarEsc);
        btExcluirEsc = (ImageButton) findViewById(R.id.btExcluirEsc);
        //fim conexão java com xml===========

        final Intent intent = getIntent();

        final String key = intent.getStringExtra("key");

        cmpNomeEsc.setText(intent.getStringExtra("nome"));
        cmpTelEsc.setText(intent.getStringExtra("tel"));
        cmpCNPJEsc.setText(intent.getStringExtra("cnpj"));
        cmpEmailEsc.setText(intent.getStringExtra("email"));
        cmpCidadeEsc.setText(intent.getStringExtra("status"));

        btExcluirEsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewExibirInformacoesEscola.this);
                builder.setTitle("Excluir!");
                builder.setMessage("Deseja realmente excluir" + intent.getStringExtra("nome") + "?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebase = ConfiguracaoFirebase.getFirebase().child("escola");
                        firebase.child(intent.getStringExtra("nome")).removeValue();
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
        btEditarEsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewExibirInformacoesEscola.this);
                builder.setTitle("Editar!");
                builder.setMessage("Deseja Editar" + intent.getStringExtra("nome") + "?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent in = new Intent(getBaseContext(), ViewCadastrarEscola.class);
                        in.putExtra("key_id", key);
                        startActivity(in);

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
}
