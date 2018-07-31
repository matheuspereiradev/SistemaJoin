package com.app.join.sistemajoin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class ViewExibirInformacoesEscola extends AppCompatActivity {

    TextView cmpNomeEsc,cmpTelEsc,cmpEmailEsc,cmpCNPJEsc,cmpCidadeEsc,cmpUFEsc,cmpCEPEsc,cmpRuaEsc,cmpNumEsc,cmpBairroEsc;
    ImageButton btEditarEsc,btExcluirEsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exibir_informacoes_escola);

        //==========inicio conexão java com xml
        cmpNomeEsc=(TextView)findViewById(R.id.cmpNomeEsc);
        cmpTelEsc=(TextView)findViewById(R.id.cmpTelEsc);
        cmpEmailEsc=(TextView)findViewById(R.id.cmpEmailEsc);
        cmpCNPJEsc=(TextView)findViewById(R.id.cmpCNPJEsc);
        cmpCidadeEsc=(TextView)findViewById(R.id.cmpCidadeEsc);
        cmpUFEsc=(TextView)findViewById(R.id.cmpUFEsc);
        cmpCEPEsc=(TextView)findViewById(R.id.cmpCEPEsc);
        cmpRuaEsc=(TextView)findViewById(R.id.cmpRuaEsc);
        cmpNumEsc=(TextView)findViewById(R.id.cmpNumEsc);
        cmpBairroEsc=(TextView)findViewById(R.id.cmpBairroEsc);
        btEditarEsc=(ImageButton) findViewById(R.id.btEditarEsc);
        btExcluirEsc=(ImageButton)findViewById(R.id.btExcluirEsc);
        //fim conexão java com xml===========


    }
}
