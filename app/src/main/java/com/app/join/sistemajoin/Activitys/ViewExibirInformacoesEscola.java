package com.app.join.sistemajoin.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.join.sistemajoin.R;

public class ViewExibirInformacoesEscola extends AppCompatActivity {

    TextView cmpNomeEsc,cmpTelEsc,cmpEmailEsc,cmpCNPJEsc,cmpLoginEsc,cmpSenhaEsc;
    ImageButton btEditarEsc,btExcluirEsc,btConfigEsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exibir_informacoes_escola);

        //==========inicio conexão java com xml
        cmpNomeEsc=(TextView)findViewById(R.id.cmpNomeEsc);
        cmpTelEsc=(TextView)findViewById(R.id.cmpTelEsc);
        cmpEmailEsc=(TextView)findViewById(R.id.cmpEmailEsc);
        cmpCNPJEsc=(TextView)findViewById(R.id.cmpCNPJEsc);
        cmpLoginEsc=(TextView)findViewById(R.id.cmpLoginEsc);
        cmpSenhaEsc=(TextView)findViewById(R.id.cmpSenhaEsc);
        btConfigEsc=(ImageButton)findViewById(R.id.btConfigEsc);
        btEditarEsc=(ImageButton) findViewById(R.id.btEditarEsc);
        btExcluirEsc=(ImageButton)findViewById(R.id.btExcluirEsc);
        //fim conexão java com xml===========


    }
}
